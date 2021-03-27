package com.kh.semi.room.model.dao;

import static com.kh.semi.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kh.semi.room.model.vo.Attachment;
import com.kh.semi.room.model.vo.PageInfo;
import com.kh.semi.room.model.vo.Room;

public class RSearchDAO {
	
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
	
		int listCount =0;
		
		String query = "SELECT COUNT(*) FROM ROOM WHERE ROOM_DEL_FL = 'N' AND " + condition;
		
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


	


	/** 검색된 숙소 목록 조회
	 * @param conn
	 * @param condition
	 * @param pInfo
	 * @return	rList
	 * @throws Exception
	 */
	public List<Room> searchRoomList(Connection conn, String condition, PageInfo pInfo) throws Exception {
		List<Room> rList=null;
		String query = "SELECT ROOM_NO, ROOM_NAME, LOCATION2 FROM" + 
	            "    (SELECT ROWNUM RNUM , R.*" + 
	            "    FROM" + 
	            "        (SELECT * FROM ROOM " + 
	            "        WHERE " + condition +
	            "        AND ROOM_DEL_FL = 'N' ORDER BY ROOM_NO DESC) R )" + 
	            "WHERE RNUM BETWEEN ? AND ?";
		
		
		try {
			
			// SQL 구문 조건절에 대입할 변수 생성 (매 페이지 시작번호 1,7,13... , 매 페이지 끝 번호 6,12,19... )
			int startRow = (pInfo.getCurrentPage()-1) * pInfo.getLimit()+1;
			int endRow = startRow + pInfo.getLimit()-1;
			
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			rList = new ArrayList<Room>();
			
			while(rset.next()) {
				Room room = new Room(rset.getInt("ROOM_NO"), 
						rset.getString("ROOM_NAME"), rset.getString("LOCATION2"));
				
				rList.add(room);
			}
			
		}finally {
			close(rset);
			close(pstmt);
		}
		return rList;
	}





	/** 검색이 적용된 썸네일 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @param condition
	 * @return	fList
	 * @throws Exception
	 */
	public List<Attachment> searchThumbnailList(Connection conn, PageInfo pInfo, String condition) throws Exception {
		List<Attachment> fList = null;
		
		String query = "SELECT FILE_NAME, ROOM_NO FROM ROOM_IMG " + 
				"WHERE ROOM_NO IN (" + 
				"    SELECT ROOM_NO FROM " + 
				"    (SELECT ROWNUM RNUM, R.* FROM " + 
				"            (SELECT ROOM_NO  FROM ROOM " + 
				"            WHERE ROOM_DEL_FL='N' " + 
				"            AND " + condition + 
				"            ORDER BY ROOM_NO DESC ) R) " + 
				"    WHERE RNUM BETWEEN ? AND ?" + 
				") " + 
				"AND FILE_LEVEL = 0";
		try {
			// 위치 홀더에 들어갈 시작 행, 끝 행번호 계산
			int startRow = (pInfo.getCurrentPage() -1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit()-1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			fList = new ArrayList<Attachment>();
			while(rset.next()){
				Attachment at = new Attachment();
				at.setFileName(rset.getString("FILE_NAME"));
				at.setRoomNo(rset.getInt("ROOM_NO"));
				
				fList.add(at);
			}
		}finally {
			close(rset);
			close(pstmt);
		}

	return fList;
	}

}
