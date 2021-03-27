package com.kh.semi.hospital.controller;

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

import com.kh.semi.common.MyFileRenamePolicy;
import com.kh.semi.hospital.model.service.HospitalService;
import com.kh.semi.hospital.model.vo.Attachment;
import com.kh.semi.hospital.model.vo.Hospital;
import com.kh.semi.hospital.model.vo.PageInfo;
import com.kh.semi.member.model.vo.Member;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/hospital/*")
public class HospitalController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri = request.getRequestURI(); //전체 요청 주소
		
		String contextPath = request.getContextPath();
		
		String command = uri.substring((contextPath+"/hospital").length());
		
		// 컨트롤러 내에서 공용으로 사용할 변수 미리 선언
		
		String path = null; // forward 또는 redirect 경로를 저장할 변수
		RequestDispatcher view = null; // 요청 위임 객체
		
		// sweet alert 메세지 전달하는 용도
		String swalIcon = null;
		String swalTitle = null;
		String swalText = null;
		String errorMsg = null;
		
		try {
			HospitalService service = new HospitalService();
			
			// 현재 페이지를 얻어와 커리스트링으로 사용할 것.
			// 쿼리스트링은 서버측에서 파라미터로 인식된다.
			String cp = request.getParameter("cp");
			// CurrentPage 사용 이유
			// 1) 페이징 처리 시 계산에 필요한 값이기 때문
			// 2) 효율적인 UI/UX를 제공하기 위해서 사용한다(상세조회/북마크...)
		    // 처음엔 얻어 올 값이 없어서 null 이 된다.
			
			
			
			// 동물병원 목록 조회**************************************
			if(command.equals("/list")) {
				
				errorMsg = "동물병원 목록 조회 중 오류 발생";
								
				
				// 1) 페이징 처리를 위한 값 계산 Service호출
				PageInfo pInfo = service.getPageInfo(cp);
				
				
				// 2) 동물병원 목록 조회 비즈니스 로직 수행
				List<Hospital> hList = service.selectHospitalList(pInfo);
				// pInfo를 가져가는 이유 = 
				// pInfo에 담겨져있는 currentPage와 limit를 이용해 현재 페이지에 맞는 게시글 목록을 조회하기 위해
				
				
				
				// 썸네일 추가
				
				if(hList!=null) {
					
					// 조회된 동물병원이 있다면 썸네일이 있는지 확인해보고 있으면 출력한다.
					List<Attachment> fList = service.selectThumbnailList(pInfo);
					
					if(!fList.isEmpty()) {
						request.setAttribute("fList", fList);
					}
				}
				
				path = "/WEB-INF/views/hospital/hospitalList.jsp";
				
				request.setAttribute("hList", hList);
				request.setAttribute("pInfo", pInfo);
				
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
			
			
			// 동물병원 상세조회 Controller ******************************
			else if(command.equals("/view")) {
				errorMsg = "동물병원 상세 조회 과정에서 오류 발생";
				
				int hospitalNo = Integer.parseInt(request.getParameter("hospitalNo"));
				
				// 상세조회 비즈니스 로직 수행 후 결과 반환받기
				Hospital hospital = service.selectHospital(hospitalNo);
				
				if(hospital!=null) { // 상세조회 성공 시
					
					// 해당 게시글에 포함된 이미지 파일 목록 조회 서비스 호출
					List<Attachment> fList = service.selectHospitalFiles(hospitalNo);
					
					if(!fList.isEmpty()) { // 해당 동물병원 이미지 정보가 DB에 있을 경우
						request.setAttribute("fList", fList);
					}
															
					
					path ="/WEB-INF/views/hospital/hospitalView.jsp";
					request.setAttribute("hospital", hospital);
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
				}else {
					request.getSession().setAttribute("swalIcon", "error");
					request.getSession().setAttribute("swalTitle", "동물병원 상세 조회 실패");
					response.sendRedirect("list");
				}
				
			}
			
			
			
			
			
			// 동물병원 등록 화면 전환 **************************************
				else if(command.equals("/insertForm")) {
					path = "/WEB-INF/views/hospital/hospitalInsert.jsp";
					view = request.getRequestDispatcher(path);
					view.forward(request, response);
				}
			
			
			
			
			
			// 동물병원 등록 **************************************
				else if(command.equals("/insert")) {
					errorMsg ="동물병원 등록 과정에서 오류 발생";
					
					// form태그에서 encType이 multipart/form-data형식이면
					// 기존에 사용하던 request  객체로 파라미터를 얻어올 수 없다.
					
					// 1. MultipartRequest 객체 생성하기
					// 1-1. 전송 파일 용량 지정(byte단위)
					int maxSize = 20* 1024 * 1024; // 20MB
					
					// 1-2. 서버에 업로드된 파일을 저장할 경로 지정
					String root = request.getSession().getServletContext().getRealPath("/");
					
					String filePath = root + "resources/image/uploadHospitalImages/";
					
					// 1-3. 파일명 변환을 위한 클래스 작성 (Attachment.java)
					
					// 1-4. MultipartRequest 객체 생성 (객체 생성과 동시에 파라미터로 넘어온 내용 중 파일이 서버에 바로 저장됨)
					MultipartRequest multiRequest = new MultipartRequest(request, filePath, maxSize, "UTF-8", new MyFileRenamePolicy());
					
					
					// 2. 생성한 객체에서 파일 정보만을 얻어와 별도의 List에 모두 저장
					
					// 2-1. 파일 정보를 모두 저장할 List 객체 생성
					List<Attachment> fList = new ArrayList<Attachment>();
					
					// 2-2. 업로드된 파일의 name 모두 받기
					Enumeration<String> files = multiRequest.getFileNames();
					
					
					// 2-3. 얻어온 Enumeration 객체에 요소를 하나씩 반복 접근, 
					// 업로드된 파일 정보를 Attachment객체에 저장 후 iList에 추가
					
					while(files.hasMoreElements()) { //다음 요소가 있다면
						String name = files.nextElement(); //img0
						
						// 제출받은 file태그 요소 중 업로드된 파일이 있을 경우
						if(multiRequest.getFilesystemName(name)!=null) {
							// Attachment 객체에 파일 정보 저장
							Attachment temp = new Attachment();
							
							temp.setFileName(multiRequest.getFilesystemName(name));
							temp.setFilePath(filePath);
							

							// name 속성에 따라 fileLevel 지정
							int fileLevel =0;
							
							switch(name) {
							case "img0" : fileLevel =0; break;
							case "img1" : fileLevel =1; break;
							case "img2" : fileLevel =2; break;
							case "img3" : fileLevel =3; break;
							case "img4" : fileLevel =4; break;
							case "img5" : fileLevel =5; break;
							
							}
							temp.setFileLevel(fileLevel);
							
							// fList에 추가
							fList.add(temp);
						}
					}// end while
					
					
					// 3.파일정보를 제외한 게시글 정보를 얻어와 저장하기
					String location1 = multiRequest.getParameter("location1"); //지역1
					
					String hospNm = multiRequest.getParameter("hospNm"); // 병원명
					
					String phone1 = multiRequest.getParameter("phone1"); // 전화번호
					String phone2 = multiRequest.getParameter("phone2"); // 전화번호
					String phone3 = multiRequest.getParameter("phone3"); // 전화번호
					String phone = phone1+"-" + phone2 + "-" + phone3; // 전화번호 합치기
					
					
			
					String location2 = multiRequest.getParameter("location2"); //상세주소
					
					
					String openTime = multiRequest.getParameter("openTime"); //오픈시간
					String closeTime = multiRequest.getParameter("closeTime"); //오픈시간
					
					
					
					
					String[] facilityArr = multiRequest.getParameterValues("hosp_facility"); // 병원시설 배열로 받기
					String facility = null;
					
				
					
					if(facilityArr!=null) { //병원 시설 배열이 비어있지 않다면.
						facility= String.join(",", facilityArr);
					}
					
					
					
					
					String hospitalInfo = multiRequest.getParameter("hospital_info");
					
					// 세션에서 로그인한 회원의 번호를 얻어옴
					Member loginMember = (Member)request.getSession().getAttribute("loginMember");
					int memberNo = loginMember.getMemberNo();
					
					// 얻어온 변수들을 모두 저장할 Map  생성
					Map<String,Object> map = new HashMap<String,Object>();
					
					map.put("fList",fList);
					map.put("location1", location1);
					map.put("hospNm", hospNm);
					map.put("phone", phone);
					map.put("location2", location2);
					map.put("openTime", openTime);
					map.put("closeTime", closeTime);
					map.put("facility", facility);
					map.put("hospitalInfo", hospitalInfo);
					map.put("memberNo", memberNo);
					
					
					
					// 4. 게시글 등록 비즈니스 로직 수행 후 결과 반환받기
					int result = service.insertHospital(map);
					
					if(result>0) {// DB에 데이터 등록 성공하면 result에 병원번호가 저장되어 있다.
						swalIcon = "success";
						swalTitle = "병원 등록 완료";
						path = "view?cp=1&hospitalNo="+result;
					} else {
						swalIcon = "error";
						swalTitle ="병원 등록 실패";
						path = "list";
					}
					
					request.getSession().setAttribute("swalIcon", swalIcon);
					request.getSession().setAttribute("swalTitle", swalTitle);
					response.sendRedirect(path);
					
				}
			
			// 동물병원 수정 화면 전환 Controller ************
				else if(command.equals("/updateForm")) {
					errorMsg ="동물병원 수정 화면 불러오는 과정에서 오류 발생";
					
					// 병원번호를 이용하여 이전에 작성했던 내용 조회해 옴
					int hospitalNo = Integer.parseInt(request.getParameter("hospitalNo"));
					Hospital hospital = service.updateView(hospitalNo);
					
					
					if(hospital!=null) {
						// 첨부된 파일을 가져 옴
						List<Attachment> fList = service.selectHospitalFiles(hospitalNo);
						
						if(!fList.isEmpty()) {
							request.setAttribute("fList", fList);
						}
						
						path = "/WEB-INF/views/hospital/hospitalUpdate.jsp";
						request.setAttribute("hospital", hospital);
						view = request.getRequestDispatcher(path);
						view.forward(request, response);
						
					}else {
						request.getSession().setAttribute("swalIcon", "error");
						request.getSession().setAttribute("swalTitle", "동물 병원 수정 화면 전환에 실패했습니다.");
						response.sendRedirect(request.getHeader("referer"));
					}
					
					
				}
			
				// 동물병원 수정하기 **********************************
				
				else if(command.equals("/update")) {
					errorMsg = "동물병원 수정 과정에서 오류 발생";
					
					// 1. MultipartRequest 객체 생성에 필요한 값 설정
		        	 int maxSize = 20 * 1024 * 1024; // 최대 크기 20MB
		        	 String root = request.getSession().getServletContext().getRealPath("/");
		        	 String filePath = root + "resources/image/uploadHospitalImages/";
		        	 
		        	 // 2. MultipartRequest 객체 생성
		        	 // -> 생성과 동시에 전달받은 파일이 서버에 저장됨
		        	 MultipartRequest multiRequest = new MultipartRequest(request, filePath, maxSize, "UTF-8", new MyFileRenamePolicy());
		        	 
		        	 // 3. 파일 정보를 제외한 파라미터 얻어오기
					String location1 = multiRequest.getParameter("location1"); //지역1
					String hospNm = multiRequest.getParameter("hospNm"); // 병원명
					String phone1 = multiRequest.getParameter("phone1"); // 전화번호
					String phone2 = multiRequest.getParameter("phone2"); // 전화번호
					String phone3 = multiRequest.getParameter("phone3"); // 전화번호
					String phone = phone1+"-" + phone2 + "-" + phone3; // 전화번호 합치기
						
					String location2 = multiRequest.getParameter("location2"); //상세주소
					String openTime = multiRequest.getParameter("openTime"); //오픈시간
					String closeTime = multiRequest.getParameter("closeTime"); //오픈시간
						
					String[] facilityArr = multiRequest.getParameterValues("hosp_facility"); // 병원시설 배열로 받기
					String facility = null;
						
						if(facilityArr!=null) { //병원 시설 배열이 비어있지 않다면.
							facility= String.join(",", facilityArr);
						}
						
					String hospitalInfo = multiRequest.getParameter("hospital_info");
					
					int hospitalNo = Integer.parseInt(multiRequest.getParameter("hospitalNo"));
					
					
		        	 
		        	 
					// 4. 전달받은 파일 정보를 List에 저장
					List<Attachment> fList = new ArrayList<Attachment>();
					Enumeration<String> files = multiRequest.getFileNames();
					// input type="file"인 모든 요소의 name 속성 값을 반환받아 files에 저장
					
					while(files.hasMoreElements()) {
						// 현재 접근중인 name속성값읇 변수에 저장
						 String name = files.nextElement();
			        		
		        		 // 현재 name 속성이 일치하는 요소로 업로드된 파일이 있다면
		        		 if(multiRequest.getFilesystemName(name) != null) {
		        			 
	        			 Attachment temp = new Attachment();
	        			 
	        			 // 변경된 파일 이름 temp에 저장
	        			 temp.setFileName(multiRequest.getFilesystemName(name));
	        			 
	        			 // 지정한 파일 경로 tmep에 저장
	        			 temp.setFilePath(filePath);
	        			 
	        			 // 해당 게시글 번호 temp에 저장
	        			 temp.setHospNo(hospitalNo);
	        			 
	        			 // 파일 레벨 temp에 저장
	        			 switch(name) {
	        			 case "img0" : temp.setFileLevel(0); break;
	        			 case "img1" : temp.setFileLevel(1); break;
	        			 case "img2" : temp.setFileLevel(2); break;
	        			 case "img3" : temp.setFileLevel(3); break;
	        			 case "img4" : temp.setFileLevel(4); break;
	        			 case "img5" : temp.setFileLevel(5); break;
	        			 }
	        			 
	        			 // temp를 fList에 추가
	        			 fList.add(temp);
	        			// 이미지를 변경한 부분들만 fList에 추가가 된다.
	        		 }
						
					}
					
					// 5. Session에서 로그인한 회원의 번호를 얻어와 저장 (작성자)
					Member loginMember = (Member)request.getSession().getAttribute("loginMember");
					int memberNo = loginMember.getMemberNo(); 
					
					// 6. 준비된 값들을 하나의 Map에 저장
					Map<String,Object> map = new HashMap<String,Object>();
					
					map.put("fList",fList);
					map.put("hospitalNo",hospitalNo);
					map.put("location1", location1);
					map.put("hospNm", hospNm);
					map.put("phone", phone);
					map.put("location2", location2);
					map.put("openTime", openTime);
					map.put("closeTime", closeTime);
					map.put("facility", facility);
					map.put("hospitalInfo", hospitalInfo);
					map.put("memberNo", memberNo);
					
					
					// 7. 준비된 값을 매개변수로 하여 게시글 수정 Service 호출
		        	 int result = service.updateHospital(map);
		        	 
		        	// 8. result 값에 따라 View 연결 처리
		        	 path ="view?cp="+cp+"&hospitalNo="+hospitalNo;
		        	 String sk = multiRequest.getParameter("sk");
		        	 String sv = multiRequest.getParameter("sv");
					
		        	// 전달된 sk,sv가 존재 할 때 (검색을 통한 접근일 때)
		        	 if(sk != null && sv != null ) {
		        		 path += "&sk="+sk + "&sv=" + sv;
		        	 }
		        	 
		        	 

		        	 if (result>0) {
		        		 swalIcon = "success";
		        		 swalTitle = "수정 성공";
		        		 
		        		 
		        		 	        	 
		        	 }else {
		        		 swalIcon = "error";
		        		 swalTitle = "수정 실패";
		        	 }
		        	 request.getSession().setAttribute("swalIcon", swalIcon);
		        	 request.getSession().setAttribute("swalTitle", swalTitle);
		        	 
		        	 response.sendRedirect(path);
		        	 
					
					
					
				}
			
				// 동물병원 삭제 **************************************
			
				else if(command.equals("/delete")) {
					errorMsg = "삭제 과정에서 오류 발생";
				
					// 병원번호 얻어오기
					
					int hospitalNo = Integer.parseInt(request.getParameter("hospitalNo"));
					// 병원 삭제 (DELETE_FL -> 'Y'로 변경)
					int result = service.deleteHospital(hospitalNo);
					
					
					if(result>0) {
						swalIcon = "success";
		        		 swalTitle="삭제 성공";
		        		 
		        		 path = "list";
					}else {
						 swalIcon = "error";
		        		 swalTitle = "삭제 실패";
		        		 
		        		 path = request.getHeader("referer");
					}
					 request.getSession().setAttribute("swalIcon", swalIcon);
		        	 request.getSession().setAttribute("swalTitle", swalTitle);
		        	 
		        	 response.sendRedirect(path);
					
				}
			
			
			
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
			path = "/WEB-INF/views/common/errorPage.jsp";
			
			// 에러 메세지를 request객체에 담는다
			request.setAttribute("errorMsg", errorMsg);
			view = request.getRequestDispatcher(path);
			view.forward(request, response);
			
		}
		
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
