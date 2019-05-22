/**
 * 
 */
package com.jeecg.basstudent.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.web.system.service.SystemService;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.model.message.TemplateData;
import org.jeewx.api.core.req.model.message.TemplateMessageSendResult;
import org.jeewx.api.wxbase.wxtoken.JwTokenAPI;
import org.jeewx.api.wxsendmsg.JwSendTemplateMsgAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jeecg.basstudent.entity.ApiV3Sms;
import com.jeecg.basstudent.entity.ApiV3SmsEntity;
import com.jeecg.basstudent.entity.WeixinUserInfo;
import com.jeecg.basstudent.entity.wxutils;
import com.sun.star.awt.Size;

import net.sf.json.JSONObject;

/**
 * @author dev001
 *
 */

@Controller
@RequestMapping("/baswxcontroller")
public class basWXController extends BaseController {
	
/*	private static final String Templateid_UP="J_1DwXtF8IorKSmpxR3I2v_kb-rLykW4kb8-7E0RQpw";
	private static final String Templateid_LO="SYi3dsdmvq7CUw3M3E0Mfl63xLl7wAo-4oJY5v126O0";*/
	
	/*private static final String Templateid_WR="adEMn2SjI_B3R_ckc9oqfDR7DPQ_t7znxiJxi9pSmoA";
	private static final String Templateid_NextUp="uocevTOp3GEooEc6AK0Me1Fk1shv4y8Uk0Gn5lIi9f8";
	private static final String Templateid_NextDw="7gNUN4ACrn0bogzgtnSQHWfjTMvCR57J7iaR05_OnqU";
	private static final String Templateid_SK="2BqUTvHUOZreolc2SDtFRpF6ESbIqjObH2OvVO6QDKc";
	private static final String Templateid_QryBusLoc="7_gJwIOoSclWvtUsgFTkTtGSHO-zuXHeO3978m2bPoA";
	private static final String Templateid_leave="2FADaZxNLxG8U4eCHt70tBTBDKuRryTo-L06AsJn4A8";
	//超出围栏预警
	public static final String Templateid_notice="MNOe2kDuz6N1D55fdpCgX-MWepR_thv3SR7R6MF0hXg";*/
	
	//高测试号
/*	private static final String Templateid_WR="jRs79YzKAdsdQk4javBgVENDvzgzSyPZRhzbQupodv8";
	private static final String Templateid_NextUp="J9jGet_IXoPXpcJtM3-IB4hudBG22hDvHdKR2Z1_cSg";
	private static final String Templateid_NextDw="gyPNPSmqQr1SjeUND1z2fUmcO2HX7bUZdj42yt0R6-U";
	private static final String Templateid_SK="U6JsaXb6eFLhib_53OgVpKuRiklQRUF_QU0O8jpaf0A";
	private static final String Templateid_QryBusLoc="hNzDLlteTH5pNsOR5ucMmk88quySmpePoEfRIrPPvQo";
	private static final String Templateid_leave="6We64z7RgzgrQpxmpAhityw5x2UJWxHQQgqjHQXXN4c";
	//超出围栏预警
	public static final String Templateid_notice="MK1v3CKZeQALOMOF2ugq3YnbWXhdfc80q1m6hFuboUc";
	//设备异常
	public static final String Templateid_warn="k3NZBmCE8MvqLfFCNlBtLeaFK_EJ8Qa6bLoQmS0LeOA";*/
	
	//ssby
	private static final String Templateid_WR="k82RguiMM9EvyKiwrbbHV3enQuXHdkhRSngd4g_j8_o";		//未刷卡通知
	private static final String Templateid_NextUp="M2EnRwvA_4VzfZK3CHfWD8RV-ErhjNUmAtooXDww--c";	//学生上车提醒ok
	private static final String Templateid_NextDw="innFPadd9YHWyLZGlzGTxKavHouHP4OiWPlURI7z910";	//学生下车提醒ok
	private static final String Templateid_SK="cmd-JJSPBxdyuyasLi2ZP3EV6LMB31xiHhGG3xtUbZg";		//学生刷卡通知ok
	private static final String Templateid_QryBusLoc="hNzDLlteTH5pNsOR5ucMmk88quySmpePoEfRIrPPvQo";	//车辆位置(X)
	private static final String Templateid_leave="iARE4zwVtsh3GojcPVDm9Y02BlLUOCIXIr5JzuHeQc8";		//学生请假提醒ok
	public static final String Templateid_notice="MK1v3CKZeQALOMOF2ugq3YnbWXhdfc80q1m6hFuboUc";		//超出围栏预警
	public static final String Templateid_warn="k3NZBmCE8MvqLfFCNlBtLeaFK_EJ8Qa6bLoQmS0LeOA";		//设备异常
	@Autowired
	private SystemService systemService;
	
	
	//接入检测
	@RequestMapping(params = "connect")
	@ResponseBody             
	public void connectWeixin(HttpServletRequest req, HttpServletResponse resp) throws IOException, WexinReqException{
        String signature=req.getParameter("signature");
        String timestamp=req.getParameter("timestamp");
        String nonce=req.getParameter("nonce");
        String echostr=req.getParameter("echostr");
        //System.out.println("-->"+signature+";"+timestamp+";"+nonce+";"+echostr);
        PrintWriter out = resp.getWriter();
        if (wxutils.checkSignature(signature, timestamp, nonce)) {
            out.println(echostr);
            System.out.println("接入成功!");
        }else{ 	
        	System.out.println("接入失败!");
        }
    }
	
	//刷卡通知
	@RequestMapping(params = "doSendTMessage_SK")
	@ResponseBody
	private int doSendTMessage_SK(String id,String sizeoid) throws WexinReqException {
		System.out.println("doSendTMessage_SK begging...");
		
		//response.addHeader("Access-Control-Allow-Origin", "*");
		//response.setCharacterEncoding("utf-8");
		
		//根据站点oid判断是上车线路还是下车线路？1:上学;2:放学
		int lt=getSizeIsUporDown(sizeoid);
		String place="";
		if (lt==1){
			place="b.bl_name,bl_size";
		}else if(lt==2){
			place="b.bl_name1,bl_size1";
		}else{
			//
		}
		//根据站点oid判断是否是终点下车(起点上车)还是普通站点 	0普通站点;1上学下车/放学上车
		int lt1=getSizeIsBorE(sizeoid);
		String sizename="";
		if (lt1==0){
			sizename=getLineSizeName(sizeoid);
		}
		
		int ri=0;
		String accessToken=wxutils.getAcctonken();
		TemplateMessageSendResult msgSend = new TemplateMessageSendResult();
		
		String message = null;
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();

		//getData	
		
		StringBuffer sql = new StringBuffer("SELECT a.id, b.bs_name,DATE_FORMAT(a.bc_datetime,'%Y-%m-%d %H:%i:%s')as bc_datetime,CONCAT("+place+")as place ,c.bo_openid,");
		sql.append("c.bo_openid,case when b.bs_sex='1' then '女' when b.bs_sex='0' then '男'  else '--' END as sex ,CONCAT(b.bc_grade,b.bc_name)as classname from bus_cardinfo a ");
		sql.append("left join bas_student b on a.bc_cardno=b.bs_cardno ");
		sql.append("left join bus_openid c on b.id=c.bs_studentid ");
		sql.append("Where c.bo_openid is not NULL AND a.id='"+id+"' ");
		System.out.println("getData sql..."+";"+sql.toString());
		
		listTree = this.systemService.findForJdbc(sql.toString());// this.systemService.findHql(hql.toString());
			
		for (Map<String, Object> o : listTree) {			
			Map<String, TemplateData> data = new HashMap<String, TemplateData>();
			data.put("first", new TemplateData("尊敬的家长，你的小孩已刷卡。","#173177"));
			data.put("keyword1", new TemplateData(o.get("bs_name").toString(),"#FF0000"));
			data.put("keyword5", new TemplateData(o.get("bc_datetime").toString(),"#173177"));
			data.put("keyword2", new TemplateData(o.get("sex").toString(),"#173177"));
			data.put("keyword3", new TemplateData(o.get("classname").toString(),"#173177"));
			if(lt1==0){
				//data.put("keyword3", new TemplateData(o.get("place").toString(),"#173177"));
				data.put("keyword4", new TemplateData(sizename,"#173177"));	//实际刷卡地点
			}else if(lt1==1){
				if(lt==1){
					data.put("keyword4", new TemplateData("上学终点下车","#173177"));
				}else if(lt==2){
					data.put("keyword4", new TemplateData("放学起点上车","#173177"));
				}else{
					data.put("keyword4", new TemplateData("--","#173177"));
				}
			}else{
				data.put("keyword3", new TemplateData("--","#173177"));
			}
			//data.put("remark", new TemplateData("点击详情，可查看照片！","#173177"));
			msgSend.setTemplate_id(Templateid_SK);
			msgSend.setTouser(o.get("bo_openid").toString());
			//msgSend.setUrl(wxutils.basurl+"/photos/"+o.get("id").toString()+".png");
			msgSend.setData(data);
			try {
				JwSendTemplateMsgAPI.sendTemplateMsg(accessToken, msgSend);
				message = "发送消息模板成功";
				ri=1;
			} catch (WexinReqException e) {
				message = "发送消息模板失败";
				ri=0;
				e.printStackTrace();
			}
		}

		//j.setMsg(message);
		System.out.println("wxoputing..."+message+";"+accessToken);
		return ri;
	}	
	

	
	//未上车提醒-->未刷卡通知
	@RequestMapping(params = "doSendTMessage_WR")
	@ResponseBody
	public int doSendTMessage_WR(String id,String sizeoid,HttpServletRequest request,HttpServletResponse response) throws WexinReqException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		//System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		int lt=getSizeIsUporDown(sizeoid);
		String place="";
		if (lt==1){
			place="b.bl_name,bl_size";
		}else if(lt==2){
			place="b.bl_name1,bl_size1";
		}else{
			//
		}	
		//根据站点oid判断是否是终点下车(起点上车)还是普通站点 	0普通站点;1上学下车/放学上车
		int lt1=getSizeIsBorE(sizeoid);
				
