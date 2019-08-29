package defpackage;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import com.autonavi.amap.app.AMapAppGlobal;
import com.huawei.lbs.IHuaweiHiGeoService;
import com.huawei.lbs.IHuaweiHiGeoService.Stub;

/* renamed from: ews reason: default package */
/* compiled from: HwMM */
public final class ews {
    private static ews d;
    IHuaweiHiGeoService a = null;
    public Handler b;
    public HandlerThread c;
    private ServiceConnection e = new ServiceConnection() {
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ewt.a("HwMM", "onServiceConnected.");
            ews.this.a = Stub.asInterface(iBinder);
            StringBuilder sb = new StringBuilder("create mService:  ");
            sb.append(String.valueOf(ews.this.a));
            ewt.a("HwMM", sb.toString());
        }

        public final void onServiceDisconnected(ComponentName componentName) {
            ewt.b("HwMM", "onServiceDisconnected.");
            ews.this.a = null;
        }
    };

    /* renamed from: ews$a */
    /* compiled from: HwMM */
    public class a {
        long a;
        double b;
        double c;
        double d;
        int e;

        public a(long j, double d2, double d3, double d4, int i) {
            this.a = j;
            this.b = d2;
            this.c = d3;
            this.d = d4;
            this.e = i;
        }
    }

    public static synchronized ews a() {
        ews ews;
        synchronized (ews.class) {
            try {
                if (d == null) {
                    d = new ews();
                }
                ews = d;
            }
        }
        return ews;
    }

    public final synchronized void b() {
        if (this.c == null) {
            this.c = new HandlerThread("HwMM") {
                /* access modifiers changed from: protected */
                public final void onLooperPrepared() {
                    ews.this.b = new Handler(getLooper()) {
                        public final void handleMessage(Message message) {
                            if (message.what == 1) {
                                if (ews.this.a == null || message.obj == null) {
                                    ewt.a("HwMM", "mService=null");
                                } else {
                                    try {
                                        a aVar = (a) message.obj;
                                        StringBuilder sb = new StringBuilder("sendMapMatchingResult : timetag :");
                                        sb.append(aVar.a);
                                        sb.append(" heading:");
                                        sb.append(aVar.b);
                                        sb.append(" rerouted:");
                                        sb.append(aVar.e);
                                        sb.append(" latitude : ");
                                        sb.append(aVar.d);
                                        sb.append(" longitude:");
                                        sb.append(aVar.c);
                                        ewt.b("HwMMgps", sb.toString());
                                        ewt.b("HwMM", "sendMapMatchingResult :".concat(String.valueOf(ews.this.a.sendMapMatchingResult(aVar.a, aVar.b, aVar.c, aVar.d, aVar.e))));
                                    } catch (Exception e) {
                                        ewt.a("HwMM", e.getMessage());
                                    }
                                }
                            }
                        }
                    };
                }
            };
            this.c.start();
        }
        if (this.a == null) {
            Intent intent = new Intent();
            intent.putExtra("PACKAGE_NAME", "com.autonavi.minimap");
            intent.setClassName("com.huawei.lbs", "com.huawei.lbs.HwLBSService");
            try {
                AMapAppGlobal.getApplication().getApplicationContext().bindService(intent, this.e, 1);
                StringBuilder sb = new StringBuilder("bindService");
                sb.append(this.e.toString());
                ewt.a("HwMM", sb.toString());
            } catch (Exception e2) {
                StringBuilder sb2 = new StringBuilder("bindIntent err:");
                sb2.append(e2.getMessage());
                ewt.a("HwMM", sb2.toString());
            }
            ewt.a("HwMM", "Connect Service. creat");
            return;
        }
        ewt.a("HwMM", "Connect Service. exist");
    }

    public final synchronized boolean c() {
        ewt.a("HwMM", "disconnectService");
        if (this.c != null) {
            if (this.b != null) {
                this.b.removeCallbacksAndMessages(null);
            }
            this.c.quit();
            this.c = null;
        }
        if (this.a == null || this.e == null) {
            ewt.a("HwMM", "mService == null || mConnection == null");
            this.a = null;
            return false;
        }
        try {
            AMapAppGlobal.getApplication().getApplicationContext().unbindService(this.e);
            this.a = null;
            new StringBuilder("unbindService").append(this.e.toString());
            return true;
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder("disconnectService e:");
            sb.append(e2.getMessage());
            ewt.a("HwMM", sb.toString());
            this.a = null;
            return false;
        }
    }
}
