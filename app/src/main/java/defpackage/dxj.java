package defpackage;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.autonavi.bundle.routecommon.entity.BusPath;
import com.autonavi.bundle.routecommon.entity.BusPath.TaxiBusPath;
import com.autonavi.bundle.routecommon.entity.BusPathSection;
import com.autonavi.bundle.routecommon.entity.IBusRouteResult;
import com.autonavi.bundle.routecommon.entity.Station;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.Marker;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.route.bus.localbus.overlay.RouteBusLineOverlay;
import com.autonavi.minimap.route.bus.localbus.overlay.RouteBusPointOverlay;
import com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay;
import com.autonavi.minimap.route.bus.localbus.overlay.RouteBusTipOverlay;
import java.util.ArrayList;
import java.util.List;

/* renamed from: dxj reason: default package */
/* compiled from: ErrorBusResultController */
public final class dxj {
    RouteBusLineOverlay a;
    RouteBusStationNameOverlay b;
    RouteBusPointOverlay c;
    RouteBusPointOverlay d;
    RouteBusPointOverlay e;
    RouteBusPointOverlay f;
    RouteBusTipOverlay g;
    IBusRouteResult h;
    Context i;
    LayoutInflater j;
    bty k;
    public int l = -1;
    private List<BusPathSection> m;

    /* renamed from: dxj$a */
    /* compiled from: ErrorBusResultController */
    static class a {
        public int a;
        public int b;
        public int c;

        public a(int i, int i2, int i3) {
            this.a = i;
            this.b = i2;
            this.c = i3;
        }
    }

    public dxj(Context context, IBusRouteResult iBusRouteResult) {
        this.i = context;
        this.h = iBusRouteResult;
    }

    static PointOverlayItem a(RouteBusPointOverlay routeBusPointOverlay, int i2, int i3, int i4, int i5) {
        if (routeBusPointOverlay == null) {
            return null;
        }
        PointOverlayItem pointOverlayItem = new PointOverlayItem(new GeoPoint(i2, i3));
        if (i4 == -999) {
            pointOverlayItem.mDefaultMarker = new Marker(-999, i5, 0, 0);
        } else {
            pointOverlayItem.mDefaultMarker = routeBusPointOverlay.createMarker(i4, i5);
        }
        routeBusPointOverlay.addItem(pointOverlayItem);
        return pointOverlayItem;
    }

    private void b() {
        if (this.c != null) {
            this.c.clearFocus();
            this.c.setClickable(false);
        }
        if (this.m != null && this.m.get(0) != null) {
            this.l = 0;
        }
    }

