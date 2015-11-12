package com.lvyerose.helpcommunity.login;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.lvyerose.helpcommunity.R;
import com.lvyerose.helpcommunity.base.BaseActivity;
import com.lvyerose.helpcommunity.main.MainActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
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

    @UiThread(delay = 3000)
    void testLogin(String username, String password) {
        if (username.equals("18311321513") && password.equals("120407yyt")) {
            cancelDialog();
            Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
            MainActivity_.intent(this).start();
            this.finish();
        }else{
            cancelDialog();
            Toast.makeText(this, "账号或密码不匹配", Toast.LENGTH_SHORT).show();
        }
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
        dialogs();
        testLogin(userName , password);

    }

    @Click(R.id.id_login_register_btn)
    void onRegister() {
        Toast.makeText(this, "注册去...", Toast.LENGTH_SHORT).show();
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
}
