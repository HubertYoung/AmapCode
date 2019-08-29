package com.autonavi.minimap.route.bus.localbus.overlay;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.base.overlay.PointOverlay;
import java.util.HashMap;

public class RouteBusStationNameOverlay extends PointOverlay {
    private int index = 0;
    private LayoutInflater inflater = ((LayoutInflater) this.mContext.getSystemService("layout_inflater"));
    private HashMap<String, a> mFilterMap = new HashMap<>();
    private View mViewIcon;

    static class a {
        public String a;
        public int b;
        public int c;
        int d;
        public int e;
        public int f;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    static class b {
        TextView a;

        private b() {
        }

        /* synthetic */ b(byte b) {
            this();
        }
    }

    public RouteBusStationNameOverlay(bty bty) {
        super(bty);
        setHideIconWhenCovered(true);
        setCheckCover(true);
        showReversed(true);
    }

    public void addBusStationName(GeoPoint geoPoint, String str, CharSequence charSequence, String str2, int i) {
        if (Thread.currentThread().getName().equals("main")) {
            addBusStationNameEx(geoPoint, str, charSequence, str2, i, false);
            return;
        }
        bty bty = this.mMapView;
        final GeoPoint geoPoint2 = geoPoint;
        final String str3 = str;
        final CharSequence charSequence2 = charSequence;
        final String str4 = str2;
        final int i2 = i;
        AnonymousClass1 r1 = new Runnable() {
            public final void run() {
                RouteBusStationNameOverlay.this.addBusStationNameEx(geoPoint2, str3, charSequence2, str4, i2, false);
            }
        };
        bty.a((Runnable) r1);
    }

    public void addBusStationName(final GeoPoint geoPoint, final CharSequence charSequence, final String str) {
        if (Thread.currentThread().getName().equals("main")) {
            addBusStationNameEx(geoPoint, "", charSequence, str, 3, true);
        } else {
            this.mMapView.a((Runnable) new Runnable() {
                public final void run() {
                    RouteBusStationNameOverlay.this.addBusStationNameEx(geoPoint, "", charSequence, str, 3, true);
                }
            });
        }
    }

