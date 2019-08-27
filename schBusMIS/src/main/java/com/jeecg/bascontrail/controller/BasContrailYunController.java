package com.jeecg.bascontrail.controller;
import com.jeecg.bascontrail.entity.BasContrailYunEntity;
import com.jeecg.bascontrail.service.BasContrailYunServiceI;
import com.jeecg.bascontrail.service.BusMapfenceServiceI;
import com.jeecg.basline.entity.BasSizeEntity;
import com.jeecg.basstudent.entity.BasStudentLocationEntity;
import com.jeecg.basstudentinfo.entity.BasStudentInfoEntity;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.DecimalFormat;
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
import org.jeewx.api.core.exception.WexinReqException;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import java.io.PrintWriter;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
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

	public int pub_IndexCmd;
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
	@RequestMapping(params = "pushMessage")  
    public void pushMessage(HttpServletResponse response,HttpServletRequest request){
            try {
                //最后一次接收到的事件的标识符
                String last = request.getHeader("Last-Event-ID");
                //logger.info(last);
                response.setContentType("text/event-stream");
                response.setCharacterEncoding("utf-8");
                PrintWriter out = response.getWriter();
                
                /*获取刷卡数据*/
                BasStudentInfoEntity o=new BasStudentInfoEntity();
        		for(BasStudentInfoEntity be:BusMapfenceController.listBs)
        		{
        			try {
        				MyBeanUtils.copyBeanNotNull2Bean(be, o);
        				BusMapfenceController.listBs.remove(be);
        				break;
        			} catch (Exception e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        		}
                if(!(o==null || o.getBsDesc()==""||o.getBsDesc()==null))
                out.println("data:"+JSONObject.fromObject(o).toString());
                out.println("event:message");
                //声明浏览器在连接断开之后进行再次连接之前的等待时间 1秒
                out.println("retry:1000");
                //事件的标识符
                out.println("id:"+System.currentTimeMillis());
                out.println();
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
	
	/*门闸相关
	 * @author:dev
	 * @date:20190802
	 * */
	//通讯握手(心调+任务)
	@RequestMapping(params = "getCommTask")	//getCommState
	@ResponseBody
	public JSONObject getCommTask(String Key,String IndexCmd,String CmdOK,String MAC,HttpServletRequest request, HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		String strRet="";
		int intRan=this.getRandom(5);
		String CmdValue="02002CFF010000D003";		//开门指令
		
		//ini
		if(Key==null||Key.equals("")){
			Key="0";	
			strRet="{\"Key\":\""+Key+"\"}";			
		}
		if(CmdOK==null||CmdOK.equals("")){
			CmdOK="0";	
		}
		//ini end
		
		//获取学生卡号和卡序号
		String cardno=this.getDoorData(MAC);
		int seq=getDoorMaxSeq(MAC);
		System.out.println("获取得卡号:"+cardno+";序号:"+seq);
		if(cardno.equals("0")){
			strRet=	"{\"Key\":\""+Key+"\",\"IndexCmd\":\"0\"}";	//结束
		}
		else{	
			int i=iDoorData(MAC,cardno,seq);
			String stip="";
			if (i==1){
				stip="写入门禁表成功:MAC:"+MAC+";cardno:"+cardno+";seq:"+seq;
			}else{
				stip="写入门禁表失败:MAC:"+MAC+";cardno:"+cardno+";seq:"+seq;
			}
			System.out.println(stip);
			
			//白名单指令
			CmdValue=this.creWhiteNameCmd(cardno,"",seq);		//test cardno:161234567890006
			//CmdValue=this.creDelAllCardCmd();		
			//System.out.println("CmdValue:"+CmdValue);
			
			//第一次  返回任务
			if(IndexCmd==null||IndexCmd.equals("")){
				strRet=	"{\"Key\":\""+Key+"\",\"IndexCmd\":\""+intRan+"\",\"CmdValue\":\""+CmdValue+"\"}";
				//System.out.println("IndexCmd(1nd):"+IndexCmd);
			}//非第一次
			else {	
				//System.out.println("pub_IndexCmd:"+pub_IndexCmd);
				if(CmdOK.equals("1")&(IndexCmd.equals(String.valueOf(pub_IndexCmd)))){
					//System.out.println("IndexCmd:"+intRan);
					strRet=	"{\"Key\":\""+Key+"\",\"IndexCmd\":\""+intRan+"\",\"CmdValue\":\""+CmdValue+"\"}";
				}else{
					strRet=	"{\"Key\":\""+Key+"\",\"IndexCmd\":\"0\"}";	//结束
				}								
			}
		}

		JSONObject json = JSONObject.fromObject(strRet); 		
		System.out.println("getCommTask:"+json);	
		pub_IndexCmd=intRan;
		return json;		
	};
		

	//生成白名单指令
	private String creWhiteNameCmd(String cardno,String personName,int seq){
		String sRet="";
		
		//包头 02 00 C1 FF 00 3F 00
		String beg_stx="02";			//开始位 16进制(占1个字节)
		String beg_radn="00";			//随机数	
		String beg_command="C1";		//指令	增加一张卡
		String beg_address="FF";		//地址
		String beg_door="00";			//门编号
		String beg_lengthL="3F";		//数据长度低位 63个字节
		String beg_lengthH="00";		//数据长度高位	
		String begData=beg_stx+beg_radn+beg_command+beg_address+beg_door+beg_lengthL+beg_lengthH;	
		
		//数据datas
		//String[] strArray=new String[63];
		//卡编号 3字节  随机生成3位数字-->取最大数
		int icarOID=seq;//this.getRandom(3);
		String carOID=intToHex(icarOID);
		carOID=addZeroForNum(carOID,6,"R");
		//System.out.println("icarOID:"+icarOID+";转换后:"+carOID);
		//String carOID="000000";
		
		//卡号 4字节
		//String cardNo="40E20100";   //1234(0x4D2)->D2 04 00 00
		int icardNum=this.getRandom(4);
		String cardNum=intToHex(icardNum);
		cardNum=addZeroForNum(cardNum,4,"L");	//前面补0
		String cardNum1=cardNum.substring(0,2);
		String cardNum2=cardNum.substring(2,4);
		cardNum=cardNum2+cardNum1;				//低位在前
		cardNum=addZeroForNum(cardNum,8,"R");
		//System.out.println("icardNum:"+icardNum+";转换后:"+cardNum);
		
		//二维码 9字节
		//String personNo1="313631323334353637";						//161234567??
		//161234567 890006
		//System.out.println("cardno:"+cardno);
		String personNo1=stringToAsciiHex(cardno.substring(0,9));
		//System.out.println("personNo1:"+personNo1+";"+cardno.substring(0,9));
		//身份证 18字节
		//String personNo2="383930303036303030303030303030303030";	//890006000000000000??
		//String personNo2=stringToAsciiHex("890006000000000000");
		//System.out.println("personNo2:"+cardno.substring(9,15));
		String personNo2=stringToAsciiHex(cardno.substring(9,15)+"000000000000");
		//System.out.println("personNo2:"+personNo2+";"+cardno.substring(9,15)+"000000000000");
		//密码 4字节
		String password="87654321";
		//门权限 2字节
		String doorRig="0100";
		//有效期 5字节
		String begDate="0A01010000";
		//有效期 5字节
		String endDate="1E0717173B";
		//备用 5字节
		String bakstr="0000000000";
		//姓名 8字节
		String perName="B2E2CAD4BFA84300";							//-->测试卡C
		String datas=carOID+cardNum+personNo1+personNo2+password+doorRig+begDate+endDate+bakstr+perName;
				
		//包尾
		String CS=verificationData(begData+datas);
		//System.out.println("CS:"+CS);
		
		String endStr="03";
		String endData=CS+endStr;	
		
		sRet=begData+datas+endData;
		return sRet;
	}

	
	//取学生卡信息
	private String getDoorData(String macno){		
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		String cardno="0";
		StringBuffer sql = new StringBuffer("select bs_cardno from bas_student ");
		sql.append("where bs_cardno not in (select bs_cardno from bas_studentdoorinfo where bs_macno='"+macno+"') ");
		sql.append("order by create_date LIMIT 1");
		System.out.println("getDoorData sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		if (listTree.size()==0){
			cardno="0";
		}else{
			cardno = listTree.get(0).get("bs_cardno").toString();
		}
		
		return cardno;

	}	
	//新增学生门禁信息
	private int iDoorData(String macno,String cardno,int seq){
		int sc=0;
		
		//是否有效
		if (isExist(cardno,macno)<1){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			String sysdt = df.format(new Date());// new Date()为获取当前系统时间
			
			UUID OID = UUID.randomUUID();
			StringBuffer sql = new StringBuffer(
					"INSERT INTO `bas_studentdoorinfo` (`id`, `bs_cardno`, `bs_macno`, `bs_state`,`create_date`,`create_date`) ");
			sql.append("VALUES ('" + OID + "','" + cardno + "','"+macno+"','1' ,'" + sysdt + "',"+seq+")");
	
			//System.out.println("iDoorData sql..." + ";" + sql.toString());
			sc =this.systemService.executeSql(sql.toString());			
			
		}else
		{
			sc=0;
		}
		return sc;

	}
	//是否已有数据
	private int isExist(String cardno,String macno){
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("select count(bs_cardno) as c from bas_studentdoorinfo ");
		sql.append(" where bs_cardno='" + cardno + "' and bs_macno='"+macno+"'");
		//System.out.println("isExist sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		String sc = listTree.get(0).get("c").toString();
		int isc = Integer.parseInt(sc);
		return isc;				
	}
	
	private int getDoorMaxSeq(String macno){
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		String seq="0";
		StringBuffer sql = new StringBuffer("SELECT IFNULL(MAX(bs_seq),0)+1 as seq from  bas_studentdoorinfo  ");
		sql.append("where bs_macno='"+macno+"') ");
		System.out.println("getDoorSeq sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		seq = listTree.get(0).get("seq").toString();
		int isc = Integer.parseInt(seq);
		return isc;		
		
	}
	
	
	
	private static String stringToAsciiHex(String value) {
		String ss="";
		for (   int i=0;i<value.length();i++  )
        {
			String s1=value.substring(i,i+1);
			String s=stringToAscii(s1);
			int k=Integer.valueOf(s);
			ss=ss+intToHex(k);			
        }
		
		return ss;
	}
	
	private static String stringToAscii(String value)  
	{  
	    StringBuffer sbu = new StringBuffer();  
	    char[] chars = value.toCharArray();   
	    for (int i = 0; i < chars.length; i++) {  
	        if(i != chars.length - 1)  
	        {  
	            sbu.append((int)chars[i]).append(",");  
	        }  
	        else {  
	            sbu.append((int)chars[i]);  
	        }  
	    }  	
	    return sbu.toString();  
	}  
    private static String intToHex(int n) {
        StringBuffer s = new StringBuffer();
        String a;
        char []b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        while(n != 0){
            s = s.append(b[n%16]);
            n = n/16;            
        }
        a = s.reverse().toString();
        return a;
    }
    
    private static String verificationData(String para){
 		 int length = para.length() / 2;
 		 String[] dateArr = new String[length];
 		 try {
 			 for (int k = 0; k < length; k++){
 				 dateArr[k]=para.substring(k*2, k*2+2);
 			 }	 
 			 
 		} catch (Exception e) {
 			// TODO: handle exception
 		}
 		 
 		String code = "";
 		for (int i = 0; i < dateArr.length-1; i++) {
 			if(i == 0){
 				code = xorString(dateArr[i], dateArr[i+1]);
 			}else{
 				code = xorString(code, dateArr[i]);
 			}
 		}
 		return code.toUpperCase();
     }

	private static String xorString(String strHex_X,String strHex_Y){ 
		//将x、y转成二进制形式 
		String anotherBinary=Integer.toBinaryString(Integer.valueOf(strHex_X,16)); 
		String thisBinary=Integer.toBinaryString(Integer.valueOf(strHex_Y,16)); 
		String result = ""; 
		//判断是否为8位二进制，否则左补零 
		if(anotherBinary.length() != 8){ 
		for (int i = anotherBinary.length(); i <8; i++) { 
				anotherBinary = "0"+anotherBinary; 
			} 
		} 
		if(thisBinary.length() != 8){ 
		for (int i = thisBinary.length(); i <8; i++) { 
				thisBinary = "0"+thisBinary; 
			} 
		} 
		//异或运算 
		for(int i=0;i<anotherBinary.length();i++){ 
		//如果相同位置数相同，则补0，否则补1 
				if(thisBinary.charAt(i)==anotherBinary.charAt(i)) 
					result+="0"; 
				else{ 
					result+="1"; 
				} 
			}
		//Log.e("code",result);
		return Integer.toHexString(Integer.parseInt(result, 2)); 
	}
	
	private static String addZeroForNum(String str, int strLength,String pos) {
		int strLen = str.length();
		StringBuffer sb = null;
		while (strLen < strLength) {
			sb = new StringBuffer();
			if (pos.equals("L")){
				sb.append("0").append(str);// 左补0
			}else{
				sb.append(str).append("0");//右补0
			}
			
			str = sb.toString();
			strLen = str.length();
		}
		return str;
	}
	
	//获取随机数
	private int getRandom(int len){
/*        SimpleDateFormat simpleDateFormat;
        simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        String date=simpleDateFormat.format(new Date());*/
        Random random=new Random();
        int rannum=0;
        if(len==5){
        	rannum= (int)(random.nextDouble()*(99999-10000 + 1))+ 10000;
        }else if(len==3){
        	rannum= (int)(random.nextDouble()*(999-100 + 1))+ 100;
        }else if(len==4){
        	rannum= (int)(random.nextDouble()*(9999-1000 + 1))+ 1000;
        }
        //System.out.println("getRandom:"+rannum);
        //String rand=rannum;
      return rannum;
	}
	
	//open door test
/*	private JSONObject openDoor(String Key){		
		if(Key==null||Key.equals("")){
			Key="0";
		}		
		String str = "{\"Key\":\""+Key+"\",\"IndexCmd\":\"25874\",\"CmdValue\":\"02002CFF010000D003\"}";			
		
		JSONObject json = JSONObject.fromObject(str); 
		
		System.out.println("openDoor:"+json);		
		return json;		
	};	*/
	/*//白名单任务
	@RequestMapping(params = "getAddCardTask")
	@ResponseBody
	public JSONObject getAddCardTask(String key,String macID,String indexCMD,int cmdOK,HttpServletRequest request, HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		
		String retStr="";
		String CmdValue="";
		String begData="";	//包头
		String datas="";	//数据
		String endData="";	//包尾
		if(key==null||key.equals("")){
			key="0";
		}	
		if(indexCMD==null||indexCMD.equals("")){
			indexCMD="0";
		}else{
			//获取学生卡号
			String cardno=this.getDoorData(macID);
			//System.out.println("获取得卡号:"+cardno);	
			int i=iDoorData(macID,cardno);
			String stip="";
			if (i==1){
				stip="写入门禁表成功:macID:"+macID+";cardno:"+cardno;
			}else{
				stip="写入门禁表失败:macID:"+macID+";cardno:"+cardno;
			}
			System.out.println(stip);
		}	
		
		//包头 02 00 C1 FF 00 3F 00
		String beg_stx="02";		//开始位 16进制(占1个字节)
		String beg_radn="00";		//随机数	
		String beg_command="C1";	//指令	增加一张卡
		String beg_address="FF";	//地址
		String beg_door="00";		//门编号
		String beg_lengthL="3F";		//数据长度低位
		String beg_lengthH="00";		//数据长度高位	
		begData=beg_stx+beg_radn+beg_command+beg_address+beg_door+beg_lengthL+beg_lengthH;		
		
		//数据datas
		//String[] strArray=new String[63];
		//卡编号 3字节
		String carOID="000000";
		//卡号 4字节
		String cardNo="40E20100";
		//二维码 9字节
		String personNo1="313631323334353637";						//161234567
		//身份证 18字节
		String personNo2="383930303036303030303030303030303030";	//890006000000000000
		//密码 4字节
		String password="87654321";
		//门权限 2字节
		String doorRig="0100";
		//有效期 5字节
		String begDate="0A01010000";
		//有效期 5字节
		String endDate="1E0717173B";
		//备用 5字节
		String bakstr="0000000000";
		//姓名 8字节
		String perName="B2E2CAD4BFA84300";
		datas=carOID+cardNo+personNo1+personNo2+password+doorRig+begDate+endDate+bakstr+perName;
				
		//包尾
		String CS="23";					//xor
		String endStr="03";
		endData=CS+endStr;
		
		CmdValue="";
		if (cmdOK==1){
			datas=begData+datas+endData;		 
		}else{
			datas="0";
		}
		retStr ="{\"key\":\""+key+"\",\"indexCMD\":\""+indexCMD+"\",\"CmdValue\":\""+datas+"\"}";
		
		JSONObject json = JSONObject.fromObject(retStr); 
		
		System.out.println("getAddCardTask:"+json);		
		return json;
	}*/
	
	//请求(控制器向服务器请求) -->busMapfenceController.do?PostData
/*	@RequestMapping(params = "getReqData")
	@ResponseBody
	public JSONObject getReqData(String type,String IndexEvent,String IndexAlarm,HttpServletRequest request, HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		String str="";
		System.out.println("请求类型:"+type);
		
		if(type==null||type.equals("")){
			type="0";
		}
		if(IndexEvent==null||IndexEvent.equals("")){
			IndexEvent="0";
		}
		if(IndexAlarm==null||IndexAlarm.equals("")){
			IndexAlarm="0";
		}
		
		if (type.equals("100")){
			str ="{\"IndexEvent\":\""+IndexEvent+"\"}"; 
		}else if(type.equals("101")){
        	str ="{\"IndexAlarm\":\""+IndexAlarm+"\"}"; 
		}
		//type=0/1/9 开门
		else if(type.equals("0")){
        	str ="{\"ActIndex\":\"0\",\"AcsRes\":\"1\",\"Time\":\"1\"}"; 
		}
		else if(type.equals("1")){
        	str ="{\"ActIndex\":\"0\",\"AcsRes\":\"1\",\"Time\":\"1\"}"; 
		}
		else if(type.equals("9")){
        	str ="{\"ActIndex\":\"0\",\"AcsRes\":\"1\",\"Time\":\"1\"}"; 
		}
		
		JSONObject json = JSONObject.fromObject(str); 
		
		System.out.println("getReqData:"+json);		
		return json;		
	};*/
	
	//生成删除所有卡指令
	/*private String creDelAllCardCmd(){
		String sRet="";
		
		//包头 02 00 C1 FF 00 3F 00
		String beg_stx="02";			//开始位 16进制(占1个字节)
		String beg_radn="00";			//随机数	
		String beg_command="17";		//指令	增加一张卡
		String beg_address="FF";		//地址
		String beg_door="00";			//门编号
		String beg_lengthL="09";		//数据长度低位 63个字节
		String beg_lengthH="00";		//数据长度高位	
		String begData=beg_stx+beg_radn+beg_command+beg_address+beg_door+beg_lengthL+beg_lengthH;	
		

				
		//包尾
		String CS=verificationData(begData);
		//System.out.println("CS:"+CS);
		
		String endStr="03";
		String endData=CS+endStr;	
		
		sRet=begData+endData;
		return sRet;
	}*/
	
}
