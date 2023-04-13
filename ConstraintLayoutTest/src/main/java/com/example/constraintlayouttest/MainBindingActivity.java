package com.example.constraintlayouttest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import com.example.constraintlayouttest.databinding.ActivityMainBindingBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainBindingActivity extends AppCompatActivity {

    public static String TAG = "lifecycle_MainBindingActivity";

    ActivityMainBindingBinding mBinding;
    MyViewModel mMyViewModel;
    NumberAndroidViewModel mNumberAndroidViewModel;

    MyChronometer mMyChronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
//        setContentView(R.layout.activity_main_binding);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main_binding);


        //==================1. 第一种方法============================================
        mMyViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        // 设置监听 livedata ，值改变的时候会回调 onChanged
        /**
         * 第一种方法 界面控制怎么显示数值的改变
         */
        mMyViewModel.getIntegerMutableLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mBinding.textView3.setText(String.valueOf(integer));
            }
        });

        mBinding.buttonADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyViewModel.addLikedNumber(1);
            }
        });

        mBinding.buttonJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyViewModel.addLikedNumber(-1);
            }
        });


        //==================2. 第二种方法============================================
        /**
         * 第二种方法，当数据回绑到界面上，当数据更新时，再xml里面自动执行
         * SavedStateViewModelFactory 使用这个创建的viewmodel，当应用在后台被kill后，数据也会保存下来
         */

        mNumberAndroidViewModel = new ViewModelProvider(this,
                new SavedStateViewModelFactory(getApplication(),this)).get(NumberAndroidViewModel.class);
        mBinding.setLiveData(mNumberAndroidViewModel);
        mBinding.setLifecycleOwner(this);




        //======================== Lifecycles 观察者 ========================
        mMyChronometer = findViewById(R.id.Chronometerlifecycles);
        getLifecycle().addObserver(mMyChronometer);
        //======================== Lifecycles 观察者 ========================


        //======================== Serializable序列化使用 ========================
        mBinding.btnSerializable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SerializableTest se = new SerializableTest("曾庆耀", 20, new Score(99, 80));
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(openFileOutput("zengqy.dat",MODE_PRIVATE));
                    oos.writeObject(se);
                    oos.flush();
                    Toast.makeText(MainBindingActivity.this,"Serializable序列化保存成功",Toast.LENGTH_SHORT).show();
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mBinding.btnSerializablefang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectInputStream ois = null;
                try {
                    ois = new ObjectInputStream(openFileInput("zengqy.dat"));
                    SerializableTest se = (SerializableTest)ois.readObject();
                    Toast.makeText(MainBindingActivity.this,"Serializable反序列化成功",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "反序列化: "+se.getName()+" "+se.getAge());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        //=============================Gson对list进行转换====================
        SerializableTest serializableTest = new SerializableTest("zengqy",22,
                new Score(80,81));
        SerializableTest serializableTest1 = new SerializableTest("曾庆耀",30,
                new Score(60,61));

        ArrayList<SerializableTest> arrayList = new ArrayList<>();
        arrayList.add(serializableTest);
        arrayList.add(serializableTest1);

        Gson gson = new Gson();
        String sJson = gson.toJson(arrayList);
        Log.e(TAG, "onCreate: "+sJson);

        Type type = new TypeToken<List<SerializableTest>>() {}.getType();

        ArrayList<SerializableTest> res = gson.fromJson(sJson, type);
        Log.e(TAG, "onCreate:0 "+res.get(0));
        Log.e(TAG, "onCreate:1 "+res.get(1));


    }


    /**
     * 销毁activity的后保存的数据
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }


    /**
     * 数据的保存都放在pause上，stop和destory有可能不会执行到
     */
    @Override
    protected void onPause() {
        super.onPause();
        mNumberAndroidViewModel.saveToShp();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }


}