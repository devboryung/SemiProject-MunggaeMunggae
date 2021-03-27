package com.kh.semi.room.model.service;

import static com.kh.semi.common.JDBCTemplate.*;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kh.semi.member.model.vo.Member;
import com.kh.semi.room.model.dao.RoomDAO;
import com.kh.semi.room.model.esception.FileInsertFailedException;
import com.kh.semi.room.model.vo.Attachment;
import com.kh.semi.room.model.vo.PageInfo;
import com.kh.semi.room.model.vo.Room;

public class RoomService {
	
	
	private RoomDAO dao = new RoomDAO();
	
	
	

	/** 페이징 처리를 위한 값 계산 Service
	 * @param cp
	 * @return	PageInfo
	 * @throws Exception
	 */
	public PageInfo getPageInfo(String cp) throws Exception {
		Connection conn = getConnection();
		
		// cp가 null일 경우 1, 아니면 cp를 얻어옴.
		int currentPage = cp == null ? 1 : Integer.parseInt(cp);
		
		// System.out.println(currentPage); 숙소 화면 들어가자마자 1 출력 됨
		
		// DB에서 전체 게시글 수를 조회하여 반환받기
		int listCount = dao.getListCount(conn);
		
		close(conn);
		
		return new PageInfo(currentPage, listCount);
	}




	/** 숙소 목록 조회 Service
	 * @param pInfo
	 * @return rList
	 * @throws Exception
	 */
	public List<Room> selectRoomList(PageInfo pInfo) throws Exception {
		Connection conn = getConnection();
		
		List<Room> rList = dao.selectRoomList(conn,pInfo);
		
		close(conn);
		return rList;
	}




	/** 숙소 상세조회 Service
	 * @param roomNo
	 * @return	room
	 * @throws Exception
	 */
	public Room selectRoom(int roomNo) throws Exception {
		Connection conn = getConnection();
		
		Room room = dao.selectRoom(conn, roomNo);
		
		// 상세조회가 성공하면
		if(room!=null) {
			int result = dao.increaseReadCount(conn,roomNo);
			
			if(result>0) {
				commit(conn);
				room.setViewCount(room.getViewCount()+1);
			}
			else  rollback(conn);
		}
		
		close(conn);
		return room;
	}




