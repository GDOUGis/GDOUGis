/**
 * 调整鹰眼地图.
 */
function mapsmallpaner(){

    //处理火狐兼容性问题！
    var evt = window.event || arguments.callee.caller.arguments[0]; // 获取event对象

	var centerx;
	var centery;
	var frametop;
	var frameleft;
	var boundhigh;
	var maphigh;
	//frametop=parseInt(document.all.mapboundframe.style.top);
	//frameleft=parseInt(document.all.mapboundframe.style.left);
	frametop=parseInt(document.all.mapboundframe.style.top);
	frameleft=parseInt(document.all.mapboundframe.style.left);
    //alert(window.event);
	//centerx=evt.clientX-(frameleft+1);
	//centery=evt.clientY-(frametop+1);
	centerx=evt.clientX-(frameleft)+30;
	centery=evt.clientY-(frametop)+20;

   // alert(centerx + "," + centery);
    //alert("rqutype=smallpanmap&centerx="+centerx+"&centery="+centery);
	chgmapsrc("rqutype=smallpanmap&centerx="+centerx+"&centery="+centery);
}

/**
 * 重置地图，初始状态.
 */
function mapreset(){
    myslider.slider("setValue",1);//更新滚动条.
    mysliderState = null;
    selectedname = null;
    //每次加载移除原先定位的点
    $(".location").remove();
	chgmapsrc("rqutype=resetmap");
}

/**
 * 显示略缩图.
 */
function mapbound(){
    mapbounder();

	/*if(document.all.mapboundframe.style.display=="none"){
		//获得略缩图，加载略缩图.
		mapbounder();
		//显示
		document.all.mapboundframe.style.display="";
	}
	else{
		document.all.mapboundframe.style.display="none";
	}*/
	/*resetimg();
	state="bound";
	document.all.bound.src="images/index-map-a_08.jpg";*/
}

/**
 * 获得略缩图的资源.
 */
function mapbounder(){
	document.all.boundmap.src=mapboundserviceurl;
}

//改变图片的资源，向服务器发请求.
function chgmapsrc(querystring){
	var locationrul;
	locatinourl="&oldx="+document.all.oldx.value+"&oldy="+document.all.oldy.value+"&oldzoom="+document.all.oldzoom.value;
	//alert(mapserviceurl+"?"+querystring+locatinourl);
	document.all.imgmap.src=mapserviceurl+"?"+querystring+locatinourl;
	//alert(mapserviceurl+"?"+querystring+locatinourl);
}


function setlocation(){	
	center.document.location.reload();
	zoom.document.location.reload();
}


/**
 * 根据图层和名称来查找.
 */
/*function Find()
{
	var layernames = document.getElementById("layerid").value;//获取图层名称
	var selectnames = document.getElementById("selectid").value;//获取选择查找的名称
	//简单校验.
	if(layernames==""||layernames==null){
	   alert("请选择要查询的图层");
	}else if(selectnames==""||selectnames==null){
	   alert("请填写查询的准确的名称");
	   document.getElementById("selectid").focus();
	}else{
		//根据选择的图层和名称改变地图资源
        //var url = encodeURI(encodeURI("rqutype=querymap&layernames="+layernames+"&selectnames="+selectnames));//精确编码
        var url = encodeURI(encodeURI("rqutype=fuzzyQuery&selectnames="+selectnames));//模糊查询
        chgmapsrc(url);
	}

}*/


/****************************************ourselves重写的方法*********************************************/
/**
 * 放大地图.
 * @param x
 * @param y
 */
function map2bigger(x,y){

    var url = mapserviceurl+"?rqutype=chgmapview"+"&centerx="+x+"&centery="+y+"&newzoom=0.5";

    $("#imgmap").attr("src",url);

}

/**
 * 缩小地图.
 * @param x
 * @param y
 */
function map2smaller(x,y){

    var url = mapserviceurl+"?rqutype=chgmapview"+"&centerx="+x+"&centery="+y+"&newzoom=2";
    $("#imgmap").attr("src",url);

}

/**
 * 点击放大按钮放大.
 */
function bigger(){
    var oldVal = myslider.slider("getValue");
    if(oldVal<5){
        mysliderState = 'big';
        var x =parseInt(document.all.imgmap.width)/2;
        var y =parseInt(document.all.imgmap.height)/2;
        var newzoom = 0.5;
        var url = mapserviceurl+"?rqutype=chgmapview"+"&centerx="+x+"&centery="+y+"&newzoom="+newzoom+"&random="+Math.random();
        $("#imgmap").attr("src",url);
    }
}
/**
 * 点击缩小按钮缩小。
 */
