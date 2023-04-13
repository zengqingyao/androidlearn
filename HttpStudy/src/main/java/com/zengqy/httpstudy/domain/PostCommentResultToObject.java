package com.zengqy.httpstudy.domain;

/**
 * @包名: com.zengqy.httpstudy
 * @USER: zengqy
 * @DATE: 2022/4/22 10:46
 * @描述: Post请求，提交评论后返回的json的对象
 */
public class PostCommentResultToObject {

    /**
     * success : true
     * code : 10000
     * message : 评论成功:这是评论内容
     * data : null
     */

    private boolean success;
    private int code;
    private String message;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PostCommentResultToObject{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
