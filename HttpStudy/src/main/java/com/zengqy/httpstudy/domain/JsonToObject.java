package com.zengqy.httpstudy.domain;

import java.util.List;

/**
 * @包名: com.zengqy.httpstudy
 * @USER: zengqy
 * @DATE: 2022/4/21 16:16
 * @描述: 通过 GsonFormat 转换为对象
 */
public class JsonToObject {

    /**
     * success : true
     * code : 10000
     * message : 获取成功
     * data : [{"id":"1517051796114219008","title":"Android加载大图片，解决OOM问题","viewCount":225,"commentCount":71,"publishTime":"2022-04-21T08:05:08.310+0000","userName":"程序员拉大锯","cover":"/imgs/3.png"},{"id":"1517051796114219009","title":"Volley/Xutils对大图片处理算法源码分析","viewCount":164,"commentCount":15,"publishTime":"2022-04-21T08:05:08.310+0000","userName":"程序员拉大锯","cover":"/imgs/16.png"},{"id":"1517051796114219010","title":"Android开发网络安全配置","viewCount":198,"commentCount":82,"publishTime":"2022-04-21T08:05:08.310+0000","userName":"程序员拉大锯","cover":"/imgs/1.png"},{"id":"1517051796114219011","title":"Android开发网络编程，请求图片","viewCount":77,"commentCount":80,"publishTime":"2022-04-21T08:05:08.310+0000","userName":"程序员拉大锯","cover":"/imgs/7.png"},{"id":"1517051796114219012","title":"Intent页面跳转工具类分享","viewCount":167,"commentCount":56,"publishTime":"2022-04-21T08:05:08.310+0000","userName":"程序员拉大锯","cover":"/imgs/12.png"},{"id":"1517051796114219013","title":"阳光沙滩商城的API文档","viewCount":97,"commentCount":50,"publishTime":"2022-04-21T08:05:08.310+0000","userName":"程序员拉大锯","cover":"/imgs/12.png"},{"id":"1517051796114219014","title":"Android课程视频打包下载","viewCount":81,"commentCount":12,"publishTime":"2022-04-21T08:05:08.310+0000","userName":"程序员拉大锯","cover":"/imgs/12.png"},{"id":"1517051796114219015","title":"非常轻量级的gif录制软件","viewCount":105,"commentCount":109,"publishTime":"2022-04-21T08:05:08.310+0000","userName":"程序员拉大锯","cover":"/imgs/9.png"},{"id":"1517051796114219016","title":"Fiddler抓包工具，墙裂推荐，功能很强大很全的一个工具","viewCount":216,"commentCount":100,"publishTime":"2022-04-21T08:05:08.311+0000","userName":"程序员拉大锯","cover":"/imgs/4.png"},{"id":"1517051796118413312","title":"AndroidStudio奇淫技巧-代码管理","viewCount":58,"commentCount":64,"publishTime":"2022-04-21T08:05:08.311+0000","userName":"程序员拉大锯","cover":"/imgs/3.png"},{"id":"1517051796118413313","title":"OC和Swift混编","viewCount":285,"commentCount":105,"publishTime":"2022-04-21T08:05:08.311+0000","userName":"程序员拉大锯","cover":"/imgs/3.png"},{"id":"1517051796118413314","title":"最新的Android studio是不是没有Android Device Monitor","viewCount":316,"commentCount":63,"publishTime":"2022-04-21T08:05:08.311+0000","userName":"程序员拉大锯","cover":"/imgs/3.png"}]
     */

    private boolean success;
    private int code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1517051796114219008
         * title : Android加载大图片，解决OOM问题
         * viewCount : 225
         * commentCount : 71
         * publishTime : 2022-04-21T08:05:08.310+0000
         * userName : 程序员拉大锯
         * cover : /imgs/3.png
         */

        private String id;
        private String title;
        private int viewCount;
        private int commentCount;
        private String publishTime;
        private String userName;
        private String cover;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }
    }
}