		int ri=0;
		String accessToken=wxutils.getAcctonken();
		TemplateMessageSendResult msgSend = new TemplateMessageSendResult();
		
		String message = null;
		//AjaxJson j = new AjaxJson();
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();

		//getData				
		StringBuffer sql = new StringBuffer("SELECT b.id, b.bs_name,CONCAT("+place+")as place ,c.bo_openid,'尊敬的家长，您的小孩未刷卡。'as msg from bas_student b ");
		sql.append("left join bus_openid c on b.id=c.bs_studentid ");
		sql.append("Where c.bo_openid is not NULL AND c.bo_openid<>'' ");
		sql.append("AND b.id='"+id+"' ");
		sql.append("UNION ");
		sql.append(" SELECT b.id, b.bs_name,CONCAT(b.bl_name,bl_size)as place,d.openid as bo_openid,'尊敬的班主任，您班的学生未刷卡。'as msg from bas_student b ");
		sql.append(" LEFT JOIN bas_class c on c.id=b.bc_id ");
		sql.append(" LEFT JOIN t_s_base_user d on d.id=c.bc_personid ");
		sql.append(" Where d.openid is not NULL AND d.openid<>'' ");
		sql.append("AND b.id='"+id+"' ");
		System.out.println("getDate sql..."+";"+sql.toString());
		
		listTree = this.systemService.findForJdbc(sql.toString());// this.systemService.findHql(hql.toString());
			
		for (Map<String, Object> o : listTree) {			
			Map<String, TemplateData> data = new HashMap<String, TemplateData>();
			//data.put("first", new TemplateData(o.get("msg").toString() ,"#173177"));
			data.put("keyword1", new TemplateData(o.get("bs_name").toString(),"#FF0000"));
			data.put("keyword2", new TemplateData("狮山博雅学校","#FF0000"));
			data.put("keyword3", new TemplateData(df.format(new Date()),"#173177"));				//当前时间
			if(lt1==0){
				data.put("first", new TemplateData(o.get("msg").toString()+"_"+o.get("place").toString(),"#173177"));
			}else if(lt1==1){
				if(lt==1){
					data.put("first", new TemplateData(o.get("msg").toString()+"_上学终点下车","#173177"));
				}else if(lt==2){
					data.put("first", new TemplateData(o.get("msg").toString()+"_放学起点上车","#173177"));
				}else{
					data.put("first", new TemplateData(o.get("msg").toString(),"#173177"));
				}
			}else{
				data.put("first", new TemplateData(o.get("msg").toString(),"#173177"));
			}
			
			data.put("remark", new TemplateData("以上信息，特警示！","#173177"));
			msgSend.setTemplate_id(Templateid_WR);
			msgSend.setTouser(o.get("bo_openid").toString());

			msgSend.setData(data);
			try {
				JwSendTemplateMsgAPI.sendTemplateMsg(accessToken, msgSend);
				//issendcard(o.get("id").toString());
				message = "发送消息模板成功";
				ri=1;
			} catch (WexinReqException e) {
				message = "发送消息模板失败";
				ri=-1;
				e.printStackTrace();
			}
		}

