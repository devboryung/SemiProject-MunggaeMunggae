package com.kh.semi.CSCenter.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.semi.CSCenter.model.service.QnaService;
import com.kh.semi.CSCenter.model.vo.Faq;
import com.kh.semi.CSCenter.model.vo.FaqAttachment;
import com.kh.semi.CSCenter.model.vo.PageInfo;
import com.kh.semi.CSCenter.model.vo.Qna;
import com.kh.semi.CSCenter.model.vo.QnaAttachment;
import com.kh.semi.common.MyFileRenamePolicy;
import com.kh.semi.member.model.vo.Member;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/qna/*")
public class QnaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
		String uri = request.getRequestURI();	
		
		String contextPath = request.getContextPath();
		
		String command = uri.substring((contextPath+"/qna").length() );
		
		// 컨트롤러 내에서 공용으로 사용할 변수 미리 선언
		String path = null;  // forward 또는 redirect 경로를 저장할 변수
		RequestDispatcher view = null; // 요청 위임 객체
		
		String swalIcon = null;
		String swalTitle = null;
		String swalText = null;
		
		String errorMsg = null;	// 에러 메세지 전달용 변수
	
		
		try {
			
			QnaService service = new QnaService();
			
			String cp = request.getParameter("cp");
			
			
			// Q&A 목록 조회  페이지 이동
			if(command.equals("/qna.do")) {
				
				errorMsg ="QNA 게시판 목록 조회 과정에서 오류 발생";
				
				String publicBtn = request.getParameter("public");
				String incomplete  = request.getParameter("incompleteBtn");
				String myQna  = request.getParameter("myQna");

				//System.out.println("값확인"+ publicBtn);
				//System.out.println("값확인"+ memNo);

					// 공개된 글 목록 조회
					if(publicBtn != null) {	
					
							
					    PageInfo pInfo = service.getPagePublicInfo(cp);
						
						List<Qna> bList = service.selectQnaPublicList(pInfo);
						
						
						path="/WEB-INF/views/qna/QnaCenter.jsp";
						request.setAttribute("bList", bList);
						request.setAttribute("pInfo", pInfo);
						view = request.getRequestDispatcher(path);
						view.forward(request, response);
					
					// 처리중인 내역 조회
					}else if(incomplete != null) {
						
						PageInfo pInfo = service.getPageIncompleteInfo(cp);
						
						List<Qna> bList = service.selectQnaIncompleteList(pInfo);
						
						path="/WEB-INF/views/qna/QnaCenter.jsp";
						request.setAttribute("bList", bList);
						request.setAttribute("pInfo", pInfo);
						view = request.getRequestDispatcher(path);
						view.forward(request, response);
					
					// 나의문의 내역 조회
					}else if(myQna != null){
						
						int memNo = Integer.parseInt(request.getParameter("memNo"));
					    PageInfo pInfo = service.getPageMyQnaInfo(cp ,memNo);
					    List<Qna> bList = service.selectMyQnaList(pInfo ,memNo);
					    path="/WEB-INF/views/qna/QnaCenter.jsp"; request.setAttribute("bList",bList);
					    request.setAttribute("pInfo", pInfo); view =
					    request.getRequestDispatcher(path); view.forward(request, response);
					  
					 
					 // 전체 목록 조회 }
					}else{
				
					PageInfo pInfo = service.getPageInfo(cp);
					
					List<Qna> bList = service.selectQnaList(pInfo);
					
					path="/WEB-INF/views/qna/QnaCenter.jsp";
					request.setAttribute("bList", bList);
					request.setAttribute("pInfo", pInfo);
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
					
					}
					
				}
					
	
			
			
			// Q&A   상세 페이지 이동
			else if(command.equals("/qnaView.do")) {
			
				errorMsg = "게시글 상세 조회 과정에서 오류 발생";
				
				int qnaNo = Integer.parseInt(request.getParameter("no"));
				
					Qna qna = service.selectQna(qnaNo);
				
					
					if(qna != null) {
						
					List<QnaAttachment> fList =service.selectQnaFile(qnaNo);

					
					  if(!fList.isEmpty()) { request.setAttribute("fList", fList);
					 
						request.setAttribute("fList", fList);

					  }
					
					path ="/WEB-INF/views/qna/QnaView.jsp";
					request.setAttribute("qna", qna);
					view=request.getRequestDispatcher(path);
					view.forward(request, response);
						
						
					}else {
					
					request.getSession().setAttribute("swalIcon", "error");
					request.getSession().setAttribute("swalTitle", "게시글 상세 조회 실패");
					response.sendRedirect("qna.do?cp=1");
						
					}
			
			}
			
			// Q&A 글쓰기 이동
			else if(command.equals("/qnaForm.do")) {
			
				errorMsg = "페이지 이동 중 오류발생";
				path = "/WEB-INF/views/qna/QnaInsert.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			
			}		
			
			// Q&A 글쓰기 등록 (이미지 업로드)
			else if(command.equals("/qnaInsert.do")) {
				
				errorMsg = "Q&A 글 등록 과정에서 오류 발생";
				
				int maxSize = 20 * 1024 * 1024;
				
					
				String root = request.getSession().getServletContext().getRealPath("/");
				String filePath = root + "resources/image/center/qnaImg/";
				
				
				MultipartRequest multiRequest
				= new MultipartRequest(request,filePath, maxSize,"UTF-8", new MyFileRenamePolicy());	
				
				
				// 파일 정보를 모두 저장할 List 객체
				List<QnaAttachment> fList = new ArrayList<QnaAttachment>();

				// MultipartRequest에서 업로드된 파일의 name 속성값 모두 반환 받기
				Enumeration<String> files = multiRequest.getFileNames();

				
				//  얻어온 Enumeration 객체에 요소를 하나씩 반복 접근하여
				//  업로드된 파일 정보를 Attachment 객체에 저장한 후
				//  fList에 추가하기
				
			
				while(files.hasMoreElements()) { 
					
					String name = files.nextElement(); // img0

					
					if(multiRequest.getFilesystemName(name) != null){
						
						//  Attachment 객체에 파일 정보 저장
						QnaAttachment temp = new QnaAttachment();
						
						temp.setQnaImgName(multiRequest.getFilesystemName(name));
						temp.setQnaImgPath(filePath);
						
						
						// name 속성에 따라 fileLevel 지정
						int fileLevel = 0;
						switch(name) {
						case "img0"  : fileLevel = 0; break;
						case "img1"  : fileLevel = 1; break;
						case "img2"  : fileLevel = 2; break;
						case "img3"  : fileLevel = 3; break;
						case "img4"  : fileLevel = 4; break;
						case "img5"  : fileLevel = 5; break;
						case "img6"  : fileLevel = 6; break;
						case "img7"  : fileLevel = 7; break;
					
						}
						
						temp.setQnaImgLevel(fileLevel);
					
						fList.add(temp);
					}
				
				}
				
				String qnaTitle = multiRequest.getParameter("qnaTitle");
				String qnaContent = multiRequest.getParameter("qnaContent");
				String qnaCheck =  multiRequest.getParameter("publicCheck");
				
				// 세션에 로그안한 회원의 번호 얻어오기
				Member loginMember = (Member)request.getSession().getAttribute("loginMember");
				int boardWriter = loginMember.getMemberNo();
				
				
				Map<String,Object> map = new HashMap<String,Object>();

				map.put("fList",fList);
				map.put("qnaTitle",qnaTitle);
				map.put("qnaContent",qnaContent);
				map.put("qnaCheck",qnaCheck);
				map.put("boardWriter",boardWriter);
				
				// 이미지 결과 반환
				int result = service.insertQna(map);
				System.out.println(result);
				if(result > 0) { // DB 삽입 성공 시 result에는 삽입한 글 번호가 저장 되어 있다!
					swalIcon ="success";
					swalTitle="게시글 등록 성공";
					path ="qnaView.do?cp=1&no="+ result;
				
				}else {
					swalIcon ="error";
					swalTitle = "게시글 등록 실패";
					path="qna.do"; // 게시글 목록
					
				}
				
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				
				response.sendRedirect(path);

			}
			
			
			
			// Q&A  질문 수정 이동
			else if(command.equals("/qnaUpdateForm.do")) {
			
				errorMsg = "Qna수정 화면  이동 중 오류발생";
				
				
				int qnaNo = Integer.parseInt(request.getParameter("no"));

				
				
				Qna qna = service.updateQnaview(qnaNo);
				
				
				if(qna != null) {
					
					
				List<QnaAttachment> fList = service.selectQnaFile(qnaNo);

				if(!fList.isEmpty()) {
					request.setAttribute("fList", fList);
					request.setAttribute("qna", qna);
					path = "/WEB-INF/views/qna/QnaUpdate.jsp";
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
						
					
				}else {
					request.setAttribute("qna", qna);
					path = "/WEB-INF/views/qna/QnaUpdate.jsp";
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
					
				}
				
				}
				
			
			
			}
			
			// Qna 게시글 수정 
			else if(command.equals("/qnaUpdate.do")) {

				errorMsg="Qna 게시글 수정 과정에서 오류 발생";

				int maxSize = 20 * 1024 * 1024; // 최대 크기 20mb

				String root = request.getSession().getServletContext().getRealPath("/");

				String filePath = root + "resources/image/center/qnaImg/";

				MultipartRequest mRequest =
						new MultipartRequest(request,filePath,maxSize,"UTF-8", new MyFileRenamePolicy());
				
				String qnaTitle = mRequest.getParameter("qnaTitle");
				String qnaContent= mRequest.getParameter("qnaContent");
				String publicCheck= mRequest.getParameter("publicCheck");

				int qnaNo = Integer.parseInt(mRequest.getParameter("no"));

				List<QnaAttachment> fList = new ArrayList<QnaAttachment>();

				Enumeration<String> files = mRequest.getFileNames();

				while(files.hasMoreElements()) {
				
					String name = files.nextElement();

					
					if(mRequest.getFilesystemName(name) !=null) {

						QnaAttachment temp = new QnaAttachment();

						
						temp.setQnaImgName(mRequest.getFilesystemName(name));

						
						temp.setQnaImgPath(filePath);

						
						temp.setParentQnaNo(qnaNo);
						
						
						switch(name) {
						case "img0" : temp.setQnaImgLevel(0); break;						
						case "img1" : temp.setQnaImgLevel(1); break;						
						case "img2" : temp.setQnaImgLevel(2); break;						
						case "img3" : temp.setQnaImgLevel(3); break;
						case "img4" : temp.setQnaImgLevel(4); break;						
						case "img5" : temp.setQnaImgLevel(5); break;						
						case "img6" : temp.setQnaImgLevel(6); break;						
						case "img7" : temp.setQnaImgLevel(7); break;						
						}
								
						fList.add(temp);
					}
					
					
				}
				
				// 로그인한 회원의 번호를 얻더와 저장
				int boardWriter 
				=  ((Member)request.getSession().getAttribute("loginMember")).getMemberNo();
		
				
				Map<String, Object> map = new HashMap<String,Object>();
				
				map.put("qnaTitle",qnaTitle);
				map.put("qnaContent",qnaContent);
				map.put("publicCheck",publicCheck);
				map.put("qnaNo",qnaNo);
				map.put("fList",fList);
				map.put("boardWriter",boardWriter);

				//  준비된 값을 매개변수로 하여 게시글 수정 Service 호출
			
				int result = service.updateQna(map);
				
				path = "qnaView.do?cp="+cp+"&no="+qnaNo; 
				
				// result 값에 따라 화면 연결 처리
				String sk = mRequest.getParameter("sk");
				String sv = mRequest.getParameter("sv");
				
				if(sk != null && sv !=null) {
					path += "&sk=" + sk + "&sv=" + sv;
				}
				
				if(result > 0) {
					swalIcon = "success";
					swalTitle = "Qna 수정 성공";
					
					
				}else {
					swalIcon ="error";
					swalTitle ="Qna 수정 실패";
				}
				
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				response.sendRedirect(path);

			}
			
			
			// Qna 게시글 삭제 
			
			else if(command.equals("/deleteQna.do")) {
				
				errorMsg="Qna 게시글 삭제 과정에서 오류 발생";

				 int qnaNo = Integer.parseInt(request.getParameter("no"));
				
			   	 int result = service.deleteQna(qnaNo);
 
					if(result > 0) {
						swalIcon = "success";
						swalTitle = "Q&A 삭제 성공";
						path="qna.do";
					}else {
						swalIcon ="error";
						swalTitle ="Q&A 삭제 실패";
						path=request.getHeader("referer");
					}
				
					request.getSession().setAttribute("swalIcon", swalIcon);
					request.getSession().setAttribute("swalTitle", swalTitle);
					response.sendRedirect(path);
					
			}
			
			
			
		} catch (Exception e) {
		
			e.printStackTrace();
			path = "/WEB-INF/views/common/errorPage.jsp";
			request.setAttribute("errorMsg", errorMsg);
			view = request.getRequestDispatcher(path);
			view.forward(request, response);
		
		}
		
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
