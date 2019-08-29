package com.autonavi.bundle.routecommute.bus.details;

import android.content.Context;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.routecommute.modlue.ModuleCommuteCommon;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.views.AmapAjxView;

@PageAction("bus_commute_list_page")
public class BusCommuteListPage extends Ajx3Page {
    private static String a = "path://amap_bundle_routecommute/src/bus_commute/pages/CommuteBusListPage.page.js";
    private ModuleCommuteCommon b;
    private ModuleBusCommuteDetails c;
    private bat d = new bat() {
        public final void a() {
            azc.a(27, (azr) new azq() {
                public final AbstractBasePage a() {
                    return BusCommuteListPage.this;
                }

                public final void b() {
                    super.b();
                    azb.a("ranbin", "BusCommuteListPage----afterDialogShow-------log");
                    LogManager.actionLogV2("P00472", "B005", null);
                }
            });
        }
    };

    public Ajx3PagePresenter createPresenter() {
        return super.createPresenter();
    }

    public String getAjx3Url() {
        return a;
    }

    public void onJsBack(Object obj, String str) {
        super.onJsBack(obj, str);
    }

    public void loadJs() {
        super.loadJs();
    }

    public void onAjxViewCreated(AmapAjxView amapAjxView) {
        super.onAjxViewCreated(amapAjxView);
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        this.b = (ModuleCommuteCommon) this.mAjxView.getJsModule(ModuleCommuteCommon.MODULE_NAME);
        if (this.b != null) {
            this.b.addDialogModuleProvider("busList", this.d);
        }
        this.c = (ModuleBusCommuteDetails) this.mAjxView.getJsModule(ModuleBusCommuteDetails.MODULE_NAME);
    }

    public void destroy() {
        super.destroy();
        if (this.b != null) {
            this.b.removeDialogModuleProvider("busList");
        }
    }

    public void onCreate(Context context) {
        PageBundle arguments = getArguments();
        if (arguments != null) {
            arguments.putString("jsData", arguments.getString(ays.a));
        }
        super.onCreate(context);
    }
}
