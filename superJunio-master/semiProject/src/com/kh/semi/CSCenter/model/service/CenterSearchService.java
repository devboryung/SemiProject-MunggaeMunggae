package com.kh.semi.CSCenter.model.service;
import static com.kh.semi.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.kh.semi.CSCenter.model.dao.CenterSearchDAO;
import com.kh.semi.CSCenter.model.vo.Faq;
import com.kh.semi.CSCenter.model.vo.Notice;
import com.kh.semi.CSCenter.model.vo.PageInfo;
import com.kh.semi.CSCenter.model.vo.Qna;

public class CenterSearchService {

	private CenterSearchDAO dao = new CenterSearchDAO();

	/** 페이징 처리를 위한 값 계산 service
	 * @param map
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getPageInfo(Map<String, Object> map) throws Exception {

		Connection conn = getConnection();
		
		// 얻어온 파라미터 cp가 null이면 1 , 아니면 int형으로 파싱
		map.put("currentPage", (map.get("currentPage") == null) ? 1 
				: Integer.parseInt((String)map.get("currentPage")));
		
		
		// 검색 조건에 따른 SQL 조건물을 조합하는 메소드 호출
		String condition = createCondition(map);
		
		// DB에서 조건을 만족하는 게시글의 수를 조회하기
		int listCount = dao.getListCount(conn,condition);

		close(conn);
		
		return new PageInfo((int)map.get("currentPage"),listCount);
	
	
	}
	
	
	
	private String createCondition(Map<String, Object> map) {
		
		String condition = null;
		
		String searchKey = (String)map.get("searchKey");
		String searchValue = (String)map.get("searchValue");
		
		// 검색 조건(searchkey)에 따라 SQL 조합
		switch(searchKey) {
		
		case "faqTitle" :
			condition =" FAQ_TITLE LIKE '%' || '" +searchValue+ "' || '%' "; 
						//"BOARD_TITLE LIKE '%' || '49' || '%'
			break;
		case "faqContent" :
			condition =" FAQ_CONTENT LIKE '%' || '" +searchValue+ "' || '%' "; 
			break;
		
		case "faqTitCont" :
			condition ="(FAQ_TITLE LIKE '%' || '" +searchValue+ "' || '%' "
						+ "OR FAQ_CONTENT LIKE '%' || ' " +searchValue + "' || '%')" ; 
			break;
			
		case "faqWriter" :
			condition =" MEM_ID LIKE '%' || '" +searchValue+ "' || '%' "; 
			break;
		
		}
		
		return condition;
	}
	
	
	
	
	private String createQnaCondition(Map<String, Object> map) {
		
		String condition = null;
		
		String searchKey = (String)map.get("searchKey");
		String searchValue = (String)map.get("searchValue");
		
		// 검색 조건(searchkey)에 따라 SQL 조합
		switch(searchKey) {
		
		case "qnaTitle" :
			condition =" QNA_TITLE LIKE '%' || '" +searchValue+ "' || '%' "; 
						//"BOARD_TITLE LIKE '%' || '49' || '%'
			break;
		case "qnaContent" :
			condition =" QNA_CONTENT LIKE '%' || '" +searchValue+ "' || '%' "; 
			break;
		
		case "qnaTitCont" :
			condition ="(QNA_TITLE LIKE '%' || '" +searchValue+ "' || '%' "
						+ "OR FAQ_CONTENT LIKE '%' || ' " +searchValue + "' || '%')" ; 
			break;
			
		case "qnaWriter" :
			condition =" NICKNAME LIKE '%' || '" +searchValue+ "' || '%' "; 
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
	public List<Faq> searchFaqList(Map<String, Object> map, PageInfo pInfo) throws Exception {

		Connection conn = getConnection();

		String condition = createCondition(map);

		List<Faq> bList = dao.searchFaqList(conn,pInfo,condition);

		close(conn);

		return bList;
	}



	/** Qna 검색 게시글 목록 조회 Service
	 * @param map
	 * @param pInfo
	 * @return bList
	 * @throws Exception
	 */
	public List<Qna> searchQnaList(Map<String, Object> map, PageInfo pInfo) throws Exception {
		
		Connection conn = getConnection();

		String condition = createQnaCondition(map);

		List<Qna> bList = dao.searchQnaList(conn,pInfo,condition);

		close(conn);

		
		return bList;
	}



	/** Qna 검색 내용이 포함된 페이징 처리 정보 생성 Service
	 * @param map
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getQnaPageInfo(Map<String, Object> map) throws Exception {
		
		Connection conn = getConnection();

		map.put("currentPage", (map.get("currentPage") == null) ? 1 
				: Integer.parseInt((String)map.get("currentPage")));
		
		
		String condition = createQnaCondition(map);

		int listCount = dao.getQnaListCount(conn,condition);
		
		
		close(conn);

		
		return new PageInfo((int)map.get("currentPage"),listCount);
	}



	/** Notice 검색 게시글 목록 리스트 조회 Service
	 * @param map
	 * @param pInfo
	 * @return bList
	 * @throws Exception
	 */
	public List<Notice> searchNoticeList(Map<String, Object> map, PageInfo pInfo) throws Exception {
		
		Connection conn = getConnection();

		String condition = createNoticeCondition(map);

		List<Notice> bList = dao.searchNoticeList(conn,pInfo,condition);

		
		close(conn);

		
		return bList;
	}

	
	
	
	private String createNoticeCondition(Map<String, Object> map) {
		
		String condition = null;
		
		String searchKey = (String)map.get("searchKey");
		String searchValue = (String)map.get("searchValue");
		
		// 검색 조건(searchkey)에 따라 SQL 조합
		switch(searchKey) {
		
		case "noticeTitle" :
			condition =" NOTICE_TITLE LIKE '%' || '" +searchValue+ "' || '%' "; 
						//"BOARD_TITLE LIKE '%' || '49' || '%'
			break;
		case "noticeContent" :
			condition =" NOTICE_CONTENT LIKE '%' || '" +searchValue+ "' || '%' "; 
			break;
		
		case "noticeTitCont" :
			condition ="(NOTICE_TITLE LIKE '%' || '" +searchValue+ "' || '%' "
						+ "OR NOTICE_CONTENT LIKE '%' || ' " +searchValue + "' || '%')" ; 
			break;
			
		case "noticeWriter" :
			condition =" NICKNAME LIKE '%' || '" +searchValue+ "' || '%' "; 
			break;
		
		}
		
		return condition;
	}



	/**  검색 내용이 포함 된 페이징 처리 정보 생성
	 * @param map
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getNoticePageInfo(Map<String, Object> map) throws Exception {
		
		
		Connection conn = getConnection();

		map.put("currentPage", (map.get("currentPage") == null) ? 1 
				: Integer.parseInt((String)map.get("currentPage")));
		
		String condition = createNoticeCondition(map);

		int listCount = dao.getNoticeListCount(conn,condition);

		
		close(conn);

		
		return new PageInfo((int)map.get("currentPage"),listCount);
	}
	
	
	

}
