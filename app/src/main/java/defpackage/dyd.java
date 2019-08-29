package defpackage;

import android.view.LayoutInflater;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.gmap.gloverlay.GLPointOverlay;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.fragmentcontainer.page.dialog.TipContainer;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay.OnItemClickListener;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.route.bus.realtimebus.data.HeartBeatManager;
import com.autonavi.minimap.route.bus.realtimebus.data.POISearchManager;
import com.autonavi.minimap.route.bus.realtimebus.model.BusStationData;
import com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.Collection;

/* renamed from: dyd reason: default package */
/* compiled from: IRTBusCtrlImpl */
public final class dyd implements awu {
    private RouteRealtimeListenerImpl a = new RouteRealtimeListenerImpl();

    /* renamed from: dyd$a */
    /* compiled from: IRTBusCtrlImpl */
    static class a {
        static dyd a = new dyd();
    }

    public final void a(bid bid, boolean z) {
        if (bid instanceof AbstractBaseMapPage) {
            RouteRealtimeListenerImpl routeRealtimeListenerImpl = this.a;
            AbstractBaseMapPage abstractBaseMapPage = (AbstractBaseMapPage) bid;
            if (!routeRealtimeListenerImpl.h) {
                IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
                if (iMainMapService != null) {
                    iMainMapService.a(czm.class);
                }
                routeRealtimeListenerImpl.h = true;
            }
            bty mapView = abstractBaseMapPage.getMapManager().getMapView();
            if (mapView != null) {
                routeRealtimeListenerImpl.a = abstractBaseMapPage;
                routeRealtimeListenerImpl.g = true;
                routeRealtimeListenerImpl.a = abstractBaseMapPage;
                routeRealtimeListenerImpl.b = abstractBaseMapPage.getContext();
                if (routeRealtimeListenerImpl.l == null) {
                    routeRealtimeListenerImpl.l = new POISearchManager(routeRealtimeListenerImpl.s);
                    routeRealtimeListenerImpl.l.a();
                }
                if (routeRealtimeListenerImpl.d == null) {
                    routeRealtimeListenerImpl.d = new dxs(abstractBaseMapPage, routeRealtimeListenerImpl);
                }
                if (RouteRealtimeListenerImpl.j() && bso.a().b == null) {
                    bso.a().b = routeRealtimeListenerImpl.u;
                }
                if (z) {
                    if (routeRealtimeListenerImpl.b != null) {
                        ToastHelper.showToast(routeRealtimeListenerImpl.b.getString(R.string.toast_realtime_open));
                    }
                    routeRealtimeListenerImpl.a(routeRealtimeListenerImpl.t, true);
                }
                if (routeRealtimeListenerImpl.c == null) {
                    routeRealtimeListenerImpl.c = new dwa(abstractBaseMapPage);
                    eao.a((String) "BusRadar", (String) "Daniel# Route init bus radar overlay");
                } else {
                    routeRealtimeListenerImpl.c.c();
                }
                if (routeRealtimeListenerImpl.c != null && !routeRealtimeListenerImpl.c.u) {
                    dwa dwa = routeRealtimeListenerImpl.c;
                    dwa.u = true;
                    dwa.m = (LayoutInflater) dwa.j.getSystemService("layout_inflater");
                    if (dwa.b == null || dwa.a == null) {
                        eao.a((String) "BusRadar", (String) "Daniel# Route add Overlay");
                        dwa.a(abstractBaseMapPage);
                    }
                    dwa.f.setVisible(true);
                    dwa.a.setMinDisplayLevel(14);
                    dwa.a.setMoveToFocus(false);
                    dwa.a.setOnItemClickListener(new OnItemClickListener() {
                        public final void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
                            if (dwa.this.p != null && !dwa.this.p.isEmpty()) {
                                dwa.this.I = false;
                                PointOverlayItem pointOverlayItem = (PointOverlayItem) obj;
                                int itemIndex = dwa.this.a.getItemIndex(pointOverlayItem);
                                if (itemIndex < dwa.this.p.size()) {
                                    BusStationData busStationData = dwa.this.p.get(itemIndex);
                                    dwa dwa = dwa.this;
                                    if (!(dwa.k == null || dwa.k.getMapManager() == null || dwa.k.getMapManager().getOverlayManager() == null)) {
                                        dwa.k.getMapManager().getOverlayManager().clearAllFocus();
                                    }
                                    dwa.this.a(false);
                                    bty.a((GLGeoPoint) pointOverlayItem.getGeoPoint());
                                    dwa.this.J = itemIndex;
                                    if (!(dwa.this.o == null || busStationData == null)) {
                                        dwa.this.q = true;
                                        dwa.this.f();
                                        dwa.this.g();
                                        if (dwa.this.y == null || !dwa.this.y.poiId.equals(busStationData.poiId)) {
                                            dwc.a(2);
                                        } else {
                                            dwc.a(1);
                                        }
                                        dwa.this.a(busStationData);
                                        if (!dwa.this.I) {
                                            dwa.this.o.a(busStationData);
                                        }
                                    }
                                }
                            }
                        }
                    });
                    cdz cdz = null;
                    if (abstractBaseMapPage.getSuspendManager() != null) {
                        cdz = abstractBaseMapPage.getSuspendManager().d();
                    }
                    dwa.t = new ean(abstractBaseMapPage.getMapManager().getMapView(), dwa.f, cdz);
                    dwa.t.a(100, 150, 100, MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_);
                    if (dwa.e != null) {
                        dwa.e.setMinDisplayLevel(14);
                        ((GLPointOverlay) dwa.e.getGLOverlay()).setOverlayPriority(900);
                        dwa.e.setOnItemClickListener(new OnItemClickListener() {
                            public final void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
                                dwa.a(dwa.this);
                                dwa.this.g();
                            }
                        });
                    }
                    if (dwa.c != null) {
                        dwa.c.setOnItemClickListener(new OnItemClickListener() {
                            public final void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
                                dwc.a(3);
                            }
                        });
                    }
                    if (dwa.b != null) {
                        dwa.b.setOnItemClickListener(new OnItemClickListener() {
                            public final void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
                                LogManager.actionLogV2("P00367", "B009");
                            }
                        });
                    }
                } else if (z && routeRealtimeListenerImpl.c != null && RouteRealtimeListenerImpl.j() && AMapPageUtil.isHomePage() && !routeRealtimeListenerImpl.e) {
                    routeRealtimeListenerImpl.c.i();
                    routeRealtimeListenerImpl.c.h();
                }
                if (z) {
                    routeRealtimeListenerImpl.c.q = false;
                }
                routeRealtimeListenerImpl.c.r = true;
                routeRealtimeListenerImpl.c.s = true;
                routeRealtimeListenerImpl.c.o = routeRealtimeListenerImpl;
                if (routeRealtimeListenerImpl.c.q) {
                    routeRealtimeListenerImpl.c.a(false);
                } else {
                    routeRealtimeListenerImpl.c.a(true);
                }
                GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                if (z) {
                    if (mapView.w() < 16) {
                        mapView.e(16);
                    }
                    mapView.a(latestPosition.x, latestPosition.y);
                }
                if (!aaw.c(routeRealtimeListenerImpl.b)) {
                    if (z) {
                        routeRealtimeListenerImpl.h();
                    }
                    return;
                }
                routeRealtimeListenerImpl.k = false;
                if (routeRealtimeListenerImpl.l != null && routeRealtimeListenerImpl.k) {
                    routeRealtimeListenerImpl.l.a();
                }
            }
        }
    }

    public final void a(bid bid, Object obj, boolean z) {
        if (bid instanceof AbstractBaseMapPage) {
            RouteRealtimeListenerImpl routeRealtimeListenerImpl = this.a;
            AbstractBaseMapPage abstractBaseMapPage = (AbstractBaseMapPage) bid;
            TipContainer tipContainer = (TipContainer) obj;
            routeRealtimeListenerImpl.g = false;
            routeRealtimeListenerImpl.j = 0;
            if (routeRealtimeListenerImpl.c != null) {
                routeRealtimeListenerImpl.c.r();
                dwa dwa = routeRealtimeListenerImpl.c;
                if (dwa.l != null) {
                    dwa.l.V();
                    dwa.n.clear();
                }
                routeRealtimeListenerImpl.c.o = null;
                routeRealtimeListenerImpl.c.b();
                routeRealtimeListenerImpl.a();
            }
            if (abstractBaseMapPage != null && z) {
                if (routeRealtimeListenerImpl.c != null) {
                    routeRealtimeListenerImpl.c.l();
                    routeRealtimeListenerImpl.c.j();
                }
                dxs.a();
                if (routeRealtimeListenerImpl.d != null) {
                    routeRealtimeListenerImpl.d.a(R.string.toast_realtime_close);
                }
                if (!(routeRealtimeListenerImpl.d == null || tipContainer == null)) {
                    tipContainer.dimissTips();
                    if (abstractBaseMapPage instanceof MapBasePage) {
                        MapBasePage mapBasePage = (MapBasePage) abstractBaseMapPage;
                        if (mapBasePage.getCQLayerController() != null) {
                            POI curPoi = mapBasePage.getCQLayerController().getCurPoi();
                            if ((curPoi == null || curPoi.getPoiExtra() == null || !curPoi.getPoiExtra().containsKey("isFromBusRadar")) ? false : true) {
                                mapBasePage.getCQLayerController().dismissCQLayer(true);
                            }
                        }
                    }
                }
                routeRealtimeListenerImpl.c();
            }
            if (routeRealtimeListenerImpl.l != null) {
                routeRealtimeListenerImpl.l.b();
                routeRealtimeListenerImpl.l.d();
            }
            if (routeRealtimeListenerImpl.a != null) {
                if (!RouteRealtimeListenerImpl.j()) {
                    bso.a().b = null;
                }
                if (z) {
                    routeRealtimeListenerImpl.a(routeRealtimeListenerImpl.t, false);
                }
            }
            routeRealtimeListenerImpl.a = null;
        }
    }

    public final void a() {
        RouteRealtimeListenerImpl routeRealtimeListenerImpl = this.a;
        if (routeRealtimeListenerImpl.c != null) {
            routeRealtimeListenerImpl.c.f();
        }
        routeRealtimeListenerImpl.g();
    }

    public final void b() {
        RouteRealtimeListenerImpl routeRealtimeListenerImpl = this.a;
        if (routeRealtimeListenerImpl.c != null) {
            if (!routeRealtimeListenerImpl.c.n()) {
                bmn.a(false);
                routeRealtimeListenerImpl.c.I = true;
                routeRealtimeListenerImpl.c();
            } else {
                return;
            }
        }
        routeRealtimeListenerImpl.j = 5;
    }

    public final void c() {
        RouteRealtimeListenerImpl routeRealtimeListenerImpl = this.a;
        if (routeRealtimeListenerImpl.c != null) {
            dwa dwa = routeRealtimeListenerImpl.c;
            if (dwa.b != null) {
                dwa.b.clearFocus();
            }
        }
    }

    public final void a(bid bid) {
        if (bid instanceof AbstractBaseMapPage) {
            RouteRealtimeListenerImpl routeRealtimeListenerImpl = this.a;
            AbstractBaseMapPage abstractBaseMapPage = (AbstractBaseMapPage) bid;
            if (abstractBaseMapPage != null) {
                if (routeRealtimeListenerImpl.c == null) {
                    routeRealtimeListenerImpl.c = new dwa(abstractBaseMapPage);
                    eao.a((String) "BusRadar", (String) "Daniel# External init bus radar overlay");
                }
                routeRealtimeListenerImpl.c.a(abstractBaseMapPage);
            }
        }
    }

    public final void b(bid bid) {
        if (bid instanceof AbstractBaseMapPage) {
            RouteRealtimeListenerImpl routeRealtimeListenerImpl = this.a;
            AbstractBaseMapPage abstractBaseMapPage = (AbstractBaseMapPage) bid;
            if (!(abstractBaseMapPage == null || routeRealtimeListenerImpl.c == null)) {
                dwa dwa = routeRealtimeListenerImpl.c;
                MapManager mapManager = abstractBaseMapPage.getMapManager();
                if (mapManager != null) {
                    bty mapView = mapManager.getMapView();
                    if (mapView != null) {
                        btm F = mapView.F();
                        if (dwa.f != null) {
                            F.c(dwa.f);
                        }
                        if (dwa.a != null) {
                            F.c(dwa.a);
                        }
                        if (dwa.b != null) {
                            F.c(dwa.b);
                        }
                        if (dwa.g != null) {
                            F.c(dwa.g);
                        }
                        if (dwa.e != null) {
                            F.c(dwa.e);
                        }
                        if (dwa.d != null) {
                            F.c(dwa.d);
                        }
                    }
                }
            }
        }
    }

    public final void d() {
        boolean z;
        RouteRealtimeListenerImpl routeRealtimeListenerImpl = this.a;
        routeRealtimeListenerImpl.a();
        if (routeRealtimeListenerImpl.c != null && routeRealtimeListenerImpl.c.K && routeRealtimeListenerImpl.c.z) {
            dwa dwa = routeRealtimeListenerImpl.c;
            if (dwa.v == null || !dwa.f() || "0".equals(dwa.v.b) || dwa.x == null) {
                z = false;
            } else {
                dwa.a(dwa.x);
                z = true;
            }
            if (z) {
                routeRealtimeListenerImpl.c.K = false;
            }
        }
        if (routeRealtimeListenerImpl.c != null) {
            dwa dwa2 = routeRealtimeListenerImpl.c;
            if (dwa2.v != null && "0".equals(dwa2.v.b)) {
                dwa2.f();
            }
        }
    }

    public final void e() {
        RouteRealtimeListenerImpl routeRealtimeListenerImpl = this.a;
        if (routeRealtimeListenerImpl.d != null) {
            dxs dxs = routeRealtimeListenerImpl.d;
            if (dxs.b != null) {
                HeartBeatManager.a().a(dxs.b);
                dxs.b = null;
            }
            dxs.b = dxs.a(dxs.c);
        }
        if (!RouteRealtimeListenerImpl.j()) {
            if (routeRealtimeListenerImpl.c != null) {
                routeRealtimeListenerImpl.c.N = false;
            }
            return;
        }
        int i = routeRealtimeListenerImpl.n == null ? 0 : routeRealtimeListenerImpl.n.mLineIndex;
        boolean z = true;
        if (!(!(routeRealtimeListenerImpl.o == null || routeRealtimeListenerImpl.n == null) || routeRealtimeListenerImpl.q) || routeRealtimeListenerImpl.c == null) {
            z = false;
        } else if (!dxx.a((Collection<T>) routeRealtimeListenerImpl.c.p)) {
            routeRealtimeListenerImpl.a(routeRealtimeListenerImpl.i, true, i);
        } else if (routeRealtimeListenerImpl.l != null) {
            routeRealtimeListenerImpl.l.a();
        }
        if (routeRealtimeListenerImpl.c != null) {
            routeRealtimeListenerImpl.c.N = z;
            if (AMapPageUtil.isHomePage() && !routeRealtimeListenerImpl.e) {
                routeRealtimeListenerImpl.c.h();
            }
        }
        if (routeRealtimeListenerImpl.l != null) {
            routeRealtimeListenerImpl.l.c();
        }
    }

    public final void f() {
        RouteRealtimeListenerImpl routeRealtimeListenerImpl = this.a;
        if (routeRealtimeListenerImpl.d != null) {
            dxs dxs = routeRealtimeListenerImpl.d;
            if (dxs.b != null) {
                HeartBeatManager.a().a(dxs.b);
                dxs.b = null;
            }
        }
        routeRealtimeListenerImpl.a(false);
        if (routeRealtimeListenerImpl.c != null) {
            routeRealtimeListenerImpl.c.l();
            routeRealtimeListenerImpl.c.e();
            routeRealtimeListenerImpl.c.N = false;
        }
        if (routeRealtimeListenerImpl.l != null) {
            routeRealtimeListenerImpl.l.b();
        }
    }

    public final void g() {
        RouteRealtimeListenerImpl routeRealtimeListenerImpl = this.a;
        if (routeRealtimeListenerImpl.c == null || !routeRealtimeListenerImpl.c.n()) {
            routeRealtimeListenerImpl.g();
            routeRealtimeListenerImpl.d();
        }
    }

    public final void h() {
        RouteRealtimeListenerImpl routeRealtimeListenerImpl = this.a;
        if (routeRealtimeListenerImpl.c != null) {
            dwa dwa = routeRealtimeListenerImpl.c;
            if (dwa.f != null && dwa.f.getSize() > 0) {
                routeRealtimeListenerImpl.e();
                routeRealtimeListenerImpl.e = false;
                routeRealtimeListenerImpl.c();
                routeRealtimeListenerImpl.q = false;
            }
        }
        if (routeRealtimeListenerImpl.e) {
            routeRealtimeListenerImpl.e();
        }
        routeRealtimeListenerImpl.e = false;
        routeRealtimeListenerImpl.c();
        routeRealtimeListenerImpl.q = false;
    }

    public final void i() {
        this.a.f();
    }

    public final boolean j() {
        RouteRealtimeListenerImpl routeRealtimeListenerImpl = this.a;
        if (routeRealtimeListenerImpl.c != null) {
            return routeRealtimeListenerImpl.c.n();
        }
        return false;
    }

    public final void k() {
        RouteRealtimeListenerImpl routeRealtimeListenerImpl = this.a;
        if (routeRealtimeListenerImpl.c != null) {
            dwa dwa = routeRealtimeListenerImpl.c;
            if (dwa.g != null) {
                dwa.g.resetTipClickState();
            }
        }
    }

    public final void l() {
        RouteRealtimeListenerImpl routeRealtimeListenerImpl = this.a;
        routeRealtimeListenerImpl.a(routeRealtimeListenerImpl.t, false);
        routeRealtimeListenerImpl.f = false;
        routeRealtimeListenerImpl.l = null;
        routeRealtimeListenerImpl.e = false;
        routeRealtimeListenerImpl.c = null;
        routeRealtimeListenerImpl.d = null;
        routeRealtimeListenerImpl.h = false;
    }

    public final void m() {
        this.a.d();
    }
}
