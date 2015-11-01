package com.lvyerose.helpcommunity.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lvyerose.helpcommunity.R;

import java.util.List;

/**
 * author: lvyeRose
 * objective: RadioGroup + Fragment 切换控制器
 * mailbox: lvyerose@163.com
 * time: 15/11/1 20:01
 */
public class FragmentControl implements RadioGroup.OnCheckedChangeListener{
    private boolean isAnimation = true; //设置切换fragment是否带动画  默认为true
    private List<Fragment> fragments; // 一个tab页面对应一个Fragment
    private RadioGroup rgs; // 用于切换tab
    private FragmentManager fragmentManager; // Fragment所属的Activity
    private int fragmentContentId; // Activity中所要被替换的区域的id
    private int currentTab; // 当前Tab页面索引
    private int contralBreakOff = -1;
    private OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener; // 用于让调用者在切换tab时候增加新的功能
    private OnBreakOffListener onBreakOffListener; //用于中断回调
    private int breakOffindex;  //中断时的前一个tab索引

    public FragmentControl(FragmentManager fragmentManager , List<Fragment> fragments, int fragmentContentId, RadioGroup rgs) {
        this.fragments = fragments;
        this.rgs = rgs;
        this.fragmentManager = fragmentManager;
        this.fragmentContentId = fragmentContentId;
        // 默认显示第一页
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(fragmentContentId, fragments.get(0));
        ft.addToBackStack(fragments.get(0).getClass().getName());
        ft.commit();
        rgs.setOnCheckedChangeListener(this);

    }

    /**
     *  设置是否带动画切换
     * @param isAnimation  默认为true
     */
    public void setIsAnimation(boolean isAnimation) {
        this.isAnimation = isAnimation;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        for(int i = 0; i < rgs.getChildCount(); i++){
            if(rgs.getChildAt(i).getId() == checkedId){
                if(contralBreakOff == i && currentTab !=1){
                    breakOffindex = currentTab;
                }
                Fragment fragment = fragments.get(i);
                FragmentTransaction ft = obtainFragmentTransaction(i);
//                getCurrentFragment().onPause(); // 暂停当前tab
                getCurrentFragment().onStop(); // 暂停当前tab
                if(fragment.isAdded()){
                    fragment.onStart(); // 启动目标tab的onStart()
                    fragment.onResume(); // 启动目标tab的onResume()
                }else{
                    ft.addToBackStack(fragment.getClass().getName());
                    ft.add(fragmentContentId, fragment);
                }
                showTab(i); // 显示目标tab
                ft.commit();

                // 如果设置了切换tab额外功能功能接口
                if(null != onRgsExtraCheckedChangedListener){
                    onRgsExtraCheckedChangedListener.OnRgsExtraCheckedChanged(radioGroup, checkedId, i);
                }
//                //中断设置
//                if(contralBreakOff == i){
//                    if(onBreakOffListener !=null){
//                        onBreakOffListener.OnBreakOffChanged(breakOffindex);
//                    }
//                    setCurrent(breakOffindex);
//                }

            }
        }

    }

    /**
     * 切换tab
     * @param idx
     */
    private void showTab(int idx){
        for(int i = 0; i < fragments.size(); i++){
            Fragment fragment = fragments.get(i);
            FragmentTransaction ft = obtainFragmentTransaction(idx);
            if(idx == i){
                ft.show(fragment);
            }else{
                ft.hide(fragment);
            }
            ft.commit();
        }
        currentTab = idx; // 更新目标tab为当前tab
    }

    /**
     * 获取一个带动画的FragmentTransaction
     * @param index
     * @return
     */
    private FragmentTransaction obtainFragmentTransaction(int index){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (!isAnimation){
            return ft;
        }
        // 设置切换动画
        if(index > currentTab){
            ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
        }else{
            ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out);
        }
        return ft;
    }

    /**
     *
     * @return 返回切换目标索引
     */
    public int getCurrentTab() {
        return currentTab;
    }

    /**
     *
     * @return 返回当前显示的fragment
     */
    public Fragment getCurrentFragment(){
        return fragments.get(currentTab);
    }

    public OnRgsExtraCheckedChangedListener getOnRgsExtraCheckedChangedListener() {
        return onRgsExtraCheckedChangedListener;
    }

    public void setOnRgsExtraCheckedChangedListener(OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener) {
        this.onRgsExtraCheckedChangedListener = onRgsExtraCheckedChangedListener;
    }

    /**
     *  切换tab额外功能功能接口
     */
    public interface OnRgsExtraCheckedChangedListener{
        void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index);
    }

    /**
     *  中断切换时的 功能接口
     */
    public interface OnBreakOffListener{
         void OnBreakOffChanged(int index);
    }



    //设置中断第几个按钮
    public void setContralBreakOff(int contralBreakOff) {
        this.contralBreakOff = contralBreakOff;
    }
    //设置中断回调监听
    public void setOnBreakOffListener(OnBreakOffListener onBreakOffListener) {
        this.onBreakOffListener = onBreakOffListener;
    }

    public void setCurrent(int index){
        RadioButton radioButton = (RadioButton) rgs.getChildAt(index);
        radioButton.setChecked(true);
    }
}
