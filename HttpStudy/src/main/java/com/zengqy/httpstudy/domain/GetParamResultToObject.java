package com.zengqy.httpstudy.domain;

/**
 * @包名: com.zengqy.httpstudy
 * @USER: zengqy
 * @DATE: 2022/4/21 22:14
 * @描述: Get 带参数，返回的json对应的对象
 */
public class GetParamResultToObject {

    /**
     * success : true
     * code : 10000
     * message : get带参数请求成功.
     * data : {"page":"10","keyword":"我是搜索的关键字","order":"顺序"}
     */

    private boolean success;
    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * page : 10
         * keyword : 我是搜索的关键字
         * order : 顺序
         */

        private String page;
        private String keyword;
        private String order;

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "page='" + page + '\'' +
                    ", keyword='" + keyword + '\'' +
                    ", order='" + order + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GetParamResultToObject{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
