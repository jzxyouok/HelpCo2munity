package com.lvyerose.helpcommunity.found;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.library.Info;
import com.bm.library.PhotoView;
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
import java.util.Random;

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
    int[] url = new int[]{
            R.drawable.defult_user,
            R.drawable.user_02,
            R.drawable.user_03,
            R.drawable.user_04
    };
    String [] urls = new String[]{
//            "http://img2.3lian.com/2014/f4/166/70.jpg",
//            "http://img2.3lian.com/2014/f4/166/70.jpg",
//            "http://img2.3lian.com/2014/f4/166/70.jpg",
//            "http://img2.3lian.com/2014/f4/166/70.jpg",
//            "http://img2.3lian.com/2014/f4/166/70.jpg",
//            "http://img2.3lian.com/2014/f4/166/70.jpg",
//            "http://img2.3lian.com/2014/f4/166/70.jpg",
//            "http://img2.3lian.com/2014/f4/166/70.jpg",
//            "http://img2.3lian.com/2014/f4/166/70.jpg",
//            "http://img2.3lian.com/2014/f4/166/70.jpg"
//
           "http://www.lvyerose.com/helpping/res/cach/03i58PICGda_1024.jpg",
           "http://www.lvyerose.com/helpping/res/cach/31.jpg",
           "http://www.lvyerose.com/helpping/res/cach/51k58PICU68.jpg",
           "http://www.lvyerose.com/helpping/res/cach/70.jpg",
           "http://www.lvyerose.com/helpping/res/cach/669913_112401044_2.jpg",
           "http://www.lvyerose.com/helpping/res/cach/3219116_190202695111_2.jpg",
           "http://www.lvyerose.com/helpping/res/cach/4653862_232742449000_2.jpg",
           "http://www.lvyerose.com/helpping/res/cach/2008328205629575_2.jpg",
           "http://www.lvyerose.com/helpping/res/cach/20131218134500022.jpg",
           "http://www.lvyerose.com/helpping/res/cach/sy_201207262122036510.jpg",
    };


    SweetAlertDialog pDialog;

    @ViewById(R.id.id_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @ViewById(R.id.id_friend_lv)
    ListView mListView;
    QuickAdapter<BeanFriendData> mAdapter;
    List<BeanFriendData> mListData;
    private SparseBooleanArray mCollapsedStatus = new SparseBooleanArray();
    /** 图片缩放功能 */
//    View mParent;
//    View mBg;
    PhotoView mPhotoView;

    AlphaAnimation in = new AlphaAnimation(0, 1);
    AlphaAnimation out = new AlphaAnimation(1, 0);


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
                mAdapter.replaceAll(addData());
            }
            Toast.makeText(getActivity(), "加载完成", Toast.LENGTH_SHORT).show();
        }
    }

    @AfterViews
    void initZoom(){
        in.setDuration(300);
        out.setDuration(300);

//        mParent = getActivity().findViewById(R.id.id_main_photo_parent);
//        mBg = getActivity().findViewById(R.id.id_main_photo_bg);
//        mPhotoView = (PhotoView) getActivity().findViewById(R.id.id_main_photo_ptv);
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
                //动态类型为图文的话执行如下图片列表
                if (item.getType() != null && item.getType().equals("TYPE_IMG")) {
                    GridView grv = (GridView) helper.getView().findViewById(R.id.id_item_imgs_gdv);
                    grv.setVisibility(View.VISIBLE);
                    //GridView的每个Item的点击事件设置...
                    grv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                            PhotoView p = (PhotoView) view;
//                            Info mInfo = p.getInfo();
//                            HolderInfo holderInfo = new HolderInfo();
//                            String urlItem = adapterView.getAdapter().getItem(position).toString();
//                            OkHttpClientManager.getDisplayImageDelegate().displayImage(mPhotoView,
//                                    urlItem);
////                            mPhotoView.setImageResource(url[position]);
//                            mBg.startAnimation(in);
//                            mParent.setVisibility(View.VISIBLE);
//                            mPhotoView.animaFrom(mInfo);
//                            holderInfo.mInfo = mInfo;
//                            holderInfo.mPhotoView = p;
//                            p.setVisibility(View.GONE);
//                            mPhotoView.setTag(holderInfo);
//

                        }
                    });

                    //设置每个item的GridView适配器
                    grv.setAdapter(new QuickAdapter<String>(getActivity(),
                            R.layout.item_dynamic_gridview,
                            item.getImageUrl()) {
                        @Override
                        protected void convert(BaseAdapterHelper helper, String item) {

                            //GridView的每个Item的内容设置，并且设置好Info点击动画
                            final SimpleDraweeView photoView = (SimpleDraweeView) helper.getView().findViewById(R.id.id_item_grdview_ptv);
//                            OkHttpClientManager.getDisplayImageDelegate().displayImage(photoView,
//                                    item);
                            photoView.setImageURI(Uri.parse(item));
//                            photoView.setImageResource(url[helper.getPosition()]);
//                            mPhotoView.enable();
//                            mPhotoView.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    HolderInfo holderInfo = (HolderInfo) v.getTag();
//                                    mBg.startAnimation(out);
//                                    if(holderInfo != null){
//                                        Info mInfo = holderInfo.mInfo;
//                                        final PhotoView mView = holderInfo.mPhotoView;
//                                        if(mInfo!=null){
//                                            mPhotoView.animaTo(mInfo, new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    mParent.setVisibility(View.GONE);
//                                                    mView.setVisibility(View.VISIBLE);
//                                                }
//                                            });
//                                        }
//                                    }
//                                }
//                            });
                        }
                    });
                } else {
                    helper.getView().findViewById(R.id.id_item_imgs_gdv).setVisibility(View.GONE);
                }

                //图文动态和纯文本动态 的公用数据设置
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
            bean.setType("TYPE_IMG");
            List<String> listUrl = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                Random random = new Random();
                listUrl.add(urls[random.nextInt(10)]);
            }
            bean.setImageUrl(listUrl);
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


    /**
     * gridView的每个Item点击弹出时候说需数据结构化
     */
    class HolderInfo{
        PhotoView mPhotoView;
        Info mInfo;

    }

}
