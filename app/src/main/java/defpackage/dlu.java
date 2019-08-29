package defpackage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.common.Callback;
import com.autonavi.minimap.life.intent.inter.ILifeIntentDispatcherFactory;
import com.autonavi.minimap.offline.intent.inter.IOfflineIntentDispatcherFactory;
import com.autonavi.minimap.offline.inter.inner.IOfflineIntentDispatcher;
import com.autonavi.minimap.search.inter.ISearchManager;

@Deprecated
/* renamed from: dlu reason: default package */
/* compiled from: AmapUriIntentInterceptor */
public final class dlu implements dlh {
    public Callback<Boolean> a;
    private dlq b;
    private dpc c;
    private IOfflineIntentDispatcher d;
    private cpn e;
    private axf f;
    private dfk g;
    private ekx h;
    private dly i;
    private final dlr j;

    public dlu(Activity activity) {
        this.b = new dlq(activity);
        ILifeIntentDispatcherFactory iLifeIntentDispatcherFactory = (ILifeIntentDispatcherFactory) ank.a(ILifeIntentDispatcherFactory.class);
        if (iLifeIntentDispatcherFactory != null) {
            this.c = iLifeIntentDispatcherFactory.a(activity);
        }
        this.e = (cpn) ank.a(cpn.class);
        IOfflineIntentDispatcherFactory iOfflineIntentDispatcherFactory = (IOfflineIntentDispatcherFactory) ank.a(IOfflineIntentDispatcherFactory.class);
        if (iOfflineIntentDispatcherFactory != null) {
            this.d = iOfflineIntentDispatcherFactory.getOfflineIntentDispatcher(activity);
        }
        if (((bax) a.a.a(bax.class)) != null) {
            this.f = null;
        }
        if (((djk) ank.a(djk.class)) != null) {
            this.g = null;
        }
        ISearchManager iSearchManager = (ISearchManager) ank.a(ISearchManager.class);
        if (iSearchManager != null) {
            this.h = iSearchManager.getSearchIntentDispatcher(activity);
        }
        this.i = new dly(activity);
        this.j = new dlr();
    }

    public final boolean a(Intent intent) {
        boolean z;
        Uri data = intent.getData();
        if (!(data != null && data.isHierarchical() && TextUtils.equals(data.getScheme(), "amapuri"))) {
            return false;
        }
        Uri data2 = intent.getData();
        if (data2 != null && !TextUtils.isEmpty(data2.getHost())) {
            if ((this.b == null || !this.b.a(intent)) && ((this.c == null || !this.c.a(intent)) && ((this.d == null || !this.d.dispatch(intent)) && ((this.e == null || !this.e.a(intent)) && ((this.f == null || !this.f.a()) && ((this.g == null || !this.g.dispatch(intent)) && (this.h == null || !this.h.dispatch(intent)))))))) {
                this.j.a = this.a;
                if (!this.j.a(intent)) {
                    this.i.b = this.a;
                    z = this.i.a(intent);
                }
            } else if (this.a != null) {
                this.a.callback(Boolean.TRUE);
            }
            z = true;
        } else {
            z = false;
        }
        return z;
    }
}
