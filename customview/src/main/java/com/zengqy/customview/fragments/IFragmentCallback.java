package com.zengqy.customview.fragments;

/**
 * @包名: com.zengqy.customview.fragments
 * @author: zengqy
 * @DATE: 2022/12/13 22:31
 * @描述:  activity和fragment的通信
 */
public interface IFragmentCallback {

    void sendMsgToActivity(String msg);

    String getMsgFromActivity();

}
