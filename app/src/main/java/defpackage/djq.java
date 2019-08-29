package defpackage;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.amap.bundle.drivecommon.model.ICarRouteResult;
import com.amap.bundle.drivecommon.model.NavigationPath;
import com.amap.bundle.drivecommon.model.NavigationResult;
import com.amap.bundle.drivecommon.overlay.RouteCarResultRouteItem;
import com.amap.bundle.drivecommon.overlay.RouteCarResultRouteOverlay;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.ae.gmap.utils.GLMapStaticValue.PolylineDrawType;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.gmap.gloverlay.GLLineOverlay;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle;
import com.autonavi.jni.ae.route.model.LineIconPoint;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.overlay.RouteCarResulLinePointOverlay;
import com.autonavi.minimap.drive.overlay.RouteCarResultEndPointOverlay;
import com.autonavi.minimap.drive.overlay.RouteCarResultMidPointPopOverlay;
import com.autonavi.minimap.drive.overlay.RouteCarResultWalkFerryOverlay;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* renamed from: djq reason: default package */
/* compiled from: NewRouteCarDrawMapLineTools */
public final class djq {
    protected Context a;
    protected ICarRouteResult b = null;
    protected RouteCarResultEndPointOverlay c;
    protected RouteCarResulLinePointOverlay d;
    protected RouteCarResultEndPointOverlay e;
    protected RouteCarResultWalkFerryOverlay f;
    protected RouteCarResultRouteOverlay[] g;
    protected bty h;
    protected AbstractBaseMapPage i;
    POI j;
    private RouteCarResultMidPointPopOverlay k;
    private RouteCarResultRouteOverlay l;
    private HashMap<Integer, Integer> m = new HashMap<>();
    private LineIconPoint[] n;

    private static int a(int i2, int i3, int i4, int i5) {
        int i6 = i2 - i4;
        int i7 = i3 - i5;
        if (i6 > 0 && i7 >= 0) {
            return 0;
        }
        if (i6 < 0 && i7 >= 0) {
            return 1;
        }
        if (i6 >= 0 || i7 > 0) {
            return (i6 <= 0 || i7 > 0) ? 0 : 3;
        }
        return 2;
    }

    public djq() {
    }

    public djq(bty bty, Context context, ICarRouteResult iCarRouteResult, AbstractBaseMapPage abstractBaseMapPage) {
        this.h = bty;
        this.a = context;
        this.b = iCarRouteResult;
        this.i = abstractBaseMapPage;
        NavigationResult naviResultData = iCarRouteResult.getNaviResultData();
        if (!(naviResultData == null || naviResultData.mPaths == null)) {
            NavigationPath[] navigationPathArr = naviResultData.mPaths;
            this.g = new RouteCarResultRouteOverlay[navigationPathArr.length];
            for (int i2 = 0; i2 < navigationPathArr.length; i2++) {
                this.g[i2] = new RouteCarResultRouteOverlay(this.h);
                abstractBaseMapPage.addOverlay(this.g[i2]);
                GLOverlay gLOverlay = this.g[i2].getGLOverlay();
                if (gLOverlay instanceof GLLineOverlay) {
                    ((GLLineOverlay) gLOverlay).setFilterZoomLevel(7.0f, 15.0f);
                }
                this.g[i2].setBoundCache(true);
                this.g[i2].setClickable(true);
                this.g[i2].setDrawType(PolylineDrawType.GREY_OVER_VIEW);
                this.m.put(Integer.valueOf(this.g[i2].getGlLineCode()), Integer.valueOf(i2));
            }
        }
        this.d = new RouteCarResulLinePointOverlay(bty);
        this.d.setOverlayPriority(12);
        this.d.setAutoSetFocus(false);
        abstractBaseMapPage.addOverlay(this.d);
        this.f = new RouteCarResultWalkFerryOverlay(bty);
        this.f.setOverlayPriority(18);
        this.f.setMinDisplayLevel(15);
        abstractBaseMapPage.addOverlay(this.f);
        this.e = new RouteCarResultEndPointOverlay(bty);
        this.e.setOverlayPriority(31);
        abstractBaseMapPage.addOverlay(this.e);
        this.k = new RouteCarResultMidPointPopOverlay(bty);
        abstractBaseMapPage.addOverlay(this.k);
        this.k.setOverlayOnTop(true);
        this.c = new RouteCarResultEndPointOverlay(bty);
        this.c.setOverlayPriority(34);
        this.c.setAutoSetFocus(false);
        abstractBaseMapPage.addOverlay(this.c);
        GLOverlayBundle aa = this.h.aa();
        if (aa != null) {
            aa.sortOverlay();
        }
    }

