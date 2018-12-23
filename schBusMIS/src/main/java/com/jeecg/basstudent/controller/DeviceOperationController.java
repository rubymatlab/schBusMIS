package com.jeecg.basstudent.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeecg.basstudent.entity.HttpRequestPost;
import com.jeecg.basstudent.entity.RequestAddDevice;
import com.jeecg.basstudent.entity.RequestDelDevice;
import com.jeecg.basstudent.entity.RequestLocationDevice;
import com.jeecg.basstudent.entity.RequestLocationsDevice;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/DeviceOperationController")
public class DeviceOperationController extends BaseController {
	@Autowired
	private SystemService systemService;

	@RequestMapping(params = "AddDevice")
	@ResponseBody
	public void AddDevice() {
		String imei = "M6//jDTq/0mKKk+Jelx9Dg==";
		System.out.println("开始执行:" + imei);
		JSONObject json = new JSONObject();
		List<Map<String, Object>> dataObject = new ArrayList<Map<String, Object>>();
		String s1 = "RemoteUrlAdd";
		String s2 = "RemoteUrlUserID";
		String s3 = "RemoteUrlAccessToken";
		StringBuffer sql = new StringBuffer(
				"SELECT bc.cf_code,bc.cf_name,bc.cf_value FROM bus_config bc where bc.cf_code='" + s1
						+ "' or bc.cf_code='" + s2 + "' or bc.cf_code='" + s3 + "'");
		dataObject = this.systemService.findForJdbc(sql.toString());// this.systemService.findHql(hql.toString());
		// 开始远程访问
		String requestUrl = "";
		RequestAddDevice rad = new RequestAddDevice();
		rad.setDeviceid(imei);
		for (Map<String, Object> o : dataObject) {
			if (s1.equals(o.get("cf_code").toString()))
				requestUrl = o.get("cf_value").toString();
			if (s2.equals(o.get("cf_code").toString()))
				rad.setUserid(o.get("cf_value").toString());
			if (s3.equals(o.get("cf_code").toString()))
				rad.setAccessToken(o.get("cf_value").toString());
		}
		System.out.println("访问->" + requestUrl);
		if (requestUrl != "") {
			JSONObject ob = JSONObject.fromObject(rad);
			json = HttpRequestPost.doPost(requestUrl, ob);
			System.out.println(json.toString());
		}
	}

	@RequestMapping(params = "DelDevice")
	@ResponseBody
	public void DelDevice() {
		String deviceid = "ced25eff-6f2d-4733-a2de-63a0f07e447c";
		System.out.println("开始执行:" + deviceid);
		JSONObject json = new JSONObject();
		List<Map<String, Object>> dataObject = new ArrayList<Map<String, Object>>();
		String s1 = "RemoteUrlDelete";
		String s2 = "RemoteUrlUserID";
		String s3 = "RemoteUrlAccessToken";
		String s4 = "RemoteUrlIsCleanData";
		StringBuffer sql = new StringBuffer(
				"SELECT bc.cf_code,bc.cf_name,bc.cf_value FROM bus_config bc where bc.cf_code='" + s1
						+ "' or bc.cf_code='" + s2 + "' or bc.cf_code='" + s3 + "' or bc.cf_code='" + s4 + "'");
		dataObject = this.systemService.findForJdbc(sql.toString());// this.systemService.findHql(hql.toString());
		// 开始远程访问
		String requestUrl = "";
		RequestDelDevice rdd = new RequestDelDevice();
		rdd.setDeviceid(deviceid);
		for (Map<String, Object> o : dataObject) {
			if (s1.equals(o.get("cf_code").toString()))
				requestUrl = o.get("cf_value").toString();
			if (s2.equals(o.get("cf_code").toString()))
				rdd.setUserid(o.get("cf_value").toString());
			if (s3.equals(o.get("cf_code").toString()))
				rdd.setAccessToken(o.get("cf_value").toString());
			if (s4.equals(o.get("cf_code").toString()))
				rdd.setIsCleanData(o.get("cf_value").toString());
		}
		System.out.println("访问->" + requestUrl);
		if (requestUrl != "") {
			JSONObject ob = JSONObject.fromObject(rdd);
			json = HttpRequestPost.doPost(requestUrl, ob);
			System.out.println(json.toString());
		}
	}
	
	@RequestMapping(params = "LocationDevice")
	@ResponseBody
	public void LocationDevice() {
		String deviceid = "ced25eff-6f2d-4733-a2de-63a0f07e447c";
		System.out.println("开始执行:" + deviceid);
		JSONObject json = new JSONObject();
		List<Map<String, Object>> dataObject = new ArrayList<Map<String, Object>>();
		String s1 = "RemoteUrlGet";
		String s2 = "RemoteUrlUserID";
		String s3 = "RemoteUrlAccessToken";
		StringBuffer sql = new StringBuffer(
				"SELECT bc.cf_code,bc.cf_name,bc.cf_value FROM bus_config bc where bc.cf_code='" + s1
						+ "' or bc.cf_code='" + s2 + "' or bc.cf_code='" + s3 + "'");
		dataObject = this.systemService.findForJdbc(sql.toString());// this.systemService.findHql(hql.toString());
		// 开始远程访问
		String requestUrl = "";
		RequestLocationDevice rld = new RequestLocationDevice();
		rld.setDeviceid(deviceid);
		for (Map<String, Object> o : dataObject) {
			if (s1.equals(o.get("cf_code").toString()))
				requestUrl = o.get("cf_value").toString();
			if (s2.equals(o.get("cf_code").toString()))
				rld.setUserid(o.get("cf_value").toString());
			if (s3.equals(o.get("cf_code").toString()))
				rld.setAccessToken(o.get("cf_value").toString());
		}
		System.out.println("访问->" + requestUrl);
		if (requestUrl != "") {
			JSONObject ob = JSONObject.fromObject(rld);
			json = HttpRequestPost.doPost(requestUrl, ob);
			System.out.println(json.toString());
		}
	}
	
	@RequestMapping(params = "LocationsDevice")
	@ResponseBody
	public void LocationsDevice() {
		String deviceid = "ced25eff-6f2d-4733-a2de-63a0f07e447c,8c6e4b68-cbf5-4f79-918c-37563822ca1e";
		System.out.println("开始执行:" + deviceid);
		JSONObject json = new JSONObject();
		List<Map<String, Object>> dataObject = new ArrayList<Map<String, Object>>();
		String s1 = "RemoteUrlGets";
		String s3 = "RemoteUrlAccessToken";
		StringBuffer sql = new StringBuffer(
				"SELECT bc.cf_code,bc.cf_name,bc.cf_value FROM bus_config bc where bc.cf_code='" + s1
						+ "' or bc.cf_code='" + s3 + "'");
		dataObject = this.systemService.findForJdbc(sql.toString());// this.systemService.findHql(hql.toString());
		// 开始远程访问
		String requestUrl = "";
		RequestLocationsDevice rlsd = new RequestLocationsDevice();
		rlsd.setDEVICEIDS(deviceid);
		for (Map<String, Object> o : dataObject) {
			if (s1.equals(o.get("cf_code").toString()))
				requestUrl = o.get("cf_value").toString();
			if (s3.equals(o.get("cf_code").toString()))
				rlsd.setAccessToken(o.get("cf_value").toString());
		}
		System.out.println("访问->" + requestUrl);
		if (requestUrl != "") {
			JSONObject ob = JSONObject.fromObject(rlsd);
			json = HttpRequestPost.doPost(requestUrl, ob);
			System.out.println(json.toString());
		}
	}
}