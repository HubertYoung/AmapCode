package defpackage;

import android.os.Handler;
import android.os.Message;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.impl.Locator.LOCATION_SCENE;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.minimap.realtimebus.RealtimeBusRequestHolder;
import com.autonavi.minimap.realtimebus.param.LinesExRequest;
import com.autonavi.minimap.route.bus.busline.newmodel.RouteRealTimeRequestModel;
import com.autonavi.minimap.route.bus.busline.page.BusLineToMapPage;
import com.autonavi.minimap.route.bus.busline.page.BusLineToMapPage.MyViewPageAdapter;
import com.autonavi.minimap.route.bus.inter.IBusLineResult;
import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusline;
import com.autonavi.minimap.route.bus.realtimebus.model.TripInfo;
import com.autonavi.minimap.route.bus.realtimebus.model.stTrip;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import com.autonavi.minimap.route.net.module.RTBusLocation;
import com.autonavi.sdk.location.LocationInstrument;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/* renamed from: duw reason: default package */
/* compiled from: BusLineToMapPresenter */
public final class duw extends eae<BusLineToMapPage> implements btz, com.autonavi.minimap.route.bus.busline.newmodel.RouteRealTimeRequestModel.a, defpackage.dum.a, defpackage.dun.a, defpackage.duq.a {
    public final duq a = new duq(((BusLineToMapPage) this.mPage).getContext());
    public final dun b = new dun();
    public Bus c;
    public int d = -1;
    private final RouteRealTimeRequestModel e = new RouteRealTimeRequestModel("5");
    private final Handler f = new a(this);
    private int g;
    private boolean h = false;

    /* renamed from: duw$a */
    /* compiled from: BusLineToMapPresenter */
    static class a extends Handler {
        private WeakReference<duw> a;

        public a(duw duw) {
            this.a = new WeakReference<>(duw);
        }

