package com.zengqy.customview.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.zengqy.customview.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TwoTestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwoTestFragment extends Fragment {

    public static final String TAG = "CView_TwoTestFragment";

    private View mRoot;
    private Button mBtn;
    private TextView mTextView;


    // 面向接口编程，可以通信
    private IFragmentCallback mIFragmentCallback;

    public IFragmentCallback getIFragmentCallback() {
        return mIFragmentCallback;
    }

    public void setIFragmentCallback(IFragmentCallback IFragmentCallback) {
        mIFragmentCallback = IFragmentCallback;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "==============================");
        Log.d(TAG, "1.TwoTestFragment的onAttach被调用！");
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "2.TwoTestFragment的onCreate被调用！");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "3.TwoTestFragment的onCreateView被调用！");
        // Inflate the layout for this fragment
        if (mRoot == null) {
            mRoot = inflater.inflate(R.layout.fragment_two_test, container, false);
        }

        mBtn = mRoot.findViewById(R.id.fragment_button2);
        mTextView = mRoot.findViewById(R.id.fragment_tv2);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("点击后的信息");
                mIFragmentCallback.sendMsgToActivity("Fragment发送消息给activity");
            }
        });


        return mRoot;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "4.TwoTestFragment的onActivityCreated被调用！");
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "5.TwoTestFragment的onStart被调用！");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "6.TwoTestFragment的onResume被调用！");
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "7.TwoTestFragment的onPause被调用！");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "8.TwoTestFragment的onStop被调用！");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "9.TwoTestFragment的onDestroyView被调用！");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "10.TwoTestFragment的onDestroy被调用！");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "11.TwoTestFragment的onDetach被调用！");
    }


}