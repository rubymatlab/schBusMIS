package com.jeecg.basstudent.entity;

public class RequestDeleteFenceDevice {
	private String id;
	private String deviceid;
	private String fenceid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getFenceid() {
		return fenceid;
	}
	public void setFenceid(String fenceid) {
		this.fenceid = fenceid;
	}
	public String getFencename() {
		return fencename;
	}
	public void setFencename(String fencename) {
		this.fencename = fencename;
	}
	public String getLa() {
		return la;
	}
	public void setLa(String la) {
		this.la = la;
	}
	public String getLo() {
		return lo;
	}
	public void setLo(String lo) {
		this.lo = lo;
	}
	public String getR() {
		return r;
	}
	public void setR(String r) {
		this.r = r;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getSwitchTag() {
		return switchTag;
	}
	public void setSwitchTag(String switchTag) {
		this.switchTag = switchTag;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	private String fencename;
	private String la;
	private String lo;
	private String r;
	private String type;
	private String accessToken;
	private String img;
	private String switchTag;
	private String memo;
	private String userid;
	private String callback_url;
	public String getCallback_url() {
		return callback_url;
	}
	public void setCallback_url(String callback_url) {
		this.callback_url = callback_url;
	}

}
