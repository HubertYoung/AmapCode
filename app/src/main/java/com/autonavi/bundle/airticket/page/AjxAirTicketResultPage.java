package com.autonavi.bundle.airticket.page;

import android.content.Context;
import android.support.annotation.Nullable;
import com.autonavi.bundle.airticket.module.ModuleAirTicket;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.route.ajx.module.ModuleValues;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.SDKFactory;

public class AjxAirTicketResultPage extends Ajx3Page {
    public IRouteUI a;
    public boolean b;
    public boolean c;
    private ModuleAirTicket d;

    @Nullable
    public String getAjx3Url() {
        return ModuleValues.URL_AIR_TICKET_RESULT;
    }

    public Ajx3PagePresenter createPresenter() {
        return new apo(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
    }

    public void destroy() {
        super.destroy();
    }

    public void loadJs() {
        eao.a((String) "JS:#", (String) UCCore.OPTION_LOAD_KERNEL_TYPE);
        this.mAjxView.load(ModuleValues.URL_AIR_TICKET_RESULT, apo.a(((apo) this.mPresenter).b, ((apo) this.mPresenter).c), "AIR_TICKET_RESULT", 0, 0);
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        if (isAlive() && iAjxContext != null) {
            this.d = (ModuleAirTicket) this.mAjxView.getJsModule(ModuleAirTicket.MODULE_NAME);
            if (this.d != null) {
                this.d.attachPage(this);
                this.d.registerCalcRouteStateChangeListener(new apn() {
                    public final void a(int i) {
                        if (a.a.b) {
                            AjxAirTicketResultPage.this.b = true;
                            if (i == 1) {
                                AjxAirTicketResultPage.this.c = true;
                            } else {
                                AjxAirTicketResultPage.this.c = false;
                            }
                            AjxAirTicketResultPage.this.a(AjxAirTicketResultPage.this.c);
                        }
                    }
                });
            }
        }
    }

    public final void a() {
        if (this.d != null) {
            this.d.requestAirTicketList(apo.a(((apo) this.mPresenter).b, ((apo) this.mPresenter).c));
        }
    }

    public final void a(boolean z) {
        if (a.a.b && this.b && getArguments() != null) {
            int i = getArguments().getInt("bundle_key_token", -1);
            if (z) {
                d.a.a(i, 10000, (String) null);
            } else {
                d.a.a(i, (int) SDKFactory.getCoreType, (String) null);
            }
            a.a.b = false;
        }
    }
}
