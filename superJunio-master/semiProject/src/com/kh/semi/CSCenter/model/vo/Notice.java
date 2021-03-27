package com.kh.semi.CSCenter.model.vo;

import java.sql.Timestamp;

public class Notice {

	private int noticeNo;
	private String noticeTitle;
	private String noticeContent;
	private Timestamp noticeCreateDt;
	private int noticeReadCount;
	private String noticeFl;
	private int memNo;
	private String memNickName;
	private int noticeReplyCount;
	
	
	
	public Notice() {
	}
	
	
	public Notice(int noticeNo, String noticeTitle, String noticeContent, Timestamp noticeCreateDt, int noticeReadCount,
			String noticeFl, int memNo, String memNickName) {
		super();
		this.noticeNo = noticeNo;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.noticeCreateDt = noticeCreateDt;
		this.noticeReadCount = noticeReadCount;
		this.noticeFl = noticeFl;
		this.memNo = memNo;
		this.memNickName = memNickName;
	}


	public Notice(int noticeNo, String noticeTitle, String noticeContent, Timestamp noticeCreateDt, int noticeReadCount,
			String noticeFl, int memNo, String memNickName, int noticeReplyCount) {
		super();
		this.noticeNo = noticeNo;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.noticeCreateDt = noticeCreateDt;
		this.noticeReadCount = noticeReadCount;
		this.noticeFl = noticeFl;
		this.memNo = memNo;
		this.memNickName = memNickName;
		this.noticeReplyCount = noticeReplyCount;
	}
	
	
	public int getNoticeNo() {
		return noticeNo;
	}
	public void setNoticeNo(int noticeNo) {
		this.noticeNo = noticeNo;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	public Timestamp getNoticeCreateDt() {
		return noticeCreateDt;
	}
	public void setNoticeCreateDt(Timestamp noticeCreateDt) {
		this.noticeCreateDt = noticeCreateDt;
	}
	public int getNoticeReadCount() {
		return noticeReadCount;
	}
	public void setNoticeReadCount(int noticeReadCount) {
		this.noticeReadCount = noticeReadCount;
	}
	public String getNoticeFl() {
		return noticeFl;
	}
	public void setNoticeFl(String noticeFl) {
		this.noticeFl = noticeFl;
	}
	public int getMemNo() {
		return memNo;
	}
	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}
	public String getMemNickName() {
		return memNickName;
	}
	public void setMemNickName(String memNickName) {
		this.memNickName = memNickName;
	}
	public int getNoticeReplyCount() {
		return noticeReplyCount;
	}
	public void setNoticeReplyCount(int noticeReplyCount) {
		this.noticeReplyCount = noticeReplyCount;
	}
	@Override
	public String toString() {
		return "Notice [noticeNo=" + noticeNo + ", noticeTitle=" + noticeTitle + ", noticeContent=" + noticeContent
				+ ", noticeCreateDt=" + noticeCreateDt + ", noticeReadCount=" + noticeReadCount + ", noticeFl="
				+ noticeFl + ", memNo=" + memNo + ", memNickName=" + memNickName + ", noticeReplyCount="
				+ noticeReplyCount + "]";
	}



	


	
}
