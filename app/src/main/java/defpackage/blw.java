package defpackage;

import com.amap.bundle.logs.AMapLog;
import org.json.JSONObject;

/* renamed from: blw reason: default package */
/* compiled from: ToggleComponentAction */
public class blw extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        if (a() != null && "bottomBar".equals(jSONObject.optString("feature"))) {
            AMapLog.e("ToggleComponentAction", "JavaScript.viewBottom 被我废了，也就是这个Action也没用了，出事了找我 congqi.lcq");
        }
    }
}
