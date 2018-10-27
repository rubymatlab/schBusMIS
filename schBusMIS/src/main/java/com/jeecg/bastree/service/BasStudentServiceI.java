package com.jeecg.bastree.service;
import com.jeecg.bastree.entity.BasStudentEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BasStudentServiceI extends CommonService{
	
 	public void delete(BasStudentEntity entity) throws Exception;
 	
 	public Serializable save(BasStudentEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BasStudentEntity entity) throws Exception;
 	
}
