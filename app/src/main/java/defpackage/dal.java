package defpackage;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.ae.ajx.map.AjxMapViewEventReceiver;
import com.autonavi.ae.gmap.AMapSurface;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.common.impl.Locator.b;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.dice.NaviEngine;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.jni.ae.gmap.glinterface.MapEngineInitParam;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle.GLAmapFocusHits;
import com.autonavi.jni.eyrie.amap.maps.MapViewManager;
import com.autonavi.jni.eyrie.amap.tbt.NaviManager;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.core.MapViewUtil;
import com.autonavi.map.core.OverlayManager;
import com.autonavi.minimap.base.overlay.LineOverlay;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.RouteOverlay;
import com.autonavi.minimap.bundle.evaluate.api.IEvaluateOperationManager;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.sdk.location.LocationInstrument;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: dal reason: default package */
/* compiled from: MapManagerImpl */
public class dal implements bro {
    private static volatile Boolean F;
    private boolean A = false;
    private Context B;
    private Handler C = new Handler(Looper.getMainLooper());
    private a D = null;
    private ImageView E;
    private long G = 0;
    private long H = 0;
    private volatile boolean I = false;
    /* access modifiers changed from: private */
    public AtomicBoolean J = new AtomicBoolean(false);
    private Runnable K = new Runnable() {
        public final void run() {
            dal.this.J.compareAndSet(true, false);
        }
    };
    private final int a = 2;
    private OverlayManager b;
    private brx c;
    private bty d;
    /* access modifiers changed from: private */
    public List<btw> e = new LinkedList();
    private List<bts> f = new LinkedList();
    private amk g = null;
    /* access modifiers changed from: private */
    public Map<Integer, btv> h = new HashMap();
    private List<defpackage.bro.a> i = new ArrayList();
    private defpackage.ami.a j;
    private List<defpackage.ami.a> k = new LinkedList();
    private amn l;
    private long m = 0;
    private long n = 0;
    private long o = 0;
    private long p = 0;
    private long q = 0;
    private long r = 0;
    private long s = 0;
    private long t = 0;
    private long u = 0;
    private long v = 0;
    private long w = 0;
    private long x = 0;
    private long y = 0;
    private long z = 0;

    /* renamed from: dal$a */
    /* compiled from: MapManagerImpl */
    class a extends defpackage.ahl.a<Void> {
        volatile boolean a;
        private int c;

        public final void onError(Throwable th) {
        }

        public final /* bridge */ /* synthetic */ void onFinished(Object obj) {
        }

        a(int i) {
            this.c = i;
        }

        public final /* synthetic */ Object doBackground() throws Exception {
            Thread.sleep(100);
            if (!this.a) {
                for (btw onMapMotionStop : dal.this.e) {
                    onMapMotionStop.onMapMotionStop();
                }
                bdl bdl = (bdl) defpackage.esb.a.a.a(bdl.class);
                if (bdl != null) {
                    bdl.a();
                }
                btv btv = (btv) dal.this.h.get(Integer.valueOf(this.c));
                if (btv != null) {
                    btv.onMapMotionStop();
                }
            }
            return null;
        }
    }

    public void beforeDrawFrame(int i2, GLMapState gLMapState) {
    }

    public int getMapMode() {
        return 2;
    }

    public boolean onClick(int i2, float f2, float f3) {
        return false;
    }

    public boolean onDoubleClick(int i2, float f2, float f3) {
        return false;
    }

    public boolean onLongPress(int i2, float f2, float f3) {
        return false;
    }

    public void onMapTipClear(int i2) {
    }

    public boolean onMontionFinish(int i2) {
        return false;
    }

    public boolean onMontionStart(int i2, float f2, float f3) {
        return false;
    }

    public void onScreenShotFinished(int i2, long j2) {
    }

    public void onScreenShotFinished(int i2, Bitmap bitmap) {
    }

    public void onScreenShotFinished(int i2, String str) {
    }

    public boolean onTouchEvent(int i2, MotionEvent motionEvent) {
        return false;
    }