function smaller(){
    var oldVal = myslider.slider("getValue");
    if(oldVal>1){
        mysliderState = 'small';
        var x =parseInt(document.all.imgmap.width)/2;
        var y =parseInt(document.all.imgmap.height)/2;
        var newzoom = 2;
        var url = mapserviceurl+"?rqutype=chgmapview"+"&centerx="+x+"&centery="+y+"&newzoom="+newzoom+"&random="+Math.random();
        $("#imgmap").attr("src",url);
    }
}

/**
 * 更新鹰眼地图.
 */
function updataBoundMap(){
    console.log("==> updateBoundMap.")
    $("#mapboundframe").empty();
    $("#mapboundframe").append(" <img id='boundmap'  class='boundmap' GALLERYIMG='false' onclick='mapsmallpaner()'"+
    "style=' position: absolute;left: 1px;top: 1px;height: 180px;width: 240px;visibility: visible;float:left;'" +
    "src="+mapboundserviceurl+"&random="+Math.random()+ ">");
    //-----------------------------------------------------------//
    //解决漫游图片跳跃
    $("#imgdiv").attr("style","position: absolute;top: 0px;left: 0;width: 960px;height: 600px;");
    //加载特征点.
    loadMapInfo();
    console.log("updateBoundMap ==>")
    //更新滚动条.
    if(mysliderState == "big"){
        var oldVal = myslider.slider("getValue");
        myslider.slider("setValue",oldVal+1);//更新滚动条.
        mysliderState = null;
    }else if(mysliderState == "small"){
        var oldVal = myslider.slider("getValue");
        myslider.slider("setValue",oldVal-1);//更新滚动条.
        mysliderState = null;
    }else if(selectedname != null){
        myslider.slider("setValue",3);//更新滚动条.
        mysliderState = null;

    }
}

/**
 * 控制图层显示.
 */
function showLayer(){
    //获取被选中的值.
    var $layer = $("input[name='layerName']:checked");

    var layernames = new Array();
    for(var i=0;i<$layer.length;i++){
        layernames[i] = $layer[i].value;
       // alert(layernames[i]);
    }

    var url = mapserviceurl + "?rqutype=changeLayer"+"&layernames="+layernames;
    $("#imgmap").attr("src",encodeURI(encodeURI(url)));

}

/**
 * 查询.
 */
function searchF(){
    var searchText = $("#searchText").val();
    //alert(searchText);
    var url = mapserviceurl + "?rqutype=fuzzyQuery&selectnames="+searchText;
    var params = {
        date : new Date()
    };
    $.getJSON(encodeURI(encodeURI(url)),params,function(data){

        var result = data.result;
        if(result == null || result==""){
            //alert("没有");
            $(".result").empty();
            $(".result").append("<p>抱歉，找不到该建筑物！</p>");
        }else{
            $(".result").empty();
            $(".result").append("<a>查询结果如下:</a><br>");
            for(var i=0;i<result.length;i++){
                $(".result").append("<a href='#' onclick='findByName(this)'>"+result[i]+"</a><br>");
            }
        }

    });
}

/**
 *根据名称查找.
 */
function findByName(a){
    var $a = $(a);
    var name = $a.text();
    //alert(name);
    var url = mapserviceurl + "?rqutype=findByName&queryName="+name;
    $("#imgmap").attr("src",encodeURI(encodeURI(url)));

    selectedname = name;
}


/**
 * 加载地图信息.
 */
