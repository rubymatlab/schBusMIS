package com.jeecg.basclass.service;
import com.jeecg.basclass.entity.BasClassEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BasClassServiceI extends CommonService{
	
 	public void delete(BasClassEntity entity) throws Exception;
 	
 	public Serializable save(BasClassEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BasClassEntity entity) throws Exception;
 	
}
