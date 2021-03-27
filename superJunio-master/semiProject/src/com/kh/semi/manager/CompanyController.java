package com.kh.semi.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.semi.mypage.vo.PageInfo;
import com.kh.semi.manager.model.service.companyService;
import com.kh.semi.member.model.vo.Member;

@WebServlet("/manager/managerCompany.do")
public class CompanyController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = null;
		String errorMsg = null;
		Member loginMember = (Member)request.getSession().getAttribute("loginMember");
		int memberNo = loginMember.getMemberNo();
	      
		// 현재 페이지 얻어옴
		String cp = request.getParameter("cp");
		
		String uri = request.getRequestURI();

		String contextPath = request.getContextPath();

		String command = uri.substring((contextPath + "/member").length());

		// 컨트롤러 내에서 공용으로 사용할 변수 미리 선언
		RequestDispatcher view = null; // 요청 위임 객체
		
	    try {
	    	companyService service = new companyService();
	    	String swalIcon = null;
	    	String swalTitle = null;
	    	String swalText = null;
	    	
	    	
	    	// 내가 쓴 글 (자유 게시판) 목록 조회 Controller
	    	
	    	// 페이징 service
	    	PageInfo pInfo = service.getPageInfo(cp);
	    	
	    	// 멤버 목록 조회 비즈니스 로직 수행
	    	List<Member> cMember = service.memberList(pInfo);
	    	
	    	if(cMember != null) {
	    		request.setAttribute("cMember", cMember);
	    		request.setAttribute("pInfo", pInfo);
	    		
	    		path = "/WEB-INF/views/manager/managerCompany.jsp";
	    		view = request.getRequestDispatcher(path);
	    		view.forward(request, response);
	    	}
	    	
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	path = "/WEB-INF/views/common/errorPage.jsp";
	        request.setAttribute("errorMsg", errorMsg);
	        //view = request.getRequestDispatcher(path);
	        //view.forward(request, response);
	    	
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
