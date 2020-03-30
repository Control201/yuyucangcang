package com.share.statusenum;

/**
 * @Description TODO:用户信息
 * @Author YuYu
 * @Date 2020-01-28 16:49
 * @Version 1.0
 */
public enum UserStatusEnum {
    User_Info_Modify_OK("信息修改成功!",2001),
    User_Info_Modify_Fail("信息修改失败~",2002),
    Message_Read_OK("消息已读",2003),
    Message_Read_Fail("当前没有未读消息",2004),
    Message_Send_OK("消息发送成功",2005),
    Message_Send_Fail("发送消息异常",2006),
    Add_Relation_OK("关注成功",2007),
    Modify_Relation_OK("取消关注",2008),
    Relation_Exception("操作异常",2009);


    private String message;
    private int status;

    UserStatusEnum(String message, int status) {
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
