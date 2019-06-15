<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>电子围栏</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<t:base type="bootstrap,layer,validform,bootstrap-form"></t:base>
</head>
 <body style="overflow:hidden;overflow-y:auto;">
 <div class="container" style="width:100%;">
	<div class="panel-heading"></div>
	<div class="panel-body">
	<form class="form-horizontal" role="form" id="formobj" action="busMapfenceController.do?doUpdate" method="POST">
		<input type="hidden" id="btn_sub" class="btn_sub"/>
		<input type="hidden" id="id" name="id" value="${busMapfence.id}"/>
		<div class="row">
		<div class="bt-item col-md-6 col-sm-6">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-3 bt-label">
					设备id：
				</div>
				<div class="col-md-9 col-sm-9 col-xs-9 bt-content">
					<input name="deviceId" type="text" class="form-control" maxlength="32" value = "${busMapfence.deviceId}"  ignore="ignore"  />
				</div>
			</div>
		</div>
		<div class="bt-item col-md-6 col-sm-6">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-3 bt-label">
					信号强度：
				</div>
				<div class="col-md-9 col-sm-9 col-xs-9 bt-content">
					<input name="signalPower" type="text" class="form-control" maxlength="32" value = "${busMapfence.signalPower}"  ignore="ignore"  />
				</div>
			</div>
		</div>
		<div class="bt-item col-md-6 col-sm-6">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-3 bt-label">
					小区id：
				</div>
				<div class="col-md-9 col-sm-9 col-xs-9 bt-content">
					<input name="cellId" type="text" class="form-control" maxlength="32" value = "${busMapfence.cellId}"  ignore="ignore"  />
				</div>
			</div>
		</div>
		<div class="bt-item col-md-6 col-sm-6">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-3 bt-label">
					电压：
				</div>
				<div class="col-md-9 col-sm-9 col-xs-9 bt-content">
					<input name="batteryVoltage" type="text" class="form-control" maxlength="32" value = "${busMapfence.batteryVoltage}"  ignore="ignore"  />
				</div>
			</div>
		</div>
		<div class="bt-item col-md-6 col-sm-6">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-3 bt-label">
					gps时间：
				</div>
				<div class="col-md-9 col-sm-9 col-xs-9 bt-content">
					<input name="gpsTime" type="text" class="form-control" maxlength="32" value = "${busMapfence.gpsTime}"  ignore="ignore"  />
				</div>
			</div>
		</div>
		<div class="bt-item col-md-6 col-sm-6">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-3 bt-label">
					纬度：
				</div>
				<div class="col-md-9 col-sm-9 col-xs-9 bt-content">
					<input name="gpsLatitude" type="text" class="form-control" maxlength="32" value = "${busMapfence.gpsLatitude}"  ignore="ignore"  />
				</div>
			</div>
		</div>
		<div class="bt-item col-md-6 col-sm-6">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-3 bt-label">
					经度：
				</div>
				<div class="col-md-9 col-sm-9 col-xs-9 bt-content">
					<input name="gpsLongitude" type="text" class="form-control" maxlength="32" value = "${busMapfence.gpsLongitude}"  ignore="ignore"  />
				</div>
			</div>
		</div>
		<div class="bt-item col-md-6 col-sm-6">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-3 bt-label">
					时间戳：
				</div>
				<div class="col-md-9 col-sm-9 col-xs-9 bt-content">
					<input name="timestamp" type="text" class="form-control" maxlength="32" value = "${busMapfence.timestamp}"  ignore="ignore"  />
				</div>
			</div>
		</div>
		<div class="bt-item col-md-6 col-sm-6">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-3 bt-label">
					设备发送时间：
				</div>
				<div class="col-md-9 col-sm-9 col-xs-9 bt-content">
					<input name="deviceTime" type="text" class="form-control" maxlength="32" value = "${busMapfence.deviceTime}"  ignore="ignore"  />
				</div>
			</div>
		</div>
		<div class="bt-item col-md-6 col-sm-6">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-3 bt-label">
					下一次时间戳：
				</div>
				<div class="col-md-9 col-sm-9 col-xs-9 bt-content">
					<input name="nextTrcTime" type="text" class="form-control" maxlength="32" value = "${busMapfence.nextTrcTime}"  ignore="ignore"  />
				</div>
			</div>
		</div>
		<div class="bt-item col-md-6 col-sm-6">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-3 bt-label">
					位置精确的精度：
				</div>
				<div class="col-md-9 col-sm-9 col-xs-9 bt-content">
					<input name="accuracy" type="text" class="form-control" maxlength="32" value = "${busMapfence.accuracy}"  ignore="ignore"  />
				</div>
			</div>
		</div>
		<div class="bt-item col-md-6 col-sm-6">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-3 bt-label">
					设备发送次数：
				</div>
				<div class="col-md-9 col-sm-9 col-xs-9 bt-content">
					<input name="counter" type="text" class="form-control" maxlength="32" value = "${busMapfence.counter}"  ignore="ignore"  />
				</div>
			</div>
		</div>
		<div class="bt-item col-md-6 col-sm-6">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-3 bt-label">
					类型：
				</div>
				<div class="col-md-9 col-sm-9 col-xs-9 bt-content">
					<input name="type" type="text" class="form-control" maxlength="32" value = "${busMapfence.type}"  ignore="ignore"  />
				</div>
			</div>
		</div>
		<div class="bt-item col-md-6 col-sm-6">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-3 bt-label">
					消息：
				</div>
				<div class="col-md-9 col-sm-9 col-xs-9 bt-content">
					<input name="msg" type="text" class="form-control" maxlength="32" value = "${busMapfence.msg}"  ignore="ignore"  />
				</div>
			</div>
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