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

import com.kh.semi.CSCenter.model.service.FaqService;
import com.kh.semi.CSCenter.model.vo.Faq;
import com.kh.semi.CSCenter.model.vo.FaqAttachment;
import com.kh.semi.CSCenter.model.vo.PageInfo;
import com.kh.semi.common.MyFileRenamePolicy;
import com.kh.semi.member.model.vo.Member;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/faq/*")
public class FaqController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
	
		String uri = request.getRequestURI();	
		
		String contextPath = request.getContextPath();
		
		String command = uri.substring((contextPath+"/faq").length() );
		
		// 컨트롤러 내에서 공용으로 사용할 변수 미리 선언
		String path = null;  // forward 또는 redirect 경로를 저장할 변수
		RequestDispatcher view = null; // 요청 위임 객체
		
		String swalIcon = null;
		String swalTitle = null;
		String swalText = null;
		
		String errorMsg = null;	// 에러 메세지 전달용 변수
	
	
		try {
			
			FaqService  service = new FaqService();
			// 현재페이지를 얻어옴
			String cp = request.getParameter("cp");
			
			// 자주묻는 질문  리스트 페이지 이동
			if(command.equals("/faq.do")) {
				
				errorMsg ="자주묻는질문 목록 조회 과정에서 오류 발생";
				
				PageInfo pInfo = service.getPageInfo(cp);
				
				
				List<Faq> bList  = service.selectFaqList(pInfo);
				
				
				
				path = "/WEB-INF/views/faq/FaqCenter.jsp";
				
				request.setAttribute("bList", bList);
				request.setAttribute("pInfo", pInfo);

				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			
			}
			
			
			// 자주묻는 질문  상세 페이지 이동
			else if(command.equals("/faqView.do")) {
			
				errorMsg = "페이지 이동 중 오류발생";
				
				int faqNo = Integer.parseInt(request.getParameter("no"));
				
				Faq faq = service.selectFaqView(faqNo);
				
				
				if(faq != null) {
					
					List<FaqAttachment> fList = service.selectFaqFile(faqNo);
					
					if(!fList.isEmpty()) {
						
						request.setAttribute("fList", fList);

					}
					
					
				}
				
				path = "/WEB-INF/views/faq/FaqView.jsp";
				request.setAttribute("faq", faq);
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			
			}
			
			// 자주묻는 질문 글쓰기 이동
			else if(command.equals("/faqInsertForm.do")) {
			
				
				errorMsg = "페이지 이동 중 오류발생";
				path = "/WEB-INF/views/faq/FaqInsert.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
				
			
			}		
			
			
			// 자주 묻는 질문 글쓰기 등록
			else if(command.equals("/faqInsert.do")) {
			
				errorMsg = "자주 묻는 질문 삽입 과정에서   오류발생";
			
				int maxSize = 20 * 1024 * 1024; // 20MB = 20 * 1024KB == 20* 1024 * 1024Byte
				
				String root = request.getSession().getServletContext().getRealPath("/");
				String filePath = root + "resources/image/center/faqImg/";
				
				MultipartRequest multiRequest
				= new MultipartRequest(request,filePath, maxSize,"UTF-8", new MyFileRenamePolicy());
				
				
				List<FaqAttachment> fList = new ArrayList<FaqAttachment>();

				
				Enumeration<String> files = multiRequest.getFileNames();

				
			while(files.hasMoreElements()) { // 다음 요소가 있다면
					
					// 현재 접근한 요소 값 반환
					String name = files.nextElement(); // img0
				//	System.out.println("name : " + name);
				//	System.out.println("원본 파일명 : " + multiRequest.getOriginalFileName(name));
				//	System.out.println("변경된 파일명:" + multiRequest.getFilesystemName(name));
				 
					
					// 제출받은 file태그 요소 중 업로드된 파일이 있을 경우
					if(multiRequest.getFilesystemName(name) != null){
						
						//  Attachment 객체에 파일 정보 저장
						FaqAttachment temp = new FaqAttachment();
						
						temp.setFaqFileName(multiRequest.getFilesystemName(name));
						temp.setFaqFilePath(filePath);
						
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
						
						temp.setFaqFileLevel(fileLevel);
						
						// fList에추가
						fList.add(temp);
					}
					
				} // end while
				
				String faqTitle = multiRequest.getParameter("faqTitle");
				String faqContent = multiRequest.getParameter("faqContent");

			
				Member loginMember = (Member)request.getSession().getAttribute("loginMember");
				int faqWriter = loginMember.getMemberNo();
				
				
				
				Map<String,Object> map = new HashMap<String,Object>();

				
				map.put("fList",fList);
				map.put("faqTitle",faqTitle);
				map.put("faqContent",faqContent);
				map.put("faqWriter",faqWriter);
				
				
				int result = service.insertFaq(map);
				
				
				if(result > 0) { // DB 삽입 성공 시 result에는 삽입한 글 번호가 저장 되어 있다!
					swalIcon ="success";
					swalTitle="게시글 등록 성공";
					path ="faqView.do?cp=1&no="+result;
				}else {
					swalIcon ="error";
					swalTitle = "게시글 등록 실패";
					path="faqView.do"; // 게시글 목록
					
				}
 				
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				
				response.sendRedirect(path);
				
				
			
			}	
			
			// 자주묻는 질문 수정 이동
			
			else if(command.equals("/faqUpdateForm.do")) {
				errorMsg = "게시글 수정페이지 이동 중 오류 발생";
				
				
				int faqNo = Integer.parseInt(request.getParameter("no"));

				Faq faq = service.faqUpdateView(faqNo);
				
				if(faq != null) {
					
					List<FaqAttachment> fList = service.selectFaqFile(faqNo);

					if(!fList.isEmpty()) {
						
						request.setAttribute("fList", fList);
						request.setAttribute("faq", faq);
						path = "/WEB-INF/views/faq/FaqUpdate.jsp";
						view = request.getRequestDispatcher(path);
						view.forward(request, response);
					
					}else {
						
						request.setAttribute("faq", faq);
						path = "/WEB-INF/views/faq/FaqUpdate.jsp";
						view = request.getRequestDispatcher(path);
						view.forward(request, response);
					}
			
				}
				
			
				
			}
			
			// faq 글 수정 controller
			else if(command.equals("/faqUpdate.do")) {
				
				errorMsg="faq수정 과정에서 오류 발생";
				
				int maxSize = 20 * 1024 * 1024; // 최대 크기 20mb
				
				String root = request.getSession().getServletContext().getRealPath("/");
				String filePath = root + "resources/image/center/faqImg/";
				
				MultipartRequest mRequest =
						new MultipartRequest(request,filePath,maxSize,"UTF-8", new MyFileRenamePolicy());
				
			
				
				String faqTitle = mRequest.getParameter("faqTitle");
				String faqContent= mRequest.getParameter("faqContent");

				int faqNo = Integer.parseInt(mRequest.getParameter("no"));

				
				List<FaqAttachment> fList = new ArrayList<FaqAttachment>();
			
				
				Enumeration<String> files = mRequest.getFileNames();

				
				while(files.hasMoreElements()) {
					
					// 현재 접근중인 name 속성값을 변수에 저장
					String name = files.nextElement();
					
					// 현재  name 속성이 일치하는 요소로 업로드된 파일이 있다면
					if(mRequest.getFilesystemName(name) !=null) {
						
						FaqAttachment temp = new FaqAttachment();
						
						//  변경된 파일 이름 temp에 저장
						temp.setFaqFileName(mRequest.getFilesystemName(name));
						
						// 지정한 파일 경로 temp에 저장
						temp.setFaqFilePath(filePath);
						
						// 해당 게시글 번호 temp에 저장
						temp.setParentfaqNo(faqNo);
						
						// 파일 레벨 temp에 저장
						
						
						switch(name) {
						case "img0"  : temp.setFaqFileLevel(0); break;
						case "img1"  : temp.setFaqFileLevel(1); break;
						case "img2"  : temp.setFaqFileLevel(2); break;
						case "img3"  : temp.setFaqFileLevel(3); break;
						case "img4"  : temp.setFaqFileLevel(4); break;
						case "img5"  : temp.setFaqFileLevel(5); break;
						case "img6"  : temp.setFaqFileLevel(6); break;
						case "img7"  : temp.setFaqFileLevel(7); break;					
						}
						
						
						// temp를 fList에 추가
						fList.add(temp);
					}
					
					
				}
				
				
				int boardWriter 
				=  ((Member)request.getSession().getAttribute("loginMember")).getMemberNo();
				
				
				Map<String, Object> map = new HashMap<String,Object>();

				map.put("faqTitle",faqTitle);
				map.put("faqContent",faqContent);
				map.put("faqNo",faqNo);
				map.put("fList",fList);
				map.put("boardWriter",boardWriter);
				
				int result = service.updateFaq(map);
				
				path = "faqView.do?cp="+cp+"&no="+faqNo;
				
				
				
				String sk =mRequest.getParameter("sk");
				String sv =mRequest.getParameter("sv");
				
			
				if(sk !=null && sv !=null) {
					
					path += "&sk=" + sk + "&sv=" +sv ;
					
					
				}
				
				if(result > 0) {
					swalIcon = "success";
					swalTitle = "FAQ수정 성공";
					
				}else{
					swalIcon ="error";
					swalTitle ="FAQ 수정 실패";
					
				}
				request.getSession().setAttribute("swalIcon", swalIcon);
				request.getSession().setAttribute("swalTitle", swalTitle);
				
				response.sendRedirect(path);
				
			}
			
			
			// FAQ 글 삭제 
			else if(command.equals("/deleteFaq.do")) {
				
			
			errorMsg="FAQ 삭제 과정에서 오류 발생 ";
			
		    int faqNo = Integer.parseInt(request.getParameter("no"));
				
		    int result = service.deleteFaq(faqNo);
	
			
			if(result > 0) {
				swalIcon = "success";
				swalTitle = "FAQ 삭제 성공";
				path="faq.do";
			
			}else {
				swalIcon ="error";
				swalTitle ="FAQ 삭제 실패";
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


