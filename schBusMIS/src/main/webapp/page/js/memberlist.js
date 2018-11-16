// 获取url的参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
}
var va = $(" input[ name='tall' ] ").val();
function getUrl() {
    var va = $(" input[ name='tall' ] ").val();
    var openid = GetQueryString("Open");
    var ruleType = $('input[name="ruleType"]:checked').val(); 

    item = $('input[name=age]:checked').val();
    var postUrl = "http://tdcq.natapp1.cc/schBusMIS/baswxcontroller.do?insertopenid";//请求路径
    alert(va+";"+ruleType);
    if (va != "" || va != null || openid != "" || openid!=null) {
        var postData = { tell: va, openid:openid};//请求数据
        $.ajax({
            type: 'POST',
            url: postUrl,
            data: postData,
            dataType: 'json',
            timeout: 15000,
            success: function (data) {
            	var re=JSON.stringify(data);
            	if (re == 1){
            		//window.location.href = "ok.html"; 
            		window.location.href = "index.html?openid=" + openid;
            	}else if (re==0){
            		alert("该手机号码不存在，请重新输入！");
            	}else{
            		window.location.href = "error.html";
            	}           	
            	
            },
            error: function () {
                showDialog('请求出错!');
            }
        });
    } else {
        alert("请输入手机号码！");
        //window.location.href = "error.html";
    }
}


//判断手机号码
function yz(str) {
    var re = /^1\d{10}$/;
    if (!re.test(str)) {
        alert("手机号格式错误！");
    }
}
