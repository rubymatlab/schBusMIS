    var openId = GetQueryString("Open");
    var postUrl = path+"/baswxcontroller.do?getStudent";//请求路径
    var ruletype=localStorage.getItem("ruletype");
    var cour = '';
    var courtilte = '';
    $content = $("#contentwx");//获取div的id
    var postData = { openid: openId,ruletype:ruletype };//请求数据
    $.ajax({
        type: 'POST',
        url: postUrl,
        data: postData,
        dataType: 'json',
        timeout: 15000,
        success: function (data) {
            //alert(data[0].bs_name+";"+data.length)
            $(" input[ name='xingming' ] ").val(data[0].bs_name);
            $(" input[ name='tall' ] ").val(data[0].bs_tel);
            
            

            if (data.length != 0 || data.length!="0") {
                for (var i = 0; i < data.length; i++) {
	                    cour = cour + ' <tbody > ' +
	                                        '  <tr> ' +
	                                            '  <td style="width:80px">' + data[i].bs_name + '</td> ' +
	                                            '  <td style="width:80px">' + data[i].bc_name + '</td> ' +
	                                        '  </tr> ' +
	                                    '  </tbody> ';                   
                }
	                courtilte = courtilte + ' <table style="width:100%" id="table11"> ' +
	                                            ' <thead> ' +
	                                                ' <tr> ' +
	                                                    ' <th style="width:80px" >姓名</th> ' +
	                                                    ' <th style="width:80px" >班级</th> '+
	                                                ' </tr> ' +
	                                            ' </thead> ' + cour +
	                                           '  </table> ';
                
                $content.append(courtilte);//div赋值

            } else {
                $content.append(' <div style="text-align:center;" class="weui_text_area"> ' +
                                ' <p class="weui_msg_desc"> </p> '+
                                ' <p class="weui_msg_desc"><h5 class="weui_msg_title">没有数据</h5></p> '+
                                ' </div>');//div赋值
            }
        
            
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