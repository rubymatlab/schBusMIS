package com.jeecg.basstudent.controller;
import com.jeecg.basstudent.entity.BasFenceconfigEntity;
import com.jeecg.basstudent.service.BasFenceconfigServiceI;
import com.jeecg.basstudentinfo.entity.BasStudentInfoEntity;

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


/**   
 * @Title: Controller  
 * @Description: 围栏监控维护
 * @author onlineGenerator
 * @date 2019-06-19 07:22:27
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/basFenceconfigController")
public class BasFenceconfigController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BasFenceconfigController.class);

	@Autowired
	private BasFenceconfigServiceI basFenceconfigService;
	@Autowired
	private SystemService systemService;
	


	/**
	 * 围栏监控维护列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/jeecg/basstudent/basFenceconfigList");
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
	public void datagrid(BasFenceconfigEntity basFenceconfig,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BasFenceconfigEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, basFenceconfig, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.basFenceconfigService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除围栏监控维护
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(BasFenceconfigEntity basFenceconfig, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		basFenceconfig = systemService.getEntity(BasFenceconfigEntity.class, basFenceconfig.getId());
		message = "围栏监控维护删除成功";
		try{
			doDeleteStudent(basFenceconfig);
			basFenceconfigService.delete(basFenceconfig);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "围栏监控维护删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除围栏监控维护
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "围栏监控维护删除成功";
		try{
			for(String id:ids.split(",")){
				BasFenceconfigEntity basFenceconfig = systemService.getEntity(BasFenceconfigEntity.class, 
				id
				);
				doDeleteStudent(basFenceconfig);
				basFenceconfigService.delete(basFenceconfig);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "围栏监控维护删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加围栏监控维护
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(BasFenceconfigEntity basFenceconfig, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "围栏监控维护添加成功";
		try{
			doUpdateStudent(basFenceconfig);
			basFenceconfigService.save(basFenceconfig);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "围栏监控维护添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新围栏监控维护
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(BasFenceconfigEntity basFenceconfig, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "围栏监控维护更新成功";
		BasFenceconfigEntity t = basFenceconfigService.get(BasFenceconfigEntity.class, basFenceconfig.getId());
		try {			
			doUpdateStudent(basFenceconfig,t);
			MyBeanUtils.copyBeanNotNull2Bean(basFenceconfig, t);
			doUpdateStudent(basFenceconfig);
			
			basFenceconfigService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "围栏监控维护更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/*
	 * 更新学生表的方案
	 */
	private void doUpdateStudent(BasFenceconfigEntity basFenceconfig,BasFenceconfigEntity t)
	{
			List<BasStudentInfoEntity> listBsi = systemService.findByProperty(BasStudentInfoEntity.class, "bfName",t.getBfName());
			for(BasStudentInfoEntity bsi:listBsi)
			{
				bsi.setBfName(basFenceconfig.getBfName());
				basFenceconfigService.saveOrUpdate(bsi);
			}
	}
	/*
	 * 更新学生表的方案
	 */
	private void doUpdateStudent(BasFenceconfigEntity basFenceconfig)
	{
		for(String cardno:basFenceconfig.getBfStudent().split(",")){
			List<BasStudentInfoEntity> listBsi = systemService.findByProperty(BasStudentInfoEntity.class, "bsCardno",cardno);
			for(BasStudentInfoEntity bsi:listBsi)
			{
				bsi.setBfName(basFenceconfig.getBfName());
				basFenceconfigService.saveOrUpdate(bsi);
			}
		}
	}
	
	/*
	 * 删除学生表的方案
	 */
	private void doDeleteStudent(BasFenceconfigEntity basFenceconfig)
	{
		for(String cardno:basFenceconfig.getBfStudent().split(",")){
			List<BasStudentInfoEntity> listBsi = systemService.findByProperty(BasStudentInfoEntity.class, "bsCardno",cardno);
			for(BasStudentInfoEntity bsi:listBsi)
			{
				bsi.setBfName(null);
				basFenceconfigService.saveOrUpdate(bsi);
			}
		}
	}
	

	/**
	 * 围栏监控维护新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(BasFenceconfigEntity basFenceconfig, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(basFenceconfig.getId())) {
			basFenceconfig = basFenceconfigService.getEntity(BasFenceconfigEntity.class, basFenceconfig.getId());
			req.setAttribute("basFenceconfigPage", basFenceconfig);
		}
		return new ModelAndView("com/jeecg/basstudent/basFenceconfig-add");
	}
	/**
	 * 围栏监控维护编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(BasFenceconfigEntity basFenceconfig, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(basFenceconfig.getId())) {
			basFenceconfig = basFenceconfigService.getEntity(BasFenceconfigEntity.class, basFenceconfig.getId());
			req.setAttribute("basFenceconfigPage", basFenceconfig);
		}
		return new ModelAndView("com/jeecg/basstudent/basFenceconfig-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","basFenceconfigController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(BasFenceconfigEntity basFenceconfig,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(BasFenceconfigEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, basFenceconfig, request.getParameterMap());
		List<BasFenceconfigEntity> basFenceconfigs = this.basFenceconfigService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"围栏监控维护");
		modelMap.put(NormalExcelConstants.CLASS,BasFenceconfigEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("围栏监控维护列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,basFenceconfigs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(BasFenceconfigEntity basFenceconfig,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"围栏监控维护");
    	modelMap.put(NormalExcelConstants.CLASS,BasFenceconfigEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("围栏监控维护列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<BasFenceconfigEntity> listBasFenceconfigEntitys = ExcelImportUtil.importExcel(file.getInputStream(),BasFenceconfigEntity.class,params);
				for (BasFenceconfigEntity basFenceconfig : listBasFenceconfigEntitys) {
					basFenceconfigService.save(basFenceconfig);
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
	
	
}
