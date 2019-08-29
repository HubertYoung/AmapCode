package defpackage;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import org.json.JSONObject;

/* renamed from: dxa reason: default package */
/* compiled from: FootRideAccessInfoData */
public final class dxa {
    public POI a;
    public int b;
    public boolean c;
    public boolean d;
    private int e;
    private String f;

    @Nullable
    public static dxa a(String str) {
        POI poi;
        dxa dxa = new dxa();
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("isRidePath", "0");
            String optString2 = jSONObject.optString("buryType", "");
            String optString3 = jSONObject.optString("endType", "0");
            JSONObject optJSONObject = jSONObject.optJSONObject("poiData");
            String optString4 = optJSONObject.optString(LocationParams.PARA_FLP_AUTONAVI_LON, "");
            String optString5 = optJSONObject.optString("lat", "");
            String optString6 = optJSONObject.optString("name", "");
            dxa.f = optJSONObject.optString("station_poiid", "");
            dxa.e = Integer.parseInt(optString2);
            dxa.b = Integer.parseInt(optString3);
            dxa.c = TextUtils.equals(optString, "1");
            dxa.d = optJSONObject.optBoolean("isFromMap", false);
            if (TextUtils.isEmpty(optString4) || TextUtils.isEmpty(optString5)) {
                poi = null;
            } else {
                poi = POIFactory.createPOI(optString6, new GeoPoint(Double.parseDouble(optString4), Double.parseDouble(optString5)));
                poi.setId(dxa.f);
            }
            dxa.a = poi;
            return dxa;
        } catch (Exception unused) {
            return null;
        }
    }
}
