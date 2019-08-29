package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.footnavi.api.IFootNaviPage;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import com.autonavi.minimap.route.foot.RouteFootResultData;
import com.autonavi.minimap.route.foot.page.AjxFootNaviPage;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONException;
import org.json.JSONObject;

@BundleInterface(avi.class)
/* renamed from: ece reason: default package */
/* compiled from: FootNaviService */
public class ece extends esi implements avi {
    public final IRouteResultData a(Context context) {
        return new RouteFootResultData(context);
    }

    public final void a(PageBundle pageBundle) {
        if (pageBundle != null) {
            GeoPoint geoPoint = (GeoPoint) pageBundle.getObject("startPoint");
            if (geoPoint == null) {
                geoPoint = LocationInstrument.getInstance().getLatestPosition();
            }
            GeoPoint geoPoint2 = (GeoPoint) pageBundle.getObject("endPoint");
            if (ebi.a(null, geoPoint, geoPoint2, 1)) {
                String string = pageBundle.getString("endPointName");
                if (AMapPageUtil.getPageContext() != null) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        POI createPOI = POIFactory.createPOI();
                        createPOI.setPoint(geoPoint2);
                        if (!TextUtils.isEmpty(string)) {
                            createPOI.setName(string);
                        }
                        jSONObject.put("endPoi", bnx.b(createPOI));
                        jSONObject.put("fromWhere", "jcdhjs");
                        pageBundle.putString("bundle_key_obj_result", jSONObject.toString());
                        AMapPageUtil.getPageContext().startPage(AjxFootNaviPage.class, pageBundle);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public final boolean e() {
        return ecd.a().a;
    }

    public final avj a() {
        return a.a;
    }

    public final avh b() {
        return a.a;
    }

    public final IFootNaviPage c() {
        return a.a;
    }

    public final avk d() {
        return a.a;
    }
}
