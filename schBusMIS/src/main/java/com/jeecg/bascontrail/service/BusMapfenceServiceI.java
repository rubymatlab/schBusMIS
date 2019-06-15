package com.jeecg.bascontrail.service;
import com.jeecg.bascontrail.entity.BusMapfenceEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BusMapfenceServiceI extends CommonService{
	
 	public void delete(BusMapfenceEntity entity) throws Exception;
 	
 	public Serializable save(BusMapfenceEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BusMapfenceEntity entity) throws Exception;
 	
}
