package com.zengqy.customview.activitys;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.zengqy.customview.App;
import com.zengqy.customview.R;
import com.zengqy.customview.customview.LoginKeyboardView;
import com.zengqy.customview.customview.LoginPageView;

public class LoginKeyboardActivity extends AppCompatActivity {

    public static final String TAG = "LoginKeyboardActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login_keyboard);

        LoginPageView loginPageView = this.findViewById(R.id.login_page_view);

        // 设置组合自定义view的监听事件
        loginPageView.setOnloginPageActionListener(new LoginPageView.OnLoginPageActionListener() {
            @Override
            public void onGetVerifyCodeClick(String phoneNum) {
                // todo 获取验证码，调用者去添加获取验证码逻辑
                Log.e(TAG, "请求验证码: ");
                Toast.makeText(LoginKeyboardActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOpenAgreementClick() {
                // todo 打开协议页面
                Log.e(TAG, "打开协议: ");
            }

            @Override
            public void onConfirmClick(String verifyCode, String phoneNum) {
                // todo 登陆
                Log.e(TAG, "点击登陆按钮, phoneNum-->"+phoneNum +" verifyCode-->"+verifyCode );

                // 模拟 4秒后登陆失败
                App.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 1. 给一个错误提示
                        Toast.makeText(LoginKeyboardActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();

                        loginPageView.onVerifyCodeError();
                    }
                },5000);

            }
        });

    }
}