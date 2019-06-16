package com.jeecg.basstudent.controller;
import com.jeecg.basstudent.entity.BasCarinfoEntity;
import com.jeecg.basstudent.entity.VwBasCarinfoEntity;
import com.jeecg.basstudent.service.VwBasCarinfoServiceI;
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
 * @Description: 加密卡号基础资料
 * @author onlineGenerator
 * @date 2019-06-16 22:01:58
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/vwBasCarinfoController")
public class VwBasCarinfoController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(VwBasCarinfoController.class);

	@Autowired
	private VwBasCarinfoServiceI vwBasCarinfoService;
	@Autowired
	private SystemService systemService;
	


	/**
	 * 加密卡号基础资料列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/jeecg/basstudent/vwBasCarinfoList");
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
	public void datagrid(VwBasCarinfoEntity vwBasCarinfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(VwBasCarinfoEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, vwBasCarinfo, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.vwBasCarinfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除加密卡号基础资料
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(VwBasCarinfoEntity vwBasCarinfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		BasCarinfoEntity bce = systemService.getEntity(BasCarinfoEntity.class, vwBasCarinfo.getId());
		message = "加密卡号基础资料删除成功";
		try{
			vwBasCarinfoService.delete(bce);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "加密卡号基础资料删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除加密卡号基础资料
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "加密卡号基础资料删除成功";
		try{
			for(String id:ids.split(",")){
				VwBasCarinfoEntity vwBasCarinfo = systemService.getEntity(VwBasCarinfoEntity.class, 
				id
				);
				vwBasCarinfoService.delete(vwBasCarinfo);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "加密卡号基础资料删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加加密卡号基础资料
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(VwBasCarinfoEntity vwBasCarinfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "加密卡号基础资料添加成功";
		try{
			vwBasCarinfoService.save(vwBasCarinfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "加密卡号基础资料添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新加密卡号基础资料
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(VwBasCarinfoEntity vwBasCarinfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "加密卡号基础资料更新成功";
		VwBasCarinfoEntity t = vwBasCarinfoService.get(VwBasCarinfoEntity.class, vwBasCarinfo.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(vwBasCarinfo, t);
			vwBasCarinfoService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "加密卡号基础资料更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 加密卡号基础资料新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(VwBasCarinfoEntity vwBasCarinfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(vwBasCarinfo.getId())) {
			vwBasCarinfo = vwBasCarinfoService.getEntity(VwBasCarinfoEntity.class, vwBasCarinfo.getId());
			req.setAttribute("vwBasCarinfoPage", vwBasCarinfo);
		}
		return new ModelAndView("com/jeecg/basstudent/vwBasCarinfo-add");
	}
	/**
	 * 加密卡号基础资料编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(VwBasCarinfoEntity vwBasCarinfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(vwBasCarinfo.getId())) {
			vwBasCarinfo = vwBasCarinfoService.getEntity(VwBasCarinfoEntity.class, vwBasCarinfo.getId());
			req.setAttribute("vwBasCarinfoPage", vwBasCarinfo);
		}
		return new ModelAndView("com/jeecg/basstudent/vwBasCarinfo-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","vwBasCarinfoController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(VwBasCarinfoEntity vwBasCarinfo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(VwBasCarinfoEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, vwBasCarinfo, request.getParameterMap());
		List<VwBasCarinfoEntity> vwBasCarinfos = this.vwBasCarinfoService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"加密卡号基础资料");
		modelMap.put(NormalExcelConstants.CLASS,VwBasCarinfoEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("加密卡号基础资料列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,vwBasCarinfos);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(VwBasCarinfoEntity vwBasCarinfo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"加密卡号基础资料");
    	modelMap.put(NormalExcelConstants.CLASS,VwBasCarinfoEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("加密卡号基础资料列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				String message="";
				List<BasCarinfoEntity> listVwBasCarinfoEntitys = ExcelImportUtil.importExcel(file.getInputStream(),BasCarinfoEntity.class,params);
				for (BasCarinfoEntity vwBasCarinfo : listVwBasCarinfoEntitys) {
					List<VwBasCarinfoEntity> listA=vwBasCarinfoService.findByProperty(VwBasCarinfoEntity.class, "bsCardno", vwBasCarinfo.getBsCardno());
					if(listA.size()>0)
					{
						message+=listA.get(0).getBsCardno()+"重复;";
					}
				}
				if("".equals(message))
				{
					for (BasCarinfoEntity bce : listVwBasCarinfoEntitys) {
						vwBasCarinfoService.save(bce);
					}
					j.setMsg("文件导入成功！");
				}
				else
				{
					j.setMsg(message);
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
	
	
}
