<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>报表</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body style="font-family: 微软雅黑">
<table class="table table-striped">
    <tr>
        <td>编号</td>
        <td>用户名</td>
        <td>操作</td>
    </tr>
    <c:forEach items="${result.list}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>
                <a href='${pageContext.request.contextPath}/servlet/UserServlet?method=delete&id=${user.id}'>删除</a>
                &nbsp;
                <a href='${pageContext.request.contextPath}/servlet/UserServlet?method=reset&id=${user.id}' onclick="confirm('是否将该用户密码重置为123456')">重置密码</a>
            </td>
        </tr>
    </c:forEach>
</table>

<div id="page" style="text-align: center">
    <c:if test="${result.page.currentPage-1>0}">
        <a href="${pageContext.request.contextPath }/servlet/UserServlet?method=list&pageNum=${result.page.currentPage-1}">[上一页]</a>
    </c:if>
    <%-- <c:if test="${result.page.currentPage-1<=0}">
         <a href="${pageContext.request.contextPath}/servlet/ModifyServlet?method=loadFPModifyPD&pageNum=${result.page.startPage}">[上一页]</a>
     </c:if>--%>
    <c:forEach begin="${result.page.startPage }" end="${result.page.endPage }" var="currentPage">
        [<a href="${pageContext.request.contextPath }/servlet/UserServlet?method=list&pageNum=${currentPage}"<c:if test="${result.page.currentPage == currentPage}">style="color: red;"</c:if>>${currentPage }</a>]
    </c:forEach>
    <%-- <c:if test="${result.page.currentPage+1>result.page.totalPage}">
         <a href="${pageContext.request.contextPath}/servlet/ModifyServlet?method=loadFPModifyPD&pageNum=${result.page.totalPage}">[下一页]</a>
     </c:if>--%>
    <c:if test="${result.page.currentPage+1<=result.page.totalPage}">
        <a href="${pageContext.request.contextPath }/servlet/UserServlet?method=list&pageNum=${result.page.currentPage+1}">[下一页]</a>
    </c:if>
</div>

<script type="text/javascript" src="/scripts/jquery.min.js"></script>

</body>
</html>
