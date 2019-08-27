<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>学生门禁信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="basStudentdoorinfoController.do?doAdd" >
					<input id="id" name="id" type="hidden" value="${basStudentdoorinfoPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							卡编号:
						</label>
					</td>
					<td class="value">
					     	 <input id="bsSeq" name="bsSeq" type="text" maxlength="15" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">卡编号</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							卡号:
						</label>
					</td>
					<td class="value">
					     	 <input id="bsCardno" name="bsCardno" type="text" maxlength="30" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">卡号</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							门设备号:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="bsMacno" type="list"  dictTable="vw_basmacno" dictField="macno" dictText="macno"  defaultVal="${basStudentdoorinfoPage.bsMacno}" hasLabel="false"  title="门设备号" ></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">门设备号</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							创建日期:
						</label>
					</td>
					<td class="value">
							   <input id="createDate" name="createDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">创建日期</label>
						</td>
				</tr>
				
				
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/jeecg/basstudentinfo/basStudentdoorinfo.js"></script>		
