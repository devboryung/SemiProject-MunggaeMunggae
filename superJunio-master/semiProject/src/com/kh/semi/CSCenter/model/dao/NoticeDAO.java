package com.kh.semi.CSCenter.model.dao;
import static com.kh.semi.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.kh.semi.CSCenter.model.vo.Faq;
import com.kh.semi.CSCenter.model.vo.FaqAttachment;
import com.kh.semi.CSCenter.model.vo.Notice;
import com.kh.semi.CSCenter.model.vo.NoticeAttachment;
import com.kh.semi.CSCenter.model.vo.PageInfo;




public class NoticeDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	private Properties prop = null;
	

	public NoticeDAO(){
		String fileName = NoticeDAO.class.getResource("/com/kh/semi/sql/center/notice-query.xml").getPath();
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	

	/** 전체 게시글 수 반환 DAO
	 * @param conn
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn) throws Exception {
		
		int listCount = 0;
		
		String query =prop.getProperty("selectNotiListCount");
		
		try {
			
			stmt = conn.createStatement();
			
			  rset = stmt.executeQuery(query);
			 
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
			
		} finally {
			close(rset);
			close(stmt);
		}
		
		
		return listCount;
	}



	/** Notice 게시글 목록 조회 DAO
	 * @param conn
	 * @param pInfo 
	 * @return bList
	 * @throws Exception
	 */

	public List<Notice> selectNoticeList(Connection conn, PageInfo pInfo) throws Exception {
		List<Notice> bList = null;
		
		String query = prop.getProperty("selectNoticeList");
		
		try {
			
			int startRow =(pInfo.getCurrentPage()-1) *pInfo.getLimit()+1;
			int endRow = startRow + pInfo.getLimit()-1;

			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			bList = new ArrayList<Notice>();

			
			while(rset.next()) {
				
				Notice notice = new Notice();
				notice.setNoticeNo(rset.getInt("NOTICE_NO"));
				notice.setNoticeTitle(rset.getString("NOTICE_TITLE"));
				notice.setNoticeCreateDt(rset.getTimestamp("NOTICE_CREATE_DT"));
				notice.setNoticeReadCount(rset.getInt("NOTICE_READCOUNT"));
				notice.setMemNo(rset.getInt("MEM_NO"));
				notice.setMemNickName(rset.getString("NICKNAME"));
				bList.add(notice);
			}
	
			//System.out.println(bList);
		
		}finally {
			
			close(rset);
			close(pstmt);
		
		}
		
		
		return bList;
	
	}





	/** Notice 게시글 상세 조회 DAO
	 * @param conn
	 * @param noticeNo
	 * @return notice
	 * @throws Exception
	 */
	public Notice selectNoticeBoard(Connection conn, int noticeNo) throws Exception {
		
		Notice notice = null;

		String query = prop.getProperty("selectNotice");

		try {
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, noticeNo);
			
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				
			    notice= new Notice();
				
				notice.setNoticeNo(rset.getInt("NOTICE_NO"));
				notice.setNoticeTitle(rset.getString("NOTICE_TITLE"));
				notice.setNoticeContent(rset.getString("NOTICE_CONTENT"));
				notice.setNoticeCreateDt(rset.getTimestamp("NOTICE_CREATE_DT"));
				notice.setNoticeReadCount(rset.getInt("NOTICE_READCOUNT"));
				notice.setMemNo(rset.getInt("MEM_NO"));
				notice.setMemNickName(rset.getString("NICKNAME"));
				
			}
			
			
		}finally {
			
			close(rset);
			close(pstmt);
		}
		
		
		return notice;
	}





	/** 조회 수 증가 
	 * @param conn
	 * @param noticeNo
	 * @return
	 * @throws Exception
	 */
	public int increaseReadCount(Connection conn, int noticeNo) throws Exception{
		
		
		int result = 0;
		
		String query = prop.getProperty("increaseReadCount");

		try {
			
			pstmt=conn.prepareStatement(query);
			
			pstmt.setInt(1,noticeNo);
			
			
			result = pstmt.executeUpdate();
		
		}finally {
			
			
			close(pstmt);
		}
		
		return result;
	}





	/** notice에 포함된 이미지 목록 조회 DAO
	 * @param conn 
	 * @param noticeNo
	 * @return fList
	 * @throws Exception
	 */
	public List<NoticeAttachment> selectNoticeFiles(Connection conn, int noticeNo) throws Exception {
	
		List<NoticeAttachment> fList =null;

		String query = prop.getProperty("selectNoticeFiles");
		
		try {
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, noticeNo);
			
			rset = pstmt.executeQuery();
			
			fList = new ArrayList<NoticeAttachment>();
			
			while(rset.next()) {
				NoticeAttachment at = new NoticeAttachment(
						rset.getInt("NOTICE_IMAGE_NO"), 
						rset.getString("NOTICE_IMG_PATH"),
						rset.getString("NOTICE_IMG_NAME"),
						rset.getInt("NOTICE_IMG_LEVEL"));
						
				
				fList.add(at);
				
			}
			
		} finally {
			close(rset);
			close(pstmt);
			
		}
		return fList;
		
		
	}




	/** 다음 notice 게시글 번호 조회 DAO
	 * @param conn
	 * @return noticeNo
	 * @throws Exception
	 */
	public int selectNoticeNextNo(Connection conn) throws Exception {
		
		int noticeNo = 0;

		String query = prop.getProperty("selectNoticeNextNo");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				noticeNo = rset.getInt(1);
			}
			
			
		} finally {
			close(rset);
			close(stmt);
		}
		
		
		return noticeNo;
	}





	/** notice 게시글 삽입 DAO
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertNoticeBoard(Connection conn, Map<String, Object> map) throws Exception {
		
		int result = 0;
		String query = prop.getProperty("insertNoticeBoard");
		
		try {
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1,(int)map.get("noticeNo"));
				pstmt.setString(2,(String)map.get("NoticeTitle"));
				pstmt.setString(3,(String)map.get("NoticeContent"));
				pstmt.setInt(4, (int)map.get("NoticeWriter"));
				result = pstmt.executeUpdate();
			
		} finally {
			
			close(pstmt);
		}
		
		
		return result;
	}




	/** notice 파일 정보 삽입
	 * @param conn
	 * @param at
	 * @return result
	 * @throws Exception
	 */
	public int insertNoticeAttachment(Connection conn, NoticeAttachment at) throws Exception {
		
		int result = 0;
		
		String query = prop.getProperty("insertNoticeAttachment");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, at.getNotifilePath());
			pstmt.setString(2, at.getNotifileName());
			pstmt.setInt(3, at.getNotiFileLevel());
			pstmt.setInt(4, at.getNotiParentNo());
			
			result = pstmt.executeUpdate();
			
			
		} finally {
			
			close(pstmt);
		}
		
		
		
		return result;
	}





	/** notice 수정 DAO
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int updateNotice(Connection conn, Map<String, Object> map) throws Exception{
	
	
		int result = 0 ;

		String query = prop.getProperty("updateNotice");

		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1,(String)map.get("noticeTitle"));
			pstmt.setString(2,(String)map.get("noticeContent"));
			pstmt.setInt(3,(int)map.get("noticeNo"));
			
			
			
			result=pstmt.executeUpdate();
			
		} finally {
			
			close(pstmt);
		}	
		
		
		
		return result;

	
	}





	/** notice 파일 정보 수정 DAO
	 * @param conn
	 * @param newFile
	 * @return result
	 * @throws Exception
	 */
	public int updateNoticeAttachment(Connection conn, NoticeAttachment newFile) throws Exception {
	
		int result = 0;
		
		String query = prop.getProperty("updateNoticeAttachment");

		try {
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, newFile.getNotifilePath());
			pstmt.setString(2, newFile.getNotifileName());
			pstmt.setInt(3, newFile.getNotiFileNo());
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
			
		}
		
		
		return result;
	}





	/** notice 삭제 DAO
	 * @param conn
	 * @param noticeNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteNotice(Connection conn, int noticeNo) throws Exception{
	
		int result = 0;

		String query = prop.getProperty("deleteNotice");
	
		try {
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, noticeNo);
			
		
			result =pstmt.executeUpdate();
			
		} finally {
		
		
		close(pstmt);
		
		}
		
		
		return result;
	}
		
		
	
}
