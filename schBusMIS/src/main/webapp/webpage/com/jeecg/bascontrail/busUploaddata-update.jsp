<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>24小时未上传数据</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="busUploaddataController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${busUploaddataPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								学生ID:
							</label>
						</td>
						<td class="value">
						    <input id="bsStudentid" name="bsStudentid" type="text" maxlength="36" style="width: 150px" class="inputxt"  ignore="ignore"  value='${busUploaddataPage.bsStudentid}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">学生ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								最后上传时间:
							</label>
						</td>
						<td class="value">
						    <input id="buData" name="buData" type="text" maxlength="2000" style="width: 150px" class="inputxt"  ignore="ignore"  value='${busUploaddataPage.buData}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">最后上传时间</label>
						</td>
					</tr>
				
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/jeecg/bascontrail/busUploaddata.js"></script>		
