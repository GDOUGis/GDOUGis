
/**
 * 按下鼠标.
 */
function mapmousedown(){
	if(window.event.srcElement.id=="imgmap"){
		//如果为放大操作.
		if (state=="big"){
			document.all.seltable.style.display="";
			document.all.seltable.style.width=0;
		 	document.all.seltable.style.height=0;
			document.all.seltable.style.left=window.event.clientX;
			document.all.seltable.style.top=window.event.clientY;
			oldx=window.event.clientX;
			oldy=window.event.clientY;
			eventstate="eventbegin";
		}
		if(state=="small"){
			document.all.selimg.style.display="";
			document.all.selimg.style.left=window.event.clientX;
			document.all.selimg.style.top=window.event.clientY;
			eventstate="eventbegin";
		}
		if(state=="pan"){
			oldx=window.event.clientX;
			oldy=window.event.clientY;
			//alert("x---->"+oldx+"---y---->"+oldy);
			startx=parseInt(document.all.imgmap.style.left);
			starty=parseInt(document.all.imgmap.style.top);
			//alert("left:"+startx+",top:"+starty);
			eventstate="eventbegin";
		}
	}
	window.event.returnValue=false;
}

/**
 * 移动鼠标.
 */
function mapmousemove(){
	
	if(window.event.srcElement.id=="imgmap"){
		
		if(state=="big"){
			if(window.event.button==1){
				box();
			}
		}
		if(state=="pan"){
			if(window.event.button==1){
				//alert("进来了");
				pan();
			}
		}
	}
	window.event.returnValue=false;
}

/**
 * 松开鼠标事件.
 */
function mapmouseup(){
	if(eventstate=="eventbegin"){
		if(state=="big"){
			document.all.seltable.style.display="none";
			mapbigger();
		}
		if(state=="small"){
			document.all.selimg.style.display="none";
			mapsmaller();
		}
		if(state=="pan"){
			mappaner();
		}
		eventstate="eventend";
	}
	window.event.returnValue=false;
}

/**
 * 不清楚用来做什么==。
 */
function tablemove(){
	var width;
	var height;
	if(window.event.button==1){
		width=window.event.clientX-oldx;
		height=window.event.clientY-oldy;
		if(width<0&&height<0){
			document.all.seltable.style.left=window.event.clientX;
			document.all.seltable.style.top=window.event.clientY;
		}
		else if(width<0){
			document.all.seltable.style.left=window.event.clientX;
	    }
		else if(height<0){
			document.all.seltable.style.top=window.event.clientY;
		}
		document.all.seltable.style.height=Math.abs(height)+1;
		document.all.seltable.style.width=Math.abs(width)+1;
	}
	window.event.returnValue=false;
}

/**
 * 点击放大按钮.
 */
function mapbig(){
	//重置按钮.
	resetimg();
	state="big";
	document.all.imgmap.style.cursor="default";
	document.all.bigimg.src="images/index-map-a_03.jpg";
	document.all.layer1.innerText="当前状态：放大";
}

/**
 * 点击缩小按钮.
 */
function mapsmall(){
	resetimg();
	state="small";
	document.all.imgmap.style.cursor="default";
	document.all.smallimg.src="images/index-map-a_05.jpg";
	document.all.layer1.innerText="当前状态：缩小";
}

/**
 * 点击漫游按钮.
 */
function mappan(){
	resetimg();
	state="pan";
	document.all.imgmap.style.cursor="move";
	document.all.panimg.src="images/index-map-a_06.jpg";
	document.all.layer1.innerText="当前状态：浏览";
}

/**
 * 大地图onreadystatechange触发事件.该事件用于监听服务器响应的状态.
 */
function downloadstate(){
	var dlstate=document.all.imgmap.readyState;
	if(dlstate=="uninitialized"){
		document.all.downloadimg.style.display="";
		document.all.imgmap.style.visibility="hidden";
	}
	if(dlstate=="loading"){
		document.all.downloadimg.style.display="";
		document.all.imgmap.style.visibility="hidden";
		if(document.all.mapboundframe.style.display==""){
			mapbounder();
		}
		setlocation();
	}
	if(dlstate=="complete"){
		document.all.downloadimg.style.display="none";
		document.all.imgmap.style.visibility="visible";
	}
}

/**
 * 鹰眼地图onreadystatechange的事件.
 */
function bounddownloadstate(){
	var dlstate=document.all.boundmap.readyState;
	if(dlstate=="uninitialized"){
		document.all.downloadimg.style.display="";
		document.all.boundmap.style.visibility="hidden";
	}
	if(dlstate=="loading"){
		document.all.downloadimg.style.display="";
		document.all.boundmap.style.visibility="hidden";
	}
	if(dlstate=="complete"){
		document.all.downloadimg.style.display="none";
		document.all.boundmap.style.visibility="visible";
	}
}

