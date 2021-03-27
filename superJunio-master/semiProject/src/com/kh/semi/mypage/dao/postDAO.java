package com.kh.semi.mypage.dao;

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

import com.kh.semi.mypage.vo.fBoard;
import com.kh.semi.freeBoard.model.vo.Attachment;
import com.kh.semi.freeBoard.model.vo.FreeBoard;
import com.kh.semi.mypage.vo.PageInfo;

public class postDAO {
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;

	private Properties prop = null;

	public postDAO() {
		String fileName = postDAO.class.getResource("/com/kh/semi/sql/mypage/post-query.xml").getPath();
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 전체 게시글 수 반환 DAO
	 * 
	 * @param conn
	 * @param memberNo 
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn, int memberNo) throws Exception {
		int listCount = 0;

		String query = prop.getProperty("getListCount");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			
			rset = pstmt.executeQuery();

			if (rset.next()) {
				listCount = rset.getInt(1);
			}

		} finally {
			close(pstmt);
			close(rset);
		}
		return listCount;
	}

	/**
	 * 게시글 목록 조회 DAO
	 * 
	 * @param conn
	 * @param pInfo
	 * @param memberNo 
	 * @return bList
	 * @throws Exception
	 */
	public List<fBoard> selectBoardList(Connection conn, PageInfo pInfo, int memberNo) throws Exception {
		List<fBoard> fList = null;

		String query = prop.getProperty("selectBoardList");

		try {
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, memberNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);

			rset = pstmt.executeQuery();

			fList = new ArrayList<fBoard>();

			while (rset.next()) {
				fBoard fBoard = new fBoard(rset.getInt("FREE_BOARD_NO"), 
						rset.getString("FREE_BOARD_TITLE"), 
						rset.getTimestamp("FREE_BOARD_DATE"), 
						rset.getInt("FREE_READ_COUNT"));
				
				fList.add(fBoard);
			}

		} finally {
			close(rset);
			close(pstmt);
		}
		return fList;
	}

	
	/**
	 * 다음 게시글 번호 조회 DAO
	 * 
	 * @param conn
	 * @return boardNo
	 * @throws Exception
	 */
	public int selectNextNo(Connection conn) throws Exception {
		int fBoardNo = 0;

		String query = prop.getProperty("selectNextNo");

		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);

			if (rset.next()) {
				fBoardNo = rset.getInt(1);
			}

		} finally {
			close(rset);
			close(stmt);
		}

		return fBoardNo;
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
}