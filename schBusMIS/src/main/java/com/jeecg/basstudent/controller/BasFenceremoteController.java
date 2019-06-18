package com.jeecg.basstudent.controller;
import com.jeecg.basstudent.entity.BasFenceremoteEntity;
import com.jeecg.basstudent.service.BasFenceremoteServiceI;
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
 * @Description: 远程电子围栏信息
 * @author onlineGenerator
 * @date 2019-06-18 21:56:33
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/basFenceremoteController")
public class BasFenceremoteController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BasFenceremoteController.class);

	@Autowired
	private BasFenceremoteServiceI basFenceremoteService;
	@Autowired
	private SystemService systemService;
	


	/**
	 * 远程电子围栏信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/jeecg/basstudent/basFenceremoteList");
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
	public void datagrid(BasFenceremoteEntity basFenceremote,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try {
			basFenceremoteService.doGetFenceDeviceBus(basFenceremote);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		CriteriaQuery cq = new CriteriaQuery(BasFenceremoteEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, basFenceremote, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.basFenceremoteService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除远程电子围栏信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(BasFenceremoteEntity basFenceremote, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		basFenceremote = systemService.getEntity(BasFenceremoteEntity.class, basFenceremote.getId());
		message = "远程电子围栏信息删除成功";
		try{
			try {
				basFenceremoteService.doDelBus(basFenceremote);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//basFenceremoteService.delete(basFenceremote);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "远程电子围栏信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除远程电子围栏信息
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "远程电子围栏信息删除成功";
		try{
			for(String id:ids.split(",")){
				BasFenceremoteEntity basFenceremote = systemService.getEntity(BasFenceremoteEntity.class, 
				id
				);
				basFenceremoteService.delete(basFenceremote);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "远程电子围栏信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加远程电子围栏信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(BasFenceremoteEntity basFenceremote, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "远程电子围栏信息添加成功";
		try{
			basFenceremoteService.save(basFenceremote);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "远程电子围栏信息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新远程电子围栏信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(BasFenceremoteEntity basFenceremote, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "远程电子围栏信息更新成功";
		BasFenceremoteEntity t = basFenceremoteService.get(BasFenceremoteEntity.class, basFenceremote.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(basFenceremote, t);
			basFenceremoteService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "远程电子围栏信息更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
 	/**
	 * 自定义按钮-[添加围栏]业务
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAddFenceDevice")
	@ResponseBody
	public AjaxJson doAddFenceDevice(BasFenceremoteEntity basFenceremote, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "添加围栏成功";
		//BasFenceremoteEntity t = basFenceremoteService.get(BasFenceremoteEntity.class, basFenceremote.getId());
		try{
			//basFenceremoteService.doAddFenceDeviceBus(t);
			try {
				basFenceremoteService.doAddBus(basFenceremote);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "添加围栏失败";
		}
		j.setMsg(message);
		return j;
	}
 	/**
	 * 自定义按钮-[获取围栏]业务
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doGetFenceDevice")
	@ResponseBody
	public AjaxJson doGetFenceDevice(BasFenceremoteEntity basFenceremote, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "获取围栏成功";
		BasFenceremoteEntity t = basFenceremoteService.get(BasFenceremoteEntity.class, basFenceremote.getId());
		try{
			basFenceremoteService.doGetFenceDeviceBus(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "获取围栏失败";
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 远程电子围栏信息新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(BasFenceremoteEntity basFenceremote, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(basFenceremote.getId())) {
			basFenceremote = basFenceremoteService.getEntity(BasFenceremoteEntity.class, basFenceremote.getId());
			req.setAttribute("basFenceremotePage", basFenceremote);
		}
		return new ModelAndView("com/jeecg/basstudent/basFenceremote-add");
	}
	/**
	 * 远程电子围栏信息编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(BasFenceremoteEntity basFenceremote, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(basFenceremote.getId())) {
			basFenceremote = basFenceremoteService.getEntity(BasFenceremoteEntity.class, basFenceremote.getId());
			req.setAttribute("basFenceremotePage", basFenceremote);
		}
		return new ModelAndView("com/jeecg/basstudent/basFenceremote-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","basFenceremoteController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(BasFenceremoteEntity basFenceremote,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(BasFenceremoteEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, basFenceremote, request.getParameterMap());
		List<BasFenceremoteEntity> basFenceremotes = this.basFenceremoteService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"远程电子围栏信息");
		modelMap.put(NormalExcelConstants.CLASS,BasFenceremoteEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("远程电子围栏信息列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,basFenceremotes);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(BasFenceremoteEntity basFenceremote,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"远程电子围栏信息");
    	modelMap.put(NormalExcelConstants.CLASS,BasFenceremoteEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("远程电子围栏信息列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<BasFenceremoteEntity> listBasFenceremoteEntitys = ExcelImportUtil.importExcel(file.getInputStream(),BasFenceremoteEntity.class,params);
				for (BasFenceremoteEntity basFenceremote : listBasFenceremoteEntitys) {
					basFenceremoteService.save(basFenceremote);
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
