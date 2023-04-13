package com.zengqy.httpstudy.domain;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

/**
 * @包名: com.zengqy.httpstudy
 * @USER: zengqy
 * @DATE: 2022/4/21 15:51
 * @描述: 通过注解驱动的方式，实现跟服务器的http通信，包括Get方法、Post方法的上传和下载文件。
 */
public interface RetrofitApi {

    // http://10.0.2.2:9102/get/text
    @GET("/get/text")
    Call<JsonToObject> getText();


    // 通过注解的方式添加带参数的get请求 ,@Query url参数
    //  http://10.0.2.2:9102/get/param?keyword=这是搜索的关键字&page=10&order=0
    @GET("/get/param")
    Call<ResponseBody> getWithParams(@Query("keyword") String keyword
            , @Query("page") int page, @Query("order") String order);


    // @QueryMap 方式添加 url参数
    @GET("/get/param")
    Call<GetParamResultToObject> getWithParamsMap(@QueryMap Map<String, Object> params);


    // post请求，并且添加url参数
    @POST("/post/string")
    Call<PostParamResultToObject> postWithParams(@Query("string") String content);


    // post请求，直接传进url参数
    @POST
    Call<PostParamResultToObject> postWithUrl(@Url String url);


    // post请求，通过 body 提交评论
    @POST("/post/comment")
    Call<PostCommentResultToObject> postCommentWithBody(@Body CommentItem commentItem);


    /**
     * 1. @Header 添加一个 请求头，值为token 测试
     *    Call<PostFilesResultToObject> postFileWithPart(@Part MultipartBody.Part part, @Header("token") String token);
     *
     * 2. @Headers 添加多个请求头
     *    @Headers({"token:asdasdascxwwc","client:android","version:9.9"})
     *
     * 3. @HeaderMap 通过HashMap添加多个请求头
     *    @HeaderMap Map<String,String> headers
     * */

    // post请求，通过 MultipartBody 上传文件到服务器
    @Multipart
    @POST("/file/upload")
    Call<PostFilesResultToObject> postFileWithPart(@Part MultipartBody.Part part);


    // post 请求，上传多个文件
    @Multipart
    @POST("/files/upload")
    Call<PostFilesResultToObject> postFilesWithPart(@Part List<MultipartBody.Part> parts);

    // post 请求，上传文件的时候携带参数
    @Multipart
    @POST("/file/params/upload")
    Call<PostFilesResultToObject> postFilesWithParamsPart(@Part MultipartBody.Part part, @PartMap Map<String, String> params);

    // post请求，发送表单信息
    @FormUrlEncoded
    @POST("/login")
    Call<LoginResultToObject> postLogin(@Field("userName") String userName, @Field("password") String password);

    // post请求，发送多个表单信息
    @FormUrlEncoded
    @POST("/login")
    Call<LoginResultToObject> postLoginMap(@FieldMap Map<String, String> params);


    @GET
    Call<ResponseBody> getDownloadFile(@Url String url);
}
