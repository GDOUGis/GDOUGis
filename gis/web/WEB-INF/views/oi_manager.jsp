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
        <h3>1.系统管理登录</h3>

        <%--<h4>1.1 数据统计</h4>--%>
        <h5>步骤1：在地图操作窗口的右上方，点击“系统管理”功能链接（如下图1-1所示），将弹出“系统管理登录”对话框，如下图1-2所示：</h5>
        <div style="text-align: center">
            <img src="${pageContext.request.contextPath}/images/io_manager/1.png">
            <h6>图1-1 系统管理的功能链接</h6>
            <img src="${pageContext.request.contextPath}/images/io_manager/2.png" width="600">
            <h6>图1-2 系统管理登录对话框</h6>
        </div>

        <%--<h4>1.2 导出数据</h4>--%>
        <h5>步骤2：在上图1-2所示的“系统管理登录”对话框中，分别在“用户名”、“密码”文本框中输入系统管理员的用户名、密码，并单击“GO”按钮（如下图1-3所示），即可进入系统管理的操作界面，如下图1-4所示：</h5>
        <div style="text-align: center">
            <img src="${pageContext.request.contextPath}/images/io_manager/3.png">
            <h6>图1-3 输入系统管理员的用户名及登录密码</h6>
            <img src="${pageContext.request.contextPath}/images/io_manager/4.png">
            <h6>图1-4 系统管理操作界面</h6>
        </div>


        <h3>2.数据管理</h3>
        <h4>2.1 数据统计</h4>
        <h5>（1）查看物点名字修改建议的统计列表</h5>
        <h5>在系统管理的功能目录栏中，点击“数据管理”<span class="glyphicon glyphicon-arrow-right"></span>“数据统计”功能链接，将在系统管理的主操作窗口中显示当前所有已提出修改建议的物点的统计列表，如下图2-1所示：</h5>
        <div style="text-align: center">
            <img src="${pageContext.request.contextPath}/images/io_manager/5.png">
            <h6>图2-1 数据统计列表</h6>
        </div>
        <h5>（2）查看物点名字修改建议的详情</h5>
        <h5>在上图2-1所示的统计列表中，点击某一记录行中“查看详情”链接，将在系统管理的主操作窗口中显示相应物点名字（如第一图书馆）的修改建议详情，如下图2-2所示：</h5>
        <div style="text-align: center">
            <img src="${pageContext.request.contextPath}/images/io_manager/6.png">
            <h6>图2-2 第一图书馆的名字修改建议详情列表</h6>
        </div>

        <h5>2.2 导出数据</h5>
        <h5>在系统管理的功能目录栏中，点击“数据管理”<span class="glyphicon glyphicon-arrow-right"></span>“导出数据”功能链接，将在系统管理的主操作窗口中弹出保存文件的路径选择对话框，在该对话框中选择好保存路径后，单击“保存”按钮，即可将当前所有的修改建议以EXCEL表格的形式导出至所选择的路径下，如下图2-3所示：</h5>
        <div style="text-align: center">
            <img src="${pageContext.request.contextPath}/images/io_manager/7.png">
            <h6>图2-3 导出数据</h6>
        </div>



        <h3>3.用户管理</h3>
        <h5>3.1 修改密码</h5>
        <h5>在系统管理的功能目录栏中，点击“用户管理”<span class="glyphicon glyphicon-arrow-right"></span>“修改密码”功能链接，将在系统管理的主操作窗口中显示修改系统管理员密码的操作框，如下图3-1所示。在该操作框中按照提示输入2次新密码后，单击“提交”按钮，即可完成对系统管理员密码的修改操作。</h5>
        <div style="text-align: center">
            <img src="${pageContext.request.contextPath}/images/io_manager/8.png">
            <h6>图3-1 修改当前用户的密码</h6>
        </div>

        <h4>4.查看系统管理的操作说明</h4>
        <h5>在系统管理的功能目录栏中，点击“操作说明”<span class="glyphicon glyphicon-arrow-right"></span>“查看说明”功能链接，将在系统管理的主操作窗口中显示有关系统管理操作的详细说明，如下图4-1所示：</h5>
        <div style="text-align: center">
            <img src="${pageContext.request.contextPath}/images/io_manager/9.png">
            <h6>图4-1查看系统管理的操作说明</h6>
        </div>

        <h4>5.退出系统管理</h4>
        <h5>在系统管理操作界面的右上方，点击“退出”功能链接，即可退出系统管理的操作界面，如下图5-1所示：</h5>
        <div style="text-align: center">
            <img src="${pageContext.request.contextPath}/images/io_manager/10.png">
            <h6>图5-1退出系统管理</h6>
        </div>


    </div>
</div>


<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/bootstrap.min.js"></script>
</body>
</html>
