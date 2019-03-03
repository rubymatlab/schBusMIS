package org.jeecgframework.web.cgform.enhance;

import java.util.Map;

import org.jeecgframework.core.common.exception.BusinessException;

import net.sf.json.JSONObject;

public interface CgformEnhanceReturnJavaInter {
	/**
	 * @param tableName 数据库表名
	 * @param map 表单数据
	 */

	public JSONObject execute(String tableName,Map map) throws BusinessException;
}
