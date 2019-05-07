<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@include file="/context/mytags.jsp"%>
<t:base type="jquery"></t:base>
<script>
	window.onload = function() {
		setTimeout('myrefresh()', 10000); //指定10秒刷新一次 
	}

	function myrefresh() {
		window.location.reload();
	}
</script>
<div style="display: flex;flex-direction: row;flex-wrap:wrap;">
	<c:forEach items="${listBusRemindList}" var="item">
		<div style="text-align: center;">
			<c:if test="${item.sizeStatus == '1'}">
				<font size="5" color="red">${item.sizeBus}</font>
				<div>
					<img src="images/bus_red.png" width="150" height="150"
						alt="${item.bsName}" />
				</div>
			</c:if>

			<c:if test="${item.sizeStatus == '0'}">
				<font size="5" color="gray">${item.sizeBus}</font>
				<div>
					<img src="images/bus_gray.png" width="150" height="150"
						alt="${item.bsName}" />
				</div>
			</c:if>
		</div>
	</c:forEach>
</div>

<%-- <div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="busRemindList" checkbox="true" pagination="true" fitColumns="true" title="微信绑定管理" sortName="createDate" actionUrl="busRemindController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="学生"  field="bsStudentid"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="绑定ID"  field="boOpenid"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="绑定时间"  field="boBinddatetime"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
   <t:dgDelOpt title="删除" url="busRemindController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="busRemindController.do?goAdd" funname="add"></t:dgToolBar>
	<t:dgToolBar title="编辑" icon="icon-edit" url="busRemindController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="busRemindController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="busRemindController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/jeecg/bascontrail/busRemindList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'busRemindController.do?upload', "busRemindList");
}

//导出
function ExportXls() {
	JeecgExcelExport("busRemindController.do?exportXls","busRemindList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("busRemindController.do?exportXlsByT","busRemindList");
}

 </script> --%>