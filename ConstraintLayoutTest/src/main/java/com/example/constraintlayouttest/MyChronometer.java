package com.example.constraintlayouttest;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Chronometer;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @包名: com.example.constraintlayouttest
 * @author: zengqy
 * @DATE: 2022/12/6 22:21
 * @描述:  一个计算器
 */
public class MyChronometer extends Chronometer implements LifecycleObserver {
    private static String TAG = "lifecycle";
    public static long elapsedTime;

    public MyChronometer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void pauseMeter(){
        Log.e(TAG, "pauseMeter: ");
        elapsedTime = SystemClock.elapsedRealtime()-getBase();
        stop();
    }

    /**
     * setBase是设置定时器起始时间，里面也会调用SystemClock.elapsedRealtime()
     * SystemClock.elapsedRealtime()-getBase() 保存了已经跑过多少毫秒
     * resmue重新恢复后,重新设置定时器的起始时间，所以：现在的时间-之前跑过的时间，因为Chronometer是基于刚开始设置的时间来计算的
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void resumeMeter()
    {
        Log.e(TAG, "resumeMeter: " );
        setBase(SystemClock.elapsedRealtime()-elapsedTime);
        start();
    }
}
