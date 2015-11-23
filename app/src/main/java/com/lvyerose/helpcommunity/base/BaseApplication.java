package com.lvyerose.helpcommunity.base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.lvyerose.helpcommunity.im.IMUtils;
import com.tencent.bugly.crashreport.CrashReport;

import io.rong.imkit.RongIM;

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
        initFresco(this);
        initRongIM();
    }

    private void initFresco(Context context){
        Fresco.initialize(context);

    }
    /**
     *  腾讯bug捕捉初始化
     * @param appContext
     */
    public void initCrashReport(Context appContext){
        CrashReport.initCrashReport(appContext, "900011750", false);
    }


    /**
     * 初始化融云SDK
     */
    private void initRongIM(){

        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(IMUtils.getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(IMUtils.getCurProcessName(getApplicationContext()))) {
            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);
        }
    }

}
