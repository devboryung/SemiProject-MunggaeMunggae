package com.kh.semi.CSCenter.model.vo;

public class QnaAttachment {
	private int qnaImgNo;
	private String qnaImgPath;
	private String qnaImgName;
	private int qnaImgLevel;
	private int parentQnaNo; 
	
	public QnaAttachment() {}
	
	
	

	public QnaAttachment(int qnaImgNo, String qnaImgPath, String qnaImgName, int qnaImgLevel) {
		super();
		this.qnaImgNo = qnaImgNo;
		this.qnaImgPath = qnaImgPath;
		this.qnaImgName = qnaImgName;
		this.qnaImgLevel = qnaImgLevel;
	}


	public QnaAttachment(int qnaImgNo, String qnaImgPath, String qnaImgName, int qnaImgLevel, int parentQnaNo) {
		super();
		this.qnaImgNo = qnaImgNo;
		this.qnaImgPath = qnaImgPath;
		this.qnaImgName = qnaImgName;
		this.qnaImgLevel = qnaImgLevel;
		this.parentQnaNo = parentQnaNo;
	}

	public int getQnaImgNo() {
		return qnaImgNo;
	}

	public void setQnaImgNo(int qnaImgNo) {
		this.qnaImgNo = qnaImgNo;
	}

	public String getQnaImgPath() {
		return qnaImgPath;
	}

	public void setQnaImgPath(String qnaImgPath) {
		this.qnaImgPath = qnaImgPath;
	}

	public String getQnaImgName() {
		return qnaImgName;
	}

	public void setQnaImgName(String qnaImgName) {
		this.qnaImgName = qnaImgName;
	}

	public int getQnaImgLevel() {
		return qnaImgLevel;
	}

	public void setQnaImgLevel(int qnaImgLevel) {
		this.qnaImgLevel = qnaImgLevel;
	}

	public int getParentQnaNo() {
		return parentQnaNo;
	}

	public void setParentQnaNo(int parentQnaNo) {
		this.parentQnaNo = parentQnaNo;
	}

	@Override
	public String toString() {
		return "QnaAttachment [qnaImgNo=" + qnaImgNo + ", qnaImgPath=" + qnaImgPath + ", qnaImgName=" + qnaImgName
				+ ", qnaImgLevel=" + qnaImgLevel + ", parentQnaNo=" + parentQnaNo + "]";
	}

	
	
	
}
