package com.zengqy.httpstudy;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.gson.Gson;
import com.zengqy.httpstudy.domain.*;
import okhttp3.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RetrofitTest extends AppCompatActivity {

    private static final String TAG = "Inet_RetrofitTest";

    public static final String BASE_URL = "http://10.0.2.2:9102";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_test);
    }


    /**
     * 使用 retrofit 的 get方法
     */
    public void getText_Retrofit(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        Call<JsonToObject> task = retrofitApi.getText();

        task.enqueue(new Callback<JsonToObject>() {
            @Override
            public void onResponse(Call<JsonToObject> call, Response<JsonToObject> response) {
                int code = response.code();
                Log.e(TAG, "onResponse: -->code:" + code);
                if (code == HttpURLConnection.HTTP_OK) {

                    // 使用 retrofit 的gson转换器直接获取到对象
                    JsonToObject jsonToObject = response.body();

                    // 打印对应的对象信息
                    Log.e(TAG, "message: " + jsonToObject.getMessage());

                    List<JsonToObject.DataBean> data = jsonToObject.getData();
                    for (JsonToObject.DataBean datum : data) {
                        Log.e(TAG, "Title: " + datum.getTitle());
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonToObject> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });

    }

    /**
     * 通过 retrofit ，get方法带参数请求
     */
    public void getWithParams_Retrofit(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        Call<ResponseBody> task = retrofitApi.getWithParams("我是搜索的关键字", 10, "0");

        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int code = response.code();
                Log.e(TAG, "onResponse: -->code:" + code);

                if (code == HttpURLConnection.HTTP_OK) {

                    // 直接获取对应的 json字符串
                    String body = null;
                    try {
                        body = response.body().string();

                        // 使用 gson 把 json字符串转换为对象
                        Gson gson = new Gson();
                        GetParamResultToObject getParamResultToObject = gson.fromJson(body, GetParamResultToObject.class);

                        Log.e(TAG, "response--> " + getParamResultToObject);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });

    }

    /**
     * Get请求，url的参数通过 map来传递
     */
    public void getWithParamsMap_Retrofit(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        Map<String, Object> params = new HashMap<>();
        params.put("keyword", "使用@QueryMap 搜索关键字");
        params.put("page", 10);
        params.put("order", 0);

        Call<GetParamResultToObject> withParamsMap = retrofitApi.getWithParamsMap(params);

        withParamsMap.enqueue(new Callback<GetParamResultToObject>() {
            @Override
            public void onResponse(Call<GetParamResultToObject> call, Response<GetParamResultToObject> response) {

                int code = response.code();
                Log.e(TAG, "onResponse: -->code:" + code);

                if (code == HttpURLConnection.HTTP_OK) {
                    GetParamResultToObject body = response.body();

                    Log.e(TAG, "onResponse: -->" + body);
                }

            }

            @Override
            public void onFailure(Call<GetParamResultToObject> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });


    }

    /**
     * Post请求，url带参数的方式
     */
    public void postWithParams_Retrofit(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        Call<PostParamResultToObject> postParamResulrToObjectCall = retrofitApi.postWithParams("Post请求，测试的内容");

        postParamResulrToObjectCall.enqueue(new Callback<PostParamResultToObject>() {
            @Override
            public void onResponse(Call<PostParamResultToObject> call, Response<PostParamResultToObject> response) {
                int code = response.code();
                Log.e(TAG, "onResponse: -->code:" + code);

                if (code == HttpURLConnection.HTTP_OK) {
                    PostParamResultToObject body = response.body();

                    Log.e(TAG, "onResponse: -->" + body);
                }

            }

            @Override
            public void onFailure(Call<PostParamResultToObject> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });
    }


    /**
     * Post请求，直接传url的方式
     */
    public void postWithUrl_Retrofit(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        String url = "/post/string?string=Post url, 这是内容测试内容";

        Call<PostParamResultToObject> postParamResulrToObjectCall = retrofitApi.postWithUrl(url);

        postParamResulrToObjectCall.enqueue(new Callback<PostParamResultToObject>() {
            @Override
            public void onResponse(Call<PostParamResultToObject> call, Response<PostParamResultToObject> response) {
                int code = response.code();
                Log.e(TAG, "onResponse: -->code:" + code);

                if (code == HttpURLConnection.HTTP_OK) {
                    PostParamResultToObject body = response.body();

                    Log.e(TAG, "onResponse: -->" + body);
                }
            }

            @Override
            public void onFailure(Call<PostParamResultToObject> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });
    }


    /**
     * Post请求，通过body注解，发送json给服务器
     */
    public void postCommentWithBody_Retrofit(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        Call<PostCommentResultToObject> postCommentResultToObjectCall = retrofitApi.postCommentWithBody(new CommentItem("999", "post请求，body注解，测试"));

        postCommentResultToObjectCall.enqueue(new Callback<PostCommentResultToObject>() {
            @Override
            public void onResponse(Call<PostCommentResultToObject> call, Response<PostCommentResultToObject> response) {
                int code = response.code();
                Log.e(TAG, "onResponse: -->code:" + code);

                if (code == HttpURLConnection.HTTP_OK) {
                    PostCommentResultToObject body = response.body();

                    Log.e(TAG, "onResponse: -->" + body);
                }
            }

            @Override
            public void onFailure(Call<PostCommentResultToObject> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });
    }


    /**
     * Post请求，上传文件到服务器
     */
    public void postUploadFile_Retrofit(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        File file = new File("/storage/emulated/0/Android/data/com.zengqy.httpstudy/files/Pictures/12.png");

        RequestBody requestBody = RequestBody.create(file, MediaType.parse("image/jpeg"));

        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        Call<PostFilesResultToObject> postFilesResultToObjectCall = retrofitApi.postFileWithPart(part);

        postFilesResultToObjectCall.enqueue(new Callback<PostFilesResultToObject>() {
            @Override
            public void onResponse(Call<PostFilesResultToObject> call, Response<PostFilesResultToObject> response) {
                int code = response.code();
                Log.e(TAG, "onResponse: -->code:" + code);

                if (code == HttpURLConnection.HTTP_OK) {
                    PostFilesResultToObject body = response.body();

                    Log.e(TAG, "onResponse: -->" + body);
                }
            }

            @Override
            public void onFailure(Call<PostFilesResultToObject> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });
    }


    /**
     * Post请求，上传多个文件到服务器
     */
    public void postUploadFileS_Retrofit(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        // 要上传的文件路径
        File fileOne = new File("/data/data/com.zengqy.httpstudy/zengqy.doc");
        File fileTwo = new File("/data/data/com.zengqy.httpstudy/touxiang.jpg");

        Log.e(TAG, fileOne.getName() + " " + fileOne.getAbsolutePath());
        Log.e(TAG, fileTwo.getName() + " " + fileTwo.getAbsolutePath());

        // 通过文件file 创建 RequestBody
        RequestBody fileOneBody = RequestBody.create(fileOne, MediaType.parse("application/msword"));
        RequestBody fileTwoBody = RequestBody.create(fileTwo, MediaType.parse("image/jpeg"));

        MultipartBody.Part partOne = MultipartBody.Part.createFormData("files", fileOne.getName(), fileOneBody);
        MultipartBody.Part partTwo = MultipartBody.Part.createFormData("files", fileTwo.getName(), fileTwoBody);

        // 添加多个 body
        List<MultipartBody.Part> parts = new ArrayList<>();
        parts.add(partOne);
        parts.add(partTwo);

        Call<PostFilesResultToObject> postFilesResultToObjectCall = retrofitApi.postFilesWithPart(parts);

        postFilesResultToObjectCall.enqueue(new Callback<PostFilesResultToObject>() {
            @Override
            public void onResponse(Call<PostFilesResultToObject> call, Response<PostFilesResultToObject> response) {
                int code = response.code();
                Log.e(TAG, "onResponse: -->code:" + code);

                if (code == HttpURLConnection.HTTP_OK) {
                    PostFilesResultToObject body = response.body();

                    Log.e(TAG, "onResponse: -->" + body);
                }
            }

            @Override
            public void onFailure(Call<PostFilesResultToObject> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });

    }

    /**
     * Post请求，上传文件到服务器并且携带信息
     */
    public void postUploadFileWithParams_Retrofit(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        // 需要上传的文件
        File file = new File("/data/data/com.zengqy.httpstudy/touxiang.jpg");

        // 根据文件创建 requestBody
        RequestBody requestBody = RequestBody.create(file, MediaType.parse("image/jpeg"));

        //
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        // 添加携带信息
        Map<String, String> params = new HashMap<>();
        params.put("description", "post请求，@Part @PartMap注解");
        params.put("isFree", "true");

        Call<PostFilesResultToObject> postFilesResultToObjectCall = retrofitApi.postFilesWithParamsPart(part, params);

        postFilesResultToObjectCall.enqueue(new Callback<PostFilesResultToObject>() {
            @Override
            public void onResponse(Call<PostFilesResultToObject> call, Response<PostFilesResultToObject> response) {
                int code = response.code();
                Log.e(TAG, "onResponse: -->code:" + code);

                if (code == HttpURLConnection.HTTP_OK) {
                    PostFilesResultToObject body = response.body();

                    Log.e(TAG, "onResponse: -->" + body);
                }
            }

            @Override
            public void onFailure(Call<PostFilesResultToObject> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });

    }

    /**
     * Post请求，发送多个表单
     */
    public void postLoginMap_Retrofit(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        Map<String, String> params = new HashMap<>();
        params.put("userName", "曾庆耀");
        params.put("password", "999999");

        // 发送登陆账户密码的表单，可以发送单个或者多个
//        Call<LoginResultToObject> task = retrofitApi.postLogin("曾庆耀","999999");
        Call<LoginResultToObject> task = retrofitApi.postLoginMap(params);

        task.enqueue(new Callback<LoginResultToObject>() {
            @Override
            public void onResponse(Call<LoginResultToObject> call, Response<LoginResultToObject> response) {
                int code = response.code();
                Log.e(TAG, "onResponse: -->code:" + code);

                if (code == HttpURLConnection.HTTP_OK) {
                    LoginResultToObject body = response.body();

                    Log.e(TAG, "onResponse: -->" + body);
                }
            }

            @Override
            public void onFailure(Call<LoginResultToObject> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });

    }


    /**
     * 通过Get方法下载文件
     */
    public void getDownloadFile_Retrofit(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();

        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        Call<ResponseBody> downloadFile = retrofitApi.getDownloadFile("/download/12");

        downloadFile.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int code = response.code();
                Log.e(TAG, "onResponse: -->code:" + code);
                if (code == HttpURLConnection.HTTP_OK) {

                    // 获取 http 响应头
                    Headers headers = response.headers();
                    for (int i = 0; i < headers.size(); i++) {
                        String name = headers.name(i);
                        String value = headers.value(i);
                        Log.e(TAG, name + " == " + value);
                    }

                    // 获取文件的名字
                    String contentDis = headers.get("Content-disposition");
                    String fileName = contentDis.replace("attachment; filename=", "");

                    try {

                        // 文件从body 下载拷贝到系统
                        File outFile = new File(RetrofitTest.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                                + File.separator + fileName);

                        Log.e(TAG, "outfile: " + outFile);

                        if (!outFile.getParentFile().exists()) {
                            outFile.mkdirs();
                        }

                        if (!outFile.exists()) {
                            outFile.createNewFile();
                        }

                        // 获取 BufferedOutputStream 输出字节流
                        FileOutputStream fos = new FileOutputStream(outFile);
                        BufferedOutputStream bos = new BufferedOutputStream(fos);
                        byte[] bytes = new byte[1024];

                        // 获取网络字节流
                        InputStream inputStream = response.body().byteStream();
                        BufferedInputStream bis = new BufferedInputStream(inputStream);

                        int rlen;
                        while ((rlen = bis.read(bytes)) != -1) {
                            bos.write(bytes, 0, rlen);
                        }

                        bos.close();
                        bis.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

                @Override
                public void onFailure (Call < ResponseBody > call, Throwable t){
                    Log.e(TAG, "onFailure: " + t.toString());
                }
            });


        }
    }
