/**
 * 
 */
package com.jeecg.basstudent.controller;

import java.io.IOException;
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

import net.sf.json.JSONObject;

/**
 * @author dev_zhu
 *
 */

@Controller
@RequestMapping("/baswxcontroller")
public class basWXController extends BaseController {
	
	//private static final String accessToken = "15_5qBTtwZOnqMTF2DYtT6TQ4JjybtyDTufFNgIOUB3FlcDV1NFsPRXL4gv6xHDcOSH6GYd-Jyf7ekAha2RRdHGiRRYpA9zQjh_0Ybw3NJJSLw5tb2zMpm1byDB0n-rEXZUxykT1G65yZO6GoyeFOEeAHAAJZ";
	private static final String Templateid_UP="J_1DwXtF8IorKSmpxR3I2v_kb-rLykW4kb8-7E0RQpw";
	private static final String Templateid_LO="SYi3dsdmvq7CUw3M3E0Mfl63xLl7wAo-4oJY5v126O0";
	private static final String Templateid_WR="jqFKmBdfNU-KhMW4n36iBk5el3Qd_DPVtpvR5GRfDyM";
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

	//上车通知
	@RequestMapping(params = "doSendTMessage_UP")
	@ResponseBody
	public AjaxJson doSendTMessage_UP(HttpServletRequest request) throws WexinReqException {
		String accessToken=wxutils.getAcctonken();
		TemplateMessageSendResult msgSend = new TemplateMessageSendResult();
		
		String message = null;
		AjaxJson j = new AjaxJson();
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();

		//getData				
		StringBuffer sql = new StringBuffer("SELECT b.bs_name,DATE_FORMAT(a.bc_datetime,'%Y-%m-%d %H:%i:%s')as bc_datetime,CONCAT(b.bl_name,bl_size)as place ,c.bo_openid from bus_cardinfo a ");
		sql.append("left join bas_student b on a.bc_cardno=b.bs_cardno ");
		sql.append("left join bus_openid c on b.id=c.bs_studentid ");
		sql.append("Where bc_sended=0 AND c.bo_openid is not NULL ");
		sql.append("AND bc_datetime >=CONCAT(DATE_FORMAT(a.bc_datetime,'%Y-%m-%d'),' 07:00') ");
		sql.append("AND bc_datetime <=CONCAT(DATE_FORMAT(a.bc_datetime,'%Y-%m-%d'),' 09:00') ");
		//System.out.println("getDate sql..."+";"+sql.toString());
		
		listTree = this.systemService.findForJdbc(sql.toString());// this.systemService.findHql(hql.toString());
		
		for (Map<String, Object> o : listTree) {			
			Map<String, TemplateData> data = new HashMap<String, TemplateData>();
			data.put("first", new TemplateData("尊敬的家长，你的小孩已刷卡上车。","#173177"));
			data.put("keyword1", new TemplateData(o.get("bs_name").toString(),"#FF0000"));
			data.put("keyword2", new TemplateData(o.get("bc_datetime").toString(),"#173177"));
			data.put("keyword3", new TemplateData(o.get("place").toString(),"#173177"));
			data.put("remark", new TemplateData("点击详情，可查看上车照片！","#173177"));
			msgSend.setTemplate_id(Templateid_UP);
			msgSend.setTouser(o.get("bo_openid").toString());
			msgSend.setUrl("https://www.baidu.com");
			msgSend.setData(data);
			try {
				JwSendTemplateMsgAPI.sendTemplateMsg(accessToken, msgSend);
				message = "发送消息模板成功";
			} catch (WexinReqException e) {
				message = "发送消息模板失败";
				e.printStackTrace();
			}
		}

		j.setMsg(message);
		System.out.println("上车通知..."+";"+message);
		return j;
	}	
	