		//j.setMsg(message);
		System.out.println("wxoputing..."+message+";"+accessToken);
		return ri;
	}	
	
	//下一站上车提醒-->上、接车准备提醒
	@RequestMapping(params = "doSendTMessage_NextUp")
	@ResponseBody
	public int doSendTMessage_NextUp(String sizeoid,HttpServletRequest request,HttpServletResponse response) throws WexinReqException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		//System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		int ri=0;
		
		//根据站点oid判断是上车线路还是下车线路？1:上学;2:放学
		int lt=getSizeIsUporDown(sizeoid);	
		String place="";
		String place1="";
		String templateidType="";
		String msg="";
		if (lt==1){
			place="a.bl_name,bl_size";
			place1="a.bl_sizeid";
			templateidType=Templateid_NextUp;
			msg="请做好上车的准备";
		}else if(lt==2){
			place="a.bl_name1,bl_size1";
			place1="a.bl_sizeid1";
			templateidType=Templateid_NextDw;
			msg="请做好接车的准备";
		}else{
			//
		}	
		//根据站点oid判断是否是终点下车(起点上车)还是普通站点 	0普通站点;1上学下车/放学上车
		int lt1=getSizeIsBorE(sizeoid);
		if((lt!=1)&&(lt1!=0)){
			//放学+下车不发送信息
			System.out.println("doSendTMessage_NextUp...未发送");
		}else{	
			
			String accessToken=wxutils.getAcctonken();
			TemplateMessageSendResult msgSend = new TemplateMessageSendResult();
			
			String message = null;
			//AjaxJson j = new AjaxJson();
			List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
	
			//getData				
			StringBuffer sql = new StringBuffer("SELECT a.id,a.bs_name,CONCAT("+place+")as place,b.bo_openid from bas_student a ");
			sql.append("left join bus_openid b on a.id=b.bs_studentid ");
			sql.append("LEFT JOIN bus_leave c ON a.id=c.bl_studentid ");
			sql.append(" AND (to_days(c.bl_begdate) != to_days(now()) and bl_linetype!="+lt+") ");
			//sql.append("AND c.bl_begdate<=date_add(sysdate(), interval 1 hour) AND c.bl_begdate>=date_sub(sysdate(), interval 1 hour)  ");	
			sql.append("WHERE "+place1+"='" + sizeoid + "' ");
			sql.append("AND a.bs_cardno is not NULL and c.bl_begdate is NULL AND b.bo_openid is not NULL ");
			sql.append("ORDER BY id ");
			System.out.println("doSendTMessage_NextUp sql..."+";"+sql.toString());
			
			listTree = this.systemService.findForJdbc(sql.toString());// this.systemService.findHql(hql.toString());
				
			for (Map<String, Object> o : listTree) {			
				Map<String, TemplateData> data = new HashMap<String, TemplateData>();
				data.put("first", new TemplateData("尊敬的家长，我们的车即将到达["+o.get("place").toString()+"]，"+msg,"#173177"));
				data.put("keyword1", new TemplateData(o.get("bs_name").toString(),"#FF0000"));
				data.put("keyword2", new TemplateData(df.format(new Date()),"#173177"));    //通知时间
				data.put("keyword3", new TemplateData(o.get("place").toString(),"#173177"));    //地点
				data.put("keyword4", new TemplateData("---","#173177"));    //随车老师
				if (templateidType.equals("Templateid_NextUp")){
					data.put("keyword5", new TemplateData("---","#173177"));    //车牌
				}
				data.put("remark", new TemplateData("以上信息，特提醒！","#173177"));
				msgSend.setTemplate_id(templateidType);
				msgSend.setTouser(o.get("bo_openid").toString());
	
				msgSend.setData(data);
				try {
					JwSendTemplateMsgAPI.sendTemplateMsg(accessToken, msgSend);
					//issendcard(o.get("id").toString());
					message = "发送消息模板成功";
					ri++;
				} catch (WexinReqException e) {
					message = "发送消息模板失败";
					ri--;
					e.printStackTrace();
				}
				
			}
			System.out.println("doSendTMessage_NextUp..."+message+";"+accessToken);
		}
		
		//j.setMsg(message);
		
		return ri;
	}	

	//车辆实时位置查询
	@RequestMapping(params = "doSendTMessage_QryBusLoc")
	@ResponseBody
	private void doSendTMessage_QryBusLoc(String msg,String lineName,String sizeName,String reDatetime,String openid) throws WexinReqException {

		int ri=0;
		String accessToken=wxutils.getAcctonken();
		TemplateMessageSendResult msgSend = new TemplateMessageSendResult();
		
		String message = null;
		
		Map<String, TemplateData> data = new HashMap<String, TemplateData>();
		data.put("first", new TemplateData(msg,"#173177"));
		data.put("keyword1", new TemplateData(lineName,"#FF0000"));
		data.put("keyword2", new TemplateData(sizeName,"#FF0000"));    	
		data.put("keyword3", new TemplateData(reDatetime,"#173177"));    	
		data.put("remark", new TemplateData("以上信息，请知悉！","#173177"));
		msgSend.setTemplate_id(Templateid_QryBusLoc);
		msgSend.setTouser(openid);

		msgSend.setData(data);
		try {
			JwSendTemplateMsgAPI.sendTemplateMsg(accessToken, msgSend);
			message = "发送消息模板成功";
			ri++;
		} catch (WexinReqException e) {
			message = "发送消息模板失败";
			ri--;
			e.printStackTrace();
		}
			
		//j.setMsg(message);
		System.out.println("wxoputing..."+message+";"+accessToken);
		//return ri;
	}
	
	//请假信息推送 msg,className,studentName,leaveDate,openid
	@RequestMapping(params = "doSendTMessage_leave")
	@ResponseBody
	private void doSendTMessage_leave(String msg,String className,String studentName,String leaveDate,String openid) throws WexinReqException {
		String accessToken=wxutils.getAcctonken();
		TemplateMessageSendResult msgSend = new TemplateMessageSendResult();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String message = null;
		
		Map<String, TemplateData> data = new HashMap<String, TemplateData>();
		data.put("first", new TemplateData(msg,"#173177"));
		data.put("keyword2", new TemplateData(className,"#FF0000"));
		data.put("keyword3", new TemplateData(studentName,"#FF0000"));    	
		data.put("keyword4", new TemplateData(leaveDate,"#173177"));    	
		data.put("keyword1", new TemplateData(df.format(new Date()),"#173177"));				//提交时间
		data.put("remark", new TemplateData("以上信息，请知悉！","#173177"));
		msgSend.setTemplate_id(Templateid_leave);
		msgSend.setTouser(openid);

		msgSend.setData(data);
		try {
			JwSendTemplateMsgAPI.sendTemplateMsg(accessToken, msgSend);
			message = "发送消息模板成功";
		} catch (WexinReqException e) {
			message = "发送消息模板失败";
			e.printStackTrace();
		}
			
		//j.setMsg(message);
		System.out.println("doSendTMessage_leave..."+message+";"+accessToken);
		//return ri;
	}	
	
	
	
	
	
	//个人中心入口
	@RequestMapping(params = "gopenid")
	@ResponseBody
	public ModelAndView gopenid(String code,HttpServletRequest request) throws WexinReqException {
		//System.out.println("参数输出code-->"+code);
		String sopenid=wxutils.OAuthGetOpenid(code);
		//System.out.println("参数输出openid-->"+sopenid);
		String accessToken=wxutils.getAcctonken();
		//缺少个人信息获取
		WeixinUserInfo weixinUserInfo=getUserInfo(accessToken,sopenid);
		String jsonStr = null;
		try {
			JSONObject jsonObject = JSONObject.fromObject(weixinUserInfo);
			jsonStr = jsonObject.toString();
			jsonStr=java.net.URLEncoder.encode(jsonStr,"UTF-8");
			jsonStr="";
			System.out.println("个人信息:"+jsonStr);
		} catch (Exception e) {
			System.out.println("数组转json失败");
		}
	
		String url = "redirect:"+wxutils.basurl+"/page/index.html?openid="+sopenid+"&accessToken="+jsonStr; 
		System.out.println("url-->:"+url);
		return new ModelAndView(url);
	}	
	
	//binding
	//绑定
	@RequestMapping(params = "binding")
	@ResponseBody
	public int binding(String openid,String ruletype,HttpServletRequest request) throws WexinReqException {
		System.out.println("binding参数输出1:"+openid+";"+ruletype);
		int i=isBinded(openid,ruletype);
		System.out.println("binding参数输出2"+i);
		return i;
	}
	
	//unBinding
	//解除绑定
	@RequestMapping(params = "unBinding")
	@ResponseBody
	public int unBinding(String openid,String ruletype,HttpServletRequest request) throws WexinReqException {
		System.out.println("binding参数输出1:"+openid);
		int i=isUnBinded(openid,ruletype);
		System.out.println("binding参数输出2"+i);
		return i;
	}	
	
	//新增openid记录
	@RequestMapping(params = "insertopenid")
	@ResponseBody
	public int insertopenid(String tell, String openid, String ruletype, String tellcode, HttpServletRequest request)
			throws WexinReqException {
		System.out.println("insertopenid参数输出:" + tell + ";" + openid + ";" + ruletype + ";" + tellcode);
		int i = 0;
		if (isExitsTelCode(tell, tellcode) == 1) {
			if (isExitsTel(tell, Integer.parseInt(ruletype)) >= 1) {
				if (ruletype.equals("1")) {// 家长
					String[] stuids = new String[3];
					stuids = getStudenID(tell);
					for (int j = 0; j < stuids.length; j++) {
						if (!stuids[j].equals("0"))
							i = iopenid(stuids[j], openid);
					}
				} else if (ruletype.equals("2")) {
					i = uopenid(tell, openid);
				}
			} else { // 手机号码不存在
				i = 0;
			}
		} else {
			i = 2;
		}
		return i;
	}
	
	private static ApiV3Sms a3s=new ApiV3Sms();
	
	//新增openid记录
	@RequestMapping(params = "postSmsCode")
	@ResponseBody
	public int postSmsCode(String tell, String openid, HttpServletRequest request){
		System.out.println("postSmsCode参数输出:" + tell + ";" + openid);
		int i = 0;

		if (isExitsTelCode(tell, "") == 2) {
			i = 2;
		} else {
			if (isExitsTel(tell, 1) >= 1 || isExitsTel(tell, 2) >= 1) {
				a3s.send(tell);
				i = 1;
			} else { // 手机号码不存在
				i = 0;
			}
		}

		return i;
	}
	
	//获取个人信息
	@RequestMapping(params = "getStudent")
	@ResponseBody	
	public List<Map<String, Object>> getStudent(String openid,String ruletype,HttpServletRequest request){		
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		// getData
		StringBuffer sql = new StringBuffer();
		if(ruletype.equals("1")){
			sql.append("SELECT b.bs_name,b.bs_tel,b.bc_name FROM bus_openid a ");
			sql.append("LEFT JOIN bas_student b on a.bs_studentid=b.id ");
			sql.append("WHERE a.bo_openid='" + openid + "'");			
		}else if(ruletype.equals("2")){
			sql.append("SELECT bs.realname as bs_name,ts.mobilePhone as bs_tel,'' as bc_name FROM t_s_base_user bs,t_s_user ts where bs.ID=ts.id ");
			sql.append("and bs.openid='" + openid + "'");			
		}

		System.out.println("getStudent sql..." + ";" + sql.toString());
		listTree = this.systemService.findForJdbc(sql.toString());
		//System.out.println("getStudent:"+listTree.get(0).get("bs_name").toString()+";"+listTree.get(0).get("bs_tel").toString());
		return listTree;
	}
	
	//获取个人信息02(请假专用)
	@RequestMapping(params = "getStudent02")
	@ResponseBody		
	public List<Map<String, Object>> getStudent02(String openid,String ruletype,HttpServletRequest request){
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		// getData
		StringBuffer sql = new StringBuffer();
		if(ruletype.equals("1")){
			sql.append("SELECT b.id,b.bs_name FROM bus_openid a ");
			sql.append("LEFT JOIN bas_student b on a.bs_studentid=b.id ");
			sql.append("WHERE a.bo_openid='" + openid + "'");			
		}else if(ruletype.equals("2")){
			
		}

		System.out.println("getStudent02 sql..." + ";" + sql.toString());
		listTree = this.systemService.findForJdbc(sql.toString());
		//System.out.println("getStudent:"+listTree.get(0).get("bs_name").toString()+";"+listTree.get(0).get("bs_tel").toString());
		return listTree;
	}
	
	//获取刷卡记录?
	@RequestMapping(params = "getcardinfo")
	@ResponseBody		
	public int getcardinfo(String openid,String ruletype,HttpServletRequest request){
		//是否绑定？
		int i=isBinded(openid,ruletype);
		return i;
	}
	
	//获取刷卡记录02
	@RequestMapping(params = "getcardinfo02")
	@ResponseBody		
	public List<Map<String, Object>> getcardinfo02(String openid,String ruletype,HttpServletRequest request){
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer();
		
		if(ruletype.equals("1")){
		// getData
			sql.append("SELECT b.bs_cardno,b.bs_name,c.bc_datetime from bus_openid a ");
			sql.append("LEFT JOIN bas_student b on a.bs_studentid=b.id ");
			sql.append("LEFT JOIN bus_cardinfo c on b.bs_cardno=c.bc_cardno ");
			sql.append("WHERE a.bo_openid='" + openid + "'");
			sql.append("ORDER BY bs_name,bc_datetime desc");
		}else if(ruletype.equals("2")){
			sql.append("SELECT d.bc_cardno as bs_cardno,c.bs_name,d.bc_datetime from t_s_base_user a ");
			sql.append("LEFT JOIN bas_class b on a.id=b.bc_personid ");
			sql.append("LEFT JOIN bas_student c on c.bc_id=b.id ");
			sql.append("LEFT JOIN bus_cardinfo d on d.bc_cardno=c.bs_cardno ");
			sql.append("WHERE a.openid='" + openid + "' AND c.bs_cardno is not NULL ");
			sql.append("ORDER BY bc_cardno,bc_datetime desc");
		}
		System.out.println("getcardinfo sql..." + ";" + sql.toString());
		listTree = this.systemService.findForJdbc(sql.toString());		
		return listTree;
	}
	
	//请假
	@RequestMapping(params = "leave")
	@ResponseBody		
	public int leave(String begb,String reason,String openid,String linetype,String stuid,HttpServletRequest request,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		
		System.out.println(begb + ";" + reason +";" + openid+";" +linetype+";" +stuid);
		int i = ilevel(begb, reason, openid,linetype,stuid);
		return i;
	}	
	//请假记录
	@RequestMapping(params = "leavelist")
	@ResponseBody		
	public List<Map<String, Object>> leavelist(String openid,String ruletype,HttpServletRequest request){
		//System.out.println(begb + ";" + bege + ";" + reason + openid);
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer();
		// getData
		if(ruletype.equals("1")){		//家长
			sql.append("SELECT a.bl_begdate,a.bl_student,a.bl_reason, ");
			sql.append("case  when a.bl_linetype='1' then '上学' when a.bl_linetype='2' then '放学'  else '其他' END as types from bus_leave a ");
			sql.append("LEFT JOIN bus_openid b ON a.bl_studentid=b.bs_studentid ");
			sql.append("WHERE b.bo_openid='" + openid + "'  ");
			sql.append("order by bl_begdate desc ");
		}else if(ruletype.equals("2")){	//班主任
			sql.append("SELECT a.id,b.bs_name,a.bl_begdate,a.bl_student,a.bl_reason,  ");
			sql.append("case  when a.bl_linetype='1' then '上学' when a.bl_linetype='2' then '放学'  else '其他' END as types  from bus_leave a ");
			sql.append("LEFT JOIN bas_student b on a.bl_studentid=b.id ");
			sql.append("LEFT JOIN bas_class c on c.id=b.bc_id ");
			sql.append("LEFT JOIN t_s_base_user d on d.id=c.bc_personid ");
			sql.append("WHERE d.openid='" + openid + "'  ");
			sql.append("order by bl_begdate desc ");
		}			
		System.out.println("leavelist sql..." + ";" + sql.toString());
		listTree = this.systemService.findForJdbc(sql.toString());		
		return listTree;
	}	
	
	//请假记录
	@RequestMapping(params = "getleaverecs")
	@ResponseBody		
	public List<Map<String, Object>> leavelist(String ruletype,String linename,HttpServletRequest request,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		System.out.println("ruletype-->"+ruletype);
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer();
		// getData
		if(ruletype.equals("1")){		//家长
		}else if(ruletype.equals("2")){	//班主任
			sql.append("SELECT * from (SELECT bl_studentid,bl_student,DATE_FORMAT(bl_begdate,'%Y-%m-%d')as bl_begdate,  ");
			sql.append("case bl_linetype when '1' then b.bl_sizeid  else b.bl_sizeid1 end as sizeid, ");
			sql.append("case bl_linetype when '1' then CONCAT(b.bl_name,'(上学)')  else CONCAT(b.bl_name1,'(放学)') end as blname ");
			sql.append("from bus_leave a left JOIN bas_student b ON a.bl_studentid=b.id ");
			sql.append("WHERE to_days(bl_begdate) = to_days(now())  ");
			sql.append("ORDER BY sizeid,bl_begdate desc)bb where blname='"+linename+"'; ");
		}			
		System.out.println("getleaverecs sql..." + ";" + sql.toString());
		listTree = this.systemService.findForJdbc(sql.toString());		
		return listTree;
	}
	
	//请假线路
	@RequestMapping(params = "getlinename02")
	@ResponseBody		
	public List<Map<String, Object>> getlinename02(String ruletype,HttpServletRequest request,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		System.out.println("ruletype-->"+ruletype);
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer();
		// getData
		if(ruletype.equals("1")){		//家长
		}else if(ruletype.equals("2")){	//班主任
/*			sql.append("select DISTINCT  blname from (SELECT bl_studentid,bl_student,DATE_FORMAT(bl_begdate,'%Y-%m-%d')as bl_begdate,  ");
			sql.append("case bl_linetype when '1' then b.bl_sizeid  else b.bl_sizeid1 end as sizeid, ");
			sql.append("case bl_linetype when '1' then CONCAT(b.bl_name,'(上学)')  else CONCAT(b.bl_name1,'(放学)') end as blname ");
			sql.append("from bus_leave a left JOIN bas_student b ON a.bl_studentid=b.id ");
			sql.append("WHERE to_days(bl_begdate) = to_days(now()) ");
			sql.append("ORDER BY sizeid,bl_begdate desc)bb ");*/
			//sql.append("SELECT case line_status when '1' then  CONCAT(bl_name,'(上学)')  else CONCAT(bl_name,'(放学)') end as blname from bas_line ORDER BY line_status ");
			sql.append("SELECT CONCAT( bl_name, bl_desc) as blname from bas_line ORDER BY line_status ");
		}			
		System.out.println("getlinename02 sql..." + ";" + sql.toString());
		listTree = this.systemService.findForJdbc(sql.toString());		
		return listTree;
	}
	
	//请假审批
	@RequestMapping(params = "approve")
	@ResponseBody		
	public int approve(String openid,String stroid,String approvetype,HttpServletRequest request){
		stroid = stroid.substring(1, stroid.length() - 1);
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE bus_leave SET bl_status='" + approvetype + "' WHERE id in (" + stroid + ")");
		
		System.out.println("approve sql..." + ";" + sql.toString());
		int sc = this.systemService.executeSql(sql.toString());
		return sc;
	}	
	//test
	@RequestMapping(params = "test1")
	@ResponseBody
	public String test(HttpServletRequest request){
		//String  s=wxutils.getAcctonken();
/*		List<WeixinButton> s=wxutils.getMenu();
		System.out.println("getAppid..."+s.get(0).getSub_button().get(0).getAppid());
		System.out.println("getKey..."+s.get(0).getSub_button().get(0).getKey());
		System.out.println("getName..."+s.get(0).getSub_button().get(0).getName());
		System.out.println("getPagepath..."+s.get(0).getSub_button().get(0).getPagepath());
		System.out.println("getType..."+s.get(0).getSub_button().get(0).getType());
		System.out.println("getUrl..."+s.get(0).getSub_button().get(0).getUrl());
		//System.out.println("getUrl..."+s.get(0).getSub_button().get(0).getAppid());
		return "";*/
		//对象转json
			Map<String, String> data = new HashMap<String, String>();
			data.put("result", "0");
/*		　 sqlModel model = new sqlModel();
		   model.setAge(18);
		   model.setHeight(188.0);
		   model.setName("张三");*/
		        
		   String jsonStr = null;
		   try {
		      JSONObject jsonObject = JSONObject.fromObject(data);
		      jsonStr = jsonObject.toString();
		      System.out.println(jsonStr);
		   } catch (Exception e) {
		      System.out.println("数组转json失败");
		   }
		   return jsonStr;	   
	}
	
	//createMenu
	@RequestMapping(params = "createMenu")
	@ResponseBody
	public String createMenu(HttpServletRequest request) throws WexinReqException {
		wxutils.createMenu();
		return "ok";
	}
	
	
	
	
	//业务处理
	//openid是否已绑定 1:已绑;0:未绑
	private int isBinded(String sopenid,String ruletype){
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer();
		if (ruletype.equals("1")){
			sql.append("SELECT count(*) as c FROM bus_openid  ");
			sql.append("WHERE bo_openid='" + sopenid + "'");
		}else if (ruletype.equals("2")){
			sql.append("SELECT count(*) as c FROM t_s_base_user  ");
			sql.append("WHERE openid='" + sopenid + "'");			
		}
		System.out.println("isBinded sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		String sc = listTree.get(0).get("c").toString();

		return Integer.parseInt(sc);		
	}
	
	//解除绑定 1:成功;0:失败
	private int isUnBinded(String sopenid,String ruletype){
		StringBuffer sql = new StringBuffer();
		if (ruletype.equals("1")){
			sql.append("DELETE FROM bus_openid ");
			sql.append("WHERE bo_openid='" + sopenid + "'");			
		}else if(ruletype.equals("2")){
			sql.append("UPDATE t_s_base_user SET openid=null WHERE openid='"+sopenid+"' ");			
		}

		System.out.println("isUnBinded sql..." + ";" + sql.toString());

		int sc = this.systemService.executeSql(sql.toString());
		if (sc >= 1) {
			sc = 1;
		} else {
			sc = 0;
		}
		
		return sc;	
	}
	
	// 电话号码是否已存在 1:存在;0:不存在
	private int isExitsTel(String stel,int type) {
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("SELECT count(bs.id) as c From ");
		if (type==1){
			//StringBuffer sql = new StringBuffer("SELECT count(*) as c From bas_student a ");
			sql.append("bas_student bs  WHERE bs_tel='" + stel + "'");
		}else if(type==2){
			sql.append("t_s_base_user bs,t_s_user ts where bs.ID=ts.id and bs.status='1' and ts.mobilePhone='" + stel + "'");
		}
		System.out.println("bindcard sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		String sc = listTree.get(0).get("c").toString();

		return Integer.parseInt(sc);
	}
	
	// 验证码  1:存在;0:不存在
	private int isExitsTelCode(String tell,String tellcode) {
		//超时300s则删除,5分钟内有效
		Date times=new Date();
		int size = a3s.listSms.size();
		for(int i=size-1;i>=0;i--){
			ApiV3SmsEntity ave=a3s.listSms.get(i);
			long diffTs=times.getTime()-ave.getTs();
			//System.out.println(diffTs);
			if(diffTs>ave.getExpiresIn()*1000)
			{
				a3s.listSms.remove(i);
				System.out.println("删除了");
			}
		}
		
		for(ApiV3SmsEntity ave : a3s.listSms)
		{
			//1则存在手机、验证码
			if( ave.getMobiles().equals(tell) && ave.getCode().equals(tellcode) )
				return 1;
			//2则存在手机、验证码已发送
			if( ave.getMobiles().equals(tell))
			{
				System.out.println("已存在手机:"+ave.getMobiles()+"当前验证码是："+ ave.getCode());
				return 2;
			}
		}
		return 0;
	}

	//获取学生ID
	private String[] getStudenID(String stel){
		String[] sc=new String[3];	//绑定最多三个学生
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("SELECT id FROM bas_student  ");
		sql.append("WHERE bs_tel='" + stel + "' order by id ");
		System.out.println("getStudenID sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		System.out.println("listTree.size-->"+listTree.size());
		if(listTree.size()==1){
			sc[0] = listTree.get(0).get("id").toString();
			sc[1] = "0";
			sc[2] = "0";
		}else if(listTree.size()==2){
			sc[0] = listTree.get(0).get("id").toString();
			sc[1] = listTree.get(1).get("id").toString();;
			sc[2] = "0";
		}else if(listTree.size()>=3){
			sc[0] = listTree.get(0).get("id").toString();
			sc[1] = listTree.get(1).get("id").toString();;
			sc[2] = listTree.get(2).get("id").toString();
		}

		return sc;
	}
	
	//获取学生ID
	private String getStudenName(String id){
		String sc="";
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("SELECT bs_name FROM bas_student  ");
		sql.append("WHERE id='" + id + "' ");
		System.out.println("getStudenName sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		System.out.println("listTree.size-->"+listTree.size());
		if(listTree.size()==1){
			sc = listTree.get(0).get("bs_name").toString();
		}

		return sc;
	}	
	//获取学生ID
	private Map<String,Object> getStudenID02(String openid){
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("SELECT a.id,a.bs_name from bas_student a  ");
		sql.append("LEFT JOIN bus_openid b ON a.id=b.bs_studentid ");
		sql.append("where b.bo_openid='" + openid + "'");
		System.out.println("getStudenID02 sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		//String sc = listTree.get(0).get("id").toString();
		return listTree.get(0);
	}
	
	//新增openid(家长)
	private int iopenid(String stuid,String openid){
		//isBinded
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String sysdt = df.format(new Date());// new Date()为获取当前系统时间
		UUID ID = UUID.randomUUID();
		StringBuffer sql = new StringBuffer(
				"INSERT INTO bus_openid  (`id`,`bs_studentid`, `bo_openid`, `bo_binddatetime`) ");
		//sql.append("VALUES ('" + ID + "','" + stuid + "','" + openid + "','" + sysdt + "');");
		sql.append("SELECT '" + ID + "', '" + stuid + "', '" + openid + "', '" + sysdt + "' ");
		sql.append(" WHERE NOT EXISTS(select * from bus_openid where bus_openid.bo_openid = '" + openid + "' and bs_studentid='" + stuid + "')");
		System.out.println("iopenid sql..." + ";" + sql.toString());

		int sc = this.systemService.executeSql(sql.toString());
		return sc;
	}
	//更新 openid(班主任)
	private int uopenid(String tel,String openid){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String sysdt = df.format(new Date());// new Date()为获取当前系统时间
		UUID ID = UUID.randomUUID();
		StringBuffer sql = new StringBuffer("UPDATE t_s_base_user SET openid='" + openid + "' WHERE id in (select id from t_s_user where mobilePhone='"+tel+"')");

		System.out.println("uopenid sql..." + ";" + sql.toString());

		int sc = this.systemService.executeSql(sql.toString());
		return sc;
	}	
	//新增请假信息
	private int ilevel(String begb,String reason,String openid,String linetype,String stuID){		
		String status = "0";
/*		Map<String,Object> ob=getStudenID02(openid);
		String stuID = ob.get("id").toString();*/
		String stuName=getStudenName(stuID);
		StringBuffer sql = new StringBuffer("");
		StringBuffer sql1 = new StringBuffer("");
		int sc=0;
		if(!linetype.equals("12")){
			//UUID ID = UUID.randomUUID();
			sql.append("INSERT INTO `bus_leave` (`id`, `bl_studentid`,`bl_student`,  `bl_reason`, `bl_begdate`, `bl_status`,`bl_linetype`) ");
		
			sql.append("SELECT UUID(), '" + stuID + "', '" + stuName + "', '" + reason + "','" + begb + "','" + status+ "',"+linetype);
			sql.append(" WHERE NOT EXISTS(select * from bus_leave where DATEDIFF(bl_begdate,'" + begb + "')=0 AND bl_linetype="+linetype+" AND bl_studentid='"+stuID+"')");
			System.out.println("ilevel sql..." + ";" + sql.toString());
			sc = this.systemService.executeSql(sql.toString());
		}else{
			sql.append("INSERT INTO `bus_leave` (`id`, `bl_studentid`,`bl_student`,  `bl_reason`, `bl_begdate`, `bl_status`,`bl_linetype`) ");
			sql.append("SELECT UUID(), '" + stuID + "', '" + stuName + "', '" + reason + "','" + begb + "','" + status+ "',1");
			sql.append(" WHERE NOT EXISTS(select * from bus_leave where DATEDIFF(bl_begdate,'" + begb + "')=0 AND bl_linetype=1 AND bl_studentid='"+stuID+"')");
			System.out.println("ilevel sql..." + ";" + sql.toString());
			sc=this.systemService.executeSql(sql.toString());
			
			sql1.append("INSERT INTO `bus_leave` (`id`, `bl_studentid`,`bl_student`,  `bl_reason`, `bl_begdate`, `bl_status`,`bl_linetype`) ");
			sql1.append("SELECT UUID(), '" + stuID + "', '" + stuName + "', '" + reason + "','" + begb + "','" + status+ "',2");
			sql1.append(" WHERE NOT EXISTS(select * from bus_leave where DATEDIFF(bl_begdate,'" + begb + "')=0 AND bl_linetype=2 AND bl_studentid='"+stuID+"')");			
			System.out.println("ilevel sql1..." + ";" + sql1.toString());
			sc=this.systemService.executeSql(sql1.toString());
		}


		

		//int sc = this.systemService.executeSql(sql.toString());
		if(sc!=0){
			String[] msgs = new String[4];
			//姓名;班;老师;openid
			msgs = qryTeacherInfo(stuID);
			if (msgs != null) {
				String msg = "尊敬的" + msgs[2] + ",您的学生提交不坐车申请,详细如下：";
				System.out.println("linetype:"+linetype);
				if (linetype.equals("1") ) {
					begb = begb + "(上学)";
				} else if (linetype.equals("2"))
					begb = begb + "(放学)";
				else if (linetype.equals("12")) {
					begb = begb + "(上/放学)";
				} else {
				}

				try {
					doSendTMessage_leave(msg, msgs[1], msgs[0], begb, msgs[3]);
				} catch (WexinReqException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return sc;
	}
	
	
	/**
	   * 获取用户信息
	   * 
	   * @param accessToken 接口访问凭证
	   * @param openId 用户标识
	   * @return WeixinUserInfo
	   */
	public WeixinUserInfo getUserInfo(String accessToken, String openId) {
		WeixinUserInfo weixinUserInfo = null;
		
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		System.out.println("requestUrl:"+requestUrl);
		// 获取用户信息
		JSONObject jsonObject = wxutils.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				weixinUserInfo = new WeixinUserInfo();
				// 用户的标识
				weixinUserInfo.setOpenId(jsonObject.getString("openid"));
				// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
				weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
				// 用户关注时间
				weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
				// 昵称
				weixinUserInfo.setNickname(jsonObject.getString("nickname"));
				// 用户的性别（1是男性，2是女性，0是未知）
				weixinUserInfo.setSex(jsonObject.getInt("sex"));
				// 用户所在国家
				weixinUserInfo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				weixinUserInfo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				weixinUserInfo.setCity(jsonObject.getString("city"));
				// 用户的语言，简体中文为zh_CN
				weixinUserInfo.setLanguage(jsonObject.getString("language"));
				// 用户头像
				weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
			} catch (Exception e) {
				if (0 == weixinUserInfo.getSubscribe()) {
					System.out.println("用户{}已取消关注"+ weixinUserInfo.getOpenId());
				} else {
					int errorCode = jsonObject.getInt("errcode");
					String errorMsg = jsonObject.getString("errmsg");
					System.out.println("获取用户信息失败 errcode:{} errmsg:{}"+ errorCode+errorMsg);
				}
			}
		}
		return weixinUserInfo;
		}
	
	//更新刷卡状态为已发送
	private int issendcard(String id){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String sysdt = df.format(new Date());// new Date()为获取当前系统时间
		UUID ID = UUID.randomUUID();
		StringBuffer sql = new StringBuffer("UPDATE bus_cardinfo SET bc_sended=1,update_date='"+sysdt+"' where id='"+id+"' and bc_sended=0");

		System.out.println("issendcard sql..." + ";" + sql.toString());

		int sc = this.systemService.executeSql(sql.toString());
		return sc;
	}	
	
	
	//APP端业务
	//登录身份验证
	@RequestMapping(params = "userlogin")
	@ResponseBody
	public int userlogin(String userid,String pwd,HttpServletRequest request,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("SELECT count(*) as c From ");
		sql.append("t_s_base_user  WHERE username='" + userid + "'");
		System.out.println("userlogin sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		String sc = listTree.get(0).get("c").toString();
		int isc = Integer.parseInt(sc);

		return isc;				
	}
	
	//根据userid=openid获取有关的线路
	@RequestMapping(params = "getlinename03")
	@ResponseBody	
	public List<Map<String, Object>> getlinename03(String userid,HttpServletRequest request,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		
		//StringBuffer sql = new StringBuffer("SELECT id,case line_status when '1' then  CONCAT(bl_name,'(上学)')  else CONCAT(bl_name,'(放学)') end as linename,line_status ");
		StringBuffer sql = new StringBuffer("SELECT id,CONCAT( bl_name, bl_desc) as linename,line_status ");
		sql.append(" from bas_line  ");

		if(userid.equals("00000")){
			//
		}else{
			sql.append("where id in(SELECT fk_bl_id from bas_size where id in(  ");
			sql.append("SELECT bl_sizeid as bl_sizeid from bas_student  ");
			sql.append("where id in (select bs_studentid from bus_openid where bo_openid='"+userid+"')  ");
			sql.append("UNION  ");
			sql.append("SELECT bl_sizeid1 as bl_sizeid from bas_student  ");
			sql.append("where id in (select bs_studentid from bus_openid where bo_openid='"+userid+"') ");
			sql.append(")) ");
		}
		sql.append("ORDER BY line_status ");

		
		System.out.println("getlinename03 sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		//String sc = listTree.get(0).get("linename").toString();
		return listTree;				
	}
	
	//根据登录者，获取其所管理的线路名称
	@RequestMapping(params = "getlinename")
	@ResponseBody	
	public List<Map<String, Object>> getlinename(String userid,HttpServletRequest request,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("SELECT c.id,CONCAT( bl_name, bl_desc) as linename,c.line_status FROM t_s_role tr,t_s_role_user tru,t_s_base_user tu,bas_line c ");
		sql.append("where tr.ID=tru.roleid and tru.userid=tu.ID and tr.rolecode='driver' and tu.status='1' and c.bl_driverid=tu.ID ");
		if(userid.equals("00000")){
			//
		}else{
			sql.append(" and tu.username='" + userid + "'");
		}
		sql.append(" order by line_status,id");
		
		System.out.println("getlinename sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		//String sc = listTree.get(0).get("linename").toString();
		return listTree;				
	}	
		
	//根据登录者，获取其管理的线路下所有站点(未使用)
	@RequestMapping(params = "getstudentlist")
	@ResponseBody	
	public List<Map<String, Object>> getstudentlist(String userid,HttpServletRequest request,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("SELECT d.id,tu.username,CONCAT( bl_name, bl_desc) as linename,d.bs_name FROM t_s_role tr,t_s_role_user tru,t_s_base_user tu,bas_line c,bas_size d ");
		sql.append("where tr.ID=tru.roleid and tru.userid=tu.ID and tr.rolecode='driver' and tu.status='1' and c.bl_driverid=tu.id  and d.fk_bl_id=c.id  ");
		sql.append("and tu.username='" + userid + "' ORDER BY bs_desc");
		System.out.println("getallsizename sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());

		return listTree;				
	}
	
	//根据线路，获取其线路下所有站点
	@RequestMapping(params = "getsizelist")
	@ResponseBody	
	public List<Map<String, Object>> getsizelist(String lineid,HttpServletRequest request,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("SELECT a.id,CONCAT(bl_name, bl_desc) as linename,bs_name FROM bas_size a LEFT JOIN bas_line b ON a.fk_bl_id=b.id ");
		sql.append("WHERE b.id='" + lineid + "' ORDER BY bs_seq");
		System.out.println("getsizelist sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());

		return listTree;				
	}	
	
	//根据站点OID，获取此站点所有学生刷卡信息
	@RequestMapping(params = "getcardlist")
	@ResponseBody	
	public List<Map<String, Object>> getcardlist(String sizeoid,HttpServletRequest request,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		//根据站点oid判断是上车线路还是下车线路？1:上学;2:放学
		int lt=getSizeIsUporDown(sizeoid);
		
		//根据站点oid判断是否是终点下车(起点上车)还是普通站点 	0普通站点;1上学下车/放学上车
		int lt1=getSizeIsBorE(sizeoid);
		
		
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer();
		if (lt1==0){  //普通站点 +当天的刷卡时间+当前站点
			if (lt==1){	//上学
				sql.append(" SELECT id,bc_name,bs_name,bs_cardno,'--' bc_datetime,'--'bl_begdate ");
				sql.append(" from bas_student where bl_sizeid='" + sizeoid + "' ");
				sql.append(" And bs_cardno not in( ");
				sql.append(" select bc_cardno from bus_cardinfo where size_oid='" + sizeoid + "'  ");
				sql.append(" and size_status=0 and to_days(bc_datetime) = to_days(now())) AND bs_cardno is not null");
				sql.append(" AND id not in( ");
				sql.append(" select bl_studentid from bus_leave where to_days(bl_begdate) = to_days(now()) and bl_linetype=1) ");
			}else if(lt==2){
				sql.append(" SELECT id,bc_name,bs_name,bs_cardno,'--' bc_datetime,'--'bl_begdate ");
				sql.append(" from bas_student where bl_sizeid1='" + sizeoid + "'");
				sql.append(" and bs_cardno in(");
				sql.append(" select bc_cardno from bus_cardinfo where size_oid=(");
				sql.append(" select id from bas_size ");
				sql.append(" where fk_bl_id =(select fk_bl_id from bas_size where id='" + sizeoid + "')");
				sql.append(" and size_status=1) and to_days(bc_datetime) = to_days(now()) )");
				sql.append(" AND bs_cardno not in (select bc_cardno from bus_cardinfo where size_oid='" + sizeoid + "' and size_status=0 ");
				sql.append(" AND to_days(bc_datetime) = to_days(now()) ) AND bs_cardno is not null");	
			}			
		}else if (lt1==1){	
			if (lt==1){	//终点下车				
				sql.append("SELECT A1.* from ( ");
				sql.append(" SELECT DISTINCT b.id,b.bc_name,b.bs_name,IFNULL(b.bs_cardno,'--')as bs_cardno,'--' bc_datetime,'--'bl_begdate from bus_cardinfo A ");
				sql.append(" LEFT JOIN bas_student b  on b.bs_cardno=a.bc_cardno");
				sql.append(" where size_oid in(");
				sql.append(" select id from bas_size ");
				sql.append(" where fk_bl_id =(select fk_bl_id from bas_size where id='" + sizeoid + "' ))");
				sql.append(" and size_status=0 AND to_days(bc_datetime) = to_days(now()) AND b.id is not NULL ) A1 ");
				sql.append(" WHERE A1.bs_cardno NOT IN(");
				sql.append(" SELECT bc_cardno from bus_cardinfo Where size_status=1 AND to_days(bc_datetime) = to_days(now())) AND bs_cardno is not null");				
			}else if (lt==2){	//起点上车
				sql.append("select id,bc_name,bs_name,bs_cardno,'--' bc_datetime,'--'bl_begdate ");
				sql.append(" from bas_student where bl_sizeid1 in( ");
				sql.append(" select id from bas_size  ");
				sql.append(" where fk_bl_id =(select fk_bl_id from bas_size where id='" + sizeoid + "')) ");
				sql.append(" AND id not in( ");
				sql.append(" select bl_studentid from bus_leave where to_days(bl_begdate) = to_days(now()) and bl_linetype=2) ");
				sql.append(" AND bs_cardno not in( ");
				sql.append(" SELECT bc_cardno from bus_cardinfo where size_oid ='" + sizeoid + "'  ");
				sql.append(" AND  size_status=1 AND to_days(bc_datetime) = to_days(now())) AND bs_cardno is not null");				
			}
			
		}
		
			
		System.out.println("getcardlist sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());

		return listTree;				
	}	
	//根据站点oid判断是上车线路还是下车线路？1:上学;2:放学
	private int getSizeIsUporDown(String sizeoid){
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT IFNULL(line_status,0) as t from bas_line a  LEFT JOIN bas_size b on a.id=b.fk_bl_id ");
		sql.append("WHERE b.id='" + sizeoid + "'");

		System.out.println("getSizeIsUporDown sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		String sc = listTree.get(0).get("t").toString();

		return Integer.parseInt(sc);			
	}
	//根据站点oid判断是上车(下车)还是普通站点 	0普通站点;1上学下车/放学上车
	private int getSizeIsBorE(String sizeoid){
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer();
		sql.append("select IFNULL(size_status,0)as t from bas_size ");
		sql.append("WHERE id='" + sizeoid + "'");

		System.out.println("getSizeIsBorE sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		String sc = listTree.get(0).get("t").toString();

		return Integer.parseInt(sc);			
	}	
	
	
	
	//新增刷卡信息
	@RequestMapping(params = "iCardData")
	@ResponseBody
	public String iCardData(String OID,String cardno,String sizeoid,HttpServletRequest request,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		//根据站点oid判断是否是终点下车(起点上车)还是普通站点 	0普通站点;1上学下车/放学上车
		int lt1=getSizeIsBorE(sizeoid);
		
		//UUID ID = UUID.randomUUID();
		StringBuffer sql = new StringBuffer(
				"INSERT INTO `bus_cardinfo` (`id`, `bc_cardno`, `bc_datetime`, `size_oid`,`size_status`) ");
		sql.append("VALUES ('" + OID + "','" + cardno + "',now(),'"+sizeoid+"','"+lt1+"' );");

		//System.out.println("iCardData sql..." + ";" + sql.toString());

		int sc =this.systemService.executeSql(sql.toString());
		System.out.println("iCardData sql..." + ";" + sql.toString()+";"+String.valueOf(sc));
		
		
		//推送消息
		try {
			this.doSendTMessage_SK(OID,sizeoid);
		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sc+";"+cardno;

	}
	
	//新增站点信息
	@RequestMapping(params = "doSaveLoc")
	@ResponseBody
	public int doSaveLoc(String lineoid,String sizeoid,HttpServletRequest request,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		int sc=0;
		if(isEnd(lineoid)>=1){
			sc=1;
		}else{
			UUID ID = UUID.randomUUID();
			StringBuffer sql = new StringBuffer(
					"INSERT INTO `bus_nextstationbuttoninfo` (`id`, `line_id`, `size_id`, `create_date`) ");
			sql.append("VALUES ('" + ID + "','" + lineoid + "','" + sizeoid + "',now() );");
	
			//System.out.println("iCardData sql..." + ";" + sql.toString());
	
			sc = this.systemService.executeSql(sql.toString());
			System.out.println("doSaveLoc sql..." + ";" + sql.toString()+";"+String.valueOf(sc));
		}
		
				
		return sc;
		
	}
	//获取某线路终点的id
	private String getsizeidOfline(String lineid){
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id,bs_seq from bas_size as t1 where fk_bl_id='"+lineid+"' ");
		sql.append("and bs_seq=(select max(bs_seq) from bas_size as t2 where fk_bl_id='"+lineid+"') ");
		System.out.println("getsizeidOfline sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		String sc = listTree.get(0).get("id").toString();	
		return sc;				
	}	
	//判断某线路的车是否已到终点
	private int isEnd(String lineid){
		String sizeid=getsizeidOfline(lineid);
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("SELECT count(*) as c From ");
		sql.append("bus_nextstationbuttoninfo  WHERE line_id='" + lineid + "' AND size_id='" + sizeid + "' AND to_days(CREATE_date) = to_days(now()) ");
		System.out.println("isEnd sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		String sc = listTree.get(0).get("c").toString();
		int isc = Integer.parseInt(sc);

		return isc;		
		
	}
	
	//查询站点
	@RequestMapping(params = "getLocInfo")
	@ResponseBody	
	public List<Map<String, Object>> getLocInfo(String lineoid,HttpServletRequest request,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("SELECT size_id,CREATE_date FROM bus_nextstationbuttoninfo ");
		sql.append("WHERE line_id='" + lineoid + "' AND to_days(CREATE_date) = to_days(now()) order by CREATE_date desc LIMIT 1 ");
		System.out.println("getLocInfo sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());

		return listTree;				
	}
	
	
	
	//接收文件
	@RequestMapping(params = "uploadFile")
	@ResponseBody
	public void uploadFile(String fileName,HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("uploadFile begging..."+fileName );
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		InputStream is = request.getInputStream();
		DataInputStream dis = new DataInputStream(is);

		String result = "";
		try {
			result = saveFile(dis,fileName);
		} catch (Exception e) {
			e.printStackTrace();
			result = "uploaderror";
		}

		request.getSession().invalidate();
		response.setContentType("text/html;charset=UTF-8");
		ObjectOutputStream dos = new ObjectOutputStream(response.getOutputStream());
		dos.writeObject(result);
		dos.flush();
		dos.close();
		dis.close();
		is.close();
	}

	/**
	 * 保存文件
	 * 
	 * @param dis
	 * @return
	 */
	private String saveFile(DataInputStream dis,String fileName) {
		//String fileurl = "D:/t/a001.png";
		String fileurl = wxutils.storePath+fileName;
		System.out.println("saveFile begging..."+fileurl );
		File file = new File(fileurl);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream fps = null;
		try {
			fps = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		int length = -1;

		try {
			while ((length = dis.read(buffer)) != -1) {
				fps.write(buffer, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fps.flush();
			fps.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	//查询车辆位置
	@RequestMapping(params = "getBusloc")
	@ResponseBody
	public ModelAndView getBusloc(String code,HttpServletRequest request,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		String sopenid=wxutils.OAuthGetOpenid(code);
		if(sopenid!=""){
			String[] sc=new String[3];
			int isT=isTecher(sopenid);
			if(isT<=0){
				sc=qryBusLoc(sopenid);
				if (sc!=null){
					try {
						doSendTMessage_QryBusLoc("尊敬的家长，您所查询的车辆位置如下：",sc[0],sc[1],sc[2],sopenid);
					} catch (WexinReqException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					try {
						doSendTMessage_QryBusLoc("尊敬的家长，您所查询的车辆位置如下：","---","未发车","---",sopenid);
					} catch (WexinReqException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else{
				try {
					doSendTMessage_QryBusLoc("尊敬的班主任，您没有权限查询！","---","---","---",sopenid);
				} catch (WexinReqException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}	
			
		}
		String url = "redirect:"+wxutils.basurl+"/page/msg.html"; 
		System.out.println("url-->:"+url);
		return new ModelAndView(url);		
	}
	
	private String[] qryBusLoc(String openid){
		String[] sc=new String[3];
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
/*		StringBuffer sql = new StringBuffer("SELECT a.bc_cardno,bc_datetime,b.bl_sizeid,b.bl_name,b.bl_size,c.bs_seq ");
		sql.append(" from bus_cardinfo a ");
		sql.append(" LEFT JOIN bas_student b ON a.bc_cardno=b.bs_cardno ");
		sql.append(" LEFT JOIN bas_size c ON b.bl_sizeid=c.id ");
		sql.append(" where bl_name=(SELECT b.bl_name ");
		sql.append(" from bus_openid a LEFT JOIN bas_student b ON a.bs_studentid=b.id ");
		sql.append(" WHERE a.bo_openid='"+openid+"') ");
		sql.append(" order by c.bs_seq desc LIMIT 1 ");
		System.out.println("qryBusLoc sql..." + ";" + sql.toString());*/
		/*
		StringBuffer sql = new StringBuffer("SELECT * FROM ( ");
		sql.append(" SELECT a.bc_cardno,bc_datetime,b.bl_sizeid,b.bl_name,b.bl_size,c.bs_seq ");  
		sql.append(" from bus_cardinfo a  LEFT JOIN bas_student b ON a.bc_cardno=b.bs_cardno ");  
		sql.append(" LEFT JOIN bas_size c ON b.bl_sizeid=c.id ");  
		sql.append(" where bl_name= ");
		sql.append(" (SELECT b.bl_name  from bus_openid a LEFT JOIN bas_student b ON a.bs_studentid=b.id  WHERE a.bo_openid='"+openid+"') ");  
		sql.append(" AND to_days(bc_datetime) = to_days(now())  ");
		sql.append(" UNION ");
		sql.append(" SELECT a.bc_cardno,bc_datetime,b.bl_sizeid,b.bl_name,b.bl_size1 as bl_size,c.bs_seq*6 as bs_seq  ");
		sql.append(" from bus_cardinfo a  LEFT JOIN bas_student b ON a.bc_cardno=b.bs_cardno ");  
		sql.append(" LEFT JOIN bas_size c ON b.bl_sizeid=c.id   ");
		sql.append(" where bl_name= ");
		sql.append(" (SELECT b.bl_name  from bus_openid a LEFT JOIN bas_student b ON a.bs_studentid=b.id  WHERE a.bo_openid='"+openid+"') ");  
		sql.append(" AND to_days(bc_datetime) = to_days(now()) ");
		sql.append(" ) A1 order by A1.bs_seq desc LIMIT 1 ");
		System.out.println("qryBusLoc sql..." + ";" + sql.toString());
		listTree = this.systemService.findForJdbc(sql.toString());
		*/
		
		
		//获取此学生上学/放学站点id
		String size=getSizeid(openid,1);
		String size1=getSizeid(openid,2);
		System.out.println("站点id:"+size+";"+size1);
		//获取线路id
		String line=getLineid(size);
		String line1=getLineid(size1);
		System.out.println("线路id:"+line+";"+line1);
		
		StringBuffer sql = new StringBuffer("SELECT DATE_FORMAT(A1.bc_datetime,'%Y-%m-%d %H:%i:%s')as bc_datetime,B1.place,B1.bs_name,B1.fk_bl_id FROM(");
		sql.append(" SELECT bc_datetime,size_oid,size_status from bus_cardinfo ");
		sql.append(" where to_days(bc_datetime) = to_days(now()) ) A1 ");
		sql.append(" left join ( ");
		sql.append(" SELECT a.id,a.fk_bl_id,a.bs_name,CONCAT(b.bl_name,b.bl_desc)as place from bas_size a ");
		sql.append(" left join bas_line b on b.id=a.fk_bl_id) B1 on B1.id=A1.size_oid ");
		sql.append(" Where fk_bl_id='"+line+"' or fk_bl_id='"+line1+"' ");
		sql.append(" order by bc_datetime desc LIMIT 1");
		System.out.println("qryBusLoc sql..." + ";" + sql.toString());
		listTree = this.systemService.findForJdbc(sql.toString());		
		if(listTree.size()==1){
			sc[0]=listTree.get(0).get("place").toString();
			sc[1]=listTree.get(0).get("bs_name").toString();
			sc[2]=listTree.get(0).get("bc_datetime").toString();			
		}else{
			sc=null;
		}


		return sc;	
		
		
	}
	
	private String getSizeid(String openid,int type){
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer();
		String sizename="";
		if (type==1){
			sizename="bl_sizeid";
		}else{
			sizename="bl_sizeid1";
		}
		sql.append("SELECT "+sizename+" as bl_sizeid from bas_student a ");
		sql.append("WHERE id =(SELECT bs_studentid from bus_openid where bo_openid='"+openid+"')");

		System.out.println("getSizeid sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		String sc = listTree.get(0).get("bl_sizeid").toString();	
		return sc;
	}
	private String getLineid(String sizeid){
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT fk_bl_id from bas_size where id='"+sizeid+"' ");


		System.out.println("getLineid sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		String sc = listTree.get(0).get("fk_bl_id").toString();	
		return sc;		
		
	}
	private String getLineSizeName(String sizeid){
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT CONCAT(b.bl_name,bl_desc,bs_name) place from bas_size a  LEFT JOIN bas_line b on b.id=a.fk_bl_id where a.id='"+sizeid+"' ");

		System.out.println("getLineSizeName sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		String sc = listTree.get(0).get("place").toString();	
		return sc;		
		
	}	
	
	
	//是否是老师
	private int isTecher(String openid){
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("SELECT count(*) as c From ");
		sql.append("t_s_base_user  WHERE openid='" + openid + "'");
		System.out.println("isTecher sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		String sc = listTree.get(0).get("c").toString();
		int isc = Integer.parseInt(sc);

		return isc;		
		
	}
	
	
	public String[] qryAcctooken(){
		String[] sc = new String[2];
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer(
				"SELECT cf_value,DATE_FORMAT(cf_desc,'%Y-%m-%d %H:%i:%s')as cf_desc from bus_config where cf_code='accesstoken'");

		System.out.println("qryacctooken sql..." + ";" + sql.toString());
		listTree = this.systemService.findForJdbc(sql.toString());
		if (listTree.size() == 1) {
			sc[0] = listTree.get(0).get("cf_value").toString();
			sc[1] = listTree.get(0).get("cf_desc").toString();
		} else {
			sc = null;
		}				
		return sc;
	}
	//更新Acctooken
	public int updateAcctooken(String acctonken){
		long sysdt=System.currentTimeMillis()+60*60*1000;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String etime = df.format(new Date(sysdt));// 
		StringBuffer sql = new StringBuffer(
				"UPDATE bus_config SET cf_desc='" + etime + "', cf_value='"+acctonken+"' where cf_code='accesstoken'");

		System.out.println("updateAcctooken sql..." + ";" + sql.toString());

		int sc = this.systemService.executeSql(sql.toString());
		return sc;
	}	
	//姓名;班;老师;openid
	public String[] qryTeacherInfo(String stuID){
		String[] sc = new String[4];
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer(
				"SELECT a.bs_name,CONCAT(b.bc_grade,b.bc_name) as bcname,c.realname as teacher,c.openid from bas_student a");
		sql.append(" LEFT JOIN bas_class b on b.id=a.bc_id LEFT JOIN t_s_base_user c on c.id=b.bc_personid");
		sql.append(" where a.id='"+stuID+"' and c.openid is not NULL");

		System.out.println("qryTeacherInfo sql..." + ";" + sql.toString());
		listTree = this.systemService.findForJdbc(sql.toString());
		if (listTree.size() == 1) {
			sc[0] = listTree.get(0).get("bs_name").toString();
			sc[1] = listTree.get(0).get("bcname").toString();
			sc[2] = listTree.get(0).get("teacher").toString();
			sc[3] = listTree.get(0).get("openid").toString();
		} else {
			sc = null;
		}				
		return sc;
	}	
	
	
	@RequestMapping(params = "test")
	@ResponseBody
	public String testing(HttpServletRequest request){
		String strAcctonken="";
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		String[] sc=new String[2];
		Date expiresTime = new Date();
		Date curTime=new Date();
		//basWXController bwx=new basWXController();
		sc=qryAcctooken();
		try {
			expiresTime=df.parse(sc[1]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(expiresTime.getTime()>curTime.getTime()){
			strAcctonken=sc[0];			
		}else{
			strAcctonken="12345678";
			updateAcctooken(strAcctonken);
		}
		System.out.println("getAcctonken:"+strAcctonken+";"+df.format(curTime));		
		return strAcctonken;
		
	};
}
