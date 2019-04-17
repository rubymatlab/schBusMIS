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
    new Date(begin.setDate((new Date().getDate() +1)));
/*    var begintime = begin.format("yyyy-MM-dd");
    var endtime = end.format("yyyy-MM-dd");
    $('#begb').val(begintime);
    $('#bege').val(endtime);*/
    var begintime = begin.format("yyyy-MM-dd");
    //var endtime = end.format("yyyy-MM-dd hh:mm");
    $('#begb').val(begintime);
    //$('#bege').val(endtime);

    //获取人员信息
    var openid = GetQueryString("Open");
    var ruletype=localStorage.getItem("ruletype");
    var postUrl = path+"/baswxcontroller.do?getStudent02";//请求路径
    if (openid != "" || openid != null) {
        $levname = $("#levname");//获取div的id
        var postData = { openid: openid,ruletype:ruletype };//请求数据
        var cour = '';
        var courtilte = '';
        $.ajax({
            type: 'POST',
            url: postUrl,
            data: postData,
            dataType: 'json',
            timeout: 15000,
            success: function (data) {
            	if (data.length != 0 || data.length!="0") {
	            	var str = '';
	            	for(var o in data) {
	            		str += '<option value="'+data[o].id+'">'+data[o].bs_name+'</option>';
	            	}
	            	$levname.append(str);

                } else {
                	$levname.append(' <div style="text-align:center;" class="weui_text_area"> ' +
                                    ' <p class="weui_msg_desc"> </p> '+
                                    ' <p class="weui_msg_desc"><h5 class="weui_msg_title">您还没有绑定</h5></p> '+
                                    ' </div>');//div赋值
                }
            },
            error: function () {
                showDialog('请求出错!');
            }
        });
    } else {
        $courses.append(' <div style="text-align:center;" class="weui_text_area"> ' +
                                     ' <p class="weui_msg_desc"> </p> ' +
                                     ' <p class="weui_msg_desc"><h5 class="weui_msg_title">您还没有刷卡数据</h5></p> ' +
                                     ' </div>');//div赋值
    }
    
    
		
// 获取url的参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
}

function getUrl() {
	var begb=document.getElementById('begb').value;
	//var bege=document.getElementById('bege').value;
    var reason=document.getElementById('reason').value;
    var openid = GetQueryString("Open");
    //var linetype= $("input[name='linetype']:checked").val();
    var reason=document.getElementById('reason').value;
    var stuid=document.getElementById('levname').value;
    
	var linetype="";
	var dd ="";
	var radio = document.getElementsByName("linetype");
	for(var i = 0;i<radio.length;i++)
	{
		if(radio[i].checked==true)
		{
			dd = radio[i].value;
			linetype= linetype+dd;
		}
	} 
  
    //alert("stuid:"+stuid);
	if(linetype!=""&&stuid!=""){
	    var postUrl = path+"/baswxcontroller.do?leave";//请求路径
	    if (openid != "" || openid!=null) {
	        var postData = { begb: begb,reason: reason, openid:openid,linetype:linetype,stuid:stuid};//请求数据
	        $.ajaxSettings.async = false;
	        $.ajax({
	            type: 'POST',
	            url: postUrl,
	            data: postData,
	            dataType: 'json',
	            timeout: 15000,
	            success: function (data) {
	            	var re=JSON.stringify(data);
	                if (re == "" || re == null) {
	                	window.location.href = "error.html";
	                } else {
	                	 window.location.href = "leavelist.html?Open=" + openid;
	                }
	            },
	            error: function () {
	            	alert('请求出错!');
	            }
	        });
	        //alert("03");
	    } else {
	        //alert("请输入手机号码！");
	        window.location.href = "error.html";
	    }		
	}else{
		alert('请选择您要申请的类型！');
	}

}

function getHistory() {
	var openid = GetQueryString("Open");
	//alert(openid);
	window.location.href = "leavelist.html?Open=" + openid;
}


