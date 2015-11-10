package com.lvyerose.helpcommunity.login;

import android.graphics.drawable.Drawable;
import android.widget.EditText;

import com.lvyerose.helpcommunity.R;
import com.lvyerose.helpcommunity.base.BaseActivity;

import org.androidannotations.annotations.AfterViews;
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

}
