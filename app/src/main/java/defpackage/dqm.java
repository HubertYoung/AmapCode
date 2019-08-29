package defpackage;

import com.autonavi.minimap.life.order.base.model.IOrderSearchResult;
import com.autonavi.minimap.life.order.viewpoint.model.ViewPointOrderResultData;

/* renamed from: dqm reason: default package */
/* compiled from: AosViewPointOrderListResponser */
public final class dqm extends dpm {
    public final String d() {
        return "VIEWPOINT_ORDER_SEARCH_RESULT";
    }

    public dqm(int i) {
        super(i);
    }

    public final IOrderSearchResult b() {
        return new ViewPointOrderResultData("VIEWPOINT_ORDER_SEARCH_RESULT");
    }
}
