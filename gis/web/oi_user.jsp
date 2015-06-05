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

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/gdou.ico">
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
    <div class="header_warp">
        <div class="header">
            <!--logo开始处-->
            <div class="logo">
                <div style="float: left">
                    <a href="#">
                        <img src="${pageContext.request.contextPath}/images/header.png" height="60px">
                    </a>
                </div>
            </div>
            <!--logo结束处-->
        </div>
    </div>
    <%--header结束--%>

    <div class="container">
        <h2 class="col-md-offset-5">操作说明</h2>
        <div class="col-md-offset-2 col-md-10">
            <h3>1.放大地图</h3>
            <h4>系统提供了3种放大地图的操作方法（最大放大倍数为5倍），具体为：</h4>

            <h5>（1）在地图操作窗口中，用鼠标左键单击放大按钮“<span class="glyphicon glyphicon-plus"></span>”，如下图1所示： </h5>
            <div style="text-align: center">
                <img src="${pageContext.request.contextPath}/images/io_user/io_1.png">
                <h6>图1 放大地图操作方法1</h6>
            </div>

            <h5>（2）在地图操作窗口中，用鼠标左键按住滑动控件的滑动块“<span class="glyphicon glyphicon-stop"></span>”，并沿着滑动条向上拖动滑动块，如下图2所示：</h5>
            <div style="text-align: center">
                <img src="${pageContext.request.contextPath}/images/io_user/io_2.png">
                <h6>图2 放大地图操作方法2</h6>
            </div>

            <h5>（3）在地图操作窗口中，用鼠标左键单击滑动控件中位于滑动块上方的滑动条区域，如下图3所示：</h5>
            <div style="text-align: center">
                <img src="${pageContext.request.contextPath}/images/io_user/io_3.png">
                <h6>图3 放大地图操作方法3</h6>
            </div>

            <h3>2. 缩小地图</h3>
            <h4>系统提供了3种缩小地图的操作方法（最小缩小倍数为1倍），具体为：</h4>
            <h5>（1）在地图操作窗口中，用鼠标左键单击缩小按钮“<span class="glyphicon glyphicon-minus"></span>”，如下图4所示：</h5>
            <div style="text-align: center">
                <img src="${pageContext.request.contextPath}/images/io_user/io_4.png">
                <h6>图4 缩小地图操作方法1</h6>
            </div>

            <h5>（2）在地图操作窗口中，用鼠标左键按住滑动控件的滑动块“<span class="glyphicon glyphicon-stop"></span>”，并沿着滑动条向下拖动滑动块。如下图5所示：</h5>
            <div style="text-align: center">
                <img src="${pageContext.request.contextPath}/images/io_user/io_5.png">
                <h6>图5 缩小地图操作方法2</h6>
            </div>

            <h5>（3）在地图操作窗口中，用鼠标左键单击滑动控件中位于滑动块下方的滑动条区域。如下图6所示：</h5>
            <div style="text-align: center">
                <img src="${pageContext.request.contextPath}/images/io_user/io_6.png">
                <h6>图6 缩小地图操作方法3</h6>
            </div>

            <h3>3. 漫游地图（拖动地图）</h3>
            <h4>在地图操作窗口中，用鼠标左键单击地图，并按住鼠标左键不放，同时沿着某一方向拖动鼠标，即可将逆方向上的未可见地图区域显示在地图操作窗口中。如下图7、图8所示：</h4>
            <div style="text-align: center">
                <img src="${pageContext.request.contextPath}/images/io_user/io_7.png">
                <h6>图7 地图拖动之前</h6>
                <img src="${pageContext.request.contextPath}/images/io_user/io_8.png">
                <h6>图8 地图拖动之后</h6>
            </div>

            <h3>4. 重置地图</h3>
            <h4>在地图操作窗口中，用鼠标左键单击重置按钮“”，即可重置地图（即重新初始化地图）。如下图9、图10所示：</h4>
            <div style="text-align: center">
                <img src="${pageContext.request.contextPath}/images/io_user/io_9.png">
                <h6>图9 地图重置之前</h6>
                <img src="${pageContext.request.contextPath}/images/io_user/io_10.png" width="400px">
                <h6>图10 地图重置之后</h6>
            </div>

            <h3>5. 选择目标</h3>
            <h4>在地图操作窗口中，用鼠标左键单击所要选择的目标，选中后该目标将高亮显示。如下图11、图12所示：</h4>
            <div style="text-align: center">
                <img src="${pageContext.request.contextPath}/images/io_user/io_11.png">
                <h6>图11 目标选择之前</h6>
                <img src="${pageContext.request.contextPath}/images/io_user/io_12.png">
                <h6>图12 目标选择之后</h6>
            </div>

            <h3>6. 目标名字修改建议</h3>
            <h4>在地图操作窗口中，当选中了某一目标后（即该目标处于高亮显示的状态），用鼠标左键单击该目标，即可弹出描述该目标的对话框，在该对话框中可填写修改目标名字的建议（相关信息填写完毕后，单击该对话框中的“提交”按钮才能将建议提交）。如下图13所示：</h4>
            <div style="text-align: center">
                <img src="${pageContext.request.contextPath}/images/io_user/io_13.png">
                <h6>图13目标名字修改建议对话框</h6>
            </div>

            <h3>7. 查找目标</h3>
            <h4>在地图操作窗口右侧的查找输入框中输入所要查找的目标的名字（即查找条件），并单击“查找”按钮，即可实现对相应目标的查找操作；所有符合查找条件的目标，其名字以列表的形式显示在查找输入框下方；单击该结果列表中的某一项名字，相应目标将高亮、居中显示在地图操作窗口中。如下图14所示：</h4>
            <div style="text-align: center">
                <img src="${pageContext.request.contextPath}/images/io_user/io_14.png">
                <h6>图14 查找目标</h6>
            </div>

        </div>
    </div>

    <div id="footer" style="padding-top: 50px;margin: auto;text-align: center">
        Copyright &copy; 2015.广东海洋大学 All rights reserved<br>
    </div>


<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/bootstrap.min.js"></script>
</body>
</html>
