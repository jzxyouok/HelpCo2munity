package com.lvyerose.helpcommunity.base;

import android.support.v7.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;

/**
 * author: lvyeRose
 * objective:  activity基础集成类
 * mailbox: lvyerose@163.com
 * time: 15/11/1 19:58
 */
public class BaseActivity extends AppCompatActivity{

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
