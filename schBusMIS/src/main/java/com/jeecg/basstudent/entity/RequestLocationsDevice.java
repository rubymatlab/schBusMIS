package com.jeecg.basstudent.entity;

public class RequestLocationsDevice {
	public String getDEVICEIDS() {
		return DEVICEIDS;
	}
	public void setDEVICEIDS(String dEVICEIDS) {
		DEVICEIDS = dEVICEIDS;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	private String	DEVICEIDS;
	private String	accessToken;
}
