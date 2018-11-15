var openid = GetQueryString("openid");
//$("#openid").append("openid:" + openid);
var accessToken = GetQueryString("accessToken");
//accessToken=decode(accessToken,"UTF-8");
//var accessToken =getURLParameters("accessToken");
//accessToken = toUnicodeFun(accessToken); 

//var o='{"subscribe":1,"openid":"osNrf0tc9973_9wZVDt8yKiUixBk","nickname":"규원아","sex":1,"language":"zh_CN","city":"汕尾","province":"广东","country":"中国","headimgurl":"http:\/\/thirdwx.qlogo.cn\/mmopen\/Xbh8ymicprMQQkbV54PlzXuJ8ia5BwVqVqZaqvboGvANkIaBe6EcA3VhyjjouxibWSUnNH1Kcia0rIDkj2khib9ia8j1WqHCdRppMe\/132","subscribe_time":1532144513,"remark":"","groupid":0,"tagid_list":[],"subscribe_scene":"ADD_SCENE_SEARCH","qr_scene":0,"qr_scene_str":""}';
//alert(openid+"--accessToken-->"+accessToken);
var info = JSON.parse(accessToken);
//alert("info3:"+info.nickname+";"+info.headimgurl);
$showminfo = $("#showminfo");//获取div的id
var whole = ' <a class="portrait_box"> ' +
                       ' <img class="portrait_img" src="' + info.headimgurl + '"> ' +
                       ' <div class="box_bg"></div> ' +
                       ' <i class="clear"></i> ' +
                   ' </a> ' +
                   ' <div class="portrait_name"> ' +
                       ' <span class="ellipsis" id="ellipsis">' + info.nickname  + '</span> ' +
                   ' </div> ' +
                   ' <div class="portrait_info"> ' +
                       ' <a id="infoProvince">' + info.province + "-" + info.city + '</a> ' +
                       ' <a id="infoProvince">&nbsp;&nbsp;&nbsp;&nbsp;</a> ' +
                   ' </div> ' +
                   ' <i class="clear"></i> ';
$showminfo.append(whole);//div赋值

function toUnicodeFun(data){
	  if(data == '' || typeof data == 'undefined') return '请输入汉字';
	   var str =''; 
	   for(var i=0;i<data.length;i++){
	      str+="\\u"+data.charCodeAt(i).toString(16);
	   }
	   return str;
}


//绑定
function geturlhui() {
    	alert("openid:" + openid);
        var postUrl = "http://devzhu.hk1.mofasuidao.cn/schBusMIS/baswxcontroller.do?binding";//请求路径
        var postData = { openid: openid };//请求数据
        $.ajax({
            type: 'POST',
            url: postUrl,
            data: postData,
            dataType: 'json',
            timeout: 15000,
            success: function (data) {
                var re=JSON.stringify(data);
                //alert("返回01:"+re+";"+data)
                if (re == "" || re == 0 || re== "0" || re == null) {
                    window.location.href = "memberlist.html?Open=" + openid;
                } else {
                    window.location.href = "unmemberlist.html?Open=" + openid;
                }

            },
            error: function () {
                showDialog('请求出错!');
            }
        });
    

}

//我的刷卡记录
function geturlke() {
    var postUrl = "http://devzhu.hk1.mofasuidao.cn/schBusMIS/baswxcontroller.do?getcardinfo";//请求路径
    //alert("openid"+openid);
    var postData = { openid: openid };//请求数据
    $.ajax({
        type: 'POST',
        url: postUrl,
        data: postData,
        dataType: 'json',
        timeout: 15000,
        success: function (data) {
            //alert("dghdfghd:"+data[0].result)
        	var re=JSON.stringify(data);
            if (re == "" || re == 0 || re== "0" || re == null) {
            	window.location.href = "fans.html?Open=" + openid;
            } else {
            	 window.location.href = "cardrecs.html?Open=" + openid;
            }
        },
        error: function () {
            showDialog('请求出错!');
        }
    });

}

//我的请假
function geturlshou() {
    
    var postUrl = "http://devzhu.hk1.mofasuidao.cn/schBusMIS/baswxcontroller.do?getcardinfo";//请求路径
    var postData = { openid: openid };//请求数据
    $.ajax({
        type: 'POST',
        url: postUrl,
        data: postData,
        dataType: 'json',
        timeout: 15000,
        success: function (data) {
            //alert("dghdfghd:"+data[0].result)
        	var re=JSON.stringify(data);
            if (re == "" || re == 0 || re== "0" || re == null) {
            	window.location.href = "fans.html?Open=" + openid;
            } else {
            	 window.location.href = "leave.html?Open=" + openid;
            }
            
        },
        error: function () {
            showDialog('请求出错!');
        }
    });
}

// 获取url的参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
}
