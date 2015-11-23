package com.lvyerose.helpcommunity.im;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * author: lvyeRose
 * objective: 融云即时通讯工具类
 * mailbox: lvyerose@163.com
 * time: 15/11/23 10:12
 */
public class IMUtils {
    private ConnectListen connectListen;

    private Context context;


    public IMUtils(Context context , ConnectListen connectListen) {
        this.context = context;
        this.connectListen = connectListen;
    }





    /**
     * 建立与融云服务器的连接
     *
     * @param token
     */
    public void connect(String token) {

        if (context.getApplicationInfo().packageName.equals(getCurProcessName(context.getApplicationContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {
                    if (connectListen!=null){
                        connectListen.fail("Token过期");
                    }
                    Log.d("LoginActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 *
                 * @param userId 当前 token
                 */
                @Override
                public void onSuccess(String userId) {
                    connectListen.success(userId);
                }

                /**
                 * 连接融云失败
                 *
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    connectListen.fail(errorCode.getMessage());
                }
            });
        }
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
