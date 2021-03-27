package com.kh.semi.travel.model.vo;

import java.sql.Date;

public class Travel {
	
	private int travelNo;		// 게시글 번호
	private String travelLocation; // 지역명
	private String travelTitle;		// 게시글 제목
	private String travelContent;	// 게시글 내용
	private int travelReadCount;	// 조회수
	private Date travelBoardDate;	// 게시글 작성일
	private char travelDelFl;		// 게시글 삭제 여부
	private int memNo;				// 회원번호
	
	public Travel() { }
	
	
	
	
	
	// 다음 게시글 번호 조회 (??) 
	public Travel(int travelNo) {
		super();
		this.travelNo = travelNo;
	}


	// 게시글 삽입용 생성자
	public Travel(int travelNo, String travelLocation, String travelTitle, String travelContent) {
		super();
		this.travelNo = travelNo;
		this.travelLocation = travelLocation;
		this.travelTitle = travelTitle;
		this.travelContent = travelContent;
	}
	
	
	public Travel(int travelNo, String travelLocation, String travelTitle, int travelReadCount, Date travelBoardDate) {
		super();
		this.travelNo = travelNo;
		this.travelLocation = travelLocation;
		this.travelTitle = travelTitle;
		this.travelReadCount = travelReadCount;
		this.travelBoardDate = travelBoardDate;
	}





	// 게시글 상세조회 화면용 생성자
	public Travel(int travelNo, String travelLocation, String travelTitle, String travelContent, int travelReadCount,
			Date travelBoardDate) {
		super();
		this.travelNo = travelNo;
		this.travelLocation = travelLocation;
		this.travelTitle = travelTitle;
		this.travelContent = travelContent;
		this.travelReadCount = travelReadCount;
		this.travelBoardDate = travelBoardDate;
	}


	// 게시글 리스트 조회 화면용 생성자
	public Travel(int travelNo, String travelTitle, int travelReadCount, Date travelBoardDate) {
		super();
		this.travelNo = travelNo;
		this.travelTitle = travelTitle;
		this.travelReadCount = travelReadCount;
		this.travelBoardDate = travelBoardDate;
	}

	// 전체 매개변수 생성자
	public Travel(int travelNo, String travelLocation, String travelTitle, String travelContent, int travelReadCount,
			Date travelBoardDate, char travelDelFl, int memNo) {
		super();
		this.travelNo = travelNo;
		this.travelLocation = travelLocation;
		this.travelTitle = travelTitle;
		this.travelContent = travelContent;
		this.travelReadCount = travelReadCount;
		this.travelBoardDate = travelBoardDate;
		this.travelDelFl = travelDelFl;
		this.memNo = memNo;
	}



	public int getTravelNo() {
		return travelNo;
	}

	public void setTravelNo(int travelNo) {
		this.travelNo = travelNo;
	}

	public String getTravelLocation() {
		return travelLocation;
	}

	public void setTravelLocation(String travelLocation) {
		this.travelLocation = travelLocation;
	}

	public String getTravelTitle() {
		return travelTitle;
	}

	public void setTravelTitle(String travelTitle) {
		this.travelTitle = travelTitle;
	}

	public String getTravelContent() {
		return travelContent;
	}

	public void setTravelContent(String travelContent) {
		this.travelContent = travelContent;
	}

	public int getTravelReadCount() {
		return travelReadCount;
	}

	public void setTravelReadCount(int travelReadCount) {
		this.travelReadCount = travelReadCount;
	}

	public Date getTravelBoardDate() {
		return travelBoardDate;
	}

	public void setTravelBoardDate(Date travelBoardDate) {
		this.travelBoardDate = travelBoardDate;
	}

	public char getTravelDelFl() {
		return travelDelFl;
	}

	public void setTravelDelFl(char travelDelFl) {
		this.travelDelFl = travelDelFl;
	}

	public int getMemNo() {
		return memNo;
	}

	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}


	@Override
	public String toString() {
		return "Travel [travelNo=" + travelNo + ", travelLocation=" + travelLocation + ", travelTitle=" + travelTitle
				+ ", travelContent=" + travelContent + ", travelReadCount=" + travelReadCount + ", travelBoardDate="
				+ travelBoardDate + ", travelDelFl=" + travelDelFl + ", memNo=" + memNo + "]";
	}
	
	
	
	

}
