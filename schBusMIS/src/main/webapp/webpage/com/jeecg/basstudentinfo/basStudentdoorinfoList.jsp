<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="basStudentdoorinfoList" checkbox="true" pagination="true" fitColumns="true" title="学生门禁信息" sortName="createDate" actionUrl="basStudentdoorinfoController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="卡编号"  field="bsSeq"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="卡号"  field="bsCardno"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="门设备号"  field="bsMacno"  query="true"  queryMode="single"  dictionary="vw_basmacno,macno,macno"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="bsState"  query="true"  hidden="false"  queryMode="single"  width="30"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="basStudentdoorinfoController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
 
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="basStudentdoorinfoController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
  <t:dgToolBar title="清除设备卡数据" icon="icon-remove" url="basStudentdoorinfoController.do?goClean" funname="clean"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/jeecg/basstudentinfo/basStudentdoorinfoList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
function clean() {//value传入值,id接受值
	 var bsMacno = $("select[name='bsMacno']").val();
	 if(bsMacno==""){
		 alert("请选择卡设备编号！");
	 }else{
		 $.messager.confirm('提示', "确定清除设备卡数据？",function(f){
			 if(f == true){
					$.ajax({
					    url:"basStudentdoorinfoController.do?goClean",
					    data:{bsMacno:bsMacno},
						type:"Post",
					    dataType:"json",
					    success:function(data){			 
					    	if(data.success){
					    		tip(data.msg);
					    		//reloadTable();
					    		$("select[name='bsMacno']").val("");
					    		basStudentdoorinfoListsearch();
					    	}
					    },
						error:function(data){
							$.messager.alert('错误',data.msg);
						}
					});  	
			 }
		 });
		 
	 } 
}


 </script>