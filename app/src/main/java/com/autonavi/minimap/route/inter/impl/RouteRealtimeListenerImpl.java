package com.autonavi.minimap.route.inter.impl;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay.OnItemClickListener;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import com.autonavi.minimap.route.bus.inter.impl.BusLineSearch;
import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.minimap.route.bus.realtimebus.RecommendResponse;
import com.autonavi.minimap.route.bus.realtimebus.callback.RealTimeBusSearchCallback;
import com.autonavi.minimap.route.bus.realtimebus.data.BusStationDataCallback;
import com.autonavi.minimap.route.bus.realtimebus.data.BusStationDataCallback.BusStationException;
import com.autonavi.minimap.route.bus.realtimebus.data.HeartBeatManager;
import com.autonavi.minimap.route.bus.realtimebus.data.HeartBeatRequest;
import com.autonavi.minimap.route.bus.realtimebus.data.POISearchManager;
import com.autonavi.minimap.route.bus.realtimebus.model.BusStationData;
import com.autonavi.minimap.route.bus.realtimebus.model.RTBusData;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeTempData;
import com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses;
import com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines;
import com.autonavi.minimap.route.bus.realtimebus.model.RecommendStation;
import com.autonavi.minimap.route.bus.realtimebus.model.RecommendStationData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import proguard.annotation.KeepName;

