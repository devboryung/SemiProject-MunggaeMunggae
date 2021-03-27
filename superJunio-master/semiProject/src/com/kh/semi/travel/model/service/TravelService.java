package com.kh.semi.travel.model.service;

import static com.kh.semi.common.JDBCTemplate.*;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kh.semi.freeBoard.model.vo.Attachment;
import com.kh.semi.hospital.model.vo.Hospital;
import com.kh.semi.hospital.model.vo.PageInfo;
import com.kh.semi.travel.model.dao.TravelDAO;
import com.kh.semi.travel.model.exception.FileInsertFailedException;
import com.kh.semi.travel.model.vo.Travel;
import com.kh.semi.travel.model.vo.TravelAttachment;

public class TravelService {
	
	private TravelDAO dao = new TravelDAO();

	/** 페이징 처리를 위한 값 계산 Service
	 * @param cp
	 * @return PageInfo
	 * @throws Exception
	 */
	public PageInfo getPageInfo(String cp) throws Exception {
		Connection conn = getConnection();
		
		// cp가 null일 경우 1, 아니면 cp를 얻어옴.
		int currentPage = (cp == null || cp.equals(""))? 1: Integer.parseInt(cp);
		
		// DB에서 전체 게시글 수를 조회하여 반환받기
		int listCount = dao.getListCount(conn);
		
		close(conn);
		
		return new PageInfo(currentPage, listCount);
	}

	/** 여행 지역정보 목록 조회 Service 
	 * @param pInfo
	 * @return tList
	 * @throws Exception
	 */
	public List<Travel> selectTravelList(PageInfo pInfo) throws Exception {
		Connection conn = getConnection();
		
		List<Travel> tList = dao.selectTravelList(conn, pInfo);
		
		close(conn);
		return tList;
	}

	/** 지역정보 글 상세 조회 Service 
	 * @param travelNo
	 * @return
	 * @throws Exception
	 */
	public Travel selectTravel(int travelNo) throws Exception {
		// 1) 커넥션 얻어오기
		Connection conn = getConnection();
		
		// 2) DAO 수행 후 결과 반환
		Travel travel = dao.selectTravel(conn, travelNo);
		System.out.println(travel);
		// 3) 공지사항 조회 성공 시 조회수 증가
		if( travel != null) {
			int result = dao.increaseReadCount(conn, travelNo);
			
			// 트랜잭션 처리
			if(result > 0) {
				commit(conn);
				
				// 이미 조회된 notice에서 readCount 값을 1 증가
				travel.setTravelReadCount( travel.getTravelReadCount() + 1 );
			} else {
				rollback(conn);
			}
		}
		// 4) 커넥션 반환
		close(conn);
		
		return travel;
	}

	/** 게시글 등록 Service 
	 * @param map
	 * @return result 
	 * @throws Exception
	 */
	public int insertBoard(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();
		
		int result = 0; 
		
		// 1. 게시글 번호 얻어오기
		int travelNo = dao.selectNextNo(conn);
		
		if(travelNo > 0) {
			// 얻어온 게시글 번호를 map에 추가(게시글, 파일정보 삽입 DAO에서 사용하기 위해)
			map.put("travelNo", travelNo);
			
			// 2. 글 제목/내용 크로스 사이트 스크립팅 방지 처리
			String travelTitle = (String)map.get("travelTitle");
			String travelContent = (String)map.get("travelContent");
			
			travelTitle = replaceParamater(travelTitle);
			travelContent = replaceParamater(travelContent);
			
			// 3. 글 내용 개행문자 \r\n -> <br> 변경 처리
			travelContent = travelContent.replaceAll("\r\n", "<br>"); 
			
			// 처리된 내용을 다시 map에 추가
			map.put("travelTitle", travelTitle);
			map.put("travelContent", travelContent);
			
			
			try {
				// 4. 게시글 부분(제목, 내용, 카테고리)만 BOARD 테이블에 삽입하는 DAO 호출
				result = dao.insertBoard(conn, map);
				
				// 5. 파일 정보 부분만 ATTACHMENT 테이블에 삽입하는 DAO 호출
				List<TravelAttachment> fList = (List<TravelAttachment>)map.get("fList"); // 노란줄 뜨는데 문제는 없음.. 
				
				// 게시글 부분 삽입 성공 && 파일 정보가 있을 경우 
				if(result > 0 && !fList.isEmpty()) {
					
					result = 0; // result 재활용을 위해 0으로 초기화
					
					// fList의 요소를 하나씩 반복 접근하여
					// DAO 메소드를 반복 호출해 정보를 삽입함. 
					for(TravelAttachment at : fList) {
						
						// 파일 정보가 저장된 Attachment 객체에 
						// 해당 파일이 작성된 게시글 번호를 추가 세팅
						at.setParentBoardNo(travelNo);
						
						result = dao.insertAttachment(conn, at);
								
						if(result == 0) { // 파일 정보 삽입 실패
							//break; // 보류 
							
							// 강제로 예외 발생
							throw new FileInsertFailedException("파일 정보 삽입 실패");
						}
					}
				} 
			} catch (Exception e) {
				// 4,5번에 대한 추가 작업
				// 게시글 또는 파일 정보 삽입 중 에러 발생 시 
				// 서버에 저장된 파일을 삭제하는 작업이 필요.
				
				List<TravelAttachment> fList = (List<TravelAttachment>)map.get("fList");
				
				if(!fList.isEmpty()) {
					for(TravelAttachment at : fList ) {
						String filePath = at.getFilePath();
						String fileName = at.getFileName();
						
						File deleteFile = new File(filePath + fileName);
						// 
						if(deleteFile.exists()) {
							// 해당 경로에 해당 파일이 존재하면 
							deleteFile.delete(); // 해당 파일 삭제 
						}
					}
				}
				// 에러 페이지 보여질 수 있도록 catch한 Exception을 Controller로 던져줌
				throw e;
				
			} // end catch
			
			// 6. 트랜잭션 처리 
			if(result > 0) {
				commit(conn);
				
				// 삽입 성공 시 상세 조회 화면으로 이동해야되기 때문에
				// 글번호를 반환할 수 있도록 result에 boardNo를 대입
				result = travelNo;
				
			}else {
				rollback(conn);
			}
		}

		// 7. 커넥션 반환
		close(conn);
		
		// 8. 결과 반환
		
		return result;
	}
	
