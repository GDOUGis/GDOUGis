package org.cpp.gis.utils;

import com.mapinfo.dp.annotation.AnnotationDataProviderHelper;
import com.mapinfo.dp.annotation.AnnotationTableDescHelper;
import com.mapinfo.dp.util.LocalDataProviderRef;
import com.mapinfo.mapj.MapJ;
import com.mapinfo.mapxtreme.client.MapXtremeImageRenderer;
import com.mapinfo.util.DoublePoint;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.security.interfaces.DSAPublicKey;
import java.util.Map;

/**
 * Created by Seven on 2015/4/27.
 */
public class MapUtils {

    //图片格式
    private static String imgtype = "jpeg";

    private static int imgsizex = 600;

    private static int imgsizey = 400;

    private static int smallimgsizex = 300;

    private static int smallimgsizey = 200;

    private static Color imgbgcolor = Color.white;

    /**
     * 加载地图.
     * @param m_mapPath
     * @param m_fileToLoad
     * @return
     */
    public static MapJ initMapJ(String m_mapPath,String m_fileToLoad) throws Exception{

        MapJ mapJ = new MapJ();
        //加载地图.
        if(m_fileToLoad.endsWith("gst")){
            mapJ.loadGeoset(m_fileToLoad,m_mapPath,null);
        } else {
            mapJ.loadMapDefinition(m_fileToLoad);
        }
        return mapJ;
    }


    /**
     * 初始化地图.
     * @param mapJ
     * @param request
     * @return
     * @throws Exception
     */
    public static MapJ initMap(MapJ mapJ,HttpServletRequest request) throws Exception{

        if ((request.getParameter("oldx") != null)
                && (request.getParameter("oldy") != null)) {
            Double oldx = new Double(request.getParameter("oldx"));
            Double oldy = new Double(request.getParameter("oldy"));
            DoublePoint mappoint = new DoublePoint(oldx.doubleValue(),
                    oldy.doubleValue());
            Double oldzoom = new Double(request.getParameter("oldzoom"));
            mapJ.setCenter(mappoint);
            mapJ.setZoom(oldzoom.doubleValue());
        }

        return mapJ;
    }

    /**
     * 初始化鹰眼地图.
     * @param boundMap
     * @return
     * @throws Exception
     */
    public static MapJ initBoundMap(MapJ boundMap) throws Exception{
        /**
         * @添加图层的步骤
         * @1--创建TableDescHelper
         * @2--创建DataProviderHelper
         * @3--创建DataProviderRef
         * @4--创建layers.insert
         */
        AnnotationTableDescHelper antable = new AnnotationTableDescHelper("anlayer");
        AnnotationDataProviderHelper andata = new AnnotationDataProviderHelper();
        LocalDataProviderRef andataref = new LocalDataProviderRef(andata);
        boundMap.getLayers().insert(andataref, antable, 0, "anlayer");

        return boundMap;
    }


    /**
     * 改变地图视野.
     * @param mapJ
     * @param request
     * @return
     * @throws Exception
     */
    public static MapJ chgMapView(MapJ mapJ,HttpServletRequest request) throws Exception{
        //原来的范围.
        Double oldzoom = mapJ.getZoom();
        //新的地图范围.
        Double newzoomopr = new Double(request.getParameter("newzoom"));
        Double newzoom = oldzoom*newzoomopr.doubleValue();
        //获取鼠标坐标.
        Double centerx = new Double(request.getParameter("centerx"));
        Double centery = new Double(request.getParameter("centery"));
        //屏幕点.
        DoublePoint screenPoint = new DoublePoint(centerx.doubleValue(),centery.doubleValue());
        //转化为地图中点.
        DoublePoint mapPoint = mapJ.transformScreenToNumeric(screenPoint);
        mapJ.setCenter(mapPoint);
        mapJ.setZoom(newzoom);

        return mapJ;
    }


    /**
     * 漫游地图.
     * @param mapJ
     * @param request
     * @return
     * @throws Exception
     */
    public static MapJ panMap(MapJ mapJ,HttpServletRequest request) throws Exception{
        // 从前台取得鼠标坐标 X ；
        Double centerx = new Double(request.getParameter("centerx"));
        // 从前台取得鼠标坐标 Y ；
        Double centery = new Double(request.getParameter("centery"));
        // 创建一个新的点
        DoublePoint screenpoint = new DoublePoint(centerx.doubleValue(),
                centery.doubleValue());
        // 转换坐标
        DoublePoint mappoint = mapJ.transformScreenToNumeric(screenpoint);
        // 设定中心
        mapJ.setCenter(mappoint);
        return mapJ;

    }

    /**
     * 重置地图、全图.
     * @param mapJ
     * @param request
     * @return
     * @throws Exception
     */
    public static MapJ resetMap(MapJ mapJ,HttpServletRequest request) throws Exception{
        //获取当前视野范围.
        Double zoom = mapJ.getZoom();
        //获取当前地图中心点.
        DoublePoint centerpoint = mapJ.getCenter();

        // 设定地图范围为最初的范围，目前写死.
        mapJ.setZoom(2140.0);

        // 设定地图中心为最初的中心点,目前写死.
        mapJ.setCenter(new DoublePoint(0.240811010150033,0.361497328528728));

        return mapJ;
    }


    /**
     * 返回生成的地图，图片.
     * @param mapJ
     * @param request
     * @return
     * @throws Exception
     */
    public static MapXtremeImageRenderer responseImg(MapJ mapJ,HttpServletRequest request) throws Exception{
        return null;
    }

}
