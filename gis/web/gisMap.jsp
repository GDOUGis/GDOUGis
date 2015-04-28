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
<style type="text/css">
</style>

<script type="text/javascript" src="/scripts/mapevent.js"></script>
<script type="text/javascript" src="/scripts/mapmove.js"></script>
<script type="text/javascript" src="/scripts/maprquest.js"></script>

</head>

<body bgcolor="#DFFFDF" link="#000000" vlink="#000000" alink="#000000">

		<!--div为地图边框，img为地图-->
		<div id="mapframe" style="position: absolute; left: 9px; top: 64px; height: 602px; width:852px; overflow: hidden; background-color: #99FFFF; layer-background-color: #99FFFF; border: 1px #339933 solid">
			<img height="600" id="imgmap" style="position: relative; left: 0px; top: 0px; height: 600px; width: 850px; cursor: default" galleryimg="false">
		</div>
		
		<!-- 不知道用来干什么==. -->
		<IFRAME id="center" style="display: none"></IFRAME>
		<IFRAME id="zoom" style="display: none"></IFRAME>

		<div id="layer1"
			style="position: absolute; left: 17px; top: 634px; width: 87px; height: 18px; z-index: 2; font-size: 12px; color: Red;">
			当前状态：		
		</div>
</body>
<script language="JavaScript" src="/scripts/init.js"></script>
</html>