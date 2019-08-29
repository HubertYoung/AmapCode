package defpackage;

import android.text.TextUtils;
import android.util.Log;
import com.amap.bundle.blutils.log.DebugLog;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import com.autonavi.minimap.route.foot.RouteFootResultData;
import com.autonavi.minimap.route.foot.model.OnFootNaviPath;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ech reason: default package */
/* compiled from: IFootNaviDataUtilImpl */
public final class ech implements avh {

    /* renamed from: ech$a */
    /* compiled from: IFootNaviDataUtilImpl */
    static class a {
        static ech a = new ech();
    }

    public final Object a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            OnFootNaviPath onFootNaviPath = new OnFootNaviPath();
            onFootNaviPath.mStartPOI = btf.a(agd.e(jSONObject, "mStartPOI"));
            onFootNaviPath.mEndPOI = btf.a(agd.e(jSONObject, "mEndPOI"));
            onFootNaviPath.mDataLength = agd.a(jSONObject, "mDataLength");
            onFootNaviPath.mPathlength = agd.a(jSONObject, "mPathlength");
            onFootNaviPath.mTaxiFee = agd.a(jSONObject, "taxi_price");
            onFootNaviPath.mStartDirection = agd.a(jSONObject, "mStartDirection");
            onFootNaviPath.crossingCount = agd.a(jSONObject, "crossingCount");
            onFootNaviPath.mstartX = agd.a(jSONObject, "mstartX");
            onFootNaviPath.mstartY = agd.a(jSONObject, "mstartY");
            onFootNaviPath.mendX = agd.a(jSONObject, "mendX");
            onFootNaviPath.mendY = agd.a(jSONObject, "mendY");
            return onFootNaviPath;
        } catch (JSONException e) {
            DebugLog.error(Log.getStackTraceString(e));
            return null;
        }
    }

    public final Object a(JSONObject jSONObject, POI poi, POI poi2, int i) {
        if (jSONObject == null) {
            return null;
        }
        OnFootNaviPath onFootNaviPath = new OnFootNaviPath();
        onFootNaviPath.mStartPOI = poi;
        onFootNaviPath.mEndPOI = poi2;
        onFootNaviPath.mDataLength = agd.a(jSONObject, "mDataLength");
        onFootNaviPath.mPathlength = i;
        onFootNaviPath.mTaxiFee = agd.a(jSONObject, "taxi_price");
        onFootNaviPath.crossingCount = agd.a(jSONObject, "crossingCount");
        onFootNaviPath.mstartX = agd.a(jSONObject, "mstartX");
        onFootNaviPath.mstartY = agd.a(jSONObject, "mstartY");
        onFootNaviPath.mendX = agd.a(jSONObject, "mendX");
        onFootNaviPath.mendY = agd.a(jSONObject, "mendY");
        return onFootNaviPath;
    }

    public final IRouteResultData a(byte[] bArr, POI poi, POI poi2) {
        RouteFootResultData routeFootResultData = new RouteFootResultData(AMapPageUtil.getAppContext());
        routeFootResultData.setFromPOI(poi);
        routeFootResultData.setToPOI(poi2);
        ecp ecp = new ecp(routeFootResultData);
        ecp.parser(bArr);
        return ecp.a;
    }

    public final String a(Object obj) {
        if (obj == null) {
            return null;
        }
        OnFootNaviPath onFootNaviPath = (OnFootNaviPath) obj;
        JSONObject jSONObject = new JSONObject();
        agd.a(jSONObject, (String) "mStartPOI", btf.a(onFootNaviPath.mStartPOI));
        agd.a(jSONObject, (String) "mEndPOI", btf.a(onFootNaviPath.mEndPOI));
        agd.a(jSONObject, (String) "mDataLength", onFootNaviPath.mDataLength);
        agd.a(jSONObject, (String) "mPathlength", onFootNaviPath.mPathlength);
        agd.a(jSONObject, (String) "taxi_price", onFootNaviPath.mTaxiFee);
        agd.a(jSONObject, (String) "crossingCount", onFootNaviPath.crossingCount);
        agd.a(jSONObject, (String) "mstartX", onFootNaviPath.mstartX);
        agd.a(jSONObject, (String) "mstartY", onFootNaviPath.mstartY);
        agd.a(jSONObject, (String) "mendX", onFootNaviPath.mendX);
        agd.a(jSONObject, (String) "mendY", onFootNaviPath.mendY);
        return jSONObject.toString();
    }
}
