package defpackage;

import com.autonavi.common.Callback;
import com.autonavi.minimap.shortaddress.ShortAddressRequestHolder;
import com.autonavi.minimap.shortaddress.param.TransformRequest;
import com.autonavi.server.ShortURLResponser;
import com.autonavi.server.ShortURLResponser.ShortURLCallback;

/* renamed from: bnw reason: default package */
/* compiled from: NormalUtilMain */
public final class bnw {
    public static void a(String str, String str2, String str3, Callback<ShortURLResponser> callback) {
        TransformRequest transformRequest = new TransformRequest();
        transformRequest.c = str2;
        transformRequest.b = str;
        if (str3 == null) {
            str3 = "";
        }
        transformRequest.addReqParam("appfrom", str3);
        ShortAddressRequestHolder.getInstance().sendTransform(transformRequest, new ShortURLCallback(callback));
    }
}
