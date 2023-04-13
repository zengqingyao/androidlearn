package com.example.view;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

public class MyService extends Service {
    private static final String TAG = "ss";

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: " );
        installFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    private void installFloatingWindow() {
        // ① 获取一个WindowManager实例
        final WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);

        // ② 新建一个按钮控件
        final Button btn = new Button(this.getBaseContext());
        btn.setText("Click me to dismiss!");

        // ③ 生成一个WindowManager.LayoutParams，用以描述窗口的类型与位置信息
        LayoutParams lp = createLayoutParams();

        // ④ 通过WindowManager.addView()方法将按钮作为一个窗口添加到系统中
        wm.addView(btn, lp);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ⑤当用户点击按钮时，将按钮从系统中删除
                wm.removeView(btn);
                stopSelf();
            }
        });
    }

    private LayoutParams createLayoutParams() {
        LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = LayoutParams.TYPE_PHONE;
        lp.gravity = Gravity.CENTER;
        lp.width = LayoutParams.WRAP_CONTENT;
        lp.height = LayoutParams.WRAP_CONTENT;
        lp.flags = LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCH_MODAL;
        return lp;

    }

}