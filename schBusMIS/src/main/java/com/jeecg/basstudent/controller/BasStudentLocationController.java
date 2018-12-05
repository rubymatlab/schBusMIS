package com.jeecg.basstudent.controller;

import com.jeecg.basstudent.entity.BasStudentLocationEntity;
import com.jeecg.basstudent.entity.wxutils;
import com.jeecg.basstudent.service.BasStudentLocationServiceI;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
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
import org.jeecgframework.web.system.util.JsapiTicketUtil;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.wxbase.wxtoken.JwTokenAPI;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import java.util.HashMap;
import org.jeecgframework.core.util.ExceptionUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import com.alibaba.fastjson.JSONArray;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.jeecg.basstudent.entity.wxutils;

/**
 * @Title: Controller
 * @Description: 学生资料
 * @author onlineGenerator
 * @date 2018-11-05 18:34:04
 * @version V1.0
 *
 */
@Api(value = "BasStudentLocation", description = "学生资料", tags = "basStudentLocationController")
@Controller
@RequestMapping("/basStudentLocationController")
public class BasStudentLocationController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BasStudentLocationController.class);

	@Autowired
	private BasStudentLocationServiceI basStudentLocationService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

	/**
	 * 学生位置资料列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(String code, String state, HttpServletRequest request) {
		// 获取地图认证
		String access_token = null;
		if (access_token == null)
			try {
				access_token = JwTokenAPI.getAccessToken(wxutils.appid, wxutils.appscret);
			} catch (WexinReqException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		String ticket = JsapiTicketUtil.getJSApiTicket(access_token);

		String signUrl = request.getRequestURL().toString() + "?list&code=" + code + "&state=" + state;
		Map<String, String> o = JsapiTicketUtil.sign(ticket, signUrl);

		System.out.println(request.getRequestURL().toString());
		request.setAttribute("appId", wxutils.appid);
		request.setAttribute("timestamp", o.get("timestamp"));
		request.setAttribute("nonceStr", o.get("nonceStr"));
		request.setAttribute("signature", o.get("signature"));
		String sql = "";
		if (code != null) {
			String sopenid = wxutils.OAuthGetOpenid(code);
			List<Map<String, Object>> listCount = new ArrayList<Map<String, Object>>();
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT count(*) as c FROM t_s_base_user  ");
			sb.append("WHERE openid='" + sopenid + "'");	
			listCount = this.systemService.findForJdbc(sb.toString());
			int flag = Integer.parseInt(listCount.get(0).get("c").toString());
			if (flag==0) {
				// openid是学生，默认最近5个打卡时间
				sql = "select * from(SELECT bs.bs_name,bs.bs_cardno,bl.bl_longitude,bl_latitude,DATE_FORMAT(bl.bl_commdatetime,'%Y-%c-%d %H:%i')bl_commdatetime FROM bas_student bs ,bus_openid bo,bus_locationinfo bl where bs.id=bo.bs_studentid and  bs.bs_cardno= bl.bl_cardno";
				sql += " and bo.bo_openid='" + sopenid + "'";
				sql += " order by bl.bl_commdatetime desc) vw  limit 0,5";
			} else {
				// opendid是老师，默认300个学生
				sql="select * from(SELECT bs.bs_name,bs.bs_cardno,bl.bl_longitude,bl_latitude,DATE_FORMAT(bl.bl_commdatetime,'%Y-%c-%d %H:%i')bl_commdatetime FROM bas_student bs ,bus_locationinfo bl where  bs.bs_cardno= bl.bl_cardno";
				sql+=" and bs.bc_id in (SELECT bc.id FROM bas_class bc ,t_s_base_user tu where bc.bc_personid=tu.id";
				sql+=" and tu.openid='"+sopenid+"'";
				sql+=" ) order by bl.bl_commdatetime desc) vw  limit 0,300";
			}
		} else {
			sql="select * from(SELECT bs.bs_name,bs.bs_cardno,bl.bl_longitude,bl_latitude,DATE_FORMAT(bl.bl_commdatetime,'%Y-%c-%d %H:%i')bl_commdatetime FROM bas_student bs ,bus_locationinfo bl where  bs.bs_cardno= bl.bl_cardno";
			sql+=" and bs.bc_id in (SELECT bc.id FROM bas_class bc ,t_s_base_user tu where bc.bc_personid=tu.id";
			sql+=" ) order by bl.bl_commdatetime desc) vw  limit 0,800";
		}

		List<Map<String, Object>> locationList = new ArrayList<Map<String, Object>>();
		locationList = systemService.findForJdbc(sql);
		request.setAttribute("locationList", locationList);
		return new ModelAndView("com/jeecg/basstudent/basStudentLocationList");
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
	public void datagrid(BasStudentLocationEntity basStudentLocation, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BasStudentLocationEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, basStudentLocation,
				request.getParameterMap());
		try {
			// 自定义追加查询条件

		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.basStudentLocationService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除学生资料
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(BasStudentLocationEntity basStudentLocation, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		basStudentLocation = systemService.getEntity(BasStudentLocationEntity.class, basStudentLocation.getId());
		message = "学生资料删除成功";
		try {
			basStudentLocationService.delete(basStudentLocation);
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
				BasStudentLocationEntity basStudentLocation = systemService.getEntity(BasStudentLocationEntity.class,
						id);
				basStudentLocationService.delete(basStudentLocation);
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
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(BasStudentLocationEntity basStudentLocation, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "学生资料添加成功";
		try {
			basStudentLocationService.save(basStudentLocation);
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
	public AjaxJson doUpdate(BasStudentLocationEntity basStudentLocation, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "学生资料更新成功";
		BasStudentLocationEntity t = basStudentLocationService.get(BasStudentLocationEntity.class,
				basStudentLocation.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(basStudentLocation, t);
			basStudentLocationService.saveOrUpdate(t);
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
	 * 学生资料新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(BasStudentLocationEntity basStudentLocation, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(basStudentLocation.getId())) {
			basStudentLocation = basStudentLocationService.getEntity(BasStudentLocationEntity.class,
					basStudentLocation.getId());
			req.setAttribute("basStudentLocation", basStudentLocation);
		}
		return new ModelAndView("com/jeecg/basstudent/basStudentLocation-add");
	}

	/**
	 * 学生资料编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(BasStudentLocationEntity basStudentLocation, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(basStudentLocation.getId())) {
			basStudentLocation = basStudentLocationService.getEntity(BasStudentLocationEntity.class,
					basStudentLocation.getId());
			req.setAttribute("basStudentLocation", basStudentLocation);
		}
		return new ModelAndView("com/jeecg/basstudent/basStudentLocation-update");
	}

	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "basStudentLocationController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(BasStudentLocationEntity basStudentLocation, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(BasStudentLocationEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, basStudentLocation,
				request.getParameterMap());
		List<BasStudentLocationEntity> basStudentLocations = this.basStudentLocationService.getListByCriteriaQuery(cq,
				false);
		modelMap.put(NormalExcelConstants.FILE_NAME, "学生资料");
		modelMap.put(NormalExcelConstants.CLASS, BasStudentLocationEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,
				new ExportParams("学生资料列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, basStudentLocations);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(BasStudentLocationEntity basStudentLocation, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
		modelMap.put(NormalExcelConstants.FILE_NAME, "学生资料");
		modelMap.put(NormalExcelConstants.CLASS, BasStudentLocationEntity.class);
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
				List<BasStudentLocationEntity> listBasStudentLocationEntitys = ExcelImportUtil
						.importExcel(file.getInputStream(), BasStudentLocationEntity.class, params);
				for (BasStudentLocationEntity basStudentLocation : listBasStudentLocationEntitys) {
					basStudentLocationService.save(basStudentLocation);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
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
	public ResponseMessage<List<BasStudentLocationEntity>> list() {
		List<BasStudentLocationEntity> listBasStudentLocations = basStudentLocationService
				.getList(BasStudentLocationEntity.class);
		return Result.success(listBasStudentLocations);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据ID获取学生资料信息", notes = "根据ID获取学生资料信息", httpMethod = "GET", produces = "application/json")
	public ResponseMessage<?> get(@ApiParam(required = true, name = "id", value = "ID") @PathVariable("id") String id) {
		BasStudentLocationEntity task = basStudentLocationService.get(BasStudentLocationEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取学生资料信息为空");
		}
		return Result.success(task);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value = "创建学生资料")
	public ResponseMessage<?> create(
			@ApiParam(name = "学生资料对象") @RequestBody BasStudentLocationEntity basStudentLocation,
			UriComponentsBuilder uriBuilder) {
		// 调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<BasStudentLocationEntity>> failures = validator.validate(basStudentLocation);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		// 保存
		try {
			basStudentLocationService.save(basStudentLocation);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("学生资料信息保存失败");
		}
		return Result.success(basStudentLocation);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value = "更新学生资料", notes = "更新学生资料")
	public ResponseMessage<?> update(
			@ApiParam(name = "学生资料对象") @RequestBody BasStudentLocationEntity basStudentLocation) {
		// 调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<BasStudentLocationEntity>> failures = validator.validate(basStudentLocation);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		// 保存
		try {
			basStudentLocationService.saveOrUpdate(basStudentLocation);
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
			basStudentLocationService.deleteEntityById(BasStudentLocationEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("学生资料删除失败");
		}

		return Result.success();
	}
}
