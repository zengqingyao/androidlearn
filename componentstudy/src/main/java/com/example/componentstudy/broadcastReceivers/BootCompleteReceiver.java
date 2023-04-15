package com.example.componentstudy.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * @包名: com.example.componentstudy.broadcastReceivers
 * @author: zengqy
 * @DATE: 2022/12/9 15:43
 * @描述: 监听开机广播，android 4.4 可以使用启动activity，加上FLAG_ACTIVITY_NEW_TASK就好
 *       但10+从后台启动 Activity 的限制
 *       https://blog.csdn.net/xiaoyantan/article/details/128401740
 *
 *       Android Q 限制后台启动Activity
 *       https://blog.csdn.net/rui574081323/article/details/88894403/
 *
 *       运行在Android Q上的APP仅在以下一种或多种情况下可运行Activity：
 *
 *      1.APP存在一个可视的窗口，例如一个处于前台的Activity
 *      2.另外一个处于前台的APP发送一个属于当前APP的PendingIntent。例如Custom Tabs provider发送一个menu item pending intent。
 *      3.系统发送一个PendingIntent，例如点击一个通知。
 *      4.系统给APP发送一个广播，如SECRET_CODE_ACTION。
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = "CMP_BootCompleteRecei";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "onReceive: --->"+action);

        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            Toast.makeText(context, "(组件app)收到开机广播", Toast.LENGTH_SHORT).show();
        }

//        Intent intent1 = new Intent();
//        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent1.setClassName("com.example.componentstudy","com.example.componentstudy.MainActivity");
//        context.startActivity(intent1);

    }

}
