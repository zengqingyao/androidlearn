package com.zengqy.customview.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.zengqy.customview.R;
import com.zengqy.customview.fragments.FirstTestFragment;
import com.zengqy.customview.fragments.IFragmentCallback;
import com.zengqy.customview.fragments.TwoTestFragment;

public class FragmentTextActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "CView_FragmentActivity";

    private Button mBtnChangeFragment;
    private Button mBtnReplaceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_text);

        mBtnChangeFragment = findViewById(R.id.fragment_change_btn);
        mBtnReplaceFragment = findViewById(R.id.fragment_replace_btn);

        mBtnChangeFragment.setOnClickListener(this);
        mBtnReplaceFragment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fragment_change_btn:
                Log.d(TAG, "fragment_change_btn: ");

                Fragment fragment = new FirstTestFragment();

                //1. activity用bundle传参给fragment
                Bundle bundle = new Bundle();
                bundle.putString("message","曾庆耀");
                fragment.setArguments(bundle);

                replaceFragment(fragment);
                break;
            case R.id.fragment_replace_btn:
                Log.d(TAG, "fragment_replace_btn: ");
                // 2. 面向接口编程，也可以通信

                TwoTestFragment twoTestFragment = new TwoTestFragment();
                twoTestFragment.setIFragmentCallback(new IFragmentCallback() {
                    @Override
                    public void sendMsgToActivity(String msg) {
                        Toast.makeText(getApplicationContext(),
                                ""+msg,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public String getMsgFromActivity() {
                        return "Activity的消息";
                    }
                });

                replaceFragment(twoTestFragment);
                break;
        }
    }


    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragmentlayout,fragment);

        // 把fragment添加到任务栈里面，按返回键就返回上一个fragment
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }


}