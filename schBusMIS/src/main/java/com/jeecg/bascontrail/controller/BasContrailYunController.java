package com.jeecg.bascontrail.controller;
import com.jeecg.bascontrail.entity.BasContrailYunEntity;
import com.jeecg.bascontrail.service.BasContrailYunServiceI;
import com.jeecg.basline.entity.BasSizeEntity;
import com.jeecg.basstudent.entity.BasStudentLocationEntity;
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
 * @Description: 云在线通道
 * @author onlineGenerator
 * @date 2019-07-21 13:02:23
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/basContrailYunController")
public class BasContrailYunController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BasContrailYunController.class);

	@Autowired
	private BasContrailYunServiceI basContrailYunService;
	@Autowired
	private SystemService systemService;
	


	/**
	 * 云在线通道列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		List<BasStudentInfoEntity> listBe=new ArrayList<BasStudentInfoEntity>();
		for(BasStudentInfoEntity be:BusMapfenceController.listBs)
		{
			try {
				BasStudentInfoEntity o=new BasStudentInfoEntity();
				MyBeanUtils.copyBeanNotNull2Bean(be, o);
				listBe.add(o);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.setAttribute("listBe", listBe);
		BusMapfenceController.listBs.clear();
		return new ModelAndView("com/jeecg/bascontrail/basContrailYunList");
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
	public void datagrid(BasContrailYunEntity basContrailYun,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BasContrailYunEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, basContrailYun, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.basContrailYunService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除云在线通道
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(BasContrailYunEntity basContrailYun, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		basContrailYun = systemService.getEntity(BasContrailYunEntity.class, basContrailYun.getId());
		message = "云在线通道删除成功";
		try{
			basContrailYunService.delete(basContrailYun);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "云在线通道删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除云在线通道
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "云在线通道删除成功";
		try{
			for(String id:ids.split(",")){
				BasContrailYunEntity basContrailYun = systemService.getEntity(BasContrailYunEntity.class, 
				id
				);
				basContrailYunService.delete(basContrailYun);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "云在线通道删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加云在线通道
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(BasContrailYunEntity basContrailYun, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "云在线通道添加成功";
		try{
			basContrailYunService.save(basContrailYun);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "云在线通道添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新云在线通道
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(BasContrailYunEntity basContrailYun, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "云在线通道更新成功";
		BasContrailYunEntity t = basContrailYunService.get(BasContrailYunEntity.class, basContrailYun.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(basContrailYun, t);
			basContrailYunService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "云在线通道更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 云在线通道新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(BasContrailYunEntity basContrailYun, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(basContrailYun.getId())) {
			basContrailYun = basContrailYunService.getEntity(BasContrailYunEntity.class, basContrailYun.getId());
			req.setAttribute("basContrailYunPage", basContrailYun);
		}
		return new ModelAndView("com/jeecg/bascontrail/basContrailYun-add");
	}
	/**
	 * 云在线通道编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(BasContrailYunEntity basContrailYun, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(basContrailYun.getId())) {
			basContrailYun = basContrailYunService.getEntity(BasContrailYunEntity.class, basContrailYun.getId());
			req.setAttribute("basContrailYunPage", basContrailYun);
		}
		return new ModelAndView("com/jeecg/bascontrail/basContrailYun-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","basContrailYunController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(BasContrailYunEntity basContrailYun,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(BasContrailYunEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, basContrailYun, request.getParameterMap());
		List<BasContrailYunEntity> basContrailYuns = this.basContrailYunService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"云在线通道");
		modelMap.put(NormalExcelConstants.CLASS,BasContrailYunEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("云在线通道列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,basContrailYuns);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(BasContrailYunEntity basContrailYun,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"云在线通道");
    	modelMap.put(NormalExcelConstants.CLASS,BasContrailYunEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("云在线通道列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<BasContrailYunEntity> listBasContrailYunEntitys = ExcelImportUtil.importExcel(file.getInputStream(),BasContrailYunEntity.class,params);
				for (BasContrailYunEntity basContrailYun : listBasContrailYunEntitys) {
					basContrailYunService.save(basContrailYun);
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
