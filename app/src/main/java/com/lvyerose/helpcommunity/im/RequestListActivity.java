package com.lvyerose.helpcommunity.im;

import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.lvyerose.helpcommunity.R;
import com.lvyerose.helpcommunity.base.BaseActivity;
import com.lvyerose.helpcommunity.base.Const;
import com.lvyerose.helpcommunity.common.network.NetworkServer;
import com.lvyerose.helpcommunity.main.AppendFriendBean;
import com.lvyerose.helpcommunity.utils.ACache;
import com.lvyerose.helpcommunity.utils.NToast;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.ResultCallback;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.activity_request_list)
public class RequestListActivity extends BaseActivity {

    @ViewById(R.id.id_title_tv)
    TextView title;
    @ViewById(R.id.id_request_lv)
    ListView listView;
    @ViewById(R.id.id_is_empty_view)
    TextView isEmpty;
    @AfterViews
    void initViews() {
        listView.setEmptyView(isEmpty);
        setTopView();
        getRequest();
    }

    private void setTopView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        title.setText("好友请求");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getRequest(){
        NetworkServer.getRequestFriend(ACache.get(RequestListActivity.this).getAsString(Const.ACACHE_USER_PHONE),
                new ResultCallback<RequestFriendBean>() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(RequestFriendBean requestFriendBean) {
                        if(requestFriendBean != null && "success".equals(requestFriendBean.getStatus())){
                            setRequestAdapter(requestFriendBean.getData());
                        }
                    }
                });




    }

    private void setRequestAdapter(List<RequestFriendBean.DataEntity> list){

        listView.setAdapter(new QuickAdapter<RequestFriendBean.DataEntity>(this , R.layout.item_request_friend_list , list) {

            @Override
            protected void convert(BaseAdapterHelper helper, final RequestFriendBean.DataEntity item) {
                LinearLayout viewSelect = (LinearLayout) helper.getView().findViewById(R.id.item_state_select);
                View viewAgreed = helper.getView().findViewById(R.id.item_state_agreed);
                View viewReject = helper.getView().findViewById(R.id.item_state_reject);
                View viewWait = helper.getView().findViewById(R.id.item_state_wait);

                viewSelect.setVisibility(View.GONE);
                viewAgreed.setVisibility(View.GONE);
                viewReject.setVisibility(View.GONE);
                viewWait.setVisibility(View.GONE);
                switch (item.getState()){
                    case "0":
                        if (item.getType().equals("1")){        //我发送出去的  等待对方验证
                            viewWait.setVisibility(View.VISIBLE);

                        }else if (item.getType().equals("2")){  //对方发送给我的   等待我选择
                            viewSelect.setVisibility(View.VISIBLE);
                            viewSelect.getChildAt(0).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    NetworkServer.agreedFriendRequest(ACache.get(RequestListActivity.this).getAsString(Const.ACACHE_USER_PHONE),
                                            item.getUser_phone(),
                                            new ResultCallback<AppendFriendBean>() {
                                                @Override
                                                public void onError(Request request, Exception e) {
                                                }

                                                @Override
                                                public void onResponse(AppendFriendBean o) {
                                                    if(o!=null && o.getStatus().equals("success")){
                                                        NToast.shortToast(RequestListActivity.this , o.getMessage());
                                                        getRequest();
                                                    }
                                                }
                                            });
                                }
                            });
                            viewSelect.getChildAt(1).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    NetworkServer.rejectFriendRequest(ACache.get(RequestListActivity.this).getAsString(Const.ACACHE_USER_PHONE),
                                            item.getUser_phone(),
                                            new ResultCallback<AppendFriendBean>() {
                                                @Override
                                                public void onError(Request request, Exception e) {
                                                    NToast.shortToast(RequestListActivity.this , "网络异常");
                                                }

                                                @Override
                                                public void onResponse(AppendFriendBean o) {
                                                    if(o!=null && o.getStatus().equals("success")){
                                                        NToast.shortToast(RequestListActivity.this , o.getMessage());
                                                        getRequest();
                                                    }

                                                }
                                            });
                                }
                            });
                        }
                        break;
                    case "1":           //已经被接受
                        viewAgreed.setVisibility(View.VISIBLE);

                        break;
                    case "2":       //已经被拒绝
                        viewReject.setVisibility(View.VISIBLE);

                        break;
                }

                SimpleDraweeView simpleDraweeView = (SimpleDraweeView) helper.getView().findViewById(R.id.id_item_request_friend_Img_sdwv);
                simpleDraweeView.setImageURI(Uri.parse(item.getUser_icon()));
                helper.setText(R.id.id_item_request_friend_name_tv, item.getNick_name());


            }
        });


    }

}
