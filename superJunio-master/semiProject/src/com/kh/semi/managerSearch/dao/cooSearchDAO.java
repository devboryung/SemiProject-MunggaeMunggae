package com.kh.semi.managerSearch.dao;

import static com.kh.semi.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kh.semi.member.model.vo.Member;
import com.kh.semi.mypage.vo.PageInfo;

public class cooSearchDAO {
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;

	public int getListCount(Connection conn, String condition) throws Exception{
		 int listCount = 0;
	      
	      String query = "SELECT COUNT(*) FROM TB_MEMBER JOIN COOPERATOR USING(MEM_NO) WHERE SCSN_FL = 'N' AND MEM_GRADE = 'C' AND " + condition;
	      
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

	public List<Member> cSearchList(Connection conn, PageInfo pInfo, String condition) throws Exception{

		List<Member> mList = null;
		
		String query = 
				"SELECT * FROM" + 
				"    (SELECT ROWNUM RNUM , V.*" + 
				"    FROM" + 
				"        (SELECT MEM_NO, MEM_ID, NICKNAME, PHONE, COO_NM, COO_PHONE FROM COOPERATOR JOIN TB_MEMBER USING(MEM_NO)" + 
				"        WHERE " + condition +
				"        AND SCSN_FL = 'N' AND MEM_GRADE = 'C' ORDER BY MEM_NO DESC) V )" + 
				"WHERE RNUM BETWEEN ? AND ?";
		
		try {
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow = (pInfo.getCurrentPage() -1) * pInfo.getLimit() +1;
			int endRow = startRow + pInfo.getLimit()-1;
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			mList = new ArrayList<Member>();
			
			while(rset.next()) {
				Member member = new Member(
									rset.getString("MEM_ID"),
									rset.getString("NICKNAME"),
									rset.getInt("MEM_NO"),
									rset.getString("PHONE"),
									rset.getString("COO_NM"),
									rset.getString("COO_PHONE")
						);
											
				mList.add(member);
			}
			
		}finally {
			close(rset);
			close(pstmt);
		}
			
		return mList;
	}

}
