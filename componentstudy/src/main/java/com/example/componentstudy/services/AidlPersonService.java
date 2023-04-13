package com.example.componentstudy.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.example.componentstudy.IAidlPersonService;
import com.example.componentstudy.pojo.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * @包名: com.example.componentstudy.services
 * @author: zengqy
 * @DATE: 2022/12/11 15:31
 * @描述:
 *  Android 接口定义语言 (AIDL) 会将对象分解成原语，
 *  操作系统可通过识别这些原语并将其编组到各进程中来执行 IPC。
 *  以前采用 Messenger 的方式实际上是以 AIDL 作为其底层结构。如上所述，
 *  Messenger 会在单个线程中创建包含所有客户端请求的队列，以便服务一次接收一个请求。
 *  不过，如果您想让服务同时处理多个请求，可以直接使用 AIDL。在此情况下，您的服务必须达到线程安全的要求，
 *  并且能够进行多线程处理。
 */
public class AidlPersonService extends Service {

    private static final String TAG = "CMP_AidlPersonService";

    ArrayList<Person> mPersonList = new ArrayList<>();

    private IBinder mBinder = new IAidlPersonService.Stub() {
        @Override
        public boolean addPerson(Person person) throws RemoteException {
            mPersonList.add(person);
            return false;
        }

        @Override
        public List<Person> getPersonList() throws RemoteException {
            return mPersonList;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: AidlPersonService" );
        Toast.makeText(this,"AidlPersonService的onBind被调用",Toast.LENGTH_SHORT).show();
        return mBinder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mPersonList.add(new Person("onCreate调用后自动添加一条",29,"男"));

        Log.d(TAG, "onCreate: AidlPersonService" );
        Toast.makeText(this,"AidlPersonService的onCreate被调用",Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: AidlPersonService" );
        Toast.makeText(this,"AidlPersonSe的onStartCommand被调用",Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: AidlPersonService" );
        Toast.makeText(this,"AidlPersonService的onUnbind被调用",Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind: AidlPersonService" );
        Toast.makeText(this,"AidlPersonService的onRebind被调用",Toast.LENGTH_SHORT).show();
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: AidlPersonService" );
        Toast.makeText(this,"AidlPersonService的onDestroy被调用",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
