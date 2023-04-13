package com.example.appservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static String TAG = "HELLO";

    @Override
    public void onReceive(Context context, Intent intent) {
        StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction() + "\n");
        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
        String log = sb.toString();
        Log.e(TAG, log);
        Toast.makeText(context, log, Toast.LENGTH_LONG).show();

        Intent intent1 = new Intent();
        intent1.setClassName("com.example.appservice","com.example.appservice.MainActivity");
        context.startActivity(intent1);
    }
}
