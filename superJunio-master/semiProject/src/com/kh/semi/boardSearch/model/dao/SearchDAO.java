package com.kh.semi.boardSearch.model.dao;

import static com.kh.semi.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kh.semi.freeBoard.model.vo.FreeBoard;
import com.kh.semi.freeBoard.model.vo.PageInfo;

public class SearchDAO {
	
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
		
		String query = "SELECT COUNT(*) FROM FV_BOARD WHERE FREE_BOARD_DELETEFL = 'N' AND " + condition;
		
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
	public List<FreeBoard> searchBoardList(Connection conn, PageInfo pInfo, String condition) throws Exception{
		List<FreeBoard> bList = null;
		
		String query = 
				"SELECT * FROM" + 
				"    (SELECT ROWNUM RNUM , V.*" + 
				"    FROM" + 
				"        (SELECT * FROM FV_BOARD " + 
				"        WHERE " + condition +
				"        AND FREE_BOARD_DELETEFL = 'N' ORDER BY FREE_BOARD_NO DESC) V )" + 
				"WHERE RNUM BETWEEN ? AND ?";
		
		try {
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow = (pInfo.getCurrentPage() -1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() -1 ;
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			bList = new ArrayList<FreeBoard>();
			
			while(rset.next()) {
				FreeBoard board = new FreeBoard(rset.getInt("FREE_BOARD_NO"),
							rset.getString("FREE_BOARD_TITLE"), 
							rset.getString("MEM_ID"),
							rset.getInt("FREE_READ_COUNT"), 
							rset.getTimestamp("FREE_BOARD_DATE"));
				
				bList.add(board);
			}
			
		}finally {
			
			close(rset);
			close(pstmt);
		}
		
		
		return bList;
	}

}
