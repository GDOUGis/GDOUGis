var featuresPoints;    // 保存特征点的集合

var state="";//当前操作的状态
var eventstate="";//鼠标事件的状态
var oldx=0;
var oldy=0;
var startx=0;
var starty=0;
var mapserviceurl="servlet/MapServlet";
var mapboundserviceurl="servlet/MapServlet?rqutype=boundmap";
/*document.onmousedown=mapmousedown;
document.onmousemove=mapmousemove;
document.onmouseup=mapmouseup;*/
document.all.imgmap.onreadystatechange=downloadstate;
document.all.boundmap.onreadystatechange=bounddownloadstate;
//document.all.seltable.onmousemove=tablemove;
window.name="mapwindow";
//在此加载地图;
document.all.imgmap.src=mapserviceurl+"?rqutype=initmap";
document.all.center.src=mapserviceurl+"?rqutype=centerpoint";
document.all.zoom.src=mapserviceurl+"?rqutype=zoom";

/*控制图片*/
/**
 * Created by lenovo on 2015/1/23.
 */

jQuery(document).ready(function(){

    $(window).load(function(){
        var zoom = $("input[name='oldzoom']").val();
        var rang =  parseInt(2140*15/zoom);//矩形区域距离中心点绝对值.
        var top = parseInt(document.all.mapframe.style.top);//图片距离顶部的距离.
        var left = parseInt(document.all.mapframe.style.left);

        /**
         * 初始化特征点
         */
        var url = "/servlet/MapServlet_Rose?rqutype=loadFeature";
        var $mapframe = $("#mapframe");
        var mapfremeLeft = $mapframe.offset().left;
        var mapfremeTop = $mapframe.offset().top;
        var picwidth = $mapframe.width();
        var picheight  = $mapframe.height();
        $.getJSON(url, null, function(data) {
            console.log("进入getJSON方法..")
            featuresPoints = data;
            console.log(mapfremeLeft + ", " + mapfremeTop + "; " + picwidth + ", " + picheight)
            var spanX;
            var spanY;
            // 绑定数据
            var i = 0;
            while(i<featuresPoints.length) {
                ////spanX = featuresPoints[i].x - mapfremeLeft;
                ////spanY = featuresPoints[i].y - mapfremeTop;
                spanX = featuresPoints[i].x;
                spanY = featuresPoints[i].y;
                var screenX = spanX-rang;
                var screenY = spanY-10-rang;
                var name = featuresPoints[i].name;
                console.log("Ospan = ("+spanX+", "+spanY+")");
                $mapframe.append("<a class="+name+" style='position:absolute; left:"+screenX+"; top:"+screenY+"; float:left; z-index:9999;'" +
                "onmouseover=moveFeaturePoint('"+name+"') onmouseout=moveoutFeaturePoint('"+name+"')><div style='width:"+rang+" ;height:"+rang+" ;'>*<span style='display: none'>"+name+"</span></div></a>");

                console.log("name: " + featuresPoints[i].name + "\n")
                console.log("location:("+featuresPoints[i].x+", "+featuresPoints[i].y+")")

                i++;
            }
            console.log("退出入getJSON方法")
        });

        $('#imgmap').each(function(){
           // alert("进来了");
            var x = 960; //填入目标图片宽度
            var y = 620; //目标图片高度
            var w=$(this).width(), h=$(this).height();//获取图片宽度、高度
            if (w > x) { //图片宽度大于目标宽度时
                var w_original=w, h_original=h;
                h = h * (x / w); //根据目标宽度按比例算出高度
                w = x; //宽度等于预定宽度
               if (h < y) { //如果按比例缩小后的高度小于预定高度时
                    w = w_original * (y / h_original); //按目标高度重新计算宽度
                    h = y; //高度等于预定高度
                }
            }
            if(w < x){//图片宽度小于目标宽度时
                var w_original=w, h_original=h;
                h = h * (x / w); //根据目标宽度按比例算出高度
                w = x; //宽度等于预定宽度
            }
            $(this).attr({width:w,height:h});
            //var screenH = parseInt(document.documentElement.clientHeight)-document.all.mapframe
           // $("#mapframe").attr('style','height:'+y);
            //alert(screenH );
        });
    });
});


/**
 * 特征点显示
 * @param x
 * @param y
 */

function moveFeaturePoint(name) {
    //alert(name);
    var mouseX = window.event.clientX;
    var mouseY = window.event.clientY;
    $("body").append("<span style='background:#fff;width:150px;height:24px;position:absolute; left:"+mouseX+"; top:"+mouseY+";' name='"+name+"'>"+name+"</span>");

}
function moveoutFeaturePoint(name){
    //alert("移开了");
    $("span[name='"+name+"']").remove();
}