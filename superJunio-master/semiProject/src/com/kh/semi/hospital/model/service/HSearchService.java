package com.kh.semi.hospital.model.service;

import static com.kh.semi.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.kh.semi.hospital.model.dao.HSearchDAO;
import com.kh.semi.hospital.model.vo.Attachment;
import com.kh.semi.hospital.model.vo.Hospital;
import com.kh.semi.hospital.model.vo.PageInfo;

public class HSearchService {

	private HSearchDAO dao = new HSearchDAO();

	/**
	 * 검색 내용이 포함된 페이징 처리 정보 생성 Service
	 * 
	 * @param map
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getPageInfo(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();

		// 얻어온 파라미터 cp가 null이면 1, 아니면 int형으로 파싱
		map.put("currentPage",
				(map.get("currentPage") == null)? 1 : Integer.parseInt((String) map.get("currentPage")));


		// 검색 조건에 따른 SQL 조건문을 조합하는 메소드 
		String condition = createCondition(map);

		// SQL 조건문을 map에 추가
		map.put("condition", condition);

		// DB에서 조건을 만족하는 게시글의 수를 조회하기
		int listCount = dao.getListCount(conn, condition);

		close(conn);

		return new PageInfo((int) map.get("currentPage"), listCount);
	}

	/**
	 * 검색 조건에 따라 sql 조합
	 * 
	 * @param map
	 * @return condition
	 */
	private String createCondition(Map<String, Object> map) {
		String condition = null;

		String searchKey = (String) map.get("searchKey");
		String searchValue = (String) map.get("searchValue");
		
		

		// 검색 조건에 따라 SQL 조합
		switch (searchKey) {
		case "hospitalName": condition = " HOSP_NM LIKE '%' || '" + searchValue + "' || '%' "; break; 
			 
		case "location":  condition = " (LOCATION1 LIKE '%' || '" + searchValue + "' || '%' "
										+ "OR LOCATION2 LIKE '%' || '" + searchValue + "' || '%') "; break;
		}
		
		
		return condition;
	}

	
	
	
	/** 검색 게시글 목록 리스트 조회 Service
	 * @param map
	 * @param pInfo
	 * @return hList
	 * @throws Exception
	 */
	public List<Hospital> searchHospitalList(Map<String, Object> map, PageInfo pInfo) throws Exception {
		Connection conn = getConnection();
		
		String condition = createCondition(map);
		
		List<Hospital> hList = dao.searchHospitalList(conn, condition, pInfo);
		
		close(conn);
		
		return hList;
	}

	
	
	
	/** 검색이 적용된 썸네일 목록 조회 Service
	 * @param map
	 * @param pInfo
	 * @return fList
	 * @throws Exception
	 */
	public List<Attachment> searchThumbnailList(Map<String, Object> map, PageInfo pInfo) throws Exception {
		Connection conn = getConnection();
		
		String condition = createCondition(map);
		
		List<Attachment> fList = dao.searchThumbnailList(conn,pInfo, condition);
		
		close(conn);
		return fList;
	}
	

	
		 
}
