package com.kh.semi.CSCenter.model.service;
import static com.kh.semi.common.JDBCTemplate.*;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kh.semi.CSCenter.model.dao.FaqDAO;
import com.kh.semi.CSCenter.model.exception.FileInsertFailedException;
import com.kh.semi.CSCenter.model.vo.Faq;
import com.kh.semi.CSCenter.model.vo.FaqAttachment;
import com.kh.semi.CSCenter.model.vo.PageInfo;


public class FaqService {

	private FaqDAO dao = new FaqDAO();
	
	
	/** faq 목록 조회
	 * @param pInfo 
	 * @return bList
	 * @throws Exception
	 */
	public List<Faq> selectFaqList(PageInfo pInfo) throws Exception {
		Connection conn = getConnection();
 
		List<Faq> bList = dao.selectFaqList(conn,pInfo);

		
		close(conn);
		return bList;
	}


	/** faq 글 등록 
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertFaq(Map<String, Object> map) throws Exception{
	
		Connection conn =getConnection();
		
		int result = 0;
		
		// faq 게시글 번호 얻어오기
		int faqNo = dao.selectFaqNextNo(conn);
		
		if(faqNo > 0) {
			
			map.put("faqNo",faqNo);
			
			// 크로스 사이트 스크립팅 방지 처리
			
			String faqTitle =(String)map.get("faqTitle");
			String faqContent =(String)map.get("faqContent");
			
			faqTitle =replaceParameter(faqTitle);
			faqContent =replaceParameter(faqContent);

			// 3. 글 내용 개행문자  \r\n -> <br> 변경 처리
			faqContent.replaceAll("\r\n", "<br>");
			
			map.put("faqTitle",faqTitle);
			map.put("faqContent",faqContent);
			
		
		
		try {
			
			result = dao.insertFaq(conn,map);
			
			List<FaqAttachment> fList = (List<FaqAttachment>)map.get("fList");

			
			if(result > 0 && !fList.isEmpty()) {
				
				result = 0;
				
				
				
				for(FaqAttachment at : fList) {
					
					at.setParentfaqNo(faqNo);
					

					result = dao.insertFaqAttachment(conn,at);

					
					if(result == 0) {
						
						
						throw new FileInsertFailedException("파일 정보 삽입 실패");

						
					}
					
				}
				
			}
			
			
		} catch(Exception e) {
			
		 List<FaqAttachment> fList = (List<FaqAttachment>)map.get("fList");

			
		 if(!fList.isEmpty()) {
			 
			 for(FaqAttachment at : fList ) {
				 
				 String filePath = at.getFaqFilePath();
				 String fileName = at.getFaqFileName();
				 
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
			result = faqNo;
			
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


	/** 페이징 처리를 위한 값 계산 Service
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


	/** 게시글 상세조회 Service 
	 * @param faqNo
	 * @return
	 * @throws Exception
	 */
	public Faq selectFaqView(int faqNo) throws Exception {
		
		Connection conn = getConnection();
		
		Faq faq = dao.selectFaqView(conn,faqNo);
		
		if(faq != null) {
			
			int result = dao.increaseReadCount(conn,faqNo);
			
			
			if(result > 0) commit(conn);
			
			
			faq.setFaqReadCount(faq.getFaqReadCount()+1);
			
		}else {
			
			rollback(conn);
		}
		
		close(conn);
		
		
		
		return faq;
	}


	/** 게시글에 포함된 이미지 조회 Service
	 * @param faqNo
	 * @return fList
	 * @throws Exception
	 */
	public List<FaqAttachment> selectFaqFile(int faqNo) throws Exception{
		
		Connection conn = getConnection();
		
		List<FaqAttachment> fList = dao.selectFaqFiles(conn,faqNo);
		
		close(conn);

		
		return fList;
	}


	/** faq 수정 화면 출력용 service
	 * @param faqNo
	 * @return faq
	 * @throws Exception
	 */
	public Faq faqUpdateView(int faqNo) throws Exception {
 
		Connection conn = getConnection();
		
		Faq faq = dao.selectFaqView(conn,faqNo);
		
		// 개행문자 변경
		faq.setFaqContent(faq.getFaqContent().replace("<br>", "\r\n"));
		
		close(conn);
		
		return faq;
	}


