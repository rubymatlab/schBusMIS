<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="basFenceremoteList" checkbox="true" pagination="true" fitColumns="true" title="远程电子围栏信息" sortName="createDate" actionUrl="basFenceremoteController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="设备ID"  field="deviceid"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="围栏ID"  field="fenceid"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="围栏名称"  field="fencename"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="ID"  field="id1"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="围栏图片"  field="img"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="纬度"  field="la"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="经度"  field="lo"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="半径"  field="r"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="开关"  field="switchtag"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="basFenceremoteController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <%-- <t:dgToolBar title="录入" icon="icon-add" url="basFenceremoteController.do?goAdd" funname="add"></t:dgToolBar>
	<t:dgToolBar title="编辑" icon="icon-edit" url="basFenceremoteController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="basFenceremoteController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="basFenceremoteController.do?goUpdate" funname="detail"></t:dgToolBar>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
     	<t:dgToolBar title="添加围栏" icon="icon-edit"  url="basFenceremoteController.do?doAddFenceDevice" funname="doAddFenceDevice" ></t:dgToolBar>
     	<%-- <t:dgToolBar title="获取围栏" icon="icon-edit"  url="basFenceremoteController.do?doGetFenceDevice" funname="doGetFenceDevice" ></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/jeecg/basstudent/basFenceremoteList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
     	//自定义按钮-添加围栏
	 	function doAddFenceDevice(title,url,gridname){
	 		/* var rowData = $('#'+gridname).datagrid('getSelected');
			if (!rowData) {
				tip('请选择添加围栏项目');
				return;
			}
			url = url+"&id="+rowData['id']; */
	 		createdialog('确认 ', '确定'+title+'吗 ?', url,gridname);
	 	}
     	//自定义按钮-获取围栏
	 	function doGetFenceDevice(title,url,gridname){
	 		var rowData = $('#'+gridname).datagrid('getSelected');
			if (!rowData) {
				tip('请选择获取围栏项目');
				return;
			}
			url = url+"&id="+rowData['id'];
	 		createdialog('确认 ', '确定'+title+'吗 ?', url,gridname);
	 	}
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'basFenceremoteController.do?upload', "basFenceremoteList");
}

//导出
function ExportXls() {
	JeecgExcelExport("basFenceremoteController.do?exportXls","basFenceremoteList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("basFenceremoteController.do?exportXlsByT","basFenceremoteList");
}

 </script>