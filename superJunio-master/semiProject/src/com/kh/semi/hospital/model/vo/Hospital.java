package com.kh.semi.hospital.model.vo;


public class Hospital {
	private int hospNo;
	private String hospNm;
	private String location1;
	private String location2;
	private String phone;
	private String openingTime;
	private String closingTime;
	private String hospInfo;
	private int viewCount;
	private String hospDelFl;
	private int memNo;
	private String  hospFacility;
	
	public Hospital() {}
	
	
//	list 조회 용 생성자
	public Hospital(int hospNo, String hospNm, String location2, String phone, String openingTime, String closingTime) {
		super();
		this.hospNo = hospNo;
		this.hospNm = hospNm;
		this.location2 = location2;
		this.phone = phone;
		this.openingTime = openingTime;
		this.closingTime = closingTime;
	}

	
	// 상세조회용 생성자
	public Hospital(String hospNm, String location2, String phone, String openingTime, String closingTime,
			String hospInfo, int viewCount, String hospFacility) {
		super();
		this.hospNm = hospNm;
		this.location2 = location2;
		this.phone = phone;
		this.openingTime = openingTime;
		this.closingTime = closingTime;
		this.hospInfo = hospInfo;
		this.viewCount = viewCount;
		this.hospFacility = hospFacility;
	}


	
	// 모든 매개변수
	public Hospital(int hospNo, String hospNm, String location1, String location2, String phone, String openingTime,
				String closingTime, String hospInfo, int viewCount, String hospDelFl, int memNo, String hospFacility) {
			super();
			this.hospNo = hospNo;
			this.hospNm = hospNm;
			this.location1 = location1;
			this.location2 = location2;
			this.phone = phone;
			this.openingTime = openingTime;
			this.closingTime = closingTime;
			this.hospInfo = hospInfo;
			this.viewCount = viewCount;
			this.hospDelFl = hospDelFl;
			this.memNo = memNo;
			this.hospFacility = hospFacility;
		}
	
	
	
	
	
	public int getHospNo() {
		return hospNo;
	}
	
	
	public void setHospNo(int hospNo) {
		this.hospNo = hospNo;
	}
	
	
	public String getHospNm() {
		return hospNm;
	}
	
	
	public void setHospNm(String hospNm) {
		this.hospNm = hospNm;
	}
	
	
	public String getLocation1() {
		return location1;
	}
	
	
	public void setLocation1(String location1) {
		this.location1 = location1;
	}
	
	
	public String getLocation2() {
		return location2;
	}
	
	
	public void setLocation2(String location2) {
		this.location2 = location2;
	}
	
	
	public String getPhone() {
		return phone;
	}
	
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	public String getOpeningTime() {
		return openingTime;
	}
	
	
	public void setOpeningTime(String openingTime) {
		this.openingTime = openingTime;
	}
	
	
	public String getClosingTime() {
		return closingTime;
	}
	
	
	public void setClosingTime(String closingTime) {
		this.closingTime = closingTime;
	}
	
	
	public String getHospInfo() {
		return hospInfo;
	}
	
	
	public void setHospInfo(String hospInfo) {
		this.hospInfo = hospInfo;
	}
	
	
	public int getViewCount() {
		return viewCount;
	}
	
	
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	
	
	public String getHospDelFl() {
		return hospDelFl;
	}
	
	
	public void setHospDelFl(String hospDelFl) {
		this.hospDelFl = hospDelFl;
	}
	
	
	public int getMemNo() {
		return memNo;
	}
	
	
	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}
	
	
	public String getHospFacility() {
		return hospFacility;
	}
	
	
	public void setHospFacility(String hospFacility) {
		this.hospFacility = hospFacility;
	}
	
	
	@Override
	public String toString() {
		return "Hospital [hospNo=" + hospNo + ", hospNm=" + hospNm + ", location1=" + location1 + ", location2=" + location2
				+ ", phone=" + phone + ", openingTime=" + openingTime + ", closingTime=" + closingTime + ", hospInfo="
				+ hospInfo + ", viewCount=" + viewCount + ", hospDelFl=" + hospDelFl + ", memNo=" + memNo
				+ ", hospFacility=" + hospFacility + "]";
	}

}

