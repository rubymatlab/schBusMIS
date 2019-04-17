<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>配置表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="busConfigController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${busConfigPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								配置编码:
							</label>
						</td>
						<td class="value">
						    <input id="cfCode" name="cfCode" type="text" maxlength="36" style="width: 150px" class="inputxt"  ignore="ignore"  value='${busConfigPage.cfCode}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">配置编码</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								配置名称:
							</label>
						</td>
						<td class="value">
						    <input id="cfName" name="cfName" type="text" maxlength="100" style="width: 150px" class="inputxt"  ignore="ignore"  value='${busConfigPage.cfName}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">配置名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								配置值:
							</label>
						</td>
						<td class="value">
						    <input id="cfValue" name="cfValue" type="text" maxlength="500" style="width: 150px" class="inputxt"  ignore="ignore"  value='${busConfigPage.cfValue}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">配置值</label>
						</td>
					</tr>
				
					<tr>
						<td align="right">
							<label class="Validform_label">
								配置描述:
							</label>
						</td>
						<td class="value" >
						  	 	<textarea id="cfDesc" style="height:auto;width:95%;" class="inputxt" rows="6" name="cfDesc"  ignore="ignore" >${busConfigPage.cfDesc}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">配置描述</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/jeecg/bascontrail/busConfig.js"></script>		
