<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>班级资料</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="basClassController.do?doAdd" >
					<input id="id" name="id" type="hidden" value="${basClassPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							年级:
						</label>
					</td>
					<td class="value">
					     	 <input id="bcGrade" name="bcGrade" type="text" maxlength="4" style="width: 150px" class="inputxt"  datatype="n" ignore="checked" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">年级</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							班级名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="bcName" name="bcName" type="text" maxlength="32" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">班级名称</label>
						</td>
				</tr>
				<tr>
					<td align="right" style="display: none;">
						<label class="Validform_label">
							班主任ID:
						</label>
					</td>
					<td class="value" style="display: none;">
							<input id="bcPersonid" name="bcPersonid" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="checked" datatype="*"    onclick="popupClick(this,'id,bp_name','bcPersonid,bcPerson','teacher_msg')"  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">班主任ID</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							班主任:
						</label>
					</td>
					<td class="value">
							<input id="bcPerson" name="bcPerson" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="checked" datatype="*"    onclick="popupClick(this,'id,bp_name','bcPersonid,bcPerson','teacher_msg')"  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">班主任</label>
						</td>
				</tr>
				
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							描述:
						</label>
					</td>
					<td class="value" >
						  	 <textarea style="height:auto;width:95%" class="inputxt" rows="6" id="bcDesc" name="bcDesc"  ignore="ignore" ></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">描述</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/jeecg/basclass/basClass.js"></script>		
