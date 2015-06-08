
<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>广东海洋大学校园地图</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/gdou.ico">

    <link rel="stylesheet" href="//apps.bdimg.com/libs/jqueryui/1.10.4/css/jquery-ui.min.css">
    <!-- bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" charset="utf-8">

    <link href="${pageContext.request.contextPath}/css/gisMap.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/bootstrap-slider.css" rel="stylesheet" type="text/css">

    <script src="${pageContext.request.contextPath}/scripts/es5-shim.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script>alert("您的浏览器版本过低，请升级之后访问！");</script>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE9.js"></script>
    <![endif]-->

    <style>
        body, div, dl, dt, dd, ul, ol, li, h1, h2, h3, h4, h5, h6, pre, code, form, fieldset, legend, input, textarea, p, blockquote, th, td {
            margin:0;
            padding:0;
        }
        /*清除边线*/
        fieldset, img {
            border:0;
        }
        li {
            list-style:none;
        }
        h1, h2, h3, h4, h5, h6 {
            font-size:100%;
            font-weight:normal;
        }
        *{margin:0;padding:0;}
    </style>

</head>

<body  link="#000000" vlink="#000000" alink="#000000" >
<%
    if(request.getSession().getAttribute("mapj")==null){
        response.sendRedirect(request.getContextPath()+"/servlet/InitServlet");
    }
%>
<div class="body-r"></div>
<!-- 模态框 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     style="z-index:10000;font-size: 13px;" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header" id="cheader" style="margin-left: -3px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <label class="modal-title" >现用名：<span id="cTitle"></span></label><br>
                <label class="modal-title">备&nbsp;&nbsp;&nbsp;注：</label>
                <span  id="cdescription" placeholder="备注"></span>
            </div>
            <div class="modal-body" id="cContent">
                <div style="height: 10px;border-bottom: inherit"></div>
                <label class="modal-title" style="margin-top: 5px;">拟改名：<span id="cPrepareName"></span></label><br>
                <form class="form-inline" style="margin-top: 5px;">
                    <label class="modal-title">说&nbsp;&nbsp;&nbsp;明： </label>
                    <div style="margin-left: 5px;" class="form-group">
                        <textarea style="font-size: 13px;" readonly="readonly" rows="3" type="text" id="cPrepareDesc" class="form-control" placeholder="拟改说明"></textarea>
                    </div>
                </form>
                <hr>
                <form class="form-inline"  method="post">
                    <input type="hidden" name="id" id="fpId">
                    <label class="control-label">修改名：</label>
                    <div style="margin-left: 10px;" class="form-group">
                        <input id="cAlias" type="text" name="alias" class="form-control" placeholder="请输入修改后的名字">
                    </div>
                    <label class="control-label">说 &nbsp; &nbsp;明：</label>
                    <div style="margin-left: 8px;" class="form-group">
                        <textarea style="font-size: 13px;"  cols="20" rows="3" id="modifyDesc" type="text"
                                  name="modifyDesc" class="form-control" placeholder="修改说明"></textarea>
                    </div>
                    <label class="control-label">修改人：</label>
                    <div style="margin-left: 10px;" class="form-group">
                        <input id="modifyPeople" type="text" class="form-control" placeholder="请输入您的姓名">
                    </div>
                    <label class="control-label">身 &nbsp; &nbsp;份：</label>
                    <select name="identification" id="identification" class="form-control" style="margin-left: 8px;font-size: 13px;">
                        <option>校内教职工</option>
                        <option>校内在读学生</option>
                        <option>其它</option>
                    </select>
                    <label class="control-label">单 &nbsp; &nbsp;位：</label>
                    <div style="margin-left: 8px;" class="form-group">
                        <select id="modifyCollege" class="form-control" style="font-size: 13px;">
                            <option>水产学院</option>
                            <option>食品科技学院</option>
                            <option>海洋与气象学院</option>
                            <option>农学院</option>
                            <option>工程学院</option>
                            <option>信息学院</option>
                            <option>经济管理学院</option>
                            <option>航海学院</option>
                            <option>理学院</option>
                            <option>外国语学院</option>
                            <option>文学院</option>
                            <option>法学院</option>
                            <option>思政理论课教学部</option>
                            <option>政治与行政学院</option>
                            <option>中歌艺术学院</option>
                            <option>体育与休闲学院</option>
                            <option>职业技术学院</option>
                            <option>继续教育学院</option>
                            <option>寸金学院</option>

                            <option>党委办公室</option>
                            <option>纪委办公室</option>
                            <option>党委组织部</option>
                            <option>党委宣传部</option>
                            <option>学生工作部</option>
                            <option>保卫处</option>
                            <option>离退休人员工作处</option>
                            <option>校工会</option>
                            <option>校团委</option>

                            <option>校长办公室</option>
                            <option>人事处</option>
                            <option>教务处</option>
                            <option>科技处</option>
                            <option>研究生处</option>
                            <option>规划与法规处</option>
                            <option>对外联络处</option>
                            <option>财务处</option>
                            <option>审计处</option>
                            <option>资产与实验室管理处</option>
                            <option>招标与采购中心</option>
                            <option>基建处</option>
                            <option>后勤管理处</option>
                            <option>招生与就业指导中心</option>

                            <option>广东省水产经济动物病原生物学及流行病学重点实验室</option>
                            <option>广东南美白对虾遗传育种中心</option>
                            <option>南方对虾质量安全控制实验室</option>
                            <option>水产经济动物病害控制重点实验室</option>
                            <option>南海水产经济动物增养殖重点实验室</option>
                            <option>海产经济无脊椎动物健康养殖工程技术研究中心</option>
                            <option>水产品深加工重点实验室</option>
                            <option>陆架及深远海气候、资源与环境重点实验室</option>
                            <option>海洋经济与管理研究中心</option>
                            <option>广东海洋大学廉政研究中心</option>
                            <option>广东省雷州文化研究基地</option>
                            <option>广东省海洋开发研究中心</option>
                            <option>湛江海洋高新科技园</option>
                            <option>珍珠研究所</option>

                            <option>图书馆</option>
                            <option>实验教学部</option>
                            <option>教育信息中心</option>
                            <option>教师教学发展中心</option>
                            <option>分析测试中心</option>
                            <option>东海岛生物研究基地 </option>
                            <option>水生生物博物馆 </option>
                            <option>学报编辑部 </option>
                            <option>校医院 </option>

                            <option>综合服务中心</option>
                            <option>学生公寓服务中心</option>
                            <option>饮食服务中心</option>
                            <option>海滨校区后勤服务中心</option>

                            <option>资产经营有限公司</option>
                            <option>其它</option>
                        </select>
                    </div>
                    <label class="control-label">手 &nbsp; &nbsp;机：</label>
                    <div style="margin-left: 8px;" class="form-group">
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
            <div style="float: right;margin-top: 15px;font-family: 微软雅黑;">
                <a href="${pageContext.request.contextPath}/admin.jsp" target="_blank">系统管理</a>&nbsp;&nbsp;&nbsp;
                <a href="${pageContext.request.contextPath}/oi_user.jsp" target="_blank">操作说明</a>
        </div>
        </div>
        <!--logo结束处-->
    </div>
