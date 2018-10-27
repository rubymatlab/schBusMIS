package com.jeecg.basstudent.controller;

import com.jeecg.basstudent.entity.BasStudentTreeEntity;
import com.jeecg.basstudent.service.BasStudentTreeServiceI;

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
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
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

/**
 * @Title: Controller
 * @Description: 学生资料
 * @author onlineGenerator
 * @date 2018-10-27 23:26:58
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/basStudentTreeController")
public class BasStudentTreeController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BasStudentTreeController.class);

	@Autowired
	private BasStudentTreeServiceI basStudentTreeService;
	@Autowired
	private SystemService systemService;

	/**
	 * 学生资料列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/jeecg/basstudent/basStudentTreeList");
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
	public void datagrid(BasStudentTreeEntity basStudentTree, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BasStudentTreeEntity.class, dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, basStudentTree,
				request.getParameterMap());
		try {
			// 自定义追加查询条件

		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.basStudentTreeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除学生资料
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(BasStudentTreeEntity basStudentTree, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		basStudentTree = systemService.getEntity(BasStudentTreeEntity.class, basStudentTree.getId());
		message = "学生资料删除成功";
		try {
			basStudentTreeService.delete(basStudentTree);
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
				BasStudentTreeEntity basStudentTree = systemService.getEntity(BasStudentTreeEntity.class, id);
				basStudentTreeService.delete(basStudentTree);
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
	public AjaxJson doAdd(BasStudentTreeEntity basStudentTree, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "学生资料添加成功";
		try {
			basStudentTreeService.save(basStudentTree);
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
	public AjaxJson doUpdate(BasStudentTreeEntity basStudentTree, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "学生资料更新成功";
		BasStudentTreeEntity t = basStudentTreeService.get(BasStudentTreeEntity.class, basStudentTree.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(basStudentTree, t);
			basStudentTreeService.saveOrUpdate(t);
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
	public ModelAndView goAdd(BasStudentTreeEntity basStudentTree, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(basStudentTree.getId())) {
			basStudentTree = basStudentTreeService.getEntity(BasStudentTreeEntity.class, basStudentTree.getId());
			req.setAttribute("basStudentTree", basStudentTree);
		}
		return new ModelAndView("com/jeecg/basstudent/basStudentTree-add");
	}

	/**
	 * 学生资料编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(BasStudentTreeEntity basStudentTree, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(basStudentTree.getId())) {
			basStudentTree = basStudentTreeService.getEntity(BasStudentTreeEntity.class, basStudentTree.getId());
			req.setAttribute("basStudentTree", basStudentTree);
		}
		return new ModelAndView("com/jeecg/basstudent/basStudentTree-update");
	}

	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "basStudentTreeController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(BasStudentTreeEntity basStudentTree, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(BasStudentTreeEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, basStudentTree,
				request.getParameterMap());
		List<BasStudentTreeEntity> basStudentTrees = this.basStudentTreeService.getListByCriteriaQuery(cq, false);
		modelMap.put(NormalExcelConstants.FILE_NAME, "学生资料");
		modelMap.put(NormalExcelConstants.CLASS, BasStudentTreeEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,
				new ExportParams("学生资料列表", "导出人:" + ResourceUtil.getSessionUser().getRealName(), "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, basStudentTrees);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(BasStudentTreeEntity basStudentTree, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
		modelMap.put(NormalExcelConstants.FILE_NAME, "学生资料");
		modelMap.put(NormalExcelConstants.CLASS, BasStudentTreeEntity.class);
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
				List<BasStudentTreeEntity> listBasStudentTreeEntitys = ExcelImportUtil
						.importExcel(file.getInputStream(), BasStudentTreeEntity.class, params);
				for (BasStudentTreeEntity basStudentTree : listBasStudentTreeEntitys) {
					basStudentTreeService.save(basStudentTree);
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

	@RequestMapping(params = "getTreeBusLineData", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public AjaxJson getTreeBusLineData(TSDepart depatr, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
			StringBuffer sql = new StringBuffer(" SELECT bl.id,bl.bl_name FROM bas_line bl ");
			listTree = this.systemService.findForJdbc(sql.toString());// this.systemService.findHql(hql.toString());
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = null;
			for (Map<String, Object> o : listTree) {
				map = new HashMap<String, Object>();
				map.put("chkDisabled", false);
				map.put("click", false);
				map.put("id", o.get("id"));
				map.put("name", o.get("bl_name"));
				map.put("nocheck", false);
				map.put("struct", "TREE");
				map.put("title", o.get("bl_name"));
				map.put("parentId", "0");
				dataList.add(map);
			}

			List<Map<String, Object>> listTrees = new ArrayList<Map<String, Object>>();
			StringBuffer sqls = new StringBuffer(
					" SELECT bl.id,bl.bl_name,bs.id bs_id,bs.bs_name FROM bas_line bl,bas_size bs where bl.id=bs.fk_bl_id ");
			listTrees = this.systemService.findForJdbc(sqls.toString());// this.systemService.findHql(hql.toString());

			for (Map<String, Object> o : listTrees) {
				map = new HashMap<String, Object>();
				map.put("chkDisabled", false);
				map.put("click", true);
				map.put("id", o.get("bs_id"));
				map.put("name", o.get("bs_name"));
				map.put("nocheck", false);
				map.put("struct", "TREE");
				map.put("title", o.get("bs_name"));
				map.put("parentId", o.get("id"));
				dataList.add(map);
			}
			j.setObj(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j;
	}

}
