package com.autonavi.minimap.bundle.locationselect.page;

import android.content.Context;
import android.view.View;
import com.autonavi.annotation.PageAction;
import com.autonavi.map.core.MapManager;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.ModuleAMap;

@PageAction("amap.basemap.action.base_select_fix_poi_from_map_ajx_page")
public class SelectFixPoiFromMapAjx3Page extends Ajx3Page {
    private bty a;
    private cde b;
    private MapManager c;
    private cyq d;
    private ModuleAMap e;

    public Ajx3PagePresenter createPresenter() {
        return new cyr(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        getContentView();
        this.b = getSuspendManager();
        this.b.b().disableView(256);
        this.b.b().disableView(1024);
        if (getMapManager() != null) {
            if (this.b != null) {
                this.b.d().f();
            }
            this.c = getMapManager();
            this.c.getOverlayManager().getGpsLayer().a(0);
        }
        if (this.d == null) {
            this.d = new cyq(this);
        }
        this.a = getMapView();
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        this.e = (ModuleAMap) this.mAjxView.getJsModule(ModuleAMap.MODULE_NAME);
        if (this.d == null) {
            this.d = new cyq(this);
        }
        if (this.e != null && this.d != null) {
            this.d.f = iAjxContext;
            this.e.setAMapSuspendView(this.d);
        }
    }

    public View getMapSuspendView() {
        View a2 = this.d.a();
        a2.setVisibility(4);
        return a2;
    }

    public void resume() {
        resetMapSkinState();
        super.resume();
    }

    public void destroy() {
        if (this.e != null) {
            this.e.setAMapSuspendView(null);
        }
        super.destroy();
    }

    public void resetMapSkinState() {
        if (!(this instanceof btz)) {
            int l = bim.aa().l((String) "101");
            bty mapView = getMapManager().getMapView();
            if (l != 0) {
                if (mapView != null) {
                    mapView.a(l, mapView.ae(), 0);
                }
                return;
            }
            if (mapView != null) {
                mapView.a(l, mapView.ae(), 0);
            }
        }
    }
}
