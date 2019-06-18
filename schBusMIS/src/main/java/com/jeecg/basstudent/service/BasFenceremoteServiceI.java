package com.jeecg.basstudent.service;
import com.jeecg.basstudent.entity.BasFenceremoteEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BasFenceremoteServiceI extends CommonService{
	
 	public void delete(BasFenceremoteEntity entity) throws Exception;
 	
 	public Serializable save(BasFenceremoteEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BasFenceremoteEntity entity) throws Exception;
 	public void doAddBus(BasFenceremoteEntity t) throws Exception;
 	public void doDelBus(BasFenceremoteEntity t) throws Exception;
 	/**
	 * 自定义按钮-[添加围栏]业务处理
	 * @param id
	 * @return
	 */
	 public void doAddFenceDeviceBus(BasFenceremoteEntity t) throws Exception;
 	/**
	 * 自定义按钮-[获取围栏]业务处理
	 * @param id
	 * @return
	 */
	 public void doGetFenceDeviceBus(BasFenceremoteEntity t) throws Exception;
}
