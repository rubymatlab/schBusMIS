package com.jeecg.bascontrail.service;
import com.jeecg.bascontrail.entity.BusOpenidEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BusOpenidServiceI extends CommonService{
	
 	public void delete(BusOpenidEntity entity) throws Exception;
 	
 	public Serializable save(BusOpenidEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BusOpenidEntity entity) throws Exception;
 	
}
