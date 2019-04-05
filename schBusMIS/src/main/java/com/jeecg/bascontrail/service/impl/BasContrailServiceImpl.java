package com.jeecg.bascontrail.service.impl;

import com.jeecg.bascontrail.service.BasContrailServiceI;
import com.jeecg.basstudentinfo.entity.BasStudentInfoEntity;

import net.sf.json.JSONObject;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.jeecg.bascontrail.entity.BasContrailEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceReturnJavaInter;
import org.jeecgframework.minidao.util.FreemarkerParseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.jeecgframework.core.util.ResourceUtil;

@Service("basContrailService")
@Transactional
public class BasContrailServiceImpl extends CommonServiceImpl implements BasContrailServiceI {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public void delete(BasContrailEntity entity) throws Exception {
		super.delete(entity);
		// 执行删除操作增强业务
		this.doDelBus(entity);
	}

	public Serializable save(BasContrailEntity entity) throws Exception {
		Serializable t = super.save(entity);
		// 执行新增操作增强业务
		this.doAddBus(entity);
		return t;
	}

	public void saveOrUpdate(BasContrailEntity entity) throws Exception {
		super.saveOrUpdate(entity);
		// 执行更新操作增强业务
		this.doUpdateBus(entity);
	}

	/**
	 * 自定义按钮-[绑定设备]业务处理
	 * 
	 * @param id
	 * @return
	 */
	public void doAddDeviceBus(BasContrailEntity t) throws Exception {
		// -----------------sql增强 start----------------------------
		// -----------------sql增强 end------------------------------

		// -----------------java增强 start---------------------------
		Map<String, Object> data = populationMap(t);
		executeJavaExtend("spring", "AddDevice", data);
		// -----------------java增强 end-----------------------------
	}

	/**
	 * 自定义按钮-[解绑设备]业务处理
	 * 
	 * @param id
	 * @return
	 */
	public void doDeleteDeviceBus(BasContrailEntity t) throws Exception {
		// -----------------sql增强 start----------------------------
		// -----------------sql增强 end------------------------------

		// -----------------java增强 start---------------------------
		Map<String, Object> data = populationMap(t);
		executeJavaExtend("spring", "DeleteDevice", data);
		// -----------------java增强 end-----------------------------
	}

	/**
	 * 新增操作增强业务
	 * 
	 * @param t
	 * @return
	 */
	private void doAddBus(BasContrailEntity t) throws Exception {
		// -----------------sql增强 start----------------------------
		// -----------------sql增强 end------------------------------

		// -----------------java增强 start---------------------------
		Map<String, Object> data = populationMap(t);
		executeJavaExtend("spring", "AddDevice", data);
		// -----------------java增强 end-----------------------------
	}

	/**
	 * 更新操作增强业务
	 * 
	 * @param t
	 * @return
	 */
	private void doUpdateBus(BasContrailEntity t) throws Exception {
		// -----------------sql增强 start----------------------------
		// -----------------sql增强 end------------------------------

		// -----------------java增强 start---------------------------
		Map<String, Object> data = populationMap(t);
		executeJavaExtend("spring", "AddDevice", data);
		// -----------------java增强 end-----------------------------
	}

	/**
	 * 删除操作增强业务
	 * 
	 * @param id
	 * @return
	 */
	private void doDelBus(BasContrailEntity t) throws Exception {
		// -----------------sql增强 start----------------------------
		// -----------------sql增强 end------------------------------

		// -----------------java增强 start---------------------------
		// -----------------java增强 end-----------------------------
	}

