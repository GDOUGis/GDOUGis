<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>地图显示</title>



    <link rel="stylesheet" href="//apps.bdimg.com/libs/jqueryui/1.10.4/css/jquery-ui.min.css">
    <!-- bootstrap -->
    <link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/css/gisMap.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/bootstrap-slider.css" rel="stylesheet" type="text/css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body  link="#000000" vlink="#000000" alink="#000000" >
<div class="body-r"></div>
<!-- 模态框 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     style="z-index:10000;" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" >现用名：<span id="cTitle"></span></h4>
                <h4 class="modal-title" >拟改名：<span id="cPrepareName"></span></h4>
                <h4 class="modal-title">拟改说明：
                    <textarea readonly="readonly" rows="3" id="cPrepareDesc" class="form-control" placeholder="拟用说明"></textarea>
                </h4>
            </div>
            <div class="modal-body" id="cContent">
                <form class="form-inline"  method="post">
                    <input type="hidden" name="id" id="fpId">
                    <label class="control-label">修改名：</label>
                    <div style="margin-left: 10px;" class="form-group">
                        <input id="cAlias" type="text" name="alias" class="form-control" placeholder="心中的名字">
                    </div>
                    <label class="control-label">说 &nbsp; &nbsp;明：</label>
                    <div style="margin-left: 8px;" class="form-group">
                        <textarea cols="20" rows="3" id="modifyDesc" type="text"
                                  name="modifyDesc" class="form-control" placeholder="修改说明"></textarea>
                    </div>
                    <label class="control-label">修改人：</label>
                    <div style="margin-left: 10px;" class="form-group">
                        <input id="modifyPeople" type="text" class="form-control" placeholder="请输入您的姓名">
                    </div>
                    <label class="control-label">身 &nbsp; &nbsp;份：</label> &nbsp; &nbsp;
                    <input type="radio" id="identification0" name="identification" /> 教师 &nbsp; &nbsp;
                    <input type="radio" id="identification1" checked="checked" name="identification" />学生<br>
                    <label class="control-label">学 &nbsp; &nbsp;院：</label>
                    <div style="margin-left: 10px;" class="form-group">
                        <input id="modifyCollege" type="text" class="form-control" placeholder="请输入您所在的学院">
                    </div>
                    <label class="control-label">手 &nbsp; &nbsp;机：</label>
                    <div style="margin-left: 10px;" class="form-group">
                        <input id="modifyPhone" type="text" class="form-control" placeholder="请输入您的手机号码">
                    </div>
                    <input id="cSubmit" class="btn btn-primary" type="button" value="提交">
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

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


<%--<!--div缩略图边框，img为缩略图,初始化为隐藏的-->
<div id="mapboundframe" style=" position: absolute;left: 10px;top: 70px;height: 182px;width: 242px;background-color: #fff;layer-background-color: #99FFFF;border: 1px #339933 solid;float:left;display: block;z-index: 9999;border-radius: 5px;">
    <img id="boundmap"  class="boundmap" GALLERYIMG="false" onclick="mapsmallpaner()"
            style=" position: absolute;left: 1px;top: 1px;height: 180px;width: 240px;visibility: visible;float:left;">
</div>--%>
<!--div缩略图边框，img为缩略图,初始化为隐藏的-->
<div id="mapboundframe" style="position: absolute;top: 70px;left: 980px;height: 183px;width: 243px; display: block;border: 1px solid #000000;">
    <img id="boundmap"  class="boundmap" GALLERYIMG="false" onclick="mapsmallpaner()"
         style=" height: 180px;width: 240px;visibility: visible;float:left;">
</div>

<!--div为地图边框，img为地图-->
<div id="mapframe"  style="z-index: 0;position: absolute;top: 70px;left: 0;width: 960px;height: 600px; overflow: hidden;"  >
    <div id="imgdiv" class="imgdiv">
        <img height="200" id="imgmap" galleryimg="false" onload="updataBoundMap()" ondragstart="mouseStop()" onmouseover="this.style.cursor='pointer'">
    </div>
</div>

