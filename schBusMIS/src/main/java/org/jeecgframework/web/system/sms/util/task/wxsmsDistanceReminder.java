/**
 * 
 */
package org.jeecgframework.web.system.sms.util.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.jeecg.bascontrail.entity.BusConfigEntity;
import com.jeecg.bascontrail.entity.BusOpenidEntity;
import com.jeecg.bascontrail.entity.BusUploaddataEntity;
import com.jeecg.basstudent.controller.basWXController;
import com.jeecg.basstudent.entity.ConvertionUtils;
import com.jeecg.basstudent.entity.Distance;
import com.jeecg.basstudent.entity.HttpRequestPost;
import com.jeecg.basstudent.entity.RequestLocationsDevice;
import com.jeecg.basstudent.entity.wxutils;
import com.jeecg.basstudentinfo.entity.BasStudentInfoEntity;

import net.sf.json.JSONObject;

/**
 * @author zhou 学生出了电子围栏，进行提醒报警
 */
public class wxsmsDistanceReminder implements Job {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Autowired
	private SystemService systemService;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		run();
	}

	public void run() {
		String query="select bs_deviceid from vw_wxremind";
		List<String> listDeviceid = systemService.findListbySql(query);
		if(listDeviceid.size()>0)
		{
			System.out.println("执行电子围栏提醒消息开始...");
			this.NoticePerson(listDeviceid);
			System.out.println("执行电子围栏提醒消息结束...");
		}
	}

	/*
	 * 超出电子围栏微信提醒
	 */
	private void NoticePerson(List<String> noticeList) {
		try {
			String accessToken = wxutils.getAcctonken();
			TemplateMessageSendResult msgSend = new TemplateMessageSendResult();
			// 将信息保存到数据库表
			for (String deviceId : noticeList) {
					List<BasStudentInfoEntity> listBse = systemService.findByProperty(BasStudentInfoEntity.class,
							"bsDeviceid", deviceId);
					for (BasStudentInfoEntity t : listBse) {
						if (t.getBsStatus().equals("Y")) {
							try {
								//通知家长
								Map<String, TemplateData> data = new HashMap<String, TemplateData>();
								data.put("first", new TemplateData("尊敬的家长，你的小孩在监控范围内，超出电子围栏。", "#173177"));
								data.put("keyword1",
										new TemplateData(t.getBcGrade() + " " + t.getBcName(), "#FF0000"));
								data.put("keyword2", new TemplateData(t.getBsName(), "#173177"));
								data.put("keyword3", new TemplateData("--", "#173177"));
								List<BusOpenidEntity> listboe = systemService.findByProperty(BusOpenidEntity.class,
										"bsStudentid", t.getId());
								for(BusOpenidEntity boe :listboe)
								{
									msgSend.setTemplate_id(basWXController.Templateid_notice);
									msgSend.setTouser(boe.getBoOpenid());
									msgSend.setData(data);
									JwSendTemplateMsgAPI.sendTemplateMsg(accessToken, msgSend);	
									systemService.executeSql("update bus_mapfence set is_send='Y' where device_id='"+deviceId+"'");
								}
								
								//通知班主任
								Map<String, TemplateData> dataTeacher = new HashMap<String, TemplateData>();
								dataTeacher.put("first", new TemplateData("尊敬的班主任，你的学生在监控范围内，超出电子围栏。", "#173177"));
								dataTeacher.put("keyword1",
										new TemplateData(t.getBcGrade() + " " + t.getBcName(), "#FF0000"));
								dataTeacher.put("keyword2", new TemplateData(t.getBsName(), "#173177"));
								dataTeacher.put("keyword3", new TemplateData("--", "#173177"));
								String query="SELECT tu.openid FROM t_s_base_user tu,bas_class bc,bas_student bs WHERE tu.id = bc.bc_personid and bc.id=bs.bc_id and bs.bs_deviceid='"+deviceId+"'";
								List<String> listOpenid = systemService.findListbySql(query);
								for(String s :listOpenid)
								{
									msgSend.setTemplate_id(basWXController.Templateid_notice);
									msgSend.setTouser(s);
									msgSend.setData(dataTeacher);
									JwSendTemplateMsgAPI.sendTemplateMsg(accessToken, msgSend);	
									systemService.executeSql("update bus_mapfence set is_send='Y' where device_id='"+deviceId+"'");
								}
								
							} catch (WexinReqException e) {
							}
						}
					}
			}

		} catch (WexinReqException e) { // TODO Auto-generated catch block //
			e.printStackTrace();
		}

	}

}
