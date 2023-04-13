package com.zengqy.customview.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.zengqy.customview.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstTestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstTestFragment extends Fragment {

    public static final String TAG = "CView_FirstTestFragment";
    private View mRoot;
    private Button mBtn;
    private TextView mTextView;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "==============================");
        Log.d(TAG, "1.FirstFragment的onAttach被调用！");
        Toast.makeText(getActivity().getApplicationContext(),
                "1.FirstFragment的onAttach被调用！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "2.FirstFragment的onCreate被调用！");
        Toast.makeText(getActivity().getApplicationContext(),
                "2.FirstFragment的onCreate被调用！",Toast.LENGTH_SHORT).show();

        Bundle bundle = getArguments();
        if (bundle != null) {
            String message = bundle.getString("message");
            Toast.makeText(getActivity().getApplicationContext(),
                    "Activity传给fragment的值为: "+message,Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mRoot == null) {
            mRoot = inflater.inflate(R.layout.fragment_first_test, container, false);
        }

        Log.d(TAG, "3.FirstFragment的onCreateView被调用！");
        Toast.makeText(getActivity().getApplicationContext(),
                "3.FirstFragment的onCreateView被调用！",Toast.LENGTH_SHORT).show();

        mBtn = mRoot.findViewById(R.id.fragment_button1);
        mTextView = mRoot.findViewById(R.id.fragment_tv1);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("点击后的信息");
            }
        });


        return mRoot;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "4.FirstFragment的onActivityCreated被调用！");
        Toast.makeText(getActivity().getApplicationContext(),
                "4.FirstFragment的onActivityCreated被调用！",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "5.FirstFragment的onStart被调用！");
        Toast.makeText(getActivity().getApplicationContext(),
                "5.FirstFragment的onStart被调用！",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "6.FirstFragment的onResume被调用！");
        Toast.makeText(getActivity().getApplicationContext(),
                "6.FirstFragment的onResume被调用！",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "7.FirstFragment的onPause被调用！");
        Toast.makeText(getActivity().getApplicationContext(),
                "7.FirstFragment的onPause被调用！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "8.FirstFragment的onStop被调用！");
        Toast.makeText(getActivity().getApplicationContext(),
                "8.FirstFragment的onStop被调用！",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "9.FirstFragment的onDestroyView被调用！");
        Toast.makeText(getActivity().getApplicationContext(),
                "9.FirstFragment的onDestroyView被调用！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "10.FirstFragment的onDestroy被调用！");
        Toast.makeText(getActivity().getApplicationContext(),
                "10.FirstFragment的onDestroy被调用！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "11.FirstFragment的onDetach被调用！");
        Toast.makeText(getActivity().getApplicationContext(),
                "11.FirstFragment的onDetach被调用！",Toast.LENGTH_SHORT).show();
    }
}