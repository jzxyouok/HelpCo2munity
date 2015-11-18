package com.lvyerose.helpcommunity.found;

import java.util.List;

/**
 * author: lvyeRose
 * objective:
 * mailbox: lvyerose@163.com
 * time: 15/11/18 14:58
 */
public class BeanActivityListData {


    /**
     * status : success
     * message : 获取成功
     * data : [{"activity_photo":"http://www.lvyerose.com/helpping/res/activity/activity_1.jpeg","list_title":"第一期","list_time":"活动截止日期:2015-12-30","list_dec":"全民coding大赛","list_id":"1","id":"1"},{"activity_photo":"http://www.lvyerose.com/helpping/res/activity/activity_2.jpg","list_title":"第二期","list_time":"活动截止日期:2015-11-30","list_dec":"秀你身边的码农","list_id":"2","id":"2"},{"activity_photo":"http://www.lvyerose.com/helpping/res/activity/activity_3.jpg","list_title":"第三期","list_time":"活动截止日期:2015-10-30","list_dec":"会议大家来吐槽","list_id":"3","id":"3"},{"activity_photo":"http://www.lvyerose.com/helpping/res/activity/activity_4.jpg","list_title":"第四期","list_time":"活动截止日期:2016-01-30","list_dec":"未来科技家装","list_id":"4","id":"4"}]
     */

    private String status;
    private String message;
    /**
     * activity_photo : http://www.lvyerose.com/helpping/res/activity/activity_1.jpeg
     * list_title : 第一期
     * list_time : 活动截止日期:2015-12-30
     * list_dec : 全民coding大赛
     * list_id : 1
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
        private String list_title;
        private String list_time;
        private String list_dec;
        private String list_id;
        private String id;

        public void setActivity_photo(String activity_photo) {
            this.activity_photo = activity_photo;
        }

        public void setList_title(String list_title) {
            this.list_title = list_title;
        }

        public void setList_time(String list_time) {
            this.list_time = list_time;
        }

        public void setList_dec(String list_dec) {
            this.list_dec = list_dec;
        }

        public void setList_id(String list_id) {
            this.list_id = list_id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getActivity_photo() {
            return activity_photo;
        }

        public String getList_title() {
            return list_title;
        }

        public String getList_time() {
            return list_time;
        }

        public String getList_dec() {
            return list_dec;
        }

        public String getList_id() {
            return list_id;
        }

        public String getId() {
            return id;
        }
    }
}
