package com.kh.semi.tripBoard.model.vo;

public class TripReport {
	
	private int reportNo;
	private String reportTitle;
	private String reportContent;
	private int freeBoardNo;
	private int memberNo;
	
	
	public TripReport() {
		// TODO Auto-generated constructor stub
	}

	
	
	

	public TripReport(String reportTitle, String reportContent, int freeBoardNo, int memberNo) {
		super();
		this.reportTitle = reportTitle;
		this.reportContent = reportContent;
		this.freeBoardNo = freeBoardNo;
		this.memberNo = memberNo;
	}





	public TripReport(String reportTitle, String reportContent, int memberNo) {
		super();
		this.reportTitle = reportTitle;
		this.reportContent = reportContent;
		this.memberNo = memberNo;
	}





	public TripReport(int reportNo, String reportTitle, String reportContent, int freeBoardNo, int memberNo) {
		super();
		this.reportNo = reportNo;
		this.reportTitle = reportTitle;
		this.reportContent = reportContent;
		this.freeBoardNo = freeBoardNo;
		this.memberNo = memberNo;
	}


	public int getReportNo() {
		return reportNo;
	}


	public void setReportNo(int reportNo) {
		this.reportNo = reportNo;
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


	public int getFreeBoardNo() {
		return freeBoardNo;
	}


	public void setFreeBoardNo(int freeBoardNo) {
		this.freeBoardNo = freeBoardNo;
	}


	public int getMemberNo() {
		return memberNo;
	}


	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}


	@Override
	public String toString() {
		return "FreeReport [reportNo=" + reportNo + ", reportTitle=" + reportTitle + ", reportContent=" + reportContent
				+ ", freeBoardNo=" + freeBoardNo + ", memberNo=" + memberNo + "]";
	}
	
	
	
	
	
}
