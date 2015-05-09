package org.cpp.gis.junit;

import org.cpp.gis.entities.Modify;
import org.cpp.gis.service.impl.ModifyServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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

    @Test
    public void testPage() {
        List<Modify> list = null;
        list = modifyService.getModifyPageData(1,10);
        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
        }
    }
}
