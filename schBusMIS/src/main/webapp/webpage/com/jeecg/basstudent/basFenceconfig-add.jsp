<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>围栏监控维护</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="basFenceconfigController.do?doAdd" >
					<input id="id" name="id" type="hidden" value="${basFenceconfigPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							监控类型:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="bfType" type="checkbox"  typeGroupCode="bf_status"  defaultVal="${basFenceconfigPage.bfType}" hasLabel="false"  title="监控类型" ></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">监控类型</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							监控名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="bfName" name="bfName" type="text" maxlength="50" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">监控名称</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							监控开始时间:
						</label>
					</td>
					<td class="value">
							   <input id="bfBegin" name="bfBegin" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">监控开始时间</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							监控结束时间:
						</label>
					</td>
					<td class="value">
							   <input id="bfEnd" name="bfEnd" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">监控结束时间</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							处理监控学生:
						</label>
					</td>
					<td class="value">
							<input id="bfStudent" name="bfStudent" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"   onclick="popupClick(this,'bs_cardno','bfStudent','fence_msg')"  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">处理监控学生</label>
						</td>
				</tr>
				
				
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/jeecg/basstudent/basFenceconfig.js"></script>		
