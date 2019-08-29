package defpackage;

import android.content.Context;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import com.autonavi.sdk.location.LocationInstrument;
import java.net.UnknownHostException;

/* renamed from: duq reason: default package */
/* compiled from: RouteResultDataRequestModel */
public final class duq implements axa {
    public a a = this;
    public Bus b = null;
    public int c = -1;
    private Context d;

    /* renamed from: duq$a */
    /* compiled from: RouteResultDataRequestModel */
    public interface a {
        void a(IRouteResultData iRouteResultData, RouteType routeType);
    }

    public duq(Context context) {
        this.d = context;
    }

    public final void a(IRouteResultData iRouteResultData, RouteType routeType) {
        if (iRouteResultData == null || !iRouteResultData.hasData()) {
            ToastHelper.showToast(this.d.getString(R.string.route_request_error));
            return;
        }
        if (this.a != null) {
            this.a.a(iRouteResultData, routeType);
        }
    }

    public final void a(RouteType routeType, int i, String str) {
        if (i == 2 || i == 201) {
            a(RouteType.ONFOOT);
            ToastHelper.showToast(this.d.getString(R.string.route_not_query_suitable_bus_try_recommend_foot));
            return;
        }
        ToastHelper.showToast(str);
    }

    public final void a(Throwable th, boolean z) {
        if (z) {
            ToastHelper.showLongToast(this.d.getString(R.string.route_request_error));
        } else if (th instanceof UnknownHostException) {
            ToastHelper.showToast(this.d.getString(R.string.network_error_message));
        } else {
            ToastHelper.showLongToast(this.d.getString(R.string.route_request_error));
        }
    }

    public final void a(RouteType routeType) {
        if (this.b != null && this.b.stationX != null && this.b.stationY != null && this.b.stations != null) {
            POI createPOI = POIFactory.createPOI();
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
            if (latestPosition != null) {
                createPOI.setName("我的位置");
                createPOI.setPoint(latestPosition);
                bax bax = (bax) defpackage.esb.a.a.a(bax.class);
                if (bax != null) {
                    POI createPOI2 = POIFactory.createPOI("", new GeoPoint());
                    createPOI2.setAdCode(this.b.areacode);
                    createPOI2.setPoint(new GeoPoint(this.b.stationX[this.c], this.b.stationY[this.c]));
                    createPOI2.setName(this.b.stations[this.c]);
                    switch (routeType) {
                        case BUS:
                            PageBundle pageBundle = new PageBundle();
                            pageBundle.putBoolean("bundle_key_auto_route", true);
                            pageBundle.putInt("key_type", RouteType.BUS.getValue());
                            pageBundle.putObject("bundle_key_poi_start", createPOI);
                            pageBundle.putObject("bundle_key_poi_end", createPOI2);
                            bax.a(pageBundle);
                            return;
                        case ONFOOT:
                            PageBundle pageBundle2 = new PageBundle();
                            pageBundle2.putBoolean("bundle_key_auto_route", true);
                            pageBundle2.putInt("key_type", RouteType.ONFOOT.getValue());
                            pageBundle2.putObject("bundle_key_poi_start", createPOI);
                            pageBundle2.putObject("bundle_key_poi_end", createPOI2);
                            bax.a(pageBundle2);
                            break;
                    }
                    return;
                }
                return;
            }
            ToastHelper.showLongToast(this.d.getString(R.string.autonavi_cannot_get_location));
        }
    }
}
