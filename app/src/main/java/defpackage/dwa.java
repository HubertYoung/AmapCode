package defpackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.wireless.security.SecExceptionCode;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.gloverlay.GLPointOverlay;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay.OnItemClickListener;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.route.bus.busline.overlay.BusLinePointOverlay;
import com.autonavi.minimap.route.bus.localbus.overlay.BusRadarOverlayManager$14;
import com.autonavi.minimap.route.bus.localbus.overlay.RealtimeStationNameOverlay;
import com.autonavi.minimap.route.bus.localbus.overlay.RealtimeTipOverlay;
import com.autonavi.minimap.route.bus.localbus.overlay.RecomStationPointOverlay;
import com.autonavi.minimap.route.bus.localbus.overlay.RouteBusLineOverlay;
import com.autonavi.minimap.route.bus.localbus.overlay.RouteBusPointOverlay;
import com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay;
import com.autonavi.minimap.route.bus.realtimebus.RecommendResponse;
import com.autonavi.minimap.route.bus.realtimebus.model.BusStationData;
import com.autonavi.minimap.route.bus.realtimebus.model.RTBConfigData;
import com.autonavi.minimap.route.bus.realtimebus.model.RTBZipFileData;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation;
import com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses.RealtimeBusTrip;
import com.autonavi.minimap.route.bus.realtimebus.model.RecommendStation;
import com.autonavi.minimap.route.bus.realtimebus.model.RecommendStationData;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;

/* renamed from: dwa reason: default package */
/* compiled from: BusRadarOverlayManager */
public final class dwa {
    public String A;
    public GeoPoint B;
    RTBConfigData C;
    Bitmap D = null;
    Bitmap E = null;
    Bitmap F = null;
    Bitmap G = null;
    public Bitmap H = null;
    public boolean I;
    public int J = -1;
    public boolean K = true;
    public duz L;
    public Map<Integer, BusStationData> M;
    public boolean N;
    private RealtimeStationNameOverlay O;
    private dwf P;
    private int Q = -11;
    private volatile int R;
    private View S = null;
    private int T = -1;
    public RouteBusPointOverlay a;
    public RouteBusPointOverlay b;
    public RouteBusPointOverlay c;
    public RealtimeStationNameOverlay d;
    public RecomStationPointOverlay e;
    public RouteBusLineOverlay f;
    public RealtimeTipOverlay g;
    public RouteBusStationNameOverlay h;
    public BusLinePointOverlay i;
    public Context j;
    public AbstractBaseMapPage k;
    public bty l;
    public LayoutInflater m;
    public HashSet<String> n = new HashSet<>();
    public a o;
    public List<BusStationData> p;
    public boolean q;
    public boolean r;
    public boolean s;
    public ean t;
    public boolean u;
    public dwe v;
    RecommendStation w;
    public GeoPoint x;
    BusStationData y = null;
    public boolean z = true;

    /* renamed from: dwa$a */
    /* compiled from: BusRadarOverlayManager */
    public interface a {
        void a(BusStationData busStationData);

        void a(RecommendStationData recommendStationData);
    }

    /* renamed from: dwa$b */
    /* compiled from: BusRadarOverlayManager */
    class b implements c {
        private b() {
        }

        /* synthetic */ b(dwa dwa, byte b) {
            this();
        }

        public final void a(final RealtimeBusTrip realtimeBusTrip) {
            aho.a(new Runnable() {
                public final void run() {
                    if (dwa.this.v != null && dwa.this.w != null) {
                        View a2 = dwa.this.v.a(dwa.this.w, realtimeBusTrip);
                        String bubble_style = dwa.this.w.getData().getBubble_style();
                        dwa.this.a(dwa.this.x, dwe.a(dwa.this.x), bubble_style, a2);
                    }
                }
            });
        }
    }

    public dwa(AbstractBaseMapPage abstractBaseMapPage) {
        this.l = abstractBaseMapPage.getMapManager().getMapView();
        this.j = abstractBaseMapPage.getContext();
        this.k = abstractBaseMapPage;
        this.v = new dwe(this.j);
        c();
        this.L = new duz();
        this.P = new dwf(this.l);
    }