        public final void handleMessage(Message message) {
            try {
                duw duw = (duw) this.a.get();
                if (duw != null) {
                    if (((BusLineToMapPage) duw.mPage).isAlive()) {
                        if (message.what == 0) {
                            duw.a(duw, ((Boolean) message.obj).booleanValue());
                        }
                    }
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }

    public duw(BusLineToMapPage busLineToMapPage) {
        super(busLineToMapPage);
    }

    public final void onPageCreated() {
        IBusLineResult iBusLineResult;
        super.onPageCreated();
        PageBundle arguments = ((BusLineToMapPage) this.mPage).getArguments();
        if (arguments != null) {
            Bus bus = null;
            if (arguments.containsKey("BusLineToMapFragment.IBusLineResult")) {
                iBusLineResult = (IBusLineResult) arguments.getObject("BusLineToMapFragment.IBusLineResult");
                if (iBusLineResult != null) {
                    this.d = iBusLineResult.getFocusStationIndex();
                    Bus[] busLineArray = iBusLineResult.getBusLineArray(iBusLineResult.getCurPoiPage());
                    int focusBusLineIndex = iBusLineResult.getFocusBusLineIndex();
                    if (!(busLineArray == null || busLineArray[focusBusLineIndex] == null)) {
                        bus = busLineArray[focusBusLineIndex].copyObject();
                    }
                }
            } else {
                iBusLineResult = null;
            }
            if (arguments.containsKey("BusLineToMapFragment.CURBUS")) {
                bus = (Bus) arguments.getObject("BusLineToMapFragment.CURBUS");
            }
            this.b.a(iBusLineResult, bus);
            if (arguments.containsKey("BusLineToMapFragment.WATCH_MODE")) {
                this.g = arguments.getInt("BusLineToMapFragment.WATCH_MODE");
            }
            if (arguments.containsKey("BusLineToMapFragment.RealTimeBuslines")) {
                RouteRealTimeRequestModel routeRealTimeRequestModel = this.e;
                HashMap hashMap = (HashMap) arguments.getObject("BusLineToMapFragment.RealTimeBuslines");
                routeRealTimeRequestModel.b.b = 0;
                if (hashMap != null && hashMap.size() > 0) {
                    routeRealTimeRequestModel.d.clear();
                    Iterator it = hashMap.entrySet().iterator();
                    if (it.hasNext()) {
                        Entry entry = (Entry) it.next();
                        RealTimeBusline realTimeBusline = new RealTimeBusline();
                        realTimeBusline.lindID = ((RTBusLocation) entry.getValue()).getLine();
                        realTimeBusline.stationID = ((RTBusLocation) entry.getValue()).getStation();
                        routeRealTimeRequestModel.d.put(entry.getKey(), realTimeBusline);
                    }
                }
            }
        }
        this.c = this.b.b;
        Bus bus2 = this.c;
        int i = this.d;
        this.b.a();
        ((BusLineToMapPage) this.mPage).a(bus2, i, this.c.isRealTime);
        a(300, false);
    }

    public final void onStart() {
        super.onStart();
        ebo.a((AbstractBaseMapPage) this.mPage);
        LocationInstrument.getInstance().subscribe(((BusLineToMapPage) this.mPage).getContext(), LOCATION_SCENE.TYPE_BUS_ROUTE);
        BusLineToMapPage busLineToMapPage = (BusLineToMapPage) this.mPage;
        Bus bus = this.c;
        int i = this.d;
        boolean z = true;
        boolean z2 = false;
        if (this.g != 1) {
            z = false;
        }
        RealTimeBusline c2 = c();
        if (busLineToMapPage.getMapView() != null) {
            busLineToMapPage.c = busLineToMapPage.getMapView().k(false);
            busLineToMapPage.getMapView().g(false);
            ebf.a(busLineToMapPage.getMapView(), busLineToMapPage.getMapView().j(false), busLineToMapPage.getMapView().l(false), 2);
        }
        busLineToMapPage.a(bus, i, z, c2);
        if (busLineToMapPage.getSuspendWidgetHelper() != null) {
            bqx bqx = (bqx) ank.a(bqx.class);
            if (bqx != null) {
                z2 = bqx.a();
            }
            busLineToMapPage.getSuspendWidgetHelper().d(z2);
        }
    }

    public final void onStop() {
        super.onStop();
        LocationInstrument.getInstance().unsubscribe(((BusLineToMapPage) this.mPage).getContext());
        BusLineToMapPage busLineToMapPage = (BusLineToMapPage) this.mPage;
        if (busLineToMapPage.getMapView() != null) {
            busLineToMapPage.getMapView().g(true);
            ebf.a(busLineToMapPage.getMapView(), busLineToMapPage.getMapView().j(false), busLineToMapPage.getMapView().l(false), busLineToMapPage.c);
        }
        busLineToMapPage.b.a();
    }

    public final void onDestroy() {
        super.onDestroy();
        this.d = -1;
        this.f.removeMessages(0);
        BusLineToMapPage busLineToMapPage = (BusLineToMapPage) this.mPage;
        busLineToMapPage.b.h = null;
        busLineToMapPage.b.a();
        bqx bqx = (bqx) ank.a(bqx.class);
        if (!(bqx == null || bqx.a() == busLineToMapPage.getMapView().s())) {
            bqx.a(bqx.a(), false, busLineToMapPage.getMapManager(), busLineToMapPage.getContext());
        }
        if (!(busLineToMapPage.d == -1 || busLineToMapPage.getMapView() == null)) {
            busLineToMapPage.getMapView().f((float) busLineToMapPage.d);
        }
        this.a.a = null;
        this.e.e = null;
        RouteRealTimeRequestModel routeRealTimeRequestModel = this.e;
        if (routeRealTimeRequestModel.c != null) {
            routeRealTimeRequestModel.c.cancel();
            routeRealTimeRequestModel.c = null;
        }
        this.b.e = null;
        PageBundle pageBundle = new PageBundle();
        pageBundle.putBoolean("result_need_exchange", this.b.d);
        ((BusLineToMapPage) this.mPage).setResult(ResultType.OK, pageBundle);
    }

    public final void b() {
        BusLineToMapPage busLineToMapPage = (BusLineToMapPage) this.mPage;
        Bus bus = this.c;
        RealTimeBusline c2 = c();
        int b2 = this.e.b.b;
        busLineToMapPage.b.a(bus, c2);
        if (busLineToMapPage.a != null) {
            MyViewPageAdapter myViewPageAdapter = busLineToMapPage.a;
            myViewPageAdapter.a = b2;
            myViewPageAdapter.notifyDataSetChanged();
        }
        a(30000, false);
        if (this.h) {
            this.h = false;
        }
    }

    public final void a() {
        this.c = this.b.b;
        Bus bus = this.c;
        int i = this.d;
        this.b.a();
        ((BusLineToMapPage) this.mPage).a(bus, i, this.c.isRealTime);
        BusLineToMapPage busLineToMapPage = (BusLineToMapPage) this.mPage;
        Bus bus2 = this.c;
        int i2 = this.d;
        boolean z = true;
        if (this.g != 1) {
            z = false;
        }
        busLineToMapPage.a(bus2, i2, z, c());
        a(300, false);
    }

    public final void a(IRouteResultData iRouteResultData, RouteType routeType) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("key_type", routeType.getValue());
        pageBundle.putObject("key_result", iRouteResultData);
    }

    public final void a(int i) {
        if (i == 0) {
            ((BusLineToMapPage) this.mPage).a(i);
        } else {
            ((BusLineToMapPage) this.mPage).a(this.c.stations.length - 1);
        }
    }

    public final void b(int i) {
        ((BusLineToMapPage) this.mPage).a(i);
    }

    public final void c(int i) {
        if (i == 0) {
            this.g = 1;
        } else {
            this.g = 0;
        }
        this.d = i - 1;
        BusLineToMapPage busLineToMapPage = (BusLineToMapPage) this.mPage;
        Bus bus = this.c;
        int i2 = this.d;
        RealTimeBusline c2 = c();
        if (i2 >= 0) {
            busLineToMapPage.b();
            busLineToMapPage.a();
        }
        busLineToMapPage.b.a(bus, i2);
        dum dum = busLineToMapPage.b;
        dum.d.clearFocus();
        dum.e.clearFocus();
        if (i2 < 0) {
            dum.i.a((Runnable) new Runnable() {
                public final void run() {
                    dum.this.f.b();
                }
            }, 300);
        }
        busLineToMapPage.b.a(bus, c2);
        busLineToMapPage.getSuspendWidgetHelper().f().changeLogoStatus(true);
    }

    public final TripInfo d(int i) {
        int i2 = i - 1;
        if (this.c != null && this.c.stationIds != null && i2 >= 0 && i2 < this.c.stationIds.length) {
            String str = this.c.stationIds[i2];
            RealTimeBusline a2 = this.e.a(this.c.id);
            if (!(a2 == null || a2.stationMap == null)) {
                stTrip sttrip = a2.stationMap.get(str);
                if (!(sttrip == null || sttrip.tripinfomap == null)) {
                    return sttrip.tripinfomap.get(Integer.valueOf(sttrip.tripinfomap.size() - 1));
                }
            }
        }
        return null;
    }

    private RealTimeBusline c() {
        if (this.e.a()) {
            return this.e.a(this.c.id);
        }
        return null;
    }

    public final void a(int i, boolean z) {
        this.f.removeMessages(0);
        this.f.sendMessageDelayed(this.f.obtainMessage(0, Boolean.valueOf(z)), (long) i);
    }

    static /* synthetic */ void a(duw duw, boolean z) {
        if (duw.c.isRealTime) {
            RouteRealTimeRequestModel routeRealTimeRequestModel = duw.e;
            Bus bus = duw.c;
            if (bus != null) {
                routeRealTimeRequestModel.b.d = z;
                String str = z ? "1" : "0";
                LinesExRequest linesExRequest = new LinesExRequest();
                linesExRequest.d = bus.areacode;
                linesExRequest.c = "";
                linesExRequest.b = bus.id;
                linesExRequest.e = routeRealTimeRequestModel.a;
                linesExRequest.f = str;
                RealtimeBusRequestHolder.getInstance().sendLinesEx(linesExRequest, routeRealTimeRequestModel.b);
            }
        }
    }
}
