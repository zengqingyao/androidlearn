package com.example.appclient;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Chronometer;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyChronometer extends Chronometer implements LifecycleObserver {
    private static String TAG = "HELLO";
    private long elapsedTime;

    public MyChronometer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void pauseMeter(){
        Log.e(TAG, "pauseMeter: ");
        elapsedTime = SystemClock.elapsedRealtime()-getBase();
        stop();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void resumeMeter()
    {
        Log.e(TAG, "resumeMeter: " );
        setBase(SystemClock.elapsedRealtime()-elapsedTime);
        start();
    }
}
