package defpackage;

import android.text.TextUtils;
import com.autonavi.common.Callback;
import com.autonavi.common.Callback.d;
import com.autonavi.minimap.drive.navi.navitts.download.NaviTtsDownloadException;
import com.autonavi.minimap.drive.navi.navitts.download.NaviTtsErrorType;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/* renamed from: dfy reason: default package */
/* compiled from: NaviTtsDownloadMananger */
public final class dfy {
    private static dfy b = new dfy();
    public c a;
    /* access modifiers changed from: private */
    public Map<String, a> c = new HashMap();
    private com.amap.bundle.utils.device.ConnectivityMonitor.a d;

    /* renamed from: dfy$a */
    /* compiled from: NaviTtsDownloadMananger */
    class a {
        com.autonavi.common.Callback.a a;
        d b;

        private a() {
        }

        /* synthetic */ a(dfy dfy, byte b2) {
            this();
        }
    }

    /* renamed from: dfy$b */
    /* compiled from: NaviTtsDownloadMananger */
    class b implements defpackage.dfz.a {
        private dgl b;
        private Callback<File> c;
        private d d;
        private long e = System.currentTimeMillis();

        public b(dgl dgl, Callback<File> callback) {
            this.b = dgl;
            this.c = callback;
            if (callback instanceof d) {
                this.d = (d) callback;
            }
        }

        public final void a(File file) {
            if (dgl.a(this.b.b(), this.b.a.i)) {
                dgl dgl = this.b;
                String b2 = dgl.b();
                String a2 = dgl.a();
                if (!TextUtils.isEmpty(b2) && !TextUtils.isEmpty(a2)) {
                    File file2 = new File(b2);
                    File file3 = new File(a2);
                    if (file3.exists() && file2.length() == dgl.a.g) {
                        file3.delete();
                    }
                    file2.renameTo(file3);
                }
                this.b.a(4);
                this.c.callback(file);
            } else {
                ((dhf) ank.a(dhf.class)).b(new File(this.b.b()));
                this.c.error(new NaviTtsDownloadException(NaviTtsErrorType.MD5_ERROR), true);
            }
            dfy.this.c.remove(this.b.a.f);
            dfy.this.b();
        }

        public final void a(Throwable th) {
            this.b.a(10);
            if (th instanceof NaviTtsDownloadException) {
                this.c.error(th, false);
            } else {
                this.c.error(new NaviTtsDownloadException(th), false);
            }
            dfy.this.c.remove(this.b.a.f);
            dfy.this.b();
        }

        public final void a() {
            this.b.a(1);
            if (this.d != null) {
                this.d.onStart();
            }
        }

        public final void a(long j, long j2) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.e >= 500) {
                this.e = currentTimeMillis;
                dgl dgl = this.b;
                if (dgl.b != null) {
                    dgl.b.d = j;
                    dgl.h();
                }
                this.b.a(j2);
                if (this.d != null) {
                    this.d.onLoading(j, j2);
                }
            }
        }
    }

    /* renamed from: dfy$c */
    /* compiled from: NaviTtsDownloadMananger */
    public interface c {
        void a();
    }

    private dfy() {
    }

    public static dfy a() {
        return b;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void b() {
        /*
            r2 = this;
            monitor-enter(r2)
            java.util.Map<java.lang.String, dfy$a> r0 = r2.c     // Catch:{ all -> 0x003f }
            if (r0 != 0) goto L_0x0007
            monitor-exit(r2)
            return
        L_0x0007:
            java.util.Map<java.lang.String, dfy$a> r0 = r2.c     // Catch:{ all -> 0x003f }
            int r0 = r0.size()     // Catch:{ all -> 0x003f }
            if (r0 <= 0) goto L_0x0025
            com.amap.bundle.utils.device.ConnectivityMonitor$a r0 = r2.d     // Catch:{ all -> 0x003f }
            if (r0 != 0) goto L_0x003d
            dfy$1 r0 = new dfy$1     // Catch:{ all -> 0x003f }
            r0.<init>()     // Catch:{ all -> 0x003f }
            r2.d = r0     // Catch:{ all -> 0x003f }
            com.amap.bundle.utils.device.ConnectivityMonitor r0 = com.amap.bundle.utils.device.ConnectivityMonitor.a()     // Catch:{ all -> 0x003f }
            com.amap.bundle.utils.device.ConnectivityMonitor$a r1 = r2.d     // Catch:{ all -> 0x003f }
            r0.a(r1)     // Catch:{ all -> 0x003f }
            monitor-exit(r2)
            return
        L_0x0025:
            java.util.Map<java.lang.String, dfy$a> r0 = r2.c     // Catch:{ all -> 0x003f }
            int r0 = r0.size()     // Catch:{ all -> 0x003f }
            if (r0 != 0) goto L_0x003d
            com.amap.bundle.utils.device.ConnectivityMonitor$a r0 = r2.d     // Catch:{ all -> 0x003f }
            if (r0 == 0) goto L_0x003d
            com.amap.bundle.utils.device.ConnectivityMonitor r0 = com.amap.bundle.utils.device.ConnectivityMonitor.a()     // Catch:{ all -> 0x003f }
            com.amap.bundle.utils.device.ConnectivityMonitor$a r1 = r2.d     // Catch:{ all -> 0x003f }
            r0.b(r1)     // Catch:{ all -> 0x003f }
            r0 = 0
            r2.d = r0     // Catch:{ all -> 0x003f }
        L_0x003d:
            monitor-exit(r2)
            return
        L_0x003f:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dfy.b():void");
    }

    public final synchronized void a(dgl dgl) {
        a remove = this.c.remove(dgl.a.f);
        if (remove != null) {
            if (remove.b != null) {
                remove.b.onCancelled();
            }
            if (remove.a != null) {
                remove.a.cancel();
            }
        }
        b();
    }

    public final synchronized void a(dgl dgl, Callback<File> callback) {
        String str = dgl.a.f;
        if (!this.c.containsKey(str)) {
            if (64 == dgl.g()) {
                dgl.i();
            }
            if (dgl.g() != 1) {
                dgl.a(2);
            }
            if (TextUtils.isEmpty(dgl.b())) {
                dgl.a(5);
                callback.error(new NaviTtsDownloadException(NaviTtsErrorType.file_io_exception), false);
                return;
            }
            dfz dfz = new dfz(dgl.a.d, dgl.b(), new b(dgl, callback));
            a aVar = new a(this, 0);
            aVar.b = (d) callback;
            dfz.a.execute(new Runnable() {
                public final void run() {
                    if (!dfz.this.b.isCancelled()) {
                        dfz.a(dfz.this, dfz.this.d, dfz.this.e);
                    }
                }
            });
            aVar.a = dfz.b;
            this.c.put(str, aVar);
            b();
        }
    }
}
