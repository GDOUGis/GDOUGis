<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/10
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>详情</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<table class="table">
    <tr>
        <td>编号</td>
        <td>修改名</td>
        <td>描述</td>
        <td>修改人</td>
        <td>身份</td>
        <td>所在学院</td>
        <td>联系电话</td>
    </tr>
    <c:forEach items="${list}" var="modify">
        <tr>
            <td>${modify.id}</td>
            <td>${modify.name}</td>
            <td>${modify.description}</td>
            <td>${modify.people}</td>
            <td>${modify.identification == 0?'学生':'教师'}</td>
            <td>${modify.college}</td>
            <td>${modify.phone}</td>
        </tr>
    </c:forEach>
</table>

<script type="text/javascript" src="/scripts/jquery.min.js"></script>
<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
</body>
</html>
