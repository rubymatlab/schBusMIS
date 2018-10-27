<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="basStudentList" checkbox="true" pagination="true" fitColumns="true" title="学生资料" actionUrl="basStudentController.do?datagrid" idField="id" sortName="createDate" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="学生姓名"  field="bsName"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="性别"  field="bsSex"  queryMode="single"  dictionary="sex"  width="120"></t:dgCol>
   <t:dgCol title="家长"  field="bcParent"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="家长手机"  field="bsTel"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="班级ID"  field="bcId"  hidden="true"  queryMode="single"  dictionary="class_msg,bcId@bcGrade@bcName,id@bc_grade@bc_name"  popup="true"  width="120"></t:dgCol>
   <t:dgCol title="年级"  field="bcGrade"  queryMode="single"  dictionary="class_msg,bcId@bcGrade@bcName,id@bc_grade@bc_name"  popup="true"  width="120"></t:dgCol>
   <t:dgCol title="班级"  field="bcName"  query="true"  queryMode="single"  dictionary="class_msg,bcId@bcGrade@bcName,id@bc_grade@bc_name"  popup="true"  width="120"></t:dgCol>
   <t:dgCol title="卡号"  field="bsCardno"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="站点ID"  field="blSizeid"  hidden="true"  queryMode="single"  dictionary="line_msg,blSizeid@blName@blSize,id@bl_name@bs_name"  popup="true"  width="120"></t:dgCol>
   <t:dgCol title="线路"  field="blName"  queryMode="single"  dictionary="line_msg,blSizeid@blName@blSize,id@bl_name@bs_name"  popup="true"  width="120"></t:dgCol>
   <t:dgCol title="站点"  field="blSize"  query="true"  queryMode="single"  dictionary="line_msg,blSizeid@blName@blSize,id@bl_name@bs_name"  popup="true"  width="120"></t:dgCol>
   <t:dgCol title="地址"  field="bsAddress"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="bsDesc"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="basStudentController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="basStudentController.do?goAdd" funname="add"  width="768"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="basStudentController.do?goUpdate" funname="update"  width="768"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="basStudentController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="basStudentController.do?goUpdate" funname="detail"  width="768"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'basStudentController.do?upload', "basStudentList");
}

//导出
function ExportXls() {
	JeecgExcelExport("basStudentController.do?exportXls","basStudentList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("basStudentController.do?exportXlsByT","basStudentList");
}

//bootstrap列表图片格式化
function btListImgFormatter(value,row,index){
	return listFileImgFormat(value,"image");
}
//bootstrap列表文件格式化
function btListFileFormatter(value,row,index){
	return listFileImgFormat(value);
}

//列表文件图片 列格式化方法
function listFileImgFormat(value,type){
	var href='';
	if(value==null || value.length==0){
		return href;
	}
	var value1 = "img/server/"+value;
	if("image"==type){
 		href+="<img src='"+value1+"' width=30 height=30  onmouseover='tipImg(this)' onmouseout='moveTipImg()' style='vertical-align:middle'/>";
	}else{
 		if(value.indexOf(".jpg")>-1 || value.indexOf(".gif")>-1 || value.indexOf(".png")>-1){
 			href+="<img src='"+value1+"' onmouseover='tipImg(this)' onmouseout='moveTipImg()' width=30 height=30 style='vertical-align:middle'/>";
 		}else{
 			var value2 = "img/server/"+value+"?down=true";
 			href+="<a href='"+value2+"' class='ace_button' style='text-decoration:none;' target=_blank><u><i class='fa fa-download'></i>点击下载</u></a>";
 		}
	}
	return href;
}

</script>
