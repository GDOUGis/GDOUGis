package org.cpp.gis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 数字化地图处理器.
 * Created by Rose on 2015/4/27.
 */
@Controller
public class MapHandler extends BaseHandler {

    @RequestMapping("/test")
    public String test() {
        return SUCCESS;
    }


}
