package com.kh.semi.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.semi.manager.model.service.memberReprotService;
import com.kh.semi.manager.vo.MemberReport;
import com.kh.semi.mypage.vo.PageInfo;


@WebServlet("/manager/managerReport.do")
public class ManagerReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = null;
		String errorMsg = null;
		
		String cp = request.getParameter("cp");
		
		String uri = request.getRequestURI();

		String contextPath = request.getContextPath();
		RequestDispatcher view = null; // 요청 위임 객체

	
		try {
			memberReprotService service = new memberReprotService();
			
			String swalIcon = null;
	    	String swalTitle = null;
	    	String swalText = null;
	    	
	    	PageInfo pInfo = service.getPageInfo(cp);
	    	
	    	List<MemberReport> report = service.selectReport(pInfo); 
			
	    	
	    	if(report != null) {
	    		
	    		request.setAttribute("report", report);
	    		request.setAttribute("pInfo", pInfo);
	    		
	    		path = "/WEB-INF/views/manager/managerReport.jsp";
	    		view = request.getRequestDispatcher(path);
	    		view.forward(request, response);
	    		
	    	}
			
			
			
			
		}catch (Exception e){
			e.printStackTrace();
	    	path = "/WEB-INF/views/common/errorPage.jsp";
	        request.setAttribute("errorMsg", errorMsg);
			
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
