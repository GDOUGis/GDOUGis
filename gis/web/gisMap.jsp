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

    <script type="text/javascript" src="/scripts/mapevent.js"></script>
    <script type="text/javascript" src="/scripts/mapmove.js"></script>
    <script type="text/javascript" src="/scripts/maprquest.js"></script>

</head>

<body bgcolor="#DFFFDF" link="#000000" vlink="#000000" alink="#000000">

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
<div id="mapframe" style="position: absolute; left: 9px; top: 64px; height: 602px; width:852px; overflow: hidden; background-color: #99FFFF; layer-background-color: #99FFFF; border: 1px #339933 solid">
    <img height="600" id="imgmap" style="position: relative; left: 0px; top: 0px; height: 600px; width: 850px; cursor: default" galleryimg="false">
</div>

<!--div缩略图边框，img为缩略图,初始化为隐藏的-->
<div id="mapboundframe"
     style="position: absolute; left: 881px; top: 283px; height: 182px; width: 242px; overflow: hidden; background-color: #99FFFF; layer-background-color: #99FFFF; border: 1px #339933 solid; display: none">
    <!-- <img id="boundmap" GALLERYIMG="false"
        style="position: relative; left: 0px; top: 0px; visibility: hidden; height: 180px; width: 240px;"
        onclick="mapsmallpaner()"> -->
    <img id="boundmap" GALLERYIMG="false"
         style="position: relative; left: 0px; top: 0px;  height: 180px; width: 240px;"
         onclick="mapsmallpaner()">
</div>

<!-- 不知道用来干什么==. -->
<IFRAME id="center" style="display: none"></IFRAME>
<IFRAME id="zoom" style="display: none"></IFRAME>

<!-- 点击选中的时候显示小红框 -->
<table id="seltable"
       style="position: absolute; border: 1px solid Red; width: 0px; height: 0px; display: none;">
    <tr>
        <td></td>
    </tr>
</table>
<img name="selimg"
     style="position: absolute; border: 1px solid Red; width: 1px; height: 1px; display: none;">

<div id="layer1"
     style="position: absolute; left: 17px; top: 634px; width: 87px; height: 18px; z-index: 2; font-size: 12px; color: Red;">
    当前状态：
</div>

<div id="center&zoom"
     style="position: absolute; left: 10px; top: 680px; width: 577px; height: 33px; z-index: 3">
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
<script language="JavaScript">
    function maplayer(){
        var layer;//打开图层控制页面
        resetimg();
        state="player";
        layer=window.open("layer.jsp");
        document.all.player.src="images/index-map-a_09.jpg";
    }
</script>
</html>