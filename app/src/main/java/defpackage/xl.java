package defpackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.MotionEvent;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.bundle.uitemplate.page.AbstractSlidablePage;
import com.autonavi.bundle.uitemplate.page.SlidableAjxPage;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle.GLAmapFocusHits;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.core.MapViewUtil;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.base.overlay.LineOverlay;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.RouteOverlay;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
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

/* renamed from: xl reason: default package */
/* compiled from: MapManagerEventDispatcher */
public final class xl implements amj, amk, aml {
    private static volatile Boolean i;
    /* access modifiers changed from: 0000 */
    public List<btw> a = new LinkedList();
    List<bts> b = new LinkedList();
    /* access modifiers changed from: 0000 */
    public Map<Integer, btv> c = new HashMap();
    amj d = null;
    private Context e;
    private Handler f = new Handler(Looper.getMainLooper());
    private a g = null;
    private int h;
    private long j = 0;
    private long k = 0;
    private volatile boolean l = false;
    /* access modifiers changed from: private */
    public AtomicBoolean m = new AtomicBoolean(false);
    private Runnable n = new Runnable() {
        public final void run() {
            xl.this.m.compareAndSet(true, false);
        }
    };

    /* renamed from: xl$a */
    /* compiled from: MapManagerEventDispatcher */
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
                if (!xl.b()) {
                    for (btw onMapMotionStop : xl.this.a) {
                        onMapMotionStop.onMapMotionStop();
                    }
                    btv btv = (btv) xl.this.c.get(Integer.valueOf(this.c));
                    if (btv != null) {
                        btv.onMapMotionStop();
                    }
                }
                bdl bdl = (bdl) defpackage.esb.a.a.a(bdl.class);
                if (bdl != null) {
                    bdl.a();
                }
            }
            return null;
        }
    }

    public final void beforeDrawFrame(int i2, GLMapState gLMapState) {
    }

    public final void onMapTipClear(int i2) {
    }

    public final void onScreenShotFinished(int i2, long j2) {
    }

    public final void onScreenShotFinished(int i2, Bitmap bitmap) {
    }

    public final void onScreenShotFinished(int i2, String str) {
    }

    xl(int i2, Context context) {
        this.e = context;
        this.h = i2;
    }

    public final void onMapTipInfo(int i2, String str) {
        if (!TextUtils.isEmpty(str)) {
            ToastHelper.showToast(str);
        }
    }

    public final void onUserMapTouchEvent(int i2, MotionEvent motionEvent) {
        AMapLog.e("MapManagerEventDispatcher", "mOnUserMapTouchEvent use time = ".concat(String.valueOf(System.currentTimeMillis() - System.currentTimeMillis())));
        Iterator<bts> it = this.b.iterator();
        while (it.hasNext()) {
            it.next();
        }
        for (btw onMapTouchEvent : this.a) {
            onMapTouchEvent.onMapTouchEvent(motionEvent);
        }
        btv btv = this.c.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onMapTouchEvent(motionEvent);
        }
    }

    public final void onLongPress(int i2, MotionEvent motionEvent) {
        AMapLog.e("MapManagerEventDispatcher", "mOnLongPress use time = ".concat(String.valueOf(System.currentTimeMillis() - System.currentTimeMillis())));
        GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(DoNotUseTool.getMapView().c((int) motionEvent.getX(), (int) motionEvent.getY()));
        for (btw onMapLongPress : this.a) {
            onMapLongPress.onMapLongPress(motionEvent, glGeoPoint2GeoPoint);
        }
        btv btv = this.c.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onMapLongPress(motionEvent, glGeoPoint2GeoPoint);
        }
    }

    public final void onDoubleTap(int i2, MotionEvent motionEvent) {
        AMapLog.e("MapManagerEventDispatcher", "mOnDoubleTap use time = ".concat(String.valueOf(System.currentTimeMillis() - System.currentTimeMillis())));
        GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(DoNotUseTool.getMapView().c((int) motionEvent.getX(), (int) motionEvent.getY()));
        for (btw onMapDoubleClick : this.a) {
            onMapDoubleClick.onMapDoubleClick(motionEvent, glGeoPoint2GeoPoint);
        }
        btv btv = this.c.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onDoubleTap();
        }
    }

    public final void onMoveBegin(int i2, MotionEvent motionEvent) {
        AMapLog.e("MapManagerEventDispatcher", "mOnMoveBegin use time = ".concat(String.valueOf(System.currentTimeMillis() - System.currentTimeMillis())));
        btv btv = this.c.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onMoveBegin();
        }
    }

    public final void onZoomOutTap(int i2, MotionEvent motionEvent) {
        AMapLog.e("MapManagerEventDispatcher", "mOnZoomOutTap use time = ".concat(String.valueOf(System.currentTimeMillis() - System.currentTimeMillis())));
        btv btv = this.c.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onZoomOutTap();
        }
    }

    public final void onScaleRotateBegin(int i2, MotionEvent motionEvent) {
        AMapLog.e("MapManagerEventDispatcher", "mOnScaleRotateBegin use time = ".concat(String.valueOf(System.currentTimeMillis() - System.currentTimeMillis())));
        btv btv = this.c.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onScaleRotateBegin();
        }
    }

    public final void onHoveBegin(int i2, MotionEvent motionEvent) {
        AMapLog.e("MapManagerEventDispatcher", "mOnHoveBegin use time = ".concat(String.valueOf(System.currentTimeMillis() - System.currentTimeMillis())));
        btv btv = this.c.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onHoveBegin();
        }
    }

    public final boolean onSingleTapUp(int i2, MotionEvent motionEvent) {
        AMapLog.e("MapManagerEventDispatcher", "mOnSingleTapUp use time = ".concat(String.valueOf(System.currentTimeMillis() - System.currentTimeMillis())));
        IOverlayManager overlayManager = DoNotUseTool.getMapManager().getOverlayManager();
        awo awo = (awo) defpackage.esb.a.a.a(awo.class);
        boolean z = false;
        boolean a2 = awo != null ? awo.a() : false;
        if ((overlayManager.getMapPointOverlay().isVisible() && overlayManager.getMapPointOverlay().isClickable()) || a2) {
            ArrayList<als> d2 = DoNotUseTool.getMapView().d((int) motionEvent.getX(), (int) motionEvent.getY());
            if (d2 != null && d2.size() > 0) {
                z = true;
            }
            if (z) {
                for (btw onLabelClick : this.a) {
                    onLabelClick.onLabelClick(d2);
                }
                btv btv = this.c.get(Integer.valueOf(i2));
                if (btv != null) {
                    btv.onLabelClick(d2);
                }
            }
        }
        GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(DoNotUseTool.getMapView().c((int) motionEvent.getX(), (int) motionEvent.getY()));
        if (i2 == this.h) {
            for (btw onMapSingleClick : this.a) {
                onMapSingleClick.onMapSingleClick(motionEvent, glGeoPoint2GeoPoint);
            }
        } else {
            for (btw onMapSingleClick2 : this.a) {
                onMapSingleClick2.onMapSingleClick(i2, motionEvent, glGeoPoint2GeoPoint);
            }
        }
        btv btv2 = this.c.get(Integer.valueOf(i2));
        if (btv2 != null) {
            btv2.onMapSingleClick(motionEvent, glGeoPoint2GeoPoint);
        }
        return z;
    }

    public final void onEngineActionGesture(int i2, alg alg) {
        AMapLog.e("MapManagerEventDispatcher", "mOnEngineActionGesture use time = ".concat(String.valueOf(System.currentTimeMillis() - System.currentTimeMillis())));
        for (btw onEngineActionGesture : this.a) {
            onEngineActionGesture.onEngineActionGesture(alg);
        }
        btv btv = this.c.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onEngineActionGesture(alg);
        }
    }

    public final boolean onFling(int i2, MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
        Iterator<bts> it = this.b.iterator();
        while (it.hasNext()) {
            it.next();
        }
        return true;
    }

    public final void onMapSizeChange(int i2) {
        for (btw onMapSizeChange : this.a) {
            onMapSizeChange.onMapSizeChange();
        }
    }

    public final void onMapLevelChange(final int i2, final boolean z) {
        AMapLog.e("MapManagerEventDispatcher", "mOnMapLevelChange use time = ".concat(String.valueOf(System.currentTimeMillis() - System.currentTimeMillis())));
        for (btw onMapLevelChange : this.a) {
            onMapLevelChange.onMapLevelChange(z);
        }
        btv btv = this.c.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onMapLevelChange(z);
        }
        for (final bts next : this.b) {
            this.f.post(new Runnable() {
                public final void run() {
                    next.a();
                }
            });
        }
    }

    public final void onEngineVisible(int i2, boolean z) {
        AMapLog.e("MapManagerEventDispatcher", "mOnEngineVisible use time = ".concat(String.valueOf(System.currentTimeMillis() - System.currentTimeMillis())));
        btv btv = this.c.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onEngineVisible(i2, z);
        }
    }

    public final void onMotionFinished(int i2, int i3) {
        if (!b()) {
            AMapLog.e("MapManagerEventDispatcher", "mOnMotionFinished use time = ".concat(String.valueOf(System.currentTimeMillis() - System.currentTimeMillis())));
            for (btw onMapMotionFinish : this.a) {
                onMapMotionFinish.onMapMotionFinish();
            }
        }
        MapViewUtil.updateLockMapAngleState(DoNotUseTool.getMapView(), (float) MapViewUtil.INVALID_CAMERA_DEGREE);
        if (this.g != null) {
            this.g.a = true;
            this.g = null;
        }
        this.g = new a(i2);
        ahl.b(this.g);
    }

    public final void onPointOverlayClick(int i2, GLAmapFocusHits gLAmapFocusHits) {
        BaseMapOverlay a2 = a(i2, gLAmapFocusHits.mOverlayHashCode);
        if (a2 != null && a2.getItem((int) gLAmapFocusHits.mHitedIndex) != null) {
            Iterator<bts> it = this.b.iterator();
            while (it.hasNext()) {
                it.next();
            }
            if (a2 instanceof PointOverlay) {
                PointOverlay pointOverlay = (PointOverlay) a2;
                if (pointOverlay.isVisible() && pointOverlay.isClickable()) {
                    pointOverlay.onPointOverlayClick((int) gLAmapFocusHits.mHitedIndex);
                }
            }
            for (btw onPointOverlayClick : this.a) {
                onPointOverlayClick.onPointOverlayClick(gLAmapFocusHits.mOverlayHashCode, (int) gLAmapFocusHits.mHitedIndex);
            }
            btv btv = this.c.get(Integer.valueOf(i2));
            if (btv != null) {
                btv.onPointOverlayClick(gLAmapFocusHits.mOverlayHashCode, (int) gLAmapFocusHits.mHitedIndex);
            }
        }
    }

    public final void onLineOverlayClick(int i2, GLAmapFocusHits gLAmapFocusHits) {
        BaseMapOverlay a2 = a(i2, gLAmapFocusHits.mOverlayHashCode);
        if (a2 != null) {
            Iterator<bts> it = this.b.iterator();
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
            for (btw onLineOverlayClick : this.a) {
                onLineOverlayClick.onLineOverlayClick(gLAmapFocusHits.mOverlayHashCode);
            }
            btv btv = this.c.get(Integer.valueOf(i2));
            if (btv != null) {
                btv.onLineOverlayClick(gLAmapFocusHits.mOverlayHashCode);
            }
        }
    }

    public final void onNoFeatureClick(int i2) {
        for (btw onNonFeatureClick : this.a) {
            onNonFeatureClick.onNonFeatureClick();
        }
        btv btv = this.c.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onFocusClear();
        }
    }

    public final boolean onBlankClick(int i2) {
        for (btw onBlankClick : this.a) {
            onBlankClick.onBlankClick();
        }
        btv btv = this.c.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onBlankClick();
        }
        return false;
    }

    public final boolean onNoBlankClick(int i2) {
        for (btw onNoBlankClick : this.a) {
            onNoBlankClick.onNoBlankClick();
        }
        btv btv = this.c.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onNoBlankClick();
        }
        return false;
    }

    public final void onHorizontalMove(int i2, float f2) {
        for (btw onHorizontalMove : this.a) {
            onHorizontalMove.onHorizontalMove(f2);
        }
    }

    public final void onHorizontalMoveEnd(int i2) {
        for (btw onHorizontalMoveEnd : this.a) {
            onHorizontalMoveEnd.onHorizontalMoveEnd();
        }
    }

    public final void onOfflineMap(int i2, final String str, int i3) {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.e.getSystemService("connectivity");
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

    public final void onRealCityAnimateFinish(int i2) {
        Iterator<bts> it = this.b.iterator();
        while (it.hasNext()) {
            it.next();
        }
    }

    public final void onMapAnimationFinished(int i2, int i3) {
        AMapLog.e("MapManagerEventDispatcher", "mOnMapAnimationFinished(int,int) use time = ".concat(String.valueOf(System.currentTimeMillis() - System.currentTimeMillis())));
        for (btw onMapAnimationFinished : this.a) {
            onMapAnimationFinished.onMapAnimationFinished(i3);
        }
        btv btv = this.c.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onMapAnimationFinished(i3);
        }
    }

    public final void onMapAnimationFinished(int i2, aln aln) {
        AMapLog.e("MapManagerEventDispatcher", "mOnMapAnimationFinished(int,object) use time = ".concat(String.valueOf(System.currentTimeMillis() - System.currentTimeMillis())));
        for (btw onMapAnimationFinished : this.a) {
            onMapAnimationFinished.onMapAnimationFinished(aln);
        }
        btv btv = this.c.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onMapAnimationFinished(aln);
        }
    }

    public final void onSelectSubWayActive(int i2, byte[] bArr) {
        List<Long> parseSubWayActiveIds = awc.parseSubWayActiveIds(bArr);
        for (btw onSelectSubWayActive : this.a) {
            onSelectSubWayActive.onSelectSubWayActive(parseSubWayActiveIds);
        }
        btv btv = this.c.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onSelectSubWayActive(parseSubWayActiveIds);
        }
    }

    public final void onMapRenderCompleted(int i2) {
        for (btw onMapRenderCompleted : this.a) {
            onMapRenderCompleted.onMapRenderCompleted();
        }
        btv btv = this.c.get(Integer.valueOf(i2));
        if (btv != null) {
            btv.onMapRenderCompleted();
        }
    }

    private static BaseMapOverlay a(int i2, long j2) {
        int a2 = a(i2).F().a();
        for (int i3 = 0; i3 < a2; i3++) {
            BaseMapOverlay a3 = a(i2).F().a(i3);
            if (a3 == null || ((long) a3.hashCode()) == j2) {
                return a3;
            }
        }
        return null;
    }

    public final boolean onClick(int i2, float f2, float f3) {
        if (this.d != null) {
            return this.d.onClick(DoNotUseTool.getMapView().m(i2), f2, f3);
        }
        return false;
    }

    public final boolean onDoubleClick(int i2, float f2, float f3) {
        if (this.d != null) {
            this.d.onDoubleClick(DoNotUseTool.getMapView().m(i2), f2, f3);
        }
        return false;
    }

    public final boolean onLongPress(int i2, float f2, float f3) {
        if (this.d != null) {
            this.d.onLongPress(DoNotUseTool.getMapView().m(i2), f2, f3);
        }
        return false;
    }

    public final boolean onMontionStart(int i2, float f2, float f3) {
        if (this.d != null) {
            this.d.onMontionStart(i2, f2, f3);
        }
        return false;
    }

    public final boolean onMontionFinish(int i2) {
        if (this.d != null) {
            this.d.onMontionFinish(i2);
        }
        return false;
    }

    public final boolean onTouchEvent(int i2, MotionEvent motionEvent) {
        if (this.d != null) {
            this.d.onTouchEvent(i2, motionEvent);
        }
        return false;
    }

    static bty a(int i2) {
        brx mapViewManager = DoNotUseTool.getMapManager().getMapViewManager();
        if (mapViewManager == null) {
            return null;
        }
        return mapViewManager.a(i2);
    }

    /* access modifiers changed from: private */
    public static boolean b() {
        bid pageContext = AMapPageUtil.getPageContext();
        return (pageContext instanceof AbstractSlidablePage) || (pageContext instanceof SlidableAjxPage);
    }

    public final void onShowPress(int i2, MotionEvent motionEvent) {
        GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(DoNotUseTool.getMapView().c((int) motionEvent.getX(), (int) motionEvent.getY()));
        for (btw onMapShowPress : this.a) {
            onMapShowPress.onMapShowPress(motionEvent, glGeoPoint2GeoPoint);
        }
    }

    public final void afterDrawFrame(int i2, GLMapState gLMapState) {
        if (bno.a) {
            if (i == null) {
                i = Boolean.valueOf(new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("show_map_draw_info", false));
            }
            if (i.booleanValue()) {
                this.j++;
                AMapLog.d("DrawFrame", String.format("DrawFrameCounter: %s", new Object[]{Long.valueOf(this.j)}));
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (elapsedRealtime - this.k >= 5000) {
                    this.k = elapsedRealtime;
                    FileUtil.saveLogToFile(String.format("[%s]---mDrawFrameCounter: %s", new Object[]{new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date()), Long.valueOf(this.j)}), "DrawFrameLogs.txt");
                }
            }
        }
    }
}