    public void init(Context context, akq akq, AMapSurface aMapSurface, ImageView imageView) {
        this.B = context;
        this.c = new brx(akq);
        this.E = imageView;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int i2 = displayMetrics.widthPixels;
        int i3 = displayMetrics.heightPixels;
        Rect rect = new Rect(0, 0, i2, i3);
        MapEngineInitParam MainMapInitParam = MapEngineInitParam.MainMapInitParam();
        brx brx = this.c;
        brw brw = new brw(brx.b, aMapSurface, MainMapInitParam, rect, i2, i3);
        brx.a.put(Integer.valueOf(brw.j()), brw);
        brx.d = brw.j();
        brw.a((bty) brw);
        if (brw.c() != null) {
            brw.c().K = brx.a;
        }
        this.d = brw;
        this.c.c = aMapSurface;
        this.d.a((defpackage.ami.a) this);
        this.d.a((amn) this);
        awb awb = (awb) defpackage.esb.a.a.a(awb.class);
        if (awb != null) {
            awb.a(context, this.d.ad());
        }
        bty bty = this.d;
        AMapLog.i("Eyrie", "initEyrie... It's B plan.");
        vk.a();
        vk.a(bty.ad());
        setEyrieMapGestureListener(vk.a().a, aMapSurface);
        drm.a((defpackage.drn.a) vk.a().b);
        this.b = new OverlayManager(this, this.d, context, this.f);
        LocationInstrument.getInstance().setMapRect(new b() {
            public final GeoPoint a() {
                return GeoPoint.glGeoPoint2GeoPoint(dal.this.getMapView().n());
            }
        });
        IEvaluateOperationManager iEvaluateOperationManager = (IEvaluateOperationManager) ank.a(IEvaluateOperationManager.class);
        if (iEvaluateOperationManager != null) {
            this.g = iEvaluateOperationManager.a();
        }
    }

    public void resetMapView(Bitmap bitmap) {
        if (this.E != null) {
            this.E.setVisibility(bitmap != null ? 0 : 8);
            LayoutParams layoutParams = this.E.getLayoutParams();
            layoutParams.width = ags.a(this.B).width();
            this.E.setLayoutParams(layoutParams);
            this.E.setImageBitmap(bitmap);
        }
    }

    @NonNull
    public IOverlayManager getOverlayManager() {
        return this.b;
    }

