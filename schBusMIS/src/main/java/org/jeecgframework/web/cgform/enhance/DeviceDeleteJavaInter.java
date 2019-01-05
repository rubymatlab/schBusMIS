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

import net.sf.json.JSONObject;
@Service("DeleteDevice")
public class DeviceDeleteJavaInter implements CgformEnhanceJavaInter {
	
	@Autowired
	private SystemService systemService;
	
    @Override
    public void execute(String tableName,Map map) throws BusinessException {
    	LogUtil.info("============调用[java增强]成功!========tableName:"+tableName+"===map==="+map);
    	
    	String deviceid = map.get("bs_deviceid").toString();//"ced25eff-6f2d-4733-a2de-63a0f07e447c";
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
			if(json.get("msg").toString().equals("1"))
			{
				StringBuffer updatesql = new StringBuffer(
						"update bas_student set bs_deviceid=null ,bs_cardno=null where bs_deviceid='"+deviceid+"'");
				this.systemService.executeSql(updatesql.toString());
			}
			System.out.println(json.toString());
		}
    }
}
