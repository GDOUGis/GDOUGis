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
    <title>报表</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <table class="table">
        <tr>
            <td>编号</td>
            <td>特征点名称</td>
            <td>修改名数（个）</td>
            <td>操作</td>
        </tr>
        <c:forEach items="${list}" var="modify">
            <tr>
                <td>${modify.feature_id}</td>
                <td>${modify.name}</td>
                <td>${modify.times}</td>
                <td><a href='/servlet/ModifyServlet?method=showModifiedFPDetail&feature_id=${modify.feature_id}'>查看详情</a></td>
            </tr>
        </c:forEach>
    </table>

<script type="text/javascript" src="/scripts/jquery.min.js"></script>
<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script>
    /*jQuery(document).ready(function(){

        $(window).load(function() {
            // send request to get all features data.
            var url = "/servlet/ModifyServlet";
            var params = {
                method:"loadFPModifyPD",
                pageNum:1,
                pageSize:20
            };
            $.getJSON(url, params, function(data){

            });
        });
    });*/
</script>
</body>
</html>
