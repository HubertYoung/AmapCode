package defpackage;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.statistics.LogManager;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.agroup.entity.GroupInfo;
import com.autonavi.minimap.agroup.entity.TeamInfo;
import com.autonavi.minimap.agroup.util.BackgroundLocateManager;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService;
import com.autonavi.minimap.bundle.agroup.api.IDataService;
import com.autonavi.minimap.bundle.agroup.api.IDataService.TeamStatus;
import com.autonavi.sdk.location.ILocationEventListener;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.server.aos.serverkey;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

@BundleInterface(cuh.class)
/* renamed from: cij reason: default package */
/* compiled from: AgroupService */
public class cij extends esi implements anp, a, defpackage.cin.a, cuh, dku, lp {
    /* access modifiers changed from: 0000 */
    public dkv a;
    ScheduledExecutorService b = Executors.newSingleThreadScheduledExecutor();
    a c = new a(this);
    private final String e = cij.class.getSimpleName();
    /* access modifiers changed from: private */
    public cjt f = defpackage.cjt.a.a;
    private dkw g;
    /* access modifiers changed from: private */
    public Boolean h = null;
    private final String i = "AGroup_control";
    private final String[] j = {"amapauto/location"};
    private final String k = NetworkParam.getAosTsMqttUrl();
    private Timer l;
    private final int m = 1;
    private final int n = 0;
    private final int o = 0;
    private final int p = 1;
    private final int q = 2;
    private final int r = 3;
    private final int s = 4;
    /* access modifiers changed from: private */
    public int t = 0;
    private WeakReference<JsFunctionCallback> u;
    private boolean v = false;
    private Runnable w = new Runnable() {
        public final void run() {
            BackgroundLocateManager.a("GroupCoordinator. mOnPauseTask. run.", new Object[0]);
            bid pageContext = AMapPageUtil.getPageContext();
            Boolean valueOf = (pageContext == null || !(pageContext.getActivity() instanceof brr)) ? null : Boolean.valueOf(!((brr) pageContext.getActivity()).i());
            if (valueOf == null || valueOf.booleanValue()) {
                BackgroundLocateManager.a("GroupCoordinator. mOnPauseTask. cancel mBgPauseConnectionTask because in foreground.", new Object[0]);
            } else if (cij.this.a == null || (cij.this.h != null && !cij.this.h.booleanValue())) {
                Object[] objArr = new Object[2];
                objArr[0] = Boolean.valueOf(cij.this.a != null);
                objArr[1] = cij.this.h;
                BackgroundLocateManager.a("GroupCoordinator. mOnPauseTask. cancel mBgPauseConnectionTask because mConnection != null: %s, mConnectionResumed: %s.", objArr);
            } else {
                int i = BackgroundLocateManager.c ? 60 : cih.a().e;
                cij.a(cij.this, ((long) i) * 1000);
                BackgroundLocateManager.a("GroupCoordinator. mOnPauseTask. postDelayed mBgPauseConnectionTask in %s seconds.", Integer.valueOf(i));
            }
        }
    };
    private b x = new b(this, 0);
    private ScheduledFuture<?> y = null;
    private ILocationEventListener z = new eov() {
        public final void onLocateStop() {
            bid pageContext = AMapPageUtil.getPageContext();
            Boolean valueOf = (pageContext == null || !(pageContext.getActivity() instanceof brr)) ? null : Boolean.valueOf(!((brr) pageContext.getActivity()).i());
            if (valueOf == null || valueOf.booleanValue()) {
                BackgroundLocateManager.a("LocationInstrument. mOnLocateStopTask. do not start because is in foreground.", new Object[0]);
                return;
            }
            cij.this.i();
            BackgroundLocateManager.a().a(true);
        }

        public final void onLocateStart() {
            BackgroundLocateManager.a().b();
            bid pageContext = AMapPageUtil.getPageContext();
            Boolean valueOf = (pageContext == null || !(pageContext.getActivity() instanceof brr)) ? null : Boolean.valueOf(!((brr) pageContext.getActivity()).i());
            if (valueOf == null || !valueOf.booleanValue()) {
                BackgroundLocateManager.a("LocationInstrument. mOnLocateStartTask. do not resume GroupCoordinator because is not in foreground.", new Object[0]);
            } else {
                cij.this.h();
            }
        }
    };

    /* renamed from: cij$a */
    /* compiled from: AgroupService */
    static class a implements com.autonavi.minimap.agroup.util.BackgroundLocateManager.a {
        WeakReference<cij> a;