<%--滚动条--%>
<div id="scroll" style="z-index: 999;top:0;">
    <div style="position: absolute;left: 72px;top: 100px;"><a href="#" title="重置" onclick="mapreset()"><span class="glyphicon glyphicon-refresh"></span></a></div>
    <div style="position: absolute;left: 72px;top: 130px;"><a href="#" title="放大" onclick="bigger();"><span class="glyphicon glyphicon-plus"></span></a></div>
    <div style="position: absolute;left: 72px;top: 410px;"><a href="#" title="缩小" onclick="smaller();"><span class="glyphicon glyphicon-minus"></span></a></div>
    <div class="well" id="well" style="position: absolute;left: 50px;top: 150px;">
        <input id="ex4" data-slider-id='ex4Slider'  type="text" data-slider-handle="square" data-slider-min="1" data-slider-max="5" data-slider-step="1" data-slider-value="1" data-slider-orientation="vertical"/>
    </div>
</div>

<div class="tool_warp" style="position: absolute;top: 270px;left: 980px;">
    <%--<div class="tool_header">
        <h3>欢迎使用广东海洋大学数字地图</h3>
    </div>
    <div class="layer" style="font-size: 10px;">
        <label>请选择要显示的图层</label><br>
        <c:forEach items="${layerNames}" var="layerName">
            <input type="checkbox" name="layerName" value="${layerName}" checked="true" onchange="showLayer()">${layerName}<br>
        </c:forEach>
    </div>--%>
    <hr>
    <div class="search">
        <input type="text" id="searchText" placeholder="请输入您要搜索的建筑物名称"> <button id="searchBtn" onclick="searchF()">查询</button>
        <div class="result" style="height: 300px;overflow-y: auto">

        </div>
    </div>
</div>

<!-- 保存中心点和范围-->
<IFRAME id="center" style="display: none"></IFRAME>
<IFRAME id="zoom" style="display: none"></IFRAME>

<div id="center&zoom"
     style="position: absolute; left: 10px; top: 680px; width: 577px; height: 33px; z-index: 3;display: none;">
    <!--显示中心点和zoom值-->
    <table width="526" border="0">
        <tr>
            <td width="250">
                <font size="2">中心点(米)Ｘ:</font>
                <input type="Text"
                       style="border: none; background: #DFFFDF; text-align: left;"
                       name="oldx">
            </td>
            <td width="266">
                <font size="2">Ｙ:</font>
                <input type="Text"
                       style="border: none; background: #DFFFDF; text-align: left;"
                       name="oldy">
            </td>
        </tr>
        <tr>
            <td width="250">
                <font size="2">&nbsp;&nbsp;&nbsp;&nbsp;视野(米):</font>
                <input type="Text"
                       style="border: none; background: #DFFFDF; text-align: left;"
                       name="oldzoom" value="oldzoom">
            </td>
            <td width="266">&nbsp;</td>
        </tr>
    </table>
</div>

<div id="footer">
    Copyright &copy; 2015.CPP Studio All rights reserved.
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.min.js"></script>
<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.mousewheel.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mapevent.js"></script>
<script language="JavaScript" src="${pageContext.request.contextPath}/scripts/pan.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/init.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/maprquest.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/bootstrap-slider.js"></script>