function loadMapInfo(){
    console.log("==> loadMapInfo.");
    //每次加载之前先移除原先的点
    $("#mapframe a").remove();
    //每次加载移除原先定位的点
    $(".location").remove();


    var zoom = $("input[name='oldzoom']").val();
    var top = parseInt(document.all.mapframe.style.top);//图片距离顶部的距离.
    var left = parseInt(document.all.mapframe.style.left);

    /**
     * 初始化特征点
     */
    var url = "servlet/MapServlet";

    var $mapframe = $("#mapframe");
    var mapfremeLeft = $mapframe.offset().left;
    var mapfremeTop = $mapframe.offset().top;
    var picwidth = $mapframe.width();
    var picheight  = $mapframe.height();
    var params = {
        'rqutype':'loadFeature',
        'date':new Date()
    }

    $.getJSON(url, params, function(data) {
       // alert("返回数据啦");

        var featurePoints = data.featurePoints;
        var newzoom = data.newzoom;
        var rang =  parseInt(2140*18/newzoom);//矩形区域距离中心点绝对值.
        console.log("进入getJSON方法..")
        //featuresPoints = data;
        console.log(mapfremeLeft + ", " + mapfremeTop + "; " + picwidth + ", " + picheight)
        var spanX;
        var spanY;
        // 绑定数据
        var i = 0;
        while(i<featurePoints.length) {
            ////spanX = featuresPoints[i].x - mapfremeLeft;
            ////spanY = featuresPoints[i].y - mapfremeTop;
            spanX = featurePoints[i].x;
            spanY = featurePoints[i].y;
            //var screenX = spanX-rang+10;
            //var screenY = spanY-rang+10;
            var screenX = spanX-rang/2;
            var screenY = spanY-rang/2;
            var name = featurePoints[i].name;
            var id = featurePoints[i].id;
            $("#imgdiv").append(
                "<a  href='#' onclick=getFeaturePoint("+id+",'"+name+"') class="+name+"  data-toggle='modal' data-target='#myModal' style='position:absolute; left:"+screenX+"; top:"+screenY+"; float:left; z-index:9999;'" +
                "onmouseover=moveFeaturePoint('"+name+"',event) onmouseout=moveoutFeaturePoint('"+name+"')>" +
                "<div style='width:"+rang+" ;height:"+rang+" ;'>*" +
                "<span style='display: none'>"+name+"</span>" +
                "</div>" +
                "</a>");
            if(selectedname == name){
                //alert("进来了");
                var x = spanX-70/2;
                var y = spanY-70;
                $("#imgdiv").append("<div class='location' style='position:absolute; left:"+x+"; top:"+y+";'><img src='images/location2.png' height='70px'></div>");
            }
            console.log("name: " + featurePoints[i].name + "\n")
            console.log("location:("+featurePoints[i].x+", "+featurePoints[i].y+")")
            i++;
        }
        console.log("退出入getJSON方法")
    });
    console.log("loadMapInfo ==>");
}
/**
 * 特征点显示
 * @param x
 * @param y
 */

function moveFeaturePoint(name,e) {
    //alert(name);
    var evn = window.event||e;
    var mouseX = evn.clientX+2;
    var mouseY = evn.clientY;
    $("body").append("<span class='FeatureName' style='border-radius:5px; color:#fff;font-size:24px;background:red;position:absolute; left:"+mouseX+"; top:"+mouseY+";' name='"+name+"'>"+name+"</span>");

}
function moveoutFeaturePoint(name){
    //alert("移开了");
    $("span[name='"+name+"']").remove();
    $(".FeatureName").remove();
}

function showFeatureDetail(id) {
    var url = "servlet/MapServlet_Rose?rqutype=showFeatureDetail";
    var param = {id:id};
    $.getJSON(url, param, function(data) {
        console.log("[id:"+data.id+", name:"+data.name+"]")
    });
}

/**
 * 根据ID查找出备用名，并拼接到cContent后面.
 * @param id
 * @param name
 */
function getAliasById(id, name){
    console.log("=============进入getAliasById方法.=============")
    $("#cContent p").remove();
    $("#cTitle").text(name);
    $("#fpId").val(id);
    // 获取父元素
    var $cContent = $("#cContent");

    var url = "servlet/MapServlet?rqutype=getAliasById";
    var params = {
        id:id
    }
    $.getJSON(url, params, function(alias){
        var i = 0;
        //for(; i < alias.length; i++) {
        //    $cContent.append("<p>"+alias[i]+"</p>");
        //    console.log(name + "的备用名【"+id+"】 => " + alias[i])
        //}
    });
    console.log("=============退出getAliasById方法.=============")
}

/**
 * 根据ID查找出备用名，并拼接到cContent后面.
 * @param id
 * @param name
 */
function getAliasByName(id, name){
    console.log("=============进入getAliasById方法.=============")
    $("#cContent p").remove();
    $("#cTitle").text(name);
    $("#fpId").val(id);
    // 获取父元素
    var $cContent = $("#cContent");

    var url = "servlet/MapServlet?rqutype=getAliasByName";
    var params = {
        name:name
    }
    $.getJSON(url, params, function(alias){
    });
    console.log("=============退出getAliasById方法.=============")
}

function getFeaturePoint(id, name) {
    console.log("=============进入getFeaturePoint方法.=============");
    $("#cTitle").text(name);
    $("#fpId").val(id);
    var url = "servlet/FeaturePointServlet";
    var params = {
        method:"getFeaturePoint",
        currentName:encodeURI(name)
    };
    $.getJSON(url, params, function(featurePoint){
        $("#cPrepareName").text(featurePoint.prepareName);
        $("#cPrepareDesc").val(featurePoint.description);
    });
    //高亮
    if(name != null){
        var url = mapserviceurl + "?rqutype=hightLight&queryName="+name;
        $("#imgmap").attr("src",encodeURI(encodeURI(url)));
    }
    console.log("=============退出getFeaturePoint方法.=============");
}