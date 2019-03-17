<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>学生资料</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="basStudentInfoController.do?doAdd" >
					<input id="id" name="id" type="hidden" value="${basStudentInfoPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							学生姓名:
						</label>
					</td>
					<td class="value">
					     	 <input id="bsName" name="bsName" type="text" maxlength="50" style="width: 150px" class="inputxt"  datatype="*" ignore="checked" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">学生姓名</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							性别:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="bsSex" type="list"  datatype="*" typeGroupCode="sex"  defaultVal="${basStudentInfoPage.bsSex}" hasLabel="false"  title="性别" ></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">性别</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							家长:
						</label>
					</td>
					<td class="value">
					     	 <input id="bcParent" name="bcParent" type="text" maxlength="50" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">家长</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							家长手机:
						</label>
					</td>
					<td class="value">
					     	 <input id="bsTel" name="bsTel" type="text" maxlength="32" style="width: 150px" class="inputxt"  datatype="n" ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">家长手机</label>
						</td>
					</tr>
				<tr>
					<!-- <td align="right">
						<label class="Validform_label">
							班级ID:
						</label>
					</td>
					<td class="value">
							<input id="bcId" name="bcId" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"   onclick="popupClick(this,'id,bc_grade,bc_name','bcId,bcGrade,bcName','class_msg')"  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">班级ID</label>
						</td> -->
					<td align="right">
						<label class="Validform_label">
							年级:
						</label>
					</td>
					<td class="value">
					<input id="bcId" name="bcId" type="hidden" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"   onclick="popupClick(this,'id,bc_grade,bc_name','bcId,bcGrade,bcName','class_msg')"  />
							
							<input id="bcGrade" name="bcGrade" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"   onclick="popupClick(this,'id,bc_grade,bc_name','bcId,bcGrade,bcName','class_msg')"  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">年级</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							班级:
						</label>
					</td>
					<td class="value">
							<input id="bcName" name="bcName" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"   onclick="popupClick(this,'id,bc_grade,bc_name','bcId,bcGrade,bcName','class_msg')"  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">班级</label>
						</td>
					</tr>
				<tr>
					
					<td align="right">
						<label class="Validform_label">
							卡号:
						</label>
					</td>
					<td class="value">
					     	 <input id="bsCardno" name="bsCardno" type="text" maxlength="24" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">卡号</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							加密卡号:
						</label>
					</td>
					<td class="value">
					     	 <input id="bsImei" name="bsImei" type="text" maxlength="32" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">加密卡号</label>
						</td>
					</tr>
				<tr>
					
					<td align="right">
						<label class="Validform_label">
							设备ID:
						</label>
					</td>
					<td class="value">
					     	 <input id="bsDeviceid" name="bsDeviceid" type="text" maxlength="50" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">设备ID</label>
						</td>
					</tr>
				<tr>
					<!-- <td align="right">
						<label class="Validform_label">
							上学站点ID:
						</label>
					</td>
					<td class="value">
							<input id="blSizeid" name="blSizeid" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"   onclick="popupClick(this,'id,bl_name,bs_name','blSizeid,blName,blSize','line_msg')"  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">上学站点ID</label>
						</td> -->
					<td align="right">
						<label class="Validform_label">
							上学线路:
						</label>
					</td>
					<td class="value">
					<input id="blSizeid" name="blSizeid" type="hidden" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"   onclick="popupClick(this,'id,bl_name,bs_name','blSizeid,blName,blSize','line_msg')"  />
							
							<input id="blName" name="blName" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"   onclick="popupClick(this,'id,bl_name,bs_name','blSizeid,blName,blSize','line_msg')"  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">上学线路</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							上学站点:
						</label>
					</td>
					<td class="value">
							<input id="blSize" name="blSize" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"   onclick="popupClick(this,'id,bl_name,bs_name','blSizeid,blName,blSize','line_msg')"  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">上学站点</label>
						</td>
					</tr>
				<!--<tr>
					
					 <td align="right">
						<label class="Validform_label">
							放学站点ID:
						</label>
					</td>
					<td class="value">
							<input id="blSizeid1" name="blSizeid1" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"   onclick="popupClick(this,'id,bl_name,bs_name','blSizeid1,blName1,blSize1','line_msg1')"  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">放学站点ID</label>
						</td>
					</tr> -->
				<tr>
					<td align="right">
						<label class="Validform_label">
							放学路线:
						</label>
					</td>
					<td class="value">
					<input id="blSizeid1" name="blSizeid1" type="hidden" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"   onclick="popupClick(this,'id,bl_name,bs_name','blSizeid1,blName1,blSize1','line_msg1')"  />
							
							<input id="blName1" name="blName1" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"   onclick="popupClick(this,'id,bl_name,bs_name','blSizeid1,blName1,blSize1','line_msg1')"  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">放学路线</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							放学站点:
						</label>
					</td>
					<td class="value">
							<input id="blSize1" name="blSize1" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"   onclick="popupClick(this,'id,bl_name,bs_name','blSizeid1,blName1,blSize1','line_msg1')"  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">放学站点</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							地址:
						</label>
					</td>
					<td class="value">
							<input id="bsAddress" name="bsAddress" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"    />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">地址</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							是否有效:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="bsStatus" type="list"  typeGroupCode="sf_yn"  defaultVal="${basStudentInfoPage.bsStatus}" hasLabel="false"  title="是否有效" ></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">是否有效</label>
						</td>
					</tr>
				
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							备注:
						</label>
					</td>
					<td class="value"  colspan="3" >
						  	 <textarea style="height:auto;width:95%" class="inputxt" rows="6" id="bsDesc" name="bsDesc"  ignore="ignore" ></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/jeecg/basstudentinfo/basStudentInfo.js"></script>		
