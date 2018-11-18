    var openId = GetQueryString("Open");
    var postUrl = path+"/baswxcontroller.do?getStudent";//请求路径
    var ruletype=localStorage.getItem("ruletype");
    var postData = { openid: openId,ruletype:ruletype };//请求数据
    $.ajax({
        type: 'POST',
        url: postUrl,
        data: postData,
        dataType: 'json',
        timeout: 15000,
        success: function (data) {
            //alert(data[0].bs_name)
            $(" input[ name='xingming' ] ").val(data[0].bs_name);
            $(" input[ name='tall' ] ").val(data[0].bs_tel);
        },
        error: function () {
            showDialog('请求出错!');
        }
    });




//var openId=JSON.parse(GetQueryString("resdata")).openId;
function getUrl() {
    //var va = $(" input[ name='tall' ] ").val();
	var ruletype=localStorage.getItem("ruletype");
	if (window.confirm('确定解绑？ 如解绑则查阅不到个人的相关信息!')) {
	    var postUrl = path+"/baswxcontroller.do?unBinding";//请求路径
	    if (openId != "" || openId != null) {
	        var postData = { openid:openId,ruletype:ruletype};//请求数据
	        $.ajax({
	            type: 'POST',
	            url: postUrl,
	            data: postData,
	            dataType: 'json',
	            timeout: 15000,
	            success: function (data) {
	            	var re=JSON.stringify(data);
	            	if (re == 1){
	                    window.location.href = "memberlist.html?Open=" + openId;            			
	            	}else{
	            		window.location.href = "error.html";
	            	}
	  
	            },
	            error: function () {
	                showDialog('请求出错!');
	            }
	        });
	    }
	    
	}

}


// 获取url的参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
}