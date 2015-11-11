package com.lvyerose.helpcommunity.login;

import android.graphics.drawable.Drawable;
import android.widget.EditText;
import android.widget.Toast;

import com.lvyerose.helpcommunity.R;
import com.lvyerose.helpcommunity.base.BaseActivity;
import com.lvyerose.helpcommunity.main.MainActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @ViewById(R.id.id_login_username_edt)
    EditText userNameEdt;
    @ViewById(R.id.id_login_password_edt)
    EditText passWordEdt;

    @AfterViews
    void initViews(){
        getSupportActionBar().hide();
        setEditDraw();
    }

    //设置输入框效果
    private void setEditDraw(){
        Drawable myImage = getResources().getDrawable(R.drawable.login_username_select);
        myImage.setBounds(1, 1, 56, 56);
        userNameEdt.setCompoundDrawables(null, null, myImage, null);
        myImage = getResources().getDrawable(R.drawable.login_password_select);
        myImage.setBounds(1, 1, 56, 56);
        passWordEdt.setCompoundDrawables(null, null, myImage, null);
    }

    @Click(R.id.id_login_login_btn)
    void onLogin(){
        Toast.makeText(this , "登陆去了" , Toast.LENGTH_SHORT).show();
        MainActivity_.intent(this).start();
        this.finish();
    }

    @Click(R.id.id_login_register_btn)
    void onRegister(){
        Toast.makeText(this , "注册去..." , Toast.LENGTH_SHORT).show();

    }
    @Click(R.id.id_login_forgot_tv)
    void onForgot(){
        Toast.makeText(this , "忘记密码就算了吧..." , Toast.LENGTH_SHORT).show();

    }

}
