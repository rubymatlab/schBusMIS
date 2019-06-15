<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="busMapfenceList" checkbox="false" pagination="true" fitColumns="true" title="电子围栏" actionUrl="busMapfenceController.do?datagrid" idField="id" sortName="createDate" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="设备id"  field="deviceId"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="信号强度"  field="signalPower"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="小区id"  field="cellId"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="电压"  field="batteryVoltage"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="gps时间"  field="gpsTime"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="纬度"  field="gpsLatitude"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="经度"  field="gpsLongitude"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="时间戳"  field="timestamp"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="设备发送时间"  field="deviceTime"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="下一次时间戳"  field="nextTrcTime"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="位置精确的精度"  field="accuracy"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="设备发送次数"  field="counter"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="类型"  field="type"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="消息"  field="msg"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="busMapfenceController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="busMapfenceController.do?goAdd" funname="add"  width="800" height="500"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="busMapfenceController.do?goUpdate" funname="update"  width="800" height="500"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="busMapfenceController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="busMapfenceController.do?goUpdate" funname="detail"  width="800" height="500"></t:dgToolBar>
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
	openuploadwin('Excel导入', 'busMapfenceController.do?upload', "busMapfenceList");
}

//导出
function ExportXls() {
	JeecgExcelExport("busMapfenceController.do?exportXls","busMapfenceList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("busMapfenceController.do?exportXlsByT","busMapfenceList");
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
