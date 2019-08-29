package com.amap.location.sdk.c.a;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings.Global;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.location.sdk.e.c;
import com.amap.location.sdk.e.e;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;

/* compiled from: PhoneStateCollector */
public class a {
    /* access modifiers changed from: private */
    public b a = new b();
    /* access modifiers changed from: private */
    public Context b;
    /* access modifiers changed from: private */
    public Handler c = null;
    /* access modifiers changed from: private */
    public ComponentCallbacks2 d = new ComponentCallbacks2() {
        public void onConfigurationChanged(Configuration configuration) {
        }

        public void onLowMemory() {
        }

        public void onTrimMemory(int i) {
            a.this.c.sendEmptyMessage(3);
        }
    };
    /* access modifiers changed from: private */
    public BroadcastReceiver e = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Message obtainMessage = a.this.c.obtainMessage(4);
            obtainMessage.arg1 = intent.getIntExtra("wifi_state", -1);
            a.this.c.sendMessage(obtainMessage);
        }
    };
    /* access modifiers changed from: private */
    public BroadcastReceiver f = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            a.this.c.sendEmptyMessage(5);
        }
    };

    public a(@NonNull Context context, @NonNull Looper looper) {
        this.b = context;
        a(looper);
    }

    public void a() {
        this.c.sendEmptyMessage(1);
    }

    public void b() {
        this.c.sendEmptyMessage(2);
    }

    private void a(Looper looper) {
        this.c = new Handler(looper) {
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        try {
                            if (VERSION.SDK_INT >= 14) {
                                a.this.b.registerComponentCallbacks(a.this.d);
                            }
                            a.this.b.registerReceiver(a.this.e, new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED"));
                            if (VERSION.SDK_INT >= 19) {
                                a.this.b.registerReceiver(a.this.f, new IntentFilter("android.location.PROVIDERS_CHANGED"));
                            }
                            a.this.f();
                            return;
                        } catch (Exception e) {
                            com.amap.location.common.d.a.a((Throwable) e);
                            return;
                        }
                    case 2:
                        try {
                            if (VERSION.SDK_INT >= 14) {
                                a.this.b.unregisterComponentCallbacks(a.this.d);
                            }
                            a.this.b.unregisterReceiver(a.this.e);
                            if (VERSION.SDK_INT >= 19) {
                                a.this.b.unregisterReceiver(a.this.f);
                            }
                            removeCallbacksAndMessages(null);
                            return;
                        } catch (Exception e2) {
                            com.amap.location.common.d.a.a((Throwable) e2);
                            return;
                        }
                    case 3:
                        a.this.a.b(1);
                        a.this.f();
                        return;
                    case 4:
                        if (3 == message.arg1) {
                            a.this.a.d(1);
                            a.this.i();
                        } else {
                            a.this.a.d(0);
                        }
                        a.this.f();
                        return;
                    case 5:
                        a.this.a.i(com.amap.location.g.b.a.a(a.this.b).a((String) WidgetType.GPS) ? 1 : 0);
                        a.this.f();
                        break;
                }
            }
        };
    }

    private void c() {
        this.a.a(d() ? 1 : 0);
        if (VERSION.SDK_INT < 14) {
            this.a.b(e());
        }
        this.a.c(c.a() ? 1 : 0);
        this.a.d(com.amap.location.g.d.a.a(this.b).g() ? 1 : 0);
        ConnectivityManager connectivityManager = (ConnectivityManager) this.b.getSystemService("connectivity");
        int i = 0;
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                this.a.e(activeNetworkInfo.isConnected() ? 1 : 0);
                this.a.f(activeNetworkInfo.getType() == 1 ? 1 : 0);
            }
        }
        this.a.g(g());
        if (e.a(this.b, "android.permission.ACCESS_FINE_LOCATION") || e.a(this.b, "android.permission.ACCESS_COARSE_LOCATION")) {
            i = 1;
        }
        this.a.h(i);
        this.a.i(com.amap.location.g.b.a.a(this.b).a((String) WidgetType.GPS) ? 1 : 0);
        this.a.j(h());
        if (1 == this.a.a()) {
            i();
        }
        this.a.c(System.currentTimeMillis());
    }

    private boolean d() {
        try {
            return VERSION.SDK_INT < 17 ? System.getInt(this.b.getContentResolver(), "airplane_mode_on", 0) == 1 : Global.getInt(this.b.getContentResolver(), "airplane_mode_on", 0) != 0;
        } catch (Exception unused) {
            return false;
        }
    }

    private int e() {
        try {
            ActivityManager activityManager = (ActivityManager) this.b.getSystemService(WidgetType.ACTIVITY);
            if (activityManager != null) {
                MemoryInfo memoryInfo = new MemoryInfo();
                activityManager.getMemoryInfo(memoryInfo);
                return memoryInfo.lowMemory ? 1 : 0;
            }
        } catch (Exception unused) {
        }
        return 2;
    }

    /* access modifiers changed from: private */
    public void f() {
        try {
            c();
            StringBuilder sb = new StringBuilder("phone status:100005[");
            sb.append(this.a.toString());
            sb.append("]");
            com.amap.location.common.d.a.b("PhoneState", sb.toString());
        } catch (Exception e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
        }
    }

    private int g() {
        switch (com.amap.location.g.a.a.a(this.b).c()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return 3;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return 2;
            case 13:
                return 1;
            default:
                return 0;
        }
    }

    private int h() {
        if (VERSION.SDK_INT >= 23) {
            return 2;
        }
        String string = Secure.getString(this.b.getContentResolver(), "mock_location");
        return (TextUtils.isEmpty(string) || "0".equals(string)) ? 0 : 1;
    }

    /* access modifiers changed from: private */
    public void i() {
        DhcpInfo f2 = com.amap.location.g.d.a.a(this.b).f();
        if (f2 != null) {
            this.a.a((long) f2.dns1);
            this.a.b((long) f2.dns2);
        }
    }
}
