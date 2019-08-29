package defpackage;

import android.content.Context;
import android.graphics.Rect;
import com.autonavi.bundle.routecommon.entity.BusEta;
import com.autonavi.bundle.routecommon.entity.BusPath;
import com.autonavi.bundle.routecommon.entity.ExTrainPath;
import com.autonavi.bundle.routecommon.entity.ExtBusPath;
import com.autonavi.bundle.routecommon.entity.IBusRouteResult;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.extbus.overlay.ExtBusLineOverlay;
import com.autonavi.minimap.route.bus.extbus.overlay.ExtBusPointOverlay;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: dvb reason: default package */
/* compiled from: ExtRouteBusResultController */
public final class dvb {
    public IBusRouteResult a = null;
    public ExtBusPointOverlay b;
    public ExtBusPointOverlay c;
    public ExtBusLineOverlay d;
    public ExtBusPointOverlay e;
    private Context f;

    public dvb(Context context, IBusRouteResult iBusRouteResult, ExtBusPointOverlay extBusPointOverlay, ExtBusLineOverlay extBusLineOverlay, ExtBusPointOverlay extBusPointOverlay2, ExtBusPointOverlay extBusPointOverlay3) {
        this.f = context;
        this.a = iBusRouteResult;
        this.c = extBusPointOverlay;
        this.d = extBusLineOverlay;
        this.e = extBusPointOverlay2;
        this.b = extBusPointOverlay3;
    }

    public final void a(GeoPoint geoPoint, int i, int i2) {
        this.c.addStation(geoPoint, i, i2, 4);
    }

    public final void a(int i) {
        int i2;
        int i3;
        if (this.a != null && this.a.hasData() && this.a.isExtBusResult()) {
            ExtBusPath focusExtBusPath = this.a.getFocusExtBusPath();
            if (focusExtBusPath != null) {
                ArrayList<axb> busPathList = focusExtBusPath.getBusPathList();
                ArrayList arrayList = new ArrayList();
                Iterator<axb> it = busPathList.iterator();
                while (it.hasNext()) {
                    axb next = it.next();
                    if (!(next instanceof BusPath) && (next instanceof ExTrainPath)) {
                        ExTrainPath exTrainPath = (ExTrainPath) next;
                        dwm dwm = new dwm();
                        dwm.a = new GeoPoint(exTrainPath.startX, exTrainPath.startY);
                        StringBuilder sb = new StringBuilder();
                        sb.append(exTrainPath.sst);
                        sb.append(this.f.getString(R.string.route_goon_bus));
                        dwm.b = sb.toString();
                        dwm.c = exTrainPath.trip;
                        arrayList.add(dwm);
                        dwm dwm2 = new dwm();
                        dwm2.a = new GeoPoint(exTrainPath.endX, exTrainPath.endY);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(exTrainPath.tst);
                        sb2.append(this.f.getString(R.string.route_off_bus));
                        dwm2.b = sb2.toString();
                        dwm2.c = exTrainPath.trip;
                        arrayList.add(dwm2);
                    }
                }
                if (arrayList.size() > 0) {
                    int size = arrayList.size();
                    if (i < 14 || size <= 2) {
                        Rect rect = new Rect();
                        dwm dwm3 = (dwm) arrayList.get(0);
                        rect.left = dwm3.a.x;
                        rect.right = dwm3.a.x;
                        rect.top = dwm3.a.y;
                        rect.bottom = dwm3.a.y;
                        for (int i4 = 1; i4 < size; i4++) {
                            dwm.b(rect, ((dwm) arrayList.get(i4)).a);
                        }
                        int a2 = dwm.a(rect, dwm3.a);
                        dwm3.e = a2;
                        if (size == 2) {
                            ((dwm) arrayList.get(1)).e = dwm.a(a2);
                        } else if (size > 2) {
                            ArrayList arrayList2 = new ArrayList();
                            arrayList2.add(Integer.valueOf(dwm3.e));
                            dwm dwm4 = dwm3;
                            int i5 = 1;
                            while (i5 < size) {
                                dwm dwm5 = (dwm) arrayList.get(i5);
                                int a3 = dwm.a(rect, dwm5.a);
                                if (a3 == a2) {
                                    a3 = dwm.a(dwm4.a, dwm5.a, a2);
                                }
                                a2 = (!arrayList2.contains(Integer.valueOf(a3)) || i5 >= size + -1) ? a3 : dwm.a(a2);
                                dwm5.e = a2;
                                arrayList2.add(Integer.valueOf(a2));
                                if (arrayList2.size() > 2) {
                                    arrayList2.remove(0);
                                }
                                i5++;
                                dwm4 = dwm5;
                            }
                        }
                    } else {
                        int i6 = 1;
                        while (true) {
                            i3 = size - 1;
                            if (i6 >= i3) {
                                break;
                            }
                            dwm.a((dwm) arrayList.get(i6), (dwm) arrayList.get(i6 + 1));
                            i6 += 2;
                        }
                        ((dwm) arrayList.get(0)).e = dwm.a(((dwm) arrayList.get(1)).e);
                        ((dwm) arrayList.get(i3)).e = dwm.a(((dwm) arrayList.get(size - 2)).e);
                    }
                }
                for (int i7 = 0; i7 < arrayList.size(); i7++) {
                    dwm dwm6 = (dwm) arrayList.get(i7);
                    switch (dwm6.e) {
                        case 1:
                            i2 = 1;
                            break;
                        case 2:
                            i2 = 3;
                            break;
                        case 3:
                            i2 = 2;
                            break;
                        default:
                            i2 = 0;
                            break;
                    }
                    dwm6.e = i2;
                    this.e.addBusStationAdvanceTip(dwm6.a, i7, dwm6.b, dwm6.c, dwm6.e);
                }
            }
        }
    }

