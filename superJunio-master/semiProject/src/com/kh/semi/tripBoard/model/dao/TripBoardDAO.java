package com.kh.semi.tripBoard.model.dao;

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
import com.kh.semi.freeBoard.model.vo.FreeReport;
import com.kh.semi.tripBoard.model.vo.PageInfo;
import com.kh.semi.tripBoard.model.vo.TripBoard;
import com.kh.semi.tripBoard.model.vo.TripReport;

public class TripBoardDAO {
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	
	private Properties prop = null;
	
	
	public TripBoardDAO() {
		String fileName = TripBoardDAO.class.getResource("/com/kh/semi/sql/tripboard/tripBoard-query.xml").getPath();
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
	}
	

	/** 전체 게시글 수 반환 DAO
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public int getListCount(Connection conn) throws Exception{
		int listCount = 0;
		
		String query = prop.getProperty("getListCount");
		
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


	/** 전체 게시글 목록 조회 Service
	 * @param conn
	 * @param pInfo
	 * @return
	 * @throws Exception
	 */
	public List<TripBoard> selectBoardList(Connection conn, PageInfo pInfo) throws Exception{
		List<TripBoard> tList = null;
		
		String query = prop.getProperty("selectBoardList");
		
		
		try {
		int startRow = (pInfo.getCurrentPage() -1) * pInfo.getLimit() + 1;
		int endRow = startRow + pInfo.getLimit() -1 ;
		
		pstmt = conn.prepareStatement(query);
		
		pstmt.setInt(1, startRow);
		pstmt.setInt(2, endRow);
		
		rset = pstmt.executeQuery();
		
		tList = new ArrayList<TripBoard>();
		
		while(rset.next()) {
			TripBoard tripBoard = new TripBoard(rset.getInt("TRIP_BOARD_NO"),
									rset.getString("TRIP_BOARD_TITLE"),
									rset.getString("MEM_ID"),
									rset.getInt("TRIP_READ_COUNT"),
									rset.getTimestamp("TRIP_BOARD_DT"));
			tList.add(tripBoard);
			
			}
		
		}finally {
			close(rset);
			close(pstmt);
		}
	
	return tList;
		
		
	}


	/** 게시글 상세조회 DAO
	 * @param conn
	 * @param boardNo
	 * @return
	 * @throws Exception
	 */
	public TripBoard selectBoard(Connection conn, int boardNo) throws Exception{
		TripBoard board = null;
		
		String query = prop.getProperty("selectBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				board = new TripBoard();
				board.setBoardNo( rset.getInt("TRIP_BOARD_NO") );
				board.setBoardTitle( rset.getString("TRIP_BOARD_TITLE") );
				board.setBoardContent( rset.getString("TRIP_BOARD_CONTENT") );
				board.setMemberId( rset.getString("MEM_ID") );
				board.setReadCount( rset.getInt("TRIP_READ_COUNT") );
				board.setBoardCreateDate( rset.getTimestamp("TRIP_BOARD_DT") );
				
			}
			
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return board;
	}


	/** 조회수 증가 DAO
	 * @param conn
	 * @param boardNo
	 * @return
	 * @throws Exception
	 */
	public int increaseReadCount(Connection conn, int boardNo) throws Exception{
		int result = 0;
		
		String query = prop.getProperty("increaseReadCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		
		return result;
	}


	/** 다음 게시글 번호  DAO
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public int selectNextNo(Connection conn) throws Exception{
		int boardNo = 0;
		
		String query = prop.getProperty("selectNextNo");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				boardNo = rset.getInt(1);
			}
			
		}finally {
			close(rset);
			close(stmt);
		}
		
		return boardNo;
	}

	

	/** 게시글 삽입 DAO
	 * @param conn
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int insertBoard(Connection conn, Map<String, Object> map) throws Exception{
		int result = 0;
		
		String query = prop.getProperty("insertBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, (int)map.get("boardNo"));
			pstmt.setString(2, (String)map.get("boardTitle"));
			pstmt.setString(3, (String)map.get("boardContent"));
			pstmt.setInt(4, (int)map.get("boardWriter"));
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
			
		}
		return result;
	}


	/** 파일 정보 게시글 삽입 DAO
	 * @param conn
	 * @param at
	 * @return
	 * @throws Exception
	 */
	public int insertAttachment(Connection conn, Attachment at) throws Exception{
		int result = 0;
		
		String query = prop.getProperty("insertAttachment");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, at.getFileName());
			pstmt.setString(2, at.getFilePath());
			pstmt.setInt(3, at.getFileLevel());
			pstmt.setInt(4, at.getParentBoardNo());
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		
		return result;
	}


