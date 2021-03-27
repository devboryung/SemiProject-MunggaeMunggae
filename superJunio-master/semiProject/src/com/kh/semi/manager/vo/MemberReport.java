package com.kh.semi.manager.vo;

public class MemberReport {
	private int reportNo;
	private String boardTitle;
	private String memId;
	private String reportTitle;
	private String reportContent;
	
	
	public MemberReport() {
		// TODO Auto-generated constructor stub
	}


	public MemberReport(int reportNo, String boardTitle, String memId, String reportTitle, String reportContent) {
		super();
		this.reportNo = reportNo;
		this.boardTitle = boardTitle;
		this.memId = memId;
		this.reportTitle = reportTitle;
		this.reportContent = reportContent;
	}


	public int getReportNo() {
		return reportNo;
	}


	public void setReportNo(int reportNo) {
		this.reportNo = reportNo;
	}


	public String getBoardTitle() {
		return boardTitle;
	}


	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}


	public String getMemId() {
		return memId;
	}


	public void setMemId(String memId) {
		this.memId = memId;
	}


	public String getReportTitle() {
		return reportTitle;
	}


	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}


	public String getReportContent() {
		return reportContent;
	}


	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}


	@Override
	public String toString() {
		return "MemberReport [reportNo=" + reportNo + ", boardTitle=" + boardTitle + ", memId=" + memId
				+ ", reportTitle=" + reportTitle + ", reportContent=" + reportContent + "]";
	}
	
	
	
	

}
