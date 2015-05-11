package org.cpp.gis.junit;

import org.cpp.gis.dao.impl.ModifyDaoImpl;
import org.cpp.gis.entities.Modify;
import org.cpp.gis.factory.DaoFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
        modify.setFeature_id(1);

        modifyDao.create(modify);
    }

    @Test
    public void testGetModifiedPageDataGroupByFN() {
        List<Modify> list = modifyDao.getModifiedPageDataGroupByFN(1, 10);
        for(int i = 0; i<list.size(); i++) {
            System.out.println(list.get(i).getName() + " == > " + list.get(i).getTimes());
        }
    }

    @Test
    public void testGetFPModifyPD() {
        List<Modify> list = modifyDao.getFPModifyPD(1, 10);
        for(int i = 0; i<list.size(); i++) {
            System.out.println(list.get(i).getFeature_id()+":"+ list.get(i).getName() + " == > " + list.get(i).getTimes());
        }
    }

    @Test
    public void testGetAllGroupRecord() {
        System.out.println(modifyDao.getAllGroupRecord());
    }

    @Test
    public void testGetModifyDetail() {
       /* *//*List<Modify> list = modifyDao.getModifyDetail(54);*//*
        System.out.println(list.toString());*/
    }
}
