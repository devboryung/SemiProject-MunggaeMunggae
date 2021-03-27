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

@WebServlet("/member/withdrawal.do") // 절대 경로
public class WithdrawalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// 회원 탈퇴
		
				// 현재 비밀번호
				String currentPw = request.getParameter("currentPw");
				
				System.out.println(currentPw);
				
				// 세션에 있는 로그인 회원 정보
				HttpSession session = request.getSession();
				Member loginMember = (Member)session.getAttribute("loginMember");
				
				// loginMember 객체에 현재 비밀번호 세팅
				loginMember.setMemberPwd(currentPw);
				
				try {
					
					// 비즈니스 로직 수행 후 결과 반환 받기
					int result = new MemberService().withdrawal(loginMember);
					
					String swalIcon = null;
					String swalTitle = null;
					String url = null;
					
					if(result > 0) {
						swalIcon = "success";
						swalTitle = "탈퇴되었습니다.";
						
						// 탈퇴 성공 시 -> 로그아웃 + 메인 페이지로 전환
						
						// 로그아웃 == 세션 무효화
						session.invalidate();
						// 세션 무효화 시 현재 얻어온 세션을 사용할 수 없는 문제점이 있다!
						// -> 새로운 세션을 얻어와야한다.
						session = request.getSession();
						
						// 시작 주소
						url = request.getContextPath();
						
					} else if (result == 0) {
						swalIcon = "error";
						swalTitle = "회원 탈퇴에 실패했습니다.";
						
						// 탈퇴 실패 시 -> 탈퇴 페이지 유지
						url = "myPageWithdrawal.do";
					
						
					} else {
						swalIcon = "warning";
						swalTitle = "현재 비밀번호가 일치하지 않습니다.";
						
						// 변경 실패 시 -> 탈퇴 페이지 유지
						url = "myPageWithdrawal.do";
					}
					
					session.setAttribute("swalIcon", swalIcon);
					session.setAttribute("swalTitle", swalTitle);
					
					response.sendRedirect(url);
					
					
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("errorMsg", "회원 탈퇴 과정에서 오류가 발생하였습니다.");
					
					String path = "/WEB-INF/views/common/errorPage.jsp";
					
					request.setAttribute("errorMsg", "비밀번호 변경 과정에서 오류 발생");
					RequestDispatcher view = request.getRequestDispatcher(path);
					view.forward(request, response);
				}
			}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
