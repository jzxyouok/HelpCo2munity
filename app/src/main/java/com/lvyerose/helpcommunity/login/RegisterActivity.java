package com.lvyerose.helpcommunity.login;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lvyerose.helpcommunity.R;
import com.lvyerose.helpcommunity.base.BaseActivity;
import com.lvyerose.helpcommunity.main.MainActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {
    @ViewById(R.id.id_register_username_edt)
    EditText userNameEdt;
    @ViewById(R.id.id_register_password_edt)
    EditText passwordEdt;
    @ViewById(R.id.id_register_code_edt)
    EditText meCodedEdt;
    @ViewById(R.id.id_register_sendCode_btn)
    Button sendCodeBtn;
    @ViewById(R.id.id_register_sex_rgp)
    RadioGroup sexRgp;

    @AfterViews
    void initViews() {
        getSupportActionBar().hide();
        setRgpDraw();
    }

    /**
     * 动态设置RadioButton的图标，可以变换图标 达到合适的大小
     */
    private void setRgpDraw() {
        RadioButton radioButton = (RadioButton) sexRgp.getChildAt(0);
        Drawable myImage = getResources().getDrawable(R.drawable.sex_man_select);
        myImage.setBounds(1, 1, 56, 56);
        radioButton.setCompoundDrawables(null, null,  myImage, null);
        radioButton = (RadioButton) sexRgp.getChildAt(1);
        myImage = getResources().getDrawable(R.drawable.sex_woman_select);
        myImage.setBounds(1, 1, 66, 66);
        radioButton.setCompoundDrawables(null, null, myImage, null);
    }


    /**
     * 发送验证码操作
     */
    private void doSendCode() {
        String phone = userNameEdt.getText().toString().trim();
        if (!TextUtils.isEmpty(phone) && phone.length() == 11) {
            Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
            timeBackground();
        } else {
            Toast.makeText(this, "您输入的是手机号码吗？", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, passwordEdt.getText().toString() + "？", Toast.LENGTH_SHORT).show();

    }


    @Click(R.id.id_register_sendCode_btn)
    void clickSendCode() {
        sendCodeBtn.setEnabled(false);
        doSendCode();
    }

    @Click(R.id.id_register_login_btn)
    void toLogin() {
        LoginActivity_.intent(this).start();
        finish();
    }

    @Click(R.id.id_register_register_btn)
    void toRegister() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        MainActivity_.intent(this).start();
        finish();
    }

    @Background
    void timeBackground() {
        for (int i = 30; i >= 0; i--) {
            changButtonText(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @UiThread
    void changButtonText(int time) {
        if (time < 10) {
            sendCodeBtn.setText("0" + time + "秒");
        } else {
            sendCodeBtn.setText(time + "秒");
        }
        if (time == 0) {
            sendCodeBtn.setEnabled(true);
            sendCodeBtn.setText(getResources().getString(R.string.register_send_code_text));
        }
    }


}
