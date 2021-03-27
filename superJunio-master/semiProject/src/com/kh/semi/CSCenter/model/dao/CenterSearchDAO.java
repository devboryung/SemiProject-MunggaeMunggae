package com.kh.semi.CSCenter.model.dao;
import static com.kh.semi.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.kh.semi.CSCenter.model.vo.Faq;
import com.kh.semi.CSCenter.model.vo.Notice;
import com.kh.semi.CSCenter.model.vo.PageInfo;
import com.kh.semi.CSCenter.model.vo.Qna;

public class CenterSearchDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	

	/** 조건을 만족하는 게시글 수 조회 DAO
	 * @param conn
	 * @param condition
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn, String condition) throws Exception  {

		
		int listCount =0;

		
		String query = "SELECT COUNT(*) FROM V_FAQ_BOARD WHERE FAQ_FL= 'Y' AND " 
				+ condition;
		
		try {
			
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
			
		} finally {
			close(rset);
			close(stmt);
			
		}
		
		
		return listCount;
	}


	/** 검색 게시글 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @param condition
	 * @return bList
	 * @throws Exception
	 */
	public List<Faq> searchFaqList(Connection conn, PageInfo pInfo, String condition) throws Exception {
		
		List<Faq> bList = null;

		
		String query =
		"SELECT * FROM" + 
		"    (SELECT ROWNUM RNUM , V.*" + 
		"    FROM" + 
		"        (SELECT * FROM V_FAQ_BOARD" + 
		"        WHERE " + condition +
		"        AND FAQ_FL = 'Y' ORDER BY FAQ_NO DESC) V )" + 
		"WHERE RNUM BETWEEN ? AND ?";		

		
		try {
		
			int startRow =(pInfo.getCurrentPage() -1 )*pInfo.getLimit() +1;
			int endRow = startRow + pInfo.getLimit()-1;

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();

			bList = new ArrayList<Faq>();

			
			while(rset.next()) {
				Faq faq = new Faq(
								rset.getInt("FAQ_NO"),
								rset.getString("FAQ_TITLE"),
								rset.getString("FAQ_CONTENT"),
								rset.getTimestamp("FAQ_CRATE_DT"),
								rset.getString("FAQ_FL"),
								rset.getInt("FAQ_READ_COUNT"),
								rset.getInt("MEM_NO"),
								rset.getString("MEM_ID"),
								rset.getString("NICKNAME"));
				
						bList.add(faq);
			}
			
		} finally {
	
			close(rset);
			close(pstmt);
		
		}
				
				
				
		
		return bList;
	}


	/** Qna 검색 게시글 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @param condition
	 * @return condition
	 * @throws Exception
	 */
	public List<Qna> searchQnaList(Connection conn, PageInfo pInfo, String condition) throws Exception{
		
		
		List<Qna> bList = null;
		
		
		String query = 
				"SELECT * FROM" + 
				"    (SELECT ROWNUM RNUM , V.*" + 
				"    FROM" + 
				"        (SELECT * FROM V_QNA_BOARD " + 
				"        WHERE " + condition +
				"        AND QNA_FL = 'Y' ORDER BY QNA_NO DESC) V )" + 
				"WHERE RNUM BETWEEN ? AND ?";
   
		try {
			
		int startRow =(pInfo.getCurrentPage() -1 )*pInfo.getLimit() +1;
		int endRow = startRow + pInfo.getLimit()-1;
		
		
		pstmt = conn.prepareStatement(query);

		pstmt.setInt(1, startRow);
		pstmt.setInt(2, endRow);
		
		rset = pstmt.executeQuery();

		bList = new ArrayList<Qna>();

		while(rset.next()) {
			Qna board = new Qna(
					rset.getInt("QNA_NO"),
					rset.getString("QNA_TITLE"),
					rset.getString("QNA_CONTENT"),
					rset.getTimestamp("QNA_CREATE_DT"),
					rset.getString("QNA_PRIVATE"),
					rset.getString("QNA_FL"),
					rset.getString("QNA_REPLY_RESPONSE"),
					rset.getInt("MEM_NO2"),
					rset.getInt("QNA_READ_COUNT"),
					rset.getString("NICKNAME"));
			
			bList.add(board);

		}
		
		
		}finally{
			
			close(rset);
			close(pstmt);
		}		
				
		
		return bList;
	}


	/** Qna 조건을 만족하는 게시글 수 조회
	 * @param conn
	 * @param condition
	 * @return listCount
	 * @throws Exception
	 */
	public int getQnaListCount(Connection conn, String condition) throws Exception{
	
		int listCount =0;

		String query = "SELECT COUNT(*) FROM V_QNA_BOARD WHERE QNA_FL= 'Y' AND QNA_PRIVATE ='N'AND " 
				+ condition;
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
			
		}finally{
			close(rset);
			close(stmt);
		}
		
		return listCount;
	}

 
	/** Notice 검색 게시글 DAO
	 * @param conn
	 * @param pInfo
	 * @param condition
	 * @return bList
	 * @throws Exception
	 */
	public List<Notice> searchNoticeList(Connection conn, PageInfo pInfo, String condition) throws Exception {

		List<Notice> bList = null;
		
		String query =
		"SELECT * FROM" + 
		"    (SELECT ROWNUM RNUM , V.*" + 
		"    FROM" + 
		"        (SELECT * FROM V_NOTICE_BOARD " + 
		"        WHERE " + condition +
		"        AND NOTICE_FL = 'Y' ORDER BY NOTICE_NO DESC) V )" + 
		"WHERE RNUM BETWEEN ? AND ?";	
		
		
		try {
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow =(pInfo.getCurrentPage() -1 )*pInfo.getLimit() +1;
			int endRow = startRow + pInfo.getLimit()-1;
			
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			
			rset = pstmt.executeQuery();
			
			
			bList = new ArrayList<Notice>();
			
			while(rset.next()) {
				Notice notice = new Notice(
								rset.getInt("NOTICE_NO"),
								rset.getString("NOTICE_TITLE"),
								rset.getString("NOTICE_CONTENT"),
								rset.getTimestamp("NOTICE_CREATE_DT"),
								rset.getInt("NOTICE_READCOUNT"),
								rset.getString("NOTICE_FL"),
								rset.getInt("MEM_NO"),
								rset.getString("NICKNAME"));
				
				bList.add(notice);
			}
			
		}finally {
			close(rset);
			close(pstmt);
			
			
		}
		
		return bList;
	}


	/**  조건을 만족하는 게시글 수 조회 DAO
	 * @param conn
	 * @param condition
	 * @return listCount
	 * @throws Exception
	 */
	public int getNoticeListCount(Connection conn, String condition) throws Exception {
		
		int listCount =0;

		String query = "SELECT COUNT(*) FROM V_NOTICE_BOARD WHERE NOTICE_FL= 'Y' AND " 
				+ condition;

		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
			
		}finally{
			close(rset);
			close(stmt);
		}
		
		return listCount;
	}
		
		
		

		

}
