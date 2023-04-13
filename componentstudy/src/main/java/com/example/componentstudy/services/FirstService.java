package com.example.componentstudy.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.example.componentstudy.interfaces.IFirstServiceMethod;

/**
 * @包名: com.example.componentstudy.services
 * @author: zengqy
 * @DATE: 2022/12/9 22:28
 * @描述:
 */
public class FirstService extends Service {

    private static final String TAG = "CMP_FirstService";


    private class InnerBinder extends Binder implements IFirstServiceMethod
    {

        @Override
        public void callIsServiceMethod() {
            thisIsServiceMethod();
        }
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this,"服务的onBind被调用",Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onBind: ");
        return new InnerBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        Toast.makeText(this,"服务的onCreate被调用",Toast.LENGTH_SHORT).show();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        Toast.makeText(this,"服务的onStartCommand被调用",Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        Toast.makeText(this,"服务的onUnbind被调用",Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        Toast.makeText(this,"服务的onDestroy被调用",Toast.LENGTH_SHORT).show();
    }


    private void thisIsServiceMethod()
    {
        Log.d(TAG, "thisIsServiceMethod: 服务的内部方法被调用");
        Toast.makeText(this,"服务的内部方法被调用",Toast.LENGTH_SHORT).show();
    }
    
}
