package com.kh.semi.member.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.semi.common.MyFileRenamePolicy;
import com.kh.semi.freeBoard.model.vo.Attachment;
import com.kh.semi.freeBoard.model.vo.PageInfo;
import com.kh.semi.member.model.service.MemberService;
import com.kh.semi.member.model.vo.Member;
import com.kh.semi.reply.model.vo.Reply;
import com.kh.semi.wrapper.EncryptWrapper;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/member/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uri = request.getRequestURI();

		String contextPath = request.getContextPath();

		String command = uri.substring((contextPath + "/member").length());

		// 컨트롤러 내에서 공용으로 사용할 변수 미리 선언
		String path = null; // forward 또는 redirect 경로를 저장할 변수
		RequestDispatcher view = null; // 요청 위임 객체

		String swalIcon = null;
		String swalTitle = null;
		String swalText = null;

		String errorMsg = null; // 에러 메세지 전달용 변수
		
		

		try {

			MemberService service = new MemberService();

			// ------- 일반회원가입 or 업체회원가입 선택해주는 jsp로 위임 ------
			if (command.equals("/signUpForm.do")) {
				errorMsg = "회원가입 선택 과정에서 문제 발생";

				path = "/WEB-INF/views/member/chooseSignUp.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);

			}

			// ------- 일반 회원가입Form jsp로 위임
			else if (command.equals("/nomalSignUpForm.do")) {
				errorMsg = "회원가입 과정에서 문제 발생";

				path = "/WEB-INF/views/member/nomalSignUpForm.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}

			// ------- 업체 회원가입Form jsp로 위임
			else if (command.equals("/companySignUpForm.do")) {
				errorMsg = "회원가입 과정에서 문제 발생";

				path = "/WEB-INF/views/member/companySignUpForm.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}

			// --------- 회원가입 처리 servlcet
			else if (command.equals("/signUp.do")) {
				errorMsg = "회원가입 과정에서 문제 발생";

				String memberId = request.getParameter("userId");
				String memberPwd = request.getParameter("pwd1");
				String memberNickName = request.getParameter("userName");

				String e1 = request.getParameter("email1");
				String e2 = request.getParameter("email2");
				String email = e1 + "@" + e2;

				String phone1 = request.getParameter("phone1");
				String phone2 = request.getParameter("phone2");
				String phone3 = request.getParameter("phone3");

				String phone = phone1 + "-" + phone2 + "-" + phone3;

				String gender = request.getParameter("gender");

				Member member = new Member(memberId, memberPwd, memberNickName, email, phone, gender);

				int result = service.signUp(member);

				if (result > 0) {
					swalIcon = "success";
					swalTitle = "회원가입 성공";
					swalText = memberNickName + "님의 회원가입을 환영합니다.";
				} else {
					swalIcon = "error";
					swalTitle = "회원가입 실패";
					swalText = "문제가 지속될 경우 고객센터로 문의 바랍니다.";
				}

				HttpSession session = request.getSession();

				session.setAttribute("swalIcon", swalIcon);
				session.setAttribute("swalTitle", swalTitle);
				session.setAttribute("swalText", swalText);

				// 회원가입 진행 후 메인 페이지로 이동 (메인 화면 재요청)
				response.sendRedirect(request.getContextPath());

			}
			
			// ------------------ 일반 회원가입용 메일 발송 Controller -------------------------
			else if(command.equals("/normalSignUpMail")) {
				final String user   = "jihoprac@gmail.com";
				  final String password  = "qkr3158!";

				  String to = request.getParameter("mail");
				  String mTitle = "[뭉개뭉개] 회원가입 인증.";
				 
				  try {
				  Map<String, Object> map = new HashMap<>();
				  Random random = new Random();
				  String key = "";
				  
				  for (int i = 0; i < 3; i++) {
						int index = random.nextInt(25) + 65; // A~Z까지 랜덤 알파벳 생성
						key += (char) index;
					}
					int numIndex = random.nextInt(8999) + 1000; // 4자리 정수를 생성
					key += numIndex;
				  
				  
				  // Get the session object
				  Properties props = new Properties();
				  props.put("mail.smtp.host", "smtp.gmail.com");
				  props.put("mail.smtp.port", 465);
				  props.put("mail.smtp.auth", "true");
				  props.put("mail.smtp.ssl.enable", "true");
				  props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

				  Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				   protected PasswordAuthentication getPasswordAuthentication() {
				    return new PasswordAuthentication(user, password);
				   }
				  });

				  // Compose the message
				  
				   MimeMessage message = new MimeMessage(session);
				   message.setFrom(new InternetAddress(user));
				   message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

				   // Subject
				   message.setSubject(mTitle);
				   
				   // Text
				   message.setText("뭉개뭉개에서 보내드리는 회원 가입용 인증 번호 : " + key);

				   // send the message
				   Transport.send(message);
				   
				   map.put("key", key);
				   response.getWriter().print(key);
				  }catch(Exception e) {
					  e.printStackTrace();
					  
					  
				  }
				
			}
			
			
			
			
			

			// ------- 아이디 중복검사 ajax --------------
			else if (command.equals("/idDupCheck.do")) {
				String userId = request.getParameter("userId");

				int result = service.idDupCheck(userId);

				response.getWriter().print(result);

			}

			// -------- 로그인 jsp로 위임
			else if (command.equals("/loginForm.do")) {
				path = "/WEB-INF/views/member/loginForm.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}

			// -------- 로그인용 servlet---------------------------------
			else if (command.equals("/login.do")) {
				String memberId = request.getParameter("userId");
				String memberPwd = request.getParameter("userPw");
				String save = request.getParameter("saveId");

				Member member = new Member();
				member.setMemberId(memberId);
				member.setMemberPwd(memberPwd);

				Member loginMember = service.loginMember(member);

				HttpSession session = request.getSession();

				if (loginMember != null) {

					session.setMaxInactiveInterval(60 * 30);

					session.setAttribute("loginMember", loginMember);

					Cookie cookie = new Cookie("saveId", memberId);

					if (save != null) {
						cookie.setMaxAge(60 * 60 * 24 * 7);
					} else {
						cookie.setMaxAge(0);
					}
					
					int memNo = loginMember.getMemberNo();
					Member comMember = service.selectComMember(memNo);
					
					if(comMember != null) {
						session.setMaxInactiveInterval(60 * 30);
						session.setAttribute("comMember", comMember);
					}
					

					cookie.setPath(request.getContextPath());

					response.addCookie(cookie);
					response.sendRedirect(request.getContextPath());

				} else {
					session.setAttribute("swalIcon", "error");
					session.setAttribute("swalTitle", "로그인 실패");
					session.setAttribute("swalText", "아이디 또는 비밀번호를 확인해주세요.");
					
					response.sendRedirect(request.getHeader("referer"));
				}


			}
			// -------------------로그아웃 servlet--------------------------
			else if (command.equals("/logout.do")) {
				request.getSession().invalidate();

				response.sendRedirect(request.getContextPath());
			}

			// ------------------ 업체 로그인 요청 위임 Servlet --------------
			else if (command.equals("/companySignUp.do")) {

				path = "/WEB-INF/views/member/companySignUpForm.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}

			// ----------------- id, pw 찾기 ------------------------------
			else if (command.equals("/findIdForm.do")) {

				path = "/WEB-INF/views/member/findIdForm.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);

			}
			
			
			// ------------------ id 찾기 인증번호 발송 Controller -----------------------
			else if(command.equals("/CheckMail")) {
				
				final String user   = "jihoprac@gmail.com";
				  final String password  = "qkr3158!";
				  String to = request.getParameter("mail");
				  String mTitle = "[뭉개뭉개] 아이디 찾기 인증.";
				 
				  try {
				  Map<String, Object> map = new HashMap<>();
				  Random random = new Random();
				  String key = "";
				  
				  for (int i = 0; i < 3; i++) {
						int index = random.nextInt(25) + 65; // A~Z까지 랜덤 알파벳 생성
						key += (char) index;
					}
					int numIndex = random.nextInt(8999) + 1000; // 4자리 정수를 생성
					key += numIndex;
				  
				  
				  // Get the session object
				  Properties props = new Properties();
				  props.put("mail.smtp.host", "smtp.gmail.com");
				  props.put("mail.smtp.port", 465);
				  props.put("mail.smtp.auth", "true");
				  props.put("mail.smtp.ssl.enable", "true");
				  props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

				  Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				   protected PasswordAuthentication getPasswordAuthentication() {
				    return new PasswordAuthentication(user, password);
				   }
				  });

				  // Compose the message
				  
				   MimeMessage message = new MimeMessage(session);
				   message.setFrom(new InternetAddress(user));
				   message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

				   // Subject
				   message.setSubject(mTitle);
				   
				   // Text
				   message.setText("뭉개뭉개에서 보내드리는 아이디 찾기용 인증 번호 : " + key);

				   // send the message
				   Transport.send(message);
				   
				   map.put("key", key);
				   response.getWriter().print(key);
				  }catch(Exception e) {
					  e.printStackTrace();
					  
					  
				  }
			}
			
			// ------------- 아이디 찾기 결과, Form 전환 Controller ------------------
			else if(command.equals("/findIdResultForm.do")) {
				errorMsg = "아이디 찾기중 오류 발생";
				
				
				String nickName = request.getParameter("userName");
				String email = request.getParameter("mail");
				
				Member member = new Member();
				member.setMemberNickName(nickName);
				member.setEmail(email);
				
				Member findMember = service.findIdResult(member);
				
				if(findMember != null) {
					path = "/WEB-INF/views/member/findIdResultForm.jsp";
					
					request.setAttribute("findMember", findMember);
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
					
				}else {
					request.getSession().setAttribute("swalIcon", "error");
					request.getSession().setAttribute("swalTitle", "존재하지 않는 아이디이거나, 이메일 주소가 다릅니다.");
					response.sendRedirect(request.getHeader("referer"));
				}
				
			}
			
			// -------------- 비밀번호 찾기 화면 전환 Controller ---------------------------
			else if(command.equals("/findPwForm.do")) {
				errorMsg = "화면 전환 과정에서 오류 발생";

				path = "/WEB-INF/views/member/findPwForm.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
			}
			
			// ------------------ 비밀번호 찾기 조회 Cotroller ------------------------------
			else if(command.equals("/findPwReady.do")) {
				errorMsg = "조회 과정에서 오류 발생.";
				
				String memberId = request.getParameter("id");
				
				String email = request.getParameter("mail");
				
				Member member = new Member();
				member.setMemberId(memberId);
				member.setEmail(email);
				
				int findPwMember = service.findPw(member);
				
				if(findPwMember > 0) {
					path = "/WEB-INF/views/member/findPwChangeForm.jsp";
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
					
				}else {
					
					request.getSession().setAttribute("swalIcon", "error");
					request.getSession().setAttribute("swalTitle", "아이디 또는 이메일을 확인해주세요.");
					response.sendRedirect(request.getHeader("referer"));
				}
				
			}
			
			
			
			
			// ---------------- 비밀번호 찾기 인증 Controller ----------------------------
			else if(command.equals("/CheckPwMail")) {
				final String user   = "jihoprac@gmail.com";
				  final String password  = "qkr3158!";

				  String to = request.getParameter("mail");
				  String mTitle = "[뭉개뭉개] 비밀번호 찾기 인증.";
				 
				  try {
				  Map<String, Object> map = new HashMap<>();
				  Random random = new Random();
				  String key = "";
				  
				  for (int i = 0; i < 3; i++) {
						int index = random.nextInt(25) + 65; // A~Z까지 랜덤 알파벳 생성
						key += (char) index;
					}
					int numIndex = random.nextInt(8999) + 1000; // 4자리 정수를 생성
					key += numIndex;
				  
				  
				  // Get the session object
				  Properties props = new Properties();
				  props.put("mail.smtp.host", "smtp.gmail.com");
				  props.put("mail.smtp.port", 465);
				  props.put("mail.smtp.auth", "true");
				  props.put("mail.smtp.ssl.enable", "true");
				  props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

				  Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				   protected PasswordAuthentication getPasswordAuthentication() {
				    return new PasswordAuthentication(user, password);
				   }
				  });

				  // Compose the message
				  
				   MimeMessage message = new MimeMessage(session);
				   message.setFrom(new InternetAddress(user));
				   message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

				   // Subject
				   message.setSubject(mTitle);
				   
				   // Text
				   message.setText("뭉개뭉개에서 보내드리는 비밀번호 찾기용 인증 번호 : " + key);

				   // send the message
				   Transport.send(message);
				   
				   map.put("key", key);
				   response.getWriter().print(key);
				  }catch(Exception e) {
					  e.printStackTrace();
					  
				  }
				  
			}
			// ------------- 비밀번호 찾기 변경 Controller -----------------------------
			else if(command.equals("/findPwResult.do")) {
				errorMsg = "비밀번호 변경 과정에서 문제 발생";
				
				String id = request.getParameter("currId");
				
				String pw1 = request.getParameter("pw1");
				
				int result = service.updatePwd(id, pw1);
				
				if(result > 0) {
					path = "/WEB-INF/views/member/findPwResultForm.jsp";
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
					
				}else {
					request.getSession().setAttribute("swalIcon", "error");
					request.getSession().setAttribute("swalTitle", "비밀번호 변경 실패");
					response.sendRedirect(request.getContextPath());
				}
				
			}
			
			// ------------------- 업체 회원가입 Controller ------------------------------
			else if(command.equals("/comSignUp.do")) {
				errorMsg = "회원가입 과정에서 오류 발생";
				int maxSize = 20 * 1024 * 1024;
				
				String root = request.getSession().getServletContext().getRealPath("/");
				String filePath = root + "resources/uploadImages/comLicenese";
				
				MultipartRequest multiRequest 
				= new MultipartRequest(request, filePath, maxSize,
						"UTF-8", new MyFileRenamePolicy());
				
				String memberId = multiRequest.getParameter("userId");
				String memberPwd = EncryptWrapper.getSha512( multiRequest.getParameter("passwd") );
				String memberNickName = multiRequest.getParameter("userName");

				String e1 = multiRequest.getParameter("email1");
				String e2 = multiRequest.getParameter("email2");
				String email = e1 + "@" + e2;

				String phone1 = multiRequest.getParameter("phone1");
				String phone2 = multiRequest.getParameter("phone2");
				String phone3 = multiRequest.getParameter("phone3");

				String phone = phone1 + "-" + phone2 + "-" + phone3;

				String gender = multiRequest.getParameter("gender");
				
				Member member = new Member(memberId, memberPwd, memberNickName, email, phone, gender);

				int result = service.signUpCom1(member);
				
				if(result > 0) {
					String comName = multiRequest.getParameter("companyName");
					
					String comPhone1 = multiRequest.getParameter("comPhone1"); 
					String comPhone2 = multiRequest.getParameter("comPhone2");
					String comPhone3 = multiRequest.getParameter("comPhone3");
					
					String comPhone = comPhone1 + "-" + comPhone2 + "-" + comPhone3;
					
					String post = multiRequest.getParameter("post"); // 우편번호
					String address1 = multiRequest.getParameter("address1");	// 도로명 주소
					String address2 = multiRequest.getParameter("address2");	// 상세 주소
					
					String comAddress = post + "," + address1 + "," + address2;
					
					

					
					List<Attachment> list = new ArrayList<Attachment>();
					
					Enumeration<String> files = multiRequest.getFileNames();
					
					while(files.hasMoreElements()) {
						
						String name = files.nextElement();
						
						if(multiRequest.getFilesystemName(name) != null) {
							
							// Attachment 객체에 파일 정보 저장
							Attachment temp = new Attachment();
							
							temp.setFileName(multiRequest.getFilesystemName(name));
							temp.setFilePath(filePath);
							
							// name 속성에 따라 fileLevel 지정
							int fileLevel = 0;
							switch(name) {
							case "img0" : fileLevel = 0; break;
							}
							
							temp.setFileLevel(fileLevel);
							
							list.add(temp);
						}
						
					}
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("list", list);
					map.put("comName", comName);
					map.put("comPhone", comPhone);
					map.put("comAddress", comAddress);
					
					result = service.signUpCom(map);
					
					if(result > 0) {	// DB 삽입 성공 시 result에는 삽입한 글 번호가 저장되어 있다.
						swalIcon = "success";
						swalTitle = "회원가입 성공";
					}else {
						swalIcon = "error";
						swalTitle = "게시글 등록 실패";
					}
					
					request.getSession().setAttribute("swalIcon", swalIcon);
					request.getSession().setAttribute("swalTitle", swalTitle);
					response.sendRedirect(request.getContextPath());
					
					
				}
				
				

				
			}
			
			// ----------------- 업체 회원가입용 이메일 Controller ------------------------------
			else if(command.equals("/signUpMail")) {
				final String user   = "jihoprac@gmail.com";
				  final String password  = "qkr3158!";

				  String to = request.getParameter("mail");
				  String mTitle = "[뭉개뭉개] 업체 회원가입 인증.";
				 
				  try {
				  Map<String, Object> map = new HashMap<>();
				  Random random = new Random();
				  String key = "";
				  
				  for (int i = 0; i < 3; i++) {
						int index = random.nextInt(25) + 65; // A~Z까지 랜덤 알파벳 생성
						key += (char) index;
					}
					int numIndex = random.nextInt(8999) + 1000; // 4자리 정수를 생성
					key += numIndex;
				  
				  
				  // Get the session object
				  Properties props = new Properties();
				  props.put("mail.smtp.host", "smtp.gmail.com");
				  props.put("mail.smtp.port", 465);
				  props.put("mail.smtp.auth", "true");
				  props.put("mail.smtp.ssl.enable", "true");
				  props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

				  Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				   protected PasswordAuthentication getPasswordAuthentication() {
				    return new PasswordAuthentication(user, password);
				   }
				  });

				  // Compose the message
				  
				   MimeMessage message = new MimeMessage(session);
				   message.setFrom(new InternetAddress(user));
				   message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

				   // Subject
				   message.setSubject(mTitle);
				   
				   // Text
				   message.setText("뭉개뭉개에서 보내드리는 업체 회원가입 인증 번호 : " + key);

				   // send the message
				   Transport.send(message);
				   
				   map.put("key", key);
				   response.getWriter().print(key);
				  }catch(Exception e) {
					  e.printStackTrace();
					  
				  }
				
			}
			
			// -------------- 내가 쓴 댓글 조회 Controller --------------------------------
			else if(command.equals("/myPageInquiryReply.do")){
				HttpSession session = request.getSession();
				Member loginMember = (Member) session.getAttribute("loginMember");
				int memNo = loginMember.getMemberNo();
				
				String cp = request.getParameter("cp");
				
				PageInfo pInfo = service.getPageInfo(cp, memNo);
				
				List<Reply> myReply = service.myReplySelect(pInfo, memNo);
				
				if(myReply != null) {
					request.setAttribute("myReply", myReply);
					request.setAttribute("pInfo", pInfo);
					
					
					path = "/WEB-INF/views/member/myPageInquiryReply.jsp";
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
					
				}
				
			}
			
			
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
			path = "/WEB-INF/views/common/errorPage.jsp";
			request.setAttribute("errorMsg", errorMsg);
			view = request.getRequestDispatcher(path);
			view.forward(request, response);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
