package com.kh.semi.CSCenter.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.semi.CSCenter.model.vo.PageInfo;
import com.kh.semi.CSCenter.model.vo.Qna;
import com.kh.semi.CSCenter.model.vo.QnaAttachment;
import com.kh.semi.CSCenter.model.service.FaqService;
import com.kh.semi.CSCenter.model.service.NoticeService;
import com.kh.semi.CSCenter.model.vo.Faq;
import com.kh.semi.CSCenter.model.vo.FaqAttachment;
import com.kh.semi.CSCenter.model.vo.Notice;
import com.kh.semi.CSCenter.model.vo.NoticeAttachment;
import com.kh.semi.common.MyFileRenamePolicy;
import com.kh.semi.member.model.vo.Member;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/notice/*")
public class NoticeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri = request.getRequestURI();	
		
		String contextPath = request.getContextPath();
		
		String command = uri.substring((contextPath+"/notice").length() );
		
		// 컨트롤러 내에서 공용으로 사용할 변수 미리 선언
		String path = null;  // forward 또는 redirect 경로를 저장할 변수
		RequestDispatcher view = null; // 요청 위임 객체
		
		String swalIcon = null;
		String swalTitle = null;
		String swalText = null;
		
		String errorMsg = null;	// 에러 메세지 전달용 변수
	
	
		
		try {
			NoticeService  service = new NoticeService();
			// 현재페이지를 얻어옴
			String cp = request.getParameter("cp");
			
			// notice 리스트페이지 이동
			if(command.equals("/notice.do")) {
				errorMsg = "공지사항 페이지 이동 중 오류발생";
				
				
				PageInfo pInfo = service.getPageInfo(cp);
				
				List<Notice> bList  = service.selectNoticeList(pInfo);
				
				path = "/WEB-INF/views/notice/NoticeCenter.jsp";
				
				request.setAttribute("bList", bList);
				request.setAttribute("pInfo", pInfo); 
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
				
			}
			
			
			// notice 상세페이지 이동
			else if(command.equals("/noticeView.do")) {
				
				errorMsg = "공지사항 상세 페이지 이동 중 오류발생";
				
				int NoticeNo = Integer.parseInt(request.getParameter("no"));

				Notice notice = service.selectNotice(NoticeNo);
				
				if(notice != null) {
					
					List<NoticeAttachment> fList = service.selectNoticeFile(NoticeNo);
					
					
					if(!fList.isEmpty()) {
						request.setAttribute("fList", fList);

						}
				
				path = "/WEB-INF/views/notice/NoticeCenterView.jsp";
				request.setAttribute("notice", notice);
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			
			}else{
				request.getSession().setAttribute("swalIcon", "error");
				request.getSession().setAttribute("swalTitle", "게시글 상세 조회 실패");
				response.sendRedirect("notice.do?cp=1");
			}
		}
		
			
			// notice 글쓰기폼 이동
 		else if(command.equals("/noticeInsertForm.do")){
				
				errorMsg = "공지사항 글쓰기 이동 중 오류발생";
				path = "/WEB-INF/views/notice/NoticeCenterInsert.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
				
			}
			
			
			// notice 글쓰거 서버 요청
			else if(command.equals("/noticeInsert.do")) {
				
				
				errorMsg = "공지사항 삽입 과정에서 오류 발생";
				
				int maxSize = 20 * 1024 * 1024; // 20MB = 20 * 1024KB == 20* 1024 * 1024Byte

				String root = request.getSession().getServletContext().getRealPath("/");
				String filePath = root + "/resources/image/center/noticeImg/";

				
				MultipartRequest multiRequest
				= new MultipartRequest(request,filePath, maxSize,"UTF-8",new MyFileRenamePolicy());
				
				
				List<NoticeAttachment> fList = new ArrayList<NoticeAttachment>();

				Enumeration<String> files = multiRequest.getFileNames();

				while(files.hasMoreElements()) { // 다음 요소가 있다면

				
					String name = files.nextElement(); // img0
	
					if(multiRequest.getFilesystemName(name) != null){
						
						//  Attachment 객체에 파일 정보 저장
						NoticeAttachment temp = new NoticeAttachment();
						
						temp.setNotifileName(multiRequest.getFilesystemName(name));
						temp.setNotifilePath(filePath);
						
						
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
						
						temp.setNotiFileLevel(fileLevel);
						
						// fList에추가
						fList.add(temp);
					}
					
					
				}
				
				String NoticeTitle = multiRequest.getParameter("noticeTitle");
				String NoticeContent = multiRequest.getParameter("noticeContent");

			
				Member loginMember = (Member)request.getSession().getAttribute("loginMember");
				int NoticeWriter = loginMember.getMemberNo();
				
				
				
				Map<String,Object> map = new HashMap<String,Object>();

				
				map.put("fList",fList);
				map.put("NoticeTitle",NoticeTitle);
				map.put("NoticeContent",NoticeContent);
				map.put("NoticeWriter",NoticeWriter);
				
				
				int result = service.insertNotice(map);
				
				
				if(result > 0) { // DB 삽입 성공 시 result에는 삽입한 글 번호가 저장 되어 있다!
					swalIcon ="success";
					swalTitle="게시글 등록 성공";
					path ="noticeView.do?cp=1&no="+result;
				}else {
					swalIcon ="error";
					swalTitle = "게시글 등록 실패";
					path="noticeView.do"; // 게시글 목록
					
				}
 				
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				
				response.sendRedirect(path);
				
				
			}
			
			
			
			// notice 수정폼 이동
			else if(command.equals("/noticeUpdateForm.do")){
				
				errorMsg = " 공지사항 수정 화면 이동 중 오류발생";
				
				
				int NoticeNo = Integer.parseInt(request.getParameter("no"));

				
				
				Notice notice = service.updateNoticeView(NoticeNo);
				
				
				if(notice != null) {
					
					
				List<NoticeAttachment> fList = service.selectNoticeFile(NoticeNo);

				if(!fList.isEmpty()) {
					request.setAttribute("fList", fList);
					request.setAttribute("notice", notice);
					path = "/WEB-INF/views/notice/NoticeCenterUpdate.jsp";
					view = request.getRequestDispatcher(path);
					view.forward(request, response);

					
				}else {
					request.setAttribute("notice", notice);
					path = "/WEB-INF/views/notice/NoticeCenterUpdate.jsp";
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
					
				}
					
				}
				
			}
			// notice 수정 
			else if(command.equals("/noticeUpdate.do")){

			
				errorMsg= "Notice수정 과정에서 오류 발생";
				
				int maxSize = 20 * 1024 * 1024; // 최대 크기 20mb
				
				String root = request.getSession().getServletContext().getRealPath("/");
				String filePath = root + "resources/image/center/noticeImg/";
				
				MultipartRequest mRequest =
						new MultipartRequest(request,filePath,maxSize,"UTF-8", new MyFileRenamePolicy());
				
			
				
				String noticeTitle = mRequest.getParameter("noticeTitle");
				String noticeContent= mRequest.getParameter("noticeContent");
				int noticeNo = Integer.parseInt(mRequest.getParameter("no"));

				
				List<NoticeAttachment> fList = new ArrayList<NoticeAttachment>();
			
				
				Enumeration<String> files = mRequest.getFileNames();

				
				while(files.hasMoreElements()) {
					
					// 현재 접근중인 name 속성값을 변수에 저장
					String name = files.nextElement();
					
					// 현재  name 속성이 일치하는 요소로 업로드된 파일이 있다면
					if(mRequest.getFilesystemName(name) !=null) {
						
						NoticeAttachment temp = new NoticeAttachment();
						
						//  변경된 파일 이름 temp에 저장
						temp.setNotifileName(mRequest.getFilesystemName(name));
						
						// 지정한 파일 경로 temp에 저장
						temp.setNotifilePath(filePath);
						
						// 해당 게시글 번호 temp에 저장
						temp.setNotiParentNo(noticeNo);
						
						// 파일 레벨 temp에 저장
						switch(name) {
						case "img0"  : temp.setNotiFileLevel(0); break;
						case "img1"  : temp.setNotiFileLevel(1); break;
						case "img2"  : temp.setNotiFileLevel(2); break;
						case "img3"  : temp.setNotiFileLevel(3); break;
						case "img4"  : temp.setNotiFileLevel(4); break;
						case "img5"  : temp.setNotiFileLevel(5); break;
						case "img6"  : temp.setNotiFileLevel(6); break;
						case "img7"  : temp.setNotiFileLevel(7); break;					
						}
						
						
						fList.add(temp);
					}
					
					
				}
				
				
				int boardWriter 
				=  ((Member)request.getSession().getAttribute("loginMember")).getMemberNo();
				
				
				Map<String, Object> map = new HashMap<String,Object>();

				map.put("noticeTitle",noticeTitle);
				map.put("noticeContent",noticeContent);
				map.put("noticeNo",noticeNo);
				map.put("fList",fList);
				map.put("boardWriter",boardWriter);
				
				int result = service.updateNotice(map);
				
				path = "noticeView.do?cp="+cp+"&no="+noticeNo;
				
				
				String sk =mRequest.getParameter("sk");
				String sv =mRequest.getParameter("sv");
				
			
				if(sk !=null && sv !=null) {
					
					path += "&sk=" + sk + "&sv=" +sv ;
					
					
				}
				
				if(result > 0) {
					swalIcon = "success";
					swalTitle = "Notice 수정 성공";
					
				}else{
					swalIcon ="error";
					swalTitle ="Notice 수정 실패";
					
				}
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				
				response.sendRedirect(path);
				
			} 
			
			else if(command.equals("/deleteNotice.do")) {

				errorMsg= "Notice 삭제 과정에서 오류 발생";

				int noticeNo = Integer.parseInt(request.getParameter("no"));

				int result = service.deleteNotice(noticeNo);

				if(result > 0) {
					swalIcon = "success";
					swalTitle = "게시글 삭제 성공";
					path="notice.do";
				}else {
					swalIcon ="error";
					swalTitle ="게시글 삭제 실패";
					path=request.getHeader("referer");
				
				}
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				response.sendRedirect(path);
			}
			
			
		} catch(Exception e) {
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
