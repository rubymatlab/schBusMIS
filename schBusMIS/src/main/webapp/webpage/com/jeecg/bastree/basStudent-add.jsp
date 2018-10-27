<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>学生资料</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<t:base type="bootstrap,layer,validform,bootstrap-form"></t:base>
</head>
 <body style="overflow:hidden;overflow-y:auto;">
 <div class="container" style="width:100%;">
	<div class="panel-heading"></div>
	<div class="panel-body">
	<form class="form-horizontal" role="form" id="formobj" action="basStudentController.do?doAdd" method="POST">
		<input type="hidden" id="btn_sub" class="btn_sub"/>
		<input type="hidden" id="id" name="id"/>
		<div class="form-group">
			<label for="bsName" class="col-sm-3 control-label">学生姓名：</label>
			<div class="col-sm-7">
				<div class="input-group" style="width:100%">
					<input id="bsName" name="bsName" type="text" maxlength="50" class="form-control input-sm" placeholder="请输入学生姓名"  datatype="*" ignore="checked" />
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="bsSex" class="col-sm-3 control-label">性别：</label>
			<div class="col-sm-7">
				<div class="input-group" style="width:100%">
					<t:dictSelect field="bsSex" type="list" extendJson="{class:'form-control input-sm'}"  datatype="*"  typeGroupCode="sex"  hasLabel="false"  title="性别"></t:dictSelect>     
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="bcParent" class="col-sm-3 control-label">家长：</label>
			<div class="col-sm-7">
				<div class="input-group" style="width:100%">
					<input id="bcParent" name="bcParent" type="text" maxlength="50" class="form-control input-sm" placeholder="请输入家长"  ignore="ignore" />
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="bsTel" class="col-sm-3 control-label">家长手机：</label>
			<div class="col-sm-7">
				<div class="input-group" style="width:100%">
					<input id="bsTel" name="bsTel" type="text" maxlength="32" class="form-control input-sm" placeholder="请输入家长手机"  datatype="n" ignore="ignore" />
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="bcGrade" class="col-sm-3 control-label">年级：</label>
			<div class="col-sm-7">
				<div class="input-group" style="width:100%">
		      		<input id="bcGrade" name="bcGrade" type="text" maxlength="4" class="form-control input-sm" placeholder="请输入年级"  ignore="ignore" />
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="bcName" class="col-sm-3 control-label">班级：</label>
			<div class="col-sm-7">
				<div class="input-group" style="width:100%">
		      		<input id="bcName" name="bcName" type="text" maxlength="50" class="form-control input-sm" placeholder="请输入班级"  ignore="ignore" />
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="bsCardno" class="col-sm-3 control-label">卡号：</label>
			<div class="col-sm-7">
				<div class="input-group" style="width:100%">
					<input id="bsCardno" name="bsCardno" type="text" maxlength="32" class="form-control input-sm" placeholder="请输入卡号"  ignore="ignore" />
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="blName" class="col-sm-3 control-label">线路：</label>
			<div class="col-sm-7">
				<div class="input-group" style="width:100%">
		      		<input id="blName" name="blName" type="text" maxlength="32" class="form-control input-sm" placeholder="请输入线路"  ignore="ignore" />
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="blSize" class="col-sm-3 control-label">站点：</label>
			<div class="col-sm-7">
				<div class="input-group" style="width:100%">
		      		<input id="blSize" name="blSize" type="text" maxlength="32" class="form-control input-sm" placeholder="请输入站点"  ignore="ignore" />
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="bsAddress" class="col-sm-3 control-label">地址：</label>
			<div class="col-sm-7">
				<div class="input-group" style="width:100%">
					<input id="bsAddress" name="bsAddress" type="text" maxlength="100" class="form-control input-sm" placeholder="请输入地址"  ignore="ignore" />
				</div>
			</div>
		</div>
					<div class="form-group">
						<label for="bsDesc" class="col-sm-3 control-label">备注：</label>
						<div class="col-sm-7">
				    <div class="input-group" style="width:100%">
						  	 	<textarea name="bsDesc" value = "${basStudent.bsDesc}" class="form-control input-sm" rows="6"  ignore="ignore" ></textarea>
						<span class="Validform_checktip" style="float:left;height:0px;"></span>
						<label class="Validform_label" style="display: none">备注</label>
			          </div>
						</div>
	</form>
	</div>
 </div>
<script type="text/javascript">
var subDlgIndex = '';
$(document).ready(function() {
	//单选框/多选框初始化
	$('.i-checks').iCheck({
		labelHover : false,
		cursor : true,
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green',
		increaseArea : '20%'
	});
	
	//表单提交
	$("#formobj").Validform({
		tiptype:function(msg,o,cssctl){
			if(o.type==3){
				validationMessage(o.obj,msg);
			}else{
				removeMessage(o.obj);
			}
		},
		btnSubmit : "#btn_sub",
		btnReset : "#btn_reset",
		ajaxPost : true,
		beforeSubmit : function(curform) {
		},
		usePlugin : {
			passwordstrength : {
				minLen : 6,
				maxLen : 18,
				trigger : function(obj, error) {
					if (error) {
						obj.parent().next().find(".Validform_checktip").show();
						obj.find(".passwordStrength").hide();
					} else {
						$(".passwordStrength").show();
						obj.parent().next().find(".Validform_checktip").hide();
					}
				}
			}
		},
		callback : function(data) {
			var win = frameElement.api.opener;
			if (data.success == true) {
				frameElement.api.close();
			    win.reloadTable();
			    win.tip(data.msg);
			} else {
			    if (data.responseText == '' || data.responseText == undefined) {
			        $.messager.alert('错误', data.msg);
			        $.Hidemsg();
			    } else {
			        try {
			            var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'), data.responseText.indexOf('错误信息'));
			            $.messager.alert('错误', emsg);
			            $.Hidemsg();
			        } catch (ex) {
			            $.messager.alert('错误', data.responseText + "");
			            $.Hidemsg();
			        }
			    }
			    return false;
			}
		}
	});
		
});
</script>
</body>
</html>