package com.zengqy.customview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.zengqy.customview.activitys.*;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "CView_MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void startInputNumberActivity(View view) {
        Log.e(TAG, "startInputNumberActivity: ");
        startActivity(new Intent(this, InputNumberActivity.class));
    }

    public void startViewPageActivity(View view) {
        Log.e(TAG, "SobLooperViewActivity: ");
        startActivity(new Intent(this, SobLooperViewActivity.class));
    }

    public void startLoginKeyboardActivity(View view) {
        Log.e(TAG, "LoginKeyboardActivity: ");
        startActivity(new Intent(this, LoginKeyboardActivity.class));
    }

    public void startFlowLayoutActivity(View view) {
        Log.e(TAG, "startFlowLayoutActivity: ");
        startActivity(new Intent(this, FlowLayoutActivity.class));
    }

    public void startFragmentActivity(View view) {
        Log.e(TAG, "startFragmentActivity: ");
        startActivity(new Intent(this, FragmentTextActivity.class));
    }
}