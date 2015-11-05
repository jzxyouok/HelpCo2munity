package com.lvyerose.helpcommunity.widgets.banslidingGv;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * author: lvyeRose
 * objective:
 * mailbox: lvyerose@163.com
 * time: 15/11/5 18:31
 */

public class GrapeGridview extends GridView {

    public GrapeGridview(Context context) {
        super(context);
    }

    public GrapeGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GrapeGridview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //通过重新dispatchTouchEvent方法来禁止滑动
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true;//禁止Gridview进行滑动
        }
        return super.dispatchTouchEvent(ev);
    }
    //不出现滚动条  自动适用高度
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}