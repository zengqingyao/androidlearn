package com.zengqy.customview.utils;

import android.content.Context;

/**
 * @包名: com.zengqy.customview.utils
 * @USER: zengqy
 * @DATE: 2022/4/25 16:06
 * @描述: 像素转dp工具类
 */
public class SizeUtils {
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
