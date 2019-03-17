<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="basStudentInfoList" checkbox="true" pagination="true" fitColumns="true" title="学生资料" sortName="createDate" actionUrl="basStudentInfoController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="学生姓名"  field="bsName"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="性别"  field="bsSex"  queryMode="single"  dictionary="sex"  width="120"></t:dgCol>
   <t:dgCol title="家长"  field="bcParent"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="家长手机"  field="bsTel"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="班级ID"  field="bcId" hidden="true"  queryMode="single"  dictionary="class_msg,bcId@bcGrade@bcName,id@bc_grade@bc_name"  popup="true"  width="120"></t:dgCol>
   <t:dgCol title="年级"  field="bcGrade"  queryMode="single"  dictionary="class_msg,bcId@bcGrade@bcName,id@bc_grade@bc_name"  popup="true"  width="120"></t:dgCol>
   <t:dgCol title="班级"  field="bcName"  query="true"  queryMode="single"  dictionary="class_msg,bcId@bcGrade@bcName,id@bc_grade@bc_name"  popup="true"  width="120"></t:dgCol>
   <t:dgCol title="卡号"  field="bsCardno"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="加密卡号"  field="bsImei"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="设备ID"  field="bsDeviceid"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="上学站点ID"  field="blSizeid" hidden="true"  queryMode="single"  dictionary="line_msg,blSizeid@blName@blSize,id@bl_name@bs_name"  popup="true"  width="120"></t:dgCol>
   <t:dgCol title="上学线路"  field="blName"  queryMode="single"  dictionary="line_msg,blSizeid@blName@blSize,id@bl_name@bs_name"  popup="true"  width="120"></t:dgCol>
   <t:dgCol title="上学站点"  field="blSize"  query="true"  queryMode="single"  dictionary="line_msg,blSizeid@blName@blSize,id@bl_name@bs_name"  popup="true"  width="120"></t:dgCol>
   <t:dgCol title="放学站点ID"  field="blSizeid1" hidden="true"  queryMode="single"  dictionary="line_msg1,blSizeid1@blName1@blSize1,id@bl_name@bs_name"  popup="true"  width="120"></t:dgCol>
   <t:dgCol title="放学路线"  field="blName1"  queryMode="single"  dictionary="line_msg1,blSizeid1@blName1@blSize1,id@bl_name@bs_name"  popup="true"  width="120"></t:dgCol>
   <t:dgCol title="放学站点"  field="blSize1"  query="true"  queryMode="single"  dictionary="line_msg1,blSizeid1@blName1@blSize1,id@bl_name@bs_name"  popup="true"  width="120"></t:dgCol>
   <t:dgCol title="地址"  field="bsAddress"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="bsDesc"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="是否有效"  field="bsStatus"  queryMode="single"  dictionary="sf_yn"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="basStudentInfoController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="basStudentInfoController.do?goAdd" funname="add"></t:dgToolBar>
	<t:dgToolBar title="编辑" icon="icon-edit" url="basStudentInfoController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="basStudentInfoController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="basStudentInfoController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
     	<t:dgToolBar title="绑定设备" icon="icon-edit"  url="basStudentInfoController.do?doAddDevice" funname="doAddDevice" ></t:dgToolBar>
     	<t:dgToolBar title="解绑设备" icon="icon-edit"  url="basStudentInfoController.do?doDeleteDevice" funname="doDeleteDevice" ></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/jeecg/basstudentinfo/basStudentInfoList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
     	//自定义按钮-绑定设备
	 	function doAddDevice(title,url,gridname){
	 		var rowData = $('#'+gridname).datagrid('getSelected');
			if (!rowData) {
				tip('请选择绑定设备项目');
				return;
			}
			url = url+"&id="+rowData['id'];
	 		createdialog('确认 ', '确定'+title+'吗 ?', url,gridname);
	 	}
     	//自定义按钮-解绑设备
	 	function doDeleteDevice(title,url,gridname){
	 		var rowData = $('#'+gridname).datagrid('getSelected');
			if (!rowData) {
				tip('请选择解绑设备项目');
				return;
			}
			url = url+"&id="+rowData['id'];
	 		createdialog('确认 ', '确定'+title+'吗 ?', url,gridname);
	 	}
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'basStudentInfoController.do?upload', "basStudentInfoList");
}

//导出
function ExportXls() {
	JeecgExcelExport("basStudentInfoController.do?exportXls","basStudentInfoList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("basStudentInfoController.do?exportXlsByT","basStudentInfoList");
}

 </script>