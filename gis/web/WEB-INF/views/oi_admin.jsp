<%--
  Created by IntelliJ IDEA.
  User: Seven
  Date: 2015/6/2
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>广东海洋大学校园地图</title>

    <!-- bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" charset="utf-8">

    <link href="${pageContext.request.contextPath}/css/gisMap.css" rel="stylesheet" type="text/css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body style="font-family: 微软雅黑">
<div class="container">
    <h2 class="col-md-offset-5">系统管理操作说明</h2>
    <div class="col-md-offset-1 col-md-10">
        <h3>1.系统统计</h3>

        <h4>1.1 数据统计</h4>
        <h5>在系统管理的功能目录栏中，点击“系统统计”“数据统计”功能链接，将在系统管理的主操作窗口中显示当前所有已提出修改建议的物点的统计列表，如下图1所示；在统计列表中，点击某一记录行中“查看详情”链接，将在系统管理的主操作窗口中显示相应物点名字（如教学主楼）的修改建议详情，如下图2所示：</h5>
        <div style="text-align: center">
            <img src="${pageContext.request.contextPath}/images/io_admin/1.png">
            <h6>图1 数据统计列表</h6>
            <img src="${pageContext.request.contextPath}/images/io_admin/2.png" width="600">
            <h6>图2 教学主楼的名字修改建议详情列表</h6>
        </div>

        <h4>1.2 导出数据</h4>
        <h5>在系统管理的功能目录栏中，点击“系统统计”“导出数据”功能链接，即可将当前所有的修改建议以EXCEL表格的形式导出，如下图3所示：</h5>
        <div style="text-align: center">
            <img src="${pageContext.request.contextPath}/images/io_admin/3.png">
            <h6>图3 导出数据的功能链接</h6>
        </div>


        <h3>2. 用户管理</h3>
        <h4>2.1 添加用户</h4>
        <h5>在系统管理的功能目录栏中，点击“用户管理”“添加用户”功能链接，将在系统管理的主操作窗口中显示添加新的系统用户的操作框，如下图4所示。在该操作框中按照提示输入用户名以及密码后，单击“提交”按钮，即可完成新用户的添加操作。
            注意：新添加的系统用户具有使用“数据统计”、“导出数据”、“修改密码”等功能的权限，但不具备使用“添加用户”、“删除用户”、“重置密码”等功能的权限。</h5>
        <div style="text-align: center">
            <img src="${pageContext.request.contextPath}/images/io_admin/4.png">
            <h6>图4 添加系统新用户的操作框</h6>
        </div>

        <h4>2.2 所有用户</h4>
        <h5>在系统管理的功能目录栏中，点击“用户管理”“所有用户”功能链接，将在系统管理的主操作窗口中显示当前系统的用户列表，如下图5所示。在该功能模块中，系统管理员可执行删除用户、重置用户密码等功能。</h5>
        <div style="text-align: center">
            <img src="${pageContext.request.contextPath}/images/io_admin/5.png">
            <h6>图5 系统的用户列表框</h6>
        </div>

        <h5>（1） 删除用户</h5>
        <h5>在系统的用户列表框中，点击某一记录行中的“删除”链接（如下图6所示），将弹出相应用户的删除确认框（如下图7所示），点击该确认框中“确定”按钮即可删除该用户。</h5>
        <div style="text-align: center">
            <img src="${pageContext.request.contextPath}/images/io_admin/6.png">
            <h6>图6 删除系统用户的功能链接</h6>
            <img src="${pageContext.request.contextPath}/images/io_admin/7.png">
            <h6>图7 删除系统用户的确认框</h6>
        </div>

        <h5>（2） 重置用户密码</h5>
        <h5>在系统的用户列表框中，点击某一记录行中的“重置密码”链接（如下图8所示），将弹出相应用户的密码重置确认框（如下图9所示），点击该确认框中“确定”按钮即可将该用户的密码重置为默认密码“123456”。在系统的用户列表框中，点击某一记录行中的“重置密码”链接（如下图8所示），将弹出相应用户的密码重置确认框（如下图9所示），点击该确认框中“确定”按钮即可将该用户的密码重置为默认密码“123456”。</h5>
        <div style="text-align: center">
            <img src="${pageContext.request.contextPath}/images/io_admin/8.png">
            <h6>图8 重置系统用户密码的功能链接</h6>
            <img src="${pageContext.request.contextPath}/images/io_admin/9.png">
            <h6>图9 重置系统用户密码的确认框</h6>
        </div>

        <h5>2.3 修改密码</h5>
        <h5>在系统管理的功能目录栏中，点击“用户管理”“修改密码”功能链接，将在系统管理的主操作窗口中显示修改当前用户的密码的操作框，如下图10所示。在该操作框中按照提示输入2次新密码后，单击“提交”按钮，即可完成对当前用户的密码的修改操作。</h5>
        <div style="text-align: center">
            <img src="${pageContext.request.contextPath}/images/io_admin/10.png">
            <h6>图10 修改当前用户的密码的操作框</h6>
        </div>

    </div>
</div>


<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/bootstrap.min.js"></script>
</body>
</html>
