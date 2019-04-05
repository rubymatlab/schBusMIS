package org.jeecgframework.web.cgform.enhance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeecg.basstudent.entity.HttpRequestPost;
import com.jeecg.basstudent.entity.RequestContrailDevice;
import com.jeecg.basstudent.entity.RequestDelDevice;

import net.sf.json.JSONObject;

@Service("ContrailDevice")
public class DeviceContrailJavaInter implements CgformEnhanceReturnJavaInter {

	@Autowired
	private SystemService systemService;

	@Override
	public JSONObject execute(String tableName, Map map) throws BusinessException {
		LogUtil.info("============调用[java增强]成功!========tableName:" + tableName + "===map===" + map);

		String deviceid = map.get("bs_deviceid").toString();// "ced25eff-6f2d-4733-a2de-63a0f07e447c";
		System.out.println("开始执行:" + deviceid);
		JSONObject json = new JSONObject();
		List<Map<String, Object>> dataObject = new ArrayList<Map<String, Object>>();
		String s1 = "RemoteUrlContrail";
		String s2 = "RemoteUrlUserID";
		String s3 = "RemoteUrlAccessToken";
		StringBuffer sql = new StringBuffer(
				"SELECT bc.cf_code,bc.cf_name,bc.cf_value FROM bus_config bc where bc.cf_code='" + s1
						+ "' or bc.cf_code='" + s2 + "' or bc.cf_code='" + s3 + "'");
		dataObject = this.systemService.findForJdbc(sql.toString());// this.systemService.findHql(hql.toString());
		// 开始远程访问
		String requestUrl = "";
		RequestContrailDevice rcd = new RequestContrailDevice();
		rcd.setDeviceid(deviceid);
		for (Map<String, Object> o : dataObject) {
			if (s1.equals(o.get("cf_code").toString()))
				requestUrl = o.get("cf_value").toString();
			if (s2.equals(o.get("cf_code").toString()))
				rcd.setUserid(o.get("cf_value").toString());
			if (s3.equals(o.get("cf_code").toString()))
				rcd.setAccessToken(o.get("cf_value").toString());
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		try {
			Date date =sdf.parse(map.get("create_date").toString());
			rcd.setStartTime(df.format(date));
		} catch (Exception e) {
		}
		try {
			Date date =sdf.parse(map.get("update_date").toString());
			rcd.setEndTime(df.format(date));
		} catch (Exception e) {
		}
		System.out.println("访问->" + requestUrl);
		if (requestUrl != "") {
			JSONObject ob = JSONObject.fromObject(rcd);
			json = HttpRequestPost.doPost(requestUrl, ob);
			if (json.getString("status").equals("1"))
				json.put("msg", json.get("result"));
			else {
				json.put("msg", "没有获取数据");
			}
			//System.out.println(ob.toString());
		} else
			json.put("msg", "物联网Iot远程url为空");
		return json;
	}
}
