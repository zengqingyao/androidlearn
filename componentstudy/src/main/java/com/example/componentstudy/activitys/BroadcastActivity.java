package com.example.componentstudy.activitys;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.componentstudy.R;

public class BroadcastActivity extends AppCompatActivity {

    private static final String TAG = "CMP_BroadcastActivity";
    private TextView mTvBattery;
    private TestBroadcastChangeReceiver mBroadcastChangeReceiver;
    private EditText mInputbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);


        mTvBattery = findViewById(R.id.tv_battery);
        mInputbox = findViewById(R.id.be_send_msg_inputbox);


        IntentFilter intentFilter = new IntentFilter();

        // 监听电量变化广播，这个广播必须动态注册
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);

        //电源拔出和插入广播
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        // 动态注册 广播接收器
        mBroadcastChangeReceiver = new TestBroadcastChangeReceiver();
        this.registerReceiver(mBroadcastChangeReceiver, intentFilter);

    }

    /**
     * 发送自定义普通广播
     * @param view
     */
    public void sendCustomBroadcast(View view) {
        String msg = mInputbox.getText().toString().trim();
        Log.d(TAG, "sendCustomBroadcast: ms: "+msg);
        // 发送广播+内容
        Intent intent = new Intent();

        // android8.0后发送广播给静态注册，需要加上包名
        intent.setPackage("com.example.componentstudy");

        intent.setAction("com.example.componentstudy.activitys.SEND_MSG");
        intent.putExtra("broaadcast_content",msg);

        sendBroadcast(intent);
    }


    /**
     * 发送自定义有序广播
     * @param view
     */
    public void sendCustomOrderBroadcast(View view) {
        String msg = mInputbox.getText().toString().trim();
        Log.d(TAG, "sendCustomOrderBroadcast: ms: "+msg);

        // 发送广播+内容
        Intent intent = new Intent();

        // android8.0后发送广播给静态注册，需要加上包名
        intent.setPackage("com.example.componentstudy");

        intent.setAction("com.example.componentstudy.activitys.ORDER_SEND_MSG");
        intent.putExtra("broaadcast_content",msg);

//        sendOrderedBroadcast(intent,null);
        Bundle bundle = new Bundle();
        bundle.putCharSequence("content","我是修改前的内容");

        // 发送广播 ，接收器需要相应的权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            sendOrderedBroadcast(intent, null,null,null
                    ,null, Activity.RESULT_OK,null,bundle);
        }
    }


    /**
     * 注册一个广播接收器
     */
    private class TestBroadcastChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // 电量变化
            if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                int currentBatteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                int maxBatteryLevel = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
                Log.d(TAG, "onReceive: " + action + " 当前电量: " + currentBatteryLevel + "%" + " 当前最大电量: " + maxBatteryLevel + "%");

                if (mTvBattery != null) {
                    float percent = (currentBatteryLevel * 1.0f / maxBatteryLevel) * 100;
                    mTvBattery.setText("收到了电量变化广播,当前电量: " + percent + "%, 最大电量:" + maxBatteryLevel + "%");
                }
            } else if (Intent.ACTION_POWER_CONNECTED.equals(action)) {
                Log.d(TAG, "onReceive: 电源连接");
                Toast.makeText(getApplicationContext(),"收到电源连接广播", Toast.LENGTH_SHORT).show();

            } else if (Intent.ACTION_POWER_DISCONNECTED.equals(action)) {
                Log.d(TAG, "onReceive: 电源拔出");
                Toast.makeText(getApplicationContext(),"收到电源拔出广播", Toast.LENGTH_SHORT).show();

            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //取消注册广播
        if (mBroadcastChangeReceiver != null) {
            this.unregisterReceiver(mBroadcastChangeReceiver);
        }

    }
}