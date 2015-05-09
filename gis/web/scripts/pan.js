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



    var fobj = nn6 ? e.target : event.srcElement;

    var topelement = nn6 ? "HTML" : "BODY";

    while (fobj.tagName != topelement && fobj.className != "imgdiv")

    {

        fobj = nn6 ? fobj.parentNode : fobj.parentElement;

    }

    if (fobj.className=="imgdiv") {

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


    }



};

