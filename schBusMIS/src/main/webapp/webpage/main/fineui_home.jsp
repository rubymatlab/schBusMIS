<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!--360浏览器优先以webkit内核解析-->
<title>校车管理平台</title>
<link rel="shortcut icon" href="images/favicon.ico">
<link href="plug-in/hplus/css/bootstrap.min.css?v=3.3.6"
	rel="stylesheet">
<link href="plug-in/hplus/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<link href="plug-in/hplus/css/animate.css" rel="stylesheet">
<link href="plug-in/hplus/css/style.css?v=4.1.0" rel="stylesheet">
<link rel="stylesheet" href="plug-in/themes/fineui/main/iconfont.css">
<script src="plug-in/laydate/laydate.js"></script>
<style type="text/css">
.gray-bg {
	background-color: #e9ecf3;
}

.col-sm-2 {
	width: 10%;
	padding-left: 5px;
	padding-right: 5px;
	float: left;
}

.p-lg {
	padding: 0px 0px 10px 0px;
}

.widget {
	margin-top: 0px;
}

.iconfont {
	font-size: 30px;
	color: white;
}

h2 {
	font-size: 19px;
}

.echart_div {
	height: 240px;
	width: 100%;
}

.ibtn {
	cursor: pointer;
}

.flot-chart {
	height: 400px;
}
/*  .top-navigation .wrapper.wrapper-content{padding:20px 5px !important;}
	.container {
    	 width:99% !important; margin:10px;
    	 padding-right: 1px !important;
    	 padding-left: 1px !important;
	}
	.color_red{color:#e55555;}
	.col-cs-2 {
	    width: 10%;
		padding-left: 5px;
		padding-right: 5px;
		float: left;
	}*/
@media ( min-width : 992px) {
	.col-cs-2 {
		width: 11.11%;
		padding-left: 5px;
		padding-right: 5px;
		float: left;
	}
}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row  border-bottom white-bg dashboard-header">
			<div class="col-sm-12">
				<blockquote class="text-warning" style="font-size: 14px">
					<h4 class="text-danger">校车管理平台</h4>
				</blockquote>
				<hr>
			</div>
		</div>

		<div class="wrapper wrapper-content"></div>
		<!-- 全局js -->
		<script src="plug-in/hplus/js/jquery.min.js?v=2.1.4"></script>
		<script src="plug-in/hplus/js/bootstrap.min.js?v=3.3.6"></script>
		<!-- 自定义js -->
		<script src="plug-in/hplus/js/content.js"></script>
		<script type="text/javascript" src="plug-in/echart/echarts.min.js"></script>
		<script type="text/javascript"
			src="plug-in/jquery-plugs/i18n/jquery.i18n.properties.js"></script>
		<t:base type="tools"></t:base>
		<script type="text/javascript" src="plug-in/login/js/getMsgs.js"></script>
		
</body>
</html>