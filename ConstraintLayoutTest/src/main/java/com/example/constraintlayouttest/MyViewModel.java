package com.example.constraintlayouttest;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @包名: com.example.constraintlayouttest
 * @author: zengqy
 * @DATE: 2022/12/6 16:41
 * @描述:
 */
public class MyViewModel extends ViewModel {



    private MutableLiveData<Integer> mIntegerMutableLiveData;

    public MutableLiveData<Integer> getIntegerMutableLiveData() {

        if (mIntegerMutableLiveData == null) {
            mIntegerMutableLiveData = new MutableLiveData<>();
            mIntegerMutableLiveData.setValue(0);
        }

        return mIntegerMutableLiveData;
    }

    public void addLikedNumber(int n) {
        if (mIntegerMutableLiveData != null) {
            mIntegerMutableLiveData.setValue(mIntegerMutableLiveData.getValue() + n);
        }
    }

    public MutableLiveData<Integer> getLiveData()
    {
        return mIntegerMutableLiveData;
    }

    public MyViewModel() {
    }

}