    public final void a(AbstractBaseMapPage abstractBaseMapPage) {
        if (abstractBaseMapPage.getMapManager() != null && abstractBaseMapPage.getMapManager().getMapView() != null) {
            bty mapView = abstractBaseMapPage.getMapManager().getMapView();
            this.f = new RouteBusLineOverlay(mapView);
            this.f.getGLOverlay().setOverlayPriority(3);
            this.e = new RecomStationPointOverlay(mapView);
            this.a = new RouteBusPointOverlay(mapView);
            ((GLPointOverlay) this.a.getGLOverlay()).setOverlayPriority(MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_, 850);
            this.b = new RouteBusPointOverlay(mapView);
            ((GLPointOverlay) this.b.getGLOverlay()).setOverlayPriority(MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_, 850);
            this.g = new RealtimeTipOverlay(mapView);
            ((GLPointOverlay) this.g.getGLOverlay()).setOverlayPriority(MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_, 850);
            this.h = new RouteBusStationNameOverlay(mapView);
            this.i = new BusLinePointOverlay(mapView);
            this.i.getGLOverlay().setOverlayPriority(828);
            this.d = new RealtimeStationNameOverlay(mapView);
            ((GLPointOverlay) this.d.getGLOverlay()).setOverlayPriority(130);
            this.O = new RealtimeStationNameOverlay(mapView);
            ((GLPointOverlay) this.O.getGLOverlay()).setOverlayPriority(830);
            this.c = new RouteBusPointOverlay(mapView);
            ((GLPointOverlay) this.c.getGLOverlay()).setOverlayPriority(MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_, 850);
            abstractBaseMapPage.addOverlay(this.f, false);
            abstractBaseMapPage.addOverlay(this.h, false);
            abstractBaseMapPage.addOverlay(this.e, false);
            abstractBaseMapPage.addOverlay(this.a, false);
            abstractBaseMapPage.addOverlay(this.b, false);
            abstractBaseMapPage.addOverlay(this.g, false);
            abstractBaseMapPage.addOverlay(this.i, false);
            abstractBaseMapPage.addOverlay(this.d, false);
            abstractBaseMapPage.addOverlay(this.c, false);
            abstractBaseMapPage.addOverlay(this.O, false);
            dwf dwf = this.P;
            if (!(abstractBaseMapPage == null || dwf.a == null || dwf.b == null)) {
                ((GLPointOverlay) dwf.a.getGLOverlay()).setOverlayPriority(998);
                ((GLPointOverlay) dwf.b.getGLOverlay()).setOverlayPriority(SecExceptionCode.SEC_ERROR_UMID_UNKNOWN_ERR);
                abstractBaseMapPage.addOverlay(dwf.a, false);
                abstractBaseMapPage.addOverlay(dwf.b, false);
            }
            this.i.setMinDisplayLevel(15);
            this.h.setMinDisplayLevel(15);
            this.d.setMinDisplayLevel(18);
            this.d.setCheckCover(true);
            this.d.showReversed(true);
        }
    }

    public final boolean a(boolean z2, OnItemClickListener onItemClickListener, BusStationData busStationData) {
        if (!AMapPageUtil.isHomePage() || NetworkReachability.b() || NetworkReachability.a() || this.g == null) {
            return false;
        }
        this.g.showNoNetTip(this.B);
        a(z2, busStationData);
        this.g.setOnItemClickListener(onItemClickListener);
        return true;
    }

    public final GeoPoint a(BusStationData busStationData) {
        if (busStationData != null) {
            l();
            this.A = busStationData.poiId;
            RealTimeBusStation busStationByIndex = busStationData.getBusStationByIndex(busStationData.selectedLineIndex);
            if (busStationByIndex != null) {
                this.B = new GeoPoint(busStationByIndex.station_lon.doubleValue(), busStationByIndex.station_lat.doubleValue());
                return this.B;
            }
        }
        return null;
    }

    private void s() {
        if (-1 != this.Q) {
            t();
        }
        dxx.a(this.a, this.J, false, false);
        a(this.J, (String) "");
        this.Q = this.J;
    }

    private void t() {
        RealTimeBusStation realTimeBusStation;
        dxx.a(this.a, this.Q, true, true);
        BusStationData u2 = u();
        if (u2 == null) {
            realTimeBusStation = null;
        } else {
            realTimeBusStation = u2.getBusStation();
        }
        a(this.Q, realTimeBusStation == null ? "" : realTimeBusStation.station_name);
        this.Q = -1;
    }

    public final void a() {
        t();
        a(this.M, true);
        if (!dxx.a(this.M)) {
            this.M.clear();
        }
    }

