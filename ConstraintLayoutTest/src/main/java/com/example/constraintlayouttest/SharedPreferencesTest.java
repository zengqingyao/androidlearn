package com.example.constraintlayouttest;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @包名: com.example.constraintlayouttest
 * @author: zengqy
 * @DATE: 2022/12/6 21:05
 * @描述:
 */
public class SharedPreferencesTest {

    private Context mContext;

    public SharedPreferencesTest(Context context) {
        mContext = context;
    }

    public boolean saveString(String key,String value)
    {
        SharedPreferences shp = mContext.getSharedPreferences("zengqy",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = shp.edit();
        edit.putString(key,value);
        edit.apply();
        return true;
    }

    public boolean saveInt(String key,int value)
    {
        SharedPreferences shp = mContext.getSharedPreferences("zengqy",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = shp.edit();
        edit.putInt(key,value);
        edit.apply();
        return true;
    }

    public String loadString(String key)
    {
        SharedPreferences shp = mContext.getSharedPreferences("zengqy",Context.MODE_PRIVATE);
        return shp.getString(key,null);
    }

    public int loadInt(String key)
    {
        SharedPreferences shp = mContext.getSharedPreferences("zengqy",Context.MODE_PRIVATE);
        return shp.getInt(key,0);
    }
}
