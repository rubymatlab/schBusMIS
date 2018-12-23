package com.jeecg.basstudent.entity;

public class RequestDelDevice {
	private String deviceid;
	private String userid;
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	private String accessToken;
	private String isCleanData;
	public String getIsCleanData() {
		return isCleanData;
	}
	public void setIsCleanData(String isCleanData) {
		this.isCleanData = isCleanData;
	}
}
