package com.example.appservice;

import android.app.Service;
import android.content.ContentProvider;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import androidx.annotation.Nullable;

import java.util.Random;



/*
在您的服务中，创建可执行以下某种操作的 Binder 实例：
包含客户端可调用的公共方法。
返回当前的 Service 实例，该实例中包含客户端可调用的公共方法。
返回由服务承载的其他类的实例，其中包含客户端可调用的公共方法。
从 onBind() 回调方法返回此 Binder 实例。
在客户端中，从 onServiceConnected() 回调方法接收 Binder，并使用提供的方法调用绑定服务。
注意：服务和客户端必须在同一应用内，这样客户端才能转换返回的对象并正确调用其 API。服务和客户端还必须在同一进程内，
因为此方式不执行任何跨进程编组
https://developer.android.com/guide/components/bound-services#Binder
 */

public class LocalService extends Service {
    private final Random mGenerator = new Random();

    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder{
        LocalService getService()
        {
            return LocalService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /** method for clients */
    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }
}
