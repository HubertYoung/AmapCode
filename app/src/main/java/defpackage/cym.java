package defpackage;

import android.content.res.Configuration;
import android.view.View;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.map.fragmentcontainer.page.IViewLayerExt;

/* renamed from: cym reason: default package */
/* compiled from: FrequentLocationViewLayer */
final class cym implements IViewLayer, IViewLayerExt, enf {
    bid a;
    a b;
    boolean c;
    private View d;

    /* renamed from: cym$a */
    /* compiled from: FrequentLocationViewLayer */
    interface a {
        void a();
    }

    public final void onConfigurationChanged(Configuration configuration) {
    }

    public final void showBackground(boolean z) {
    }

    cym(bid bid, View view) {
        this.a = bid;
        this.d = view;
    }

    public final void a() {
        this.c = false;
        this.a.dismissViewLayer(this);
    }

    public final View getView() {
        return this.d;
    }

    public final boolean onBackPressed() {
        if (this.b != null) {
            this.b.a();
        }
        return true;
    }

    public final boolean isDismiss() {
        return this.b == null;
    }
}
