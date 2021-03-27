package com.kh.semi.manager.model.dao;

import static com.kh.semi.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.semi.manager.vo.MemberReport;
import com.kh.semi.mypage.vo.PageInfo;

public class reportDAO {
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;

	private Properties prop = null;
	
	public reportDAO() {
		String fileName = reportDAO.class.getResource("/com/kh/semi/sql/mypage/report-query.xml").getPath();
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getListCount(Connection conn) throws Exception{
		int listCount = 0;

		String query = prop.getProperty("getListCount");

		try {
			pstmt = conn.prepareStatement(query);
			
			rset = pstmt.executeQuery();

			if (rset.next()) {
				listCount = rset.getInt(1);
			}

		}finally {
			close(rset);
			close(pstmt);
			
		}
		
		
		return listCount;
	}

	public List<MemberReport> selectReport(Connection conn, PageInfo pInfo) throws Exception{

		List<MemberReport> report = null;
		
		String query = prop.getProperty("selectReport");
		
		try {
			
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);

			rset = pstmt.executeQuery();
			
			report = new ArrayList<MemberReport>();
			
			while(rset.next()) {
				MemberReport mReport = new MemberReport();
				mReport.setReportNo(rset.getInt(1));
				mReport.setBoardTitle(rset.getString(2));
				mReport.setMemId(rset.getString(3));
				mReport.setReportTitle(rset.getString(4));
				mReport.setReportContent(rset.getString(5));
				
				report.add(mReport);
			}
			
			
		}finally {
			close(rset);
			close(pstmt);
			
		}
		
		return report;
	}
	
	
	
	
	
	

}
