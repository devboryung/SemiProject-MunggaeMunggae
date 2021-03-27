package com.kh.semi.CSCenter.model.service;
import static com.kh.semi.common.JDBCTemplate.*;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kh.semi.CSCenter.model.dao.QnaDAO;
import com.kh.semi.CSCenter.model.exception.FileInsertFailedException;
import com.kh.semi.CSCenter.model.vo.Faq;
import com.kh.semi.CSCenter.model.vo.PageInfo;
import com.kh.semi.CSCenter.model.vo.Qna;
import com.kh.semi.CSCenter.model.vo.QnaAttachment;

public class QnaService {

	
	private QnaDAO dao = new QnaDAO();
	
	/** 페이징 처리를 위한 값 계산 Service
	 * @param cp
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getPageInfo(String cp)  throws Exception{
		
		Connection conn = getConnection();

		
		int currentPage = cp ==null ? 1:Integer.parseInt(cp);
		
		// DB에서 전체 게시글 수를 조회하여 반환 받기
		int listCount = dao.getListCount(conn);
		
		close(conn);
		
		
		return new PageInfo(currentPage,listCount);
	}
	
	/** qna 공개된 글 조회 
	 * @param cp
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getPagePublicInfo(String cp) throws Exception {
		
		
		Connection conn = getConnection();

		
		int currentPage = cp ==null ? 1:Integer.parseInt(cp);
		
		// DB에서 전체 게시글 수를 조회하여 반환 받기
		int listCount = dao.getListPublicCount(conn);
		
		close(conn);
		
		
		return new PageInfo(currentPage,listCount);
		
		
	}
	
	/** 처리되지 않은 글 목록 조회
	 * @param cp
	 * @return  pInfo
	 * @throws Exception
	 */
	public PageInfo getPageIncompleteInfo(String cp) throws Exception {

		Connection conn = getConnection();

		int currentPage = cp ==null ? 1:Integer.parseInt(cp);
		
		int listCount = dao.getListIncompleteCount(conn);

		close(conn);

		return new PageInfo(currentPage,listCount);
	}
	
	/** 나의 문의 내역 조회
	 * @param cp
	 * @param memNo
	 * @return pInfo
	 * @throws Exception
	 */
	public PageInfo getPageMyQnaInfo(String cp, int memNo)  throws Exception{
		
		Connection conn = getConnection();

		int currentPage = cp ==null ? 1:Integer.parseInt(cp);
		
		int listCount = dao.getPageMyQnaInfoCount(conn ,memNo);

		close(conn);

		
		return new PageInfo(currentPage,listCount);
	}


	/** Qna 게시글 목록 조회
	 * @param pInfo
	 * @return bList
	 * @throws Exception
	 */
	public List<Qna> selectQnaList(PageInfo pInfo) throws Exception {
		
		Connection conn = getConnection();

		
		List<Qna> bList = dao.selectQnaList(conn,pInfo);

		
		close(conn);
		return bList;
	}
	
	
	/** qna 공개된 글 만 조회
	 * @param pInfo
	 * @return bList
	 * @throws Exception
	 */
	public List<Qna> selectQnaPublicList(PageInfo pInfo)  throws Exception{
	
		Connection conn = getConnection();

		List<Qna> bList = dao.selectQnaPublicList(conn,pInfo);
		
		close(conn);
		return bList;
	}
	
	
	/** Qna 처리되지 않은 글 목록 조회
	 * @param pInfo
	 * @return bList
	 * @throws Exception
	 */
	public List<Qna> selectQnaIncompleteList(PageInfo pInfo)  throws Exception{
		
		Connection conn = getConnection();

		List<Qna> bList = dao.selectIncompleteList(conn,pInfo);
		
		close(conn);
		return bList;
	}
	
	
	
	/** 나의 문의 내역 목록 조회
	 * @param pInfo
	 * @param memNo 
	 * @return bList
	 * @throws Exception
	 */
	public List<Qna> selectMyQnaList(PageInfo pInfo, int memNo) throws Exception {
		
		Connection conn = getConnection();

		List<Qna> bList = dao.selectMyQnaList(conn,pInfo ,memNo);
		
		close(conn);
		
		return bList;
	}

	
	
	
	
	
	

