package com.autonavi.link.adapter.server.a;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.autonavi.link.adapter.b.c;

/* compiled from: BaseTransmitStation */
public abstract class a {
    private Thread a = new C0045a();
    private Handler b = null;

    /* renamed from: com.autonavi.link.adapter.server.a.a$a reason: collision with other inner class name */
    /* compiled from: BaseTransmitStation */
    class C0045a extends Thread {
        private C0045a() {
        }

        public void run() {
            Looper.prepare();
            a.this.b();
            Looper.loop();
        }
    }

    /* access modifiers changed from: protected */
    public abstract void a(byte[] bArr);

    public a() {
        this.a.start();
    }

    public void a() {
        if (this.b != null) {
            this.b.getLooper().quit();
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        this.b = new Handler() {
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 5:
                        a.this.c(message.arg1, message.arg2);
                        return;
                    case 6:
                        a.this.d(message.arg1, message.arg2);
                        return;
                    case 7:
                        Bundle data = message.getData();
                        a.this.b(data.getString("handler_bundle_car_name"), data.getString("handler_bundle_des_name"));
                        return;
                    case 8:
                        a.this.c(message.getData().getByteArray("handler_bundle_data"));
                        break;
                }
            }
        };
    }

    public void a(int i, int i2) {
        if (this.b != null) {
            Message message = new Message();
            message.what = 5;
            message.arg1 = i;
            message.arg2 = i2;
            this.b.sendMessage(message);
        }
    }

    public void b(int i, int i2) {
        if (this.b != null) {
            Message message = new Message();
            message.what = 6;
            message.arg1 = i;
            message.arg2 = i2;
            this.b.sendMessage(message);
        }
    }

    public void a(String str, String str2) {
        if (this.b != null) {
            Message message = new Message();
            message.what = 7;
            Bundle bundle = new Bundle();
            bundle.putString("handler_bundle_car_name", str);
            bundle.putString("handler_bundle_des_name", str2);
            message.setData(bundle);
            this.b.sendMessage(message);
        }
    }

    public void b(byte[] bArr) {
        if (this.b != null) {
            Message message = new Message();
            message.what = 8;
            Bundle bundle = new Bundle();
            bundle.putByteArray("handler_bundle_data", bArr);
            message.setData(bundle);
            this.b.sendMessage(message);
        }
    }

    /* access modifiers changed from: private */
    public void c(int i, int i2) {
        byte[] bArr = new byte[10];
        com.autonavi.link.utils.a.a(bArr, 0, 35);
        com.autonavi.link.utils.a.b(bArr, 2, i);
        com.autonavi.link.utils.a.b(bArr, 6, i2);
        a(c.a(bArr, true));
    }

    /* access modifiers changed from: private */
    public void d(int i, int i2) {
        byte[] bArr = new byte[8];
        com.autonavi.link.utils.a.a(bArr, 0, 37);
        com.autonavi.link.utils.a.a(bArr, 2, i);
        com.autonavi.link.utils.a.b(bArr, 4, i2);
        a(c.a(bArr, true));
    }

    /* access modifiers changed from: private */
    public void b(String str, String str2) {
        byte[] bytes = str.getBytes();
        byte[] bytes2 = str2.getBytes();
        int length = bytes.length;
        int length2 = bytes2.length;
        int i = length + 6;
        byte[] bArr = new byte[(i + 4 + length2 + 4)];
        com.autonavi.link.utils.a.a(bArr, 0, 52);
        com.autonavi.link.utils.a.b(bArr, 2, length);
        System.arraycopy(bytes, 0, bArr, 6, length);
        com.autonavi.link.utils.a.b(bArr, i, length2);
        int i2 = length + 10;
        System.arraycopy(bytes2, 0, bArr, i2, length2);
        com.autonavi.link.utils.a.b(bArr, i2 + length2, 0);
        a(c.a(bArr, true));
    }

    /* access modifiers changed from: private */
    public void c(byte[] bArr) {
        if (bArr != null) {
            int length = bArr.length;
            int i = length + 6;
            byte[] bArr2 = new byte[(i + 4)];
            com.autonavi.link.utils.a.a(bArr2, 0, 45);
            com.autonavi.link.utils.a.b(bArr2, 2, length);
            System.arraycopy(bArr, 0, bArr2, 6, length);
            com.autonavi.link.utils.a.b(bArr2, i, 0);
            a(c.a(bArr2, true));
        }
    }
}
