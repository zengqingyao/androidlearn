package com.zengqy.customview.activitys;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.zengqy.customview.R;
import com.zengqy.customview.customview.InputNumberView;

/**
 * 使用一个简单的自定义的组合控件
 * */
public class InputNumberActivity extends AppCompatActivity implements InputNumberView.OnNumberChangeListener {

    private static final String TAG = "InputNumberActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_number);

        // 使用自定义的组合控件 view
        InputNumberView inputNumberView = findViewById(R.id.input_number_view);

        inputNumberView.setOnNumberChangeListener(this);
    }

    @Override
    public void onNumberChange(int number) {
        Log.e(TAG, "onNumberChange--> " + number);
    }

    @Override
    public void onNumberMax(int number) {
        Log.e(TAG, "onNumberMax--> " + number);
    }

    @Override
    public void onNumberMin(int number) {
        Log.e(TAG, "onNumberMin--> " + number);
    }
}