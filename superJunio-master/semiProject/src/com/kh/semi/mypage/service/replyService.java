package com.kh.semi.mypage.service;

import static com.kh.semi.common.JDBCTemplate.*;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kh.semi.mypage.dao.postDAO;
import com.kh.semi.mypage.vo.PageInfo;
import com.kh.semi.mypage.vo.rBoard;



public class replyService {

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
/*	public List<rBoard> selectBoardList(PageInfo pInfo, int memberNo) throws Exception {
		Connection conn = getConnection();

		List<rBoard> fList = dao.selectBoardList(conn, pInfo, memberNo);

		close(conn);

		return fList;
	}*/

}