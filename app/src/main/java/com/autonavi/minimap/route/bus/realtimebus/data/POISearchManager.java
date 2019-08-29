package com.autonavi.minimap.route.bus.realtimebus.data;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.searchservice.api.ISearchService;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.realtimebus.RecommendResponse;
import com.autonavi.minimap.route.bus.realtimebus.data.BusStationDataCallback.BusStationException;
import com.autonavi.minimap.route.bus.realtimebus.data.SearchBuslines.BuslinesEntity;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation;
import com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses;
import com.autonavi.minimap.route.bus.realtimebus.model.RecommendStation;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class POISearchManager {
    GeoPoint a;
    BusStationDataCallback b;
    dxr c;
    HeartBeatRequest d;
    /* access modifiers changed from: 0000 */
    public int e = 0;
    dyc f = new dyc<RealtimeBuses>() {
        public final /* synthetic */ void a(Object obj) {
            final RealtimeBuses realtimeBuses = (RealtimeBuses) obj;
            b();
            if (POISearchManager.this.b != null) {
                int i = realtimeBuses == null ? 7 : realtimeBuses.code;
                if (i == 7) {
                    POISearchManager.this.e = POISearchManager.this.e + 1;
                } else if (i == 1) {
                    POISearchManager.this.e = 0;
                }
                if (!POISearchManager.c(POISearchManager.this)) {
                    ahl.b(new defpackage.ahl.a<Object>() {
                        public final void onError(Throwable th) {
                        }

                        /* JADX WARNING: Removed duplicated region for block: B:20:0x006a  */
                        /* JADX WARNING: Removed duplicated region for block: B:30:0x00b3  */
                        /* Code decompiled incorrectly, please refer to instructions dump. */
                        public final java.lang.Object doBackground() throws java.lang.Exception {
                            /*
                                r9 = this;
                                com.autonavi.minimap.route.bus.realtimebus.data.POISearchManager$2 r0 = com.autonavi.minimap.route.bus.realtimebus.data.POISearchManager.AnonymousClass2.this
                                com.autonavi.minimap.route.bus.realtimebus.data.POISearchManager r0 = com.autonavi.minimap.route.bus.realtimebus.data.POISearchManager.this
                                dxr r0 = r0.c
                                com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses r1 = r3
                                com.autonavi.minimap.route.bus.realtimebus.model.RTBusData r2 = r0.c
                                if (r2 != 0) goto L_0x000f
                                r0.a()
                            L_0x000f:
                                java.util.Map<java.lang.String, com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation> r2 = r0.b
                                r3 = 0
                                if (r2 == 0) goto L_0x00ef
                                com.autonavi.minimap.route.bus.realtimebus.model.RTBusData r2 = r0.c
                                r2.resetDataCache()
                                if (r1 == 0) goto L_0x0053
                                java.util.List<com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses$RealtimeBus> r1 = r1.buses
                                boolean r2 = defpackage.dsx.a(r1)
                                if (r2 != 0) goto L_0x0053
                                java.util.HashMap r2 = new java.util.HashMap
                                r2.<init>()
                                java.util.Iterator r1 = r1.iterator()
                            L_0x002c:
                                boolean r4 = r1.hasNext()
                                if (r4 == 0) goto L_0x0054
                                java.lang.Object r4 = r1.next()
                                com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses$RealtimeBus r4 = (com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses.RealtimeBus) r4
                                com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation r5 = new com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation
                                r5.<init>()
                                java.lang.String r6 = r4.station
                                r5.station_id = r6
                                int r6 = r4.status
                                r5.status = r6
                                java.lang.String r6 = r4.line
                                r5.line = r6
                                java.util.List<com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses$RealtimeBusTrip> r6 = r4.trip
                                r5.trip = r6
                                java.lang.String r4 = r4.station
                                r2.put(r4, r5)
                                goto L_0x002c
                            L_0x0053:
                                r2 = r3
                            L_0x0054:
                                if (r2 == 0) goto L_0x00a3
                                int r1 = r2.size()
                                if (r1 <= 0) goto L_0x00a3
                                java.util.Set r1 = r2.entrySet()
                                java.util.Iterator r1 = r1.iterator()
                            L_0x0064:
                                boolean r2 = r1.hasNext()
                                if (r2 == 0) goto L_0x00a3
                                java.lang.Object r2 = r1.next()
                                java.util.Map$Entry r2 = (java.util.Map.Entry) r2
                                java.lang.Object r4 = r2.getKey()
                                java.lang.String r4 = (java.lang.String) r4
                                java.lang.Object r2 = r2.getValue()
                                com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation r2 = (com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation) r2
                                if (r2 == 0) goto L_0x0064
                                java.util.List<com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses$RealtimeBusTrip> r5 = r2.trip
                                if (r5 == 0) goto L_0x0064
                                java.util.Map<java.lang.String, com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation> r5 = r0.b
                                java.lang.Object r5 = r5.get(r4)
                                if (r5 == 0) goto L_0x0064
                                java.util.Map<java.lang.String, com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation> r5 = r0.b
                                java.lang.Object r5 = r5.get(r4)
                                com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation r5 = (com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation) r5
                                java.util.List<com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses$RealtimeBusTrip> r6 = r2.trip
                                r5.trip = r6
                                java.util.Map<java.lang.String, com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation> r5 = r0.b
                                java.lang.Object r4 = r5.get(r4)
                                com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation r4 = (com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation) r4
                                int r2 = r2.status
                                r4.status = r2
                                goto L_0x0064
                            L_0x00a3:
                                java.util.Map<java.lang.String, com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation> r1 = r0.b
                                java.util.Set r1 = r1.entrySet()
                                java.util.Iterator r1 = r1.iterator()
                            L_0x00ad:
                                boolean r2 = r1.hasNext()
                                if (r2 == 0) goto L_0x00e5
                                java.lang.Object r2 = r1.next()
                                java.util.Map$Entry r2 = (java.util.Map.Entry) r2
                                java.lang.Object r2 = r2.getValue()
                                com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation r2 = (com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation) r2
                                com.autonavi.minimap.route.bus.realtimebus.model.RTBusData r4 = r0.c
                                java.lang.String r5 = r2.poiid1
                                com.autonavi.minimap.route.bus.realtimebus.model.BusStationData r4 = r4.getRTBusData(r5)
                                if (r4 != 0) goto L_0x00e1
                                com.autonavi.minimap.route.bus.realtimebus.model.BusStationData r4 = new com.autonavi.minimap.route.bus.realtimebus.model.BusStationData
                                java.lang.String r5 = r2.station_name
                                java.lang.String r6 = r2.poiid1
                                java.lang.Double r7 = r2.station_lon
                                java.lang.Double r8 = r2.station_lat
                                r4.<init>(r5, r6, r7, r8)
                                r4.addRealTimeBusStation(r2)
                                com.autonavi.minimap.route.bus.realtimebus.model.RTBusData r5 = r0.c
                                java.lang.String r2 = r2.poiid1
                                r5.addRTBusData(r2, r4)
                                goto L_0x00ad
                            L_0x00e1:
                                r4.addRealTimeBusStation(r2)
                                goto L_0x00ad
                            L_0x00e5:
                                com.autonavi.minimap.route.bus.realtimebus.model.RTBusData r1 = r0.c
                                r1.filterNoRTStation()
                                java.util.Map<java.lang.String, com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation> r0 = r0.b
                                r0.clear()
                            L_0x00ef:
                                return r3
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.realtimebus.data.POISearchManager.AnonymousClass2.AnonymousClass1.doBackground():java.lang.Object");
                        }

                        public final void onFinished(Object obj) {
                            if (POISearchManager.this.c.c == null) {
                                POISearchManager.this.b.a(new BusStationException(10, AMapAppGlobal.getApplication().getString(R.string.toast_realtime_no_station)));
                            }
                        }
                    });
                }
            }
        }

        private void b() {
            HeartBeatManager.a().a(POISearchManager.this.d);
            dxu.a((AosResponseCallback<RecommendResponse>) new RecommendCallback<RecommendResponse>(POISearchManager.this, 0));
        }

        public final void a() {
            b();
            POISearchManager.this.e = POISearchManager.this.e + 1;
            if (!POISearchManager.c(POISearchManager.this)) {
                POISearchManager.a(POISearchManager.this);
            }
        }
    };
    private bbm g;
    private a h;
    private aeb i = new aeb<aud>() {
        public final /* synthetic */ void callback(Object obj) {
            aud aud = (aud) obj;
            if (POISearchManager.this.b != null) {
                if (aud == null || !(aud.c == null || 1 == aud.c.errorCode)) {
                    a();
                    return;
                }
                auc auc = aud.b;
                if (auc != null) {
                    ArrayList<POI> arrayList = auc.d;
                    if (arrayList != null) {
                        final ArrayList<RealTimeBusStation> a2 = dxu.a(arrayList, POISearchManager.this.c.a);
                        if (dxx.a((Collection<T>) a2)) {
                            a();
                            return;
                        } else {
                            POISearchManager.this.c.a();
                            ahl.b(new defpackage.ahl.a<List<btd>>() {
                                public final void onError(Throwable th) {
                                }

                                public final /* synthetic */ void onFinished(Object obj) {
                                    List<btd> list = (List) obj;
                                    POISearchManager pOISearchManager = POISearchManager.this;
                                    StringBuilder sb = new StringBuilder();
                                    StringBuilder sb2 = new StringBuilder();
                                    boolean z = false;
                                    for (btd btd : list) {
                                        if (btd != null && btd.isRealTimeBus()) {
                                            if (z) {
                                                sb.append(",");
                                                sb2.append(",");
                                            }
                                            sb.append(btd.bus_id);
                                            sb2.append(btd.station_id);
                                            z = true;
                                        }
                                    }
                                    pOISearchManager.d = POISearchManager.a(((btd) list.get(0)).adcode, sb.toString(), sb2.toString(), pOISearchManager.f, false);
                                    POISearchManager.this.c();
                                }

                                public final /* synthetic */ Object doBackground() throws Exception {
                                    ArrayList arrayList = new ArrayList();
                                    Collections.sort(a2, new defpackage.dxr.a());
                                    POISearchManager.this.c.a(a2);
                                    arrayList.add((btd) a2.get(a2.size() - 1));
                                    return arrayList;
                                }
                            });
                        }
                    }
                }
                a();
            }
        }

        public final void error(int i) {
            POISearchManager.a(POISearchManager.this);
        }

        private void a() {
            POISearchManager.this.b.a(new BusStationException(10, AMapAppGlobal.getApplication().getString(R.string.toast_realtime_no_station)));
        }
    };

    class RecommendCallback implements AosResponseCallback<RecommendResponse> {
        private RecommendCallback() {
        }

        /* synthetic */ RecommendCallback(POISearchManager pOISearchManager, byte b) {
            this();
        }

        public /* synthetic */ void onSuccess(AosResponse aosResponse) {
            a((RecommendStation) ((RecommendResponse) aosResponse).getResult());
        }

        private void a(RecommendStation recommendStation) {
            if (POISearchManager.this.c != null) {
                dxr dxr = POISearchManager.this.c;
                if (dxr.c == null) {
                    dxr.a();
                }
                dxr.c.recommendStation = recommendStation;
            }
            if (POISearchManager.this.b != null) {
                POISearchManager.this.e = 0;
                POISearchManager.this.b.a(POISearchManager.this.c.c);
            }
        }

        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            eao.b("RecommendCallback", aosResponseException.toString());
            a(null);
        }
    }

    class a extends Handler {
        private a() {
        }

        /* synthetic */ a(POISearchManager pOISearchManager, byte b) {
            this();
        }

        public final void handleMessage(Message message) {
            if (message.what == 0) {
                if (cfe.a(POISearchManager.this.a, LocationInstrument.getInstance().getLatestPosition()) > 100.0f) {
                    POISearchManager.this.a();
                    return;
                }
                a();
            }
        }

        public final void a() {
            removeMessages(0);
            sendEmptyMessageDelayed(0, 60000);
        }
    }

    public POISearchManager(BusStationDataCallback busStationDataCallback) {
        this.b = busStationDataCallback;
        this.c = new dxr(AMapAppGlobal.getApplication());
    }

    public final void a() {
        b();
        d();
        this.a = LocationInstrument.getInstance().getLatestPosition();
        if (this.a != null) {
            ISearchService iSearchService = (ISearchService) defpackage.esb.a.a.a(ISearchService.class);
            if (iSearchService != null) {
                ael ael = new ael(AMapAppGlobal.getApplication().getString(R.string.bus_real_time_around_keyword), this.a);
                ael.h = 0;
                ael.d = 1;
                ael.k = 100;
                ael.e = "1000";
                aeo aeo = new aeo();
                aeo.g = "1";
                ael.j = aeo;
                SuperId.getInstance().reset();
                SuperId.getInstance().setBit1(SuperId.BIT_1_MAIN_BUSSTATION);
                SuperId.getInstance().setBit2(SuperId.BIT_2_MAIN_BUSSTATION);
                iSearchService.b(aew.a((aem) ael), 0, this.i);
            }
        }
    }

    public final void b() {
        if (this.g != null) {
            this.g.a();
        }
        if (this.d != null) {
            HeartBeatManager.a().a(this.d);
        }
        d();
    }

    public final void c() {
        if (this.h != null) {
            d();
        }
        this.h = new a(this, 0);
        this.h.a();
    }

    public static HeartBeatRequest a(String str, String str2, String str3, dyc<RealtimeBuses> dyc) {
        return a(str, str2, str3, dyc, true);
    }

    public static AosRequest a(String str, AosResponseCallback<RecommendResponse> aosResponseCallback) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str) || aosResponseCallback == null) {
            return null;
        }
        AosPostRequest b2 = aax.b(new BuslinesEntity(str));
        yq.a();
        yq.a((AosRequest) b2, aosResponseCallback);
        return b2;
    }

    static HeartBeatRequest a(String str, String str2, String str3, dyc<RealtimeBuses> dyc, boolean z) {
        return dxu.a(str, str2, str3, dyc, "5", "10", "0", z);
    }

    public final void d() {
        if (this.h != null) {
            this.h.removeMessages(0);
            this.h.removeCallbacks(null);
            this.h = null;
        }
    }

    static /* synthetic */ void a(POISearchManager pOISearchManager) {
        String str;
        int i2;
        if (pOISearchManager.b != null) {
            if (!NetworkReachability.b()) {
                i2 = 11;
                str = AMapAppGlobal.getApplication().getString(R.string.toast_realtime_network_error);
                if (pOISearchManager.c.c != null) {
                    pOISearchManager.b.a(pOISearchManager.c.c);
                }
            } else {
                i2 = 10;
                str = AMapAppGlobal.getApplication().getString(R.string.toast_realtime_no_station);
            }
            pOISearchManager.b.a(new BusStationException(i2, str));
        }
    }

    static /* synthetic */ boolean c(POISearchManager pOISearchManager) {
        if (pOISearchManager.e < 2) {
            return false;
        }
        pOISearchManager.b.a(new BusStationException(12, ""));
        pOISearchManager.e = 0;
        return true;
    }
}