	/** Qna 게시글 상세 조회 Service
	 * @param qnaNo
	 * @return
	 * @throws Exception
	 */
	public Qna selectQna(int qnaNo) throws Exception{
		
		Connection conn = getConnection();

		Qna qna = dao.selectQna(conn,qnaNo);
		
		if(qna != null) {
			
			// 조회수 증가
			int result = dao.increaseReadCount(conn,qnaNo);

			if(result > 0) commit(conn);

			qna.setQnaReadCount(qna.getQnaReadCount() + 1);

		}else {
			rollback(conn);
		}

		
		close(conn);
		
		return qna;
	}
	
	

	/** Qna 등록 Service (게시글 + 이미지)
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertQna(Map<String, Object> map) throws Exception {
		
		Connection conn = getConnection();

		int result = 0;
		
		// qna 번호 얻어오기
		
		int qnaNo = dao.selectQnaNextNo(conn);
		
		if(qnaNo > 0) {
			
			// 얻어온 게시글 번호 map에 추가
			map.put("qnaNo",qnaNo);

			// 크로스 사이트 스크립팅 방지 처리
			String qnaTitle =(String)map.get("qnaTitle");
			String qnaContent =(String)map.get("qnaContent");
			
			qnaTitle =replaceParameter(qnaTitle);
			qnaContent =replaceParameter(qnaContent);
			
			qnaContent.replaceAll("\r\n","<br>");

			// 처리된 내용 다시 담기
			map.put("qnaTitle",qnaTitle);
			map.put("qnaContent",qnaContent);
			
		
		try {
			// 이미지 제외 테이블삽입 DAO 호출
			result = dao.insertQna(conn,map);
			// 파일 정보 부분만 테이블에 삽입하는 DAO 호출
			List<QnaAttachment> fList = (List<QnaAttachment>)map.get("fList");
			
			
			if(result > 0 && !fList.isEmpty()){
				
				result = 0 ; // 재활용
				
			for(QnaAttachment at : fList) {
					
					// 파일 정보가 저장된 Attachment 객체에
					// 해당 파일이 작성된 게시글 번호를 추가 세팅
					at.setParentQnaNo(qnaNo);
					
					result = dao.insertQnaAttachment(conn,at);
					
					if(result == 0){ // 파일 정보 삽입 실패
					
						// break;  // 보류
				
						// 강제로 예외 발생
						throw new FileInsertFailedException("파일 정보 삽입 실패");
					}
					
				}	
				
			}
			
		}catch(Exception e) {
			
			List<QnaAttachment> fList = (List<QnaAttachment>)map.get("fList");

			if(!fList.isEmpty()) {
				 
				 for(QnaAttachment at : fList ) {
					 
					 String filePath = at.getQnaImgPath();
					 String fileName = at.getQnaImgName();
					 
					 File deleteFile = new File(filePath + fileName);
					 
					 
					 if(deleteFile.exists()) {
						 // 해당 경로에 해당 파일이 존재하면 
						 deleteFile.delete();
						 
					 	}
				 	}
					 
				 }
			
			throw e;
		}
		
  		if(result > 0) {
			commit(conn);
			
			// 삽입 성공 시 상세 조회 화면으로 이동해야되기 때문에
			// 글번호를 반환할 수 있도록 result에 boardNo를 대입
			result = qnaNo;
			
		}else {
			rollback(conn);
		}
		
		
	}
		close(conn);
		
		return result;
	
	}
	
	

	
	
	// 크로스 사이트 스크립팅 방지 코드 메소드
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

		/** qna 게시글에 포함된 이미지 목록 조회 service
		 * @param qnaNo
		 * @return fList
		 */
		public List<QnaAttachment> selectQnaFile(int qnaNo) throws Exception {
			
			Connection conn = getConnection();

			List<QnaAttachment> fList = dao.selectQnaFiles(conn,qnaNo);

			close(conn);

			
			return fList;
		}

	
	

		/** qna 수정 화면 출력용 Service
		 * @param qnaNo
		 * @return Qna
		 * @throws Exception
		 */
		public Qna updateQnaview(int qnaNo) throws Exception {
	
			Connection conn =getConnection();

			
			Qna qna = dao.selectQna(conn,qnaNo);
			
			
			qna.setQnaContent(qna.getQnaContent().replace("<br>", "\r\n"));

			
			close(conn);
			
			
			return qna;
		}

