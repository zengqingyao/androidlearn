package com.zengqy.httpstudy.domain;

/**
 * @包名: com.zengqy.httpstudy.domain
 * @USER: zengqy
 * @DATE: 2022/4/22 15:44
 * @描述: post请求，登陆时候返回的json对象
 */
public class LoginResultToObject {


    /**
     * success : true
     * code : 10000
     * message : 这是你提交上来的数据：拉大锯 - 12938471902387
     * data : 2a142e7c-754e-413d-a662-77c27abc097c
     */

    private boolean success;
    private int code;
    private String message;
    private String data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LoginResultToObject{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