	// 크로스 사이트 스크립팅 방지 메소드
	private String replaceParamater(String param) {
		String result = param;
		if(param != null) {
			result = result.replaceAll("&", "&amp;"); // 왼쪽에 있는 기호들을 오른쪽으로 바꿔줌 
			result = result.replaceAll("<", "&lt;");  
			result = result.replaceAll(">", "&gt;"); 
			result = result.replaceAll("\"", "&quot;"); 
		}
		
		return result ;
	}

	/** 게시글 싱세조회 파일
	 * @param travelNo
	 * @return
	 * @throws Exception
	 */
	public List<TravelAttachment> selectBoardFiles(int travelNo) throws Exception{
		Connection conn = getConnection();
		
		List<TravelAttachment> fList = dao.selectBoardFiles(conn, travelNo);

		close(conn);
		
		return fList;
	}

	/** 썸네일용 Service 
	 * @param pInfo
	 * @return fList
	 * @throws Exception
	 */
	public List<TravelAttachment> selectThumbnailList(PageInfo pInfo) throws Exception {
		Connection conn = getConnection();
		List<TravelAttachment> fList = dao.selectThumbnailList(conn, pInfo);
		close(conn);
		
		return fList;
	}

	/** 수정화면 전환용 Service 
	 * @param travelNo
	 * @return travel
	 * @throws Exception
	 */
	public Travel updateView(int travelNo) throws Exception  {
		Connection conn = getConnection();
		
		// 이전에 만들어준 상세조회 DAO 호출
		Travel travel = dao.selectTravel(conn, travelNo); 
		
		// textArea 출력을 위한 개행문자 변경
		travel.setTravelContent(travel.getTravelContent().replaceAll("<br>", "\r\n") );
		
		close(conn);
		
		return travel;
	}

