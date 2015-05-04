package org.cpp.gis.junit;

import org.cpp.gis.dao.FeaturePointDao;
import org.cpp.gis.entities.FeaturePoint;
import org.cpp.gis.factory.DaoFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * FeaturePointDao测试类.
 * Created by Rose on 2015/5/3.
 */
public class TestFeaturePointDao {

    FeaturePointDao fpDao = null;
    @Before
    public void init() {
        fpDao = DaoFactory.getInstance().createDao("org.cpp.gis.dao.impl.FeaturePointDaoImpl");
    }

    @Test
    public void testCreate() {
        FeaturePoint featurePoint = new FeaturePoint();
        featurePoint.setId(1);
        featurePoint.setName("test");
        fpDao.create(featurePoint);
    }

    @Test
    public void testReadById() {
        System.out.println(fpDao.readById(1));
    }
}
