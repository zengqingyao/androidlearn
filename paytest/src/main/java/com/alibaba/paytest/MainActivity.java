package com.alibaba.paytest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.alipay.IThirdPartPayAction;
import com.alibaba.alipay.IThirdPartPayResult;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView mSobCountTv;
    private Button mBuySobBtn;
    private AlipayConnection mAlipayConnection;
    private boolean mIsBind;
    private IThirdPartPayAction mIThirdPartPayAction;


    private Handler mHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what)
            {
                case 1:
                    mSobCountTv.setText("100");
                    Toast.makeText(MainActivity.this, "充值成功！", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(MainActivity.this, "充值失败！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindAliPayService();

        initView();
        setListener();
    }


    private class PayCallback extends IThirdPartPayResult.Stub {
        @Override
        public void onPaySuccess() throws RemoteException {
            Log.e(TAG, "onPaySuccess: pay Success");
            // 支付成功，修改UI上面的内存
            // 实际上是去修改数据库，其实，支付宝是通过回调的URL地址，通知我们的服务器
            mHandler.sendEmptyMessage(1);
        }

        @Override
        public void onPayFailed(int errorCode, String msg) throws RemoteException {
            Log.e(TAG, "onPayFailed: pay error" );
            mHandler.sendEmptyMessage(2);
        }
    }


    private class AlipayConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected: " + service);
            mIThirdPartPayAction = IThirdPartPayAction.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected: " + name);

        }
    }

    private void bindAliPayService() {
        Intent intent = new Intent();
        intent.setAction("com.alibaba.alipay.THIRD_PART_PAY");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setPackage("com.alibaba.alipay");

        mAlipayConnection = new AlipayConnection();

        mIsBind = bindService(intent, mAlipayConnection, BIND_AUTO_CREATE);

        Log.e(TAG, "bindAliPayService: "+mIsBind);
    }

    private void setListener() {
        mBuySobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                try {
                    if (mIThirdPartPayAction != null) {
                        mIThirdPartPayAction.requestPay("充值100So币", 100.00f, new PayCallback());
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView() {
        mSobCountTv = findViewById(R.id.sob_count_tv);
        mBuySobBtn = findViewById(R.id.buy_sob_btn);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsBind && mAlipayConnection != null) {
            Log.e(TAG, "unbindService: ");
            unbindService(mAlipayConnection);
            mAlipayConnection = null;
            mIsBind = false;
        }
    }
}