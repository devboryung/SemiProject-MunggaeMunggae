package com.kh.semi.CSCenter.model.dao;

import static com.kh.semi.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.semi.CSCenter.model.vo.Faq;
import com.kh.semi.CSCenter.model.vo.NoticeReply;
import com.kh.semi.CSCenter.model.vo.Qna;
import com.kh.semi.CSCenter.model.vo.QnaReply;

public class CenterReplyDAO {

	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;

	private Properties prop;

	public CenterReplyDAO() {
		String fileName = CenterReplyDAO.class.getResource("/com/kh/semi/sql/center/centerReply-query.xml").getPath();

		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Qna 댓글 목록 조회 DAO
	 * 
	 * @param conn
	 * @param parentQnaNo
	 * @return qrList
	 * @throws Exception
	 */
	public List<QnaReply> selectQnaList(Connection conn, int parentQnaNo) throws Exception {

		List<QnaReply> qrList = null;

		String query = prop.getProperty("selectQnaList");

		try {

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, parentQnaNo);

			rset = pstmt.executeQuery();

			qrList = new ArrayList<QnaReply>();

			while (rset.next()) {

				QnaReply reply = new QnaReply();
				reply.setQnaReplyNo(rset.getInt("QNA_REPLY_NO"));
				reply.setQnaReplyContent(rset.getString("QNA_REPLY_CONTENT"));
				reply.setQnaReplyCreateDt(rset.getTimestamp("QNA_REPLY_CREATE_DT"));
				reply.setMemNickName(rset.getString("NICKNAME"));

				qrList.add(reply);
			}

		} finally {

			close(rset);
			close(pstmt);

		}

		return qrList;
	}

	/**
	 * 댓글 삽입 DAO
	 * 
	 * @param conn
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int insertQnaReply(Connection conn, QnaReply reply) throws Exception {

		int result = 0;

		String query = prop.getProperty("insertQnaReply");

		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, reply.getQnaReplyContent());
			pstmt.setInt(2, reply.getParentQnaNo());
			pstmt.setInt(3, reply.getMemNo());

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}

		return result;
	}

	/**
	 * 답변처리 DAO
	 * 
	 * @param conn
	 * @param replyResponse
	 * @param parentQnaNo
	 * @return responResult
	 * @throws Exception
	 */
	public int replyResponse(Connection conn, String replyResponse, int parentQnaNo) throws Exception {

		int responResult = 0;

		String query = prop.getProperty("replyResponse");

		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, replyResponse);
			pstmt.setInt(2, parentQnaNo);

			responResult = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}

		return responResult;
	}

	/**
	 * 댓글 수정 DAO
	 * 
	 * @param conn
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int updateQnaReply(Connection conn, QnaReply reply) throws Exception {

		int result = 0;

		String query = prop.getProperty("updateQnaReply");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, reply.getQnaReplyContent());
			pstmt.setInt(2, reply.getQnaReplyNo());

			result = pstmt.executeUpdate();

		} finally {

			close(pstmt);
		}

		return result;
	}

	/**
	 * 댓글 상태 (삭제)변경 DAO
	 * 
	 * @param conn
	 * @param replyNo
	 * @param replyResponse
	 * @return result
	 * @throws Exception
	 */
	public int deleteQnaReply(Connection conn, int replyNo) throws Exception {

		int result = 0;

		String query = prop.getProperty("deleteQnaReply");

		try {

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, replyNo);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}

		return result;
	}

	/**
	 * 답변 상태 확인
	 * 
	 * @param conn
	 * @param parentQnaNo
	 * @return qna
	 * @throws Exception
	 */
	public String replyCheck(Connection conn, int parentQnaNo) throws Exception {

		String qna = null;

		String query = prop.getProperty("replyCheck");

		try {

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, parentQnaNo);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				qna = rset.getString("QNA_REPLY_RESPONSE");

			}

		} finally {
			close(rset);
			close(pstmt);

		}

		return qna;
	}

	/**
	 * Notice 댓글 목록 조회 DAO
	 * 
	 * @param conn
	 * @param parentNoticeNo
	 * @return rList
	 * @throws Exception
	 */
	public List<NoticeReply> selectNoticeList(Connection conn, int parentNoticeNo) throws Exception {

		List<NoticeReply> rList = null;

		String query = prop.getProperty("selectNoticeList");

		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, parentNoticeNo);

			rset = pstmt.executeQuery();

			rList = new ArrayList<NoticeReply>();

			while (rset.next()) {

				NoticeReply reply = new NoticeReply();
				reply.setNoticeRepNo(rset.getInt("NOTICE_REPLY_NO"));
				reply.setNoticeRepContent(rset.getString("NOTICE_REPLY_CONTENT"));
				reply.setNoticeRepCreateDt(rset.getTimestamp("NOTICE_REPLY_CREATE_DT"));
				reply.setNoticeRepWriterNo(rset.getInt("NOTICE_WRITER_NO"));
				reply.setMemNickName(rset.getString("NICKNAME"));
				rList.add(reply);
			}

		} finally {

			close(rset);
			close(pstmt);

		}

		return rList;
	}

	/**
	 * Notice 댓글 삽입 DAO
	 * 
	 * @param conn
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int insertNoticeReply(Connection conn, NoticeReply reply) throws Exception {

		int result = 0;

		String query = prop.getProperty("insertNoticeReply");

		try {

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, reply.getNoticeRepContent());
			pstmt.setInt(2, reply.getNoticeNo());
			pstmt.setInt(3, reply.getNoticeRepWriterNo());

			result = pstmt.executeUpdate();
		} finally {

			close(pstmt);

		}

		return result;

	}

	/**
	 * 공지사항 댓글 수정 DAO
	 * 
	 * @param conn
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int updateNoticeReply(Connection conn, NoticeReply reply) throws Exception {

		int result = 0;

		String query = prop.getProperty("updateNoticeReply");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, reply.getNoticeRepContent());
			pstmt.setInt(2, reply.getNoticeRepNo());

			result = pstmt.executeUpdate();

		} finally {

			close(pstmt);
		}

		return result;
	}

	/**
	 * Notie 댓글 삭제 변경 DAO
	 * 
	 * @param conn
	 * @param replyNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteNoticeReply(Connection conn, int replyNo) throws Exception {

		int result = 0;

		String query = prop.getProperty("deleteNoticeReply");

		try {

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, replyNo);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}

		return result;
	}

	/**
	 * 댓글 수 조회하기 DAO
	 * 
	 * @param conn
	 * @param parentNoticeNo
	 * @return
	 * @throws Exception
	 *//*
		 * public int getNoticeListCount(Connection conn, int parentNoticeNo) throws
		 * Exception {
		 * 
		 * int replyCount = 0;
		 * 
		 * String query = prop.getProperty("replyCount");
		 * 
		 * try {
		 * 
		 * pstmt = conn.prepareStatement(query);
		 * 
		 * pstmt.setInt(1, parentNoticeNo);
		 * 
		 * rset = pstmt.executeQuery();
		 * 
		 * 
		 * if(rset.next()) { replyCount = rset.getInt(1);
		 * 
		 * }
		 * 
		 * }finally { close(rset); close(pstmt);
		 * 
		 * }
		 * 
		 * 
		 * return replyCount; }
		 */

}
