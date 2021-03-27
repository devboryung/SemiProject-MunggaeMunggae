package com.kh.semi.CSCenter.model.vo;

public class FaqAttachment {
	private int faqFileNo;
	private String faqFilePath;
	private String faqFileName;
	private int faqFileLevel;
	private int parentfaqNo;
	
	
	public FaqAttachment() {
	}

	public FaqAttachment(int faqFileNo, String faqFilePath, String faqFileName, int faqFileLevel) {
		super();
		this.faqFileNo = faqFileNo;
		this.faqFilePath = faqFilePath;
		this.faqFileName = faqFileName;
		this.faqFileLevel = faqFileLevel;
	}


	public FaqAttachment(int faqFileNo, String faqFilePath, String faqFileName, int faqFileLevel, int parentfaqNo) {
		super();
		this.faqFileNo = faqFileNo;
		this.faqFilePath = faqFilePath;
		this.faqFileName = faqFileName;
		this.faqFileLevel = faqFileLevel;
		this.parentfaqNo = parentfaqNo;
	}


	public int getFaqFileNo() {
		return faqFileNo;
	}


	public void setFaqFileNo(int faqFileNo) {
		this.faqFileNo = faqFileNo;
	}


	public String getFaqFilePath() {
		return faqFilePath;
	}


	public void setFaqFilePath(String faqFilePath) {
		this.faqFilePath = faqFilePath;
	}


	public String getFaqFileName() {
		return faqFileName;
	}


	public void setFaqFileName(String faqFileName) {
		this.faqFileName = faqFileName;
	}


	public int getFaqFileLevel() {
		return faqFileLevel;
	}


	public void setFaqFileLevel(int faqFileLevel) {
		this.faqFileLevel = faqFileLevel;
	}


	public int getParentfaqNo() {
		return parentfaqNo;
	}


	public void setParentfaqNo(int parentfaqNo) {
		this.parentfaqNo = parentfaqNo;
	}


	@Override
	public String toString() {
		return "FaqAttachment [faqFileNo=" + faqFileNo + ", faqFilePath=" + faqFilePath + ", faqFileName=" + faqFileName
				+ ", faqFileLevel=" + faqFileLevel + ", parentfaqNo=" + parentfaqNo + "]";
	}
	
	
}
