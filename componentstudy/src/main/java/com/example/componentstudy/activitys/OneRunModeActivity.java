package com.example.componentstudy.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.componentstudy.R;

public class OneRunModeActivity extends AppCompatActivity {

    private static final String TAG = "CMP_OneRunModeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_run_mode);
        Log.e(TAG, "onCreate: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: " );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: " );
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: " );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: " );
    }


    /**
     * 1.当用户按下HOME键时。
     *  这是显而易见的，系统不知道你按下HOME后要运行多少其他的程序，
     *  自然也不知道acitvity A是否会被销毁所以系统会调用onSaveInstanceState,
     *  让用户有机会保存某些非永久性数据，以下几种分析都遵循该原则。（参见 一、3.3，有具体代码和gif图片演示 ）
     *
     * 2.打开任务列表（长按HOME键/长按选项键，因机型而异），按下就执行保存数据。（参见 四 3）
     *  因为你按下任务列表，你完全有可能选择其他程序然后玩很久，当前这个程序就被冷落好久了，
     *  既然被冷落了，就有可能被gc回收，所以保存数据很正常。（参见 一、3.4）
     *
     * 3.从甲Activity 中启动一个新的activity时。（参见 一、3.5）
     * 4.屏幕横竖屏切换
     * 5、锁屏（锁屏会执行savaInstanceState，解锁不会执行）（参见 一、3.6）
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: " );
        outState.putString("name","zengqy");
    }


    /**
     * Activity被异常销毁后打开Activity，
     * 横竖屏切换后打开Activity，
     * 或者说gc回收后打开Activity。
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "onRestoreInstanceState: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: " );
    }

    public void runOneActivity(View view) {
        startActivity(new Intent(this,OneRunModeActivity.class));
    }

    public void runTwoActivity(View view) {
        startActivity(new Intent(this,TwoRunModeActivity.class));
    }
}