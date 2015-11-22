package com.lvyerose.helpcommunity.slidingmenu.me;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.actionsheet.ActionSheet;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.lvyerose.helpcommunity.R;
import com.lvyerose.helpcommunity.base.Const;
import com.lvyerose.helpcommunity.common.network.NetworkConst;
import com.lvyerose.helpcommunity.login.UserInfoBean;
import com.lvyerose.helpcommunity.widgets.cemare.CamareAndPhotoUtils;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.ResultCallback;
import com.zhy.http.okhttp.request.OkHttpRequest;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


@EActivity(R.layout.activity_my_message)
public class MyMessageActivity extends AppCompatActivity implements ActionSheet.ActionSheetListener {
    @Extra
    UserInfoBean userInfo;
    @ViewById(R.id.top_rendbg_view)
    LinearLayout topBg;
    @ViewById(R.id.id_me_top_photo)
    CircularImageView user_photo;
    @ViewById(R.id.id_me_topbg_sdwv)
    SimpleDraweeView topBgSdwv;

    @ViewById(R.id.id_me_id_tv)
    TextView user_id_tv;
    @ViewById(R.id.id_me_nick_tv)
    TextView nick_name_tv;
    @ViewById(R.id.id_me_sex_tv)
    TextView user_sex_tv;
    @ViewById(R.id.id_me_school_tv)
    TextView user_school_tv;
    @ViewById(R.id.id_me_dec_tv)
    TextView user_dec_tv;

    @AfterViews
    void initViews() {
        // 注册对象
        EventBus.getDefault().register(this);
        setAppBar(userInfo.getData().getNick_name());
        loadMessage(userInfo);
        loadTopImg(userInfo.getData().getUser_bg(), userInfo.getData().getUser_icon());
    }


    /**
     * 初始化顶部Actionbar
     *
     * @param title
     */
    private void setAppBar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(title);
        //通过CollapsingToolbarLayout修改字体颜色
        collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);//设置还没收缩时状态下字体颜色
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.all_appbar_title_color));//设置收缩后Toolbar上字体的颜色

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyMessageActivity.this.finish();
            }
        });
    }

    /**
     * 初始化顶部背景和用户头像
     *
     * @param bgUrl
     * @param photoUrl
     */
    private void loadTopImg(String bgUrl, String photoUrl) {
        topBgSdwv.setImageURI(Uri.parse(bgUrl));

        new OkHttpRequest.Builder()
                .url(photoUrl)
                .imageView(user_photo)
                .displayImage(null);
    }


    private void loadMessage(UserInfoBean userInfo) {
        user_id_tv.setText(userInfo.getData().getUser_id());
        nick_name_tv.setText(userInfo.getData().getNick_name());
        user_sex_tv.setText(userInfo.getData().getUser_sex().equals("0") ? "男" : "女");
        user_school_tv.setText(userInfo.getData().getUser_school());
        user_dec_tv.setText(userInfo.getData().getUser_dec());
    }

    @LongClick({
            R.id.id_click_nick_linear,
            R.id.id_click_school_linear,
            R.id.id_click_dec_linear})
    void onLongClick(View view) {
        switch (view.getId()) {
            case R.id.id_click_nick_linear:
                EditMessageActivity_.intent(this).type("1").title("昵称").content(userInfo.getData().getNick_name()).start();
                break;
            case R.id.id_click_school_linear:
                EditMessageActivity_.intent(this).type("2").title("学校").content(userInfo.getData().getUser_school()).start();
                break;
            case R.id.id_click_dec_linear:
                EditMessageActivity_.intent(this).type("3").title("一句话").content(userInfo.getData().getUser_dec()).start();
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_me_setting, menu);
        return true;
    }

    //菜单选项点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, CamareAndPhotoUtils.class);
        intent.putExtra(CamareAndPhotoUtils.TYPE, CamareAndPhotoUtils.SELECT_PHOTO);
        startActivityForResult(intent, Const.MESSAGE_BG_SELECT);
        return true;
    }

    //头像点击事件
    @Click(R.id.id_me_top_photo)
    void clickIcon() {
        showBottomMenu();
    }


    /**
     * 上传头像
     *
     * @param file
     */
    private void updataIcon(String type, File file) {
        if (file == null) {
            return;
        }

        Pair<String, File> files = new Pair<>("photo", file);
        Map<String, String> params = new HashMap<>();
        params.put("user_type", type);
        params.put("user_phone", userInfo.getData().getUser_phone());

        new OkHttpRequest.Builder()
                .url(NetworkConst.UPDATE_ICON)
                .params(params)
                .files(files)
                .upload(new ResultCallback<UserInfoBean>() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(UserInfoBean o) {
                        loadTopImg(o.getData().getUser_bg(), o.getData().getUser_icon());
                        EventBus.getDefault().post(o, "update_user");
                    }
                });
    }

    //图片选择回调结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final Uri uri = data.getData();
        String path = uri.getEncodedPath();
        final File file = new File(path);
        //选择头像图片结果
        if (requestCode == Const.MESSAGE_ICON_SELECT
                && resultCode == RESULT_OK) {
            updataIcon("1", file);

        }

        //选择背景图片结果
        if (requestCode == Const.MESSAGE_BG_SELECT
                && resultCode == RESULT_OK) {
            updataIcon("2", file);
        }


    }


    //修改头像的弹窗操作
    private void showBottomMenu() {
        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("取 消")
                .setOtherButtonTitles("拍照", "去相册找")
                .setCancelableOnTouchOutside(true)
                .setListener(this).show();
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancle) {

    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
        Intent intent = new Intent(this, CamareAndPhotoUtils.class);
        switch (index) {
            case 0:
                intent.putExtra(CamareAndPhotoUtils.TYPE, CamareAndPhotoUtils.SELECT_CAMARE);
                break;
            case 1:
                intent.putExtra(CamareAndPhotoUtils.TYPE, CamareAndPhotoUtils.SELECT_PHOTO);
                break;
        }
        startActivityForResult(intent, Const.MESSAGE_ICON_SELECT);
    }






    // 接收方法,默认的tag,执行在UI线程
    @Subscriber(tag = "update_user")
    private void updateUser(UserInfoBean user) {
        loadMessage(user);
    }





    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

}

