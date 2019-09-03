<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>学生资料</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css" />
	<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="basStudentInfoController.do?doUpdate" callback="jeecgFormFileCallBack@Override">
					<input id="id" name="id" type="hidden" value="${basStudentInfoPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								学生姓名:
							</label>
						</td>
						<td class="value">
						    <input id="bsName" name="bsName" type="text" maxlength="50" style="width: 150px" class="inputxt"  datatype="*" ignore="checked"  value='${basStudentInfoPage.bsName}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">学生姓名</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								性别:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="bsSex" type="list"  datatype="*" typeGroupCode="sex"   defaultVal="${basStudentInfoPage.bsSex}" hasLabel="false"  title="性别" ></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">性别</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								家长:
							</label>
						</td>
						<td class="value">
						    <input id="bcParent" name="bcParent" type="text" maxlength="50" style="width: 150px" class="inputxt"  ignore="ignore"  value='${basStudentInfoPage.bcParent}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">家长</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								家长手机:
							</label>
						</td>
						<td class="value">
						    <input id="bsTel" name="bsTel" type="text" maxlength="32" style="width: 150px" class="inputxt"  datatype="n" ignore="ignore"  value='${basStudentInfoPage.bsTel}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">家长手机</label>
						</td>
					</tr>
					<tr>
						<%-- <td align="right">
							<label class="Validform_label">
								班级ID:
							</label>
						</td>
						<td class="value">
							<input id="bcId" name="bcId" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"  onclick="popupClick(this,'id,bc_grade,bc_name','bcId,bcGrade,bcName','class_msg')" value='${basStudentInfoPage.bcId}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">班级ID</label>
						</td> --%>
						<td align="right">
							<label class="Validform_label">
								年级:
							</label>
						</td>
						<td class="value">
						<input id="bcId" name="bcId" type="hidden" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"  onclick="popupClick(this,'id,bc_grade,bc_name','bcId,bcGrade,bcName','class_msg')" value='${basStudentInfoPage.bcId}'/>
							
							<input id="bcGrade" name="bcGrade" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"  onclick="popupClick(this,'id,bc_grade,bc_name','bcId,bcGrade,bcName','class_msg')" value='${basStudentInfoPage.bcGrade}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">年级</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								班级:
							</label>
						</td>
						<td class="value">
							<input id="bcName" name="bcName" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"  onclick="popupClick(this,'id,bc_grade,bc_name','bcId,bcGrade,bcName','class_msg')" value='${basStudentInfoPage.bcName}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">班级</label>
						</td>
					</tr>
					<tr>
						
						<td align="right">
							<label class="Validform_label">
								卡号:
							</label>
						</td>
						<td class="value">
						    <input id="bsCardno" name="bsCardno" type="text" maxlength="24" style="width: 150px" class="inputxt"  ignore="ignore"  datatype=n15-15 value='${basStudentInfoPage.bsCardno}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">卡号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								加密卡号:
							</label>
						</td>
						<td class="value">
						    <input id="bsImei" name="bsImei" type="text" maxlength="32" style="width: 150px" class="inputxt"  ignore="ignore"  value='${basStudentInfoPage.bsImei}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">加密卡号</label>
						</td>
					</tr>
					<tr>
						
						<td align="right">
							<label class="Validform_label">
								设备ID:
							</label>
						</td>
						<td class="value">
						    <input id="bsDeviceid" name="bsDeviceid" type="text" maxlength="50" style="width: 150px" class="inputxt"  ignore="ignore"  value='${basStudentInfoPage.bsDeviceid}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">设备ID</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								图片:
							</label>
						</td>
						<td class="value">
		<table id="bs_photo_fileTable"></table>
		<div class="form jeecgDetail">
			<t:upload name="bsPhoto" id="bsPhoto" queueID="filediv_bsPhoto" outhtml="false" uploader="cgUploadController.do?saveFiles"  extend="pic" buttonText='添加图片'  onUploadStart="bsPhotoOnUploadStart"> </t:upload>
			<div class="form" id="filediv_bsPhoto"></div>
			<script type="text/javascript">
				function bsPhotoOnUploadStart(file){
					var cgFormId=$("input[name='id']").val();
					$('#bsPhoto').uploadify("settings", "formData", {
						'cgFormId':cgFormId,
						'cgFormName':'bas_student',
						'cgFormField':'BS_PHOTO'
					});
				}
			</script>
		</div>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">图片</label>
						</td>
					</tr>
					<tr>
						<%-- <td align="right">
							<label class="Validform_label">
								上学站点ID:
							</label>
						</td>
						<td class="value">
							<input id="blSizeid" name="blSizeid" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"  onclick="popupClick(this,'id,bl_name,bs_name','blSizeid,blName,blSize','line_msg')" value='${basStudentInfoPage.blSizeid}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">上学站点ID</label>
						</td> --%>
						<td align="right">
							<label class="Validform_label">
								上学线路:
							</label>
						</td>
						<td class="value">
						<input id="blSizeid" name="blSizeid" type="hidden" style="width: 150px" class="searchbox-inputtext"  ignore="checked" datatype="*"  onclick="popupClick(this,'id,bl_name,bs_name','blSizeid,blName,blSize','line_msg')" value='${basStudentInfoPage.blSizeid}'/>
							
							<input id="blName" name="blName" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="checked" datatype="*"  onclick="popupClick(this,'id,bl_name,bs_name','blSizeid,blName,blSize','line_msg')" value='${basStudentInfoPage.blName}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">上学线路</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								上学站点:
							</label>
						</td>
						<td class="value">
							<input id="blSize" name="blSize" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="checked" datatype="*"  onclick="popupClick(this,'id,bl_name,bs_name','blSizeid,blName,blSize','line_msg')" value='${basStudentInfoPage.blSize}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">上学站点</label>
						</td>
					</tr>
					<%-- <tr>
						
						<td align="right">
							<label class="Validform_label">
								放学站点ID:
							</label>
						</td>
						<td class="value">
							<input id="blSizeid1" name="blSizeid1" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"  onclick="popupClick(this,'id,bl_name,bs_name','blSizeid1,blName1,blSize1','line_msg1')" value='${basStudentInfoPage.blSizeid1}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">放学站点ID</label>
						</td> 
					</tr>--%>
					<tr>
						<td align="right">
							<label class="Validform_label">
								放学路线:
							</label>
						</td>
						<td class="value">
							<input id="blSizeid1" name="blSizeid1" type="hidden" style="width: 150px" class="searchbox-inputtext"  ignore="checked" datatype="*"  onclick="popupClick(this,'id,bl_name,bs_name','blSizeid1,blName1,blSize1','line_msg1')" value='${basStudentInfoPage.blSizeid1}'/>
							<input id="blName1" name="blName1" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="checked" datatype="*"  onclick="popupClick(this,'id,bl_name,bs_name','blSizeid1,blName1,blSize1','line_msg1')" value='${basStudentInfoPage.blName1}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">放学路线</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								放学站点:
							</label>
						</td>
						<td class="value">
							<input id="blSize1" name="blSize1" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="checked" datatype="*"  onclick="popupClick(this,'id,bl_name,bs_name','blSizeid1,blName1,blSize1','line_msg1')" value='${basStudentInfoPage.blSize1}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">放学站点</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								地址:
							</label>
						</td>
						<td class="value">
							<input id="bsAddress" name="bsAddress" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"  value='${basStudentInfoPage.bsAddress}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">地址</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								是否有效:
							</label>
						</td>
						<td class="value">
							<t:dictSelect field="bsStatus" type="list"  typeGroupCode="sf_yn"   defaultVal="${basStudentInfoPage.bsStatus}" hasLabel="false"  title="是否有效" ></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">是否有效</label>
						</td>
					</tr>
				
					<tr>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value"  colspan="3" >
						  	 	<textarea id="bsDesc" style="height:auto;width:95%;" class="inputxt" rows="6" name="bsDesc"  ignore="ignore" >${basStudentInfoPage.bsDesc}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/jeecg/basstudentinfo/basStudentInfo.js"></script>		
