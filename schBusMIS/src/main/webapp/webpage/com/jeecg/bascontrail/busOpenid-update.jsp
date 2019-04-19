<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>微信绑定管理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="busOpenidController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${busOpenidPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								学生:
							</label>
						</td>
						<td class="value">
						    <input id="bsStudentid" name="bsStudentid" type="text" maxlength="36" style="width: 150px" class="inputxt"  ignore="ignore"  value='${busOpenidPage.bsStudentid}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">学生</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								绑定ID:
							</label>
						</td>
						<td class="value">
						    <input id="boOpenid" name="boOpenid" type="text" maxlength="50" style="width: 150px" class="inputxt"  ignore="ignore"  value='${busOpenidPage.boOpenid}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">绑定ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								绑定时间:
							</label>
						</td>
						<td class="value">
						    <input id="boBinddatetime" name="boBinddatetime" type="text" maxlength="32" style="width: 150px" class="inputxt"  ignore="ignore"  value='${busOpenidPage.boBinddatetime}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">绑定时间</label>
						</td>
					</tr>
				
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/jeecg/bascontrail/busOpenid.js"></script>		
