<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>校园地图后台管理</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/gdou.ico">

</head>
<body style="font-family: 微软雅黑">
<table class="table table-striped">
    <tr>
        <td>编号</td>
        <td>用户名</td>
        <td>用户类型</td>
        <td>操作</td>
    </tr>
    <c:forEach items="${result.list}" var="u">
        <tr>
            <td>${u.id}</td>
            <td>${u.username}</td>
            <td>${u.is_Su == 1 ? "管理员" : "普通用户"}</td>
            <td>
                <c:if test="${u.username ne sessionScope.user.username}">
                    <a href='${pageContext.request.contextPath}/servlet/UserServlet?method=delete&id=${u.id}' onclick="return confirm('是否将该用户删除')">删除</a>
                    &nbsp;
                </c:if>
                <a href='${pageContext.request.contextPath}/servlet/UserServlet?method=reset&id=${u.id}' onclick="return confirm('是否将该用户密码重置为123456')">重置密码</a>
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

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.min.js"></script>

</body>
</html>
