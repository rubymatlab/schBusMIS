package com.jeecg.bascontrail.service;
import com.jeecg.bascontrail.entity.BusConfigEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BusConfigServiceI extends CommonService{
	
 	public void delete(BusConfigEntity entity) throws Exception;
 	
 	public Serializable save(BusConfigEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BusConfigEntity entity) throws Exception;
 	
}
