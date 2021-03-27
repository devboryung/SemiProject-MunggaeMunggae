package com.kh.semi.travel.model.dao;

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

import com.kh.semi.freeBoard.model.vo.Attachment;
import com.kh.semi.hospital.model.dao.HospitalDAO;
import com.kh.semi.hospital.model.vo.Hospital;
import com.kh.semi.hospital.model.vo.PageInfo;
import com.kh.semi.travel.model.vo.Travel;
import com.kh.semi.travel.model.vo.TravelAttachment;

public class TravelDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	private Properties prop = null;	

	public TravelDAO() {
		String fileName = TravelDAO.class.getResource("/com/kh/semi/sql/travel/travel-query.xml").getPath();
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/** 전체 지역정보 게시글 수 반환 DAO 
	 * @param conn
	 * @return listCount
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


	/** 여행 지역정보 목록 조회 DAO 
	 * @param conn
	 * @param pInfo
	 * @return tList
	 * @throws Exception
	 */
	public List<Travel> selectTravelList(Connection conn, PageInfo pInfo) throws Exception {
		List<Travel> tList = null;
		String query = prop.getProperty("selectTravelList");
		
		try {
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow = (pInfo.getCurrentPage()-1) * pInfo.getLimit()+1;
			int endRow = startRow + pInfo.getLimit()-1;
			// 7개의 글 중에서 1페이지에 해당하는 글을 가져옴 : 7~2번째의 글만 가져오게 됨.
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2,  endRow);
			
			rset = pstmt.executeQuery();
			
			tList = new ArrayList<Travel>();
			
			while(rset.next()) {
				Travel travel = new Travel(rset.getInt("TRAVEL_NO"),
											rset.getString("TRAVEL_LOCATION"),
											rset.getString("TRAVEL_TITLE"),
											rset.getInt("TRAVEL_READ_COUNT"),
											rset.getDate("TRAVEL_BOARD_DATE"));
				tList.add(travel);
			}
			
		}finally {
			close(rset);
			close(pstmt);
			
		}
		return tList;
	}


	/** 지역정보 글 상세 조회 DAO 
	 * @param conn
	 * @param travelNo
	 * @return travel 
	 * @throws Exception
	 */
	public Travel selectTravel(Connection conn, int travelNo) throws Exception {
		// 1) 결과를 저장할 변수 선언
		Travel travel = null;
		
		// 2) SQL 구문 얻어오기
		String query = prop.getProperty("selectTravel");
		
		// 3) SQL 수행 후 결과를 notice에 저장
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, travelNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				travel = new Travel();
				
				travel.setTravelNo(rset.getInt("TRAVEL_NO"));
				travel.setTravelLocation(rset.getString("TRAVEL_LOCATION"));
				travel.setTravelTitle(rset.getString("TRAVEL_TITLE"));
				travel.setTravelContent(rset.getString("TRAVEL_CONTENT"));
				travel.setTravelReadCount(rset.getInt("TRAVEL_READ_COUNT"));
				travel.setTravelBoardDate(rset.getDate("TRAVEL_BOARD_DATE"));
				travel.setMemNo(rset.getInt("MEM_NO"));
			}
		} finally {
			// 4) 사용한 JDBC 객체 반환
			close(rset);
			close(pstmt);
		}
		// 5) 결과 반
		return travel;
	}


	/** 조회수 증가 DAO
	 * @param conn
	 * @param travelNo
	 * @return result
	 * @throws Exception
	 */
	public int increaseReadCount(Connection conn, int travelNo) throws Exception {
		int result = 0; 
		String query = prop.getProperty("increaseReadCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, travelNo);
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}


	/** 게시글 삽입 DAO 
	 * @param conn
	 * @param map
	 * @return result 
	 * @throws Exception
	 */
	public int insertBoard(Connection conn, Map<String, Object> map) throws Exception {
		int result = 0; 
		String query = prop.getProperty("insertBoard");
		
		try {
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, (int)map.get("travelNo")  );
			pstmt.setString(2, (String)map.get("travelLocation")  );
			pstmt.setString(3, (String)map.get("travelTitle")  );
			pstmt.setString(4, (String)map.get("travelContent")  );
			pstmt.setInt(5, (int)map.get("boardWriter")  );
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		
		
		return result;
	}


	/** 파일 정보 삽입 DAO 
	 * @param conn
	 * @param at
	 * @return result 
	 * @throws Exception
	 */
	public int insertAttachment(Connection conn, TravelAttachment at) throws Exception {
		int result = 0;
		String query = prop.getProperty("insertAttachment");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, at.getFilePath());
			pstmt.setString(2, at.getFileName());
			pstmt.setInt(3, at.getFileLevel());
			pstmt.setInt(4, at.getParentBoardNo());
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	/** 다음 게시글 번호 조회 DAO 
	 * @param conn
	 * @return travelNo 
	 * @throws Exception
	 */
	public int selectNextNo(Connection conn) throws Exception {
		int travelNo = 0;
		String query = prop.getProperty("selectNextNo");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				travelNo = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		
		return travelNo;
	}


	/**
	 * @param conn
	 * @param travelNo
	 * @return
	 * @throws Exception
	 */
	public List<TravelAttachment> selectBoardFiles(Connection conn, int travelNo) throws Exception{
		List<TravelAttachment> fList = null;
		
		String query = prop.getProperty("selectBoardFiles");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, travelNo);
			
			rset = pstmt.executeQuery();
					
			fList = new ArrayList<TravelAttachment>();
			
			while(rset.next()) {
				
				TravelAttachment at = new TravelAttachment(
								rset.getInt("TRVEL_IMG_NO"),
								rset.getString("TRVEL_IMG_NAME"),
								rset.getInt("TRVEL_IMG_LEVEL"));
				
				at.setFilePath(rset.getString("TRVEL_IMG_PATH"));
				
				fList.add(at);
			}
			
			
		}finally{
			close(rset);
			close(pstmt);
			
		}
		
		return fList;
	}


	/** 썸네일용 DAO 
	 * @param conn
	 * @param pInfo
	 * @return fList 
	 * @throws Exception
	 */
	public List<TravelAttachment> selectThumbnailList(Connection conn, PageInfo pInfo) throws Exception {
		List<TravelAttachment> fList = null;
		
		String query = prop.getProperty("selectThumbnailList");
		
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


	/** 수정 업데이트 DAO 
	 * @param conn
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateTravel(Connection conn, Map<String, Object> map) throws Exception {
		int result = 0; 
		String query = prop.getProperty("updateTravel"); 
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString( 1, (String)map.get("travelLocation"));
			pstmt.setString( 2, (String)map.get("travelContent"));
			pstmt.setString( 3, (String)map.get("travelTitle"));
			pstmt.setInt( 4, (int)map.get("travelNo"));
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	/** 여행 정보 이미지 수정 DAO  
	 * @param conn
	 * @param newFile
	 * @return
	 * @throws Exception
	 */
	public int updateAttachment(Connection conn, TravelAttachment newFile) throws Exception {
		int result = 0;
		String query = prop.getProperty("updateAttachment");
		System.out.println(newFile);
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newFile.getFilePath());
			pstmt.setString(2, newFile.getFileName());
			pstmt.setInt(3, newFile.getFileNo());
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	/** 게시글 삭제 DAO 
	 * @param conn
	 * @param travelNo
	 * @return result
	 * @throws Exception
	 */
	public int updateBoardFl(Connection conn, int travelNo) throws Exception {
		int result = 0 ;
		String query = prop.getProperty("updateBoardFl");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, travelNo);
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}





}
