/**
 * 放大地图.
 */
function mapbigger(){
	var centerx;
	var centery;
	var newzoom;
	var frametop;
	var frameleft;
	var tablewidth;
	var tablehigh;
	var picwidth;
	var pichigh;
	frametop=parseInt(document.all.mapframe.style.top);
	frameleft=parseInt(document.all.mapframe.style.left);
	tablehigh=parseInt(document.all.seltable.style.height);
	tablewidth=parseInt(document.all.seltable.style.width);
	tableleft=parseInt(document.all.seltable.style.left);
	tabletop=parseInt(document.all.seltable.style.top);
	picwidth=parseInt(document.all.imgmap.style.width);
	pichigh=parseInt(document.all.imgmap.style.height);
	centerx=tablewidth/2+tableleft-frameleft;
	centery=tablehigh/2+tabletop-frametop;
	if(tablewidth>tablehigh){
		//newzoom=tablewidth/picwidth
		newzoom=0.5;
	}
	else{
		//newzoom=tablehigh/pichigh
		newzoom=0.5;
	}
	if(newzoom==0){
		newzoom=0.5;
	}
	//计算出新的地图坐标大小之后改变地图的显示。
	chgmapsrc("rqutype=chgmapview&centerx="+centerx+"&centery="+centery+"&newzoom="+newzoom);
}

/**
 * 缩小地图.
 */
function mapsmaller(){
	var centerx;
	var centery;
	var frametop;
	var frameleft;
	frametop=parseInt(document.all.mapframe.style.top);
	frameleft=parseInt(document.all.mapframe.style.left);
	centerx=window.event.clientX-(frameleft+1);
	centery=window.event.clientY-(frametop+1);
	chgmapsrc("rqutype=chgmapview&centerx="+centerx+"&centery="+centery+"&newzoom=2");
}

/**
 * 漫游地图.
 */
function mappaner(){
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
	//alert("wi--"+picwidth+"--he--"+pichigh+"--pictop--"+pictop+"--le--"+picleft);
	if(pictop!=0&&picleft!=0){
		centerx=picwidth/2-picleft;
		centery=pichigh/2-pictop;
		//alert(picwidth + ',' + picleft + ',' + centerx + ',' + centery);
		chgmapsrc("rqutype=panmap&centerx="+centerx+"&centery="+centery);
		document.all.imgmap.style.left=0;
		document.all.imgmap.style.top=0;
	}
}

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
	frametop=parseInt(document.all.mapboundframe.style.top);
	frameleft=parseInt(document.all.mapboundframe.style.left);
    //alert(window.event);
	centerx=evt.clientX-(frameleft+1);

	centery=evt.clientY-(frametop+1);
   // alert(centerx + "," + centery);
	chgmapsrc("rqutype=smallpanmap&centerx="+centerx+"&centery="+centery);
}

/**
 * 重置地图，初始状态.
 */
function mapreset(){
    resetimg();
	state="reset";
	chgmapsrc("rqutype=resetmap");
	document.all.resetimg.src="images/index-map-a_07.jpg";
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
	resetimg();
	state="bound";
	document.all.bound.src="images/index-map-a_08.jpg";
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
function Find()
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
	   chgmapsrc("rqutype=querymap&layernames="+layernames+"&selectnames="+selectnames);
	}

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