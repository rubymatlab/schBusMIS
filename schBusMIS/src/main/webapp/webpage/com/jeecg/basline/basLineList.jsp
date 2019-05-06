<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="basLineList" checkbox="true" fitColumns="true" title="线路资料" extendParams="view: detailview,detailFormatter:detailFormatterFun,onExpandRow: onExpandRowFun" sortName="createDate" actionUrl="basLineController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="线路状态"  field="lineStatus"  queryMode="single"  dictionary="bus_status"  width="120"></t:dgCol>
   <t:dgCol title="线路名称"  field="blName"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="线路描述"  field="blDesc"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="司机编号"  field="blDriverid" hidden="true"  queryMode="single"  dictionary="driver_msg,blDriverid@blDriver,id@bp_name"  popup="true"  width="120"></t:dgCol>
   <t:dgCol title="线路司机"  field="blDriver"  queryMode="single"  dictionary="driver_msg,blDriverid@blDriver,id@bp_name"  popup="true"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="basLineController.do?doDel&id={id}"  urlclass="ace_button" urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="basLineController.do?goAdd" funname="add" width="100%" height="100%"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="basLineController.do?goUpdate" funname="update" width="100%" height="100%"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="basLineController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="basLineController.do?goUpdate" funname="detail" width="100%" height="100%"></t:dgToolBar>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/jeecg/basline/basLineList.js"></script>		
 <script src="plug-in/easyui/extends/datagrid-detailview.js"></script>
 <script type="text/javascript">
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'basLineController.do?upload', "basLineList");
}

//导出
function ExportXls() {
	JeecgExcelExport("basLineController.do?exportXls","basLineList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("basLineController.do?exportXlsByT","basLineList");
}

//返回行明细内容的格式化函数。
function detailFormatterFun() {
    var s = '<div class="orderInfoHidden" style="padding:2px;">' 
   		+ '		<div class="easyui-tabs" style="height:230px;width:800px;">' 
   		+ '			<div title="站点明细" style="padding:2px;">' 
   		+ '				<table class="jfrom_order_linetablelines" ></table>' 
   		+ '			</div>' 
   		+ '		</div>' 
   		+ '	</div>';
    return s;
}
//当展开一行时触发 
function onExpandRowFun(index, row) {
	//把加上的子表tabs和datagrid初始化
    var tabs = $(this).datagrid('getRowDetail', index).find('div.easyui-tabs');
    tabs.tabs();
    var jfrom_order_linetablelines = $(this).datagrid('getRowDetail', index).find('table.jfrom_order_linetablelines');
    var jfrom_order_linedurl = 'basLineController.do?bassizeDatagrid&field=bsSeq,bsName,bsDesc,sizeStatus,sizeBus&fkBlId=' + row.id;
    jfrom_order_linetablelines.datagrid({
        singleSelect: true,
        loadMsg: '正在加载',
        fitColumns: true,
        height: '180',
        pageSize: 50,
        pageList: [50, 150, 200, 250, 300],
        border: false,
        url: jfrom_order_linedurl,
        idField: 'id',
        rownumbers: true,
        pagination: true,
        columns: [[{
            title: '排序',
            field: 'bsSeq',
            align: 'left',
            width: 50
        },
        {
            title: '站点名称',
            field: 'bsName',
            align: 'left',
            width: 50
        },
        {
            title: '站点描述',
            field: 'bsDesc',
            align: 'left',
            width: 50
        },
        {
            title: '返校提醒',
            field: 'sizeBus',
            align: 'left',
            width: 50
        }/* ,
        {
            title: '起点或终点',
            field: 'sizeStatus',
            align: 'left',
            width: 50
        } */]]
    });
}
 </script>