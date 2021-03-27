package com.kh.semi.room.model.dao;

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

import com.kh.semi.member.model.vo.Member;
import com.kh.semi.room.model.vo.Attachment;
import com.kh.semi.room.model.vo.PageInfo;
import com.kh.semi.room.model.vo.Room;



public class RoomDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	private Properties prop = null;
	
	public RoomDAO() {
		String fileName = RoomDAO.class.getResource("/com/kh/semi/sql/room/room-query.xml").getPath();
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	

	/** 전체 숙소 수 반환 DAO
	 * @param conn
	 * @return	listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn) throws Exception {
		int listCount =0;
		String query = prop.getProperty("getListCount");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			while(rset.next()){
				listCount = rset.getInt(1);
			}
		}finally {
			close(rset);
			close(stmt);
		}
		
		return listCount;
	}






	/** 숙소 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @return rList
	 * @throws Exception
	 */
	public List<Room> selectRoomList(Connection conn, PageInfo pInfo) throws Exception {
		List<Room> rList = null;
		String query = prop.getProperty("selectRoomList");
		
		try {
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow = (pInfo.getCurrentPage()-1) * pInfo.getLimit()+1;
			int endRow = startRow + pInfo.getLimit()-1;
			// 7개의 글 중에서 1페이지에 해당하는 글을 가져옴 : 7~2번째의 글만 가져오게 됨.
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2,  endRow);
			
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






	/** 숙소 상세조회 DAO
	 * @param conn
	 * @param roomNo
	 * @return room
	 * @throws Exception
	 */
	public Room selectRoom(Connection conn, int roomNo) throws Exception {
		Room room = null;
		String query = prop.getProperty("selectRoom");
		
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, roomNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				room = new Room();
				room.setRoomName(rset.getString("ROOM_NAME"));
				room.setLocation2(rset.getString("LOCATION2"));
				room.setPhone(rset.getString("PHONE"));
				room.setRoomInfo(rset.getString("ROOM_INFO"));
				room.setCheckin(rset.getString("CHECKIN"));
				room.setCheckout(rset.getString("CHECKOUT"));
				room.setFacility(rset.getString("FACILITY"));
				room.setDog(rset.getString("DOG"));
				room.setViewCount(rset.getInt("VIEW_COUNT"));
				room.setMemNo(rset.getInt("MEM_NO"));
				
			}
			
		}finally {
			close(rset);
			close(pstmt);
		}
		return room;
	}






	/** 조회 수 증가 DAO
	 * @param conn
	 * @param roomNo
	 * @return result
	 * @throws Exception
	 */
	public int increaseReadCount(Connection conn, int roomNo) throws Exception {
		int result =0;
		
		String query = prop.getProperty("increaseReadCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, roomNo);
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		
		return result;
	}






	/** 숙소 다음번호 받아오기 Service
	 * @param conn
	 * @return insertNo
	 * @throws Exception
	 */
	public int selectNextNo(Connection conn) throws Exception {
		int insertNo = 0;
		String query = prop.getProperty("selectNextNo");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				insertNo = rset.getInt(1);
			}
		}finally {
			close(rset);
			close(stmt);
		}
		return insertNo;
	}




	
	


	/** 숙소 등록 DAO
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertRoom(Connection conn, Map<String, Object> map) throws Exception {
		int result =0;
		
		String query = prop.getProperty("insertRoom");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, (int)map.get("insertNo"));
			pstmt.setString(2, (String)map.get("roomName"));
			pstmt.setString(3, (String)map.get("location2"));
			pstmt.setString(4, (String)map.get("phone"));
			pstmt.setString(5, (String)map.get("roomInfo"));
			pstmt.setString(6, (String)map.get("checkin"));
			pstmt.setString(7, (String)map.get("checkout"));
			pstmt.setString(8, (String)map.get("facility"));
			pstmt.setString(9, (String)map.get("dog"));
			pstmt.setInt(10, (int)map.get("memberNo"));
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		
		return result;
	}






	/**	파일 정보 등록 DAO
	 * @param conn
	 * @param at
	 * @return result
	 * @throws Exception
	 */
	public int insertAttachment(Connection conn, Attachment at) throws Exception {
		int result =0;
		
		String query = prop.getProperty("insertAttachment");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, at.getFilePath());
			pstmt.setString(2, at.getFileName());
			pstmt.setInt(3, at.getFileLevel());
			pstmt.setInt(4, at.getRoomNo());
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		return result;
	}






	/**	숙소에 포함된 이미지 목록 조회 DAO
	 * @param conn
	 * @param roomNo
	 * @return	fList
	 * @throws Exception
	 */
	public List<Attachment> selectRoomFiles(Connection conn, int roomNo) throws Exception {
		List<Attachment> fList = null;
		String query = prop.getProperty("selectRoomFiles");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,roomNo);
			
			rset = pstmt.executeQuery();
			
			fList = new ArrayList<Attachment>();
			
			while(rset.next()) {
				Attachment at = new Attachment( rset.getInt("FILE_NO"),
                        rset.getString("FILE_NAME"),
                        rset.getInt("FILE_LEVEL"));
				
				 at.setFilePath(rset.getString("FILE_PATH") );
				
				fList.add(at);
			}
			
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return fList;
	}






	/** 업체 정보 조회 DAO
	 * @param conn
	 * @param memberNo
	 * @return	comMember
	 * @throws Exception
	 */
	public Member selectComMember(Connection conn, int memberNo) throws Exception {
Member comMember = null;
		
		String query = prop.getProperty("selectComMember");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				comMember = new Member(rset.getNString("COO_NM"), rset.getNString("COO_ADDR"), rset.getNString("COO_PHONE"));
			}
			
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return comMember;
	}






	/** 썸네일 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @return fList
	 * @throws Exception
	 */
	public List<Attachment> selectThumbnailList(Connection conn, PageInfo pInfo) throws Exception {
		List<Attachment> fList =null;
		
		String query = prop.getProperty("selectThumbnailList");
		
		// 위치 홀더에 들어갈 시작 행, 끝 행번호 계산
					int startRow = (pInfo.getCurrentPage() -1) * pInfo.getLimit() +1;
					int endRow = startRow + pInfo.getLimit()-1;
			  try {		
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, startRow);
					pstmt.setInt(2, endRow);
					
					rset = pstmt.executeQuery();
					
					fList = new ArrayList<Attachment>();
					
					while(rset.next()) {
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






	/**	숙소 수정 DAO
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int updateRoom(Connection conn, Map<String, Object> map) throws Exception {
		int result =0;
		String query = prop.getProperty("updateRoom");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, (String)map.get("roomInfo"));
			pstmt.setString(2, (String)map.get("checkin"));
			pstmt.setString(3, (String)map.get("checkout"));
			pstmt.setString(4, (String)map.get("facility"));
			pstmt.setString(5, (String)map.get("dog"));
			pstmt.setInt(6, (int)map.get("roomNo"));
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		return result;
	}






	/**	파일 정보 수정 DAO
	 * @param conn
	 * @param newFile
	 * @return	result
	 * @throws Exception
	 */
	public int updateAttachment(Connection conn, Attachment newFile) throws Exception {
		int result =0;
		
		String query = prop.getProperty("updateAttachment");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newFile.getFilePath());
			pstmt.setString(2, newFile.getFileName());
			pstmt.setInt(3, newFile.getFileNo());
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		
		
		return result;
	}






	/**	숙소 삭제 DAO
	 * @param conn
	 * @param roomNo
	 * @return	result
	 * @throws Exception
	 */
	public int deleteRoom(Connection conn, int roomNo) throws Exception {
		int result =0;
		String query = prop.getProperty("deleteRoom");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, roomNo);
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	
	
	

}
