/*
drag = 0;
move = 0;
function mousedown()
{
    if(drag)
    {
        X1 = window.event.x - parseInt(dragimages.style.left);
        Y1 = window.event.y - parseInt(dragimages.style.top);
        dragimages.style.Index += 1;
        move = 1;
    }
    startx = window.event.clientX;
    starty = window.event.clientY;
}
function mouseStop()
{
    window.event.returnValue = false;
}
function mousemove()
{
    if (move)
    {
        dragimages.style.left = window.event.x - X1;
        dragimages.style.top = window.event.y - Y1;
    }
}
function mouseup()
{

    move = 0;

    var x = window.event.clientX-startx;
    var y = window.event.clientY-starty;
    //原来图片的中心点
    var oldcenterx=parseInt(document.all.imgmap.width)/2;
    var oldcentery=parseInt(document.all.imgmap.height)/2;
    //alert(oldcenterx);
    //新的图片中心点
    var centerx = oldcenterx-x;
    var centery = oldcentery-y;



    chgmapsrc("rqutype=panmap&centerx=" + centerx + "&centery=" + centery);
    //复原div，一下代码交给updataBoundMap()处理了。
    //$("#imgdiv").removeAttr("style");
    //$("#imgdiv").attr("style","position: absolute;top: 0px;left: 0;width: 960px;height: 600px;")

}
*/

var ie=document.all;

var nn6=document.getElementById&&!document.all;

var isdrag=false;

var x,y;

var dobj;


function movemouse(e)

{

    if (isdrag)

    {

        dobj.style.left = nn6 ? tx + e.clientX - x : tx + event.clientX - x;

        dobj.style.top  = nn6 ? ty + e.clientY - y : ty + event.clientY - y;

        return false;

    }

}

function selectmouse(e)

{
    startx = e.clientX;
    starty = e.clientY;

    var fobj = nn6 ? e.target : event.srcElement;

    var topelement = nn6 ? "HTML" : "BODY";

    while (fobj.tagName != topelement && fobj.className != "imgdiv")

    {

        fobj = nn6 ? fobj.parentNode : fobj.parentElement;

    }

    if (fobj.className=="imgdiv")

    {

        isdrag = true;

        dobj = fobj;

        tx = parseInt(dobj.style.left+0);

        ty = parseInt(dobj.style.top+0);

        x = nn6 ? e.clientX : event.clientX;

        y = nn6 ? e.clientY : event.clientY;

        document.onmousemove=movemouse;

        return false;

    }

}

document.onmousedown=selectmouse;

document.onmouseup=function(e){
    //alert("123");
    var evn= window.event||e;
    isdrag = false;

    var x = evn.clientX-startx;
    var y = evn.clientY-starty;
    //原来图片的中心点
    var oldcenterx=parseInt(document.all.imgmap.width)/2;
    var oldcentery=parseInt(document.all.imgmap.height)/2;
    //alert(oldcenterx);
    //新的图片中心点
    var centerx = oldcenterx-x;
    var centery = oldcentery-y;

    chgmapsrc("rqutype=panmap&centerx=" + centerx + "&centery=" + centery);
};

//document.onmouseup=new Function("isdrag=false");



function c_mappaner(){
    var centerx;
    var centery;
    var picwidth;
    var pichigh;
    var picleft;
    var pictop;
    picwidth=parseInt(document.all.imgmap.style.width);
    pichigh=parseInt(document.all.imgmap.style.height);
    pictop=parseInt(document.all.imgmap.style.top);
    picleft=parseInt(document.all.imgmap.style.left);
    picwidth = 900;
    pichigh  = 400;
    console.log("("+picwidth+","+ pichigh +","+ picleft +","+picleft+")");
    //alert("wi--"+picwidth+"--he--"+pichigh+"--pictop--"+pictop+"--le--"+picleft);
    if(pictop!=0&&picleft!=0) {
        centerx = picwidth / 2 - picleft;
        centery = pichigh / 2 - pictop;
        console.log(picwidth + ',' + picleft + ',' + centerx + ',' + centery);
        chgmapsrc("rqutype=panmap&centerx=" + centerx + "&centery=" + centery);
        document.all.imgmap.style.left = 0;
        document.all.imgmap.style.top = 0;
    }
}

/**
 * 移动鼠标漫游,获得新的中心点；
 */
function c_pan(){
    var newx;
    var newy;
    var picwidth;
    var pichigh;
    picwidth=parseInt(document.all.imgmap.style.width);
    pichigh=parseInt(document.all.imgmap.style.height);
    newx=startx+(window.event.clientX-oldx);
    //startx startx=parseInt(document.all.imgmap.style.left)
    newy=starty+(window.event.clientY-oldy);
    //alert(startx) ;
    console.log("newx"+newx + ", newy:" + newy);
    if (newx>picwidth) newx=picwidth;
    if (newx<-picwidth) newx=-picwidth;
    if (newy>pichigh) newy=pichigh;
    if (newy<-pichigh) newy=-pichigh;
    document.all.imgmap.style.left=newx;
    document.all.imgmap.style.top=newy;
}

function movemap(event) {
   /* if((window.event.button == 1 || window.event.button == 0)&&mouse) {
        //alert("123");
        c_pan();
    }*/
    if(mousestate == -1){
        mousestate = 0;
        c_pan();
    }
}

/**
 * 按下鼠标.
 * @param event
 */
function recordOldPoint(event) {

    if((window.event.button == 1 || window.event.button == 0)) {
        mousestate = -1;
        oldx = event.x;
        oldy = event.y;
    }

}

/**
 * 松开鼠标，请求数据.
 * @param event
 */
function getMap(event) {

    if(mousestate == 0){
        mousestate = 1;
        c_mappaner();
    }

   /* if((window.event.button == 1 || window.event.button == 0)) {
        c_mappaner();
    }
    mousestate = 1;*/
}