    public final void a() {
        RouteCarResultRouteOverlay[] routeCarResultRouteOverlayArr;
        if (this.g != null) {
            for (RouteCarResultRouteOverlay routeCarResultRouteOverlay : this.g) {
                routeCarResultRouteOverlay.removeRouteName();
                routeCarResultRouteOverlay.clear();
            }
        }
        if (this.c != null) {
            this.c.clear();
        }
        if (this.f != null) {
            this.f.clear();
        }
        if (this.k != null) {
            this.k.clear();
        }
        if (this.l != null) {
            this.l.clear();
        }
        if (this.e != null) {
            this.e.clear();
        }
        if (this.d != null) {
            this.d.clear();
        }
        this.n = null;
    }

    public final int a(long j2) {
        if (this.g != null && this.g.length > 1) {
            for (int i2 = 0; i2 < this.g.length; i2++) {
                if (((long) this.g[i2].getGlLineCode()) == j2) {
                    int i3 = (int) j2;
                    if (!this.m.containsKey(Integer.valueOf(i3))) {
                        return i2;
                    }
                    Integer num = this.m.get(Integer.valueOf(i3));
                    if (num == null) {
                        return -1;
                    }
                    return num.intValue();
                }
            }
        }
        return -1;
    }

    public final Rect b() {
        Rect rect = (this.b == null || this.b.getNaviResultData() == null) ? null : this.b.getNaviResultData().maxBound;
        Rect bound = this.c.getBound();
        if (!(rect == null || bound == null)) {
            rect.union(bound);
        }
        return rect;
    }

