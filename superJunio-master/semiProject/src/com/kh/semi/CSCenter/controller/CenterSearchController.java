package com.kh.semi.CSCenter.controller;

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

import com.kh.semi.CSCenter.model.service.CenterSearchService;
import com.kh.semi.CSCenter.model.vo.Faq;
import com.kh.semi.CSCenter.model.vo.Notice;
import com.kh.semi.CSCenter.model.vo.PageInfo;
import com.kh.semi.CSCenter.model.vo.Qna;

@WebServlet("/centerSearch/*")
public class CenterSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		String uri = request.getRequestURI();	
		
		String contextPath = request.getContextPath();
		
		String command = uri.substring((contextPath+"/centerSearch").length() );
		
		CenterSearchService service = new CenterSearchService();
		
			try {
				
				String searchKey = request.getParameter("sk");
				String searchValue = request.getParameter("sv");
				String cp = request.getParameter("cp");
				
				
				if(command.equals("/faq.do")) {
				
				
				
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("searchKey",searchKey);
				map.put("searchValue",searchValue);
				map.put("currentPage",cp);
				
				
				PageInfo pInfo = service.getPageInfo(map);
				
				List<Faq> bList = service.searchFaqList(map,pInfo);

				String path = "/WEB-INF/views/faq/FaqCenter.jsp";
				
				request.setAttribute("bList", bList);
				request.setAttribute("pInfo", pInfo);
				
				RequestDispatcher view = request.getRequestDispatcher(path);
				view.forward(request, response);
					
				}
				
				
			else if(command.equals("/qna.do")) {
	
					
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("searchKey",searchKey);
				map.put("searchValue",searchValue);
				map.put("currentPage",cp);
					
					
				PageInfo pInfo = service.getQnaPageInfo(map);

				List<Qna> bList = service.searchQnaList(map,pInfo);

				
				String path = "/WEB-INF/views/qna/QnaCenter.jsp";
				
				request.setAttribute("bList", bList);
				request.setAttribute("pInfo", pInfo);
				
				RequestDispatcher view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
				
				
				}
				
				
			else if(command.equals("/notice.do")) {
			
			Map<String,Object> map = new HashMap<String,Object>();

			map.put("searchKey",searchKey);
			map.put("searchValue",searchValue);
			map.put("currentPage",cp);
				
				
			PageInfo pInfo = service.getNoticePageInfo(map);

			List<Notice> bList = service.searchNoticeList(map,pInfo);

			
			String path = "/WEB-INF/views/notice/NoticeCenter.jsp";
			
			request.setAttribute("bList", bList);
			request.setAttribute("pInfo", pInfo);
			
			RequestDispatcher view = request.getRequestDispatcher(path);
			view.forward(request, response);
			
			
			
			
			
			
			
			}
			

				
				
				
			} catch(Exception e) {
			
				e.printStackTrace();
				String path = "/WEB-INF/views/common/errorPage.jsp";
				request.setAttribute("errorMsg","검색과정중에 오류발생");
				RequestDispatcher view = request.getRequestDispatcher(path);
				view.forward(request, response);
			
			}
			
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
