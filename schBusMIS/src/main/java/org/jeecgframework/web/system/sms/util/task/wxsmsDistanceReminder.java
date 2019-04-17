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

import com.jeecg.bascontrail.entity.BusUploaddataEntity;
import com.jeecg.basstudent.controller.basWXController;
import com.jeecg.basstudent.entity.ConvertionUtils;
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
		System.out.println("执行电子围栏提醒消息开始...");

		List<BasStudentInfoEntity> listBsie = systemService.findByProperty(BasStudentInfoEntity.class, "bsStatus", "Y");
		List<String> listDeviceid = new ArrayList<String>();
		List<String> allDeviceid = new ArrayList<String>();
		String strSize = "";
		// 分组
		for (BasStudentInfoEntity t : listBsie) {
			if (!("".equals(t.getBsDeviceid()) || t.getBsDeviceid()==null)) {
				allDeviceid.add(t.getBsDeviceid());
				// 每50个deviceid去访问
				if (allDeviceid.size() % 50 == 0) {
					listDeviceid.add(strSize);
					strSize = "";
				} else {
					strSize += t.getBsDeviceid() + ",";
				}
			}
		}
		if (strSize != "")
			listDeviceid.add(strSize);
		// 访问并获取需要预警的值
		List<String> noticeList = this.CompareRemoteTime(listDeviceid);

		// 将信息保存到数据库表
		for (String job : noticeList) {
			String deviceId=JSONObject.fromObject(job).getString("deviceId");
			List<BasStudentInfoEntity> listBse=systemService.findByProperty(BasStudentInfoEntity.class, "bsDeviceid", deviceId);
			if(listBse.size()>0)
			{
				BusUploaddataEntity bce=new BusUploaddataEntity();
				bce.setBsStudentid(listBse.get(0).getId());
				bce.setBuData(job);
				bce.setCreateDate(Calendar.getInstance().getTime());
				systemService.save(bce);
			}
		}
		System.out.println("执行电子围栏提醒消息结束...");
	}

	/**
	 * 获取超过24小时需要预警的值
	 * 
	 * @param listDeviceid
	 * @return
	 */
	private List<String> CompareRemoteTime(List<String> listDeviceid) {
		// 需要提醒的设备
		List<String> noticeList = new ArrayList<String>();
		for (String deviceids : listDeviceid) {
			System.out.println("开始执行:" + deviceids);
			JSONObject json = new JSONObject();
			List<Map<String, Object>> dataObject = new ArrayList<Map<String, Object>>();
			String s1 = "RemoteUrlGets";
			String s3 = "RemoteUrlAccessToken";
			StringBuffer sql = new StringBuffer(
					"SELECT bc.cf_code,bc.cf_name,bc.cf_value FROM bus_config bc where bc.cf_code='" + s1
							+ "' or bc.cf_code='" + s3 + "'");
			dataObject = this.systemService.findForJdbc(sql.toString());// this.systemService.findHql(hql.toString());
			// 开始远程访问
			String requestUrl = "";
			RequestLocationsDevice rlsd = new RequestLocationsDevice();
			rlsd.setDEVICEIDS(deviceids);
			for (Map<String, Object> o : dataObject) {
				if (s1.equals(o.get("cf_code").toString()))
					requestUrl = o.get("cf_value").toString();
				if (s3.equals(o.get("cf_code").toString()))
					rlsd.setAccessToken(o.get("cf_value").toString());
			}
			System.out.println("访问->" + requestUrl);
			if (requestUrl != "") {
				JSONObject ob = JSONObject.fromObject(rlsd);
				System.out.println(ob.toString());
				json = HttpRequestPost.doPost(requestUrl, ob);
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
				for (String deviceid : deviceids.split(",")) {
					if (deviceid != null && deviceids!="")
						if (json.toString().contains(deviceid)) {
							net.sf.json.JSONArray array = net.sf.json.JSONArray
									.fromObject(json.get(deviceid).toString());// 将json字符串转成json数组
							for (int i = 0; i < array.size(); i++) {// 循环json数组
								JSONObject job = (JSONObject) array.get(i);// 得到json对象
								// 时间转换
								if (job.has("deviceId")) {
									if (job.getString("type").equals("2")) {
										try {
											// 超过一天未获取数据，则报警
											Date remoteTime = df.parse(job.getString("device_time"));
											Date nowTime = Calendar.getInstance().getTime();
											long diff = nowTime.getTime() - remoteTime.getTime();
											long hour = diff / (1000 * 60 * 60);
											if (hour > 24)
											{
												noticeList.add(job.toString());
											}
										} catch (Exception e) {
											// TODO Auto-generated catch block
											//e.printStackTrace();
										}
									}
								}
							}
						}
				}
			}
		}

		return noticeList;
	}

	/*private void NoticePerson(List<String> noticeList) {
		try {
			String accessToken = wxutils.getAcctonken();
			TemplateMessageSendResult msgSend = new TemplateMessageSendResult();
			// 将信息进行微信推送给用户
			for (String s : noticeList) {
				Map<String, TemplateData> data = new HashMap<String, TemplateData>();
				List<BasStudentInfoEntity> listO = systemService.findByProperty(BasStudentInfoEntity.class,
						"bsDeviceid", s);
				if (listO.size() > 0) {
					data.put("first", new TemplateData("尊敬的家长，你的小孩学生卡24小时未上传数据。", "#173177"));
					data.put("keyword1", new TemplateData(listO.get(0).getBcGrade()+" "+listO.get(0).getBcName(), "#FF0000"));
					data.put("keyword2", new TemplateData(listO.get(0).getBsName(), "#173177"));
					msgSend.setTemplate_id(basWXController.Templateid_notice);
					try {
						msgSend.setTouser("");
						msgSend.setData(data);
						JwSendTemplateMsgAPI.sendTemplateMsg(accessToken, msgSend);
					} catch (WexinReqException e) {
					}
				}
			}

		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

	}*/

}
