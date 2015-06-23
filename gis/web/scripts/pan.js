var ie=document.all;

var nn6=document.getElementById&&!document.all;

var isdrag=false;

var x,y;

var dobj;


function movemouse(e)

{
    //console.log("移动了");
    var evn= window.event||e;

    if (isdrag)

    {
        moveState = true;//鼠标移动了

        dobj.style.left = (nn6 ? tx + e.clientX - x : tx + event.clientX - x) + 'px';

        dobj.style.top  = (nn6 ? ty + e.clientY - y : ty + event.clientY - y)  + 'px';

        return false;

    }

}

function selectmouse(e)

{
    var evn= window.event||e;

    startx = evn.clientX;
    starty = evn.clientY;

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

    var fobj = nn6 ? e.target : event.srcElement;

    var topelement = nn6 ? "HTML" : "BODY";

    while (fobj.tagName != topelement && fobj.className != "imgdiv")

    {

        fobj = nn6 ? fobj.parentNode : fobj.parentElement;

    }

    var x = evn.clientX-startx;
    var y = evn.clientY-starty;

    if (fobj.className=="imgdiv") {
        isdrag = false;
        if(x==0 & y==0) {

            onclickState = true;
            //鼠标xy
            var x = evn.clientX;
            var y = evn.clientY;
            //图片所在div距离屏幕边界的xy
            var imgx = document.all.mapframe.style.left;
            var imgy = document.all.mapframe.style.top;
            //计算得出相对于图片的xy
            var scrollX = $(document).scrollLeft();
            x = parseInt(x) - parseInt(imgx)+ scrollX;
            var scrollY = $(document).scrollTop();
            y = parseInt(y) - parseInt(imgy)+scrollY;
            var date = new Date();
            var url = mapserviceurl + "?rqutype=findByPoint&x="+x+"&y="+y+"&date="+date;

            $("#imgmap").attr("src",encodeURI(encodeURI(url)));

        }else{
            //原来图片的中心点
            var oldcenterx=parseInt(document.all.imgmap.width)/2;
            var oldcentery=parseInt(document.all.imgmap.height)/2;
            //alert(oldcenterx);
            //新的图片中心点
            var centerx = oldcenterx-x;
            var centery = oldcentery-y;

            chgmapsrc("rqutype=panmap&centerx=" + centerx + "&centery=" + centery);
        }

    }

    //去除提示
    $(".FeatureName").remove();
};

