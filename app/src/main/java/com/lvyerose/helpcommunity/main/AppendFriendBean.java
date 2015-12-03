package com.lvyerose.helpcommunity.main;

/**
 * author: lvyeRose
 * objective:
 * mailbox: lvyerose@163.com
 * time: 15/12/2 15:29
 */
public class AppendFriendBean {


    /**
     * status : success
     * message : 发送成功
     * data : null
     */

    private String status;
    private String message;
    private Object data;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
