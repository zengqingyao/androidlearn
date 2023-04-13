// IThirdPartPayAction.aidl
package com.alibaba.alipay;

import com.alibaba.alipay.IThirdPartPayResult;

interface IThirdPartPayAction {
    /**
    *  Aidl 申请支付宝支付
    */
    void requestPay(String orderInfo, float payMoney, IThirdPartPayResult callback);
}