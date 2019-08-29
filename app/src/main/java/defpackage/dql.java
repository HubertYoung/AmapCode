package defpackage;

import com.autonavi.minimap.life.order.base.model.IOrderSearchResult;
import com.autonavi.minimap.life.order.viewpoint.model.ViewPointOrderResultData;

/* renamed from: dql reason: default package */
/* compiled from: AosViewPointOrderListByPhoneResponser */
public final class dql extends dpm {
    public final String d() {
        return "VIEWPOINT_ORDER_SEARCH_BY_PHONE_RESULT";
    }

    public dql(int i) {
        super(i);
    }

    public final IOrderSearchResult b() {
        return new ViewPointOrderResultData("VIEWPOINT_ORDER_SEARCH_BY_PHONE_RESULT");
    }
}
