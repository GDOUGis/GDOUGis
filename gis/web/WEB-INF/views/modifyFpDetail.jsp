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
<body style="font-family: 微软雅黑">
<div style="font-size: 22px;">
    对&nbsp;<span style="color: blue">${featurePointName}</span>&nbsp;的修改情况如下:
</div>
<table class="table table-striped">
    <tr>
        <td>编号</td>
        <td>修改名</td>
        <td>描述</td>
        <td>修改人</td>
        <td>身份</td>
        <td>所在学院</td>
        <td>联系电话</td>
    </tr>
    <c:forEach items="${result.list}" var="modify">
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
<div id="page" style="text-align: center">
    <c:if test="${result.page.currentPage-1>0}">
        <a href="${pageContext.request.contextPath}/servlet/ModifyServlet?method=showModifiedFPDetail&feature_id=${result.list[0].feature_id}&pageNum=${result.page.currentPage-1}">[上一页]</a>
    </c:if>
    <%-- <c:if test="${result.page.currentPage-1<=0}">
         <a href="${pageContext.request.contextPath}/servlet/ModifyServlet?method=loadFPModifyPD&pageNum=${result.page.startPage}">[上一页]</a>
     </c:if>--%>
    <c:forEach begin="${result.page.startPage }" end="${result.page.endPage }" var="currentPage">
        [<a href="${pageContext.request.contextPath}/servlet/ModifyServlet?method=showModifiedFPDetail&feature_id=${result.list[0].feature_id}&pageNum=${currentPage}"<c:if test="${result.page.currentPage == currentPage}">style="color: red;"</c:if>>${currentPage }</a>]
    </c:forEach>
    <%-- <c:if test="${result.page.currentPage+1>result.page.totalPage}">
         <a href="${pageContext.request.contextPath}/servlet/ModifyServlet?method=loadFPModifyPD&pageNum=${result.page.totalPage}">[下一页]</a>
     </c:if>--%>
    <c:if test="${result.page.currentPage+1<=result.page.totalPage}">
        <a href="${pageContext.request.contextPath}/servlet/ModifyServlet?method=showModifiedFPDetail&feature_id=${result.list[0].feature_id}&pageNum=${result.page.currentPage+1}">[下一页]</a>
    </c:if>

</div>
    <div style="text-align: right;margin-right: 150px;">
        <button type="button" class="btn btn-primary" onclick="window.history.go(-1)">返回</button>
    </div>
<script type="text/javascript" src="/scripts/jquery.min.js"></script>
<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
</body>
</html>
