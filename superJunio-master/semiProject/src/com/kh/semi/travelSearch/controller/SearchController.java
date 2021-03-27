package com.kh.semi.travelSearch.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.semi.travel.model.vo.PageInfo;
import com.kh.semi.travel.model.vo.Travel;
import com.kh.semi.travel.model.vo.TravelAttachment;
import com.kh.semi.travelSearch.model.service.SearchService;


@WebServlet("/travelSearch.do")
public class SearchController extends HttpServlet {
   private static final long serialVersionUID = 1L;
   
   
   
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String searchKey = request.getParameter("searchKey");
      String searchValue = request.getParameter("searchValue");
      String cp = request.getParameter("cp");
      
      try {
         SearchService service = new SearchService();
         
         Map<String, Object> map = new HashMap<String, Object>();
         map.put("searchKey", searchKey);
         map.put("searchValue", searchValue);
         map.put("currentPage", cp);
         
         // 페이징 처리를 위한 데이터를 계산하고 저장하는 객체 PageInfo 얻어오기
         PageInfo pInfo = service.getTpageInfo(map);
         
         // 검색 게시글 목록 조회
         List<Travel> tList = service.searchTravelList(map, pInfo);
         
         
         // 결과 확인
         /* System.out.println(pInfo);
         for(Board b : bList) {
        	 System.out.println(b);
         } */ 
         
         // 검색 게시글 목록 조회 성공 시 썸네일 목록 조회 수행
         if(tList != null) {
        	 List<TravelAttachment> fList = service.searchThumbnailList(map, pInfo);
        	 
        	 if(!fList.isEmpty()) { // 조회된 썸네일 목록이 있다면
        		 request.setAttribute("fList", fList);
        	 }
         }
         
         
         
         
         
         // 조회된 내용과 PageInfo 객체를 request객체에 담아서 요청 위임
         String path = "/WEB-INF/views/travel/localInfo/localList.jsp";
         
         request.setAttribute("tList", tList);
         request.setAttribute("pInfo", pInfo);
         
         RequestDispatcher view = request.getRequestDispatcher(path);
         view.forward(request, response);
         
         
      }catch (Exception e) {
         e.printStackTrace();
         String path = "/WEB-INF/views/common/errorPage.jsp";
         request.setAttribute("errorMsg", "검색 과정에서 오류 발생");
         RequestDispatcher view = request.getRequestDispatcher(path);
         view.forward(request, response);
      }
      
   
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}