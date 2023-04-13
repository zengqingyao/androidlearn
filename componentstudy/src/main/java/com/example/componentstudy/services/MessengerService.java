package com.example.componentstudy.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;


/**
 * 如需让接口跨不同进程工作，您可以使用 Messenger 为服务创建接口。采用这种方式时，
 * 服务会定义一个 Handler，用于响应不同类型的 Message 对象。此 Handler 是 Messenger 的基础，
 * 后者随后可与客户端分享一个 IBinder，以便客户端能利用 Message 对象向服务发送命令。
 * 此外，客户端还可定义一个自有 Messenger，以便服务回传消息。
 * 这是执行进程间通信 (IPC) 最为简单的方式，因为 Messenger 会在单个线程中创建包含所有请求的队列，
 * 这样您就不必对服务进行线程安全设计。
 */

public class MessengerService extends Service {
    /**
     * Command to the service to display a message
     */
    static final int MSG_SAY_HELLO = 1;

    /**
     * Handler of incoming messages from clients.
     */
    static class IncomingHandler extends Handler {
        private Context applicationContext;

        IncomingHandler(Context context) {
            applicationContext = context.getApplicationContext();
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    Toast.makeText(applicationContext, "MessengerService收到信息!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    Messenger mMessenger;

    /**
     * When binding to the service, we return an interface to our messenger
     * for sending messages to the service.
     */
    @Override
    public IBinder onBind(Intent intent) {
//        Toast.makeText(getApplicationContext(), "binding", Toast.LENGTH_SHORT).show();
        mMessenger = new Messenger(new IncomingHandler(this));
        return mMessenger.getBinder();
    }
}