	//下车提醒
	@RequestMapping(params = "doSendTMessage_LO")
	@ResponseBody
	public AjaxJson doSendTMessage_LO(HttpServletRequest request) throws WexinReqException {
		String accessToken=wxutils.getAcctonken();
		TemplateMessageSendResult msgSend = new TemplateMessageSendResult();
		
		String message = null;
		AjaxJson j = new AjaxJson();
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();

		//getData				
		StringBuffer sql = new StringBuffer("SELECT b.bs_name,DATE_FORMAT(a.bc_datetime,'%Y-%m-%d %H:%i:%s')as bc_datetime,CONCAT(b.bl_name,bl_size)as place ,c.bo_openid from bus_cardinfo a ");
		sql.append("left join bas_student b on a.bc_cardno=b.bs_cardno ");
		sql.append("left join bus_openid c on b.id=c.bs_studentid ");
		sql.append("Where bc_sended=0 AND c.bo_openid is not NULL ");
		sql.append("AND bc_datetime >=CONCAT(DATE_FORMAT(a.bc_datetime,'%Y-%m-%d'),' 17:00') ");
		sql.append("AND bc_datetime <=CONCAT(DATE_FORMAT(a.bc_datetime,'%Y-%m-%d'),' 19:00') ");
		System.out.println("getDate sql..."+";"+sql.toString());
		
		listTree = this.systemService.findForJdbc(sql.toString());// this.systemService.findHql(hql.toString());
		
		for (Map<String, Object> o : listTree) {			
			Map<String, TemplateData> data = new HashMap<String, TemplateData>();
			data.put("first", new TemplateData("尊敬的家长，你的小孩已刷卡下车。","#173177"));
			data.put("keyword1", new TemplateData(o.get("bs_name").toString(),"#FF0000"));
			data.put("keyword2", new TemplateData(o.get("bc_datetime").toString(),"#173177"));
			data.put("keyword3", new TemplateData(o.get("place").toString(),"#173177"));
			data.put("remark", new TemplateData("点击详情，可查看下车照片！","#173177"));
			msgSend.setTemplate_id(Templateid_LO);
			msgSend.setTouser(o.get("bo_openid").toString());
			msgSend.setUrl("https://www.baidu.com");
			msgSend.setData(data);
			try {
				JwSendTemplateMsgAPI.sendTemplateMsg(accessToken, msgSend);
				message = "发送消息模板成功";
			} catch (WexinReqException e) {
				message = "发送消息模板失败";
				e.printStackTrace();
			}
		}
		j.setMsg(message);
		System.out.println("下车提醒..."+";"+message);
		return j;
	}	
	
	//未上车提醒
	@RequestMapping(params = "doSendTMessage_WR")
	@ResponseBody
	public AjaxJson doSendTMessage_WR(String cardno,HttpServletRequest request) throws WexinReqException {
		String accessToken=wxutils.getAcctonken();
		TemplateMessageSendResult msgSend = new TemplateMessageSendResult();
		
		String message = null;
		AjaxJson j = new AjaxJson();
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();

		//getData				
		StringBuffer sql = new StringBuffer("SELECT b.bs_name,CONCAT(b.bl_name,bl_size)as place ,c.bo_openid from bas_student b ");
		sql.append("left join bus_openid c on b.id=c.bs_studentid ");
		sql.append("Where c.bo_openid is not NULL ");
		sql.append("AND b.bs_cardno='"+cardno+"'");
		System.out.println("getDate sql..."+";"+sql.toString());
		
		listTree = this.systemService.findForJdbc(sql.toString());// this.systemService.findHql(hql.toString());
			
		for (Map<String, Object> o : listTree) {			
			Map<String, TemplateData> data = new HashMap<String, TemplateData>();
			data.put("first", new TemplateData("尊敬的家长，你的小孩未上车。","#173177"));
			data.put("keyword1", new TemplateData(o.get("bs_name").toString(),"#FF0000"));
			data.put("keyword2", new TemplateData("-----","#173177"));
			data.put("keyword3", new TemplateData(o.get("place").toString(),"#173177"));
			data.put("remark", new TemplateData("以上信息，特警示！","#173177"));
			msgSend.setTemplate_id(Templateid_WR);
			msgSend.setTouser(o.get("bo_openid").toString());

			msgSend.setData(data);
			try {
				JwSendTemplateMsgAPI.sendTemplateMsg(accessToken, msgSend);
				message = "发送消息模板成功";
			} catch (WexinReqException e) {
				message = "发送消息模板失败";
				e.printStackTrace();
			}
		}

		j.setMsg(message);
		System.out.println("wxoputing..."+accessToken);
		return j;
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
	
		String url = "redirect:http://devzhu.hk1.mofasuidao.cn/schBusMIS/page/index.html?openid="+sopenid+"&accessToken="+jsonStr; 
		System.out.println("url-->:"+url);
		return new ModelAndView(url);
	}	
	
