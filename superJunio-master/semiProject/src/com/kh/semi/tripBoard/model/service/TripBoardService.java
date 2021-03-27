package com.kh.semi.tripBoard.model.service;

import static com.kh.semi.common.JDBCTemplate.*;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kh.semi.freeBoard.model.exception.FileInsertFailedException;
import com.kh.semi.freeBoard.model.vo.Attachment;
import com.kh.semi.freeBoard.model.vo.FreeReport;
import com.kh.semi.tripBoard.model.dao.TripBoardDAO;
import com.kh.semi.tripBoard.model.vo.PageInfo;
import com.kh.semi.tripBoard.model.vo.TripBoard;
import com.kh.semi.tripBoard.model.vo.TripReport;

public class TripBoardService {
	
	TripBoardDAO dao = new TripBoardDAO();

	
	
	/** 페이징 처리를 위한 현재 페이지값 얻어오는 Service
	 * @param cp
	 * @return
	 * @throws Exception
	 */
	public PageInfo getPageInfo(String cp) throws Exception{
		Connection conn = getConnection();
		
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);
		
		int listCount = dao.getListCount(conn);
		
		close(conn);
		
		return new PageInfo(currentPage, listCount);
		
	}



	/** 전체 게시글 목록 조회 Service
	 * @param pInfo
	 * @return
	 * @throws Exception
	 */
	public List<TripBoard> selectBoardList(PageInfo pInfo) throws Exception{
		Connection conn = getConnection();
		
		List<TripBoard> tList = dao.selectBoardList(conn, pInfo);
		
		close(conn);
		
		return tList;
	}



	/** 게시글 상세조회 Service
	 * @param boardNo
	 * @return 
	 * @throws Exception
	 */
	public TripBoard selectBoard(int boardNo) throws Exception{
		Connection conn = getConnection();
		
		TripBoard board = dao.selectBoard(conn, boardNo);
		
		if(board != null) {
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
	 * @return
	 * @throws Exception
	 */
	public int insertBoard(Map<String, Object> map) throws Exception{
		Connection conn = getConnection();
		
		int result = 0;
		
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
			
			result = dao.insertBoard(conn, map);
			
			List<Attachment> trList =  (List<Attachment>)map.get("trList");
			
			if(result > 0 && !trList.isEmpty()) {
				
				result = 0;	// result 재활용을 위해 0으로 초기화
				
				// fList의 요소를 하나씩 반복 접근하여
				// DAO 메소드를 반복 호출해 정보를 삽입함
				for(Attachment at : trList) {
					
					// 파일 정보가 저장된 Attachment 객체에
					// 해당 파일이 작성된 게시글 번호를 추가 세팅
					at.setParentBoardNo(boardNo);
					
					result = dao.insertAttachment(conn, at);
					
					if(result == 0) {	
						
						throw new FileInsertFailedException("파일정보 삽입 실패");
					}
				}
				
			}
			
		}catch(Exception e) {
			
			List<Attachment> trList = (List<Attachment>)map.get("trList");
			
			if(!trList.isEmpty()) {
				
				for(Attachment at : trList) {
					
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



	/** 파일 정보 상세보기 Service
	 * @param boardNo
	 * @return
	 * @throws Exception
	 */
	public List<Attachment> selectBoardFiles(int boardNo) throws Exception{
		Connection conn = getConnection();
		
		List<Attachment> trList = dao.selectBoardFiles(conn, boardNo);
		
		close(conn);
		
		return trList;
	}



	/** 썸네일 얻어오는 Service
	 * @param pInfo
	 * @return
	 * @throws Exception
	 */
	public List<Attachment> selectThumbnailList(PageInfo pInfo) throws Exception{
		Connection conn = getConnection();
		
		List<Attachment> trList = dao.selectThumbnailList(conn, pInfo);
		
		close(conn);
		
		return trList;
	}



	/** 게시글 수정을 위한 정보 Service
	 * @param boardNo
	 * @return
	 * @throws Exception
	 */
	public TripBoard updateView(int boardNo) throws Exception{
		Connection conn = getConnection();
		
		TripBoard board = dao.selectBoard(conn, boardNo);
		
		board.setBoardContent(board.getBoardContent().replaceAll("<br>", "\r\n"));
		
		close(conn);
		
		return board;
	}



	/** 게시글 수정 Service
	 * @param map
	 * @return
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
			
			List<Attachment> newFileList = (List<Attachment>)map.get("trList");
			
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
			
			
			
		}catch (Exception e) {
			
			List<Attachment> trList = (List<Attachment>)map.get("trList");
			
			if(!trList.isEmpty()) {
			
				for(Attachment at : trList) {
					
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



	/** 게시글 삭제 Service
	 * @param boardNo
	 * @return
	 * @throws Exception
	 */
	public int delete(int boardNo) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.delete(conn, boardNo);
		
		if(result > 0)		commit(conn);
		else				rollback(conn);
		
		return result;
	}



	/** 여행 후기 게시판 신고
	 * @param tReport
	 * @return
	 * @throws Exception
	 */
	public int tripReport(TripReport tReport) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.tripReport(conn, tReport);
		
		if(result > 0) {
			int reportNum = tReport.getFreeBoardNo();
			int result2 = dao.reportNum(conn, reportNum);
			
			if(result2 > 0)		commit(conn);
			else				rollback(conn);
			
		}
		close(conn);
		
		return result;
	}


}
