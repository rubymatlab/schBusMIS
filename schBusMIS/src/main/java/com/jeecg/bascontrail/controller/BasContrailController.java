package com.jeecg.bascontrail.controller;

import com.jeecg.bascontrail.entity.BasContrailEntity;
import com.jeecg.bascontrail.service.BasContrailServiceI;
import com.jeecg.basstudent.entity.ConvertionUtils;
import com.jeecg.basstudentinfo.entity.BasStudentInfoEntity;

import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
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
 * @date 2019-04-04 15:09:01
 * @version V1.0
 *
 */
@Api(value = "BasContrail", description = "学生资料", tags = "basContrailController")
@Controller
@RequestMapping("/basContrailController")
public class BasContrailController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BasContrailController.class);

	@Autowired
	private BasContrailServiceI basContrailService;
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
		return new ModelAndView("com/jeecg/bascontrail/basContrailList");
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
	public void datagrid(BasContrailEntity basContrail, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BasContrailEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, basContrail, request.getParameterMap());
		try {
			// 自定义追加查询条件
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.basContrailService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除学生资料
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(BasContrailEntity basContrail, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		basContrail = systemService.getEntity(BasContrailEntity.class, basContrail.getId());
		message = "学生资料删除成功";
		try {
			basContrailService.delete(basContrail);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
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
	public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "学生资料删除成功";
		try {
			for (String id : ids.split(",")) {
				BasContrailEntity basContrail = systemService.getEntity(BasContrailEntity.class, id);
				basContrailService.delete(basContrail);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
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
	@RequestMapping(params = "doContrail")
	@ResponseBody
	public AjaxJson doContrail(BasContrailEntity basContrail, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "定位成功";
		List<BasContrailEntity> listt = systemService.findByProperty(BasContrailEntity.class, "bsName",
				basContrail.getBsName());
		if(listt.size()==0)
			listt = systemService.findByProperty(BasContrailEntity.class, "bsCardno",
					basContrail.getBsCardno());
		JSONArray arrayobject = new JSONArray();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (BasContrailEntity t : listt) {
				t.setCreateDate(basContrail.getCreateDate());
				t.setUpdateDate(basContrail.getUpdateDate());
				JSONObject json = basContrailService.doContrailDeviceBus(t);
				net.sf.json.JSONArray arr = json.getJSONArray("msg");
				for (int i = 0; i < arr.size(); i++) {
					JSONObject job = arr.getJSONObject(i); // 遍历 jsonarray
					// 转换为火星坐标系
					double[] clearLocation = ConvertionUtils.getClear(job.getString("gps_latitude"),
							job.getString("gps_longitude"));
					job.put("gps_latitude", clearLocation[0]);
					job.put("gps_longitude", clearLocation[1]);
					try {
						String time_and_power = sdf.format(df.parse(job.getString("device_time")));
						job.put("device_time", t.getBsName() + " " + time_and_power);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					arrayobject.add(job);
				}
				//System.out.println(basContrail.getBsName());
				//System.out.println(basContrail.getBsCardno());
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			throw new BusinessException(e.getMessage());
		}
		j.setObj(arrayobject);
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
	public AjaxJson doAdd(BasContrailEntity basContrail, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "学生资料添加成功";
		try {
			basContrailService.save(basContrail);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
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
	public AjaxJson doUpdate(BasContrailEntity basContrail, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "学生资料更新成功";
		BasContrailEntity t = basContrailService.get(BasContrailEntity.class, basContrail.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(basContrail, t);
			basContrailService.saveOrUpdate(t);
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
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAddDevice")
	@ResponseBody
	public AjaxJson doAddDevice(BasContrailEntity basContrail, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "绑定设备成功";
		BasContrailEntity t = basContrailService.get(BasContrailEntity.class, basContrail.getId());
		try {
			basContrailService.doAddDeviceBus(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "绑定设备失败";
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 自定义按钮-[解绑设备]业务
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doDeleteDevice")
	@ResponseBody
	public AjaxJson doDeleteDevice(BasContrailEntity basContrail, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "解绑设备成功";
		BasContrailEntity t = basContrailService.get(BasContrailEntity.class, basContrail.getId());
		try {
			basContrailService.doDeleteDeviceBus(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
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
	public ModelAndView goAdd(BasContrailEntity basContrail, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(basContrail.getId())) {
			basContrail = basContrailService.getEntity(BasContrailEntity.class, basContrail.getId());
			req.setAttribute("basContrailPage", basContrail);
		}
		return new ModelAndView("com/jeecg/bascontrail/basContrail-add");
	}

	/**
	 * 学生资料编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(BasContrailEntity basContrail, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(basContrail.getId())) {
			basContrail = basContrailService.getEntity(BasContrailEntity.class, basContrail.getId());
			req.setAttribute("basContrailPage", basContrail);
		}
		return new ModelAndView("com/jeecg/bascontrail/basContrail-update");
	}

	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "basContrailController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(BasContrailEntity basContrail, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(BasContrailEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, basContrail, request.getParameterMap());
		List<BasContrailEntity> basContrails = this.basContrailService.getListByCriteriaQuery(cq, false);
		modelMap.put(NormalExcelConstants.FILE_NAME, "学生资料");
		modelMap.put(NormalExcelConstants.CLASS, BasContrailEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,
				new ExportParams("学生资料列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, basContrails);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(BasContrailEntity basContrail, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid, ModelMap modelMap) {
		modelMap.put(NormalExcelConstants.FILE_NAME, "学生资料");
		modelMap.put(NormalExcelConstants.CLASS, BasContrailEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,
				new ExportParams("学生资料列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, new ArrayList());
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
				List<BasContrailEntity> listBasContrailEntitys = ExcelImportUtil.importExcel(file.getInputStream(),
						BasContrailEntity.class, params);
				for (BasContrailEntity basContrail : listBasContrailEntitys) {
					basContrailService.save(basContrail);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(e.getMessage());
			} finally {
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
	@ApiOperation(value = "学生资料列表信息", produces = "application/json", httpMethod = "GET")
	public ResponseMessage<List<BasContrailEntity>> list() {
		List<BasContrailEntity> listBasContrails = basContrailService.getList(BasContrailEntity.class);
		return Result.success(listBasContrails);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据ID获取学生资料信息", notes = "根据ID获取学生资料信息", httpMethod = "GET", produces = "application/json")
	public ResponseMessage<?> get(@ApiParam(required = true, name = "id", value = "ID") @PathVariable("id") String id) {
		BasContrailEntity task = basContrailService.get(BasContrailEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取学生资料信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value = "创建学生资料")
	public ResponseMessage<?> create(@ApiParam(name = "学生资料对象") @RequestBody BasContrailEntity basContrail,
			UriComponentsBuilder uriBuilder) {
		// 调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<BasContrailEntity>> failures = validator.validate(basContrail);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		// 保存
		try {
			basContrailService.save(basContrail);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("学生资料信息保存失败");
		}
		return Result.success(basContrail);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value = "更新学生资料", notes = "更新学生资料")
	public ResponseMessage<?> update(@ApiParam(name = "学生资料对象") @RequestBody BasContrailEntity basContrail) {
		// 调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<BasContrailEntity>> failures = validator.validate(basContrail);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		// 保存
		try {
			basContrailService.saveOrUpdate(basContrail);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新学生资料信息失败");
		}

		// 按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新学生资料信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "删除学生资料")
	public ResponseMessage<?> delete(
			@ApiParam(name = "id", value = "ID", required = true) @PathVariable("id") String id) {
		logger.info("delete[{}]", id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			basContrailService.deleteEntityById(BasContrailEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("学生资料删除失败");
		}

		return Result.success();
	}
}
