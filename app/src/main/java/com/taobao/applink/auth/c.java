package com.taobao.applink.auth;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

class c implements ServiceConnection {
    final /* synthetic */ a a;

    c(a aVar) {
        this.a = aVar;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Messenger messenger = new Messenger(iBinder);
        Message obtain = Message.obtain(null, 11801);
        obtain.replyTo = this.a.e;
        try {
            messenger.send(obtain);
        } catch (Throwable unused) {
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.a.b();
        if (this.a.d != null) {
            this.a.d.onFailure();
        }
        this.a.c = null;
        this.a.e = null;
        a.a = null;
    }
}
