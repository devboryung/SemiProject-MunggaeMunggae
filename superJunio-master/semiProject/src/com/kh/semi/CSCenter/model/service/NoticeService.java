package com.kh.semi.CSCenter.model.service;

import static com.kh.semi.common.JDBCTemplate.*;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kh.semi.CSCenter.model.dao.FaqDAO;
import com.kh.semi.CSCenter.model.dao.NoticeDAO;
import com.kh.semi.CSCenter.model.exception.FileInsertFailedException;
import com.kh.semi.CSCenter.model.vo.Faq;
import com.kh.semi.CSCenter.model.vo.FaqAttachment;
import com.kh.semi.CSCenter.model.vo.Notice;
import com.kh.semi.CSCenter.model.vo.NoticeAttachment;
import com.kh.semi.CSCenter.model.vo.PageInfo;



public class NoticeService {
	
	
	private NoticeDAO dao = new NoticeDAO();

	
	
	/** 페이징 계산처리
	 * @param cp
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getPageInfo(String cp) throws Exception{

		
		Connection conn = getConnection();
		
		int currentPage = cp ==null ? 1:Integer.parseInt(cp);
		
		
		// DB에서 전체 게시글 수를 조회하여 반환 받기
		int listCount = dao.getListCount(conn);

		
		close(conn);

		
		// 얻어온 현재페이지와 , DB에서 조회한 전체 게시글 수를 이용하여
		// PageInfo 객체를 생성함
		return new PageInfo(currentPage,listCount);
		
		
	}


	
	/** Notice 목록 조회
	 * @param pInfo 
	 * @return bList
	 * @throws Exception
	 */
	public List<Notice> selectNoticeList(PageInfo pInfo) throws Exception {

		Connection conn = getConnection();
		 
		List<Notice> bList = dao.selectNoticeList(conn,pInfo);

		
		close(conn);
		
		return bList;
		
	}



	/** 공지사항 게시글 상세조회 Service
	 * @param noticeNo
	 * @return notice
	 * @throws Exception
	 */
	public Notice selectNotice(int noticeNo) throws Exception{
	
		Connection conn = getConnection();
		
		Notice notice = dao.selectNoticeBoard(conn,noticeNo);

		
		if(notice != null) { // DB 조회 성공 시
			
			// 조회수 증가 
			
			int result = dao.increaseReadCount(conn,noticeNo);

			
			if(result > 0) commit(conn);

			notice.setNoticeReadCount(notice.getNoticeReadCount()+ 1);
			
			
			}else {
				rollback(conn);

				
			}
		
		close(conn);

		
		return notice;
	}



	/** 이미지 파일 조회 Service
	 * @param noticeNo
	 * @return fList
	 * @throws Exception
	 */
	public List<NoticeAttachment> selectNoticeFile(int noticeNo) throws Exception {
		
		Connection conn = getConnection();

		List<NoticeAttachment> fList = dao.selectNoticeFiles(conn,noticeNo);

		close(conn);

		
		return fList;
	}



	/** 게시글 등록 Service (게시글 + 파일)
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertNotice(Map<String, Object> map)  throws Exception{

		Connection conn = getConnection();

		int result = 0;
		
		// 번호 얻어오기
		int noticeNo =dao.selectNoticeNextNo(conn);

	if(noticeNo > 0) {
		map.put("noticeNo",noticeNo);
		
		// 2. 글 제목/내용 크로스 사이트 스크립팅 방지 처리
		
		String NoticeTitle =(String)map.get("NoticeTitle");
		String NoticeContent =(String)map.get("NoticeContent");
		
		
		NoticeTitle =replaceParameter(NoticeTitle);
		NoticeContent =replaceParameter(NoticeContent);

		// 3. 글 내용 개행문자  \r\n -> <br> 변경 처리
		NoticeContent.replaceAll("\r\n", "<br>");
		
		// 처리된 내용을 다시 map에 추가
		map.put("NoticeTitle",NoticeTitle);
		map.put("NoticeContent",NoticeContent);
		
  try {
		// 4. 게시글 부분(제목, 내용, 카테고리)만 BOARD 테이블에 삽입하는 DAO 호출
		result = dao.insertNoticeBoard(conn,map);
		
		// 5. 파일 정보 부분만 ATTACHMENT 테이블에 삽입하는 DAO 호출
		List<NoticeAttachment> fList = (List<NoticeAttachment>)map.get("fList");
	
		// 게시글 부분 삽입 성공 && 파일 정보가 있을 경우
		if(result > 0 && !fList.isEmpty()) {
			
			result = 0 ; // result 재활용을 위해 0으로 초기화
			
			// fList의 요소를 하나씩 반복 접근하여
			// DAO 메소드를 반복 호출해 정보를 삽입함.
			for(NoticeAttachment at : fList) {
				
				// 파일 정보가 저장된 Attachment 객체에
				// 해당 파일이 작성된 게시글 번호를 추가 세팅
				at.setNotiParentNo(noticeNo);
				
				result = dao.insertNoticeAttachment(conn,at);
				
				if(result == 0){ // 파일 정보 삽입 실패
				
					// break;  // 보류
			
					// 강제로 예외 발생
					throw new FileInsertFailedException("파일 정보 삽입 실패");
				}
				
			}
			
		}
		
	 }catch(Exception e) {
	 
		 List<NoticeAttachment> fList = (List<NoticeAttachment>)map.get("fList");
		 
		 
		 if(!fList.isEmpty()) {
			 
			 for(NoticeAttachment at : fList ) {
				 
				 String filePath = at.getNotifilePath();
				 String fileName = at.getNotifileName();
				 
				 File deleteFile = new File(filePath + fileName);
				 
				 
				 if(deleteFile.exists()) {
					 // 해당 경로에 해당 파일이 존재하면 
					 deleteFile.delete();
					 
				 	}
			 	}
				 
			 }
		 	// 에러페이지 보여질 수 있도록 catch한 Exception을 Controller로 던져줌
		 
		 	throw e;
		 
		 }// end catch
		 
	 
		// 6. 트랜잭션 처리
  		if(result > 0) {
			commit(conn);
			
			// 삽입 성공 시 상세 조회 화면으로 이동해야되기 때문에
			// 글번호를 반환할 수 있도록 result에 boardNo를 대입
			result = noticeNo;
			
		}else {
			rollback(conn);
		}
		
	}

		close(conn);
		return result;
	}
	

	
	
	private String replaceParameter(String param) {
		
		String result = param;
		
		if(param != null) {
			result = result.replaceAll("&", "&amp;");
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("\"", "&quot;");
			
		}
		
		return result;
		
	}



	/** notice 수정 화면 출력용 Service
	 * @param noticeNo
	 * @return  Notice
	 * @throws Exception
	 */
	public Notice updateNoticeView(int noticeNo) throws Exception{
		
			Connection conn =getConnection();


			Notice notice = dao.selectNoticeBoard(conn,noticeNo);

			
			notice.setNoticeContent(notice.getNoticeContent().replace("<br>", "\r\n"));

			close(conn);

			
		return notice;
	}



