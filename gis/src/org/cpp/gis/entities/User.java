package org.cpp.gis.entities;

/**
 * 管理员实体类.
 * Created by Rose on 2015/5/9.
 */
public class User {
    private Integer id;
    private String username;
    private String password;
    private Integer isSu;       // 是否为超级用户.

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isSu=" + isSu +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsSu() {
        return isSu;
    }

    public void setIsSu(Integer isSu) {
        this.isSu = isSu;
    }
}
