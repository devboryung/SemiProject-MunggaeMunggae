package com.kh.semi.reply2.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kh.semi.reply2.model.service.ReplyService;
import com.kh.semi.reply2.model.vo.Reply;


@WebServlet("/reply21/*")
public class ReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = uri.substring((contextPath+"/reply21").length());

		try {
			ReplyService service = new ReplyService();
			
			if(command.equals("/selectList.do")) {
				
				int parentBoardNo = Integer.parseInt(request.getParameter("parentBoardNo"));
				
				List<Reply> rList = service.selectList(parentBoardNo);
				
				// gson에 맞게 날짜 형식 변경
				Gson gson = new GsonBuilder().setDateFormat("yyyy년 MM월 dd일 HH:mm").create();
				
				// json 형태로 전송
				gson.toJson(rList, response.getWriter());
			}
			
			// -------------댓글 작성 Controller ----------------------------
			else if(command.equals("/insertReply.do")) {
				
				String replyWriter = request.getParameter("replyWriter");
				int parentBoardNo = Integer.parseInt(request.getParameter("parentBoardNo"));
				String replyContent = request.getParameter("replyContent");
				
				Reply reply = new Reply();
				reply.setMemberId(replyWriter);	// 회원번호 저장됨
				reply.setReplyContent(replyContent);
				reply.setParentBoardNo(parentBoardNo);
				
				int result = service.insertReply(reply);

				response.getWriter().print(result);
			}
			
			// ---------댓글 수정 Controller -------------
			else if(command.equals("/updateReply.do")) {
				
				// 파라미터(댓글 번호, 댓글 내용) 얻어오기
				int replyNo = Integer.parseInt(request.getParameter("replyNo"));
				String replyContent = request.getParameter("replyContent");
				
				Reply reply = new Reply();
				reply.setReplyNo(replyNo);
				reply.setReplyContent(replyContent);
				
				int result = service.updateReply(reply);
				
				response.getWriter().print(result);
			}

			// ---------- 댓글 삭제 Controller --------------
			else if(command.equals("/deleteReply.do")) {
				
				int replyNo = Integer.parseInt(request.getParameter("replyNo"));
				
				int result = service.updateReplyStatus(replyNo);
				
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
