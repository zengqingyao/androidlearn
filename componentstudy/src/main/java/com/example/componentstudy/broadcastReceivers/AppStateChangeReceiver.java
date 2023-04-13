package com.example.componentstudy.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

/**
 * @包名: com.example.componentstudy.broadcastReceivers
 * @author: zengqy
 * @DATE: 2022/12/9 15:56
 * @描述: 监听应用的安装与卸载
 */
public class AppStateChangeReceiver extends BroadcastReceiver {
    private static final String TAG = "CMP_AppStateChangeR";

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        Log.d(TAG, "onReceive: "+action);
        PackageManager pm = context.getPackageManager();

        if (Intent.ACTION_PACKAGE_ADDED.equals(action)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            Log.d(TAG, "--------安装成功" + packageName);
            Toast.makeText(context, "安装成功" + packageName, Toast.LENGTH_LONG).show();

        } else if (Intent.ACTION_PACKAGE_REPLACED.equals(action)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            Log.d(TAG, "--------替换成功" + packageName);
            Toast.makeText(context, "替换成功" + packageName, Toast.LENGTH_LONG).show();

        } else if (Intent.ACTION_PACKAGE_REMOVED.equals(action)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            Log.d(TAG, "--------卸载成功" + packageName);
            Toast.makeText(context, "卸载成功" + packageName, Toast.LENGTH_LONG).show();
        }

    }
}
