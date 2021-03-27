package com.kh.semi.mypage.dao;

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

import com.kh.semi.mypage.vo.rBoard;
import com.kh.semi.mypage.vo.PageInfo;

public class replyDAO {
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;

	private Properties prop = null;

	public replyDAO() {
		String fileName = replyDAO.class.getResource("/com/kh/semi/sql/mypage/reply-query.xml").getPath();
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
	public int getListCount(Connection conn, int memberNo) throws Exception {
		int listCount = 0;

		String query = prop.getProperty("getListCount");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			
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
	 * @return rList
	 * @throws Exception
	 */
	public List<rBoard> selectBoardList(Connection conn, PageInfo pInfo, int memberNo) throws Exception {
		List<rBoard> rList = null;

		String query = prop.getProperty("selectBoardList");

		try {
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, memberNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);

			rset = pstmt.executeQuery();

			rList = new ArrayList<rBoard>();
/*
			while (rset.next()) {
				rBoard rBoard = new rBoard(rset.getInt("FREE_BOARD_NO"), 
						rset.getString("FREE_BOARD_TITLE"), 
						rset.getTimestamp("FREE_BOARD_DATE"), 
						rset.getInt("FREE_READ_COUNT"));
				
				rList.add(rBoard);
			}*/

		} finally {
			close(rset);
			close(pstmt);
		}
		return rList;
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