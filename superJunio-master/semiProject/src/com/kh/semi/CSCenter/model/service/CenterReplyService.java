package com.kh.semi.CSCenter.model.service;

import static com.kh.semi.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.kh.semi.CSCenter.model.dao.CenterReplyDAO;
import com.kh.semi.CSCenter.model.vo.NoticeReply;
import com.kh.semi.CSCenter.model.vo.Qna;
import com.kh.semi.CSCenter.model.vo.QnaReply;

public class CenterReplyService {

	private CenterReplyDAO dao = new CenterReplyDAO();

	/**
	 * 댓글 조회 Service
	 * 
	 * @param parentQnaNo
	 * @return qrList
	 * @throws Exception
	 */
	public List<QnaReply> selectQnaList(int parentQnaNo) throws Exception {

		Connection conn = getConnection();

		List<QnaReply> rList = dao.selectQnaList(conn, parentQnaNo);

		close(conn);

		return rList;
	}

	/**
	 * 댓글 삽입 Service
	 * 
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int insertQnaReply(QnaReply reply) throws Exception {

		Connection conn = getConnection();

		String replyContent = reply.getQnaReplyContent();

		// 크로스 사이트 스크립팅 방지 처리
		replyContent = replaceParameter(replyContent);

		replyContent = replyContent.replaceAll("\n", "<br>");

		reply.setQnaReplyContent(replyContent);

		int result = dao.insertQnaReply(conn, reply);

		if (result > 0)
			commit(conn);
		else
			rollback(conn);

		close(conn);

		return result;
	}

	// 크로스 사이트 스크립트 방지 메소드
	private String replaceParameter(String param) {
		String result = param;
		if (param != null) {
			result = result.replaceAll("&", "&amp;");
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("\"", "&quot;");
		}

		return result;
	}

	/**
	 * 답변처리 완료 Service
	 * 
	 * @param replyResponse
	 * @param parentQnaNo
	 * @return responResult
	 * @throws Exception
	 */
	public int replyResponse(String replyResponse, int parentQnaNo) throws Exception {

		Connection conn = getConnection();

		int responResult = dao.replyResponse(conn, replyResponse, parentQnaNo);

		if (responResult > 0)
			commit(conn);
		else
			rollback(conn);

		close(conn);

		return responResult;
	}

	/**
	 * 댓글 수정 Service
	 * 
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int updateQnaReply(QnaReply reply) throws Exception {

		Connection conn = getConnection();

		String replyContent = reply.getQnaReplyContent();

		replyContent = replaceParameter(replyContent);

		replyContent = replyContent.replaceAll("\n", "<br>");

		reply.setQnaReplyContent(replyContent);

		int result = dao.updateQnaReply(conn, reply);

		if (result > 0)
			commit(conn);
		else
			rollback(conn);

		close(conn);

		return result;

	}

	/**
	 * 댓글 상태 (삭제)변경
	 * 
	 * @param replyNo
	 * @param replyResponse
	 * @return result
	 * @throws Exception
	 */
	public int deleteQnaReply(int replyNo) throws Exception {

		Connection conn = getConnection();
		int result = dao.deleteQnaReply(conn, replyNo);

		if (result > 0)
			commit(conn);
		else
			rollback(conn);

		close(conn);

		return result;

	}

	/**
	 * QNA 답변상태 확인
	 * 
	 * @param parentQnaNo
	 * @return result
	 * @throws Exception
	 */
	public String replyCheck(int parentQnaNo) throws Exception {

		Connection conn = getConnection();

		String replyCheck = dao.replyCheck(conn, parentQnaNo);

		close(conn);

		return replyCheck;
	}

	/**
	 * notice 댓글 조회
	 * 
	 * @param parentNoticeNo
	 * @return rList
	 * @throws Exception
	 */
	public List<NoticeReply> selectNotiList(int parentNoticeNo) throws Exception {

		Connection conn = getConnection();

		List<NoticeReply> rList = dao.selectNoticeList(conn, parentNoticeNo);

		close(conn);

		return rList;
	}

	/**
	 * Notice 댓글 삽입 Service
	 * 
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int insertNoticeReply(NoticeReply reply) throws Exception {

		Connection conn = getConnection();

		String replyContent = reply.getNoticeRepContent();

		replyContent = replaceParameter(replyContent);

		replyContent = replyContent.replaceAll("\n", "<br>");

		reply.setNoticeRepContent(replyContent);

		int result = dao.insertNoticeReply(conn, reply);

		if (result > 0)
			commit(conn);
		else
			rollback(conn);

		close(conn);

		return result;
	}

	/**
	 * 공지사항 댓글 수정 Service
	 * 
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int updateNoticeReply(NoticeReply reply) throws Exception {

		Connection conn = getConnection();

		String replyContent = reply.getNoticeRepContent();

		replyContent = replaceParameter(replyContent);

		replyContent = replyContent.replaceAll("\n", "<br>");

		reply.setNoticeRepContent(replyContent);

		int result = dao.updateNoticeReply(conn, reply);

		if (result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);

		return result;
	}

	/**
	 * Notice 댓글 삭제 Service
	 * 
	 * @param replyNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteNoticeReply(int replyNo) throws Exception {

		Connection conn = getConnection();

		int result = dao.deleteNoticeReply(conn, replyNo);

		if (result > 0)
			commit(conn);
		else
			rollback(conn);

		return result;
	}

	/*
	 * // Notice 댓글 수 조회
	 *//**
		 * @param parentNoticeNo
		 * @return
		 * @throws Exception
		 *//*
			 * public int getNoticeListCount(int parentNoticeNo) throws Exception {
			 * 
			 * 
			 * Connection conn = getConnection();
			 * 
			 * int replyCount = dao.getNoticeListCount(conn,parentNoticeNo);
			 * 
			 * 
			 * close(conn);
			 * 
			 * return replyCount; }
			 */

}
