package com.kh.semi.CSCenter.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import static com.kh.semi.common.JDBCTemplate.*;

import com.kh.semi.CSCenter.model.vo.Faq;
import com.kh.semi.CSCenter.model.vo.FaqAttachment;
import com.kh.semi.CSCenter.model.vo.PageInfo;



public class FaqDAO {
	
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	private Properties prop = null;
	

	public FaqDAO(){
		String fileName = FaqDAO.class.getResource("/com/kh/semi/sql/center/faq-query.xml").getPath();
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	/** 게시글 목록 조회 DAO
	 * @param conn
	 * @param pInfo 
	 * @return bList
	 * @throws Exception
	 */
	public List<Faq> selectFaqList(Connection conn, PageInfo pInfo) throws Exception{
		List<Faq> bList = null;
		
		String query = prop.getProperty("selectFaqList");
		
		try {
			
			int startRow =(pInfo.getCurrentPage()-1) *pInfo.getLimit()+1;
			int endRow = startRow + pInfo.getLimit()-1;

			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			bList = new ArrayList<Faq>();

			
			while(rset.next()) {
				
				Faq faq = new Faq();
				faq.setFaqNo(rset.getInt("FAQ_NO"));
				faq.setFaqTitle(rset.getString("FAQ_TITLE"));
				faq.setFaqCreateDt(rset.getTimestamp("FAQ_CRATE_DT"));
				faq.setFaqReadCount(rset.getInt("FAQ_READ_COUNT"));
				faq.setMemId(rset.getString("NICKNAME"));

						
				
				bList.add(faq);
			}
	
		//	System.out.println(bList);
		
		}finally {
			
			close(rset);
			close(pstmt);
		
		}
		
		
		
		return bList;
	}



	/** 다음 게시글 번호 조회 DAO
	 * @param conn
	 * @return faqNo
	 * @throws Exception
	 */
	public int selectFaqNextNo(Connection conn) throws Exception{
		
		int faqNo = 0 ;
		String query = prop.getProperty("selectFaqNextNo");
		
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
		
			if(rset.next()) {
				
				faqNo = rset.getInt(1);
			}
			
		} finally {
			close(rset);
			close(stmt);
		}
		
		
		return faqNo;
	}



	/** faq 게시글 삽입 DAO
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertFaq(Connection conn, Map<String, Object> map) throws Exception{
		
		
		int result = 0;

		String query = prop.getProperty("insertFaq");

		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1,(int)map.get("faqNo"));
			pstmt.setString(2,(String)map.get("faqTitle"));
			pstmt.setString(3,(String)map.get("faqContent"));
			pstmt.setInt(4, (int)map.get("faqWriter"));

			result = pstmt.executeUpdate();

		
		} finally {
		
			close(pstmt);
		
		}
		
		
		return result;
	}



	/** faq 파일 정보 삽입 DAO
	 * @param conn
	 * @param at
	 * @return result
	 * @throws Exception
	 */
	public int insertFaqAttachment(Connection conn, FaqAttachment at) throws Exception{
	
		int result = 0;

		String query = prop.getProperty("insertFaqAttachment");

		try {
			
			pstmt = conn.prepareStatement(query);
			

			pstmt.setString(1, at.getFaqFilePath());
			pstmt.setString(2, at.getFaqFileName());
			pstmt.setInt(3, at.getFaqFileLevel());
			pstmt.setInt(4, at.getParentfaqNo());
			
			result = pstmt.executeUpdate();

			
			
		}finally {
			
			
			close(pstmt);

			
		}
		
		return result;
	}



	/** 전체 게시글 수 반환 DAO
	 * @param conn
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn) throws Exception {
		
		int listCount = 0;
		
		String query =prop.getProperty("selectFaqListCount");
		
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



	/** faq 상세 조회 DAO
	 * @param conn
	 * @param faqNo
	 * @return Faq
	 * @throws Exception
	 */
	public Faq selectFaqView(Connection conn, int faqNo) throws Exception {
		
		Faq faq = null;
		
		String query = prop.getProperty("selectFaqView");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, faqNo);
			
