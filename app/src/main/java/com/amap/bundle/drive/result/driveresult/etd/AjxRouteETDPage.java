package com.amap.bundle.drive.result.driveresult.etd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.suspend.refactor.scale.ScaleView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import defpackage.os;

public class AjxRouteETDPage<Presenter extends os> extends Ajx3Page {
    private FrameLayout a;
    private boolean b;

    public Ajx3PagePresenter createPresenter() {
        this.mPresenter = new os(this);
        return (Ajx3PagePresenter) this.mPresenter;
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        MapManager mapManager = getMapManager();
        if (!(mapManager == null || mapManager.getMapView() == null)) {
            this.b = mapManager.getMapView().s();
        }
        bqx bqx = (bqx) ank.a(bqx.class);
        if (bqx != null) {
            bqx.a(false, false, getMapManager(), getContext());
        }
    }

    public View onCreateView(AmapAjxView amapAjxView) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.ajx_route_etd_page, null);
        viewGroup.addView(amapAjxView, new LayoutParams(-1, -1));
        this.a = (FrameLayout) viewGroup.findViewById(R.id.scale_container);
        if (getSuspendManager() != null) {
            ScaleView f = getSuspendWidgetHelper().f();
            if (f != null) {
                ViewGroup viewGroup2 = (ViewGroup) f.getParent();
                if (viewGroup2 != null) {
                    viewGroup2.removeView(f);
                }
                this.a.removeAllViews();
                this.a.addView(f);
                f.setScaleStatus(0);
                f.changeLogoStatus(true);
            }
            if (getSuspendManager().d() != null) {
                getSuspendManager().d().f();
            }
        }
        return viewGroup;
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
    }

    public void destroy() {
        bqx bqx = (bqx) ank.a(bqx.class);
        if (bqx != null) {
            bqx.a(this.b, false, getMapManager(), getContext());
        }
        super.destroy();
    }
}
