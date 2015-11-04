package com.lvyerose.helpcommunity.found;

import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lvyerose.helpcommunity.R;
import com.lvyerose.helpcommunity.base.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * author: lvyeRose
 * objective:   发现界面的二级界面  社区动态
 * mailbox: lvyerose@163.com
 * time: 15/11/2 11:24
 */
@EFragment(R.layout.fragment_community_dynamic)
public class CommunityDynamicFragment extends BaseFragment{
    @ViewById(R.id.my_image_view)
    SimpleDraweeView draweeView;

    @AfterViews
    void init(){
        Uri uri = Uri.parse("https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/fresco-logo.png");
        draweeView.setImageURI(uri);
    }
}
