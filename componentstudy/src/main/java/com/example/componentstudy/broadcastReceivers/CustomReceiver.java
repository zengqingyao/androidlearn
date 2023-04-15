package com.example.componentstudy.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * @包名: com.example.componentstudy.broadcastReceivers
 * @author: zengqy
 * @DATE: 2022/12/9 18:44
 * @描述: 自定义接收广播
 */
public class CustomReceiver extends BroadcastReceiver {


    private static final String TAG = "CMP_CustomReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra("broaadcast_content");
        String action = intent.getAction();
        Log.d(TAG, "onReceive: action" + action + " extra:" + stringExtra);

        if ("com.example.componentstudy.activitys.SEND_MSG".equals(action)) {
            Toast.makeText(context, "接收到自定义广播: " + action + " 值:" + stringExtra, Toast.LENGTH_SHORT).show();

        } else if ("com.example.componentstudy.activitys.ORDER_SEND_MSG".equals(action)) {

            // 终止继续发送广播
            if("stop".equals(stringExtra)) {
                abortBroadcast();
            }

            // 可以修改广播的内容

            Bundle res = getResultExtras(true);
            String content=null;
            if (res != null) {
                content = res.getCharSequence("content").toString();
                res.putCharSequence("content","我是被修改后的内容");
                setResultExtras(res);
            }
            Toast.makeText(context, "接收到 "+getResultData() + action + " 值:" + stringExtra
                    +" 传进来的值："+content, Toast.LENGTH_SHORT).show();

            if (getResultData() != null) {
                // 这个值也可以被修改
                setResultData("低优先级"+getResultData());
            }
        }

    }
}
