package com.lvyerose.helpcommunity.base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.lvyerose.helpcommunity.im.IMUtils;
import com.lvyerose.helpcommunity.im.message.CustomizeMessage;
import com.lvyerose.helpcommunity.utils.ACache;
import com.lvyerose.helpcommunity.utils.NotificationUtils;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;

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
        initFresco(this);
        initRongIM();
    }

    private void initFresco(Context context){
        Fresco.initialize(context);

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
            RongIM.registerMessageType(CustomizeMessage.class);
//            RongIM.setOnReceivePushMessageListener(new RongIMClient.OnReceivePushMessageListener() {
//                @Override
//                public boolean onReceivePushMessage(PushNotificationMessage pushNotificationMessage) {
//                    return false;
//                }
//            });
            // 设置消息接收监听器。
            RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
                @Override
                public boolean onReceived(Message message, int i) {
                    MessageContent messageContent = message.getContent();

                    if (messageContent instanceof CustomizeMessage){
                        NotificationUtils.sendNotification(getApplicationContext() , "好友验证" , "点击进入好友请求列表查看详情");
                        ACache.get(getApplicationContext()).put(Const.ACACHE_MSG_NEW , "1");
                        return true;
                    }
                    return false;
                }
            });

        }
    }




}
