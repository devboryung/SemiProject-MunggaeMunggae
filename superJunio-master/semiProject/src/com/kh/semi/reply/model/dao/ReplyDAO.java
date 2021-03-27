package com.kh.semi.reply.model.dao;

import static com.kh.semi.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.semi.reply.model.vo.Reply;

public class ReplyDAO {
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	
	private Properties prop;
	
	public ReplyDAO(){
		String fileName = ReplyDAO.class.getResource("/com/kh/semi/sql/reply/reply-query.xml").getPath();
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * @param conn
	 * @param parentBoardNo
	 * @return
	 * @throws Exception
	 */
	public List<Reply> selectList(Connection conn, int parentBoardNo) throws Exception{
		List<Reply> rList = null;
		
		String query = prop.getProperty("selectList");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, parentBoardNo);
			
			rset = pstmt.executeQuery();
			
			rList = new ArrayList<Reply>();
			
			while(rset.next()) {
				Reply reply = new Reply();
				reply.setReplyNo(rset.getInt("REPLY_NO"));
				reply.setReplyContent(rset.getString("REPLY_CONTENT"));
				reply.setReplyCreateDate(rset.getTimestamp("REPLY_DT"));
				reply.setMemberId(rset.getString("MEM_ID"));
				
				rList.add(reply);
			}
			
		}finally {
			close(rset);
			close(pstmt);
		}
	
		return rList;
	}




	/** 댓글 등록 DAO
	 * @param conn
	 * @param reply
	 * @return
	 * @throws Exception
	 */
	public int insertReply(Connection conn, Reply reply) throws Exception{
		
		int result = 0;
		
		String query = prop.getProperty("insertReply");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, reply.getReplyContent());
			pstmt.setString(2, reply.getMemberId());
			pstmt.setInt(3, reply.getParentBoardNo());
			
			result = pstmt.executeUpdate();
			
		}finally {
			
			close(pstmt);
		}
		
		return result;
		
		
	}




	/** 댓글 수정 DAO
	 * @param conn
	 * @param reply
	 * @return
	 * @throws Exception
	 */
	public int updateReply(Connection conn, Reply reply) throws Exception{
int result = 0;
		
		String query = prop.getProperty("updateReply");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, reply.getReplyContent());
			pstmt.setInt(2, reply.getReplyNo());
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
			
		}
		
		
		return result;
	}




	/** 댓글 삭제 DAO
	 * @param conn
	 * @param replyNo
	 * @return
	 * @throws Exception
	 */
	public int updateReplyStatus(Connection conn, int replyNo) throws Exception{

		int result = 0;
		
		String query = prop.getProperty("updateReplyStatus");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, replyNo);
			
			result = pstmt.executeUpdate();
			
		}finally {
			
			close(pstmt);
		}
		
		return result;
		
		
	}

}
