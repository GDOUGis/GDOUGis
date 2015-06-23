<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>校园地图后台管理</title>
    <link href="${pageContext.request.contextPath}/css/gisMap.css" rel="stylesheet" type="text/css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/gdou.ico">

</head>

<body  link="#000000" vlink="#000000" alink="#000000" >


<div class="header_warp">
    <div class="header">
        <!--logo开始处-->
        <div class="logo">
            <div style="float: left">
                <a href="#">
                    <img src="${pageContext.request.contextPath}/images/logo.png" height="60px">
                </a>
            </div>
            <div style="float: left;margin-top: 10px;margin-left: 10px;"><span style="font-size: 24px;font-family: '微软雅黑';">校园地图系统管理</span></div>
            <div style="float: right;margin-right: 150px;margin-top:10px;font-family: 微软雅黑">
                <label>欢迎&nbsp;${user.username}&nbsp;使用本系统</label>
                &nbsp;
                <a href="${pageContext.request.contextPath}/servlet/UserServlet?method=logout">退出</a>
            </div>
        </div>
        <!--logo结束处-->
    </div>
</div>


<script type="text/javascript" src="/scripts/jquery.min.js"></script>

</body>


</html>