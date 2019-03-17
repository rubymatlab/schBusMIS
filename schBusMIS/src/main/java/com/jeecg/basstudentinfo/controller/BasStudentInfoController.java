package com.jeecg.basstudentinfo.controller;
import com.jeecg.basclass.entity.BasClassEntity;
import com.jeecg.basline.entity.BasLineEntity;
import com.jeecg.basstudentinfo.entity.BasStudentInfoEntity;
import com.jeecg.basstudentinfo.service.BasStudentInfoServiceI;
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
import org.jeecgframework.core.common.model.common.TreeChildCount;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
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
import net.sf.json.JSONObject;

/**   
 * @Title: Controller  
 * @Description: 学生资料
 * @author onlineGenerator
 * @date 2019-03-17 21:32:12
 * @version V1.0   
 *
 */
@Api(value="BasStudentInfo",description="学生资料",tags="basStudentInfoController")
@Controller
@RequestMapping("/basStudentInfoController")
public class BasStudentInfoController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BasStudentInfoController.class);

	@Autowired
	private BasStudentInfoServiceI basStudentInfoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 学生资料列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/jeecg/basstudentinfo/basStudentInfoList");
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
	public void datagrid(BasStudentInfoEntity basStudentInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BasStudentInfoEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, basStudentInfo, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.basStudentInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除学生资料
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(BasStudentInfoEntity basStudentInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		basStudentInfo = systemService.getEntity(BasStudentInfoEntity.class, basStudentInfo.getId());
		message = "学生资料删除成功";
		try{
			basStudentInfoService.delete(basStudentInfo);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "学生资料删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除学生资料
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "学生资料删除成功";
		try{
			for(String id:ids.split(",")){
				BasStudentInfoEntity basStudentInfo = systemService.getEntity(BasStudentInfoEntity.class, 
				id
				);
				basStudentInfoService.delete(basStudentInfo);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "学生资料删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加学生资料
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(BasStudentInfoEntity basStudentInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "学生资料添加成功";
		try{
			basStudentInfoService.save(basStudentInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "学生资料添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新学生资料
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(BasStudentInfoEntity basStudentInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "学生资料更新成功";
		BasStudentInfoEntity t = basStudentInfoService.get(BasStudentInfoEntity.class, basStudentInfo.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(basStudentInfo, t);
			basStudentInfoService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "学生资料更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
 	/**
	 * 自定义按钮-[绑定设备]业务
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAddDevice")
	@ResponseBody
	public AjaxJson doAddDevice(BasStudentInfoEntity basStudentInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "绑定设备成功";
		BasStudentInfoEntity t = basStudentInfoService.get(BasStudentInfoEntity.class, basStudentInfo.getId());
		try{
			JSONObject json=basStudentInfoService.doAddDeviceBus(t);
			message=json.getString("msg");
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "绑定设备失败";
		}
		j.setMsg(message);
		return j;
	}
 	/**
	 * 自定义按钮-[解绑设备]业务
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doDeleteDevice")
	@ResponseBody
	public AjaxJson doDeleteDevice(BasStudentInfoEntity basStudentInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "解绑设备成功";
		BasStudentInfoEntity t = basStudentInfoService.get(BasStudentInfoEntity.class, basStudentInfo.getId());
		try{
			JSONObject json=basStudentInfoService.doDeleteDeviceBus(t);
			message=json.getString("msg");
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "解绑设备失败";
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 学生资料新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(BasStudentInfoEntity basStudentInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(basStudentInfo.getId())) {
			basStudentInfo = basStudentInfoService.getEntity(BasStudentInfoEntity.class, basStudentInfo.getId());
			req.setAttribute("basStudentInfoPage", basStudentInfo);
		}
		return new ModelAndView("com/jeecg/basstudentinfo/basStudentInfo-add");
	}
	/**
	 * 学生资料编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(BasStudentInfoEntity basStudentInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(basStudentInfo.getId())) {
			basStudentInfo = basStudentInfoService.getEntity(BasStudentInfoEntity.class, basStudentInfo.getId());
			req.setAttribute("basStudentInfoPage", basStudentInfo);
		}
		return new ModelAndView("com/jeecg/basstudentinfo/basStudentInfo-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","basStudentInfoController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(BasStudentInfoEntity basStudentInfo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(BasStudentInfoEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, basStudentInfo, request.getParameterMap());
		List<BasStudentInfoEntity> basStudentInfos = this.basStudentInfoService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"学生资料");
		modelMap.put(NormalExcelConstants.CLASS,BasStudentInfoEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("学生资料列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,basStudentInfos);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(BasStudentInfoEntity basStudentInfo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"学生资料");
    	modelMap.put(NormalExcelConstants.CLASS,BasStudentInfoEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("学生资料列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<BasStudentInfoEntity> listBasStudentInfoEntitys = ExcelImportUtil.importExcel(file.getInputStream(),BasStudentInfoEntity.class,params);
				boolean flag = true;
				boolean flagup=true;
				boolean flagdown=true;
				String errorMessage="";
				for (BasStudentInfoEntity basStudentInfo : listBasStudentInfoEntitys) {
					//判断年级、班级
					if(basStudentInfo.getBcGrade()!=null && basStudentInfo.getBcName()!=null)
					{
						String sql="SELECT bc.id id FROM bas_class bc where bc.bc_grade='{bc_grade}' and bc.bc_name='{bc_name}'";
						sql=sql.replace("{bc_grade}", basStudentInfo.getBcGrade()).replace("{bc_name}", basStudentInfo.getBcName());
						List<Map<String, Object>> o=systemService.findForJdbc(sql);
						if (o.isEmpty()) {
							flag = false;
							errorMessage += basStudentInfo.getBcGrade() + "," + basStudentInfo.getBcName() + ";";
						} else {
							basStudentInfo.setBcId(o.get(0).get("id").toString());
						}
					}
					//判断上学站点
					if(basStudentInfo.getBlName()!=null && basStudentInfo.getBlSize()!=null)
					{
						String sql="select bs.id id from bas_size bs,bas_line bl where bs.fk_bl_id=bl.id and bl.line_status='1' and bl.bl_name='{bl_name}' and bs.bs_name='{bs_name}'";
						sql=sql.replace("{bl_name}", basStudentInfo.getBlName()).replace("{bs_name}", basStudentInfo.getBlSize());
						List<Map<String, Object>> o=systemService.findForJdbc(sql);
						if(o.isEmpty())
						{
							flagup=false;
							errorMessage+=basStudentInfo.getBlName()+","+basStudentInfo.getBlSize()+";";
						} else {
							basStudentInfo.setBlSizeid(o.get(0).get("id").toString());
						}
					}
					//判断 放学站点
					if(basStudentInfo.getBlName1()!=null && basStudentInfo.getBlSize1()!=null)
					{
						String sql="select bs.id id from bas_size bs,bas_line bl where bs.fk_bl_id=bl.id and bl.line_status='2' and bl.bl_name='{bl_name}' and bs.bs_name='{bs_name}'";
						sql=sql.replace("{bl_name}", basStudentInfo.getBlName1()).replace("{bs_name}", basStudentInfo.getBlSize1());
						List<Map<String, Object>> o=systemService.findForJdbc(sql);
						if(o.isEmpty())
						{
							flagdown=false;
							errorMessage+=basStudentInfo.getBlName()+","+basStudentInfo.getBlSize()+";";
						}else{
							basStudentInfo.setBlSizeid1(o.get(0).get("id").toString());
						}
					}
				}
				
				if (flag && flagup && flagdown) {
					for (BasStudentInfoEntity basStudentInfo : listBasStudentInfoEntitys) {
						basStudentInfoService.save(basStudentInfo);
					}
					j.setMsg("文件导入成功！");
				}else
				{
					j.setMsg(errorMessage+"不存在！");
				}
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
	@ApiOperation(value="学生资料列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<BasStudentInfoEntity>> list() {
		List<BasStudentInfoEntity> listBasStudentInfos=basStudentInfoService.getList(BasStudentInfoEntity.class);
		return Result.success(listBasStudentInfos);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取学生资料信息",notes="根据ID获取学生资料信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		BasStudentInfoEntity task = basStudentInfoService.get(BasStudentInfoEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取学生资料信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建学生资料")
	public ResponseMessage<?> create(@ApiParam(name="学生资料对象")@RequestBody BasStudentInfoEntity basStudentInfo, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<BasStudentInfoEntity>> failures = validator.validate(basStudentInfo);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			basStudentInfoService.save(basStudentInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("学生资料信息保存失败");
		}
		return Result.success(basStudentInfo);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新学生资料",notes="更新学生资料")
	public ResponseMessage<?> update(@ApiParam(name="学生资料对象")@RequestBody BasStudentInfoEntity basStudentInfo) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<BasStudentInfoEntity>> failures = validator.validate(basStudentInfo);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			basStudentInfoService.saveOrUpdate(basStudentInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新学生资料信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新学生资料信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除学生资料")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" , id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			basStudentInfoService.deleteEntityById(BasStudentInfoEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("学生资料删除失败");
		}

		return Result.success();
	}
}
