package com.module.giftflowdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.WindowManager;


/**
 * 系统工具,发短信,调音量,获取app版本..
 *
 * @author xiatian
 */
public class DensityUtil {

    /**
     * 获取屏幕高
     */
    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    @NonNull
    private static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager systemService = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        systemService.getDefaultDisplay().getMetrics(metric);
        return metric;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
