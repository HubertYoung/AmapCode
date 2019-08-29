package defpackage;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.common.Callback;
import com.autonavi.minimap.life.order.base.net.OrderNetManager$2;
import com.autonavi.minimap.ordercenter.OrderCenterRequestHolder;
import com.autonavi.minimap.ordercenter.param.ScenicOrdersListRequest;

/* renamed from: dps reason: default package */
/* compiled from: OrderNetManager */
public final class dps {
    public final AosRequest a(dpm dpm, int i, Callback<dpp> callback, String str) {
        ScenicOrdersListRequest scenicOrdersListRequest = new ScenicOrdersListRequest();
        scenicOrdersListRequest.c = dpm.j;
        scenicOrdersListRequest.b = i;
        a(dpm, callback, scenicOrdersListRequest, str);
        return scenicOrdersListRequest;
    }

    public final AosRequest a(dpm dpm, Callback<dpp> callback, ScenicOrdersListRequest scenicOrdersListRequest, String str) {
        CompatDialog a = !TextUtils.isEmpty(str) ? aav.a(scenicOrdersListRequest, str) : null;
        if (a != null) {
            a.show();
        }
        OrderCenterRequestHolder.getInstance().sendScenicOrdersList(scenicOrdersListRequest, new OrderNetManager$2(this, callback, dpm, a));
        return scenicOrdersListRequest;
    }
}
