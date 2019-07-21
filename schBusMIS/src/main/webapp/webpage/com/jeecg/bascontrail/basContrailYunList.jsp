<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script>
	window.onload = function() {
		setTimeout('myrefresh()', 4000); //指定4秒刷新一次 
	}

	function myrefresh() {
		window.location.reload();
	}
</script>
<body>
	<div id="dv" />
	<table width="100%">
		<tr>
			<c:forEach items="${listBe}" var="item">
				<td>
				<c:if test="${(item.bsPhoto == '' || item.bsPhoto ==null) && item.bsSex == '0'}"><img src="upload/files/boy.jpg" width="165" height="165" /></c:if>
				<c:if test="${(item.bsPhoto == '' || item.bsPhoto ==null) && item.bsSex == '1'}"><img src="upload/files/girl.jpg" width="165" height="165" /></c:if>
				<c:if test="${(item.bsPhoto != '' && item.bsPhoto !=null)}"><img src="${item.bsPhoto }" width="165" height="165" /></c:if>
					<br /> 
					卡号：${item.bsCardno}<br /> 姓名：${item.bsName}<br />
					<c:if test="${item.bsSex == '0'}">性别：男</c:if>
					<c:if test="${item.bsSex == '1'}">性别：女</c:if>
					<br /> 年级：${item.bcGrade }<br />
					班级：${item.bcName }</td>
			</c:forEach>
		</tr>
	</table>
</body>