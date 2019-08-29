package defpackage;

import com.alipay.mobile.rome.longlinkservice.LongLinkMsgConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: yp reason: default package */
/* compiled from: UplinkProto */
public final class yp {
    public long a = 0;
    public int b = 0;
    public JSONObject c = null;

    public final JSONObject a() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("protoId", this.a);
            jSONObject.put("bizType", this.b);
            jSONObject.put(LongLinkMsgConstants.LONGLINK_APPDATA, this.c != null ? this.c : new JSONObject());
            return jSONObject;
        } catch (JSONException unused) {
            return null;
        }
    }
}
