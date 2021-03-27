package com.kh.semi.manager.model.service;

import static com.kh.semi.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.kh.semi.manager.model.dao.reportDAO;
import com.kh.semi.manager.vo.MemberReport;
import com.kh.semi.mypage.vo.PageInfo;

public class memberReprotService {
	
	private reportDAO dao = new reportDAO();

	public PageInfo getPageInfo(String cp) throws Exception{
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

	public List<MemberReport> selectReport(PageInfo pInfo) throws Exception{
		Connection conn = getConnection();
		
		List<MemberReport> report = dao.selectReport(conn, pInfo);
		close(conn);
		
		return report;
	}

}
