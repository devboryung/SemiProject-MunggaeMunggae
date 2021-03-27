package com.kh.semi.mypage.service;

import static com.kh.semi.common.JDBCTemplate.*;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kh.semi.freeBoard.model.vo.Attachment;
import com.kh.semi.freeBoard.model.vo.FreeBoard;
import com.kh.semi.mypage.dao.postDAO;
import com.kh.semi.mypage.vo.PageInfo;
import com.kh.semi.mypage.vo.fBoard;



public class postService {

	private postDAO dao = new postDAO();

	/**
	 * 페이징 처리를 위한 값 계산 Service
	 * 
	 * @param cp
	 * @param memberNo 
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getPageInfo(String cp, int memberNo) throws Exception {
		Connection conn = getConnection();

		// cp가 null일 경우
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);

		// DB에서 전체 게시글 수를 조회하여 반환 받기
		int listCount = dao.getListCount(conn, memberNo);

		close(conn);

		// 얻어온 현재 페이지와, DB에서 조회한 전체 게시글 수를 이용하여
		// PageInfo 객체를 생성함
		return new PageInfo(currentPage, listCount);
	}

	/**
	 * 게시글 목록 조회 Service
	 * 
	 * @param pInfo
	 * @param memberNo 
	 * @return fList
	 * @throws Exception
	 */
	public List<fBoard> selectBoardList(PageInfo pInfo, int memberNo) throws Exception {
		Connection conn = getConnection();

		List<fBoard> fList = dao.selectBoardList(conn, pInfo, memberNo);

		close(conn);

		return fList;
	}
	
	
	/** 게시글 상세조회 Service
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public FreeBoard selectBoard(int boardNo) throws Exception{
		Connection conn = getConnection();
		
		FreeBoard board = dao.selectBoard(conn, boardNo);
		
			if(board != null) {
			
			// 조회수 증가
			int result = dao.increaseReadCount(conn, boardNo);
			
				if(result > 0) {		
					commit(conn);
					
					board.setReadCount(board.getReadCount() + 1);
					
				}
				else {
					rollback(conn);
				}
		}
		
		close(conn);
		
		
		return board;
	}
	
	
	/** 상세조회페이지 이미지 보여주는 Service
	 * @param boardNo
	 * @return fList
	 * @throws Exception
	 */
	public List<Attachment> selectBoardFiles(int boardNo) throws Exception{
		Connection conn = getConnection();
		
		List<Attachment> fList = dao.selectBoardFiles(conn, boardNo);
		
		close(conn);
		
		
		return fList;
	}

}