<script language="JavaScript">


    $("input,select").click(function(e){
        $(this).select();
    });

    //加载鹰眼地图
    $(document).ready(function(){

        $("#cSubmit").click(function(event) {
            // 获取参数
            var currentName = $("#cTitle").text();
            var id = $("#fpId").val();      // id
            var name = $("#cAlias").val();  // 修改名
            var modifyDesc = $("#modifyDesc").val();   // 修改说明
            var modifyPeople = $("#modifyPeople").val();  // 修改人名字
            var identification0 = $("#identification0").attr("checked");    // 教师
            var identification1 = $("#identification1").attr("checked");    // 学生
            var identification;
            if("checked" == identification0) {
                identification = 0;
            } else {
                identification = 1;
            }
            var modifyCollege = $("#modifyCollege").val();        // 所在学院
            var modifyPhone = $("#modifyPhone").val();            // 修改人手机
            var url = "servlet/MapServlet?rqutype=addModifyName"
            var params = {
                fpId:id,
                currentName:currentName,
                modifyName:name,
                modifyDesc:modifyDesc,
                modifyPeople:modifyPeople,
                modifyCollege:modifyCollege,
                modifyPhone:modifyPhone,
                identification:identification
            };
            console.log(params);
            console.log("Before addModifyName的getJSON方法");
            console.log($(".form-inline").serialize());
//            $.ajax(url,{
//                type:"POST",
//                dataType:"html",
//                data:params
//            })
//                    .success(function(response){
//                        alert("run it!!")
//                    })
//                    .error(function(){
//                        alert("error");
//                    });
//            $.ajax({
//                type:"POST",
//                url:url,
//                data:params,
//                success: function (result) {
//                    alert("run it!!")
//                }
//            })
            $.getJSON(url, params, function(data) {
                 alert("进入addModifyName的getJSON方法")
                 if(data == "1") {
                     $("#cAlias").val("");
                     $("#modifyDesc").val("");
                     $("#modifyPeople").val("");
                     $("#modifyCollege").val("");
                     $("#modifyPhone").val("");
                     alert("提交成功！")
                 } else {
                     alert("服务器正忙..")
                 }
                 alert("退出addModifyName的getJSON方法")
                 });
            alert("After addModifyName的getJSON方法")
            return false;
        });

        //mapbound();



        //显示滚动条.
        myslider = $("#ex4").slider({
            reversed : true
        });
        slideVal = 1;
        $("#ex4").on("slideStart", function(slideEvt) {
            slideVal = slideEvt.value;
            mysliderState = null;//去除放大缩小的状态;
        });
        $("#ex4").on("slideStop", function(slideEvt) {

            var val = slideEvt.value;
            //原来图片的中心点
            var x =parseInt(document.all.imgmap.width)/2;
            var y =parseInt(document.all.imgmap.height)/2;
            var newzoom = 1;
            if(slideVal - val >0){//缩小
                for(var i=0;i<slideVal-val;i++){
                    newzoom = newzoom * 2;
                }
                //console.log([slideEvt.value ,newzoom,x,y]);
                var url = mapserviceurl+"?rqutype=chgmapview"+"&centerx="+x+"&centery="+y+"&newzoom="+newzoom+"&random="+Math.random();
                $("#imgmap").attr("src",url);
            }
            if(slideVal - val < 0){//放大
                for(var i=0;i<val-slideVal;i++){
                    newzoom = newzoom * 0.5;
                }
                //console.log([slideEvt.value ,newzoom,x,y]);
                var url = mapserviceurl+"?rqutype=chgmapview"+"&centerx="+x+"&centery="+y+"&newzoom="+newzoom+"&random="+Math.random();
                $("#imgmap").attr("src",url);
            }
            slideVal = val;
        });

        //定时器
        myTimeOut = null;
        //监听滚轮.
        $('#imgmap').mousewheel(function(event, delta) {
            var evn = event || window.event;
            //鼠标xy
            var x = evn.clientX;
            var y = evn.clientY;
            //图片所在div距离屏幕边界的xy
            var imgx = document.all.mapframe.style.left;
            var imgy = document.all.mapframe.style.top;
            //计算得出相对于图片的xy
            x = parseInt(x) - parseInt(imgx);
            y = parseInt(y) - parseInt(imgy);
            //alert(x+","+y);
            //再次触发就清理
            if(myTimeOut != null){
                //再次触发清理定时器
                window.clearTimeout(myTimeOut);
                mysliderState = null;
            }
            //向上滚,放大
            if(delta==1){
                //$("#ex4").attr("data-slider-value",$("#ex4").attr("data-slider-value")+1);
                var oldVal = myslider.slider("getValue");
                if(oldVal<5){
                    myTimeOut=window.setTimeout(function(){myfunction()},500);
                    mysliderState = 'big';
                    //map2bigger(x,y);
                    function myfunction(){
                        map2bigger(x,y);
                    }
                }
            }else{//向下滚，缩小
                var oldVal = myslider.slider("getValue");
                if(oldVal>1) {
                    myTimeOut=window.setTimeout(function(){myfunction2()},500);
                    mysliderState = "small";
                    function myfunction2(){
                        map2smaller(x, y);
                    }
                }

            }
        });
    });

</script>
</body>


</html>