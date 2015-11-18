package com.lvyerose.helpcommunity.found;

import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.flyco.pageindicator.indicator.FlycoPageIndicaor;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.lvyerose.helpcommunity.R;
import com.lvyerose.helpcommunity.base.BaseFragment;
import com.lvyerose.helpcommunity.common.network.NetworkServer;
import com.lvyerose.helpcommunity.widgets.autoviewpager.AutoScrollViewPager;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.ResultCallback;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * author: lvyeRose
 * objective:   发现界面的二级界面  热门活动
 * mailbox: lvyerose@163.com
 * time: 15/11/2 11:24
 */
@EFragment(R.layout.fragment_popular_activities)
public class PopularActivitiesFragment extends BaseFragment {
    View topView;
    FlycoPageIndicaor indicator;
    AutoScrollViewPager mViewPager;
    TextView indicatorTv;

    List<SimpleDraweeView> mTopListViews = new ArrayList<>();

    @ViewById(R.id.id_activity_lv)
    ListView mListView;

    @AfterViews
    void initViews() {
        initTopView();
        getNetworkData();
    }

    private void initTopView(){
        topView = LayoutInflater.from(getActivity()).inflate(R.layout.list_top_view , null);
        indicator = (FlycoPageIndicaor) topView.findViewById(R.id.indicator_round_rectangle);
        mViewPager = (AutoScrollViewPager) topView.findViewById(R.id.id_top_viewpager);
        indicatorTv = (TextView) topView.findViewById(R.id.id_top_title_tv);
    }
    /**
     * 联网获取数据
     */
    private void getNetworkData() {
        //顶部轮播图数据获取
        NetworkServer.getTopData(new ResultCallback<BeanActivityTopData>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(BeanActivityTopData beanActivityTopData) {
                if (beanActivityTopData != null && beanActivityTopData.getData() != null) {
                    setTopViewPager(beanActivityTopData.getData());
                    //底部活动列表数据获取
                    NetworkServer.getListData(new ResultCallback<BeanActivityListData>() {
                        @Override
                        public void onError(Request request, Exception e) {

                        }

                        @Override
                        public void onResponse(BeanActivityListData beanActivityListData) {
                            if (beanActivityListData != null && beanActivityListData.getData() != null) {
                                setListView(beanActivityListData.getData());
                            }
                        }
                    });
                }
            }
        });

//        //底部活动列表数据获取
//        NetworkServer.getListData(new ResultCallback<BeanActivityListData>() {
//            @Override
//            public void onError(Request request, Exception e) {
//
//            }
//
//            @Override
//            public void onResponse(BeanActivityListData beanActivityListData) {
//                if (beanActivityListData != null && beanActivityListData.getData() != null) {
//                    setListView(beanActivityListData.getData());
//                }
//            }
//        });


    }

    /**
     * 设置顶部ViewPager广告轮播图
     *
     * @param datas 网络获取的数据源
     */
    private void setTopViewPager(final List<BeanActivityTopData.DataEntity> datas) {
        indicatorTv.setText(datas.get(0).getActivity_title());
        for (int i = 0; i < datas.size(); i++) {
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(getActivity());
            GenericDraweeHierarchy hierarchy = simpleDraweeView.getHierarchy();
            hierarchy.setPlaceholderImage(R.drawable.loading_top_viewpager);
            simpleDraweeView.setImageURI(Uri.parse(datas.get(i).getActivity_photo()));
            mTopListViews.add(simpleDraweeView);
        }
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
//                return mTopListViews.size()*100;  //当数据大于三条才能使用无限不回头循环
                return mTopListViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mTopListViews.get(position >= mTopListViews.size() ? position % mTopListViews.size() : position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mTopListViews.get(position >= mTopListViews.size() ? position % mTopListViews.size() : position));
                return mTopListViews.get(position >= mTopListViews.size() ? position % mTopListViews.size() : position);
            }

        });
        mViewPager.setInterval(4000); //设置自动滚动的间隔时间，单位为毫秒
        mViewPager.setAutoScrollDurationFactor(4.0f); //设置ViewPager滑动动画间隔时间的倍率，达到减慢动画或改变动画速度的效果
        mViewPager.startAutoScroll();
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicatorTv.setText(datas.get(position).getActivity_title());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        indicator.setViewPager(mViewPager, datas.size());

    }

    private void setListView(List<BeanActivityListData.DataEntity> list) {
        mListView.addHeaderView(topView);
        mListView.setAdapter(new QuickAdapter<BeanActivityListData.DataEntity>(
                getActivity(),
                R.layout.item_activity_popular,
                list
        ) {
            @Override
            protected void convert(BaseAdapterHelper helper, BeanActivityListData.DataEntity item) {
                helper.setText(R.id.id_activity_title_item , item.getList_title());
                helper.setText(R.id.id_activity_dec_item , item.getList_dec());
                helper.setText(R.id.id_activity_time_item , item.getList_time());
                SimpleDraweeView simpleDraweeView = (SimpleDraweeView) helper.getView().findViewById(R.id.id_activity_photo_item);
                simpleDraweeView.setImageURI(Uri.parse(item.getActivity_photo()));

            }
        });

    }
}
