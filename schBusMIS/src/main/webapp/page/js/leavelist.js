var openid = GetQueryString("Open");
var ruletype=localStorage.getItem("ruletype");
//ruletype="2";
//alert(ruletype);
var postUrl = path+"/baswxcontroller.do?leavelist";//请求路径
if (openid != "" || openid != null) {
    $content = $("#contentwx");//获取div的id
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
                for (var i = 0; i < data.length; i++) {
                	if(ruletype=="1"){
	                    cour = cour + ' <tbody > ' +
	                                        '  <tr> ' +
	                                            '  <td style="width:150px">' + data[i].bl_begdate + '</td> ' +
	                                            '  <td style="width:150px">' + data[i].bl_enddate + '</td> ' +
	                                            '  <td style="width:300px">' + data[i].bl_reason + '</td> ' +
	                                            '  <td style="width:50px">' + data[i].status + '</td> ' +
	                                        '  </tr> ' +
	                                    '  </tbody> ';
                    
                	}else if(ruletype=="2"){
	                    cour = cour + ' <tbody > ' +
                        '  <tr> ' +
                        	'  <td> <input type="checkbox" name="boxs" value=' + data[i].id + '>'+
                        	'  <td style="width:150px">' + data[i].bs_name + '</td> ' +
                            '  <td style="width:150px">' + data[i].bl_begdate + '</td> ' +
                            '  <td style="width:150px">' + data[i].bl_enddate + '</td> ' +
                            '  <td style="width:300px">' + data[i].bl_reason + '</td> ' +
                            '  <td style="width:50px">' + data[i].status + '</td> ' +
                        '  </tr> ' +
                    '  </tbody> ';               		
                	}  
                }
                if(ruletype=="1"){ 
	                courtilte = courtilte + ' <table style="width:100%" id="table11"> ' +
	                                            ' <thead> ' +
	                                                ' <tr> ' +
	                                                    ' <th style="width:150px" >开始日期</th> ' +
	                                                    ' <th style="width:150px" >结束日期</th> ' +
	                                                    ' <th style="width:300px" >请假原因</th> ' +
	                                                    ' <th style="width:50px" >状态</th> ' +
	                                                ' </tr> ' +
	                                            ' </thead> ' + cour +
	                                           '  </table> ';
                }else if(ruletype=="2"){
	                courtilte = courtilte + ' <table style="width:100%" id="table11"> ' +
                    ' <thead> ' +
                        ' <tr> ' +
                        	' <th style="width:50px"> <input id="allboxs" onclick="allcheck()" type="checkbox"/></th> ' +
                        	' <th style="width:150px" >姓名</th> ' +
                            ' <th style="width:150px" >开始日期</th> ' +
                            ' <th style="width:150px" >结束日期</th> ' +
                            ' <th style="width:300px" >请假原因</th> ' +
                            ' <th style="width:50px" >状态</th> ' +
                        ' </tr> ' +
                    ' </thead> ' + cour +
                   '  </table> ';                	
                }
                
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
                                 ' <p class="weui_msg_desc"><h5 class="weui_msg_title">没有请假数据</h5></p> ' +
                                 ' </div>');//div赋值
}

// 获取url的参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
}

function allcheck() {
    var nn = $("#allboxs").is(":checked"); //判断th中的checkbox是否被选中，如果被选中则nn为true，反之为false
        if(nn == true) {
            var namebox = $("input[name^='boxs']");  //获取name值为boxs的所有input
            for(i = 0; i < namebox.length; i++) {
                namebox[i].checked=true;    //js操作选中checkbox
            }
        }
        if(nn == false) {
            var namebox = $("input[name^='boxs']");
            for(i = 0; i < namebox.length; i++) {
                namebox[i].checked=false;
            }
        }
}

function checksel() {
    var oidArray = new Array();
    var checkcontent = $("input[name='boxs']:checked"); //所选中
    $.each(checkcontent, function () {
        oidArray.push($(this).val());;
    });
    if (oidArray.length <= 0) {
    	alert('请选择要审批的请假记录');
    } else{
		var $dialog = $('#dialog1');
		$dialog.show();
		$dialog.find('.weui_btn_dialog').one('click', function () {
		    $dialog.hide();
		});
    }
}

//请假审批
function approve(approvetype) {
        var oidArray = new Array();
        var checkcontent = $("input[name='boxs']:checked"); //所选中
        $.each(checkcontent, function () {
            oidArray.push($(this).val());;
        });
        if (oidArray.length <= 0) {
        	alert('请选择要审批的请假记录');
        } else {
            var stroid = JSON.stringify(oidArray);
            var postUrl = path+"/baswxcontroller.do?approve";//请求路径
            //alert("postUrl"+postUrl);
            var postData = { openid: openid,stroid:stroid,approvetype:approvetype };//请求数据
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
                    	window.location.href = "leavelist.html?Open=" + openid; 
                    }
                    
                },
                error: function () {
                    showDialog('请求出错!');
                }
            });
        }//end else  

}