	//binding
	//绑定
	@RequestMapping(params = "binding")
	@ResponseBody
	public int binding(String tell,String openid,HttpServletRequest request) throws WexinReqException {
		System.out.println("binding参数输出1:"+tell+";"+openid);
		int i=isBinded(openid);
		System.out.println("binding参数输出2"+i);
		return i;
	}
	
	//unBinding
	//解除绑定
	@RequestMapping(params = "unBinding")
	@ResponseBody
	public int unBinding(String openid,HttpServletRequest request) throws WexinReqException {
		System.out.println("binding参数输出1:"+openid);
		int i=isUnBinded(openid);
		System.out.println("binding参数输出2"+i);
		return i;
	}	
	
	//新增openid记录
	@RequestMapping(params = "insertopenid")
	@ResponseBody
	public int insertopenid(String tell,String openid,HttpServletRequest request) throws WexinReqException {
		System.out.println("insertopenid参数输出:" + tell + ";" + openid);
		int i = 0;
		if (isExitsTel(tell) >=1) {
			String stuid = getStudenID(tell);
			i = iopenid(stuid, openid);
		} else {	//手机号码不存在
			i = 0;
		}

		return i;
	}
	
	//获取个人信息
	@RequestMapping(params = "getStudent")
	@ResponseBody	
	public List<Map<String, Object>> getStudent(String openid,HttpServletRequest request){		
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		// getData
		StringBuffer sql = new StringBuffer("SELECT b.bs_name,b.bs_tel FROM bus_openid a ");
		sql.append("LEFT JOIN bas_student b on a.bs_studentid=b.id ");
		sql.append("WHERE a.bo_openid='" + openid + "'");
		System.out.println("getStudent sql..." + ";" + sql.toString());
		listTree = this.systemService.findForJdbc(sql.toString());
		System.out.println("getStudent:"+listTree.get(0).get("bs_name").toString()+";"+listTree.get(0).get("bs_tel").toString());
		return listTree;
	}

	//获取刷卡记录
	@RequestMapping(params = "getcardinfo")
	@ResponseBody		
	public int getcardinfo(String openid,HttpServletRequest request){
		//是否绑定？
		int i=isBinded(openid);
		return i;
	}
	
	//获取刷卡记录02
	@RequestMapping(params = "getcardinfo02")
	@ResponseBody		
	public List<Map<String, Object>> getcardinfo02(String openid,HttpServletRequest request){
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		// getData
		StringBuffer sql = new StringBuffer("SELECT b.bs_cardno,b.bs_name,c.bc_datetime from bus_openid a ");
		sql.append("LEFT JOIN bas_student b on a.bs_studentid=b.id ");
		sql.append("LEFT JOIN bus_cardinfo c on b.bs_cardno=c.bc_cardno ");
		sql.append("where a.bo_openid='" + openid + "'");
		System.out.println("getcardinfo sql..." + ";" + sql.toString());
		listTree = this.systemService.findForJdbc(sql.toString());		
		return listTree;
	}
	
