package com.jeecg.bascontrail.controller;

import com.jeecg.bascontrail.entity.BasContrailYunEntity;
import com.jeecg.bascontrail.entity.BusMapfenceEntity;
import com.jeecg.bascontrail.service.BasContrailYunServiceI;
import com.jeecg.bascontrail.service.BusMapfenceServiceI;
import com.jeecg.basstudent.entity.BasStudentLocationEntity;
import com.jeecg.basstudentinfo.entity.BasStudentInfoEntity;

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
	public void datagrid(BusMapfenceEntity busMapfence, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BusMapfenceEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, busMapfence, request.getParameterMap());
		try {
			// 自定义追加查询条件

		} catch (Exception e) {
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
		try {
			busMapfenceService.delete(busMapfence);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
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
	public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "电子围栏删除成功";
		try {
			for (String id : ids.split(",")) {
				BusMapfenceEntity busMapfence = systemService.getEntity(BusMapfenceEntity.class, id);
				busMapfenceService.delete(busMapfence);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
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
		try {
			busMapfenceService.save(busMapfence);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
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
		req.setAttribute("controller_name", "busMapfenceController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(BusMapfenceEntity busMapfence, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(BusMapfenceEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, busMapfence, request.getParameterMap());
		List<BusMapfenceEntity> busMapfences = this.busMapfenceService.getListByCriteriaQuery(cq, false);
		modelMap.put(NormalExcelConstants.FILE_NAME, "电子围栏");
		modelMap.put(NormalExcelConstants.CLASS, BusMapfenceEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,
				new ExportParams("电子围栏列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, busMapfences);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(BusMapfenceEntity busMapfence, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid, ModelMap modelMap) {
		modelMap.put(NormalExcelConstants.FILE_NAME, "电子围栏");
		modelMap.put(NormalExcelConstants.CLASS, BusMapfenceEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,
				new ExportParams("电子围栏列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));
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
				List<BusMapfenceEntity> listBusMapfenceEntitys = ExcelImportUtil.importExcel(file.getInputStream(),
						BusMapfenceEntity.class, params);
				for (BusMapfenceEntity busMapfence : listBusMapfenceEntitys) {
					busMapfenceService.save(busMapfence);
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

	/**
	 * 添加电子围栏
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAddData")
	@ResponseBody
	public AjaxJson doAddData(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		try {
			BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			StringBuilder responseStrBuilder = new StringBuilder();
			String inputStr;
			while ((inputStr = streamReader.readLine()) != null)
				responseStrBuilder.append(inputStr);

			JSONObject jsonObject = JSONObject.fromObject(responseStrBuilder.toString());

			BusMapfenceEntity busMapfence = new BusMapfenceEntity();
			if (jsonObject.containsKey("deviceId"))
				busMapfence.setDeviceId(jsonObject.getString("deviceId"));
			else
				message += "deviceId为空;";
			if (jsonObject.containsKey("signalPower"))
				busMapfence.setSignalPower(jsonObject.getString("signalPower"));
			else
				message += "signalPower为空;";
			if (jsonObject.containsKey("cellId"))
				busMapfence.setCellId(jsonObject.getString("cellId"));
			else
				message += "cellId为空;";
			if (jsonObject.containsKey("gps_time"))
				busMapfence.setGpsTime(jsonObject.getString("gps_time"));
			else
				message += "gps_time为空;";
			if (jsonObject.containsKey("gps_latitude"))
				busMapfence.setGpsLatitude(jsonObject.getString("gps_latitude"));
			else
				message += "gps_latitude为空;";
			if (jsonObject.containsKey("gps_longitude"))
				busMapfence.setGpsLongitude(jsonObject.getString("gps_longitude"));
			else
				message += "gps_longitude为空;";
			if (jsonObject.containsKey("timestamp"))
				busMapfence.setTimestamp(jsonObject.getString("timestamp"));
			else
				message += "timestamp为空;";
			if (jsonObject.containsKey("next_trc_time"))
				busMapfence.setNextTrcTime(jsonObject.getString("next_trc_time"));
			else
				message += "next_trc_time为空;";
			if (jsonObject.containsKey("device_time"))
				busMapfence.setDeviceTime(jsonObject.getString("device_time"));
			else
				message += "device_time为空;";
			if (jsonObject.containsKey("accuracy"))
				busMapfence.setAccuracy(jsonObject.getString("accuracy"));
			else
				message += "accuracy为空;";
			if (jsonObject.containsKey("counter"))
				busMapfence.setCounter(jsonObject.getString("counter"));
			else
				message += "counter为空;";
			if (jsonObject.containsKey("type"))
				busMapfence.setType(jsonObject.getString("type"));
			else
				message += "type为空;";
			if (jsonObject.containsKey("msg"))
				busMapfence.setMsg(jsonObject.getString("msg"));
			else
				message += "msg为空;";
			if (message == null) {
				busMapfenceService.save(busMapfence);
				message = "电子围栏写入成功";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "电子围栏添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	public static List<BasStudentInfoEntity> listBs = new ArrayList<BasStudentInfoEntity>();

	@Autowired
	private BasContrailYunServiceI basContrailYunService;

	/*
	 * 双通道刷卡
	 */
//	@RequestMapping(params = "doPostData")
//	@ResponseBody
//	public JSONObject doPostData(HttpServletRequest request) {
//		String message = null;
//		//AjaxJson j = new AjaxJson();
//		JSONObject json=null;
//		try {
//			BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
//			StringBuilder responseStrBuilder = new StringBuilder();
//			String inputStr;
//			while ((inputStr = streamReader.readLine()) != null)
//				responseStrBuilder.append(inputStr);
//			// System.out.println(responseStrBuilder.toString());
//			if (null != responseStrBuilder.toString()) {
//				JSONObject ob = JSONObject.fromObject(responseStrBuilder.toString());
//				if(ob.containsKey("type") && ob.containsKey("IndexEvent") && ob.containsKey("IndexAlarm"))
//				{
//					String type=ob.getString("type");
//					String IndexEvent=ob.getString("IndexEvent");
//					String IndexAlarm=ob.getString("IndexAlarm");
//					String str="{}";
//					if (type == null || type.equals("")) 
//						type = "0";
//					if (IndexEvent == null || IndexEvent.equals("")) 
//						IndexEvent = "0";
//					if (IndexAlarm == null || IndexAlarm.equals(""))
//						IndexAlarm = "0";
//
//					String acsres="0";
//					String time="0";
//					if(ob.containsKey("Card"))
//					{
//						String card = ob.getString("Card");
//						List<BasStudentInfoEntity> listBsl = busMapfenceService.findByProperty(BasStudentInfoEntity.class,
//								"bsCardno", card);
//						if(listBsl.size()>0)
//						{
//							acsres="1";
//							time="1";
//						}
//					}
//					if (type.equals("100")) {
//						str = "{\"IndexEvent\":\"" + IndexEvent + "\"}";
//					} else if (type.equals("101")) {
//						str = "{\"IndexAlarm\":\"" + IndexAlarm + "\"}";
//					}else if (type.equals("0")) {
//						str = "{\"ActIndex\":\"0\",\"AcsRes\":\""+acsres+"\",\"Time\":\""+time+"\"}";
//					} else if (type.equals("1")) {
//						str = "{\"ActIndex\":\"0\",\"AcsRes\":\""+acsres+"\",\"Time\":\""+time+"\"}";
//					} else if (type.equals("9")) {
//						str = "{\"ActIndex\":\"0\",\"AcsRes\":\""+acsres+"\",\"Time\":\""+time+"\"}";
//					}else
//					{
//						str = "{\"ActIndex\":\"0\",\"AcsRes\":\"0\",\"Time\":\"0\"}";
//					}
//					json = JSONObject.fromObject(str);
//				}
//				if (ob.containsKey("Card") && ob.containsKey("Reader")) {
//					String card = ob.getString("Card");
//					String reader = ob.getString("Reader");
//					List<BasStudentInfoEntity> listBsl = busMapfenceService.findByProperty(BasStudentInfoEntity.class,
//							"bsCardno", card);
//					for (BasStudentInfoEntity be : listBsl) {
//						BasStudentInfoEntity o = new BasStudentInfoEntity();
//						MyBeanUtils.copyBeanNotNull2Bean(be, o);
//						if ("0".equals(reader))
//							o.setBsDesc("进通道");
//						else
//							o.setBsDesc("出通道");
//
//						listBs.add(o);
//					}
//					if (listBsl.size() == 0) {
//						BasStudentInfoEntity o = new BasStudentInfoEntity();
//						o.setBsName("未知");
//						o.setBcGrade("未知");
//						o.setBcName("未知");
//						o.setBsSex("0");
//						o.setBsCardno(card);
//						if ("0".equals(reader))
//							o.setBsDesc("进通道");
//						else
//							o.setBsDesc("出通道");
//						listBs.add(o);
//					}
//				}
//			}
//			try {
//				BasContrailYunEntity basContrailYun = new BasContrailYunEntity();
//				basContrailYun.setBcyKey("刷卡值");
//				basContrailYun.setBcyDesc("双通道刷卡");
//				basContrailYun.setBcyValue(responseStrBuilder.toString());
//				basContrailYunService.save(basContrailYun);
//			} catch (Exception e) {
//				// e.printStackTrace();
//				message = "云在线通道添加失败";
//				throw new BusinessException(e.getMessage());
//			}
//			if (message == null) {
//				message = "双通道写入成功";
//			}
//		} catch (Exception e) {
//			// e.printStackTrace();
//			message = "双通道添加失败";
//			throw new BusinessException(e.getMessage());
//		}
//		//j.setMsg(message);
//		return json;
//	}
	
	@RequestMapping(params = "doPostData")
	@ResponseBody
	public JSONObject doPostData(HttpServletRequest request) {
		String message = null;
		//AjaxJson j = new AjaxJson();
		JSONObject json=null;
		try {
			Enumeration pNames=request.getParameterNames();
			HashMap m=new HashMap();
			while(pNames.hasMoreElements()){
				String name=(String)pNames.nextElement();
				String value=request.getParameter(name);
				m.put(name, value);
			}
			JSONObject ob = JSONObject.fromObject(m);
			//System.out.print(ob.toString());
				if(ob.containsKey("type") && ob.containsKey("IndexEvent") && ob.containsKey("IndexAlarm"))
				{
					String type=ob.getString("type");
					String IndexEvent=ob.getString("IndexEvent");
					String IndexAlarm=ob.getString("IndexAlarm");
					String str="{}";
					if (type == null || type.equals("")) 
						type = "0";
					if (IndexEvent == null || IndexEvent.equals("")) 
						IndexEvent = "0";
					if (IndexAlarm == null || IndexAlarm.equals(""))
						IndexAlarm = "0";

					String acsres="0";
					String time="0";
					if(ob.containsKey("Card"))
					{
						String card = ob.getString("Card");
						List<BasStudentInfoEntity> listBsl = busMapfenceService.findByProperty(BasStudentInfoEntity.class,
								"bsCardno", card);
						if(listBsl.size()>0)
						{
							acsres="1";
							time="1";
						}
					}
					if (type.equals("100")) {
						str = "{\"IndexEvent\":\"" + IndexEvent + "\"}";
					} else if (type.equals("101")) {
						str = "{\"IndexAlarm\":\"" + IndexAlarm + "\"}";
					}else if (type.equals("0")) {
						str = "{\"ActIndex\":\"0\",\"AcsRes\":\""+acsres+"\",\"Time\":\""+time+"\"}";
					} else if (type.equals("1")) {
						str = "{\"ActIndex\":\"0\",\"AcsRes\":\""+acsres+"\",\"Time\":\""+time+"\"}";
					} else if (type.equals("9")) {
						str = "{\"ActIndex\":\"0\",\"AcsRes\":\""+acsres+"\",\"Time\":\""+time+"\"}";
					}else
					{
						str = "{\"ActIndex\":\"0\",\"AcsRes\":\"0\",\"Time\":\"0\"}";
					}
					json = JSONObject.fromObject(str);
				}
				if (ob.containsKey("Card") && ob.containsKey("Reader")) {
					String card = ob.getString("Card");
					String reader = ob.getString("Reader");
					List<BasStudentInfoEntity> listBsl = busMapfenceService.findByProperty(BasStudentInfoEntity.class,
							"bsCardno", card);
					for (BasStudentInfoEntity be : listBsl) {
						BasStudentInfoEntity o = new BasStudentInfoEntity();
						MyBeanUtils.copyBeanNotNull2Bean(be, o);
						if ("0".equals(reader))
							o.setBsDesc("进通道");
						else
							o.setBsDesc("出通道");

						listBs.add(o);
					}
					if (listBsl.size() == 0) {
						BasStudentInfoEntity o = new BasStudentInfoEntity();
						o.setBsName("未知");
						o.setBcGrade("未知");
						o.setBcName("未知");
						o.setBsSex("0");
						o.setBsCardno(card);
						if ("0".equals(reader))
							o.setBsDesc("进通道");
						else
							o.setBsDesc("出通道");
						listBs.add(o);
					}
				}
			try {
				BasContrailYunEntity basContrailYun = new BasContrailYunEntity();
				basContrailYun.setBcyKey("刷卡值");
				basContrailYun.setBcyDesc("双通道刷卡");
				basContrailYun.setBcyValue(ob.toString());
				basContrailYunService.save(basContrailYun);
			} catch (Exception e) {
				// e.printStackTrace();
				message = "云在线通道添加失败";
				throw new BusinessException(e.getMessage());
			}
			if (message == null) {
				message = "双通道写入成功";
			}
		} catch (Exception e) {
			// e.printStackTrace();
			message = "双通道添加失败";
			throw new BusinessException(e.getMessage());
		}
		//j.setMsg(message);
		return json;
	}

	// 请求(控制器向服务器请求)
	@RequestMapping(params = "getReqData")
	@ResponseBody
	public JSONObject getReqData(String type, String IndexEvent, String IndexAlarm, HttpServletRequest request,
			HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		String str = "";
		System.out.println("请求类型:" + type);

		if (type == null || type.equals("")) {
			type = "0";
		}
		if (IndexEvent == null || IndexEvent.equals("")) {
			IndexEvent = "0";
		}
		if (IndexAlarm == null || IndexAlarm.equals("")) {
			IndexAlarm = "0";
		}

		if (type.equals("100")) {
			str = "{\"IndexEvent\":\"" + IndexEvent + "\"}";
		} else if (type.equals("101")) {
			str = "{\"IndexAlarm\":\"" + IndexAlarm + "\"}";
		}
		// type=0/1/9 开门
		else if (type.equals("0")) {
			str = "{\"ActIndex\":\"0\",\"AcsRes\":\"1\",\"Time\":\"1\"}";
		} else if (type.equals("1")) {
			str = "{\"ActIndex\":\"0\",\"AcsRes\":\"1\",\"Time\":\"1\"}";
		} else if (type.equals("9")) {
			str = "{\"ActIndex\":\"0\",\"AcsRes\":\"1\",\"Time\":\"1\"}";
		}

		JSONObject json = JSONObject.fromObject(str);

		return json;
	};

}
