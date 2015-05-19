<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.cpp.gis.servlet.InitServlet" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>广东海洋大学校园地图</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/gdou.ico">

  </head>
  
  <body>

    <%
        response.sendRedirect(request.getContextPath()+"/servlet/InitServlet");
    %>
  </body>
</html>
