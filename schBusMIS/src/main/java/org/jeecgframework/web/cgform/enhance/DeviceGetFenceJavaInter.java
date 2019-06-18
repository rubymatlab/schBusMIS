package org.jeecgframework.web.cgform.enhance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeecg.basstudent.entity.BasFenceremoteEntity;
import com.jeecg.basstudent.entity.HttpRequestPost;
import com.jeecg.basstudent.entity.RequestDelDevice;
import com.jeecg.basstudent.entity.RequestLocationDevice;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("GetFenceDevice")
public class DeviceGetFenceJavaInter implements CgformEnhanceReturnJavaInter{

	@Autowired
	private SystemService systemService;

	@Override
	public JSONObject execute(String tableName, Map map) throws BusinessException {
		List<Map<String, Object>> listT=this.systemService.findForJdbc("select bs.bs_deviceid from bas_student bs where bs.bs_deviceid is not null");
		
		String deviceid = "";
		if (listT.size()>0)
			deviceid = listT.get(0).get("bs_deviceid").toString();
		System.out.println("开始执行围栏电子信息");
		JSONObject json = new JSONObject();
		List<Map<String, Object>> dataObject = new ArrayList<Map<String, Object>>();
		String s1 = "RemoteUrlGetFence";
		String s2 = "RemoteUrlUserID";
		String s3 = "RemoteUrlAccessToken";
		StringBuffer sql = new StringBuffer(
				"SELECT bc.cf_code,bc.cf_name,bc.cf_value FROM bus_config bc where bc.cf_code='" + s1
						+ "' or bc.cf_code='" + s2 + "' or bc.cf_code='" + s3 + "' ");
		dataObject = this.systemService.findForJdbc(sql.toString());// this.systemService.findHql(hql.toString());
		// 开始远程访问
		String requestUrl = "";
		RequestLocationDevice rdd = new RequestLocationDevice();
		rdd.setDeviceid(deviceid);
		for (Map<String, Object> o : dataObject) {
			if (s1.equals(o.get("cf_code").toString()))
				requestUrl = o.get("cf_value").toString();
			if (s2.equals(o.get("cf_code").toString()))
				rdd.setUserid(o.get("cf_value").toString());
			if (s3.equals(o.get("cf_code").toString()))
				rdd.setAccessToken(o.get("cf_value").toString());
		}
		System.out.println("访问->" + requestUrl);
		if (requestUrl != "" && deviceid!="") {
			this.systemService.executeSql("DELETE FROM bas_fenceremote");
			JSONObject ob = JSONObject.fromObject(rdd);
			json = HttpRequestPost.doPost(requestUrl, ob);
			JSONArray jsonArr = JSONArray.fromObject(json.getString("result"));
			if(jsonArr.size()>0){
				for (int i = 0; i < jsonArr.size(); i++) {
					JSONObject job = jsonArr.getJSONObject(i);
					BasFenceremoteEntity bfe = new BasFenceremoteEntity();
					if (job.containsKey("id"))
						bfe.setId1(job.getString("id"));
					if (job.containsKey("deviceId"))
						bfe.setDeviceid(job.getString("deviceId"));
					if (job.containsKey("fenceid"))
						bfe.setFenceid(job.getString("fenceid"));
					if (job.containsKey("fencename"))
						bfe.setFencename(job.getString("fencename"));
					if (job.containsKey("img"))
						bfe.setImg(job.getString("img"));
					if (job.containsKey("la"))
						bfe.setLa(job.getString("la"));
					if (job.containsKey("lo"))
						bfe.setLo(job.getString("lo"));
					if (job.containsKey("r"))
						bfe.setR(job.getString("r"));
					if (job.containsKey("switchTag"))
						bfe.setSwitchtag(job.getString("switchTag"));
					this.systemService.save(bfe);
				}
			}
			
			if (json.getString("status").equals("1"))
				json.put("msg", "获取成功");
			else {
				json.put("msg", "获取失败");
			}
			System.out.println(json.toString());
		} else
			json.put("msg", "物联网Iot远程url为空");
		return json;
	}
}
