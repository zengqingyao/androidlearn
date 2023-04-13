package com.example.componentstudy.activitys;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.componentstudy.IAidlPersonService;
import com.example.componentstudy.R;
import com.example.componentstudy.pojo.Person;
import com.example.componentstudy.services.AidlPersonService;

import java.util.List;

public class ServiceAidlPersonActivity extends AppCompatActivity {

    private static final String TAG = "CMP_AidlPersonActivity";

    private TextView mTextView;
    private Intent mIntent;
    private boolean mIsbind;


    private IAidlPersonService mIAidlPersonService;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: 服务已经绑定并且返回binder");
            mIAidlPersonService = IAidlPersonService.Stub.asInterface(service);
            Toast.makeText(getApplicationContext(), "onServiceConnected服务已经绑定完成",
                    Toast.LENGTH_SHORT).show();
        }

        /**
         * 方法在正常情况下是不被调用的，它的调用时机是当Service服务被异外销毁时，例如内存的资源不足时这个方法才被自动调用。
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_aidl_person);
        mTextView = findViewById(R.id.tv_person_view);
        mIntent = new Intent(this, AidlPersonService.class);

    }

    public void startAidlPersonService(View view) {
        mTextView.setText("启动AIDL服务");
        startService(mIntent);
    }

    public void stopAidlPersonService(View view) {
        if (mIntent != null) {
            stopService(mIntent);
            mTextView.setText("停止AIDL服务");
        }
    }

    public void bindAidlPersonService(View view) {

        mIsbind = bindService(mIntent, mServiceConnection, BIND_AUTO_CREATE);
        mTextView.setText("AIDL服务已经绑定");
        Log.d(TAG, "bindService: " + mIsbind);
    }

    public void unbindAidlPersonService(View view) {
        if (mIsbind && mServiceConnection!=null) {
            unbindService(mServiceConnection);
            mIAidlPersonService=null;
            mIsbind=false;
        }
    }


    public void callRemoteAddPersonMethod(View view) {
        if (mIAidlPersonService != null) {
            try {
                int size = mIAidlPersonService.getPersonList().size();
                mIAidlPersonService.addPerson(new Person("曾庆耀",size,"男"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(getApplicationContext(), "错误：没有绑定AIDL服务",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void callRemoteGetPersonMethod(View view) {

        if (mIAidlPersonService != null) {
            try {
                List<Person> personList = mIAidlPersonService.getPersonList();
                for (Person person : personList) {
                    Log.d(TAG, "callRemoteGetPersonMethod: "+person);
                    Toast.makeText(getApplicationContext(), person.toString(),
                            Toast.LENGTH_SHORT).show();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "错误：没有绑定AIDL服务",
                    Toast.LENGTH_SHORT).show();
        }
    }
}