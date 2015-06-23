package org.cpp.gis.servlet;

import com.mapinfo.dp.*;
import com.mapinfo.dp.annotation.AnnotationDataProviderHelper;
import com.mapinfo.dp.annotation.AnnotationTableDescHelper;
import com.mapinfo.dp.util.LocalDataProviderRef;
import com.mapinfo.dp.util.RewindableFeatureSet;
import com.mapinfo.graphics.Rendition;
import com.mapinfo.graphics.RenditionImpl;
import com.mapinfo.mapj.*;
import com.mapinfo.mapxtreme.client.MapXtremeImageRenderer;
import com.mapinfo.theme.SelectionTheme;
import com.mapinfo.util.DoublePoint;
import com.mapinfo.util.DoubleRect;
import com.mapinfo.xmlprot.mxtj.ImageRequestComposer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.cpp.gis.entities.FeaturePoint;
import org.cpp.gis.entities.HighLightResult;
import org.cpp.gis.service.FeaturePointService;
import org.cpp.gis.service.impl.FeaturePointServiceImpl;
import org.cpp.gis.service.impl.ModifyServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.List;

public class MapServlet extends HttpServlet {

    /**解决异常.
     * Error: Could not load mediaLib accelerator wrapper classes. Continuing in pure Java mode.
     * Occurs in: com.sun.media.jai.mlib.MediaLibAccessor
     * com.sun.media.jai.mlib.MediaLibLoadException
     */
    static {
        System.setProperty("com.sun.media.jai.disableMediaLib", "true");
    }

    private FeaturePointService fpService = new FeaturePointServiceImpl();
    private ModifyServiceImpl modifyService = new ModifyServiceImpl();


    // 包含地图文件的路径
    private String m_mapPath = "";

    // 地图定义文件的完整路径
    private String m_fileToLoad = "";

    private boolean errflag = false;

    private String errmessage = null;

    // mapxtremeservlet地图服务器url

    private String mapxtremeurl = "http://localhost:8080/mapxtreme";

    private String imgtype = "jpeg";

    private int imgsizex = 960;

    private int imgsizey = 620;

    private int smallimgsizex = 300;

    private int smallimgsizey = 200;

    private Color imgbgcolor = Color.white;

    static DoublePoint resetpoint = null;

    static double resetzoom = 0.0D;

    private boolean hightLight = false;