	/**	숙소 등록 Service
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertRoom(Map<String, Object> map) throws Exception{
		Connection conn = getConnection();
		int result = 0;
		
		// 1. 추가될 번호 가져오기
		int insertNo = dao.selectNextNo(conn);
		
		if(insertNo>0) {
			// 얻어온 번호를 map에 추가해준다.
			map.put("insertNo", insertNo);
			
			// 크로스 사이트 스크립팅 방지
			String roomInfo = (String)map.get("roomInfo");
			
			roomInfo = replaceParameter(roomInfo);
			
			// 내용에 개행문자 변경처리.
			
			roomInfo = roomInfo.replaceAll("\r\n", "<br>");
			
			// 처리된 내용을 다시 map에 추가
			map.put("roomInfo",roomInfo);
			
			
			try {
				
				// 파일 정보 제외한 정보  등록하는 DAO호출
				result = dao.insertRoom(conn,map);
				
				
				// 파일 정보만 등록하는 DAO
				List<Attachment> fList = (List<Attachment>)map.get("fList");
				
				if(result>0 && !fList.isEmpty()) {
					// 게시글부분, 파일정보 모두 있다면.
					 result =0; // result를  재활용하기 위해 초기화시킴
					 
					 
					 // fList의 요소를 하나씩 반복접근하여 DAO메소드를 반복 호출해 정보를 삽입함.
					 for(Attachment at : fList) {
						 // 파일 정보가 저장된 Attachment 객체에 해당 파일이 작성된 게시글 번호를 추가 세팅
						 at.setRoomNo(insertNo);
						 
						 result = dao.insertAttachment(conn,at); 
						 
						 if(result==0) { // 등록 실패.이미지 정보가 DB에 안들어갔을떄.
							 
							 // 강제로 예외를 발생시킨다. ( 사용자 정의 예외 )
							 throw new FileInsertFailedException("파일 정보 삽입 실패");
							 
						 }
						System.out.println(result);
					 }
				}
				
			}catch(Exception e) {
				// 게시글 또는 파일 정보 삽입 중 에러 발생 시 서버에 저장된 파일을 삭제하는 작업이 필요함.
				// 왜? MultipartRequest 객체가 생성되면 바로 서버에 등록이 되기 때문에
				// 객체는 생성되었지만 비즈니스 로직 실패시  데이버와 서버의 정보 불일치가 일어남.
				List<Attachment> fList = (List<Attachment>)map.get("fList");
				
				if(!fList.isEmpty()) {
					for(Attachment at : fList ) {
						String filePath = at.getFilePath();
						String fileName = at.getFileName();
						
						File deleteFile = new File(filePath + fileName);
						
						if(deleteFile.exists()) {
							// 해당 경로에 해당 파일이 존재하면
							deleteFile.delete(); // 해당 파일 삭제
						}
				}
				
			}
			// 에러페이지가 보여질 수 있도록 catch 한 Exception을 Controller로 던져줌
			throw e;
				
			}// end catch
			
			if(result > 0) {
				commit(conn);
				// 삽입 성공 시 해당 병원의 상세 조회 화면으로 이동해야되기 때문에 
				// 글 번호를 받환할 수 있도록 result에 hospitalNo를 대입
				result = insertNo;
				
			}else {
				rollback(conn);
			}
			
		}
		close(conn);
		return result;
	}
	
	
	// 크로스 사이트 스크립팅
		// 웹 애플리케이션에서 많이 나타나는 보안 취약점 중 하나로
		// 웹 사이트 관리자가 아닌 사용자가 웹 페이지에 악성 스크립트를 삽입할 수 있는 취약점
		
		// 크로스 사이트 스크립팅 방지 메소드
		private String replaceParameter(String param) {
			String result = param;
			
			if(result != null) {
				result = result.replaceAll("&", "&amp;"); 
				result = result.replaceAll("<", "&lt;"); 
				result = result.replaceAll(">", "&gt;"); 
				result = result.replaceAll("\"", "&quot;"); 
			}
			
			return result;
		}




		/** 숙소수정 화면 출력 Service
		 * @param roomNo
		 * @return	room
		 * @throws Exception
		 */
		public Room updateView(int roomNo) throws Exception {
			Connection conn = getConnection();
			
			Room room = dao.selectRoom(conn, roomNo);
			
			// 크로스 스크립팅 방지를 위해 개행문자가 <br>로 바뀌어 있는 상태 -> \r\n 으로 바꿈
			room.setRoomInfo(room.getRoomInfo().replaceAll("<br>", "\r\n"));
			
			close(conn);
			
			return room;
		}

	




		/** 업체 정보 얻어오기
		 * @param memberNo
		 * @return comMember
		 * @throws Exception
		 */
		public Member selectComMember(int memberNo) throws Exception {
			Connection conn = getConnection();
			Member comMember = dao.selectComMember(conn,memberNo);
			close(conn);
			return comMember;
		}




		/** 숙소에 포함된 이미지 목록 조회 Service
		 * @param roomNo
		 * @return fList
		 * @throws Exception
		 */
		public List<Attachment> selectRoomFiles(int roomNo) throws Exception {
			Connection conn = getConnection();
			List<Attachment> fList = dao.selectRoomFiles(conn,roomNo);
			close(conn);
			return fList;
		}




		/** 썸네일 목록 조회 Service
		 * @param pInfo
		 * @return	fList
		 * @throws Exception
		 */
		public List<Attachment> selectThumbnailList(PageInfo pInfo) throws Exception {
			Connection conn = getConnection();
			
			List<Attachment> fList = dao.selectThumbnailList(conn,pInfo);
			
			close(conn);
			
			return fList;
		}