    public void release() {
        brn brn = (brn) ank.a(brn.class);
        if (brn != null) {
            brn.a();
        }
        if (this.b != null) {
            cdx gpsLayer = this.b.getGpsLayer();
            MapViewManager.removeTextureLoader(gpsLayer.g);
            NaviManager.unregisterEventReceiver(gpsLayer.f);
        }
        xp xpVar = (xp) defpackage.esb.a.a.a(xp.class);
        if (xpVar != null) {
            xpVar.e();
        }
        if (!(this.d == null || this.d.b() == null)) {
            setEyrieMapGestureListener(null, this.d.b());
        }
        drm.b((defpackage.drn.a) vk.a().b);
        NaviEngine.uninitNaviManager();
        vk.a();
        vk.b();
        AMapLog.info("basemap.maphome", "NaviEngine", "uninitNaviManager()");
        LocationInstrument.getInstance().setMapRect(null);
        if (this.d != null) {
            this.d.b((amk) this);
            this.d.a((aml) this);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0058  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0060  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void restoreMapStateWithouMapMode() {
        /*
            r8 = this;
            com.amap.bundle.mapstorage.MapSharePreference r0 = new com.amap.bundle.mapstorage.MapSharePreference
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r1 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences
            r0.<init>(r1)
            java.lang.String r1 = "traffic"
            boolean r1 = r0.contains(r1)
            java.lang.String r2 = "traffic"
            r3 = 1
            boolean r2 = r0.getBooleanValue(r2, r3)
            bty r4 = r8.getMapView()
            r4.b(r2)
            if (r1 != 0) goto L_0x0022
            java.lang.String r1 = "traffic"
            r0.putBooleanValue(r1, r2)
        L_0x0022:
            java.lang.String r1 = "blind_mode_status"
            r2 = 0
            boolean r0 = r0.getBooleanValue(r1, r2)
            bty r1 = r8.getMapView()
            r1.t(r0)
            bim r0 = defpackage.bim.aa()
            java.lang.String r1 = "101"
            int r0 = r0.l(r1)
            r1 = 2
            if (r0 != 0) goto L_0x0041
            r0 = 1
        L_0x003e:
            r4 = 0
        L_0x003f:
            r5 = 0
            goto L_0x004e
        L_0x0041:
            if (r0 != r3) goto L_0x0046
            r0 = 0
            r4 = 1
            goto L_0x003f
        L_0x0046:
            if (r0 != r1) goto L_0x004c
            r0 = 0
            r4 = 0
            r5 = 1
            goto L_0x004e
        L_0x004c:
            r0 = 0
            goto L_0x003e
        L_0x004e:
            bty r6 = r8.getMapView()
            int r7 = r6.k(r2)
            if (r0 == 0) goto L_0x0060
            int r0 = r6.l(r2)
            r6.a(r2, r0, r7)
            return
        L_0x0060:
            if (r4 == 0) goto L_0x0066
            r6.a(r3, r2, r7)
            return
        L_0x0066:
            if (r5 == 0) goto L_0x006b
            r6.a(r1, r2, r7)
        L_0x006b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dal.restoreMapStateWithouMapMode():void");
    }

    public void saveMapState() {
        Editor edit = new MapSharePreference(SharePreferenceName.SharedPreferences).edit();
        edit.putInt("X", getMapView().n().x);
        edit.putInt("Y", getMapView().n().y);
        if (LocationInstrument.getInstance().getLatestPosition(5) != null) {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            if (latestPosition != null) {
                edit.putInt("myX", latestPosition.x);
                edit.putInt("myY", latestPosition.y);
            }
        }
        edit.putFloat("PRESISE_ZOOM_LEVEL", getMapView().v());
        edit.putFloat("D", getMapView().I());
        edit.putFloat("C", getMapView().J());
        edit.apply();
    }

    public void onMapTipInfo(int i2, String str) {
        if (!TextUtils.isEmpty(str)) {
            ToastHelper.showToast(str);
        }
    }

    public void onUserMapTouchEvent(int i2, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 || motionEvent.getAction() == 1) {
            AjxMapViewEventReceiver.onMapTouchEventS(i2, motionEvent.getX(), motionEvent.getY(), motionEvent.getAction());
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (this.g != null) {
            this.g.onUserMapTouchEvent(i2, motionEvent);
        }
        this.m = System.currentTimeMillis() - currentTimeMillis;
        StringBuilder sb = new StringBuilder("mOnUserMapTouchEvent use time = ");
        sb.append(this.m);
        AMapLog.e("MapManager", sb.toString());
        Iterator<bts> it = this.f.iterator();
        while (it.hasNext()) {
            it.next();
        }
        for (btw onMapTouchEvent : this.e) {
            onMapTouchEvent.onMapTouchEvent(motionEvent);
        }
        btv btv = this.h.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onMapTouchEvent(motionEvent);
        }
    }

    public void onShowPress(int i2, MotionEvent motionEvent) {
        GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(getMapView().c((int) motionEvent.getX(), (int) motionEvent.getY()));
        for (btw onMapShowPress : this.e) {
            onMapShowPress.onMapShowPress(motionEvent, glGeoPoint2GeoPoint);
        }
    }

    public void onLongPress(int i2, MotionEvent motionEvent) {
        AjxMapViewEventReceiver.onLongPressS(i2, motionEvent.getX(), motionEvent.getY());
        long currentTimeMillis = System.currentTimeMillis();
        if (this.g != null) {
            this.g.onLongPress(i2, motionEvent);
        }
        this.p = System.currentTimeMillis() - currentTimeMillis;
        StringBuilder sb = new StringBuilder("mOnLongPress use time = ");
        sb.append(this.p);
        AMapLog.e("MapManager", sb.toString());
        GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(getMapView().c((int) motionEvent.getX(), (int) motionEvent.getY()));
        for (btw onMapLongPress : this.e) {
            onMapLongPress.onMapLongPress(motionEvent, glGeoPoint2GeoPoint);
        }
        btv btv = this.h.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onMapLongPress(motionEvent, glGeoPoint2GeoPoint);
        }
    }

    public void onDoubleTap(int i2, MotionEvent motionEvent) {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.g != null) {
            this.g.onDoubleTap(i2, motionEvent);
        }
        this.u = System.currentTimeMillis() - currentTimeMillis;
        StringBuilder sb = new StringBuilder("mOnDoubleTap use time = ");
        sb.append(this.u);
        AMapLog.e("MapManager", sb.toString());
        GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(getMapView().c((int) motionEvent.getX(), (int) motionEvent.getY()));
        for (btw onMapDoubleClick : this.e) {
            onMapDoubleClick.onMapDoubleClick(motionEvent, glGeoPoint2GeoPoint);
        }
        btv btv = this.h.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onDoubleTap();
        }
    }

    public void onMoveBegin(int i2, MotionEvent motionEvent) {
        AjxMapViewEventReceiver.onEngineActionGestureS(i2, 3, 1, false);
        long currentTimeMillis = System.currentTimeMillis();
        if (this.g != null) {
            this.g.onMoveBegin(i2, motionEvent);
        }
        this.v = System.currentTimeMillis() - currentTimeMillis;
        StringBuilder sb = new StringBuilder("mOnMoveBegin use time = ");
        sb.append(this.v);
        AMapLog.e("MapManager", sb.toString());
        btv btv = this.h.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onMoveBegin();
        }
    }

    public void onZoomOutTap(int i2, MotionEvent motionEvent) {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.g != null) {
            this.g.onZoomOutTap(i2, motionEvent);
        }
        this.w = System.currentTimeMillis() - currentTimeMillis;
        StringBuilder sb = new StringBuilder("mOnZoomOutTap use time = ");
        sb.append(this.w);
        AMapLog.e("MapManager", sb.toString());
        btv btv = this.h.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onZoomOutTap();
        }
    }

    public void onScaleRotateBegin(int i2, MotionEvent motionEvent) {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.g != null) {
            this.g.onScaleRotateBegin(i2, motionEvent);
        }
        this.x = System.currentTimeMillis() - currentTimeMillis;
        StringBuilder sb = new StringBuilder("mOnScaleRotateBegin use time = ");
        sb.append(this.x);
        AMapLog.e("MapManager", sb.toString());
        btv btv = this.h.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onScaleRotateBegin();
        }
    }

    public void onHoveBegin(int i2, MotionEvent motionEvent) {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.g != null) {
            this.g.onHoveBegin(i2, motionEvent);
        }
        this.y = System.currentTimeMillis() - currentTimeMillis;
        StringBuilder sb = new StringBuilder("mOnHoveBegin use time = ");
        sb.append(this.y);
        AMapLog.e("MapManager", sb.toString());
        btv btv = this.h.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onHoveBegin();
        }
    }

    public boolean onSingleTapUp(int i2, MotionEvent motionEvent) {
        AjxMapViewEventReceiver.onSingleTapUpS(i2, motionEvent.getX(), motionEvent.getY());
        long currentTimeMillis = System.currentTimeMillis();
        if (this.g != null) {
            this.g.onSingleTapUp(i2, motionEvent);
        }
        this.z = System.currentTimeMillis() - currentTimeMillis;
        StringBuilder sb = new StringBuilder("mOnSingleTapUp use time = ");
        sb.append(this.z);
        AMapLog.e("MapManager", sb.toString());
        awo awo = (awo) defpackage.esb.a.a.a(awo.class);
        boolean z2 = false;
        boolean a2 = awo != null ? awo.a() : false;
        if ((this.b.getMapPointOverlay() != null && this.b.getMapPointOverlay().isVisible() && this.b.getMapPointOverlay().isClickable()) || a2) {
            ArrayList<als> d2 = getMapView().d((int) motionEvent.getX(), (int) motionEvent.getY());
            if (d2 != null && d2.size() > 0) {
                z2 = true;
            }
            if (z2) {
                for (btw onLabelClick : this.e) {
                    onLabelClick.onLabelClick(d2);
                }
                btv btv = this.h.get(Integer.valueOf(i2));
                if (btv != null) {
                    btv.onLabelClick(d2);
                }
            }
        }
        GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(getMapView().c((int) motionEvent.getX(), (int) motionEvent.getY()));
        if (i2 == brx.d) {
            for (btw onMapSingleClick : this.e) {
                onMapSingleClick.onMapSingleClick(motionEvent, glGeoPoint2GeoPoint);
            }
        } else {
            for (btw onMapSingleClick2 : this.e) {
                onMapSingleClick2.onMapSingleClick(i2, motionEvent, glGeoPoint2GeoPoint);
            }
        }
        btv btv2 = this.h.get(Integer.valueOf(i2));
        if (btv2 != null) {
            btv2.onMapSingleClick(motionEvent, glGeoPoint2GeoPoint);
        }
        return z2;
    }

    public boolean onFling(int i2, MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
        Iterator<bts> it = this.f.iterator();
        while (it.hasNext()) {
            it.next();
        }
        return true;
    }

    public void onMapSizeChange(int i2) {
        for (btw onMapSizeChange : this.e) {
            onMapSizeChange.onMapSizeChange();
        }
    }

    public void onMapLevelChange(final int i2, final boolean z2) {
        AjxMapViewEventReceiver.onMapLevelChangeS(i2, z2);
        long currentTimeMillis = System.currentTimeMillis();
        if (this.g != null) {
            this.g.onMapLevelChange(i2, z2);
        }
        this.n = System.currentTimeMillis() - currentTimeMillis;
        StringBuilder sb = new StringBuilder("mOnMapLevelChange use time = ");
        sb.append(this.n);
        AMapLog.e("MapManager", sb.toString());
        for (btw onMapLevelChange : this.e) {
            onMapLevelChange.onMapLevelChange(z2);
        }
        btv btv = this.h.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onMapLevelChange(z2);
        }
        for (final bts next : this.f) {
            this.C.post(new Runnable() {
                public final void run() {
                    next.a();
                }
            });
        }
    }

    public void onEngineVisible(int i2, boolean z2) {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.g != null) {
            this.g.onEngineVisible(i2, z2);
        }
        this.q = System.currentTimeMillis() - currentTimeMillis;
        StringBuilder sb = new StringBuilder("mOnEngineVisible use time = ");
        sb.append(this.q);
        AMapLog.e("MapManager", sb.toString());
        btv btv = this.h.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onEngineVisible(i2, z2);
        }
    }

    public void updateLockMapAngleState(float f2) {
        if (getMapView() != null) {
            MapViewUtil.updateLockMapAngleState(getMapView(), f2);
        }
    }

    public void updateLockMapAngleState(bty bty, float f2) {
        if (bty != null) {
            MapViewUtil.updateLockMapAngleState(bty, f2);
        }
    }

    public void setCameraDegree(int i2) {
        if (getMapView() != null) {
            bty mapView = getMapView();
            if (mapView != null) {
                float f2 = (float) i2;
                mapView.g(f2);
                MapViewUtil.updateLockMapAngleState(mapView, f2);
            }
        }
    }

    public void onMotionFinished(int i2, int i3) {
        AjxMapViewEventReceiver.onMotionFinishedS(i2, i3);
        long currentTimeMillis = System.currentTimeMillis();
        if (this.g != null) {
            this.g.onMotionFinished(i2, i3);
        }
        this.o = System.currentTimeMillis() - currentTimeMillis;
        StringBuilder sb = new StringBuilder("mOnMotionFinished use time = ");
        sb.append(this.o);
        AMapLog.e("MapManager", sb.toString());
        MapViewUtil.updateLockMapAngleState(getMapView(), (float) MapViewUtil.INVALID_CAMERA_DEGREE);
        if (this.D != null) {
            this.D.a = true;
            this.D = null;
        }
        this.D = new a(i2);
        ahl.b(this.D);
        for (btw onMapMotionFinish : this.e) {
            onMapMotionFinish.onMapMotionFinish();
        }
    }

    public void onPointOverlayClick(int i2, GLAmapFocusHits gLAmapFocusHits) {
        BaseMapOverlay a2 = a(i2, gLAmapFocusHits.mOverlayHashCode);
        if (a2 != null && a2.getItem((int) gLAmapFocusHits.mHitedIndex) != null) {
            Iterator<bts> it = this.f.iterator();
            while (it.hasNext()) {
                it.next();
            }
            if (a2 instanceof PointOverlay) {
                PointOverlay pointOverlay = (PointOverlay) a2;
                if (pointOverlay.isVisible() && pointOverlay.isClickable()) {
                    pointOverlay.onPointOverlayClick((int) gLAmapFocusHits.mHitedIndex);
                }
            }
            for (btw onPointOverlayClick : this.e) {
                onPointOverlayClick.onPointOverlayClick(gLAmapFocusHits.mOverlayHashCode, (int) gLAmapFocusHits.mHitedIndex);
            }
            btv btv = this.h.get(Integer.valueOf(i2));
            if (btv != null) {
                btv.onPointOverlayClick(gLAmapFocusHits.mOverlayHashCode, (int) gLAmapFocusHits.mHitedIndex);
            }
        }
    }

    public void onLineOverlayClick(int i2, GLAmapFocusHits gLAmapFocusHits) {
        BaseMapOverlay a2 = a(i2, gLAmapFocusHits.mOverlayHashCode);
        if (a2 != null) {
            Iterator<bts> it = this.f.iterator();
            while (it.hasNext()) {
                it.next();
            }
            if (a2 instanceof LineOverlay) {
                LineOverlay lineOverlay = (LineOverlay) a2;
                if (lineOverlay.isVisible() && lineOverlay.isClickable()) {
                    lineOverlay.onLineOverlayClick(gLAmapFocusHits.mOverlayHashCode);
                }
            } else if (a2 instanceof RouteOverlay) {
                RouteOverlay routeOverlay = (RouteOverlay) a2;
                if (routeOverlay.isVisible() && routeOverlay.isClickable()) {
                    routeOverlay.onLineOverlayClick(gLAmapFocusHits.mOverlayHashCode);
                }
            }
            for (btw onLineOverlayClick : this.e) {
                onLineOverlayClick.onLineOverlayClick(gLAmapFocusHits.mOverlayHashCode);
            }
            btv btv = this.h.get(Integer.valueOf(i2));
            if (btv != null) {
                btv.onLineOverlayClick(gLAmapFocusHits.mOverlayHashCode);
            }
        }
    }

    public void onNoFeatureClick(int i2) {
        for (btw onNonFeatureClick : this.e) {
            onNonFeatureClick.onNonFeatureClick();
        }
        btv btv = this.h.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onFocusClear();
        }
    }

    public boolean onBlankClick(int i2) {
        for (btw onBlankClick : this.e) {
            onBlankClick.onBlankClick();
        }
        btv btv = this.h.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onBlankClick();
        }
        return false;
    }

    public boolean onNoBlankClick(int i2) {
        for (btw onNoBlankClick : this.e) {
            onNoBlankClick.onNoBlankClick();
        }
        btv btv = this.h.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onNoBlankClick();
        }
        return false;
    }

    public void onHorizontalMove(int i2, float f2) {
        for (btw onHorizontalMove : this.e) {
            onHorizontalMove.onHorizontalMove(f2);
        }
    }

    public void onHorizontalMoveEnd(int i2) {
        for (btw onHorizontalMoveEnd : this.e) {
            onHorizontalMoveEnd.onHorizontalMoveEnd();
        }
    }

    public void onOfflineMap(int i2, final String str, int i3) {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.B.getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                ToastHelper.showToast("该城市离线文件损坏，请重新下载");
            } else {
                ToastHelper.showToast("该城市离线文件损坏，正在加载在线地图");
            }
        }
        ahm.a(new Runnable() {
            public final void run() {
                IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
                if (iOfflineManager != null) {
                    iOfflineManager.notifyCityDataError(str);
                }
            }
        });
    }

    public void onRealCityAnimateFinish(int i2) {
        Iterator<bts> it = this.f.iterator();
        while (it.hasNext()) {
            it.next();
        }
    }

    public void onMapAnimationFinished(int i2, int i3) {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.g != null) {
            this.g.onMapAnimationFinished(i2, i3);
        }
        this.t = System.currentTimeMillis() - currentTimeMillis;
        StringBuilder sb = new StringBuilder("mOnMapAnimationFinished(int,int) use time = ");
        sb.append(this.t);
        AMapLog.e("MapManager", sb.toString());
        for (btw onMapAnimationFinished : this.e) {
            onMapAnimationFinished.onMapAnimationFinished(i3);
        }
        btv btv = this.h.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onMapAnimationFinished(i3);
        }
    }

    public void onMapAnimationFinished(int i2, aln aln) {
        AjxMapViewEventReceiver.onMapAnimationFinishedS(i2, aln.a, aln.b, aln.c);
        long currentTimeMillis = System.currentTimeMillis();
        if (this.g != null) {
            this.g.onMapAnimationFinished(i2, aln);
        }
        this.s = System.currentTimeMillis() - currentTimeMillis;
        StringBuilder sb = new StringBuilder("mOnMapAnimationFinished(int,object) use time = ");
        sb.append(this.s);
        AMapLog.e("MapManager", sb.toString());
        for (btw onMapAnimationFinished : this.e) {
            onMapAnimationFinished.onMapAnimationFinished(aln);
        }
        btv btv = this.h.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onMapAnimationFinished(aln);
        }
    }

    public void onSelectSubWayActive(int i2, byte[] bArr) {
        List<Long> parseSubWayActiveIds = awc.parseSubWayActiveIds(bArr);
        for (btw onSelectSubWayActive : this.e) {
            onSelectSubWayActive.onSelectSubWayActive(parseSubWayActiveIds);
        }
        btv btv = this.h.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onSelectSubWayActive(parseSubWayActiveIds);
        }
    }

    public void onMapRenderCompleted(int i2) {
        for (btw onMapRenderCompleted : this.e) {
            onMapRenderCompleted.onMapRenderCompleted();
        }
        btv btv = this.h.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onMapRenderCompleted();
        }
    }

    private BaseMapOverlay a(int i2, long j2) {
        int a2 = getMapView(i2).F().a();
        for (int i3 = 0; i3 < a2; i3++) {
            BaseMapOverlay a3 = getMapView(i2).F().a(i3);
            if (a3 == null || ((long) a3.hashCode()) == j2) {
                return a3;
            }
        }
        return null;
    }

    public brx getMapViewManager() {
        return this.c;
    }

    public bty getMapView() {
        return this.c.a.get(Integer.valueOf(brx.d));
    }

    public bty getMapView(int i2) {
        return this.c.a(i2);
    }

    public boolean isMapSurfaceCreated() {
        return this.A;
    }

    public void setMapSurfaceCreated(boolean z2) {
        this.A = z2;
    }

    public void notifyMapModeChange(int i2) {
        for (defpackage.bro.a a2 : this.i) {
            a2.a();
        }
    }

    public void addMapModeChangeListener(defpackage.bro.a aVar) {
        this.i.add(aVar);
    }

    public void removeMapModeChangeListener(defpackage.bro.a aVar) {
        this.i.remove(aVar);
    }

    public void renderPauseDelay() {
        if (!this.I) {
            bry.a(true);
            this.I = true;
        }
    }

    public void indoorBuildingActivity(int i2, ami ami) {
        if (this.j != null) {
            this.j.indoorBuildingActivity(i2, ami);
        }
        if (this.k != null) {
            for (defpackage.ami.a indoorBuildingActivity : this.k) {
                indoorBuildingActivity.indoorBuildingActivity(i2, ami);
            }
        }
    }

    public void refreshScaleLineView(int i2) {
        if (this.l != null) {
            this.l.refreshScaleLineView(i2);
        }
    }

    public void setScaleColor(int i2, int i3, int i4) {
        if (this.l != null) {
            this.l.setScaleColor(i2, i3, i4);
        }
    }

    public void paintCompass(int i2) {
        if (this.l != null) {
            this.l.paintCompass(i2);
        }
    }

    public void fadeCompassWidget(int i2) {
        if (this.l != null) {
            this.l.fadeCompassWidget(i2);
        }
    }

    public void setFrontViewVisibility(int i2, boolean z2) {
        if (this.l != null) {
            this.l.setFrontViewVisibility(i2, z2);
        }
    }

    public void setIndoorBuildingListener(defpackage.ami.a aVar) {
        this.j = aVar;
    }

    public void addIndoorBuildingListener(defpackage.ami.a aVar) {
        if (aVar != null) {
            this.k.add(aVar);
        }
    }

    public void removeIndoorBuidingListener(defpackage.ami.a aVar) {
        this.k.remove(aVar);
    }

    public void setMapWidgetListener(amn amn) {
        this.l = amn;
    }

    public void doMutex() {
        this.J.compareAndSet(false, true);
        this.C.postDelayed(this.K, 1000);
    }

    public boolean checkMutex() {
        return this.J.get();
    }

    public void setIRealtimeBusStateListener(ccz ccz) {
        this.b.setIRealtimeBusStateListener(ccz);
    }

    public void onEngineActionGesture(int i2, alg alg) {
        int i3;
        switch (alg.a) {
            case 1:
            case 3:
                i3 = 1;
                break;
            case 2:
            case 4:
                i3 = 2;
                break;
            case 5:
                i3 = 3;
                break;
            default:
                i3 = -1;
                break;
        }
        AjxMapViewEventReceiver.onEngineActionGestureS(i2, i3, 3, alg.c);
        long currentTimeMillis = System.currentTimeMillis();
        if (this.g != null) {
            this.g.onEngineActionGesture(i2, alg);
        }
        this.r = System.currentTimeMillis() - currentTimeMillis;
        StringBuilder sb = new StringBuilder("mOnEngineActionGesture use time = ");
        sb.append(this.r);
        AMapLog.e("MapManager", sb.toString());
        for (btw onEngineActionGesture : this.e) {
            onEngineActionGesture.onEngineActionGesture(alg);
        }
        btv btv = this.h.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onEngineActionGesture(alg);
        }
    }

    public void afterDrawFrame(int i2, GLMapState gLMapState) {
        if (bno.a) {
            if (F == null) {
                F = Boolean.valueOf(new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("show_map_draw_info", false));
            }
            if (F.booleanValue()) {
                this.G++;
                AMapLog.d("DrawFrame", String.format("DrawFrameCounter: %s", new Object[]{Long.valueOf(this.G)}));
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (elapsedRealtime - this.H >= 5000) {
                    this.H = elapsedRealtime;
                    FileUtil.saveLogToFile(String.format("[%s]---mDrawFrameCounter: %s", new Object[]{new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date()), Long.valueOf(this.G)}), "DrawFrameLogs.txt");
                }
            }
        }
    }

    public void setEyrieMapGestureListener(amj amj, aky aky) {
        awb awb = (awb) defpackage.esb.a.a.a(awb.class);
        if (awb != null) {
            awb.a(amj, aky);
        }
    }
}
