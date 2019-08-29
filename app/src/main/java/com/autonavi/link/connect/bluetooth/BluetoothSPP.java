package com.autonavi.link.connect.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.autonavi.link.connect.listener.Connection.OnBtStateChangeListener;
import com.autonavi.link.connect.model.DiscoverInfo;
import java.lang.ref.WeakReference;

@SuppressLint({"NewApi"})
public class BluetoothSPP {
    public static final String BLUETOOTH_HOST = "127.0.0.1";
    private static volatile BluetoothSPP a;
    private WeakReference<Context> b;
    private BluetoothAdapter c = null;
    /* access modifiers changed from: private */
    public b d = null;
    /* access modifiers changed from: private */
    public String e = null;
    /* access modifiers changed from: private */
    public String f = null;
    private boolean g = false;
    /* access modifiers changed from: private */
    public DiscoverInfo h;
    @SuppressLint({"HandlerLeak"})
    private final Handler i = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    BluetoothSPP.this.e = message.getData().getString("device_name");
                    BluetoothSPP.this.f = message.getData().getString("device_address");
                    BluetoothSPP.this.h = (DiscoverInfo) message.getData().getSerializable("device_info");
                    if (BluetoothSPP.this.k != null) {
                        BluetoothSPP.this.k.onStateChange(1, BluetoothSPP.this.h);
                        return;
                    }
                    break;
                case 2:
                    int i = message.arg1;
                    if (BluetoothSPP.this.d != null) {
                        BluetoothSPP.this.d.a(false);
                    }
                    if (BluetoothSPP.this.k != null) {
                        if (i != 1 && i != 2 && i != -1) {
                            if (BluetoothSPP.this.k != null) {
                                BluetoothSPP.this.k.onStateChange(i, BluetoothSPP.this.h);
                                break;
                            }
                        } else {
                            BluetoothSPP.this.k.onStateChange(-1, BluetoothSPP.this.h);
                            BluetoothSPP.this.e = null;
                            BluetoothSPP.this.f = null;
                            return;
                        }
                    }
                    break;
            }
        }
    };
    private final BroadcastReceiver j = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(intent.getAction())) {
                int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1);
                if (intExtra == 10 || intExtra == 13) {
                    if (BluetoothSPP.this.k != null) {
                        BluetoothSPP.this.k.onStateChange(-5, BluetoothSPP.this.h);
                    }
                } else if (intExtra == 12 && BluetoothSPP.this.k != null) {
                    BluetoothSPP.this.k.onStateChange(2, BluetoothSPP.this.h);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public OnBtStateChangeListener k;

    public static BluetoothSPP getInstance(Context context) {
        if (a == null) {
            synchronized (BluetoothSPP.class) {
                try {
                    a = new BluetoothSPP(context);
                }
            }
        }
        return a;
    }

    private BluetoothSPP(Context context) {
        if (context != null) {
            this.b = new WeakReference<>(context);
        }
        this.c = BluetoothAdapter.getDefaultAdapter();
    }

    private void a() {
        try {
            if (this.b != null) {
                Context context = (Context) this.b.get();
                if (context != null && !this.g) {
                    this.g = true;
                    context.registerReceiver(this.j, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
                }
            }
        } catch (Exception unused) {
        }
    }

    private void b() {
        try {
            if (this.b != null) {
                Context context = (Context) this.b.get();
                if (context != null && this.g) {
                    this.g = false;
                    context.unregisterReceiver(this.j);
                }
            }
        } catch (Exception unused) {
        }
    }

    private boolean c() {
        return this.d != null;
    }

    private void d() {
        this.d = new b(this.i);
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return this.c;
    }

    public boolean getIsConnect() {
        if (this.d != null) {
            return this.d.b();
        }
        return false;
    }

    private void e() {
        if (this.d != null && this.d.a() == 0) {
            this.d.b(true);
        }
    }

    private void f() {
        if (this.d != null) {
            this.d.c();
        }
    }

    private void a(String str) {
        try {
            this.d.a(this.c.getRemoteDevice(str));
        } catch (IllegalArgumentException unused) {
            this.i.obtainMessage(2, -2, -1).sendToTarget();
        }
    }

    public String getConnectedDeviceName() {
        return this.e;
    }

    public String getConnectedDeviceAddress() {
        return this.f;
    }

    public void doBtConnect(String str) {
        stopBt();
        if (!c()) {
            d();
        }
        a();
        a(str);
    }

    public void startBtServer() {
        stopBt();
        if (!c()) {
            d();
        }
        a();
        e();
    }

    public void stopBt() {
        f();
        b();
    }

    public void setOnBtStateChangeListener(OnBtStateChangeListener onBtStateChangeListener) {
        this.k = onBtStateChangeListener;
    }
}
