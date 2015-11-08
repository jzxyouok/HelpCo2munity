package com.lvyerose.helpcommunity.found;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
public class CommunityDynamicFragment extends BaseFragment {
    @ViewById(R.id.my_image_view)
    SimpleDraweeView draweeView;

    @ViewById(R.id.img_1)
    ImageView mImg1;
    RelativeLayout frameLayout;
    @AfterViews
    void init() {
//        mMainImv = (ImageView) getActivity().findViewById(R.id.id_main_photo_imv);
//        bgView = getActivity().findViewById(R.id.id_main_photo_bg);
//        frameLayout = (RelativeLayout) getActivity().findViewById(R.id.id_main_photo_parent);
        Uri uri = Uri.parse("https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/fresco-logo.png");
        draweeView.setImageURI(uri);
//        mImg1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                animIn(view, frameLayout);
//            }
//        });
//
//        frameLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity() , " 点击 " , Toast.LENGTH_SHORT).show();
//                animOut(mImg1, view);
//            }
//        });
    }


//    void animIn(View view1, View view2) {
//        frameLayout.setVisibility(View.VISIBLE);
//        view2.setClickable(true);
//        int [] xy = new int[2];
//        view1.getLocationOnScreen(xy);
//        Display display = getActivity().getWindowManager().getDefaultDisplay();
//        float x = ((float)xy[0]+(float)view1.getWidth()/2)/getView().getWidth();
//        float y = ((float)xy[1]-MethodUtils.getStatusBarHeight(getActivity())+(float)view1.getHeight()/2)/display.getHeight();
//        view2.setVisibility(View.VISIBLE);
//        final ScaleAnimation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
//                Animation.RELATIVE_TO_SELF, x, Animation.RELATIVE_TO_SELF, y);
//        animation.setDuration(300);//设置动画持续时间
//        /** 常用方法 */
//        //animation.setRepeatCount(int repeatCount);//设置重复次数
//        //animation.setFillAfter(boolean);//动画执行完后是否停留在执行完的状态
//        //animation.setStartOffset(long startOffset);//执行前的等待时间
//        view2.clearAnimation();
//        view2.setAnimation(animation);
//        /** 开始动画 */
//        animation.startNow();
//    }
//
//    void animOut(View view1, final View view2) {
//        int [] xy = new int[2];
//        view1.getLocationOnScreen(xy);
//        float x = ((float)xy[0]+(float)view1.getWidth()/2)/getView().getWidth();
//        float y = ((float)xy[1]-MethodUtils.getStatusBarHeight(getActivity())+(float)view1.getHeight()/2)/ MethodUtils.getActivityHgOrWd(getActivity() , MethodUtils.ACTIVITY_HEIGHT);
////        view2.setVisibility(View.VISIBLE);
//        final ScaleAnimation animation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
//                Animation.RELATIVE_TO_SELF, x, Animation.RELATIVE_TO_SELF, y);
//        animation.setDuration(500);//设置动画持续时间
//        /** 常用方法 */
//        //animation.setRepeatCount(int repeatCount);//设置重复次数
//        animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
//        //animation.setStartOffset(long startOffset);//执行前的等待时间
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                frameLayout.setVisibility(View.GONE);
//                frameLayout.setClickable(false);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        view2.clearAnimation();
//        view2.setAnimation(animation);
//        /** 开始动画 */
//        animation.startNow();
//
//
//    }

}
