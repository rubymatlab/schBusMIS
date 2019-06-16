<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="vwBasCarinfoList" checkbox="true" pagination="true" fitColumns="true" title="加密卡号基础资料" sortName="createDate" actionUrl="vwBasCarinfoController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="卡号"  field="bsCardno"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="加密卡号"  field="bsImei"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="绑定学生姓名"  field="bsName"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="设备ID"  field="bsDeviceid"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="vwBasCarinfoController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o" exp="bsName#eq#"/>
   <%-- <t:dgToolBar title="录入" icon="icon-add" url="vwBasCarinfoController.do?goAdd" funname="add"></t:dgToolBar>
	<t:dgToolBar title="编辑" icon="icon-edit" url="vwBasCarinfoController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="vwBasCarinfoController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="vwBasCarinfoController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/jeecg/basstudent/vwBasCarinfoList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'vwBasCarinfoController.do?upload', "vwBasCarinfoList");
}

//导出
function ExportXls() {
	JeecgExcelExport("vwBasCarinfoController.do?exportXls","vwBasCarinfoList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("vwBasCarinfoController.do?exportXlsByT","vwBasCarinfoList");
}

 </script>