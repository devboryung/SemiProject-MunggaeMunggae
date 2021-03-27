package com.kh.semi.CSCenter.model.vo;

import java.sql.Timestamp;

public class NoticeReply {
	
	private int noticeRepNo;
	private String noticeRepContent;
	private Timestamp noticeRepCreateDt;
	private String noticeRepfl;
	private int noticeNo;
	private int noticeRepWriterNo;
	private String memNickName;

	public NoticeReply() {
	
	}

	public NoticeReply(int noticeRepNo, String noticeRepContent, Timestamp noticeRepCreateDt, String noticeRepfl,
			int noticeNo, int noticeRepWriterNo, String memNickName) {
		super();
		this.noticeRepNo = noticeRepNo;
		this.noticeRepContent = noticeRepContent;
		this.noticeRepCreateDt = noticeRepCreateDt;
		this.noticeRepfl = noticeRepfl;
		this.noticeNo = noticeNo;
		this.noticeRepWriterNo = noticeRepWriterNo;
		this.memNickName = memNickName;
	}

	public int getNoticeRepNo() {
		return noticeRepNo;
	}

	public void setNoticeRepNo(int noticeRepNo) {
		this.noticeRepNo = noticeRepNo;
	}

	public String getNoticeRepContent() {
		return noticeRepContent;
	}

	public void setNoticeRepContent(String noticeRepContent) {
		this.noticeRepContent = noticeRepContent;
	}

	public Timestamp getNoticeRepCreateDt() {
		return noticeRepCreateDt;
	}

	public void setNoticeRepCreateDt(Timestamp noticeRepCreateDt) {
		this.noticeRepCreateDt = noticeRepCreateDt;
	}

	public String getNoticeRepfl() {
		return noticeRepfl;
	}

	public void setNoticeRepfl(String noticeRepfl) {
		this.noticeRepfl = noticeRepfl;
	}

	public int getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(int noticeNo) {
		this.noticeNo = noticeNo;
	}

	public int getNoticeRepWriterNo() {
		return noticeRepWriterNo;
	}

	public void setNoticeRepWriterNo(int noticeRepWriterNo) {
		this.noticeRepWriterNo = noticeRepWriterNo;
	}

	public String getMemNickName() {
		return memNickName;
	}

	public void setMemNickName(String memNickName) {
		this.memNickName = memNickName;
	}

	@Override
	public String toString() {
		return "NoticeReply [noticeRepNo=" + noticeRepNo + ", noticeRepContent=" + noticeRepContent
				+ ", noticeRepCreateDt=" + noticeRepCreateDt + ", noticeRepfl=" + noticeRepfl + ", noticeNo=" + noticeNo
				+ ", noticeRepWriterNo=" + noticeRepWriterNo + ", memNickName=" + memNickName + "]";
	}


}
