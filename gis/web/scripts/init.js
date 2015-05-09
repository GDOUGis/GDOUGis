var state="";//当前操作的状态
var eventstate="";//鼠标事件的状态
var oldx=0;
var oldy=0;
var mapserviceurl="servlet/MapServlet";
var mapboundserviceurl="servlet/MapServlet?rqutype=boundmap";
startx=0;
starty=0;
mousestate ="";//鼠标是否按下的状态
selectedname = null;
myslider = null;
mysliderState = null;
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
