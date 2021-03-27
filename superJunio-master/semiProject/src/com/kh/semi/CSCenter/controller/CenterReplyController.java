package com.kh.semi.CSCenter.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kh.semi.CSCenter.model.service.CenterReplyService;
import com.kh.semi.CSCenter.model.vo.NoticeReply;
import com.kh.semi.CSCenter.model.vo.Qna;
import com.kh.semi.CSCenter.model.vo.QnaReply;

@WebServlet("/centerReply/*")
public class CenterReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = uri.substring((contextPath+"/centerReply").length());
		
		try {
			
			CenterReplyService service = new CenterReplyService();

			
			//  Qna 댓글 목록 조회 
			if(command.equals("/selectQnaList.do")) {
				
				int parentQnaNo = Integer.parseInt(request.getParameter("parentQnaNo"));
				
				List<QnaReply> qrList = service.selectQnaList(parentQnaNo);
				
				Gson gson = new GsonBuilder().setDateFormat("yyyy년 MM월dd일 HH:mm").create();
				gson.toJson(qrList,response.getWriter());

			
			}
			
			
			// Qna 댓글 등록
			if(command.equals("/insertQnaReply.do")) {
				
				int replyWriter = Integer.parseInt(request.getParameter("replyWriter"));
				int parentQnaNo = Integer.parseInt(request.getParameter("parentQnaNo"));
				String replyContent = request.getParameter("replyContent");
				String replyResponse = request.getParameter("replyResponse"); // 답변처리 확인 파라미터
				
				QnaReply  reply = new QnaReply();

				reply.setMemNo(replyWriter); // 회원번호 저장됨
				reply.setQnaReplyContent(replyContent);
				reply.setParentQnaNo(parentQnaNo);
				
				
				
				int result = service.insertQnaReply(reply);
				
				
				if(result > 0) {
				
				 service.replyResponse(replyResponse,parentQnaNo);
				
				 
				 
				}
				
				response.getWriter().print(result);
				

			}
			
			else if(command.equals("/updateQnaReply.do")) {
				
			int qnaReplyNo = Integer.parseInt(request.getParameter("replyNo"));
			String replyContent =request.getParameter("replyContent");
			
			QnaReply reply = new QnaReply();

			
			reply.setQnaReplyNo(qnaReplyNo);
			reply.setQnaReplyContent(replyContent);
			
			
			int result = service.updateQnaReply(reply);
				
			response.getWriter().print(result);
				
				
			}
			
			
			// 댓글 삭제 controller *****************************
			else if(command.equals("/deleteQnaReply.do")) {
				
				int replyNo = Integer.parseInt(request.getParameter("replyNo"));
				String replyResponse = request.getParameter("replyResponse");
				int parentQnaNo = Integer.parseInt(request.getParameter("parentQnaNo"));
				
				
				int result = service.deleteQnaReply(replyNo);				
			
				if(result > 0) {
					
				   service.replyResponse(replyResponse,parentQnaNo);

					
					
				}
				response.getWriter().print(result);
				
			}
			
			
			else if(command.equals("/replyCheck.do")) {

				int parentQnaNo = Integer.parseInt(request.getParameter("parentQnaNo"));

				String replyCheck = service.replyCheck(parentQnaNo);				
				
				response.getWriter().print(replyCheck);

				
			
			}
			
	//		else if(command.equals("/NoticeListCount.do")) {
				
	//	    int parentNoticeNo = Integer.parseInt(request.getParameter("parentNoticeNo"));

				
	//		int NoticelistCount = service.getNoticeListCount(parentNoticeNo);

		    
	//		Gson gson = new GsonBuilder().setDateFormat("yyyy년 MM월dd일 HH:mm").create();
	//		gson.toJson(NoticelistCount,response.getWriter());
				
				
				
	//		}
			
			//Notice 댓글 수 조회
			else if(command.equals("/selectNoticeList.do")) {
				
			    int parentNoticeNo = Integer.parseInt(request.getParameter("parentNoticeNo"));

					
				 List<NoticeReply> rList = service.selectNotiList(parentNoticeNo);

			    
				Gson gson = new GsonBuilder().setDateFormat("yyyy년 MM월dd일 HH:mm").create();
				gson.toJson(rList,response.getWriter());
					
					
					
				}
			
			else if(command.equals("/insertNoticeReply.do")){

			// 오라클이 자동으로 숫자로 변환해줌
			int replyWriter = Integer.parseInt(request.getParameter("replyWriter"));
			int parentNoticeNo = Integer.parseInt(request.getParameter("parentNoticeNo"));
			String replyContent = request.getParameter("replyContent");
			
				
			
				
			NoticeReply reply = new NoticeReply();
			reply.setNoticeRepWriterNo(replyWriter);// 회원번호 저장됨
			reply.setNoticeRepContent(replyContent);
			reply.setNoticeNo(parentNoticeNo);
			
			
			
			int result = service.insertNoticeReply(reply);
				
			response.getWriter().print(result);

			
			}
			
			
			// notice 댓글 수정 
			else if(command.equals("/updateNoticeReply.do")) {
	
			int replyNo = Integer.parseInt(request.getParameter("replyNo"));
			String replyContent =request.getParameter("replyContent");

			
			NoticeReply reply = new NoticeReply();

			reply.setNoticeRepNo(replyNo);
			reply.setNoticeRepContent(replyContent);
	
			int result = service.updateNoticeReply(reply);

			response.getWriter().print(result);
			
			}
			 // Notice 댓글 삭제 
			else if(command.equals("/deleteNoticeReply.do")) {
				
				int replyNo = Integer.parseInt(request.getParameter("replyNo"));
				
				int result = service.deleteNoticeReply(replyNo);
				
				response.getWriter().print(result);
				
			}
			
			
			
		
		}catch(Exception e) {
			
		  e.printStackTrace();
	
		}

		
		
	
	
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