    private BusStationData u() {
        if (dxx.a((Collection<T>) this.p) || this.Q < 0 || this.Q >= this.p.size()) {
            return null;
        }
        return this.p.get(this.Q);
    }

    public final void a(boolean z2) {
        if (this.a != null) {
            this.a.showReversed(z2);
        }
    }

    public final void b() {
        f();
        g();
        this.v.v = null;
        this.v.w = null;
        this.v.e = true;
        this.v.f = true;
    }

    public final void c() {
        this.C = dyv.a();
        v();
        if (this.C == null || this.C.isDownLoadAll()) {
            if (this.C != null && this.C.isDownLoadAll()) {
                this.v.d = this.C;
                d();
            }
            return;
        }
        dyt dyt = new dyt();
        dyt.a = new defpackage.dyt.b() {
            public final void a() {
                dwa.this.C.setDownLoadAll(true);
                dwa.this.v.d = dwa.this.C;
                dyv.a(dwa.this.C);
                dyv.b(dwa.this.C.getImgAbsolutePath());
                dwa.this.d();
            }
        };
        dyt.a(this.C);
    }

    private void v() {
        if (this.C != null && this.C.isDownLoadAll() && !dyv.a(this.C.getRtbZipFileData().getPaths())) {
            this.C.setDownLoadAll(false);
            this.v.d = this.C;
        }
    }

    public final void d() {
        if (this.C != null && this.C.getRtbZipFileData() != null) {
            final RTBZipFileData rtbZipFileData = this.C.getRtbZipFileData();
            ahm.a(new Runnable() {
                public final void run() {
                    if (dwa.this.D == null) {
                        dwa.this.D = dyv.c(rtbZipFileData.getRtbBusLike());
                    }
                    if (dwa.this.E == null) {
                        dwa.this.E = dyv.c(rtbZipFileData.getRtbBusNormal());
                    }
                    if (dwa.this.F == null) {
                        dwa.this.F = dyv.c(rtbZipFileData.getRtbSelected());
                    }
                    if (dwa.this.G == null) {
                        dwa.this.G = dyv.c(rtbZipFileData.getRtbBuslikeSelected());
                    }
                    if (dwa.this.H == null) {
                        dwa.this.H = dyv.c(rtbZipFileData.getRtbBusIcon());
                    }
                }
            });
        }
    }

    public final void e() {
        if (this.a != null) {
            this.a.clear();
        }
        if (this.d != null) {
            this.d.clear();
        }
    }

    private void w() {
        if (this.v != null && this.z && !this.N && !"0".equals(this.v.b) && this.x != null) {
            a(this.x);
        }
    }

    public final boolean f() {
        if (this.P == null) {
            return false;
        }
        this.P.a();
        return true;
    }

    public final void g() {
        if (this.e != null) {
            this.e.clear();
        }
    }

