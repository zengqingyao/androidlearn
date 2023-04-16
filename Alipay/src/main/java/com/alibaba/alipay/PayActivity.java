package com.alibaba.alipay;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class PayActivity extends Activity {

    private static final String TAG = "PayActivity";
    private boolean mIsbind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        doBindService();

        initView();

    }

    private void initView() {
        Intent intent = getIntent();
        String orderInfo = intent.getStringExtra(Constants.KEY_BILL_INFO);
        float payMoney = intent.getFloatExtra(Constants.KEY_PAY_MONEY, 0f);

        TextView orderInfoTv = this.findViewById(R.id.order_info_tv);
        orderInfoTv.setText("支付信息：" + orderInfo);

        TextView payMoneyTv = this.findViewById(R.id.pay_money);
        payMoneyTv.setText("支付金额：" + payMoney + "元");

        EditText passwordBox = this.findViewById(R.id.pay_password_input);
        Button commitBtn = this.findViewById(R.id.pay_commit);

        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击提交了
                String password = passwordBox.getText().toString().trim();
                if ("123456".equals(password) && mPayAction != null) {
                    mPayAction.pay(payMoney);
                    finish();
                    Log.e(TAG, "pay finsh");
                } else {
                    Toast.makeText(PayActivity.this, "密码错误!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * activity需要绑定到自己的服务程序，去支付
     */
    private void doBindService() {
        Intent intent = new Intent(this, PayService.class);
        mIsbind = bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private PayService.payAction mPayAction;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPayAction = (PayService.payAction) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mPayAction = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsbind && mServiceConnection != null) {
            unbindService(mServiceConnection);
            mServiceConnection = null;
            mIsbind = false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mPayAction != null) {
            mPayAction.onUserCancel();
        }
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG, "onWindowFocusChanged: "+(hasFocus?"activity有焦点":"activity失去焦点"));
    }
}
