/**
 * 
 */
package com.schbusmis.wx.service;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;


import com.schbusmis.wx.entity.MsgBox;

/**
 * @author dev001
 *
 */
public interface wxServiceI extends CommonService {
	
	public List<MsgBox> getMsgBox_UP();
	public List<MsgBox> getMsgBox_LO();
	public List<MsgBox> getMsgBox_WR();

}
