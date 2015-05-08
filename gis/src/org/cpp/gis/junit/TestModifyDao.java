package org.cpp.gis.junit;

import org.cpp.gis.dao.impl.ModifyDaoImpl;
import org.cpp.gis.entities.Modify;
import org.cpp.gis.factory.DaoFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * ModifyDaoImpl 测试单元.
 * Created by Rose on 2015/5/8.
 */
public class TestModifyDao {

    private ModifyDaoImpl modifyDao = null;

    @Before
    public void init() {
        modifyDao = DaoFactory.getInstance().createDao("org.cpp.gis.dao.impl.ModifyDaoImpl");
    }

    @Test
    public void testCreate() {
        Modify modify = new Modify();
        modify.setName("哈哈");
        modify.setCollege("Infomation college");
        modify.setDescription("none");
        modify.setIdentification(1);
        modify.setPeople("Rose");
        modify.setPhone("13132165487");
        modify.setFeatuere_id(1);

        modifyDao.create(modify);
    }
}
