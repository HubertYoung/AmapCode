package defpackage;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.network.util.NetworkReachability.NetworkType;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.json.JSONObject;

/* renamed from: ik reason: default package */
/* compiled from: AdiuService */
public class ik {
    public static final String a;
    private static volatile ik l;
    public Context b;
    public a c;
    public volatile String d = "";
    public volatile String e = "";
    /* access modifiers changed from: private */
    public ReentrantReadWriteLock f = new ReentrantReadWriteLock();
    /* access modifiers changed from: private */
    public Handler g;
    /* access modifiers changed from: private */
    public int h = 0;
    private int i = 2;
    private long j = 60000;
    private volatile boolean k = false;
    private Runnable m = new Runnable() {
        public final void run() {
            if (!ik.this.b()) {
                boolean a2 = il.a(ik.this.b);
                if (bno.a) {
                    AMapLog.debug("paas.adiu", "AdiuService", "当前请求adiu时的网络状态 ".concat(String.valueOf(a2)));
                }
                if (a2) {
                    ik.this.h = ik.this.h + 1;
                    ik.this.a((String) "B015", 2);
                    new im(ik.this.b).a(ik.this.o);
                    ik.this.d();
                    return;
                }
                ik.this.a((String) "B015", 3);
                NetworkReachability.a(ik.this.n);
                return;
            }
            if (ik.this.c != null) {
                ik.this.c.a();
            }
            ik.this.a((String) "B015", 1);
            if (bno.a) {
                AMapLog.debug("paas.adiu", "AdiuService", "本地已经存在adiu！！！");
            }
        }
    };
    /* access modifiers changed from: private */
    public com.amap.bundle.network.util.NetworkReachability.a n = new com.amap.bundle.network.util.NetworkReachability.a() {
        public final void a(NetworkType networkType) {
            boolean b = NetworkReachability.b();
            if (bno.a) {
                AMapLog.debug("paas.adiu", "AdiuService", "收到当前网络状态回调 connected ".concat(String.valueOf(b)));
            }
            if (b) {
                NetworkReachability.b(ik.this.n);
                if (ik.this.c != null) {
                    ik.this.c.a(new Runnable() {
                        public final void run() {
                            ik.this.d();
                        }
                    });
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public defpackage.im.a o = new defpackage.im.a() {
        public final void a(String str) {
            if (!TextUtils.isEmpty(str)) {
                AMapLog.info("paas.adiu", "AdiuService", "onAdiuValue:".concat(String.valueOf(str)));
                ik.this.d = str;
                chw.a(ik.this.b, ik.a, str);
            }
        }
    };

    /* renamed from: ik$a */
    /* compiled from: AdiuService */
    public final class a extends HandlerThread {
        private volatile boolean b;

        a(String str) {
            super(str, 10);
        }

        public final void a(Runnable runnable) {
            ik.this.f.readLock().lock();
            try {
                if (ik.this.g != null) {
                    ik.this.g.post(runnable);
                }
            } finally {
                ik.this.f.readLock().unlock();
            }
        }

        /* access modifiers changed from: 0000 */
        public final void a(Runnable runnable, long j) {
            ik.this.f.readLock().lock();
            try {
                if (ik.this.g != null) {
                    ik.this.g.postDelayed(runnable, j);
                }
            } finally {
                ik.this.f.readLock().unlock();
            }
        }

        /* access modifiers changed from: 0000 */
        public final synchronized void a() {
            if (ik.this.c != null) {
                ik.this.c.b = true;
            }
            ik.this.f.writeLock().lock();
            final Handler b2 = ik.this.g;
            ik.this.g = null;
            ik.this.f.writeLock().unlock();
            if (b2 != null) {
                b2.removeCallbacksAndMessages(null);
                b2.post(new Runnable() {
                    public final void run() {
                        Looper looper = b2.getLooper();
                        if (looper != null) {
                            looper.quit();
                        }
                    }
                });
            }
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: protected */
        public final void onLooperPrepared() {
            if (this.b) {
                Looper looper = getLooper();
                if (looper != null) {
                    looper.quit();
                }
                return;
            }
            ik.this.f.writeLock().lock();
            try {
                ik.this.g = new Handler(Looper.myLooper());
                ik.this.f.writeLock().unlock();
                ik.this.a(kj.a());
            } catch (Throwable th) {
                ik.this.f.writeLock().unlock();
                throw th;
            }
        }
    }

    static {
        if (bno.c) {
            a = cib.a((String) "amap_device_adiu_test");
        } else {
            a = cib.a((String) "amap_device_adiu");
        }
    }

    public static ik a() {
        if (l == null) {
            synchronized (ik.class) {
                try {
                    if (l == null) {
                        l = new ik();
                    }
                }
            }
        }
        return l;
    }

    public final synchronized void a(Context context) {
        if (bno.a) {
            AMapLog.debug("paas.adiu", "AdiuService", "AdiuService初始化");
        }
        if (!this.k) {
            if (context != null) {
                this.b = context.getApplicationContext();
            }
            this.c = new a("AdiuService-T");
            this.c.start();
            this.k = true;
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0020, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x006b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(boolean r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            java.lang.String r0 = r3.d     // Catch:{ all -> 0x006c }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x006c }
            if (r0 != 0) goto L_0x0021
            ik$a r4 = r3.c     // Catch:{ all -> 0x006c }
            if (r4 == 0) goto L_0x0012
            ik$a r4 = r3.c     // Catch:{ all -> 0x006c }
            r4.a()     // Catch:{ all -> 0x006c }
        L_0x0012:
            boolean r4 = defpackage.bno.a     // Catch:{ all -> 0x006c }
            if (r4 == 0) goto L_0x001f
            java.lang.String r4 = "paas.adiu"
            java.lang.String r0 = "AdiuService"
            java.lang.String r1 = "本地内存已存在adiu"
            com.amap.bundle.logs.AMapLog.debug(r4, r0, r1)     // Catch:{ all -> 0x006c }
        L_0x001f:
            monitor-exit(r3)
            return
        L_0x0021:
            boolean r0 = r3.b()     // Catch:{ all -> 0x006c }
            if (r0 != 0) goto L_0x003e
            if (r4 == 0) goto L_0x003e
            java.lang.String r4 = "B014"
            r1 = 0
            r3.a(r4, r1)     // Catch:{ all -> 0x006c }
            im r4 = new im     // Catch:{ all -> 0x006c }
            android.content.Context r1 = r3.b     // Catch:{ all -> 0x006c }
            r4.<init>(r1)     // Catch:{ all -> 0x006c }
            im$a r1 = r3.o     // Catch:{ all -> 0x006c }
            r4.a(r1)     // Catch:{ all -> 0x006c }
            r3.d()     // Catch:{ all -> 0x006c }
        L_0x003e:
            if (r0 == 0) goto L_0x006a
            boolean r4 = defpackage.bno.a     // Catch:{ all -> 0x006c }
            if (r4 == 0) goto L_0x005b
            java.lang.String r4 = "paas.adiu"
            java.lang.String r0 = "AdiuService"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x006c }
            java.lang.String r2 = "adiu from local:"
            r1.<init>(r2)     // Catch:{ all -> 0x006c }
            java.lang.String r2 = r3.d     // Catch:{ all -> 0x006c }
            r1.append(r2)     // Catch:{ all -> 0x006c }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x006c }
            com.amap.bundle.logs.AMapLog.debug(r4, r0, r1)     // Catch:{ all -> 0x006c }
        L_0x005b:
            ik$a r4 = r3.c     // Catch:{ all -> 0x006c }
            if (r4 == 0) goto L_0x0064
            ik$a r4 = r3.c     // Catch:{ all -> 0x006c }
            r4.a()     // Catch:{ all -> 0x006c }
        L_0x0064:
            java.lang.String r4 = "B014"
            r0 = 1
            r3.a(r4, r0)     // Catch:{ all -> 0x006c }
        L_0x006a:
            monitor-exit(r3)
            return
        L_0x006c:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ik.a(boolean):void");
    }

    public final boolean b() {
        if (!TextUtils.isEmpty(this.d)) {
            return true;
        }
        List<String> a2 = chw.a(this.b, a);
        if (a2 != null && a2.size() > 0) {
            String str = a2.get(0);
            if (!TextUtils.isEmpty(str)) {
                this.d = str;
                String str2 = "";
                if (a2.size() > 1) {
                    String str3 = a2.get(1);
                    if (!TextUtils.isEmpty(str3)) {
                        str2 = str3;
                    }
                }
                if (a2.size() > 2) {
                    String str4 = a2.get(2);
                    if (!TextUtils.isEmpty(str4)) {
                        str2 = MergeUtil.SEPARATOR_KV.concat(String.valueOf(str4));
                    }
                }
                if (!TextUtils.isEmpty(str2)) {
                    this.e = str2;
                }
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void d() {
        if (bno.a) {
            StringBuilder sb = new StringBuilder("调用 retryHandler 目前已重试 ");
            sb.append(this.h);
            sb.append("&最大重试次数");
            sb.append(this.i);
            AMapLog.debug("paas.adiu", "AdiuService", sb.toString());
        }
        if (this.h < this.i) {
            if (this.c != null) {
                this.c.a(this.m, this.j);
            }
        } else if (bno.a) {
            AMapLog.debug("paas.adiu", "AdiuService", "已达到最大重试次数，不再重试");
        }
    }

    /* access modifiers changed from: private */
    public void a(String str, int i2) {
        JSONObject jSONObject = new JSONObject();
        try {
            if ("B014".equalsIgnoreCase(str)) {
                jSONObject.put("type", i2);
                LogManager.actionLogV2("2000", str, jSONObject);
                return;
            }
            if ("B015".equalsIgnoreCase(str)) {
                jSONObject.put("type", i2);
                jSONObject.put(TrafficUtil.KEYWORD, this.h);
                LogManager.actionLogV2("2000", str, jSONObject);
            }
        } catch (Exception unused) {
        }
    }

    public final synchronized void b(@NonNull Context context) {
        this.d = "";
        chw.a(context, a, "");
    }
}
