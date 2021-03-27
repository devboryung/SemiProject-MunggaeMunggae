package com.kh.semi.member.model.service;

import static com.kh.semi.common.JDBCTemplate.*;

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.kh.semi.freeBoard.model.exception.FileInsertFailedException;
import com.kh.semi.freeBoard.model.vo.Attachment;
import com.kh.semi.freeBoard.model.vo.PageInfo;
import com.kh.semi.member.model.dao.MemberDAO;
import com.kh.semi.member.model.vo.Member;
import com.kh.semi.reply.model.vo.Reply;

public class MemberService {
	private MemberDAO dao = new MemberDAO();

	/**
	 * 회원가입용 Service
	 * 
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int signUp(Member member) throws Exception {
		Connection conn = getConnection();

		int result = dao.signUp(conn, member);

		if (result > 0)
			commit(conn);
		else
			rollback(conn);

		close(conn);
		return result;
	}

	/**
	 * 로그인용 Service
	 * 
	 * @param member
	 * @return loginMember
	 * @throws Exception
	 */
	public Member loginMember(Member member) throws Exception {
		Connection conn = getConnection();

		Member loginMember = dao.loginMember(conn, member);

		close(conn);
		return loginMember;
	}

	/**
	 * 아이디 중복 검사용 Service
	 * 
	 * @param id
	 * @return result
	 * @throws Exception
	 */
	public int idDupCheck(String userId) throws Exception {

		Connection conn = getConnection();

		int result = dao.idDupCheck(conn, userId);

		close(conn);

		return result;
	}

	/**
	 * 일반 내 정보 수정 Service
	 * 
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int updateMember(Member member) throws Exception {
		Connection conn = getConnection();

		int result = dao.updateMember(conn, member);

		if (result > 0)
			commit(conn);
		else
			rollback(conn);

		close(conn);

		return result;
	}


	/**
	 * 비밀번호 변경 Service
	 * 
	 * @param loginMember
	 * @param newPw1
	 * @param newPw2
	 * @return
	 * @throws Exception
	 */
	public int updatePw(Member loginMember, String newPwd) throws Exception {
		Connection conn = getConnection();

		int result = dao.checkCurrentPw(conn, loginMember);

		if (result > 0) {
			loginMember.setMemberPwd(newPwd);

			result = dao.updatePwd(conn, loginMember);

			if (result > 0)
				commit(conn);
			else
				rollback(conn);

		} else { // 현재 비밀번호 불일치
			result = -1;
		}

		close(conn);

		return result;
	}

	/**
	 * 회원 탈퇴 (탈퇴 여부 변경) Service
	 * 
	 * @param loginMember
	 * @return result
	 * @throws Exception
	 */

	public int withdrawal(Member loginMember) throws Exception {
		Connection conn = getConnection();

		int result = dao.checkCurrentPw(conn, loginMember); // 재활용

		if (result > 0) {

			result = dao.withdrawal(conn, loginMember.getMemberNo());

			if (result > 0)
				commit(conn);
			else
				rollback(conn);

		} else {
			// 현재 비밀번호가 일치하지 않을 경우 -1 반환
			result = -1;
		}

		close(conn);

		return result;
	}
	
	
	/** 아이디 찾기
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public Member findIdResult(Member member) throws Exception{
		Connection conn = getConnection();
		
		Member findMember = dao.findIdResult(conn, member);
		
		close(conn);
		
		return findMember;
	}
	
	

	/** 비밀번호 찾기 Service
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public int findPw(Member member) throws Exception{
		Connection conn = getConnection();
		
		int findPwMember = dao.findPw(conn, member);
		
		close(conn);
		
		return findPwMember;
	}
	
	

	/** 새로운 비밀번호로 변경 Service
	 * @param id
	 * @param pw1
	 * @return
	 * @throws Exception
	 */
	public int updatePwd(String id, String pw1) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.updatePwd(conn, id, pw1);
		
		if(result > 0)		commit(conn);
		else				rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 업체 회원가입
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int signUpCom(Map<String, Object> map) throws Exception{
		Connection conn = getConnection();
		
		int result = 0;
		
		int memNo = dao.selectNextNo(conn);
		
		if(memNo > 0) {
			map.put("memNo", memNo);
			
			
			try {
				result = dao.signUpCom(conn, map);
				
				List<Attachment> list =  (List<Attachment>)map.get("list");
				
				if(result > 0 && !list.isEmpty()) {
					
					result = 0;	// result 재활용을 위해 0으로 초기화
					
					// fList의 요소를 하나씩 반복 접근하여
					// DAO 메소드를 반복 호출해 정보를 삽입함
					for(Attachment at : list) {
						
						// 파일 정보가 저장된 Attachment 객체에
						// 해당 파일이 작성된 게시글 번호를 추가 세팅
						at.setParentBoardNo(memNo);
						
						result = dao.insertAttachment(conn, at);
						
						if(result == 0) {	
							
							throw new FileInsertFailedException("파일정보 삽입 실패");
						}
					}
					
				}
				
			}catch (Exception e) {
				List<Attachment> list = (List<Attachment>)map.get("list");
				
				if(!list.isEmpty()) {
					
					for(Attachment at : list) {
						
						String filePath = at.getFilePath();
						String fileName = at.getFileName();
						
						File deleteFile = new File(filePath + fileName);
						// 파일 경로가 나옴
						
						if(deleteFile.exists()) {
							// 해당 경로에 해당 파일이 존재하면
							deleteFile.delete();	// 파일 삭제
						}
						
					}
				}
				throw e;
			}
			
			if(result > 0) {
				commit(conn);
		
			}else {
				rollback(conn);
			}
			
		}
		close(conn);
		
		return result;
	}

	/** 업체 회원가입 공통부분
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public int signUpCom1(Member member) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.signUpCom1(conn, member);
		
		if(result > 0)		commit(conn);
		else				rollback(conn);
		
		close(conn);
		
		return result;
	}

	
	/** 업체 내 정보 수정 Service
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int updateCompany(Member member)throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.updateCompany(conn, member);
		
		if(result > 0) commit(conn);
		else           rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 업체 정보 조회 Service
	 * @param memNo
	 * @return comMember
	 * @throws Exception
	 */
	public Member selectComMember(int memNo)throws Exception {
		Connection conn = getConnection();
		
		Member comMember = dao.selectComMember(conn ,memNo);
		
		close(conn);
		
		return comMember;
	}

	public List<Reply> myReplySelect(PageInfo pInfo, int memNo) throws Exception{
		Connection conn = getConnection();
		
		List<Reply> myReply = dao.myReplySelect(conn, pInfo, memNo);
		
		close(conn);
		
		return myReply;
	}

	/**
	 * @param cp
	 * @param memNo 
	 * @return
	 * @throws Exception
	 */
	public PageInfo getPageInfo(String cp, int memNo) throws Exception{
		Connection conn = getConnection();
		
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);
		
		int listCount = dao.getListCount(conn, memNo);
		
		close(conn);
		
		return new PageInfo(currentPage, listCount);
		
	}



}
