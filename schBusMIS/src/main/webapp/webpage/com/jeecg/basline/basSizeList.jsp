<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('#addBasSizeBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delBasSizeBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#addBasSizeBtn').bind('click', function(){   
 		 var tr =  $("#add_basSize_table_template tr").clone();
	 	 $("#add_basSize_table").append(tr);
	 	 resetTrNum('add_basSize_table');
	 	 return false;
    });  
	$('#delBasSizeBtn').bind('click', function(){   
		$("#add_basSize_table").find("input[name$='ck']:checked").parent().parent().remove();  
        resetTrNum('add_basSize_table'); 
        return false;
    }); 
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
    });
</script>
<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<a id="addBasSizeBtn" href="#">添加</a> <a id="delBasSizeBtn" href="#">删除</a> 
</div>
<table border="0" cellpadding="2" cellspacing="0" id="basSize_table">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE" style="width: 25px;">序号</td>
		<td align="center" bgcolor="#EEEEEE" style="width: 25px;">操作</td>
				  <td align="left" bgcolor="#EEEEEE" style="width: 126px;">
						排序
				  </td>
				  <td align="left" bgcolor="#EEEEEE" style="width: 126px;">
						站点名称
				  </td>
				  <td align="left" bgcolor="#EEEEEE" style="width: 126px;">
						站点描述
				  </td>
				  <td align="left" bgcolor="#EEEEEE" style="width: 126px;">
						起点或终点
				  </td>
	</tr>
	<tbody id="add_basSize_table">
	<c:if test="${fn:length(basSizeList)  <= 0 }">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">1</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck"/></td>
					<input name="basSizeList[0].id" type="hidden"/>
					<input name="basSizeList[0].createName" type="hidden"/>
					<input name="basSizeList[0].createBy" type="hidden"/>
					<input name="basSizeList[0].createDate" type="hidden"/>
					<input name="basSizeList[0].updateName" type="hidden"/>
					<input name="basSizeList[0].updateBy" type="hidden"/>
					<input name="basSizeList[0].updateDate" type="hidden"/>
					<input name="basSizeList[0].fkBlId" type="hidden"/>
				  <td align="left">
					  	<input name="basSizeList[0].bsSeq" maxlength="3" type="text" class="inputxt"  style="width:120px;"  datatype="n" ignore="checked" >
					  <label class="Validform_label" style="display: none;">排序</label>
					</td>
				  <td align="left">
					  	<input name="basSizeList[0].bsName" maxlength="50" type="text" class="inputxt"  style="width:120px;"  ignore="ignore" >
					  <label class="Validform_label" style="display: none;">站点名称</label>
					</td>
				  <td align="left">
					  	<input name="basSizeList[0].bsDesc" maxlength="100" type="text" class="inputxt"  style="width:120px;"  ignore="ignore" >
					  <label class="Validform_label" style="display: none;">站点描述</label>
					</td>
				  <td align="left">
							<t:dictSelect field="basSizeList[0].sizeStatus" type="list"   typeGroupCode="sz_status"  defaultVal="${basSizePage.sizeStatus}" hasLabel="false"  title="起点或终点"></t:dictSelect>     
					  <label class="Validform_label" style="display: none;">起点或终点</label>
					</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(basSizeList)  > 0 }">
		<c:forEach items="${basSizeList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck" /></td>
						<input name="basSizeList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
						<input name="basSizeList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
						<input name="basSizeList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
						<input name="basSizeList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
						<input name="basSizeList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
						<input name="basSizeList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
						<input name="basSizeList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
						<input name="basSizeList[${stuts.index }].fkBlId" type="hidden" value="${poVal.fkBlId }"/>
				   <td align="left">
					  	<input name="basSizeList[${stuts.index }].bsSeq" maxlength="3" type="text" class="inputxt"  style="width:120px;"  datatype="n" ignore="checked"  value="${poVal.bsSeq }"/>
					  <label class="Validform_label" style="display: none;">排序</label>
				   </td>
				   <td align="left">
					  	<input name="basSizeList[${stuts.index }].bsName" maxlength="50" type="text" class="inputxt"  style="width:120px;"  ignore="ignore"  value="${poVal.bsName }"/>
					  <label class="Validform_label" style="display: none;">站点名称</label>
				   </td>
				   <td align="left">
					  	<input name="basSizeList[${stuts.index }].bsDesc" maxlength="100" type="text" class="inputxt"  style="width:120px;"  ignore="ignore"  value="${poVal.bsDesc }"/>
					  <label class="Validform_label" style="display: none;">站点描述</label>
				   </td>
				   <td align="left">
							<t:dictSelect field="basSizeList[${stuts.index }].sizeStatus" type="list"   typeGroupCode="sz_status"  defaultVal="${poVal.sizeStatus }" hasLabel="false"  title="起点或终点"></t:dictSelect>     
					  <label class="Validform_label" style="display: none;">起点或终点</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
