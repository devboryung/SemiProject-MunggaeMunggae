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

import com.kh.semi.CSCenter.model.vo.PageInfo;
import com.kh.semi.CSCenter.model.vo.Qna;
import com.kh.semi.CSCenter.model.vo.QnaAttachment;

public class QnaDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	
	private Properties prop = null;
	
	
	public QnaDAO(){
		String fileName = QnaDAO.class.getResource("/com/kh/semi/sql/center/qna-query.xml").getPath();
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
	public int getListCount(Connection conn) throws Exception{
		
		int listCount = 0;

		String query = prop.getProperty("getListCount");
		
		try {
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				listCount = rset.getInt(1);
				
			}
		
		}finally {
			close(rset);
			close(stmt);
		}
		
		
		return listCount;
	}
	
	
	/** qna 공개된 글 조회 게시글 수 반환
	 * @param conn
	 * @return  listCount
	 * @throws Exception
	 */
	public int getListPublicCount(Connection conn)  throws Exception{
	
		
		int listCount = 0;

		String query = prop.getProperty("getListPublicCount");
		
		try {
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				listCount = rset.getInt(1);
				
			}
		
		}finally {
			close(rset);
			close(stmt);
		}
		
		
		return listCount;
	}
	
	/** qna 처리되지 않은 글 조회 게시글 수 반환
	 * @param conn
	 * @return listCount
	 * @throws Exception
	 */
	public int getListIncompleteCount(Connection conn) throws Exception {
		
		int listCount = 0;

		String query = prop.getProperty("getListIncompleteCount");
		
		try {
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				listCount = rset.getInt(1);
				
			}
		
		}finally {
			close(rset);
			close(stmt);
		}
		
		
		return listCount;
	}
	
	
	
	/** 나의 문의 내역 글 게시글  수 반환
	 * @param conn
	 * @param memNo
	 * @return listCount
	 * @throws Exception
	 */
	public int getPageMyQnaInfoCount(Connection conn, int memNo)  throws Exception{

		int listCount = 0;

		String query = prop.getProperty("getPageMyQnaInfoCount");
		
		try {
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, memNo);
			
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt(1);
				
			}
		
		}finally {
			close(rset);
			close(stmt);
		}
		
		
		return listCount;
	}
	
	

	/** 게시글 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @return bList
	 * @throws Exception
	 */
	public List<Qna> selectQnaList(Connection conn, PageInfo pInfo) throws Exception {
		
		
		List<Qna> bList = null;

		String query =prop.getProperty("selectQnaList");

		try {
			
			int startRow =(pInfo.getCurrentPage() -1 )*pInfo.getLimit() +1;
			int endRow = startRow + pInfo.getLimit()-1;

			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();

			bList = new ArrayList<Qna>();

			while(rset.next()) {
				Qna board = new Qna(
								rset.getInt("QNA_NO"),
								rset.getString("QNA_TITLE"),
								rset.getString("QNA_CONTENT"),
								rset.getTimestamp("QNA_CREATE_DT"),
								rset.getString("QNA_PRIVATE"),
								rset.getString("QNA_FL"),
								rset.getString("QNA_REPLY_RESPONSE"),
								rset.getInt("MEM_NO2"),
								rset.getInt("QNA_READ_COUNT"),
								rset.getString("NICKNAME"));
				


						bList.add(board);
			}
			
			
			
		} finally {
			close(rset);
			close(pstmt);
		}
		
		
		return bList;
	}
	
	
	
	/** Qna 공개된 글 목록 조회
	 * @param conn
	 * @param pInfo
	 * @return bList
	 * @throws Exception
	 */
	public List<Qna> selectQnaPublicList(Connection conn, PageInfo pInfo) throws Exception {
		
		
		List<Qna> bList = null;

		String query =prop.getProperty("selectQnaPublicList");

		try {
			
			int startRow =(pInfo.getCurrentPage() -1 )*pInfo.getLimit() +1;
			int endRow = startRow + pInfo.getLimit()-1;

			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();

			bList = new ArrayList<Qna>();

			while(rset.next()) {
				Qna board = new Qna(
								rset.getInt("QNA_NO"),
								rset.getString("QNA_TITLE"),
								rset.getString("QNA_CONTENT"),
								rset.getTimestamp("QNA_CREATE_DT"),
								rset.getString("QNA_PRIVATE"),
								rset.getString("QNA_FL"),
								rset.getString("QNA_REPLY_RESPONSE"),
								rset.getInt("MEM_NO2"),
								rset.getInt("QNA_READ_COUNT"),
								rset.getString("NICKNAME"));
				


						bList.add(board);
			}
			
			
			
		} finally {
			close(rset);
			close(pstmt);
		}
		
		
		return bList;
		
}
	
	/** qna 처리되지 않은 글 목록 조회
	 * @param conn
	 * @param pInfo
	 * @return bList
	 */
	public List<Qna> selectIncompleteList(Connection conn, PageInfo pInfo)  throws Exception{
		List<Qna> bList = null;

		String query =prop.getProperty("selectIncompleteList");

		try {
			
			int startRow =(pInfo.getCurrentPage() -1 )*pInfo.getLimit() +1;
			int endRow = startRow + pInfo.getLimit()-1;

			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();

			bList = new ArrayList<Qna>();

			while(rset.next()) {
				Qna board = new Qna(
								rset.getInt("QNA_NO"),
								rset.getString("QNA_TITLE"),
								rset.getString("QNA_CONTENT"),
								rset.getTimestamp("QNA_CREATE_DT"),
								rset.getString("QNA_PRIVATE"),
								rset.getString("QNA_FL"),
								rset.getString("QNA_REPLY_RESPONSE"),
								rset.getInt("MEM_NO2"),
								rset.getInt("QNA_READ_COUNT"),
								rset.getString("NICKNAME"));

						bList.add(board);
			}
			
			
			
		} finally {
			close(rset);
			close(pstmt);
		}
		
		
		return bList;
	} 
	
	
	
	/** 나의 문의내역 목록 반환
	 * @param conn
	 * @param pInfo
	 * @param memNo 
	 * @return
	 * @throws Exception
	 */
	public List<Qna> selectMyQnaList(Connection conn, PageInfo pInfo, int memNo)  throws Exception{
		
		List<Qna> bList = null;

		String query =prop.getProperty("selectMyQnaList");

		try {
			
			int startRow =(pInfo.getCurrentPage() -1 )*pInfo.getLimit() +1;
			int endRow = startRow + pInfo.getLimit()-1;

			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, memNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();

			bList = new ArrayList<Qna>();

			while(rset.next()) {
				Qna board = new Qna(
								rset.getInt("QNA_NO"),
								rset.getString("QNA_TITLE"),
								rset.getString("QNA_CONTENT"),
								rset.getTimestamp("QNA_CREATE_DT"),
								rset.getString("QNA_PRIVATE"),
								rset.getString("QNA_FL"),
								rset.getString("QNA_REPLY_RESPONSE"),
								rset.getInt("MEM_NO2"),
								rset.getInt("QNA_READ_COUNT"),
								rset.getString("NICKNAME"));

						bList.add(board);
			}
			
			
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return bList;
	}


	/** qna 게시글 상세조회
	 * @param conn
	 * @param qnaNo
	 * @return
	 * @throws Exception
	 */
	public Qna selectQna(Connection conn, int qnaNo) throws Exception {
		
		Qna qna = null;

		String query = prop.getProperty("selectQna");

		
		try {
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, qnaNo);
			
			rset = pstmt.executeQuery();

			if(rset.next()) {
			
				qna = new Qna();
		
				qna.setQnaNo(rset.getInt("QNA_NO"));
				qna.setQnaTitle(rset.getString("QNA_TITLE"));
				qna.setQnaContent(rset.getString("QNA_CONTENT"));
				qna.setQnaCreateDt(rset.getTimestamp("QNA_CREATE_DT"));
				qna.setQnaPrivate(rset.getString("QNA_PRIVATE"));
				qna.setQnaFl(rset.getString("QNA_FL"));
				qna.setQnaReplyResponse(rset.getString("QNA_FL"));
				qna.setMemNo(rset.getInt("MEM_NO2"));
				qna.setQnaReadCount(rset.getInt("QNA_READ_COUNT"));
				qna.setMemNickName(rset.getString("NICKNAME"));
				
			}
			
		} finally {
			
			close(rset);
			close(pstmt);
		}
		
		return qna;
	}

	/** qna조회수 증가
	 * @param conn
	 * @param qnaNo
	 * @return result
	 * @throws Exception
	 */
	public int increaseReadCount(Connection conn, int qnaNo) throws Exception {

		int result = 0;

		String query = prop.getProperty("increaseReadCount");

	try {
			
			pstmt=conn.prepareStatement(query);
			
			pstmt.setInt(1,qnaNo);
			
			
			result = pstmt.executeUpdate();
		
		}finally {
			
			
			close(pstmt);
		}	
		
		return result;
	}
	
	
	

	/** 다음 Qna 게시글 번호 조회 DAO
	 * @param conn
	 * @return qnaNo
	 * @throws Exception
	 */
	public int selectQnaNextNo(Connection conn)  throws Exception {
		
		int qnaNo = 0;

		String query = prop.getProperty("selectQnaNextNo");

		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				qnaNo = rset.getInt(1);
			}
		
		} finally {
		
			close(rset);
			close(stmt);
		
		}
		
		return qnaNo;
	}

	/** qna 게시글 삽입 DAO
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertQna(Connection conn, Map<String, Object> map) throws Exception{
		
		
		int result = 0;

		String query = prop.getProperty("insertQna");
		
		try {
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, (int)map.get("qnaNo"));
			pstmt.setString(2, (String)map.get("qnaTitle"));
			pstmt.setString(3, (String)map.get("qnaContent"));
			pstmt.setString(4, (String)map.get("qnaCheck"));
			pstmt.setInt(5, (int)map.get("boardWriter"));
		
			result = pstmt.executeUpdate();

			
		}finally {
			close(pstmt);
		
		}
		
		
		return result;
	}

	/** 파일 정보 삽입 DAO
	 * @param conn
	 * @param at
	 * @return result
	 * @throws Exception
	 */
	public int insertQnaAttachment(Connection conn, QnaAttachment at) throws Exception  {
	
		
		int result = 0;

		String query = prop.getProperty("insertQnaAttachment");
		
		try {
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, at.getQnaImgPath());
			pstmt.setString(2, at.getQnaImgName());
			pstmt.setInt(3, at.getQnaImgLevel());
			pstmt.setInt(4, at.getParentQnaNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/** 게시글에 포함된 이미지 목록 조회 DAO
	 * @param conn
	 * @param qnaNo
	 * @return fList
	 * @throws Exception
	 */
	public List<QnaAttachment> selectQnaFiles(Connection conn, int qnaNo) throws Exception {
		
		List<QnaAttachment> fList =null;

		
		String query = prop.getProperty("selectQnaFiles");
		
		
		try {
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, qnaNo);
			
			rset = pstmt.executeQuery();
			
			fList = new ArrayList<QnaAttachment>();
			
			while(rset.next()) {
				QnaAttachment at = new QnaAttachment(
						rset.getInt("QNA_IMAGE_NO"), 
						rset.getString("QNA_IMG_PATH"),
						rset.getString("QNA_IMG_NAME"), 
						rset.getInt("QNA_IMG_LEVEL")
						);
						
				fList.add(at);
				
			}
			
		} finally {
			close(rset);
			close(pstmt);
			
		}
		return fList;
	}

	
	
	
	/** Qna 게시글 수정 DAO
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int updateQna(Connection conn, Map<String, Object> map) throws Exception {
		
		int result = 0 ;
		String query = prop.getProperty("updateQna");

		
		try {
		
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1,(String)map.get("qnaTitle"));
			pstmt.setString(2,(String)map.get("qnaContent"));
			pstmt.setString(3,(String)map.get("publicCheck"));
			pstmt.setInt(4,(int)map.get("qnaNo"));
			
			result=pstmt.executeUpdate();

			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/** 파일 정보 수정 DAO
	 * @param conn
	 * @param newFile
	 * @return result
	 * @throws Exception
	 */
	public int updateQnaAttachment(Connection conn, QnaAttachment newFile) throws Exception {

		int result = 0;
		String query = prop.getProperty("updateAttachment");

		try {
			
			
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, newFile.getQnaImgPath());
			pstmt.setString(2, newFile.getQnaImgName());
			pstmt.setInt(3, newFile.getQnaImgNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			close(pstmt);
		}
		
		
		return result;
	}

	/**  Qna 게시글 삭제 DAO
	 * @param conn
	 * @param qnaNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteQna(Connection conn, int qnaNo) throws Exception{
	
		
		int result = 0;

		
		String query = prop.getProperty("deleteQna");
		
		try {
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, qnaNo);
			
		
			result =pstmt.executeUpdate();
			
		} finally {
		
		
		close(pstmt);
		
		}
		
		
		return result;
	}







}
