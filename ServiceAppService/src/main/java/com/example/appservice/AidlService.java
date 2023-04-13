package com.example.appservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AidlService extends Service {
    private static final String TAG = "HELLO";
    ArrayList<Person> mPersonList = new ArrayList<>();


    private IBinder mBinder = new IAidlServiceInterface.Stub() {
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


    public AidlService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mPersonList.add(new Person("曾庆耀",29,"男"));

        Log.e(TAG, "onCreate: AidlService" );
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: AidlService" );
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.e(TAG, "onBind: AidlService" );

        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind: AidlService" );
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.e(TAG, "onRebind: AidlService" );
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: AidlService" );
        super.onDestroy();
    }


}