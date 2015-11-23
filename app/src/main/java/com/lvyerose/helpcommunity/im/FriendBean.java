package com.lvyerose.helpcommunity.im;

import java.util.List;

/**
 * author: lvyeRose
 * objective:
 * mailbox: lvyerose@163.com
 * time: 15/11/23 23:55
 */
public class FriendBean {

    /**
     * status : success
     * message : 获取成功
     * data : [{"user_phone":"18311321513","nick_name":"蜀汉玫瑰","user_icon":"http://www.lvyerose.com/helpping/res/user_icon/temp20151123144548.jpeg"}]
     */

    private String status;
    private String message;
    /**
     * user_phone : 18311321513
     * nick_name : 蜀汉玫瑰
     * user_icon : http://www.lvyerose.com/helpping/res/user_icon/temp20151123144548.jpeg
     */

    private List<DataEntity> data;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private String user_phone;
        private String nick_name;
        private String user_icon;

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public void setUser_icon(String user_icon) {
            this.user_icon = user_icon;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public String getNick_name() {
            return nick_name;
        }

        public String getUser_icon() {
            return user_icon;
        }
    }
}
