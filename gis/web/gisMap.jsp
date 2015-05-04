<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>地图显示</title>

    <link href="/css/gisMap.css" rel="stylesheet" type="text/css">

</head>

<body  link="#000000" vlink="#000000" alink="#000000" >

<div class="header_warp">
    <div class="header">
        <!--logo开始处-->
        <div class="logo">
            <div style="float: left">
                <a href="#">
                    <img src="images/logo.gif" height="60px">
                </a>
            </div>
            <div style="float: left;margin-top: 10px;margin-left: 10px;"><span style="font-size: 24px;font-family: '微软雅黑';">校园地图</span></div>
        </div>
        <!--logo结束处-->
    </div>
</div>


<!--div缩略图边框，img为缩略图,初始化为隐藏的-->
<div id="mapboundframe" style=" position: absolute;left: 10px;top: 70px;height: 182px;width: 242px;background-color: #fff;layer-background-color: #99FFFF;border: 1px #339933 solid;float:left;display: block;z-index: 9999;border-radius: 5px;">
    <img id="boundmap"  class="boundmap" GALLERYIMG="false" onclick="mapsmallpaner()"
            style=" position: absolute;left: 1px;top: 1px;height: 180px;width: 240px;visibility: visible;float:left;">
</div>


<!--div为地图边框，img为地图-->
<div id="mapframe"  style="position: absolute;top: 70px;left: 0;width: 960px;height: 600px; overflow: hidden;"  >
    <div id="imgdiv" onMouseOver="dragimages=imgdiv;drag=1;"style="position: absolute;top: 0px;left: 0;width: 960px;height: 600px;">
        <img height="200" id="imgmap" galleryimg="false"  onmousemove="mousemove()" onmousedown="mousedown()" onmouseup="mouseup()"  onload="updataBoundMap()" ondragstart="mouseStop()" onmouseover="this.style.cursor='pointer'">
    </div>
</div>

<div class="tool_warp" style="position: absolute;top: 70px;left: 980px;">
    <div class="tool_header">
        <h3>欢迎使用广东海洋大学数字地图</h3>
    </div>
    <div class="layer" style="font-size: 10px;">
        <label>请选择要显示的图层</label><br>
        <c:forEach items="${layerNames}" var="layerName">
            <input type="checkbox" name="layerName" value="${layerName}" checked="true" onchange="showLayer()">${layerName}<br>
        </c:forEach>
    </div>
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
                       name="oldzoom">
            </td>
            <td width="266">&nbsp;</td>
        </tr>
    </table>
</div>

<script type="text/javascript" src="/scripts/jquery.min.js"></script>
<script type="text/javascript" src="/scripts/jquery.mousewheel.min.js"></script>
<script type="text/javascript" src="/scripts/mapevent.js"></script>
<script type="text/javascript" src="/scripts/maprquest.js"></script>
<script language="JavaScript" src="/scripts/pan.js"></script>
<script type="text/javascript" src="/scripts/init.js"></script>

<script language="JavaScript">

    $("input,select").click(function(e){
        $(this).select();
    });

    //加载鹰眼地图
    $(document).ready(function(){
        mapbound();
    });

    //监听滚轮.
    $('#imgmap').mousewheel(function(event, delta) {
        //鼠标xy
        var x = event.clientX;
        var y = event.clientY;
        //图片所在div距离屏幕边界的xy
        var imgx = document.all.mapframe.style.left;
        var imgy = document.all.mapframe.style.top;
        //计算得出相对于图片的xy
        x = parseInt(x) - parseInt(imgx);
        y = parseInt(y) - parseInt(imgy);
        //alert(x+","+y);
        //向上滚,放大
        if(delta==1){
            map2bigger(x,y);
        }else{//向下滚，缩小
            map2smaller(x,y);
        }
    });

</script>
</body>


</html>