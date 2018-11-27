/**
 * 
 */
package org.jeecgframework.web.system.sms.util.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.web.system.service.SystemService;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.model.message.TemplateData;
import org.jeewx.api.core.req.model.message.TemplateMessageSendResult;
import org.jeewx.api.wxsendmsg.JwSendTemplateMsgAPI;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeecg.basstudent.entity.wxutils;

/**
 * @author dev001
 *
 */
@Service("wxsmsUP")
public class wxsmsUP implements Job {

	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	private static final String Templateid_UP="J_1DwXtF8IorKSmpxR3I2v_kb-rLykW4kb8-7E0RQpw";
	@Autowired
	private SystemService systemService;
	
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		run();
	}
	
	public void run() {
		System.out.println("执行上车提醒消息发送开始...");
		String accessToken="";
		try {
			accessToken = wxutils.getAcctonken();
		} catch (WexinReqException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TemplateMessageSendResult msgSend = new TemplateMessageSendResult();
		
		List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();

		//getData				
		StringBuffer sql = new StringBuffer("SELECT a.id, b.bs_name,DATE_FORMAT(a.bc_datetime,'%Y-%m-%d %H:%i:%s')as bc_datetime,CONCAT(b.bl_name,bl_size)as place ,c.bo_openid from bus_cardinfo a ");
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
			msgSend.setUrl("http://tdcq.natapp1.cc/schBusMIS/page/imges/carding.jpg");
			msgSend.setData(data);
			try {
				JwSendTemplateMsgAPI.sendTemplateMsg(accessToken, msgSend);
				//issendcard(o.get("id").toString());
				System.out.println("发送消息模板成功...");
			} catch (WexinReqException e) {
				System.out.println("发送消息模板失败...");
				e.printStackTrace();
			}
		}
		System.out.println("执行上车提醒消息发送结束...");		
	}

}
