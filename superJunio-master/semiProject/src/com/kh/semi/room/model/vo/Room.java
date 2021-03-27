package com.kh.semi.room.model.vo;

public class Room {
	
	private int roomNo;
	private String roomName;
	private String location1;
	private String location2;
	private String phone;
	private String roomInfo;
	private String checkin;
	private String checkout;
	private String facility;
	private String dog;
	private int viewCount;
	private String roomdelFl;
	private int memNo;
	
	
	public Room() {	}
	
	// 목록조회용
		public Room(int roomNo, String roomName, String location2) {
		super();
		this.roomNo = roomNo;
		this.roomName = roomName;
		this.location2 = location2;
	}
		
		
	
		
	

	// 모든 매개변수
	public Room(int roomNo, String roomName, String location1, String location2, String phone, String roomInfo,
			String checkin, String checkout, String facility, String dog, int viewCount, String roomdelFl,
			String roomEnrollFl, int memNo) {
		super();
		this.roomNo = roomNo;
		this.roomName = roomName;
		this.location1 = location1;
		this.location2 = location2;
		this.phone = phone;
		this.roomInfo = roomInfo;
		this.checkin = checkin;
		this.checkout = checkout;
		this.facility = facility;
		this.dog = dog;
		this.viewCount = viewCount;
		this.roomdelFl = roomdelFl;
		this.memNo = memNo;
	}




	public int getRoomNo() {
		return roomNo;
	}


	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}


	public String getRoomName() {
		return roomName;
	}


	public void setRoomName(String roomName) {
		this.roomName = roomName;
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


	public String getRoomInfo() {
		return roomInfo;
	}


	public void setRoomInfo(String roomInfo) {
		this.roomInfo = roomInfo;
	}


	public String getCheckin() {
		return checkin;
	}


	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}


	public String getCheckout() {
		return checkout;
	}


	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}


	public String getFacility() {
		return facility;
	}


	public void setFacility(String facility) {
		this.facility = facility;
	}


	public String getDog() {
		return dog;
	}


	public void setDog(String dog) {
		this.dog = dog;
	}


	public int getViewCount() {
		return viewCount;
	}


	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}


	public String getRoomdelFl() {
		return roomdelFl;
	}


	public void setRoomdelFl(String roomdelFl) {
		this.roomdelFl = roomdelFl;
	}




	public int getMemNo() {
		return memNo;
	}


	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}


	@Override
	public String toString() {
		return "Room [roomNo=" + roomNo + ", roomName=" + roomName + ", location1=" + location1 + ", location2="
				+ location2 + ", phone=" + phone + ", roomInfo=" + roomInfo + ", checkin=" + checkin + ", checkout="
				+ checkout + ", facility=" + facility + ", dog=" + dog + ", viewCount=" + viewCount + ", roomdelFl="
				+ roomdelFl + ", memNo=" + memNo + "]";
	}
	
	

	
}
