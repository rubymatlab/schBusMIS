package org.jeecgframework.web.cgform.enhance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeecg.basstudent.entity.BasFenceremoteEntity;
import com.jeecg.basstudent.entity.HttpRequestPost;
import com.jeecg.basstudent.entity.RequestDeleteFenceDevice;

import net.sf.json.JSONObject;

@Service("AddFenceDevice")
public class DeviceAddFenceJavaInter implements CgformEnhanceReturnJavaInter{

	@Autowired
	private SystemService systemService;

	@Override
	public JSONObject execute(String tableName, Map map) throws BusinessException {
		LogUtil.info("============调用[java增强]成功!========tableName:" + tableName + "===map===" + map);
		List<Map<String, Object>> listT=this.systemService.findForJdbc("select bs.bs_deviceid from bas_student bs where bs.bs_deviceid is not null");
		
		String deviceid = "";
		if (listT.size()>0)
			deviceid = listT.get(0).get("bs_deviceid").toString();
		System.out.println("开始执行添加围栏电子信息");
		
		JSONObject json = new JSONObject();
		List<Map<String, Object>> dataObject = new ArrayList<Map<String, Object>>();
		String s1 = "RemoteUrlFence";
		String s2 = "RemoteUrlUserID";
		String s3 = "RemoteUrlAccessToken";
		String s4 = "RemoteCallBackUrl";
		String s5 = "MapFenceName";
		String s6="MapLongitude";
		String s7="MapLatitude";
		String s8="MapDistance";
		
		StringBuffer sql = new StringBuffer(
				"SELECT bc.cf_code,bc.cf_name,bc.cf_value FROM bus_config bc where bc.cf_code='" + s1
						+ "' or bc.cf_code='" + s2 + "' "
						+ " or bc.cf_code='" + s3 + "' "
						+ " or bc.cf_code='" + s4 + "' "
						+ " or bc.cf_code='" + s5 + "' "
						+ " or bc.cf_code='" + s6 + "' "
						+ " or bc.cf_code='" + s7 + "' "
						+ " or bc.cf_code='" + s8 + "' ");
		dataObject = this.systemService.findForJdbc(sql.toString());// this.systemService.findHql(hql.toString());
		// 开始远程访问
		String requestUrl = "";
		RequestDeleteFenceDevice rdd = new RequestDeleteFenceDevice();
		rdd.setId("");
		rdd.setDeviceid(deviceid);
		rdd.setFenceid(String.valueOf(Calendar.getInstance().getTimeInMillis()));
		rdd.setImg("");
		rdd.setMemo("");
		rdd.setType("1");
		rdd.setSwitchTag("3");
		for (Map<String, Object> o : dataObject) {
			if (s1.equals(o.get("cf_code").toString()))
				requestUrl = o.get("cf_value").toString();
			if (s2.equals(o.get("cf_code").toString()))
				rdd.setUserid(o.get("cf_value").toString());
			if (s3.equals(o.get("cf_code").toString()))
				rdd.setAccessToken(o.get("cf_value").toString());
			if (s4.equals(o.get("cf_code").toString()))
				rdd.setCallback_url(o.get("cf_value").toString());
			if (s5.equals(o.get("cf_code").toString()))
				rdd.setFencename(o.get("cf_value").toString());
			if (s6.equals(o.get("cf_code").toString()))
				rdd.setLo(o.get("cf_value").toString());
			if (s7.equals(o.get("cf_code").toString()))
				rdd.setLa(o.get("cf_value").toString());
			if (s8.equals(o.get("cf_code").toString()))
				rdd.setR(o.get("cf_value").toString());
		}
		System.out.println("访问->" + requestUrl);
		if (requestUrl != "") {
			//System.out.println(JSONObject.fromObject(rdd).toString());
			JSONObject ob = JSONObject.fromObject(rdd);
			json = HttpRequestPost.doPost(requestUrl, ob);
			if (json.getString("status").equals("1"))
				json.put("msg", "添加成功");
			else {
				json.put("msg", "添加失败");
			}
			System.out.println(json.toString());
		} else
			json.put("msg", "物联网Iot远程url为空");
		return json;
	}
}