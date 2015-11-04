package com.lvyerose.helpcommunity.found;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.lvyerose.helpcommunity.R;
import com.lvyerose.helpcommunity.base.BaseFragment;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.lvyerose.helpcommunity.R.id.id_item_userImg_sdwv;

/**
 * author: lvyeRose
 * objective:   发现界面的二级界面  好友动态
 * mailbox: lvyerose@163.com
 * time: 15/11/2 11:24
 */
@EFragment(R.layout.fragment_friend_dynamic)
public class FriendDynamicFragment extends BaseFragment {
    @StringArrayRes(R.array.datas_friends_message)
    String[] contents;
    @StringArrayRes(R.array.datas_friends_userName)
    String[] usernames;
    @StringArrayRes(R.array.datas_friends_city)
    String[] userCity;
    @StringArrayRes(R.array.datas_user_icon)
    String[] userIconUrl;

    @StringArrayRes(R.array.datas_friends_message_add)
    String[] contentsadd;
    @StringArrayRes(R.array.datas_friends_userName_add)
    String[] usernamesadd;
    @StringArrayRes(R.array.datas_friends_city_add)
    String[] userCityadd;
    @StringArrayRes(R.array.datas_user_icon_add)
    String[] userIconUrladd;


    SweetAlertDialog pDialog;

    @ViewById(R.id.id_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @ViewById(R.id.id_friend_lv)
    ListView mListView;
    QuickAdapter<BeanFriendData> mAdapter;
    List<BeanFriendData> mListData;
    private SparseBooleanArray mCollapsedStatus = new SparseBooleanArray();


    @AfterViews
    void dailogs() {
        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        cancelDailog();
    }

    @UiThread(delay = 3000)
    void cancelDailog() {
        if (pDialog != null) {
            pDialog.cancel();
        }
    }

    @UiThread(delay = 2000)
    void refreshFinsh() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
            if (mListData != null && mListData.size() > 0) {
//                mAdapter.addAll(addData());
                mAdapter.replaceAll(addData());
            }
            Toast.makeText(getActivity(), "加载完成", Toast.LENGTH_SHORT).show();
        }
    }


    @AfterViews
    void initData() {

        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                refreshFinsh();
            }
        });

        //listView设置适配器
        mListView.setAdapter(mAdapter = new QuickAdapter<BeanFriendData>(getActivity(),
                R.layout.item_friend_dynamic,
                mListData = getDatas()) {

            @Override
            protected void convert(final BaseAdapterHelper helper, final BeanFriendData item) {

                SimpleDraweeView simpleDraweeView = (SimpleDraweeView) helper.getView().findViewById(id_item_userImg_sdwv);
                simpleDraweeView.setImageURI(Uri.parse(item.getIcon()));

                final TextView iconFavourTv = (TextView) helper.getView().findViewById(R.id.id_item_favour_tv);
                TextView iconCommunityTv = (TextView) helper.getView().findViewById(R.id.id_item_community_tv);
                ImageButton iconCollectImbt = (ImageButton) helper.getView().findViewById(R.id.id_item_collect_imbt);

                iconFavourTv.setSelected(item.isFavour());
                iconCollectImbt.setSelected(item.isCollect());

                iconFavourTv.setText(item.getFavour() + "");
                iconCommunityTv.setText(item.getCommnunity() + "");

                setItemBottomIcon(iconFavourTv, R.drawable.item_favour_select);
                setItemBottomIcon(iconCommunityTv, R.drawable.item_commnunity_select);

                iconFavourTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (view.isSelected()) {
                            view.setSelected(false);
                            item.setIsFavour(false);
                            item.setFavour(item.getFavour() - 1);
                            iconFavourTv.setText(item.getFavour() + "");
                        } else {
                            view.setSelected(true);
                            item.setIsFavour(true);
                            item.setFavour(item.getFavour() + 1);
                            iconFavourTv.setText(item.getFavour() + "");
                        }
                    }
                });
                iconCommunityTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "打开去评论几句", Toast.LENGTH_SHORT).show();
                    }
                });
                iconCollectImbt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (view.isSelected()) {
                            view.setSelected(false);
                            item.setIsCollect(false);
                        } else {
                            view.setSelected(true);
                            item.setIsCollect(true);
                        }
                    }
                });


                ExpandableTextView expTv1 = (ExpandableTextView) helper.getView()
                        .findViewById(R.id.expand_text_view);
                expTv1.setText(item.getContent()
                        , mCollapsedStatus, helper.getPosition());

                helper.setText(R.id.id_item_userName_tv, item.getName());
                helper.setText(R.id.id_item_userTime_tv, item.getTime());
                helper.setText(R.id.id_item_userAdders_tv, item.getAddress());
                // helper.getView(R.id.tv_title).setOnClickListener(l)
            }
        });

    }


    void setItemBottomIcon(TextView textView, int resId) {

        Drawable resDraw = getResources().getDrawable(resId);
        resDraw.setBounds(1, 1, 38, 38);
        textView.setCompoundDrawables(resDraw, null, null, null);


    }


    //模拟网络获取数据
    List<BeanFriendData> getDatas() {
        List<BeanFriendData> list = new ArrayList<>();
        for (int i = 0; i < contents.length; i++) {
            BeanFriendData bean = new BeanFriendData();
            bean.setId(i);
            bean.setIcon(userIconUrl[i]);
            bean.setName(usernames[i]);
            bean.setAddress(userCity[i]);
            bean.setTime("11/12 12:2" + i);
            bean.setContent(contents[i]);
            bean.setCommnunity(i * i + i * 4 + i);
            bean.setFavour(i * i * i + i * i);
            if (i == 6 || i == 10) {
                bean.setIsCollect(true);
                bean.setIsFavour(true);
            }
            list.add(bean);
        }

        return list;
    }

    List<BeanFriendData> addData() {
        List<BeanFriendData> list = new ArrayList<>();
        for (int i = 0; i < contentsadd.length; i++) {
            BeanFriendData bean = new BeanFriendData();
            bean.setId(i);
            bean.setIcon(userIconUrladd[i]);
            bean.setName(usernamesadd[i]);
            bean.setAddress(userCityadd[i]);
            bean.setTime("11/15 10:2" + i);
            bean.setContent(contentsadd[i]);
            bean.setCommnunity(i * i + i * 4 + i + 10);
            bean.setFavour(i * i * i + i * i);
            list.add(bean);
        }
        list.addAll(getDatas());

        return list;
    }

}
