package com.lvyerose.helpcommunity.slidingmenu.me;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lvyerose.helpcommunity.R;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_edit_message)
public class EditMessageActivity extends AppCompatActivity {
    @Extra
    String title;
    @Extra
    String content;
    @Extra
    String type;            //修改属性的类型   1 昵称   2 学校   3 一句话

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
        Toast.makeText(this , "更新数据并返回" , Toast.LENGTH_SHORT).show();
    }

    @AfterTextChange(R.id.id_input_1line_edt)
    void edtChang(Editable content){
        if (content.toString().equals(this.content) || content.length() == 0){
            update_tv.setEnabled(false);
        }else {
            if("1".equals(type)){
                if(content.length()<=6){
                    update_tv.setEnabled(true);
                }else{
                    update_tv.setEnabled(false);
                }
            }else if ("2".equals(type)){
                if(content.length()<=10){
                    update_tv.setEnabled(true);
                }else{
                    update_tv.setEnabled(false);
                }
            }else if ("3".equals(type)){
                if(content.length()<=16){
                    update_tv.setEnabled(true);
                }else{
                    update_tv.setEnabled(false);
                }
            }
        }
    }


}
