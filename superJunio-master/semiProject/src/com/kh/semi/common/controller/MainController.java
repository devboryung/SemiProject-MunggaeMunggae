package com.kh.semi.common.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.semi.common.model.service.MainService;
import com.kh.semi.room.model.vo.Attachment;
import com.kh.semi.room.model.vo.PageInfo;
import com.kh.semi.room.model.vo.Room;

/**
 * Servlet implementation class MainController
 */
@WebServlet("/main")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String path = null;
		RequestDispatcher view = null;

		String errorMsg = null;
		
		try {
			
			
			MainService service = new MainService();
			
			
			// 숙소 목록 가져오기 (조회수 상위 3개)
			List<Room> roomList = service.roomList();
			
			// 숙소 썸네일 가져오기
			if(roomList!=null) {
				List<Attachment> fList = service.selectThumbnailList();
				request.setAttribute("fList", fList);
			}
			
			request.setAttribute("roomList",roomList);
			
			
		
	
		request.getRequestDispatcher("index.jsp").forward(request, response);
		
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