	/**Notice 이미지 수정  Servie
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int updateNotice(Map<String, Object> map) throws Exception{
	
		
		Connection conn =getConnection();

		
		int result = 0 ; // service 수행 결과 저장 변수

		
		List<NoticeAttachment> deleteFiles = null; // 삭제할 파일 정보 저장 변수 선언

		String noticeTitle =(String)map.get("noticeTitle");
		String noticeContent =(String)map.get("noticeContent");
		
		noticeTitle =replaceParameter(noticeTitle);
		noticeContent =replaceParameter(noticeContent);
		
		noticeContent.replaceAll("\r\n", "<br>");

		map.put("noticeTitle",noticeTitle);
		map.put("noticeContent",noticeContent);
		
		
		try{
			
	     result = dao.updateNotice(conn,map);

			
			
	     List<NoticeAttachment> newFileList = (List<NoticeAttachment>)map.get("fList");
	
			
		
		if(result > 0 && !newFileList.isEmpty()) {
			
			List<NoticeAttachment> oldFileList = 
					dao.selectNoticeFiles(conn, (int)map.get("noticeNo"));
			
			
			result = 0; // result 재활용
			deleteFiles = new ArrayList<NoticeAttachment>(); 
			
			for(NoticeAttachment newFile : newFileList) {

				
				boolean flag = true;

				for(NoticeAttachment oldFile : oldFileList) {

				
					if(newFile.getNotiFileLevel() == oldFile.getNotiFileLevel()) {

					
						deleteFiles.add(oldFile);

						
						newFile.setNotiFileNo(oldFile.getNotiFileNo());

					
						flag = false;

						break;
						
					}
				}
						
				
				
				if(flag) {

					result = dao.insertNoticeAttachment(conn,newFile);

				}else {

				
					result = dao.updateNoticeAttachment(conn,newFile);
				}
				
				
				if(result == 0) {
					// 강제로 사용자 정의 예외 발생
					throw new FileInsertFailedException("파일 정보 삽입 또는 수정 실패");
				}

				
			}
		}

			
			
		}catch(Exception e) {
			
			
			List<NoticeAttachment> fList = (List<NoticeAttachment>)map.get("fList");

			
			 if(!fList.isEmpty()) {

				 for(NoticeAttachment at : fList ) {

					 String filePath = at.getNotifilePath();
					 String fileName = at.getNotifileName();
					 
					 File deleteFile = new File(filePath + fileName);

					 if(deleteFile.exists()) {

						 deleteFile.delete();

				 	 }
				 	
				 	}

				 }
		
			 	throw e;

		 }// end catch

		
		if(result > 0) {
			
			
			commit(conn);
		
			if(deleteFiles!=null) {

				for (NoticeAttachment at : deleteFiles) {
					
					String filePath = at.getNotifilePath();
					String fileName = at.getNotifileName();
					
					File deleteFile = new File(filePath+fileName);

					if(deleteFile.exists()) {

						deleteFile.delete();
					}
				
				}

		}
			
		}else {
			rollback(conn);
			
		}
		
		return result;
	}



	/** notice 삭제 DAO
	 * @param noticeNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteNotice(int noticeNo) throws Exception {
		
		Connection conn = getConnection();

		int result = dao.deleteNotice(conn,noticeNo);

		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}

		close(conn);
		
		
		return result;
	}
	

}

