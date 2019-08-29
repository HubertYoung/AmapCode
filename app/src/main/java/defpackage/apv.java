package defpackage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.bundle.amaphome.compat.service.MainMapCallbackHolder;
import com.autonavi.bundle.amaphome.compat.service.MainMapCallbackHolder.MethodType;
import com.autonavi.bundle.amaphome.page.MapHomePage;
import com.autonavi.common.Callback.a;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.inter.IMainMapFeatureProvider;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.basemap.weather.net.GetWeatherInfoManager;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.sdk.location.LocationInstrument;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* renamed from: apv reason: default package */
/* compiled from: MainPageServiceCompat */
public final class apv {
    private final arn a;
    private List<Object> b = new ArrayList();
    private boolean c;
    private aqg d;
    private MainMapCallbackHolder e;
    private GetWeatherInfoManager f;

    public apv(arn arn) {
        this.a = arn;
        this.f = new GetWeatherInfoManager();
    }

    public final void b() {
        this.a.a();
        if (this.e != null) {
            this.e.a(MethodType.START, false, new Object[0]);
            this.e.a(MethodType.START, true, new Object[0]);
        }
    }

    public final void c() {
        this.a.b();
        if (this.e != null) {
            this.e.a(MethodType.STOP, false, new Object[0]);
            this.e.a(MethodType.STOP, true, new Object[0]);
        }
    }

    public final void d() {
        if (this.e != null) {
            this.e.a(MethodType.RESUME, false, new Object[0]);
            this.e.a(MethodType.RESUME, true, new Object[0]);
        }
        if (this.f != null) {
            this.f.a();
        }
    }

    public final void e() {
        if (this.e != null) {
            this.e.a(MethodType.PAUSE, false, new Object[0]);
            this.e.a(MethodType.PAUSE, true, new Object[0]);
        }
        if (this.f != null) {
            GetWeatherInfoManager getWeatherInfoManager = this.f;
            GetWeatherInfoManager.a((String) "cancelRequest.");
            a aVar = getWeatherInfoManager.b;
            if (aVar != null && !aVar.isCancelled()) {
                aVar.cancel();
            }
            getWeatherInfoManager.b = null;
            if (getWeatherInfoManager.a != null) {
                getWeatherInfoManager.a.a();
            }
        }
    }

