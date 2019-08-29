package defpackage;

import android.app.Activity;
import android.os.Handler;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.inter.IBundleManifest;
import java.util.LinkedList;
import java.util.List;

/* renamed from: esg reason: default package */
/* compiled from: VAppManager */
public final class esg {
    private static final String e = "esg";
    public Activity a;
    public List<esh> b;
    public esj c;
    public boolean d;
    private List<esh> f;
    private List<Class<esh>> g;
    private boolean h;
    private Handler i;

    /* renamed from: esg$a */
    /* compiled from: VAppManager */
    static class a {
        /* access modifiers changed from: private */
        public static esg a = new esg(0);
    }

    /* synthetic */ esg(byte b2) {
        this();
    }

    private esg() {
        this.h = true;
        this.g = new LinkedList();
        this.f = new LinkedList();
        this.b = new LinkedList();
        this.i = new Handler();
    }

    public static esg a() {
        return a.a;
    }

    public final void c() {
        boolean a2 = ahs.a(this.c.a);
        la.c(null);
        for (Class next : this.g) {
            if (a2) {
                a(next);
            } else if (esd.class.isAssignableFrom(next)) {
                esh a3 = a(next);
                if (a3 != null) {
                    a3.vAppCreate();
                }
            }
            la.c(next.getSimpleName());
        }
    }

    private esh a(Class<esh> cls) {
        esh esh;
        InstantiationException e2;
        IllegalAccessException e3;
        try {
            esh = cls.newInstance();
            try {
                esh.attachWingContext(this.c);
                this.f.add(esh);
                if (esh.isRegisterLifeCycle()) {
                    this.b.add(esh);
                }
            } catch (InstantiationException e4) {
                e2 = e4;
                String str = e;
                StringBuilder sb = new StringBuilder();
                sb.append(cls);
                sb.append(e2.toString());
                AMapLog.e(str, sb.toString());
                return esh;
            } catch (IllegalAccessException e5) {
                e3 = e5;
                String str2 = e;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(cls);
                sb2.append(e3.toString());
                AMapLog.e(str2, sb2.toString());
                return esh;
            }
        } catch (InstantiationException e6) {
            InstantiationException instantiationException = e6;
            esh = null;
            e2 = instantiationException;
            String str3 = e;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(cls);
            sb3.append(e2.toString());
            AMapLog.e(str3, sb3.toString());
            return esh;
        } catch (IllegalAccessException e7) {
            IllegalAccessException illegalAccessException = e7;
            esh = null;
            e3 = illegalAccessException;
            String str22 = e;
            StringBuilder sb22 = new StringBuilder();
            sb22.append(cls);
            sb22.append(e3.toString());
            AMapLog.e(str22, sb22.toString());
            return esh;
        }
        return esh;
    }

    public final void d() {
        for (esh vAppMapLoadCompleted : this.b) {
            vAppMapLoadCompleted.vAppMapLoadCompleted();
        }
    }

    public final void a(Activity activity) {
        this.a = activity;
        if (!this.d) {
            for (esh next : this.f) {
                next.mIsColdBoot = this.h;
                next.vAppCreate();
                if (bny.a) {
                    new StringBuilder("VAppManager-dispatchOnCreate:").append(next.getClass().getName());
                    la.i();
                }
            }
        }
        this.d = true;
        this.h = false;
    }

    public final void b() {
        List<Class> loadAllBundle = ((IBundleManifest) bqn.a(IBundleManifest.class)).loadAllBundle();
        if (loadAllBundle != null) {
            for (Class next : loadAllBundle) {
                if (esh.class.isAssignableFrom(next)) {
                    this.g.add(next);
                }
            }
        }
    }
}
