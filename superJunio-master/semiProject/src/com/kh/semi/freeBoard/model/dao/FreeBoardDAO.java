package com.kh.semi.freeBoard.model.dao;

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
import com.kh.semi.freeBoard.model.vo.FreeBoard;
import com.kh.semi.freeBoard.model.vo.FreeReport;
import com.kh.semi.freeBoard.model.vo.PageInfo;

public class FreeBoardDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	
	private Properties prop = null;
	
	public FreeBoardDAO() {
		String fileName = FreeBoardDAO.class.getResource("/com/kh/semi/sql/freeboard/freeBoard-query.xml").getPath();
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	

	/** 전체 게시글 수 반환 DAO
	 * @param conn
	 * @return listCount
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



	/** 게시글 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @return bList
	 * @throws Exception
	 */
	public List<FreeBoard> selectBoardList(Connection conn, PageInfo pInfo) throws Exception{
		List<FreeBoard> bList = null;
		
		String query = prop.getProperty("selectBoardList");
		
		try {
			int startRow = (pInfo.getCurrentPage() -1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() -1 ;
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			bList = new ArrayList<FreeBoard>();
			
			while(rset.next()) {
				FreeBoard freeBoard = new FreeBoard(rset.getInt("FREE_BOARD_NO"),
										rset.getString("FREE_BOARD_TITLE"),
										rset.getString("MEM_ID"),
										rset.getInt("FREE_READ_COUNT"),
										rset.getTimestamp("FREE_BOARD_DATE"));
				bList.add(freeBoard);
				
			}
			
			
		}finally {
			close(rset);
			close(pstmt);
			
		}
		
		
		return bList;
	}



	/** 게시글 상세조회 DAO
	 * @param conn
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public FreeBoard selectBoard(Connection conn, int boardNo) throws Exception{
		FreeBoard board = null;
		
		String query = prop.getProperty("selectBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				board = new FreeBoard();
				board.setBoardNo( rset.getInt("FREE_BOARD_NO") );
				board.setBoardTitle( rset.getString("FREE_BOARD_TITLE") );
				board.setBoardContent( rset.getString("FREE_BOARD_CONTENT") );
				board.setMemberId( rset.getString("MEM_ID") );
				board.setReadCount( rset.getInt("FREE_READ_COUNT") );
				board.setBoardCreateDate( rset.getTimestamp("FREE_BOARD_DATE") );
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



	/** 다음 게시글 번호 DAO
	 * @param conn
	 * @return boardNo
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



	/** 게시글 삽입
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



	/** 파일 정보 삽입 DAO
	 * @param conn
	 * @param at
	 * @return result
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



	/** 이미지 상세조회 DAO
	 * @param conn
	 * @param boardNo
	 * @return fList
	 * @throws Exception
	 */
	public List<Attachment> selectBoardFiles(Connection conn, int boardNo) throws Exception{
		List<Attachment> fList = null;
		
		String query = prop.getProperty("selectBoardFiles");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
					
			fList = new ArrayList<Attachment>();
			
			while(rset.next()) {
				
				Attachment at = new Attachment(
								rset.getInt("FREE_FILE_NO"),
								rset.getString("FREE_FILE_NAME"),
								rset.getInt("FREE_FILE_LEVEL"));
				
				at.setFilePath(rset.getString("FREE_FILE_PATH"));
				
				fList.add(at);
			}
			
		}finally {
			close(rset);
			close(pstmt);
		}
		
		
		
		return fList;
	}



	/** 게시글 수정 DAO
	 * @param conn
	 * @param map
	 * @return result
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



	/** 파일 수정 DAO
	 * @param conn
	 * @param newFile
	 * @return result
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



	/** 게시글 삭제여부 변경  DAO
	 * @param conn
	 * @param boardNo
	 * @return result
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



	public int freeReport(Connection conn, FreeReport fReport) throws Exception{
		int result = 0;
	
		String query = prop.getProperty("freeReport");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, fReport.getReportTitle());
			pstmt.setString(2, fReport.getReportContent());
			pstmt.setInt(3, fReport.getFreeBoardNo());
			pstmt.setInt(4, fReport.getMemberNo());
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
			
		}
		
		return result;
	}



	/** 신고 수 증가
	 * @param fReport
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
