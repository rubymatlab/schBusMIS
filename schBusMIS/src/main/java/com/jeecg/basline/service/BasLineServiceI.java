package com.jeecg.basline.service;
import com.jeecg.basline.entity.BasLineEntity;
import com.jeecg.basline.entity.BasSizeEntity;

import java.util.List;
import org.jeecgframework.core.common.service.CommonService;
import java.io.Serializable;

public interface BasLineServiceI extends CommonService{
 	public void delete(BasLineEntity entity) throws Exception;
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(BasLineEntity basLine,
	        List<BasSizeEntity> basSizeList) throws Exception;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(BasLineEntity basLine,
	        List<BasSizeEntity> basSizeList) throws Exception;
	        
	/**
	 * 删除一对多
	 * 
	 */
	public void delMain (BasLineEntity basLine) throws Exception;
}
