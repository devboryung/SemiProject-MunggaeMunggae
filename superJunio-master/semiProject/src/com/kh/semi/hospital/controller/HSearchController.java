package com.kh.semi.hospital.controller;

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

import com.kh.semi.hospital.model.service.HSearchService;
import com.kh.semi.hospital.model.vo.Attachment;
import com.kh.semi.hospital.model.vo.Hospital;
import com.kh.semi.hospital.model.vo.PageInfo;


@WebServlet("/hospital/search")
public class HSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String searchKey = request.getParameter("sk");
		String searchValue = request.getParameter("sv");
		String cp = request.getParameter("cp");
		
		
		try {
			HSearchService service = new HSearchService();
			
			Map<String,Object> map  = new HashMap<String,Object>();
			map.put("searchKey", searchKey);
			map.put("searchValue", searchValue);
			map.put("currentPage", cp);
			
			// 페이징 처리를 위한 데이터를 계산하고 저장하는객체 PageInfo 얻어오기
			PageInfo pInfo = service.getPageInfo(map);
			
			// 검색된 동물병원 목록 조회
			List<Hospital> hList = service.searchHospitalList(map,pInfo);
			
			
			// 동물병원 목록 조회 성공 시 썸네일 목록 조회 수행
			if(hList!=null) {
				List<Attachment> fList = service.searchThumbnailList(map,pInfo);
				
				if(!fList.isEmpty()) {
					request.setAttribute("fList", fList);
				}
			}
			
			
			// 조회된 내용과 PageInfo 객체를 request객체에 담아서 요청 위임
			String path ="/WEB-INF/views/hospital/hospitalList.jsp";
			
			request.setAttribute("hList", hList);
			request.setAttribute("pInfo", pInfo);
			
			RequestDispatcher view = request.getRequestDispatcher(path);
			view.forward(request, response);
			
		}catch(Exception e) {
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
