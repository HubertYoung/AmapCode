package defpackage;

import com.autonavi.common.Callback;
import com.autonavi.mine.page.ToolsBoxUtils$1;
import com.autonavi.minimap.oss.OssRequestHolder;
import com.autonavi.minimap.oss.param.TripConfigListRequest;

/* renamed from: cgq reason: default package */
/* compiled from: ToolsBoxUtils */
public final class cgq {
    public final void a(double d, double d2, String str, Callback<ddt> callback) {
        TripConfigListRequest tripConfigListRequest = new TripConfigListRequest();
        tripConfigListRequest.e = "4";
        tripConfigListRequest.b = Double.toString(d);
        tripConfigListRequest.c = Double.toString(d2);
        tripConfigListRequest.d = str;
        OssRequestHolder.getInstance().sendTripConfigList(tripConfigListRequest, new ToolsBoxUtils$1(this, callback));
    }
}
