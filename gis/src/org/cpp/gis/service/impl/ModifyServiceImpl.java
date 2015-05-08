package org.cpp.gis.service.impl;

import org.cpp.gis.dao.FeaturePointDao;
import org.cpp.gis.dao.impl.ModifyDaoImpl;
import org.cpp.gis.entities.FeaturePoint;
import org.cpp.gis.entities.Modify;
import org.cpp.gis.factory.DaoFactory;

/**
 * 修改信息Service实现类》
 * Created by Rose on 2015/5/8.
 */
public class ModifyServiceImpl {
    private ModifyDaoImpl modifyDao = DaoFactory.getInstance().createDao("org.cpp.gis.dao.impl.ModifyDaoImpl");
    private FeaturePointDao featurePointDao = DaoFactory.getInstance().createDao("org.cpp.gis.dao.impl.FeaturePointDaoImpl");

    public void addModify(String modifyName, String modifyDesc, String modifyPeople, String modifyCollege,
                           String modifyPhone,String modifyIdentification, int fpId, String currentName) {
        try {
            FeaturePoint featurePoint = featurePointDao.readByName(currentName);
            if(null != featurePoint) {
                fpId = featurePoint.getId();
            }

            Modify modify = new Modify();
            modify.setName(modifyName);
            modify.setCollege(modifyCollege);
            modify.setDescription(modifyDesc);
            modify.setIdentification(Integer.parseInt(modifyIdentification));
            modify.setPeople(modifyPeople);
            modify.setPhone(modifyPhone);
            modify.setFeatuere_id(fpId);

            modifyDao.create(modify);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
