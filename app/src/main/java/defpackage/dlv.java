package defpackage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.autonavi.common.Callback;
import com.autonavi.minimap.life.intent.inter.ILifeIntentDispatcherFactory;
import com.autonavi.minimap.offline.intent.inter.IOfflineIntentDispatcherFactory;
import com.autonavi.minimap.offline.inter.inner.IOfflineIntentDispatcher;
import com.autonavi.minimap.search.inter.ISearchManager;

@Deprecated
/* renamed from: dlv reason: default package */
/* compiled from: AndroidAmapIntentInterceptor */
public final class dlv implements dlh {
    a a;
    public Callback<Boolean> b;
    private Handler c = new Handler(Looper.getMainLooper());
    private dlq d;
    private dpc e;
    private IOfflineIntentDispatcher f;
    private cpn g;
    private axf h;
    private dfk i;
    private ekx j;
    private dly k;

    /* renamed from: dlv$a */
    /* compiled from: AndroidAmapIntentInterceptor */
    abstract class a implements Runnable {
        volatile boolean c = false;

        public abstract void a();

        a() {
        }

        public void run() {
            if (!this.c) {
                a();
            }
        }
    }

    public dlv(Activity activity) {
        this.d = new dlq(activity);
        ILifeIntentDispatcherFactory iLifeIntentDispatcherFactory = (ILifeIntentDispatcherFactory) ank.a(ILifeIntentDispatcherFactory.class);
        if (iLifeIntentDispatcherFactory != null) {
            this.e = iLifeIntentDispatcherFactory.a(activity);
        }
        this.g = (cpn) ank.a(cpn.class);
        IOfflineIntentDispatcherFactory iOfflineIntentDispatcherFactory = (IOfflineIntentDispatcherFactory) ank.a(IOfflineIntentDispatcherFactory.class);
        if (iOfflineIntentDispatcherFactory != null) {
            this.f = iOfflineIntentDispatcherFactory.getOfflineIntentDispatcher(activity);
        }
        if (((bax) defpackage.esb.a.a.a(bax.class)) != null) {
            this.h = null;
        }
        if (((djk) ank.a(djk.class)) != null) {
            this.i = null;
        }
        ISearchManager iSearchManager = (ISearchManager) ank.a(ISearchManager.class);
        if (iSearchManager != null) {
            this.j = iSearchManager.getSearchIntentDispatcher(activity);
        }
        this.k = new dly(activity);
    }

    /* access modifiers changed from: 0000 */
    public final boolean b(Intent intent) {
        Uri data = intent.getData();
        if (data == null || TextUtils.isEmpty(data.getHost())) {
            return false;
        }
        if ((this.d == null || !this.d.a(intent)) && ((this.e == null || !this.e.a(intent)) && ((this.f == null || !this.f.dispatch(intent)) && ((this.g == null || !this.g.a(intent)) && ((this.h == null || !this.h.a()) && ((this.i == null || !this.i.dispatch(intent)) && (this.j == null || !this.j.dispatch(intent)))))))) {
            this.k.b = this.b;
            return this.k.a(intent);
        }
        if (this.b != null) {
            this.b.callback(Boolean.TRUE);
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0033 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0034  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(final android.content.Intent r5) {
        /*
            r4 = this;
            dlv$a r0 = r4.a
            r1 = 1
            if (r0 == 0) goto L_0x0013
            dlv$a r0 = r4.a
            r0.c = r1
            android.os.Handler r0 = r4.c
            dlv$a r2 = r4.a
            r0.removeCallbacks(r2)
            r0 = 0
            r4.a = r0
        L_0x0013:
            r0 = 0
            if (r5 == 0) goto L_0x0030
            android.net.Uri r2 = r5.getData()
            if (r2 == 0) goto L_0x0030
            boolean r3 = r2.isHierarchical()
            if (r3 == 0) goto L_0x0030
            java.lang.String r3 = "androidamap"
            java.lang.String r2 = r2.getScheme()
            boolean r2 = r3.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x0030
            r2 = 1
            goto L_0x0031
        L_0x0030:
            r2 = 0
        L_0x0031:
            if (r2 != 0) goto L_0x0034
            return r0
        L_0x0034:
            android.net.Uri r0 = r5.getData()
            defpackage.dmh.b(r0)
            java.lang.String r2 = "sourceApplication"
            java.lang.String r0 = r0.getQueryParameter(r2)
            if (r0 == 0) goto L_0x006e
            java.lang.String r2 = "Trip"
            boolean r2 = r0.equalsIgnoreCase(r2)
            if (r2 != 0) goto L_0x006e
            java.lang.String r2 = "notify"
            boolean r2 = r0.equalsIgnoreCase(r2)
            if (r2 != 0) goto L_0x006e
            java.lang.String r2 = "amap"
            boolean r2 = r0.equalsIgnoreCase(r2)
            if (r2 != 0) goto L_0x006e
            java.lang.String r2 = "splash"
            boolean r2 = r0.equalsIgnoreCase(r2)
            if (r2 != 0) goto L_0x006e
            java.lang.String r2 = "nearby"
            boolean r2 = r0.equalsIgnoreCase(r2)
            if (r2 != 0) goto L_0x006e
            com.amap.bundle.network.request.param.NetworkParam.setSa(r0)
        L_0x006e:
            java.lang.String r0 = "owner"
            java.lang.String r0 = r5.getStringExtra(r0)
            java.lang.String r2 = "banner"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L_0x00c8
            java.lang.String r2 = "js"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L_0x00c8
            java.lang.String r2 = "from_owner"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x008d
            goto L_0x00c8
        L_0x008d:
            java.lang.String r2 = "geofence"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00b7
            java.lang.String r0 = "gid"
            java.lang.String r0 = r5.getStringExtra(r0)
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x00b7
            org.json.JSONObject r2 = new org.json.JSONObject
            r2.<init>()
            java.lang.String r3 = "type"
            r2.put(r3, r0)     // Catch:{ JSONException -> 0x00ac }
            goto L_0x00b0
        L_0x00ac:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00b0:
            java.lang.String r0 = "P00120"
            java.lang.String r3 = "B007"
            com.amap.bundle.statistics.LogManager.actionLogV2(r0, r3, r2)
        L_0x00b7:
            dlv$1 r0 = new dlv$1
            r0.<init>(r5)
            r4.a = r0
            android.os.Handler r5 = r4.c
            dlv$a r0 = r4.a
            r2 = 500(0x1f4, double:2.47E-321)
            r5.postDelayed(r0, r2)
            return r1
        L_0x00c8:
            boolean r5 = r4.b(r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dlv.a(android.content.Intent):boolean");
    }
}
