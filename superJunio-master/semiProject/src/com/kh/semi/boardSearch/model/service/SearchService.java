package com.kh.semi.boardSearch.model.service;

import static com.kh.semi.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.kh.semi.boardSearch.model.dao.SearchDAO;
import com.kh.semi.freeBoard.model.vo.FreeBoard;
import com.kh.semi.freeBoard.model.vo.PageInfo;

public class SearchService {
	private SearchDAO dao = new SearchDAO();
	
	
	/** 페이징 처리용 Service
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PageInfo getPageInfo(Map<String, Object> map) throws Exception{
		Connection conn = getConnection();
		
		map.put("currentPage",
				(map.get("currentPage") == null) ? 1 
						: Integer.parseInt((String)map.get("currentPage")));
		
		String condition = createCondition(map);
		
		int listCount = dao.getListCount(conn, condition);
		
		close(conn);
		
		// PageInfo 객체를 생성하여 반환
		return new PageInfo((int)map.get("currentPage"), listCount);
	
	}
	
	
	
	

	/** 검색 조건에 맞는 리스트 조회 Service
	 * @param map
	 * @param pInfo
	 * @return
	 * @throws Exception
	 */
	public List<FreeBoard> searchBoardList(Map<String, Object> map, PageInfo pInfo) throws Exception{
		Connection conn = getConnection();
		
		String condition = createCondition(map);
		
		List<FreeBoard> bList = dao.searchBoardList(conn, pInfo, condition);
		
		close(conn);
		
		return bList;
		
	}
	
	
	
	
	
	private String createCondition(Map<String, Object> map) {
		String condition = null;
		
		String searchKey = (String)map.get("searchKey");
		String searchValue = (String)map.get("searchValue");
		
		// 검색조건(searchKey)에 따라 SQL 조합
		switch(searchKey){
			case "title" :
				condition = " FREE_BOARD_TITLE LIKE '%' || '" + searchValue + "' || '%' ";
							// " BOARD_TITLE LIKE '%49%'
				break;
			case "content" :
				condition = " FREE_BOARD_CONTENT LIKE '%' || '" + searchValue + "' || '%' ";
				break;
				
			case "titcont" :
				condition = " (FREE_BOARD_TITLE LIKE '%' || '" + searchValue + "' || '%' "
							+ "OR FREE_BOARD_CONTENT LIKE '%' || '" + searchValue + "' || '%') ";
				break;
				
			case "writer" :
				condition = " MEM_ID LIKE '%' || '" + searchValue + "' || '%' ";
				break;
		}
		
		return condition;
	}

}
