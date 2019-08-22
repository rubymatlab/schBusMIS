<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery"></t:base>
<body>


	<div id="result"><font size="20">欢迎来到进出口界面！</font></div>
	<table width="100%">
		<tr>
			<td style='border:1px solid' width="50%">
				<div id="chanel1"></div>
			</td>
			<td  style='border:1px solid' width="50%">
				<div id="chanel2"></div>
			</td>
		</tr>
	</table>



	<script>
		var type = 0;
		if (typeof (EventSource) !== "undefined") {
			var eventSource = new EventSource(
					"basContrailYunController.do?pushMessage");
			eventSource.onopen = function() {
				/* console.log("连接打开。。。。。。"); */
			}
			eventSource.onerror = function(e) {
			};
			//如果服务器响应报文中没有指明事件，默认触发message事件
			eventSource.onmessage = function(event) {
				var item = JSON.parse(event.data);
				var temp = "";
				if (!jQuery.isEmptyObject(item)) {
					temp+='<p><font size="20" color="red">'+item.bsDesc+'</font></p>';
					if ((item.bsPhoto == '' || item.bsPhoto == null)
							&& item.bsSex == '0')
						temp += '<img src="upload/files/boy.jpg" width="500" height="500" />';
					else if ((item.bsPhoto == '' || item.bsPhoto == null)
							&& item.bsSex == '1')
						temp += '<img src="upload/files/girl.jpg" width="500" height="500" />';
					else
						temp += '<img src="'+item.bsPhoto+'" width="500" height="500" />';
					temp += '<br />卡号：' + item.bsCardno;
					temp += '<br />姓名：' + item.bsName;
					if (item.bsSex == '0')
						temp += '<br />性别：男';
					else if (item.bsSex == '1')
						temp += '<br />性别：女';
					else
						temp += '<br />性别:' + item.bsSex;
					temp += '<br /> 年级：' + item.bcGrade;
					temp += '<br />班级：' + item.bcName;

					if (type == '0') {
						document.getElementById("chanel1").innerHTML = temp;
						type = 1;
					} else {
						document.getElementById("chanel2").innerHTML = temp;
						type = 0;
					}
				}
			};
		} else {
			document.getElementById("result").innerHTML = "对不起，您的浏览器不支持HTML5";
		}
	</script>

</body>