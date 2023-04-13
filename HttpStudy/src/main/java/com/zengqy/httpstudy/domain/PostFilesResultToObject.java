package com.zengqy.httpstudy.domain;

/**
 * @包名: com.zengqy.httpstudy.domain
 * @USER: zengqy
 * @DATE: 2022/4/22 11:56
 * @描述: 上传一个或者多个文件后返回的 json 对象
 */
public class PostFilesResultToObject {


    /**
     * success : true
     * code : 10000
     * message : 上传成功3个文件，路径：E:/codes/Idear/SobNetworkCourseServer/target/classes/sobUpload
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
        return "PostFilesResultToObject{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
