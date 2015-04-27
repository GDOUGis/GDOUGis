package org.cpp.gis.utils;

import com.mapinfo.dp.annotation.AnnotationDataProviderHelper;
import com.mapinfo.dp.annotation.AnnotationTableDescHelper;
import com.mapinfo.dp.util.LocalDataProviderRef;
import com.mapinfo.mapj.MapJ;
import com.mapinfo.util.DoublePoint;

import java.util.Map;

/**
 * Created by Seven on 2015/4/27.
 */
public class MapUtils {

    /**
     * 加载地图.
     * @param m_mapPath
     * @param m_fileToLoad
     * @return
     */
    public static MapJ loadMapJ(String m_mapPath,String m_fileToLoad) throws Exception{

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
     * @param oldx
     * @param oldy
     * @param oldzoom
     * @return
     * @throws Exception
     */
    public static MapJ initMap(MapJ mapJ,Double oldx, Double oldy, Double oldzoom) throws Exception{

        //根据坐标生成中心点
        DoublePoint doublePoint = new DoublePoint(oldx.doubleValue(),oldy.doubleValue());

        mapJ.setCenter(doublePoint);
        mapJ.setZoom(oldzoom);

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




}
