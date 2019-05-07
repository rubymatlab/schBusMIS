package com.jeecg.bascontrail.controller;
import com.jeecg.bascontrail.entity.BusOpenidEntity;
import com.jeecg.bascontrail.service.BusOpenidServiceI;
import com.jeecg.basline.entity.BasSizeEntity;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import java.util.HashMap;
import org.jeecgframework.core.util.ExceptionUtil;

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
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.jwt.util.GsonUtil;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**   
 * @Title: Controller  
 * @Description: 微信绑定管理
 * @author onlineGenerator
 * @date 2019-05-06 18:36:10
 * @version V1.0   
 *
 */
@Api(value="BusRemind",description="微信绑定管理",tags="busRemindController")
@Controller
@RequestMapping("/busRemindController")
public class BusRemindController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BusRemindController.class);

	@Autowired
	private BusOpenidServiceI busRemindService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 微信绑定管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		CriteriaQuery cq = new CriteriaQuery(BasSizeEntity.class);
		cq.isNotNull("sizeBus");
		cq.add();
		List<BasSizeEntity> listBse = systemService.getListByCriteriaQuery(cq, false);
		for (int i = listBse.size() - 1; i >= 0; i--)
			if (StringUtil.isEmpty(listBse.get(i).getSizeBus()))
				listBse.remove(i);
		
		for(BasSizeEntity bse:listBse)
		{
			String sql="SELECT count(id) count_number FROM bus_nextstationbuttoninfo where create_date>curdate() and size_id='{size_id}' ";
			sql=sql.replace("{size_id}", bse.getId());
			Map<String, Object> obj=systemService.findOneForJdbc(sql);
			int count_number=Integer.valueOf(obj.get("count_number").toString());
			if(count_number>0)
				bse.setSizeStatus("1");
			else
				bse.setSizeStatus("0");
		}
		
		request.setAttribute("listBusRemindList", listBse);
		return new ModelAndView("com/jeecg/bascontrail/busRemindList");
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
	public void datagrid(BusOpenidEntity busRemind,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BusOpenidEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, busRemind, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.busRemindService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除微信绑定管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(BusOpenidEntity busRemind, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		busRemind = systemService.getEntity(BusOpenidEntity.class, busRemind.getId());
		message = "微信绑定管理删除成功";
		try{
			busRemindService.delete(busRemind);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "微信绑定管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除微信绑定管理
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "微信绑定管理删除成功";
		try{
			for(String id:ids.split(",")){
				BusOpenidEntity busRemind = systemService.getEntity(BusOpenidEntity.class, 
				id
				);
				busRemindService.delete(busRemind);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "微信绑定管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加微信绑定管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(BusOpenidEntity busRemind, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "微信绑定管理添加成功";
		try{
			busRemindService.save(busRemind);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "微信绑定管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新微信绑定管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(BusOpenidEntity busRemind, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "微信绑定管理更新成功";
		BusOpenidEntity t = busRemindService.get(BusOpenidEntity.class, busRemind.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(busRemind, t);
			busRemindService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "微信绑定管理更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 微信绑定管理新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(BusOpenidEntity busRemind, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(busRemind.getId())) {
			busRemind = busRemindService.getEntity(BusOpenidEntity.class, busRemind.getId());
			req.setAttribute("busRemindPage", busRemind);
		}
		return new ModelAndView("com/jeecg/bascontrail/busRemind-add");
	}
	/**
	 * 微信绑定管理编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(BusOpenidEntity busRemind, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(busRemind.getId())) {
			busRemind = busRemindService.getEntity(BusOpenidEntity.class, busRemind.getId());
			req.setAttribute("busRemindPage", busRemind);
		}
		return new ModelAndView("com/jeecg/bascontrail/busRemind-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","busRemindController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(BusOpenidEntity busRemind,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(BusOpenidEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, busRemind, request.getParameterMap());
		List<BusOpenidEntity> busReminds = this.busRemindService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"微信绑定管理");
		modelMap.put(NormalExcelConstants.CLASS,BusOpenidEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("微信绑定管理列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,busReminds);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(BusOpenidEntity busRemind,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"微信绑定管理");
    	modelMap.put(NormalExcelConstants.CLASS,BusOpenidEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("微信绑定管理列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
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
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<BusOpenidEntity> listBusOpenidEntitys = ExcelImportUtil.importExcel(file.getInputStream(),BusOpenidEntity.class,params);
				for (BusOpenidEntity busRemind : listBusOpenidEntitys) {
					busRemindService.save(busRemind);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(e.getMessage());
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
	
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="微信绑定管理列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<BusOpenidEntity>> list() {
		List<BusOpenidEntity> listBusReminds=busRemindService.getList(BusOpenidEntity.class);
		return Result.success(listBusReminds);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取微信绑定管理信息",notes="根据ID获取微信绑定管理信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		BusOpenidEntity task = busRemindService.get(BusOpenidEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取微信绑定管理信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建微信绑定管理")
	public ResponseMessage<?> create(@ApiParam(name="微信绑定管理对象")@RequestBody BusOpenidEntity busRemind, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<BusOpenidEntity>> failures = validator.validate(busRemind);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			busRemindService.save(busRemind);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("微信绑定管理信息保存失败");
		}
		return Result.success(busRemind);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新微信绑定管理",notes="更新微信绑定管理")
	public ResponseMessage<?> update(@ApiParam(name="微信绑定管理对象")@RequestBody BusOpenidEntity busRemind) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<BusOpenidEntity>> failures = validator.validate(busRemind);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			busRemindService.saveOrUpdate(busRemind);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新微信绑定管理信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新微信绑定管理信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除微信绑定管理")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" , id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			busRemindService.deleteEntityById(BusOpenidEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("微信绑定管理删除失败");
		}

		return Result.success();
	}
}
