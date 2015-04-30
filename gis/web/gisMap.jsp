<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.mapinfo.mapj.*"%>
<%
    String flag = request.getParameter("flag");
    if (flag != null && flag.equals("true")) {
        MapJ mymap = (MapJ) session.getAttribute("mapj");
        //图层
        Layer tlayer = null;
        if (mymap != null) {
            for (int i = 0; i < mymap.getLayers().size(); i++) {
                tlayer = mymap.getLayers().elementAt(i);
                String param1 = "isview" + i;
                String param2 = "isselect" + i;
                String param3 = "islabel" + i;
                String isview = request.getParameter(param1);
                String isselect = request.getParameter(param2);
                String islabel = request.getParameter(param3);
                if (isview != null && isview.equals("true"))
                    tlayer.setVisible(true);
                else
                    tlayer.setVisible(false);
                if (isselect != null && isselect.equals("true"))
                    tlayer.setSelectable(true);
                else
                    tlayer.setSelectable(false);
                if (islabel != null && islabel.equals("true"))
                    tlayer.setAutoLabel(true);
                else
                    tlayer.setAutoLabel(false);
            }
        }
    }
%>
<html>
<head>
    <title>地图显示</title>

    <link href="/css/gisMap.css" rel="stylesheet" type="text/css">


    <link rel="stylesheet" href="//apps.bdimg.com/libs/jqueryui/1.10.4/css/jquery-ui.min.css">
    <script src="//apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="//apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="jqueryui/style.css">
    <script>
/*        $(function(){
            $("body").mousemove(function(){
                console.log([document.documentElement.clientWidth+"---"+window.screen.width])
                //alert(document.documentElement.clientHeight);
            })
        })*/

       /* function show_coords(event){
            var x = event.clientX;
            var y = event.clientY;
        }

        $(function() {
            $( "#slider-vertical" ).slider({
                orientation: "vertical",
                range: "min",
                min: 0,
                max: 100,
                value: 50,
                slide: function( event, ui ) {
                    $( "#amount" ).val( ui.value );
                }
            });
            $( "#amount" ).val( $( "#slider-vertical" ).slider( "value" ) );
        });*/
    </script>

    <script type="text/javascript" src="/scripts/mapevent.js"></script>
    <script type="text/javascript" src="/scripts/mapmove.js"></script>
    <script type="text/javascript" src="/scripts/maprquest.js"></script>

</head>

<body  link="#000000" vlink="#000000" alink="#000000">


<div id="header" style="position: absolute;left: 0px;top: 0px ">
    选择图层：
    <select id="layerid" name="layername">
        <c:forEach items="${layerNames}" var="name">
            <option value="${name}">${name}</option>
        </c:forEach>
    </select>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    查询名称：
    <input id="selectid" name="selectname" type="text"/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" id="queryid" value="查找" onClick="Find()"/>
</div>

<div>
    <!--div缩略图边框，img为缩略图,初始化为隐藏的-->
    <div id="mapboundframe" style=" position: absolute;left: 10px;top: 60px;height: 182px;width: 242px;background-color: #18FF6F;layer-background-color: #99FFFF;border: 1px #339933 solid;float:left;display: block;z-index: 9999;border-radius: 5px;">
        <img id="boundmap" GALLERYIMG="false" onclick="mapsmallpaner()"
                style=" position: absolute;left: 1px;top: 1px;height: 180px;width: 240px;visibility: visible;float:left;">
    </div>
</div>



<!--div为地图边框，img为地图-->
<div id="mapframe" style="position: absolute;top: 60px;left: 0;" >
    <img height="200" id="imgmap" galleryimg="false" >
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

</body>
<script language="JavaScript" src="/scripts/init.js"></script>
<script language="JavaScript" src="/scripts/jquery.min.js"></script>
<script language="JavaScript" src="/scripts/jquery.mousewheel.min.js"></script>
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

   /* function maplayer(){
        var layer;//打开图层控制页面
        resetimg();
        state="player";
        layer=window.open("layer.jsp");
        document.all.player.src="images/index-map-a_09.jpg";
    }*/

</script>
</html>