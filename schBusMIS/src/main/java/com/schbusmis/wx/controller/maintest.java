/**
 * 
 */
package com.schbusmis.wx.controller;

import java.util.HashMap;
import java.util.Map;

import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.model.message.TemplateData;
import org.jeewx.api.core.req.model.message.TemplateMessageSendResult;
import org.jeewx.api.wxsendmsg.JwSendTemplateMsgAPI;
import org.jeewx.api.wxuser.user.JwUserAPI;

/**
 * @author dev_zhu
 *
 */
public class maintest {

	/**
	 * @param args
	 */
	private static final String accessToken = "14_mUOWKuaKZtUlgFTqv4I-UpjGyzN8OoooTPXldLRHOPn6ElOCn-i4T1IegDAOcDGdneJi6Jg63vESfpXFe-72DMVTdq4RrA7H0DxWI2zMzEGpJlOZwKBkik_H_-IChqp2p0qP9G5NQwJgpNA-QRZfAFAJKS";
	public static void main(String[] args) {
/*		try {
			String accesstoken = "14_in0-Zn4yKtqT85X0a6VVeh2TidgX11-SXJnG3QvT4a1pk8_K2UFoG_DcLXMENDc-vZJB6m4zWUVfBiADJLLHUizGCcBD44_e3mBxFk-QsC32djdh8Pms5cmudBWOdR5CTK2Abx1p35cRzpT7SWIaAAAKOA";
			String user_openid = "oyXOc02dBg9sEZlcjwKCYL9s8JRk";
			JwUserAPI.getWxuser(accesstoken, user_openid);

		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("wxoputing...");*/
		
		TemplateMessageSendResult msgSend = new TemplateMessageSendResult();
		Map<String, TemplateData> data = new HashMap<String, TemplateData>();
		data.put("first", new TemplateData("尊敬的家长，你的小孩已刷卡上车。","#00FF00"));
		data.put("keyword1", new TemplateData("张三","#FF0000"));
		data.put("keyword2", new TemplateData("2018-10-18 08:01","#173177"));
		data.put("keyword3", new TemplateData("惠州政府站","#173177"));
		data.put("remark", new TemplateData("点击详情，可查看上车照片！","#FF0000"));
		msgSend.setTemplate_id("6RJ6XMEWv-ngnzQU56VLlV8Ey0JCjxQSnKHqoSMQQWQ");
		msgSend.setTouser("oyXOc0y_YrRmjq0f0B0RL0y7BHDg");
		msgSend.setUrl("https://www.baidu.com");
		msgSend.setData(data);
		try {
			JwSendTemplateMsgAPI.sendTemplateMsg(accessToken, msgSend);
		} catch (WexinReqException e) {
			e.printStackTrace();
		}
		

	}

}
