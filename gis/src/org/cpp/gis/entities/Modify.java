package org.cpp.gis.entities;

/**
 * 修改信息类.
 * Created by Rose on 2015/5/8.
 */
public class Modify {
    private Integer id;
    private String name;
    private String description;
    private String people;
    private Integer identification;
    private String college;
    private String phone;
    private Integer times;

    private Integer featuere_id;

    public Integer getFeatuere_id() {
        return featuere_id;
    }

    public void setFeatuere_id(Integer featuere_id) {
        this.featuere_id = featuere_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public Integer getIdentification() {
        return identification;
    }

    public void setIdentification(Integer identification) {
        this.identification = identification;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
}