	/** faq 수정 Service
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int updateFaq(Map<String, Object> map) throws Exception {
		
			Connection conn = getConnection();
			
			int result = 0;
			
			
			List<FaqAttachment> deleteFaqFiles = null;
			
			
			String faqTitle =(String)map.get("faqTitle");
			String faqContent =(String)map.get("faqContent");
						
			
			faqTitle =replaceParameter(faqTitle);
			faqContent =replaceParameter(faqContent);

			
			faqContent.replace("\r\n", "<br>");
			
			map.put("faqTitle",faqTitle);
			map.put("faqContent",faqContent);
			
		
			try {
				
				result = dao.updateFaq(conn,map);
				
				
				List<FaqAttachment> newFaqFileList = (List<FaqAttachment>)map.get("fList");

				
				if(result > 0 && !newFaqFileList.isEmpty()) {
					
					
					// 이전 파일
					List<FaqAttachment> oldFaqFileList = 
							dao.selectFaqFiles(conn, (int)map.get("faqNo"));
					
					
					
					result = 0; // 재활용
					
					
					deleteFaqFiles = new ArrayList<FaqAttachment>();
					
						
					for(FaqAttachment newFile : newFaqFileList) {
						
						
						// flag가 false인 경우 : 새 이미지와 기존 이미지의 파일 레벨이 중복 - > update
						// flag가 true 인 경우 : 새 이미지와 기존 이미지의 파일 레벨이 중복되지 않는 경우 - > insert
						
						boolean flag = true;
						
						
						for(FaqAttachment oldFile : oldFaqFileList ) {
							
							
							if(newFile.getFaqFileLevel() == oldFile.getFaqFileLevel()) {
								
								// 중복되는 기존 파일 삭제 list에 추가
								deleteFaqFiles.add(oldFile);
								
								//새 이미지 정보에 이전 파일 번호를 추가 -> 파일 번호를 이용한 수정
								newFile.setFaqFileNo(oldFile.getFaqFileNo());
						
								flag = false;

								break;
							}
							
							
						}
						
						// flag 값에 따라 파일 정보 insert 또는 update 수행
						if(flag) {
							
							result= dao.insertFaqAttachment(conn,newFile);
							
							
						}else {
							result= dao.updateFaqAttachment(conn,newFile);
							
						}
						
						// 파일 정보 삽입 또는 수정 중 실패 시
						if(result == 0) {
							// 강제로 사용자 정의 예외 발생
							throw new FileInsertFailedException("파일 정보 삽입 또는 수정 실패");
						}
					
					
					}
					
					
				}

				
			} catch (Exception e) {
				// 오류 발생 시 서버에 저장되어있던 이미지 삭제
				
				List<FaqAttachment> fList = (List<FaqAttachment>)map.get("fList");
				
				
				 if(!fList.isEmpty()) {
					 
					 for(FaqAttachment at : fList ) {
						 
						 String filePath = at.getFaqFilePath();
						 String fileName = at.getFaqFileName();
						 
						 File faqDeleteFile = new File(filePath + fileName);
						 
						 
						 if(faqDeleteFile.exists()) {
							
							 faqDeleteFile.delete();
							 
						 	 }
					     }
						 
					 }
				
				 throw e;
				
			} // catch end
			
			
			// 트랜잭션 처리 및 삭제 목록에 있는 파일 삭제
		  if(result > 0) {
			  
			  commit(conn);
			  
			  
			  // DB 정보와 맞지 않는 파일 (deleteFiles) 삭제 진행
			  
			  if(deleteFaqFiles!=null) {
				  
				  
				  for(FaqAttachment at : deleteFaqFiles) {
					  
					  String filePath = at.getFaqFilePath();
					  String fileName = at.getFaqFileName();
					  
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


	/** FAQ 글 삭제
	 * @param faqNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteFaq(int faqNo) throws Exception{
	
		Connection conn = getConnection();

		int result = dao.deleteFaq(conn,faqNo);


		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}

		close(conn);
		
		
		
		return result;
	}

}
