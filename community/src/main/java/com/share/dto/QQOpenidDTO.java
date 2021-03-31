package com.share.dto;

/**
 * @Description TODO:qq接受openid
 * @Author Kang
 * @Date 2020-03-30 18:37
 * @Version 1.0
 */
public class QQOpenidDTO {

    private String openid;

    private String client_id;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
