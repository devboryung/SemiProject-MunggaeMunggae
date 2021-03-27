package com.kh.semi.travelSearch.model.dao;

import static com.kh.semi.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kh.semi.travel.model.vo.Travel;
import com.kh.semi.travel.model.vo.TravelAttachment;
import com.kh.semi.travel.model.vo.PageInfo;

public class SearchDAO {
	
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
		
		String query = "SELECT COUNT(*) FROM TRAVEL WHERE TRAVEL_DEL_FL = 'N' AND "
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
   public List<Travel> searchTravelList(Connection conn, PageInfo pInfo, String condition) throws Exception {
      
      List<Travel> bList = null;
      
      String query = 
				"SELECT * FROM" + 
				"    (SELECT ROWNUM RNUM , V.*" + 
				"    FROM" + 
				"        (SELECT * FROM TRAVEL " + 
				"        WHERE " + condition + 
				"        AND TRAVEL_DEL_FL = 'N' ORDER BY TRAVEL_NO DESC) V )" + 
				"WHERE RNUM BETWEEN ? AND ?";

      try {
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1 ; // 1
			int endRow = startRow + pInfo.getLimit() - 1 ; // 10 
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			bList = new ArrayList<Travel>();
			
			while(rset.next()) {
				Travel travel = new Travel(rset.getInt("TRAVEL_NO"),
										rset.getString("TRAVEL_LOCATION"),
										rset.getString("TRAVEL_TITLE"),
					            		rset.getInt("TRAVEL_READ_COUNT"),
					            		rset.getDate("TRAVEL_BOARD_DATE"));
	            bList.add(travel);
	         }
		} finally {
			close(rset);
			close(pstmt);
		}
      
      return bList;
   }

	/** 검색이 적용된 썸네일 목록 조회 DAO 
	 * @param conn
	 * @param pInfo
	 * @param condition
	 * @return fList
	 * @throws Exception
	 */
	public List<TravelAttachment> searchThumbnailList(Connection conn, PageInfo pInfo, String condition) throws Exception {
		
		List<TravelAttachment> fList = null;
		
		String query = 
		"SELECT TRVEL_IMG_NAME, TRAVEL_NO FROM TRAVEL_IMG " + 
		"WHERE TRAVEL_NO IN (" + 
		"    SELECT TRAVEL_NO FROM " + 
		"    (SELECT ROWNUM RNUM, V.* FROM " + 
		"            (SELECT TRAVEL_NO  FROM TRAVEL " + 
		"            WHERE TRAVEL_DEL_FL='N' " + 
		"            AND " + condition + 
		"            ORDER BY TRAVEL_NO DESC ) V) " + 
		"    WHERE RNUM BETWEEN ? AND ?" + 
		") " + 
		"AND TRVEL_IMG_LEVEL = 0";
		
		try {
			// 위치 홀더에 들어갈 시작행, 끝 행번호 계싼
			int startRow = (pInfo.getCurrentPage() -1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1 ;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			// 조회 결과를 저장할 List 생성
			fList = new ArrayList<TravelAttachment>();
			
			while(rset.next()) {
				
				TravelAttachment at = new TravelAttachment();
				at.setFileName(rset.getString("TRVEL_IMG_NAME"));
				at.setParentBoardNo(rset.getInt("TRAVEL_NO"));
				
				fList.add(at);
			}
			
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return fList;
	}
	
}
