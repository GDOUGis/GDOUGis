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
	frametop=parseInt(document.all.mapboundframe.style.top);
	frameleft=parseInt(document.all.mapboundframe.style.left);
	centerx=evt.clientX-(frameleft)+30;
	centery=evt.clientY-(frametop)+20;

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
	document.all.imgmap.src=mapserviceurl+"?"+querystring+locatinourl;
}


function setlocation(){	
	center.document.location.reload();
	zoom.document.location.reload();
}


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
    //如果是点击事件发起的
    if(onclickState){
        /**
         * 判断是否弹出修改框。
         */
        $.getJSON(mapserviceurl + "?rqutype=isHightLight", {Date: new Date()}, function (data) {
            var result = data.result;
            if (result == 1) {
                $('#myModal').modal('show');//显示模态框.
                $("#cTitle").text(data.ftrName);
                name=data.ftrName;
                var url = "servlet/FeaturePointServlet";
                var params = {
                    method: "getFeaturePoint",
                    currentName: encodeURI(name)
                };
                $.getJSON(url, params, function (featurePoint) {
                    $("#cPrepareName").text(featurePoint.prepareName);
                    $("#cPrepareDesc").val(featurePoint.prepareDescription);
                    $("#cdescription").text(featurePoint.description);
                    $("#fpId").val(featurePoint.id);
                });
            } else {
                return false;
            }
        });
        //将标识标示为否
        onclickState = false;
    }else{
        //更新鹰眼.
        $("#boundmap").attr("src",mapboundserviceurl+"&random="+Math.random());
        //加载特征点.
        loadMapInfo();
    }

    //解决漫游图片跳跃
    $("#imgdiv").attr("style","position: absolute;top: 0px;left: 0;width: 960px;height: 600px;");

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
        selectedname = null;//去除选中名字，解决改变滚动条之后又回到3的问题
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
    }

    var url = mapserviceurl + "?rqutype=changeLayer"+"&layernames="+layernames;
    $("#imgmap").attr("src",encodeURI(encodeURI(url)));

}

/**
 * 查询.
 */
function searchF(){
    var searchText = $("#searchText").val();
    var url = mapserviceurl + "?rqutype=fuzzyQuery&selectnames="+searchText;
    var params = {
        date : new Date()
    };
    $.getJSON(encodeURI(encodeURI(url)),params,function(data){

        var result = data.result;
        if(result == null || result==""){
            $(".result").empty();
            $(".result").append("<p>抱歉，找不到该建筑物！</p>");
        }else{
            $(".result").empty();
            $(".result").append("<a>查找结果如下:</a><br>");
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
    var url = mapserviceurl + "?rqutype=findByName&queryName="+name;
    $("#imgmap").attr("src",encodeURI(encodeURI(url)));

    selectedname = name;
}


/**
 * 加载地图信息.
 */
function loadMapInfo(){
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

    var params = {
        'rqutype':'loadFeature',
        'date':new Date()
    };

    $.getJSON(url, params, function(data) {
        var featurePoints = data.featurePoints;
        var newzoom = data.newzoom;
        var rang =  parseInt(2140*18/newzoom);//矩形区域距离中心点绝对值.
        var spanX;
        var spanY;
        // 绑定数据
        var i = 0;

        while(i<featurePoints.length) {

            spanX = featurePoints[i].x;
            spanY = featurePoints[i].y;

            var screenX = (spanX-rang/2) +'px';
            var screenY = (spanY-rang/2)+'px';
            var name = featurePoints[i].name;

           $("#imgdiv").append(
                "<a  href='#' class='"+name+" model' style='width:"+rang+"px;height:"+rang+"px;position:absolute; left:"+screenX+"; top:"+screenY+"; z-index:9999;'" +
                "onmouseover=moveFeaturePoint('"+name+"',event) onmouseout=moveoutFeaturePoint('"+name+"')>" +
                "<div style=''>" +
                "<span style='display: none'>"+name+"</span>" +
                "</div>" +
                "</a>");
            i++;

        }
    });
}
/**
 * 特征点显示
 * @param x
 * @param y
 */

function moveFeaturePoint(name,e) {
    var evn = window.event||e;
    var mouseX = evn.clientX+2+$(document).scrollLeft();
    var mouseY = evn.clientY+$(document).scrollTop();
    $("body").append("<span class='FeatureName' style='border-radius:5px; color:#fff;font-size:24px;background:red;position:absolute; left:"+mouseX+"; top:"+mouseY+";' name='"+name+"'>"+name+"</span>");

}
function moveoutFeaturePoint(name){
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
    $("#cContent p").remove();
    $("#cTitle").text(name);
    $("#fpId").val(id);
    // 获取父元素
    var $cContent = $("#cContent");

    var url = "servlet/MapServlet?rqutype=getAliasById";
    var params = {
        id:id
    };
    $.getJSON(url, params, function(alias){
        var i = 0;
        //for(; i < alias.length; i++) {
        //    $cContent.append("<p>"+alias[i]+"</p>");
        //    console.log(name + "的备用名【"+id+"】 => " + alias[i])
        //}
    });
}

/**
 * 根据ID查找出备用名，并拼接到cContent后面.
 * @param id
 * @param name
 */
function getAliasByName(id, name){
    $("#cContent p").remove();
    $("#cTitle").text(name);
    $("#fpId").val(id);
    // 获取父元素
    var $cContent = $("#cContent");

    var url = "servlet/MapServlet?rqutype=getAliasByName";
    var params = {
        name:name
    };
    $.getJSON(url, params, function(alias){
    });
}


function getFeaturePoint(id, name) {
    if(moveState){
        moveState = false;
        $('#myModal').modal('hide');//关闭模态框.
        return false;
    }else{
        return false;
    }
}
