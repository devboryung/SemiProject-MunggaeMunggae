package com.kh.semi.manager.model.service;

import static com.kh.semi.common.JDBCTemplate.*;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kh.semi.freeBoard.model.vo.Attachment;
import com.kh.semi.freeBoard.model.vo.FreeBoard;
import com.kh.semi.manager.model.dao.normalDAO;
import com.kh.semi.member.model.vo.Member;
import com.kh.semi.mypage.dao.postDAO;
import com.kh.semi.mypage.vo.PageInfo;
import com.kh.semi.mypage.vo.fBoard;

public class normalService {

	private normalDAO dao = new normalDAO();

	/**
	 * 페이징 처리를 위한 값 계산 Service
	 * 
	 * @param cp
	 * @param memberNo 
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getPageInfo(String cp) throws Exception {
		Connection conn = getConnection();

		// cp가 null일 경우
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);

		// DB에서 전체 게시글 수를 조회하여 반환 받기
		int listCount = dao.getListCount(conn);

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
	 * @return mList
	 * @throws Exception
	 */
	public List<Member> memberList(PageInfo pInfo) throws Exception {
		Connection conn = getConnection();

		List<Member> mList = dao.memberList(conn, pInfo);

		close(conn);

		return mList;
	}

}