

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

