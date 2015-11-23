package com.lvyerose.helpcommunity.im;

/**
 * author: lvyeRose
 * objective:   连接融云监听
 * mailbox: lvyerose@163.com
 * time: 15/11/23 15:44
 */
public interface ConnectListen {
    void success(String userId);
    void fail(String message);
}
