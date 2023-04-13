package com.zengqy.httpstudy;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.zengqy.httpstudy.domain.CommentItem;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;


public class OKHttpTest extends AppCompatActivity {

    private static final String TAG = "Inet_OKHttpTest";

    public static final String BASE_URL = "http://10.0.2.2:9102";
    public static final String SHOP_URL = "https://api.sunofbeaches.com/shop/discovery/categories";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_test);
    }



    /**
     * 使用 Get 获取，返回json
     */
    public void getTextOkHttp(View view) {


        // 创建客户端，类似于有一个浏览器
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .build();

        Request request = new Request.Builder()
                .get()
                .url(SHOP_URL)
                .build();


        // 得到一个任务
        Call task = okHttpClient.newCall(request);

        // 异步执行任务
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure--> " + e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    ResponseBody body = response.body();
                    Log.d(TAG, "getTextOkHttp onResponse: --> " + body.string());
                }
            }
        });

    }

    /**
     * 使用POST，提交一个评论
     */
    public void postComment_OkHttp(View view) {

        // // 创建客户端，类似于有一个浏览器
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .build();


        // 根据类获取对应的json的字符串
        CommentItem commentItem = new CommentItem("123456", "这是一个评论，测试");
        Gson gson = new Gson();
        String jsonStr = gson.toJson(commentItem);


        MediaType mediaType = MediaType.parse("application/json");

        RequestBody requestBody = RequestBody.create(jsonStr, mediaType);

        Request request = new Request.Builder()
                .post(requestBody)
                .url(BASE_URL + "/post/comment")
                .build();


        Call task = okHttpClient.newCall(request);
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure--> " + e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    ResponseBody body = response.body();
                    Log.d(TAG, "postComment_OkHttp onResponse: -->" + body.string());
                }

            }
        });


    }

    /**
     * 使用Post，上传某一个文件
     */
    public void uploadFile_OkHttp(View view) {

        Log.d(TAG, "uploadFile_OkHttp: ");
        // 创建客户端
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .build();


        // 要上传的文件路径
        File file = new File("/data/data/com.zengqy.httpstudy/touxiang.jpg");

        // 具体的 Content-Type
        MediaType mediaType = MediaType.parse("image/jpeg");
        RequestBody fileBody = RequestBody.create(file, mediaType);


        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("file", file.getName(), fileBody)
                .build();

        Request request = new Request.Builder()
                .post(requestBody)
                .url(BASE_URL + "/file/upload")
                .build();


        Call task = okHttpClient.newCall(request);

        // 异步执行
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure--> " + e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code = response.code();

                if (code == HttpURLConnection.HTTP_OK) {
                    ResponseBody body = response.body();
                    Log.d(TAG, "uploadFile_OkHttp onResponse: " + body.string());
                }

            }
        });

    }


    /**
     * 使用 Post 上传多个文件
     */
    public void uploadFileS_OkHttp(View view) {


        // 创建客户端
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .build();

        // 要上传的文件路径
        File fileOne = new File("/data/data/com.zengqy.httpstudy/touxiang.jpg");
        File fileTwo = new File("/data/data/com.zengqy.httpstudy/zengqy.doc");


        Log.d(TAG, fileOne.getName() + " " + fileOne.getAbsolutePath());
        Log.d(TAG, fileTwo.getName() + " " + fileTwo.getAbsolutePath());


        MediaType jpgType = MediaType.parse("image/jpeg");
        MediaType docType = MediaType.parse("application/msword");


        // 通过文件file 创建 RequestBody
        RequestBody fileOneBody = RequestBody.create(fileOne, docType);
        RequestBody fileTwoBody = RequestBody.create(fileTwo, jpgType);

        // 添加 body
        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("files", fileOne.getName(), fileOneBody)
                .addFormDataPart("files", fileTwo.getName(), fileTwoBody)
                .build();


        Request request = new Request.Builder()
                .url(BASE_URL + "/files/upload")
                .post(requestBody)
                .build();


        Call task = okHttpClient.newCall(request);

        // 异步执行
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure--> " + e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                //服务器响应
                int code = response.code();

                if (code == HttpURLConnection.HTTP_OK) {
                    ResponseBody body = response.body();
                    Log.d(TAG, "uploadFile_OkHttp onResponse: " + body.string());
                }
            }
        });

    }

    /**
     * 下载文件
     */
    public void downloadFile_OkHttp(View view) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .build();

        // 下载15
        Request request = new Request.Builder()
                .get()
                .url(BASE_URL + "/download/15")
                .build();

        Call task = okHttpClient.newCall(request);

        // 同步执行代码，异步跟其他接口一样实现就行
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 同步执行 http下载
                    Response execute = task.execute();

                    // 获取 http 响应头
                    Headers headers = execute.headers();
                    for (int i = 0; i < headers.size(); i++) {
                        String name = headers.name(i);
                        String value = headers.value(i);
                        Log.d(TAG, name + " == " + value);
                    }

                    // 获取文件的名字
                    String contentDis = headers.get("Content-disposition");
                    String fileName = contentDis.replace("attachment; filename=", "");

                    File outFile = new File(OKHttpTest.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + fileName);
                    Log.d(TAG, "outfile: " + outFile);

                    if (!outFile.getParentFile().exists()) {
                        outFile.mkdirs();
                    }

                    if (!outFile.exists()) {
                        outFile.createNewFile();
                    }

                    FileOutputStream fos = new FileOutputStream(outFile);

                    // 文件从body 下载拷贝到系统
                    if (execute.body() != null) {
                        InputStream inputStream = execute.body().byteStream();

                        byte[] bytes = new byte[1024];
                        int rlen;
                        while ((rlen = inputStream.read(bytes, 0, bytes.length)) != -1) {
                            fos.write(bytes, 0, rlen);
                        }

                        fos.flush();

                        fos.close();
                        inputStream.close();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
