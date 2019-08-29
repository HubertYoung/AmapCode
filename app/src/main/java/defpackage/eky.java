package defpackage;

import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.common.model.POI;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: eky reason: default package */
/* compiled from: GeocodeResult */
public final class eky {
    public POI a = null;

    /* renamed from: eky$a */
    /* compiled from: GeocodeResult */
    public static class a {
        public static eky a(byte[] bArr) {
            eky eky = new eky();
            if (bArr == null) {
                return eky;
            }
            try {
                String str = new String(bArr, "UTF-8");
                if (!TextUtils.isEmpty(str)) {
                    JSONArray jSONArray = new JSONObject(str).getJSONArray("geo_results");
                    if (jSONArray != null && jSONArray.length() > 0) {
                        JSONObject jSONObject = jSONArray.getJSONObject(0);
                        eky.a = POIFactory.createPOI(jSONObject.getString("formattedaddress"), cob.a(Double.valueOf(jSONObject.getDouble("latitude")).doubleValue(), Double.valueOf(jSONObject.getDouble("longitude")).doubleValue()));
                        eky.a.setCityName(jSONObject.getString("cityname"));
                    }
                }
            } catch (Throwable unused) {
            }
            return eky;
        }
    }
}
