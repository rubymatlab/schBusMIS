package com.jeecg.basstudent.service;
import com.jeecg.basstudent.entity.VwBasCarinfoEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface VwBasCarinfoServiceI extends CommonService{
	
 	public void delete(VwBasCarinfoEntity entity) throws Exception;
 	
 	public Serializable save(VwBasCarinfoEntity entity) throws Exception;
 	
 	public void saveOrUpdate(VwBasCarinfoEntity entity) throws Exception;
 	
}