	/** 파일 정보 상세보기 DAO
	 * @param conn
	 * @param boardNo
	 * @return
	 * @throws Exception
	 */
	public List<Attachment> selectBoardFiles(Connection conn, int boardNo) throws Exception{
		List<Attachment> trList = null;
		
		String query = prop.getProperty("selectBoardFiles");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
					
			trList = new ArrayList<Attachment>();
			
			while(rset.next()) {
				
				Attachment at = new Attachment(
								rset.getInt("TRIP_FILE_NO"),
								rset.getString("TRIP_FILE_NAME"),
								rset.getInt("TRIP_FILE_LEVEL"));
				
				at.setFilePath(rset.getString("TRIP_FILE_PATH"));
				
				trList.add(at);
			}
			
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return trList;
	}


	/** 썸네일 얻어오기 Service
	 * @param conn
	 * @param pInfo
	 * @return
	 * @throws Exception
	 */
	public List<Attachment> selectThumbnailList(Connection conn, PageInfo pInfo) throws Exception{
		List<Attachment> trList = null;
		
		String query = prop.getProperty("selectThumbnailList");
		
		try {
			// 위치 홀더에 들어갈 시작 행, 끝 행번호 계산
			int startRow = (pInfo.getCurrentPage() -1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() -1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			trList = new ArrayList<Attachment>();
			
			while(rset.next()) {
				
				Attachment at = new Attachment();
				at.setFileName(rset.getString("TRIP_FILE_NAME"));
				at.setParentBoardNo(rset.getInt("TRIP_BOARD_NO"));
				
				trList.add(at);
			}
			
		}finally {
			
			close(rset);
			close(pstmt);
		}
		
		return trList;
	}


	/** 게시글 수정 DAO
	 * @param conn
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateBoard(Connection conn, Map<String, Object> map) throws Exception{
		int result = 0;
		String query = prop.getProperty("updateBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, (String)map.get("boardTitle"));
			pstmt.setString(2, (String)map.get("boardContent"));
			pstmt.setInt(3, (int)map.get("boardNo"));
			
			result = pstmt.executeUpdate();
			
		}finally {
			
			close(pstmt);
		}
		
		
		return result;
	}


	/** 파일 정보 수정 Service
	 * @param conn
	 * @param newFile
	 * @return
	 * @throws Exception
	 */
	public int updateBoard(Connection conn, Attachment newFile) throws Exception{
		int result = 0;
		
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


	/** 게시글 삭제 DAO
	 * @param conn
	 * @param boardNo
	 * @return
	 * @throws Exception
	 */
	public int delete(Connection conn, int boardNo) throws Exception{
		int result = 0;
		
		String query = prop.getProperty("deleteFl");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
			
		}
		return result;
	}


	/** 여행후기 게시판 신고
	 * @param conn
	 * @param tReport
	 * @return
	 * @throws Exception
	 */
	public int tripReport(Connection conn, TripReport tReport) throws Exception{
		int result = 0;
		
		String query = prop.getProperty("tripReport");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, tReport.getReportTitle());
			pstmt.setString(2, tReport.getReportContent());
			pstmt.setInt(3, tReport.getFreeBoardNo());
			pstmt.setInt(4, tReport.getMemberNo());
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
			
		}
		
		return result;
	}


	/** 신고 수 증가
	 * @param conn
	 * @param reportNum
	 * @return
	 * @throws Exception
	 */
	public int reportNum(Connection conn, int reportNum) throws Exception{
		int result = 0;
		
		String query = prop.getProperty("reportNum");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, reportNum);
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
			
		}
		
		return result;
	}
	
	
	
	

}
