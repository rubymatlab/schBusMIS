package com.jeecg.bascontrail.controller;
import com.jeecg.bascontrail.entity.BusMapfenceEntity;
import com.jeecg.bascontrail.service.BusMapfenceServiceI;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Enumeration;
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
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.core.util.ResourceUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import java.util.HashMap;
import org.jeecgframework.core.util.ExceptionUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**   
 * @Title: Controller  
 * @Description: 电子围栏
 * @author onlineGenerator
 * @date 2019-06-10 21:40:30
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/busMapfenceController")
public class BusMapfenceController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BusMapfenceController.class);

	@Autowired
	private BusMapfenceServiceI busMapfenceService;
	@Autowired
	private SystemService systemService;
	


	/**
	 * 电子围栏列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/jeecg/bascontrail/busMapfenceList");
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
	public void datagrid(BusMapfenceEntity busMapfence,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BusMapfenceEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, busMapfence, request.getParameterMap());
		try{
		//自定义追加查询条件
		
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.busMapfenceService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除电子围栏
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(BusMapfenceEntity busMapfence, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		busMapfence = systemService.getEntity(BusMapfenceEntity.class, busMapfence.getId());
		message = "电子围栏删除成功";
		try{
			busMapfenceService.delete(busMapfence);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "电子围栏删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除电子围栏
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "电子围栏删除成功";
		try{
			for(String id:ids.split(",")){
				BusMapfenceEntity busMapfence = systemService.getEntity(BusMapfenceEntity.class, 
				id
				);
				busMapfenceService.delete(busMapfence);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "电子围栏删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加电子围栏
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(BusMapfenceEntity busMapfence, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "电子围栏添加成功";
		try{
			busMapfenceService.save(busMapfence);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "电子围栏添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新电子围栏
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(BusMapfenceEntity busMapfence, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "电子围栏更新成功";
		BusMapfenceEntity t = busMapfenceService.get(BusMapfenceEntity.class, busMapfence.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(busMapfence, t);
			busMapfenceService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "电子围栏更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 电子围栏新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(BusMapfenceEntity busMapfence, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(busMapfence.getId())) {
			busMapfence = busMapfenceService.getEntity(BusMapfenceEntity.class, busMapfence.getId());
			req.setAttribute("busMapfence", busMapfence);
		}
		return new ModelAndView("com/jeecg/bascontrail/busMapfence-add");
	}
	/**
	 * 电子围栏编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(BusMapfenceEntity busMapfence, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(busMapfence.getId())) {
			busMapfence = busMapfenceService.getEntity(BusMapfenceEntity.class, busMapfence.getId());
			req.setAttribute("busMapfence", busMapfence);
		}
		return new ModelAndView("com/jeecg/bascontrail/busMapfence-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","busMapfenceController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(BusMapfenceEntity busMapfence,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(BusMapfenceEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, busMapfence, request.getParameterMap());
		List<BusMapfenceEntity> busMapfences = this.busMapfenceService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"电子围栏");
		modelMap.put(NormalExcelConstants.CLASS,BusMapfenceEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("电子围栏列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,busMapfences);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(BusMapfenceEntity busMapfence,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"电子围栏");
    	modelMap.put(NormalExcelConstants.CLASS,BusMapfenceEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("电子围栏列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<BusMapfenceEntity> listBusMapfenceEntitys = ExcelImportUtil.importExcel(file.getInputStream(),BusMapfenceEntity.class,params);
				for (BusMapfenceEntity busMapfence : listBusMapfenceEntitys) {
					busMapfenceService.save(busMapfence);
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
	 * 添加电子围栏
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAddData")
	@ResponseBody
	public AjaxJson doAddData(HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		try{
			BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
			StringBuilder responseStrBuilder = new StringBuilder();
			String inputStr;
			while ((inputStr = streamReader.readLine()) != null)
				responseStrBuilder.append(inputStr);
			
			JSONObject jsonObject = JSONObject.fromObject(responseStrBuilder.toString());
			
			BusMapfenceEntity busMapfence=new BusMapfenceEntity();
			if(jsonObject.containsKey("deviceId"))
				busMapfence.setDeviceId(jsonObject.getString("deviceId"));
			else
				message+="deviceId为空;";
			if(jsonObject.containsKey("signalPower"))
				busMapfence.setSignalPower(jsonObject.getString("signalPower"));
			else
				message+="signalPower为空;";
			if(jsonObject.containsKey("cellId"))
				busMapfence.setCellId(jsonObject.getString("cellId"));
			else
				message+="cellId为空;";
			if(jsonObject.containsKey("gps_time"))
				busMapfence.setGpsTime(jsonObject.getString("gps_time"));
			else
				message+="gps_time为空;";
			if(jsonObject.containsKey("gps_latitude"))
				busMapfence.setGpsLatitude(jsonObject.getString("gps_latitude"));
			else
				message+="gps_latitude为空;";
			if(jsonObject.containsKey("gps_longitude"))
				busMapfence.setGpsLongitude(jsonObject.getString("gps_longitude"));
			else
				message+="gps_longitude为空;";
			if(jsonObject.containsKey("timestamp"))
				busMapfence.setTimestamp(jsonObject.getString("timestamp"));
			else
				message+="timestamp为空;";
			if(jsonObject.containsKey("next_trc_time"))
				busMapfence.setNextTrcTime(jsonObject.getString("next_trc_time"));
			else
				message+="next_trc_time为空;";
			if(jsonObject.containsKey("device_time"))
				busMapfence.setDeviceTime(jsonObject.getString("device_time"));
			else
				message+="device_time为空;";
			if(jsonObject.containsKey("accuracy"))
				busMapfence.setAccuracy(jsonObject.getString("accuracy"));
			else
				message+="accuracy为空;";
			if(jsonObject.containsKey("counter"))
				busMapfence.setCounter(jsonObject.getString("counter"));
			else
				message+="counter为空;";
			if(jsonObject.containsKey("type"))
				busMapfence.setType(jsonObject.getString("type"));
			else
				message+="type为空;";
			if(jsonObject.containsKey("msg"))
				busMapfence.setMsg(jsonObject.getString("msg"));
			else
				message+="msg为空;";
			if(message==null)
			{
				busMapfenceService.save(busMapfence);
				message = "电子围栏写入成功";
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "电子围栏添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	@RequestMapping(params = "doPostData")
	@ResponseBody
	public AjaxJson doPostData(HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		try{
			BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
			StringBuilder responseStrBuilder = new StringBuilder();
			String inputStr;
			while ((inputStr = streamReader.readLine()) != null)
				responseStrBuilder.append(inputStr);
			
			//JSONObject jsonObject = JSONObject.fromObject(responseStrBuilder.toString());
			
			System.out.println(responseStrBuilder.toString());
			
			Map map = new HashMap();
			Enumeration paramNames = request.getParameterNames();
			while (paramNames.hasMoreElements()) {
				String paramName = (String) paramNames.nextElement();

				String[] paramValues = request.getParameterValues(paramName);
				if (paramValues.length == 1) {
					String paramValue = paramValues[0];
					if (paramValue.length() != 0) {
						System.out.println("参数：" + paramName + "=" + paramValue);
						map.put(paramName, paramValue);
					}
				}
			}
			if(message==null)
			{
				message = "双通道写入成功";
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "双通道添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
}
