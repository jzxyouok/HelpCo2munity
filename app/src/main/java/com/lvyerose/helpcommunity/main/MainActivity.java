package com.lvyerose.helpcommunity.main;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lvyerose.helpcommunity.R;
import com.lvyerose.helpcommunity.base.BaseActivity;
import com.lvyerose.helpcommunity.base.FragmentControl;
import com.lvyerose.helpcommunity.found.FindFragment_;
import com.lvyerose.helpcommunity.helping.HelppingFragment_;
import com.lvyerose.helpcommunity.im.FriendFragment_;
import com.lvyerose.helpcommunity.im.MessageFragment_;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialdrawer.view.BezelImageView;
import com.mikepenz.materialize.util.UIUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

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
    //底部导航
    private AccountHeader headerResult = null;
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
    @ViewById(R.id.id_user_bimv)
    BezelImageView userIcon_bimv;


    @AfterViews
    void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        mTitle.setText(titles[0]);
        setSupportActionBar(toolbar);
        initDrawer(toolbar);
        initRadioButtonIcon();
    }

    @AfterViews
    void setFragments() {
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
    void initRadioButtonIcon() {
        for (int i = 0; i < mRgp.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) mRgp.getChildAt(i);
            Drawable myImage = getResources().getDrawable(resIconIds[i]);
            myImage.setBounds(1, 1, 56, 56);
            radioButton.setCompoundDrawables(null, myImage, null, null);

        }
    }


    /**
     * 初始化侧滑菜单
     *
     * @param toolbar
     */
    void initDrawer(Toolbar toolbar) {
        final IProfile profile = new ProfileDrawerItem().withName("蜀汉玫瑰").withEmail("lvyerose@163.com").withIcon(R.mipmap.ic_launcher);

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
//                .withCompactStyle(true)
                .withHeaderBackground(R.mipmap.slidingmenu_bg)
                .withDividerBelowHeader(true)
                .withSelectionListEnabledForSingleProfile(false)
                .withSelectionListEnabled(false)
                .addProfiles(
                        profile
//                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //IMPORTANT! notify the MiniDrawer about the profile click
                        miniResult.onProfileClick();
                        Toast.makeText(MainActivity.this, profile.getName().getText(), Toast.LENGTH_SHORT).show();
                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .build();

        //create the CrossfadeDrawerLayout which will be used as alternative DrawerLayout for the Drawer
        crossfadeDrawerLayout = new CrossfadeDrawerLayout(this);

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
//                .withToolbar(toolbar)
                .withDrawerLayout(crossfadeDrawerLayout)
                .withHasStableIds(true)
                .withDrawerWidthDp(72)
                .withAccountHeader(headerResult, true) //set the AccountHeader we created earlier for the header
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

                        //IMPORTANT notify the MiniDrawer about the onItemClick
                        return miniResult.onItemClick(drawerItem);

                    }
                })
                .withShowDrawerOnFirstLaunch(true)
                .build();
        //define maxDrawerWidth
        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));
        //add second view (which is the miniDrawer)
        miniResult = new MiniDrawer().withDrawer(result).withAccountHeader(headerResult);
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

    @Click(R.id.id_user_bimv)
    void userIconClicked() {
        if (result != null && !result.isDrawerOpen()) {
            result.openDrawer();
        }
    }

    @Click(R.id.id_add_imv)
    void addImvClicked() {
        Toast.makeText(getApplicationContext(), "Add", Toast.LENGTH_SHORT).show();
    }

}
