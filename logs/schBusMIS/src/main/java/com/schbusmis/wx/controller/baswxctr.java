/**
 * 
 */
package com.schbusmis.wx.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.web.system.service.SystemService;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.model.message.TemplateData;
import org.jeewx.api.core.req.model.message.TemplateMessageSendResult;
import org.jeewx.api.wxsendmsg.JwSendTemplateMsgAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeecg.demo.entity.JeecgDemoExcelEntity;
import com.schbusmis.wx.entity.MsgBox;
import com.schbusmis.wx.service.wxService;

/**
 * @author dev_zhu
 *
 */

@Controller
@RequestMapping("/baswxctr")
public class baswxctr extends BaseController {

	private static final String accessToken = "14_mUOWKuaKZtUlgFTqv4I-UpjGyzN8OoooTPXldLRHOPn6ElOCn-i4T1IegDAOcDGdneJi6Jg63vESfpXFe-72DMVTdq4RrA7H0DxWI2zMzEGpJlOZwKBkik_H_-IChqp2p0qP9G5NQwJgpNA-QRZfAFAJKS";
	
	@Autowired
	private wxService wxservice;
	
	//上车提醒
	@RequestMapping(params = "doSendTMessage_UP")
	@ResponseBody
	public AjaxJson doSendTMessage_UP(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		List<MsgBox> msgboxlist = null;
		msgboxlist=wxservice.getMsgBox_UP();	//获取发送消息体
		
		TemplateMessageSendResult msgSend = new TemplateMessageSendResult();
		Map<String, TemplateData> data = new HashMap<String, TemplateData>();
		data.put("first", new TemplateData(msgboxlist.get(0).getFirst().toString(),"#00FF00"));
		data.put("keyword1", new TemplateData(msgboxlist.get(0).getKeyword1().toString(),"#FF0000"));
		data.put("keyword2", new TemplateData(msgboxlist.get(0).getKeyword2().toString(),"#173177"));
		data.put("keyword3", new TemplateData(msgboxlist.get(0).getRemark().toString(),"#173177"));
		data.put("remark", new TemplateData(msgboxlist.get(0).getRemark().toString(),"#FF0000"));
		msgSend.setTemplate_id(msgboxlist.get(0).getTemplateid().toString());
		msgSend.setTouser(msgboxlist.get(0).getTouser().toString());
		msgSend.setUrl(msgboxlist.get(0).getUrl().toString());
		msgSend.setData(data);
		try {
			JwSendTemplateMsgAPI.sendTemplateMsg(accessToken, msgSend);
			message = "发送消息模板成功";
		} catch (WexinReqException e) {
			message = "发送消息模板失败";
			e.printStackTrace();
		}
		
		j.setMsg(message);
		System.out.println("wxoputing...");
		return j;
	}	
	
	//下车提醒
	@RequestMapping(params = "doSendTMessage_LO")
	@ResponseBody
	public AjaxJson doSendTMessage_LO(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		
		
		TemplateMessageSendResult msgSend = new TemplateMessageSendResult();
		Map<String, TemplateData> data = new HashMap<String, TemplateData>();
		data.put("first", new TemplateData("尊敬的家长，你的小孩已刷卡下车。","#00FF00"));
		data.put("keyword1", new TemplateData("李四","#FF0000"));
		data.put("keyword2", new TemplateData("2018-10-22 18:02","#173177"));
		data.put("keyword3", new TemplateData("惠州政府站","#173177"));
		data.put("remark", new TemplateData("点击详情，可查看下车照片！","#FF0000"));
		msgSend.setTemplate_id("koemcE0LAqRKN3s1dFbTALKt1pNjBEqBOQNNyYF5l7U");
		msgSend.setTouser("oyXOc0y_YrRmjq0f0B0RL0y7BHDg");
		msgSend.setUrl("https://www.baidu.com");
		msgSend.setData(data);
		try {
			JwSendTemplateMsgAPI.sendTemplateMsg(accessToken, msgSend);
			message = "发送消息模板成功";
		} catch (WexinReqException e) {
			message = "发送消息模板失败";
			e.printStackTrace();
		}
		
		j.setMsg(message);
		System.out.println("wxoputing...");
		return j;
	}	
	
	//未上车提醒
	@RequestMapping(params = "doSendTMessage_WR")
	@ResponseBody
	public AjaxJson doSendTMessage_WR(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		
		
		TemplateMessageSendResult msgSend = new TemplateMessageSendResult();
		Map<String, TemplateData> data = new HashMap<String, TemplateData>();
		data.put("first", new TemplateData("尊敬的家长，你的小孩未上车。","#00FF00"));
		data.put("keyword1", new TemplateData("李四","#FF0000"));
		data.put("keyword2", new TemplateData("2018-10-22 18:02","#173177"));
		data.put("keyword3", new TemplateData("惠州政府站","#173177"));
		data.put("remark", new TemplateData("以上信息，特警示！","#FF0000"));
		msgSend.setTemplate_id("WcmGveeAyW-Z2rxs_bE6RkhsqYhTI4hLosxmoizslyg");
		msgSend.setTouser("oyXOc0y_YrRmjq0f0B0RL0y7BHDg");
		msgSend.setUrl("https://www.baidu.com");
		msgSend.setData(data);
		try {
			JwSendTemplateMsgAPI.sendTemplateMsg(accessToken, msgSend);
			message = "发送消息模板成功";
		} catch (WexinReqException e) {
			message = "发送消息模板失败";
			e.printStackTrace();
		}
		
		j.setMsg(message);
		System.out.println("wxoputing...");
		return j;
	}	
}


