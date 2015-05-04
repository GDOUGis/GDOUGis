package org.cpp.gis.factory;

/**
 * Dao工厂方法.
 * Created by Rose on 2015/5/3.
 */
public class DaoFactory {
    /**
     * 默认构造器私有化.
     */
    private DaoFactory() {}

    private static DaoFactory instance = null;

    /**
     * 懒汉式加载.
     * @author Rose.
     * @return
     */
    public static DaoFactory getInstance() {
        if(null == instance) {
            instance = new DaoFactory();
        }
        return instance;
    }

    /**
     * 根据泛型参数生成Dao对象.
     * @author Rose.
     * @param className
     * @param <T>
     * @return
     */
    public static <T> T createDao(String className) {
        try {
            T t = (T) Class.forName(className).newInstance();
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
