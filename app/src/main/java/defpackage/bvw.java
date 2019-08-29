package defpackage;

import android.os.Looper;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import java.lang.ref.WeakReference;

/* renamed from: bvw reason: default package */
/* compiled from: SearchWaterfallCallback */
public final class bvw extends aea<InfoliteResult> {
    public boolean b;
    private WeakReference<bxh> c;

    public bvw(bxh bxh, int i) {
        this.c = new WeakReference<>(bxh);
        ((bxh) this.c.get()).a(i);
    }

    /* renamed from: a */
    public final void callback(final InfoliteResult infoliteResult) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            aho.a(new Runnable() {
                public final void run() {
                    bvw.this.callback(infoliteResult);
                }
            });
        } else if (this.c.get() != null) {
            if (!bcy.b(infoliteResult)) {
                error(-1);
                return;
            }
            ((bxh) this.c.get()).g = this.b;
            bxh bxh = (bxh) this.c.get();
            bxh.c(infoliteResult);
            if (bxh.d != null) {
                bxh.d.addAll(bxh.d(bxh.d.isEmpty() ? 0 : bxh.d.size() - bxh.h));
            }
            ((bxh) this.c.get()).a((String) "search_for_turnpage_callback");
        }
    }

    public final void error(int i) {
        if (this.c.get() != null) {
            ((bxh) this.c.get()).g = this.b;
            ((bxh) this.c.get()).a.a();
        }
    }
}
