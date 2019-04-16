<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<body onload="window.init()">
	<table style="width: 80%;" cellpadding="0" cellspacing="1"
		class="formtable">
		<tr>
			<td align="right"><label class="Validform_label"> 学生姓名:
			</label></td>
			<td class="value"><input id="bsName" name="bsName" type="text"
				maxlength="50" style="width: 150px" class="inputxt" datatype="*"
				ignore="checked" /> <span class="Validform_checktip"></span> <label
				class="Validform_label" style="display: none;">学生姓名</label></td>
			<td align="right"><label class="Validform_label"> 卡号: </label></td>
			<td class="value"><input id="bsCardno" name="bsCardno"
				type="text" maxlength="24" style="width: 150px" class="inputxt"
				ignore="ignore" /> <span class="Validform_checktip"></span> <label
				class="Validform_label" style="display: none;">卡号</label></td>
			</td>
			<td align="right"><label class="Validform_label"> 开始时间:
			</label></td>
			<td class="value"><input id="createDate" name="createDate"
				type="text" maxlength="24" style="width: 150px" class="Wdate"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				ignore="ignore" /> <span class="Validform_checktip"></span> <label
				class="Validform_label" style="display: none;">开始时间</label></td>
			<td align="right"><label class="Validform_label"> 结束时间:
			</label></td>
			<td class="value"><input id="updateDate" name="updateDate"
				type="text" maxlength="24" style="width: 150px" class="Wdate"
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				ignore="ignore" /> <span class="Validform_checktip"></span> <label
				class="Validform_label" style="display: none;">结束时间</label></td>
			<td><a href="#" class="easyui-linkbutton l-btn"
				iconcls="icon-search" onclick="basClassListsearch()" id="">查询</a></td>

		</tr>
	</table>
	<div id="panel">
		<div id="map_canvas"></div>
		<div style='width: 80%; height: 700px' id="infoDiv"></div>
	</div>
</body>

<style type="text/css">
#map_canvas {
	position: absolute;
	top: 40px;
	width: 98%;
	min-height: 700px;
}

#panel {
	padding: 5px;
}
</style>
<script charset="utf-8"
	src="https://map.qq.com/api/js?v=2.exp&libraries=drawing&key=ACEBZ-EM33F-CEYJL-NAU2E-RM65Q-GSBD5"></script>
<script>
	var markersArray = [];
	function basClassListsearch() {
		//console.log(basePath);
		$.ajax({
			url : basePath + "/basContrailController.do?doContrail",
			type : "POST",
			data : {
				bsName : $("#bsName").val(),
				bsCardno : $("#bsCardno").val(),
				createDate : $("#createDate").val(),
				updateDate : $("#updateDate").val()
			},//核心代码，form表单序列化  
			dataType : "JSON",
			success : function(data) {
				deleteOverlays();
				var arrayobject = data.obj;
				for (var i = 0; i < arrayobject.length; i++) {
					var position = new qq.maps.LatLng(
							arrayobject[i].gps_latitude,
							arrayobject[i].gps_longitude);
					var decoration = new qq.maps.MarkerDecoration(
							arrayobject[i].number, new qq.maps.Point(0,
									-5))

					var marker = new qq.maps.Marker({
						position : position,
						map : map,
						decoration : decoration
					});

					var label = new qq.maps.Label({
						position : position,
						//标签的文本。
						content : arrayobject[i].device_time,
						map : map,
						//如果为true，表示标签可见，默认为true。
						visible : true
					});
					markersArray.push(marker);
					markersArray.push(label);
					if (i == 0) {
						map.panTo(position);
					}
				} 
				//hello=[];
				/*for (var i = 1; i < arrayobject.length; i++) {
					//hello.push([arrayobject[i - 1].gps_longitude,
						//		arrayobject[i - 1].gps_latitude]); 
					//设置获取驾车线路方案的服务
					var drivingService = new qq.maps.TransferService({
						map : map,
						complete : function(res) {
							//console.log(res);
						}
					});
					//设置驾车方案
					drivingService
							.setPolicy(qq.maps.TransferPolicy["LEAST_WALKING"]);
					var start = new qq.maps.LatLng(
							arrayobject[i - 1].gps_latitude,
							arrayobject[i - 1].gps_longitude);
					var end = new qq.maps.LatLng(arrayobject[i].gps_latitude,
							arrayobject[i].gps_longitude);
					drivingService.search(start, end);

					drivingService.clear();

					var position = new qq.maps.LatLng(
							arrayobject[i].gps_latitude,
							arrayobject[i].gps_longitude);
					var decoration = new qq.maps.MarkerDecoration(
							arrayobject[i].number, new qq.maps.Point(0, -5))

					var marker = new qq.maps.Marker({
						position : position,
						map : map,
						decoration : decoration
					});

					var label = new qq.maps.Label({
						position : position,
						//标签的文本。
						content : arrayobject[i].device_time,
						map : map,
						//如果为true，表示标签可见，默认为true。
						visible : true
					});

					markersArray.push(drivingService);
					markersArray.push(marker);
					markersArray.push(label);
					if (i == 1)
						map.panTo(position);
				}*/
				//console.log(hello);
			}
		})
	}

	//清除地图上的marker
	function clearOverlay(overlays) {
		var overlay;
		while (overlay = overlays.pop()) {
			overlay.setMap(null);
		}
	}

	//删除覆盖物
	function deleteOverlays() {
		if (markersArray) {
			for (i in markersArray) {
				markersArray[i].setMap(null);
			}
			markersArray.length = 0;
		}
	}
	window.init = function() {
		//直接加载地图
		//初始化地图函数  自定义函数名init
		//function init() {
		//惠州经济职业技术学院
		var centerJ = 0;
		var center = null;
		var content = "";
		if (centerJ == 0)
			center = new qq.maps.LatLng(22.5333200000, 113.9304100000);

		//设置全局变量
		map = new qq.maps.Map(document.getElementById("map_canvas"), {
			center : center,
			zoom : 13,
			//启用缩放控件
			zoomControl : true,
			//设置缩放控件的位置和样式
			zoomControlOptions : {
				//设置缩放控件的位置为相对左方中间位置对齐.
				position : qq.maps.ControlPosition.LEFT_CENTER,
				//设置缩放控件样式为仅包含放大缩小两个按钮
				style : qq.maps.ZoomControlStyle.SMALL
			}
		});
		//}
	}
</script>