	private Map<String, Object> populationMap(BasContrailEntity t) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", t.getId());
		map.put("bs_name", t.getBsName());
		map.put("bs_sex", t.getBsSex());
		map.put("bc_parent", t.getBcParent());
		map.put("bs_tel", t.getBsTel());
		map.put("bc_id", t.getBcId());
		map.put("bc_grade", t.getBcGrade());
		map.put("bc_name", t.getBcName());
		map.put("bs_cardno", t.getBsCardno());
		map.put("bs_imei", t.getBsImei());
		map.put("bs_deviceid", t.getBsDeviceid());
		map.put("bl_sizeid", t.getBlSizeid());
		map.put("bl_name", t.getBlName());
		map.put("bl_size", t.getBlSize());
		map.put("bl_sizeid1", t.getBlSizeid1());
		map.put("bl_name1", t.getBlName1());
		map.put("bl_size1", t.getBlSize1());
		map.put("bs_address", t.getBsAddress());
		map.put("bs_desc", t.getBsDesc());
		map.put("bs_status", t.getBsStatus());
		map.put("create_name", t.getCreateName());
		map.put("create_by", t.getCreateBy());
		map.put("create_date", t.getCreateDate());
		map.put("update_name", t.getUpdateName());
		map.put("update_by", t.getUpdateBy());
		map.put("update_date", t.getUpdateDate());
		return map;
	}

	/**
	 * 替换sql中的变量
	 * 
	 * @param sql
	 * @param t
	 * @return
	 */
	public String replaceVal(String sql, BasContrailEntity t) {
		sql = sql.replace("#{id}", String.valueOf(t.getId()));
		sql = sql.replace("#{bs_name}", String.valueOf(t.getBsName()));
		sql = sql.replace("#{bs_sex}", String.valueOf(t.getBsSex()));
		sql = sql.replace("#{bc_parent}", String.valueOf(t.getBcParent()));
		sql = sql.replace("#{bs_tel}", String.valueOf(t.getBsTel()));
		sql = sql.replace("#{bc_id}", String.valueOf(t.getBcId()));
		sql = sql.replace("#{bc_grade}", String.valueOf(t.getBcGrade()));
		sql = sql.replace("#{bc_name}", String.valueOf(t.getBcName()));
		sql = sql.replace("#{bs_cardno}", String.valueOf(t.getBsCardno()));
		sql = sql.replace("#{bs_imei}", String.valueOf(t.getBsImei()));
		sql = sql.replace("#{bs_deviceid}", String.valueOf(t.getBsDeviceid()));
		sql = sql.replace("#{bl_sizeid}", String.valueOf(t.getBlSizeid()));
		sql = sql.replace("#{bl_name}", String.valueOf(t.getBlName()));
		sql = sql.replace("#{bl_size}", String.valueOf(t.getBlSize()));
		sql = sql.replace("#{bl_sizeid1}", String.valueOf(t.getBlSizeid1()));
		sql = sql.replace("#{bl_name1}", String.valueOf(t.getBlName1()));
		sql = sql.replace("#{bl_size1}", String.valueOf(t.getBlSize1()));
		sql = sql.replace("#{bs_address}", String.valueOf(t.getBsAddress()));
		sql = sql.replace("#{bs_desc}", String.valueOf(t.getBsDesc()));
		sql = sql.replace("#{bs_status}", String.valueOf(t.getBsStatus()));
		sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
		sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
		sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
		sql = sql.replace("#{update_name}", String.valueOf(t.getUpdateName()));
		sql = sql.replace("#{update_by}", String.valueOf(t.getUpdateBy()));
		sql = sql.replace("#{update_date}", String.valueOf(t.getUpdateDate()));
		sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
		return sql;
	}

	/**
	 * 执行JAVA增强
	 */
	/*private void executeJavaExtend(String cgJavaType, String cgJavaValue, Map<String, Object> data) throws Exception {
		if (StringUtil.isNotEmpty(cgJavaValue)) {
			Object obj = null;
			try {
				if ("class".equals(cgJavaType)) {
					// 因新增时已经校验了实例化是否可以成功，所以这块就不需要再做一次判断
					obj = MyClassLoader.getClassByScn(cgJavaValue).newInstance();
				} else if ("spring".equals(cgJavaType)) {
					obj = ApplicationContextUtil.getContext().getBean(cgJavaValue);
				}
				if (obj instanceof CgformEnhanceJavaInter) {
					CgformEnhanceJavaInter javaInter = (CgformEnhanceJavaInter) obj;
					javaInter.execute("bas_student", data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			}
		}
	}*/
	
	/**
	 * 执行JAVA增强
	 */
 	private JSONObject executeJavaExtend(String cgJavaType,String cgJavaValue,Map<String,Object> data) throws Exception {
 		JSONObject json=null;
 		if(StringUtil.isNotEmpty(cgJavaValue)){
			Object obj = null;
			try {
				if("class".equals(cgJavaType)){
					//因新增时已经校验了实例化是否可以成功，所以这块就不需要再做一次判断
					obj = MyClassLoader.getClassByScn(cgJavaValue).newInstance();
				}else if("spring".equals(cgJavaType)){
					obj = ApplicationContextUtil.getContext().getBean(cgJavaValue);
				}
				if(obj instanceof CgformEnhanceReturnJavaInter){
					CgformEnhanceReturnJavaInter javaInter = (CgformEnhanceReturnJavaInter) obj;
					json=javaInter.execute("bas_student",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 		return json;
 	}

	private void executeSqlEnhance(String sqlEnhance, BasContrailEntity t) {
		Map<String, Object> data = populationMap(t);
		sqlEnhance = ResourceUtil.formateSQl(sqlEnhance, data);
		boolean isMiniDao = false;
		try {
			data = ResourceUtil.minidaoReplaceExtendSqlSysVar(data);
			sqlEnhance = FreemarkerParseFactory.parseTemplateContent(sqlEnhance, data);
			isMiniDao = true;
		} catch (Exception e) {
		}
		String[] sqls = sqlEnhance.split(";");
		for (String sql : sqls) {
			if (sql == null || sql.toLowerCase().trim().equals("")) {
				continue;
			}
			int num = 0;
			if (isMiniDao) {
				num = namedParameterJdbcTemplate.update(sql, data);
			} else {
				num = this.executeSql(sql);
			}
		}
	}

	/**
	 * 查询轨迹操作增强业务
	 * 
	 * @param t
	 * @return
	 */
	@Override
	public JSONObject doContrailDeviceBus(BasContrailEntity t) throws Exception {
		Map<String, Object> data = populationMap(t);
		JSONObject json = executeJavaExtend("spring", "ContrailDevice", data);
		return json;
	}
}