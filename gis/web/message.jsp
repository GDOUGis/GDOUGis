<%--
  Created by IntelliJ IDEA.
  User: Seven
  Date: 2015/5/11
  Time: 20:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title></title>
    <!-- bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
    <h3>出错了==。</h3>
    <c:if test="${!empty message}">
        <h4>${message}</h4>
    </c:if>
    <button class="btn btn-primary" onclick="window.history.go(-1)">返回</button>
</body>
</html>
