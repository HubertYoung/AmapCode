package defpackage;

import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.routecommon.entity.IBusRouteResult;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONObject;

/* renamed from: eic reason: default package */
/* compiled from: OpenRouteBusDetailAction */
public class eic extends vz implements axa {
    private JsAdapter a;
    private POI b;
    private POI c;

    public final void a(RouteType routeType, int i, String str) {
    }

    public final void a(Throwable th, boolean z) {
    }

    public final void a(JSONObject jSONObject, wa waVar) {
        this.a = a();
        if (this.a != null) {
            try {
                if (jSONObject.has("startPoi") && jSONObject.has("endPoi")) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("startPoi");
                    JSONObject jSONObject3 = jSONObject.getJSONObject("endPoi");
                    this.b = a(jSONObject2);
                    this.c = a(jSONObject3);
                    if (this.b != null) {
                        if (this.c != null) {
                            atb atb = (atb) a.a.a(atb.class);
                            if (atb != null) {
                                if (!jSONObject.has("pathData") || jSONObject.isNull("pathData")) {
                                    atb.c().a(this.a.mPageContext.getContext(), this.b, this.c, "6", 0, this);
                                    return;
                                }
                                JSONObject jSONObject4 = jSONObject.getJSONObject("pathData");
                                IBusRouteResult iBusRouteResult = (IBusRouteResult) atb.d();
                                iBusRouteResult.setFromPOI(this.b);
                                iBusRouteResult.setToPOI(this.c);
                                String jSONObject5 = jSONObject4.toString();
                                if (iBusRouteResult.parse(jSONObject4, 1) && !TextUtils.isEmpty(jSONObject5)) {
                                    iBusRouteResult.setBaseData(jSONObject5.getBytes());
                                    a(iBusRouteResult);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void a(IBusRouteResult iBusRouteResult) {
        a(this.a.mPageContext, iBusRouteResult);
    }

    private static POI a(JSONObject jSONObject) {
        try {
            String e = axr.e(jSONObject, "name");
            Double valueOf = Double.valueOf(axr.c(jSONObject, LocationParams.PARA_FLP_AUTONAVI_LON));
            Double valueOf2 = Double.valueOf(axr.c(jSONObject, "lat"));
            String e2 = axr.e(jSONObject, LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
            POI createPOI = POIFactory.createPOI(e, new GeoPoint(valueOf.doubleValue(), valueOf2.doubleValue()));
            createPOI.setId(e2);
            return createPOI;
        } catch (Exception e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public final void a(IRouteResultData iRouteResultData, RouteType routeType) {
        if (iRouteResultData != null && (iRouteResultData instanceof IBusRouteResult)) {
            a((IBusRouteResult) iRouteResultData);
        }
    }

    private static void a(bid bid, IBusRouteResult iBusRouteResult) {
        if (bid != null && iBusRouteResult != null) {
            atb atb = (atb) a.a.a(atb.class);
            if (atb != null) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ, iBusRouteResult);
                bid.startPage(atb.a().a(2), pageBundle);
            }
        }
    }
}
