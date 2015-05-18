<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>校园地图后台管理</title>

    <!-- bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

    <script>
        function check(){
            with(document.all){
                if(password.value==""){
                    alert("密码不能为空");
                    return false;
                }else if(password.value!=repassword.value)
                {
                    alert("您输入两次密码不一致");
                    return false;
                }else {
                    document.forms[0].submit();
                }
            }
        }
    </script>
</head>


<body style="font-family: '微软雅黑'">
<c:if test="${!empty  message}">
    <script>
        alert("${message}");
    </script>
</c:if>
<div class="container">
    <div class="col-lg-offset-2" style="margin-top: 80px;">
        <form action="${pageContext.request.contextPath}/servlet/UserServlet?method=updatePwd" method="post" onsubmit="return check()">
            <div class="form-group">
                <label for="password">新密码:</label>
                <input type="password" name="password" class="password form-control" id="password" placeholder="输入密码">
            </div>
            <div class="form-group">
                <label for="repassword">再次输入密码:</label>
                <input type="password" name="repassword" class="repassword form-control" id="repassword" placeholder="再次输入密码">
            </div>
            <input type="submit" class="btn btn-primary" value="提交">
        </form>
    </div>
</div>
</body>
</html>
