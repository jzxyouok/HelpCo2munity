package com.lvyerose.helpcommunity.found;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.lvyerose.helpcommunity.R;
import com.lvyerose.helpcommunity.base.BaseFragment;
import com.mikepenz.materialdrawer.view.BezelImageView;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

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
    int[] userIcon = new int[]{
        R.drawable.user_01,
        R.drawable.user_02,
        R.drawable.user_03,
        R.drawable.user_04,
        R.drawable.user_05,
        R.drawable.user_06,
        R.drawable.user_07,
        R.drawable.user_08,
        R.drawable.user_09,
        R.drawable.user_10,
        R.drawable.user_11,
        R.drawable.user_12,
        R.drawable.user_13,
        R.drawable.user_14,
        R.drawable.user_16
    };

    SweetAlertDialog pDialog;

    @ViewById(R.id.id_friend_lv)
    ListView mListView;
    private SparseBooleanArray mCollapsedStatus = new SparseBooleanArray();


    @AfterViews
    void dailogs(){
        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        cancelDailog();
    }

    @UiThread(delay=3000)
    void cancelDailog(){
        if(pDialog != null){
            pDialog.cancel();
        }
    }

    @AfterViews
    void initData() {

        mListView.setAdapter(new QuickAdapter<BeanFriendData>(getActivity(), R.layout.item_friend_dynamic, getDatas()) {

            @Override
            protected void convert(final BaseAdapterHelper helper, final BeanFriendData item) {

                BezelImageView imageView = (BezelImageView) helper.getView().findViewById(R.id.id_item_userImg_bimv);
                imageView.setImageResource(userIcon[helper.getPosition()]);

                final TextView iconFavourTv = (TextView) helper.getView().findViewById(R.id.id_item_favour_tv);
                TextView iconCommunityTv = (TextView) helper.getView().findViewById(R.id.id_item_community_tv);
                ImageButton iconCollectImbt = (ImageButton) helper.getView().findViewById(R.id.id_item_collect_imbt);

                iconFavourTv.setSelected(item.isFavour());
                iconCollectImbt.setSelected(item.isCollect());

                iconFavourTv.setText(item.getFavour() + "");
                iconCommunityTv.setText(item.getCommnunity()+"");

                setItemBottomIcon(iconFavourTv, R.drawable.item_favour_select);
                setItemBottomIcon(iconCommunityTv, R.drawable.item_commnunity_select);

                iconFavourTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (view.isSelected()) {
                            view.setSelected(false);
                            item.setIsFavour(false);
                            item.setFavour(item.getFavour() - 1);
                            iconFavourTv.setText(item.getFavour()+"");
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
                        Toast.makeText(getActivity() , "打开去评论几句" , Toast.LENGTH_SHORT).show();
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


    void setItemBottomIcon(TextView textView , int resId){

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
            bean.setName(usernames[i]);
            bean.setAddress(userCity[i]);
            bean.setTime("11/12 12:2" +  i);
            bean.setContent(contents[i]);
            bean.setCommnunity(i * i + i * 4 + i);
            bean.setFavour(i * i * i + i * i);
            if(i==6 || i==10){
                bean.setIsCollect(true);
                bean.setIsFavour(true);
            }
            list.add(bean);
        }

        return list;
    }

}
