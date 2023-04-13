package com.zengqy.httpstudy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.gson.Gson;
import com.zengqy.httpstudy.domain.CommentItem;
import com.zengqy.httpstudy.domain.GetShopResultToObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Inet_MainActivity";
    private static final int PERMISSION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 获取读写外部存储的权限
        int readResult = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeResult = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (readResult != PackageManager.PERMISSION_GRANTED || writeResult != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            //判断结果
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "用户允许访问权限..");
                //有权限
            } else {
                Log.e(TAG, "用户拒绝访问权限...");
                //没权限
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) && !ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    //走到这里，说明用户之前用户禁止权限的同时，勾选了 不再询问
                    //那么，你需要弹出一个dialog，提示用户需要权限，然后跳转到设置里头去打开。
                    Log.e(TAG, "用户之前勾选了不再询问...");
                    //TODO:弹出一个框框，然后提示用户说需要开启权限。
                    //TODO:用户点击确定的时候，跳转到设置里去
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    //在activity结果范围的地方，再次检查是否有权限
                    startActivityForResult(intent, PERMISSION_REQUEST_CODE);
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    //请求权限
                    Log.e(TAG, "再次请求权限...");
                }
            }
        }
    }


    /**
     * 底层通过接口模拟，获取应用商城的返回的json列表，通常我们会使用okhttp等框架
     */
    public void getHttpMsg(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "getHttpMsg: -->");

                BufferedReader bufferedReader = null;
                try {

                    //获取应用商城的返回的json
                    URL url = new URL("https://api.sunofbeaches.com/shop/discovery/categories");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(10000);
                    httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
                    httpURLConnection.setRequestProperty("Accept", "*/*");
                    httpURLConnection.connect();

                    // 获取响应值
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {

                        // 遍历响应头，并且打印出来
                        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
                        Set<Map.Entry<String, List<String>>> entries = headerFields.entrySet();
                        for (Map.Entry<String, List<String>> entry : entries) {
                            Log.e(TAG, entry.getKey() + " = " + entry.getValue());
                        }

                        // 获取输入流，读取
                        InputStream inputStream = httpURLConnection.getInputStream();
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String jsonStr = bufferedReader.readLine();

                        // 通过gson 把json字符串变为对象
                        Gson gson = new Gson();
                        GetShopResultToObject getHttpJsonObj = gson.fromJson(jsonStr, GetShopResultToObject.class);

                        //打印对象的值
                        Log.e(TAG, "getHttpJsonObj: " + getHttpJsonObj.isSuccess());
                        Log.e(TAG, "getHttpJsonObj: " + getHttpJsonObj.getMessage());
                        List<GetShopResultToObject.DataBean> data = getHttpJsonObj.getData();
                        for (GetShopResultToObject.DataBean datum : data) {
                            Log.e(TAG, "" + datum);
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }).start();
    }

    /**
     * 使用Post提交一个评论，底层具体接口的实现，通常我们会使用okhttp
     */
    public void postHttpMsg(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "postHttpMsg: --> ");
                BufferedReader bufferedReader = null;
                try {

                    URL url = new URL("http://10.0.2.2:9102/post/comment");

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setConnectTimeout(10000);
                    httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    httpURLConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
                    httpURLConnection.setRequestProperty("Accept", "*/*");

                    CommentItem commentItem = new CommentItem("123456", "这是一个评论，测试");

                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(commentItem);
                    byte[] bytes = jsonStr.getBytes(StandardCharsets.UTF_8);

                    // 设置内容的长度
                    httpURLConnection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
                    // 连接
                    httpURLConnection.connect();
//                    httpURLConnection.setDoOutput(true);

                    // 发送数据给服务器
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    outputStream.write(bytes);
                    outputStream.flush();

                    //判断是否发送成功
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        Log.e(TAG, "result: -->" + bufferedReader.readLine());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    public void startOkHttpActivity(View view) {
        Intent intent = new Intent(this, OKHttpTest.class);
        startActivity(intent);
    }

    public void startRetrofitActivity(View view) {
        Intent intent = new Intent(this, RetrofitTest.class);
        startActivity(intent);
    }

}

