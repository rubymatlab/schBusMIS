<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>远程电子围栏信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="basFenceremoteController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${basFenceremotePage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								设备ID:
							</label>
						</td>
						<td class="value">
						    <input id="deviceid" name="deviceid" type="text" maxlength="50" style="width: 150px" class="inputxt"  ignore="ignore"  value='${basFenceremotePage.deviceid}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">设备ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								围栏ID:
							</label>
						</td>
						<td class="value">
						    <input id="fenceid" name="fenceid" type="text" maxlength="32" style="width: 150px" class="inputxt"  ignore="ignore"  value='${basFenceremotePage.fenceid}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">围栏ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								围栏名称:
							</label>
						</td>
						<td class="value">
						    <input id="fencename" name="fencename" type="text" maxlength="32" style="width: 150px" class="inputxt"  ignore="ignore"  value='${basFenceremotePage.fencename}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">围栏名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								ID:
							</label>
						</td>
						<td class="value">
						    <input id="id1" name="id1" type="text" maxlength="32" style="width: 150px" class="inputxt"  ignore="ignore"  value='${basFenceremotePage.id1}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								围栏图片:
							</label>
						</td>
						<td class="value">
						    <input id="img" name="img" type="text" maxlength="32" style="width: 150px" class="inputxt"  ignore="ignore"  value='${basFenceremotePage.img}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">围栏图片</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								纬度:
							</label>
						</td>
						<td class="value">
						    <input id="la" name="la" type="text" maxlength="32" style="width: 150px" class="inputxt"  ignore="ignore"  value='${basFenceremotePage.la}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">纬度</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								经度:
							</label>
						</td>
						<td class="value">
						    <input id="lo" name="lo" type="text" maxlength="32" style="width: 150px" class="inputxt"  ignore="ignore"  value='${basFenceremotePage.lo}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">经度</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								半径:
							</label>
						</td>
						<td class="value">
						    <input id="r" name="r" type="text" maxlength="32" style="width: 150px" class="inputxt"  ignore="ignore"  value='${basFenceremotePage.r}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">半径</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								开关:
							</label>
						</td>
						<td class="value">
						    <input id="switchtag" name="switchtag" type="text" maxlength="32" style="width: 150px" class="inputxt"  ignore="ignore"  value='${basFenceremotePage.switchtag}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">开关</label>
						</td>
					</tr>
				
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/jeecg/basstudent/basFenceremote.js"></script>		
