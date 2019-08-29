package com.autonavi.minimap.life.order.viewpoint.page;

import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.life.order.base.model.IOrderSearchResult;
import com.autonavi.minimap.life.order.base.model.OrderRequest;
import com.autonavi.minimap.life.order.base.page.BaseByPhonePage;

public class ViewPointListByPhonePage extends BaseByPhonePage {
    private dqi A;
    private OrderRequest y;
    private IOrderSearchResult z;

    public void onDeleteFinished(dqe dqe) {
    }

    public void onOrderListNetDataFinished(dpp dpp) {
    }

    public void onOrderListNetDataFinishedNew(dpp dpp) {
    }

    public final void b() {
        this.x = this.A.a(this.y);
    }

    public final void c() {
        this.x = this.A.a(this.g + 1);
    }

    public final void d() {
        this.A = new dqi(getContext(), this);
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.z = (IOrderSearchResult) arguments.getObject("resultData");
            this.y = (OrderRequest) arguments.getObject("phoneData");
        }
        if (this.z != null) {
            this.h = this.z.getTotalOrderSize();
            this.g = this.z.getPage();
            if (this.g == 1) {
                this.c = this.z.getTotalOrdersList();
            } else {
                this.c.addAll(this.z.getTotalOrdersList());
            }
            a(true);
        }
    }

    public final void f() {
        this.x = this.A.a(this.y);
    }

    public void onOrderListByPhoneNetDataFinished(dpp dpp) {
        new dpo();
        if (!dpo.a(dpp)) {
            IOrderSearchResult c = ((dpm) dpp).c();
            this.h = c.getTotalOrderSize();
            this.g = c.getPage();
            if (this.g == 1) {
                this.c = c.getTotalOrdersList();
            } else {
                this.c.addAll(c.getTotalOrdersList());
            }
            a(true);
            return;
        }
        dpo.a(dpp.c, dpp.a());
        a(false);
    }

    public final void a(int i) {
        dpl dpl = (dpl) this.c.get(i);
        dpl.a(this.y.phone, this.y.code);
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("IOrderListEntityKey", dpl);
        pageBundle.putString("INTENT_KEY_H5_TEMPLATE_PATH", "life/viewpoint/exViewpointDetail.html");
        startPage(ViewPointOrderDetailPage.class, pageBundle);
    }

    public final void e() {
        a();
    }

    public void onError() {
        ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.ic_net_error_tipinfo));
        a(false);
    }
}
