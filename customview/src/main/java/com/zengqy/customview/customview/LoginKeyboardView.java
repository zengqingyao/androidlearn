package com.zengqy.customview.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.zengqy.customview.R;

/**
 * @包名: com.zengqy.customview.customview
 * @USER: zengqy
 * @DATE: 2022/4/30 22:41
 * @描述: 数字键盘的组合控件view
 * Android开发日常-编写一个登录界面: https://www.sunofbeach.net/a/1167463405121036288
 */
public class LoginKeyboardView extends LinearLayout implements View.OnClickListener {

    public static final String TAG = "CView_LoginKeyboardView";
    private onKeyPressListener mKeyPressListener = null;

    public LoginKeyboardView(Context context) {
        this(context, null);
    }

    public LoginKeyboardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginKeyboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.num_key_keyboard_view, this, true);

        initView();
    }

    private void initView() {
        this.findViewById(R.id.number_1).setOnClickListener(this);
        this.findViewById(R.id.number_2).setOnClickListener(this);
        this.findViewById(R.id.number_3).setOnClickListener(this);
        this.findViewById(R.id.number_4).setOnClickListener(this);
        this.findViewById(R.id.number_5).setOnClickListener(this);
        this.findViewById(R.id.number_6).setOnClickListener(this);
        this.findViewById(R.id.number_7).setOnClickListener(this);
        this.findViewById(R.id.number_8).setOnClickListener(this);
        this.findViewById(R.id.number_9).setOnClickListener(this);
        this.findViewById(R.id.number_0).setOnClickListener(this);
        this.findViewById(R.id.number_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int viewId = v.getId();

        if (mKeyPressListener == null) {
            Log.e(TAG, "mKeyPressListener is null, need set callback");
            return;
        }

        // 按下返回键，直接返回给监听者
        if (viewId == R.id.number_back) {
            mKeyPressListener.onBackPress();
        } else {

            // 按下0-9数字键盘，返回给监听者
            if (v instanceof TextView) {
                String text = ((TextView) v).getText().toString();
                mKeyPressListener.onNumberPress(Integer.parseInt(text));
            }
        }

    }

    public void setOnKeyPressListener(onKeyPressListener listener) {
        this.mKeyPressListener = listener;
    }


    public interface onKeyPressListener {

        void onNumberPress(int number);

        void onBackPress();
    }
}
