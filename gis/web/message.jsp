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
    <title>广东海洋大学校园地图</title>
    <!-- bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/gdou.ico">

    <script>
       if(window.parent != window){
           window.parent.location.href = window.location.href;
       }
    </script>
</head>
<body style="text-align: center">
   <div style="margin-top: 200px;">
       <h3>出错了==。</h3>
       <c:if test="${!empty message}">
           <h4>${message}</h4>
       </c:if>
       <button class="btn btn-primary" onclick="window.history.go(-1)">返回</button>
       <a class="btn btn-primary" href="${pageContext.request.contextPath}/servlet/InitServlet">首页</a>
   </div>
</body>
</html>
