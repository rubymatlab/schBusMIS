/**
 * 
 */
package com.schbusmis.wx.service.impl;

import java.util.List;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.system.pojo.base.DictEntity;

import com.schbusmis.wx.entity.MsgBox;
import com.schbusmis.wx.service.wxService;

/**
 * @author dev_zhu
 *
 */
public class wxServiceImpl extends CommonServiceImpl implements wxService {

	/* (non-Javadoc)
	 * @see com.schbusmis.wx.service.wxService#getMsgBox_UP()
	 */
	@Override
	public List<MsgBox> getMsgBox_UP() {
		List<MsgBox> msgboxlist = null;
		MsgBox msgbox=null;
		
		msgbox.setFirst("尊敬的家长，你的小孩已刷卡上车。");
		msgbox.setKeyword1("张三");
		msgbox.setKeyword2("2018-10-22 08:02");
		msgbox.setKeyword3("惠州政府站");
		msgbox.setRemark("点击详情，可查看上车照片！");
		msgbox.setTemplateid("6RJ6XMEWv-ngnzQU56VLlV8Ey0JCjxQSnKHqoSMQQWQ");
		msgbox.setTouser("oyXOc0y_YrRmjq0f0B0RL0y7BHDg");
		msgbox.setUrl("https://www.baidu.com");
		
		msgboxlist.add(msgbox);
		return msgboxlist;
	}

	/* (non-Javadoc)
	 * @see com.schbusmis.wx.service.wxService#getMsgBox_LO()
	 */
	@Override
	public List<MsgBox> getMsgBox_LO() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.schbusmis.wx.service.wxService#getMsgBox_WR()
	 */
	@Override
	public List<MsgBox> getMsgBox_WR() {
		// TODO Auto-generated method stub
		return null;
	}

}
