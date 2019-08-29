package defpackage;

import android.app.Service;
import android.os.HandlerThread;
import com.amap.bundle.logs.AMapLog;
import java.util.ArrayList;
import java.util.List;

/* renamed from: wt reason: default package */
/* compiled from: LotusPoolManager */
public class wt {
    public static final String a = "wt";
    public a b;
    public Service c;

    /* renamed from: wt$a */
    /* compiled from: LotusPoolManager */
    public static class a extends HandlerThread {
        public ws a = null;
        public final List<Integer> b = new ArrayList();
        private Service c;

        public a(String str, Service service) {
            super(str);
            this.c = service;
        }

        /* access modifiers changed from: protected */
        public final void onLooperPrepared() {
            super.onLooperPrepared();
            synchronized (this.b) {
                this.a = new ws(getLooper(), this.c);
                if (this.b.size() > 0) {
                    for (Integer intValue : this.b) {
                        int intValue2 = intValue.intValue();
                        if (-1 != intValue2) {
                            this.a.a(intValue2);
                        } else {
                            a();
                        }
                    }
                    this.b.clear();
                }
            }
        }

        /* access modifiers changed from: private */
        public void a() {
            this.a.removeCallbacksAndMessages(null);
            this.a.post(new Runnable() {
                public final void run() {
                    ws b = a.this.a;
                    if (b.b != null) {
                        xf xfVar = b.b;
                        xfVar.d.clear();
                        xfVar.b.clear();
                        xe xeVar = xfVar.e;
                        xd xdVar = xeVar.a;
                        xdVar.a.getIdentityScope().clear();
                        xdVar.b.getIdentityScope().clear();
                        xeVar.a = null;
                        xeVar.b.close();
                        xeVar.b = null;
                        b.b = null;
                    }
                    if (b.d != null) {
                        try {
                            b.c.unbindService(b.d);
                        } catch (Exception unused) {
                            if (bno.a) {
                                AMapLog.w(ws.a, "IllegalArgumentException: Service not registered");
                            }
                        }
                        b.d = null;
                    }
                    a.this.quit();
                    a.this.a = null;
                }
            });
        }
    }

    public wt(Service service) {
        this.c = service;
    }
}
