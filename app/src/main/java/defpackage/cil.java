package defpackage;

import android.support.annotation.NonNull;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.annotation.MainMapFeature;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.sdk.location.LocationInstrument;
import java.lang.ref.WeakReference;

@MainMapFeature
/* renamed from: cil reason: default package */
/* compiled from: AGroupLifeCycleManager */
public final class cil implements czu {
    /* access modifiers changed from: private */
    public static final String a = "cil";
    private a b;
    private b c;
    /* access modifiers changed from: private */
    public Class<?> d;

    /* renamed from: cil$a */
    /* compiled from: AGroupLifeCycleManager */
    static class a implements defpackage.dro.a {
        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }

        public final void a(@NonNull WeakReference<AbstractBasePage> weakReference) {
            cji.e().b(true);
            AMapLog.d(cil.a, "onPageLifeCreated: ");
        }

        public final void b(WeakReference<AbstractBasePage> weakReference) {
            cji.e().b(true);
            AMapLog.d(cil.a, "onPageLifeDestroyed: ");
        }
    }

    /* renamed from: cil$b */
    /* compiled from: AGroupLifeCycleManager */
    class b implements d {
        private b() {
        }

        /* synthetic */ b(cil cil, byte b) {
            this();
        }

        public final void onPageLifeResumed(@NonNull WeakReference<AbstractBasePage> weakReference) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) weakReference.get();
            if (abstractBasePage != null) {
                cil.this.d = abstractBasePage.getClass();
                ciu a2 = defpackage.ciu.a.a;
                Class<?> cls = abstractBasePage.getClass();
                cji e = cji.e();
                if (e != null) {
                    cuf cuf = a2.a.get(cls);
                    if (cuf == null) {
                        if (a2.b == null) {
                            a2.b = new ciw();
                        }
                        cuf = a2.b;
                    }
                    e.a(cuf.a());
                    e.a(cuf.c());
                    e.h = cuf.b();
                    if (e.h == 1) {
                        if (e.b != null) {
                            e.b.e = true;
                        }
                        if (e.i == null) {
                            MapManager mapManager = DoNotUseTool.getMapManager();
                            if (mapManager != null) {
                                e.i = mapManager.getOverlayManager();
                                LocationInstrument.getInstance().addHighFrequencyStatusCallback(e.p, null);
                            }
                        }
                    } else {
                        if (e.b != null) {
                            e.b.e = false;
                        }
                        if (e.i != null) {
                            LocationInstrument.getInstance().removeHighFrequencyStatusCallback(e.p);
                            e.i = null;
                        }
                    }
                    if (!e.f) {
                        e.f = true;
                        if (e.e) {
                            e.c.aa().sortOverlay();
                            if (e.b != null) {
                                e.b.a();
                            }
                        }
                    }
                }
            }
        }

        public final void onPageLifePaused(@NonNull WeakReference<AbstractBasePage> weakReference) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) weakReference.get();
            if (abstractBasePage != null && cil.this.d == abstractBasePage.getClass()) {
                defpackage.ciu.a.a;
                cji e = cji.e();
                if (e != null && e.f) {
                    e.f = false;
                }
            }
        }
    }

    public final void onCreate() {
        this.b = new a(0);
        this.c = new b(this, 0);
        drm.a((c) this.b);
        drm.a((c) this.c);
    }

    public final void onDestroy() {
        drm.b((c) this.b);
        drm.b((c) this.c);
        defpackage.ciu.a.a.a.clear();
    }
}