    private static GeoPoint[] a(int[] iArr, int[] iArr2) {
        GeoPoint[] geoPointArr = new GeoPoint[iArr.length];
        for (int i2 = 0; i2 < iArr.length; i2++) {
            geoPointArr[i2] = new GeoPoint(iArr[i2], iArr2[i2]);
        }
        return geoPointArr;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0115, code lost:
        if (r10.infolist != null) goto L_0x0124;
     */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x02d1 A[Catch:{ Exception -> 0x0399 }] */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x02d6 A[Catch:{ Exception -> 0x0399 }] */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x0313 A[Catch:{ Exception -> 0x0399 }] */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x0388 A[Catch:{ Exception -> 0x0399 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int a(com.autonavi.bundle.routecommon.entity.BusPath r28, com.autonavi.minimap.route.bus.localbus.overlay.RouteBusLineOverlay r29, java.util.List<defpackage.dvw> r30, int r31) {
        /*
            r27 = this;
            r1 = r27
            r2 = r28
            r3 = r29
            r4 = r30
            int r5 = r2.mstartX
            int r6 = r2.mstartY
            int r7 = r2.mendX
            int r8 = r2.mendY
            r10 = 0
            r12 = r31
            r11 = r10
            r10 = 0
        L_0x0015:
            int r13 = r2.mSectionNum
            if (r10 >= r13) goto L_0x00a8
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r11 = r2.mPathSections     // Catch:{ Exception -> 0x00a2 }
            r11 = r11[r10]     // Catch:{ Exception -> 0x00a2 }
            int[] r13 = r11.mXs     // Catch:{ Exception -> 0x00a2 }
            int[] r15 = r11.mYs     // Catch:{ Exception -> 0x00a2 }
            int r9 = r11.mBusType     // Catch:{ Exception -> 0x00a2 }
            boolean r9 = com.autonavi.bundle.routecommon.entity.BusPath.isSubway(r9)     // Catch:{ Exception -> 0x00a2 }
            if (r9 != 0) goto L_0x0037
            com.autonavi.common.model.GeoPoint[] r9 = a(r13, r15)     // Catch:{ Exception -> 0x00a2 }
            java.lang.String r14 = r11.mRouteColor     // Catch:{ Exception -> 0x00a2 }
            int r14 = a(r14)     // Catch:{ Exception -> 0x00a2 }
            r3.createAndAddBackgroundLineItem(r9, r14)     // Catch:{ Exception -> 0x00a2 }
            goto L_0x0054
        L_0x0037:
            if (r11 == 0) goto L_0x0054
            int[] r9 = r11.mXs     // Catch:{ Exception -> 0x00a2 }
            int[] r14 = r11.mYs     // Catch:{ Exception -> 0x00a2 }
            r17 = r8
            java.lang.String r8 = r11.mRouteColor     // Catch:{ Exception -> 0x0046 }
            int r8 = android.graphics.Color.parseColor(r8)     // Catch:{ Exception -> 0x0046 }
            goto L_0x004c
        L_0x0046:
            r0 = move-exception
            r8 = r0
            r8.printStackTrace()     // Catch:{ Exception -> 0x00a2 }
            r8 = 0
        L_0x004c:
            com.autonavi.common.model.GeoPoint[] r9 = a(r9, r14)     // Catch:{ Exception -> 0x00a2 }
            r3.createAndAddBackgroundLineItem(r9, r8)     // Catch:{ Exception -> 0x00a2 }
            goto L_0x0056
        L_0x0054:
            r17 = r8
        L_0x0056:
            int r8 = a(r13, r15, r3)     // Catch:{ Exception -> 0x00a2 }
            dvw r9 = new dvw     // Catch:{ Exception -> 0x00a2 }
            r9.<init>()     // Catch:{ Exception -> 0x00a2 }
            r14 = 1
            r9.d = r14     // Catch:{ Exception -> 0x00a2 }
            r9.b = r8     // Catch:{ Exception -> 0x00a2 }
            r9.f = r10     // Catch:{ Exception -> 0x00a2 }
            r14 = 6
            r9.c = r14     // Catch:{ Exception -> 0x00a2 }
            r4.add(r9)     // Catch:{ Exception -> 0x00a2 }
            com.autonavi.minimap.route.bus.localbus.overlay.RouteBusPointOverlay r9 = r1.d     // Catch:{ Exception -> 0x00a2 }
            r14 = 0
            r13 = r13[r14]     // Catch:{ Exception -> 0x00a2 }
            r15 = r15[r14]     // Catch:{ Exception -> 0x00a2 }
            r14 = -999(0xfffffffffffffc19, float:NaN)
            r18 = r11
            r11 = 4
            com.autonavi.minimap.base.overlay.PointOverlayItem r9 = a(r9, r13, r15, r14, r11)     // Catch:{ Exception -> 0x00a2 }
            dvw r11 = new dvw     // Catch:{ Exception -> 0x00a2 }
            r11.<init>()     // Catch:{ Exception -> 0x00a2 }
            r11.e = r9     // Catch:{ Exception -> 0x00a2 }
            r9 = 0
            r11.d = r9     // Catch:{ Exception -> 0x00a2 }
            r11.b = r8     // Catch:{ Exception -> 0x00a2 }
            int r8 = r12 + 1
            r11.a = r12     // Catch:{ Exception -> 0x009e }
            r11.f = r10     // Catch:{ Exception -> 0x009e }
            r9 = 14
            r11.c = r9     // Catch:{ Exception -> 0x009e }
            r4.add(r11)     // Catch:{ Exception -> 0x009e }
            int r10 = r10 + 1
            r12 = r8
            r8 = r17
            r11 = r18
            goto L_0x0015
        L_0x009e:
            r0 = move-exception
            r2 = r0
            r12 = r8
            goto L_0x00a4
        L_0x00a2:
            r0 = move-exception
            r2 = r0
        L_0x00a4:
            r2.printStackTrace()
            return r12
        L_0x00a8:
            r17 = r8
            com.autonavi.bundle.routecommon.entity.BusPath$TaxiBusPath r8 = r2.taxiBusPath
            if (r8 == 0) goto L_0x00fc
            boolean r10 = r8.isStart
            if (r10 == 0) goto L_0x00fc
            int[] r10 = r8.mXs
            int[] r13 = r8.mYs
            r14 = 0
            r15 = r10[r14]
            r9 = r13[r14]
            if (r5 != r15) goto L_0x00c5
            if (r6 == r9) goto L_0x00c0
            goto L_0x00c5
        L_0x00c0:
            r20 = r7
            r19 = r11
            goto L_0x00e3
        L_0x00c5:
            r19 = r11
            r14 = 2
            int[] r11 = new int[r14]
            r20 = r7
            int[] r7 = new int[r14]
            r14 = 0
            r11[r14] = r5
            r7[r14] = r6
            r14 = 1
            r11[r14] = r15
            r7[r14] = r9
            if (r5 <= 0) goto L_0x00e3
            if (r6 <= 0) goto L_0x00e3
            boolean r9 = r2.isExtBusStartCityPath
            if (r9 != 0) goto L_0x00e3
            a(r3, r11, r7)
        L_0x00e3:
            a(r8, r3)
            int r7 = a(r10, r13, r3)
            dvw r9 = new dvw
            r9.<init>()
            r10 = 1
            r9.d = r10
            r9.b = r7
            r7 = 15
            r9.c = r7
            r4.add(r9)
            goto L_0x0100
        L_0x00fc:
            r20 = r7
            r19 = r11
        L_0x0100:
            r9 = r19
            r7 = 0
        L_0x0103:
            int r10 = r2.mSectionNum
            if (r7 >= r10) goto L_0x03a3
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r9 = r2.mPathSections     // Catch:{ Exception -> 0x039b }
            r9 = r9[r7]     // Catch:{ Exception -> 0x039b }
            com.autonavi.bundle.routecommon.entity.BusPath$WalkPath r10 = r9.walk_path     // Catch:{ Exception -> 0x039b }
            int r11 = r9.mFootLength     // Catch:{ Exception -> 0x039b }
            if (r11 > 0) goto L_0x0124
            if (r10 == 0) goto L_0x011e
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r11 = r10.infolist     // Catch:{ Exception -> 0x0118 }
            if (r11 == 0) goto L_0x011e
            goto L_0x0124
        L_0x0118:
            r0 = move-exception
            r1 = r0
            r22 = r12
            goto L_0x039f
        L_0x011e:
            r24 = r10
            r22 = r12
            goto L_0x02dc
        L_0x0124:
            if (r10 == 0) goto L_0x0256
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r13 = r10.infolist     // Catch:{ Exception -> 0x039b }
            if (r13 == 0) goto L_0x0256
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r13 = r10.infolist     // Catch:{ Exception -> 0x039b }
            java.util.ArrayList r14 = new java.util.ArrayList     // Catch:{ Exception -> 0x039b }
            r14.<init>()     // Catch:{ Exception -> 0x039b }
            r15 = 0
        L_0x0132:
            int r11 = r13.size()     // Catch:{ Exception -> 0x039b }
            if (r15 >= r11) goto L_0x018c
            java.lang.Object r11 = r13.get(r15)     // Catch:{ Exception -> 0x039b }
            com.autonavi.bundle.routecommon.entity.OnFootNaviSection r11 = (com.autonavi.bundle.routecommon.entity.OnFootNaviSection) r11     // Catch:{ Exception -> 0x039b }
            if (r11 == 0) goto L_0x0179
            r21 = r13
            int[] r13 = r11.mXs     // Catch:{ Exception -> 0x039b }
            if (r13 == 0) goto L_0x0172
            int[] r13 = r11.mXs     // Catch:{ Exception -> 0x039b }
            int r13 = r13.length     // Catch:{ Exception -> 0x039b }
            r22 = r12
            r12 = 0
        L_0x014c:
            if (r12 >= r13) goto L_0x016d
            r23 = r13
            android.graphics.Point r13 = new android.graphics.Point     // Catch:{ Exception -> 0x0399 }
            r24 = r10
            int[] r10 = r11.mXs     // Catch:{ Exception -> 0x0399 }
            r10 = r10[r12]     // Catch:{ Exception -> 0x0399 }
            r25 = r8
            int[] r8 = r11.mYs     // Catch:{ Exception -> 0x0399 }
            r8 = r8[r12]     // Catch:{ Exception -> 0x0399 }
            r13.<init>(r10, r8)     // Catch:{ Exception -> 0x0399 }
            r14.add(r13)     // Catch:{ Exception -> 0x0399 }
            int r12 = r12 + 1
            r13 = r23
            r10 = r24
            r8 = r25
            goto L_0x014c
        L_0x016d:
            r25 = r8
            r24 = r10
            goto L_0x0181
        L_0x0172:
            r25 = r8
            r24 = r10
            r22 = r12
            goto L_0x0181
        L_0x0179:
            r25 = r8
            r24 = r10
            r22 = r12
            r21 = r13
        L_0x0181:
            int r15 = r15 + 1
            r13 = r21
            r12 = r22
            r10 = r24
            r8 = r25
            goto L_0x0132
        L_0x018c:
            r25 = r8
            r24 = r10
            r22 = r12
            int r8 = r14.size()     // Catch:{ Exception -> 0x0399 }
            int[] r10 = new int[r8]     // Catch:{ Exception -> 0x0399 }
            int[] r11 = new int[r8]     // Catch:{ Exception -> 0x0399 }
            r12 = 0
        L_0x019b:
            if (r12 >= r8) goto L_0x01b4
            java.lang.Object r13 = r14.get(r12)     // Catch:{ Exception -> 0x0399 }
            android.graphics.Point r13 = (android.graphics.Point) r13     // Catch:{ Exception -> 0x0399 }
            int r13 = r13.x     // Catch:{ Exception -> 0x0399 }
            r10[r12] = r13     // Catch:{ Exception -> 0x0399 }
            java.lang.Object r13 = r14.get(r12)     // Catch:{ Exception -> 0x0399 }
            android.graphics.Point r13 = (android.graphics.Point) r13     // Catch:{ Exception -> 0x0399 }
            int r13 = r13.y     // Catch:{ Exception -> 0x0399 }
            r11[r12] = r13     // Catch:{ Exception -> 0x0399 }
            int r12 = r12 + 1
            goto L_0x019b
        L_0x01b4:
            com.autonavi.minimap.route.bus.localbus.overlay.RouteBusLineOverlay r12 = r1.a     // Catch:{ Exception -> 0x0399 }
            com.autonavi.common.model.GeoPoint[] r13 = a(r10, r11)     // Catch:{ Exception -> 0x0399 }
            int r12 = r12.createAndAddLinkPathItem(r13)     // Catch:{ Exception -> 0x0399 }
            dvw r13 = new dvw     // Catch:{ Exception -> 0x0399 }
            r13.<init>()     // Catch:{ Exception -> 0x0399 }
            r14 = 1
            r13.d = r14     // Catch:{ Exception -> 0x0399 }
            r13.b = r12     // Catch:{ Exception -> 0x0399 }
            r13.f = r7     // Catch:{ Exception -> 0x0399 }
            boolean r12 = defpackage.dwk.a(r2, r7)     // Catch:{ Exception -> 0x0399 }
            if (r12 == 0) goto L_0x01d5
            r12 = 12
            r13.c = r12     // Catch:{ Exception -> 0x0399 }
            goto L_0x01d8
        L_0x01d5:
            r12 = 0
            r13.c = r12     // Catch:{ Exception -> 0x0399 }
        L_0x01d8:
            r4.add(r13)     // Catch:{ Exception -> 0x0399 }
            if (r7 <= 0) goto L_0x0226
            r12 = 2
            int[] r13 = new int[r12]     // Catch:{ Exception -> 0x0399 }
            int[] r14 = new int[r12]     // Catch:{ Exception -> 0x0399 }
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r12 = r2.mPathSections     // Catch:{ Exception -> 0x0399 }
            int r15 = r7 + -1
            r12 = r12[r15]     // Catch:{ Exception -> 0x0399 }
            int[] r12 = r12.mXs     // Catch:{ Exception -> 0x0399 }
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r1 = r2.mPathSections     // Catch:{ Exception -> 0x0399 }
            r1 = r1[r15]     // Catch:{ Exception -> 0x0399 }
            int[] r1 = r1.mXs     // Catch:{ Exception -> 0x0399 }
            int r1 = r1.length     // Catch:{ Exception -> 0x0399 }
            r16 = 1
            int r1 = r1 + -1
            r1 = r12[r1]     // Catch:{ Exception -> 0x0399 }
            r12 = 0
            r13[r12] = r1     // Catch:{ Exception -> 0x0399 }
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r1 = r2.mPathSections     // Catch:{ Exception -> 0x0399 }
            r1 = r1[r15]     // Catch:{ Exception -> 0x0399 }
            int[] r1 = r1.mYs     // Catch:{ Exception -> 0x0399 }
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r12 = r2.mPathSections     // Catch:{ Exception -> 0x0399 }
            r12 = r12[r15]     // Catch:{ Exception -> 0x0399 }
            int[] r12 = r12.mYs     // Catch:{ Exception -> 0x0399 }
            int r12 = r12.length     // Catch:{ Exception -> 0x0399 }
            r15 = 1
            int r12 = r12 - r15
            r1 = r1[r12]     // Catch:{ Exception -> 0x0399 }
            r12 = 0
            r14[r12] = r1     // Catch:{ Exception -> 0x0399 }
            r1 = r10[r12]     // Catch:{ Exception -> 0x0399 }
            r13[r15] = r1     // Catch:{ Exception -> 0x0399 }
            r1 = r11[r12]     // Catch:{ Exception -> 0x0399 }
            r14[r15] = r1     // Catch:{ Exception -> 0x0399 }
            r1 = r13[r12]     // Catch:{ Exception -> 0x0399 }
            r12 = r13[r15]     // Catch:{ Exception -> 0x0399 }
            if (r1 != r12) goto L_0x0223
            r1 = 0
            r12 = r14[r1]     // Catch:{ Exception -> 0x0399 }
            r1 = r14[r15]     // Catch:{ Exception -> 0x0399 }
            if (r12 == r1) goto L_0x0226
        L_0x0223:
            a(r3, r13, r14)     // Catch:{ Exception -> 0x0399 }
        L_0x0226:
            r1 = 2
            int[] r12 = new int[r1]     // Catch:{ Exception -> 0x0399 }
            int[] r13 = new int[r1]     // Catch:{ Exception -> 0x0399 }
            int r8 = r8 + -1
            r1 = r10[r8]     // Catch:{ Exception -> 0x0399 }
            r14 = 0
            r12[r14] = r1     // Catch:{ Exception -> 0x0399 }
            r1 = r11[r8]     // Catch:{ Exception -> 0x0399 }
            r13[r14] = r1     // Catch:{ Exception -> 0x0399 }
            int[] r1 = r9.mXs     // Catch:{ Exception -> 0x0399 }
            r1 = r1[r14]     // Catch:{ Exception -> 0x0399 }
            r8 = 1
            r12[r8] = r1     // Catch:{ Exception -> 0x0399 }
            int[] r1 = r9.mYs     // Catch:{ Exception -> 0x0399 }
            r1 = r1[r14]     // Catch:{ Exception -> 0x0399 }
            r13[r8] = r1     // Catch:{ Exception -> 0x0399 }
            r1 = r12[r14]     // Catch:{ Exception -> 0x0399 }
            r15 = r12[r8]     // Catch:{ Exception -> 0x0399 }
            if (r1 != r15) goto L_0x024f
            r1 = r13[r14]     // Catch:{ Exception -> 0x0399 }
            r14 = r13[r8]     // Catch:{ Exception -> 0x0399 }
            if (r1 == r14) goto L_0x0252
        L_0x024f:
            a(r3, r12, r13)     // Catch:{ Exception -> 0x0399 }
        L_0x0252:
            r8 = r25
        L_0x0254:
            r13 = 1
            goto L_0x02bc
        L_0x0256:
            r25 = r8
            r24 = r10
            r22 = r12
            r1 = 2
            int[] r10 = new int[r1]     // Catch:{ Exception -> 0x0399 }
            int[] r11 = new int[r1]     // Catch:{ Exception -> 0x0399 }
            if (r7 <= 0) goto L_0x028f
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r1 = r2.mPathSections     // Catch:{ Exception -> 0x0399 }
            int r8 = r7 + -1
            r1 = r1[r8]     // Catch:{ Exception -> 0x0399 }
            int[] r1 = r1.mXs     // Catch:{ Exception -> 0x0399 }
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r12 = r2.mPathSections     // Catch:{ Exception -> 0x0399 }
            r12 = r12[r8]     // Catch:{ Exception -> 0x0399 }
            int[] r12 = r12.mXs     // Catch:{ Exception -> 0x0399 }
            int r12 = r12.length     // Catch:{ Exception -> 0x0399 }
            r13 = 1
            int r12 = r12 - r13
            r1 = r1[r12]     // Catch:{ Exception -> 0x0399 }
            r12 = 0
            r10[r12] = r1     // Catch:{ Exception -> 0x0399 }
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r1 = r2.mPathSections     // Catch:{ Exception -> 0x0399 }
            r1 = r1[r8]     // Catch:{ Exception -> 0x0399 }
            int[] r1 = r1.mYs     // Catch:{ Exception -> 0x0399 }
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r12 = r2.mPathSections     // Catch:{ Exception -> 0x0399 }
            r8 = r12[r8]     // Catch:{ Exception -> 0x0399 }
            int[] r8 = r8.mYs     // Catch:{ Exception -> 0x0399 }
            int r8 = r8.length     // Catch:{ Exception -> 0x0399 }
            r12 = 1
            int r8 = r8 - r12
            r1 = r1[r8]     // Catch:{ Exception -> 0x0399 }
            r8 = 0
            r11[r8] = r1     // Catch:{ Exception -> 0x0399 }
            r1 = 0
            goto L_0x0294
        L_0x028f:
            r1 = 0
            r10[r1] = r5     // Catch:{ Exception -> 0x0399 }
            r11[r1] = r6     // Catch:{ Exception -> 0x0399 }
        L_0x0294:
            if (r7 != 0) goto L_0x02ac
            if (r25 == 0) goto L_0x02ac
            r8 = r25
            boolean r12 = r8.isStart     // Catch:{ Exception -> 0x0399 }
            if (r12 == 0) goto L_0x02ae
            int[] r12 = r8.mXs     // Catch:{ Exception -> 0x0399 }
            r12 = r12[r1]     // Catch:{ Exception -> 0x0399 }
            r13 = 1
            r10[r13] = r12     // Catch:{ Exception -> 0x0399 }
            int[] r12 = r8.mYs     // Catch:{ Exception -> 0x0399 }
            r12 = r12[r1]     // Catch:{ Exception -> 0x0399 }
            r11[r13] = r12     // Catch:{ Exception -> 0x0399 }
            goto L_0x0254
        L_0x02ac:
            r8 = r25
        L_0x02ae:
            int[] r1 = r9.mXs     // Catch:{ Exception -> 0x0399 }
            r12 = 0
            r1 = r1[r12]     // Catch:{ Exception -> 0x0399 }
            r13 = 1
            r10[r13] = r1     // Catch:{ Exception -> 0x0399 }
            int[] r1 = r9.mYs     // Catch:{ Exception -> 0x0399 }
            r1 = r1[r12]     // Catch:{ Exception -> 0x0399 }
            r11[r13] = r1     // Catch:{ Exception -> 0x0399 }
        L_0x02bc:
            int r1 = a(r3, r10, r11)     // Catch:{ Exception -> 0x0399 }
            dvw r10 = new dvw     // Catch:{ Exception -> 0x0399 }
            r10.<init>()     // Catch:{ Exception -> 0x0399 }
            r10.d = r13     // Catch:{ Exception -> 0x0399 }
            r10.b = r1     // Catch:{ Exception -> 0x0399 }
            r10.f = r7     // Catch:{ Exception -> 0x0399 }
            boolean r1 = defpackage.dwk.a(r2, r7)     // Catch:{ Exception -> 0x0399 }
            if (r1 == 0) goto L_0x02d6
            r1 = 12
            r10.c = r1     // Catch:{ Exception -> 0x0399 }
            goto L_0x02d9
        L_0x02d6:
            r1 = 0
            r10.c = r1     // Catch:{ Exception -> 0x0399 }
        L_0x02d9:
            r4.add(r10)     // Catch:{ Exception -> 0x0399 }
        L_0x02dc:
            if (r8 == 0) goto L_0x0391
            boolean r1 = r8.isStart     // Catch:{ Exception -> 0x0399 }
            if (r1 == 0) goto L_0x0391
            if (r7 != 0) goto L_0x0391
            int[] r1 = r8.mXs     // Catch:{ Exception -> 0x0399 }
            if (r1 == 0) goto L_0x0391
            int[] r1 = r8.mYs     // Catch:{ Exception -> 0x0399 }
            if (r1 == 0) goto L_0x0391
            int[] r1 = r8.mXs     // Catch:{ Exception -> 0x0399 }
            int r1 = r1.length     // Catch:{ Exception -> 0x0399 }
            if (r1 <= 0) goto L_0x0391
            int[] r1 = r8.mYs     // Catch:{ Exception -> 0x0399 }
            int r1 = r1.length     // Catch:{ Exception -> 0x0399 }
            if (r1 <= 0) goto L_0x0391
            r1 = 2
            int[] r10 = new int[r1]     // Catch:{ Exception -> 0x0399 }
            int[] r11 = new int[r1]     // Catch:{ Exception -> 0x0399 }
            int[] r1 = r8.mXs     // Catch:{ Exception -> 0x0399 }
            int[] r12 = r8.mXs     // Catch:{ Exception -> 0x0399 }
            int r12 = r12.length     // Catch:{ Exception -> 0x0399 }
            r13 = 1
            int r12 = r12 - r13
            r1 = r1[r12]     // Catch:{ Exception -> 0x0399 }
            r12 = 0
            r10[r12] = r1     // Catch:{ Exception -> 0x0399 }
            int[] r1 = r8.mYs     // Catch:{ Exception -> 0x0399 }
            int[] r14 = r8.mYs     // Catch:{ Exception -> 0x0399 }
            int r14 = r14.length     // Catch:{ Exception -> 0x0399 }
            int r14 = r14 - r13
            r1 = r1[r14]     // Catch:{ Exception -> 0x0399 }
            r11[r12] = r1     // Catch:{ Exception -> 0x0399 }
            if (r24 == 0) goto L_0x0374
            r1 = r24
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r13 = r1.infolist     // Catch:{ Exception -> 0x0399 }
            if (r13 == 0) goto L_0x0374
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r13 = r1.infolist     // Catch:{ Exception -> 0x0399 }
            java.lang.Object r13 = r13.get(r12)     // Catch:{ Exception -> 0x0399 }
            if (r13 == 0) goto L_0x0374
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r13 = r1.infolist     // Catch:{ Exception -> 0x0399 }
            java.lang.Object r13 = r13.get(r12)     // Catch:{ Exception -> 0x0399 }
            com.autonavi.bundle.routecommon.entity.OnFootNaviSection r13 = (com.autonavi.bundle.routecommon.entity.OnFootNaviSection) r13     // Catch:{ Exception -> 0x0399 }
            int[] r13 = r13.mXs     // Catch:{ Exception -> 0x0399 }
            if (r13 == 0) goto L_0x0374
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r13 = r1.infolist     // Catch:{ Exception -> 0x0399 }
            java.lang.Object r13 = r13.get(r12)     // Catch:{ Exception -> 0x0399 }
            com.autonavi.bundle.routecommon.entity.OnFootNaviSection r13 = (com.autonavi.bundle.routecommon.entity.OnFootNaviSection) r13     // Catch:{ Exception -> 0x0399 }
            int[] r13 = r13.mYs     // Catch:{ Exception -> 0x0399 }
            if (r13 == 0) goto L_0x0374
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r13 = r1.infolist     // Catch:{ Exception -> 0x0399 }
            java.lang.Object r13 = r13.get(r12)     // Catch:{ Exception -> 0x0399 }
            com.autonavi.bundle.routecommon.entity.OnFootNaviSection r13 = (com.autonavi.bundle.routecommon.entity.OnFootNaviSection) r13     // Catch:{ Exception -> 0x0399 }
            int[] r13 = r13.mXs     // Catch:{ Exception -> 0x0399 }
            int r13 = r13.length     // Catch:{ Exception -> 0x0399 }
            if (r13 <= 0) goto L_0x0374
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r13 = r1.infolist     // Catch:{ Exception -> 0x0399 }
            java.lang.Object r13 = r13.get(r12)     // Catch:{ Exception -> 0x0399 }
            com.autonavi.bundle.routecommon.entity.OnFootNaviSection r13 = (com.autonavi.bundle.routecommon.entity.OnFootNaviSection) r13     // Catch:{ Exception -> 0x0399 }
            int[] r13 = r13.mYs     // Catch:{ Exception -> 0x0399 }
            int r13 = r13.length     // Catch:{ Exception -> 0x0399 }
            if (r13 <= 0) goto L_0x0374
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r13 = r1.infolist     // Catch:{ Exception -> 0x0399 }
            java.lang.Object r13 = r13.get(r12)     // Catch:{ Exception -> 0x0399 }
            com.autonavi.bundle.routecommon.entity.OnFootNaviSection r13 = (com.autonavi.bundle.routecommon.entity.OnFootNaviSection) r13     // Catch:{ Exception -> 0x0399 }
            int[] r13 = r13.mXs     // Catch:{ Exception -> 0x0399 }
            r13 = r13[r12]     // Catch:{ Exception -> 0x0399 }
            r14 = 1
            r10[r14] = r13     // Catch:{ Exception -> 0x0399 }
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r1 = r1.infolist     // Catch:{ Exception -> 0x0399 }
            java.lang.Object r1 = r1.get(r12)     // Catch:{ Exception -> 0x0399 }
            com.autonavi.bundle.routecommon.entity.OnFootNaviSection r1 = (com.autonavi.bundle.routecommon.entity.OnFootNaviSection) r1     // Catch:{ Exception -> 0x0399 }
            int[] r1 = r1.mYs     // Catch:{ Exception -> 0x0399 }
            r1 = r1[r12]     // Catch:{ Exception -> 0x0399 }
            r12 = 1
            r11[r12] = r1     // Catch:{ Exception -> 0x0399 }
            r12 = 0
            r13 = 1
            goto L_0x0382
        L_0x0374:
            int[] r1 = r9.mXs     // Catch:{ Exception -> 0x0399 }
            r12 = 0
            r1 = r1[r12]     // Catch:{ Exception -> 0x0399 }
            r13 = 1
            r10[r13] = r1     // Catch:{ Exception -> 0x0399 }
            int[] r1 = r9.mYs     // Catch:{ Exception -> 0x0399 }
            r1 = r1[r12]     // Catch:{ Exception -> 0x0399 }
            r11[r13] = r1     // Catch:{ Exception -> 0x0399 }
        L_0x0382:
            r1 = r10[r12]     // Catch:{ Exception -> 0x0399 }
            r14 = r10[r13]     // Catch:{ Exception -> 0x0399 }
            if (r1 != r14) goto L_0x038e
            r1 = r11[r12]     // Catch:{ Exception -> 0x0399 }
            r12 = r11[r13]     // Catch:{ Exception -> 0x0399 }
            if (r1 == r12) goto L_0x0391
        L_0x038e:
            a(r3, r10, r11)     // Catch:{ Exception -> 0x0399 }
        L_0x0391:
            int r7 = r7 + 1
            r12 = r22
            r1 = r27
            goto L_0x0103
        L_0x0399:
            r0 = move-exception
            goto L_0x039e
        L_0x039b:
            r0 = move-exception
            r22 = r12
        L_0x039e:
            r1 = r0
        L_0x039f:
            r1.printStackTrace()
            return r22
        L_0x03a3:
            r22 = r12
            int r1 = r2.mEndWalkLength
            if (r1 <= 0) goto L_0x0480
            if (r9 == 0) goto L_0x0480
            int[] r1 = r9.mXs
            int r1 = r1.length
            r5 = 1
            int r1 = r1 - r5
            com.autonavi.bundle.routecommon.entity.BusPath$WalkPath r5 = r2.endwalk
            if (r5 == 0) goto L_0x0451
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r7 = r5.infolist
            if (r7 == 0) goto L_0x0451
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            r10 = 0
        L_0x03be:
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r11 = r5.infolist
            int r11 = r11.size()
            if (r10 >= r11) goto L_0x03f0
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r11 = r5.infolist
            java.lang.Object r11 = r11.get(r10)
            com.autonavi.bundle.routecommon.entity.OnFootNaviSection r11 = (com.autonavi.bundle.routecommon.entity.OnFootNaviSection) r11
            if (r11 == 0) goto L_0x03ed
            int[] r12 = r11.mXs
            if (r12 == 0) goto L_0x03ed
            int[] r12 = r11.mXs
            int r12 = r12.length
            r13 = 0
        L_0x03d8:
            if (r13 >= r12) goto L_0x03ed
            android.graphics.Point r14 = new android.graphics.Point
            int[] r15 = r11.mXs
            r15 = r15[r13]
            int[] r6 = r11.mYs
            r6 = r6[r13]
            r14.<init>(r15, r6)
            r7.add(r14)
            int r13 = r13 + 1
            goto L_0x03d8
        L_0x03ed:
            int r10 = r10 + 1
            goto L_0x03be
        L_0x03f0:
            int r5 = r7.size()
            int[] r6 = new int[r5]
            int[] r10 = new int[r5]
            r11 = 0
        L_0x03f9:
            if (r11 >= r5) goto L_0x0412
            java.lang.Object r12 = r7.get(r11)
            android.graphics.Point r12 = (android.graphics.Point) r12
            int r12 = r12.x
            r6[r11] = r12
            java.lang.Object r12 = r7.get(r11)
            android.graphics.Point r12 = (android.graphics.Point) r12
            int r12 = r12.y
            r10[r11] = r12
            int r11 = r11 + 1
            goto L_0x03f9
        L_0x0412:
            r11 = 2
            int[] r5 = new int[r11]
            int[] r7 = new int[r11]
            int[] r11 = r9.mXs
            r11 = r11[r1]
            r12 = 0
            r5[r12] = r11
            int[] r11 = r9.mYs
            r1 = r11[r1]
            r7[r12] = r1
            r1 = r6[r12]
            r11 = 1
            r5[r11] = r1
            r1 = r10[r12]
            r7[r11] = r1
            r1 = r5[r12]
            r13 = r5[r11]
            if (r1 != r13) goto L_0x0439
            r1 = r7[r12]
            r12 = r7[r11]
            if (r1 == r12) goto L_0x043c
        L_0x0439:
            a(r3, r5, r7)
        L_0x043c:
            int r1 = a(r3, r6, r10)
            dvw r5 = new dvw
            r5.<init>()
            r5.d = r11
            r5.b = r1
            r1 = 13
            r5.c = r1
            r4.add(r5)
            goto L_0x0480
        L_0x0451:
            r5 = 2
            int[] r6 = new int[r5]
            int[] r7 = new int[r5]
            int[] r5 = r9.mXs
            r5 = r5[r1]
            r10 = 0
            r6[r10] = r5
            int[] r5 = r9.mYs
            r1 = r5[r1]
            r7[r10] = r1
            r1 = 1
            r6[r1] = r20
            r7[r1] = r17
            if (r20 <= 0) goto L_0x0480
            if (r17 <= 0) goto L_0x0480
            int r5 = a(r3, r6, r7)
            dvw r6 = new dvw
            r6.<init>()
            r6.d = r1
            r6.b = r5
            r1 = 13
            r6.c = r1
            r4.add(r6)
        L_0x0480:
            if (r8 == 0) goto L_0x04dc
            boolean r1 = r8.isStart
            if (r1 != 0) goto L_0x04dc
            if (r9 == 0) goto L_0x04dc
            int[] r1 = r9.mXs
            int[] r5 = r9.mXs
            int r5 = r5.length
            r6 = 1
            int r5 = r5 - r6
            r1 = r1[r5]
            int[] r5 = r9.mYs
            int[] r7 = r9.mYs
            int r7 = r7.length
            int r7 = r7 - r6
            r5 = r5[r7]
            com.autonavi.bundle.routecommon.entity.BusPath$TaxiBusPath r6 = r2.taxiBusPath
            int[] r6 = r6.mXs
            com.autonavi.bundle.routecommon.entity.BusPath$TaxiBusPath r7 = r2.taxiBusPath
            int[] r7 = r7.mYs
            r9 = 0
            r10 = r6[r9]
            r11 = r7[r9]
            if (r1 == r10) goto L_0x04c4
            if (r5 == r11) goto L_0x04c4
            if (r1 <= 0) goto L_0x04c4
            if (r5 <= 0) goto L_0x04c4
            boolean r2 = r2.isExtBusStartCityPath
            if (r2 != 0) goto L_0x04c4
            r2 = 2
            int[] r12 = new int[r2]
            int[] r2 = new int[r2]
            r12[r9] = r1
            r2[r9] = r5
            r1 = 1
            r12[r1] = r10
            r2[r1] = r11
            a(r3, r12, r2)
            goto L_0x04c5
        L_0x04c4:
            r1 = 1
        L_0x04c5:
            int r2 = a(r8, r3)
            dvw r5 = new dvw
            r5.<init>()
            r5.d = r1
            r5.b = r2
            r1 = 16
            r5.c = r1
            r4.add(r5)
            a(r6, r7, r3)
        L_0x04dc:
            return r22
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dxj.a(com.autonavi.bundle.routecommon.entity.BusPath, com.autonavi.minimap.route.bus.localbus.overlay.RouteBusLineOverlay, java.util.List, int):int");
    }

    /* JADX WARNING: Removed duplicated region for block: B:147:0x03d7  */
    /* JADX WARNING: Removed duplicated region for block: B:154:0x0408  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(float r21, boolean r22) {
        /*
            r20 = this;
            r1 = r20
            com.autonavi.bundle.routecommon.entity.IBusRouteResult r3 = r1.h
            if (r3 == 0) goto L_0x0538
            com.autonavi.minimap.route.bus.localbus.overlay.RouteBusPointOverlay r3 = r1.c
            if (r3 == 0) goto L_0x0538
            com.autonavi.minimap.route.bus.localbus.overlay.RouteBusPointOverlay r3 = r1.e
            if (r3 == 0) goto L_0x0538
            com.autonavi.minimap.route.bus.localbus.overlay.RouteBusLineOverlay r3 = r1.a
            if (r3 != 0) goto L_0x0014
            goto L_0x0538
        L_0x0014:
            com.autonavi.bundle.routecommon.entity.IBusRouteResult r3 = r1.h
            com.autonavi.bundle.routecommon.entity.BusPaths r3 = r3.getBusPathsResult()
            if (r3 != 0) goto L_0x001d
            return
        L_0x001d:
            com.autonavi.bundle.routecommon.entity.IBusRouteResult r3 = r1.h
            com.autonavi.bundle.routecommon.entity.BusPath r3 = r3.getFocusBusPath()
            if (r3 == 0) goto L_0x0536
            int r4 = r3.mSectionNum
            if (r4 != 0) goto L_0x002b
            goto L_0x0536
        L_0x002b:
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            com.autonavi.bundle.routecommon.entity.BusPath$TaxiBusPath r7 = r3.taxiBusPath
            com.autonavi.minimap.route.bus.localbus.overlay.RouteBusPointOverlay r8 = r1.c
            r8.clear()
            com.autonavi.minimap.route.bus.localbus.overlay.RouteBusPointOverlay r8 = r1.e
            r8.clear()
            com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay r8 = r1.b
            r9 = 1097859072(0x41700000, float:15.0)
            if (r8 == 0) goto L_0x005b
            int r8 = (r21 > r9 ? 1 : (r21 == r9 ? 0 : -1))
            if (r8 >= 0) goto L_0x0056
            com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay r8 = r1.b
            r8.clearFilter()
            goto L_0x005b
        L_0x0056:
            com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay r8 = r1.b
            r8.addFilter()
        L_0x005b:
            r8 = 1092616192(0x41200000, float:10.0)
            int r8 = (r21 > r8 ? 1 : (r21 == r8 ? 0 : -1))
            r10 = 0
            if (r8 < 0) goto L_0x00c1
            if (r7 == 0) goto L_0x00c1
            boolean r11 = r7.isStart
            if (r11 == 0) goto L_0x00c1
            int[] r11 = r7.mXs
            int[] r12 = r7.mYs
            dxj$a r13 = new dxj$a
            r14 = r11[r10]
            r15 = r12[r10]
            int r9 = com.autonavi.minimap.R.drawable.taxi_turnpoint
            r13.<init>(r14, r15, r9)
            r5.add(r13)
            if (r8 < 0) goto L_0x00c1
            dwm r9 = new dwm
            r9.<init>()
            com.autonavi.common.model.GeoPoint r13 = new com.autonavi.common.model.GeoPoint
            r11 = r11[r10]
            r12 = r12[r10]
            r13.<init>(r11, r12)
            r9.a = r13
            android.content.Context r11 = r1.i
            int r12 = com.autonavi.minimap.R.string.route_taxi
            java.lang.String r11 = r11.getString(r12)
            r9.b = r11
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            android.content.Context r12 = r1.i
            int r13 = com.autonavi.minimap.R.string.route_to
            java.lang.String r12 = r12.getString(r13)
            r11.append(r12)
            java.lang.String r12 = " "
            r11.append(r12)
            java.lang.String r12 = r7.mEndName
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            r9.c = r11
            java.lang.String r11 = r7.color
            int r11 = a(r11)
            r9.f = r11
            r4.add(r9)
        L_0x00c1:
            r9 = 0
        L_0x00c2:
            int r11 = r3.mSectionNum
            if (r9 >= r11) goto L_0x0349
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r11 = r3.mPathSections     // Catch:{ Exception -> 0x0337 }
            r11 = r11[r9]     // Catch:{ Exception -> 0x0337 }
            int[] r15 = r11.mXs     // Catch:{ Exception -> 0x0337 }
            int[] r14 = r11.mYs     // Catch:{ Exception -> 0x0337 }
            if (r8 < 0) goto L_0x02d6
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0337 }
            r13.<init>()     // Catch:{ Exception -> 0x0337 }
            java.lang.String r12 = r11.mStartName     // Catch:{ Exception -> 0x0337 }
            r13.append(r12)     // Catch:{ Exception -> 0x0337 }
            java.lang.String r12 = " "
            r13.append(r12)     // Catch:{ Exception -> 0x0337 }
            java.lang.String r12 = r13.toString()     // Catch:{ Exception -> 0x0337 }
            dwm r13 = new dwm     // Catch:{ Exception -> 0x0337 }
            r13.<init>()     // Catch:{ Exception -> 0x0337 }
            com.autonavi.common.model.GeoPoint r1 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x0337 }
            r17 = r7
            r7 = r15[r10]     // Catch:{ Exception -> 0x02d3 }
            r18 = r6
            r6 = r14[r10]     // Catch:{ Exception -> 0x02ce }
            r1.<init>(r7, r6)     // Catch:{ Exception -> 0x02ce }
            r13.a = r1     // Catch:{ Exception -> 0x02ce }
            r13.b = r12     // Catch:{ Exception -> 0x02ce }
            if (r8 < 0) goto L_0x01b3
            r1 = 1097859072(0x41700000, float:15.0)
            int r6 = (r21 > r1 ? 1 : (r21 == r1 ? 0 : -1))
            if (r6 >= 0) goto L_0x01b3
            r1 = 0
            int r6 = r9 + -1
            if (r6 < 0) goto L_0x0115
            int r7 = r3.mSectionNum     // Catch:{ Exception -> 0x010f }
            if (r6 >= r7) goto L_0x0115
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r1 = r3.mPathSections     // Catch:{ Exception -> 0x010f }
            r1 = r1[r6]     // Catch:{ Exception -> 0x010f }
            goto L_0x0115
        L_0x010f:
            r0 = move-exception
            r6 = r0
            r1 = r18
            goto L_0x033c
        L_0x0115:
            if (r1 == 0) goto L_0x0138
            int r6 = r11.mBusType     // Catch:{ Exception -> 0x010f }
            r7 = 12
            if (r6 == r7) goto L_0x0131
            int r6 = r11.mBusType     // Catch:{ Exception -> 0x010f }
            r7 = 13
            if (r6 == r7) goto L_0x0131
            java.lang.String r6 = r1.getSectionFastSimpleName()     // Catch:{ Exception -> 0x010f }
            r13.d = r6     // Catch:{ Exception -> 0x010f }
            java.lang.String r1 = r1.mRouteColor     // Catch:{ Exception -> 0x010f }
            int r1 = a(r1)     // Catch:{ Exception -> 0x010f }
            r13.g = r1     // Catch:{ Exception -> 0x010f }
        L_0x0131:
            java.lang.String r1 = r11.getSectionFastSimpleName()     // Catch:{ Exception -> 0x010f }
            r13.c = r1     // Catch:{ Exception -> 0x010f }
            goto L_0x013e
        L_0x0138:
            java.lang.String r1 = r11.getSectionFastSimpleName()     // Catch:{ Exception -> 0x02ce }
            r13.c = r1     // Catch:{ Exception -> 0x02ce }
        L_0x013e:
            if (r9 != 0) goto L_0x0144
            r1 = 3
            r13.i = r1     // Catch:{ Exception -> 0x010f }
            goto L_0x0147
        L_0x0144:
            r1 = 2
            r13.i = r1     // Catch:{ Exception -> 0x02ce }
        L_0x0147:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02ce }
            r1.<init>()     // Catch:{ Exception -> 0x02ce }
            java.lang.String r6 = r11.mStartName     // Catch:{ Exception -> 0x02ce }
            r1.append(r6)     // Catch:{ Exception -> 0x02ce }
            java.lang.String r6 = r11.bus_id     // Catch:{ Exception -> 0x02ce }
            r1.append(r6)     // Catch:{ Exception -> 0x02ce }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x02ce }
            r13.h = r1     // Catch:{ Exception -> 0x02ce }
            java.lang.String r1 = r11.mRouteColor     // Catch:{ Exception -> 0x02ce }
            int r1 = a(r1)     // Catch:{ Exception -> 0x02ce }
            r13.f = r1     // Catch:{ Exception -> 0x02ce }
            r4.add(r13)     // Catch:{ Exception -> 0x02ce }
            boolean r1 = r11.isRidePath     // Catch:{ Exception -> 0x02ce }
            r13.j = r1     // Catch:{ Exception -> 0x02ce }
            if (r9 <= 0) goto L_0x019f
            int r1 = r11.mBusType     // Catch:{ Exception -> 0x02ce }
            r6 = 12
            if (r1 == r6) goto L_0x018b
            int r1 = r11.mBusType     // Catch:{ Exception -> 0x010f }
            r6 = 13
            if (r1 == r6) goto L_0x018b
            dxj$a r1 = new dxj$a     // Catch:{ Exception -> 0x010f }
            r6 = r15[r10]     // Catch:{ Exception -> 0x010f }
            r7 = r14[r10]     // Catch:{ Exception -> 0x010f }
            int r12 = com.autonavi.minimap.R.drawable.bus_turnpoint_transfer     // Catch:{ Exception -> 0x010f }
            r1.<init>(r6, r7, r12)     // Catch:{ Exception -> 0x010f }
            r5.add(r1)     // Catch:{ Exception -> 0x010f }
            r1 = r18
            goto L_0x020a
        L_0x018b:
            dxj$a r1 = new dxj$a     // Catch:{ Exception -> 0x02ce }
            r6 = r15[r10]     // Catch:{ Exception -> 0x02ce }
            r7 = r14[r10]     // Catch:{ Exception -> 0x02ce }
            int r12 = com.autonavi.minimap.R.drawable.bus_turnpoint_on     // Catch:{ Exception -> 0x02ce }
            r1.<init>(r6, r7, r12)     // Catch:{ Exception -> 0x02ce }
            r5.add(r1)     // Catch:{ Exception -> 0x02ce }
            r1 = r18
            r1.add(r11)     // Catch:{ Exception -> 0x030a }
            goto L_0x020a
        L_0x019f:
            r1 = r18
            dxj$a r6 = new dxj$a     // Catch:{ Exception -> 0x030a }
            r7 = r15[r10]     // Catch:{ Exception -> 0x030a }
            r12 = r14[r10]     // Catch:{ Exception -> 0x030a }
            int r13 = com.autonavi.minimap.R.drawable.bus_turnpoint_on     // Catch:{ Exception -> 0x030a }
            r6.<init>(r7, r12, r13)     // Catch:{ Exception -> 0x030a }
            r5.add(r6)     // Catch:{ Exception -> 0x030a }
            r1.add(r11)     // Catch:{ Exception -> 0x030a }
            goto L_0x020a
        L_0x01b3:
            r1 = r18
            java.lang.String r6 = r11.getSectionFastSimpleName()     // Catch:{ Exception -> 0x030a }
            r13.c = r6     // Catch:{ Exception -> 0x030a }
            java.lang.String r6 = ""
            r13.d = r6     // Catch:{ Exception -> 0x030a }
            java.lang.String r6 = r11.mRouteColor     // Catch:{ Exception -> 0x030a }
            int r6 = a(r6)     // Catch:{ Exception -> 0x030a }
            r13.f = r6     // Catch:{ Exception -> 0x030a }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x030a }
            r6.<init>()     // Catch:{ Exception -> 0x030a }
            java.lang.String r7 = r11.mSectionName     // Catch:{ Exception -> 0x030a }
            r6.append(r7)     // Catch:{ Exception -> 0x030a }
            java.lang.String r7 = r11.bus_id     // Catch:{ Exception -> 0x030a }
            r6.append(r7)     // Catch:{ Exception -> 0x030a }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x030a }
            r13.h = r6     // Catch:{ Exception -> 0x030a }
            if (r9 != 0) goto L_0x01e2
            r6 = 3
            r13.i = r6     // Catch:{ Exception -> 0x030a }
            goto L_0x01f6
        L_0x01e2:
            int r6 = r11.mBusType     // Catch:{ Exception -> 0x030a }
            r7 = 12
            if (r6 == r7) goto L_0x01f3
            int r6 = r11.mBusType     // Catch:{ Exception -> 0x030a }
            r7 = 13
            if (r6 != r7) goto L_0x01ef
            goto L_0x01f3
        L_0x01ef:
            r6 = 1
            r13.i = r6     // Catch:{ Exception -> 0x030a }
            goto L_0x01f6
        L_0x01f3:
            r6 = 2
            r13.i = r6     // Catch:{ Exception -> 0x030a }
        L_0x01f6:
            r4.add(r13)     // Catch:{ Exception -> 0x030a }
            r1.add(r11)     // Catch:{ Exception -> 0x030a }
            dxj$a r6 = new dxj$a     // Catch:{ Exception -> 0x030a }
            r7 = r15[r10]     // Catch:{ Exception -> 0x030a }
            r12 = r14[r10]     // Catch:{ Exception -> 0x030a }
            int r13 = com.autonavi.minimap.R.drawable.bus_turnpoint_on     // Catch:{ Exception -> 0x030a }
            r6.<init>(r7, r12, r13)     // Catch:{ Exception -> 0x030a }
            r5.add(r6)     // Catch:{ Exception -> 0x030a }
        L_0x020a:
            int r6 = r3.mSectionNum     // Catch:{ Exception -> 0x030a }
            r7 = 1
            int r6 = r6 - r7
            if (r9 >= r6) goto L_0x025f
            r6 = 1097859072(0x41700000, float:15.0)
            int r12 = (r21 > r6 ? 1 : (r21 == r6 ? 0 : -1))
            if (r12 < 0) goto L_0x025f
            dxj$a r6 = new dxj$a     // Catch:{ Exception -> 0x030a }
            int r12 = r15.length     // Catch:{ Exception -> 0x030a }
            int r12 = r12 - r7
            r12 = r15[r12]     // Catch:{ Exception -> 0x030a }
            int r13 = r14.length     // Catch:{ Exception -> 0x030a }
            int r13 = r13 - r7
            r7 = r14[r13]     // Catch:{ Exception -> 0x030a }
            int r13 = com.autonavi.minimap.R.drawable.bus_turnpoint_off     // Catch:{ Exception -> 0x030a }
            r6.<init>(r12, r7, r13)     // Catch:{ Exception -> 0x030a }
            r5.add(r6)     // Catch:{ Exception -> 0x030a }
            java.lang.String r6 = r11.mEndName     // Catch:{ Exception -> 0x030a }
            dwm r7 = new dwm     // Catch:{ Exception -> 0x030a }
            r7.<init>()     // Catch:{ Exception -> 0x030a }
            com.autonavi.common.model.GeoPoint r12 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x030a }
            int r13 = r15.length     // Catch:{ Exception -> 0x030a }
            r16 = 1
            int r13 = r13 + -1
            r13 = r15[r13]     // Catch:{ Exception -> 0x030a }
            int r10 = r14.length     // Catch:{ Exception -> 0x030a }
            int r10 = r10 + -1
            r10 = r14[r10]     // Catch:{ Exception -> 0x030a }
            r12.<init>(r13, r10)     // Catch:{ Exception -> 0x030a }
            r7.a = r12     // Catch:{ Exception -> 0x030a }
            r7.b = r6     // Catch:{ Exception -> 0x030a }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x030a }
            r6.<init>()     // Catch:{ Exception -> 0x030a }
            java.lang.String r10 = r11.mEndName     // Catch:{ Exception -> 0x030a }
            r6.append(r10)     // Catch:{ Exception -> 0x030a }
            java.lang.String r10 = r11.bus_id     // Catch:{ Exception -> 0x030a }
            r6.append(r10)     // Catch:{ Exception -> 0x030a }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x030a }
            r7.h = r6     // Catch:{ Exception -> 0x030a }
            r6 = 1
            r7.i = r6     // Catch:{ Exception -> 0x030a }
            r4.add(r7)     // Catch:{ Exception -> 0x030a }
        L_0x025f:
            int r6 = r3.mSectionNum     // Catch:{ Exception -> 0x030a }
            r7 = 1
            int r6 = r6 - r7
            if (r9 != r6) goto L_0x033f
            dxj$a r6 = new dxj$a     // Catch:{ Exception -> 0x030a }
            int r10 = r15.length     // Catch:{ Exception -> 0x030a }
            int r10 = r10 - r7
            r10 = r15[r10]     // Catch:{ Exception -> 0x030a }
            int r12 = r14.length     // Catch:{ Exception -> 0x030a }
            int r12 = r12 - r7
            r7 = r14[r12]     // Catch:{ Exception -> 0x030a }
            int r12 = com.autonavi.minimap.R.drawable.bus_turnpoint_off     // Catch:{ Exception -> 0x030a }
            r6.<init>(r10, r7, r12)     // Catch:{ Exception -> 0x030a }
            r5.add(r6)     // Catch:{ Exception -> 0x030a }
            java.lang.String r6 = r11.mEndName     // Catch:{ Exception -> 0x030a }
            dwm r7 = new dwm     // Catch:{ Exception -> 0x030a }
            r7.<init>()     // Catch:{ Exception -> 0x030a }
            com.autonavi.common.model.GeoPoint r10 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x030a }
            int r12 = r15.length     // Catch:{ Exception -> 0x030a }
            r13 = 1
            int r12 = r12 - r13
            r12 = r15[r12]     // Catch:{ Exception -> 0x030a }
            int r15 = r14.length     // Catch:{ Exception -> 0x030a }
            int r15 = r15 - r13
            r13 = r14[r15]     // Catch:{ Exception -> 0x030a }
            r10.<init>(r12, r13)     // Catch:{ Exception -> 0x030a }
            r7.a = r10     // Catch:{ Exception -> 0x030a }
            r7.b = r6     // Catch:{ Exception -> 0x030a }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x030a }
            r6.<init>()     // Catch:{ Exception -> 0x030a }
            java.lang.String r10 = r11.mEndName     // Catch:{ Exception -> 0x030a }
            r6.append(r10)     // Catch:{ Exception -> 0x030a }
            java.lang.String r10 = r11.bus_id     // Catch:{ Exception -> 0x030a }
            r6.append(r10)     // Catch:{ Exception -> 0x030a }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x030a }
            r7.h = r6     // Catch:{ Exception -> 0x030a }
            r6 = 3
            r7.i = r6     // Catch:{ Exception -> 0x030a }
            if (r8 < 0) goto L_0x02c9
            r6 = 1097859072(0x41700000, float:15.0)
            int r10 = (r21 > r6 ? 1 : (r21 == r6 ? 0 : -1))
            if (r10 >= 0) goto L_0x02c9
            int r6 = r3.mEndWalkLength     // Catch:{ Exception -> 0x030a }
            if (r6 <= 0) goto L_0x02c9
            boolean r6 = r3.isRidePath     // Catch:{ Exception -> 0x030a }
            if (r6 == 0) goto L_0x02c9
            java.lang.String r6 = r11.getSectionFastSimpleName()     // Catch:{ Exception -> 0x030a }
            r7.d = r6     // Catch:{ Exception -> 0x030a }
            java.lang.String r6 = r11.mRouteColor     // Catch:{ Exception -> 0x030a }
            int r6 = a(r6)     // Catch:{ Exception -> 0x030a }
            r7.g = r6     // Catch:{ Exception -> 0x030a }
            r6 = 1
            r7.j = r6     // Catch:{ Exception -> 0x030a }
        L_0x02c9:
            r4.add(r7)     // Catch:{ Exception -> 0x030a }
            goto L_0x033f
        L_0x02ce:
            r0 = move-exception
            r1 = r18
            goto L_0x033b
        L_0x02d3:
            r0 = move-exception
            r1 = r6
            goto L_0x033b
        L_0x02d6:
            r1 = r6
            r17 = r7
            if (r9 <= 0) goto L_0x030c
            int r6 = r11.mBusType     // Catch:{ Exception -> 0x030a }
            r7 = 12
            if (r6 == r7) goto L_0x02f7
            int r6 = r11.mBusType     // Catch:{ Exception -> 0x030a }
            r7 = 13
            if (r6 == r7) goto L_0x02f7
            dxj$a r6 = new dxj$a     // Catch:{ Exception -> 0x030a }
            r7 = 0
            r10 = r15[r7]     // Catch:{ Exception -> 0x030a }
            r11 = r14[r7]     // Catch:{ Exception -> 0x030a }
            int r7 = com.autonavi.minimap.R.drawable.bus_turnpoint_transfer     // Catch:{ Exception -> 0x030a }
            r6.<init>(r10, r11, r7)     // Catch:{ Exception -> 0x030a }
            r5.add(r6)     // Catch:{ Exception -> 0x030a }
            goto L_0x031e
        L_0x02f7:
            dxj$a r6 = new dxj$a     // Catch:{ Exception -> 0x030a }
            r7 = 0
            r10 = r15[r7]     // Catch:{ Exception -> 0x030a }
            r12 = r14[r7]     // Catch:{ Exception -> 0x030a }
            int r7 = com.autonavi.minimap.R.drawable.bus_turnpoint_on     // Catch:{ Exception -> 0x030a }
            r6.<init>(r10, r12, r7)     // Catch:{ Exception -> 0x030a }
            r5.add(r6)     // Catch:{ Exception -> 0x030a }
            r1.add(r11)     // Catch:{ Exception -> 0x030a }
            goto L_0x031e
        L_0x030a:
            r0 = move-exception
            goto L_0x033b
        L_0x030c:
            dxj$a r6 = new dxj$a     // Catch:{ Exception -> 0x030a }
            r7 = 0
            r10 = r15[r7]     // Catch:{ Exception -> 0x030a }
            r12 = r14[r7]     // Catch:{ Exception -> 0x030a }
            int r7 = com.autonavi.minimap.R.drawable.bus_turnpoint_on     // Catch:{ Exception -> 0x030a }
            r6.<init>(r10, r12, r7)     // Catch:{ Exception -> 0x030a }
            r5.add(r6)     // Catch:{ Exception -> 0x030a }
            r1.add(r11)     // Catch:{ Exception -> 0x030a }
        L_0x031e:
            int r6 = r3.mSectionNum     // Catch:{ Exception -> 0x030a }
            r7 = 1
            int r6 = r6 - r7
            if (r9 != r6) goto L_0x033f
            dxj$a r6 = new dxj$a     // Catch:{ Exception -> 0x030a }
            int r10 = r15.length     // Catch:{ Exception -> 0x030a }
            int r10 = r10 - r7
            r10 = r15[r10]     // Catch:{ Exception -> 0x030a }
            int r11 = r14.length     // Catch:{ Exception -> 0x030a }
            int r11 = r11 - r7
            r7 = r14[r11]     // Catch:{ Exception -> 0x030a }
            int r11 = com.autonavi.minimap.R.drawable.bus_turnpoint_off     // Catch:{ Exception -> 0x030a }
            r6.<init>(r10, r7, r11)     // Catch:{ Exception -> 0x030a }
            r5.add(r6)     // Catch:{ Exception -> 0x030a }
            goto L_0x033f
        L_0x0337:
            r0 = move-exception
            r1 = r6
            r17 = r7
        L_0x033b:
            r6 = r0
        L_0x033c:
            r6.printStackTrace()
        L_0x033f:
            int r9 = r9 + 1
            r6 = r1
            r7 = r17
            r1 = r20
            r10 = 0
            goto L_0x00c2
        L_0x0349:
            r1 = r6
            r17 = r7
            if (r8 < 0) goto L_0x03ce
            if (r17 == 0) goto L_0x03ce
            r6 = r17
            boolean r7 = r6.isStart
            if (r7 != 0) goto L_0x03ce
            com.autonavi.bundle.routecommon.entity.BusPath$TaxiBusPath r7 = r3.taxiBusPath
            int[] r7 = r7.mXs
            com.autonavi.bundle.routecommon.entity.BusPath$TaxiBusPath r9 = r3.taxiBusPath
            int[] r9 = r9.mYs
            dxj$a r10 = new dxj$a
            r11 = 0
            r12 = r7[r11]
            r13 = r9[r11]
            int r14 = com.autonavi.minimap.R.drawable.taxi_turnpoint
            r10.<init>(r12, r13, r14)
            r5.add(r10)
            if (r8 < 0) goto L_0x03ce
            dwm r8 = new dwm
            r8.<init>()
            com.autonavi.common.model.GeoPoint r10 = new com.autonavi.common.model.GeoPoint
            r7 = r7[r11]
            r9 = r9[r11]
            r10.<init>(r7, r9)
            r8.a = r10
            r7 = r20
            android.content.Context r9 = r7.i
            int r10 = com.autonavi.minimap.R.string.route_taxi
            java.lang.String r9 = r9.getString(r10)
            r8.b = r9
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            android.content.Context r10 = r7.i
            int r11 = com.autonavi.minimap.R.string.route_to
            java.lang.String r10 = r10.getString(r11)
            r9.append(r10)
            java.lang.String r10 = " "
            r9.append(r10)
            java.lang.String r10 = r6.mEndName
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.c = r9
            com.autonavi.bundle.routecommon.entity.BusPath$TaxiBusPath r3 = r3.taxiBusPath
            java.lang.String r3 = r3.color
            int r3 = a(r3)
            r8.f = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r9 = r6.mEndName
            r3.append(r9)
            int r6 = r6.mendX
            r3.append(r6)
            java.lang.String r3 = r3.toString()
            r8.h = r3
            r4.add(r8)
            goto L_0x03d0
        L_0x03ce:
            r7 = r20
        L_0x03d0:
            r7.a(r5)
            r7.m = r1
            if (r22 != 0) goto L_0x0402
            java.util.List<com.autonavi.bundle.routecommon.entity.BusPathSection> r1 = r7.m
            r3 = 0
            java.lang.Object r1 = r1.get(r3)
            com.autonavi.bundle.routecommon.entity.BusPathSection r1 = (com.autonavi.bundle.routecommon.entity.BusPathSection) r1
            if (r1 == 0) goto L_0x0402
            com.autonavi.common.model.GeoPoint r5 = new com.autonavi.common.model.GeoPoint
            int[] r6 = r1.mXs
            r6 = r6[r3]
            int[] r1 = r1.mYs
            r1 = r1[r3]
            r5.<init>(r6, r1)
            com.autonavi.sdk.location.LocationInstrument r1 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            com.autonavi.common.model.GeoPoint r1 = r1.getLatestPosition()
            int r1 = defpackage.cfe.b(r1, r5)
            r3 = 10000(0x2710, float:1.4013E-41)
            if (r1 > r3) goto L_0x0402
            r20.b()
        L_0x0402:
            int r1 = r4.size()
            if (r1 <= 0) goto L_0x0535
            int r1 = r4.size()
            r3 = 1097859072(0x41700000, float:15.0)
            int r2 = (r21 > r3 ? 1 : (r21 == r3 ? 0 : -1))
            if (r2 < 0) goto L_0x0478
            r2 = 2
            if (r1 <= r2) goto L_0x0478
            r2 = 1
        L_0x0416:
            int r3 = r1 + -1
            if (r2 >= r3) goto L_0x042e
            java.lang.Object r3 = r4.get(r2)
            dwm r3 = (defpackage.dwm) r3
            int r5 = r2 + 1
            java.lang.Object r5 = r4.get(r5)
            dwm r5 = (defpackage.dwm) r5
            defpackage.dwm.a(r3, r5)
            int r2 = r2 + 2
            goto L_0x0416
        L_0x042e:
            r2 = 0
            java.lang.Object r5 = r4.get(r2)
            dwm r5 = (defpackage.dwm) r5
            r2 = 1
            java.lang.Object r6 = r4.get(r2)
            dwm r6 = (defpackage.dwm) r6
            com.autonavi.common.model.GeoPoint r2 = r5.a
            int r2 = r2.x
            com.autonavi.common.model.GeoPoint r8 = r5.a
            int r8 = r8.y
            com.autonavi.common.model.GeoPoint r9 = r6.a
            int r9 = r9.x
            com.autonavi.common.model.GeoPoint r6 = r6.a
            int r6 = r6.y
            int r2 = defpackage.dwm.a(r2, r8, r9, r6)
            r5.e = r2
            java.lang.Object r2 = r4.get(r3)
            dwm r2 = (defpackage.dwm) r2
            r3 = 2
            int r1 = r1 - r3
            java.lang.Object r1 = r4.get(r1)
            dwm r1 = (defpackage.dwm) r1
            com.autonavi.common.model.GeoPoint r3 = r2.a
            int r3 = r3.x
            com.autonavi.common.model.GeoPoint r5 = r2.a
            int r5 = r5.y
            com.autonavi.common.model.GeoPoint r6 = r1.a
            int r6 = r6.x
            com.autonavi.common.model.GeoPoint r1 = r1.a
            int r1 = r1.y
            int r1 = defpackage.dwm.a(r3, r5, r6, r1)
            r2.e = r1
            goto L_0x051d
        L_0x0478:
            android.graphics.Rect r2 = new android.graphics.Rect
            r2.<init>()
            r3 = 0
            java.lang.Object r5 = r4.get(r3)
            dwm r5 = (defpackage.dwm) r5
            com.autonavi.common.model.GeoPoint r3 = r5.a
            int r3 = r3.x
            r2.left = r3
            com.autonavi.common.model.GeoPoint r3 = r5.a
            int r3 = r3.x
            r2.right = r3
            com.autonavi.common.model.GeoPoint r3 = r5.a
            int r3 = r3.y
            r2.top = r3
            com.autonavi.common.model.GeoPoint r3 = r5.a
            int r3 = r3.y
            r2.bottom = r3
            r3 = 1
        L_0x049d:
            if (r3 >= r1) goto L_0x04ad
            java.lang.Object r6 = r4.get(r3)
            dwm r6 = (defpackage.dwm) r6
            com.autonavi.common.model.GeoPoint r6 = r6.a
            defpackage.dwm.b(r2, r6)
            int r3 = r3 + 1
            goto L_0x049d
        L_0x04ad:
            com.autonavi.common.model.GeoPoint r3 = r5.a
            int r3 = defpackage.dwm.a(r2, r3)
            r5.e = r3
            r6 = 2
            if (r1 != r6) goto L_0x04c6
            r8 = 1
            java.lang.Object r1 = r4.get(r8)
            dwm r1 = (defpackage.dwm) r1
            int r2 = defpackage.dwm.a(r3)
            r1.e = r2
            goto L_0x051d
        L_0x04c6:
            if (r1 <= r6) goto L_0x051d
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            int r8 = r5.e
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            r6.add(r8)
            r8 = r5
            r5 = r3
            r3 = 1
        L_0x04d9:
            if (r3 >= r1) goto L_0x051d
            java.lang.Object r9 = r4.get(r3)
            dwm r9 = (defpackage.dwm) r9
            com.autonavi.common.model.GeoPoint r10 = r9.a
            int r10 = defpackage.dwm.a(r2, r10)
            if (r10 != r5) goto L_0x04f1
            com.autonavi.common.model.GeoPoint r8 = r8.a
            com.autonavi.common.model.GeoPoint r10 = r9.a
            int r10 = defpackage.dwm.a(r8, r10, r5)
        L_0x04f1:
            java.lang.Integer r8 = java.lang.Integer.valueOf(r10)
            boolean r8 = r6.contains(r8)
            if (r8 == 0) goto L_0x0504
            int r8 = r1 + -1
            if (r3 >= r8) goto L_0x0504
            int r5 = defpackage.dwm.a(r5)
            goto L_0x0505
        L_0x0504:
            r5 = r10
        L_0x0505:
            r9.e = r5
            java.lang.Integer r8 = java.lang.Integer.valueOf(r5)
            r6.add(r8)
            int r8 = r6.size()
            r10 = 2
            if (r8 <= r10) goto L_0x0519
            r8 = 0
            r6.remove(r8)
        L_0x0519:
            int r3 = r3 + 1
            r8 = r9
            goto L_0x04d9
        L_0x051d:
            com.autonavi.minimap.route.bus.localbus.overlay.RouteBusTipOverlay r1 = r7.g
            if (r1 == 0) goto L_0x0526
            com.autonavi.minimap.route.bus.localbus.overlay.RouteBusTipOverlay r1 = r7.g
            r1.clear()
        L_0x0526:
            r1 = 0
            r2 = 3
            int r1 = r7.a(r4, r1, r2)
            r2 = 2
            int r1 = r7.a(r4, r1, r2)
            r2 = 1
            r7.a(r4, r1, r2)
        L_0x0535:
            return
        L_0x0536:
            r7 = r1
            return
        L_0x0538:
            r7 = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dxj.a(float, boolean):void");
    }

    private int a(ArrayList<dwm> arrayList, int i2, int i3) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            dwm dwm = arrayList.get(size);
            if (dwm.i == i3) {
                int i4 = 7;
                switch (dwm.e) {
                    case 1:
                        i4 = 6;
                        break;
                    case 2:
                        i4 = 5;
                        break;
                    case 3:
                        i4 = 8;
                        break;
                }
                dwm.e = i4;
                if (this.g != null) {
                    this.g.addBusStationTip(dwm, i2);
                    i2++;
                }
            }
        }
        return i2;
    }

    private void a(ArrayList<a> arrayList) {
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            a aVar = arrayList.get(i2);
            if (aVar.c == R.drawable.bus_turnpoint_on) {
                RouteBusPointOverlay routeBusPointOverlay = this.e;
                int i3 = aVar.a;
                int i4 = aVar.b;
                int i5 = i2 + 20;
                if (routeBusPointOverlay != null) {
                    GeoPoint geoPoint = new GeoPoint(i3, i4);
                    MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, geoPoint, 3);
                    mapViewLayoutParams.mode = 0;
                    View inflate = this.j.inflate(R.layout.layout_turnon, null);
                    this.k.a(inflate, (LayoutParams) mapViewLayoutParams);
                    PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
                    pointOverlayItem.mDefaultMarker = routeBusPointOverlay.createMarker(i5, inflate, 4, 0.0f, 0.0f, false);
                    this.k.a(inflate);
                    routeBusPointOverlay.addItem(pointOverlayItem);
                }
            } else {
                a(this.c, aVar.a, aVar.b, aVar.c, 4);
            }
        }
    }

    private static int a(String str) {
        if (str == null || str.trim().equals(MetaRecord.LOG_SEPARATOR)) {
            return 0;
        }
        try {
            return Color.parseColor(str);
        } catch (Exception e2) {
            e2.printStackTrace();
            return -12409345;
        }
    }

    public final void a() {
        if (this.h != null && this.h.hasData() && this.b != null) {
            this.b.clear();
            this.b.clearFocus();
            this.b.setClickable(false);
            BusPath focusBusPath = this.h.getFocusBusPath();
            if (focusBusPath != null && focusBusPath.mSectionNum != 0) {
                int i2 = 0;
                while (i2 < focusBusPath.mSectionNum) {
                    try {
                        BusPathSection busPathSection = focusBusPath.mPathSections[i2];
                        Station[] stationArr = busPathSection.mStations;
                        for (int i3 = 0; i3 < stationArr.length; i3++) {
                            Station station = stationArr[i3];
                            String str = "";
                            if (!(i3 == 0 || i3 == stationArr.length - 1)) {
                                str = station.mName;
                            }
                            String str2 = str;
                            this.b.addBusStationName(new GeoPoint(station.mX, station.mY), busPathSection.mRouteColor, str2, station.id, station.mStationdirection);
                        }
                        i2++;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                }
            }
        }
    }

    private static int a(int[] iArr, int[] iArr2, RouteBusLineOverlay routeBusLineOverlay) {
        int length = iArr.length;
        GeoPoint[] geoPointArr = new GeoPoint[length];
        for (int i2 = 0; i2 < length; i2++) {
            geoPointArr[i2] = new GeoPoint(iArr[i2], iArr2[i2]);
        }
        return routeBusLineOverlay.createAndAddArrowLineItem(geoPointArr);
    }

    private static int a(TaxiBusPath taxiBusPath, RouteBusLineOverlay routeBusLineOverlay) {
        int i2;
        if (taxiBusPath == null) {
            return 0;
        }
        int[] iArr = taxiBusPath.mXs;
        int[] iArr2 = taxiBusPath.mYs;
        try {
            i2 = Color.parseColor(taxiBusPath.color);
        } catch (Exception e2) {
            e2.printStackTrace();
            i2 = 0;
        }
        return routeBusLineOverlay.createAndAddBackgroundLineItem(a(iArr, iArr2), i2);
    }

    private static int a(RouteBusLineOverlay routeBusLineOverlay, int[] iArr, int[] iArr2) {
        return routeBusLineOverlay.createAndAddLinkPathItem(a(iArr, iArr2));
    }
}