    public static void a(int i, int i2, int[] iArr, ExtBusLineOverlay extBusLineOverlay) {
        int[] iArr2 = {i, iArr[0]};
        int[] iArr3 = {i2, iArr[1]};
        if (iArr3[1] == -1) {
            return;
        }
        if (iArr2[0] != iArr2[1] || iArr3[0] != iArr3[1]) {
            extBusLineOverlay.createAndAddLinkPathItem(iArr2, iArr3);
        }
    }

    private static void a(int[] iArr, int i, int i2) {
        iArr[0] = i;
        iArr[1] = i2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00af, code lost:
        if (r8.infolist != null) goto L_0x00ca;
     */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x0328 A[Catch:{ Exception -> 0x0323 }] */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x0336 A[Catch:{ Exception -> 0x0323 }] */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x0339 A[Catch:{ Exception -> 0x0323 }] */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x0262  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int[] a(com.autonavi.bundle.routecommon.entity.BusPath r29, defpackage.dvw r30, com.autonavi.minimap.route.bus.extbus.overlay.ExtBusLineOverlay r31, int[] r32) {
        /*
            r28 = this;
            r1 = r28
            r2 = r29
            r3 = r30
            r4 = r31
            int r5 = r2.mstartX
            int r6 = r2.mstartY
            int r7 = r2.mendX
            int r8 = r2.mendY
            r9 = r32
            a(r5, r6, r9, r4)
            com.autonavi.bundle.routecommon.entity.BusPath$TaxiBusPath r9 = r2.taxiBusPath
            r10 = 2
            int[] r11 = new int[r10]
            r11 = {-1, -1} // fill-array
            r13 = 0
            if (r9 == 0) goto L_0x0096
            boolean r15 = r9.isStart
            if (r15 == 0) goto L_0x0096
            int[] r15 = r9.mXs
            int[] r12 = r9.mYs
            r14 = r15[r13]
            r10 = r12[r13]
            if (r5 != r14) goto L_0x0036
            if (r6 == r10) goto L_0x0031
            goto L_0x0036
        L_0x0031:
            r18 = r7
            r17 = r8
            goto L_0x005e
        L_0x0036:
            r17 = r8
            r13 = 2
            int[] r8 = new int[r13]
            r18 = r7
            int[] r7 = new int[r13]
            r13 = 0
            r8[r13] = r5
            r7[r13] = r6
            r13 = 1
            r8[r13] = r14
            r7[r13] = r10
            if (r5 <= 0) goto L_0x005e
            if (r6 <= 0) goto L_0x005e
            boolean r10 = r2.isExtBusStartCityPath
            if (r10 != 0) goto L_0x005e
            int r10 = r4.createAndAddLinkPathItem(r8, r7)
            r3.b = r10
            r8 = r8[r13]
            r7 = r7[r13]
            a(r11, r8, r7)
        L_0x005e:
            com.autonavi.common.model.GeoPoint r7 = new com.autonavi.common.model.GeoPoint
            r8 = 0
            r10 = r15[r8]
            r13 = r12[r8]
            r7.<init>(r10, r13)
            int r8 = com.autonavi.minimap.R.drawable.taxi_turnpoint
            int r10 = r3.b
            r1.a(r7, r8, r10)
            int r7 = r4.createLineItem(r15, r12)
            r3.b = r7
            int r7 = r15.length
            r8 = 1
            int r7 = r7 - r8
            r7 = r15[r7]
            int r10 = r12.length
            int r10 = r10 - r8
            r10 = r12[r10]
            a(r11, r7, r10)
            com.autonavi.common.model.GeoPoint r7 = new com.autonavi.common.model.GeoPoint
            int r10 = r15.length
            int r10 = r10 - r8
            r10 = r15[r10]
            int r13 = r12.length
            int r13 = r13 - r8
            r8 = r12[r13]
            r7.<init>(r10, r8)
            int r8 = r3.b
            r10 = -999(0xfffffffffffffc19, float:NaN)
            r1.a(r7, r10, r8)
            goto L_0x009a
        L_0x0096:
            r18 = r7
            r17 = r8
        L_0x009a:
            r7 = 0
            r8 = r7
            r7 = 0
        L_0x009d:
            int r10 = r2.mSectionNum
            if (r7 >= r10) goto L_0x03d9
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r10 = r2.mPathSections     // Catch:{ Exception -> 0x03ba }
            r10 = r10[r7]     // Catch:{ Exception -> 0x03ba }
            com.autonavi.bundle.routecommon.entity.BusPath$WalkPath r8 = r10.walk_path     // Catch:{ Exception -> 0x03ac }
            int r12 = r10.mFootLength     // Catch:{ Exception -> 0x03ac }
            if (r12 > 0) goto L_0x00ca
            if (r8 == 0) goto L_0x00c1
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r12 = r8.infolist     // Catch:{ Exception -> 0x00b2 }
            if (r12 == 0) goto L_0x00c1
            goto L_0x00ca
        L_0x00b2:
            r0 = move-exception
            r24 = r5
            r23 = r6
        L_0x00b7:
            r12 = -999(0xfffffffffffffc19, float:NaN)
            r5 = r0
            r27 = r9
            r9 = r1
            r1 = r27
            goto L_0x03c8
        L_0x00c1:
            r23 = r6
            r20 = r8
            r22 = r9
            r9 = r1
            goto L_0x0260
        L_0x00ca:
            if (r8 == 0) goto L_0x01f1
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r12 = r8.infolist     // Catch:{ Exception -> 0x01ea }
            if (r12 == 0) goto L_0x01f1
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r12 = r8.infolist     // Catch:{ Exception -> 0x01ea }
            java.util.ArrayList r13 = new java.util.ArrayList     // Catch:{ Exception -> 0x01ea }
            r13.<init>()     // Catch:{ Exception -> 0x01ea }
            r14 = 0
        L_0x00d8:
            int r15 = r12.size()     // Catch:{ Exception -> 0x01ea }
            if (r14 >= r15) goto L_0x0131
            java.lang.Object r15 = r12.get(r14)     // Catch:{ Exception -> 0x00b2 }
            com.autonavi.bundle.routecommon.entity.OnFootNaviSection r15 = (com.autonavi.bundle.routecommon.entity.OnFootNaviSection) r15     // Catch:{ Exception -> 0x00b2 }
            if (r15 == 0) goto L_0x0120
            r19 = r12
            int[] r12 = r15.mXs     // Catch:{ Exception -> 0x00b2 }
            if (r12 == 0) goto L_0x011b
            int[] r12 = r15.mXs     // Catch:{ Exception -> 0x00b2 }
            int r12 = r12.length     // Catch:{ Exception -> 0x00b2 }
            r20 = r8
            r8 = 0
        L_0x00f2:
            if (r8 >= r12) goto L_0x011d
            r21 = r12
            android.graphics.Point r12 = new android.graphics.Point     // Catch:{ Exception -> 0x00b2 }
            r22 = r9
            int[] r9 = r15.mXs     // Catch:{ Exception -> 0x0111 }
            r9 = r9[r8]     // Catch:{ Exception -> 0x0111 }
            int[] r1 = r15.mYs     // Catch:{ Exception -> 0x0157 }
            r1 = r1[r8]     // Catch:{ Exception -> 0x0157 }
            r12.<init>(r9, r1)     // Catch:{ Exception -> 0x0157 }
            r13.add(r12)     // Catch:{ Exception -> 0x0157 }
            int r8 = r8 + 1
            r12 = r21
            r9 = r22
            r1 = r28
            goto L_0x00f2
        L_0x0111:
            r0 = move-exception
            r9 = r1
            r24 = r5
            r23 = r6
        L_0x0117:
            r1 = r22
            goto L_0x03a9
        L_0x011b:
            r20 = r8
        L_0x011d:
            r22 = r9
            goto L_0x0126
        L_0x0120:
            r20 = r8
            r22 = r9
            r19 = r12
        L_0x0126:
            int r14 = r14 + 1
            r12 = r19
            r8 = r20
            r9 = r22
            r1 = r28
            goto L_0x00d8
        L_0x0131:
            r20 = r8
            r22 = r9
            int r1 = r13.size()     // Catch:{ Exception -> 0x01e5 }
            int[] r8 = new int[r1]     // Catch:{ Exception -> 0x01e5 }
            int[] r9 = new int[r1]     // Catch:{ Exception -> 0x01e5 }
            r12 = 0
        L_0x013e:
            if (r12 >= r1) goto L_0x015e
            java.lang.Object r14 = r13.get(r12)     // Catch:{ Exception -> 0x0157 }
            android.graphics.Point r14 = (android.graphics.Point) r14     // Catch:{ Exception -> 0x0157 }
            int r14 = r14.x     // Catch:{ Exception -> 0x0157 }
            r8[r12] = r14     // Catch:{ Exception -> 0x0157 }
            java.lang.Object r14 = r13.get(r12)     // Catch:{ Exception -> 0x0157 }
            android.graphics.Point r14 = (android.graphics.Point) r14     // Catch:{ Exception -> 0x0157 }
            int r14 = r14.y     // Catch:{ Exception -> 0x0157 }
            r9[r12] = r14     // Catch:{ Exception -> 0x0157 }
            int r12 = r12 + 1
            goto L_0x013e
        L_0x0157:
            r0 = move-exception
            r24 = r5
            r23 = r6
            goto L_0x03a5
        L_0x015e:
            if (r7 <= 0) goto L_0x01b7
            r12 = 2
            int[] r13 = new int[r12]     // Catch:{ Exception -> 0x01e5 }
            int[] r14 = new int[r12]     // Catch:{ Exception -> 0x01e5 }
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r12 = r2.mPathSections     // Catch:{ Exception -> 0x01e5 }
            int r15 = r7 + -1
            r12 = r12[r15]     // Catch:{ Exception -> 0x01e5 }
            int[] r12 = r12.mXs     // Catch:{ Exception -> 0x01e5 }
            r23 = r6
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r6 = r2.mPathSections     // Catch:{ Exception -> 0x03a2 }
            r6 = r6[r15]     // Catch:{ Exception -> 0x03a2 }
            int[] r6 = r6.mXs     // Catch:{ Exception -> 0x03a2 }
            int r6 = r6.length     // Catch:{ Exception -> 0x03a2 }
            r16 = 1
            int r6 = r6 + -1
            r6 = r12[r6]     // Catch:{ Exception -> 0x03a2 }
            r12 = 0
            r13[r12] = r6     // Catch:{ Exception -> 0x03a2 }
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r6 = r2.mPathSections     // Catch:{ Exception -> 0x03a2 }
            r6 = r6[r15]     // Catch:{ Exception -> 0x03a2 }
            int[] r6 = r6.mYs     // Catch:{ Exception -> 0x03a2 }
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r12 = r2.mPathSections     // Catch:{ Exception -> 0x03a2 }
            r12 = r12[r15]     // Catch:{ Exception -> 0x03a2 }
            int[] r12 = r12.mYs     // Catch:{ Exception -> 0x03a2 }
            int r12 = r12.length     // Catch:{ Exception -> 0x03a2 }
            r15 = 1
            int r12 = r12 - r15
            r6 = r6[r12]     // Catch:{ Exception -> 0x03a2 }
            r12 = 0
            r14[r12] = r6     // Catch:{ Exception -> 0x03a2 }
            r6 = r8[r12]     // Catch:{ Exception -> 0x03a2 }
            r13[r15] = r6     // Catch:{ Exception -> 0x03a2 }
            r6 = r9[r12]     // Catch:{ Exception -> 0x03a2 }
            r14[r15] = r6     // Catch:{ Exception -> 0x03a2 }
            r6 = r13[r12]     // Catch:{ Exception -> 0x03a2 }
            r12 = r13[r15]     // Catch:{ Exception -> 0x03a2 }
            if (r6 != r12) goto L_0x01a8
            r6 = 0
            r12 = r14[r6]     // Catch:{ Exception -> 0x03a2 }
            r6 = r14[r15]     // Catch:{ Exception -> 0x03a2 }
            if (r12 == r6) goto L_0x01b9
        L_0x01a8:
            int r6 = r4.createAndAddLinkPathItem(r13, r14)     // Catch:{ Exception -> 0x03a2 }
            r3.b = r6     // Catch:{ Exception -> 0x03a2 }
            r6 = 1
            r12 = r13[r6]     // Catch:{ Exception -> 0x03a2 }
            r13 = r14[r6]     // Catch:{ Exception -> 0x03a2 }
            a(r11, r12, r13)     // Catch:{ Exception -> 0x03a2 }
            goto L_0x01b9
        L_0x01b7:
            r23 = r6
        L_0x01b9:
            r6 = 2
            int[] r12 = new int[r6]     // Catch:{ Exception -> 0x03a2 }
            int[] r13 = new int[r6]     // Catch:{ Exception -> 0x03a2 }
            int r1 = r1 + -1
            r6 = r8[r1]     // Catch:{ Exception -> 0x03a2 }
            r14 = 0
            r12[r14] = r6     // Catch:{ Exception -> 0x03a2 }
            r1 = r9[r1]     // Catch:{ Exception -> 0x03a2 }
            r13[r14] = r1     // Catch:{ Exception -> 0x03a2 }
            int[] r1 = r10.mXs     // Catch:{ Exception -> 0x03a2 }
            r1 = r1[r14]     // Catch:{ Exception -> 0x03a2 }
            r6 = 1
            r12[r6] = r1     // Catch:{ Exception -> 0x03a2 }
            int[] r1 = r10.mYs     // Catch:{ Exception -> 0x03a2 }
            r1 = r1[r14]     // Catch:{ Exception -> 0x03a2 }
            r13[r6] = r1     // Catch:{ Exception -> 0x03a2 }
            int r1 = r4.createAndAddLinkPathItem(r12, r13)     // Catch:{ Exception -> 0x03a2 }
            r3.b = r1     // Catch:{ Exception -> 0x03a2 }
            r1 = r12[r6]     // Catch:{ Exception -> 0x03a2 }
            r12 = r13[r6]     // Catch:{ Exception -> 0x03a2 }
            a(r11, r1, r12)     // Catch:{ Exception -> 0x03a2 }
            r12 = 1
            goto L_0x023c
        L_0x01e5:
            r0 = move-exception
            r23 = r6
            goto L_0x03a3
        L_0x01ea:
            r0 = move-exception
            r23 = r6
            r24 = r5
            goto L_0x00b7
        L_0x01f1:
            r23 = r6
            r20 = r8
            r22 = r9
            r1 = 2
            int[] r8 = new int[r1]     // Catch:{ Exception -> 0x03a2 }
            int[] r9 = new int[r1]     // Catch:{ Exception -> 0x03a2 }
            if (r7 <= 0) goto L_0x022a
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r1 = r2.mPathSections     // Catch:{ Exception -> 0x03a2 }
            int r6 = r7 + -1
            r1 = r1[r6]     // Catch:{ Exception -> 0x03a2 }
            int[] r1 = r1.mXs     // Catch:{ Exception -> 0x03a2 }
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r12 = r2.mPathSections     // Catch:{ Exception -> 0x03a2 }
            r12 = r12[r6]     // Catch:{ Exception -> 0x03a2 }
            int[] r12 = r12.mXs     // Catch:{ Exception -> 0x03a2 }
            int r12 = r12.length     // Catch:{ Exception -> 0x03a2 }
            r13 = 1
            int r12 = r12 - r13
            r1 = r1[r12]     // Catch:{ Exception -> 0x03a2 }
            r12 = 0
            r8[r12] = r1     // Catch:{ Exception -> 0x03a2 }
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r1 = r2.mPathSections     // Catch:{ Exception -> 0x03a2 }
            r1 = r1[r6]     // Catch:{ Exception -> 0x03a2 }
            int[] r1 = r1.mYs     // Catch:{ Exception -> 0x03a2 }
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r12 = r2.mPathSections     // Catch:{ Exception -> 0x03a2 }
            r6 = r12[r6]     // Catch:{ Exception -> 0x03a2 }
            int[] r6 = r6.mYs     // Catch:{ Exception -> 0x03a2 }
            int r6 = r6.length     // Catch:{ Exception -> 0x03a2 }
            r12 = 1
            int r6 = r6 - r12
            r1 = r1[r6]     // Catch:{ Exception -> 0x03a2 }
            r6 = 0
            r9[r6] = r1     // Catch:{ Exception -> 0x03a2 }
            r1 = 0
            goto L_0x022f
        L_0x022a:
            r1 = 0
            r8[r1] = r5     // Catch:{ Exception -> 0x03a2 }
            r9[r1] = r23     // Catch:{ Exception -> 0x03a2 }
        L_0x022f:
            int[] r6 = r10.mXs     // Catch:{ Exception -> 0x03a2 }
            r6 = r6[r1]     // Catch:{ Exception -> 0x03a2 }
            r12 = 1
            r8[r12] = r6     // Catch:{ Exception -> 0x03a2 }
            int[] r6 = r10.mYs     // Catch:{ Exception -> 0x03a2 }
            r6 = r6[r1]     // Catch:{ Exception -> 0x03a2 }
            r9[r12] = r6     // Catch:{ Exception -> 0x03a2 }
        L_0x023c:
            int r1 = r4.createAndAddLinkPathItem(r8, r9)     // Catch:{ Exception -> 0x03a2 }
            r3.b = r1     // Catch:{ Exception -> 0x03a2 }
            int r1 = r8.length     // Catch:{ Exception -> 0x03a2 }
            int r1 = r1 - r12
            r1 = r8[r1]     // Catch:{ Exception -> 0x03a2 }
            int r6 = r9.length     // Catch:{ Exception -> 0x03a2 }
            int r6 = r6 - r12
            r6 = r9[r6]     // Catch:{ Exception -> 0x03a2 }
            a(r11, r1, r6)     // Catch:{ Exception -> 0x03a2 }
            com.autonavi.common.model.GeoPoint r1 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x03a2 }
            r6 = 0
            r8 = r8[r6]     // Catch:{ Exception -> 0x03a2 }
            r9 = r9[r6]     // Catch:{ Exception -> 0x03a2 }
            r1.<init>(r8, r9)     // Catch:{ Exception -> 0x03a2 }
            int r6 = com.autonavi.minimap.R.drawable.foot_turnpoint     // Catch:{ Exception -> 0x03a2 }
            int r8 = r3.b     // Catch:{ Exception -> 0x03a2 }
            r9 = r28
            r9.a(r1, r6, r8)     // Catch:{ Exception -> 0x039d }
        L_0x0260:
            if (r22 == 0) goto L_0x0328
            r1 = r22
            boolean r6 = r1.isStart     // Catch:{ Exception -> 0x0323 }
            if (r6 == 0) goto L_0x032a
            if (r7 != 0) goto L_0x032a
            int[] r6 = r1.mXs     // Catch:{ Exception -> 0x0323 }
            if (r6 == 0) goto L_0x032a
            int[] r6 = r1.mYs     // Catch:{ Exception -> 0x0323 }
            if (r6 == 0) goto L_0x032a
            int[] r6 = r1.mXs     // Catch:{ Exception -> 0x0323 }
            int r6 = r6.length     // Catch:{ Exception -> 0x0323 }
            if (r6 <= 0) goto L_0x032a
            int[] r6 = r1.mYs     // Catch:{ Exception -> 0x0323 }
            int r6 = r6.length     // Catch:{ Exception -> 0x0323 }
            if (r6 <= 0) goto L_0x032a
            r6 = 2
            int[] r8 = new int[r6]     // Catch:{ Exception -> 0x0323 }
            int[] r12 = new int[r6]     // Catch:{ Exception -> 0x0323 }
            int[] r6 = r1.mXs     // Catch:{ Exception -> 0x0323 }
            int[] r13 = r1.mXs     // Catch:{ Exception -> 0x0323 }
            int r13 = r13.length     // Catch:{ Exception -> 0x0323 }
            r14 = 1
            int r13 = r13 - r14
            r6 = r6[r13]     // Catch:{ Exception -> 0x0323 }
            r13 = 0
            r8[r13] = r6     // Catch:{ Exception -> 0x0323 }
            int[] r6 = r1.mYs     // Catch:{ Exception -> 0x0323 }
            int[] r15 = r1.mYs     // Catch:{ Exception -> 0x0323 }
            int r15 = r15.length     // Catch:{ Exception -> 0x0323 }
            int r15 = r15 - r14
            r6 = r6[r15]     // Catch:{ Exception -> 0x0323 }
            r12[r13] = r6     // Catch:{ Exception -> 0x0323 }
            if (r20 == 0) goto L_0x02fa
            r6 = r20
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r14 = r6.infolist     // Catch:{ Exception -> 0x0323 }
            if (r14 == 0) goto L_0x02fa
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r14 = r6.infolist     // Catch:{ Exception -> 0x0323 }
            java.lang.Object r14 = r14.get(r13)     // Catch:{ Exception -> 0x0323 }
            if (r14 == 0) goto L_0x02fa
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r14 = r6.infolist     // Catch:{ Exception -> 0x0323 }
            java.lang.Object r14 = r14.get(r13)     // Catch:{ Exception -> 0x0323 }
            com.autonavi.bundle.routecommon.entity.OnFootNaviSection r14 = (com.autonavi.bundle.routecommon.entity.OnFootNaviSection) r14     // Catch:{ Exception -> 0x0323 }
            int[] r14 = r14.mXs     // Catch:{ Exception -> 0x0323 }
            if (r14 == 0) goto L_0x02fa
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r14 = r6.infolist     // Catch:{ Exception -> 0x0323 }
            java.lang.Object r14 = r14.get(r13)     // Catch:{ Exception -> 0x0323 }
            com.autonavi.bundle.routecommon.entity.OnFootNaviSection r14 = (com.autonavi.bundle.routecommon.entity.OnFootNaviSection) r14     // Catch:{ Exception -> 0x0323 }
            int[] r14 = r14.mYs     // Catch:{ Exception -> 0x0323 }
            if (r14 == 0) goto L_0x02fa
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r14 = r6.infolist     // Catch:{ Exception -> 0x0323 }
            java.lang.Object r14 = r14.get(r13)     // Catch:{ Exception -> 0x0323 }
            com.autonavi.bundle.routecommon.entity.OnFootNaviSection r14 = (com.autonavi.bundle.routecommon.entity.OnFootNaviSection) r14     // Catch:{ Exception -> 0x0323 }
            int[] r14 = r14.mXs     // Catch:{ Exception -> 0x0323 }
            int r14 = r14.length     // Catch:{ Exception -> 0x0323 }
            if (r14 <= 0) goto L_0x02fa
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r14 = r6.infolist     // Catch:{ Exception -> 0x0323 }
            java.lang.Object r14 = r14.get(r13)     // Catch:{ Exception -> 0x0323 }
            com.autonavi.bundle.routecommon.entity.OnFootNaviSection r14 = (com.autonavi.bundle.routecommon.entity.OnFootNaviSection) r14     // Catch:{ Exception -> 0x0323 }
            int[] r14 = r14.mYs     // Catch:{ Exception -> 0x0323 }
            int r14 = r14.length     // Catch:{ Exception -> 0x0323 }
            if (r14 <= 0) goto L_0x02fa
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r14 = r6.infolist     // Catch:{ Exception -> 0x0323 }
            java.lang.Object r14 = r14.get(r13)     // Catch:{ Exception -> 0x0323 }
            com.autonavi.bundle.routecommon.entity.OnFootNaviSection r14 = (com.autonavi.bundle.routecommon.entity.OnFootNaviSection) r14     // Catch:{ Exception -> 0x0323 }
            int[] r14 = r14.mXs     // Catch:{ Exception -> 0x0323 }
            r14 = r14[r13]     // Catch:{ Exception -> 0x0323 }
            r15 = 1
            r8[r15] = r14     // Catch:{ Exception -> 0x0323 }
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r6 = r6.infolist     // Catch:{ Exception -> 0x0323 }
            java.lang.Object r6 = r6.get(r13)     // Catch:{ Exception -> 0x0323 }
            com.autonavi.bundle.routecommon.entity.OnFootNaviSection r6 = (com.autonavi.bundle.routecommon.entity.OnFootNaviSection) r6     // Catch:{ Exception -> 0x0323 }
            int[] r6 = r6.mYs     // Catch:{ Exception -> 0x0323 }
            r6 = r6[r13]     // Catch:{ Exception -> 0x0323 }
            r13 = 1
            r12[r13] = r6     // Catch:{ Exception -> 0x0323 }
            r13 = 0
            r14 = 1
            goto L_0x0308
        L_0x02fa:
            int[] r6 = r10.mXs     // Catch:{ Exception -> 0x0323 }
            r13 = 0
            r6 = r6[r13]     // Catch:{ Exception -> 0x0323 }
            r14 = 1
            r8[r14] = r6     // Catch:{ Exception -> 0x0323 }
            int[] r6 = r10.mYs     // Catch:{ Exception -> 0x0323 }
            r6 = r6[r13]     // Catch:{ Exception -> 0x0323 }
            r12[r14] = r6     // Catch:{ Exception -> 0x0323 }
        L_0x0308:
            r6 = r8[r13]     // Catch:{ Exception -> 0x0323 }
            r15 = r8[r14]     // Catch:{ Exception -> 0x0323 }
            if (r6 != r15) goto L_0x0314
            r6 = r12[r13]     // Catch:{ Exception -> 0x0323 }
            r13 = r12[r14]     // Catch:{ Exception -> 0x0323 }
            if (r6 == r13) goto L_0x032a
        L_0x0314:
            int r6 = r4.createAndAddLinkPathItem(r8, r12)     // Catch:{ Exception -> 0x0323 }
            r3.b = r6     // Catch:{ Exception -> 0x0323 }
            r6 = 1
            r8 = r8[r6]     // Catch:{ Exception -> 0x0323 }
            r12 = r12[r6]     // Catch:{ Exception -> 0x0323 }
            a(r11, r8, r12)     // Catch:{ Exception -> 0x0323 }
            goto L_0x032a
        L_0x0323:
            r0 = move-exception
            r24 = r5
            goto L_0x03a9
        L_0x0328:
            r1 = r22
        L_0x032a:
            int[] r6 = r10.mXs     // Catch:{ Exception -> 0x0323 }
            int[] r8 = r10.mYs     // Catch:{ Exception -> 0x0323 }
            int r12 = r10.mBusType     // Catch:{ Exception -> 0x0323 }
            boolean r12 = com.autonavi.bundle.routecommon.entity.BusPath.isBus(r12)     // Catch:{ Exception -> 0x0323 }
            if (r12 == 0) goto L_0x0339
            int r12 = com.autonavi.minimap.R.drawable.bus_turnpoint     // Catch:{ Exception -> 0x0323 }
            goto L_0x0346
        L_0x0339:
            int r12 = r10.mBusType     // Catch:{ Exception -> 0x0323 }
            boolean r12 = com.autonavi.bundle.routecommon.entity.BusPath.isSubway(r12)     // Catch:{ Exception -> 0x0323 }
            if (r12 == 0) goto L_0x0344
            int r12 = com.autonavi.minimap.R.drawable.sub_turnpoint     // Catch:{ Exception -> 0x0323 }
            goto L_0x0346
        L_0x0344:
            int r12 = com.autonavi.minimap.R.drawable.bubble_turnpoint     // Catch:{ Exception -> 0x0323 }
        L_0x0346:
            int r13 = r4.createLineItem(r6, r8)     // Catch:{ Exception -> 0x0323 }
            r3.b = r13     // Catch:{ Exception -> 0x0323 }
            int r13 = r6.length     // Catch:{ Exception -> 0x0323 }
            r14 = 1
            int r13 = r13 - r14
            r13 = r6[r13]     // Catch:{ Exception -> 0x0323 }
            int r15 = r8.length     // Catch:{ Exception -> 0x0323 }
            int r15 = r15 - r14
            r14 = r8[r15]     // Catch:{ Exception -> 0x0323 }
            a(r11, r13, r14)     // Catch:{ Exception -> 0x0323 }
            com.autonavi.bundle.routecommon.entity.BusEta r13 = r10.mEta     // Catch:{ Exception -> 0x0323 }
            a(r13, r4)     // Catch:{ Exception -> 0x0323 }
            com.autonavi.common.model.GeoPoint r13 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x0323 }
            r14 = 0
            r15 = r6[r14]     // Catch:{ Exception -> 0x0323 }
            r24 = r5
            r5 = r8[r14]     // Catch:{ Exception -> 0x039b }
            r13.<init>(r15, r5)     // Catch:{ Exception -> 0x039b }
            int r5 = r3.b     // Catch:{ Exception -> 0x039b }
            r9.a(r13, r12, r5)     // Catch:{ Exception -> 0x039b }
            com.autonavi.common.model.GeoPoint r5 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x039b }
            int r12 = r6.length     // Catch:{ Exception -> 0x039b }
            r13 = 2
            int r12 = r12 / r13
            r12 = r6[r12]     // Catch:{ Exception -> 0x039b }
            int r14 = r8.length     // Catch:{ Exception -> 0x039b }
            int r14 = r14 / r13
            r13 = r8[r14]     // Catch:{ Exception -> 0x039b }
            r5.<init>(r12, r13)     // Catch:{ Exception -> 0x039b }
            int r12 = r3.b     // Catch:{ Exception -> 0x039b }
            r13 = -999(0xfffffffffffffc19, float:NaN)
            r9.a(r5, r13, r12)     // Catch:{ Exception -> 0x039b }
            com.autonavi.common.model.GeoPoint r5 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x039b }
            int r12 = r6.length     // Catch:{ Exception -> 0x039b }
            r13 = 1
            int r12 = r12 - r13
            r6 = r6[r12]     // Catch:{ Exception -> 0x039b }
            int r12 = r8.length     // Catch:{ Exception -> 0x039b }
            int r12 = r12 - r13
            r8 = r8[r12]     // Catch:{ Exception -> 0x039b }
            r5.<init>(r6, r8)     // Catch:{ Exception -> 0x039b }
            int r6 = r3.b     // Catch:{ Exception -> 0x039b }
            r12 = -999(0xfffffffffffffc19, float:NaN)
            r9.a(r5, r12, r6)     // Catch:{ Exception -> 0x0399 }
            goto L_0x03cb
        L_0x0399:
            r0 = move-exception
            goto L_0x03b8
        L_0x039b:
            r0 = move-exception
            goto L_0x03a9
        L_0x039d:
            r0 = move-exception
            r24 = r5
            goto L_0x0117
        L_0x03a2:
            r0 = move-exception
        L_0x03a3:
            r24 = r5
        L_0x03a5:
            r1 = r22
            r9 = r28
        L_0x03a9:
            r12 = -999(0xfffffffffffffc19, float:NaN)
            goto L_0x03b8
        L_0x03ac:
            r0 = move-exception
            r24 = r5
            r23 = r6
            r12 = -999(0xfffffffffffffc19, float:NaN)
            r27 = r9
            r9 = r1
            r1 = r27
        L_0x03b8:
            r5 = r0
            goto L_0x03c8
        L_0x03ba:
            r0 = move-exception
            r24 = r5
            r23 = r6
            r12 = -999(0xfffffffffffffc19, float:NaN)
            r27 = r9
            r9 = r1
            r1 = r27
            r5 = r0
            r10 = r8
        L_0x03c8:
            r5.printStackTrace()
        L_0x03cb:
            r8 = r10
            int r7 = r7 + 1
            r6 = r23
            r5 = r24
            r27 = r9
            r9 = r1
            r1 = r27
            goto L_0x009d
        L_0x03d9:
            r27 = r9
            r9 = r1
            r1 = r27
            int r5 = r2.mEndWalkLength
            if (r5 <= 0) goto L_0x04da
            int[] r5 = r8.mXs
            int r5 = r5.length
            r6 = 1
            int r5 = r5 - r6
            com.autonavi.bundle.routecommon.entity.BusPath$WalkPath r6 = r2.endwalk
            if (r6 == 0) goto L_0x04a2
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r7 = r6.infolist
            if (r7 == 0) goto L_0x04a2
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            r10 = 0
        L_0x03f5:
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r12 = r6.infolist
            int r12 = r12.size()
            if (r10 >= r12) goto L_0x0433
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r12 = r6.infolist
            java.lang.Object r12 = r12.get(r10)
            com.autonavi.bundle.routecommon.entity.OnFootNaviSection r12 = (com.autonavi.bundle.routecommon.entity.OnFootNaviSection) r12
            if (r12 == 0) goto L_0x042c
            int[] r13 = r12.mXs
            if (r13 == 0) goto L_0x042c
            int[] r13 = r12.mXs
            int r13 = r13.length
            r14 = 0
        L_0x040f:
            if (r14 >= r13) goto L_0x042c
            android.graphics.Point r15 = new android.graphics.Point
            r25 = r6
            int[] r6 = r12.mXs
            r6 = r6[r14]
            r26 = r13
            int[] r13 = r12.mYs
            r13 = r13[r14]
            r15.<init>(r6, r13)
            r7.add(r15)
            int r14 = r14 + 1
            r6 = r25
            r13 = r26
            goto L_0x040f
        L_0x042c:
            r25 = r6
            int r10 = r10 + 1
            r6 = r25
            goto L_0x03f5
        L_0x0433:
            int r6 = r7.size()
            int[] r10 = new int[r6]
            int[] r12 = new int[r6]
            r13 = 0
        L_0x043c:
            if (r13 >= r6) goto L_0x0455
            java.lang.Object r14 = r7.get(r13)
            android.graphics.Point r14 = (android.graphics.Point) r14
            int r14 = r14.x
            r10[r13] = r14
            java.lang.Object r14 = r7.get(r13)
            android.graphics.Point r14 = (android.graphics.Point) r14
            int r14 = r14.y
            r12[r13] = r14
            int r13 = r13 + 1
            goto L_0x043c
        L_0x0455:
            r13 = 2
            int[] r6 = new int[r13]
            int[] r7 = new int[r13]
            int[] r13 = r8.mXs
            r13 = r13[r5]
            r14 = 0
            r6[r14] = r13
            int[] r13 = r8.mYs
            r5 = r13[r5]
            r7[r14] = r5
            r5 = r10[r14]
            r13 = 1
            r6[r13] = r5
            r5 = r12[r14]
            r7[r13] = r5
            r5 = r6[r14]
            r15 = r6[r13]
            if (r5 != r15) goto L_0x047c
            r5 = r7[r14]
            r14 = r7[r13]
            if (r5 == r14) goto L_0x047f
        L_0x047c:
            r4.createAndAddLinkPathItem(r6, r7)
        L_0x047f:
            int r5 = r4.createAndAddLinkPathItem(r10, r12)
            r3.b = r5
            int r5 = r10.length
            int r5 = r5 - r13
            r5 = r10[r5]
            int r6 = r12.length
            int r6 = r6 - r13
            r6 = r12[r6]
            a(r11, r5, r6)
            com.autonavi.common.model.GeoPoint r5 = new com.autonavi.common.model.GeoPoint
            r6 = 0
            r7 = r10[r6]
            r10 = r12[r6]
            r5.<init>(r7, r10)
            int r6 = com.autonavi.minimap.R.drawable.foot_turnpoint
            int r7 = r3.b
            r9.a(r5, r6, r7)
            goto L_0x04da
        L_0x04a2:
            r6 = 2
            int[] r7 = new int[r6]
            int[] r10 = new int[r6]
            int[] r6 = r8.mXs
            r6 = r6[r5]
            r12 = 0
            r7[r12] = r6
            int[] r6 = r8.mYs
            r5 = r6[r5]
            r10[r12] = r5
            r5 = 1
            r7[r5] = r18
            r10[r5] = r17
            if (r18 <= 0) goto L_0x04da
            if (r17 <= 0) goto L_0x04da
            int r6 = r4.createAndAddLinkPathItem(r7, r10)
            r3.b = r6
            r6 = r7[r5]
            r13 = r10[r5]
            a(r11, r6, r13)
            com.autonavi.common.model.GeoPoint r5 = new com.autonavi.common.model.GeoPoint
            r6 = r7[r12]
            r7 = r10[r12]
            r5.<init>(r6, r7)
            int r6 = com.autonavi.minimap.R.drawable.foot_turnpoint
            int r7 = r3.b
            r9.a(r5, r6, r7)
        L_0x04da:
            if (r1 == 0) goto L_0x0549
            boolean r1 = r1.isStart
            if (r1 != 0) goto L_0x0549
            int[] r1 = r8.mXs
            int[] r5 = r8.mXs
            int r5 = r5.length
            r6 = 1
            int r5 = r5 - r6
            r1 = r1[r5]
            int[] r5 = r8.mYs
            int[] r7 = r8.mYs
            int r7 = r7.length
            int r7 = r7 - r6
            r5 = r5[r7]
            com.autonavi.bundle.routecommon.entity.BusPath$TaxiBusPath r6 = r2.taxiBusPath
            int[] r6 = r6.mXs
            com.autonavi.bundle.routecommon.entity.BusPath$TaxiBusPath r7 = r2.taxiBusPath
            int[] r7 = r7.mYs
            r8 = 0
            r10 = r6[r8]
            r12 = r7[r8]
            if (r1 == r10) goto L_0x0526
            if (r5 == r12) goto L_0x0526
            if (r1 <= 0) goto L_0x0526
            if (r5 <= 0) goto L_0x0526
            boolean r2 = r2.isExtBusStartCityPath
            if (r2 != 0) goto L_0x0526
            r2 = 2
            int[] r13 = new int[r2]
            int[] r2 = new int[r2]
            r13[r8] = r1
            r2[r8] = r5
            r1 = 1
            r13[r1] = r10
            r2[r1] = r12
            int r5 = r4.createAndAddLinkPathItem(r13, r2)
            r3.b = r5
            r5 = r13[r1]
            r2 = r2[r1]
            a(r11, r5, r2)
            goto L_0x0527
        L_0x0526:
            r1 = 1
        L_0x0527:
            int r2 = r4.createLineItem(r6, r7)
            r3.b = r2
            int r2 = r6.length
            int r2 = r2 - r1
            r2 = r6[r2]
            int r4 = r7.length
            int r4 = r4 - r1
            r1 = r7[r4]
            a(r11, r2, r1)
            com.autonavi.common.model.GeoPoint r1 = new com.autonavi.common.model.GeoPoint
            r2 = 0
            r4 = r6[r2]
            r2 = r7[r2]
            r1.<init>(r4, r2)
            int r2 = com.autonavi.minimap.R.drawable.taxi_turnpoint
            int r3 = r3.b
            r9.a(r1, r2, r3)
        L_0x0549:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dvb.a(com.autonavi.bundle.routecommon.entity.BusPath, dvw, com.autonavi.minimap.route.bus.extbus.overlay.ExtBusLineOverlay, int[]):int[]");
    }

    private static void a(BusEta busEta, ExtBusLineOverlay extBusLineOverlay) {
        if (busEta != null) {
            for (int i = 0; i < busEta.etalinks.length; i++) {
                int i2 = busEta.etalinks[i].etastate;
                int i3 = busEta.etalinks[i].startindex;
                int i4 = busEta.etalinks[i].endindex;
                boolean z = true;
                switch (i2) {
                    case -2:
                    case -1:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        break;
                    default:
                        z = false;
                        break;
                }
                if (z) {
                    extBusLineOverlay.createColorLineItem(b(busEta.mXs, i3, i4), b(busEta.mYs, i3, i4), i2);
                } else {
                    extBusLineOverlay.createColorLineItem(b(busEta.mXs, i3, i4), b(busEta.mYs, i3, i4), busEta.linestatus);
                }
            }
        }
    }

    private static int[] b(int[] iArr, int i, int i2) {
        if (iArr == null || iArr.length == 0) {
            return null;
        }
        int i3 = (i2 - i) + 1;
        if (i3 == 0) {
            return null;
        }
        int[] iArr2 = new int[i3];
        System.arraycopy(iArr, i, iArr2, 0, i3);
        return iArr2;
    }
}
