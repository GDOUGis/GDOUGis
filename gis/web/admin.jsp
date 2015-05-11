<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html dir="ltr" lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>校园地图管理员登录</title>
 <link rel="shortcut icon" href="${pageContext.request.contextPath }/browser/img/common/1.ico" type="image/x-icon"/>
<!-- CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/adminLogin/style.css"
	type="text/css" />

<!-- Javascript libraries (jQuery and Selectivizr) used for the custom checkbox ->

	<!--[if (gte IE 6)&(lte IE 8)]>
		<script type="text/javascript" src="${pageContext.request.contextPath }/adminLogin/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/adminLogin/selectivizr.js"></script>
		<noscript><link rel="stylesheet" href="${pageContext.request.contextPath }/adminLogin/fallback.css" /></noscript>
	<![endif]-->

<script type="text/javascript">
	function validate() {
		with (document.all) {
			var username = document.getElementById('username');
			var password = document.getElementById('password');
			if (password.value == "" || username.value == "") {
				alert("用户名和密码都不能为空！");
				return false;
			} else {
				document.forms[0].submit();
			}
		}
	}
</script>

</head>

<body>
	<c:if test="${!empty message}">
		<script type="text/javascript">
			alert("${message}");
		</script>
	</c:if>
	<div id="container">
		<%--<form action="${pageContext.request.contextPath }/servlet/UserServlet?method=login"
			method="post" onsubmit="return validate()">--%><form action="${pageContext.request.contextPath }/servlet/UserServlet?method=login"
			method="post">
			<div class="login">后台登录</div>
			<div class="username-text">用户名:</div>
			<div class="password-text">密码:</div>
			<div class="username-field">
				<input type="text" name="username" id="username"
					placeholder="请输入用户名" value="admin" />
			</div>
			<div class="password-field">
				<input type="password" name="password" id="password"
					placeholder="请输入密码"  value="admin"/>
			</div>
			<input type="submit" name="submit" value="GO" />
		</form>
	</div>
	<div id="footer">
		Copyright &copy; 2014.Company name All rights reserved.
	</div>
</body>
</html>