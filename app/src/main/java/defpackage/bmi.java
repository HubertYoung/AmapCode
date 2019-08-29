package defpackage;

import com.amap.bundle.logs.AMapLog;
import org.json.JSONObject;

/* renamed from: bmi reason: default package */
/* compiled from: SendVUICmdResultAction */
public class bmi extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        if (jSONObject != null) {
            int optInt = jSONObject.optInt("code");
            int optInt2 = jSONObject.optInt("token_id");
            int optInt3 = jSONObject.optInt("autoListen");
            String optString = jSONObject.optString("tipText");
            StringBuilder sb = new StringBuilder("SendVUICmdResultAction code: ");
            sb.append(optInt);
            sb.append(", tokenId: ");
            sb.append(optInt2);
            sb.append(", tipText: ");
            sb.append(optString);
            sb.append(", autoListen: ");
            sb.append(optInt3);
            AMapLog.d("WebViewH5", sb.toString());
            bfo bfo = (bfo) a.a.a(bfo.class);
            if (bfo != null) {
                bfo.a(optInt2, optInt);
            }
            return;
        }
        AMapLog.e("WebViewH5", "SendVUICmdResultAction param null");
    }
}
