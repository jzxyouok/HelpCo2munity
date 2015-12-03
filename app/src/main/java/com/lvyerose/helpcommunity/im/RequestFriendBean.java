package com.lvyerose.helpcommunity.im;

import java.util.List;

/**
 * author: lvyeRose
 * objective:
 * mailbox: lvyerose@163.com
 * time: 15/12/3 11:12
 */
public class RequestFriendBean {


    /**
     * status : success
     * message : 获取成功
     * data : [{"user_phone":"18311321514","nick_name":"粉丝2号","user_icon":"http://www.lvyerose.com/helpping/res/user_icon/20151124090902.jpeg","type":"1","state":"0"},{"user_phone":"18311321515","nick_name":"粉丝1号","user_icon":"http://www.lvyerose.com/helpping/res/user_icon/temp20151125085658.jpeg","type":"1","state":"1"},{"user_phone":"18311321516","nick_name":"小邦邦","user_icon":"http://www.lvyerose.com/helpping/res/default/default_icon.png","type":"2","state":"2"},{"user_phone":"18528667754","nick_name":"小邦邦","user_icon":"http://www.lvyerose.com/helpping/res/default/default_icon.png","type":"2","state":"0"},{"user_phone":"15321558311","nick_name":"jing","user_icon":"http://www.lvyerose.com/helpping/res/user_icon/temp20151130135528.jpeg","type":"2","state":"1"}]
     */

    private String status;
    private String message;
    /**
     * user_phone : 18311321514
     * nick_name : 粉丝2号
     * user_icon : http://www.lvyerose.com/helpping/res/user_icon/20151124090902.jpeg
     * type : 1
     * state : 0
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
        private String type;
        private String state;

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public void setUser_icon(String user_icon) {
            this.user_icon = user_icon;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setState(String state) {
            this.state = state;
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

        public String getType() {
            return type;
        }

        public String getState() {
            return state;
        }
    }
}
