package com.lvyerose.helpcommunity.wellcome;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lvyerose.helpcommunity.R;
import com.lvyerose.helpcommunity.main.MainActivity;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import tyrantgit.explosionfield.ExplosionField;

/**
 * author: lvyeRose
 * objective:   欢迎界面，主动画视觉元素实现
 * mailbox: lvyerose@163.com
 * time: 15/10/24 02:07
 */
@EActivity(R.layout.activity_wellcome)
public class WellComeActivity extends AppCompatActivity {
    @ViewById(R.id.id_wellComeTop_imv)
    ImageView mTop_imv;
    @ViewById(R.id.id_wellComeText1_imv)
    ImageView mLeft_imv;
    @ViewById(R.id.id_wellComeText2_imv)
    ImageView mRight_imv;
    @ViewById(R.id.id_wctxt_shr)
    ShimmerTextView mWellCome_st;

    private ExplosionField mExplosionField;
    private Shimmer shimmer; // 文字高亮扫描类

    @AfterViews
    void init() {
        getSupportActionBar().hide();
        startWdigtAnim(mTop_imv, mLeft_imv, mRight_imv, mWellCome_st);
        mExplosionField = ExplosionField.attach2Window(this);   //实例化粉碎动画
        initShimmer();  // 初始化文字扫描动画类
        shimmer.start(mWellCome_st);    //开启文字动画扫描

    }

    /**
     * 实例化动画扫描类及相关动画设置
     */
    private void initShimmer() {
        shimmer = new Shimmer();
        shimmer.setRepeatCount(0)
                .setDuration(2000)
                .setStartDelay(1800)
                .setDirection(Shimmer.ANIMATION_DIRECTION_LTR)
                .setAnimatorListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        timing(findViewById(R.id.wellCome_root));   //开启倒计时
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }


    /**
     * 实现动画加载效果
     *
     * @param topView    顶部头像控件
     * @param leftView   左出场效果控件
     * @param rightView  右出场效果控件
     * @param bottomView 底部欢迎页面
     */
    private void startWdigtAnim(View topView, View leftView, View rightView, View bottomView) {
        Animator animators_trans_top = ObjectAnimator.ofFloat(topView, "translationY", -4000, 0);
        Animator animators_trans_left = ObjectAnimator.ofFloat(leftView, "translationX", -4000, 0);
        Animator animators_trans_right = ObjectAnimator.ofFloat(rightView, "translationX", 4000, 0);
        Animator animators_trans_bottom = ObjectAnimator.ofFloat(bottomView, "translationY", 2000, 0);
        animators_trans_left.setupEndValues();
        animators_trans_top.setDuration(1000);
        animators_trans_left.setDuration(800);
        animators_trans_left.setStartDelay(500);
        animators_trans_right.setDuration(800);
        animators_trans_right.setStartDelay(500);
        animators_trans_bottom.setDuration(1500);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(getAlphAnim(leftView, 500), getAlphAnim(rightView, 500), animators_trans_top, animators_trans_left, animators_trans_right, animators_trans_bottom);
        set.start();
    }

    //设置控件动画前不可见
    private AnimatorSet getAlphAnim(View view, long delayTime) {
        AnimatorSet set = new AnimatorSet();
        Animator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 0, 0);
        Animator alphaAnimators = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
        alphaAnimator.setDuration(delayTime);
        set.playSequentially(alphaAnimator, alphaAnimators);
        return set;
    }

    @Background
    void timing(View root) {
        if (root instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) root;
            for (int i = 0; i < parent.getChildCount(); i++) {
                crushing(parent.getChildAt(i));
                try {
                    Thread.sleep(300);
                    //所有控件粉碎后进行页面自动跳转
                    if (i == parent.getChildCount() - 1) {
                        Thread.sleep(500);
                        jumpActivity();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    @UiThread
    void crushing(View view) {      //页面粉碎
        mExplosionField.explode(view);
    }

    @UiThread
    void jumpActivity() {        //页面自动跳转
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.wellcome_alpha_in, R.anim.wellcome_alpha_in);
        finish();
        overridePendingTransition(R.anim.wellcome_alpha_in, R.anim.wellcome_alpha_out);// 淡出淡入动画效果
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (shimmer != null) {         //取消动画扫描，释放内存
            shimmer.cancel();
        }
    }
}
