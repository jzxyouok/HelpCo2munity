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
     * data : {"user_phone":"18311321513","pass_word":"120726","user_icon":"http://www.lvyerose.com/helpping/res/default/default_icon.png","nick_name":"蜀汉玫瑰","user_token":"123456","user_sex":"1","user_id":"123","user_dec":"只有纯粹的力量才能带来存储","user_school":"湖南科技学院","user_bg":"Htt"}
     */

    private String status;
    private String message;
    /**
     * user_phone : 18311321513
     * pass_word : 120726
     * user_icon : http://www.lvyerose.com/helpping/res/default/default_icon.png
     * nick_name : 蜀汉玫瑰
     * user_token : 123456
     * user_sex : 1
     * user_id : 123
     * user_dec : 只有纯粹的力量才能带来存储
     * user_school : 湖南科技学院
     * user_bg : Htt
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
        private String user_icon;
        private String nick_name;
        private String user_token;
        private String user_sex;
        private String user_id;
        private String user_dec;
        private String user_school;
        private String user_bg;

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public void setPass_word(String pass_word) {
            this.pass_word = pass_word;
        }

        public void setUser_icon(String user_icon) {
            this.user_icon = user_icon;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public void setUser_token(String user_token) {
            this.user_token = user_token;
        }

        public void setUser_sex(String user_sex) {
            this.user_sex = user_sex;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setUser_dec(String user_dec) {
            this.user_dec = user_dec;
        }

        public void setUser_school(String user_school) {
            this.user_school = user_school;
        }

        public void setUser_bg(String user_bg) {
            this.user_bg = user_bg;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public String getPass_word() {
            return pass_word;
        }

        public String getUser_icon() {
            return user_icon;
        }

        public String getNick_name() {
            return nick_name;
        }

        public String getUser_token() {
            return user_token;
        }

        public String getUser_sex() {
            return user_sex;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getUser_dec() {
            return user_dec;
        }

        public String getUser_school() {
            return user_school;
        }

        public String getUser_bg() {
            return user_bg;
        }
    }
}
