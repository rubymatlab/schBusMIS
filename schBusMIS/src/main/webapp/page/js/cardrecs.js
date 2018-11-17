var openid = GetQueryString("Open");
var postUrl = path+"/baswxcontroller.do?getcardinfo02";//请求路径
if (openid != "" || openid != null) {
    $content = $("#contentwx");//获取div的id
    var postData = { openid: openid };//请求数据
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
                    cour = cour + ' <tbody > ' +
                                        '  <tr> ' +
                                            '  <td style="width:200px">' + data[i].bs_cardno + '</td> ' +
                                            '  <td style="width:200px">' + data[i].bs_name + '</td> ' +
                                            '  <td style="width:200px">' + data[i].bc_datetime + '</td> ' +
                                        '  </tr> ' +
                                    '  </tbody> ';
                }
                courtilte = courtilte + ' <table style="width:100%" id="table11"> ' +
                                            ' <thead> ' +
                                                ' <tr> ' +
                                                    ' <th style="width:200px" >卡号</th> ' +
                                                    ' <th style="width:200px" >姓名</th> ' +
                                                    ' <th style="width:200px" >刷卡时间</th> ' +
                                                ' </tr> ' +
                                            ' </thead> ' + cour +
                                           '  </table> ';
                $content.append(courtilte);//div赋值

            } else {
                $content.append(' <div style="text-align:center;" class="weui_text_area"> ' +
                                ' <p class="weui_msg_desc"> </p> '+
                                ' <p class="weui_msg_desc"><h5 class="weui_msg_title">您还没有刷卡数据</h5></p> '+
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