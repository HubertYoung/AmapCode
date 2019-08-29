package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.amap.bundle.logs.AMapLog;
import org.json.JSONObject;

/* renamed from: bmh reason: default package */
/* compiled from: SendMediaEventAction */
public class bmh extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        if (jSONObject == null) {
            AMapLog.d("WebViewH5", "SendMediaEventAction param null");
            return;
        }
        String optString = jSONObject.optString("event", null);
        AMapLog.d("WebViewH5", "mediaEvent: ".concat(String.valueOf(optString)));
        bfo bfo = (bfo) a.a.a(bfo.class);
        if (bfo != null) {
            if (TextUtils.equals(optString, AudioUtils.CMDPLAY)) {
                bfo.b(1);
                return;
            }
            if (TextUtils.equals(optString, "end") || TextUtils.equals(optString, AudioUtils.CMDPAUSE)) {
                bfo.b(0);
            }
        }
    }
}
