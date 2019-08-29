package defpackage;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.danikula.videocache.ProxyCacheException;
import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: etn reason: default package */
/* compiled from: HttpProxyCacheServerClients */
final class etn {
    final AtomicInteger a = new AtomicInteger(0);
    volatile etl b;
    private final String c;
    private final List<eti> d = new CopyOnWriteArrayList();
    private final eti e;
    private final etj f;

    /* renamed from: etn$a */
    /* compiled from: HttpProxyCacheServerClients */
    static final class a extends Handler implements eti {
        private final String a;
        private final List<eti> b;

        public a(String str, List<eti> list) {
            super(Looper.getMainLooper());
            this.a = str;
            this.b = list;
        }

        public final void a(File file, int i) {
            Message obtainMessage = obtainMessage();
            obtainMessage.arg1 = i;
            obtainMessage.obj = file;
            sendMessage(obtainMessage);
        }

        public final void handleMessage(Message message) {
            for (eti a2 : this.b) {
                a2.a((File) message.obj, message.arg1);
            }
        }
    }

    public etn(String str, etj etj) {
        this.c = (String) etr.a(str);
        this.f = (etj) etr.a(etj);
        this.e = new a(str, this.d);
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void a() throws ProxyCacheException {
        etl etl;
        if (this.b == null) {
            eto eto = new eto(this.c, this.f.d, this.f.e);
            etj etj = this.f;
            etl = new etl(eto, new ety(new File(etj.a, etj.b.generate(this.c)), this.f.c));
            etl.c = this.e;
        } else {
            etl = this.b;
        }
        this.b = etl;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0022 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void b() {
        /*
            r3 = this;
            monitor-enter(r3)
            java.util.concurrent.atomic.AtomicInteger r0 = r3.a     // Catch:{ all -> 0x002b }
            int r0 = r0.decrementAndGet()     // Catch:{ all -> 0x002b }
            if (r0 > 0) goto L_0x0029
            etl r0 = r3.b     // Catch:{ all -> 0x002b }
            java.lang.Object r1 = r0.e     // Catch:{ all -> 0x002b }
            monitor-enter(r1)     // Catch:{ all -> 0x002b }
            r2 = 1
            r0.g = r2     // Catch:{ ProxyCacheException -> 0x0022 }
            java.lang.Thread r2 = r0.f     // Catch:{ ProxyCacheException -> 0x0022 }
            if (r2 == 0) goto L_0x001a
            java.lang.Thread r2 = r0.f     // Catch:{ ProxyCacheException -> 0x0022 }
            r2.interrupt()     // Catch:{ ProxyCacheException -> 0x0022 }
        L_0x001a:
            eth r0 = r0.d     // Catch:{ ProxyCacheException -> 0x0022 }
            r0.b()     // Catch:{ ProxyCacheException -> 0x0022 }
            goto L_0x0022
        L_0x0020:
            r0 = move-exception
            goto L_0x0027
        L_0x0022:
            monitor-exit(r1)     // Catch:{ all -> 0x0020 }
            r0 = 0
            r3.b = r0     // Catch:{ all -> 0x002b }
            goto L_0x0029
        L_0x0027:
            monitor-exit(r1)     // Catch:{ all -> 0x0020 }
            throw r0     // Catch:{ all -> 0x002b }
        L_0x0029:
            monitor-exit(r3)
            return
        L_0x002b:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.etn.b():void");
    }
}
