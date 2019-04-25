package com.jeecg.basstudent.entity;

public class ApiV3SmsEntity {
	private String code;
	
	private String mobiles;
	public String getMobiles() {
		return mobiles;
	}
	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getTs() {
		return ts;
	}
	public void setTs(long ts) {
		this.ts = ts;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	private long ts;
	private int expiresIn;
}
