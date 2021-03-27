package com.kh.semi.member.model.dao;

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

import com.kh.semi.freeBoard.model.vo.Attachment;
import com.kh.semi.freeBoard.model.vo.PageInfo;
import com.kh.semi.member.model.vo.Member;
import com.kh.semi.reply.model.vo.Reply;

public class MemberDAO {

//  DAO에서 자주 사용되는 JDBC 참조 변수 선언
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	private Properties prop = null;

	public MemberDAO() {
//    외부에 저장된 XML 파일로부터 SQL 을 얻어온다. -> 유지보수성 향상
		try {
			String filePath = MemberDAO.class.getResource("/com/kh/semi/sql/member-query.xml").getPath();

			prop = new Properties();
//     k , y 가 둘다 string 자료형
			prop.loadFromXML(new FileInputStream(filePath));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 회원가입용 DAO
	 * 
	 * @param conn
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int signUp(Connection conn, Member member) throws Exception {
		int result = 0;

		String query = prop.getProperty("signUp");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPwd());
			pstmt.setString(3, member.getMemberNickName());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getPhone());
			pstmt.setString(6, member.getGender());

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);

		}

		return result;
	}

	/**
	 * 로그인용 DAO
	 * 
	 * @param conn
	 * @param member
	 * @return loginMember
	 * @throws Exception
	 */
	public Member loginMember(Connection conn, Member member) throws Exception {
		Member loginMember = null;

		String query = prop.getProperty("loginMember");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPwd());

			rset = pstmt.executeQuery();

			if (rset.next()) {
				loginMember = new Member(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4),
						rset.getString(5), rset.getString(6), rset.getString(7));
			}

		} finally {
			close(rset);
			close(pstmt);
		}

		return loginMember;
	}

	/**
	 * 아이디 중복검사용 DAO
	 * 
	 * @param conn
	 * @param id
	 * @return result
	 * @throws Exception
	 */
	public int idDupCheck(Connection conn, String userId) throws Exception {
		int result = 0;

		String query = prop.getProperty("idDupCheck");

		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, userId);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				result = rset.getInt(1);
			}

		} finally {
			close(rset);
			close(pstmt);

		}

		return result;

	}

	/**
	 * 내 정보 수정 DAO
	 * 
	 * @param conn
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int updateMember(Connection conn, Member member) throws Exception {
		int result = 0;

		String query = prop.getProperty("updateMember");

		try {

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, member.getMemberNickName());
			pstmt.setString(2, member.getEmail());
			pstmt.setString(3, member.getPhone());
			pstmt.setString(4, member.getGender());
			pstmt.setInt(5, member.getMemberNo());

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}

	/**
	 * 현재 비밀번호 검사 DAO
	 * 
	 * @param conn
	 * @param loginMember
	 * @return
	 * @throws Exception
	 */
	public int checkCurrentPw(Connection conn, Member loginMember) throws Exception {
		int result = 0;

		String query = prop.getProperty("checkCurrentPw");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, loginMember.getMemberNo());
			pstmt.setString(2, loginMember.getMemberPwd());

			rset = pstmt.executeQuery();

			if (rset.next()) {
				result = rset.getInt(1);
			}

		} finally {
			close(rset);
			close(pstmt);
		}

		return result;
	}

	/**
	 * 비밀번호 변경 DAO
	 * 
	 * @param conn
	 * @param loginMember
	 * @return
	 * @throws Exception
	 */
	public int updatePwd(Connection conn, Member loginMember) throws Exception {
		int result = 0;

		String query = prop.getProperty("updatePw");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, loginMember.getMemberPwd());
			pstmt.setInt(2, loginMember.getMemberNo());

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);

		}
		return result;
	}


	/**
	 * 회원 탈퇴 DAO
	 * 
	 * @param conn
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	public int withdrawal(Connection conn, int memberNo) throws Exception {
		int result = 0;

		String query = prop.getProperty("withdrawal");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}

		return result;
	}

	/** 아이디 찾기 DAO
	 * @param conn
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public Member findIdResult(Connection conn, Member member) throws Exception{
		Member findMember = null;
		
		String query = prop.getProperty("findIdResult");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberNickName());
			pstmt.setString(2, member.getEmail());
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				findMember = new Member();
				findMember.setMemberId(rset.getString("MEM_ID"));
			}
			
			
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return findMember;
	}
	
	

	/** 비밀번호 변경을 위한 조회 DAO
	 * @param conn
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public int findPw(Connection conn, Member member) throws Exception{
		int findPwMember = 0;
		
		String query = prop.getProperty("findPw");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getEmail());
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				findPwMember = rset.getInt(1);
			}
			
			
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return findPwMember;
	}

	/** 새로운 비밀번호로 변경 DAO
	 * @param conn
	 * @param id
	 * @param pw1
	 * @return
	 * @throws Exception
	 */
	public int updatePwd(Connection conn, String id, String pw1) throws Exception{
		int result = 0;
		
		String query = prop.getProperty("updatePwd");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, pw1);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	
	/** 회원번호 얻어오기
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public int selectNextNo(Connection conn) throws Exception{
		int memNo = 0;
		
		String query = prop.getProperty("selectNextNo");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				memNo = rset.getInt(1);
			}
			
		}finally {
			
			close(rset);
			close(pstmt);
		}
		
		return memNo;
	}

	
	/** 업체 회원가입 DAO
	 * @param conn
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int signUpCom(Connection conn, Map<String, Object> map) throws Exception{
		int result = 0;
		
		String query = prop.getProperty("signUpCom");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, (int)map.get("memNo"));
			pstmt.setString(2, (String)map.get("comName"));
			pstmt.setString(3, (String)map.get("comAddress"));
			pstmt.setString(4, (String)map.get("comPhone"));
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
			
		}
		
		return result;
	}

	/** 사업자 등록증 삽입 DAO
	 * @param conn
	 * @param at
	 * @return
	 * @throws Exception
	 */
	public int insertAttachment(Connection conn, Attachment at) throws Exception{
		int result = 0;
		
		String query = prop.getProperty("insertAttachment");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, at.getFilePath());
			pstmt.setString(2, at.getFileName());
			pstmt.setInt(3, at.getFileLevel());
			pstmt.setInt(4, at.getParentBoardNo());
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
			
		}
		
		return result;
	}

	
	
	
	/** 업체 회원가입 공통부분
	 * @param conn
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public int signUpCom1(Connection conn, Member member) throws Exception{
		int result = 0;
		
		String query = prop.getProperty("signUpCom1");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPwd());
			pstmt.setString(3, member.getMemberNickName());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getPhone());
			pstmt.setString(6, member.getGender());

			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	/** 업체 내 정보 수정 DAO
	 * @param conn
	 * @param member
	 * @return reusult
	 */
	public int updateCompany(Connection conn, Member member) throws Exception {
		
		int result = 0;
		
		String query = prop.getProperty("updateCompany");
		
		try {
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, member.getComPhone());
			pstmt.setString(2, member.getComAddress());
			pstmt.setInt(3, member.getMemberNo());

			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 업체 정보 조회 DAO
	 * @param conn
	 * @param memNo
	 * @return comMember
	 * @throws Exception
	 */
	public Member selectComMember(Connection conn, int memNo)throws Exception {
		Member comMember = null;
		
		String query = prop.getProperty("selectComMember");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				comMember = new Member(rset.getNString("COO_NM"), rset.getNString("COO_ADDR"), rset.getNString("COO_PHONE"));
			}
			
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return comMember;
	}

	/** 내가 쓴 댓그 ㄹ 조회 DAO
	 * @param conn 
	 * @param pInfo 
	 * @param memNo 
	 * @return
	 * @throws Exception
	 */
	public List<Reply> myReplySelect(Connection conn, PageInfo pInfo, int memNo) throws Exception{
		List<Reply> myReply = null;
		
		String query = prop.getProperty("myReplySelect");
		
		try {
			
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			myReply = new ArrayList<Reply>();
			while(rset.next()) {
				Reply myRi = new Reply();
				myRi.setParentBoardNo(rset.getInt("FREE_BOARD_NO"));
				myRi.setReplyContent(rset.getString("REPLY_CONTENT"));
				myRi.setReplyCreateDate(rset.getTimestamp("REPLY_DT"));
				myRi.setBoardTitle(rset.getString("FREE_BOARD_TITLE"));
				
				myReply.add(myRi);
				
			}
			
		}finally {
			close(rset);
			close(pstmt);
			
		}
		
		return myReply;
	}

	public int getListCount(Connection conn, int memNo) throws Exception{
		int listCount = 0;
		
		String query = prop.getProperty("getListCount");
		
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
}
