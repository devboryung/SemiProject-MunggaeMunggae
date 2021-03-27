package com.kh.semi.CSCenter.model.vo;

import java.sql.Timestamp;

public class QnaReply {
		
	private int qnaReplyNo;
	private String qnaReplyContent;
	private Timestamp qnaReplyCreateDt;
	private String qnaReplyFl;
	private int parentQnaNo;
	private String memNickName;
	private int memNo;
	private String qnaReplyResponse;
	
	public QnaReply() {}

	public QnaReply(int qnaReplyNo, String qnaReplyContent, Timestamp qnaReplyCreateDt, String qnaReplyFl,
			int parentQnaNo, String memNickName, int memNo, String qnaReplyResponse) {
		super();
		this.qnaReplyNo = qnaReplyNo;
		this.qnaReplyContent = qnaReplyContent;
		this.qnaReplyCreateDt = qnaReplyCreateDt;
		this.qnaReplyFl = qnaReplyFl;
		this.parentQnaNo = parentQnaNo;
		this.memNickName = memNickName;
		this.memNo = memNo;
		this.qnaReplyResponse = qnaReplyResponse;
	}

	public int getQnaReplyNo() {
		return qnaReplyNo;
	}

	public void setQnaReplyNo(int qnaReplyNo) {
		this.qnaReplyNo = qnaReplyNo;
	}

	public String getQnaReplyContent() {
		return qnaReplyContent;
	}

	public void setQnaReplyContent(String qnaReplyContent) {
		this.qnaReplyContent = qnaReplyContent;
	}

	public Timestamp getQnaReplyCreateDt() {
		return qnaReplyCreateDt;
	}

	public void setQnaReplyCreateDt(Timestamp qnaReplyCreateDt) {
		this.qnaReplyCreateDt = qnaReplyCreateDt;
	}

	public String getQnaReplyFl() {
		return qnaReplyFl;
	}

	public void setQnaReplyFl(String qnaReplyFl) {
		this.qnaReplyFl = qnaReplyFl;
	}

	public int getParentQnaNo() {
		return parentQnaNo;
	}

	public void setParentQnaNo(int parentQnaNo) {
		this.parentQnaNo = parentQnaNo;
	}

	public String getMemNickName() {
		return memNickName;
	}

	public void setMemNickName(String memNickName) {
		this.memNickName = memNickName;
	}

	public int getMemNo() {
		return memNo;
	}

	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}

	public String getQnaReplyResponse() {
		return qnaReplyResponse;
	}

	public void setQnaReplyResponse(String qnaReplyResponse) {
		this.qnaReplyResponse = qnaReplyResponse;
	}

	@Override
	public String toString() {
		return "QnaReply [qnaReplyNo=" + qnaReplyNo + ", qnaReplyContent=" + qnaReplyContent + ", qnaReplyCreateDt="
				+ qnaReplyCreateDt + ", qnaReplyFl=" + qnaReplyFl + ", parentQnaNo=" + parentQnaNo + ", memNickName="
				+ memNickName + ", memNo=" + memNo + ", qnaReplyResponse=" + qnaReplyResponse + "]";
	}

	
	
}
