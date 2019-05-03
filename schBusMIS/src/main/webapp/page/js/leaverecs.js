   //获取线路信息
    var openid = GetQueryString("Open");
    var ruletype=localStorage.getItem("ruletype");
    var postUrl = path+"/baswxcontroller.do?getlinename02";//请求路径
    //alert("openid:"+openid+";"+ruletype);
    if(ruletype==2){
	    if (openid != "" || openid != null) {
	        $levname = $("#linname");//获取div的id
	        var postData = { openid: openid,ruletype:"2" };//请求数据
	        var cour = '';
	        var courtilte = '';
	        $.ajax({
	            type: 'POST',
	            url: postUrl,
	            data: postData,
	            dataType: 'json',
	            timeout: 15000,
	            success: function (data) {
	            	//alert(data.length);
	            	if (data.length != 0 || data.length!="0") {
		            	var str = '';
		            	for(var o in data) {
		            		str += '<option value="'+data[o].blname+'">'+data[o].blname+'</option>';
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
	    }
    }
    else {
        $courses.append(' <div style="text-align:center;" class="weui_text_area"> ' +
                                     ' <p class="weui_msg_desc"> </p> ' +
                                     ' <p class="weui_msg_desc"><h5 class="weui_msg_title">您没有权限查看</h5></p> ' +
                                     ' </div>');//div赋值
    }
    
    
function getUrl() {
	var openid = GetQueryString("Open");
	var ruletype=localStorage.getItem("ruletype");
	var postUrl = path+"/baswxcontroller.do?getleaverecs";//请求路径
	var linename=document.getElementById('linname').value;
	//alert(linename);
	
	if (ruletype != "" || ruletype != null) {
	    $content = $("#contentwx");//获取div的id
	    var postData = {ruletype:"2",linename:linename};//请求数据
	    var cour = '';
	    var courtilte = '';
	    $.ajax({
	        type: 'POST',
	        url: postUrl,
	        data: postData,
	        dataType: 'json',
	        timeout: 15000,
	        success: function (data) {
	    	    cour = '';
	    	    courtilte = '';
	    	    $content.empty();//清空
	            if (data.length != 0 || data.length!="0") {
	                for (var i = 0; i < data.length; i++) {
	                    cour = cour + ' <tbody > ' +
	                                        '  <tr> ' +
	                                            '  <td style="width:130px">' + data[i].bl_student + '</td> ' +
	                                            '  <td style="width:170px">' + data[i].bl_begdate + '</td> ' +
	                                            '  <td style="width:300px">' + data[i].blname + '</td> ' +
	                                        '  </tr> ' +
	                                    '  </tbody> ';
	                }
	                courtilte =  ' <table style="width:100%" id="table11"> ' +
	                                            ' <thead> ' +
	                                                ' <tr> ' +
	                                                    ' <th style="width:100px" >姓名</th> ' +
	                                                    ' <th style="width:100px" >请假时间</th> ' +
	                                                    ' <th style="width:100px" >线路</th> ' +
	                                                ' </tr> ' +
	                                            ' </thead> ' + cour +
	                                           '  </table> ';
	                $content.append(courtilte);//div赋值
	
	            } else {
	                $content.append(' <div style="text-align:center;" class="weui_text_area"> ' +
	                                ' <p class="weui_msg_desc"> </p> '+
	                                ' <p class="weui_msg_desc"><h5 class="weui_msg_title">没有请假数据</h5></p> '+
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

}

// 获取url的参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
}