    public final void a(final GeoPoint geoPoint) {
        if (!(geoPoint == null || this.e == null || this.w == null || this.w.getData() == null)) {
            this.e.clear();
            String bubble_style = this.w.getData().getBubble_style();
            if ("3".equals(bubble_style) || "4".equals(bubble_style) || "6".equals(bubble_style)) {
                if (!this.v.f) {
                    this.v.f = true;
                }
                this.v.w = new defpackage.dwe.b() {
                    public final void a() {
                        dwa.this.v.f = false;
                        dwa.this.e.clear();
                        dwa.this.b(geoPoint);
                    }
                };
                this.S = this.v.a(this.w);
                return;
            }
            this.S = this.v.a(this.w);
            b(geoPoint);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b(GeoPoint geoPoint) {
        if (this.S != null) {
            PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
            int i2 = this.v.u;
            this.l.a(this.S, (LayoutParams) dwe.a(geoPoint));
            pointOverlayItem.mDefaultMarker = this.e.createMarker(i2, this.S, 5, 0.0f, 0.0f, false);
            this.l.a(this.S);
            this.e.addItem(pointOverlayItem);
        }
    }

    private void d(GeoPoint geoPoint) {
        MapViewLayoutParams mapViewLayoutParams;
        if (geoPoint != null && this.v != null && this.l != null) {
            i();
            if (!(this.w == null || this.w.getData() == null)) {
                String bubble_style = this.w.getData().getBubble_style();
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("type", bubble_style);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                LogManager.actionLogV2("P00367", "B002", jSONObject);
                if ("0".equals(bubble_style)) {
                    mapViewLayoutParams = dwe.a(LocationInstrument.getInstance().getLatestPosition());
                } else {
                    mapViewLayoutParams = dwe.a(geoPoint);
                }
                a(geoPoint, mapViewLayoutParams, bubble_style, this.v.a(this.w, (RealtimeBusTrip) null));
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(final GeoPoint geoPoint, final MapViewLayoutParams mapViewLayoutParams, String str, final View view) {
        if ("3".equals(str) || "4".equals(str) || "6".equals(str)) {
            this.v.v = new defpackage.dwe.a() {
                public final void a() {
                    dwa.this.v.e = false;
                    dwa.this.a(view, geoPoint, mapViewLayoutParams);
                }
            };
            return;
        }
        a(view, geoPoint, mapViewLayoutParams);
    }

    /* access modifiers changed from: 0000 */
    public final void a(View view, GeoPoint geoPoint, MapViewLayoutParams mapViewLayoutParams) {
        OnItemClickListener onItemClickListener;
        if (view != null) {
            if (this.S != null) {
                this.l.a(this.S);
            }
            int id = view.getId();
            OnItemClickListener onItemClickListener2 = null;
            if (R.id.realtime_bus_daily_recom_common == id) {
                view.findViewById(R.id.realtime_bus_white_close_container).setVisibility(8);
                this.P.a(geoPoint, view, mapViewLayoutParams);
                view.findViewById(R.id.real_bus_daily_recom_common_outside_main).setVisibility(4);
                view.findViewById(R.id.realtime_bus_white_close_container).setVisibility(0);
                this.P.b(geoPoint, view, mapViewLayoutParams);
                onItemClickListener2 = new OnItemClickListener() {
                    public final void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
                        dwa.a(dwa.this);
                        dwa.this.f();
                    }
                };
                onItemClickListener = new OnItemClickListener() {
                    public final void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
                        dwa.this.f();
                        dwa.this.z = false;
                        LogManager.actionLogV2("P00367", "B003");
                    }
                };
            } else if (R.id.realtime_bus_recom_common == id) {
                view.findViewById(R.id.realtime_bus_gray_close_container).setVisibility(8);
                this.P.a(geoPoint, view, mapViewLayoutParams);
                view.findViewById(R.id.real_bus_recom_common_outside_main).setVisibility(4);
                view.findViewById(R.id.realtime_bus_gray_close_container).setVisibility(0);
                this.P.b(geoPoint, view, mapViewLayoutParams);
                onItemClickListener2 = new OnItemClickListener() {
                    public final void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
                        dwa.a(dwa.this);
                        dwa.this.f();
                    }
                };
                onItemClickListener = new OnItemClickListener() {
                    public final void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
                        dwa.this.f();
                        dwa.this.z = false;
                        LogManager.actionLogV2("P00367", "B003");
                    }
                };
            } else if (R.id.realtime_bus_without_busstop == id) {
                view.findViewById(R.id.real_bus_without_stop_close).setVisibility(8);
                this.P.a(geoPoint, view, mapViewLayoutParams);
                view.findViewById(R.id.real_bus_without_stop_main).setVisibility(4);
                view.findViewById(R.id.real_bus_without_stop_close).setVisibility(0);
                this.P.b(geoPoint, view, mapViewLayoutParams);
                onItemClickListener = new OnItemClickListener() {
                    public final void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
                        dwa.this.f();
                    }
                };
            } else {
                onItemClickListener = null;
            }
            this.P.a(onItemClickListener2, onItemClickListener);
        }
    }

    private void a(int i2, String str) {
        int i3 = i2 + 100;
        View inflate = LayoutInflater.from(this.j).inflate(R.layout.realtimebus_label_layout, null);
        if (inflate != null) {
            ((TextView) inflate.findViewById(R.id.realtimebus_label)).setText(str);
            this.d.resetItemDefaultMarker(i3, this.d.createMarker(i3, inflate, 2, 0.0f, 0.0f, false));
        }
    }