    public final void a(dhj dhj) {
        if (this.b != null && this.b.hasData()) {
            tn.a();
            if (tn.a(this.b)) {
                if (this.c != null) {
                    this.c.clear();
                    this.c.clearFocus();
                }
                if (this.d != null) {
                    this.d.clear();
                    this.d.clearFocus();
                }
                this.f.clear();
                this.f.clearFocus();
                this.n = null;
                NavigationPath[] navigationPathArr = this.b.getNaviResultData().mPaths;
                int length = navigationPathArr.length;
                if (length > 0) {
                    int focusRouteIndex = this.b.getFocusRouteIndex();
                    if (focusRouteIndex > navigationPathArr.length - 1) {
                        focusRouteIndex = navigationPathArr.length - 1;
                    } else if (focusRouteIndex < 0) {
                        focusRouteIndex = 0;
                    }
                    this.l = this.g[focusRouteIndex];
                    HashMap hashMap = new HashMap();
                    if (focusRouteIndex != navigationPathArr.length - 1) {
                        hashMap.put(Integer.valueOf(focusRouteIndex), Integer.valueOf(navigationPathArr.length - 1));
                        hashMap.put(Integer.valueOf(navigationPathArr.length - 1), Integer.valueOf(focusRouteIndex));
                        this.l = this.g[navigationPathArr.length - 1];
                    }
                    for (int i2 = 0; i2 < length; i2++) {
                        RouteCarResultRouteOverlay routeCarResultRouteOverlay = this.g[i2];
                        if (routeCarResultRouteOverlay != null) {
                            routeCarResultRouteOverlay.removeRouteName();
                            routeCarResultRouteOverlay.clear();
                            if (dhj.b) {
                                this.g[i2].setDrawType(PolylineDrawType.GROWN_ANIMATION);
                            } else {
                                this.g[i2].setDrawType(PolylineDrawType.GREY_OVER_VIEW);
                            }
                        }
                    }
                    this.l = this.g[navigationPathArr.length - 1];
                    a(this.b.getFocusNavigationPath(), this.l, dhj.a, dhj.b);
                    for (int i3 = 0; i3 < length; i3++) {
                        if (!(this.g[i3] == null || length - 1 == i3)) {
                            NavigationPath navigationPath = this.g[i3].getNavigationPath();
                            if (!(navigationPath == null || navigationPath.mEngineLineItem == null)) {
                                RouteCarResultRouteItem routeCarResultRouteItem = new RouteCarResultRouteItem(navigationPath.mEngineLineItem, false, dhj.c == 0);
                                this.g[i3].addLineItem(routeCarResultRouteItem);
                                this.g[i3].addRouteName(routeCarResultRouteItem.isRefreshMap());
                            }
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0151, code lost:
        r13 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0153, code lost:
        r13 = 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(com.amap.bundle.drivecommon.model.NavigationPath r18, com.amap.bundle.drivecommon.overlay.RouteCarResultRouteOverlay r19, boolean r20, boolean r21) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            r0.l = r2
            if (r1 != 0) goto L_0x000b
            return
        L_0x000b:
            com.amap.bundle.drivecommon.model.NavigationSection[] r4 = r1.mSections
            r5 = 0
            r4 = r4[r5]
            com.autonavi.common.model.GeoPoint[] r4 = r4.mGeoPoints
            r4 = r4[r5]
            int r4 = r4.x
            com.amap.bundle.drivecommon.model.NavigationSection[] r6 = r1.mSections
            r6 = r6[r5]
            com.autonavi.common.model.GeoPoint[] r6 = r6.mGeoPoints
            r6 = r6[r5]
            int r6 = r6.y
            com.amap.bundle.drivecommon.model.ICarRouteResult r7 = r0.b
            com.autonavi.common.model.POI r7 = r7.getFromPOI()
            com.autonavi.common.model.GeoPoint r8 = r7.getPoint()
            int r8 = r8.x
            com.autonavi.common.model.GeoPoint r9 = r7.getPoint()
            int r9 = r9.y
            java.lang.String r10 = r7.getName()
            java.lang.String r11 = "我的位置"
            boolean r10 = r10.equals(r11)
            if (r10 == 0) goto L_0x0057
            java.util.HashMap r8 = r7.getPoiExtra()
            java.lang.String r9 = "startGpsValueList"
            boolean r8 = r8.containsKey(r9)
            if (r8 == 0) goto L_0x0065
            com.autonavi.common.model.GeoPoint r8 = r7.getPoint()
            r8.x = r4
            com.autonavi.common.model.GeoPoint r7 = r7.getPoint()
            r7.y = r6
            goto L_0x0065
        L_0x0057:
            com.autonavi.minimap.drive.overlay.RouteCarResultBeginEndPointItem r7 = new com.autonavi.minimap.drive.overlay.RouteCarResultBeginEndPointItem
            com.autonavi.minimap.drive.overlay.RouteCarResultBeginEndPointItem$TYPE r10 = com.autonavi.minimap.drive.overlay.RouteCarResultBeginEndPointItem.TYPE.BEGIN
            r7.<init>(r8, r9, r10)
            r7.j = r5
            com.autonavi.minimap.drive.overlay.RouteCarResultEndPointOverlay r8 = r0.c
            r8.addItem(r7)
        L_0x0065:
            com.autonavi.minimap.drive.overlay.RouteCarResultLinePointItem r7 = new com.autonavi.minimap.drive.overlay.RouteCarResultLinePointItem
            com.autonavi.minimap.drive.overlay.RouteCarResultLinePointItem$TYPE r8 = com.autonavi.minimap.drive.overlay.RouteCarResultLinePointItem.TYPE.BEGIN
            r7.<init>(r4, r6, r8)
            com.autonavi.minimap.drive.overlay.RouteCarResulLinePointOverlay r4 = r0.d
            r4.addItem(r7)
            com.amap.bundle.drivecommon.model.ICarRouteResult r4 = r0.b
            com.autonavi.common.model.POI r4 = r4.getToPOI()
            com.autonavi.common.model.GeoPoint r4 = r4.getPoint()
            int r4 = r4.x
            com.amap.bundle.drivecommon.model.ICarRouteResult r6 = r0.b
            com.autonavi.common.model.POI r6 = r6.getToPOI()
            com.autonavi.common.model.GeoPoint r6 = r6.getPoint()
            int r6 = r6.y
            com.amap.bundle.drivecommon.overlay.RouteCarResultRouteItem r7 = new com.amap.bundle.drivecommon.overlay.RouteCarResultRouteItem
            com.autonavi.jni.ae.route.model.LineItem r8 = r1.mEngineLineItem
            r9 = 1
            r7.<init>(r8, r9, r9)
            int r8 = r2.addLineItem(r7)
            boolean r7 = r7.isRefreshMap()
            r2.addRouteName(r7)
            com.amap.bundle.drivecommon.model.ICarRouteResult r2 = r0.b
            boolean r2 = r2.isM_bNative()
            r7 = 2
            if (r2 != 0) goto L_0x0162
            java.util.List<com.amap.bundle.drivecommon.model.JamInfo> r2 = r1.mJamInfo
            if (r2 == 0) goto L_0x0162
            java.util.List<com.amap.bundle.drivecommon.model.JamInfo> r2 = r1.mJamInfo
            int r2 = r2.size()
            if (r2 <= 0) goto L_0x0162
            java.util.List<com.amap.bundle.drivecommon.model.JamInfo> r2 = r1.mJamInfo
            int r2 = r2.size()
            java.util.List<com.amap.bundle.drivecommon.model.JamInfo> r10 = r1.mJamInfo
            java.lang.Object r10 = r10.get(r5)
            com.amap.bundle.drivecommon.model.JamInfo r10 = (com.amap.bundle.drivecommon.model.JamInfo) r10
            android.graphics.Rect r11 = new android.graphics.Rect
            r11.<init>()
            com.autonavi.common.model.GeoPoint r12 = r10.gPoint
            int r12 = r12.x
            r11.left = r12
            com.autonavi.common.model.GeoPoint r12 = r10.gPoint
            int r12 = r12.x
            r11.right = r12
            com.autonavi.common.model.GeoPoint r12 = r10.gPoint
            int r12 = r12.y
            r11.top = r12
            com.autonavi.common.model.GeoPoint r12 = r10.gPoint
            int r12 = r12.y
            r11.bottom = r12
            r12 = 1
        L_0x00dd:
            if (r12 >= r2) goto L_0x0114
            java.util.List<com.amap.bundle.drivecommon.model.JamInfo> r13 = r1.mJamInfo
            java.lang.Object r13 = r13.get(r12)
            com.amap.bundle.drivecommon.model.JamInfo r13 = (com.amap.bundle.drivecommon.model.JamInfo) r13
            com.autonavi.common.model.GeoPoint r13 = r13.gPoint
            int r14 = r11.left
            int r15 = r13.x
            if (r14 <= r15) goto L_0x00f3
            int r14 = r13.x
            r11.left = r14
        L_0x00f3:
            int r14 = r11.right
            int r15 = r13.x
            if (r14 >= r15) goto L_0x00fd
            int r14 = r13.x
            r11.right = r14
        L_0x00fd:
            int r14 = r11.top
            int r15 = r13.y
            if (r14 <= r15) goto L_0x0107
            int r14 = r13.y
            r11.top = r14
        L_0x0107:
            int r14 = r11.bottom
            int r15 = r13.y
            if (r14 >= r15) goto L_0x0111
            int r13 = r13.y
            r11.bottom = r13
        L_0x0111:
            int r12 = r12 + 1
            goto L_0x00dd
        L_0x0114:
            com.autonavi.common.model.GeoPoint r12 = r10.gPoint
            int r12 = a(r11, r12)
            if (r2 == r7) goto L_0x0162
            if (r2 <= r7) goto L_0x0162
            r13 = r10
            r10 = 1
        L_0x0120:
            if (r10 >= r2) goto L_0x0162
            java.util.List<com.amap.bundle.drivecommon.model.JamInfo> r14 = r1.mJamInfo
            java.lang.Object r14 = r14.get(r10)
            com.amap.bundle.drivecommon.model.JamInfo r14 = (com.amap.bundle.drivecommon.model.JamInfo) r14
            com.autonavi.common.model.GeoPoint r15 = r14.gPoint
            int r15 = a(r11, r15)
            if (r15 != r12) goto L_0x015b
            com.autonavi.common.model.GeoPoint r13 = r13.gPoint
            com.autonavi.common.model.GeoPoint r15 = r14.gPoint
            int r5 = r15.x
            int r7 = r13.x
            int r5 = r5 - r7
            int r7 = r15.y
            int r13 = r13.y
            int r7 = r7 - r13
            int r7 = java.lang.Math.abs(r7)
            int r5 = java.lang.Math.abs(r5)
            r13 = 3
            if (r7 <= r5) goto L_0x0155
            switch(r12) {
                case 0: goto L_0x0153;
                case 1: goto L_0x0151;
                case 2: goto L_0x0159;
                case 3: goto L_0x014f;
                default: goto L_0x014e;
            }
        L_0x014e:
            goto L_0x0151
        L_0x014f:
            r13 = 2
            goto L_0x0159
        L_0x0151:
            r13 = 0
            goto L_0x0159
        L_0x0153:
            r13 = 1
            goto L_0x0159
        L_0x0155:
            switch(r12) {
                case 0: goto L_0x0159;
                case 1: goto L_0x014f;
                case 2: goto L_0x0153;
                case 3: goto L_0x0151;
                default: goto L_0x0158;
            }
        L_0x0158:
            goto L_0x0151
        L_0x0159:
            r12 = r13
            goto L_0x015c
        L_0x015b:
            r12 = r15
        L_0x015c:
            int r10 = r10 + 1
            r13 = r14
            r5 = 0
            r7 = 2
            goto L_0x0120
        L_0x0162:
            if (r21 != 0) goto L_0x0167
            r17.c()
        L_0x0167:
            com.autonavi.minimap.drive.overlay.RouteCarResultBeginEndPointItem r1 = new com.autonavi.minimap.drive.overlay.RouteCarResultBeginEndPointItem
            com.autonavi.minimap.drive.overlay.RouteCarResultBeginEndPointItem$TYPE r2 = com.autonavi.minimap.drive.overlay.RouteCarResultBeginEndPointItem.TYPE.END
            r1.<init>(r4, r6, r2)
            r1.j = r8
            com.autonavi.minimap.drive.overlay.RouteCarResultEndPointOverlay r2 = r0.c
            r2.addItem(r1)
            com.amap.bundle.drivecommon.model.ICarRouteResult r1 = r0.b
            int r1 = r1.getFocusRouteIndex()
            com.amap.bundle.drivecommon.model.ICarRouteResult r2 = r0.b
            com.autonavi.jni.ae.route.route.CalcRouteResult r2 = r2.getCalcRouteResult()
            if (r20 != 0) goto L_0x01c7
            int r4 = r2.getPathCount()
            r5 = 0
        L_0x0188:
            if (r5 >= r4) goto L_0x01c7
            if (r5 == r1) goto L_0x01c4
            com.autonavi.jni.ae.route.route.Route r6 = r2.getRoute(r5)
            if (r6 == 0) goto L_0x01c4
            int r7 = r6.getSegmentCount()
            int r7 = r7 - r9
            com.autonavi.jni.ae.route.route.RouteSegment r6 = r6.getSegment(r7)
            double[] r6 = r6.getSegCoor()
            if (r6 == 0) goto L_0x01c4
            int r7 = r6.length
            if (r7 <= 0) goto L_0x01c4
            int r7 = r6.length
            r8 = 2
            int r7 = r7 / r8
            com.autonavi.common.model.GeoPoint r15 = new com.autonavi.common.model.GeoPoint
            int r7 = r7 - r9
            int r7 = r7 * 2
            r11 = r6[r7]
            int r7 = r7 + r9
            r13 = r6[r7]
            r6 = 0
            r10 = r15
            r7 = r15
            r15 = r6
            r10.<init>(r11, r13, r15)
            com.autonavi.minimap.drive.overlay.RouteCarResultLinePointItem r6 = new com.autonavi.minimap.drive.overlay.RouteCarResultLinePointItem
            com.autonavi.minimap.drive.overlay.RouteCarResultLinePointItem$TYPE r8 = com.autonavi.minimap.drive.overlay.RouteCarResultLinePointItem.TYPE.END_BAK
            r6.<init>(r7, r8)
            com.autonavi.minimap.drive.overlay.RouteCarResulLinePointOverlay r7 = r0.d
            r7.addItem(r6)
        L_0x01c4:
            int r5 = r5 + 1
            goto L_0x0188
        L_0x01c7:
            com.autonavi.jni.ae.route.route.Route r1 = r2.getRoute(r1)
            if (r1 == 0) goto L_0x01fd
            int r2 = r1.getSegmentCount()
            int r2 = r2 - r9
            com.autonavi.jni.ae.route.route.RouteSegment r1 = r1.getSegment(r2)
            double[] r1 = r1.getSegCoor()
            if (r1 == 0) goto L_0x01fd
            int r2 = r1.length
            if (r2 <= 0) goto L_0x01fd
            int r2 = r1.length
            r4 = 2
            int r2 = r2 / r4
            com.autonavi.common.model.GeoPoint r5 = new com.autonavi.common.model.GeoPoint
            int r2 = r2 - r9
            int r2 = r2 * 2
            r11 = r1[r2]
            int r2 = r2 + r9
            r13 = r1[r2]
            r15 = 0
            r10 = r5
            r10.<init>(r11, r13, r15)
            com.autonavi.minimap.drive.overlay.RouteCarResultLinePointItem r1 = new com.autonavi.minimap.drive.overlay.RouteCarResultLinePointItem
            com.autonavi.minimap.drive.overlay.RouteCarResultLinePointItem$TYPE r2 = com.autonavi.minimap.drive.overlay.RouteCarResultLinePointItem.TYPE.END_MAIN
            r1.<init>(r5, r2)
            com.autonavi.minimap.drive.overlay.RouteCarResulLinePointOverlay r2 = r0.d
            r2.addItem(r1)
        L_0x01fd:
            com.amap.bundle.drivecommon.model.ICarRouteResult r1 = r0.b
            int r1 = r1.getFocusStationIndex()
            r2 = -1
            if (r1 == r2) goto L_0x020c
            com.autonavi.minimap.drive.overlay.RouteCarResultEndPointOverlay r2 = r0.c
            r4 = 0
            r2.setFocus(r1, r4)
        L_0x020c:
            com.amap.bundle.drivecommon.model.ICarRouteResult r1 = r0.b
            boolean r1 = r1.hasMidPos()
            if (r1 == 0) goto L_0x022f
            com.amap.bundle.drivecommon.model.ICarRouteResult r1 = r0.b
            java.util.ArrayList r1 = r1.getMidPOIs()
            java.util.List r2 = defpackage.dhn.a(r1)
            com.autonavi.minimap.drive.overlay.RouteCarResultEndPointOverlay r4 = r0.e
            r4.addItem(r2)
            if (r20 != 0) goto L_0x022f
            com.autonavi.minimap.drive.overlay.RouteCarResultEndPointOverlay r2 = r0.e
            djq$1 r3 = new djq$1
            r3.<init>(r1)
            r2.setOnItemClickListener(r3)
        L_0x022f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.djq.a(com.amap.bundle.drivecommon.model.NavigationPath, com.amap.bundle.drivecommon.overlay.RouteCarResultRouteOverlay, boolean, boolean):void");
    }

    private void c() {
        if (this.b != null && this.b.getFocusNavigationPath() != null) {
            this.f.clear();
            this.f.clearFocus();
            this.n = null;
            this.n = this.b.getFocusNavigationPath().mLineIconPoints;
            if (this.n != null && this.n.length > 0) {
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < this.n.length; i2++) {
                    LineIconPoint lineIconPoint = this.n[i2];
                    dhm a2 = dhm.a(i2 + 640, this.a, new GeoPoint(lineIconPoint.lon, lineIconPoint.lat), lineIconPoint.type);
                    a2.j = 0;
                    arrayList.add(a2);
                }
                this.f.addItem((List<E>) arrayList);
            }
        }
    }

    private static int a(Rect rect, GeoPoint geoPoint) {
        GeoPoint geoPoint2 = new GeoPoint();
        geoPoint2.x = (rect.left + rect.right) / 2;
        geoPoint2.y = (rect.top + rect.bottom) / 2;
        return a(geoPoint.x, geoPoint.y, geoPoint2.x, geoPoint2.y);
    }

    static /* synthetic */ void a(djq djq, dhn dhn, int i2) {
        int i3;
        djq.k.clear();
        POI poi = dhn.g;
        djq.j = poi;
        int i4 = dhn.h;
        int a2 = agn.a(djq.a, 64.0f);
        new MapViewLayoutParams(-2, -2, poi.getPoint(), 81).mode = 0;
        View inflate = LayoutInflater.from(djq.a).inflate(R.layout.drive_tip_fromto_result_map, null);
        View findViewById = inflate.findViewById(R.id.tips_layout);
        findViewById.setBackgroundResource(R.drawable.tip_route_mid_action);
        TextView textView = (TextView) inflate.findViewById(R.id.txt_distance);
        textView.setText(poi.getName());
        textView.setMaxLines(1);
        textView.setEllipsize(TruncateAt.END);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.tips_marker);
        if (i2 != 1) {
            switch (i4) {
                case 0:
                    i3 = R.drawable.bubble_midd1;
                    break;
                case 1:
                    i3 = R.drawable.bubble_midd2;
                    break;
                case 2:
                    i3 = R.drawable.bubble_midd3;
                    break;
            }
        }
        i3 = R.drawable.bubble_midd;
        imageView.setImageResource(i3);
        imageView.setVisibility(4);
        textView.setMaxWidth((int) (((((float) ags.a(djq.a).width()) - (djq.a.getResources().getDimension(R.dimen.map_container_btn_size) * 2.0f)) - 66.0f) - 40.0f));
        textView.measure(0, 0);
        findViewById.measure(0, 0);
        ArrayList arrayList = new ArrayList();
        int measuredWidth = findViewById.getMeasuredWidth();
        int measuredWidth2 = textView.getMeasuredWidth();
        int i5 = measuredWidth - measuredWidth2;
        int i6 = ((measuredWidth / 2) - measuredWidth2) / 2;
        if (i6 > 0) {
            LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
            layoutParams.leftMargin = i6;
            textView.setPadding(0, 0, i6, 0);
            textView.setLayoutParams(layoutParams);
        }
        arrayList.add(new aly(measuredWidth2, a2));
        aly aly = new aly(i5, a2);
        aly.e = new a() {
            public final void a() {
                if (!bnx.a(djq.this.b.getFromPOI(), djq.this.b.getToPOI()) || djq.this.b.getMidPOIs().size() > 1) {
                    ArrayList arrayList = new ArrayList();
                    if (djq.this.b.getMidPOIs() != null && !djq.this.b.getMidPOIs().isEmpty()) {
                        Iterator<POI> it = djq.this.b.getMidPOIs().iterator();
                        while (it.hasNext()) {
                            POI next = it.next();
                            if (!bnx.a(djq.this.j, next)) {
                                arrayList.add(next);
                            }
                        }
                    }
                    return;
                }
                ToastHelper.showLongToast(djq.this.a.getResources().getString(R.string.car_disable_delete_midpoi));
            }
        };
        arrayList.add(aly);
        djq.k.setClickList(arrayList);
        djq.k.addItem(new dho(dhn.g, dhn.h, inflate, djq.h));
    }
}
