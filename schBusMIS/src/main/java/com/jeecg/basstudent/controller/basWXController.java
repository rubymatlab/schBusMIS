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
import org.jeewx.api.wxsendmsg.JwSendTemplateMsgAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
	private static final String Templateid_WR="adEMn2SjI_B3R_ckc9oqfDR7DPQ_t7znxiJxi9pSmoA";
	private static final String Templateid_NextUp="uocevTOp3GEooEc6AK0Me1Fk1shv4y8Uk0Gn5lIi9f8";
	private static final String Templateid_NextDw="7gNUN4ACrn0bogzgtnSQHWfjTMvCR57J7iaR05_OnqU";
	private static final String Templateid_SK="2BqUTvHUOZreolc2SDtFRpF6ESbIqjObH2OvVO6QDKc";
	private static final String Templateid_QryBusLoc="7_gJwIOoSclWvtUsgFTkTtGSHO-zuXHeO3978m2bPoA";
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
		
		
		int ri=0;
		String accessToken=wxutils.getAcctonken();
		TemplateMessageSendResult msgSend = new TemplateMessageSendResult();
		
		String message = null;
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();

		//getData	
		
		StringBuffer sql = new StringBuffer("SELECT a.id, b.bs_name,DATE_FORMAT(a.bc_datetime,'%Y-%m-%d %H:%i:%s')as bc_datetime,CONCAT("+place+")as place ,c.bo_openid from bus_cardinfo a ");
		sql.append("left join bas_student b on a.bc_cardno=b.bs_cardno ");
		sql.append("left join bus_openid c on b.id=c.bs_studentid ");
		sql.append("Where c.bo_openid is not NULL AND a.id='"+id+"' ");
		System.out.println("getData sql..."+";"+sql.toString());
		
		listTree = this.systemService.findForJdbc(sql.toString());// this.systemService.findHql(hql.toString());
			
		for (Map<String, Object> o : listTree) {			
			Map<String, TemplateData> data = new HashMap<String, TemplateData>();
			data.put("first", new TemplateData("尊敬的家长，你的小孩已刷卡。","#173177"));
			data.put("keyword1", new TemplateData(o.get("bs_name").toString(),"#FF0000"));
			data.put("keyword2", new TemplateData(o.get("bc_datetime").toString(),"#173177"));
			if(lt1==0){
				data.put("keyword3", new TemplateData(o.get("place").toString(),"#173177"));
			}else if(lt1==1){
				if(lt==1){
					data.put("keyword3", new TemplateData("上学终点下车","#173177"));
				}else if(lt==2){
					data.put("keyword3", new TemplateData("放学起点上车","#173177"));
				}else{
					data.put("keyword3", new TemplateData("--","#173177"));
				}
			}else{
				data.put("keyword3", new TemplateData("--","#173177"));
			}
			data.put("remark", new TemplateData("点击详情，可查看照片！","#173177"));
			msgSend.setTemplate_id(Templateid_SK);
			msgSend.setTouser(o.get("bo_openid").toString());
			msgSend.setUrl(wxutils.basurl+"/photos/"+o.get("id").toString()+".png");
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
		StringBuffer sql = new StringBuffer("SELECT b.id, b.bs_name,CONCAT("+place+")as place ,c.bo_openid from bas_student b ");
		sql.append("left join bus_openid c on b.id=c.bs_studentid ");
		sql.append("Where c.bo_openid is not NULL ");
		sql.append("AND b.id='"+id+"'");
		System.out.println("getDate sql..."+";"+sql.toString());
		
		listTree = this.systemService.findForJdbc(sql.toString());// this.systemService.findHql(hql.toString());
			
		for (Map<String, Object> o : listTree) {			
			Map<String, TemplateData> data = new HashMap<String, TemplateData>();
			data.put("first", new TemplateData("尊敬的家长，你的小孩未刷卡。","#173177"));
			data.put("keyword1", new TemplateData(o.get("bs_name").toString(),"#FF0000"));
			data.put("keyword2", new TemplateData(df.format(new Date()),"#173177"));				//当前时间
			if(lt1==0){
				data.put("keyword3", new TemplateData(o.get("place").toString(),"#173177"));
			}else if(lt1==1){
				if(lt==1){
					data.put("keyword3", new TemplateData("上学终点下车","#173177"));
				}else if(lt==2){
					data.put("keyword3", new TemplateData("放学起点上车","#173177"));
				}else{
					data.put("keyword3", new TemplateData("--","#173177"));
				}
			}else{
				data.put("keyword3", new TemplateData("--","#173177"));
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
	
	//下一站上车提醒-->上车准备提醒
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
		if (lt==1){
			place="a.bl_name,bl_size";
			place1="a.bl_sizeid";
			templateidType=Templateid_NextUp;
		}else if(lt==2){
			place="a.bl_name1,bl_size1";
			place1="a.bl_sizeid1";
			templateidType=Templateid_NextDw;
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
			sql.append("AND c.bl_begdate<=date_add(sysdate(), interval 1 hour) AND c.bl_begdate>=date_sub(sysdate(), interval 1 hour)  ");	
			sql.append("WHERE "+place1+"='" + sizeoid + "' ");
			sql.append("AND a.bs_cardno is not NULL and c.bl_begdate is NULL AND b.bo_openid is not NULL ");
			sql.append("ORDER BY id ");
			System.out.println("doSendTMessage_NextUp sql..."+";"+sql.toString());
			
			listTree = this.systemService.findForJdbc(sql.toString());// this.systemService.findHql(hql.toString());
				
			for (Map<String, Object> o : listTree) {			
				Map<String, TemplateData> data = new HashMap<String, TemplateData>();
				data.put("first", new TemplateData("尊敬的家长，我们的车即将到达["+o.get("place").toString()+"]，请做好接送准备。","#173177"));
				data.put("keyword1", new TemplateData(o.get("bs_name").toString(),"#FF0000"));
				data.put("keyword2", new TemplateData(df.format(new Date()),"#173177"));    //通知时间
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
	private void doSendTMessage_QryBusLoc(String lineName,String sizeName,String reDatetime,String openid) throws WexinReqException {

		int ri=0;
		String accessToken=wxutils.getAcctonken();
		TemplateMessageSendResult msgSend = new TemplateMessageSendResult();
		
		String message = null;
		
		Map<String, TemplateData> data = new HashMap<String, TemplateData>();
		data.put("first", new TemplateData("尊敬的家长，您所查询的车辆位置如下：","#173177"));
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
	public int insertopenid(String tell,String openid,String ruletype,HttpServletRequest request) throws WexinReqException {
		System.out.println("insertopenid参数输出:" + tell + ";" + openid+";"+ruletype);
		int i = 0;
		if (isExitsTel(tell,Integer.parseInt(ruletype)) >=1) {
			if(ruletype.equals("1")){//家长		
				String stuid = getStudenID(tell);
				i = iopenid(stuid, openid);
			}else if(ruletype.equals("2")){
				i=uopenid(tell,openid);
			}
		}else {	//手机号码不存在
			i = 0;
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
			sql.append("SELECT b.bs_name,b.bs_tel FROM bus_openid a ");
			sql.append("LEFT JOIN bas_student b on a.bs_studentid=b.id ");
			sql.append("WHERE a.bo_openid='" + openid + "'");			
		}else if(ruletype.equals("2")){
			sql.append("SELECT bs.realname as bs_name,ts.mobilePhone as bs_tel FROM t_s_base_user bs,t_s_user ts where bs.ID=ts.id ");
			sql.append("and bs.openid='" + openid + "'");			
		}

		System.out.println("getStudent sql..." + ";" + sql.toString());
		listTree = this.systemService.findForJdbc(sql.toString());
		System.out.println("getStudent:"+listTree.get(0).get("bs_name").toString()+";"+listTree.get(0).get("bs_tel").toString());
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
			sql.append("ORDER BY bc_datetime");
		}else if(ruletype.equals("2")){
			sql.append("SELECT d.bc_cardno as bs_cardno,c.bs_name,d.bc_datetime from t_s_base_user a ");
			sql.append("LEFT JOIN bas_class b on a.id=b.bc_personid ");
			sql.append("LEFT JOIN bas_student c on c.bc_id=b.id ");
			sql.append("LEFT JOIN bus_cardinfo d on d.bc_cardno=c.bs_cardno ");
			sql.append("WHERE a.openid='" + openid + "' AND c.bs_cardno is not NULL ");
			sql.append("ORDER BY bc_cardno,bc_datetime");
		}
		System.out.println("getcardinfo sql..." + ";" + sql.toString());
		listTree = this.systemService.findForJdbc(sql.toString());		
		return listTree;
	}
	
	//请假
	@RequestMapping(params = "leave")
	@ResponseBody		
	public int leave(String begb,String reason,String openid,HttpServletRequest request){
		System.out.println(begb + ";" + reason + openid);
		int i = ilevel(begb, reason, openid);
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
			sql.append("SELECT a.bl_begdate,a.bl_enddate,a.bl_reason, ");
			sql.append("case  when a.bl_status='0' then '待批' when a.bl_status='1' then '已通过'  when a.bl_status='2' then '未通过' else '其他' END as status from bus_leave a ");
			sql.append("LEFT JOIN bus_openid b ON a.bl_studentid=b.bs_studentid ");
			sql.append("WHERE b.bo_openid='" + openid + "'  ");
			sql.append("order by bl_begdate desc ");
		}else if(ruletype.equals("2")){	//班主任
			sql.append("SELECT a.id,b.bs_name,a.bl_begdate,a.bl_enddate,a.bl_reason,  ");
			sql.append("case  when a.bl_status='0' then '待批' when a.bl_status='1' then '已通过'  when a.bl_status='2' then '未通过' else '其他' END as status  from bus_leave a ");
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
	@RequestMapping(params = "test")
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

	//获取学生ID
	private String getStudenID(String stel){
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("SELECT id FROM bas_student  ");
		sql.append("WHERE bs_tel='" + stel + "'");
		System.out.println("getStudenID sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		String sc = listTree.get(0).get("id").toString();
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
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String sysdt = df.format(new Date());// new Date()为获取当前系统时间
		UUID ID = UUID.randomUUID();
		StringBuffer sql = new StringBuffer(
				"INSERT INTO bus_openid  (`id`,`bs_studentid`, `bo_openid`, `bo_binddatetime`) ");
		sql.append("VALUES ('" + ID + "','" + stuid + "','" + openid + "','" + sysdt + "');");

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
	private int ilevel(String begb,String reason,String openid){
		String status = "0";
		Map<String,Object> ob=getStudenID02(openid);
		String stuID = ob.get("id").toString();
		String stuName=ob.get("bs_name").toString();
		UUID ID = UUID.randomUUID();
		StringBuffer sql = new StringBuffer(
				"INSERT INTO `bus_leave` (`id`, `bl_studentid`,`bl_student`,  `bl_reason`, `bl_begdate`, `bl_status`) ");
		sql.append("VALUES ('" + ID + "','" + stuID + "','"+stuName+"','" + reason + "','" + begb + "','" + status
				+ "');");

		System.out.println("ilevel sql..." + ";" + sql.toString());

		int sc = this.systemService.executeSql(sql.toString());
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
	
	//根据登录者，获取其所管理的线路名称
	@RequestMapping(params = "getlinename")
	@ResponseBody	
	public List<Map<String, Object>> getlinename(String userid,HttpServletRequest request,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("SELECT c.id,CONCAT( bl_name, bl_desc) as linename FROM t_s_role tr,t_s_role_user tru,t_s_base_user tu,bas_line c ");
		sql.append("where tr.ID=tru.roleid and tru.userid=tu.ID and tr.rolecode='driver' and tu.status='1' and c.bl_driverid=tu.ID ");
		sql.append("and tu.username='" + userid + "'");
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
				sql.append(" and size_status=0 AND to_days(bc_datetime) = to_days(now())) A1 ");
				sql.append(" WHERE A1.bs_cardno NOT IN(");
				sql.append(" SELECT bc_cardno from bus_cardinfo Where size_status=1 AND to_days(bc_datetime) = to_days(now())) AND bs_cardno is not null");				
			}else if (lt==2){	//起点上车
				sql.append("select id,bc_name,bs_name,bs_cardno,'--' bc_datetime,'--'bl_begdate ");
				sql.append(" from bas_student where bl_sizeid1 in( ");
				sql.append(" select id from bas_size  ");
				sql.append(" where fk_bl_id =(select fk_bl_id from bas_size where id='" + sizeoid + "')) ");
				sql.append(" AND id not in( ");
				sql.append(" select bl_studentid from bus_leave where to_days(bl_begdate) = to_days(now())) ");
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
	public int iCardData(String OID,String cardno,String sizeoid,HttpServletRequest request,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		//根据站点oid判断是否是终点下车(起点上车)还是普通站点 	0普通站点;1上学下车/放学上车
		int lt1=getSizeIsBorE(sizeoid);
		
		//UUID ID = UUID.randomUUID();
		StringBuffer sql = new StringBuffer(
				"INSERT INTO `bus_cardinfo` (`id`, `bc_cardno`, `bc_datetime`, `size_oid`,`size_status`) ");
		sql.append("VALUES ('" + OID + "','" + cardno + "',now(),'"+sizeoid+"','"+lt1+"' );");

		//System.out.println("iCardData sql..." + ";" + sql.toString());

		int sc = this.systemService.executeSql(sql.toString());
		System.out.println("iCardData sql..." + ";" + sql.toString()+";"+String.valueOf(sc));
		
		
		//推送消息
		try {
			this.doSendTMessage_SK(OID,sizeoid);
		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sc;
		
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
			sc=qryBusLoc(sopenid);
			if (sc!=null){
				try {
					doSendTMessage_QryBusLoc(sc[0],sc[1],sc[2],sopenid);
				} catch (WexinReqException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					doSendTMessage_QryBusLoc("---","---","---",sopenid);
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
		StringBuffer sql = new StringBuffer("SELECT a.bc_cardno,bc_datetime,b.bl_sizeid,b.bl_name,b.bl_size,c.bs_seq ");
		sql.append(" from bus_cardinfo a ");
		sql.append(" LEFT JOIN bas_student b ON a.bc_cardno=b.bs_cardno ");
		sql.append(" LEFT JOIN bas_size c ON b.bl_sizeid=c.id ");
		sql.append(" where bl_name=(SELECT b.bl_name ");
		sql.append(" from bus_openid a LEFT JOIN bas_student b ON a.bs_studentid=b.id ");
		sql.append(" WHERE a.bo_openid='"+openid+"') ");
		sql.append(" order by c.bs_seq desc LIMIT 1 ");
		System.out.println("qryBusLoc sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		if(listTree.size()==1){
			sc[0]=listTree.get(0).get("bl_name").toString();
			sc[1]=listTree.get(0).get("bl_size").toString();
			sc[2]=listTree.get(0).get("bc_datetime").toString();			
		}else{
			sc=null;
		}


		return sc;	
		
		
	}
	
	
}
