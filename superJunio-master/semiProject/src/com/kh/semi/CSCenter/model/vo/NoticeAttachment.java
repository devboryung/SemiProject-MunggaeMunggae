package com.kh.semi.CSCenter.model.vo;

public class NoticeAttachment {
	private int notiFileNo;
	private String notifilePath;
	private String notifileName;
	private int notiFileLevel;
	private int notiParentNo;

	public NoticeAttachment() {
	}
	
	
	

	public NoticeAttachment(int notiFileNo, String notifilePath, String notifileName, int notiFileLevel) {
		super();
		this.notiFileNo = notiFileNo;
		this.notifilePath = notifilePath;
		this.notifileName = notifileName;
		this.notiFileLevel = notiFileLevel;
	}




	public NoticeAttachment(int notiFileNo, String notifilePath, String notifileName, int notiFileLevel,
			int notiParentNo) {
		super();
		this.notiFileNo = notiFileNo;
		this.notifilePath = notifilePath;
		this.notifileName = notifileName;
		this.notiFileLevel = notiFileLevel;
		this.notiParentNo = notiParentNo;
	}




	public int getNotiFileNo() {
		return notiFileNo;
	}

	public void setNotiFileNo(int notiFileNo) {
		this.notiFileNo = notiFileNo;
	}

	public String getNotifilePath() {
		return notifilePath;
	}

	public void setNotifilePath(String notifilePath) {
		this.notifilePath = notifilePath;
	}

	public String getNotifileName() {
		return notifileName;
	}

	public void setNotifileName(String notifileName) {
		this.notifileName = notifileName;
	}

	public int getNotiFileLevel() {
		return notiFileLevel;
	}

	public void setNotiFileLevel(int notiFileLevel) {
		this.notiFileLevel = notiFileLevel;
	}

	public int getNotiParentNo() {
		return notiParentNo;
	}

	public void setNotiParentNo(int notiParentNo) {
		this.notiParentNo = notiParentNo;
	}

	@Override
	public String toString() {
		return "noticeAttachment [notiFileNo=" + notiFileNo + ", notifilePath=" + notifilePath + ", notifileName="
				+ notifileName + ", notiFileLevel=" + notiFileLevel + ", notiParentNo=" + notiParentNo + "]";
	}
	
	

}
