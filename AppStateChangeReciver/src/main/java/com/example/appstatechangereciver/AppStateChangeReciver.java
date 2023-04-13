package com.example.appstatechangereciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AppStateChangeReciver extends BroadcastReceiver {
    private static final String TAG = "AppStateChangeReciver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent();
        intent1.setClassName("com.example.appstatechangereciver","com.example.appstatechangereciver.MainActivity");
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        String action = intent.getAction();
        if(Intent.ACTION_PACKAGE_ADDED.equals(action)){
            Log.e(TAG, "ACTION_PACKAGE_ADDED: "+intent);
            context.startActivity(intent1);
        }else if (Intent.ACTION_PACKAGE_REMOVED.equals(action)){
            Log.e(TAG, "ACTION_PACKAGE_REMOVED: "+intent);
            context.startActivity(intent1);
        }
    }
}
