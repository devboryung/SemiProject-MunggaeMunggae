package com.kh.semi.hospital.model.dao;

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

import com.kh.semi.hospital.model.vo.Attachment;
import com.kh.semi.hospital.model.vo.Hospital;
import com.kh.semi.hospital.model.vo.PageInfo;

public class HospitalDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	private Properties prop = null;
	
	public HospitalDAO() {
		String fileName = HospitalDAO.class.getResource("/com/kh/semi/sql/hospital/hospital-query.xml").getPath();
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	

	/** 전체 동물병원 수 반환 DAO
	 * @param conn
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn) throws Exception {
		int listCount =0;
		String query = prop.getProperty("getListCount");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			while(rset.next()){
				listCount = rset.getInt(1);
			}
		}finally {
			close(rset);
			close(stmt);
		}
		
		return listCount;
	}
	
	
	
	




	/** 동물병원 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @param location1 
	 * @return
	 * @throws Exception
	 */
	public List<Hospital> selectHospitalList(Connection conn, PageInfo pInfo) throws Exception {
		List<Hospital> hList = null;
		String query = prop.getProperty("selectHospitalList");
		
		try {
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow = (pInfo.getCurrentPage()-1) * pInfo.getLimit()+1;
			int endRow = startRow + pInfo.getLimit()-1;
			// 7개의 글 중에서 1페이지에 해당하는 글을 가져옴 : 7~2번째의 글만 가져오게 됨.
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2,  endRow);
			
			rset = pstmt.executeQuery();
			
			hList = new ArrayList<Hospital>();
			
			while(rset.next()) {
				Hospital hospital = new Hospital(rset.getInt("HOSP_NO"), rset.getString("HOSP_NM"), 
						rset.getString("LOCATION2"), rset.getString("PHONE"), 
						rset.getString("OPENING_TIME"), rset.getString("CLOSING_TIME"));
						
				hList.add(hospital);		
			}
			
		}finally {
			close(rset);
			close(pstmt);
			
		}
		return hList;
	}



	/** 동물병원 상세조회 Service
	 * @param conn
	 * @param hospitalNo
	 * @return hospital
	 * @throws Exception
	 */
	public Hospital selectHospital(Connection conn, int hospitalNo) throws Exception {
		Hospital hospital = null;
		
		String query = prop.getProperty("selectHospital");
		
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, hospitalNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				hospital = new Hospital();
				hospital.setHospNm(rset.getString("HOSP_NM"));
				hospital.setLocation1(rset.getString("LOCATION1"));
				hospital.setLocation2(rset.getString("LOCATION2"));
				hospital.setPhone(rset.getString("PHONE"));
				hospital.setOpeningTime(rset.getString("OPENING_TIME"));
				hospital.setClosingTime(rset.getString("CLOSING_TIME"));
				hospital.setHospInfo(rset.getString("HOSP_INFO"));
				hospital.setViewCount(rset.getInt("VIEW_COUNT"));
				hospital.setHospFacility(rset.getString("HOSP_FACILITY"));
				
			}
		}finally {
			close(rset);
			close(pstmt);
		}
		return hospital;
	}

	
	

	

	/** 조회 수 증가 DAO
	 * @param conn
	 * @param hospitalNo
	 * @return result
	 * @throws Exception
	 */
	public int increaseReadCount(Connection conn, int hospitalNo)throws Exception {
		int result =0;
		
		String query = prop.getProperty("increaseReadCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, hospitalNo);
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	
	
    /*****************************게시글 등록 + 파일 업로드 DAO *****************************/


	/** 다음 병원번호 조회 DAO
	 * @param conn
	 * @return	hospitalNo
	 * @throws Exception
	 */
	public int selectNextNo(Connection conn) throws Exception {
		int hospitalNo =0;
		String query = prop.getProperty("selectNextNo");
		
		try {	
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				hospitalNo = rset.getInt(1);
			}
			
		}finally {
			close(rset);
			close(stmt);
		}
		return hospitalNo;
	}



	
	
	
	/** 동물병원 등록 DAO (첨부파일 제외)
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertHospital(Connection conn, Map<String, Object> map) throws Exception {
		int result =0;
		
		String query = prop.getProperty("insertHospital");
		
		try {
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,(int)map.get("insertNo"));
			pstmt.setString(2,(String)map.get("hospNm"));
			pstmt.setString(3,(String)map.get("location1"));
			pstmt.setString(4,(String)map.get("location2"));
			pstmt.setString(5,(String)map.get("phone"));
			pstmt.setString(6,(String)map.get("openTime"));
			pstmt.setString(7,(String)map.get("closeTime"));
			pstmt.setString(8,(String)map.get("facility"));
			pstmt.setString(9,(String)map.get("hospitalInfo"));
			pstmt.setInt(10,(int)map.get("memberNo"));
			
			result = pstmt.executeUpdate();
		
		}finally {
			close(pstmt);
		}
		
		return result;
	}



	
	
	
	/** 파일 정보 등록 DAO
	 * @param conn
	 * @param at
	 * @return result
	 * @throws Exception
	 */
	public int insertAttachment(Connection conn, Attachment at) throws Exception {
		int result =0;
		
		String query = prop.getProperty("insertAttachment");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, at.getFilePath());
			pstmt.setString(2, at.getFileName());
			pstmt.setInt(3, at.getFileLevel());
			pstmt.setInt(4, at.getHospNo());
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		return result;
	}


	
	
	

	/** 동물병원에 포함된 이미지 목록 조회 DAO
	 * @param conn
	 * @param hospitalNo
	 * @return fList
	 * @throws Exception
	 */
	public List<Attachment> selectHospitalFiles(Connection conn, int hospitalNo) throws Exception {
		List<Attachment> fList=null;
		String query = prop.getProperty("selectHospitalFiles");
		
		try {
			pstmt= conn.prepareStatement(query);
			pstmt.setInt(1, hospitalNo);
			
			rset = pstmt.executeQuery();
			
			fList = new ArrayList<Attachment>();
			
			while(rset.next()) {
				Attachment at = new Attachment(
						rset.getInt("FILE_NO"),
                        rset.getString("IMG_NAME"),
                        rset.getInt("IMG_LEVEL"));
				
				at.setFilePath(rset.getString("IMG_PATH"));
				
				fList.add(at);
			}
			
		}finally{
			close(rset);
			close(pstmt);
		}
		return fList;
	}


	
	
	

	/** 썸네일 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @return fList
	 * @throws Exception
	 */
	public List<Attachment> selectThumbnailList(Connection conn, PageInfo pInfo) throws Exception {
		List<Attachment> fList = null;
		
		String query = prop.getProperty("selectThumbnailList");
		
		try {
			// 위치 홀더에 들어갈 시작 행, 끝 행번호 계산
			int startRow = (pInfo.getCurrentPage() -1) * pInfo.getLimit() +1;
			int endRow = startRow + pInfo.getLimit()-1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			fList = new ArrayList<Attachment>();
			
			while(rset.next()) {
				Attachment at = new Attachment();
				at.setFileName(rset.getString("IMG_NAME"));
				at.setHospNo(rset.getInt("HOSP_NO"));

				
				fList.add(at);
			}
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return fList;
	}



	
	
	
	/** 동물병원 삭제 DAO
	 * @param conn
	 * @param hospitalNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteHospital(Connection conn, int hospitalNo) throws Exception {
		int result =0;
		String query = prop.getProperty("deleteHospital");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, hospitalNo);
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		return result;
	}



	
	
	
	/** 동물병원 수정 DAO
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int updateHospital(Connection conn, Map<String, Object> map) throws Exception {
		int result =0;
		String query = prop.getProperty("updateHospital");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,(String)map.get("hospNm"));
			pstmt.setString(2,(String)map.get("location1"));
			pstmt.setString(3,(String)map.get("location2"));
			pstmt.setString(4,(String)map.get("phone"));
			pstmt.setString(5,(String)map.get("openTime"));
			pstmt.setString(6,(String)map.get("closeTime"));
			pstmt.setString(7,(String)map.get("facility"));
			pstmt.setString(8,(String)map.get("hospitalInfo"));
			pstmt.setInt(9, (int)map.get("hospitalNo"));
			
			result = pstmt.executeUpdate();
			
		}finally{
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
	public int updateAttachment(Connection conn, Attachment newFile) throws Exception {
		int result =0;
		
		String query = prop.getProperty("updateAttachment");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newFile.getFilePath());
			pstmt.setNString(2, newFile.getFileName());
			pstmt.setInt(3, newFile.getFileNo());
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		
		return result;
	}



	




	
	




	

}
