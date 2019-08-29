package defpackage;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.statistics.LogManager;
import org.json.JSONObject;

/* renamed from: dmv reason: default package */
/* compiled from: LogUserActionAction */
public class dmv extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        int i;
        if (a() != null) {
            try {
                int i2 = 0;
                int optInt = jSONObject.has("pageid") ? jSONObject.optInt("pageid") : 0;
                int optInt2 = jSONObject.has("buttonid") ? jSONObject.optInt("buttonid") : 0;
                if (jSONObject.has("poiInfo")) {
                    JSONObject optJSONObject = jSONObject.optJSONObject("poiInfo");
                    if (optJSONObject != null) {
                        i2 = optJSONObject.optInt(DictionaryKeys.CTRLXY_X);
                        i = optJSONObject.optInt(DictionaryKeys.CTRLXY_Y);
                        LogManager.actionLog(optInt, optInt2, i2, i, new JSONObject(jSONObject.getString("para")));
                    }
                }
                i = 0;
                LogManager.actionLog(optInt, optInt2, i2, i, new JSONObject(jSONObject.getString("para")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
