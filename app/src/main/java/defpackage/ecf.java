package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.navigation.NavigationRequestHolder;
import com.autonavi.minimap.navigation.param.NewFootRequest;
import com.autonavi.minimap.route.foot.RouteFootResultCallBack;
import com.autonavi.minimap.route.foot.net.FootRequestHelper$1;
import com.autonavi.minimap.route.foot.net.FootRequestHelper$2;
import com.autonavi.server.aos.serverkey;

/* renamed from: ecf reason: default package */
/* compiled from: FootRequestImpl */
public final class ecf implements avj {

    /* renamed from: ecf$a */
    /* compiled from: FootRequestImpl */
    static class a {
        static ecf a = new ecf();
    }

    public final AosRequest a(Context context, POI poi, POI poi2, axa axa) {
        eag eag = new eag(RouteType.ONFOOT, poi, poi2);
        if (!ebi.a(null, eag.b.getPoint(), eag.c.getPoint(), 0)) {
            return null;
        }
        POI poi3 = eag.b;
        POI poi4 = eag.c;
        FootRequestHelper$1 footRequestHelper$1 = new FootRequestHelper$1(axa, eag);
        if (bnx.a(poi3, poi4)) {
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.route_same_from_to_foot));
            return null;
        }
        String a2 = eah.a(poi3, poi4);
        eau.a("Foot Request", "requestRouteFootResponse param: ".concat(String.valueOf(a2)));
        RouteFootResultCallBack routeFootResultCallBack = new RouteFootResultCallBack(new FootRequestHelper$2(context, poi3, poi4, footRequestHelper$1), poi3, poi4);
        ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.AOS_URL_KEY);
        NewFootRequest newFootRequest = new NewFootRequest();
        String amapEncodeV2 = serverkey.amapEncodeV2(a2);
        if (!TextUtils.isEmpty(amapEncodeV2)) {
            newFootRequest.setBody(amapEncodeV2.getBytes());
        }
        NavigationRequestHolder.getInstance().sendNewFoot(newFootRequest, routeFootResultCallBack);
        return newFootRequest;
    }
}