	/** 수정 업데이틑 Service
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateTravel(Map<String, Object> map) throws Exception {
		Connection conn = getConnection();
		
		int result = 0; // service 수행 결과 저장 변수 
		List<TravelAttachment> deleteFiles = null; // 삭제할 파일 정보 저장 변수 선언 
		
		// 1. 글 제목/내용 크로스 사이트 스크립팅 방지 처리
		String travelTitle = (String)map.get("travelTitle");
		String travelContent = (String)map.get("travelContent");
		
		travelTitle = replaceParamater(travelTitle);
		travelContent = replaceParamater(travelContent);
		
		// 2. 글 내용 개행문자 \r\n -> <br> 변경 처리
		travelContent = travelContent.replaceAll("\r\n", "<br>"); 
		
		// 처리된 내용을 다시 map에 추가
		map.put("travelTitle", travelTitle);
		map.put("travelContent", travelContent);
		
		try {
			// 3. 게시글 부분 수정 DAO 호출
			result = dao.updateTravel(conn, map);
			
			// 4. 게시글 수정 성공하고 fList가 비어있지 않으면
			//    파일 정보 수정 DAO 호출
			
			// 수정 화면에서 새로운 이미지가 업로드된 파일 정보만을 담고있는 List
			List<TravelAttachment> newFileList = (List<TravelAttachment>)map.get("fList");
			
			if(result > 0 && !newFileList.isEmpty() ) {
				
				// DB에서 해당 게시글의 수정전 파일 목록을 조회함.
				List<TravelAttachment> oldFileList = dao.selectBoardFiles(conn, (int)map.get("travelNo"));
				
				// newFileList -> 수정된 썸네일(lv.0)
				
				// oldFileList -> 썸네일(lv.0), 이미지1(lv.1), 이미지2(lv.2)
				
				// 기존 썸네일(lv.0) -> 수정된썸네일(lv.0)로 변경됨
				// --> DB에서 기존 썸네일의 데이터를 수정된 썸네일로 변경
				//   --> DB에서 기존 썸네일 정보가 사라짐.
				
				result = 0; // result 재활용
				deleteFiles = new ArrayList<TravelAttachment>(); // 삭제될 파일 정보 저장 List 
				
				
				// 새로운 이미지 정보 반복 접근 
				for(TravelAttachment newFile : newFileList) {
					
					// flag가 false인 경우 : 새 이미지와 기존 이미지 파일 레벨이 중복되는 경우 -> update 
					// flag가 true인 경우 : 새 이미지와 기존 이미지의 파일 레벨이 중복되지 않는 경우 -> insert
					boolean flag = true;
					
					// 기존 이미지 정보 반복 접근 
					for(TravelAttachment oldFile : oldFileList) {
						
						// 새로운 이미지와 가존 이미지의 파일레벨이 동일한 파일이 있다면
						if(newFile.getFileLevel() == oldFile.getFileLevel()) {
							
							// 기존 파일을 삭제 List에 추가 
							deleteFiles.add(oldFile);
							
							// 새 이미지 정보에 이전 파일 번호를 추가 -> 파일 번호를 이용한 수정 진행
							newFile.setFileNo( oldFile.getFileNo() );
							
							flag = false;
							
							break;
						}
					}
					
					// flag 값에 따라 파일정보 insert 또는 update 수행 
					if(flag) {
						result = dao.insertAttachment(conn, newFile);
					} else {
						result = dao.updateAttachment(conn, newFile);
					}
					
					// 파일 정보 삽입 또는 수정 중 실패시 
					if(result == 0) {
						// 강제로 사용자 정의 예외 발생
						
						throw new FileInsertFailedException("파일 정보 삽입 또는 수정 실패");
					}
				}
			}
			
		} catch (Exception e) {
			// 게시글 수정 중 실패 또는 오류 발생 시
			// 서버에 미리 저장되어 있던 이미지 파일 삭제
			
			List<TravelAttachment> fList = (List<TravelAttachment>)map.get("fList");
			
			if(!fList.isEmpty()) {
				for(TravelAttachment at : fList ) {
					String filePath = at.getFilePath();
					String fileName = at.getFileName();
					
					File deleteFile = new File(filePath + fileName);
					// 
					if(deleteFile.exists()) {
						// 해당 경로에 해당 파일이 존재하면 
						deleteFile.delete(); // 해당 파일 삭제 
					}
				}
			}
			// 에러 페이지 보여질 수 있도록 catch한 Exception을 Controller로 던져줌
			throw e;
		}
		
		// 5. 트랜잭션 처리 및 삭제 목록에 있는 파일 삭제
		if(result > 0) {
			commit(conn);
			
			// DB 정보와 맞지 않는 파일 (deleteFiles) 삭제 진행
			if(deleteFiles != null) {
				
				for(TravelAttachment at : deleteFiles) {
					
					String filePath = at.getFilePath();
					String fileName = at.getFileName();
					
					// java.io로 import
					File deleteFile = new File(filePath + fileName);
					
					if(deleteFile.exists()) {
						deleteFile.delete();
					}
				}
			}
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}

	/** 게시글 삭제 Service 
	 * @param travelNo
	 * @return result 
	 * @throws Exception
	 */
	public int updateBoardFl(int travelNo) throws Exception {
		Connection conn = getConnection();
		int result = dao.updateBoardFl(conn, travelNo);
		
		if(result>0) commit(conn);
		else 		rollback(conn);

		close(conn);
		
		return result;
	}

}
