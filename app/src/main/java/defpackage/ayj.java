package defpackage;

import android.annotation.SuppressLint;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.bundle.routecommute.bus.bean.BusCommuteTipBean;
import com.autonavi.bundle.routecommute.bus.overlay.BusCommuteTipOverlay;
import com.autonavi.bundle.routecommute.bus.overlay.BusCommuteTipSimOverlay;
import com.autonavi.bundle.routecommute.bus.overlay.RouteCommuteBusOverlay;
import com.autonavi.bundle.routecommute.bus.overlay.RouteCommuteGuideTipOverlay;
import com.autonavi.bundle.routecommute.bus.overlay.RouteCommuteGuideTipOverlay.a;
import com.autonavi.bundle.routecommute.bus.overlay.RouteCommuteStationDescOverlay;
import com.autonavi.bundle.routecommute.bus.overlay.RouteCommuteStationOverlay;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRealTimeResponse;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRealTimeResponse.RealTimeBusLineInfo;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRouteResponse;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.minimap.base.overlay.PointOverlay.OnItemClickListener;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import java.util.ArrayList;

/* renamed from: ayj reason: default package */
/* compiled from: RouteCommuteOverlayManager */
public final class ayj implements OnItemClickListener {
    public final String a = getClass().getSimpleName();
    public BusCommuteTipOverlay b;
    public BusCommuteTipSimOverlay c;
    public RouteCommuteStationDescOverlay d;
    public RouteCommuteGuideTipOverlay e;
    public BusCommuteTipBean f;
    public boolean g = false;
    public int h = 0;
    public a i;
    public ayk j;
    private AbstractBaseMapPage k;
    private bty l;
    private RouteCommuteBusOverlay m;
    private RouteCommuteStationOverlay n;

    public ayj(AbstractBaseMapPage abstractBaseMapPage) {
        if (abstractBaseMapPage != null) {
            this.k = abstractBaseMapPage;
            this.l = this.k.getMapView();
            if (this.k != null && this.l != null) {
                this.m = new RouteCommuteBusOverlay(this.l);
                this.m.setMinDisplayLevel(14);
                this.m.setOnItemClickListener(this);
                this.k.addOverlay(this.m);
                this.d = new RouteCommuteStationDescOverlay(this.l);
                this.d.setMinDisplayLevel(14);
                this.d.setAutoSetFocus(false);
                this.d.setOnItemClickListener(this);
                this.k.addOverlay(this.d);
                this.n = new RouteCommuteStationOverlay(this.l);
                this.n.setMinDisplayLevel(14);
                this.n.setAutoSetFocus(false);
                this.n.setOnItemClickListener(this);
                this.k.addOverlay(this.n);
                this.b = new BusCommuteTipOverlay(this.l);
                this.b.setOverlayOnTop(true);
                this.k.addOverlay(this.b);
                this.c = new BusCommuteTipSimOverlay(this.l);
                this.c.setOverlayOnTop(true);
                this.c.setOnItemClickListener(this);
                this.k.addOverlay(this.c);
                this.b.setMoveToFocus(false);
                this.c.setMoveToFocus(false);
                this.m.setMoveToFocus(false);
                this.e = new RouteCommuteGuideTipOverlay(this.l);
                this.e.setOverlayOnTop(true);
                this.e.setMoveToFocus(false);
                this.k.addOverlay(this.e);
            }
        }
    }

    public final void a(BusRealTimeResponse busRealTimeResponse) {
        if (busRealTimeResponse != null && busRealTimeResponse.buses != null) {
            a(busRealTimeResponse.buses);
        }
    }

    public final void a(BusRouteResponse busRouteResponse) {
        if (busRouteResponse != null) {
            ArrayList<BusPath> arrayList = busRouteResponse.buslist;
            if (arrayList != null && !arrayList.isEmpty()) {
                if (this.n != null) {
                    this.n.addStationOverlay(arrayList);
                }
                if (this.d != null) {
                    this.d.addBusStationDescOverlay(arrayList, null);
                }
            }
        }
    }

    private void a(ArrayList<RealTimeBusLineInfo> arrayList) {
        if (this.m != null) {
            this.m.drawRealTimeBus(arrayList);
        }
    }

    public final void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
        if (baseMapOverlay == this.m) {
            LogManager.actionLogV2(ayp.a, ayp.d);
        } else if (baseMapOverlay == this.c) {
            a(3);
            ayp.a(ayp.a, ayp.g, this.f.isSettingUser, this.f.isGoHome);
        } else {
            if (baseMapOverlay == this.n || baseMapOverlay == this.d) {
                int intValue = ((Integer) ((PointOverlayItem) obj).Tag).intValue();
                ayp.a(ayp.a, ayp.e, intValue);
                if (this.n != null) {
                    this.n.setFocus(intValue, false);
                }
                if (this.d != null) {
                    this.d.setFocus(intValue, false);
                }
            }
        }
    }

    private void c() {
        if (this.b != null) {
            this.b.clear();
        }
        if (this.c != null) {
            this.c.clear();
        }
        this.g = false;
    }

    public final void a() {
        c();
        if (this.n != null) {
            this.n.clear();
        }
        if (this.d != null) {
            this.d.clear();
        }
        if (this.m != null) {
            this.m.clear();
        }
        if (this.e != null) {
            this.e.clear();
        }
    }

    @SuppressLint({"SwitchIntDef"})
    public final void a(int i2, BusCommuteTipBean busCommuteTipBean) {
        String str = this.a;
        StringBuilder sb = new StringBuilder("showTipOverlay:tipType = ");
        sb.append(i2);
        sb.append(",tipOverlayType = ");
        sb.append(this.h);
        azb.a(str, sb.toString());
        this.f = busCommuteTipBean;
        if (this.h == 0) {
            this.h = i2;
        }
        switch (this.h) {
            case 3:
                this.b.show(busCommuteTipBean, true);
                break;
            case 4:
                this.c.show(busCommuteTipBean, true);
                break;
        }
        this.g = true;
    }

    public final void a(int i2) {
        String str = this.a;
        StringBuilder sb = new StringBuilder("switchTipType:newType = ");
        sb.append(i2);
        sb.append(",tipOverlayType = ");
        sb.append(this.h);
        azb.a(str, sb.toString());
        this.h = i2;
        switch (this.h) {
            case 3:
                this.c.hide();
                this.b.show(this.f, true);
                return;
            case 4:
                this.b.hide();
                this.c.show(this.f, true);
                break;
        }
    }

    public final void b() {
        if (this.e != null) {
            this.e.clear();
        }
    }
}