		/** Qna 게시글 수정 Dao
		 * @param map
		 * @return result
		 * @throws Excetption
		 */
		public int updateQna(Map<String, Object> map) throws Exception{
			
			Connection conn =getConnection();

			int result = 0 ;
			
			
			List<QnaAttachment> deleteFiles = null; // 삭제할 파일 정보 저장 변수 선언

			String qnaTitle =(String)map.get("qnaTitle");
			String qnaContent =(String)map.get("qnaContent");
			
			qnaTitle =replaceParameter(qnaTitle);
			qnaContent =replaceParameter(qnaContent);
			
			qnaContent.replaceAll("\r\n", "<br>");

			
			map.put("qnaTitle",qnaTitle);			
			map.put("qnaContent",qnaContent);

			
			
			try {
				// 게시글 부분 수정 DAO
				result = dao.updateQna(conn,map);

				
				// 수정 화면에서 새로운 이미지가 업로드된 파일 정보만을 담고있다.
				List<QnaAttachment> newFileList = (List<QnaAttachment>)map.get("fList");

				
				
				if(result > 0 && !newFileList.isEmpty()) {
				
				// DB에서 해당 게시글의 수정 전 파일목록을 조회함
				List<QnaAttachment> oldFileList = 
						dao.selectQnaFiles(conn, (int)map.get("qnaNo"));
					
				result = 0; // result 재활용

				
				deleteFiles = new ArrayList<QnaAttachment>(); // 삭제된 파일 정보 저장 List

				
				for(QnaAttachment newFile : newFileList) {
					
					// flag가 false인 경우 : 새 이미지와 기존 이미지의 파일 레벨이 중복되는 경우 - > update
					// flag가 true 인 경우 : 새 이미지와 기존 이미지의 파일 레벨이 중복되지 않는 경우 - > insert
					boolean flag = true;

					// 기존 이미지 정보 반복 접근
					for(QnaAttachment oldFile : oldFileList) {

					
						if(newFile.getQnaImgLevel() == oldFile.getQnaImgLevel()) {
	
							
							// 기존파일을 삭제 리스트에 추가
							deleteFiles.add(oldFile);

							
							//새 이미지 정보에 이전 파일 번호를 추가 -> 파일 번호를 이용한 수정
							newFile.setQnaImgNo(oldFile.getQnaImgNo());
							
							
							flag =false;
							
							break;
							
							
						}
					
					}
				
				
					// flag 값에 따라 파일 정보 insert 또는 update 수행

					if(flag) {
						
						result = dao.insertQnaAttachment(conn,newFile);
						
					}else {
						result = dao.updateQnaAttachment(conn,newFile);
					}
					
					
					// 파일 정보 삽입 또는 수정 중 실패 시
					if(result == 0) {
						
						throw new FileInsertFailedException("파일 정보 삽입 또는 수정 실패");

					}
					
					}
			
				
				
			}
				
				
				
				
			} catch (Exception e) {
				
				// 게시글 수정 중 실패 또는 오류 발생 시
				// 서버에 미리 저장되어있던 이미지 파일 삭제
			
			
				 List<QnaAttachment> fList = (List<QnaAttachment>)map.get("fList");
				 
				 
				 if(!fList.isEmpty()) {
					 
					 for(QnaAttachment at : fList ) {
						 
						 String filePath = at.getQnaImgPath();
						 String fileName = at.getQnaImgName();
						 
						 File deleteFile = new File(filePath + fileName);
						 
						 
						 if(deleteFile.exists()) {
							 // 해당 경로에 해당 파일이 존재하면 
							 deleteFile.delete();
							 
						 	 }
					     }
						 
					 }
				 	// 에러페이지 보여질 수 있도록 catch한 Exception을 Controller로 던져줌
				 
				 	throw e;
				}
			
			// 5. 트랜잭션 처리 및 삭제 목록에 있는 파일 삭제
			if(result > 0) {
				commit(conn);
				
				//DB 정보와 맞지 않는 파일 (deleteFiles) 삭제 진행
				if(deleteFiles!=null) {
			
					for (QnaAttachment at : deleteFiles) {
					
						String filePath = at.getQnaImgPath();
						String fileName = at.getQnaImgName();
						
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
		
		

		/** Qna 게시글 삭제
		 * @param qnaNo
		 * @return result
		 * @throws Exception
		 */
		public int deleteQna(int qnaNo) throws Exception {
	
			Connection conn = getConnection();
			
			int result = dao.deleteQna(conn,qnaNo);

			
			if(result > 0) {
				commit(conn);
			}else {
				rollback(conn);
			}
			close(conn);
			
			
			return result;
		}



}
