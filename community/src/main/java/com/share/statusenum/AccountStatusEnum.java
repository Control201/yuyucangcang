package com.share.statusenum;

/**
 * @Description TODO:账号状态 ：-1：异常（拉黑，无效） 0:删除（注销）  1：正常，  -- 采用enum方式
 * @Author Kang
 * @Date 2020-01-15 9:46
 * @Version 1.0
 */
public enum  AccountStatusEnum {
    ABNORMAL(-1,"账号异常"),
    REMOVE(0,"账号已删除"),
    NORMAL (1,"账号正常");

    private  int status;
    private String info;

    AccountStatusEnum(int status, String info) {
        this.status = status;
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * 该方法用于根据 下标 确定 账号状态
     * values() 枚举中的一个方法，枚举隐性继承java.lang.enum
     * @param index
     * @return
     */
    public static AccountStatusEnum statusOf(int index){
        for (AccountStatusEnum status :values()){
            if (status.getStatus() == index){
                return status;
            }
        }
        return null;
    }
}
