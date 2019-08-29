package defpackage;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.autonavi.core.network.inter.response.ResponseException;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

/* renamed from: boz reason: default package */
/* compiled from: NetworkDispatcher */
public final class boz {
    final Map<bph, bpl> a = Collections.synchronizedMap(new HashMap());
    private final Executor b;

    /* renamed from: boz$a */
    /* compiled from: NetworkDispatcher */
    static class a implements Runnable {
        private final bph a;
        private final ResponseException b;
        private final WeakReference<bpl> c;

        public a(bpl bpl, bph bph, ResponseException responseException) {
            this.a = bph;
            this.b = responseException;
            this.c = new WeakReference<>(bpl);
        }

        public final void run() {
            bpl bpl = (bpl) this.c.get();
            if (bpl != null && this.a != null && !this.a.isCancelled()) {
                bpl.onFailure(this.a, this.b);
            }
        }
    }

    /* renamed from: boz$b */
    /* compiled from: NetworkDispatcher */
    static class b implements Runnable {
        private final bph a;
        private final WeakReference<bpn> b;
        private final long c;
        private final long d;

        public b(bpn bpn, bph bph, long j, long j2) {
            this.b = new WeakReference<>(bpn);
            this.a = bph;
            this.c = j;
            this.d = j2;
        }

        public final void run() {
            bpn bpn = (bpn) this.b.get();
            if (bpn != null && this.a != null && !this.a.isCancelled()) {
                bpn.a(this.a, this.c, this.d);
            }
        }
    }

    /* renamed from: boz$c */
    /* compiled from: NetworkDispatcher */
    static class c implements Runnable {
        private final bpk a;
        private final WeakReference<bpl> b;

        public c(bpl bpl, bpk bpk) {
            this.a = bpk;
            this.b = new WeakReference<>(bpl);
        }

        public final void run() {
            bpl bpl = (bpl) this.b.get();
            if (bpl != null && this.a != null && !this.a.getRequest().isCancelled()) {
                bpl.onSuccess(this.a);
                bow.a(this.a.getBodyInputStream());
            }
        }
    }

    public boz(final Handler handler) {
        this.b = new Executor() {
            public final void execute(Runnable runnable) {
                handler.post(runnable);
            }
        };
    }

    public final void a(@NonNull bph bph, bpl bpl) {
        this.a.put(bph, bpl);
        if (bpv.a(3)) {
            StringBuilder sb = new StringBuilder("start http request, url: ");
            sb.append(bph.getUrl());
            bpv.b("ANet-NetworkDispatcher", sb.toString());
        }
    }

    public final void a(@NonNull final bph bph) {
        bpl bpl = this.a.get(bph);
        if (bpl != null && (bpl instanceof bpm)) {
            a((Runnable) new Runnable() {
                public final void run() {
                    if (boz.this.a.containsKey(bph)) {
                        boz.this.a.remove(bph);
                    }
                }
            });
        } else if (this.a.containsKey(bph)) {
            this.a.remove(bph);
        }
        if (bpv.a(3)) {
            StringBuilder sb = new StringBuilder("finishRequest, url: ");
            sb.append(bph.getUrl());
            bpv.b("ANet-NetworkDispatcher", sb.toString());
        }
        if (bph != null) {
            bpo.a(bph.requestStatistics);
            if (bpv.a(3)) {
                StringBuilder sb2 = new StringBuilder("commitStat, url: ");
                sb2.append(bph.getUrl());
                sb2.append("\n statistic:");
                sb2.append(bph.requestStatistics.toString());
                bpv.b("ANet-NetworkDispatcher", sb2.toString());
            }
        }
    }

    public final void b(@NonNull bph bph) {
        bph.cancel();
        bph.requestStatistics.d = bok.c;
        if (this.a.containsKey(bph)) {
            this.a.remove(bph);
        }
    }

    public final void a(@Nullable bpl bpl, @NonNull bph bph, @Nullable ResponseException responseException) {
        if (bpl != null && !bph.isCancelled()) {
            if (bpv.a(6) && responseException != null) {
                StringBuilder sb = new StringBuilder("post error, errorCode: ");
                sb.append(responseException.errorCode);
                sb.append(", msg:");
                sb.append(responseException.getLocalizedMessage());
                sb.append(", url: ");
                sb.append(bph.getUrl());
                bpv.e("ANet-NetworkDispatcher", sb.toString());
            }
            if (bpl instanceof bpm) {
                a((Runnable) new a(bpl, bph, responseException));
                return;
            }
            bpl.onFailure(bph, responseException);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(@NonNull Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            this.b.execute(runnable);
        }
    }
}
