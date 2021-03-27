package com.kh.semi.travel.controller;

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
import javax.servlet.http.HttpSession;

import com.kh.semi.common.MyFileRenamePolicy;
import com.kh.semi.freeBoard.model.vo.Attachment;
import com.kh.semi.hospital.model.vo.Hospital;
import com.kh.semi.hospital.model.vo.PageInfo;
import com.kh.semi.member.model.vo.Member;
import com.kh.semi.travel.model.service.TravelService;
import com.kh.semi.travel.model.vo.Travel;
import com.kh.semi.travel.model.vo.TravelAttachment;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/travel/*")
public class TravelController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uri = request.getRequestURI(); // 요청 들어오는 주소 /travel/
		String contextPath = request.getContextPath();
		String command = uri.substring((contextPath + "/travel").length());

		// 컨트롤러 내에서 공용으로 사용할 변수 미리 선언
		String path = null; // forward 또는 redirect 경로를 저장할 변수
		RequestDispatcher view = null; // 요청 위임 객체

		String swalIcon = null; // sweet alert로 메세지 전달하는 용도
		String swalTitle = null;
		String swalText = null;
		String errorMsg = null; // 에러 메세지 전달용 변수

		try {
			TravelService service = new TravelService();
			
			String cp = request.getParameter("cp");
			
			// 지역정보 리스트 조회 Controller **************************************************
			if (command.equals("/localList.do")) {
				errorMsg = "지역정보 리스트 조회 중 오류 발생.";
				
				// 1) 페이징 처리를 위한 값 계산 Service호출
				PageInfo pInfo = service.getPageInfo(cp);
				
				System.out.println(cp);
				
				// 2) 게시글 목록 조회 비즈니스 로직 수행
				List<Travel> tList = service.selectTravelList(pInfo);
				// pInfo를 가져가는 이유 = 
				// pInfo에 담겨져있는 currentPage와 limit를 이용해 현재 페이지에 맞는 게시글 목록을 조회하기 위해
				
				
				// 썸네일 추가
	            if(tList != null) {
	                // 썸네일 이미지 목록 조회 서비스 호출
	                List<TravelAttachment> fList = service.selectThumbnailList(pInfo);
	                
	                // 썸네일 이미지 목록이 비어있지 않은 경우
	                if(!fList.isEmpty()) {
	                   request.setAttribute("fList", fList);
	                }
	                
	            }
				
				
				// 요청을 위임할 경로 jsp 경로 지정
				path = "/WEB-INF/views/travel/localInfo/localList.jsp";

				request.setAttribute("tList", tList);
				request.setAttribute("pInfo", pInfo);				
				
				// 요청 위임 객체 생성 후 위임 진행
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// 게시글 작성 화면 전환 Controller **********************************
	         else if(command.equals("/localInsertForm.do")) {
	            
	            path = "/WEB-INF/views/travel/localInfo/localInsert.jsp";
	            view = request.getRequestDispatcher(path);
	            view.forward(request, response);
	            
	         }
			
			
			// 지역정보 글 상세 조회 Controller **************************************************
			else if (command.equals("/localView.do")) {
				errorMsg = "지역정보 글 상세 조회 중 오류 발생.";
				
				// 쿼리스트링으로 전달된 공지사항 번호를 int형으로 파싱(자료형을 바꿈)하여 저장
				int travelNo = Integer.parseInt(request.getParameter("no") );				
				System.out.println(travelNo);
				// 상세조회 비즈니스 로직 수행 결과 반환
				Travel travel = service.selectTravel(travelNo);
				
				// 조회 결과에 따른 view 연결 처리
				if(travel != null) { // 조회 성공
					
					List<TravelAttachment> fList = service.selectBoardFiles(travelNo);
					
					if(!fList.isEmpty()) {	// 해당 게시글 이미지 정보가 DA에 있을 경우
						request.setAttribute("fList", fList);
					}
					
					path = "/WEB-INF/views/travel/localInfo/localView.jsp";
					request.setAttribute("travel", travel);
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
				} else { // 조회 실패 했을 때
					request.getSession().setAttribute("swalIcon", "error");
					request.getSession().setAttribute("swalTitle", "지역정보글 상세 조회 실패");
					response.sendRedirect(request.getHeader("referer"));
				}
			}

			// 지역정보 글쓰기 Controller **************************************************
			else if (command.equals("/localInsert.do")) {
				errorMsg = "지역정보 글쓰기 중 오류 발생.";

				// 1. MultipartRequest 객체 생성하기
	            // 1-1. 전송 파일 용량 지정 (byte 단위)
	            int maxSize = 20 * 1024 * 1024;   //20MB == 20 * 1024KB == 20 * 1024 * 1024Byte
	               
	            // 1-2. 서버에 업로드된 파일을 저장할 경로 지정
	            String root = request.getSession().getServletContext().getRealPath("/");
	                     // 배포되는 최상위 주소    /wsp
	            String filePath = root + "resources/uploadImages/travel/";
	            
	            System.out.println("filePath : " + filePath);
				
	         // 1-3. 파일명 변환을 위한 클래스 작성하기
	            // cos.jar에서 중복되는 파일이 업로드 되었을 때 파일명을 바꿔주는
	            // DefaultFileRenamePolicy 클래스를 제공해주지만
	            // ex) a.jpg, a(1).jpg, a(2).jpg
	            // 파일명에 업로드된 시간을 표기할 수 있도록 변경하는 별도의 클래스 작성
	            
	            
	            // 1-4. MultipartRequest 객체 생성
	            // -> 객체 생성과 동시에 파라미터로 넘어온 내용 중 파일이 서버에 바로 저장됨.
	            MultipartRequest multiRequest 
	               = new MultipartRequest(request, filePath, maxSize, "UTF-8", new MyFileRenamePolicy());
	            
	            
	            // 2. 생성한 MultipartRequest 객체에서 파일 정보만을 얻어와
	            // 별도의 List에 모두 저장하기
	            
	            // 2-1. 파일 정보를 모두 저장할 List 객체 생성
	            List<TravelAttachment> fList = new ArrayList<TravelAttachment>();
	            
	            // 2-2. MultipartRequest에서 업로드된 파일  name 속성값 모두 반환 받기
	            Enumeration<String> files = multiRequest.getFileNames();
	            // Iterator : 컬렉션 요소 반복 접근자
	            // Enumeration : Iterator의 과거 버전
				
	            // 2-3. 얻어온 Enumeration 객체에 요소를 하나씩 반복 접근하여
	            //      업로드된 파일 정보를 Attachment 객체에 저장한 후
	            //      fList에 추가하기
	            while(files.hasMoreElements()) {
	               //hasMoreElements : 다음 요소가 있는지?
	               
	               // 현재 접근한 요소 값 반환
	               String name = files.nextElement(); // img0
//	               System.out.println("name : " + name);
//	               System.out.println("원본 파일명 : " 
//	                     + multiRequest.getOriginalFileName(name));
//	               System.out.println("변경된 파일명 : " 
//	                     + multiRequest.getFilesystemName(name));
	               
	               // 제출받은 file태그 요소 중 업로드된 파일이 있을 경우
	               if(multiRequest.getFilesystemName(name) != null) {
	                  
	                  // Attachment 객체에 파일 정보 저장
	                  TravelAttachment temp = new TravelAttachment();
	                  
	                  temp.setFileName(multiRequest.getFilesystemName(name));
						temp.setFilePath(filePath);
	                  
	                  // name 속성에 따라 fileLevel 지정
	                  int fileLevel = 0;
	                  switch(name) {
	                  case "img0" : fileLevel = 0; break;
	                  case "img1" : fileLevel = 1; break;
	                  case "img2" : fileLevel = 2; break;
	                  case "img3" : fileLevel = 3; break;
	                  }
	                  
	                  temp.setFileLevel(fileLevel);
	                  
	                  // fList에 추가
	                  fList.add(temp);
	               }
	            } // end while
				
	            
	            // 3. 파일정보를 제외한 게시글 정보를 얻어 저장하기
	            String travelTitle = multiRequest.getParameter("travelTitle");
	            String travelContent = multiRequest.getParameter("travelContent");
	            String travelLocation = multiRequest.getParameter("travelLocation");
	            
	            // 세션에서 로그인한 회원의 번호 얻어오기
	            Member loginMember
	               = (Member)request.getSession().getAttribute("loginMember");
	            
	            int boardWriter = loginMember.getMemberNo();
	            
	            // Map 객체를 생성하여 얻어온 정보들을 모두 저장
	            Map<String, Object> map = new HashMap<String, Object>();
	            map.put("fList", fList);
	            map.put("travelTitle", travelTitle);
	            map.put("travelContent", travelContent);
	            map.put("travelLocation", travelLocation);
	            map.put("boardWriter", boardWriter);
	            
	            // 4. 게시글 등록 비즈니스 로직 수행 후 결과 반환받기
	            int result = service.insertBoard(map);
	            
	            if(result > 0) { // DB 삽입 성공 시 result에는 삽입한 글 번호가 저장되어있음
	               swalIcon = "success";
	               swalTitle = "게시글 등록 성공";
	               path = "localView.do?cp=1&no=" + result;
	               
	            } else {
	               swalIcon = "error";
	               swalTitle = "게시글 등록 실패";
	               path = "localList.do"; // 게시글 목록
	            }
	            
	            request.getSession().setAttribute("swalIcon", swalIcon);
	            request.getSession().setAttribute("swalTitle", swalTitle);
	            response.sendRedirect(path);
			}
			
			
			// 지역정보 게시글 수정 화면 전환 Controller ******************************************
			else if(command.equals("/updateForm.do")) {
				 
	            errorMsg = "게시글 수정 화면 전환 과정에서 오류 발생";
	            
	            // 수정화면이 미리 이전 내용을 작성 될 수 있게
	            // 글 번호를 이용하여 이전 내용을 조회해옴
	            
	            int travelNo = Integer.parseInt(request.getParameter("no"));
	            
	            Travel travel = service.updateView(travelNo);
				
				// 조회 결과에 따른 view 연결 처리
				if(travel != null) { // 조회 성공
					
					List<TravelAttachment> fList = service.selectBoardFiles(travelNo);
					
					if(!fList.isEmpty()) {	// 해당 게시글 이미지 정보가 DA에 있을 경우
						request.setAttribute("fList", fList);
					}
					
					path = "/WEB-INF/views/travel/localInfo/localUpdate.jsp";
					request.setAttribute("travel", travel);
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
				} else { // 조회 실패 했을 때
					request.getSession().setAttribute("swalIcon", "error");
					request.getSession().setAttribute("swalTitle", "지역정보글 상세 조회 실패");
					response.sendRedirect(request.getHeader("referer"));
				}
			}

			// 게시글 수정 Controller **********************************************************
			else if(command.equals("/localUpdate.do")) {
	            
	            errorMsg = "게시글 수정 과정에서 오류 발생";
	            
	            // 1. MultipartRequest 객체 생성에 필요한 값 설정
	            int maxSize = 20 * 1024 * 1024; // 최대 크기 20MB
	            String root = request.getSession().getServletContext().getRealPath("/");
	            String filePath = root + "resources/uploadImages/travel/";
	            
	            // 2. MultipartRequest 객체 생성
	            // -> 생성과 동시에 전달받은 파일이 서버에 저장됨
	            MultipartRequest mRequest 
	               = new MultipartRequest(request, filePath, maxSize, "UTF-8", new MyFileRenamePolicy());
	            
	            // 3. 파일 정보를 제외한 파라미터 얻어오기
	            String travelTitle = mRequest.getParameter("travelTitle");
	            String travelContent = mRequest.getParameter("travelContent");
	            String travelLocation = mRequest.getParameter("travelLocation");
	            int travelNo = Integer.parseInt(mRequest.getParameter("no"));
	            
	            // 4. 전달 받은 파일 정보를 List에 저장
	            List<TravelAttachment> fList = new ArrayList<TravelAttachment>();
	            
	            Enumeration<String> files = mRequest.getFileNames();
	            // input type="file"인 모든 요소의 name 속성값을 반환 받아 files에 저장
	            
	            while(files.hasMoreElements()) {
	               
	               // 현재 접근중인 name 속성 값을 변수에 저장
	               String name = files.nextElement();
	               
	               // 현재 name 속성이 일치하는 요소로 업로드된 파일이 있다면
	               if(mRequest.getFilesystemName(name) != null) {
	                  
	            	   TravelAttachment temp = new TravelAttachment();
	                  
	                  // 변경된 파일 이름 temp에 저장
	                  temp.setFileName(mRequest.getFilesystemName(name));
	                  
	                  // 지정한 파일 경로 temp에 저장
	                  temp.setFilePath(filePath);
	                  
	                  // 해당 게시글 번호 temp에 저장
	                  temp.setParentBoardNo(travelNo);
	                  
	                  // 파일 레벨 temp에 저장
	                  switch(name) {
	                  case "img0" : temp.setFileLevel(0); break;
	                  case "img1" : temp.setFileLevel(1); break;
	                  case "img2" : temp.setFileLevel(2); break;
	                  case "img3" : temp.setFileLevel(3); break;
	                  }
	                  
	                  // temp를 fList에 추가
	                  fList.add(temp);
	               }
	            }
	            
	            // 5. Session에서 로그인한 회원의 번호를 얻어와 저장
	            int memNo
	               = ((Member)request.getSession().getAttribute("loginMember")).getMemberNo();
	            
	            // 6. 준비된 값들을 하나의 Map에 저장
	            Map<String, Object> map = new HashMap<String, Object>();
	            map.put("travelTitle", travelTitle);
	            map.put("travelContent", travelContent);
	            map.put("travelLocation", travelLocation);
	            map.put("travelNo", travelNo);
	            map.put("fList", fList);
	            map.put("memNo", memNo);
	            
	            // 7. 준비된 값을 매개변수로 하여 게시글 수정 Service 호출
	            int result = service.updateTravel(map);
	            
	            // 8. result 값에 따라 View 연결 처리
	            path = "localView.do?cp=" + cp + "&no=" + travelNo;
	            
	            
	            String sk = mRequest.getParameter("sk");
	            String sv = mRequest.getParameter("sv");
	            
	            if(sk != null && sv !=null) {
	               path += "&sk=" + sk + "&sv=" + sv;
	            }

	            
	            if(result > 0) {
	               swalIcon = "success";
	               swalTitle = "게시글 수정 성공";
	            }else {
	               swalIcon = "error";
	               swalTitle = "게시글 수정 실패";
	            }
	            
	            request.getSession().setAttribute("swalIcon", swalIcon);
	            request.getSession().setAttribute("swalTitle", swalTitle);
	            
	            response.sendRedirect(path);
	         }
			
			// 게시글 삭제 Controller ***************************************************************
			else if(command.equals("/delete.do")) {
	        	 // 게시글 번호 얻어오기
	        	 int travelNo = Integer.parseInt(request.getParameter("no"));
	        	 
	        	 // 게시글 삭제(게시글 상태 -> 'N') 비즈니스 로직 수행 후 결과 반환
	        	 int result = service.updateBoardFl(travelNo);
	        	 
	        	 // 비즈닌스 로직 결과에 따라 
	        	 // "게시글 삭제 성공" / "게시글 삭제 실패" 메세지를 전달
	        	 if(result>0) { // 삭제 성공 시 : 게시글 목록 redirect
	        		 swalIcon = "success";
	        		 swalTitle = "게시글이 삭제되었습니다.";
	        		 path = "localList.do?cp=1";
	        	 }else { // 삭제 실패 시 : 삭제 시도한 게시글 상세조회 페이지 redirect 
	        		 swalIcon = "error";
	        		 swalTitle = "게시글 삭제 실패";
	        		 path = request.getHeader("referer");
	        		 // 요청을 보냈던 이전 주소(상세페이지)로 이동
	        	 }
	        	 
	        	 request.getSession().setAttribute("swalIcon", swalIcon);
	        	 request.getSession().setAttribute("swalTitle", swalTitle);
	        	 
	        	 response.sendRedirect(path);
	         }
			
			
			
			
			
			
			
			
			

			
			// ***********************************************************************
			// 관광지 리스트 Controller **************************************************
			else if (command.equals("/sightsList.do")) {
				errorMsg = "관광지 리스트 조회 중 오류 발생.";

				// 요청을 위임할 경로 jsp 경로 지정
				path = "/WEB-INF/views/travel/sights/sightsList.jsp";

				// 요청 위임 객체 생성 후 위임 진행
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			// 관광지 글쓰기 Controller **************************************************
			else if (command.equals("/sightsInsert.do")) {
				errorMsg = "관광지 글쓰기 중 오류 발생.";
				
				// 요청을 위임할 경로 jsp 경로 지정
				path = "/WEB-INF/views/travel/sights/sightsInsert.jsp";
				
				// 요청 위임 객체 생성 후 위임 진행
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
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
