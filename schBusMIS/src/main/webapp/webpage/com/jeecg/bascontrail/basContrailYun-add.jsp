<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>云在线通道</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="basContrailYunController.do?doAdd" >
					<input id="id" name="id" type="hidden" value="${basContrailYunPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							云在线门禁key值:
						</label>
					</td>
					<td class="value">
					     	 <input id="bcyKey" name="bcyKey" type="text" maxlength="32" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">云在线门禁key值</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							云在线门禁名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="bcyDesc" name="bcyDesc" type="text" maxlength="32" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">云在线门禁名称</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							云在线门禁value值:
						</label>
					</td>
					<td class="value">
					     	 <input id="bcyValue" name="bcyValue" type="text" maxlength="100" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">云在线门禁value值</label>
						</td>
				</tr>
				
				
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/jeecg/bascontrail/basContrailYun.js"></script>		
