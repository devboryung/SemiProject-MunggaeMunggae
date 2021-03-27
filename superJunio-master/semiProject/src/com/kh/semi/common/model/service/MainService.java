package com.kh.semi.common.model.service;

import static com.kh.semi.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.kh.semi.common.model.dao.MainDAO;
import com.kh.semi.room.model.vo.Attachment;
import com.kh.semi.room.model.vo.PageInfo;
import com.kh.semi.room.model.vo.Room;

public class MainService {
	
	private MainDAO dao = new MainDAO();
	
	/** 메인  인기 숙소 조회 Service
	 * @return room
	 * @throws Exception
	 */
	public List<Room> roomList() throws Exception {
		Connection conn = getConnection();

		List<Room> roomList = dao.roomList(conn);

		close(conn);

		return roomList;
	}

	
	
	/** 숙소 썸네일 조회
	 * @return fList
	 * @throws Exception
	 */
	public List<Attachment> selectThumbnailList() throws Exception{
		Connection conn = getConnection();
		
		List<Attachment> fList = dao.selectThumbnailList(conn);
		
		close(conn);
				
		return fList;
	}

	

}
