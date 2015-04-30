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
    picwidth = 600;
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
    if(window.event.button == 1) {
        c_pan();
    }
}

function recordOldPoint(event) {

    if(window.event.button == 1) {
        oldx = event.x;
        oldy = event.y;
    }

}

function getMap(event) {
    if(window.event.button == 1) {
        c_mappaner();
    }
}