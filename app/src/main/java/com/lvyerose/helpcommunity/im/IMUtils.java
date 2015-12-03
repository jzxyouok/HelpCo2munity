package com.lvyerose.helpcommunity.im;

import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.lvyerose.helpcommunity.base.Const;
import com.lvyerose.helpcommunity.utils.ACache;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

/**
 * author: lvyeRose
 * objective: 融云即时通讯工具类
 * mailbox: lvyerose@163.com
 * time: 15/11/23 10:12
 */
public class IMUtils {
    private ConnectListen connectListen;
    private Context context;
    List<FriendBean.DataEntity> friendList;

    public IMUtils(Context context, ConnectListen connectListen) {
        this.context = context;
        this.connectListen = connectListen;
    }

    public IMUtils(Context context) {
        this.context = context;
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
                    if (connectListen != null) {
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
//                    // 设置消息接收监听器。
//                    RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
//                        @Override
//                        public boolean onReceived(Message message, int i) {
//                            return false;
//                        }
//                    });
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

    public void initUserInfoProvider(List<FriendBean.DataEntity> friendList) {
        this.friendList = friendList;
        if (RongIM.getInstance() != null) {
            /**
             * 启动单聊界面。
             *
             * @param context      应用上下文。
             * @param targetUserId 要与之聊天的用户 Id。
             * @param title        聊天的标题，如果传入空值，则默认显示与之聊天的用户名称。
             */
            RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                @Override
                public UserInfo getUserInfo(String s) {
                    return findById(s);
                }
            }, true);
        }

    }

    private UserInfo findById(String user_id ) {
        ACache aCache = ACache.get(context);
        if (user_id.equals(aCache.getAsString(Const.ACACHE_USER_PHONE))) {
            return new UserInfo(aCache.getAsString(Const.ACACHE_USER_PHONE)
                    , aCache.getAsString(Const.ACACHE_USER_NAME)
                    , Uri.parse(aCache.getAsString(Const.ACACHE_USER_ICON)));
        }

        for (int i = 0; i < friendList.size(); i++) {
            if (friendList.get(i).getUser_phone().equals(user_id)) {
                return new UserInfo(friendList.get(i).getUser_phone(), friendList.get(i).getNick_name(), Uri.parse(friendList.get(i).getUser_icon()));

            }
        }
        return null;
    }


    /**
     * 友盟账号统计
     * @param user_id 用户账号登陆
     */
    public static void Mobclick(String user_id){
        MobclickAgent.onProfileSignIn(user_id);
    }
}
