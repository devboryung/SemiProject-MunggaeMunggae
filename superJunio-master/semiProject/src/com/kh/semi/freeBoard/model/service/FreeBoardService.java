package com.kh.semi.freeBoard.model.service;

import static com.kh.semi.common.JDBCTemplate.*;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kh.semi.freeBoard.model.dao.FreeBoardDAO;
import com.kh.semi.freeBoard.model.exception.FileInsertFailedException;
import com.kh.semi.freeBoard.model.vo.Attachment;
import com.kh.semi.freeBoard.model.vo.FreeBoard;
import com.kh.semi.freeBoard.model.vo.FreeReport;
import com.kh.semi.freeBoard.model.vo.PageInfo;

public class FreeBoardService {
	
	FreeBoardDAO dao = new FreeBoardDAO();
	
	
	public PageInfo getPageInfo(String cp) throws Exception{
		Connection conn = getConnection();
		
		// cp가 null일 경우
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);
		
		int listCount = dao.getListCount(conn);
		
		close(conn);
		
		return new PageInfo(currentPage, listCount);
	}


	/** 게시글 목록 조회
	 * @param pInfo
	 * @return bList
	 * @throws Exception
	 */
	public List<FreeBoard> selectBoardList(PageInfo pInfo) throws Exception{
		Connection conn = getConnection();
		
		List<FreeBoard> bList = dao.selectBoardList(conn, pInfo);
		
		close(conn);
		
		return bList;
	}


	/** 게시글 상세조회 Service
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public FreeBoard selectBoard(int boardNo) throws Exception{
		Connection conn = getConnection();
		
		FreeBoard board = dao.selectBoard(conn, boardNo);
		
			if(board != null) {
			
			// 조회수 증가
			int result = dao.increaseReadCount(conn, boardNo);
			
				if(result > 0) {		
					commit(conn);
					
					board.setReadCount(board.getReadCount() + 1);
					
				}
				else {
					rollback(conn);
				}
		}
		
		close(conn);
		
		
		return board;
	}


	/** 게시글 등록 Service
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertBoard(Map<String, Object> map) throws Exception{

		Connection conn = getConnection();
		
		int result = 0;
		
		// 1. 게시글 번호 얻어오기
		int boardNo = dao.selectNextNo(conn);
		
		if(boardNo > 0) {
			map.put("boardNo", boardNo);
			
			String boardTitle = (String)map.get("boardTitle");
			String boardContent = (String)map.get("boardContent");
			
			boardTitle = replaceParameter(boardTitle);
			boardContent = replaceParameter(boardContent);
			
			
			// 3. 글 내용 개행문자 \r\n -> <br> 변경처리
			 boardContent = boardContent.replaceAll("\r\n", "<br>");
			
			// 처리된 내용을 다시 map에 추가
			map.put("boardTitle", boardTitle);
			map.put("boardContent", boardContent);
			
			
			try {
				// 4. 게시글 부분(제목, 내용, 카테고리)만 BOARD 테이블에 삽입하는 DAO 호출
				result = dao.insertBoard(conn, map);
	
				
				// 5. 파일 정보 부분만 ATTACHMENT 테이블에 삽입하는 DAO 호출
				List<Attachment> fList =  (List<Attachment>)map.get("fList");
				
				// 게시글 부분 삽입 성공 && 파일 정보가 있을 경우
				if(result > 0 && !fList.isEmpty()) {
					
					result = 0;	// result 재활용을 위해 0으로 초기화
					
					// fList의 요소를 하나씩 반복 접근하여
					// DAO 메소드를 반복 호출해 정보를 삽입함
					for(Attachment at : fList) {
						
						// 파일 정보가 저장된 Attachment 객체에
						// 해당 파일이 작성된 게시글 번호를 추가 세팅
						at.setParentBoardNo(boardNo);
						
						result = dao.insertAttachment(conn, at);
						
						if(result == 0) {	// 파일정보 삽입 실패
//							break; 보류
							
							// 강제로 예외 발생
							throw new FileInsertFailedException("파일정보 삽입 실패");
						}
					}
					
				}
			
			}catch (Exception e) {
				// 4,5 번에 대한 추가 작업
				// 게시글 또는 파일 정보 삽입 중 에러 발생 시
				// 서버에 저장된 파일을 삭제하는 작업이 필요.
				
				List<Attachment> fList = (List<Attachment>)map.get("fList");
				
				if(!fList.isEmpty()) {
				
					for(Attachment at : fList) {
						
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
				
				// 삽입 성공 시 상세 조회 화면으로 이동해야되기 때문에
				// 글 번호를 반환할 수 있도록 result에 boardNo를 대입
				result = boardNo;
		
			}else {
				rollback(conn);
			}
			
		}		
			close(conn);
			
			return result;
	}
	
	
	
	
	
	
	
	// 크로스 사이트 스크립팅 방지 메소드
		private String replaceParameter(String param) {
			String result = param;
			
			if(param != null) {
				result = result.replaceAll("&", "&amp");
				result = result.replaceAll("<", "&lt");
				result = result.replaceAll(">", "&gt");
				result = result.replaceAll("\"", "&quot");
			}
			
			return result;
		}


		/** 상세조회페이지 이미지 보여주는 Service
		 * @param boardNo
		 * @return fList
		 * @throws Exception
		 */
		public List<Attachment> selectBoardFiles(int boardNo) throws Exception{
			Connection conn = getConnection();
			
			List<Attachment> fList = dao.selectBoardFiles(conn, boardNo);
			
			close(conn);
			
			
			return fList;
		}


		/** 게시글 수정 화면 전환 Service
		 * @param boardNo
		 * @return 
		 * @throws Exception
		 */
		public FreeBoard updateView(int boardNo) throws Exception{
			Connection conn = getConnection();
			
			FreeBoard board = dao.selectBoard(conn, boardNo);
			
			board.setBoardContent(board.getBoardContent().replaceAll("<br>", "\r\n"));
			
			close(conn);
			
			return board;
		}
		
		


		/** 게시글 수정 Service
		 * @param map
		 * @return result
		 * @throws Exception
		 */
		public int updateBoard(Map<String, Object> map) throws Exception{
			Connection conn = getConnection();
			
			int result = 0;
			List<Attachment> deleteFiles = null;
			
			
			String boardTitle = (String)map.get("boardTitle");
			String boardContent = (String)map.get("boardContent");
			
			boardTitle = replaceParameter(boardTitle);
			boardContent = replaceParameter(boardContent);
			
			boardContent = boardContent.replaceAll("\r\n", "<br>");
			
			map.put("boardTitle", boardTitle);
			map.put("boardContent", boardContent);
			
			try {
				
				result = dao.updateBoard(conn, map);
				
				List<Attachment> newFileList = (List<Attachment>)map.get("fList");
				
				if(result > 0 && !newFileList.isEmpty()) {
					List<Attachment> oldFileList 
					= dao.selectBoardFiles(conn, (int)map.get("boardNo"));
				
					result = 0;
					deleteFiles = new ArrayList<Attachment>();
					
					for(Attachment newFile : newFileList) {
						boolean flag = true;
						
						for(Attachment oldFile : oldFileList) {
							
							if(newFile.getFileLevel() == oldFile.getFileLevel()) {
								deleteFiles.add(oldFile);
								
								newFile.setFileNo(oldFile.getFileNo());
								
								flag = false;
								break;
							}
						}
						
						if(flag) {
							result = dao.insertAttachment(conn, newFile);
						}else {
							result = dao.updateBoard(conn, newFile);
						}
						
						if(result == 0) {
							throw new FileInsertFailedException("파일 정보 삽입 또는 수정 실패");
							
						}
					}
				}
				
			}catch(Exception e) {
				List<Attachment> fList = (List<Attachment>)map.get("fList");
				
				if(!fList.isEmpty()) {
				
					for(Attachment at : fList) {
						
						String filePath = at.getFilePath();
						String fileName = at.getFileName();
						
						File deleteFile = new File(filePath + fileName);
						// 파일 경로가 나옴
						
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
				
				if(deleteFiles != null) {
				
				// DB 정보와 맞지 않는 파일 (deleteFiles) 삭제 진행
				for(Attachment at : deleteFiles) {
					
					String filePath = at.getFilePath();
					String fileName = at.getFileName();
					
					File deleteFile = new File(filePath + fileName);
					
					if(deleteFile.exists()) {
						deleteFile.delete();
					}
				}
				
			}
			}else {
				rollback(conn);
			}
			
			close(conn);
			return result;
			
		}


		/** 게시글 삭제여부 변겅 Service
		 * @param boardNo
		 * @return result
		 * @throws Exception
		 */
		public int delete(int boardNo) throws Exception{
			Connection conn = getConnection();
			
			int result = dao.delete(conn, boardNo);
			
			if(result > 0)		commit(conn);
			else				rollback(conn);
			close(conn);
			
			return result;
		}


		/** 게시글 신고
		 * @param fReport
		 * @return
		 * @throws Exception
		 */
		public int freeReport(FreeReport fReport) throws Exception{
			Connection conn = getConnection();
			
			int result = dao.freeReport(conn, fReport);
			
			if(result > 0) {
				int reportNum = fReport.getFreeBoardNo();
				int result2 = dao.reportNum(conn, reportNum);
				
				if(result2 > 0)		commit(conn);
				else				rollback(conn);
				
			}
			close(conn);
			
			return result;
		}
}
				
			