    /**
     * 初始化
     * @param config
     * @throws ServletException
     */
    public void init(ServletConfig config) throws ServletException {

        super.init(config);

        String strParam = "";
        URL url = this.getClass().getResource("/../..");
        strParam = getInitParameter("mapPath");
        if (strParam != null) {
            //也可以直接在web.xml 配置绝对路径 那么下面这一句就可以不要了
            strParam = url.getPath().substring(1).replaceAll("%20", " ") + strParam;

            m_mapPath = strParam;
        }
        strParam = getInitParameter("fileToLoad");
        if (strParam != null) {
            //也可以直接在web.xml 配置绝对路径 那么下面这一句就可以不要了
            strParam = url.getPath().substring(1).replaceAll("%20", " ") + strParam;
            m_fileToLoad = strParam;
        }

        strParam = getInitParameter("mapxtremeURL");
        if (strParam != null && strParam.length() > 0) {
            mapxtremeurl = strParam;
        }

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (this.errflag == true) {
            PrintWriter out = response.getWriter();
            response.setContentType("text/html; charset=UTF-8");
            out.print(new String(this.errmessage.getBytes("UTF-8"),
                    "iso-8859-1"));
            out.close();
        }
        MapJ mymap = null;
        MapJ boundmap = null;
        String rqutype = request.getParameter("rqutype");
        if ((rqutype != null) && (rqutype.equals("initmap"))) {
            mymap = initmap(request);
            responseimg(mymap, request,response);
        } else if ((rqutype != null) && (rqutype.equals("chgmapview"))) {
            mymap = initmap(request);
            chgmapview(mymap, request);
            responseimg(mymap,request, response);
        } else if ((rqutype != null) && (rqutype.equals("panmap"))) {
            mymap = initmap(request);
            panmap(mymap, request);
            responseimg(mymap,request, response);
        } else if ((rqutype != null) && (rqutype.equals("resetmap"))) {
            mymap = initmap(request);
            resetmap(mymap, request);
            //去除高亮
            request.getSession().removeAttribute("layerName");
            request.getSession().removeAttribute("clickSecond");
            responseimg(mymap, request, response);
        } else if ((rqutype != null) && (rqutype.equals("centerpoint"))) {
            mymap = initmap(request);
            responsetext(mymap, request, response, "centerpoint");
        } else if ((rqutype != null) && (rqutype.equals("zoom"))) {
            mymap = initmap(request);
            responsetext(mymap, request, response, "zoom");
        } else if ((rqutype != null) && (rqutype.equals("boundmap"))) {
            mymap = initmap(request);
            boundmap = initboundmap(request);
            responsebound(mymap, boundmap, response);
        } else if ((rqutype != null) && (rqutype.equals("smallpanmap"))) {
            mymap = initmap(request);
            boundmap = initboundmap(request);
            resetbybound(mymap, boundmap, request);
            responseimg(mymap,request,  response);
        } else if ((rqutype != null) && (rqutype.equals("querymap"))) {
            String layernames = request.getParameter("layernames");
            String selectnames = request.getParameter("selectnames");

            //解码
            layernames = URLDecoder.decode(layernames, "UTF-8");
            selectnames = URLDecoder.decode(selectnames, "UTF-8");

            mymap = initmap(request);

            try {
                selectF(mymap, layernames, selectnames,request,  response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ((rqutype != null) && (rqutype.equals("fuzzyQuery"))) {
            //String layernames = request.getParameter("layernames");
            String selectnames = request.getParameter("selectnames");

            //解码
            //layernames= URLDecoder.decode(layernames,"UTF-8");
            selectnames = URLDecoder.decode(selectnames, "UTF-8");
            mymap = initmap(request);

            try {
                fuzzyQuery(mymap, selectnames, response);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (rqutype != null && rqutype.equals("changeLayer")) {

            //获取参数.
            String layerNames = request.getParameter("layernames");
            layerNames = URLDecoder.decode(layerNames, "UTF-8");
            String[] showLayers = layerNames.split(",");

            mymap = initmap(request);
            chgmaplayer(mymap, showLayers);
            responseimg(mymap, request, response);

        } else if (rqutype != null && rqutype.equals("findByName")) {

            //获取参数.
            String queryName = request.getParameter("queryName");
            queryName = URLDecoder.decode(queryName, "UTF-8");

            mymap = initmap(request);
            findByName(mymap, queryName,request, response);

        } else if ((rqutype != null) && (rqutype.equals("loadFeature"))) {
            mymap = initmap(request);
            try {
                loadFeature_new(response, request, mymap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ((rqutype != null) && (rqutype.equals("showFeatureDetail"))) {
            String id = request.getParameter("id");
            showFeatureDetail(id, response);
        } else if ((rqutype != null) && (rqutype.equals("getAliasById"))) {
            getAliasById(request, response);
        } else if ((rqutype != null) && (rqutype.equals("updateAlias"))) {
            updateAlias(request, response);
        } else if ((rqutype != null) && (rqutype.equals("getAliasByName"))) {
            getAliasByName(request, response);
        } else if ((rqutype != null) && (rqutype.equals("addModifyName"))) {
            addModifyName(request, response);
        } else if ((rqutype != null) && (rqutype.equals("hightLight"))) {
            //获取参数.
            String queryName = request.getParameter("queryName");
            queryName = URLDecoder.decode(queryName, "UTF-8");


            mymap = initmap(request);

            hightLight = true;


            findByName(mymap, queryName,request, response);

        } else if ((rqutype != null) && (rqutype.equals("findByPoint"))) {

            mymap = initmap(request);

            findBypoint(mymap, request, response);
        }else if((rqutype != null) && (rqutype.equals("isHightLight"))){

            isHightLight(request,response);
        }
    }




    /**
     * *@加载地图
     */
    public MapJ initMapJ() throws Exception {
        MapJ myMap = new MapJ();

        try {
            //加载.gst 格式的地图文件
            if (m_fileToLoad.endsWith(".gst")) {
                myMap.loadGeoset(m_fileToLoad, m_mapPath, null);
//				myMap.loadGeoset(m_fileToLoad, m_mapPath, mapxtremeurl);
            } else { //加载.mdf 格式的地图文件
                myMap.loadMapDefinition(m_fileToLoad);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return myMap;
    }

    /**
     * @初始化地图
     */
    private MapJ initmap(HttpServletRequest request) {
        MapJ mymap = null;
        mymap = (MapJ) request.getSession().getAttribute("mapj");
        if (mymap == null) {
            try {
                mymap = initMapJ();

                // 加载地图
                if ((request.getParameter("oldx") != null)
                        && (request.getParameter("oldy") != null)) {
                    Double oldx = new Double(request.getParameter("oldx"));
                    Double oldy = new Double(request.getParameter("oldy"));
                    DoublePoint mappoint = new DoublePoint(oldx.doubleValue(),
                            oldy.doubleValue());
                    Double oldzoom = new Double(request.getParameter("oldzoom"));
                    mymap.setCenter(mappoint);
                    mymap.setZoom(oldzoom.doubleValue());
                }

                mymap.setZoom(2140);
                //将地图放到session里面
                request.getSession().setAttribute("mapj", mymap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mymap;
    }

    /**
     * @鹰眼地图初始化
     */
    private MapJ initboundmap(HttpServletRequest request) {
        MapJ boundmap = null;
        boundmap = (MapJ) request.getSession().getAttribute("boundmap");
        if (boundmap == null) {
            try {
                boundmap = initMapJ();
                boundmap.setZoom(2140 * 1.5D);//针对于本地图写死数据
                // 加载地图
                /**
                 * @添加图层的步骤
                 * @1--创建TableDescHelper
                 * @2--创建DataProviderHelper
                 * @3--创建DataProviderRef
                 * @4--创建layers.insert
                 */
                AnnotationTableDescHelper antable = new AnnotationTableDescHelper(
                        "anlayer");
                AnnotationDataProviderHelper andata = new AnnotationDataProviderHelper();
                LocalDataProviderRef andataref = new LocalDataProviderRef(
                        andata);
                boundmap.getLayers().insert(andataref, antable, 0, "anlayer");
                request.getSession().setAttribute("boundmap", boundmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return boundmap;
    }

    /**
     * @应该与缩放有关
     */
    private void chgmapview(MapJ mymap, HttpServletRequest request) {
        try {
            double oldzoom = mymap.getZoom();
            // 原来的地图范围
            Double newzoomopr = new Double(request.getParameter("newzoom"));
            double newzoom = oldzoom * newzoomopr.doubleValue();
            // 新的地图范围
            Double centerx = new Double(request.getParameter("centerx"));
            Double centery = new Double(request.getParameter("centery"));
            // 取得鼠标坐标
            DoublePoint screenpoint = new DoublePoint(centerx.doubleValue(),
                    centery.doubleValue());
            DoublePoint mappoint = mymap.transformScreenToNumeric(screenpoint);
            mymap.setCenter(mappoint);
            mymap.setZoom(newzoom);
            // 设定点和地图的范围
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @地图移动方法
     */
    private void panmap(MapJ mymap, HttpServletRequest request) {
        try {
            Double centerx = new Double(request.getParameter("centerx"));
            // 从前台取得鼠标坐标 X ；
            Double centery = new Double(request.getParameter("centery"));
            // 从前台取得鼠标坐标 Y ；
            DoublePoint screenpoint = new DoublePoint(centerx.doubleValue(),
                    centery.doubleValue());
            // 创建一个新的点
            DoublePoint mappoint = mymap.transformScreenToNumeric(screenpoint);
            // 转换坐标1
            mymap.setCenter(mappoint);
            // 设定中心
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @地图还原方法
     */
    private void resetmap(MapJ mymap, HttpServletRequest request) {
        try {
            mymap.setZoom(2140.0);
            // 设定地图范围为最初的范围
            resetpoint = new DoublePoint(Double.parseDouble(request.getSession().getAttribute("oldx").toString()), Double.parseDouble(request.getSession().getAttribute("oldy").toString()));
            mymap.setCenter(resetpoint);
            // 设定地图中心为最初的中心点
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @地图渲染
     */
    private void responseimg(MapJ mymap, HttpServletRequest request,HttpServletResponse response) {
        ServletOutputStream sout = null;
        mymap.setDeviceBounds(new DoubleRect(0.0D, 0.0D, this.imgsizex,
                this.imgsizey));
        // 设定地图的大小
        response.setContentType("image/jpeg");
        try {
            //显示名字层
            Layer nameLayer = mymap.getLayers().getLayer("名字");
            if(nameLayer!=null){
                if(2140.0/mymap.getZoom()>2){
                    nameLayer.setVisible(true);
                }else{
                    nameLayer.setVisible(false);
                }
            }

            sout = response.getOutputStream();

            if(request.getSession().getAttribute("layerName")!=null){
                String layerName = (String)request.getSession().getAttribute("layerName");
                Layer toHightLightL = mymap.getLayers().getLayer(layerName);

                // 创建一个 SelectionTheme
                SelectionTheme selTheme = new SelectionTheme("LocateFeature");
                // 创建一个Selection对象并且把选择的图元加入
                Selection sel = new Selection();

                List<Feature> list = (List<Feature>)request.getSession().getAttribute("ftrList");

                //sel.add(selFtr);
                for(Feature feature : list){
                    sel.add(feature);
                }
                // 把Selection对象加入到SelectionTheme
                selTheme.setSelection(sel);

                // 设置SelectionTheme的显示渲染的样式
                Rendition rend = RenditionImpl.getDefaultRendition();

                rend.setValue(Rendition.FILL, Color.red);
                rend.setValue(Rendition.STROKE,Color.red);//划线
                selTheme.setRendition(rend);
                // 添加SelectionTheme到指定layer的theme列表中
                toHightLightL.getThemeList().add(selTheme);
            }

            ImageRequestComposer irc = ImageRequestComposer.create(mymap,
                    65535, Color.white, "image/jpeg");
            // 创建输出地图的属性---显示的像素，背景颜色，地图格式
            MapXtremeImageRenderer renderer = new MapXtremeImageRenderer(
                    this.mapxtremeurl);
            // 加载mapxtreme的servlet
            renderer.render(irc);


            // 渲染
            renderer.toStream(sout);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (sout != null){
                sout.flush();
                sout.close();
            }
        } catch (Exception localException1) {
            localException1.printStackTrace();
        }
    }

    private void responsetext(MapJ mymap, HttpServletRequest request, HttpServletResponse response,
                              String flag) {
        try {
            PrintWriter out = response.getWriter();
            response.setContentType("text/html; charset=UTF-8");
            if (flag.equals("centerpoint")) {
                DoublePoint centerpoint = mymap.getCenter();
                request.getSession().setAttribute("oldx", centerpoint.x);
                request.getSession().setAttribute("oldy", centerpoint.y);
                out
                        .print(String
                                .valueOf(String
                                        .valueOf(new StringBuffer(
                                                "<body onload=\"setxy()\"><input name=centerx type=hidden value=")
                                                .append(centerpoint.x)
                                                .append(">")
                                                .append(
                                                        "<input name=centery type=hidden value=")
                                                .append(centerpoint.y)
                                                .append(">")
                                                .append("</body>")
                                                .append(
                                                        "<script language=\"JavaScript\">")
                                                .append("function setxy(){")
                                                .append(
                                                        "parent.document.all.oldx.value=document.all.centerx.value;")
                                                .append(
                                                        "parent.document.all.oldy.value=document.all.centery.value")
                                                .append("}")
                                                .append("</script>"))));
            } else {
                request.getSession().setAttribute("oldzoom", mymap.getZoom());
                out
                        .print(String
                                .valueOf(String
                                        .valueOf(new StringBuffer(
                                                "<body onload=\"setzoom()\"><input name=zoom type=hidden value=")
                                                .append(mymap.getZoom())
                                                .append(">")
                                                .append("</body>")
                                                .append(
                                                        "<script language=\"JavaScript\">")
                                                .append("function setzoom(){")
                                                .append(
                                                        "parent.document.all.oldzoom.value=document.all.zoom.value;")
                                                .append("}")
                                                .append("</script>"))));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @鹰眼功能
     */

    private void responsebound(MapJ mymap, MapJ boundmap,
                               HttpServletResponse response) {
        ServletOutputStream sout = null;
        try {
            Layer anlayer = boundmap.getLayers().elementAt(0);
            // 取得一个图层 （鹰眼地图所有层的第一层）
            FeatureFactory ff = boundmap.getFeatureFactory();
            // 取得一个图元工厂类，可以用来创建图元---点、线、面
            PrimaryKey pk = new PrimaryKey(new Attribute(101));
            // 创建一个主键 具体是什么意思呢？？？
            Rendition rend = RenditionImpl.getDefaultRendition();
            // 创建一个样式的对象
            rend.setValue(Rendition.STROKE, Color.red);
            // 设置线的颜色为红色
            rend.setValue(Rendition.STROKE_WIDTH, 2);
            // 设置线的宽度
            rend.setValue(Rendition.FILL_OPACITY, new Float(0.0D));

            DoublePoint p1 = new DoublePoint(0.0D, 0.0D);
            DoublePoint p2 = new DoublePoint(this.imgsizex, 0.0D);
            DoublePoint p3 = new DoublePoint(this.imgsizex, this.imgsizey);
            DoublePoint p4 = new DoublePoint(0.0D, this.imgsizey);
            // 取得一个矩形的四个点
            DoublePoint mp1 = mymap.transformScreenToNumeric(p1);
            DoublePoint mp2 = mymap.transformScreenToNumeric(p2);
            DoublePoint mp3 = mymap.transformScreenToNumeric(p3);
            DoublePoint mp4 = mymap.transformScreenToNumeric(p4);
            double[] p = new double[10];
            p[0] = mp1.x;
            p[1] = mp1.y;
            p[2] = mp2.x;
            p[3] = mp2.y;
            p[4] = mp3.x;
            p[5] = mp3.y;
            p[6] = mp4.x;
            p[7] = mp4.y;
            p[8] = mp1.x;
            p[9] = mp1.y;

            Feature ft = ff.createRegion(p, rend, null, null, pk);
            /**
             * @创建一个图元（通过featurefactory 创建---region是区域的意思）
             * @第一个参数 p double[] ---thePoints
             * @第二个参数 样式
             * @第三个参数 lableRend
             * @第四个参数 attrs
             * @第五个参数 主键值
             */
            PrimaryKey[] spk = {new PrimaryKey(new Attribute(101))};
            Vector col = new Vector();
            FeatureSet ftset = anlayer.searchByPrimaryKey(col, spk, null);
            if (ftset == null) {
                PrimaryKey localPrimaryKey1 = anlayer.addFeature(ft);
            } else {
                anlayer.replaceFeature(pk, ft);
            }
            boundmap.setDeviceBounds(new DoubleRect(0.0D, 0.0D,
                    this.smallimgsizex, this.smallimgsizey));
            boundmap.setDistanceUnits(mymap.getDistanceUnits());


        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType("image/jpeg");
        try {
            sout = response.getOutputStream();
            ImageRequestComposer irc = ImageRequestComposer.create(boundmap,
                    65535, Color.white, "image/jpeg");
            MapXtremeImageRenderer renderer = new MapXtremeImageRenderer(
                    this.mapxtremeurl);
            renderer.render(irc);
            renderer.toStream(sout);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (sout != null)
                sout.close();
        } catch (Exception localException1) {
        }
    }

    /**
     * @通过鹰眼移动地图
     */
    private void resetbybound(MapJ mymap, MapJ boundmap,
                              HttpServletRequest request) {
        try {
            Double centerx = new Double(request.getParameter("centerx"));
            // 取得x点
            Double centery = new Double(request.getParameter("centery"));
            // 取得Y点
            DoublePoint screenpoint = new DoublePoint(centerx.doubleValue(),
                    centery.doubleValue());
            DoublePoint mappoint = boundmap
                    .transformScreenToNumeric(screenpoint);
            mymap.setCenter(mappoint);
            // 主地图中点变化
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加修改名.
     *
     * @param request
     * @param response
     */
    private void addModifyName(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            /* 获取参数 */
            int fpId = Integer.parseInt(request.getParameter("fpId"));
            String currentName = URLDecoder.decode(request.getParameter("currentName"), "UTF-8");
            String modifyName = URLDecoder.decode(request.getParameter("modifyName"), "UTF-8");
            String modifyDesc = URLDecoder.decode(request.getParameter("modifyDesc"), "UTF-8");
            String modifyPeople = URLDecoder.decode(request.getParameter("modifyPeople"), "UTF-8");
            String modifyCollege = URLDecoder.decode(request.getParameter("modifyCollege"), "UTF-8");
            String modifyPhone = URLDecoder.decode(request.getParameter("modifyPhone"), "UTF-8");
            String modifyIdentification = URLDecoder.decode(request.getParameter("identification"), "UTF-8");
            response.setContentType("text/html;charset=utf-8");



            /* 校验 */


            modifyService.addModify(modifyName, modifyDesc, modifyPeople, modifyCollege, modifyPhone,
                    modifyIdentification, fpId, currentName);
            out.write("1");
        } catch (Exception e) {
            out.write("0");
            e.printStackTrace();
        } finally {
            if(out!=null){
                out.flush();
                out.close();
            }
        }
    }

    /**
     * 根据名字找备用名.
     *
     * @param request
     * @param response
     */
    private void getAliasByName(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String[] alias = null;
        try {
            alias = fpService.getAliasByName(name);
            if (alias == null) {
                return;
            }
            response.setContentType("text/html;charset=utf-8");
            JSONArray jsonArray = JSONArray.fromObject(alias);
            response.getWriter().write(jsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * (添加)更新备用名.
     *
     * @param request
     * @param response
     */
    private void updateAlias(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String alias = request.getParameter("alias");
        response.setContentType("text/html;charset=utf-8;");
        try {
            if (id != null && alias != null && "".equals(alias.trim())) {
                fpService.addAlias(id, alias);
                response.getWriter().write("1");
            } else {
                response.getWriter().write("0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据特征点ID查找别名.
     *
     * @param request
     * @param response
     */
    private void getAliasById(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String[] alias = null;
        try {
            alias = fpService.getAliasById(id);
            if (alias == null) {
                return;
            }
            response.setContentType("text/html;charset=utf-8");
            JSONArray jsonArray = JSONArray.fromObject(alias);
            response.getWriter().write(jsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 显示特征点信息.
     *
     * @param id
     */
    private void showFeatureDetail(String id, HttpServletResponse response) {
        try {
            if (!"".equals(id) && id != null) {
                FeaturePoint fq = new FeaturePoint();
                fq = fpService.getById(Integer.parseInt(id));
                JSONObject jsonObject = JSONObject.fromObject(fq);
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().write(jsonObject.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @指定查找一个图元
     * @第一个参数 mymap ---Mapj
     * @第二个参数 layernames 图层名称
     * @第三个参数 selectnames 图元名称
     * @第四个参数 Response
     */
    private void selectF(MapJ mymap, String layernames, String selectnames,HttpServletRequest request,
                         HttpServletResponse res) throws Exception {

        if (mymap == null) {
            mymap = initMapJ();
        }

        // 以下是进行图元的查找和渲染
        Layer m_Layer = mymap.getLayers().getLayer(layernames);

        if (m_Layer == null) {
            responseimg(mymap,request,  res);
            return;
        }

        // 删除以上操作已经添加的theme列表
        m_Layer.getThemeList().removeAll(true);

        List columnNames = new ArrayList();
        Feature ftr;

        TableInfo tabInfo = m_Layer.getTableInfo();
        // fill vector with Column names
        for (int i = 0; i < tabInfo.getColumnCount(); i++) {
            columnNames.add(tabInfo.getColumnName(i));
        }
        // Perform a search to get the Features(records)from the layer
        RewindableFeatureSet rFtrSet;
        rFtrSet = new RewindableFeatureSet(m_Layer.searchAll(columnNames, null));

        // FeatureSet fs = m_Layer.searchAll(columnNames,
        // QueryParams.ALL_PARAMS);
        ftr = rFtrSet.getNextFeature();

        while (ftr != null) {
            if (ftr.getAttribute(0).toString().equals(selectnames)) {
                // 定位点
                if (ftr.getGeometry().getType() == Geometry.TYPE_POINT) {
                    double newZoomValue;
                    double currentZoom = mymap.getZoom();
                    if (m_Layer.isZoomLayer()
                            && (currentZoom > m_Layer.getZoomMax() || currentZoom < m_Layer
                            .getZoomMin())) {
                        newZoomValue = m_Layer.getZoomMax() / 2;
                        mymap.setZoom(newZoomValue);
                    }

                    mymap.setCenter(ftr.getGeometry().getBounds().center());
                }
                // 定位线、面
                if (ftr.getGeometry().getType() == Geometry.TYPE_LINE
                        || ftr.getGeometry().getType() == Geometry.TYPE_REGION) {

                    if (ftr.getGeometry().getBounds().width() > 0
                            && ftr.getGeometry().getBounds().height() > 0) {
                        mymap.setBounds(ftr.getGeometry().getBounds());
                        mymap.setZoom(mymap.getZoom() * 1.1);
                    }
                }
                break;
            }
            ftr = rFtrSet.getNextFeature();
        }
        rFtrSet.rewind();


        // 高亮显示

        // 创建一个 SelectionTheme
        SelectionTheme selTheme = new SelectionTheme("LocateFeature");
        // 创建一个Selection对象并且把选择的图元加入
        Selection sel = new Selection();
        sel.add(ftr);

        // 把Selection对象加入到SelectionTheme
        selTheme.setSelection(sel);

        // 设置SelectionTheme的显示渲染的样式
        Rendition rend = RenditionImpl.getDefaultRendition();
        rend.setValue(Rendition.FILL, Color.red);
        selTheme.setRendition(rend);

        // 添加SelectionTheme到指定layer的theme列表中
        m_Layer.getThemeList().add(selTheme);
        // m_Layer.getThemeList().insert(selTheme, 0);

        responseimg(mymap,request,  res);

    }

    /**
     * 模糊查询.
     *
     * @param mymap
     * @param selectnames
     * @param response
     */
    private void fuzzyQuery(MapJ mymap, String selectnames, HttpServletResponse response) throws Exception {

        List<Feature> features = new ArrayList<Feature>();
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "application/javascript");
        PrintWriter out = response.getWriter();

        try {
            if (mymap == null) {
                mymap = initMapJ();
            }

            // 以下是进行图元的查找和渲染

            //获取所有的图层
            Layers layers = mymap.getLayers();


            //对每个图层进行搜索.
            Layer m_Layer = null;
            for (int i = 0; i < layers.size(); i++) {

                //获得layer
                m_Layer = layers.elementAt(i);
                //如果该图层用户没有显示，那么不提供给搜索.
                if (!m_Layer.isVisible()) {
                    continue;
                }
                //以下图层不用搜索
                if (m_Layer.getName().equals("绿地")) {
                    continue;
                }
                if (m_Layer.getName().equals("铺地层")) {
                    continue;
                }
                if (m_Layer.getName().equals("名字")) {
                    continue;
                }
                if (m_Layer.getName().equals("无信息1")) {
                    continue;
                }
                if (m_Layer.getName().equals("无标题2")) {
                    continue;
                }
                // 删除以上操作已经添加的theme列表
                m_Layer.getThemeList().removeAll(true);
                List columnNames = new ArrayList();
                Feature ftr = null;
                TableInfo tabInfo = m_Layer.getTableInfo();
                // 获得ColumnName
                for (int j = 0; j < tabInfo.getColumnCount(); j++) {
                    columnNames.add(tabInfo.getColumnName(j));
                }

                // Perform a search to get the Features(records)from the layer
                //获得当前图层的所有图元
                RewindableFeatureSet rFtrSet;
                rFtrSet = new RewindableFeatureSet(m_Layer.searchAll(columnNames, null));

                // FeatureSet fs = m_Layer.searchAll(columnNames,
                // QueryParams.ALL_PARAMS);

                //对每个图元进行遍历

                ftr = rFtrSet.getNextFeature();

                while (ftr != null) {
                    //利用contain模拟模糊搜索.
                    if (ftr.getAttribute(0).toString().contains(selectnames)) {
                        features.add(ftr);
                    }
                    ftr = rFtrSet.getNextFeature();
                }
                rFtrSet.rewind();
            }

            //获取图元的名称
            List<String> list = new ArrayList<String>();
            JSONArray jsonArray = new JSONArray();
            Set<String> set = new HashSet<String>();
            for (Feature f : features) {
                set.add(f.getAttribute(0).toString());
            }
            for(String s : set){
                jsonArray.add(s);
            }

            Map<String, Object> topMap = new HashMap<String, Object>();
            topMap.put("result", jsonArray);
            JSONObject jsonObject = JSONObject.fromObject(topMap);
            out.write(jsonObject.toString());

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            out.flush();
            out.close();
        }
    }

    /**
     * 修改layer的显示.
     *
     * @param mymap
     * @param showLayers
     */
    private void chgmaplayer(MapJ mymap, String[] showLayers) {

        for (int i = 0; i < mymap.getLayers().size(); i++) {
            Layer layer = mymap.getLayers().elementAt(i);
            boolean flag = false;
            for (String showLayer : showLayers) {
                if (showLayer.equals(layer.getName())) {
                    flag = true;
                }
            }
            layer.setVisible(flag);
        }
    }

    /**
     * 根据名称查找建筑物.
     *
     * @param mymap
     * @param queryName
     * @param response
     */
    private void findByName(MapJ mymap, String queryName, HttpServletRequest request,HttpServletResponse response) {
        try {

            if (mymap == null) {
                mymap = initMapJ();
            }

            Double oldzoom = mymap.getZoom();
            DoublePoint oldCenter = mymap.getCenter();

            Feature ftr = null;
            Feature selFtr = null;
            //获取所有的图层
            Layers layers = mymap.getLayers();

            HighLightResult result = new HighLightResult();

            //对每个图层进行搜索.
            Layer m_Layer = null;
            for (int i = 0; i < layers.size(); i++) {

                m_Layer = layers.elementAt(i);

                //如果该图层用户没有显示，那么不提供给搜索.
                if (!m_Layer.isVisible()) {
                    continue;
                }
                //以下图层不用搜索
                if (m_Layer.getName().equals("绿地")) {
                    continue;
                }
                if (m_Layer.getName().equals("名字")) {
                    continue;
                }
                if (m_Layer.getName().equals("无标题2")) {
                    continue;
                }


                // 删除以上操作已经添加的theme列表
                m_Layer.getThemeList().removeAll(true);

                List columnNames = new ArrayList();

                TableInfo tabInfo = m_Layer.getTableInfo();
                // 获得ColumnName
                for (int j = 0; j < tabInfo.getColumnCount(); j++) {
                    columnNames.add(tabInfo.getColumnName(j));
                }

                // Perform a search to get the Features(records)from the layer
                //获得当前图层的所有图元
                RewindableFeatureSet rFtrSet;
                rFtrSet = new RewindableFeatureSet(m_Layer.searchAll(columnNames, null));

                // FeatureSet fs = m_Layer.searchAll(columnNames,
                // QueryParams.ALL_PARAMS);

                //对每个图元进行遍历

                ftr = rFtrSet.getNextFeature();

                while (ftr != null) {
                    if (ftr.getAttribute(0).toString().equals(queryName)) {
                        selFtr = ftr;//找到之后.
                        result.setFeature(selFtr);
                        result.setLayerName(m_Layer.getName());
                        // 定位点
                        if (selFtr.getGeometry().getType() == Geometry.TYPE_POINT) {
                            double newZoomValue;
                            double currentZoom = mymap.getZoom();
                            if (m_Layer.isZoomLayer()
                                    && (currentZoom > m_Layer.getZoomMax() || currentZoom < m_Layer
                                    .getZoomMin())) {
                                newZoomValue = m_Layer.getZoomMax() / 2;
                                mymap.setZoom(newZoomValue);
                            }

                            mymap.setCenter(selFtr.getGeometry().getBounds().center());
                        }
                        // 定位线、面
                        if (selFtr.getGeometry().getType() == Geometry.TYPE_LINE
                                || selFtr.getGeometry().getType() == Geometry.TYPE_REGION) {

                            if (selFtr.getGeometry().getBounds().width() > 0
                                    && selFtr.getGeometry().getBounds().height() > 0) {
                                mymap.setBounds(ftr.getGeometry().getBounds());
                                mymap.setZoom(mymap.getZoom() * 1.1);
                            }
                        }
                        break;
                    }
                    ftr = rFtrSet.getNextFeature();
                }
                if (selFtr != null) {
                    break;
                }
                rFtrSet.rewind();
            }


            //控制zoom
            mymap.setZoom(2140 / 4);

            if (hightLight) {
                mymap.setZoom(oldzoom);
                mymap.setCenter(oldCenter);
                hightLight = false;
            }

            // 找到相似的
            List<Feature> list = findSameNameFtr(mymap,selFtr);

            //将信息保存到session中
            System.out.println(result.getLayerName()+"-------------->"+result.getFeature().getAttribute(0).toString()+",一共有："+list.size());
            request.getSession().setAttribute("ftrList",list);
            request.getSession().setAttribute("layerName",result.getLayerName());
            request.getSession().setAttribute("ftrName",result.getFeature().getAttribute(0).toString());

            responseimg(mymap, request, response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



    /**
     * 加载特征点
     */
    private void loadFeature_new(HttpServletResponse response, HttpServletRequest request, MapJ mapJ) throws Exception {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        int id = 0;//给id赋值.
        try {
            if (mapJ == null) {
                mapJ = initMapJ();
            }

            // 以下是进行图元的查找和渲染

            //获取所有的图层
            Layers layers = mapJ.getLayers();

            //对每个图层进行搜索.
            Layer m_Layer = null;


            List<FeaturePoint> list = new ArrayList<FeaturePoint>();

            for (int i = 0; i < layers.size(); i++) {
                //获得layer
                m_Layer = layers.elementAt(i);

                //如果该图层用户没有显示，那么不提供给搜索.
                if (!m_Layer.isVisible()) {
                    continue;
                }
                if (m_Layer.getName().equals("绿地")) {
                    continue;
                }
                if (m_Layer.getName().equals("铺地层")) {
                    continue;
                }
                if (m_Layer.getName().equals("名字")) {
                    continue;
                }
                if (m_Layer.getName().equals("无信息1")) {
                    continue;
                }
                if (m_Layer.getName().equals("无标题2")) {
                    continue;
                }
                // 删除以上操作已经添加的theme列表
                m_Layer.getThemeList().removeAll(true);
                List columnNames = new ArrayList();
                Feature ftr = null;
                TableInfo tabInfo = m_Layer.getTableInfo();
                // 获得ColumnName
                for (int j = 0; j < tabInfo.getColumnCount(); j++) {
                    columnNames.add(tabInfo.getColumnName(j));
                }
                // Perform a search to get the Features(records)from the layer
                //获得当前图层的所有图元
                RewindableFeatureSet rFtrSet;
                rFtrSet = new RewindableFeatureSet(m_Layer.searchAll(columnNames, null));

                // FeatureSet fs = m_Layer.searchAll(columnNames,
                // QueryParams.ALL_PARAMS);

                /*对每个图元进行遍历*/
                ftr = rFtrSet.getNextFeature();

                DoublePoint doublePoint = null;
                DoublePoint screenPoint = null;
                FeaturePoint fp = null;


                String name = null;             // 现用名
                String desc = null;             // 描述
                String prepareName = null;      // 拟用名.
                String prepareDescription = null;//拟改说明

                while (ftr != null) {
                    name = ftr.getAttribute(0).toString();
                    desc = ftr.getAttribute(1).toString();
                    prepareName = ftr.getAttribute(2).toString();
                    prepareDescription = ftr.getAttribute(3).toString();

                    if (!"".equals(name.trim()) || name != null) {
                        if (ftr != null && ftr.getGeometry() != null) {
                            doublePoint = ftr.getGeometry().getBounds().center();
                            // 坐标转换
                            screenPoint = mapJ.transformNumericToScreen(doublePoint);
                            // 过滤超出960 * 620 的坐标点
                            if (screenPoint.x < 960 && screenPoint.x > 0
                                    && screenPoint.y > 0 && screenPoint.y < 620) {
                                // 将名称和坐标返回
                                fp = new FeaturePoint();
                                fp.setId(fp.getId() == null ? id : fp.getId());
                                fp.setName(name);
                                fp.setDescription(desc);
                                fp.setPrepareName(prepareName);
                                fp.setX(screenPoint.x);
                                fp.setY(screenPoint.y);
                                fp.setPrepareDescription(prepareDescription);


                                //存到数据库，工程师执行，一次就够了
                                //System.out.println("inserting data start...");
                                //fpService.addFeaturePoint(fp);
                                //System.out.println("inserting data over...");

                                list.add(fp);
                                id++;
                            }

                        }
                    }

                    ftr = rFtrSet.getNextFeature();
                }
                rFtrSet.rewind();
            }

            JSONArray jsonArray = JSONArray.fromObject(list);
            double newzoom = mapJ.getZoom();
            //封装到顶层结果集
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("featurePoints", jsonArray);
            map.put("newzoom", newzoom);

            JSONObject mapObject = JSONObject.fromObject(map);
            out.write(mapObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }

    }

    /**
     * 根据特征点找建筑物.
     *
     * @param mapJ
     * @param request
     * @param response
     */
    public void findBypoint(MapJ mapJ, HttpServletRequest request, HttpServletResponse response) {

        //response.setCharacterEncoding("UTF-8");
        //response.setContentType("text/html;charset=utf-8");
        PrintWriter out = null;

        try {
            String x = request.getParameter("x");
            String y = request.getParameter("y");
            //获取屏幕的点
            DoublePoint screenP = new DoublePoint(Double.parseDouble(x), Double.parseDouble(y));
            //转为数字点
            DoublePoint numericPoint = mapJ.transformScreenToNumeric(screenP);

            if (mapJ == null) {
                mapJ = initMapJ();
            }

            // 以下是进行图元的查找和渲染

            //获取所有的图层
            Layers layers = mapJ.getLayers();

            //对每个图层进行搜索.
            Layer m_Layer = null;

            List<HighLightResult> list = new ArrayList<HighLightResult>();

            for (int i = 0; i < layers.size(); i++) {
                //获得layer
                m_Layer = layers.elementAt(i);
                // 删除以上操作已经添加的theme列表
                m_Layer.getThemeList().removeAll(true);

                //对每个图元进行遍历

                //如果该图层用户没有显示，那么不提供给搜索.
                if (!m_Layer.isVisible()) {
                    continue;
                }
                if (m_Layer.getName().equals("绿地")) {
                    continue;
                }
                if (m_Layer.getName().equals("铺地层")) {
                    continue;
                }
                if (m_Layer.getName().equals("名字")) {
                    continue;
                }
                if (m_Layer.getName().equals("无信息1")) {
                    continue;
                }
                if (m_Layer.getName().equals("无标题2")) {
                    continue;
                }


                List columnNames = new ArrayList();

                Feature ftr = null;
                TableInfo tabInfo = m_Layer.getTableInfo();
                // 获得ColumnName
                for (int j = 0; j < tabInfo.getColumnCount(); j++) {
                    columnNames.add(tabInfo.getColumnName(j));
                }
                //获得当前图层的所有图元
                RewindableFeatureSet rFtrSet;
                rFtrSet = new RewindableFeatureSet(m_Layer.searchAll(columnNames, null));


                /*对每个图元进行遍历*/
                ftr = rFtrSet.getNextFeature();

                while (ftr != null) {

                    if (ftr != null && ftr.getGeometry() != null) {
                        DoubleRect ftrRect = ftr.getGeometry().getBounds();
                        if (ftrRect.contains(numericPoint)) {
                            HighLightResult result = new HighLightResult();
                            result.setLayerName(m_Layer.getName());
                            result.setFeature(ftr);
                            list.add(result);
                        }
                    }
                    ftr = rFtrSet.getNextFeature();
                }
                rFtrSet.rewind();
            }
            if (list.isEmpty()) {
                responseimg(mapJ,request,  response);
                return;
            }

            HighLightResult result = findNearest(list, numericPoint);

            List<Feature> resultList = findSameNameFtr(mapJ,result.getFeature());


            /**
             * 用于高亮之后判断是否上次也高亮.
             */
            String ftrName = (String)request.getSession().getAttribute("ftrName");
            if(ftrName!=null){
                if(result.getFeature().getAttribute(0).toString().equals(ftrName)){
                    request.getSession().setAttribute("clickSecond","true");
                }else{
                    request.getSession().setAttribute("clickSecond","false");
                }
            }

            //将信息保存到session中
            request.getSession().setAttribute("ftrList",resultList);
            request.getSession().setAttribute("layerName",result.getLayerName());
            request.getSession().setAttribute("ftrName",result.getFeature().getAttribute(0).toString());


            responseimg(mapJ,request,  response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(out!=null){
                out.close();
            }
        }

    }

    private List<Feature> findSameNameFtr(MapJ mapJ,Feature f) throws Exception{
        //获取名字
        String name = f.getAttribute(0).toString();

        //获取所有的图层
        Layers layers = mapJ.getLayers();

        //对每个图层进行搜索.
        Layer m_Layer = null;

        List<Feature> list = new ArrayList<Feature>();

        for (int i = 0; i < layers.size(); i++) {
            //获得layer
            m_Layer = layers.elementAt(i);
            // 删除以上操作已经添加的theme列表
            m_Layer.getThemeList().removeAll(true);

            //对每个图元进行遍历

            //如果该图层用户没有显示，那么不提供给搜索.
            if (!m_Layer.isVisible()) {
                continue;
            }
            if (m_Layer.getName().equals("绿地")) {
                continue;
            }
            if (m_Layer.getName().equals("铺地层")) {
                continue;
            }
            if (m_Layer.getName().equals("名字")) {
                continue;
            }
            if (m_Layer.getName().equals("无信息1")) {
                continue;
            }
            if (m_Layer.getName().equals("无标题2")) {
                continue;
            }


            List columnNames = new ArrayList();

            Feature ftr = null;
            TableInfo tabInfo = m_Layer.getTableInfo();
            // 获得ColumnName
            for (int j = 0; j < tabInfo.getColumnCount(); j++) {
                columnNames.add(tabInfo.getColumnName(j));
            }
            //获得当前图层的所有图元
            RewindableFeatureSet rFtrSet;
            rFtrSet = new RewindableFeatureSet(m_Layer.searchAll(columnNames, null));


                /*对每个图元进行遍历*/
            ftr = rFtrSet.getNextFeature();
            while (ftr != null) {
                if (ftr != null && ftr.getGeometry() != null) {
                   if(ftr.getAttribute(0).toString().equals(name)){
                       list.add(ftr);
                   }
                }
                ftr = rFtrSet.getNextFeature();
            }
            rFtrSet.rewind();
        }

        return list;
    }

    /**
     * 寻找最接近的点
     *
     * @param list
     * @param numericPoint
     * @return
     */
    private HighLightResult findNearest(List<HighLightResult> list, DoublePoint numericPoint) throws Exception {
       /* Double x = list.get(0).getFeature().getGeometry().getBounds().center().x - numericPoint.x;
        Double y = list.get(0).getFeature().getGeometry().getBounds().center().y - numericPoint.y;
        if(x<0){x*=-1;}
        if(y<0){y*=-1; }
        HighLightResult result = list.get(0);

        for (HighLightResult h : list) {
            Double newx = h.getFeature().getGeometry().getBounds().center().x - numericPoint.x;
            Double newy = h.getFeature().getGeometry().getBounds().center().y - numericPoint.y;
            if(newx<0){newx*=-1;}
            if(newy<0){newy*=-1; }
            if (newx*newx*newy*newy <= x*x*y*y) {
                x = newx;
                y = newy;
                result = h;
            }
        }*/
        HighLightResult result = null;

        //找出与上边界最接近的点
        Double x = numericPoint.x - list.get(0).getFeature().getGeometry().getBounds().xmin;
        Double y = numericPoint.y - list.get(0).getFeature().getGeometry().getBounds().ymin;

        HighLightResult topResult = list.get(0);

        for (HighLightResult h : list) {
            Double newx = numericPoint.x - h.getFeature().getGeometry().getBounds().xmin;
            Double newy = numericPoint.y - h.getFeature().getGeometry().getBounds().ymin;
            if (newx*newx+newy*newy < x*x+y*y) {
                x = newx;
                y = newy;
                topResult = h;
            }
        }
        result = topResult;
      /*  //找出与下边界最接近的点
        x =  numericPoint.x - list.get(0).getFeature().getGeometry().getBounds().xmin;
        y =  numericPoint.y - list.get(0).getFeature().getGeometry().getBounds().ymin;

        HighLightResult bottomResult = list.get(0);

        for (HighLightResult h : list) {
            Double newx = numericPoint.x - h.getFeature().getGeometry().getBounds().xmin;
            Double newy = numericPoint.y - h.getFeature().getGeometry().getBounds().ymin;
            if (newx <= x && newy <= y) {
                x = newx;
                y = newy;
                bottomResult = h;
            }
        }*/

       /* //通过找到的两个物体中心点比较
        Double topx = topResult.getFeature().getGeometry().getBounds().center().x;
        Double topy = topResult.getFeature().getGeometry().getBounds().center().y;
        Double bottomx = bottomResult.getFeature().getGeometry().getBounds().center().x;
        Double bottomy = bottomResult.getFeature().getGeometry().getBounds().center().y;
        if(((topx-numericPoint.x)*(topx-numericPoint.x)+(topy-numericPoint.y)*(topy-numericPoint.y))<
                ((bottomx-numericPoint.x)*(bottomx-numericPoint.x)+(bottomy-numericPoint.y)*(bottomy-numericPoint.y))){
            result = topResult;
        }else {
            result = bottomResult;
        }*/

        return result;
    }

    /**
     * 判断是否已经高亮
     * @param request
     * @param response
     */
    private void isHightLight(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "application/javascript");
        try{
            out = response.getWriter();

            String clickSecond = (String)request.getSession().getAttribute("clickSecond");
            JSONObject jsonObject = null;
            if(clickSecond!=null){
                if("true".equals(clickSecond)){
                    String ftrName = (String)request.getSession().getAttribute("ftrName");
                    Map<String,Object> map = new HashMap<String, Object>();
                    map.put("ftrName",ftrName);
                    map.put("result",1);
                    jsonObject = JSONObject.fromObject(map);
                    request.getSession().setAttribute("clickSecond","false");
                }else{
                    Map<String,Object> map = new HashMap<String, Object>();
                    map.put("result",0);
                    jsonObject = JSONObject.fromObject(map);
                }
            }else{
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("result",0);
                jsonObject = JSONObject.fromObject(map);
            }
            out.write(jsonObject.toString());

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(out!=null){
                out.close();
            }
        }

    }
}

