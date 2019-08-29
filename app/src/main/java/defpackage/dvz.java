package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.entity.IBusRouteResult;
import com.autonavi.minimap.R;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dvz reason: default package */
/* compiled from: AosBusRouteResponsor */
public final class dvz extends AbstractAOSParser {
    public IBusRouteResult a;
    private boolean b = false;

    public dvz(IBusRouteResult iBusRouteResult) {
        this.a = iBusRouteResult;
    }

    public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        parseHeader(bArr);
        if (this.errorCode == -1) {
            eko.a(1);
        } else if (this.errorCode == 1) {
            this.errorCode = a(this.mDataObject, "why");
            if (this.errorCode == 0) {
                try {
                    this.a.setBaseData(bArr);
                    this.a.parse(this.mDataObject, 5);
                    this.b = true;
                    eko.a(10000);
                } catch (Exception e) {
                    kf.a((Throwable) e);
                    this.errorCode = -1;
                    this.errorMessage = ERROR_NETWORK;
                    eko.a(10008);
                }
            } else {
                a();
            }
        } else if (this.errorCode == 0 || (this.errorCode > 1 && this.errorCode < 15)) {
            this.errorCode = 101;
            a();
        } else {
            this.errorCode = 6;
            a();
        }
    }

    public final String getErrorDesc(int i) {
        return this.errorMessage;
    }

    private String a() {
        int i = this.errorCode;
        if (i == 101) {
            StringBuilder sb = new StringBuilder();
            sb.append(AMapAppGlobal.getApplication().getString(R.string.server_exception));
            sb.append("/");
            sb.append(AMapAppGlobal.getApplication().getString(R.string.please_retry_later));
            this.errorMessage = sb.toString();
            eko.a(20042);
        } else if (i != 201) {
            switch (i) {
                case 1:
                    this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.no_route_result);
                    eko.a(20040);
                    break;
                case 2:
                    this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.route_bus_request_distance_near_promptinfo);
                    eko.a(20041);
                    break;
                case 3:
                case 4:
                case 5:
                case 7:
                case 9:
                    this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.no_route_result);
                    eko.a(20040);
                    break;
                case 6:
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(AMapAppGlobal.getApplication().getString(R.string.network_is_unstable_));
                    sb2.append("，");
                    sb2.append(AMapAppGlobal.getApplication().getString(R.string.please_check_and_retry));
                    this.errorMessage = sb2.toString();
                    eko.a(10008);
                    break;
                case 8:
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(AMapAppGlobal.getApplication().getString(R.string.no_subway_result));
                    sb3.append("/");
                    sb3.append(AMapAppGlobal.getApplication().getString(R.string.route_not_query_suitable_onlysubway_try_recommend_buspath));
                    this.errorMessage = sb3.toString();
                    eko.a(20040);
                    break;
                default:
                    switch (i) {
                        case 41:
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append(AMapAppGlobal.getApplication().getString(R.string.no_route_result));
                            sb4.append("/");
                            sb4.append(AMapAppGlobal.getApplication().getString(R.string.suggest_try_others));
                            this.errorMessage = sb4.toString();
                            eko.a(20041);
                            break;
                        case 42:
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(AMapAppGlobal.getApplication().getString(R.string.no_route_result));
                            sb5.append("/");
                            sb5.append(AMapAppGlobal.getApplication().getString(R.string.suggest_try_others));
                            this.errorMessage = sb5.toString();
                            eko.a(20041);
                            break;
                        case 43:
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(AMapAppGlobal.getApplication().getString(R.string.server_exception));
                            sb6.append("/");
                            sb6.append(AMapAppGlobal.getApplication().getString(R.string.please_retry_later));
                            this.errorMessage = sb6.toString();
                            eko.a(20042);
                            break;
                        case 44:
                        case 45:
                        case 46:
                        case 47:
                        case 48:
                        case 49:
                            StringBuilder sb7 = new StringBuilder();
                            sb7.append(AMapAppGlobal.getApplication().getString(R.string.no_route_result));
                            sb7.append("/");
                            sb7.append(AMapAppGlobal.getApplication().getString(R.string.suggest_try_others));
                            this.errorMessage = sb7.toString();
                            eko.a(20041);
                            break;
                        default:
                            this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.route_not_query_suitable_buspath);
                            eko.a(20040);
                            break;
                    }
            }
        } else {
            this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.route_not_query_suitable_bus_try_recommend_foot);
            eko.a(20041);
        }
        return this.errorMessage;
    }

    private static int a(JSONObject jSONObject, String str) {
        try {
            String optString = jSONObject.optString(str);
            if (optString.equals("")) {
                return 0;
            }
            return ahh.b(optString);
        } catch (Exception unused) {
            return 0;
        }
    }
}