        public a(cij cij) {
            this.a = new WeakReference<>(cij);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0055, code lost:
            if ((defpackage.cih.a().g == 1) != false) goto L_0x0057;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean a() {
            /*
                r5 = this;
                java.lang.ref.WeakReference<cij> r0 = r5.a
                java.lang.Object r0 = r0.get()
                r1 = 1
                r2 = 0
                if (r0 == 0) goto L_0x0059
                java.lang.ref.WeakReference<cij> r0 = r5.a
                java.lang.Object r0 = r0.get()
                cij r0 = (defpackage.cij) r0
                dkv r0 = r0.a
                if (r0 == 0) goto L_0x0059
                java.lang.ref.WeakReference<cij> r0 = r5.a
                java.lang.Object r0 = r0.get()
                cij r0 = (defpackage.cij) r0
                int r0 = r0.t
                if (r0 == 0) goto L_0x0059
                java.lang.ref.WeakReference<cij> r0 = r5.a
                java.lang.Object r0 = r0.get()
                cij r0 = (defpackage.cij) r0
                cjt r0 = r0.f
                if (r0 == 0) goto L_0x0059
                java.lang.ref.WeakReference<cij> r0 = r5.a
                java.lang.Object r0 = r0.get()
                cij r0 = (defpackage.cij) r0
                cjt r0 = r0.f
                boolean r0 = r0.l()
                if (r0 == 0) goto L_0x0059
                boolean r0 = com.autonavi.minimap.agroup.util.BackgroundLocateManager.c
                if (r0 != 0) goto L_0x0057
                cih r0 = defpackage.cih.a()
                int r0 = r0.g
                if (r0 != r1) goto L_0x0054
                r0 = 1
                goto L_0x0055
            L_0x0054:
                r0 = 0
            L_0x0055:
                if (r0 == 0) goto L_0x0059
            L_0x0057:
                r0 = 1
                goto L_0x005a
            L_0x0059:
                r0 = 0
            L_0x005a:
                java.lang.String r3 = "GroupCoordinator. BackgroundLocateListener. isValid: %s"
                java.lang.Object[] r1 = new java.lang.Object[r1]
                java.lang.Boolean r4 = java.lang.Boolean.valueOf(r0)
                r1[r2] = r4
                com.autonavi.minimap.agroup.util.BackgroundLocateManager.a(r3, r1)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.cij.a.a():boolean");
        }

        public final void a(GeoPoint geoPoint) {
            BackgroundLocateManager.a("GroupCoordinator. BackgroundLocateListener. onLocate. getLongitude: %s, getLatitude: %s", Double.valueOf(geoPoint.getLongitude()), Double.valueOf(geoPoint.getLatitude()));
            if (this.a.get() != null) {
                cij cij = (cij) this.a.get();
                Object[] objArr = new Object[1];
                objArr[0] = Boolean.valueOf(cij.a != null);
                BackgroundLocateManager.a("GroupCoordinator. doSendMyPos. mConnection !=null: %s", objArr);
                if (cij.a != null) {
                    dkv dkv = cij.a;
                    AnonymousClass4 r1 = new Runnable() {
                        public final void run() {
                            cij.this.o();
                        }
                    };
                    BackgroundLocateManager.a("PersistentConnection. doSendMyPos. mInited: %s", Boolean.valueOf(dkv.b));
                    if (dkv.b) {
                        if (dkv.a == 1 && dkv.k) {
                            BackgroundLocateManager.a("PersistentConnection. doSendMyPos. do mqtt sendMyPos", new Object[0]);
                            dkv.c();
                            dkv.l = true;
                            r1.run();
                            ahm.a(dkv.h, 5000);
                        }
                        if (dkv.a == 0 && dkv.d != null && dkv.d.d) {
                            BackgroundLocateManager.a("PersistentConnection. doSendMyPos. do http sendMyPos", new Object[0]);
                            dkr dkr = dkv.d;
                            dkr.a("amapauto/location", dkr.e);
                        }
                    }
                }
            }
        }

        public final long b() {
            long j = ((long) cih.a().c) * 1000;
            BackgroundLocateManager.a("GroupCoordinator. BackgroundLocateListener. getPeroidMilliSecs: %s", Long.valueOf(j));
            return j;
        }

        public final long c() {
            long j = ((long) cih.a().d) * 1000;
            BackgroundLocateManager.a("GroupCoordinator. BackgroundLocateListener. getContinuousMilliSecs: %s", Long.valueOf(j));
            return j;
        }
    }

    /* renamed from: cij$b */
    /* compiled from: AgroupService */
    class b implements Runnable {
        Runnable a;

        private b() {
            this.a = new Runnable() {
                public final void run() {
                    BackgroundLocateManager.a("GroupCoordinator. mBgPauseConnectionTask. onRun().", new Object[0]);
                    bid pageContext = AMapPageUtil.getPageContext();
                    Boolean valueOf = (pageContext == null || !(pageContext.getActivity() instanceof brr)) ? null : Boolean.valueOf(!((brr) pageContext.getActivity()).i());
                    if (valueOf == null || valueOf.booleanValue()) {
                        BackgroundLocateManager.a("GroupCoordinator. mBgPauseConnectionTask. cancel pauseConnection because in foreground.", new Object[0]);
                        return;
                    }
                    cij.this.i();
                    BackgroundLocateManager.a("GroupCoordinator. mBgPauseConnectionTask. pauseConnection.", new Object[0]);
                }
            };
        }

        /* synthetic */ b(cij cij, byte b2) {
            this();
        }

        public final void run() {
            boolean s = cij.s();
            BackgroundLocateManager.a("GroupCoordinator. mBgPauseConnectionTask. run(). isUIThread: %s", Boolean.valueOf(s));
            if (s) {
                this.a.run();
                return;
            }
            aho.b(this.a);
            aho.a(this.a);
        }
    }

    private static String b(int i2) {
        switch (i2) {
            case 0:
                return "尚未初始化";
            case 1:
                return "正在初始化";
            case 2:
                return "初始化失败";
            case 3:
                return "初始化成功";
            default:
                return "未知状态";
        }
    }

    public void onConfigCallBack(int i2) {
    }

    public void onUserInfoUpdate(ant ant) {
    }

    public cij() {
        LocationInstrument.getInstance().addLocationEventListener(this.z);
    }

    public final IAgroupOverlayService b() {
        return cji.e();
    }

    public final cug c() {
        return cih.a();
    }

    public final boolean e() {
        return this.t == 1;
    }

    public final void a(JsFunctionCallback jsFunctionCallback) {
        this.u = new WeakReference<>(jsFunctionCallback);
        if (C()) {
            aho.a(new Runnable() {
                public final void run() {
                    cij.this.B();
                }
            });
        }
    }

    public final void f() {
        cjo.a();
        u();
        h();
    }

    public final void g() {
        cjo.a();
        if (!LocationInstrument.getInstance().isLocating2()) {
            i();
        }
        u();
        aho.a(this.w);
    }

    public void onLoginStateChanged(boolean z2, boolean z3) {
        try {
            if (!v()) {
                a((TeamStatus) null);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void t() {
        Object[] objArr = new Object[1];
        objArr[0] = Boolean.valueOf(this.y != null);
        BackgroundLocateManager.a("cancelBgPauseConnectionTask. bgPauseConnectionTaskFuture: %s", objArr);
        if (this.y != null) {
            this.y.cancel(true);
            this.y = null;
        }
        aho.b(this.x.a);
    }

    private void u() {
        BackgroundLocateManager.a("GroupCoordinator. ClearPauseConnectionTask.", new Object[0]);
        aho.b(this.w);
        t();
    }

    public final void h() {
        u();
        if (this.a == null) {
            return;
        }
        if (this.h == null || !this.h.booleanValue()) {
            this.h = Boolean.TRUE;
            this.a.b();
        }
    }

    public final void i() {
        u();
        if (this.a == null) {
            return;
        }
        if (this.h == null || this.h.booleanValue()) {
            this.h = Boolean.FALSE;
            this.a.a();
        }
    }

    public final void j() {
        cjo.a();
        x();
        lo.a().b("AGroup_control", this);
        cih.a().i = null;
        z();
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.b((anp) this);
        }
        BackgroundLocateManager a2 = BackgroundLocateManager.a();
        a2.b.b(this.c);
        a(0);
    }

    public final void b(JsFunctionCallback jsFunctionCallback) {
        if (!v()) {
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback("");
            }
        } else if (!cih.a().c()) {
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback("");
            }
        } else {
            a(1);
            cin.a((String) "", (defpackage.cin.a) this, jsFunctionCallback);
        }
    }

    public final boolean c(JsFunctionCallback jsFunctionCallback) {
        if (v() && cih.a().c() && cin.a((defpackage.cin.a) this, jsFunctionCallback)) {
            return true;
        }
        return false;
    }

    public final IDataService l() {
        return this.f;
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x008c A[Catch:{ Exception -> 0x0138 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean w() {
        /*
            r9 = this;
            dkv r0 = r9.a
            r1 = 1
            if (r0 == 0) goto L_0x0006
            return r1
        L_0x0006:
            r0 = 0
            boolean r2 = v()     // Catch:{ Exception -> 0x0138 }
            if (r2 != 0) goto L_0x000e
            return r0
        L_0x000e:
            dkv r2 = new dkv     // Catch:{ Exception -> 0x0138 }
            r2.<init>()     // Catch:{ Exception -> 0x0138 }
            r9.a = r2     // Catch:{ Exception -> 0x0138 }
            dkw r2 = new dkw     // Catch:{ Exception -> 0x0138 }
            r2.<init>()     // Catch:{ Exception -> 0x0138 }
            r9.g = r2     // Catch:{ Exception -> 0x0138 }
            r2 = 0
            r9.h = r2     // Catch:{ Exception -> 0x0138 }
            dkw r3 = r9.g     // Catch:{ Exception -> 0x0138 }
            java.lang.String r4 = r9.k()     // Catch:{ Exception -> 0x0138 }
            r3.a = r4     // Catch:{ Exception -> 0x0138 }
            dkw r3 = r9.g     // Catch:{ Exception -> 0x0138 }
            cih r4 = defpackage.cih.a()     // Catch:{ Exception -> 0x0138 }
            int r4 = r4.b     // Catch:{ Exception -> 0x0138 }
            long r4 = (long) r4     // Catch:{ Exception -> 0x0138 }
            r3.e = r4     // Catch:{ Exception -> 0x0138 }
            dkw r3 = r9.g     // Catch:{ Exception -> 0x0138 }
            java.lang.String r4 = r9.k     // Catch:{ Exception -> 0x0138 }
            r3.d = r4     // Catch:{ Exception -> 0x0138 }
            dkw r3 = r9.g     // Catch:{ Exception -> 0x0138 }
            java.lang.String r4 = com.autonavi.server.aos.serverkey.getAosKey()     // Catch:{ Exception -> 0x0138 }
            r3.b = r4     // Catch:{ Exception -> 0x0138 }
            dkw r3 = r9.g     // Catch:{ Exception -> 0x0138 }
            java.lang.String r4 = r9.A()     // Catch:{ Exception -> 0x0138 }
            r3.c = r4     // Catch:{ Exception -> 0x0138 }
            dkv r3 = r9.a     // Catch:{ Exception -> 0x0138 }
            cim r4 = new cim     // Catch:{ Exception -> 0x0138 }
            r4.<init>()     // Catch:{ Exception -> 0x0138 }
            boolean r5 = r3.b     // Catch:{ Exception -> 0x0138 }
            if (r5 != 0) goto L_0x0055
            r3.d = r4     // Catch:{ Exception -> 0x0138 }
        L_0x0055:
            dkv r3 = r9.a     // Catch:{ Exception -> 0x0138 }
            dkw r4 = r9.g     // Catch:{ Exception -> 0x0138 }
            if (r4 == 0) goto L_0x00b1
            java.lang.String r5 = r4.a     // Catch:{ Exception -> 0x0138 }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0138 }
            if (r5 == 0) goto L_0x0065
        L_0x0063:
            r5 = 0
            goto L_0x008a
        L_0x0065:
            java.lang.String r5 = r4.d     // Catch:{ Exception -> 0x0138 }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0138 }
            if (r5 == 0) goto L_0x006e
            goto L_0x0063
        L_0x006e:
            java.lang.String r5 = r4.b     // Catch:{ Exception -> 0x0138 }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0138 }
            if (r5 == 0) goto L_0x0077
            goto L_0x0063
        L_0x0077:
            java.lang.String r5 = r4.c     // Catch:{ Exception -> 0x0138 }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0138 }
            if (r5 == 0) goto L_0x0080
            goto L_0x0063
        L_0x0080:
            long r5 = r4.e     // Catch:{ Exception -> 0x0138 }
            r7 = 0
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 > 0) goto L_0x0089
            goto L_0x0063
        L_0x0089:
            r5 = 1
        L_0x008a:
            if (r5 == 0) goto L_0x00b1
            boolean r5 = r3.b     // Catch:{ Exception -> 0x0138 }
            if (r5 == 0) goto L_0x0091
            goto L_0x00b1
        L_0x0091:
            r3.e = r4     // Catch:{ Exception -> 0x0138 }
            int r5 = r3.a     // Catch:{ Exception -> 0x0138 }
            if (r5 != r1) goto L_0x009c
            r3.e()     // Catch:{ Exception -> 0x0138 }
            r3.b = r1     // Catch:{ Exception -> 0x0138 }
        L_0x009c:
            int r5 = r3.a     // Catch:{ Exception -> 0x0138 }
            if (r5 != 0) goto L_0x00b1
            dkr r5 = r3.d     // Catch:{ Exception -> 0x0138 }
            if (r5 == 0) goto L_0x00b1
            dkr r5 = r3.d     // Catch:{ Exception -> 0x0138 }
            r5.a(r4)     // Catch:{ Exception -> 0x0138 }
            dkr r4 = r3.d     // Catch:{ Exception -> 0x0138 }
            dku r5 = r3.g     // Catch:{ Exception -> 0x0138 }
            r4.a = r5     // Catch:{ Exception -> 0x0138 }
            r3.b = r1     // Catch:{ Exception -> 0x0138 }
        L_0x00b1:
            dkv r3 = r9.a     // Catch:{ Exception -> 0x0138 }
            cih r4 = defpackage.cih.a()     // Catch:{ Exception -> 0x0138 }
            int r4 = r4.a     // Catch:{ Exception -> 0x0138 }
            if (r4 != 0) goto L_0x00bd
            r4 = 0
            goto L_0x00be
        L_0x00bd:
            r4 = 1
        L_0x00be:
            boolean r5 = r3.b     // Catch:{ Exception -> 0x0138 }
            if (r5 == 0) goto L_0x0119
            int r5 = r3.a     // Catch:{ Exception -> 0x0138 }
            if (r5 != r4) goto L_0x00c7
            goto L_0x0119
        L_0x00c7:
            if (r4 == r1) goto L_0x00d0
            r3.c()     // Catch:{ Exception -> 0x0138 }
            r3.f()     // Catch:{ Exception -> 0x0138 }
            goto L_0x00e3
        L_0x00d0:
            dkr r5 = r3.d     // Catch:{ Exception -> 0x0138 }
            if (r5 == 0) goto L_0x00e3
            dkr r5 = r3.d     // Catch:{ Exception -> 0x0138 }
            r5.c()     // Catch:{ Exception -> 0x0138 }
            dkr r5 = r3.d     // Catch:{ Exception -> 0x0138 }
            dku r6 = r3.g     // Catch:{ Exception -> 0x0138 }
            dku r7 = r5.a     // Catch:{ Exception -> 0x0138 }
            if (r7 != r6) goto L_0x00e3
            r5.a = r2     // Catch:{ Exception -> 0x0138 }
        L_0x00e3:
            r3.a = r4     // Catch:{ Exception -> 0x0138 }
            int r2 = r3.a     // Catch:{ Exception -> 0x0138 }
            switch(r2) {
                case 0: goto L_0x00f8;
                case 1: goto L_0x00eb;
                default: goto L_0x00ea;
            }     // Catch:{ Exception -> 0x0138 }
        L_0x00ea:
            goto L_0x0119
        L_0x00eb:
            r3.e()     // Catch:{ Exception -> 0x0138 }
            ye r2 = defpackage.ye.b()     // Catch:{ Exception -> 0x0138 }
            java.lang.String r3 = "POLLING change to MQTT"
            r2.a(r3)     // Catch:{ Exception -> 0x0138 }
            goto L_0x0119
        L_0x00f8:
            dkr r2 = r3.d     // Catch:{ Exception -> 0x0138 }
            if (r2 == 0) goto L_0x0119
            dkr r2 = r3.d     // Catch:{ Exception -> 0x0138 }
            dkw r4 = r3.e     // Catch:{ Exception -> 0x0138 }
            r2.a(r4)     // Catch:{ Exception -> 0x0138 }
            dkr r2 = r3.d     // Catch:{ Exception -> 0x0138 }
            java.lang.String[] r4 = r3.c     // Catch:{ Exception -> 0x0138 }
            r2.a(r4)     // Catch:{ Exception -> 0x0138 }
            dkr r2 = r3.d     // Catch:{ Exception -> 0x0138 }
            dku r3 = r3.g     // Catch:{ Exception -> 0x0138 }
            r2.a = r3     // Catch:{ Exception -> 0x0138 }
            ye r2 = defpackage.ye.b()     // Catch:{ Exception -> 0x0138 }
            java.lang.String r3 = "MQTT change to POLLING"
            r2.a(r3)     // Catch:{ Exception -> 0x0138 }
        L_0x0119:
            dkv r2 = r9.a     // Catch:{ Exception -> 0x0138 }
            java.util.ArrayList<dku> r2 = r2.f     // Catch:{ Exception -> 0x0138 }
            r2.add(r9)     // Catch:{ Exception -> 0x0138 }
            dkv r2 = r9.a     // Catch:{ Exception -> 0x0138 }
            java.lang.String[] r3 = r9.j     // Catch:{ Exception -> 0x0138 }
            boolean r4 = r2.b     // Catch:{ Exception -> 0x0138 }
            if (r4 == 0) goto L_0x0137
            r2.c = r3     // Catch:{ Exception -> 0x0138 }
            int r4 = r2.a     // Catch:{ Exception -> 0x0138 }
            if (r4 != 0) goto L_0x0137
            dkr r4 = r2.d     // Catch:{ Exception -> 0x0138 }
            if (r4 == 0) goto L_0x0137
            dkr r2 = r2.d     // Catch:{ Exception -> 0x0138 }
            r2.a(r3)     // Catch:{ Exception -> 0x0138 }
        L_0x0137:
            return r1
        L_0x0138:
            r1 = move-exception
            r1.printStackTrace()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cij.w():boolean");
    }

    private void x() {
        if (this.a != null) {
            this.a.a(this);
            this.a.d();
            this.a = null;
            this.h = null;
        }
    }

    private boolean y() {
        if (!v()) {
            return false;
        }
        if (this.l != null) {
            return true;
        }
        this.l = new Timer(this.e);
        this.l.scheduleAtFixedRate(new TimerTask() {
            public final void run() {
                cij.this.o();
            }
        }, 0, ((long) cih.a().b) * 1000);
        return true;
    }

    private void z() {
        if (this.l != null) {
            this.l.cancel();
            this.l = null;
        }
    }

    public final void a(String str, String str2) {
        if (this.a != null && this.f != null) {
            StringBuilder sb = new StringBuilder("onReceive: topic: ");
            sb.append(str);
            sb.append(", data=");
            sb.append(str2);
            if (cih.a().a == 1) {
                cjo.a();
                cid d = cju.d(str2);
                if (d != null) {
                    switch (d.a) {
                        case 0:
                            this.f.a(d.c);
                            this.f.a(d.d);
                            return;
                        case 1:
                            return;
                        case 2:
                            a(TeamStatus.STATUS_TEAM_DISMISS);
                            return;
                        case 3:
                            a(TeamStatus.STATUS_USER_NOT_JOIN_IN_TEAM);
                            return;
                        case 4:
                            this.f.a(d.c);
                            cin.a((defpackage.cin.a) this, (JsFunctionCallback) null);
                            break;
                    }
                }
                return;
            }
            cjo.a();
            GroupInfo c2 = cju.c(str2);
            if (c2 != null) {
                TeamStatus teamStatus = c2.getTeamStatus();
                if (teamStatus == TeamStatus.STATUS_TEAM_DISMISS || teamStatus == TeamStatus.STATUS_TEAM_NOT_EXISTS || teamStatus == TeamStatus.STATUS_USER_NOT_JOIN_IN_TEAM) {
                    a(teamStatus);
                    return;
                }
                this.f.a(c2);
            }
        }
    }

    public final void b(String str) {
        cjo.a();
    }

    public final void m() {
        cjo.a();
    }

    public final void n() {
        cjo.a();
    }

    public final void o() {
        JSONObject jSONObject;
        cjo.a();
        if (this.a != null) {
            String a2 = cin.a(this.f);
            if (a2 != null) {
                dkv dkv = this.a;
                if (dkv.b) {
                    System.currentTimeMillis();
                    if (dkv.a == 1) {
                        try {
                            jSONObject = new JSONObject(a2);
                        } catch (JSONException unused) {
                            jSONObject = null;
                        }
                        if (jSONObject != null && dkv.i != null) {
                            if (!dkv.k || dkv.l) {
                                dkv.i.a(jSONObject, false);
                            }
                        }
                    }
                }
            }
        }
    }

    public final void a(String str, JsFunctionCallback jsFunctionCallback) {
        if (!TextUtils.isEmpty(str)) {
            TeamInfo c2 = this.f.c();
            new StringBuilder("onTeamStatusFetched:").append(c2);
            cjo.a();
            if (c2 == null || TextUtils.isEmpty(c2.teamId)) {
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback("");
                }
            } else if (c(jsFunctionCallback)) {
                return;
            }
        } else if (jsFunctionCallback != null) {
            jsFunctionCallback.callback("");
        }
        a(2);
    }

    public final void a(String str) {
        cjo.a();
        if (!TextUtils.isEmpty(str)) {
            GroupInfo c2 = cju.c(str);
            if (c2 != null) {
                this.f.a(c2);
                if (q()) {
                    a(3);
                    return;
                }
            }
        }
        a(2);
    }

    public final void p() {
        cjo.a();
        b((JsFunctionCallback) null);
    }

    public final void a(Context context) {
        if (aaw.c(context) && this.v) {
            b((JsFunctionCallback) null);
            this.v = false;
        }
    }

    public final boolean q() {
        if (w() && y()) {
            return true;
        }
        return false;
    }

    public final void a(TeamStatus teamStatus) {
        new StringBuilder("uninitConnection:").append(teamStatus);
        cjo.a();
        x();
        z();
        this.f.a(teamStatus);
    }

    private String A() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("tid", NetworkParam.getTaobaoID());
            jSONObject.put("channel", serverkey.getAosChannel());
            jSONObject.put(LocationParams.PARA_COMMON_DIP, NetworkParam.getDip());
            jSONObject.put(LocationParams.PARA_COMMON_DIC, NetworkParam.getDic());
            jSONObject.put(LocationParams.PARA_COMMON_DIU, NetworkParam.getDiu());
            jSONObject.put(LocationParams.PARA_COMMON_AUTODIV, NetworkParam.getDiv());
            jSONObject.put(Oauth2AccessToken.KEY_UID, k());
            jSONObject.put("sessionid", abj.a((Context) AMapAppGlobal.getApplication()).b());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject.toString();
    }

    public final void a() {
        b((JsFunctionCallback) null);
    }

    private void a(int i2) {
        new StringBuilder("state set to ").append(b(i2));
        this.t = i2;
        if (C()) {
            B();
        }
    }

    /* access modifiers changed from: private */
    public void B() {
        if (this.u != null) {
            JsFunctionCallback jsFunctionCallback = (JsFunctionCallback) this.u.get();
            if (jsFunctionCallback != null) {
                JSONObject a2 = cju.a();
                Object[] objArr = new Object[1];
                objArr[0] = a2 != null ? a2.toString() : "";
                jsFunctionCallback.callback(objArr);
            }
        }
    }

    private boolean C() {
        return this.t == 2 || this.t == 3;
    }

    public final cui a(bid bid) {
        return new cif(bid);
    }

    public final boolean r() {
        return cih.a().b();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(13:1|2|3|4|5|6|(1:10)|11|(1:13)|14|(1:16)|17|(3:19|20|21)(5:22|23|24|25|26)) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x0021 */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x00c6  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00e2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void d() {
        /*
            r6 = this;
            monitor-enter(r6)
            cih r0 = defpackage.cih.a()     // Catch:{ all -> 0x00e8 }
            r0.i = r6     // Catch:{ all -> 0x00e8 }
            lo r0 = defpackage.lo.a()     // Catch:{ all -> 0x00e8 }
            java.lang.String r1 = "AGroup_control"
            r0.a(r1, r6)     // Catch:{ all -> 0x00e8 }
            lo r0 = defpackage.lo.a()     // Catch:{ all -> 0x00e8 }
            java.lang.String r1 = "AGroup_control"
            java.lang.String r0 = r0.a(r1)     // Catch:{ all -> 0x00e8 }
            cih r1 = defpackage.cih.a()     // Catch:{ Exception -> 0x0021 }
            r1.a(r0)     // Catch:{ Exception -> 0x0021 }
        L_0x0021:
            cih r0 = defpackage.cih.a()     // Catch:{ all -> 0x00e8 }
            com.amap.bundle.mapstorage.MapSharePreference r1 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ all -> 0x00e8 }
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r2 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences     // Catch:{ all -> 0x00e8 }
            r1.<init>(r2)     // Catch:{ all -> 0x00e8 }
            java.lang.String r2 = "keep_alive_mode"
            r3 = 0
            int r1 = r1.getIntValue(r2, r3)     // Catch:{ all -> 0x00e8 }
            r0.a = r1     // Catch:{ all -> 0x00e8 }
            int r1 = r0.a     // Catch:{ all -> 0x00e8 }
            r2 = 1
            if (r1 == r2) goto L_0x0040
            int r1 = r0.a     // Catch:{ all -> 0x00e8 }
            if (r1 == 0) goto L_0x0040
            r0.a = r3     // Catch:{ all -> 0x00e8 }
        L_0x0040:
            com.amap.bundle.mapstorage.MapSharePreference r1 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ all -> 0x00e8 }
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r4 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences     // Catch:{ all -> 0x00e8 }
            r1.<init>(r4)     // Catch:{ all -> 0x00e8 }
            java.lang.String r4 = "polling_timer_period"
            r5 = 10
            int r1 = r1.getIntValue(r4, r5)     // Catch:{ all -> 0x00e8 }
            r0.b = r1     // Catch:{ all -> 0x00e8 }
            int r1 = r0.b     // Catch:{ all -> 0x00e8 }
            if (r1 > 0) goto L_0x0057
            r0.b = r5     // Catch:{ all -> 0x00e8 }
        L_0x0057:
            com.amap.bundle.mapstorage.MapSharePreference r1 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ all -> 0x00e8 }
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r4 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences     // Catch:{ all -> 0x00e8 }
            r1.<init>(r4)     // Catch:{ all -> 0x00e8 }
            java.lang.String r4 = "bg_polling_timer_period"
            r5 = 120(0x78, float:1.68E-43)
            int r1 = r1.getIntValue(r4, r5)     // Catch:{ all -> 0x00e8 }
            r0.c = r1     // Catch:{ all -> 0x00e8 }
            com.amap.bundle.mapstorage.MapSharePreference r1 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ all -> 0x00e8 }
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r4 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences     // Catch:{ all -> 0x00e8 }
            r1.<init>(r4)     // Catch:{ all -> 0x00e8 }
            java.lang.String r4 = "agroup_bg_continuous_time"
            r5 = 86400(0x15180, float:1.21072E-40)
            int r1 = r1.getIntValue(r4, r5)     // Catch:{ all -> 0x00e8 }
            r0.d = r1     // Catch:{ all -> 0x00e8 }
            com.amap.bundle.mapstorage.MapSharePreference r1 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ all -> 0x00e8 }
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r4 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences     // Catch:{ all -> 0x00e8 }
            r1.<init>(r4)     // Catch:{ all -> 0x00e8 }
            java.lang.String r4 = "agroup_bg_max_upload_time"
            r5 = 21600(0x5460, float:3.0268E-41)
            int r1 = r1.getIntValue(r4, r5)     // Catch:{ all -> 0x00e8 }
            r0.e = r1     // Catch:{ all -> 0x00e8 }
            com.amap.bundle.mapstorage.MapSharePreference r1 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ all -> 0x00e8 }
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r4 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences     // Catch:{ all -> 0x00e8 }
            r1.<init>(r4)     // Catch:{ all -> 0x00e8 }
            java.lang.String r4 = "agroup_open"
            int r1 = r1.getIntValue(r4, r3)     // Catch:{ all -> 0x00e8 }
            r0.f = r1     // Catch:{ all -> 0x00e8 }
            com.amap.bundle.mapstorage.MapSharePreference r1 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ all -> 0x00e8 }
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r4 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences     // Catch:{ all -> 0x00e8 }
            r1.<init>(r4)     // Catch:{ all -> 0x00e8 }
            java.lang.String r4 = "agroup_bg_upload_open"
            int r1 = r1.getIntValue(r4, r3)     // Catch:{ all -> 0x00e8 }
            r0.g = r1     // Catch:{ all -> 0x00e8 }
            com.amap.bundle.mapstorage.MapSharePreference r1 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ all -> 0x00e8 }
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r3 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences     // Catch:{ all -> 0x00e8 }
            r1.<init>(r3)     // Catch:{ all -> 0x00e8 }
            java.lang.String r3 = "agroup_https"
            int r1 = r1.getIntValue(r3, r2)     // Catch:{ all -> 0x00e8 }
            r0.h = r1     // Catch:{ all -> 0x00e8 }
            esb r0 = defpackage.esb.a.a     // Catch:{ all -> 0x00e8 }
            java.lang.Class<com.autonavi.bundle.account.api.IAccountService> r1 = com.autonavi.bundle.account.api.IAccountService.class
            esc r0 = r0.a(r1)     // Catch:{ all -> 0x00e8 }
            com.autonavi.bundle.account.api.IAccountService r0 = (com.autonavi.bundle.account.api.IAccountService) r0     // Catch:{ all -> 0x00e8 }
            if (r0 == 0) goto L_0x00c9
            r0.a(r6)     // Catch:{ all -> 0x00e8 }
        L_0x00c9:
            com.autonavi.minimap.agroup.util.BackgroundLocateManager r0 = com.autonavi.minimap.agroup.util.BackgroundLocateManager.a()     // Catch:{ all -> 0x00e8 }
            cij$a r1 = r6.c     // Catch:{ all -> 0x00e8 }
            agl<com.autonavi.minimap.agroup.util.BackgroundLocateManager$a> r0 = r0.b     // Catch:{ all -> 0x00e8 }
            r0.a(r1)     // Catch:{ all -> 0x00e8 }
            android.content.Context r0 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()     // Catch:{ all -> 0x00e8 }
            boolean r0 = defpackage.aaw.c(r0)     // Catch:{ all -> 0x00e8 }
            if (r0 != 0) goto L_0x00e2
            r6.v = r2     // Catch:{ all -> 0x00e8 }
            monitor-exit(r6)
            return
        L_0x00e2:
            r0 = 0
            r6.b(r0)     // Catch:{ all -> 0x00e8 }
            monitor-exit(r6)
            return
        L_0x00e8:
            r0 = move-exception
            monitor-exit(r6)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cij.d():void");
    }

    private static boolean v() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        return iAccountService.a();
    }

    public final String k() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e2 = iAccountService.e();
        return e2 != null ? e2.a : "";
    }

    public final boolean c(String str) {
        return cjp.a(str, "");
    }

    public void onConfigResultCallBack(int i2, String str) {
        cih.a().a(str);
    }

    public final void b(@NonNull String str, @NonNull String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", cjq.a() ? "in" : "out");
            LogManager.actionLogV2(str, str2, jSONObject);
        } catch (JSONException unused) {
        }
    }

    static /* synthetic */ void a(cij cij, long j2) {
        boolean z2 = true;
        Object[] objArr = new Object[1];
        if (cij.y == null) {
            z2 = false;
        }
        objArr[0] = Boolean.valueOf(z2);
        BackgroundLocateManager.a("startBgPauseConnectionTask. bgPauseConnectionTaskFuture: %s", objArr);
        cij.t();
        cij.y = cij.b.schedule(cij.x, j2, TimeUnit.MILLISECONDS);
    }

    static /* synthetic */ boolean s() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
