package com.jeecg.basline.service.impl;
import com.jeecg.basline.service.BasLineServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.jeecg.basline.entity.BasLineEntity;
import com.jeecg.basline.entity.BasSizeEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import java.util.ArrayList;
import java.util.UUID;
import java.io.Serializable;

import java.util.Map;
import java.util.HashMap;
import org.jeecgframework.minidao.util.FreemarkerParseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.jeecgframework.core.util.ResourceUtil;

@Service("basLineService")
@Transactional
public class BasLineServiceImpl extends CommonServiceImpl implements BasLineServiceI {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
 	public void delete(BasLineEntity entity) throws Exception{
 		super.delete(entity);
 	}
	public void addMain(BasLineEntity basLine,
	        List<BasSizeEntity> basSizeList) throws Exception{
			//保存主信息
			this.save(basLine);
		
			/**保存-站点明细*/
			for(BasSizeEntity basSize:basSizeList){
				//外键设置
				basSize.setFkBlId(basLine.getId());
				this.save(basSize);
			}
	}

	public void updateMain(BasLineEntity basLine,
	        List<BasSizeEntity> basSizeList) throws Exception {
		//保存主表信息
		if(StringUtil.isNotEmpty(basLine.getId())){
			try {
				BasLineEntity temp = findUniqueByProperty(BasLineEntity.class, "id", basLine.getId());
				MyBeanUtils.copyBeanNotNull2Bean(basLine, temp);
				this.saveOrUpdate(temp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			this.saveOrUpdate(basLine);
		}
		//===================================================================================
		//获取参数
		Object id0 = basLine.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-站点明细
	    String hql0 = "from BasSizeEntity where 1 = 1 AND fkBlId = ? ";
	    List<BasSizeEntity> basSizeOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-站点明细
		if(basSizeList!=null&&basSizeList.size()>0){
		for(BasSizeEntity oldE:basSizeOldList){
			boolean isUpdate = false;
				for(BasSizeEntity sendE:basSizeList){
					//需要更新的明细数据-站点明细
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							this.saveOrUpdate(oldE);
						} catch (Exception e) {
							e.printStackTrace();
							throw new BusinessException(e.getMessage());
						}
						isUpdate= true;
		    			break;
		    		}
		    	}
	    		if(!isUpdate){
		    		//如果数据库存在的明细，前台没有传递过来则是删除-站点明细
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-站点明细
			for(BasSizeEntity basSize:basSizeList){
				if(oConvertUtils.isEmpty(basSize.getId())){
					//外键设置
					basSize.setFkBlId(basLine.getId());
					this.save(basSize);
				}
			}
		}
	}

	public void delMain(BasLineEntity basLine) throws Exception{
		//删除主表信息
		this.delete(basLine);
		//===================================================================================
		//获取参数
		Object id0 = basLine.getId();
		//===================================================================================
		//删除-站点明细
	    String hql0 = "from BasSizeEntity where 1 = 1 AND fkBlId = ? ";
	    List<BasSizeEntity> basSizeOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(basSizeOldList);
		
	}
 	
}