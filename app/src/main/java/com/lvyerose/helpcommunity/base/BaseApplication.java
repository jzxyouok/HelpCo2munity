package com.lvyerose.helpcommunity.base;

import android.app.Application;

/**
 * author: lvyeRose
 * objective: 基础进程类
 * mailbox: lvyerose@163.com
 * time: 15/10/30 17:37
 */
public class BaseApplication extends Application{
    private static BaseApplication application;

    public static BaseApplication getInstance(){
        return application;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;


    }
}
