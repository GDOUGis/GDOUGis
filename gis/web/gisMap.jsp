<%@ page language="java" pageEncoding="UTF-8"%>
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

        function show_coords(event){
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
        });
    </script>

    <script type="text/javascript" src="/scripts/mapevent.js"></script>
    <script type="text/javascript" src="/scripts/mapmove.js"></script>
    <script type="text/javascript" src="/scripts/maprquest.js"></script>

</head>

<body bgcolor="#DFFFDF" link="#000000" vlink="#000000" alink="#000000">
<p id="cScale">
    <label for="amount">缩放：</label>
    <input type="text" id="amount" style="border:0; color:#f6931f; font-weight:bold;">
</p>
<!-- 垂直滑动条 -->
<div id="slider-vertical" style="height:200px;"></div>

<!-- 鹰眼. -->
<div>
    <!--div缩略图边框，img为缩略图,初始化为隐藏的-->
    <div id="mapboundframe">
        <img id="boundmap" GALLERYIMG="false" onclick="mapsmallpaner()">
    </div>
</div>

查询测试： 选择图层：<font color="red">*</font>

<!-- 存在问题，一开始在session没有mapj对象 -->
<select id="layerid" name="layername">
    <%
        MapJ mymap = (MapJ) session.getAttribute("mapj");
        Layer tlayer = null;
        if (mymap != null) {
            for (int i = 0; i < mymap.getLayers().size(); i++) {
                tlayer = mymap.getLayers().elementAt(i);
    %>
    <!-- 获取到图层的名字 -->
    <option value="<%=tlayer.getName()%>"><%=tlayer.getName()%></option>

    <%
            }
        }
    %>
</select>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
查询名称：<font color="red">*</font>
<Input id="selectid" name="selectname" type="text"/>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" id="queryid" value="查找" onClick="Find()"/>

<!--div为地图边框，img为地图-->
<div id="mapframe" style="  position: absolute;top: 60px;left: 0;" >
    <img height="200" id="imgmap" galleryimg="false" >
</div>



<!-- 不知道用来干什么==. -->
<IFRAME id="center" style="display: none"></IFRAME>
<IFRAME id="zoom" style="display: none"></IFRAME>

<!-- 点击选中的时候显示小红框 -->
<table id="seltable"
       style="position: absolute; border: 1px solid Red; width: 0px; height: 0px; display: block;">
    <tr>
        <td></td>
    </tr>
</table>
<img name="selimg"
     style="position: absolute; border: 1px solid Red; width: 1px; height: 1px; display: none;">

</body>
<script language="JavaScript" src="/scripts/init.js"></script>
<script language="JavaScript" src="/scripts/jquery.min.js"></script>
<script language="JavaScript" src="/scripts/jquery.mousewheel.min.js"></script>
<script language="JavaScript">

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

    function maplayer(){
        var layer;//打开图层控制页面
        resetimg();
        state="player";
        layer=window.open("layer.jsp");
        document.all.player.src="images/index-map-a_09.jpg";
    }
</script>
</html>