    Date.prototype.format = function (format) {
        var o = {
            "M+": this.getMonth() + 1, // month
            "d+": this.getDate(), // day
            "h+": this.getHours(), // hour
            "m+": this.getMinutes(), // minute
            "s+": this.getSeconds(), // second
            "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
            "S": this.getMilliseconds()
            // millisecond
        }
        if (/(y+)/.test(format))
            format = format.replace(RegExp.$1, (this.getFullYear() + "")
                    .substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(format))
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                        : ("00" + o[k]).substr(("" + o[k]).length));
        return format;
    }

    var begin = new Date();
    var end = new Date();
    //new Date(begin.setMonth((new Date().getMonth() - 1)));
    new Date(begin.setDate((new Date().getDate() - 1)));
/*    var begintime = begin.format("yyyy-MM-dd");
    var endtime = end.format("yyyy-MM-dd");
    $('#begb').val(begintime);
    $('#bege').val(endtime);*/
    var begintime = begin.format("yyyy-MM-dd hh:mm");
    var endtime = end.format("yyyy-MM-dd hh:mm");
    $('#begb').val(begintime);
    $('#bege').val(endtime);


 
		
// 获取url的参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
}

function getUrl() {
	var begb=document.getElementById('begb').value;
	var bege=document.getElementById('bege').value;
    var reason=document.getElementById('reason').value;
    var openid = GetQueryString("Open");
    //alert(openid+";"+begb+";"+bege+";"+reason);
    var postUrl = "http://tdcq.natapp1.cc/schBusMIS/baswxcontroller.do?leave";//请求路径
    if (openid != "" || openid!=null) {
        var postData = { begb: begb,bege: bege,reason: reason, openid:openid};//请求数据
        $.ajaxSettings.async = false;
        $.ajax({
            type: 'POST',
            url: postUrl,
            data: postData,
            dataType: 'json',
            timeout: 15000,
            success: function (data) {
            	var re=JSON.stringify(data);
                if (re == "" || re == 0 || re== "0" || re == null) {
                	window.location.href = "error.html";
                } else {
                	 window.location.href = "leavelist.html?Open=" + openid;
                }
            },
            error: function () {
                //showDialog('请求出错!');
            	alert('请求出错!');
            }
        });
        //alert("03");
    } else {
        //alert("请输入手机号码！");
        window.location.href = "error.html";
    }
}