			rset = pstmt.executeQuery();

			
			if(rset.next()) {
				
				faq = new Faq();
				faq.setFaqNo(rset.getInt("FAQ_NO"));
				faq.setFaqTitle(rset.getString("FAQ_TITLE"));
				faq.setFaqContent(rset.getString("FAQ_CONTENT"));
				faq.setFaqCreateDt(rset.getTimestamp("FAQ_CRATE_DT"));
				faq.setFaqReadCount(rset.getInt("FAQ_READ_COUNT"));
				faq.setMemId(rset.getString("NICKNAME"));
			}
			
			
		} finally {
			close(rset);
			close(pstmt);
		
		}
		
		
		
		return faq;
	}



	/** 조회수 DAO
	 * @param conn
	 * @param faqNo
	 * @return result
	 * @throws Exception
	 */
	public int increaseReadCount(Connection conn, int faqNo) throws Exception {
	
		int result = 0;
		
		String query = prop.getProperty("increaseReadCount");
		
		try {
			
			pstmt=conn.prepareStatement(query);

			pstmt.setInt(1,faqNo);
			
			result = pstmt.executeUpdate();


		} finally {
		
			close(pstmt);

		
		}
		
		return result;
	}



	/** faq상세 페이지에 포함된 이미지 목록 조회 DAO
	 * @param conn
	 * @param faqNo
	 * @return fList
	 * @throws Exception
	 */
	public List<FaqAttachment> selectFaqFiles(Connection conn, int faqNo) throws Exception {
		
		List<FaqAttachment> fList =null;

		String query = prop.getProperty("selectFaqFiles");

		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, faqNo);

			rset = pstmt.executeQuery();
			
			fList = new ArrayList<FaqAttachment>();

			
			
			
			while(rset.next()) {
				FaqAttachment at = new FaqAttachment(
						rset.getInt("FAQ_IMG_NO"), 
						rset.getString("FAQ_IMAGE_PATH"), 
						rset.getString("FAQ_IMG_NAME"),
						rset.getInt("FAQ_IMG_LEVEL"));
				
				fList.add(at);
			}
			
			
			
		} finally {
		
			close(rset);
			close(pstmt);
		
		}
		
		
		return fList;
	}



	/** faq 수정 DAO
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int updateFaq(Connection conn, Map<String, Object> map) throws Exception {
		
		int result = 0;
		
		String query = prop.getProperty("updateFaq");
		
		try {
			
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, (String)map.get("faqTitle"));
			pstmt.setString(2, (String)map.get("faqContent"));
			pstmt.setInt(3,(int)map.get("faqNo"));

			
			
			result=pstmt.executeUpdate();

			
			
		} finally {
			
			close(pstmt);

		}
		
		
		
		return result;
	}



	
	/** 사진 업데이트 
	 * @param conn
	 * @param newFile
	 * @return result
	 * @throws Exception
	 */
	public int updateFaqAttachment(Connection conn, FaqAttachment newFile) throws Exception{
		
		int result = 0;
		String query = prop.getProperty("updateFaqAttachment");

		
		try {
			
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, newFile.getFaqFilePath());
			pstmt.setString(2, newFile.getFaqFileName());
			pstmt.setInt(3, newFile.getFaqFileNo());
		
			result = pstmt.executeUpdate();

		} finally {
		
			close(pstmt);
		
		
		}
		
		return result;
	}



	/** faq 삭제 dao
	 * @param conn
	 * @param faqNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteFaq(Connection conn, int faqNo) throws Exception {
		
		int result = 0;

		String query = prop.getProperty("deleteFaq");

		
		
		try {
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, faqNo);
			
		
			result =pstmt.executeUpdate();
			
		} finally {
		
		
		close(pstmt);
		
		}
		
		
		return result;
	}

}
