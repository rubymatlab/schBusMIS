<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>加密卡号基础资料</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="vwBasCarinfoController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${vwBasCarinfoPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								卡号:
							</label>
						</td>
						<td class="value">
						    <input id="bsCardno" name="bsCardno" type="text" maxlength="36" style="width: 150px" class="inputxt"  ignore="ignore"  value='${vwBasCarinfoPage.bsCardno}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">卡号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								加密卡号:
							</label>
						</td>
						<td class="value">
						    <input id="bsImei" name="bsImei" type="text" maxlength="36" style="width: 150px" class="inputxt"  ignore="ignore"  value='${vwBasCarinfoPage.bsImei}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">加密卡号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								绑定学生姓名:
							</label>
						</td>
						<td class="value">
						    <input id="bsName" name="bsName" type="text" maxlength="36" style="width: 150px" class="inputxt"  ignore="ignore"  value='${vwBasCarinfoPage.bsName}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">绑定学生姓名</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								设备ID:
							</label>
						</td>
						<td class="value">
						    <input id="bsDeviceid" name="bsDeviceid" type="text" maxlength="36" style="width: 150px" class="inputxt"  ignore="ignore"  value='${vwBasCarinfoPage.bsDeviceid}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">设备ID</label>
						</td>
					</tr>
				
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/jeecg/basstudent/vwBasCarinfo.js"></script>		
