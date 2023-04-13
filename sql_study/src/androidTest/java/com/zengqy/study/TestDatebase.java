package com.zengqy.study;

import android.content.Context;
import android.util.Log;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @包名: com.zengqy.study
 * @USER: zengqy
 * @DATE: 2022/4/7 17:27
 * @描述:
 */

@RunWith(AndroidJUnit4.class)
public class TestDatebase {

    private static final String TAG = "TestDatebase";

    @Test
    public void test_insert(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Dao dao = new Dao(appContext);
        dao.insert();
        Log.e(TAG, "test_insert: " );
    }

    @Test
    public void test_delete(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Dao dao = new Dao(appContext);
        dao.delete();
        Log.e(TAG, "test_insert: " );
    }

    @Test
    public void test_update(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Dao dao = new Dao(appContext);
        dao.update();
        Log.e(TAG, "test_insert: " );
    }

    @Test
    public void test_query(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        Dao dao = new Dao(appContext);
        dao.query();
        Log.e(TAG, "test_insert: " );
    }

}
