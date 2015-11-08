package com.lvyerose.helpcommunity.utils;

import android.app.Activity;
import android.view.Display;

/**
 * author: lvyeRose
 * objective:
 * mailbox: lvyerose@163.com
 * time: 15/11/8 16:23
 */
public class MethodUtils {

public final static String ACTIVITY_HEIGHT = "activity_height";
public final static String ACTIVITY_WIDTH = "activity_width";

    /**
     *  获取系统状态栏的高度
     * @param activity
     * @return  最顶部状态栏的高度
     */
    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     *  获取Activity布局的宽度或者高度
     * @param activity 需要获取的activity对象
     * @param type 获取的是高度还是宽度   分别传入   Method.ACTIVITY_HEIGHT  or  Method.ACTIVITY_WIDTH
     * @return  返回结果，如果未传入获取的类型  则返回 0
     */
    public static int getActivityHgOrWd(Activity activity , String type){
        Display display = activity.getWindowManager().getDefaultDisplay();
        if(type.equals(ACTIVITY_HEIGHT)){
            return display.getHeight();
        }else if(type.equals(ACTIVITY_WIDTH)){
            return  display.getWidth();
        }else{
            return 0;
        }
    }
}
