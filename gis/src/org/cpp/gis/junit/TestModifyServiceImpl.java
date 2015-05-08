package org.cpp.gis.junit;

import org.cpp.gis.service.impl.ModifyServiceImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Rose on 2015/5/8.
 */
public class TestModifyServiceImpl {

    private ModifyServiceImpl modifyService = null;

    @Before
    public void init() {
        modifyService = new ModifyServiceImpl();
    }

    @Test
    public void testAddModify() {
        modifyService.addModify("A", null, "Rose", "se", "1831234578","1",1, "钟海楼");
    }
}
