package com.kh.semi.boardSearch.model.dao;

import static com.kh.semi.common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kh.semi.tripBoard.model.vo.Attachment;
import com.kh.semi.tripBoard.model.vo.PageInfo;
import com.kh.semi.tripBoard.model.vo.TripBoard;


public class SearchDAO2 {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;

	/**
	 * @param conn
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int getListCount(Connection conn, String condition) throws Exception{
		int listCount = 0;
		
		String query = "SELECT COUNT(*) FROM TV_BOARD WHERE TRIP_BOARD_DELETEFL = 'N' AND " + condition;
		
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

	/** 검색 게시글 목록 조회
	 * @param conn
	 * @param pInfo
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<TripBoard> searchBoardList(Connection conn, PageInfo pInfo, String condition) throws Exception{
		List<TripBoard> tList = null;
		
		String query = 
				"SELECT * FROM" + 
				"    (SELECT ROWNUM RNUM , V.*" + 
				"    FROM" + 
				"        (SELECT * FROM TV_BOARD " + 
				"        WHERE " + condition +
				"        AND TRIP_BOARD_DELETEFL = 'N' ORDER BY TRIP_BOARD_NO DESC) V )" + 
				"WHERE RNUM BETWEEN ? AND ?";
		
		try {
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow = (pInfo.getCurrentPage() -1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() -1 ;
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			tList = new ArrayList<TripBoard>();
			
			while(rset.next()) {
				TripBoard board = new TripBoard(rset.getInt("TRIP_BOARD_NO"),
							rset.getString("TRIP_BOARD_TITLE"), 
							rset.getString("MEM_ID"),
							rset.getInt("TRIP_READ_COUNT"), 
							rset.getTimestamp("TRIP_BOARD_DT"));
				
				tList.add(board);
			}
			
		}finally {
			
			close(rset);
			close(pstmt);
		}
		
		
		return tList;
	}

	/** 검색 썸네일 이미지 가져오기 
	 * @param conn
	 * @param pInfo
	 * @param condition
	 * @return imgList
	 * @throws Exception
	 */
	public List<Attachment> searchImgList(Connection conn, PageInfo pInfo, String condition) throws Exception {

		List<Attachment> imgList = null;

		
/*		String query = 
				
		"SELECT * FROM" + 
		"    (SELECT ROWNUM RNUM , V.*" + 
		"    FROM" + 
		"        (SELECT * FROM TV_BOARD " + 
		"        WHERE " + condition +
		"        AND TRIP_BOARD_DELETEFL = 'N' ORDER BY TRIP_BOARD_NO DESC) V )" + 
		"WHERE RNUM BETWEEN ? AND ?";*/

		String query = 
	
		"SELECT * FROM" +
		" (SELECT ROWNUM RNUM , V.*"+
		"  FROM " +
		" 		(SELECT TRIP_BOARD_NO ,TRIP_FILE_NAME " +
		" 		 FROM TV_BOARD"+
		" 		JOIN TRIP_REVIEW_IMAGE USING(TRIP_BOARD_NO)" +
		" 		WHERE" + condition +
		" 		AND TRIP_BOARD_DELETEFL = 'N' " + 
		" 		ORDER BY TRIP_BOARD_NO DESC) V ) "+
		" 		WHERE RNUM BETWEEN ? AND ? " ;
		
		try {
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow = (pInfo.getCurrentPage() -1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() -1 ;
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			imgList = new ArrayList<Attachment>();
			
			while(rset.next()) {
				Attachment boardImg = new Attachment(
							rset.getString("TRIP_FILE_NAME"),
							rset.getInt("TRIP_BOARD_NO")
							);
				imgList.add(boardImg);
			}
			
		}finally {
			
			close(rset);
			close(pstmt);
		}
		
		
		return imgList;
	}

		
		
}