package com.cornucopia.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

public class MessengerService extends Service  {
    
    static final int HANDLE_MESSENGER = 1;

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what) {
            case HANDLE_MESSENGER: {
                Object obj = msg.obj;
                String content = "";
                if (obj instanceof String) {
                    content = new StringBuffer().append(obj).toString();
                }
                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
                break;
            }
            default: {
                break;
            }
            }
        }
    }
    
    final Messenger messenger = new Messenger(new IncomingHandler());
    
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "binding messenger service", Toast.LENGTH_SHORT).show();
        return messenger.getBinder();
    }
    
    

}
