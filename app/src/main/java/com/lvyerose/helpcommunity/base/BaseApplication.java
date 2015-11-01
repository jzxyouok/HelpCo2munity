package com.lvyerose.helpcommunity.base;

import android.app.Application;
import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;

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
        initCrashReport(this);


    }

    /**
     *  腾讯bug捕捉初始化
     * @param appContext
     */
    public void initCrashReport(Context appContext){
        CrashReport.initCrashReport(appContext, "900011750", false);
    }
}
