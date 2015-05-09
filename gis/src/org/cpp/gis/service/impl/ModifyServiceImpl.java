package org.cpp.gis.service.impl;

import org.cpp.gis.dao.FeaturePointDao;
import org.cpp.gis.dao.impl.ModifyDaoImpl;
import org.cpp.gis.entities.FeaturePoint;
import org.cpp.gis.entities.Modify;
import org.cpp.gis.factory.DaoFactory;
import org.cpp.gis.utils.Page;
import org.cpp.gis.utils.PageUtil;

import java.util.List;

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

    /**
     * 判断修改名是否已经存在，存在则返回true，否则返回false.
     * @param modifyName
     * @return
     */
    public boolean isModifyNameExsit(String modifyName) {

        Modify modify = modifyDao.getByName(modifyName);
        if(modify != null) {
            return true;
        } else{
        return false;
        }
    }


    /**
     * 获取Modify实体信息.
     * @param modifyName
     * @return
     */
    public Modify getModifyByName(String modifyName) {
        return modifyDao.getByName(modifyName);
    }

    /**
     * 更新修改名次数
     * @param i
     */
    public void updateModifyTimes(int i) {
        if(i > 0) {
            modifyDao.updateTimes(i);
        }
    }

    public List<Modify> getModifyPageData(int pageNum, int pageSize) {
        List<Modify> list = null;
        try {
            int totalRecord = modifyDao.getTotalRecord();
            Page page = PageUtil.createPage(pageSize, totalRecord,pageNum);
            list = modifyDao.getAllModifyPageData(page.getBeginIndex(), pageSize);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}