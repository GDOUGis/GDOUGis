package org.cpp.gis.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.cpp.gis.entities.Modify;
import org.cpp.gis.utils.JDBCUtil;

/**
 * 修改名Dao实现类.
 * Created by Rose on 2015/5/7.
 */
public class ModifyDaoImpl {

    public void create(Modify modify) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "INSERT INTO tb_modify(name, description, people, identification," +
                    "college, phone, feature_id) values(?,?,?,?,?,?,?)";
            Object[] params = {modify.getName(), modify.getDescription(), modify.getPeople(),
                modify.getIdentification(), modify.getCollege(), modify.getPhone(), modify.getFeatuere_id()};
            qr.update(sql,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
