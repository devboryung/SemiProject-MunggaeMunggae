package com.kh.semi.member.model.vo;

public class Member {
	private int memberNo;
	private String memberId;
	private String memberPwd;
	private String memberNickName;
	private String email;
	private String phone;
	private String gender;
	private String signDt;
	private String scsnFl;
	private String memberAdmin;
	
	
	// -------------- 업체 ---------------------
	
	private String comName;
	private String comAddress;
	private String comPhone;
	
	
	
	// 일반 검색용
	public Member(String memberId, String memberNickName) {
		super();
		this.memberId = memberId;
		this.memberNickName = memberNickName;
	}

	// 관리자 일반 조회용
	public Member(int memberNo, String memberId, String memberNickName, String email, String phone, String gender) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberNickName = memberNickName;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
	}

	// 관리자 업체 조회용
	public Member(String memberId, String memberNickName,int memberNo, String phone, String comName, String comPhone) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberNickName = memberNickName;
		this.phone = phone;
		this.comName = comName;
		this.comPhone = comPhone;
	}

	public String getComName() {
		return comName;


	}

	public void setComName(String comName) {
		this.comName = comName;
	}



	public String getComAddress() {
		return comAddress;
	}



	public void setComAddress(String comAddress) {
		this.comAddress = comAddress;
	}



	public String getComPhone() {
		return comPhone;
	}



	public void setComPhone(String comPhone) {
		this.comPhone = comPhone;
	}



	public Member(String comName, String comAddress, String comPhone) {
		super();
		this.comName = comName;
		this.comAddress = comAddress;
		this.comPhone = comPhone;
	}



	public Member() {}



	public Member(int memberNo, String memberId, String memberPwd, String memberNickName, String email, String phone,
			String gender, String signDt, String scsnFl, String memberAdmin) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberPwd = memberPwd;
		this.memberNickName = memberNickName;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.signDt = signDt;
		this.scsnFl = scsnFl;
		this.memberAdmin = memberAdmin;
	}
	
	


	// 회원가입용 생성자
	public Member(String memberId, String memberPwd, String memberNickName, String email, String phone, String gender) {
		super();
		this.memberId = memberId;
		this.memberPwd = memberPwd;
		this.memberNickName = memberNickName;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
	}
	
	



	public Member(int memberNo, String memberId, String memberNickName, String email, String phone, String gender,
			String memberAdmin) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberNickName = memberNickName;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.memberAdmin = memberAdmin;
	}



	public int getMemberNo() {
		return memberNo;
	}



	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}



	public String getMemberId() {
		return memberId;
	}



	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}



	public String getMemberPwd() {
		return memberPwd;
	}



	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}



	public String getMemberNickName() {
		return memberNickName;
	}



	public void setMemberNickName(String memberNickName) {
		this.memberNickName = memberNickName;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}



	public String getSignDt() {
		return signDt;
	}



	public void setSignDt(String signDt) {
		this.signDt = signDt;
	}



	public String getScsnFl() {
		return scsnFl;
	}



	public void setScsnFl(String scsnFl) {
		this.scsnFl = scsnFl;
	}



	public String getMemberAdmin() {
		return memberAdmin;
	}



	public void setMemberAdmin(String memberAdmin) {
		this.memberAdmin = memberAdmin;
	}



	@Override
	public String toString() {
		return "Member [memberNo=" + memberNo + ", memberId=" + memberId + ", memberPwd=" + memberPwd
				+ ", memberNickName=" + memberNickName + ", email=" + email + ", phone=" + phone + ", gender=" + gender
				+ ", signDt=" + signDt + ", scsnFl=" + scsnFl + ", memberAdmin=" + memberAdmin + ", comName=" + comName
				+ ", comAddress=" + comAddress + ", comPhone=" + comPhone + "]";
	}

}
