package defpackage;

import android.os.Looper;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import java.lang.ref.WeakReference;

/* renamed from: bvv reason: default package */
/* compiled from: SearchTurnPageOfflineCallback */
public final class bvv extends aea<InfoliteResult> {
    WeakReference<bxh> b;

    public bvv(bxh bxh) {
        this.b = new WeakReference<>(bxh);
    }

    /* renamed from: a */
    public final void callback(final InfoliteResult infoliteResult) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            aho.a(new Runnable() {
                public final void run() {
                    bvv.this.callback(infoliteResult);
                }
            });
            return;
        }
        bct.a();
        if (infoliteResult == null || infoliteResult.searchInfo == null || infoliteResult.searchInfo.l == null || infoliteResult.searchInfo.l.size() == 0 || infoliteResult.searchInfo.a == null || infoliteResult.searchInfo.a.a == 1) {
            error(0);
        } else if (this.b.get() != null) {
            InfoliteResult infoliteResult2 = ((bxh) this.b.get()).b;
            infoliteResult2.responseHeader.f = false;
            if (infoliteResult2.searchInfo.l != null) {
                infoliteResult2.searchInfo.l.clear();
            }
            if (infoliteResult2.searchInfo.h != null) {
                infoliteResult2.searchInfo.h.clear();
            }
            if (infoliteResult2.searchInfo.i != null) {
                infoliteResult2.searchInfo.i.clear();
            }
            if (infoliteResult2.searchInfo.j != null) {
                infoliteResult2.searchInfo.j.clear();
            }
            if (infoliteResult2.searchInfo.r != null && infoliteResult2.searchInfo.r.size() > 0) {
                infoliteResult2.searchInfo.r.clear();
            }
            if (bcy.c(infoliteResult2)) {
                infoliteResult2.searchInfo.u = 0;
                if (infoliteResult2.searchInfo.f != null && !infoliteResult2.searchInfo.f.isEmpty()) {
                    infoliteResult2.searchInfo.f.clear();
                }
            }
            infoliteResult2.searchInfo.p = infoliteResult.searchInfo.l.size();
            infoliteResult.searchInfo.n = bcy.m(infoliteResult);
            infoliteResult2.searchInfo.l.addAll(infoliteResult.searchInfo.l);
            ((bxh) this.b.get()).a(2);
            ((bxh) this.b.get()).b(infoliteResult2);
            bxh bxh = (bxh) this.b.get();
            InfoliteResult infoliteResult3 = bxh.b;
            if (infoliteResult3 != null) {
                infoliteResult3.searchInfo.a.u = null;
                if (infoliteResult3.searchInfo.r != null) {
                    infoliteResult3.searchInfo.r.clear();
                }
                infoliteResult3.searchInfo.q = 0;
                if (infoliteResult3.searchInfo.b != null) {
                    infoliteResult3.searchInfo.b.clear();
                }
                infoliteResult3.searchInfo.d = null;
                if (infoliteResult3.mWrapper != null) {
                    infoliteResult3.mWrapper.pagenum = 1;
                }
            }
            bxh.a();
            ((bxh) this.b.get()).a((String) "search_for_turnpage_callback");
        }
    }

    public final void error(final int i) {
        aho.a(new Runnable() {
            public final void run() {
                if (bvv.this.b.get() != null) {
                    if (i == 0) {
                        ((bxh) bvv.this.b.get()).a(6);
                    } else {
                        ((bxh) bvv.this.b.get()).a(2);
                    }
                    ((bxh) bvv.this.b.get()).a.a();
                }
            }
        });
    }
}
