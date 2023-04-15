package com.example.constraintlayouttest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    String TAG = "lifecycle_MainActivity";

    MyViewModel mMyViewModel;

    EditText mEditTextPass;
    EditText mEditTextAccount;
    Button mButton;
    Button mButton2;
    TextView mTextView;


    /**
     * activity第一次创建的时候调用，跟ondestroy对应
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");



        // ==============================怎么使用 SharedPreferences===========================
        // 不能传this，因为activity会经常创建销毁，这里传app的全局Context
        SharedPreferencesTest shp = new SharedPreferencesTest(getApplicationContext());
        shp.saveString("NAME","zengqy");
        shp.saveInt("INT",100);
        Log.d(TAG, "SharedPreferencesTest: "+shp.loadString("NAME"));
        Log.d(TAG, "SharedPreferencesTest: "+shp.loadInt("INT"));
        // ========================================================================


        //======================== Bundle 保存和还原数据方法 ========================
        if (savedInstanceState != null) {
            String key = savedInstanceState.getString("KEY");
            Log.d(TAG, "onCreate: "+key);
        }
        //======================== Bundle 保存和还原数据方法 ========================


        mTextView = findViewById(R.id.textView);
        mMyViewModel = new ViewModelProvider(this).get(MyViewModel.class);


        //======================== livedata监听数据变更 ========================
        // 设置监听 livedata ，值改变的时候会回调 onChanged
        mMyViewModel.getIntegerMutableLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mTextView.setText(String.valueOf(integer));
            }
        });
        //======================== livedata监听数据变更 ========================

        mEditTextAccount = findViewById(R.id.editTextTextPassword3);
        mEditTextPass = findViewById(R.id.editTextTextPassword4);
        mButton = findViewById(R.id.button4);
        mButton2 = findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyViewModel.addLikedNumber(1); //更改livedata值
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyViewModel.addLikedNumber(-1);
            }
        });

        // 启动另外一个activity
        Intent activityIntent = new Intent();
        activityIntent.setClassName("com.example.constraintlayouttest"
                ,"com.example.constraintlayouttest.MainBindingActivity");

        findViewById(R.id.btn_startMainbinding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(activityIntent);
            }
        });
    }

    /**
     * 销毁activity的后保存的数据
     * @param outState
     * onSaveInstanceState以下5种情况被调用：
     * 1、当用户按下手机home键的时候。
     * 2、长按手机home键或者按下菜单键时。
     * 3、手机息屏时。
     * 4、FirstActivity启动SecondActivity，FirstActivity就会调用，也就是说打开新Activity时，原Activity就会调用。
     * 5、默认情况下横竖屏切换时。
     *
     * 当竖屏切换到横屏时，系统会销毁竖屏Activity然后创建横屏的Activity，所以竖屏被销毁时，该方法就会被调用。
     *
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
        outState.putString("KEY","value");
    }

    /**
     * activity可见的时候调用，但没有焦点，不可以进行操作，跟onStop对应
     */
    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: ");
    }

    /**
     * activity获取到焦点的时候，可以进行交互，跟onPause对应
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    /**
     * 失去焦点的时候，但还是可见的，比如跳转到一个透明的activity的时候就会调用
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    /**
     * 不可见的时候调用，比如点击home键返回到桌面
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    /**
     * 重新返回的到activity的时候会调用，然后再调用onStart
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    /**
     * activity被销毁的时候调用
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}