<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>线路资料</title>
    <style>
  .ui-button {
  	  display: inline-block;
	  padding: 2px 2px;
	  margin-bottom: 0;
	  font-size: 8px;
	  font-weight: normal;
	  line-height: 1.42857143;
	  text-align: center;
	  white-space: nowrap;
	  vertical-align: middle;
	  -ms-touch-action: manipulation;
      touch-action: manipulation;
	  cursor: pointer;
	  -webkit-user-select: none;
      -moz-user-select: none;
      -ms-user-select: none;
      user-select: none;
	  background-image: none;
	  border: 1px solid transparent;
	  border-radius: 4px;
  }
  </style>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  $(document).ready(function(){
	$('#tt').tabs({
	   onSelect:function(title){
	       $('#tt .panel-body').css('width','auto');
		}
	});
	$(".tabs-wrap").css('width','100%');
  });
 </script>
 </head>
 <body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="basLineController.do?doAdd" >
					<input id="id" name="id" type="hidden" value="${basLinePage.id }"/>
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">线路状态:</label>
			</td>
			<td class="value">
					  <t:dictSelect field="lineStatus" type="list"   typeGroupCode="bus_status"  defaultVal="${basLinePage.lineStatus}" hasLabel="false"  title="线路状态" ></t:dictSelect>     
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">线路状态</label>
			</td>
			<td align="right">
				<label class="Validform_label">线路名称:</label>
			</td>
			<td class="value">
		     	 <input id="blName" name="blName" type="text" maxlength="50" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">线路名称</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">线路描述:</label>
			</td>
			<td class="value">
		     	 <input id="blDesc" name="blDesc" type="text" maxlength="100" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">线路描述</label>
			</td>
			<!-- <td align="right">
				<label class="Validform_label">司机编号:</label>
			</td>
			<td class="value">
				<input id="blDriverid" name="blDriverid" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"  onclick="popupClick(this,'id,bp_name','blDriverid,blDriver','driver_msg')"/>			 
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">司机编号</label>
			</td> -->
			
			<td align="right">
				<label class="Validform_label">线路司机:</label>
			</td>
			<td class="value">
				<input id="blDriverid" name="blDriverid" type="hidden" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"  onclick="popupClick(this,'id,bp_name','blDriverid,blDriver','driver_msg')"/>			 
				
				<input id="blDriver" name="blDriver" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"  onclick="popupClick(this,'id,bp_name','blDriverid,blDriver','driver_msg')"/>			 
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">线路司机</label>
			</td>
		</tr>
		<!-- <tr>
			<td align="right">
				<label class="Validform_label">线路司机:</label>
			</td>
			<td class="value">
				<input id="blDriver" name="blDriver" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"  onclick="popupClick(this,'id,bp_name','blDriverid,blDriver','driver_msg')"/>			 
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">线路司机</label>
			</td>
		</tr> -->
	
	</table>
			<div style="width: auto;height: 200px;">
				<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
				<div style="width:800px;height:1px;"></div>
				<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				 <t:tab href="basLineController.do?basSizeList&id=${basLinePage.id}" icon="icon-search" title="站点明细" id="basSize"></t:tab>
				</t:tabs>
			</div>
			</t:formvalid>
			<!-- 添加 附表明细 模版 -->
	<table style="display:none">
	<tbody id="add_basSize_table_template">
		<tr>
			 <td align="center"><div style="width: 25px;" name="xh"></div></td>
			 <td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
				  <td align="left">
					  	<input name="basSizeList[#index#].bsSeq" maxlength="3" type="text" class="inputxt"  style="width:120px;"  datatype="n" ignore="checked" />
					  <label class="Validform_label" style="display: none;">排序</label>
				  </td>
				  <td align="left">
					  	<input name="basSizeList[#index#].bsName" maxlength="50" type="text" class="inputxt"  style="width:120px;"  ignore="ignore" />
					  <label class="Validform_label" style="display: none;">站点名称</label>
				  </td>
				  <td align="left">
					  	<input name="basSizeList[#index#].bsDesc" maxlength="100" type="text" class="inputxt"  style="width:120px;"  ignore="ignore" />
					  <label class="Validform_label" style="display: none;">站点描述</label>
				  </td>
				  <td align="left">
							<t:dictSelect field="basSizeList[#index#].sizeStatus" type="list"    typeGroupCode="sz_status"  defaultVal="" hasLabel="false"  title="起点或终点"></t:dictSelect>     
					  <label class="Validform_label" style="display: none;">起点或终点</label>
				  </td>
			</tr>
		 </tbody>
		</table>
 </body>
 <script src = "webpage/com/jeecg/basline/basLine.js"></script>
	