package org.cpp.gis.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * C3P0数据库链接池工具类.
 * Created by Rose on 2015/5/3.
 */
public class JDBCUtil {
    private static ComboPooledDataSource dataSource = null;

    static {
        dataSource = new ComboPooledDataSource();
    }

    /**
     * 获取数据库连接.
     * @author Rose.
     * @return
     */
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取链接池资源.
     * @author Rose.
     * @return
     */
    public static DataSource getDataSource() {
        return dataSource;
    }
}
