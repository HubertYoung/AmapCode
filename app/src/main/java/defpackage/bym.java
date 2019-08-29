package defpackage;

import com.alipay.mobile.beehive.audio.Constants;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.PageBundle;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bym reason: default package */
/* compiled from: EditCommentAction */
public class bym extends vz {
    private static final String a = "bym";

    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        JsAdapter a2 = a();
        if (a2 != null) {
            try {
                String optString = jSONObject.optString(Constants.KEY_AUDIO_BUSINESS_ID);
                int i = jSONObject.getInt("score");
                String string = jSONObject.getString("from");
                JSONObject jSONObject2 = jSONObject.getJSONObject("poiInfo");
                String optString2 = jSONObject2.optString("name");
                String optString3 = jSONObject2.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("EDIT_COMMENT_CALLBACK", waVar);
                pageBundle.putInt("EDIT_COMMENT_RATING", i);
                pageBundle.putString("EDIT_COMMENT_POI", optString2);
                pageBundle.putString("EDIT_COMMENT_POI_ID", optString3);
                pageBundle.putString("EDIT_COMMENT_POI_BUSINESS", optString);
                pageBundle.putString("COMMENT_FROM", string);
                if (a2 != null) {
                    a2.mPageContext.startPageForResult((String) "amap.search.action.comment", pageBundle, 1);
                }
            } catch (JSONException e) {
                AMapLog.e(a, e.toString());
            }
        }
    }
}