		/**	숙소 수정 Service
		 * @param map
		 * @return	result
		 * @throws Exception
		 */
		public int updateRoom(Map<String, Object> map) throws Exception {
			Connection conn = getConnection();
			
			int result =0;
			
			List<Attachment> deleteFiles = null; // 삭제할 파일 정보 저장
			
			// 크로스 사이트 스크립팅 방지
			String roomInfo = (String)map.get("roomInfo");
			
			roomInfo = replaceParameter(roomInfo);
			
			// 내용에 개행문자 변경처리.
			
			roomInfo = roomInfo.replaceAll("\r\n", "<br>");
			
			// 처리된 내용을 다시 map에 추가
			map.put("roomInfo",roomInfo);
			
			try {
				
				// 수정 DAO
				result = dao.updateRoom(conn,map);
				
				List<Attachment> newFileList = (List<Attachment>)map.get("fList");
			
				if(result>0 && !newFileList.isEmpty()) {
					List<Attachment> oldFileList = dao.selectRoomFiles(conn, (int)map.get("roomNo"));
					
					result =0; // 재활용
					deleteFiles = new ArrayList<Attachment>(); // 삭제될 파일정보 저장
					
					// 새로운 이미지 정보 반복 접근
					for(Attachment newFile : newFileList) {
			               
			               // flag가 false인 경우 : 새 이미지와 기존 이미지의 파일 레벨이 중복되는 경우 -> update
			               // flag가 true인 경우 : 새 이미지와 기존 이미지의 파일 레벨이 중복되지 않는 경우 -> insert
			               boolean flag = true;
			               
			               // 기존 이미지 정보 반복 접근
			               for(Attachment oldFile : oldFileList) {
			                  
			                  // 새로운 이미지와 기존 이미지의 파일 레벨이 동일한 파일이 있다면
			                  if(newFile.getFileLevel() == oldFile.getFileLevel()) {
			                     
			                     // 기존 파일을 삭제 List에 추가
			                     deleteFiles.add(oldFile);
			                     
			                     // 새 이미지 정보에 이전 파일 번호를 추가 -> 파일 번호를 이용한 수정 진행
			                     newFile.setFileNo(oldFile.getFileNo());
			                     
			                     flag = false;
			                     
			                     break;
			                  }
			               }
					
		            // flag 값에 따라 파일 정보 insert 또는 update수행
		               if(flag) {
		                  result = dao.insertAttachment(conn, newFile);
		               }else {
		                  result = dao.updateAttachment(conn, newFile);
		               }
		               
		               // 파일 정보 삽입 또는 수정 중 실패 시
		               if(result == 0) {
		                  // 강제로 사용자 정의 예외 발생
		                  throw new FileInsertFailedException("파일 정보 삽입 또는 수정 실패");
		               }
		            }
				}
				
				
			}catch(Exception e) {

				 // 게시글 수정 중 실패 또는 오류 발생 시
		         // 서버에 미리 저장되어 있던 이미지 파일 삭제
		         List<Attachment> fList = (List<Attachment>)map.get("fList");
		         
		         if(!fList.isEmpty()) {
		            for(Attachment at : fList) {
		               String filePath = at.getFilePath();
		               String fileName = at.getFileName();
		               
		               File deleteFile = new File(filePath + fileName);
		               
		               if(deleteFile.exists()) {
		                  // 해당 경로에 해당 파일이 존재하면
		                  deleteFile.delete(); // 해당 파일 삭제
		               }
		            }
		         }
		         
		         // 에러페이지가 보여질 수 있도록 catch한 Exception을 Controller로 던져줌
		         throw e; 
			}
			
			 // 5. 트랜잭션 처리 및 삭제 목록에 있는 파일 삭제
		      if(result > 0) {
		         commit(conn);	
					
		         // DB 정보와 맞지 않는 파일(deleteFiles) 삭제 진행
		         if(deleteFiles !=null) {
		        	 
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
		      return result;
		}




		/** 숙소 삭제 Service
		 * @param roomNo
		 * @return result
		 * @throws Exception
		 */
		public int deleteRoom(int roomNo) throws Exception {
			Connection conn = getConnection();
			
			int result = dao.deleteRoom(conn, roomNo);
			
			if(result>0) commit(conn);
			else 		 rollback(conn);
			
			close(conn);
			return result;
		}



	
}