    public void addBusStationName(GeoPoint geoPoint, CharSequence charSequence, String str, int i) {
        if (Thread.currentThread().getName().equals("main")) {
            addBusLineStation(geoPoint, charSequence, str, i);
            return;
        }
        bty bty = this.mMapView;
        final GeoPoint geoPoint2 = geoPoint;
        final CharSequence charSequence2 = charSequence;
        final String str2 = str;
        final int i2 = i;
        AnonymousClass3 r1 = new Runnable() {
            public final void run() {
                RouteBusStationNameOverlay.this.addBusLineStation(geoPoint2, charSequence2, str2, i2);
            }
        };
        bty.a((Runnable) r1);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002e, code lost:
        r13 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0032, code lost:
        r0 = r3;
        r3 = 7;
        r13 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0035, code lost:
        r4 = new com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay.b(0);
        r11 = r7.inflater.inflate(r0, null);
        r4.a = (android.widget.TextView) r11.findViewById(com.autonavi.minimap.R.id.station_name);
        r0 = r4.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x004d, code lost:
        if (r24 == false) goto L_0x0052;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004f, code lost:
        r5 = 13.0f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0052, code lost:
        r5 = 12.0f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0054, code lost:
        r0.setTextSize(2, r5);
        r4.a.setTextColor(r7.mContext.getResources().getColor(com.autonavi.minimap.R.color.f_c_2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0068, code lost:
        if (r24 == false) goto L_0x0073;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006a, code lost:
        r4.a.getPaint().setFakeBoldText(true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0073, code lost:
        r4.a.setText(r21);
        r10 = new com.autonavi.minimap.base.overlay.PointOverlayItem(r8);
        r9 = new com.autonavi.map.core.MapViewLayoutParams(-2, -2, r8, 3);
        r9.mode = 0;
        r7.mMapView.a(r11, (android.widget.FrameLayout.LayoutParams) r9);
        r1 = r7.index;
        r7.index = r1 + 1;
        r10.mBgMarker = createMarker(r1, r11, r3, 0.0f, 0.0f, false);
        r0 = r7.mMapView;
        r1 = r8.x;
        r6 = r9;
        r9 = r0;
        r0 = r10;
        r10 = r1;
        r1 = r11;
        r2 = r13;
        r4 = r15;
        r9.a(r10, r8.y, r13, (float) (r10.mBgMarker.mWidth + defpackage.agn.a(r7.mContext, 8.0f)), (float) (r10.mBgMarker.mHeight + defpackage.agn.a(r7.mContext, 8.0f)), r15, -1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00da, code lost:
        if (r7.mFilterMap.keySet().contains(r4) != false) goto L_0x010c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00dc, code lost:
        r9 = new com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay.a(0);
        r9.a = r4;
        r9.b = r8.x;
        r9.c = r8.y;
        r9.d = r2;
        r9.e = r0.mBgMarker.mWidth + defpackage.agn.a(r7.mContext, 8.0f);
        r9.f = r0.mBgMarker.mHeight + defpackage.agn.a(r7.mContext, 8.0f);
        r7.mFilterMap.put(r4, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x010c, code lost:
        r7.mMapView.a(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0113, code lost:
        if (r7.mViewIcon != null) goto L_0x0122;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0115, code lost:
        if (r24 != false) goto L_0x0122;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0117, code lost:
        r7.mViewIcon = r7.inflater.inflate(com.autonavi.minimap.R.layout.bus_map_stationicon_tip_layout, null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0122, code lost:
        if (r24 != false) goto L_0x0147;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0124, code lost:
        r7.mMapView.a(r7.mViewIcon, (android.widget.FrameLayout.LayoutParams) r6);
        r1 = r7.index;
        r7.index = r1 + 1;
        r8 = r0;
        r8.mDefaultMarker = createMarker(r1, r7.mViewIcon, 4, 0.0f, 0.0f, false);
        r7.mMapView.a(r7.mViewIcon);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0147, code lost:
        r8 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0148, code lost:
        addItem(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x014b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addBusStationNameEx(com.autonavi.common.model.GeoPoint r19, java.lang.String r20, java.lang.CharSequence r21, java.lang.String r22, int r23, boolean r24) {
        /*
            r18 = this;
            r7 = r18
            r8 = r19
            r15 = r22
            android.view.LayoutInflater r0 = r7.inflater
            if (r0 != 0) goto L_0x0016
            android.content.Context r0 = r7.mContext
            java.lang.String r1 = "layout_inflater"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.view.LayoutInflater r0 = (android.view.LayoutInflater) r0
            r7.inflater = r0
        L_0x0016:
            r0 = 7
            r1 = 2
            r2 = 1
            r14 = 0
            switch(r23) {
                case 0: goto L_0x0030;
                case 1: goto L_0x002b;
                case 2: goto L_0x0027;
                case 3: goto L_0x0024;
                case 4: goto L_0x001e;
                default: goto L_0x001d;
            }
        L_0x001d:
            return
        L_0x001e:
            int r0 = com.autonavi.minimap.R.layout.bus_map_stationname_tip_left_layout
            r3 = 8
            r13 = 1
            goto L_0x0035
        L_0x0024:
            int r3 = com.autonavi.minimap.R.layout.bus_map_stationname_tip_right_layout
            goto L_0x0032
        L_0x0027:
            int r0 = com.autonavi.minimap.R.layout.bus_map_stationname_tip_top_layout
            r3 = 5
            goto L_0x002e
        L_0x002b:
            int r0 = com.autonavi.minimap.R.layout.bus_map_stationname_tip_bottom_layout
            r3 = 6
        L_0x002e:
            r13 = 2
            goto L_0x0035
        L_0x0030:
            int r3 = com.autonavi.minimap.R.layout.bus_map_stationname_tip_right_layout
        L_0x0032:
            r0 = r3
            r3 = 7
            r13 = 0
        L_0x0035:
            com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay$b r4 = new com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay$b
            r4.<init>(r14)
            android.view.LayoutInflater r5 = r7.inflater
            r12 = 0
            android.view.View r11 = r5.inflate(r0, r12)
            int r0 = com.autonavi.minimap.R.id.station_name
            android.view.View r0 = r11.findViewById(r0)
            android.widget.TextView r0 = (android.widget.TextView) r0
            r4.a = r0
            android.widget.TextView r0 = r4.a
            if (r24 == 0) goto L_0x0052
            r5 = 1095761920(0x41500000, float:13.0)
            goto L_0x0054
        L_0x0052:
            r5 = 1094713344(0x41400000, float:12.0)
        L_0x0054:
            r0.setTextSize(r1, r5)
            android.widget.TextView r0 = r4.a
            android.content.Context r1 = r7.mContext
            android.content.res.Resources r1 = r1.getResources()
            int r5 = com.autonavi.minimap.R.color.f_c_2
            int r1 = r1.getColor(r5)
            r0.setTextColor(r1)
            if (r24 == 0) goto L_0x0073
            android.widget.TextView r0 = r4.a
            android.text.TextPaint r0 = r0.getPaint()
            r0.setFakeBoldText(r2)
        L_0x0073:
            android.widget.TextView r0 = r4.a
            r1 = r21
            r0.setText(r1)
            com.autonavi.minimap.base.overlay.PointOverlayItem r10 = new com.autonavi.minimap.base.overlay.PointOverlayItem
            r10.<init>(r8)
            com.autonavi.map.core.MapViewLayoutParams r9 = new com.autonavi.map.core.MapViewLayoutParams
            r0 = 3
            r1 = -2
            r9.<init>(r1, r1, r8, r0)
            r9.mode = r14
            bty r0 = r7.mMapView
            r0.a(r11, r9)
            int r1 = r7.index
            int r0 = r1 + 1
            r7.index = r0
            r4 = 0
            r5 = 0
            r6 = 0
            r0 = r7
            r2 = r11
            com.autonavi.minimap.base.overlay.Marker r0 = r0.createMarker(r1, r2, r3, r4, r5, r6)
            r10.mBgMarker = r0
            bty r0 = r7.mMapView
            int r1 = r8.x
            int r2 = r8.y
            com.autonavi.minimap.base.overlay.Marker r3 = r10.mBgMarker
            int r3 = r3.mWidth
            android.content.Context r4 = r7.mContext
            r5 = 1090519040(0x41000000, float:8.0)
            int r4 = defpackage.agn.a(r4, r5)
            int r3 = r3 + r4
            float r3 = (float) r3
            com.autonavi.minimap.base.overlay.Marker r4 = r10.mBgMarker
            int r4 = r4.mHeight
            android.content.Context r6 = r7.mContext
            int r6 = defpackage.agn.a(r6, r5)
            int r4 = r4 + r6
            float r4 = (float) r4
            r16 = -1
            r6 = r9
            r9 = r0
            r0 = r10
            r10 = r1
            r1 = r11
            r11 = r2
            r2 = r12
            r12 = r13
            r2 = r13
            r13 = r3
            r3 = 0
            r14 = r4
            r4 = r15
            r9.a(r10, r11, r12, r13, r14, r15, r16)
            java.util.HashMap<java.lang.String, com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay$a> r9 = r7.mFilterMap
            java.util.Set r9 = r9.keySet()
            boolean r9 = r9.contains(r4)
            if (r9 != 0) goto L_0x010c
            com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay$a r9 = new com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay$a
            r9.<init>(r3)
            r9.a = r4
            int r3 = r8.x
            r9.b = r3
            int r3 = r8.y
            r9.c = r3
            r9.d = r2
            com.autonavi.minimap.base.overlay.Marker r2 = r0.mBgMarker
            int r2 = r2.mWidth
            android.content.Context r3 = r7.mContext
            int r3 = defpackage.agn.a(r3, r5)
            int r2 = r2 + r3
            r9.e = r2
            com.autonavi.minimap.base.overlay.Marker r2 = r0.mBgMarker
            int r2 = r2.mHeight
            android.content.Context r3 = r7.mContext
            int r3 = defpackage.agn.a(r3, r5)
            int r2 = r2 + r3
            r9.f = r2
            java.util.HashMap<java.lang.String, com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay$a> r2 = r7.mFilterMap
            r2.put(r4, r9)
        L_0x010c:
            bty r2 = r7.mMapView
            r2.a(r1)
            android.view.View r1 = r7.mViewIcon
            if (r1 != 0) goto L_0x0122
            if (r24 != 0) goto L_0x0122
            android.view.LayoutInflater r1 = r7.inflater
            int r2 = com.autonavi.minimap.R.layout.bus_map_stationicon_tip_layout
            r3 = 0
            android.view.View r1 = r1.inflate(r2, r3)
            r7.mViewIcon = r1
        L_0x0122:
            if (r24 != 0) goto L_0x0147
            bty r1 = r7.mMapView
            android.view.View r2 = r7.mViewIcon
            r1.a(r2, r6)
            int r1 = r7.index
            int r2 = r1 + 1
            r7.index = r2
            android.view.View r2 = r7.mViewIcon
            r3 = 4
            r4 = 0
            r5 = 0
            r6 = 0
            r8 = r0
            r0 = r7
            com.autonavi.minimap.base.overlay.Marker r0 = r0.createMarker(r1, r2, r3, r4, r5, r6)
            r8.mDefaultMarker = r0
            bty r0 = r7.mMapView
            android.view.View r1 = r7.mViewIcon
            r0.a(r1)
            goto L_0x0148
        L_0x0147:
            r8 = r0
        L_0x0148:
            r7.addItem(r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay.addBusStationNameEx(com.autonavi.common.model.GeoPoint, java.lang.String, java.lang.CharSequence, java.lang.String, int, boolean):void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0034, code lost:
        r13 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0038, code lost:
        r3 = 7;
        r13 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003a, code lost:
        r1 = new com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay.b(0);
        r2 = r7.inflater.inflate(r0, null);
        r1.a = (android.widget.TextView) r2.findViewById(com.autonavi.minimap.R.id.station_name);
        r1.a.setText(r19);
        r1.a.setFocusable(false);
        r1.a.setEnabled(false);
        r1.a.setTextColor(r7.mContext.getResources().getColor(com.autonavi.minimap.R.color.f_c_2));
        r1.a.getPaint().setShadowLayer(10.0f, 0.0f, 0.0f, r7.mContext.getResources().getColor(com.autonavi.minimap.R.color.white));
        r12 = new com.autonavi.minimap.base.overlay.PointOverlayItem(r8);
        r1 = r7.index;
        r7.index = r1 + 1;
        r12.mBgMarker = createMarker(r1, r2, r3, 10.0f, 0.0f, false);
        r3 = r12;
        r4 = r13;
        r1 = r15;
        r7.mMapView.a(r8.x, r8.y, r13, (float) (r12.mBgMarker.mWidth + defpackage.agn.a(r7.mContext, 8.0f)), (float) (r12.mBgMarker.mHeight + defpackage.agn.a(r7.mContext, 8.0f)), r15, -1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x00d5, code lost:
        if (r7.mFilterMap.keySet().contains(r1) != false) goto L_0x0107;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x00d7, code lost:
        r5 = new com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay.a(0);
        r5.a = r1;
        r5.b = r8.x;
        r5.c = r8.y;
        r5.d = r4;
        r5.e = r3.mBgMarker.mWidth + defpackage.agn.a(r7.mContext, 8.0f);
        r5.f = r3.mBgMarker.mHeight + defpackage.agn.a(r7.mContext, 8.0f);
        r7.mFilterMap.put(r1, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0107, code lost:
        addItem(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x010a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addBusLineStation(com.autonavi.common.model.GeoPoint r18, java.lang.CharSequence r19, java.lang.String r20, int r21) {
        /*
            r17 = this;
            r7 = r17
            r8 = r18
            r15 = r20
            android.view.LayoutInflater r0 = r7.inflater
            if (r0 != 0) goto L_0x0016
            android.content.Context r0 = r7.mContext
            java.lang.String r1 = "layout_inflater"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.view.LayoutInflater r0 = (android.view.LayoutInflater) r0
            r7.inflater = r0
        L_0x0016:
            r0 = 2
            r1 = 7
            r14 = 0
            switch(r21) {
                case 0: goto L_0x0036;
                case 1: goto L_0x002f;
                case 2: goto L_0x0029;
                case 3: goto L_0x0026;
                case 4: goto L_0x001d;
                default: goto L_0x001c;
            }
        L_0x001c:
            return
        L_0x001d:
            int r0 = com.autonavi.minimap.R.layout.bus_map_stationname_tip_left_layout
            r1 = 8
            r2 = 1
            r3 = 8
            r13 = 1
            goto L_0x003a
        L_0x0026:
            int r0 = com.autonavi.minimap.R.layout.busline_map_stationname_layout
            goto L_0x0038
        L_0x0029:
            int r1 = com.autonavi.minimap.R.layout.bus_map_stationname_tip_top_layout
            r2 = 5
            r0 = r1
            r3 = 5
            goto L_0x0034
        L_0x002f:
            int r1 = com.autonavi.minimap.R.layout.bus_map_stationname_tip_bottom_layout
            r2 = 6
            r0 = r1
            r3 = 6
        L_0x0034:
            r13 = 2
            goto L_0x003a
        L_0x0036:
            int r0 = com.autonavi.minimap.R.layout.bus_map_stationname_tip_right_layout
        L_0x0038:
            r3 = 7
            r13 = 0
        L_0x003a:
            com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay$b r1 = new com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay$b
            r1.<init>(r14)
            android.view.LayoutInflater r2 = r7.inflater
            r4 = 0
            android.view.View r2 = r2.inflate(r0, r4)
            int r0 = com.autonavi.minimap.R.id.station_name
            android.view.View r0 = r2.findViewById(r0)
            android.widget.TextView r0 = (android.widget.TextView) r0
            r1.a = r0
            android.widget.TextView r0 = r1.a
            r4 = r19
            r0.setText(r4)
            android.widget.TextView r0 = r1.a
            r0.setFocusable(r14)
            android.widget.TextView r0 = r1.a
            r0.setEnabled(r14)
            android.widget.TextView r0 = r1.a
            android.content.Context r4 = r7.mContext
            android.content.res.Resources r4 = r4.getResources()
            int r5 = com.autonavi.minimap.R.color.f_c_2
            int r4 = r4.getColor(r5)
            r0.setTextColor(r4)
            android.widget.TextView r0 = r1.a
            android.text.TextPaint r0 = r0.getPaint()
            r1 = 1092616192(0x41200000, float:10.0)
            android.content.Context r4 = r7.mContext
            android.content.res.Resources r4 = r4.getResources()
            int r5 = com.autonavi.minimap.R.color.white
            int r4 = r4.getColor(r5)
            r5 = 0
            r0.setShadowLayer(r1, r5, r5, r4)
            com.autonavi.minimap.base.overlay.PointOverlayItem r12 = new com.autonavi.minimap.base.overlay.PointOverlayItem
            r12.<init>(r8)
            int r1 = r7.index
            int r0 = r1 + 1
            r7.index = r0
            r4 = 1092616192(0x41200000, float:10.0)
            r6 = 0
            r0 = r7
            com.autonavi.minimap.base.overlay.Marker r0 = r0.createMarker(r1, r2, r3, r4, r5, r6)
            r12.mBgMarker = r0
            bty r9 = r7.mMapView
            int r10 = r8.x
            int r11 = r8.y
            com.autonavi.minimap.base.overlay.Marker r0 = r12.mBgMarker
            int r0 = r0.mWidth
            android.content.Context r1 = r7.mContext
            r2 = 1090519040(0x41000000, float:8.0)
            int r1 = defpackage.agn.a(r1, r2)
            int r0 = r0 + r1
            float r0 = (float) r0
            com.autonavi.minimap.base.overlay.Marker r1 = r12.mBgMarker
            int r1 = r1.mHeight
            android.content.Context r3 = r7.mContext
            int r3 = defpackage.agn.a(r3, r2)
            int r1 = r1 + r3
            float r1 = (float) r1
            r16 = -1
            r3 = r12
            r12 = r13
            r4 = r13
            r13 = r0
            r0 = 0
            r14 = r1
            r1 = r15
            r9.a(r10, r11, r12, r13, r14, r15, r16)
            java.util.HashMap<java.lang.String, com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay$a> r5 = r7.mFilterMap
            java.util.Set r5 = r5.keySet()
            boolean r5 = r5.contains(r1)
            if (r5 != 0) goto L_0x0107
            com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay$a r5 = new com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay$a
            r5.<init>(r0)
            r5.a = r1
            int r0 = r8.x
            r5.b = r0
            int r0 = r8.y
            r5.c = r0
            r5.d = r4
            com.autonavi.minimap.base.overlay.Marker r0 = r3.mBgMarker
            int r0 = r0.mWidth
            android.content.Context r4 = r7.mContext
            int r4 = defpackage.agn.a(r4, r2)
            int r0 = r0 + r4
            r5.e = r0
            com.autonavi.minimap.base.overlay.Marker r0 = r3.mBgMarker
            int r0 = r0.mHeight
            android.content.Context r4 = r7.mContext
            int r2 = defpackage.agn.a(r4, r2)
            int r0 = r0 + r2
            r5.f = r0
            java.util.HashMap<java.lang.String, com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay$a> r0 = r7.mFilterMap
            r0.put(r1, r5)
        L_0x0107:
            r7.addItem(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay.addBusLineStation(com.autonavi.common.model.GeoPoint, java.lang.CharSequence, java.lang.String, int):void");
    }

    public boolean clear() {
        if (this.mFilterMap.keySet().size() > 0) {
            for (String a2 : this.mFilterMap.keySet()) {
                this.mMapView.a(a2);
            }
        }
        this.mFilterMap.clear();
        this.index = 0;
        return super.clear();
    }

    public void clearFilter() {
        if (this.mFilterMap.keySet().size() > 0) {
            for (String next : this.mFilterMap.keySet()) {
                if (this.mMapView != null) {
                    this.mMapView.a(next);
                }
            }
        }
    }

    public void addFilter() {
        if (this.mFilterMap.size() > 0) {
            for (a next : this.mFilterMap.values()) {
                if (this.mMapView != null) {
                    this.mMapView.a(next.b, next.c, next.d, (float) next.e, (float) next.f, next.a, -1);
                }
            }
        }
    }
}
