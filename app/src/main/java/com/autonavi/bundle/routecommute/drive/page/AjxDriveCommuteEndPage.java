package com.autonavi.bundle.routecommute.drive.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import com.autonavi.bundle.routecommute.modlue.ModuleCommuteCommon;
import com.autonavi.bundle.routecommute.modlue.ModuleDriveRouteCommute;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import defpackage.bae;

public class AjxDriveCommuteEndPage<Presenter extends bae> extends Ajx3Page implements mr {
    public a a = new a(0);
    public ModuleCommuteCommon b;
    private ModuleDriveRouteCommute c;
    private bat d = new bat() {
        public final void a() {
            azc.a(28, (azr) new azq() {
                public final AbstractBasePage a() {
                    return AjxDriveCommuteEndPage.this;
                }
            });
        }
    };

    public static class a {
        public boolean a;
        public int b;
        public int c;
        public int d;
        public float e;
        public float f;
        public int g;

        private a() {
            this.a = false;
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public Ajx3PagePresenter createPresenter() {
        this.mPresenter = new bae(this);
        return (Ajx3PagePresenter) this.mPresenter;
    }

    public View onCreateView(AmapAjxView amapAjxView) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.layout_drive_commute_end, null);
        viewGroup.addView(amapAjxView, new LayoutParams(-1, -1));
        return viewGroup;
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        this.c = (ModuleDriveRouteCommute) this.mAjxView.getJsModule(ModuleDriveRouteCommute.MODULE_NAME);
        if (this.c != null) {
            this.c.setPage(this);
        }
        this.b = (ModuleCommuteCommon) this.mAjxView.getJsModule(ModuleCommuteCommon.MODULE_NAME);
        if (this.b != null) {
            this.b.addDialogModuleProvider("drive", this.d);
        }
    }

    public void onCreate(Context context) {
        bty mapView = getMapManager().getMapView();
        if (mapView != null) {
            mapView.t(false);
            this.a.a = mapView.s();
            this.a.b = mapView.o(false);
            this.a.c = mapView.ae();
            this.a.d = mapView.p(false);
            this.a.e = mapView.I();
            this.a.g = mapView.w();
            this.a.f = mapView.J();
        }
        super.onCreate(context);
    }
}
