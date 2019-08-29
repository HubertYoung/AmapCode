package defpackage;

import android.content.Context;
import com.autonavi.minimap.life.order.base.model.IOrderSearchResult;
import com.autonavi.minimap.life.order.viewpoint.net.ViewPointOrderNetWorkListener;
import java.util.ArrayList;

/* renamed from: dqq reason: default package */
/* compiled from: ViewPointPresenter */
public final class dqq implements dqo {
    private dqi a;

    public dqq(Context context) {
        this.a = new dqi(context);
    }

    public final void a(final dqj dqj) {
        try {
            dqi.a(new ViewPointOrderNetWorkListener(new dpq() {
                public final void onDeleteFinished(dqe dqe) {
                }

                public final void onError() {
                }

                public final void onOrderListByPhoneNetDataFinished(dpp dpp) {
                }

                public final void onOrderListNetDataFinishedNew(dpp dpp) {
                }

                public final void onOrderListNetDataFinished(dpp dpp) {
                    if (dpp.c == 1) {
                        IOrderSearchResult c = ((dpm) dpp).c();
                        int totalOrderSize = c.getTotalOrderSize();
                        ArrayList<dpl> totalOrdersList = c.getTotalOrdersList();
                        if (totalOrderSize > 0 || (totalOrdersList != null && totalOrdersList.size() > 0)) {
                            dqj.haveHistory(true);
                        }
                    }
                }
            }), 5, 1, "");
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
    }
}
