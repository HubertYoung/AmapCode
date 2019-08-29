package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.PageBundle;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: aws reason: default package */
/* compiled from: PictureUploadAction */
public class aws extends vz {
    private ArrayList<cal> a = new ArrayList<>();

    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        JsAdapter a2 = a();
        if (a2 != null) {
            new PageBundle();
            try {
                JSONObject optJSONObject = jSONObject.optJSONObject("poiInfo");
                if (optJSONObject != null) {
                    String optString = optJSONObject.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, "");
                    if (!optString.isEmpty()) {
                        can.a().b = optString;
                        can.a().a = optJSONObject.optString("_action", "");
                        can.a().c = optJSONObject.optString(LocationParams.PARA_FLP_AUTONAVI_LON, "");
                        can.a().d = optJSONObject.optString("lat", "");
                        can.a().e = jSONObject.optString("uploadTips", "");
                        can.a().f = jSONObject.optString("link", "");
                        can.a().g = waVar;
                        caf caf = (caf) a.a.a(caf.class);
                        if (caf != null) {
                            caf.a(a2.mPageContext);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
