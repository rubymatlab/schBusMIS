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
import com.jeecg.basstudent.entity.RequestDelDevice;
import com.jeecg.basstudent.entity.RequestSendDevice;

import net.sf.json.JSONObject;

@Service("SendDevice")
public class DeviceSendJavaInter implements CgformEnhanceReturnJavaInter {

	@Autowired
	private SystemService systemService;

	@Override
	public JSONObject execute(String tableName, Map map) throws BusinessException {
		LogUtil.info("============调用[java增强]成功!========tableName:" + tableName + "===map===" + map);

		Thread thread1 = new Thread(new Runnable() {
			public void run() {
				JSONObject json = new JSONObject();
				// 写入数据库
				List<Map<String, Object>> allDataObject = new ArrayList<Map<String, Object>>();
				allDataObject = systemService.findForJdbc(
						"SELECT bs.bs_deviceid FROM bas_student bs where bs.bs_status='Y' and bs.bs_deviceid is not null");

				for (Map<String, Object> allo : allDataObject) {
					String deviceid = allo.get("bs_deviceid").toString();// "ced25eff-6f2d-4733-a2de-63a0f07e447c";
					System.out.println("开始执行:" + deviceid);

					List<Map<String, Object>> dataObject = new ArrayList<Map<String, Object>>();
					String s1 = "RemoteUrlSend";
					String s2 = "RemoteUrlUserID";
					String s3 = "RemoteUrlAccessToken";
					String s4 = "RemoteUrlCommand";
					StringBuffer sql = new StringBuffer(
							"SELECT bc.cf_code,bc.cf_name,bc.cf_value FROM bus_config bc where bc.cf_code='" + s1
									+ "' or bc.cf_code='" + s2 + "' or bc.cf_code='" + s3 + "' or bc.cf_code='" + s4
									+ "'");
					dataObject = systemService.findForJdbc(sql.toString());// this.systemService.findHql(hql.toString());
					// 开始远程访问
					String requestUrl = "";
					RequestSendDevice rsd = new RequestSendDevice();
					rsd.setDeviceid(deviceid);
					for (Map<String, Object> o : dataObject) {
						if (s1.equals(o.get("cf_code").toString()))
							requestUrl = o.get("cf_value").toString();
						if (s2.equals(o.get("cf_code").toString()))
							rsd.setUserid(o.get("cf_value").toString());
						if (s3.equals(o.get("cf_code").toString()))
							rsd.setAccessToken(o.get("cf_value").toString());
						if (s4.equals(o.get("cf_code").toString()))
							rsd.setCommand(o.get("cf_value").toString());
					}
					System.out.println("访问->" + requestUrl);
					if (requestUrl != "") {
						JSONObject ob = JSONObject.fromObject(rsd);
						json = HttpRequestPost.doPost(requestUrl, ob);
						if (json.get("status").toString().equals("1")) {
							json.put("msg", "下发策略成功");
						}
						System.out.println(json.toString());
					} else
						json.put("msg", "物联网Iot远程url为空");
				}
			}
		});
		thread1.start();

		JSONObject json = new JSONObject();
		json.put("msg", "下发策略成功");
		return json;
	}
}
