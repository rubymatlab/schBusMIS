package org.jeecgframework.web.system.sms.util.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeecgframework.web.system.service.SystemService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jeecg.basstudent.controller.DeviceOperationController;
import com.jeecg.basstudent.entity.HttpRequestPost;
import com.jeecg.basstudent.entity.wxutils;

import net.sf.json.JSONObject;



public class wxcardnoDevice implements Job {

	@Autowired
	private SystemService systemService;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		run();
	}
	
	public void run() {
		System.out.println("执行获取设备ID开始...");
		
		System.out.println("执行获取设备ID结束...");
	}
}
