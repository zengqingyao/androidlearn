package com.example.componentstudy.activitys;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.componentstudy.R;
import com.example.componentstudy.interfaces.IFirstServiceMethod;
import com.example.componentstudy.services.FirstService;
import com.example.componentstudy.services.LocalService;
import com.example.componentstudy.services.MessengerService;

public class ServiceActivity extends AppCompatActivity {

    private static final String TAG = "CMP_ServiceActivity";
    private Intent mStartServiceIntent;
    private TextView mTextView;

    private IFirstServiceMethod mRemoteBinder;

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: 绑定服务成功");
            Toast.makeText(getApplicationContext(), "服务已经绑定完成", Toast.LENGTH_SHORT).show();
            mRemoteBinder = (IFirstServiceMethod) service;
        }

        /**
         * 方法在正常情况下是不被调用的，它的调用时机是当Service服务被异外销毁时，例如内存的资源不足时这个方法才被自动调用。
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: 服务已经解绑定");
            Toast.makeText(getApplicationContext(), "服务已经断开", Toast.LENGTH_SHORT).show();
            mRemoteBinder = null;

        }
    };
    private boolean mIsServiceBinder;
    private boolean mIslocalServiceBind = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        mTextView = findViewById(R.id.textView);
        mStartServiceIntent = new Intent(this, FirstService.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Activity被销毁 onDestroy: ");
        if (mServiceConnection != null && mIsServiceBinder) {
            unbindService(mServiceConnection);
            mServiceConnection = null;
            mIsServiceBinder = false;
            mRemoteBinder = null;
        }
    }

    /**
     * 启动服务
     *
     * @param view
     */
    public void startService(View view) {
        Log.d(TAG, "启动服务 startService: ");
        startService(mStartServiceIntent);
        mTextView.setText("启动服务");
    }

    /**
     * 停止服务
     *
     * @param view
     */
    public void stopService(View view) {
        Log.d(TAG, "停止服务 stopService: ");
        mTextView.setText("停止服务");
        stopService(mStartServiceIntent);
    }

    /**
     * 绑定服务
     *
     * @param view
     */
    public void bindService(View view) {
        Log.d(TAG, "绑定服务 bindService: ");
        mIsServiceBinder = bindService(mStartServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        mTextView.setText("绑定服务，线程名 "+Thread.currentThread());
    }


    /**
     * 解绑服务
     *
     * @param view
     */
    public void unbindService(View view) {
        Log.d(TAG, "解绑服务 bindService: ");
        if (mServiceConnection != null && mIsServiceBinder) {
            unbindService(mServiceConnection);
            mIsServiceBinder = false;
            mRemoteBinder = null;
            mTextView.setText("解绑服务");
        }
    }

    /**
     * 调用远程服务的方法
     *
     * @param view
     */
    public void callRemoteServiceMethod(View view) {
        if (mRemoteBinder != null) {
            mRemoteBinder.callIsServiceMethod();
        }
    }

    /**
     * 启动阿里巴巴支付
     * @param view
     */
    public void startAliPayClient(View view) {
        Intent intent = new Intent(this,ServiceAliPayActivity.class);
        startActivity(intent);
    }

    /**
     * 启动测试连接本地aidl服务
     * @param view
     */
    public void startAidlPersonTest(View view) {
        Intent intent = new Intent(this,ServiceAidlPersonActivity.class);
        startActivity(intent);
    }


    //******************************** LocalServer方式绑定调用服务 ************************************
    // 如果您的服务仅供本地应用使用，且无需跨进程工作，您可以实现自有 Binder 类，让客户端通过该类直接访问服务中的公共方法。
    /**
     * 调用本地服务方法
     * @param view
     */
    public void callLocalServerMethod(View view) {

        if (mLocalServiceHandle != null) {
            mLocalServiceHandle.getRandomNumber();
        }

        if(!mIslocalServiceBind) {
            Toast.makeText(getApplicationContext(), "开始绑定LocalService", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LocalService.class);
            mIslocalServiceBind = bindService(intent, mLocalServiceConnection, BIND_AUTO_CREATE);
        }

    }

    private LocalService mLocalServiceHandle;
    private ServiceConnection mLocalServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: LocalService 绑定服务成功");
            Toast.makeText(getApplicationContext(), "LocalService服务已经绑定完成", Toast.LENGTH_SHORT).show();
            LocalService.LocalBinder localBinder = (LocalService.LocalBinder) service;
            mLocalServiceHandle = localBinder.getService();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: LocalService");
        }
    };



    //******************************** Messenger方式绑定调用服务 ***************************************
    //    如需让接口跨不同进程工作，您可以使用 Messenger 为服务创建接口
    //    这是执行进程间通信 (IPC) 最为简单的方式，因为 Messenger 会在单个线程中创建包含所有请求的队列，
    //    这样您就不必对服务进行线程安全设计
    private boolean mIsMessengerServiceBind = false;


    public void callMessengerServiceMethod(View view) {
        if (mMessenger != null) {
            Message msg = Message.obtain(null, 1, 0, 0);
            try {
                mMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        if(!mIsMessengerServiceBind) {
            Toast.makeText(getApplicationContext(), "开始绑定MessengerService", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MessengerService.class);
            mIsMessengerServiceBind = bindService(intent, mMessengerServiceConnection, BIND_AUTO_CREATE);
        }
    }

    Messenger mMessenger;
    private ServiceConnection mMessengerServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
            Log.d(TAG, "onServiceConnected: MessengerService 绑定服务成功");
            Toast.makeText(getApplicationContext(), "MessengerService服务已经绑定完成", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}