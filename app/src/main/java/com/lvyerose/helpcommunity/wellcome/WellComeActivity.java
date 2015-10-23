package com.lvyerose.helpcommunity.wellcome;

import android.animation.Animator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lvyerose.helpcommunity.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import tyrantgit.explosionfield.ExplosionField;

@EActivity(R.layout.activity_wellcome)
public class WellComeActivity extends AppCompatActivity {
    @ViewById(R.id.id_wellComeTop_imv)
    ImageView mTop_imv;
    @ViewById(R.id.id_wellComeText1_imv)
    ImageView mLeft_imv;
    @ViewById(R.id.id_wellComeText2_imv)
    ImageView mRight_imv;
    @ViewById(R.id.id_bottom_tv)
    TextView mCountdown_tv;
    @ViewById(R.id.id_wctxt_shr)
    ShimmerTextView mWellCome_st;

    private ExplosionField mExplosionField;
    private Shimmer shimmer; // 文字高亮扫描类

    @AfterViews
    void init() {
        getSupportActionBar().hide();
        initShimmer();  // 初始化文字扫描动画类
        shimmer.start(mWellCome_st);    //开启文字动画扫描
        mExplosionField = ExplosionField.attach2Window(this);   //实例化粉碎动画

    }

    private void startAnimation(){
//        TranslateAnimation animation = new TranslateAnimation();
    }

    /**
     * 实例化动画扫描类及相关动画设置
     */
    private void initShimmer() {
        shimmer = new Shimmer();
        shimmer.setRepeatCount(0)
                .setDuration(3000)
                .setStartDelay(2000)
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


    @Background
    void timing(View root) {
        if (root instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) root;
            for (int i = 0; i < parent.getChildCount(); i++) {
                changTime(parent.getChildCount() - i - 1 + "");
                crushing(parent.getChildAt(i));
                try {
                    Thread.sleep(1000);
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
    void changTime(String times) {      //倒计时
        if (!mCountdown_tv.isShown()) {
            mCountdown_tv.setVisibility(View.VISIBLE);
        }
        mCountdown_tv.setText(times + "秒");
    }

    @UiThread
    void crushing(View view) {      //页面粉碎
        mExplosionField.explode(view);
    }

    @UiThread
    void jumpActivity() {        //页面自动跳转
        Toast.makeText(this, "跳转", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (shimmer != null) {         //取消动画扫描，释放内存
            shimmer.cancel();
        }
    }
}
