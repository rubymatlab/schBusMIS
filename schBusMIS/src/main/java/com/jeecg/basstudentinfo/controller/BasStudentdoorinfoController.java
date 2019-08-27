package com.jeecg.basstudentinfo.controller;
import com.jeecg.basstudentinfo.entity.BasStudentdoorinfoEntity;
import com.jeecg.basstudentinfo.service.BasStudentdoorinfoServiceI;
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
 * @Description: 学生门禁信息
 * @author onlineGenerator
 * @date 2019-08-27 16:17:51
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/basStudentdoorinfoController")
public class BasStudentdoorinfoController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BasStudentdoorinfoController.class);

	@Autowired
	private BasStudentdoorinfoServiceI basStudentdoorinfoService;
	@Autowired
	private SystemService systemService;
	


	/**
	 * 学生门禁信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/jeecg/basstudentinfo/basStudentdoorinfoList");
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
	public void datagrid(BasStudentdoorinfoEntity basStudentdoorinfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BasStudentdoorinfoEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, basStudentdoorinfo, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.basStudentdoorinfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 清除设备卡数据
	 * 
	 * @return
	 */
	@RequestMapping(params = "goClean")
	@ResponseBody
	public AjaxJson goClean(String bsMacno,HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		//System.out.println("bsMacno:"+bsMacno);
		message = "清除设备卡数据成功";
		try{
			String sql=" delete from bas_studentdoorinfo where bs_macno='"+bsMacno+"' ";
			systemService.executeSql(sql);			
		}catch(Exception e){
			e.printStackTrace();
			message = "清除设备卡数据失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 删除学生门禁信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(BasStudentdoorinfoEntity basStudentdoorinfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		basStudentdoorinfo = systemService.getEntity(BasStudentdoorinfoEntity.class, basStudentdoorinfo.getId());
		message = "学生门禁信息删除成功";
		try{
			basStudentdoorinfoService.delete(basStudentdoorinfo);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "学生门禁信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 批量删除学生门禁信息
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "学生门禁信息删除成功";
		try{
			for(String id:ids.split(",")){
				BasStudentdoorinfoEntity basStudentdoorinfo = systemService.getEntity(BasStudentdoorinfoEntity.class, 
				id
				);
				basStudentdoorinfoService.delete(basStudentdoorinfo);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "学生门禁信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加学生门禁信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(BasStudentdoorinfoEntity basStudentdoorinfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "学生门禁信息添加成功";
		try{
			basStudentdoorinfoService.save(basStudentdoorinfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "学生门禁信息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新学生门禁信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(BasStudentdoorinfoEntity basStudentdoorinfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "学生门禁信息更新成功";
		BasStudentdoorinfoEntity t = basStudentdoorinfoService.get(BasStudentdoorinfoEntity.class, basStudentdoorinfo.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(basStudentdoorinfo, t);
			basStudentdoorinfoService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "学生门禁信息更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 学生门禁信息新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(BasStudentdoorinfoEntity basStudentdoorinfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(basStudentdoorinfo.getId())) {
			basStudentdoorinfo = basStudentdoorinfoService.getEntity(BasStudentdoorinfoEntity.class, basStudentdoorinfo.getId());
			req.setAttribute("basStudentdoorinfoPage", basStudentdoorinfo);
		}
		return new ModelAndView("com/jeecg/basstudentinfo/basStudentdoorinfo-add");
	}
	/**
	 * 学生门禁信息编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(BasStudentdoorinfoEntity basStudentdoorinfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(basStudentdoorinfo.getId())) {
			basStudentdoorinfo = basStudentdoorinfoService.getEntity(BasStudentdoorinfoEntity.class, basStudentdoorinfo.getId());
			req.setAttribute("basStudentdoorinfoPage", basStudentdoorinfo);
		}
		return new ModelAndView("com/jeecg/basstudentinfo/basStudentdoorinfo-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","basStudentdoorinfoController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(BasStudentdoorinfoEntity basStudentdoorinfo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(BasStudentdoorinfoEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, basStudentdoorinfo, request.getParameterMap());
		List<BasStudentdoorinfoEntity> basStudentdoorinfos = this.basStudentdoorinfoService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"学生门禁信息");
		modelMap.put(NormalExcelConstants.CLASS,BasStudentdoorinfoEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("学生门禁信息列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,basStudentdoorinfos);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(BasStudentdoorinfoEntity basStudentdoorinfo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"学生门禁信息");
    	modelMap.put(NormalExcelConstants.CLASS,BasStudentdoorinfoEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("学生门禁信息列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<BasStudentdoorinfoEntity> listBasStudentdoorinfoEntitys = ExcelImportUtil.importExcel(file.getInputStream(),BasStudentdoorinfoEntity.class,params);
				for (BasStudentdoorinfoEntity basStudentdoorinfo : listBasStudentdoorinfoEntitys) {
					basStudentdoorinfoService.save(basStudentdoorinfo);
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
