package com.lvyerose.helpcommunity.login;

/**
 * author: lvyeRose
 * objective:
 * mailbox: lvyerose@163.com
 * time: 15/11/16 19:26
 */
public class MobileCodeBean {

    /**
     * status : success
     * message : 发送成功
     * data : 988419
     */

    private String status;
    private String message;
    private String data;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }
}
