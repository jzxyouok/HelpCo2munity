package com.lvyerose.helpcommunity.found;

import java.util.List;

/**
 * author: lvyeRose
 * objective:
 * mailbox: lvyerose@163.com
 * time: 15/11/18 10:10
 */
public class BeanActivityTopData {

    /**
     * status : success
     * message : 获取成功
     * data : [{"activity_photo":"http://www.lvyerose.com/helpping/res/sfigure/top_keji.jpg","activity_url":"http://www.lvyerose.com/","activity_title":"时尚科技，引领未来","id":"1"},{"activity_photo":"http://www.lvyerose.com/helpping/res/sfigure/top_love","activity_url":"http://www.lvyerose.com/","activity_title":"粉色恋爱季","id":"2"},{"activity_photo":"http://www.lvyerose.com/helpping/res/sfigure/top_per.jpg","activity_url":"http://www.lvyerose.com/","activity_title":"私人空间，全面开启","id":"3"}]
     */

    private String status;
    private String message;
    /**
     * activity_photo : http://www.lvyerose.com/helpping/res/sfigure/top_keji.jpg
     * activity_url : http://www.lvyerose.com/
     * activity_title : 时尚科技，引领未来
     * id : 1
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
        private String activity_photo;
        private String activity_url;
        private String activity_title;

        public void setActivity_photo(String activity_photo) {
            this.activity_photo = activity_photo;
        }

        public void setActivity_url(String activity_url) {
            this.activity_url = activity_url;
        }

        public void setActivity_title(String activity_title) {
            this.activity_title = activity_title;
        }

        public String getActivity_photo() {
            return activity_photo;
        }

        public String getActivity_url() {
            return activity_url;
        }

        public String getActivity_title() {
            return activity_title;
        }
    }
}
