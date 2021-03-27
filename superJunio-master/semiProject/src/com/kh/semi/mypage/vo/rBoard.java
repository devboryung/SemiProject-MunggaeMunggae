package com.kh.semi.mypage.vo;

import java.sql.Timestamp;

public class rBoard {
	private int boardNo;
	private int memberNo;
	private String boardTitle;
	private String reply;
	private Timestamp boardCreateDate;
	private int readCount;
	private String boardStatus;
	
	
	public rBoard() {
	   }


	public rBoard(int boardNo, int memberNo, String boardTitle, String reply, Timestamp boardCreateDate, int readCount,
			String boardStatus) {
		super();
		this.boardNo = boardNo;
		this.memberNo = memberNo;
		this.boardTitle = boardTitle;
		this.reply = reply;
		this.boardCreateDate = boardCreateDate;
		this.readCount = readCount;
		this.boardStatus = boardStatus;
	}


	public int getBoardNo() {
		return boardNo;
	}


	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}


	public int getMemberNo() {
		return memberNo;
	}


	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}


	public String getBoardTitle() {
		return boardTitle;
	}


	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}


	public String getReply() {
		return reply;
	}


	public void setReply(String reply) {
		this.reply = reply;
	}


	public Timestamp getBoardCreateDate() {
		return boardCreateDate;
	}


	public void setBoardCreateDate(Timestamp boardCreateDate) {
		this.boardCreateDate = boardCreateDate;
	}


	public int getReadCount() {
		return readCount;
	}


	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}


	public String getBoardStatus() {
		return boardStatus;
	}


	public void setBoardStatus(String boardStatus) {
		this.boardStatus = boardStatus;
	}


	@Override
	public String toString() {
		return "rBoard [boardNo=" + boardNo + ", memberNo=" + memberNo + ", boardTitle=" + boardTitle + ", reply="
				+ reply + ", boardCreateDate=" + boardCreateDate + ", readCount=" + readCount + ", boardStatus="
				+ boardStatus + "]";
	}

	
	
	
	
}
