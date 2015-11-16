package com.lvyerose.helpcommunity.login;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lvyerose.helpcommunity.R;
import com.lvyerose.helpcommunity.base.BaseActivity;
import com.lvyerose.helpcommunity.common.network.NetworkServer;
import com.lvyerose.helpcommunity.main.MainActivity_;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.ResultCallback;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import cn.pedant.SweetAlert.SweetAlertDialog;

@EActivity(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {
    SweetAlertDialog pDialog;
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
    @ViewById(R.id.id_sex_man_rbtn)
    RadioButton manRbtn;


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
        radioButton.setCompoundDrawables(null, null, myImage, null);
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

            NetworkServer.getMobCode(phone, new ResultCallback<MobileCodeBean>() {
                @Override
                public void onError(Request request, Exception e) {
                    sendCodeBtn.setEnabled(true);
                    sendCodeBtn.setText(getResources().getString(R.string.register_send_code_text));
                }

                @Override
                public void onResponse(MobileCodeBean mobileCodeBean) {
                    if (mobileCodeBean != null && "success".equals(mobileCodeBean.getStatus())) {
                        Toast.makeText(RegisterActivity.this, mobileCodeBean.getData(), Toast.LENGTH_LONG).show();
                        timeBackground();
                    }else{
                        Toast.makeText(RegisterActivity.this, mobileCodeBean.getMessage(), Toast.LENGTH_LONG).show();
                        sendCodeBtn.setEnabled(true);
                        sendCodeBtn.setText(getResources().getString(R.string.register_send_code_text));
                    }
                }
            });
        } else {
            Toast.makeText(this, "您输入的是手机号码吗？", Toast.LENGTH_SHORT).show();
            sendCodeBtn.setEnabled(true);
        }

    }

    /**
     * 联网注册方法
     */
    private void doRegister(String username, String password, String code, int sex) {
        dialogs();
        NetworkServer.toRegister(username , password , code , sex , new ResultCallback<UserInfoBean>() {
            @Override
            public void onError(Request request, Exception e) {
                cancelDialog();
            }

            @Override
            public void onResponse(UserInfoBean userInfoBean) {
                cancelDialog();
                Toast.makeText(RegisterActivity.this , userInfoBean.getMessage() , Toast.LENGTH_LONG).show();
                if(userInfoBean != null && "success".equals(userInfoBean.getStatus())){
                    MainActivity_.intent(RegisterActivity.this).user_info(userInfoBean).start();
                    finish();
                }
            }
        });
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
        String username = userNameEdt.getText().toString().trim();
        String password = passwordEdt.getText().toString().trim();
        String code = meCodedEdt.getText().toString().trim();
        if (TextUtils.isEmpty(username) ||
                TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(code)) {
            Toast.makeText(this, "信息填写不完整", Toast.LENGTH_SHORT).show();
            return;
        }

        doRegister(username, password, code, getSelectSex());

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

    /**
     * 获取用户选择的性别  0表示男   1表示女
     *
     * @return 返回性别对应的int值
     */
    private int getSelectSex() {
        if (manRbtn.isChecked()) {
            return 0;
        } else {
            return 1;
        }
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