</div>

<!--div缩略图边框，img为缩略图,初始化为隐藏的-->
<div id="mapboundframe" style="position: absolute;top: 70px;left: 980px;height: 183px;width: 243px; display: block;border: 1px solid #000000;">
    <img id="boundmap"  class="boundmap" GALLERYIMG="false" onclick="mapsmallpaner()"
         style=" height: 180px;width: 240px;visibility: visible;float:left;">
</div>




<!--div为地图边框，img为地图-->
<div id="mapframe"  style="z-index: 0;position: absolute;top: 70px;left: 0;width: 960px;height: 600px; overflow: hidden;">
    <div id="imgdiv" class="imgdiv">
        <img height="200" id="imgmap" galleryimg="false" onload="updataBoundMap()" ondragstart="mouseStop()" onmouseover="this.style.cursor='pointer'">
    </div>
</div>

<%--滚动条--%>
<div id="scroll" style="z-index: 999;top:0;">
    <div style="position: absolute;left: 72px;top: 100px;"><a href="#" title="重置" onclick="mapreset()"><span class="glyphicon glyphicon-refresh"  style="width: 30px;height: 30px;"></span></a></div>
    <div style="position: absolute;left: 72px;top: 130px;"><a href="#" title="放大" onclick="bigger();"><span class="glyphicon glyphicon-plus"  style="width: 30px;height: 30px;"></span></a></div>
    <div style="position: absolute;left: 72px;top: 410px;"><a href="#" title="缩小" onclick="smaller();"><span class="glyphicon glyphicon-minus"  style="width: 30px;height: 30px;"></span></a></div>
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
        <input type="text" id="searchText" placeholder="请输入您要查找的目标名称"> <button id="searchBtn" onclick="searchF()">查找</button>
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

