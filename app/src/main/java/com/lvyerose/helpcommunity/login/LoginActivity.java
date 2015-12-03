package com.lvyerose.helpcommunity.login;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.lvyerose.helpcommunity.R;
import com.lvyerose.helpcommunity.base.BaseActivity;
import com.lvyerose.helpcommunity.base.Const;
import com.lvyerose.helpcommunity.common.network.NetworkServer;
import com.lvyerose.helpcommunity.im.ConnectListen;
import com.lvyerose.helpcommunity.im.IMUtils;
import com.lvyerose.helpcommunity.main.MainActivity_;
import com.lvyerose.helpcommunity.utils.ACache;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.ResultCallback;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.pedant.SweetAlert.SweetAlertDialog;

@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    private SweetAlertDialog pDialog;

    @ViewById(R.id.id_login_username_edt)
    EditText userNameEdt;
    @ViewById(R.id.id_login_password_edt)
    EditText passWordEdt;

    @AfterViews
    void initViews() {
        getSupportActionBar().hide();
        setEditDraw();
    }

    //设置输入框效果
    private void setEditDraw() {
        Drawable myImage = getResources().getDrawable(R.drawable.login_username_select);
        myImage.setBounds(1, 1, 56, 56);
        userNameEdt.setCompoundDrawables(null, null, myImage, null);
        myImage = getResources().getDrawable(R.drawable.login_password_select);
        myImage.setBounds(1, 1, 56, 56);
        passWordEdt.setCompoundDrawables(null, null, myImage, null);
    }

    private void netWorkLogin(String username, String password) {
        dialogs();
        NetworkServer.toLogin(username, password, new ResultCallback<UserInfoBean>() {
            @Override
            public void onError(Request request, Exception e) {
                cancelDialog();
            }

            @Override
            public void onResponse(final UserInfoBean userInfoBean) {
                cancelDialog();
                Toast.makeText(LoginActivity.this, userInfoBean.getMessage(), Toast.LENGTH_LONG).show();
                if (userInfoBean != null && "success".equals(userInfoBean.getStatus())) {
                    IMUtils imUtils = new IMUtils(LoginActivity.this, new ConnectListen() {
                        @Override
                        public void success(String userId) {

                            IMUtils.Mobclick(userId);
                            //设置自己的信息缓存
                            ACache aCache = ACache.get(LoginActivity.this);
                            aCache.put(Const.ACACHE_USER_ID, userId);
                            aCache.put(Const.ACACHE_USER_PHONE, userInfoBean.getData().getUser_phone());
                            aCache.put(Const.ACACHE_USER_ICON , userInfoBean.getData().getUser_icon());
                            aCache.put(Const.ACACHE_USER_NAME , userInfoBean.getData().getNick_name());
                            startActivity(userInfoBean);
                        }

                        @Override
                        public void fail(String message) {

                        }
                    });
                    imUtils.connect(userInfoBean.getData().getUser_token());

                }
            }
        });

    }

    @Click(R.id.id_login_login_btn)
    void onLogin() {
        String userName = userNameEdt.getText().toString().trim();
        String password = passWordEdt.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        netWorkLogin(userName, password);

    }

    @Click(R.id.id_login_register_btn)
    void onRegister() {
        RegisterActivity_.intent(this).start();
        finish();

    }

    @Click(R.id.id_login_forgot_tv)
    void onForgot() {
        Toast.makeText(this, "忘记密码就算了吧...", Toast.LENGTH_SHORT).show();
    }


    private void dialogs() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void cancelDialog() {
        if (pDialog != null) {
            pDialog.cancel();
        }
    }


    void startActivity(UserInfoBean userInfoBean) {
        MainActivity_.intent(LoginActivity.this).user_info(userInfoBean).start();
        finish();

    }
}
