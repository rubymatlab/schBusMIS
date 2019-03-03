package org.jeecgframework.web.cgform.enhance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeecg.basstudent.entity.HttpRequestPost;
import com.jeecg.basstudent.entity.RequestAddDevice;

import net.sf.json.JSONObject;

@Service("AddDevice")
public class DeviceAddJavaInter implements CgformEnhanceReturnJavaInter {

	@Autowired
	private SystemService systemService;

	@Override

	public JSONObject execute(String tableName, Map map) throws BusinessException {
		LogUtil.info("============调用[java增强]成功!========tableName:" + tableName + "===map===" + map);

		String imei = map.get("bs_cardno").toString();// "M6//jDTq/0mKKk+Jelx9Dg==";

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
			if (json.get("msg").toString().equals("1")) {
				StringBuffer updatesql = new StringBuffer("update bas_student set bs_deviceid='"
						+ json.get("deviceid").toString() + "' where bs_cardno='" + imei + "'");
				this.systemService.executeSql(updatesql.toString());
			}
			if (json.getString("status").equals("1"))
				json.put("msg", "绑定成功");
			else {
				if (json.getString("msg").equals("1"))
					json.put("msg", "添加设备成功");
				if (json.getString("msg").equals("2"))
					json.put("msg", "用户不存在");
				if (json.getString("msg").equals("3"))
					json.put("msg", "设备不存在");
				if (json.getString("msg").equals("4"))
					json.put("msg", "用户已添加了这个设备");
				if (json.getString("msg").equals("5"))
					json.put("msg", "设备已被其它人绑定");
				else
					json.put("msg", "设备为空或已被绑定，绑定不成功");
			}
			System.out.println(json.toString());
		} else
			json.put("msg", "物联网Iot远程url为空");
		return json;
	}
}