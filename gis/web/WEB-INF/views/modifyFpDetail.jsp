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
    <title>校园地图后台管理</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/gdou.ico">

</head>
<body style="font-family: 微软雅黑">
<hr>
<div style="font-style: italic; margin-top:10px; font-size: 16px;">
    对&nbsp;<span style="color: #FF4E14">${featurePointName}</span>&nbsp;的修改情况如下:
</div>
<table class="table table-striped">
    <tr>
        <td>修改名</td>
        <td>描述</td>
        <td>修改人</td>
        <td>身份</td>
        <td>所在单位</td>
        <td>联系电话</td>
        <td>提交时间</td>
        <c:if test="${user.is_Su==1}">
            <td>操作</td>
        </c:if>
    </tr>
    <c:forEach items="${result.list}" var="modify">
        <tr>
            <td>${modify.name}</td>
            <td>${modify.description}</td>
            <td>${modify.people}</td>
            <td>${modify.identification}</td>
            <td>${modify.college}</td>
            <td>${modify.phone}</td>
            <td>${modify.date}</td>
            <c:if test="${user.is_Su==1}">
                <td><a href="${pageContext.request.contextPath}/servlet/ModifyServlet?method=delete&modifyId=${modify.id}&feature_id=${modify.feature_id}" onclick="return confirm('确认要删除吗？')">删除</a></td>
            </c:if>
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
        <a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/servlet/ModifyServlet?method=loadFPModifyPD">返回</a>
    </div>
<script type="text/javascript" src="/scripts/jquery.min.js"></script>
<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
</body>
</html>
