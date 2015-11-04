package com.lvyerose.helpcommunity.found;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.lvyerose.helpcommunity.R;
import com.lvyerose.helpcommunity.base.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import java.util.ArrayList;
import java.util.List;

/**
 * author: lvyeRose
 * objective:   主页 发现主界面
 * mailbox: lvyerose@163.com
 * time: 15/11/1 20:11
 */
@EFragment(R.layout.fragment_find)
public class FindFragment extends BaseFragment {
    @ViewById(R.id.id_find_tablayout)
    TabLayout mTabLayout;
    @ViewById(R.id.id_find_viewpager)
    ViewPager mViewPager;
    List<Fragment> mList = new ArrayList<>();
    @StringArrayRes(R.array.find_top_arrays)
    String[] mTitle;

    @AfterViews
    void initTab() {
        mList.add(new FriendDynamicFragment_());
        mList.add(new CommunityDynamicFragment_());
        mList.add(new PopularActivitiesFragment_());
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return mList.get(position);
            }

            @Override
            public int getCount() {

                return mTitle.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {

                return mTitle[position];
            }
        });

        //初始化TabLayout与ViewPager建立联系
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