<script type="text/javascript">
		  	//加载 已存在的 文件
		  	$(function(){
	  			var cgFormId=$("input[name='id']").val();
		  		$.ajax({
		  		   type: "post",
		  		   url: "basStudentInfoController.do?getFiles&id=" +  cgFormId,
		  		   success: function(data){
		  			 var arrayFileObj = jQuery.parseJSON(data).obj;
		  			 
		  			$.each(arrayFileObj,function(n,file){
		  				var fieldName = file.field.toLowerCase();
		  				var table = $("#"+fieldName+"_fileTable");
		  				var tr = $("<tr style=\"height:34px;\"></tr>");
		  				var title = file.title;
		  				if(title.length > 15){
		  					title = title.substring(0,12) + "...";
		  				}
		  				var td_title = $("<td>" + title + "</td>");
		  		  		var td_download = $("<td><a style=\"margin-left:10px;\" href=\"commonController.do?viewFile&fileid=" + file.fileKey + "&subclassname=org.jeecgframework.web.cgform.entity.upload.CgUploadEntity\" title=\"下载\">下载</a></td>")
		  		  		var td_view = $("<td><a style=\"margin-left:10px;\" href=\"javascript:void(0);\" onclick=\"openwindow('预览','commonController.do?openViewFile&fileid=" + file.fileKey + "&subclassname=org.jeecgframework.web.cgform.entity.upload.CgUploadEntity','fList',700,500)\">预览</a></td>");
		  		  		tr.appendTo(table);
		  		  		td_title.appendTo(tr);
		  		  		td_download.appendTo(tr);
		  		  		td_view.appendTo(tr);
		  		  		if(location.href.indexOf("load=detail")==-1){
			  		  		var td_del = $("<td><a style=\"margin-left:10px;\" href=\"javascript:void(0)\" class=\"jeecgDetail\" onclick=\"del('cgUploadController.do?delFile&id=" + file.fileKey + "',this)\">删除</a></td>");
			  		  		td_del.appendTo(tr);
		  		  		}
		  			 });
		  		   }
		  		});
		  	});
		  	
		  	/**
		 	 * 删除图片数据资源
		 	 */
		  	function del(url,obj){
		  		var content = "请问是否要删除该资源";
		  		var navigatorName = "Microsoft Internet Explorer"; 
		  		if( navigator.appName == navigatorName ){ 
		  			$.dialog.confirm(content, function(){
		  				submit(url,obj);
		  			}, function(){
		  			});
		  		}else{
		  			layer.open({
						title:"提示",
						content:content,
						icon:7,
						yes:function(index){
							submit(url,obj);
						},
						btn:['确定','取消'],
						btn2:function(index){
							layer.close(index);
						}
					});
		  		}
		  	}
		  	
		  	function submit(url,obj){
		  		$.ajax({
		  			async : false,
		  			cache : false,
		  			type : 'POST',
		  			url : url,// 请求的action路径
		  			error : function() {// 请求失败处理函数
		  			},
		  			success : function(data) {
		  				var d = $.parseJSON(data);
		  				if (d.success) {
		  					var msg = d.msg;
		  					tip(msg);
		  					obj.parentNode.parentNode.parentNode.deleteRow(obj.parentNode.parentNode);
		  				} else {
		  					tip(d.msg);
		  				}
		  			}
		  		});
		  	}
		  	
	  		function jeecgFormFileCallBack(data){
	  			if (data.success == true) {
					uploadFile(data);
				} else {
					if (data.responseText == '' || data.responseText == undefined) {
						$.messager.alert('错误', data.msg);
						$.Hidemsg();
					} else {
						try {
							var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'), data.responseText.indexOf('错误信息'));
							$.messager.alert('错误', emsg);
							$.Hidemsg();
						} catch(ex) {
							$.messager.alert('错误', data.responseText + '');
						}
					}
					return false;
				}
				if (!neibuClickFlag) {
					var win = frameElement.api.opener;
					win.reloadTable();
				}
	  		}
	  		function upload() {
					$('#bsPhoto').uploadify('upload', '*');	
			}
			
			var neibuClickFlag = false;
			function neibuClick() {
				neibuClickFlag = true; 
				$('#btn_sub').trigger('click');
			}
			function cancel() {
					$('#bsPhoto').uploadify('cancel', '*');	
			}
			function uploadFile(data){
				if(!$("input[name='id']").val()){
					if(data.obj!=null && data.obj!='undefined'){
						$("input[name='id']").val(data.obj.id);
					}
				}
				if($(".uploadify-queue-item").length>0){
					upload();
				}else{
					if (neibuClickFlag){
						alert(data.msg);
						neibuClickFlag = false;
					}else {
						var win = frameElement.api.opener;
						win.reloadTable();
						win.tip(data.msg);
						frameElement.api.close();
					}
				}
			}
	  	</script>
  