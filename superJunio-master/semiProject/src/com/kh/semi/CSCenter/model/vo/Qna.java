package com.kh.semi.CSCenter.model.vo;

import java.sql.Timestamp;

public class Qna {
	
	private int qnaNo;
	private String qnaTitle;
	private String qnaContent;
	private Timestamp qnaCreateDt;
	private String qnaPrivate;
	private String qnaFl;
	private String qnaReplyResponse;
	private int memNo;
	private int qnaReadCount;
	private String memNickName;
	

	public Qna() {
	}
	

	public Qna(int qnaNo, String qnaTitle, String qnaContent, Timestamp qnaCreateDt, String qnaPrivate, String qnaFl,
			String qnaReplyResponse, int memNo, int qnaReadCount, String memNickName) {
		super();
		this.qnaNo = qnaNo;
		this.qnaTitle = qnaTitle;
		this.qnaContent = qnaContent;
		this.qnaCreateDt = qnaCreateDt;
		this.qnaPrivate = qnaPrivate;
		this.qnaFl = qnaFl;
		this.qnaReplyResponse = qnaReplyResponse;
		this.memNo = memNo;
		this.qnaReadCount = qnaReadCount;
		this.memNickName = memNickName;
	}

	public int getQnaNo() {
		return qnaNo;
	}

	public void setQnaNo(int qnaNo) {
		this.qnaNo = qnaNo;
	}

	public String getQnaTitle() {
		return qnaTitle;
	}

	public void setQnaTitle(String qnaTitle) {
		this.qnaTitle = qnaTitle;
	}

	public String getQnaContent() {
		return qnaContent;
	}

	public void setQnaContent(String qnaContent) {
		this.qnaContent = qnaContent;
	}

	public Timestamp getQnaCreateDt() {
		return qnaCreateDt;
	}

	public void setQnaCreateDt(Timestamp qnaCreateDt) {
		this.qnaCreateDt = qnaCreateDt;
	}

	public String getQnaPrivate() {
		return qnaPrivate;
	}

	public void setQnaPrivate(String qnaPrivate) {
		this.qnaPrivate = qnaPrivate;
	}

	public String getQnaFl() {
		return qnaFl;
	}

	public void setQnaFl(String qnaFl) {
		this.qnaFl = qnaFl;
	}

	public String getQnaReplyResponse() {
		return qnaReplyResponse;
	}

	public void setQnaReplyResponse(String qnaReplyResponse) {
		this.qnaReplyResponse = qnaReplyResponse;
	}

	public int getMemNo() {
		return memNo;
	}

	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}

	public int getQnaReadCount() {
		return qnaReadCount;
	}

	public void setQnaReadCount(int qnaReadCount) {
		this.qnaReadCount = qnaReadCount;
	}

	public String getMemNickName() {
		return memNickName;
	}

	public void setMemNickName(String memNickName) {
		this.memNickName = memNickName;
	}

	@Override
	public String toString() {
		return "Qna [qnaNo=" + qnaNo + ", qnaTitle=" + qnaTitle + ", qnaContent=" + qnaContent + ", qnaCreateDt="
				+ qnaCreateDt + ", qnaPrivate=" + qnaPrivate + ", qnaFl=" + qnaFl + ", qnaReplyResponse="
				+ qnaReplyResponse + ", memNo=" + memNo + ", qnaReadCount=" + qnaReadCount + ", memNickName="
				+ memNickName + "]";
	}

	
	
}
