package com.example.constraintlayouttest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

/**
 * @包名: com.example.constraintlayouttest
 * @author: zengqy
 * @DATE: 2023/4/12 21:58
 * @描述:
 *       viewModel来管理的数据，当activity被destory或者重新创建的时候(比如，切换系统语言，旋转屏幕)，数据不会丢失
 *       liveData 可以监控数据的更改，observe进行监测，或者在layout.xml里面回绑界面自动更新
 *       AndroidViewModel，所有就有context了
 *       同时使用SharedPreferences ，所以永久保存数据
 */
public class NumberAndroidViewModel extends AndroidViewModel {

    private SavedStateHandle mSavedStateHandle;

    // 获取mContext
    private Context mContext = getApplication().getApplicationContext();


    private String mkey = "NUMBER";

    // sha
    private String mShpName = "NumberAndroidViewModel";


    public NumberAndroidViewModel(@NonNull Application application , SavedStateHandle handle) {
        super(application);
        mSavedStateHandle = handle;
        if(!mSavedStateHandle.contains(mkey))
        {
            loadFromShp();
        }

    }

    /**
     * 从sp里面获取数据
     */
    private void loadFromShp() {

        SharedPreferences sp = mContext.getSharedPreferences(mShpName, Context.MODE_PRIVATE);
        mSavedStateHandle.set(mkey,sp.getInt(mkey,0));
    }


    /**
     * 把值保存到sp里面
     */
    public void saveToShp()
    {
        SharedPreferences sp = mContext.getSharedPreferences(mShpName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(mkey,getNumber().getValue());
        editor.apply();
    }



    /**
     * 返回Number的值
     * @return
     */
    public LiveData<Integer> getNumber()
    {
        return mSavedStateHandle.getLiveData(mkey);
    }

    /**
     * 计算number的值
     * @param n
     */
    public void calculateNumber(int n)
    {
        mSavedStateHandle.set(mkey,(int) mSavedStateHandle.get(mkey)+n);
    }


}
