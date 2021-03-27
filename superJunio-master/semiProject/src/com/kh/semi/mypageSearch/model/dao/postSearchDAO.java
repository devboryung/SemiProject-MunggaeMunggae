package com.kh.semi.mypageSearch.model.dao;

import static com.kh.semi.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kh.semi.mypage.vo.PageInfo;
import com.kh.semi.mypage.vo.fBoard;


public class postSearchDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	
	
	/** 조건을 만족하는 게시글 수 조회 DAO
	    * @param conn
	    * @param condition
	    * @return listCount
	    * @throws Exception
	    */
	   public int getListCount(Connection conn, String condition) throws Exception {
	      int listCount = 0;
	      
	      String query = "SELECT COUNT(*) FROM FREE_BOARD WHERE FREE_BOARD_DELETEFL = 'N' AND " + condition;
	      
	      try {
	         stmt = conn.createStatement();
	         
	         rset = stmt.executeQuery(query);
	         
	         if(rset.next()) {
	            listCount = rset.getInt(1);
	         }
	      }finally {
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
	public List<fBoard> fSearchList(Connection conn, PageInfo pInfo, String condition) throws Exception {
		
		List<fBoard> fList = null;
		
		String query = 
				"SELECT * FROM" + 
				"    (SELECT ROWNUM RNUM , V.*" + 
				"    FROM" + 
				"        (SELECT * FROM FREE_BOARD " + 
				"        WHERE " + condition +
				"        AND FREE_BOARD_DELETEFL = 'N' ORDER BY FREE_BOARD_NO DESC) V )" + 
				"WHERE RNUM BETWEEN ? AND ?";
		
		try {
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow = (pInfo.getCurrentPage() -1) * pInfo.getLimit() +1;
			int endRow = startRow + pInfo.getLimit()-1;
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			fList = new ArrayList<fBoard>();
			
			while(rset.next()) {
				fBoard fBoard = new fBoard(rset.getInt("FREE_BOARD_NO"), 
						rset.getString("FREE_BOARD_TITLE"), 
						rset.getTimestamp("FREE_BOARD_DATE"), 
						rset.getInt("FREE_READ_COUNT"));
				fList.add(fBoard);
			}
			
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return fList;
	}

	
}
