package defpackage;

import android.os.Looper;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.Callback.b;
import com.autonavi.common.Callback.c;
import com.autonavi.common.model.POI;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* renamed from: bvu reason: default package */
/* compiled from: SearchRefreshPageCallback */
public final class bvu extends aea<InfoliteResult> implements b, c {
    public boolean b = true;
    public String c;
    private int d;
    private WeakReference<bxh> e;

    public bvu(bxh bxh, int i) {
        this.e = new WeakReference<>(bxh);
        this.d = i;
    }

    /* renamed from: a */
    public final void callback(final InfoliteResult infoliteResult) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            aho.a(new Runnable() {
                public final void run() {
                    bvu.this.callback(infoliteResult);
                }
            });
        } else if (this.e.get() != null) {
            ((bxh) this.e.get()).a(this.b);
            boolean z = true;
            if (bcy.b(infoliteResult)) {
                List<POI> h = bcy.h(infoliteResult);
                ArrayList<aue> arrayList = infoliteResult.searchInfo.r;
                if (!(h.size() == 0 && (arrayList == null || arrayList.size() == 0))) {
                    z = false;
                }
            }
            if (z) {
                ((bxh) this.e.get()).a(infoliteResult.responseHeader.f ? 7 : 6);
                ((bxh) this.e.get()).a.a();
                return;
            }
            ((bxh) this.e.get()).a(this.d);
            ((bxh) this.e.get()).b(infoliteResult);
            if (this.d == 3) {
                ((bxh) this.e.get()).a((String) "footer_recommend_more_callback");
            } else {
                ((bxh) this.e.get()).a((String) "search_for_filter_callback");
            }
        }
    }

    public final void error(int i) {
        if (this.e != null) {
            ((bxh) this.e.get()).a(this.d);
            ((bxh) this.e.get()).a(this.b);
            ((bxh) this.e.get()).a.a();
        }
    }

    public final void onCancelled() {
        if (this.e.get() != null) {
            for (a b_ : ((bxh) this.e.get()).a.b) {
                b_.b_();
            }
        }
    }

    public final String getLoadingMessage() {
        return this.c;
    }
}
