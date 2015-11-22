package com.lvyerose.helpcommunity.common.network;

import com.zhy.http.okhttp.callback.ResultCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * author: lvyeRose
 * objective:
 * mailbox: lvyerose@163.com
 * time: 15/11/16 14:12
 */
public class NetworkServer {

    /**
     * 登录请求业务
     *
     * @param user_phone     用户手机号码
     * @param pass_word      用户密码
     * @param resultCallback 回调结果
     */
    public static void toLogin(String user_phone, String pass_word, ResultCallback<?> resultCallback) {
        String url = NetworkConst.IS_LOGIN_;
        Map<String, String> params = new HashMap<>();
        params.put("user_phone", user_phone);
        params.put("pass_word", pass_word);
        new BaseNetwor().post(url, params, resultCallback);
    }

    /**
     * 发送验证码
     *
     * @param user_phone     用户手机号码
     * @param resultCallback 回调结果
     */
    public static void getMobCode(String user_phone, ResultCallback<?> resultCallback) {
        String url = NetworkConst.SEND_CODE;
        Map<String, String> params = new HashMap<>();
        params.put("user_phone", user_phone);
        new BaseNetwor().post(url, params, resultCallback);
    }

    /**
     * 注册请求
     *
     * @param user_phone     用户手机号码
     * @param pass_word      用户设置的密码
     * @param mobile_code    用户获取的验证码
     * @param user_sex       用户性别
     * @param resultCallback 回调结果
     */
    public static void toRegister(String user_phone, String pass_word, String mobile_code
            , int user_sex, ResultCallback<?> resultCallback) {
        String url = NetworkConst.REGISTER;
        Map<String, String> params = new HashMap<>();
        params.put("user_phone", user_phone);
        params.put("pass_word", pass_word);
        params.put("mobile_code", mobile_code);
        params.put("user_sex", user_sex + "");
        new BaseNetwor().post(url, params, resultCallback);
    }

    /**
     *  活动界面顶部轮播图
     * @param resultCallback
     */
    public static void getTopData(ResultCallback<?> resultCallback) {
        String url = NetworkConst.GET_SF;
        new BaseNetwor().get(url, resultCallback);
    }

    /**
     * 活动界面底部list列表活动项
     * @param resultCallback
     */
    public static void getListData(ResultCallback<?> resultCallback) {
        String url = NetworkConst.GET_LIST;
        new BaseNetwor().get(url, resultCallback);
    }

    /**
     * 更新用户文字信息
     * @param user_phone
     * @param user_type
     * @param user_content
     * @param resultCallback
     */
    public static void updateMessage(String user_phone, String user_type, String user_content
            , ResultCallback<?> resultCallback) {
        String url = NetworkConst.UPDATE_MESSAGE;
        Map<String, String> params = new HashMap<>();
        params.put("user_phone", user_phone);
        params.put("user_type", user_type);
        params.put("user_content", user_content);
        new BaseNetwor().post(url, params, resultCallback);
    }


}