package com.fanqi.succulent.bean;

import java.util.Date;

public class User extends  Bean{
    private int id;
    private String wechat_token;
    private Date expire_time;
    private Date created_at;
    private Date updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWechat_token() {
        return wechat_token;
    }

    public void setWechat_token(String wechat_token) {
        this.wechat_token = wechat_token;
    }

    public Date getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(Date expire_time) {
        this.expire_time = expire_time;
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