	//请假
	@RequestMapping(params = "leave")
	@ResponseBody		
	public int leave(String begb,String bege,String reason,String openid,HttpServletRequest request){
		System.out.println(begb + ";" + bege + ";" + reason + openid);
		int i = ilevel(begb, bege, reason, openid);
		return i;
	}	
	//请假记录
	@RequestMapping(params = "leavelist")
	@ResponseBody		
	public List<Map<String, Object>> leavelist(String openid,HttpServletRequest request){
		//System.out.println(begb + ";" + bege + ";" + reason + openid);
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		// getData
		StringBuffer sql = new StringBuffer("SELECT a.bl_begdate,a.bl_enddate,a.bl_reason, ");
		sql.append("case  when a.bl_status='0' then '待批' when a.bl_status='1' then '已批'  when a.bl_status='-1' then '未批' else '其他' END as status from bus_leave a ");
		sql.append("LEFT JOIN bus_openid b ON a.bl_studentid=b.bs_studentid ");
		sql.append("WHERE b.bo_openid='" + openid + "'  ");
		sql.append("order by bl_begdate desc ");
		System.out.println("leavelist sql..." + ";" + sql.toString());
		listTree = this.systemService.findForJdbc(sql.toString());		
		return listTree;
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
		return null;
	}
	
	
	
	
	//业务处理
	//openid是否已绑定 1:已绑;0:未绑
	private int isBinded(String sopenid){
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("SELECT count(*) as c FROM bus_openid  ");
		sql.append("WHERE bo_openid='" + sopenid + "'");
		System.out.println("isBinded sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		String sc = listTree.get(0).get("c").toString();

		return Integer.parseInt(sc);		
	}
	
	//解除绑定 1:成功;0:失败
	private int isUnBinded(String sopenid){
		Object ob = null;
		StringBuffer sql = new StringBuffer("DELETE FROM bus_openid ");
		sql.append("WHERE bo_openid='" + sopenid + "'");
		System.out.println("isUnBinded sql..." + ";" + sql.toString());

		int sc = this.systemService.executeSql(sql.toString());

		return sc;	
	}
	
	// 电话号码是否已存在 1:存在;0:不存在
	private int isExitsTel(String stel) {
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();

		StringBuffer sql = new StringBuffer("SELECT count(*) as c From bas_student a ");
		sql.append("WHERE a.bs_tel='" + stel + "'");
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
	private String getStudenID02(String openid){
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer("SELECT a.id from bas_student a  ");
		sql.append("LEFT JOIN bus_openid b ON a.id=b.bs_studentid ");
		sql.append("where b.bo_openid='" + openid + "'");
		System.out.println("getStudenID02 sql..." + ";" + sql.toString());

		listTree = this.systemService.findForJdbc(sql.toString());
		String sc = listTree.get(0).get("id").toString();
		return sc;
	}
	
	//新增openid
	private int iopenid(String stuid,String openid){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String sysdt = df.format(new Date());// new Date()为获取当前系统时间

		StringBuffer sql = new StringBuffer(
				"INSERT INTO bus_openid  (`bs_studentid`, `bo_openid`, `bo_binddatetime`) ");
		sql.append("VALUES ('" + stuid + "','" + openid + "','" + sysdt + "');");

		System.out.println("iopenid sql..." + ";" + sql.toString());

		int sc = this.systemService.executeSql(sql.toString());
		return sc;
	}
	
	//新增请假信息
	private int ilevel(String begb,String bete,String reason,String openid){
		String status = "0";
		String stuID = getStudenID02(openid);
		UUID ID = UUID.randomUUID();
		StringBuffer sql = new StringBuffer(
				"INSERT INTO `bus_leave` (`id`, `bl_studentid`,  `bl_reason`, `bl_begdate`, `bl_enddate`, `bl_status`) ");
		sql.append("VALUES ('" + ID + "','" + stuID + "','" + reason + "','" + begb + "','" + bete + "','" + status
				+ "');");

		System.out.println("iopenid sql..." + ";" + sql.toString());

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
	
}
