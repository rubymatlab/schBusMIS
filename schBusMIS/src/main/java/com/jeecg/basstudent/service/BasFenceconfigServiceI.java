package com.jeecg.basstudent.service;
import com.jeecg.basstudent.entity.BasFenceconfigEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BasFenceconfigServiceI extends CommonService{
	
 	public void delete(BasFenceconfigEntity entity) throws Exception;
 	
 	public Serializable save(BasFenceconfigEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BasFenceconfigEntity entity) throws Exception;
 	
}
