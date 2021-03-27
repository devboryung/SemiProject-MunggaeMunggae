package com.kh.semi.common.model.dao;

import static com.kh.semi.common.JDBCTemplate.*;

import java.lang.reflect.Executable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.semi.room.model.vo.Attachment;
import com.kh.semi.room.model.vo.Room;

public class MainDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	private Properties prop = null;
	
	
	public List<Room> roomList(Connection conn) throws Exception {
		List<Room> roomList = null;
		String query =  "SELECT * FROM ROOM \r\n" + 
				"JOIN ROOM_IMG USING (ROOM_NO) \r\n" + 
				"WHERE ROOM_NO \r\n" + 
				"IN (SELECT ROOM_NO FROM  \r\n" + 
				"    (SELECT ROWNUM RNUM, R.* FROM  \r\n" + 
				"        (SELECT ROOM_NO FROM ROOM \r\n" + 
				"        WHERE ROOM_DEL_FL='N'\r\n" + 
				"         ORDER BY VIEW_COUNT DESC)R) \r\n" + 
				"WHERE RNUM BETWEEN 1 AND 3 )\r\n" + 
				"AND FILE_LEVEL =0\r\n" + 
				"ORDER BY VIEW_COUNT DESC";
				

		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);

			roomList = new ArrayList<Room>();

			while(rset.next()) {
				Room room = new Room();
				room.setRoomName(rset.getString("ROOM_NAME"));
				room.setLocation2(rset.getString("LOCATION2"));
				room.setRoomNo(rset.getInt("ROOM_NO"));
				
				// 썸네일
				
				roomList.add(room);
			}

		}finally {
			close(rset);
			close(stmt);

		}

		return roomList;
	}

	
	

	/** 숙소 썸네일 목록 조회 DAO
	 * @param conn
	 * @return	fList
	 * @throws Exception
	 */
	public List<Attachment> selectThumbnailList(Connection conn) throws Exception {
		List<Attachment> fList = null;
		String query = "SELECT * FROM ROOM_IMG " + 
				"JOIN ROOM USING(ROOM_NO) " + 
				"WHERE ROOM_NO " + 
				"IN (SELECT ROOM_NO FROM " + 
				"        (SELECT ROWNUM RNUM, R.* FROM " + 
				"            (SELECT ROOM_NO FROM ROOM" + 
				"                WHERE ROOM_DEL_FL='N'" + 
				"                ORDER BY VIEW_COUNT DESC) R)" + 
				"WHERE RNUM BETWEEN 1 AND 3)" + 
				"AND FILE_LEVEL =0 " + 
				"ORDER BY VIEW_COUNT DESC";
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			fList = new ArrayList<Attachment>();
			
			while(rset.next()) {
				Attachment at = new Attachment();
				at.setFileNo(rset.getInt("FILE_NO"));
				at.setFilePath(rset.getString("FILE_PATH"));
				at.setFileName(rset.getString("FILE_NAME"));
				at.setFileLevel(rset.getInt("FILE_LEVEL"));
				at.setRoomNo(rset.getInt("ROOM_NO"));
				
				fList.add(at);
			}
			
		}finally {
			close(rset);
			close(stmt);
		}
		return fList;
	}

}
