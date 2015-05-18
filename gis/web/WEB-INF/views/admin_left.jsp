<%--
  Created by IntelliJ IDEA.
  User: Seven
  Date: 2015/5/11
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="utf-8">
    <title>校园地图后台管理</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $(".div2").click(function(){
                $(this).next("div").slideToggle("slow")
                        .siblings(".div3:visible").slideUp("slow");
            });
        });
    </script>
    <style>
        body{ margin:0;font-family:微软雅黑;}
        .left{ width:200px; height:100%; border-right:1px solid #CCCCCC ;#FFFFFF; color:#000000; font-size:14px; text-align:center;}
        .div1{text-align:center; width:200px; padding-top:10px;}
        .div2{height:40px; line-height:40px;cursor: pointer; font-size:13px; position:relative;border-bottom:#ccc 1px dotted;}
        .jbsz {position: absolute; height: 20px; width: 20px; left: 40px; top: 10px; background:url(${pageContext.request.contextPath}/images/1.png);}
        .xwzx {position: absolute; height: 20px; width: 20px; left: 40px; top: 10px; background:url(${pageContext.request.contextPath}/images/2.png);}
        .zxcp {position: absolute; height: 20px; width: 20px; left: 40px; top: 10px; background:url(${pageContext.request.contextPath}/images/4.png);}
        .lmtj {position: absolute; height: 20px; width: 20px; left: 40px; top: 10px; background:url(${pageContext.request.contextPath}/images/8.png);}
        .div3{display: none;cursor:pointer; font-size:13px;}
        .div3 ul{margin:0;padding:0;}
        .div3 li{ height:30px; line-height:30px; list-style:none; border-bottom:#ccc 1px dotted; text-align:center;}
    </style>
</head>
<body>
<div class="left">
    <div class="div1">
        <div class="left_top"><img src="${pageContext.request.contextPath}/images/bbb_01.jpg"><img src="${pageContext.request.contextPath}/images/bbb_02.jpg" id="2"><img src="${pageContext.request.contextPath}/images/bbb_03.jpg"><img src="${pageContext.request.contextPath}/images/bbb_04.jpg"> </div>
        <div class="div2"><div class="lmtj"> </div>系统统计</div>
        <div class="div3">
            <ul>
                <li><a href="${pageContext.request.contextPath}/servlet/ModifyServlet?method=loadFPModifyPD" TARGET="main">数据统计</a></li>
            </ul>
        </div>

        <div class="div2"><div class="xwzx"> </div>用户管理</div>
        <div class="div3">
            <ul>
               <c:if test="${user.is_Su==1}">
                   <li><a href="${pageContext.request.contextPath}/servlet/ForwardServlet?resource=addUser" target="main">添加用户</a></li>
                   <li><a href="${pageContext.request.contextPath }/servlet/UserServlet?method=list" target="main">所有用户</a></li>
               </c:if>
                <li><a href="${pageContext.request.contextPath }/servlet/ForwardServlet?resource=updatePwdUI" target="main">修改密码</a></li>
            </ul>
        </div>
    </div>
</div>

</body>
</html>
