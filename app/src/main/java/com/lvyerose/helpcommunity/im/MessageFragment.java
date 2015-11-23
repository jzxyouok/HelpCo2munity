package com.lvyerose.helpcommunity.im;

import android.widget.Button;

import com.lvyerose.helpcommunity.R;
import com.lvyerose.helpcommunity.base.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * author: lvyeRose
 * objective:   主页 消息界面
 * mailbox: lvyerose@163.com
 * time: 15/11/1 20:11
 */
@EFragment(R.layout.fragment_message)
public class MessageFragment extends BaseFragment {
    @ViewById(R.id.to_IM)
    Button toIM;

    @AfterViews
    void initView(){

    }

    @Click(R.id.to_IM)
    void click(){

//            UserInfoBean bean = new UserInfoBean();
//            UserInfoBean.DataEntity dataEntity= new UserInfoBean.DataEntity();
//            dataEntity.setNick_name("soul,");
//            dataEntity.setUser_icon("");
//            dataEntity.setUser_id("18311321513");
//
//            bean.setData(dataEntity);
//            IMUtils.setUserInfo(bean);
    }



}
