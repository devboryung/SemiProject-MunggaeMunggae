package com.kh.semi.managerSearch.service;

import static com.kh.semi.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.kh.semi.managerSearch.dao.cooSearchDAO;
import com.kh.semi.member.model.vo.Member;
import com.kh.semi.mypage.vo.PageInfo;

public class cooSearchService {

	private cooSearchDAO dao = new cooSearchDAO();

	public PageInfo getPageInfo(Map<String, Object> map) throws Exception{
		Connection conn = getConnection();

		map.put("currentPage", (map.get("currentPage") == null) ? 1 : Integer.parseInt((String)map.get("currentPage")));
		
		// 검색 조건에 따른 SQL 조건물을 조합하는 메소드 호출
		String condition = createCondition(map);
		
		// DB에서 조건을 만족하는 게시글의 수를 조회하기
		int listCount = dao.getListCount(conn, condition);
		
		// 커넥션 반환
		close(conn);
		
		// PageInfo 객체를 생성하여 반환
		return new PageInfo((int)map.get("currentPage"), listCount);
		
	}

	private String createCondition(Map<String, Object> map) throws Exception{
		 String condition = null;
	      
	      String searchKey = (String)map.get("searchKey");
	      String searchValue = (String)map.get("searchValue");
	      
	      // 검색 조건(searchKey)에 따라 SQL 조합
	      switch(searchKey) {
	      case "id" :
	                        // " BOARD_TITLE LIKE '%' || '49' || '%' "
	         condition = " MEM_ID LIKE '%' || '" + searchValue + "' || '%' ";
	         break;
	         
	      case "nickNm" :
	         condition = " NICKNAME LIKE '%' || '" + searchValue + "' || '%' ";
	         break;

	      case "cooName" :
	    	  condition = " COO_NM LIKE '%' || '" + searchValue + "' || '%' ";
	    	  break;
	         
	      }
	      
	      
	      return condition;
		
		
	}

	public List<Member> cSearchList(Map<String, Object> map, PageInfo pInfo) throws Exception{
		Connection conn = getConnection();
		

		String condition = createCondition(map);
		
		List<Member> mList = dao.cSearchList(conn, pInfo, condition);
		
		close(conn);
		
		return mList;
	}
	
	
}
