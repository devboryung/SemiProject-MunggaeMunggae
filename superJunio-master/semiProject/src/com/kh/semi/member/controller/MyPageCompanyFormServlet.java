package com.kh.semi.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.semi.member.model.service.MemberService;
import com.kh.semi.member.model.vo.Member;

@WebServlet("/member/myPageCompany.do")

public class MyPageCompanyFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String swalIcon = null;
		String swalTitle = null;
		String swalText = null;
		
		// Session에 있는 로그인 정보를 얻어옴. -> 회원번호 사용
		HttpSession session = request.getSession();
		Member loginMember = (Member) session.getAttribute("loginMember");
		int memNo = loginMember.getMemberNo();
		
		//System.out.println(loginMember);
		try {
			Member comMember = new MemberService().selectComMember(memNo);
			
			//System.out.println(comMember);
			if(comMember != null) {
				session.setMaxInactiveInterval(60 * 30);
				session.setAttribute("comMember", comMember);
			}
			
		}catch(Exception e) {
			swalIcon = "error";
			swalTitle = "회원 정보 수정 실패 TᴥT";
			swalText = "고객 센터에 문의 바랍니다.";
		}
		
		String path = "/WEB-INF/views/member/myPageCompany.jsp";
		RequestDispatcher view = request.getRequestDispatcher(path);
		view.forward(request, response);
		
	
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
