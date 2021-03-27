package com.kh.semi.manager.model.dao;

import static com.kh.semi.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.kh.semi.mypage.vo.fBoard;
import com.kh.semi.freeBoard.model.vo.Attachment;
import com.kh.semi.freeBoard.model.vo.FreeBoard;
import com.kh.semi.member.model.vo.Member;
import com.kh.semi.mypage.vo.PageInfo;

public class companyDAO {
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;

	private Properties prop = null;

	public companyDAO() {
		String fileName = companyDAO.class.getResource("/com/kh/semi/sql/mypage/company-query.xml").getPath();
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 전체 게시글 수 반환 DAO
	 * 
	 * @param conn
	 * @param memberNo 
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn) throws Exception {
		int listCount = 0;

		String query = prop.getProperty("getListCount");

		try {
			pstmt = conn.prepareStatement(query);
			
			rset = pstmt.executeQuery();

			if (rset.next()) {
				listCount = rset.getInt(1);
			}

		} finally {
			close(pstmt);
			close(rset);
		}
		return listCount;
	}

	/**
	 * 게시글 목록 조회 DAO
	 * 
	 * @param conn
	 * @param pInfo
	 * @param memberNo 
	 * @return cList
	 * @throws Exception
	 */
	public List<Member> memberList(Connection conn, PageInfo pInfo) throws Exception {
		List<Member> cList = null;

		String query = prop.getProperty("memberList");

		try {
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);

			rset = pstmt.executeQuery();

			cList = new ArrayList<Member>();

			while (rset.next()) {
				Member member = new Member(
						rset.getString("MEM_ID"), 
						rset.getString("NICKNAME"), 
						rset.getInt("MEM_NO"), 
						rset.getString("PHONE"), 
						rset.getString("COO_NM"), 
						rset.getString("COO_PHONE"));
				
				cList.add(member);
			}

		} finally {
			close(rset);
			close(pstmt);
		}
		return cList;
	}

	
	/**
	 * 다음 게시글 번호 조회 DAO
	 * 
	 * @param conn
	 * @return boardNo
	 * @throws Exception
	 */
	public int selectNextNo(Connection conn) throws Exception {
		int fBoardNo = 0;

		String query = prop.getProperty("selectNextNo");

		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);

			if (rset.next()) {
				fBoardNo = rset.getInt(1);
			}

		} finally {
			close(rset);
			close(stmt);
		}

		return fBoardNo;
	}
	
}