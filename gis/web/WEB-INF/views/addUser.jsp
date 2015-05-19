<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>校园地图后台管理</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/gdou.ico">

    <!-- bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

    <script>
        function check(){
            with(document.all){
                if(username.value==""){
                    alert("用户名不能为空");
                    return false;
                }else if(password.value==""){
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
    <div class="container">
        <div class="col-lg-offset-2" style="margin-top: 80px;">
            <form action="${pageContext.request.contextPath}/servlet/UserServlet?method=add" method="post" onsubmit="return check()">
                <div class="form-group">
                    <label for="exampleInputEmail1">用户名</label>
                    <input type="text" name="username" class="username form-control" id="exampleInputEmail1" placeholder="请输入用户名">
                </div>
                <div class="form-group">
                    <label for="password">密码:</label>
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
