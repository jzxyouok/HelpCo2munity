package com.lvyerose.helpcommunity.im;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.lvyerose.helpcommunity.R;
import com.lvyerose.helpcommunity.base.BaseFragment;
import com.lvyerose.helpcommunity.base.Const;
import com.lvyerose.helpcommunity.utils.ACache;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * author: lvyeRose
 * objective:   主页 消息界面
 * mailbox: lvyerose@163.com
 * time: 15/11/1 20:11
 */
@EFragment(R.layout.fragment_message)
public class MessageFragment extends BaseFragment {
    @ViewById(R.id.id_message_new_msg)
    ImageView isMsgImv;             //新的好友请求标志
    @AfterViews
    void initView(){
        isMsgImv.setImageResource(R.drawable.icon_system_msg3);
        ConversationListFragment fragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();
        fragment.setUri(uri);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.rong_content, fragment);
        transaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        setChangNotify();
    }

    @Click(R.id.id_message_request_friend)
    void toRequestFriend(){
        RequestListActivity_.intent(getActivity()).start();
        ACache.get(getActivity()).put(Const.ACACHE_MSG_NEW , "0");
    }

    private void setChangNotify(){
        if (TextUtils.isEmpty(ACache.get(getActivity()).getAsString(Const.ACACHE_MSG_NEW)) || "0".equals(ACache.get(getActivity()).getAsString(Const.ACACHE_MSG_NEW))){
            isMsgImv.setVisibility(View.GONE);
        }else {
            isMsgImv.setVisibility(View.VISIBLE);
        }
    }




}
