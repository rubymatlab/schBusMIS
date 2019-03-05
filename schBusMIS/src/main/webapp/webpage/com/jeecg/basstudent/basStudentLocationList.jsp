<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<meta content="yes" name="apple-mobile-web-app-capable">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />


<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>电子围栏</title>
<script src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>


<style type="text/css">
html, body {
	width: 100%;
	height: 100%;
}

* {
	margin: 0px;
	padding: 0px;
}

body, button, input, select, textarea {
	font: 12px/16px Verdana, Helvetica, Arial, sans-serif;
}

p {
	width: 603px;
	padding-top: 3px;
	overflow: hidden;
}

.btn {
	width: 142px;
}

#container {
	min-width: 600px;
	min-height: 767px;
}
</style>
<script charset="utf-8"
	src="https://map.qq.com/api/js?v=2.exp&libraries=drawing&key=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77"></script>

<script>
	wx.config({
		debug : false,
		appId : '${appId}',
		timestamp : '${timestamp}',
		nonceStr : '${nonceStr}',
		signature : '${signature}',
		jsApiList : [
		// 所有要调用的 API 都要加到这个列表中
		'getLocation', 'openLocation' ]
	});
	wx.ready(function() {
		wx.getLocation({
			type : 'gcj02',
			success : function(res) {
				init();
			},
			cancel : function(res) {
				alert('用户拒绝授权获取地理位置');
			}
		});
	});
</script>
<script>
	window.onload = function() {
		//直接加载地图
		//初始化地图函数  自定义函数名init
		function init() {
			//惠州经济职业技术学院
			var centerJ = 0;
			var center = null;
			<c:forEach items="${locationList}" var="item">
				<c:if test="${item.bl_latitude>0 }">
					<c:if test="${item.bl_longitude>0 }">
					if (centerJ == 0) {
						center = new qq.maps.LatLng("${item.bl_latitude}",
								"${item.bl_longitude}");
					}
					centerJ++;
					</c:if>
				</c:if>
			</c:forEach>
			if (centerJ == 0)
				center = new qq.maps.LatLng(22.5333200000, 113.9304100000);
			
			var map = new qq.maps.Map(document.getElementById("container"), {
				center : center,
				zoom : 13
			});
			/* var cirle = new qq.maps.Circle({
				center : center,
				radius : 2000,
				map : map
			}); */

			var i = 0;
			<c:forEach items="${locationList}" var="item">
				<c:if test="${item.bl_latitude>0 }">
					<c:if test="${item.bl_longitude>0 }">
						var position = new qq.maps.LatLng("${item.bl_latitude}",
								"${item.bl_longitude}");
						if (i == 0) {
							var marker = new qq.maps.Marker({
								position : position,
								map : map,
								animation : qq.maps.MarkerAnimation.BOUNCE
							});
						} else {
							var marker = new qq.maps.Marker({
								position : position,
								map : map,
								animation : qq.maps.MarkerAnimation.BOUNCE
							});
						}
						i++;
						var label = new qq.maps.Label({
							//如果为true，表示可点击，默认true。
							clickable : true,
							position : position,
							//标签的文本。
							content : '${item.bs_name} ${item.bl_commdatetime}打卡',
							//显示标签的地图。
							map : map,
							//如果为true，表示标签可见，默认为true。
							visible : true
			
						});
					</c:if>
				</c:if>
			</c:forEach>

			//惠州东江沙公园
			/* var marker = new qq.maps.Marker({
				position : new qq.maps.LatLng(23.0923800000, 114.4253600000),
				map : map
			});
			var marker = new qq.maps.Marker({
				position : new qq.maps.LatLng(23.0662400000, 114.4318100000),
				map : map
			}); */

		}
		//调用初始化函数地图
		init();
	}
</script>
</head>
<body>
	<!--   定义地图显示容器   -->
	<div id="container"></div>
</body>
</html>