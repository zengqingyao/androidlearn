package com.example.appservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "HELLO";
    private String mServerType = "AIDL";

    TextView mTextView;
    Button mBtnStartService;
    Button mBtnStopService;
    Button mBtnBindService;
    Button mBtnUnbindService;

    Intent serviceIntent;
    Intent messengerIntet;
    Intent activityIntent;

    IAidlServiceInterface mBinder;
    Messenger mMessenger;

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected: MainActivity TYPE:"+mServerType );

            if(mServerType.equals("AIDL")) { //AIDL获取
                mBinder = IAidlServiceInterface.Stub.asInterface(service);
                mTextView.setText("连接AIDL成功");
            }else if(mServerType.equals("Messenger")) { //Messenger获取{
                mTextView.setText("Messenger连接成功");
                mMessenger = new Messenger(service);

                Message msg = Message.obtain(null, 1, 0, 0);
                try {
                    mMessenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected: MainActivity" );

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {
            String save = savedInstanceState.getString("test");
            Log.e(TAG, "onCreate: 保存的值为："+save );
        }


        Log.e(TAG, "onCreate: MainActivity :");
        mTextView = findViewById(R.id.textViedShow);
        mBtnStartService = findViewById(R.id.btnStartSevrice);
        mBtnStopService = findViewById(R.id.btnStopSevrice);
        mBtnBindService = findViewById(R.id.btnBindevrice);
        mBtnUnbindService = findViewById(R.id.btnUnbindSevrice);

        serviceIntent = new Intent();
        serviceIntent.setClassName("com.example.appservice","" +
                "" +
                "");

        messengerIntet = new Intent();
        messengerIntet.setClassName("com.example.appservice","com.example.appservice.MessengerService");

        activityIntent = new Intent();
        activityIntent.setClassName("com.example.appservice","com.example.appservice.MainActivity");


        findViewById(R.id.btnSendBroadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setFlags(0x01000000);
                intent.setAction("android.intent.action.MY_TEST_ZENGQY");
                Log.e(TAG, "onClick: SendBroadcast: " +intent);
                sendBroadcast(intent);
            }
        });

        mBtnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServerType="AIDL";
                mTextView.setText("启动服务");
                startService(serviceIntent);
            }
        });


        mBtnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServerType="AIDL";
                mTextView.setText("停止服务");
                stopService(serviceIntent);
            }
        });

        mBtnBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServerType="AIDL";
                mTextView.setText("绑定服务");
                bindService(serviceIntent,mServiceConnection,BIND_AUTO_CREATE);
            }
        });

        mBtnUnbindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(mServiceConnection);
            }
        });

        findViewById(R.id.btnAddPerson).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServerType="AIDL";
                if(mBinder==null)
                    return;
                try {
                    mBinder.addPerson(new Person("测试姓名",99,"测试性别"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });

        findViewById(R.id.btnGetPerson).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServerType="AIDL";
                try {
                    if(mBinder==null)
                        return;
                    List<Person> list = mBinder.getPersonList();
                    for (Person person : list) {
                        Log.e(TAG, "onClick: " + person.getName() + "-" + person.getSex() +
                                "-" + person.getAge());
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });


        findViewById(R.id.btnMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServerType = "Messenger";
                bindService(messengerIntet,mServiceConnection,BIND_AUTO_CREATE);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: MainActivity" );
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: MainActivity" );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: MainActivity" );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: MainActivity" );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: MainActivity" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: MainActivity" );
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("test","测试是否正常保存");
    }

}