@KeepName
public class RouteRealtimeListenerImpl implements a {
    public AbstractBaseMapPage a;
    public Context b;
    public dwa c;
    public dxs d;
    public boolean e = false;
    public boolean f;
    public boolean g;
    public boolean h;
    public BusStationData i;
    public int j = 0;
    public boolean k;
    public POISearchManager l;
    public String m;
    public RealTimeTempData n;
    public HeartBeatRequest o;
    public int p;
    public boolean q;
    public boolean r;
    public BusStationDataCallback s = new BusStationDataCallback() {
        public final void a(final RTBusData rTBusData) {
            aho.a(new Runnable() {
                public final void run() {
                    if (RouteRealtimeListenerImpl.this.c != null && AMapPageUtil.isHomePage()) {
                        RouteRealtimeListenerImpl.this.c.j();
                    }
                    if (RouteRealtimeListenerImpl.this.e) {
                        RouteRealtimeListenerImpl.this.f();
                    }
                }
            });
        }

        public final void a(final BusStationException busStationException) {
            aho.a(new Runnable() {
                public final void run() {
                    if (RouteRealtimeListenerImpl.this.c != null && !RouteRealtimeListenerImpl.this.e && AMapPageUtil.isHomePage()) {
                        dwa a2 = RouteRealtimeListenerImpl.this.c;
                        busStationException.getStateCode();
                        bmn.b();
                        a2.a((RecommendStationData) null);
                        a2.p = null;
                        if (busStationException != null && busStationException.getStateCode() == 12) {
                            RouteRealtimeListenerImpl.this.c();
                        }
                    }
                }
            });
        }
    };
    public final BroadcastReceiver t = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction()) && RouteRealtimeListenerImpl.this.a != null) {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) RouteRealtimeListenerImpl.this.a.getActivity().getSystemService("connectivity")).getActiveNetworkInfo();
                bmn.b();
                if (RouteRealtimeListenerImpl.this.c != null && activeNetworkInfo != null) {
                    activeNetworkInfo.isConnected();
                }
            }
        }
    };
    public a u = new a() {
        public final void a() {
            if (RouteRealtimeListenerImpl.this.c != null) {
                RouteRealtimeListenerImpl.this.c.k();
                if (RouteRealtimeListenerImpl.this.c != null) {
                    RouteRealtimeListenerImpl.this.c.b(false);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public GeoPoint v;
    private AosResponseCallbackOnUi w;

    public final void a(BroadcastReceiver broadcastReceiver, boolean z) {
        if (this.a != null) {
            Activity activity = this.a.getActivity();
            if (activity != null) {
                try {
                    if (this.f || !z) {
                        if (this.f) {
                            activity.unregisterReceiver(broadcastReceiver);
                            this.f = false;
                        }
                        return;
                    }
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                    if (activity.registerReceiver(broadcastReceiver, intentFilter) != null) {
                        this.f = true;
                    }
                } catch (Exception unused) {
                    this.f = false;
                }
            }
        }
    }

    public final void a() {
        if (this.c != null && this.c.v != null) {
            this.c.v.b();
        }
    }

    public final boolean b() {
        if (this.c != null) {
            return this.c.I;
        }
        return false;
    }

    public final void c() {
        this.p = 0;
        if (this.c != null) {
            this.c.l();
            this.c.a();
            if (this.r) {
                this.r = false;
                this.c.b(true);
            }
        }
        a(true);
    }

    public final void a(BusStationData busStationData) {
        a(busStationData, false, -1);
    }

    public final void a(BusStationData busStationData, boolean z, int i2) {
        a(busStationData, z, 0, i2, false);
    }

    public final void a(BusStationData busStationData, boolean z, int i2, int i3, boolean z2) {
        this.p = 0;
        this.q = false;
        if (busStationData != null) {
            a(busStationData, z);
            if (this.c != null) {
                dwa dwa = this.c;
                boolean isFollow = busStationData.isFollow();
                final BusStationData busStationData2 = busStationData;
                final int i4 = i2;
                final int i5 = i3;
                final boolean z3 = z2;
                AnonymousClass4 r2 = new OnItemClickListener() {
                    public final void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
                        if (NetworkReachability.b() || NetworkReachability.a()) {
                            RouteRealtimeListenerImpl.this.c.l();
                            RouteRealtimeListenerImpl.this.a(busStationData2, i4, i5, z3);
                            return;
                        }
                        RouteRealtimeListenerImpl.this.h();
                        RouteRealtimeListenerImpl.this.d.a((dyg) null);
                    }
                };
                if (dwa.a(isFollow, (OnItemClickListener) r2, busStationData)) {
                    a(false);
                    this.d.a((dyg) null);
                    return;
                }
            }
            a(busStationData, i2, i3, z2);
        }
    }

    public final void a(BusStationData busStationData, int i2, int i3, boolean z) {
        if (busStationData != null && !b()) {
            this.v = busStationData.getGeoPoint();
            final BusStationData busStationData2 = busStationData;
            final int i4 = i3;
            final int i5 = i2;
            final boolean z2 = z;
            AnonymousClass5 r1 = new AosResponseCallbackOnUi<RecommendResponse>() {
                public /* synthetic */ void onSuccess(AosResponse aosResponse) {
                    RecommendResponse recommendResponse = (RecommendResponse) aosResponse;
                    if (RouteRealtimeListenerImpl.this.i() && recommendResponse != null && !RouteRealtimeListenerImpl.this.b()) {
                        RecommendStation recommendStation = (RecommendStation) recommendResponse.getResult();
                        String str = null;
                        if (recommendStation == null || !recommendStation.getCode().equals("1")) {
                            RouteRealtimeListenerImpl.this.a(false);
                            RouteRealtimeListenerImpl.this.q = true;
                            RouteRealtimeListenerImpl.a(RouteRealtimeListenerImpl.this, (dyg) null);
                        } else {
                            RecommendStationData data = recommendStation.getData();
                            List<RecommenStationLines> linesInOrder = data.getLinesInOrder(busStationData2);
                            int i = i4;
                            RealTimeBusStation busStationByIndex = busStationData2.getBusStationByIndex(i5);
                            if (z2) {
                                if (busStationByIndex != null && !dxx.a((Collection<T>) linesInOrder)) {
                                    int size = linesInOrder.size();
                                    int i2 = 0;
                                    while (true) {
                                        if (i2 >= size) {
                                            break;
                                        } else if (busStationByIndex.bus_id.equals(linesInOrder.get(i2).getLineid())) {
                                            i = i2;
                                            break;
                                        } else {
                                            i2++;
                                        }
                                    }
                                }
                            } else if (-1 == i) {
                                i = data.getDefaultSelectedLineIndex(busStationData2);
                            }
                            RouteRealtimeListenerImpl routeRealtimeListenerImpl = RouteRealtimeListenerImpl.this;
                            if (busStationByIndex != null) {
                                str = busStationByIndex.adcode;
                            }
                            routeRealtimeListenerImpl.a(linesInOrder, i, str, RouteRealtimeListenerImpl.f(RouteRealtimeListenerImpl.this));
                        }
                    }
                }

                public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                    if (RouteRealtimeListenerImpl.this.i()) {
                        RouteRealtimeListenerImpl.this.q = true;
                        RouteRealtimeListenerImpl.a(RouteRealtimeListenerImpl.this, RouteRealtimeListenerImpl.f(RouteRealtimeListenerImpl.this), (OnItemClickListener) new OnItemClickListener() {
                            public final void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
                                RouteRealtimeListenerImpl.this.a(busStationData2, i5, i4, z2);
                            }
                        });
                    }
                }
            };
            this.w = r1;
            POISearchManager.a(busStationData.poiId, this.w);
        }
    }

    public final void a(List<RecommenStationLines> list, int i2, String str) {
        boolean z;
        if (this.i == null) {
            z = false;
        } else {
            z = this.i.isFollow();
        }
        a(list, i2, str, z);
    }

    /* access modifiers changed from: private */
    public void a(List<RecommenStationLines> list, int i2, String str, boolean z) {
        if (i()) {
            RecommenStationLines recommenStationLines = list.get(i2);
            if (recommenStationLines != null && !b()) {
                a(recommenStationLines.getLineid(), list, i2, str, z);
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(String str, List<RecommenStationLines> list, int i2, String str2, boolean z) {
        final boolean z2 = z;
        final List<RecommenStationLines> list2 = list;
        final int i3 = i2;
        final String str3 = str;
        final String str4 = str2;
        AnonymousClass6 r0 = new RealTimeBusSearchCallback() {
            public void onResultParseDoneCallback(IBusLineSearchResult iBusLineSearchResult) {
                Bus bus;
                IBusLineSearchResult iBusLineSearchResult2 = iBusLineSearchResult;
                if (!RouteRealtimeListenerImpl.this.b() && RouteRealtimeListenerImpl.this.i()) {
                    ViewGroup viewGroup = null;
                    if (iBusLineSearchResult2 == null) {
                        bus = null;
                    } else {
                        bus = iBusLineSearchResult2.getBusLine(0);
                    }
                    if (bus != null) {
                        RouteRealtimeListenerImpl.this.m = bus.returnId;
                        if (RouteRealtimeListenerImpl.this.c != null) {
                            dwa a2 = RouteRealtimeListenerImpl.this.c;
                            GeoPoint[] a3 = dxx.a(bus.coordX, bus.coordY);
                            if (a2.f != null) {
                                a2.q();
                                a2.f.createAndAddBackgroundLineItemRT(a3, -12417025, -14594141, agn.a(a2.j, 3.0f), 4);
                                a2.f.createAndAddArrowLineItemRT(a3);
                            }
                            dwa a4 = RouteRealtimeListenerImpl.this.c;
                            if (!(bus == null || bus.stations == null || a4.h == null)) {
                                a4.A = a4.A == null ? "" : a4.A;
                                a4.p();
                                a4.h.clear();
                                int length = bus.stations.length;
                                int i = 0;
                                while (i < length) {
                                    GeoPoint geoPoint = new GeoPoint(bus.stationX[i], bus.stationY[i]);
                                    if (!a4.A.equals(bus.stationpoiid1[i]) && !a4.A.equals(bus.stationpoiid2[i])) {
                                        PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
                                        View inflate = LayoutInflater.from(a4.j).inflate(R.layout.realtime_bus_map_stationicon_tip_layout, viewGroup);
                                        PointOverlayItem pointOverlayItem2 = pointOverlayItem;
                                        pointOverlayItem2.mDefaultMarker = a4.i.createMarker(i, inflate, 4, 0.0f, 0.0f, false);
                                        a4.i.addItem(pointOverlayItem2);
                                        a4.h.addBusStationName(geoPoint, bus.stations[i], bus.stationIds[i]);
                                    }
                                    i++;
                                    viewGroup = null;
                                }
                                String[] strArr = bus.stationpoiid1;
                                if (strArr != null && !dxx.a((Collection<T>) a4.p)) {
                                    Map<String, BusStationData> convertListToMap = RTBusData.convertListToMap(a4.p);
                                    if (convertListToMap != null) {
                                        a4.a(a4.M, true);
                                        a4.M = new HashMap();
                                        if (strArr != null) {
                                            for (String str : strArr) {
                                                BusStationData busStationData = convertListToMap.get(str);
                                                if (busStationData != null) {
                                                    a4.M.put(Integer.valueOf(busStationData.pointItemIndex), busStationData);
                                                }
                                            }
                                        }
                                        a4.a(a4.M, false);
                                    }
                                }
                            }
                            RouteRealtimeListenerImpl.this.c.a(z2, RouteRealtimeListenerImpl.this.i);
                        }
                        RouteRealtimeListenerImpl.this.a(bus, list2, i3);
                        return;
                    }
                    eao.a((String) "---realtimebus---", (String) "----searchBuslineByIDInner----bus--is null");
                    RouteRealtimeListenerImpl.this.a(false);
                    RouteRealtimeListenerImpl.this.q = true;
                    RouteRealtimeListenerImpl.a(RouteRealtimeListenerImpl.this, RouteRealtimeListenerImpl.b(null, list2, i3));
                }
            }

            public void error(Throwable th, boolean z) {
                if (RouteRealtimeListenerImpl.this.i()) {
                    RouteRealtimeListenerImpl.this.q = true;
                    RouteRealtimeListenerImpl.a(RouteRealtimeListenerImpl.this, z2, (OnItemClickListener) new OnItemClickListener() {
                        public final void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
                            RouteRealtimeListenerImpl.this.a(str3, list2, i3, str4, z2);
                        }
                    });
                }
            }
        };
        BusLineSearch.a(str, str2, (Callback<IBusLineSearchResult>) r0, false);
    }

    public final void a(final Bus bus, List<RecommenStationLines> list, int i2) {
        if (bus != null) {
            final RecommenStationLines recommenStationLines = list.get(i2);
            if (recommenStationLines != null) {
                this.n = new RealTimeTempData(bus, bus.areacode, list, i2);
                final dyg b2 = b(bus, list, i2);
                if (!recommenStationLines.isRealTime()) {
                    if (this.c != null) {
                        this.c.a(this.v, recommenStationLines.getKey_name(), recommenStationLines.getFormateStartTime(), recommenStationLines.getFormateEndTime());
                    }
                    this.d.a(b2);
                    a(false);
                    this.q = true;
                    return;
                }
                a(false);
                this.o = POISearchManager.a(bus.areacode, bus.id, recommenStationLines.getStationid(), new dyc<RealtimeBuses>() {
                    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0116, code lost:
                        if (r4 <= 0.0d) goto L_0x014a;
                     */
                    /* JADX WARNING: Removed duplicated region for block: B:67:0x0180  */
                    /* JADX WARNING: Removed duplicated region for block: B:68:0x0186  */
                    /* JADX WARNING: Removed duplicated region for block: B:75:0x01c3  */
                    /* JADX WARNING: Removed duplicated region for block: B:81:0x01ef  */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public final /* synthetic */ void a(java.lang.Object r30) {
                        /*
                            r29 = this;
                            r0 = r29
                            r1 = r30
                            com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses r1 = (com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses) r1
                            com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl r2 = com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl.this
                            r3 = 0
                            r2.p = r3
                            com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl r2 = com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl.this
                            boolean r2 = r2.b()
                            if (r2 != 0) goto L_0x02a4
                            boolean r2 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.isHomePage()
                            if (r2 != 0) goto L_0x001b
                            goto L_0x02a4
                        L_0x001b:
                            if (r1 == 0) goto L_0x029c
                            int r2 = r1.code
                            r4 = 1
                            if (r2 != r4) goto L_0x029c
                            com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl r2 = com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl.this
                            com.autonavi.minimap.route.bus.realtimebus.data.HeartBeatRequest r2 = r2.o
                            r0.a(r2, r1)
                            java.util.List<com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses$RealtimeBus> r1 = r1.buses
                            boolean r2 = defpackage.dxx.a(r1)
                            if (r2 == 0) goto L_0x0035
                            r2 = 0
                            goto L_0x003b
                        L_0x0035:
                            java.lang.Object r2 = r1.get(r3)
                            com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses$RealtimeBus r2 = (com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses.RealtimeBus) r2
                        L_0x003b:
                            if (r2 == 0) goto L_0x0256
                            int r6 = r2.status
                            if (r6 != r4) goto L_0x0256
                            com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl r6 = com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl.this
                            dwa r6 = r6.c
                            if (r6 == 0) goto L_0x027f
                            com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl r6 = com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl.this
                            dwa r6 = r6.c
                            com.autonavi.minimap.route.bus.model.Bus r7 = r5
                            com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses$RealtimeBusTrip r2 = r2.getNearestBusTrip()
                            if (r2 == 0) goto L_0x027f
                            com.autonavi.common.model.GeoPoint r8 = new com.autonavi.common.model.GeoPoint
                            double r9 = r2.x
                            double r11 = r2.y
                            r8.<init>(r9, r11)
                            int[] r9 = r7.coordX
                            int[] r10 = r7.coordY
                            com.autonavi.common.model.GeoPoint[] r9 = defpackage.dxx.a(r9, r10)
                            dyu r10 = new dyu
                            r10.<init>(r8, r9)
                            com.autonavi.common.model.GeoPoint r8 = r10.a
                            com.autonavi.common.model.GeoPoint[] r9 = r10.b
                            r11 = 2
                            if (r9 != 0) goto L_0x0077
                            r5 = 0
                            goto L_0x00ea
                        L_0x0077:
                            com.autonavi.common.model.GeoPoint[] r9 = r10.b
                            r9 = r9[r3]
                            com.autonavi.common.model.GeoPoint[] r12 = r10.b
                            r12 = r12[r3]
                            double r12 = defpackage.dyu.a(r12, r8)
                            r15 = r9
                            r9 = 1
                            r14 = 0
                        L_0x0086:
                            com.autonavi.common.model.GeoPoint[] r5 = r10.b
                            int r5 = r5.length
                            if (r9 >= r5) goto L_0x00a2
                            com.autonavi.common.model.GeoPoint[] r5 = r10.b
                            r5 = r5[r9]
                            double r17 = defpackage.dyu.a(r8, r5)
                            int r5 = (r17 > r12 ? 1 : (r17 == r12 ? 0 : -1))
                            if (r5 > 0) goto L_0x009f
                            com.autonavi.common.model.GeoPoint[] r5 = r10.b
                            r5 = r5[r9]
                            r15 = r5
                            r14 = r9
                            r12 = r17
                        L_0x009f:
                            int r9 = r9 + 1
                            goto L_0x0086
                        L_0x00a2:
                            int r5 = r14 + 1
                            com.autonavi.common.model.GeoPoint[] r8 = r10.b
                            int r8 = r8.length
                            if (r5 >= r8) goto L_0x00ae
                            com.autonavi.common.model.GeoPoint[] r8 = r10.b
                            r8 = r8[r5]
                            goto L_0x00af
                        L_0x00ae:
                            r8 = 0
                        L_0x00af:
                            com.autonavi.common.model.GeoPoint[] r9 = r10.b
                            int r9 = r9.length
                            int r9 = r9 - r11
                            r12 = 4643985272004935680(0x4072c00000000000, double:300.0)
                            if (r14 != r9) goto L_0x00d0
                            int r14 = r14 - r4
                            r5 = r15
                        L_0x00bc:
                            if (r14 <= 0) goto L_0x00cd
                            com.autonavi.common.model.GeoPoint[] r5 = r10.b
                            r5 = r5[r14]
                            double r8 = defpackage.dyu.a(r5, r15)
                            int r8 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
                            if (r8 > 0) goto L_0x00cd
                            int r14 = r14 + -1
                            goto L_0x00bc
                        L_0x00cd:
                            r8 = r15
                            r15 = r5
                            goto L_0x00e4
                        L_0x00d0:
                            com.autonavi.common.model.GeoPoint[] r9 = r10.b
                            int r9 = r9.length
                            if (r5 >= r9) goto L_0x00e4
                            com.autonavi.common.model.GeoPoint[] r8 = r10.b
                            r8 = r8[r5]
                            double r17 = defpackage.dyu.a(r15, r8)
                            int r9 = (r17 > r12 ? 1 : (r17 == r12 ? 0 : -1))
                            if (r9 > 0) goto L_0x00e4
                            int r5 = r5 + 1
                            goto L_0x00d0
                        L_0x00e4:
                            com.autonavi.common.model.GeoPoint[] r5 = new com.autonavi.common.model.GeoPoint[r11]
                            r5[r3] = r15
                            r5[r4] = r8
                        L_0x00ea:
                            r8 = 4640537203540230144(0x4066800000000000, double:180.0)
                            r12 = 0
                            if (r5 == 0) goto L_0x0149
                            r10 = r5[r3]
                            r5 = r5[r4]
                            if (r10 == 0) goto L_0x0149
                            if (r5 != 0) goto L_0x00fc
                            goto L_0x0149
                        L_0x00fc:
                            double r14 = r5.getLongitude()
                            double r17 = r10.getLongitude()
                            double r14 = r14 - r17
                            double r17 = r5.getLatitude()
                            double r19 = r10.getLatitude()
                            double r4 = r17 - r19
                            int r10 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
                            if (r10 != 0) goto L_0x0119
                            int r4 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
                            if (r4 > 0) goto L_0x0149
                            goto L_0x014a
                        L_0x0119:
                            int r17 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
                            if (r17 != 0) goto L_0x012b
                            if (r10 <= 0) goto L_0x0125
                            r8 = 4636033603912859648(0x4056800000000000, double:90.0)
                            goto L_0x014a
                        L_0x0125:
                            r8 = 4643457506423603200(0x4070e00000000000, double:270.0)
                            goto L_0x014a
                        L_0x012b:
                            double r4 = java.lang.Math.atan2(r4, r14)
                            if (r10 < 0) goto L_0x0132
                            goto L_0x0134
                        L_0x0132:
                            r12 = 4607182418800017408(0x3ff0000000000000, double:1.0)
                        L_0x0134:
                            r14 = 4646624099911598080(0x407c200000000000, double:450.0)
                            r17 = 4614256656552045848(0x400921fb54442d18, double:3.141592653589793)
                            double r4 = r4 / r17
                            double r4 = r4 * r8
                            double r14 = r14 - r4
                            double r14 = r14 % r8
                            double r12 = r12 * r8
                            double r8 = r14 + r12
                            goto L_0x014a
                        L_0x0149:
                            r8 = r12
                        L_0x014a:
                            double r4 = r2.x
                            double r12 = r2.y
                            r6.o()
                            r6.m()
                            com.autonavi.common.model.GeoPoint r10 = new com.autonavi.common.model.GeoPoint
                            r10.<init>(r4, r12)
                            com.autonavi.minimap.base.overlay.PointOverlayItem r4 = new com.autonavi.minimap.base.overlay.PointOverlayItem
                            r4.<init>(r10)
                            com.autonavi.map.core.MapViewLayoutParams r5 = new com.autonavi.map.core.MapViewLayoutParams
                            r12 = 3
                            r13 = -2
                            r5.<init>(r13, r13, r10, r12)
                            r5.mode = r3
                            android.content.Context r13 = r6.j
                            android.view.LayoutInflater r13 = android.view.LayoutInflater.from(r13)
                            int r14 = com.autonavi.minimap.R.layout.realtime_bus_icon_layout
                            r15 = 0
                            android.view.View r13 = r13.inflate(r14, r15)
                            int r14 = com.autonavi.minimap.R.id.bus_icon
                            android.view.View r14 = r13.findViewById(r14)
                            android.widget.ImageView r14 = (android.widget.ImageView) r14
                            android.graphics.Bitmap r15 = r6.H
                            if (r15 == 0) goto L_0x0186
                            android.graphics.Bitmap r15 = r6.H
                            r14.setImageBitmap(r15)
                            goto L_0x018b
                        L_0x0186:
                            int r15 = com.autonavi.minimap.R.drawable.marker_realtime_bus
                            r14.setImageResource(r15)
                        L_0x018b:
                            com.autonavi.minimap.route.bus.localbus.overlay.RouteBusPointOverlay r14 = r6.b
                            r22 = 315(0x13b, float:4.41E-43)
                            r24 = 4
                            r25 = 0
                            r26 = 0
                            r27 = 0
                            r21 = r14
                            r23 = r13
                            com.autonavi.minimap.base.overlay.Marker r14 = r21.createMarker(r22, r23, r24, r25, r26, r27)
                            r4.mDefaultMarker = r14
                            bty r14 = r6.l
                            r14.a(r13, r5)
                            int r5 = (int) r8
                            r4.mAngle = r5
                            r4.mAngleMode = r12
                            bty r5 = r6.l
                            r5.a(r13)
                            com.autonavi.minimap.route.bus.localbus.overlay.RouteBusPointOverlay r5 = r6.b
                            r5.addItemWithAngle(r4)
                            duz r4 = r6.L
                            if (r4 == 0) goto L_0x0239
                            com.autonavi.common.model.GeoPoint r4 = r6.B
                            if (r4 == 0) goto L_0x0239
                            duz r4 = r6.L
                            com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage r5 = r6.k
                            if (r5 == 0) goto L_0x01e8
                            bty r5 = r5.getMapView()
                            if (r5 == 0) goto L_0x01e8
                            dzv r8 = new dzv
                            float r13 = r5.I()
                            float r14 = r5.v()
                            float r15 = r5.J()
                            com.autonavi.common.model.GeoPoint r16 = r5.o()
                            int r17 = r5.j(r3)
                            int r18 = r5.k(r3)
                            r12 = r8
                            r12.<init>(r13, r14, r15, r16, r17, r18)
                            goto L_0x01e9
                        L_0x01e8:
                            r8 = 0
                        L_0x01e9:
                            r4.a = r8
                            ean r4 = r6.t
                            if (r4 == 0) goto L_0x0239
                            ean r4 = r6.t
                            com.autonavi.common.model.GeoPoint[] r5 = new com.autonavi.common.model.GeoPoint[r11]
                            com.autonavi.common.model.GeoPoint r8 = r6.B
                            r5[r3] = r8
                            r8 = 1
                            r5[r8] = r10
                            r8 = -999999999(0xffffffffc4653601, float:-916.8438)
                            r9 = 999999999(0x3b9ac9ff, float:0.004723787)
                            r8 = 999999999(0x3b9ac9ff, float:0.004723787)
                            r10 = -999999999(0xffffffffc4653601, float:-916.8438)
                            r12 = -999999999(0xffffffffc4653601, float:-916.8438)
                        L_0x0209:
                            if (r3 >= r11) goto L_0x022e
                            r13 = r5[r3]
                            int r13 = r13.x
                            int r9 = java.lang.Math.min(r9, r13)
                            r13 = r5[r3]
                            int r13 = r13.y
                            int r8 = java.lang.Math.min(r8, r13)
                            r13 = r5[r3]
                            int r13 = r13.x
                            int r10 = java.lang.Math.max(r10, r13)
                            r13 = r5[r3]
                            int r13 = r13.y
                            int r12 = java.lang.Math.max(r12, r13)
                            int r3 = r3 + 1
                            goto L_0x0209
                        L_0x022e:
                            android.graphics.Rect r3 = new android.graphics.Rect
                            r3.<init>()
                            r3.set(r9, r8, r10, r12)
                            r4.b(r3)
                        L_0x0239:
                            android.content.Context r3 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()
                            com.autonavi.minimap.route.bus.localbus.overlay.RealtimeTipOverlay r4 = r6.g
                            com.autonavi.common.model.GeoPoint r5 = new com.autonavi.common.model.GeoPoint
                            double r8 = r2.x
                            double r10 = r2.y
                            r5.<init>(r8, r10)
                            java.lang.String r6 = r7.key_name
                            int r7 = r2.arrival
                            int r2 = r2.station_left
                            java.lang.String r2 = defpackage.ebj.a(r3, r7, r2)
                            r4.addTipUpBusIcon(r5, r6, r2)
                            goto L_0x027f
                        L_0x0256:
                            com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl r2 = com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl.this
                            dwa r2 = r2.c
                            if (r2 == 0) goto L_0x027f
                            com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl r2 = com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl.this
                            dwa r2 = r2.c
                            com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl r3 = com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl.this
                            com.autonavi.common.model.GeoPoint r3 = r3.v
                            com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines r4 = r0
                            java.lang.String r4 = r4.getKey_name()
                            com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines r5 = r0
                            java.lang.String r5 = r5.getFormateStartTime()
                            com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines r6 = r0
                            java.lang.String r6 = r6.getFormateEndTime()
                            r2.a(r3, r4, r5, r6)
                        L_0x027f:
                            com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl r2 = com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl.this
                            dxs r2 = r2.d
                            if (r2 == 0) goto L_0x029b
                            if (r1 == 0) goto L_0x0290
                            dyg r2 = r6
                            java.util.List<com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses$RealtimeBus> r2 = r2.c
                            r2.addAll(r1)
                        L_0x0290:
                            com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl r1 = com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl.this
                            dxs r1 = r1.d
                            dyg r2 = r6
                            r1.a(r2)
                        L_0x029b:
                            return
                        L_0x029c:
                            com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl r1 = com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl.this
                            dyg r2 = r6
                            com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl.a(r1, r2)
                            return
                        L_0x02a4:
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl.AnonymousClass7.a(java.lang.Object):void");
                    }

                    public final void a() {
                        RouteRealtimeListenerImpl routeRealtimeListenerImpl = RouteRealtimeListenerImpl.this;
                        int i = routeRealtimeListenerImpl.p;
                        routeRealtimeListenerImpl.p = i + 1;
                        if (i < 5) {
                            RouteRealtimeListenerImpl.this.h();
                        }
                        if (RouteRealtimeListenerImpl.this.p >= 5 && RouteRealtimeListenerImpl.this.c != null && recommenStationLines != null) {
                            RouteRealtimeListenerImpl.this.c.o();
                            RouteRealtimeListenerImpl.this.c.a(RouteRealtimeListenerImpl.this.v, recommenStationLines.getKey_name(), recommenStationLines.getFormateStartTime(), recommenStationLines.getFormateEndTime());
                            if (RouteRealtimeListenerImpl.this.d != null) {
                                RouteRealtimeListenerImpl.this.d.a((dyg) null);
                            }
                        }
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    @NonNull
    public static dyg b(Bus bus, List<RecommenStationLines> list, int i2) {
        dyg dyg = new dyg();
        dyg.a = new ArrayList();
        if (list != null) {
            dyg.a.addAll(list);
        }
        dyg.b = i2;
        dyg.c = new ArrayList();
        dyg.d = bus;
        return dyg;
    }

    private void a(BusStationData busStationData, boolean z) {
        if (!z) {
            this.j = 2;
        }
        this.i = busStationData;
        if (this.d != null) {
            this.d.a(this.i, z);
        }
    }

    public final void a(RecommendStationData recommendStationData) {
        if (this.d != null) {
            dxs dxs = this.d;
            if (dxs.a != null) {
                dxs.a.b = recommendStationData;
            }
        }
    }

    public final void a(boolean z) {
        if (this.o != null) {
            HeartBeatManager.a().a(this.o);
            if (z) {
                this.n = null;
                this.o = null;
                if (this.c != null) {
                    this.c.N = false;
                }
            }
        }
    }

    public final void d() {
        l();
        this.e = false;
    }

    public final void e() {
        if (this.c != null) {
            k();
        }
    }

    private static boolean k() {
        if (AMapPageUtil.isHomePage()) {
            bmn.b();
        }
        return false;
    }

    private boolean l() {
        if (this.e) {
            k();
        }
        return false;
    }

    public final void f() {
        this.e = true;
        bmn.b();
    }

    public final boolean g() {
        if (!this.e && AMapPageUtil.isHomePage()) {
            bmn.b();
        }
        return false;
    }

    public final void h() {
        if (AMapPageUtil.isHomePage() && this.d != null) {
            this.d.a(R.string.toast_realtime_network_error);
        }
    }

    public final boolean i() {
        return this.a != null && this.a.isResumed() && AMapPageUtil.isHomePage();
    }

    public static boolean j() {
        return bmn.b();
    }

    static /* synthetic */ boolean f(RouteRealtimeListenerImpl routeRealtimeListenerImpl) {
        if (routeRealtimeListenerImpl.i == null) {
            return false;
        }
        return routeRealtimeListenerImpl.i.isFollow();
    }

    static /* synthetic */ void a(RouteRealtimeListenerImpl routeRealtimeListenerImpl, dyg dyg) {
        eao.a((String) "---realtimebus---", (String) "----getRealTimeData----realtimeBuses--is null");
        if (routeRealtimeListenerImpl.c != null) {
            routeRealtimeListenerImpl.c.o();
            routeRealtimeListenerImpl.c.m();
            if (routeRealtimeListenerImpl.i != null) {
                routeRealtimeListenerImpl.c.a(routeRealtimeListenerImpl.i.isFollow(), routeRealtimeListenerImpl.i);
            }
            dwa dwa = routeRealtimeListenerImpl.c;
            if (AMapPageUtil.isHomePage() && dwa.g != null) {
                dwa.g.showNoNetTip(dwa.B, dwa.j.getResources().getString(R.string.toast_realtime_no_realtime_bus_data), false);
            }
        }
        if (routeRealtimeListenerImpl.d != null) {
            routeRealtimeListenerImpl.d.a(dyg);
        }
    }

    static /* synthetic */ void a(RouteRealtimeListenerImpl routeRealtimeListenerImpl, boolean z, OnItemClickListener onItemClickListener) {
        GeoPoint geoPoint;
        if (routeRealtimeListenerImpl.c != null) {
            routeRealtimeListenerImpl.c.m();
            routeRealtimeListenerImpl.c.a(z, onItemClickListener, routeRealtimeListenerImpl.i);
            if (routeRealtimeListenerImpl.i != null) {
                RealTimeBusStation busStationByIndex = routeRealtimeListenerImpl.i.getBusStationByIndex(routeRealtimeListenerImpl.i.pointItemIndex);
                if (busStationByIndex == null) {
                    geoPoint = null;
                } else {
                    geoPoint = new GeoPoint(busStationByIndex.station_lon.doubleValue(), busStationByIndex.station_lat.doubleValue());
                }
                if (geoPoint != null) {
                    routeRealtimeListenerImpl.c.c(geoPoint);
                }
            }
        }
    }
}
