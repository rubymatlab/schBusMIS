package com.jeecg.basline.controller;
import com.jeecg.basline.entity.BasLineEntity;
import com.jeecg.basline.service.BasLineServiceI;
import com.jeecg.basstudent.entity.BasStudentLocationEntity;
import com.jeecg.basstudentinfo.entity.BasStudentInfoEntity;
import com.jeecg.demo.entity.JfromOrderLineEntity;
import com.jeecg.basline.page.BasLinePage;
import com.jeecg.basline.entity.BasSizeEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.jwt.util.GsonUtil;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import com.alibaba.fastjson.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**   
 * @Title: Controller
 * @Description: 线路资料
 * @author onlineGenerator
 * @date 2019-03-17 02:06:38
 * @version V1.0   
 *
 */
@Api(value="BasLine",description="线路资料",tags="basLineController")
@Controller
@RequestMapping("/basLineController")
public class BasLineController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BasLineController.class);

	@Autowired
	private BasLineServiceI basLineService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

	/**
	 * 线路资料列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/jeecg/basline/basLineList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(BasLineEntity basLine,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BasLineEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, basLine, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.basLineService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "bassizeDatagrid")
	public void bassizeDatagrid(BasSizeEntity basSizeEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BasSizeEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, basSizeEntity);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("bsSeq", "desc");
		cq.setOrder(map);
		cq.add();
		this.basLineService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除线路资料
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(BasLineEntity basLine, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		basLine = systemService.getEntity(BasLineEntity.class, basLine.getId());
		String message = "线路资料删除成功";
		try{
			basLineService.delMain(basLine);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "线路资料删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除线路资料
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "线路资料删除成功";
		try{
			for(String id:ids.split(",")){
				BasLineEntity basLine = systemService.getEntity(BasLineEntity.class,
				id
				);
				basLineService.delMain(basLine);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "线路资料删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加线路资料
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(BasLineEntity basLine,BasLinePage basLinePage, HttpServletRequest request) {
		List<BasSizeEntity> basSizeList =  basLinePage.getBasSizeList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			basLineService.addMain(basLine, basSizeList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "线路资料添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新线路资料
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(BasLineEntity basLine,BasLinePage basLinePage, HttpServletRequest request) {
		List<BasSizeEntity> basSizeList =  basLinePage.getBasSizeList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			basLineService.updateMain(basLine, basSizeList);
			for(BasSizeEntity bse:basSizeList)
			{
				//更新
				List<BasStudentInfoEntity> listBasStudentInfoEntity=basLineService.findByProperty(BasStudentInfoEntity.class, "blSizeid", bse.getId());
				for(BasStudentInfoEntity bie:listBasStudentInfoEntity )
				{
					bie.setBlSize(bse.getBsName());
					bie.setBlName(basLine.getBlName());
					basLineService.saveOrUpdate(bie);
				}
				
				List<BasStudentInfoEntity> listBasStudentInfoEntity1=basLineService.findByProperty(BasStudentInfoEntity.class, "blSizeid1", bse.getId());
				for(BasStudentInfoEntity bie:listBasStudentInfoEntity1 )
				{
					bie.setBlSize1(bse.getBsName());
					bie.setBlName1(basLine.getBlName());
					basLineService.saveOrUpdate(bie);
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新线路资料失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 线路资料新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(BasLineEntity basLine, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(basLine.getId())) {
			basLine = basLineService.getEntity(BasLineEntity.class, basLine.getId());
			req.setAttribute("basLinePage", basLine);
		}
		return new ModelAndView("com/jeecg/basline/basLine-add");
	}
	
	/**
	 * 线路资料编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(BasLineEntity basLine, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(basLine.getId())) {
			basLine = basLineService.getEntity(BasLineEntity.class, basLine.getId());
			req.setAttribute("basLinePage", basLine);
		}
		return new ModelAndView("com/jeecg/basline/basLine-update");
	}
	
	
	/**
	 * 加载明细列表[站点明细]
	 * 
	 * @return
	 */
	@RequestMapping(params = "basSizeList")
	public ModelAndView basSizeList(BasLineEntity basLine, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object id0 = basLine.getId();
		//===================================================================================
		//查询-站点明细
	    String hql0 = "from BasSizeEntity where 1 = 1 AND fkBlId = ? order by bsSeq asc ";
	    try{
	    	List<BasSizeEntity> basSizeEntityList = systemService.findHql(hql0,id0);
			req.setAttribute("basSizeList", basSizeEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/jeecg/basline/basSizeList");
	}

    /**
    * 导出excel
    *
    * @param request
    * @param response
    */
    @RequestMapping(params = "exportXls")
    public String exportXls(BasLineEntity basLine,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,ModelMap map) {
    	CriteriaQuery cq = new CriteriaQuery(BasLineEntity.class, dataGrid);
    	//查询条件组装器
    	org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, basLine);
    	try{
    	//自定义追加查询条件
    	}catch (Exception e) {
    		throw new BusinessException(e.getMessage());
    	}
    	cq.add();
    	List<BasLineEntity> list=this.basLineService.getListByCriteriaQuery(cq, false);
    	List<BasLinePage> pageList=new ArrayList<BasLinePage>();
        if(list!=null&&list.size()>0){
        	for(BasLineEntity entity:list){
        		try{
        		BasLinePage page=new BasLinePage();
        		   MyBeanUtils.copyBeanNotNull2Bean(entity,page);
            	    Object id0 = entity.getId();
				    String hql0 = "from BasSizeEntity where 1 = 1 AND fkBlId = ? order by bsSeq asc ";
        	        List<BasSizeEntity> basSizeEntityList = systemService.findHql(hql0,id0);
            		page.setBasSizeList(basSizeEntityList);
            		pageList.add(page);
            	}catch(Exception e){
            		logger.info(e.getMessage());
            	}
            }
        }
        map.put(NormalExcelConstants.FILE_NAME,"线路资料");
        map.put(NormalExcelConstants.CLASS,BasLinePage.class);
        map.put(NormalExcelConstants.PARAMS,new ExportParams("线路资料列表", "导出人:Jeecg",
            "导出信息"));
        map.put(NormalExcelConstants.DATA_LIST,pageList);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

    /**
	 * 通过excel导入数据
	 * @param request
	 * @param
	 * @return
	 */
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(2);
			params.setNeedSave(true);
			try {
				List<BasLinePage> list =  ExcelImportUtil.importExcel(file.getInputStream(), BasLinePage.class, params);
				BasLineEntity entity1=null;
				for (BasLinePage page : list) {
					entity1=new BasLineEntity();
					MyBeanUtils.copyBeanNotNull2Bean(page,entity1);
		            basLineService.addMain(entity1, page.getBasSizeList());
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			}
			return j;
	}
	/**
	* 导出excel 使模板
	*/
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ModelMap map) {
		map.put(NormalExcelConstants.FILE_NAME,"线路资料");
		map.put(NormalExcelConstants.CLASS,BasLinePage.class);
		map.put(NormalExcelConstants.PARAMS,new ExportParams("线路资料列表", "导出人:"+ ResourceUtil.getSessionUser().getRealName(),
		"导出信息"));
		map.put(NormalExcelConstants.DATA_LIST,new ArrayList());
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	* 导入功能跳转
	*
	* @return
	*/
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "basLineController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

 	
 	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="线路资料列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<BasLinePage>> list() {
		List<BasLineEntity> list= basLineService.getList(BasLineEntity.class);
    	List<BasLinePage> pageList=new ArrayList<BasLinePage>();
        if(list!=null&&list.size()>0){
        	for(BasLineEntity entity:list){
        		try{
        			BasLinePage page=new BasLinePage();
        		   MyBeanUtils.copyBeanNotNull2Bean(entity,page);
					Object id0 = entity.getId();
				     String hql0 = "from BasSizeEntity where 1 = 1 AND fkBlId = ? order by bsSeq asc ";
	    			List<BasSizeEntity> basSizeOldList = this.basLineService.findHql(hql0,id0);
            		page.setBasSizeList(basSizeOldList);
            		pageList.add(page);
            	}catch(Exception e){
            		logger.info(e.getMessage());
            	}
            }
        }
		return Result.success(pageList);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取线路资料信息",notes="根据ID获取线路资料信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		BasLineEntity task = basLineService.get(BasLineEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取线路资料信息为空");
		}
		BasLinePage page = new BasLinePage();
		try {
			MyBeanUtils.copyBeanNotNull2Bean(task, page);
				Object id0 = task.getId();
		    String hql0 = "from BasSizeEntity where 1 = 1 AND fkBlId = ? order by bsSeq asc ";
			List<BasSizeEntity> basSizeOldList = this.basLineService.findHql(hql0,id0);
    		page.setBasSizeList(basSizeOldList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.success(page);
	}
 	
 	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建线路资料")
	public ResponseMessage<?> create(@ApiParam(name="线路资料对象")@RequestBody BasLinePage basLinePage, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<BasLinePage>> failures = validator.validate(basLinePage);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		List<BasSizeEntity> basSizeList =  basLinePage.getBasSizeList();
		
		BasLineEntity basLine = new BasLineEntity();
		try{
			MyBeanUtils.copyBeanNotNull2Bean(basLinePage,basLine);
		}catch(Exception e){
            logger.info(e.getMessage());
            return Result.error("保存线路资料失败");
        }
		try {
			basLineService.addMain(basLine, basSizeList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Result.success(basLine);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新线路资料",notes="更新线路资料")
	public ResponseMessage<?> update(@RequestBody BasLinePage basLinePage) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<BasLinePage>> failures = validator.validate(basLinePage);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		List<BasSizeEntity> basSizeList =  basLinePage.getBasSizeList();
		
		BasLineEntity basLine = new BasLineEntity();
		try{
			MyBeanUtils.copyBeanNotNull2Bean(basLinePage,basLine);
		}catch(Exception e){
            logger.info(e.getMessage());
            return Result.error("线路资料更新失败");
        }
		try {
			basLineService.updateMain(basLine, basSizeList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除线路资料")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" , id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			BasLineEntity basLine = basLineService.get(BasLineEntity.class, id);
			basLineService.delMain(basLine);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("线路资料删除失败");
		}

		return Result.success();
	}
}
