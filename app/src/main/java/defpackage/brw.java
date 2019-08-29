package defpackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.ae.gmap.utils.GLMapStaticValue;
import com.autonavi.ae.gmap.utils.GLMapStaticValue.WeatherAnimationState;
import com.autonavi.ae.gmap.utils.GLMapStaticValue.WeatherType;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.jni.ae.gmap.glinterface.MapEngineInitParam;
import com.autonavi.jni.ae.gmap.gloverlay.GLGpsOverlay;
import com.autonavi.jni.ae.gmap.gloverlay.GLNaviOverlay;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle;
import com.autonavi.jni.ae.gmap.scenic.Label3rd;
import com.autonavi.jni.eyrie.amap.redesign.maps.vmap.VMapPage;
import com.autonavi.jni.eyrie.amap.redesign.maps.vmap.VMapSurface;
import java.util.ArrayList;

/* renamed from: brw reason: default package */
/* compiled from: MapViewImpl */
public final class brw implements bty {
    public int a = 0;
    public int b = 35;
    aky c;
    private akq d;
    private int e;
    private bty f;
    private VMapSurface g;
    private VMapPage h;
    private agl<b> i = new agl<>();

    /* renamed from: brw$a */
    /* compiled from: MapViewImpl */
    public static class a implements b {
        public void a() {
        }

        public void b() {
        }
    }

    public brw(akq akq, aky aky, MapEngineInitParam mapEngineInitParam, Rect rect, int i2, int i3) {
        akq akq2 = akq;
        aky aky2 = aky;
        if (akq2 != null && aky2 != null) {
            this.d = akq2;
            this.c = aky2;
            SharedPreferences sharedPrefs = new MapSharePreference((String) "SharedPreferences").sharedPrefs();
            int i4 = sharedPrefs.getInt("X", 0);
            int i5 = sharedPrefs.getInt("Y", 0);
            boolean z = sharedPrefs.getBoolean("cmd_render", true);
            alr alr = new alr(i4, i5);
            akq akq3 = this.d;
            akq.b("setMapGeoStateDefaultParams: ".concat(String.valueOf(alr)));
            akq3.g.a(alr.a, alr.b, alr.c, alr.d, alr.e);
            emv.c();
            la.b();
            this.e = this.d.a(this.c.getDeviceId(), rect, i2, i3, mapEngineInitParam);
            if (!z) {
                akq akq4 = this.d;
                int i6 = this.e;
                akq.b("setContentType: ".concat(String.valueOf(i6)));
                akq4.d.setBusinessDataParamater(i6, 79, -1, 0, 0, 0);
                akq4.p(akq4.d.getBelongToRenderDeviceId(i6));
            }
            this.g = new VMapSurface(this.e);
            VMapSurface vMapSurface = this.g;
            Context context = ((View) this.c).getContext();
            StringBuilder sb = new StringBuilder("GlobalVPage");
            sb.append(this.e);
            this.h = vMapSurface.createVMapPage(context, sb.toString(), true);
            this.h.onShow();
        }
    }

    public final Resources a() {
        return this.d.c.getApplicationContext().getResources();
    }

    public final aky b() {
        return this.c;
    }

    public final akq c() {
        return this.d;
    }

    public final void a(float f2) {
        akr akr = this.d.z(this.e).d;
        if (-1 != akr.a && akr.b != null && akr.c != null) {
            StringBuilder sb = new StringBuilder("setMapAngle: ");
            sb.append(akr.a);
            sb.append(", ");
            sb.append(f2);
            sb.append(", false");
            akq.b(sb.toString());
            akr.b.setMapAngle(akr.a, f2);
            akr.c.p(akr.b.getBelongToRenderDeviceId(akr.a));
        }
    }

    public final void a(bty bty) {
        this.f = bty;
    }

    public final bty e() {
        return this.f;
    }

    public final void a(Runnable runnable, int i2) {
        this.d.a(runnable, (long) i2);
    }

    public final void a(Runnable runnable) {
        this.d.a(runnable, 0);
    }

    public final GLMapState f() {
        return this.d.d.getMapState(this.e);
    }

    public final GLMapState a(int i2) {
        return this.d.d.getMapState(i2);
    }

    public final boolean g() {
        return this.d.d.isInMapAnimation(this.e);
    }

    public final int h() {
        akq akq = this.d;
        int i2 = this.e;
        akq.b((String) "getMapViewLeft: ");
        return akq.d.getMapLeftTop(i2).x;
    }

    public final int i() {
        akq akq = this.d;
        int i2 = this.e;
        akq.b((String) "getMapViewTop: ");
        return akq.d.getMapLeftTop(i2).y;
    }

    public final void b(int i2) {
        this.d.e(this.c.getDeviceId(), i2);
    }

    public final void c(int i2) {
        this.d.d(this.c.getDeviceId(), akq.F, i2);
    }

    public final int j() {
        return this.e;
    }

    public final boolean k() {
        return this.d.a(this.e);
    }

    public final int l() {
        return this.d.b(this.e);
    }

    public final int m() {
        return this.d.c(this.e);
    }

    public final GLGeoPoint n() {
        return this.d.d(this.e);
    }

    public final GeoPoint o() {
        return new GeoPoint(n());
    }

    public final int p() {
        akr akr = this.d.z(this.e).d;
        if (-1 == akr.a || akr.b == null || akr.c == null) {
            return 0;
        }
        Point mapCenter = akr.b.getMapCenter(akr.a);
        StringBuilder sb = new StringBuilder("getCenterX: ");
        sb.append(akr.a);
        sb.append(", ");
        sb.append(mapCenter.x);
        akq.b(sb.toString());
        return mapCenter.x;
    }

    public final int q() {
        akr akr = this.d.z(this.e).d;
        if (-1 == akr.a || akr.b == null || akr.c == null) {
            return 0;
        }
        Point mapCenter = akr.b.getMapCenter(akr.a);
        StringBuilder sb = new StringBuilder("getCenterY: ");
        sb.append(akr.a);
        sb.append(", ");
        sb.append(mapCenter.y);
        akq.b(sb.toString());
        return mapCenter.y;
    }

    public final boolean a(int i2, int i3) {
        return this.d.a(this.e, i2, i3);
    }

