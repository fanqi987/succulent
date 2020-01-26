package com.fanqi.succulent.bean;

import java.util.Date;

public class UserFavorite extends Bean {

    private int id;
    private int user_id;
    private int succulent_id;
    private Date created_at;
    private Date updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSucculent_id() {
        return succulent_id;
    }

    public void setSucculent_id(int succulent_id) {
        this.succulent_id = succulent_id;
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
