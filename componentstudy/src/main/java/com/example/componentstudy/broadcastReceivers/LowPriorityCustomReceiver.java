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
 * @DATE: 2022/12/9 19:11
 * @描述:  有序广播低优先级接收
 */
public class LowPriorityCustomReceiver extends BroadcastReceiver {

    private static final String TAG = "CMP_LPrCustomReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra("broaadcast_content");
        String action = intent.getAction();
        Log.d(TAG, "onReceive: action"+action+" extra:"+stringExtra);

        Bundle res = getResultExtras(true);
        String content = res.getCharSequence("content").toString();

        Toast.makeText(context,"接收到 "+getResultData()+action+" 值:"+stringExtra
                + "传进来的值："+content,Toast.LENGTH_SHORT).show();
    }
}
