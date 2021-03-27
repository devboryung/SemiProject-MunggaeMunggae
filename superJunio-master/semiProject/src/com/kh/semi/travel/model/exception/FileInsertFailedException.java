package com.kh.semi.travel.model.exception;

// 파일 정보 삽입 실패시 발생할 사용자 정의 예외
public class FileInsertFailedException extends Exception {
	
	// 기본 생성자. 
	public FileInsertFailedException() {
		super();
	}
	
	// 메세지가 전달된 게 있으면 부모에게 전달해줘라. 
	public FileInsertFailedException(String message) {
		super(message);
	}
	
}
