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
 * @描述: 监听开机广播
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = "CMP_BootCompleteRecei";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "onReceive: --->"+action);

        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            Toast.makeText(context, "cmp软件收到开机广播", Toast.LENGTH_SHORT).show();
        }

    }

}
