package com.lvyerose.helpcommunity.login;

import java.io.Serializable;

/**
 * author: lvyeRose
 * objective:
 * mailbox: lvyerose@163.com
 * time: 15/11/16 14:19
 */
public class UserInfoBean implements Serializable{

    /**
     * status : success
     * message : 登陆成功
     * data : {"user_phone":"18311321513","pass_word":"123456aA","user_icon":null,"nick_name":"蜀汉玫瑰","user_token":null,"user_sex":null}
     */

    private String status;
    private String message;
    /**
     * user_phone : 18311321513
     * pass_word : 123456aA
     * user_icon : null
     * nick_name : 蜀汉玫瑰
     * user_token : null
     * user_sex : null
     */

    private DataEntity data;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity implements Serializable{
        private String user_phone;
        private String pass_word;
        private Object user_icon;
        private String nick_name;
        private Object user_token;
        private Object user_sex;

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public void setPass_word(String pass_word) {
            this.pass_word = pass_word;
        }

        public void setUser_icon(Object user_icon) {
            this.user_icon = user_icon;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public void setUser_token(Object user_token) {
            this.user_token = user_token;
        }

        public void setUser_sex(Object user_sex) {
            this.user_sex = user_sex;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public String getPass_word() {
            return pass_word;
        }

        public Object getUser_icon() {
            return user_icon;
        }

        public String getNick_name() {
            return nick_name;
        }

        public Object getUser_token() {
            return user_token;
        }

        public Object getUser_sex() {
            return user_sex;
        }
    }
}
