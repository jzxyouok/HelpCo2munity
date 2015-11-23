package com.lvyerose.helpcommunity.main;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lvyerose.helpcommunity.R;
import com.lvyerose.helpcommunity.base.BaseActivity;
import com.lvyerose.helpcommunity.base.FragmentControl;
import com.lvyerose.helpcommunity.found.FindFragment_;
import com.lvyerose.helpcommunity.helping.HelppingFragment_;
import com.lvyerose.helpcommunity.im.FriendFragment_;
import com.lvyerose.helpcommunity.im.MessageFragment_;
import com.lvyerose.helpcommunity.login.UserInfoBean;
import com.lvyerose.helpcommunity.slidingmenu.me.MyMessageActivity_;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * author: lvyeRose
 * objective:   首页功能，负责底部菜单切换，顶部导航栏，切换侧滑菜单 等
 * mailbox: lvyerose@163.com
 * time: 15/9/25 17:03
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @Extra("user_info")
    UserInfoBean user_info;
    //侧滑菜单
    private Drawer result = null;
    private MiniDrawer miniResult = null;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;
    @ViewById(R.id.id_title_tv)
    TextView mTitle;
    //切换控制
    @ViewById(R.id.id_foot_rgp)
    RadioGroup mRgp;
    final int mLayoutId = R.id.layout_content_main;
    private FragmentControl mFragmentControl;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private int[] resIconIds = new int[]{
            R.drawable.foot_find_select,
            R.drawable.foot_friend_select,
            R.drawable.foot_message_select,
            R.drawable.foot_message_select,
            R.drawable.foot_helpping_select
    };

    @StringArrayRes(R.array.main_title_arrays)
    String[] titles;
    @ViewById(R.id.id_user_sdvw)
    SimpleDraweeView userIcon_sdvw;

    //HeardViews
    SimpleDraweeView headUserIcon;
    TextView headNick;
    TextView headPhone;


    @AfterViews
    void init() {
        EventBus.getDefault().register(this);
        initToolbar();
        initDrawer();
        initRadioButtonIcon();
        updateUser(user_info);
        setFragments();
    }
    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        mTitle.setText(titles[0]);
        setSupportActionBar(toolbar);
    }
    private void setFragments() {
        mFragmentList.add(new FindFragment_());
        mFragmentList.add(new FriendFragment_());
        mFragmentList.add(new Fragment());
        mFragmentList.add(new MessageFragment_());
        mFragmentList.add(new HelppingFragment_());
        mFragmentControl = new FragmentControl(getSupportFragmentManager(), mFragmentList, mLayoutId, mRgp);
        mFragmentControl.setOnRgsExtraCheckedChangedListener(new FragmentControl.OnRgsExtraCheckedChangedListener() {
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                mTitle.setText(titles[index]);
            }
        });
    }


    /**
     * 动态设置RadioButton的图标，可以变换图标 达到合适的大小
     */
    private void initRadioButtonIcon() {
        for (int i = 0; i < mRgp.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) mRgp.getChildAt(i);
            Drawable myImage = getResources().getDrawable(resIconIds[i]);
            myImage.setBounds(1, 1, 56, 56);
            radioButton.setCompoundDrawables(null, myImage, null, null);

        }
    }


    /**
     * 初始化侧滑菜单
     */
    private void initDrawer() {
        crossfadeDrawerLayout = new CrossfadeDrawerLayout(this);

        View headView = LayoutInflater.from(this).inflate(R.layout.nav_header_main , null);
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyMessageActivity_.intent(MainActivity.this).userInfo(user_info).start();
                result.closeDrawer();
            }
        });

        //初始化头部控件
        headUserIcon = (SimpleDraweeView) headView.findViewById(R.id.id_sliding_icon_sdwv);
        headPhone = (TextView) headView.findViewById(R.id.id_sliding_phone);
        headNick = (TextView) headView.findViewById(R.id.id_sliding_nick);

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                        .withHeader(headView)
//                .withToolbar(toolbar)
                .withDrawerLayout(crossfadeDrawerLayout)
                .withHasStableIds(true)
                .withDrawerWidthDp(72)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_me_zone).withIcon(FontAwesome.Icon.faw_user_md),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_me_collect).withIcon(FontAwesome.Icon.faw_tags),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_me_participate).withIcon(FontAwesome.Icon.faw_globe),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_chicken_soup).withIcon(FontAwesome.Icon.faw_book),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_advice_feedback).withIcon(FontAwesome.Icon.faw_edit),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_about_us).withIcon(FontAwesome.Icon.faw_info_circle),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_system_setting).withIcon(FontAwesome.Icon.faw_cog)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            Toast.makeText(MainActivity.this, ((Nameable) drawerItem).getName().getText(MainActivity.this), Toast.LENGTH_SHORT).show();
                        }

                        return miniResult.onItemClick(drawerItem);

                    }
                })
                .withShowDrawerOnFirstLaunch(true)
                .build();
        //define maxDrawerWidth
        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));
        //add second view (which is the miniDrawer)
        miniResult = new MiniDrawer().withDrawer(result);
        //build the view for the MiniDrawer
        View view = miniResult.build(this);
        //set the background of the MiniDrawer as this would be transparent
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));
        //we do not have the MiniDrawer view during CrossfadeDrawerLayout creation so we will add it here
        crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade(400);
                //only close the drawer if we were already faded and want to close it now
                if (isFaded) {
                    result.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
            }
        });


    }

    /**
     * 返回键 是否关闭侧滑菜单
     */
    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            new SweetAlertDialog(this)
                    .setTitleText("您确定不再找找Bug吗？")
                    .setCancelText(" 退 出 ")
                    .setConfirmText(" 再看看 ")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            MainActivity.this.finish();
                        }
                    })
                    .show();
        }
    }

    @Click(R.id.id_user_sdvw)
    void userIconClicked() {
        if (result != null && !result.isDrawerOpen()) {
            result.openDrawer();
        }
    }

    @Click(R.id.id_add_imv)
    void addImvClicked() {
        Toast.makeText(getApplicationContext(), "Add", Toast.LENGTH_SHORT).show();
    }



    //更新主页用户信息
    // 接收方法,默认的tag,执行在UI线程
    @Subscriber(tag = "update_user")
    private void updateUser(UserInfoBean user) {
        setUserInfo(user);
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 设置用户信息
     * @param user
     */
    private void setUserInfo(UserInfoBean user){
        if (user != null && user.getData() != null) {
            if(user != user_info){
                user_info = user;
            }
            Uri uri = Uri.parse(user.getData().getUser_icon());
            userIcon_sdvw.setImageURI(uri);

            headUserIcon.setImageURI(uri);
            headPhone.setText(user.getData().getUser_phone());
            headNick.setText(user.getData().getNick_name());
        }
    }



}