    public final void f() {
        this.e.a(MethodType.DESTROY, false, new Object[0]);
        this.c = false;
        this.b.clear();
        if (this.d != null) {
            this.e.a(MethodType.DESTROY, true, new Object[0]);
            aqg aqg = this.d;
            MainMapCallbackHolder mainMapCallbackHolder = aqg.b;
            mainMapCallbackHolder.a.clear();
            mainMapCallbackHolder.b.clear();
            if (aqg.j()) {
                for (int i = 0; i < aqg.c.size(); i++) {
                    btw btw = aqg.c.get(i);
                    if (btw != null) {
                        aqg.a.a(btw);
                    }
                }
                aqg.c.clear();
            }
            if (aqg.a != null) {
                aqf aqf = aqg.a;
                aqf.a = null;
                aqf.b = null;
                aqf.d = null;
                aqf.e = null;
                aqg.a = null;
            }
            aqg.d.clear();
            this.e = null;
            this.d = null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0047  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean g() {
        /*
            r7 = this;
            aqg r0 = r7.d
            r1 = 0
            if (r0 == 0) goto L_0x0049
            com.autonavi.bundle.amaphome.compat.service.MainMapCallbackHolder r0 = r7.e
            java.util.Map<java.lang.String, java.util.List<com.autonavi.bundle.amaphome.compat.service.MainMapCallbackHolder$a>> r0 = r0.b
            com.autonavi.bundle.amaphome.compat.service.MainMapCallbackHolder$MethodType r2 = com.autonavi.bundle.amaphome.compat.service.MainMapCallbackHolder.MethodType.BACK_PRESSED
            java.lang.String r2 = r2.methodName
            java.lang.Object r0 = r0.get(r2)
            java.util.List r0 = (java.util.List) r0
            if (r0 == 0) goto L_0x0042
            boolean r2 = r0.isEmpty()
            if (r2 != 0) goto L_0x0042
            int r2 = r0.size()
            r3 = 0
        L_0x0020:
            if (r3 >= r2) goto L_0x0042
            java.lang.Object r4 = r0.get(r3)
            com.autonavi.bundle.amaphome.compat.service.MainMapCallbackHolder$a r4 = (com.autonavi.bundle.amaphome.compat.service.MainMapCallbackHolder.a) r4
            float r5 = r4.a
            r6 = 1084227584(0x40a00000, float:5.0)
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 >= 0) goto L_0x003f
            T r4 = r4.b
            czs r4 = (defpackage.czs) r4
            com.autonavi.common.Page$ON_BACK_TYPE r4 = r4.onBackPressed()
            com.autonavi.common.Page$ON_BACK_TYPE r5 = com.autonavi.common.Page.ON_BACK_TYPE.TYPE_IGNORE
            if (r4 != r5) goto L_0x003f
            com.autonavi.common.Page$ON_BACK_TYPE r0 = com.autonavi.common.Page.ON_BACK_TYPE.TYPE_IGNORE
            goto L_0x0043
        L_0x003f:
            int r3 = r3 + 1
            goto L_0x0020
        L_0x0042:
            r0 = 0
        L_0x0043:
            com.autonavi.common.Page$ON_BACK_TYPE r2 = com.autonavi.common.Page.ON_BACK_TYPE.TYPE_IGNORE
            if (r0 != r2) goto L_0x0049
            r0 = 1
            return r0
        L_0x0049:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.apv.g():boolean");
    }

    public final void h() {
        if (this.e != null) {
            this.e.a(true);
            this.e.b(true);
        }
    }

    public final void i() {
        if (this.e != null) {
            this.e.a(false);
            this.e.b(false);
        }
    }

    public final void j() {
        if (this.e != null) {
            this.e.a(MethodType.ACTIVITY_START, false, new Object[0]);
            this.e.a(MethodType.ACTIVITY_START, true, new Object[0]);
        }
    }

    public final void k() {
        this.a.c();
        if (this.e != null) {
            this.e.a(MethodType.ACTIVITY_RESUME, false, new Object[0]);
            this.e.a(MethodType.ACTIVITY_RESUME, true, new Object[0]);
        }
    }

    public final void l() {
        this.a.d();
        if (this.e != null) {
            this.e.a(MethodType.ACTIVITY_PAUSE, false, new Object[0]);
            this.e.a(MethodType.ACTIVITY_PAUSE, true, new Object[0]);
        }
    }

    public final void m() {
        if (this.e != null) {
            this.e.a(MethodType.ACTIVITY_STOP, false, new Object[0]);
        }
        if (this.e != null) {
            this.e.a(MethodType.ACTIVITY_STOP, true, new Object[0]);
        }
    }

    public final void n() {
        if (this.e != null) {
            this.e.a(MethodType.SURFACE_CREATED, false, new Object[0]);
            this.e.a(MethodType.SURFACE_CREATED, true, new Object[0]);
        }
    }

    public final void a(int i, int i2) {
        if (this.e != null) {
            this.e.a(MethodType.SURFACE_CHANGED, false, Integer.valueOf(i), Integer.valueOf(i2));
            this.e.a(MethodType.SURFACE_CHANGED, true, Integer.valueOf(i), Integer.valueOf(i2));
        }
    }

    public final void o() {
        if (this.e != null) {
            this.e.a(MethodType.SURFACE_DESTROY, false, new Object[0]);
            this.e.a(MethodType.SURFACE_DESTROY, true, new Object[0]);
        }
    }

    public final void a(int i, ResultType resultType, PageBundle pageBundle) {
        if (this.e != null) {
            Boolean a2 = this.e.a(MethodType.RESULT, false, Integer.valueOf(i), resultType, pageBundle);
            if (a2 != null && a2.booleanValue()) {
                return;
            }
        }
        if (this.e != null) {
            Boolean a3 = this.e.a(MethodType.RESULT, true, Integer.valueOf(i), resultType, pageBundle);
            if (a3 != null) {
                a3.booleanValue();
            }
        }
    }

    public final void a(Configuration configuration) {
        if (this.e != null) {
            this.e.a(MethodType.CONFIGURATION_CHANGED, false, configuration);
            this.e.a(MethodType.CONFIGURATION_CHANGED, true, configuration);
        }
    }

    public final void a(boolean z) {
        if (this.e != null) {
            this.e.a(MethodType.WINDOW_FOCUS_CHANGED, false, Boolean.valueOf(z));
        }
        if (this.e != null) {
            this.e.a(MethodType.WINDOW_FOCUS_CHANGED, true, Boolean.valueOf(z));
        }
    }

    public final void p() {
        IOverlayManager iOverlayManager;
        cdx cdx;
        if (this.a != null && (this.a instanceof MapHomePage)) {
            MapHomePage mapHomePage = (MapHomePage) this.a;
            if (mapHomePage.i || MapHomePage.m()) {
                int l = mapHomePage.l();
                bez.b(mapHomePage.getClass().getSimpleName(), "setGpsOverlayToCenterPoint", new bew("offSetHeight", Integer.valueOf(l)));
                Context context = mapHomePage.getContext();
                bty mapView = mapHomePage.getMapView();
                if (!(context == null || mapView == null)) {
                    Point a2 = aru.a(context, l);
                    MapManager mapManager = DoNotUseTool.getMapManager();
                    cdy cdy = null;
                    if (mapManager == null) {
                        iOverlayManager = null;
                    } else {
                        iOverlayManager = mapManager.getOverlayManager();
                    }
                    if (iOverlayManager == null) {
                        cdx = null;
                    } else {
                        cdx = iOverlayManager.getGpsLayer();
                    }
                    if (cdx != null) {
                        cdy = cdx.b();
                    }
                    if (!(cdy == null || a2 == null)) {
                        mapView.a(200, (GLGeoPoint) LocationInstrument.getInstance().getLatestPosition(), a2, true);
                    }
                }
                mapHomePage.i = false;
            }
        }
        this.e.a(MethodType.APPEAR, false, new Object[0]);
        this.e.a(MethodType.APPEAR, true, new Object[0]);
    }

    public final void q() {
        this.e.a(MethodType.COVER, false, new Object[0]);
        this.e.a(MethodType.COVER, true, new Object[0]);
    }

    public final boolean a(PageBundle pageBundle) {
        if (this.e != null) {
            Boolean a2 = this.e.a(MethodType.NEW_INTENT, false, pageBundle);
            if (a2 != null && a2.booleanValue()) {
                return true;
            }
        }
        return false;
    }

    public final void a(int i, int i2, Intent intent) {
        if (this.e != null) {
            Boolean a2 = this.e.a(MethodType.ACTIVITY_RESULT, false, Integer.valueOf(i), Integer.valueOf(i2), intent);
            if (a2 != null && a2.booleanValue()) {
                return;
            }
        }
        if (this.e != null) {
            Boolean a3 = this.e.a(MethodType.ACTIVITY_RESULT, true, Integer.valueOf(i), Integer.valueOf(i2), intent);
            if (a3 != null) {
                a3.booleanValue();
            }
        }
    }

    @SuppressFBWarnings({"VA_FORMAT_STRING_USES_NEWLINE"})
    public final void a() {
        this.d = (aqg) ank.a(IMainMapService.class);
        if (this.d != null) {
            this.e = this.d.b;
            this.d.a = new aqf(this.a);
            ((czh) ((IMainMapService) ank.a(IMainMapService.class)).a(czh.class)).a();
        }
        if (!this.c) {
            long currentTimeMillis = System.currentTimeMillis();
            IMainMapFeatureProvider iMainMapFeatureProvider = (IMainMapFeatureProvider) bqn.a(IMainMapFeatureProvider.class);
            if (iMainMapFeatureProvider != null) {
                Set<Class<?>> mainMapFeatures = iMainMapFeatureProvider.getMainMapFeatures();
                IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
                if (mainMapFeatures != null && mainMapFeatures.size() > 0) {
                    for (Class next : mainMapFeatures) {
                        if (next != null) {
                            try {
                                long currentTimeMillis2 = System.currentTimeMillis();
                                Object newInstance = next.newInstance();
                                AMapLog.i("MainMapFeatureManager", String.format("\ninitPresenter %s cost %d ms!", new Object[]{next.getSimpleName(), Long.valueOf(System.currentTimeMillis() - currentTimeMillis2)}));
                                if (newInstance != null) {
                                    this.b.add(newInstance);
                                    iMainMapService.a(newInstance);
                                }
                            } catch (IllegalAccessException | InstantiationException unused) {
                            }
                        }
                    }
                }
                AMapLog.i("MainMapFeatureManager", String.format("\ninitPresenter features totally cost %d ms!", new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)}));
            }
        }
        this.e.a(MethodType.CREATE, false, new Object[0]);
        if (this.e != null) {
            this.e.a(MethodType.CREATE, true, new Object[0]);
        }
        if (this.f != null) {
            this.f.a();
        }
    }
}
