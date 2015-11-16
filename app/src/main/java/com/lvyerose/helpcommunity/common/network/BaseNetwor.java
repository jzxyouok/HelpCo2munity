package com.lvyerose.helpcommunity.common.network;

import android.util.Pair;

import com.zhy.http.okhttp.callback.ResultCallback;
import com.zhy.http.okhttp.request.OkHttpRequest;

import java.io.File;
import java.util.Map;

/**
 * author: lvyeRose
 * objective:
 * mailbox: lvyerose@163.com
 * time: 15/11/16 14:12
 */
public class BaseNetwor {

    /**
     * 普通get请求
     *
     * @param url
     * @param request
     */
    public void get(String url, ResultCallback<?> request) {
        new OkHttpRequest.Builder()
                .url(url)
                .get(request);
    }


    /**
     * 普通Post请求
     *
     * @param url
     * @param params
     * @param callBack
     */
    public void post(String url, Map<String, String> params,
                     ResultCallback<?> callBack) {
        //正式启动请求
        new OkHttpRequest.Builder()
                .url(url)
                .params(getParams(params))
                .post(callBack);
    }


    /**
     *  文件上传
     * @param url
     * @param files
     * @param params
     * @param callBack
     */
    public void uploadFile(String url, Pair<String, File> files, Map<String, String> params,
                           ResultCallback<?> callBack) {

        new OkHttpRequest.Builder()
                .url(url)
                .params(getParams(params))
                .files(files)
                .upload(callBack);
    }

    /**
     * 获取公共的参数传递
     *
     * @param params
     * @return
     */
    private Map<String, String> getParams(Map<String, String> params) {
        //接口加密
//        params.put("" , "" );
        return params;
    }
}
