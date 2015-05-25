package org.cpp.gis.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.cpp.gis.entities.FeaturePoint;
import org.cpp.gis.entities.Modify;
import org.cpp.gis.utils.JDBCUtil;

import java.util.List;

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
                modify.getIdentification(), modify.getCollege(), modify.getPhone(), modify.getFeature_id()};
            qr.update(sql,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据名字获取修改信息.
     * @param modifyName
     * @return
     */
    public Modify getByName(String modifyName) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "SELECT * FROM tb_modify WHERE name = ?";
            Modify modify = (Modify) qr.query(sql, new BeanHandler(Modify.class));
            return modify;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新修改名次数
     * @param i
     */
    public void updateTimes(int i) {

    }

    /**
     * 获取所有
     * @return
     */
    public int getTotalRecord() {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "select count(*) from tb_modify";
            long l = (Long) qr.query(sql, new ScalarHandler());
            return (int) l;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取分页数据.
     * @param beginIndex
     * @param pageSize
     * @return
     */
    public List<Modify> getAllModifyPageData(int beginIndex, int pageSize) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "select * from tb_modify limit ?,?";
            Object[] params = {beginIndex, pageSize};

            return (List<Modify>) qr.query(sql, params, new BeanListHandler(Modify.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据特征点分组返回修改名的统计信息.
     * @param beginIndex
     * @param pageSize
     * @return
     */
    public List<Modify> getModifiedPageDataGroupByFN(int beginIndex, int pageSize) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "SELECT name, count(*) times FROM tb_modify GROUP BY feature_id LIMIT ?,?";
            Object[] params = {beginIndex, pageSize};
            return (List<Modify>) qr.query(sql, params, new BeanListHandler(Modify.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加载被修改过的特征点的分页统计数据.
     * @param beginIndex
     * @param i
     * @return
     */
    public List<Modify> getFPModifyPD(int beginIndex, int i) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "select f.id feature_id, f.name name, count(m.feature_id) times from tb_features f, tb_modify m " +
                    "where m.feature_id = f.id group by m.feature_id limit ?,?";
            Object[] params = {beginIndex, i};
            return (List<Modify>) qr.query(sql, params, new BeanListHandler(Modify.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取分组后的总记录数.
     * @return
     */
    public int getAllGroupRecord() {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "select f.id feature_id, f.name name, count(m.feature_id) times from tb_features f, tb_modify m " +
                    "where m.feature_id = f.id group by m.feature_id ";
            List<Modify> list = (List<Modify>) qr.query(sql, new BeanListHandler(Modify.class));
            return list.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取修改名详情信息.
     * @param feature_id
     * @return
     */
    public List<Modify> getModifyDetail(int feature_id,int beginIndex, int pageSize) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "SELECT * FROM tb_modify WHERE feature_id = ? LIMIT ?,?";
            Object params[] = {feature_id,beginIndex,pageSize};
            return (List<Modify>) qr.query(sql,params, new BeanListHandler(Modify.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public int getModifyTotalRecord(int feature_id){
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "SELECT count(*) FROM tb_modify WHERE feature_id = ?";
            long l = (Long) qr.query(sql,feature_id, new ScalarHandler());
            return (int) l;
        }catch (Exception e){
            throw new  RuntimeException(e);
        }
    }

    /**
     * 根据物点ID查找出拟用名
     * @param id
     * @return
     */
    public String readPrepareNameByFPId(int id) {
        try {
            QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
            String sql = "SELECT * FROM tb_features WHERE id = ?";
            FeaturePoint fp = (FeaturePoint) qr.query(sql, id, new BeanHandler(FeaturePoint.class));
            return fp.getPrepareName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
