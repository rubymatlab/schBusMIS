package com.jeecg.bascontrail.service;
import com.jeecg.bascontrail.entity.BusUploaddataEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BusUploaddataServiceI extends CommonService{
	
 	public void delete(BusUploaddataEntity entity) throws Exception;
 	
 	public Serializable save(BusUploaddataEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BusUploaddataEntity entity) throws Exception;
 	
}
