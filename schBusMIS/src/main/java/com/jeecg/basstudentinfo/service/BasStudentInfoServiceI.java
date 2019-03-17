package com.jeecg.basstudentinfo.service;
import com.jeecg.basstudentinfo.entity.BasStudentInfoEntity;

import net.sf.json.JSONObject;

import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BasStudentInfoServiceI extends CommonService{
	
 	public void delete(BasStudentInfoEntity entity) throws Exception;
 	
 	public Serializable save(BasStudentInfoEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BasStudentInfoEntity entity) throws Exception;
 	
 	/**
	 * 自定义按钮-[绑定设备]业务处理
	 * @param id
	 * @return
	 */
	 public JSONObject doAddDeviceBus(BasStudentInfoEntity t) throws Exception;
 	/**
	 * 自定义按钮-[解绑设备]业务处理
	 * @param id
	 * @return
	 */
	 public JSONObject doDeleteDeviceBus(BasStudentInfoEntity t) throws Exception;
}
