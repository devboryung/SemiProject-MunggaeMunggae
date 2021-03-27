package com.kh.semi.travelSearch.model.service;

import static com.kh.semi.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.kh.semi.travel.model.vo.PageInfo;
import com.kh.semi.travel.model.vo.Travel;
import com.kh.semi.travel.model.vo.TravelAttachment;
import com.kh.semi.travelSearch.model.dao.SearchDAO;

public class SearchService {
   private SearchDAO dao = new SearchDAO();

   /** 검색 내용이 포함된 페이징 처리 정보 생성 Service
    * @param map
    * @return map
    * @throws Exception
    */
   public PageInfo getTpageInfo(Map<String, Object> map) throws Exception {
      Connection conn = getConnection();
      
      // 얻어온 파라미터 cp가 null이면 1, 아니면 int 형으로 파싱
      map.put("currentPage",  
            (map.get("currentPage") == null) ? 1 
                     : Integer.parseInt((String)map.get("currentPage")));
      
      // 검색 조건에 따른 SQL 조건문을 조합하는 메소드 호출
      String condition = createCondition(map);
      
      // DB에서 조건을 만족하는 게시글의 수를 조회하기
      int listCount = dao.getListCount(conn, condition);
      
      // 커넥션 반환
      close(conn);
      
      // PageInfo 객체를 생성하여 반환
      return new PageInfo((int)map.get("currentPage"), listCount);
   }

   /** 검색 조건에 따라 SQL에 사용될 조건문을 조합하는 메소드
    * @param map
    * @return 
    */
   private String createCondition(Map<String, Object> map) {
      
      String condition = null;
      
      String searchKey = (String)map.get("searchKey");
      String searchValue = (String)map.get("searchValue");
      
      System.out.println(searchKey);
      System.out.println(searchValue);
      
      // 검색 조건(searchKey)에 따라 SQL 조합
      switch(searchKey) {
      case "title" :
                        // " BOARD_TITLE LIKE '%' || '49' || '%' "
         condition = " TRAVEL_TITLE LIKE '%' || '" + searchValue + "' || '%' ";
         break;
         
      case "content" :
         condition = " TRAVEL_CONTENT LIKE '%' || '" + searchValue + "' || '%' ";
         break;
         
      case "location" :
    	  condition = " TRAVEL_LOCATION LIKE '%' || '" + searchValue + "' || '%' ";
    	  break;
         
      }
      return condition;
   }

   /** 검색 게시글 목록 리스트 조회 Service
    * @param map
    * @param pInfo 
    * @return bList
    * @throws Exception
    */
   public List<Travel> searchTravelList(Map<String, Object> map, PageInfo pInfo) throws Exception {
      Connection conn = getConnection();
      
      String condition = createCondition(map);
      
      List<Travel> bList = dao.searchTravelList(conn, pInfo, condition);
      
      close(conn);
      
      System.out.println(bList);
      
      return bList;
   }

	/** 검색이 적용된 썸네일 목록 조회 Service 
	 * @param map
	 * @param pInfo
	 * @return fList 
	 * @throws Exception
	 */
	public List<TravelAttachment> searchThumbnailList(Map<String, Object> map, PageInfo pInfo) throws Exception {
		Connection conn = getConnection();
		
		// 검색에 사용될 SQL 조건문 생성
		String condition = createCondition(map);
		
		List<TravelAttachment> fList = dao.searchThumbnailList(conn, pInfo, condition);
		
		close(conn);
		
		return fList;
	}


}