package com.jeecg.bascontrail.service;
import com.jeecg.bascontrail.entity.BasContrailEntity;

import net.sf.json.JSONObject;

import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BasContrailServiceI extends CommonService{
	
 	public void delete(BasContrailEntity entity) throws Exception;
 	
 	public Serializable save(BasContrailEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BasContrailEntity entity) throws Exception;
 	
 	/**
	 * 自定义按钮-[绑定设备]业务处理
	 * @param id
	 * @return
	 */
	 public void doAddDeviceBus(BasContrailEntity t) throws Exception;
 	/**
	 * 自定义按钮-[解绑设备]业务处理
	 * @param id
	 * @return
	 */
	 public void doDeleteDeviceBus(BasContrailEntity t) throws Exception;
	 
		/**
	 * 自定义按钮-[查询轨迹]业务处理
	 * 
	 * @param id
	 * @return
	 */
		public JSONObject doContrailDeviceBus(BasContrailEntity t) throws Exception;
}
