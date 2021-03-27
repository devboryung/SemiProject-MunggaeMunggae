package com.kh.semi.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.semi.member.model.service.MemberService;
import com.kh.semi.member.model.vo.Member;

@WebServlet("/member/myPageUpdateCompany.do")
public class UpdateCompanyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		
		String swalIcon = null;
		String swalTitle = null;
		String swalText = null;
		
		
		// 닉네임 이메일 전화번호 성별 순
		
		// 닉네임
		String nickName = request.getParameter("userName");
		
		// 이메일
		String email1 = request.getParameter("email1");
		String email2 = request.getParameter("email2");
		String memberEmail = email1 + "@" + email2;

		// 전화번호
		String phone1 = request.getParameter("phone1");
		String phone2 = request.getParameter("phone2");
		String phone3 = request.getParameter("phone3");
		String memberPhone = phone1 + "-" + phone2 + "-" + phone3; // 전화번호를 '-' 구분자로 하여 하나로 합치기

		// 성별
		String gender = request.getParameter("gender");

		// 업체 정보 추가 ----------------------------------------------------------------------------
		// 업체 전화번호
		String phone4 = request.getParameter("phone4");
		String phone5 = request.getParameter("phone5");
		String phone6 = request.getParameter("phone6");
		String comPhone = phone4 + "-" + phone5 + "-" + phone6; // 전화번호를 '-' 구분자로 하여 하나로 합치기
		
		// 업체주소
		String post = request.getParameter("post");
	    String address1 = request.getParameter("address1");
	    String address2 = request.getParameter("address2");
	    String comAddress = post + "," + address1 + "," + address2;
		
		
		// Session에 있는 로그인 정보를 얻어옴. -> 회원번호 사용
		HttpSession session = request.getSession();
		Member loginMember = (Member) session.getAttribute("loginMember");

		int memNo = loginMember.getMemberNo();
		// 얻어온 수정 정보와 회원 번호를 하나의 Member 객체에 저장
		Member member = new Member();
		member.setMemberNo(memNo);
		member.setMemberNickName(nickName);
		member.setEmail(memberEmail);
		member.setPhone(memberPhone);
		member.setGender(gender);
		
		member.setComPhone(comPhone);
		member.setComAddress(comAddress);
		
		try {
			// 비즈니스 로직 수행 후 결과 반환
			int result = new MemberService().updateMember(member);
			
			if(result > 0) {
				
				result = new MemberService().updateCompany(member);
				
				if(result > 0) {
					
					swalIcon = "success";
					swalTitle = "업체 정보 수정 성공 ᵔᴥᵔ ";
					swalText = "회원 정보가 수정되었습니다.";

					// DB 데이터가 갱신된 경우 Session에 있는 회원 정보도 갱신되어야 한다.

					// 기존 로그인 정보에서 id를 얻어와 갱신에 사용된 member 객체에 저장
					member.setMemberId(loginMember.getMemberId());
					member.setMemberAdmin(loginMember.getMemberAdmin());
					// -> member 객체가 갱신된 회원 정보를 모두 갖게됨

					// Session에 있는 loginMember 정보를 member로 갱신
					session.setAttribute("loginMember", member);
				
				}else {
					
					swalIcon = "error";
					swalTitle = "업체 정보 수정 실패 TᴥT";
					swalText = "고객 센터에 문의 바랍니다.";
					
				}
				
				swalIcon = "success";
				swalTitle = "업체 정보 수정 성공 ᵔᴥᵔ ";
				swalText = "회원 정보가 수정되었습니다.";
				
				
			}else {
				
				swalIcon = "error";
				swalTitle = "회원 정보 수정 실패 TᴥT";
				swalText = "고객 센터에 문의 바랍니다.";
				
			}
			
			session.setAttribute("swalIcon", swalIcon);
			session.setAttribute("swalTitle", swalTitle);
			session.setAttribute("swalText", swalText);

			// 수정 완료 후 다시 내 정보 페이지로 재요청
			response.sendRedirect(request.getHeader("referer"));

		} catch (Exception e) {
			e.printStackTrace();
			
			
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
