package com.example.componentstudy.activitys;

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
import com.example.componentstudy.R;

import java.math.BigDecimal;

public class ServiceAliPayActivity extends AppCompatActivity {

    private static final String TAG = "CMP_AliPayActivity";
    private TextView mSobCountTv;
    private Button mBuySobBtn;
    private boolean mIsbind;

    private IThirdPartPayAction mIThirdPartPayAction;


    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    BigDecimal bigDecimal = new BigDecimal(mSobCountTv.getText().toString().trim());
                    bigDecimal = bigDecimal.add(new BigDecimal("100"));
                    mSobCountTv.setText(bigDecimal.toString());
                    Toast.makeText(ServiceAliPayActivity.this, "充值成功！", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(ServiceAliPayActivity.this, "充值失败！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: 服务已经绑定并且返回binder");
            mIThirdPartPayAction = IThirdPartPayAction.Stub.asInterface(service);
            Toast.makeText(getApplicationContext(), "onServiceConnected服务已经绑定完成", Toast.LENGTH_SHORT).show();
        }

        /**
         * 方法在正常情况下是不被调用的，它的调用时机是当Service服务被异外销毁时，例如内存的资源不足时这个方法才被自动调用。
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };


    /**
     * 集成 binder
     */
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
            Log.e(TAG, "onPayFailed: pay error");
            mHandler.sendEmptyMessage(2);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_ali_pay);

        bindAliPayService();
        initView();
        setListener();
    }

    private void bindAliPayService() {
        Intent intent = new Intent();
        intent.setPackage("com.alibaba.alipay");
        intent.setAction("com.alibaba.alipay.THIRD_PART_PAY");
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        mIsbind = bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
        Log.d(TAG, "bindAliPayService: " + mIsbind);
    }

    private void initView() {
        mSobCountTv = findViewById(R.id.sob_count_tv);
        mBuySobBtn = findViewById(R.id.buy_sob_btn);
    }

    private void setListener() {
        mBuySobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIThirdPartPayAction != null) {
                    try {
                        mIThirdPartPayAction.requestPay("充值100So币", 100.00f, new PayCallback());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mIsbind && mServiceConnection != null) {
            Log.d(TAG, "unbindService: 解绑定服务");
            unbindService(mServiceConnection);
            mIsbind=false;
            Toast.makeText(ServiceAliPayActivity.this, "AliPayActivity已经被销毁，故解绑定服务！",
                    Toast.LENGTH_SHORT).show();
        }

    }
}