<div id="footer" style="text-align: center">
    Copyright &copy; 2015.广东海洋大学 All rights reserved<br>
    （推荐使用分辨率1366*768以上的显示器）.
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.mousewheel.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/bootstrap-slider.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/init.js"></script>
<script language="JavaScript" src="${pageContext.request.contextPath}/scripts/pan.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/maprquest.js"></script>

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

            var identification = $("#identification").val();
            var modifyCollege = $("#modifyCollege").val();        // 所在学院
            var modifyPhone = $("#modifyPhone").val();            // 修改人手机

            //主要字段校验
            if(name==""||name==null){
                alert("修改名不能为空！");
                return false;
            }else if(modifyPeople == ""||name==null){
                alert("修改人名字不能为空");
                return false;
            }

            var url = "servlet/MapServlet?rqutype=addModifyName"
            var params = {
                fpId:id,
                currentName:encodeURI(currentName),
                modifyName:encodeURI(name),
                modifyDesc:encodeURI(modifyDesc),
                modifyPeople:encodeURI(modifyPeople),
                modifyCollege:encodeURI(modifyCollege),
                modifyPhone:encodeURI(modifyPhone),
                identification:encodeURI(identification)
            };

            $.getJSON(url, params, function(data) {
                 //alert("进入addModifyName的getJSON方法")
                 if(data == "1") {
                     $("#cAlias").val("");
                     $("#modifyDesc").val("");
                     $("#modifyPeople").val("");
                     $("#modifyCollege").val("");
                     $("#modifyPhone").val("");
                     $('#myModal').modal('hide');//关闭模态框.
                     alert("提交成功！");
                 } else {
                     alert("服务器正忙..")
                 }
                 });
            return false;
        });

        //mapbound();



        //显示滚动条.
        myslider = $("#ex4").slider({
            reversed : true
        });
        slideVal = myslider.val();
        slideValMov = null;
        slideValMovVal = null;
        $("#ex4").on("change",function(data){
            slideVal = data.value.oldValue;
            $(".slider-track-high").attr('style','background:#82BE97');
        });
        $("#ex4").on("slideStart", function(slideEvt) {
            slideValMovVal = slideEvt.value;
            mysliderState = null;//去除放大缩小的状态;
        });
        $("#ex4").on("slideStop", function(slideEvt) {
            //alert("暂停");

           if(slideValMovVal!=slideEvt.value){
                slideVal = slideValMovVal;
            }
            var val = slideEvt.value;
            //原来图片的中心点
            var x =parseInt(document.all.imgmap.width)/2;
            var y =parseInt(document.all.imgmap.height)/2;
            var newzoom = 1;
            if(slideVal - val >0){//缩小
                for(var i=0;i<slideVal-val;i++){
                    newzoom = newzoom * 2;
                }
                var url = mapserviceurl+"?rqutype=chgmapview"+"&centerx="+x+"&centery="+y+"&newzoom="+newzoom+"&random="+Math.random();
                $("#imgmap").attr("src",url);
            }
            if(slideVal - val < 0){//放大
                for(var i=0;i<val-slideVal;i++){
                    newzoom = newzoom * 0.5;
                }
                var url = mapserviceurl+"?rqutype=chgmapview"+"&centerx="+x+"&centery="+y+"&newzoom="+newzoom+"&random="+Math.random();
                $("#imgmap").attr("src",url);
            }
            slideVal = val;
        });

      /*  myslider.on("change",function(data){
            alert(data.oldValue);
            return false;
        });*/

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
                        $(".slider-track-high").attr('style','background:#82BE97');
                        map2bigger(x,y);
                    }
                }
            }else{//向下滚，缩小
                var oldVal = myslider.slider("getValue");
                if(oldVal>1) {
                    myTimeOut=window.setTimeout(function(){myfunction2()},500);
                    mysliderState = "small";
                    function myfunction2(){
                        $(".slider-track-high").attr('style','background:#82BE97');
                        map2smaller(x, y);
                    }
                }

            }
        });




    });

</script>
</body>


</html>