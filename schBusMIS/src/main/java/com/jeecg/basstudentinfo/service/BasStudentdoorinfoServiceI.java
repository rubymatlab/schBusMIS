package com.jeecg.basstudentinfo.service;
import com.jeecg.basstudentinfo.entity.BasStudentdoorinfoEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BasStudentdoorinfoServiceI extends CommonService{
	
 	public void delete(BasStudentdoorinfoEntity entity) throws Exception;
 	
 	public Serializable save(BasStudentdoorinfoEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BasStudentdoorinfoEntity entity) throws Exception;
 	
}
