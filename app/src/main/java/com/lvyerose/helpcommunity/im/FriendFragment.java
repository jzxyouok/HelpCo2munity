package com.lvyerose.helpcommunity.im;

import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.lvyerose.helpcommunity.R;
import com.lvyerose.helpcommunity.base.BaseFragment;
import com.lvyerose.helpcommunity.base.Const;
import com.lvyerose.helpcommunity.common.network.NetworkServer;
import com.lvyerose.helpcommunity.utils.ACache;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.ResultCallback;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * author: lvyeRose
 * objective:   主页 好友界面
 * mailbox: lvyerose@163.com
 * time: 15/11/1 20:11
 */
@EFragment(R.layout.fragment_friend)
public class FriendFragment extends BaseFragment {
    @ViewById(R.id.id_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @ViewById(R.id.id_friend_lv)
    ListView friendLv;
    QuickAdapter mAdapter;
    List<FriendBean.DataEntity> friendList;

    @AfterViews
    void initViews() {
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                getFriends();
            }
        });
        getFriends();
    }

    private void getFriends() {
        ACache aCache = ACache.get(getActivity());
        NetworkServer.getFriends(aCache.getAsString(Const.ACACHE_USER_PHONE), new ResultCallback<FriendBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(FriendBean friendBean) {
                if (friendBean != null && "success".equals(friendBean.getStatus())) {
                    friendList = friendBean.getData();
                    setData();
                }
            }
        });
    }

    void refreshFinsh() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
            if (friendList != null && friendList.size() > 0) {
                mAdapter.replaceAll(friendList);
            }
            Toast.makeText(getActivity(), "加载完成", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        if (mAdapter == null) {

            friendLv.setAdapter(mAdapter = new QuickAdapter<FriendBean.DataEntity>(getActivity(), R.layout.item_friend_list, friendList) {
                @Override
                protected void convert(BaseAdapterHelper helper, FriendBean.DataEntity item) {
                    SimpleDraweeView simpleDraweeView = (SimpleDraweeView) helper.getView().findViewById(R.id.id_item_friend_Img_sdwv);
                    simpleDraweeView.setImageURI(Uri.parse(item.getUser_icon()));
                    helper.setText(R.id.id_item_friend_name_tv, item.getNick_name());
                    helper.setText(R.id.id_item_friend_dec_tv, item.getUser_dec());
                }
            });

            friendLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
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


                        RongIM.getInstance().startPrivateChat(getActivity(), friendList.get(position).getUser_phone(), friendList.get(position).getNick_name());

                    }
                }
            });
        }else {
            refreshFinsh();
        }

    }


    UserInfo findById(String user_id) {
        ACache aCache = ACache.get(getActivity());
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


}
