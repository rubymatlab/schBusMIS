﻿// 获取url的参数
function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return decodeURI(r[2]);
	return null;
}
var va = $(" input[ name='tall' ] ").val();
function getUrl() {
	var va = $(" input[ name='tall' ] ").val();
	var openid = GetQueryString("Open");
	var ruletype = $("input[name='ruleType']:checked").val();
	var vaCode = $(" input[ name='tallcode' ] ").val();
	// $("input[name='reportType']:checked").val();

	var postUrl = path + "/baswxcontroller.do?insertopenid";// 请求路径
	if (va != "" && va != null && openid != "" && openid != null
			&& vaCode != null && vaCode != "") {
		var postData = {
			tell : va,
			openid : openid,
			ruletype : ruletype,
			tellcode : vaCode
		};// 请求数据
		$.ajax({
			type : 'POST',
			url : postUrl,
			data : postData,
			dataType : 'json',
			timeout : 15000,
			success : function(data) {
				var re = JSON.stringify(data);
				if (re == 1) {
					localStorage.setItem("ruletype", ruletype); // 本地存储ruletype
					// window.location.href = "ok.html";
					window.location.href = "unmemberlist.html?Open=" + openid;
					// window.location.href = "index.html?openid=" + openid;
				} else if (re == 0) {
					alert("该手机号码不存在，请重新输入！");
				} else if (re == 2) {
					alert("验证码不存在，请重新输入！");
				} else {
					window.location.href = "error.html";
				}

			},
			error : function() {
				showDialog('请求出错!');
			}
		});
	} else {
		alert("请输入手机号码、验证码！");
		// window.location.href = "error.html";
	}
}

function postSmsCode() {
	var va = $(" input[ name='tall' ] ").val();
	var openid = GetQueryString("Open");
	var postUrl = path + "/baswxcontroller.do?postSmsCode";// 请求路径
	// alert(va+"a;"+ruleType);
	if (va != "" && va != null && openid != "" && openid != null) {
		var postData = {
			tell : va,
			openid : openid
		};// 请求数据
		$.ajax({
			type : 'POST',
			url : postUrl,
			data : postData,
			dataType : 'json',
			success : function(data) {
				var re = JSON.stringify(data);
				if (re == 1) {
					settime($("#sendcode"));
				} else if (re == 0) {
					alert("该手机号码不存在，请重新输入！");
				} else if (re == 2) {
					settime($("#sendcode"));
					alert("刚才验证码有效期为5分钟，请勿频繁发送！");
				} else {
					window.location.href = "error.html";
				}
			},
			error : function() {
				showDialog('请求出错!');
			}
		});
	} else {
		alert("请输入手机号码！");
	}
}

var countdown=300;
//发送验证码倒计时
function settime(obj) { 
    if (countdown == 0) { 
        obj.attr('disabled',false); 
        obj.val("免费获取验证码");
        countdown = 300; 
        return;
    } else { 
        obj.attr('disabled',true);
        obj.val("重新发送(" + countdown + ")");
        countdown--; 
    } 
setTimeout(function() { 
    settime(obj) }
    ,1000) 
}

// 判断手机号码
function yz(str) {
	var re = /^1\d{10}$/;
	if (!re.test(str)) {
		alert("手机号格式错误！");
	}
}
