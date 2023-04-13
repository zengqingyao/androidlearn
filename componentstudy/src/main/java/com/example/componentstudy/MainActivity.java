package com.example.componentstudy;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.componentstudy.activitys.BroadcastActivity;
import com.example.componentstudy.activitys.ParcelableActivity;
import com.example.componentstudy.activitys.ServiceActivity;
import com.example.componentstudy.pojo.User;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CMP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // /data/user/0/com.example.componentstudy/cache
        Log.d(TAG, "onCreate, getCacheDir: "+getCacheDir());

        //  /data/user/0/com.example.componentstudy/files
        Log.d(TAG, "onCreate, getFilesDir: "+getFilesDir()+
                " 大小："+Formatter.formatFileSize(this,getFilesDir().getFreeSpace()));


        //  /storage/emulated/0/Android/data/com.example.componentstudy/cache
        Log.d(TAG, "onCreate, getExternalCacheDir:"+getExternalCacheDir()+
                " 大小："+Formatter.formatFileSize(this,getExternalCacheDir().getFreeSpace()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // /data/user/0/com.example.componentstudy
            Log.d(TAG, "onCreate, getDataDir:"+getDataDir()+
                    " 大小："+Formatter.formatFileSize(this,getDataDir().getFreeSpace()));
        }

        // /data
        File dataDirectory = Environment.getDataDirectory();

        Log.d(TAG, "Environment, getExternalCacheDir:"+ dataDirectory
                +" 大小："+Formatter.formatFileSize(this,dataDirectory.getFreeSpace()));


        // 已经弃用 /storage/emulated/0
        Log.d(TAG, "Environment, getExternalStorageDirectory:"+ Environment.getExternalStorageDirectory());

        Log.d(TAG, "Environment, getExternalStorageState:"+ Environment.getExternalStorageState());

        // /system
        Log.d(TAG, "Environment, getRootDirectory:"+ Environment.getRootDirectory());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        //  storage
            Log.d(TAG, "Environment, getStorageDirectory:"+ Environment.getStorageDirectory());
        }


    }

    public void startlearnActivity(View view) {
        Log.d(TAG, "startlearnActivity: ");

        Intent intent = new Intent(this, ParcelableActivity.class);

        User user = new User("曾庆耀", 30);

        Bundle bundle = new Bundle();
        bundle.putParcelable("USER", user);
        bundle.putString("NAME", "啥也不是");

        intent.putExtra("BUNDLE", bundle);

        startActivityForResult(intent, 1);

    }

    /**
     * 启动activity返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            Toast.makeText(this,"启动Activity学习的返回值: "+data.getStringExtra("NAME")
            ,Toast.LENGTH_SHORT).show();

        }
    }

    public void startlearnBroadcast(View view) {
        Log.d(TAG, "startlearnBroadcast: ");
        Intent intent = new Intent(this, BroadcastActivity.class);
        startActivity(intent);
    }

    public void startlearnService(View view) {
        Log.d(TAG, "startlearnService: ");
        Intent intent = new Intent(this, ServiceActivity.class);
        startActivity(intent);
    }

    public void startlearnContentProvide(View view) {
    }
}