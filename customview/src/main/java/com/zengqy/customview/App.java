package com.zengqy.customview;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * @包名: com.zengqy.customview
 * @USER: zengqy
 * @DATE: 2022/5/16 20:22
 * @描述:  注册application，实现全局handler，生命周期跟整个应用一样
 */
public class App extends Application {

    private static Handler sHandler = null;
    private static Context sContext = null;

    @Override
    public void onCreate() {
        super.onCreate();
        sHandler = new Handler(Looper.myLooper());

        sContext = getApplicationContext();
    }

    public static Context getAppContext(){
        return sContext;
    }

    public static Handler getHandler(){
        return sHandler;
    }

}
