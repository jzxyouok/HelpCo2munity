package com.lvyerose.helpcommunity.widgets.photoview;

import android.graphics.RectF;
import android.widget.ImageView;

/**
 * author: lvyeRose
 * objective:
 * mailbox: lvyerose@163.com
 * time: 15/11/7 22:49
 */


public class Info {
    // 内部图片在整个窗口的位置
    RectF mRect = new RectF();
    // 控件在窗口的位置
    RectF mLocalRect = new RectF();
    RectF mImgRect = new RectF();
    RectF mWidgetRect = new RectF();
    float mScale;
    float mDegrees;
    ImageView.ScaleType mScaleType;

    public Info(RectF rect, RectF local, RectF img, RectF widget, float scale, float degrees, ImageView.ScaleType scaleType) {
        mRect.set(rect);
        mLocalRect.set(local);
        mImgRect.set(img);
        mWidgetRect.set(widget);
        mScale = scale;
        mScaleType = scaleType;
        mDegrees = degrees;
    }
}

