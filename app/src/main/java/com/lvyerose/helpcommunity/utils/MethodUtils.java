package com.lvyerose.helpcommunity.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Display;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     *  获取系统时间
     * @param format
     * @return
     */
    public static String getSystemTime(String format){
        String time = null;
        SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
        time = df.format(new Date(System.currentTimeMillis()));// new Date()为获取当前系统时间
        return time;
    }

    /**
     * 邮箱格式是否正确
     *
     * @param email
     * @return
     */
    public boolean isEmail(String email) {

        if (TextUtils.isEmpty(email))
            return false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches())
            return true;
        else
            return false;

    }


}
