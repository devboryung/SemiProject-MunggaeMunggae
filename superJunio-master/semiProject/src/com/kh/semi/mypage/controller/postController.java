package com.kh.semi.mypage.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.semi.mypage.service.postService;
import com.kh.semi.mypage.vo.PageInfo;
import com.kh.semi.mypage.vo.fBoard;
import com.kh.semi.freeBoard.model.vo.Attachment;
import com.kh.semi.freeBoard.model.vo.FreeBoard;
import com.kh.semi.member.model.vo.Member;

@WebServlet("/member/myPageInquiryPost.do")
public class postController extends HttpServlet {
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
	    	postService service = new postService();
	    	String swalIcon = null;
	    	String swalTitle = null;
	    	String swalText = null;
	    	
	    	
	    	// 내가 쓴 글 (자유 게시판) 목록 조회 Controller
	    	
	    	// 페이징 service
	    	PageInfo pInfo = service.getPageInfo(cp, memberNo);
	    	
	    	// 게시글 목록 조회 비즈니스 로직 수행
	    	List<fBoard> fList = service.selectBoardList(pInfo, memberNo);
	    	
	    	if(fList != null) {
	    		
	    		request.setAttribute("fList", fList);
	    		request.setAttribute("pInfo", pInfo);
	    		
	    		path = "/WEB-INF/views/member/myPageInquiryPost.jsp";
	    		view = request.getRequestDispatcher(path);
	    		view.forward(request, response);
	    	}
	    	
	    	// 게시글 상세 조회 Controller ********************************************
	    	else if(command.equals("/view.do")) {
				errorMsg = "게시글 상세 조회 과정에서 오류 발생";
				
				int boardNo = Integer.parseInt(request.getParameter("no"));
				
				FreeBoard board = service.selectBoard(boardNo);
				
				if(board != null) {
					List<Attachment> fList1 = service.selectBoardFiles(boardNo);
					
					if(!fList1.isEmpty()) {	// 해당 게시글 이미지 정보가 DA에 있을 경우
						request.setAttribute("fList", fList1);
					}
					
					path = "/WEB-INF/views/freeBoard/view.do?cp=" + cp + "no=" + boardNo;
					request.setAttribute("board", board);
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
					
				}else {
					
					request.getSession().setAttribute("swalIcon", "error");
					request.getSession().setAttribute("swalTitle", "게시글 상세조회 실패");
					response.sendRedirect("freeList.do?cp=1");
				}
				
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
