package org.cpp.gis.junit;

import org.cpp.gis.service.FeaturePointService;
import org.cpp.gis.service.impl.FeaturePointServiceImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * 特征点Service测试类.
 * Created by Rose on 2015/5/3.
 */
public class TestFeaturePointService {
    private FeaturePointService fpService = null;

    @Before
    public void init() {
        fpService = new FeaturePointServiceImpl();
    }

    @Test
    public void testGetById() {
        System.out.println(fpService.getById(10));
    }
}