    public final void b(float f2) {
        akq akq = this.d;
        int i2 = this.e;
        StringBuilder sb = new StringBuilder("setTextScale: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(f2);
        akq.b(sb.toString());
        if (f2 > 0.001f) {
            if (f2 < 0.8f) {
                f2 = 0.8f;
            }
            if (f2 > 2.0f) {
                f2 = 2.0f;
            }
            akq.d.setBusinessDataParamater(i2, 68, (int) (f2 * 1000.0f), 0, 0, 0);
            akq.p(akq.d.getBelongToRenderDeviceId(i2));
        }
    }

    public final void a(boolean z) {
        akq akq = this.d;
        int i2 = this.e;
        StringBuilder sb = new StringBuilder("setTrafficLightStyle: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(z);
        akq.b(sb.toString());
        if (akq.d != null) {
            akq.d.setControllerStateBoolValue(i2, 2, z);
        }
        akq.o(akq.d.getBelongToRenderDeviceId(i2));
    }

    public final boolean r() {
        akq akq = this.d;
        int i2 = this.e;
        boolean controllerStateBoolValue = akq.d != null ? akq.d.getControllerStateBoolValue(i2, 2) : false;
        StringBuilder sb = new StringBuilder("isTrafficLight: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(controllerStateBoolValue);
        akq.b(sb.toString());
        return controllerStateBoolValue;
    }

    public final boolean s() {
        akq akq = this.d;
        int i2 = this.e;
        boolean controllerStateBoolValue = akq.d != null ? akq.d.getControllerStateBoolValue(i2, 1) : false;
        StringBuilder sb = new StringBuilder("getTrafficState: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(controllerStateBoolValue);
        akq.b(sb.toString());
        return controllerStateBoolValue;
    }

    public final void b(boolean z) {
        akt z2 = this.d.z(this.e);
        if (-1 != z2.a && z2.b != null && z2.c != null) {
            StringBuilder sb = new StringBuilder("setTrafficState: ");
            sb.append(z2.a);
            sb.append(", ");
            sb.append(z);
            akq.b(sb.toString());
            z2.b.setControllerStateBoolValue(z2.a, 1, z);
            z2.c.p(z2.b.getBelongToRenderDeviceId(z2.a));
        }
    }

    public final void t() {
        akq akq = this.d;
        int i2 = this.e;
        akq.b("refreshTraffic: ".concat(String.valueOf(i2)));
        long currentTimeMillis = System.currentTimeMillis();
        akt z = akq.z(i2);
        int belongToRenderDeviceId = akq.d.getBelongToRenderDeviceId(i2);
        if (currentTimeMillis - z.g > 5000) {
            z.g = currentTimeMillis;
            akq.b(i2, 39, true);
            akq.p(belongToRenderDeviceId);
        }
        akq.p(belongToRenderDeviceId);
    }

    public final void c(boolean z) {
        akq akq = this.d;
        int i2 = this.e;
        StringBuilder sb = new StringBuilder("setTrafficDepthInfo: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(z);
        akq.b(sb.toString());
        akq.b(i2, 19, z);
        akq.p(akq.d.getBelongToRenderDeviceId(i2));
    }

    public final boolean u() {
        akq akq = this.d;
        int i2 = this.e;
        akq.b("getTrafficDepthInfoEnable: ".concat(String.valueOf(i2)));
        return akq.b(i2, 19);
    }

    public final void a(GLGeoPoint gLGeoPoint) {
        akq akq = this.d;
        int i2 = this.e;
        StringBuilder sb = new StringBuilder("animateTo: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(gLGeoPoint);
        akq.b(sb.toString());
        if (gLGeoPoint != null) {
            akq.a(i2, 300, -9999.0f, -9999.0f, -9999.0f, gLGeoPoint.x, gLGeoPoint.y);
        }
    }

    public final void c(float f2) {
        this.d.a(this.e, f2);
    }

    public final void a(float f2, int i2) {
        akq akq = this.d;
        int i3 = this.e;
        StringBuilder sb = new StringBuilder("animateZoomToDelay: ");
        sb.append(i3);
        sb.append(", ");
        sb.append(f2);
        sb.append(", ");
        sb.append(i2);
        akq.b(sb.toString());
        akq.u.postDelayed(new Runnable(i3, f2) {
            final /* synthetic */ int a;
            final /* synthetic */ float b;

            {
                this.a = r2;
                this.b = r3;
            }

            public final void run() {
                akq.this.a(this.a, this.b);
            }
        }, (long) i2);
    }

    public final float v() {
        return this.d.e(this.e);
    }

    public final int w() {
        akr akr = this.d.z(this.e).d;
        if (-1 == akr.a || akr.b == null || akr.c == null) {
            return 3;
        }
        float mapZoomer = akr.b.getMapZoomer(akr.a);
        StringBuilder sb = new StringBuilder("getZoomLevel: ");
        sb.append(akr.a);
        sb.append(", ");
        sb.append(mapZoomer);
        akq.b(sb.toString());
        return (int) mapZoomer;
    }

    public final void e(int i2) {
        this.d.b(this.e, (float) i2);
    }

    public final boolean d(float f2) {
        return this.d.b(this.e, f2);
    }

    public final void x() {
        akq akq = this.d;
        int i2 = this.e;
        akq.b("zoomIn: ".concat(String.valueOf(i2)));
        akq.a(i2, (Point) null);
    }

    public final void y() {
        this.d.f(this.e);
    }

    public final void d(boolean z) {
        this.d.a(this.e, z);
    }

    public final void z() {
        this.d.g(this.e);
    }

    public final boolean A() {
        return this.d.h(this.e);
    }

    public final void e(boolean z) {
        this.d.b(this.e, z);
    }

    public final void B() {
        this.d.i(this.e);
    }

    public final boolean C() {
        return this.d.j(this.e);
    }

    public final void f(boolean z) {
        this.d.c(this.c.getDeviceId(), z);
    }

    public final void g(boolean z) {
        akq akq = this.d;
        int i2 = this.e;
        StringBuilder sb = new StringBuilder("enableFocusClear: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(z);
        akq.b(sb.toString());
        akq.b(i2, 6, z);
        akq.p(akq.d.getBelongToRenderDeviceId(i2));
    }

    public final boolean D() {
        akq akq = this.d;
        int i2 = this.e;
        boolean b2 = akq.b(i2, 6);
        StringBuilder sb = new StringBuilder("isEnableFocusClear: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(b2);
        akq.b(sb.toString());
        return b2;
    }

    public final void a(int i2, int i3, final defpackage.bty.a aVar) {
        AnonymousClass1 r0 = new b() {
            public final void a(Bitmap bitmap) {
                if (aVar != null) {
                    aVar.onCallBack(bitmap);
                }
            }
        };
        akq akq = this.d;
        int i4 = this.e;
        akq.b("createBitmapFromGLSurface: ".concat(String.valueOf(i4)));
        int belongToRenderDeviceId = akq.d.getBelongToRenderDeviceId(i4);
        if (akq.n(belongToRenderDeviceId)) {
            akq.w(belongToRenderDeviceId);
        }
        akq.j = r0;
        akq.k = new Rect();
        akq.k.left = 0;
        akq.k.bottom = 0;
        akq.k.top = i3;
        akq.k.right = i2;
        akq.l = Integer.valueOf(akq.l.intValue() + 1);
        Bitmap createBitmap = Bitmap.createBitmap((int) (((float) i2) * 1.0f), (((int) (((float) i3) * 1.0f)) / 4) * 4, Config.RGB_565);
        Bitmap createBitmap2 = Bitmap.createBitmap(i2, i3, akq.B(belongToRenderDeviceId).e);
        akq.m.put(akq.l, createBitmap);
        akq.n.put(akq.l, createBitmap2);
        akq.d.readMapPixelsToBmp(i4, createBitmap2, 0, 0, akq.l.intValue());
    }

    public final void E() {
        this.d.v(this.e);
    }

    public final void a(GLGpsOverlay gLGpsOverlay, boolean z) {
        akq akq = this.d;
        int i2 = this.e;
        StringBuilder sb = new StringBuilder("setGpsOverlayCenterLocked: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(gLGpsOverlay);
        sb.append(", ");
        sb.append(z);
        akq.b(sb.toString());
        if (gLGpsOverlay != null) {
            gLGpsOverlay.setGpsOverlayCenterLocked(z);
            akt z2 = akq.z(i2);
            if (z2.e != z) {
                akq.p(akq.d.getBelongToRenderDeviceId(i2));
                z2.e = z;
            }
        }
    }

    public final btm F() {
        return new btm(this.e, this.d.s(this.e));
    }

    public final synchronized void b(int i2, int i3) {
        this.d.b(this.e, i2, i3);
    }

    public final synchronized void a(float f2, float f3) {
        this.d.a(this.e, f2, f3);
    }

    public final void a(GLNaviOverlay gLNaviOverlay, amf amf, GLGeoPoint gLGeoPoint, int i2, int i3, int i4, int i5, GLGeoPoint gLGeoPoint2) {
        String str;
        GLNaviOverlay gLNaviOverlay2 = gLNaviOverlay;
        amf amf2 = amf;
        GLGeoPoint gLGeoPoint3 = gLGeoPoint;
        int i6 = i2;
        int i7 = i3;
        int i8 = i4;
        int i9 = i5;
        GLGeoPoint gLGeoPoint4 = gLGeoPoint2;
        akq akq = this.d;
        int i10 = this.e;
        if (gLGeoPoint3 == null || gLGeoPoint4 == null) {
            StringBuilder sb = new StringBuilder("setNaviStateAsync 1: ");
            sb.append(i10);
            sb.append(", ");
            sb.append(gLNaviOverlay2);
            sb.append(", ");
            sb.append(amf2);
            sb.append(", (");
            sb.append(gLGeoPoint3);
            sb.append(")");
            sb.append(i6);
            sb.append(", ");
            sb.append(i7);
            sb.append(", ");
            sb.append(i8);
            sb.append(", ");
            sb.append(i9);
            sb.append(", (");
            sb.append(gLGeoPoint4);
            sb.append(")");
            str = sb.toString();
        } else {
            StringBuilder sb2 = new StringBuilder("setNaviStateAsync 1: ");
            sb2.append(i10);
            sb2.append(", ");
            sb2.append(gLNaviOverlay2);
            sb2.append(", ");
            sb2.append(amf2);
            sb2.append(", (");
            sb2.append(gLGeoPoint3.x);
            sb2.append(",");
            sb2.append(gLGeoPoint3.y);
            sb2.append(")");
            sb2.append(i6);
            sb2.append(", ");
            sb2.append(i7);
            sb2.append(", ");
            sb2.append(i8);
            sb2.append(", ");
            sb2.append(i9);
            sb2.append(", (");
            sb2.append(gLGeoPoint4.x);
            sb2.append(",");
            sb2.append(gLGeoPoint4.y);
            sb2.append(")");
            str = sb2.toString();
        }
        akq.b(str);
        if (GLMapStaticValue.g) {
            if (gLGeoPoint3 == null || gLGeoPoint4 == null) {
                StringBuilder sb3 = new StringBuilder("setNaviStateAsync 1: ");
                sb3.append(i10);
                sb3.append(", ");
                sb3.append(gLNaviOverlay2);
                sb3.append(", ");
                sb3.append(amf2);
                sb3.append(", (");
                sb3.append(gLGeoPoint3);
                sb3.append(")");
                sb3.append(i6);
                sb3.append(", ");
                sb3.append(i7);
                sb3.append(", ");
                sb3.append(i8);
                sb3.append(", ");
                sb3.append(i9);
                sb3.append(", (");
                sb3.append(gLGeoPoint4);
                sb3.append(")");
                sb3.append(Log.getStackTraceString(new Throwable()));
                ana.b("navioverlay", sb3.toString());
            } else {
                StringBuilder sb4 = new StringBuilder("setNaviStateAsync 1: ");
                sb4.append(i10);
                sb4.append(", ");
                sb4.append(gLNaviOverlay2);
                sb4.append(", ");
                sb4.append(amf2);
                sb4.append(", (");
                sb4.append(gLGeoPoint3.x);
                sb4.append(",");
                sb4.append(gLGeoPoint3.y);
                sb4.append(")");
                sb4.append(i6);
                sb4.append(", ");
                sb4.append(i7);
                sb4.append(", ");
                sb4.append(i8);
                sb4.append(", ");
                sb4.append(i9);
                sb4.append(", (");
                sb4.append(gLGeoPoint4.x);
                sb4.append(",");
                sb4.append(gLGeoPoint4.y);
                sb4.append(")");
                sb4.append(Log.getStackTraceString(new Throwable()));
                ana.b("navioverlay", sb4.toString());
            }
        }
        if (gLNaviOverlay2 != null) {
            akq.d.addNaviStateMsg(i10, gLNaviOverlay2, amf2, gLGeoPoint3, i7, gLGeoPoint4, i9, i6, i8, 0.0f);
            akq.e(akq.d.getBelongToRenderDeviceId(i10), 2);
        }
    }

    public final void h(boolean z) {
        this.c.setNaviMode(this.e, z);
    }

    public final void e(float f2) {
        this.d.c(this.e, f2);
    }

    public final GLGeoPoint c(int i2, int i3) {
        akq akq = this.d;
        int i4 = this.e;
        GLGeoPoint gLGeoPoint = new GLGeoPoint();
        Point point = new Point(i2, i3);
        Point point2 = new Point();
        akq.d.screenToP20Point(i4, (float) point.x, (float) point.y, point2);
        gLGeoPoint.x = point2.x;
        gLGeoPoint.y = point2.y;
        StringBuilder sb = new StringBuilder("getPixel20Pnt: ");
        sb.append(i4);
        sb.append("(");
        sb.append(point.toString());
        sb.append(")->(");
        sb.append(gLGeoPoint.toString());
        sb.append(")");
        akq.b(sb.toString());
        StringBuilder sb2 = new StringBuilder("fromPixels: ");
        sb2.append(i4);
        sb2.append(", ");
        sb2.append(i2);
        sb2.append(", ");
        sb2.append(i3);
        sb2.append(", (");
        sb2.append(gLGeoPoint.x);
        sb2.append(", ");
        sb2.append(gLGeoPoint.y);
        sb2.append(")");
        akq.b(sb2.toString());
        return gLGeoPoint;
    }

    public final Point a(GLGeoPoint gLGeoPoint, Point point) {
        akq akq = this.d;
        int i2 = this.e;
        akq.b("toPixels: ".concat(String.valueOf(i2)));
        if (point == null) {
            return null;
        }
        int i3 = gLGeoPoint.x;
        int i4 = gLGeoPoint.y;
        PointF pointF = new PointF();
        akq.d.p20ToScreenPoint(i2, i3, i4, pointF);
        point.x = (int) pointF.x;
        point.y = (int) pointF.y;
        StringBuilder sb = new StringBuilder("getScreenPntBy20Pixel: ");
        sb.append(i2);
        sb.append("(");
        sb.append(i3);
        sb.append(", ");
        sb.append(i4);
        sb.append(")->(");
        sb.append(point.toString());
        sb.append(")");
        akq.b(sb.toString());
        return point;
    }

    public final float a(int i2, int i3, int i4, int i5) {
        akq akq = this.d;
        int i6 = this.e;
        akq.b("getMapZoom 1: ".concat(String.valueOf(i6)));
        akt z = akq.z(i6);
        return akq.d.calMapZoomLevel(i6, i2, i3, i4, i5, akq.h, akq.i, z.f);
    }

    public final float a(int i2, int i3, int i4, int i5, int i6, int i7) {
        akq akq = this.d;
        int i8 = this.e;
        akq.b("getMapZoom 2: ".concat(String.valueOf(i8)));
        akt z = akq.z(i8);
        return akq.d.calMapZoomLevel(i8, i2, i3, i4, i5, i6, i7, z.f);
    }

    public final void f(float f2) {
        akq akq = this.d;
        int i2 = this.e;
        StringBuilder sb = new StringBuilder("setZoomLevel: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(f2);
        akq.b(sb.toString());
        akq.b(i2, f2);
    }

    public final void G() {
        akq akq = this.d;
        int i2 = this.e;
        akq.b("clearSelectMapPois: ".concat(String.valueOf(i2)));
        akq.d.clearSelectMapPois(i2);
    }

    public final ArrayList<als> d(int i2, int i3) {
        akq akq = this.d;
        int i4 = this.e;
        akq.b("getLabelItem: ".concat(String.valueOf(i4)));
        akq.C = null;
        akq.d.getLabelBuffer(i4, i2, i3, 25, false);
        return akq.C;
    }

    public final Rect H() {
        akq akq = this.d;
        int i2 = this.e;
        akq.b("getPixel20Bound: ".concat(String.valueOf(i2)));
        Rect rect = new Rect();
        akq.d.getPixel20Bound(i2, rect);
        return rect;
    }

    public final float I() {
        return this.d.k(this.e);
    }

    public final void b(int i2, int i3, int i4, int i5) {
        akq akq = this.d;
        int i6 = this.e;
        akq.b("setMapCenterScreen: ".concat(String.valueOf(i6)));
        if (akq.h != 0 && akq.i != 0) {
            GLMapState newMapState = akq.d.getNewMapState(i6);
            if (newMapState != null) {
                akq.u(i6);
                Point point = new Point();
                newMapState.setMapGeoCenter(i2, i3);
                newMapState.recalculate();
                newMapState.screenToP20Point((float) i4, (float) i5, point);
                newMapState.recycle();
                akq.a(i6, (i2 << 1) - point.x, (i3 << 1) - point.y);
                akq.p(akq.d.getBelongToRenderDeviceId(i6));
            }
        }
    }

    public final float J() {
        return this.d.l(this.e);
    }

    public final void g(float f2) {
        this.d.d(this.e, f2);
    }

    public final float e(int i2, int i3) {
        akq akq = this.d;
        int i4 = this.e;
        akq.b("getGLUnitWithWinByY: ".concat(String.valueOf(i4)));
        return akq.d.getGLUnitWithWinByY(i4, i2, i3);
    }

    public final void a(final View view, final LayoutParams layoutParams) {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            aho.a(new Runnable() {
                public final void run() {
                    brw.this.c.addView(view, layoutParams);
                }
            });
        } else {
            this.c.addView(view, layoutParams);
        }
    }

    public final void a(final View view) {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            aho.a(new Runnable() {
                public final void run() {
                    brw.this.c.removeView(view);
                }
            });
        } else {
            this.c.removeView(view);
        }
    }

    public final void K() {
        akq akq = this.d;
        int i2 = this.e;
        akq.b("animateChangeMapMode: ".concat(String.valueOf(i2)));
        if (akq.t != null) {
            akq.t.fadeCompassWidget(i2);
        }
        float k = akq.k(i2) % 360.0f;
        if (Math.abs(k) < 1.0E-9f && akq.l(i2) == 0.0f) {
            akq.i(i2);
            akq.d.AddGroupAnimation(i2, -1, 500, -9999.0f, -9999.0f, 55.0f, -9999, -9999, true);
        } else if (Math.abs(360.0f - Math.abs(k)) <= 2.0f) {
            akq.c(i2, 0.0f);
            akq.d(i2, 0.0f);
        } else {
            akq.d.AddGroupAnimation(i2, -1, 500, -9999.0f, 0.0f, 0.0f, -9999, -9999, true);
        }
        akq.p(akq.d.getBelongToRenderDeviceId(i2));
    }

    public final void L() {
        akq akq = this.d;
        int i2 = this.e;
        akq.b("animateResoreMap: ".concat(String.valueOf(i2)));
        if (akq.t != null) {
            akq.t.fadeCompassWidget(i2);
        }
        akq.d.AddGroupAnimation(i2, -1, 500, -9999.0f, 0.0f, 0.0f, -9999, -9999, true);
        akq.p(akq.d.getBelongToRenderDeviceId(i2));
    }

    public final void a(int i2, float f2, int i3, int i4, int i5, int i6) {
        this.d.a(this.e, i2, f2, (float) i3, (float) i4, i5, i6);
    }

    public final void a(int i2, float f2, int i3, int i4, int i5, int i6, boolean z) {
        this.d.a(this.e, i2, f2, (float) i3, (float) i4, i5, i6, z);
    }

    public final void a(int i2, GLGeoPoint gLGeoPoint, Point point, boolean z) {
        akq akq = this.d;
        int i3 = this.e;
        akq.b("AddGeoAndScreenCenterGroupAnimation: ".concat(String.valueOf(i3)));
        if (akq.d != null && gLGeoPoint != null && point != null) {
            akq.d.AddGeoAndScreenCenterGroupAnimation(i3, i2, gLGeoPoint, point, z);
        }
    }

    public final void b(int i2, float f2, int i3, int i4, int i5, int i6) {
        float f3 = f2;
        int i7 = i5;
        int i8 = i6;
        akq akq = this.d;
        int i9 = this.e;
        float f4 = (float) i3;
        float f5 = (float) i4;
        StringBuilder sb = new StringBuilder("addMapAnimation: ");
        sb.append(i9);
        sb.append(", 201, ");
        int i10 = i2;
        sb.append(i10);
        sb.append(", ");
        sb.append(f3);
        sb.append(", ");
        sb.append(f4);
        sb.append(", ");
        sb.append(f5);
        sb.append(", ");
        sb.append(i7);
        sb.append(", ");
        sb.append(i8);
        akq.b(sb.toString());
        if (i7 != -9999 || i8 != -9999 || f3 != -9999.0f || f4 != -9999.0f || f5 != -9999.0f) {
            akq.d.AddGroupAnimation(i9, 201, i10, f3, f4, f5, i7, i8, true);
            akq.e(akq.d.getBelongToRenderDeviceId(i9), 6);
        }
    }

    public final void M() {
        bry.a(true);
        akq akq = this.d;
        int deviceId = this.c.getDeviceId();
        akq.b("renderPause: ".concat(String.valueOf(deviceId)));
        akq.d.renderPauseIn(deviceId);
        int[] engineIDs = akq.d.getEngineIDs(deviceId);
        if (engineIDs != null && engineIDs.length > 0) {
            for (int t : engineIDs) {
                akq.t(t);
            }
        }
    }

    public final void N() {
        bry.a(false);
        this.d.m(this.c.getDeviceId());
    }

    public final boolean O() {
        return this.d.n(this.c.getDeviceId());
    }

    public final void P() {
        this.d.p(this.c.getDeviceId());
    }

    public final void Q() {
        this.d.q(this.c.getDeviceId());
    }

    public final void R() {
        this.d.r(this.c.getDeviceId());
    }

    public final void S() {
        this.d.r(this.c.getDeviceId());
    }

    public final void a(int i2, Bitmap bitmap, int i3, float f2, float f3) {
        akq akq = this.d;
        int i4 = this.e;
        akq.b("addMarkerRouteBoardBitmap: ".concat(String.valueOf(i4)));
        GLOverlayBundle<BaseMapOverlay<?, ?>> s = akq.s(i4);
        if (s != null) {
            akq.d.addOverlayTexture(i4, i2, i3, f2, f3, bitmap);
            s.addOverlayTextureItem(i2, i3, f2, f3, bitmap.getWidth(), bitmap.getHeight());
        }
    }

    public final void a(amh amh) {
        this.d.a(this.e, amh);
    }

    public final void a(int i2, int i3, float f2, float f3, float f4) {
        akq akq = this.d;
        int i4 = this.e;
        akq.b("setMapStatus: ".concat(String.valueOf(i4)));
        GLMapState newMapState = akq.d.getNewMapState(i4);
        if (newMapState != null) {
            newMapState.setCameraDegree(f4);
            newMapState.setMapAngle(f3);
            newMapState.setMapZoomer(f2);
            newMapState.setMapGeoCenter(i2, i3);
            akq.d.setMapState(i4, newMapState);
            akq.p(akq.d.getBelongToRenderDeviceId(i4));
        }
    }

    public final void i(boolean z) {
        akt z2 = this.d.z(this.e);
        if (-1 != z2.a && z2.b != null && z2.c != null) {
            StringBuilder sb = new StringBuilder("showLabel: ");
            sb.append(z2.a);
            sb.append(", ");
            sb.append(z);
            akq.b(sb.toString());
            z2.b.setSrvViewStateBoolValue(z2.a, 28, z);
            z2.c.p(z2.b.getBelongToRenderDeviceId(z2.a));
        }
    }

    public final void T() {
        akq akq = this.d;
        int i2 = this.e;
        akq.b("clearLabel: ".concat(String.valueOf(i2)));
        akq.b(i2, 28, false);
        akq.b(i2, 28, true);
        akq.p(akq.d.getBelongToRenderDeviceId(i2));
    }

    public final int j(boolean z) {
        akq akq = this.d;
        int i2 = this.e;
        StringBuilder sb = new StringBuilder("getMapIntMode: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(z);
        akq.b(sb.toString());
        int[] mapModeState = akq.d.getMapModeState(i2, !z);
        if (mapModeState == null) {
            return 0;
        }
        return mapModeState[0];
    }

    public final int k(boolean z) {
        akq akq = this.d;
        int i2 = this.e;
        StringBuilder sb = new StringBuilder("getMapIntModeState: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(z);
        akq.b(sb.toString());
        int[] mapModeState = akq.d.getMapModeState(i2, !z);
        if (mapModeState == null) {
            return 0;
        }
        return mapModeState[2];
    }

    public final float U() {
        return this.d.z(this.e).f;
    }

    public final void a(int i2, int i3, int i4, float f2, float f3, String str) {
        akq akq = this.d;
        int i5 = this.e;
        akq.b("addPoiFilter: ".concat(String.valueOf(i5)));
        akq.a(i5, i2, i3, i4, f2, f3, 3.0f, 20.0f, str, 1);
    }

    public final void a(int i2, int i3, int i4, float f2, float f3, String str, int i5) {
        akq akq = this.d;
        int i6 = this.e;
        akq.b("addPoiFilter 2: ".concat(String.valueOf(i6)));
        akq.a(i6, i2, i3, i4, f2, f3, 3.0f, 20.0f, str, i5);
    }

    public final void a(int i2, int i3, int i4, float f2, float f3, float f4, float f5, String str, int i5) {
        this.d.a(this.e, i2, i3, i4, f2, f3, f4, f5, str, i5);
    }

    public final void a(String str) {
        akq akq = this.d;
        int i2 = this.e;
        akq.b("removePoiFilter: ".concat(String.valueOf(i2)));
        akq.d.removePoiFilter(i2, str);
        akq.q(akq.d.getBelongToRenderDeviceId(i2));
    }

    public final void V() {
        akq akq = this.d;
        int i2 = this.e;
        akq.b("clearPoiFilter: ".concat(String.valueOf(i2)));
        akq.d.clearPoiFilter(i2);
        akq.q(akq.d.getBelongToRenderDeviceId(i2));
    }

    public final synchronized void a(int i2, int i3, int i4) {
        this.d.a(this.e, i2, i3, i4);
        if (i2 == 2) {
            a(true);
        }
        this.i.a((defpackage.agl.a<T>) new defpackage.agl.a<b>() {
            public final /* synthetic */ void onNotify(Object obj) {
                ((b) obj).a();
            }
        });
    }

    public final int l(boolean z) {
        akq akq = this.d;
        int i2 = this.e;
        StringBuilder sb = new StringBuilder("getMapIntTime: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(z);
        akq.b(sb.toString());
        int[] mapModeState = akq.d.getMapModeState(i2, !z);
        if (mapModeState == null) {
            return 0;
        }
        return mapModeState[1];
    }

    public final void W() {
        this.d.t(this.e);
    }

    public final void X() {
        this.d.u(this.e);
    }

    public final void b(Runnable runnable) {
        akq akq = this.d;
        int deviceId = this.c.getDeviceId();
        akq.b("postQueueEvent: ".concat(String.valueOf(runnable)));
        akq.a(deviceId, runnable);
    }

    public final void a(amo amo) {
        akq akq = this.d;
        akq.b("setNaviRouteBoardListener: ".concat(String.valueOf(amo)));
        akq.d.setNaviRouteBoardDataListener(amo);
    }

    public final PointF f(int i2, int i3) {
        return this.d.c(this.e, i2, i3);
    }

    public final PointF b(int i2, int i3, int i4) {
        akq akq = this.d;
        int i5 = this.e;
        akq.b("getP20ToScreenPointWithZ: ".concat(String.valueOf(i5)));
        PointF pointF = new PointF();
        akq.d.p20ToScreenPoint(i5, i2, i3, i4, pointF);
        return pointF;
    }

    public final void a(String[] strArr) {
        akq akq = this.d;
        int i2 = this.e;
        akq.b("setSearchedSubwayIds: ".concat(String.valueOf(i2)));
        akq.d.setSearchedSubwayIds(i2, strArr);
        akq.p(akq.d.getBelongToRenderDeviceId(i2));
    }

    public final void a(String str, int i2, String str2) {
        akq akq = this.d;
        int i3 = this.e;
        akq.b("setIndoorBuildingToBeActive: ".concat(String.valueOf(i3)));
        akq.d.setIndoorBuildingToBeActive(i3, str, i2, str2);
    }

    public final void a(defpackage.ami.a aVar) {
        akq akq = this.d;
        akq.b("setIndoorBuildingListener: ".concat(String.valueOf(aVar)));
        akq.d.setIndoorBuildingListener(aVar);
    }

    public final void a(amw amw) {
        akq akq = this.d;
        akq.b("setScenicListener: ".concat(String.valueOf(amw)));
        akq.d.setScenicListener(amw);
    }

    public final void f(int i2) {
        akq akq = this.d;
        int i3 = this.e;
        StringBuilder sb = new StringBuilder("setRenderListenerStatus: ");
        sb.append(i3);
        sb.append(", ");
        sb.append(i2);
        akq.b(sb.toString());
        akq.d.setRenderListenerStatus(i3, i2);
        akq.p(akq.d.getBelongToRenderDeviceId(i3));
    }

    public final void m(boolean z) {
        akq akq = this.d;
        int i2 = this.e;
        StringBuilder sb = new StringBuilder("setShowMapMask: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(z);
        akq.b(sb.toString());
        akq.d.setShowMask(i2, z);
        akq.p(akq.d.getBelongToRenderDeviceId(i2));
    }

    public final void g(int i2) {
        akq akq = this.d;
        int i3 = this.e;
        akq.b("setMapMaskColor: ".concat(String.valueOf(i3)));
        akq.d.setMaskColor(i3, i2);
        akq.p(akq.d.getBelongToRenderDeviceId(i3));
    }

    public final void Y() {
        akq akq = this.d;
        int i2 = this.e;
        akq.b("setMapHeatEnable: ".concat(String.valueOf(i2)));
        akq.d.setMapHeatEnable(i2, false);
        akq.p(akq.d.getBelongToRenderDeviceId(i2));
    }

    public final void a(WeatherType weatherType, Bitmap bitmap) {
        akq akq = this.d;
        int i2 = this.e;
        akq.b(String.format("startWeatherEffect: %s, %s", new Object[]{Integer.valueOf(i2), weatherType}));
        if (!akq.d.getSrvViewStateBoolValue(i2, 11)) {
            int C = akq.C(i2);
            akq.d.startWeatherEffect(i2, weatherType, bitmap, 0);
            akq.p(C);
        }
    }

    public final void n(boolean z) {
        akq akq = this.d;
        int i2 = this.e;
        akq.b(String.format("stopWeatherEffect: %s, %s", new Object[]{Integer.valueOf(i2), Boolean.valueOf(z)}));
        akq.d.stopWeatherEffect(i2, z);
    }

    public final WeatherAnimationState Z() {
        akq akq = this.d;
        return akq.d.getWeatherAnimationState(this.e);
    }

    public final void a(amk amk) {
        this.d.a(amk);
    }

    public final void b(amk amk) {
        this.d.b(amk);
    }

    public final void a(aml aml) {
        this.d.b(aml);
    }

    public final void a(amm amm) {
        this.c.setMapSurfaceListener(amm);
    }

    public final void a(amn amn) {
        this.c.setMapWidgetListener(amn);
    }

    public final GLOverlayBundle aa() {
        return this.d.s(this.e);
    }

    public final void ab() {
        this.d.d.clearAnimations(this.e, false);
    }

    public final void h(int i2) {
        this.d.d.clearAnimations(i2, false);
    }

    public final void i(int i2) {
        akq akq = this.d;
        int i3 = this.e;
        StringBuilder sb = new StringBuilder("setMapNeedForceDrawLabel: ");
        sb.append(i3);
        sb.append(", ");
        sb.append(i2);
        akq.b(sb.toString());
        if (i2 > 0) {
            akq.d.setBusinessDataParamater(i3, 66, 1, i2, 0, 0);
        } else {
            akq.d.setBusinessDataParamater(i3, 66, 0, 0, 0, 0);
        }
        akq.p(akq.d.getBelongToRenderDeviceId(i3));
    }

    public final void a(alv alv) {
        akt z = this.d.z(this.e);
        if (-1 != z.a && z.b != null && z.c != null) {
            StringBuilder sb = new StringBuilder("setOpenlayerParam: ");
            sb.append(z.a);
            akq.b(sb.toString());
            z.b.setOpenlayerParam(z.a, alv);
            z.c.p(z.b.getBelongToRenderDeviceId(z.a));
        }
    }

    public final alt a(alu alu) {
        akt z = this.d.z(this.e);
        StringBuilder sb = new StringBuilder("getOpenlayerParam: ");
        sb.append(z.a);
        akq.b(sb.toString());
        if (-1 == z.a || z.b == null || z.c == null) {
            return new alt();
        }
        return z.b.getOpenlayerParam(z.a, alu);
    }

    public final void ac() {
        if (this.d != null) {
            akq akq = this.d;
            akq.b((String) "uninit: ");
            synchronized (akq.f) {
                if (akq.e.intValue() == 1) {
                    if (akq.b != null) {
                        akq.b.a(akq.c.getApplicationContext(), false);
                        akq.b.a = null;
                        akq.b = null;
                    }
                    akq.d.DestroyGLThread();
                    akq.B = null;
                    akq.e = Integer.valueOf(0);
                }
            }
        }
    }

    public final int ad() {
        return this.f != null ? this.f.j() : this.e;
    }

    @Deprecated
    public final int o(boolean z) {
        return k(z);
    }

    @Deprecated
    public final int ae() {
        return l(false);
    }

    @Deprecated
    public final int p(boolean z) {
        return j(z);
    }

    public final boolean j(int i2) {
        akq akq = this.d;
        akq.b("getSimple3DEnable: ".concat(String.valueOf(i2)));
        return akq.b(i2, 29);
    }

    public final boolean k(int i2) {
        akq akq = this.d;
        akq.b("isSimple3DShow: ".concat(String.valueOf(i2)));
        if (akq.d != null) {
            return akq.d.isSimple3DShow(i2);
        }
        return false;
    }

    public final void a(int i2, boolean z) {
        akq akq = this.d;
        StringBuilder sb = new StringBuilder("setSimple3DEnable: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(z);
        akq.b(sb.toString());
        akq.b(i2, 29, z);
        akq.p(akq.d.getBelongToRenderDeviceId(i2));
    }

    public final void g(int i2, int i3) {
        akq akq = this.d;
        StringBuilder sb = new StringBuilder("setGestureCenterType: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(i3);
        akq.b(sb.toString());
        akq.d.setGestureCenterType(i2, i3);
    }

    public final boolean c(int i2, int i3, int i4, int i5) {
        akq akq = this.d;
        int i6 = this.e;
        akq.b("doMapDataControl: ".concat(String.valueOf(i6)));
        boolean doMapDataControl = akq.d != null ? akq.d.doMapDataControl(i6, i2, i3, i4, i5) : false;
        if (doMapDataControl) {
            akq.p(akq.d.getBelongToRenderDeviceId(i6));
        }
        return doMapDataControl;
    }

    public final void a(int i2, Label3rd[] label3rdArr, boolean z) {
        akq akq = this.d;
        int i3 = this.e;
        akq.b("addLabels3rd: ".concat(String.valueOf(i3)));
        akq.d.addLabels3rd(i3, i2, label3rdArr, z);
        akq.p(akq.d.getBelongToRenderDeviceId(i3));
    }

    public final void b(int i2, boolean z) {
        akq akq = this.d;
        int i3 = this.e;
        akq.b("clearLabels3rd: ".concat(String.valueOf(i3)));
        akq.d.clearLabels3rd(i3, i2, z);
        akq.p(akq.d.getBelongToRenderDeviceId(i3));
    }

    public final void af() {
        akq akq = this.d;
        int i2 = this.e;
        akq.b("setBldAndModelVisibility: ".concat(String.valueOf(i2)));
        akq.b(i2, 23, true);
        akq.p(akq.d.getBelongToRenderDeviceId(i2));
    }

    public final boolean ag() {
        akq akq = this.d;
        int i2 = this.e;
        akq.b("isShowLandBuild: ".concat(String.valueOf(i2)));
        if (akq.d != null) {
            return akq.d.getIsProcessBuildingMark(i2);
        }
        return false;
    }

    public final boolean ah() {
        akq akq = this.d;
        int i2 = this.e;
        akq.b("isShowLandBuildingPoi: ".concat(String.valueOf(i2)));
        if (akq.d != null) {
            return akq.d.isShowLandMarkBuildingPoi(i2);
        }
        return false;
    }

    public final void q(boolean z) {
        akt z2 = this.d.z(this.e);
        if (-1 != z2.a && z2.b != null && z2.c != null) {
            StringBuilder sb = new StringBuilder("setBuildTextureVisibility: ");
            sb.append(z2.a);
            akq.b(sb.toString());
            z2.c.b(z2.a, 24, z);
            z2.c.p(z2.b.getBelongToRenderDeviceId(z2.a));
        }
    }

    public final boolean ai() {
        akt z = this.d.z(this.e);
        boolean b2 = (-1 == z.a || z.b == null || z.c == null) ? false : z.c.b(z.a, 24);
        StringBuilder sb = new StringBuilder("isShowBuildTexture: ");
        sb.append(z.a);
        sb.append(", ");
        sb.append(b2);
        akq.b(sb.toString());
        return b2;
    }

    public final void r(boolean z) {
        akq akq = this.d;
        int i2 = this.e;
        StringBuilder sb = new StringBuilder("setScenicHDMapEnable: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(z);
        akq.b(sb.toString());
        akq.d.setScenicHDMapEnable(i2, z);
        akq.p(akq.d.getBelongToRenderDeviceId(i2));
    }

    public final void h(float f2) {
        akr akr = this.d.z(this.e).d;
        if (-1 != akr.a && akr.b != null && akr.c != null) {
            StringBuilder sb = new StringBuilder("setMapMaxLevel: ");
            sb.append(akr.a);
            sb.append(", ");
            sb.append(f2);
            akq.b(sb.toString());
            akr.b.setMapMaxZoomer(akr.a, f2);
            akr.c.p(akr.b.getBelongToRenderDeviceId(akr.a));
        }
    }

    public final void i(float f2) {
        akr akr = this.d.z(this.e).d;
        if (-1 != akr.a && akr.b != null && akr.c != null) {
            StringBuilder sb = new StringBuilder("setMapMinLevel: ");
            sb.append(akr.a);
            sb.append(", ");
            sb.append(f2);
            akq.b(sb.toString());
            akr.b.setMapMinZoomer(akr.a, f2);
            akr.c.p(akr.b.getBelongToRenderDeviceId(akr.a));
        }
    }

    public final void a(alp alp, alp alp2) {
        akq akq = this.d;
        akq.d.setMapMovableArea(this.e, alp, alp2);
    }

    public final void a(byte[] bArr) {
        akt z = this.d.z(this.e);
        if (-1 != z.a && z.b != null && z.c != null) {
            StringBuilder sb = new StringBuilder("appendOpenLayer: ");
            sb.append(z.a);
            akq.b(sb.toString());
            z.b.appendOpenLayer(z.a, bArr);
            z.c.p(z.b.getBelongToRenderDeviceId(z.a));
        }
    }

    public final void l(int i2) {
        akt z = this.d.z(this.e);
        if (-1 != z.a && z.b != null && z.c != null) {
            StringBuilder sb = new StringBuilder("deleteOpenLayer: ");
            sb.append(z.a);
            akq.b(sb.toString());
            z.b.deleteOpenLayer(z.a, i2);
            z.c.p(z.b.getBelongToRenderDeviceId(z.a));
        }
    }

    public final void aj() {
        akt z = this.d.z(this.e);
        if (-1 != z.a && z.b != null && z.c != null) {
            StringBuilder sb = new StringBuilder("setOpenLayerVisibility: ");
            sb.append(z.a);
            akq.b(sb.toString());
            z.b.setBusinessDataParamater(z.a, 60, 1, 0, 0, 0);
            z.c.p(z.b.getBelongToRenderDeviceId(z.a));
        }
    }

    public final void c(int i2, boolean z) {
        akt z2 = this.d.z(this.e);
        if (-1 != z2.a && z2.b != null && z2.c != null) {
            StringBuilder sb = new StringBuilder("setOpenLayerVisibility 2: ");
            sb.append(z2.a);
            akq.b(sb.toString());
            z2.b.setBusinessDataParamater(z2.a, 60, z ? 1 : 0, i2, 0, 0);
            z2.c.p(z2.b.getBelongToRenderDeviceId(z2.a));
        }
    }

    public final String ak() {
        return this.d.c();
    }

    public final void s(boolean z) {
        this.d.d(this.e, z);
    }

    public final void c(Runnable runnable) {
        this.d.a(this.c.getDeviceId(), runnable);
    }

    public final void t(boolean z) {
        akq akq = this.d;
        int i2 = this.e;
        StringBuilder sb = new StringBuilder("setColorBlindStatus: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(z);
        akq.b(sb.toString());
        if (akq.d != null) {
            akq.d.setControllerStateBoolValue(i2, 3, z);
        }
        akq.o(akq.d.getBelongToRenderDeviceId(i2));
    }

    public final void a(int i2, int i3, int i4, int i5, int i6) {
        this.d.a(i2, i3, i4, i5, i6);
        if (i3 == 2) {
            a(true);
        }
        this.i.a((defpackage.agl.a<T>) new defpackage.agl.a<b>() {
            public final /* synthetic */ void onNotify(Object obj) {
                ((b) obj).b();
            }
        });
    }

    public final long an() {
        akq akq = this.d;
        return akq.d.getTotalRenderFrames(this.c.getDeviceId());
    }

    public final int m(int i2) {
        return this.d.C(i2);
    }

    public final void a(b bVar) {
        this.i.a(bVar);
    }

    public final void b(b bVar) {
        this.i.b(bVar);
    }

    public final void u(boolean z) {
        akq akq = this.d;
        int i2 = this.e;
        StringBuilder sb = new StringBuilder("setDiffEnable: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(z);
        akq.b(sb.toString());
        akq.d.setDiffEnable(i2, z);
        akq.p(akq.d.getBelongToRenderDeviceId(i2));
    }

    public final VMapSurface ao() {
        return this.g;
    }

    public final VMapPage ap() {
        return this.h;
    }

    public final Context d() {
        return ((View) this.c).getContext();
    }

    public final void d(int i2) {
        ((View) this.c).setVisibility(i2);
    }

    public final int al() {
        return ((View) this.c).getWidth();
    }

    public final int am() {
        return ((View) this.c).getHeight();
    }
}
