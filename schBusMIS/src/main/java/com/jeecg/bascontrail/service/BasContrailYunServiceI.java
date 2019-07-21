package com.jeecg.bascontrail.service;
import com.jeecg.bascontrail.entity.BasContrailYunEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BasContrailYunServiceI extends CommonService{
	
 	public void delete(BasContrailYunEntity entity) throws Exception;
 	
 	public Serializable save(BasContrailYunEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BasContrailYunEntity entity) throws Exception;
 	
}
