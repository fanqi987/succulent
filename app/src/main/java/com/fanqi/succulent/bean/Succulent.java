package com.fanqi.succulent.bean;

import java.util.Date;

public class Succulent extends Bean {

    protected int id;
    protected String name;
    protected String page_name;
    protected int family_id;
    protected int genera_id;
    protected int light;
    protected int water;
    protected Date created_at;
    protected Date updated_at;

    public Succulent() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPage_name() {
        return page_name;
    }

    public void setPage_name(String page_name) {
        this.page_name = page_name;
    }

    public int getFamily_id() {
        return family_id;
    }

    public void setFamily_id(int family_id) {
        this.family_id = family_id;
    }

    public int getGenera_id() {
        return genera_id;
    }

    public void setGenera_id(int genera_id) {
        this.genera_id = genera_id;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
