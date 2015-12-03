package com.lvyerose.helpcommunity.slidingmenu.me;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lvyerose.helpcommunity.R;
import com.lvyerose.helpcommunity.base.BaseActivity;
import com.lvyerose.helpcommunity.base.Const;
import com.lvyerose.helpcommunity.common.network.NetworkServer;
import com.lvyerose.helpcommunity.login.UserInfoBean;
import com.lvyerose.helpcommunity.utils.ACache;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.ResultCallback;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;

@EActivity(R.layout.activity_edit_message)
public class EditMessageActivity extends BaseActivity {
    @Extra
    String title;
    @Extra
    String content;
    @Extra
    String type;            //修改属性的类型   1 昵称   2 学校   3 一句话

    UserInfoBean userInfoBean;

    @ViewById(R.id.id_title_tv)
    TextView title_tv;

    @ViewById(R.id.id_title_update_tv)
    TextView update_tv;

    @ViewById(R.id.id_input_1line_edt)
    EditText inputOneLineEdt;

    @AfterViews
    void initViews() {
        setToolbar();
        setEdtContent();

    }

    private void setEdtContent() {
        inputOneLineEdt.setText(content);
        inputOneLineEdt.setSelection(content.length());


    }



    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        title_tv.setText(title);
        setSupportActionBar(toolbar);
    }

    @Click(R.id.id_input_cancel_imbtn)
    void cancelEdt() {
        inputOneLineEdt.setText("");
    }

    @Click(R.id.id_title_cancel_tv)
    void cancelClick() {
        this.finish();
    }

    @Click(R.id.id_title_update_tv)
    void updateClick() {
        updateMessage();
    }

    @AfterTextChange(R.id.id_input_1line_edt)
    void edtChang(Editable content){
        if (content.toString().equals(this.content) || content.length() == 0){
            update_tv.setEnabled(false);
        }else {
            if("1".equals(type)){
                if(content.length()<=8){
                    update_tv.setEnabled(true);
                }else{
                    update_tv.setEnabled(false);
                }
            }else if ("2".equals(type)){
                if(content.length()<=12){
                    update_tv.setEnabled(true);
                }else{
                    update_tv.setEnabled(false);
                }
            }else if ("3".equals(type)){
                if(content.length()<=22){
                    update_tv.setEnabled(true);
                }else{
                    update_tv.setEnabled(false);
                }
            }
        }
    }

    private void updateMessage(){
        ACache aCache = ACache.get(this);
        NetworkServer.updateMessage(aCache.getAsString(Const.ACACHE_USER_PHONE), type, inputOneLineEdt.getText().toString().trim(),
                new ResultCallback<UserInfoBean>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(EditMessageActivity.this , "无效网络连接" , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(UserInfoBean userInfoBean) {
                        if(userInfoBean!=null && "success".equals(userInfoBean.getStatus())){
                            EventBus.getDefault().post( userInfoBean , "update_user");
                            EditMessageActivity.this.finish();
                        }else {
                            Toast.makeText(EditMessageActivity.this , "更新失败" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
