package com.kh.semi.tripBoard.model.vo;

import java.sql.Timestamp;

public class TripBoard {
	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String memberId;
	private int readCount;
	private Timestamp boardCreateDate;
	private String boardStatus;
	private int reportNo;
	
	public TripBoard() {}

	public TripBoard(int boardNo, String boardTitle, String boardContent, String memberId, int readCount,
			Timestamp boardCreateDate, String boardStatus, int reportNo) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.memberId = memberId;
		this.readCount = readCount;
		this.boardCreateDate = boardCreateDate;
		this.boardStatus = boardStatus;
		this.reportNo = reportNo;
	}
	
	
	
	
	

	public TripBoard(int boardNo, String boardTitle, String memberId, int readCount, Timestamp boardCreateDate) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.memberId = memberId;
		this.readCount = readCount;
		this.boardCreateDate = boardCreateDate;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public Timestamp getBoardCreateDate() {
		return boardCreateDate;
	}

	public void setBoardCreateDate(Timestamp boardCreateDate) {
		this.boardCreateDate = boardCreateDate;
	}

	public String getBoardStatus() {
		return boardStatus;
	}

	public void setBoardStatus(String boardStatus) {
		this.boardStatus = boardStatus;
	}

	public int getReportNo() {
		return reportNo;
	}

	public void setReportNo(int reportNo) {
		this.reportNo = reportNo;
	}

	@Override
	public String toString() {
		return "TripBoard [boardNo=" + boardNo + ", boardTitle=" + boardTitle + ", boardContent=" + boardContent
				+ ", memberId=" + memberId + ", readCount=" + readCount + ", boardCreateDate=" + boardCreateDate
				+ ", boardStatus=" + boardStatus + ", reportNo=" + reportNo + "]";
	}
	
	
	
	
	

}
