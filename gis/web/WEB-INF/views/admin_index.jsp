<%--
  Created by IntelliJ IDEA.
  User: Seven
  Date: 2015/5/11
  Time: 14:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>校园地图后台管理</title>
</head>
<frameset rows="64,*,60"  frameborder="no" border="0" framespacing="0">
    <frame src="${pageContext.request.contextPath}/servlet/ForwardServlet?resource=admin_top" name="topFrame" noresize="noresize" frameborder="no"  scrolling="no" marginwidth="0" marginheight="0"  />
    <frameset cols="200,*"  id="frame">
        <frame src="${pageContext.request.contextPath}/servlet/ForwardServlet?resource=admin_left" name="leftFrame" noresize="noresize" marginwidth="0" marginheight="0" frameborder="0" scrolling="no"  />
        <frame src="${pageContext.request.contextPath}/servlet/ForwardServlet?resource=admin_main" name="main" marginwidth="0" marginheight="0" frameborder="0" scrolling="auto" />
    </frameset>
    <frame src="${pageContext.request.contextPath}/servlet/ForwardServlet?resource=admin_bottom" name="topFrame" noresize="noresize" frameborder="no"  scrolling="no" marginwidth="0" marginheight="0"  />
    <noframes>
        <body></body>
    </noframes>
</frameset>
</html>

