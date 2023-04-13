package com.alibaba.alipay;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class PayService extends Service {
    private static final String TAG = "PayService";
    private ThirdPartPayActiomImpl mThirdPartPayActiom;

    @Override
    public IBinder onBind(Intent intent) {
        String action = intent.getAction();
        Log.e(TAG, "onBind: -->action--> " + action);
        if (action != null && "com.alibaba.alipay.THIRD_PART_PAY".equals(action)) {
            mThirdPartPayActiom = new ThirdPartPayActiomImpl();
            //外部AIDL绑定
            return mThirdPartPayActiom;
        }

        // 内部Activity绑定，直接返回payAction
        return new payAction();
    }

    public class payAction extends Binder{
        public void pay(float payMoney)
        {
            Log.e(TAG, "pay money --> "+payMoney);
            // 实际支付是很复杂的，比如说加密，向服务器发起请求，等待服务器的结果
            //
            if (mThirdPartPayActiom != null) {
                mThirdPartPayActiom.paySuccess();
            }
        }


        public void onUserCancel(){
            if (mThirdPartPayActiom != null) {
                mThirdPartPayActiom.payFailed(1,"user cancel pay.");
            }
        }
    }


    private class ThirdPartPayActiomImpl extends IThirdPartPayAction.Stub {
        private IThirdPartPayResult mCallback;

        @Override
        public void requestPay(String orderInfo, float payMoney, IThirdPartPayResult callback) throws RemoteException {

            this.mCallback = callback;

            // 第三方调用，打开支付界面
            Intent intent = new Intent();
            intent.setClass(PayService.this, PayActivity.class);
            intent.putExtra(Constants.KEY_BILL_INFO, orderInfo);
            intent.putExtra(Constants.KEY_PAY_MONEY, payMoney);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        }

        public void paySuccess() {
            if (mCallback != null) {
                try {
                    mCallback.onPaySuccess();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }


        public void payFailed(int errorCode, String errorMsg) {
            if (mCallback != null) {
                try {
                    mCallback.onPayFailed(errorCode, errorMsg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}