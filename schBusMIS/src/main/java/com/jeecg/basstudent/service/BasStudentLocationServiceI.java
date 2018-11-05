package com.jeecg.basstudent.service;
import com.jeecg.basstudent.entity.BasStudentLocationEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BasStudentLocationServiceI extends CommonService{
	
 	public void delete(BasStudentLocationEntity entity) throws Exception;
 	
 	public Serializable save(BasStudentLocationEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BasStudentLocationEntity entity) throws Exception;
 	
}
