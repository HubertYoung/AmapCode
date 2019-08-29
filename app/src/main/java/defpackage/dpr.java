package defpackage;

import com.autonavi.common.Callback;
import com.autonavi.minimap.auth.AuthRequestHolder;
import com.autonavi.minimap.auth.param.RequestVerifycodeRequest;
import com.autonavi.minimap.life.order.base.net.OrderAosHelper$1;

/* renamed from: dpr reason: default package */
/* compiled from: OrderAosHelper */
public final class dpr {
    public static void a(RequestVerifycodeRequest requestVerifycodeRequest, Callback<dpn> callback) {
        AuthRequestHolder.getInstance().sendRequestVerifycode(requestVerifycodeRequest, new OrderAosHelper$1(callback));
    }
}
