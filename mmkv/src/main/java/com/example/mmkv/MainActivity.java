package com.example.mmkv;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {
    
    public static String TAG = "HELLO_MMKV";
    private SharedPreferences mSharedPreferences;
    private MMKV mMmkv;
    TextView textView;
    private Random random;

    public void testSharedPreferences()
    {
        Log.e(TAG, "SharedPreferences开始写入" );
        SharedPreferences.Editor edit = mSharedPreferences.edit();

        edit.putString("KEY1","曾庆耀1").commit();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            edit.putInt(String.valueOf(i),random.nextInt()).commit();
        }
        long time = System.currentTimeMillis()-start;
        edit.putString("KEY2","曾庆耀2").commit();
        edit.putString("KEY1","曾庆耀").commit();

        Log.e(TAG, "SharedPreferences开始耗时: "+ time +"ms");
        String string = mSharedPreferences.getString("KEY1", "null");
        String show = "Share:"+time+"ms value:"+string;
        textView.setText(show);
    }

    public void testMmkv()
    {
        Log.e(TAG, "MMKV开始写入" );

        mMmkv.putString("KEY1","曾庆耀1");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            mMmkv.putInt(String.valueOf(i),random.nextInt());
        }
        long time = System.currentTimeMillis()-start;
        mMmkv.putString("KEY2","曾庆耀2");
        mMmkv.putString("KEY1","曾庆耀");
        Log.e(TAG, "MMKV开始耗时: "+ time +"ms");

        String string = mMmkv.getString("KEY1", "null");
        String show = "MMKV:"+time+"ms value:"+string;
        textView.setText(show);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = getSharedPreferences("zenqy_test",MODE_PRIVATE);
        MMKV.initialize(this);
        mMmkv = MMKV.defaultMMKV();
        random = new Random();

        textView = findViewById(R.id.textView);

        findViewById(R.id.mmkv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testMmkv();
            }
        });

        findViewById(R.id.sharepre).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testSharedPreferences();
            }
        });

    }
}