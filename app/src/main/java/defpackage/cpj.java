package defpackage;

import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.CpData;
import com.autonavi.common.model.POI;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xidea.el.json.JSONEncoder;

/* renamed from: cpj reason: default package */
/* compiled from: POITransfer */
public final class cpj {
    public static void a(String str, POI poi) {
        try {
            if (!TextUtils.isEmpty(str)) {
                JSONArray jSONArray = new JSONArray(str);
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    CpData cpData = new CpData();
                    if (jSONObject.has("cpid")) {
                        cpData.setCpid(jSONObject.getString("cpid"));
                    }
                    if (jSONObject.has("source")) {
                        cpData.setSource(jSONObject.getString("source"));
                    }
                    arrayList.add(cpData);
                }
                poi.getPoiExtra().put("Cpdata", JSONEncoder.encode(arrayList));
                return;
            }
            poi.getPoiExtra().remove("Cpdata");
        } catch (JSONException e) {
            kf.a((Throwable) e);
        }
    }
}