    private GeoPoint a(RecommendStation recommendStation, List<BusStationData> list) {
        List<RealTimeBusStation> list2;
        int size = list.size();
        String recommend_station = recommendStation.getData().getRecommend_station();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                list2 = null;
                break;
            }
            BusStationData busStationData = list.get(i2);
            if (recommend_station.equals(busStationData.poiId)) {
                list2 = busStationData.stations;
                this.y = busStationData;
                this.T = i2;
                break;
            }
            i2++;
        }
        if (list2 == null || list2.size() <= 0) {
            return null;
        }
        RealTimeBusStation realTimeBusStation = list2.get(0);
        return new GeoPoint(realTimeBusStation.station_lon.doubleValue(), realTimeBusStation.station_lat.doubleValue());
    }

    public final void a(RecommendStationData recommendStationData) {
        if (this.o != null) {
            this.o.a(recommendStationData);
        }
    }

    private boolean x() {
        return this.z && !this.N && this.K;
    }

    private void y() {
        if (!(this.v == null || this.w == null || this.w.getData() == null)) {
            String bubble_style = this.w.getData().getBubble_style();
            if (!TextUtils.isEmpty(bubble_style)) {
                this.v.b = bubble_style;
            }
        }
    }

    public final void h() {
        if (this.p != null && this.p.size() > 0) {
            eao.b("zyl", "refreshStations");
            b();
            e();
            a(this.p);
            if (this.w == null || this.w.getData() == null) {
                a((RecommendStationData) null);
                eao.b("zyl", "recommendStation == null");
            } else {
                a(this.w.getData());
                eao.b("zyl", "recommendStation != null");
                this.x = a(this.w, this.p);
                if (this.x != null) {
                    eao.b("zyl", "recomPoint != null");
                    if (x()) {
                        d(this.x);
                        this.v.a((c) new b(this, 0));
                        eao.b("zyl", "没线");
                    } else if (this.z) {
                        y();
                        w();
                        eao.b("zyl", "有线");
                    }
                } else {
                    eao.b("zyl", "recomPoint == null");
                }
            }
        }
    }

    public final void i() {
        if (!this.z) {
            this.z = true;
        }
        if (!this.K) {
            this.K = true;
        }
    }

    public final void j() {
        b();
        e();
        bmn.b();
        this.p = null;
    }

    public final void a(RecommendStation recommendStation, boolean z2) {
        if (z2) {
            b();
            List<BusStationData> list = this.p;
            eao.b("zyl", "addNearByStations sus");
            this.w = recommendStation;
            if (this.w == null || recommendStation.getData() == null) {
                a((RecommendStationData) null);
                eao.b("zyl", "recommendStation == null");
            } else {
                a(recommendStation.getData());
                eao.b("zyl", "recommendStation != null");
                this.x = a(this.w, list);
                if (this.x != null) {
                    eao.b("zyl", "recomPoint != null");
                    if (x()) {
                        d(this.x);
                        this.v.a((c) new b(this, 0));
                        eao.b("zyl", "没线");
                        return;
                    } else if (this.z) {
                        y();
                        w();
                        eao.b("zyl", "有线");
                        return;
                    }
                } else {
                    eao.b("zyl", "recomPoint == null");
                    return;
                }
            }
            return;
        }
        if (recommendStation != null) {
            this.w = recommendStation;
        }
    }

    public final void k() {
        if (this.p != null) {
            if (!dxx.a((Collection<T>) this.p)) {
                for (BusStationData next : this.p) {
                    if (next != null) {
                        List<RealTimeBusStation> list = next.stations;
                        Context context = this.j;
                        if (!dxx.a((Collection<T>) list) && context != null) {
                            for (RealTimeBusStation next2 : list) {
                                next2.isFollow = bso.a().a(next2.bus_id, next2.station_id);
                            }
                        }
                    }
                }
            }
            e();
            a(this.p);
        }
    }

    public final void b(boolean z2) {
        dxu.a((AosResponseCallback<RecommendResponse>) new BusRadarOverlayManager$14<RecommendResponse>(this, z2));
    }

    private void a(List<BusStationData> list) {
        if (this.k != null && this.k.isStarted()) {
            if (list == null || list.isEmpty()) {
                this.p = null;
                return;
            }
            bmn.b();
            this.p = null;
        }
    }

    public final void a(boolean z2, BusStationData busStationData) {
        s();
        a(this.B, z2);
        if (busStationData != null) {
            a(this.B, busStationData.station_name);
        }
    }

    public final void a(Map<Integer, BusStationData> map, boolean z2) {
        if (!dxx.a(map)) {
            if (!z2) {
                for (Integer intValue : map.keySet()) {
                    int intValue2 = intValue.intValue();
                    dxx.a(this.a, intValue2, false, false);
                    a(intValue2, (String) "");
                }
                return;
            }
            for (Entry next : map.entrySet()) {
                int intValue3 = ((Integer) next.getKey()).intValue();
                BusStationData busStationData = (BusStationData) next.getValue();
                dxx.a(this.a, intValue3, true, true);
                a(intValue3, busStationData.getBusStation() == null ? "" : busStationData.getBusStation().station_name);
            }
        }
    }

    private void a(GeoPoint geoPoint, boolean z2) {
        if (geoPoint != null) {
            z();
            MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, geoPoint, 3);
            mapViewLayoutParams.mode = 0;
            View inflate = LayoutInflater.from(this.j).inflate(R.layout.realtime_bus_marker_stopsign_layout, null);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.stationsign_icon);
            if (z2) {
                if (this.G != null) {
                    imageView.setImageBitmap(this.G);
                } else {
                    imageView.setImageResource(R.drawable.marker_realtime_follow_station);
                }
            } else if (this.F != null) {
                imageView.setImageBitmap(this.F);
            } else {
                imageView.setImageResource(R.drawable.marker_realtime_station);
            }
            this.l.a(inflate, (LayoutParams) mapViewLayoutParams);
            dwd dwd = new dwd(geoPoint);
            dwd.mDefaultMarker = this.c.createMarker(314, inflate, 5, 0.0f, 0.0f, false);
            this.l.a(inflate);
            this.c.addItem(dwd);
        }
    }

    private void a(GeoPoint geoPoint, String str) {
        if (geoPoint != null) {
            A();
            MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, geoPoint, 3);
            mapViewLayoutParams.mode = 0;
            View inflate = LayoutInflater.from(this.j).inflate(R.layout.realtime_bus_marker_focusstop_label_layout, null);
            TextView textView = (TextView) inflate.findViewById(R.id.focus_station_label);
            if (textView != null && !TextUtils.isEmpty(str)) {
                if (str.contains("()")) {
                    str = str.substring(0, str.length() - 2);
                }
                textView.setText(str);
            }
            this.l.a(inflate, (LayoutParams) mapViewLayoutParams);
            dwd dwd = new dwd(geoPoint);
            dwd.mDefaultMarker = this.O.createMarker(5678, inflate, 2, 0.0f, 0.0f, false);
            this.l.a(inflate);
            this.O.addItem(dwd);
        }
    }

    private void z() {
        if (this.c != null) {
            this.c.clear();
            this.c.clearFocus();
        }
    }

    private void A() {
        if (this.O != null) {
            this.O.clear();
            this.O.clearFocus();
        }
    }

    public final void l() {
        m();
        q();
        p();
        z();
        A();
        o();
    }

    public final void m() {
        if (this.g != null) {
            this.g.clearTip();
        }
    }

    public final void a(GeoPoint geoPoint, String str, String str2, String str3) {
        if (AMapPageUtil.isHomePage()) {
            if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
                o();
                this.g.addTipUpStationIcon(geoPoint, str, str2, str3);
            }
            c(geoPoint);
        }
    }

    public final boolean n() {
        if (this.g != null) {
            return this.g.isTipClick();
        }
        return false;
    }

    public final void c(GeoPoint geoPoint) {
        if (!(this.l == null || this.f == null || this.l.v() == 15.0f || this.p == null || this.p.isEmpty() || this.f.getSize() == 0)) {
            e(geoPoint);
        }
    }

    private void e(GeoPoint geoPoint) {
        if (!(this.f == null || this.l == null || geoPoint == null)) {
            this.l.a(geoPoint.x, geoPoint.y);
            this.l.c(15.0f);
        }
    }

    public final void o() {
        if (this.b != null) {
            this.b.clear();
        }
    }

    public final void p() {
        if (this.i != null) {
            this.i.clear();
            this.i.clearFocus();
        }
        if (this.h != null) {
            this.h.clear();
            this.h.clearFocus();
        }
    }

    public final void q() {
        if (this.f != null) {
            this.f.clear();
        }
    }

    public final synchronized void r() {
        this.R = 0;
        if (this.a != null) {
            this.a.clear();
        }
    }

    static /* synthetic */ void a(dwa dwa) {
        if (dwa.T != -1 && dwa.y != null) {
            dwa.I = false;
            dwa.J = dwa.T;
            if (!(dwa.o == null || dwa.y == null)) {
                dwa.q = true;
                dwa.a(dwa.y);
                dwa.o.a(dwa.y);
            }
            dwc.a(1);
        }
    }
}
