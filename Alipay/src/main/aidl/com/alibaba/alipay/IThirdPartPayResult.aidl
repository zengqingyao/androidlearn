// IThirdPartPayResult.aidl
package com.alibaba.alipay;


interface IThirdPartPayResult {
   void onPaySuccess();
   void onPayFailed(in int errorCode, in String msg);
}