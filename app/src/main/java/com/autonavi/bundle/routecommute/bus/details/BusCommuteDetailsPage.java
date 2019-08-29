package com.autonavi.bundle.routecommute.bus.details;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.routecommute.modlue.ModuleCommuteCommon;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.suspend.refactor.scale.ScaleView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONObject;

@PageAction("bus_commute_details_page")
public class BusCommuteDetailsPage extends Ajx3Page implements bgm, bgo, com.autonavi.bundle.routecommute.bus.details.ModuleBusCommuteDetails.a {
    private ModuleBusCommuteDetails a;
    private ModuleCommuteCommon b;
    private FrameLayout c;
    private ScaleView d;
    private a e;
    private bat f = new bat() {
        public final void a() {
            azc.a(29, (azr) new azq() {
                public final AbstractBasePage a() {
                    return BusCommuteDetailsPage.this;
                }

                public final void b() {
                    super.b();
                    azb.a("ranbin", "BusCommuteListPage----BusCommuteDetailsPage-------log");
                    LogManager.actionLogV2("P00449", "B007", null);
                }
            });
        }
    };

    public static class a {
        public float a;
        public float b;
        public float c;
        public GeoPoint d = new GeoPoint();
        int e = 0;
        int f = 0;
        public int g = 0;

        protected a() {
        }
    }

    public void finishSelf() {
    }

    @Nullable
    public String getAjx3Url() {
        return "path://amap_bundle_routecommute/src/bus_commute/pages/CommuteBusDetailPage.page.js";
    }

    public bgo getPresenter() {
        return this;
    }

    public JSONObject getScenesData() {
        return null;
    }

    public long getScenesID() {
        return 9007199254740992L;
    }

    public boolean handleVUICmd(bgb bgb, bfb bfb) {
        return false;
    }

    public boolean isInnerPage() {
        return false;
    }

    public boolean needKeepSessionAlive() {
        return false;
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        this.a = (ModuleBusCommuteDetails) this.mAjxView.getJsModule(ModuleBusCommuteDetails.MODULE_NAME);
        if (this.a != null) {
            this.a.setUiListener(this);
        }
        this.b = (ModuleCommuteCommon) this.mAjxView.getJsModule(ModuleCommuteCommon.MODULE_NAME);
        if (this.b != null) {
            this.b.addDialogModuleProvider("busDetail", this.f);
        }
    }

    private void b() {
        this.e = new a();
        cde suspendManager = getSuspendManager();
        MapManager mapManager = getMapManager();
        if (suspendManager != null && mapManager != null && mapManager.getMapView() != null) {
            if (suspendManager.d() != null) {
                suspendManager.d().f();
            }
            bty mapView = mapManager.getMapView();
            bty mapView2 = getMapView();
            mapView.e(true);
            mapView.d(true);
            this.e.a = mapView.I();
            this.e.b = mapView.v();
            this.e.c = mapView.J();
            this.e.d = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
            this.e.e = mapView2.j(true);
            this.e.g = mapView.l(true);
            this.e.f = mapView2.k(true);
        }
    }

    private void c() {
        if (getMapManager() != null && getMapView() != null) {
            bty mapView = getMapManager().getMapView();
            bty mapView2 = getMapView();
            if (!(mapView == null || mapView2 == null)) {
                mapView.ab();
                mapView.B();
                mapView.z();
                mapView.N();
                mapView2.a(this.e.e, this.e.g, this.e.f);
                if (!(this.e.d == null || this.e.d.x == 0 || this.e.d.y == 0)) {
                    mapView.a(this.e.d.x, this.e.d.y);
                    brj.a(this.e.d.x, this.e.d.y);
                }
                mapView.e(this.e.a);
                mapView.d(this.e.b);
                mapView.g(this.e.c);
                mapView2.b(tt.a((Context) getActivity()) / 2, (tt.b((Context) getActivity()) - ags.d(getActivity())) / 2);
            }
        }
    }

    public void onJsBack(Object obj, String str) {
        super.onJsBack(obj, str);
    }

    public Ajx3PagePresenter createPresenter() {
        return super.createPresenter();
    }

    public void pageCreated() {
        super.pageCreated();
    }

    public View getMapSuspendView() {
        return super.getMapSuspendView();
    }

    public void onAjxViewCreated(AmapAjxView amapAjxView) {
        super.onAjxViewCreated(amapAjxView);
    }

    public View onCreateView(AmapAjxView amapAjxView) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.layout_bus_commute_detail_page, null);
        viewGroup.addView(amapAjxView, new LayoutParams(-1, -1));
        this.c = (FrameLayout) viewGroup.findViewById(R.id.scale_container);
        this.d = getSuspendWidgetHelper().f();
        if (this.d != null) {
            ViewGroup viewGroup2 = (ViewGroup) this.d.getParent();
            if (viewGroup2 != null) {
                viewGroup2.removeView(this.d);
            }
            this.d.setScaleStatus(0);
            this.d.changeLogoStatus(true);
        }
        return viewGroup;
    }

    public AmapAjxView getAjxView() {
        return super.getAjxView();
    }

    public void loadJs() {
        super.loadJs();
    }

    public void resume() {
        super.resume();
    }

    public void pause() {
        super.pause();
    }

    public void destroy() {
        super.destroy();
        c();
        if (this.b != null) {
            this.b.removeDialogModuleProvider("busDetail");
        }
    }

    public void result(int i, ResultType resultType, PageBundle pageBundle) {
        super.result(i, resultType, pageBundle);
    }

    public boolean backPressed() {
        return super.backPressed();
    }

    public String getAjxPageId() {
        return super.getAjxPageId();
    }

    public final void a() {
        bty mapView = getMapView();
        if (mapView != null) {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            if (latestPosition != null) {
                mapView.a((GLGeoPoint) latestPosition);
            }
        }
    }

    public final void a(int i, int i2) {
        if (this.c != null && this.d != null) {
            StringBuilder sb = new StringBuilder("deng---update比例尺位置");
            sb.append(i);
            sb.append("-");
            sb.append(i2);
            azb.a(null, sb.toString());
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            layoutParams.bottomMargin = i2;
            layoutParams.leftMargin = i;
            this.c.removeAllViews();
            this.c.addView(this.d, layoutParams);
        }
    }

    public void onPageAppear() {
        super.onPageAppear();
        b();
    }

    public void onPageCover() {
        c();
        super.onPageCover();
    }

    public void onCreate(Context context) {
        PageBundle arguments = getArguments();
        if (arguments != null) {
            arguments.putString("jsData", arguments.getString(ays.a));
        }
        b();
        super.onCreate(context);
    }
}
