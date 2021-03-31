package com.share.entity;

/**
 * @Description TODO:qq实体类对应yaml文件中的数据
 * @Author Kang
 * @Date 2020-03-30 17:41
 * @Version 2.0
 */
public class QQProperties {
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String code_callback_uri;
    private String access_token_callback_uri;
    private String openid_callback_uri;
    private String user_info_callback_uri;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getCode_callback_uri() {
        return code_callback_uri;
    }

    public void setCode_callback_uri(String code_callback_uri) {
        this.code_callback_uri = code_callback_uri;
    }

    public String getAccess_token_callback_uri() {
        return access_token_callback_uri;
    }

    public void setAccess_token_callback_uri(String access_token_callback_uri) {
        this.access_token_callback_uri = access_token_callback_uri;
    }

    public String getOpenid_callback_uri() {
        return openid_callback_uri;
    }

    public void setOpenid_callback_uri(String openid_callback_uri) {
        this.openid_callback_uri = openid_callback_uri;
    }

    public String getUser_info_callback_uri() {
        return user_info_callback_uri;
    }

    public void setUser_info_callback_uri(String user_info_callback_uri) {
        this.user_info_callback_uri = user_info_callback_uri;
    }

    @Override
    public String toString() {
        return "QQProperties{" +
                "client_id='" + client_id + '\'' +
                ", client_secret='" + client_secret + '\'' +
                ", redirect_uri='" + redirect_uri + '\'' +
                ", code_callback_uri='" + code_callback_uri + '\'' +
                ", access_token_callback_uri='" + access_token_callback_uri + '\'' +
                ", openid_callback_uri='" + openid_callback_uri + '\'' +
                ", user_info_callback_uri='" + user_info_callback_uri + '\'' +
                '}';
    }
}
