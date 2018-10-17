/**
 * 
 */
package com.schbusmis.wx.controller;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeecg.demo.entity.JeecgDemoExcelEntity;

/**
 * @author ZhuMingXing
 *
 */

@Controller
@RequestMapping("/baswxctr")
public class baswxctr extends BaseController {

	@RequestMapping(params = "doSendTemplateMessage")
	@ResponseBody
	public AjaxJson doSendTemplateMessage(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();

		message = "发送消息模板成功";

		j.setMsg(message);
		//System.out.println("wxoputing...");
		return j;
	}	
	
}


