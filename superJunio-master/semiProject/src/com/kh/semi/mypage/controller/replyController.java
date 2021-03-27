//package com.kh.semi.mypage.controller;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.kh.semi.mypage.service.postService;
//import com.kh.semi.mypage.vo.PageInfo;
//import com.kh.semi.mypage.vo.rBoard;
//import com.kh.semi.member.model.vo.Member;
//
//@WebServlet("/member/myPageInquiryReply.do")
//public class replyController extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
///*	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		String path = null;
//		String errorMsg = null;
//		Member loginMember = (Member)request.getSession().getAttribute("loginMember");
//		int memberNo = loginMember.getMemberNo();
//	      
//		// 현재 페이지 얻어옴
//		String cp = request.getParameter("cp");
//		
//	    try {
//	    	postService service = new postService();
//	    	String swalIcon = null;
//	    	String swalTitle = null;
//	    	String swalText = null;
//	    	
//	    	
//	    	// 내가 쓴 글 (자유 게시판) 목록 조회 Controller
//	    	
//	    	// 페이징 service
//	    	PageInfo pInfo = service.getPageInfo(cp, memberNo);
//	    	
//	    	// 게시글 목록 조회 비즈니스 로직 수행
//	    	List<rBoard> rList = service.selectBoardList(pInfo, memberNo);
//	    	
//	    	if(rList != null) {
//	    		
//	    		request.setAttribute("rList", rList);
//	    		request.setAttribute("pInfo", pInfo);
//	    		
//	    		path = "/WEB-INF/views/member/myPageInquiryReply.jsp";
//	    		RequestDispatcher view = request.getRequestDispatcher(path);
//	    		view.forward(request, response);
//	    	}
//	    	
//	    } catch (Exception e) {
//	    	e.printStackTrace();
//	    	path = "/WEB-INF/views/common/errorPage.jsp";
//	        request.setAttribute("errorMsg", errorMsg);
//	        //view = request.getRequestDispatcher(path);
//	        //view.forward(request, response);
//	    	
//	    }
//	}*/
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doGet(request, response);
//	}
//
//}
