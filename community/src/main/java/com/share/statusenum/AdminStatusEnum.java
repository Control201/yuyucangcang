package com.share.statusenum;

/**
 * @Description TODO:游客端 登录，注册  ajax响应返回信息
 * @Author YuYu
 * @Date 2020-02-19 14:55
 * @Version 1.0
 */
public enum AdminStatusEnum {
    Blog_Top_Modify_OK("设置成功",6001),
    Blog_Boutique_Modify_OK("设置成功",6002),
    Blog_Modify_Exception("操作异常",6003);

    private String message;
    private int status;
    AdminStatusEnum(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
