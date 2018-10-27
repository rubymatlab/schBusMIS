package com.jeecg.basstudent.service;
import com.jeecg.basstudent.entity.BasStudentTreeEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BasStudentTreeServiceI extends CommonService{
	
 	public void delete(BasStudentTreeEntity entity) throws Exception;
 	
 	public Serializable save(BasStudentTreeEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BasStudentTreeEntity entity) throws Exception;
 	
}
