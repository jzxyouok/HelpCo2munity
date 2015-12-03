package com.lvyerose.helpcommunity.common.network;

/**
 * author: lvyeRose
 * objective:   联网常量类   生产联网所需的网络链接等
 * mailbox: lvyerose@163.com
 * time: 15/9/25 17:03
 */
public class NetworkConst {
    /** 服务器地址*/
    private static final String MAIN_ = "http://www.lvyerose.com/helpping/index.php/Home/";
    /** 欢迎页广告图获取*/
    public static final String  WELLCOME_AD_= "";
    /** 判断是否直接登录*/
    public static final String IS_LOGIN_ = MAIN_ + "Login/login";
    /** 发送验证码*/
    public static final String SEND_CODE = MAIN_ + "Login/mobileCode";
    /** 用户注册*/
    public static final String REGISTER = MAIN_ + "Login/register";
    /** 图像更新接口*/
    public static final String UPDATE_ICON = MAIN_ + "Update/updateIcon";
    /** 用户文字信息更新接口*/
    public static final String UPDATE_MESSAGE = MAIN_ + "Update/updateMessage";
    /** 获取活动界面顶部轮播图*/
    public static final String GET_SF = MAIN_ + "Activity/getsf";
    /** 获取活动界面列表数据*/
    public static final String GET_LIST = MAIN_ + "Activity/getlist";
    /** 好友列表获取*/
    public static final String GET_FRIEND = MAIN_ + "Friend/friends";
    /** 发送添加好友请求*/
    public static final String SEND_FRIEND = MAIN_ + "AddFriend/send";
    /** 同意好友请求*/
    public static final String AGREED_FRIEND = MAIN_ + "AddFriend/agreement";
    /** 拒绝好友请求*/
    public static final String REJECT_FRIEND = MAIN_ + "AddFriend/reject";
    /** 获取好友请求列表*/
    public static final String GET_REQUEST_FRIEND = MAIN_ + "AddFriend/getRequestFriend";




}
