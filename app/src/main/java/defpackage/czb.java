package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;
import org.json.JSONObject;

/* renamed from: czb reason: default package */
/* compiled from: RescuePlayResponse */
public final class czb extends AbstractAOSParser {
    public String a;
    public String b;
    public String c;
    public int d = -1;

    public final String getErrorDesc(int i) {
        return null;
    }

    public final void parser(byte[] bArr) {
        try {
            JSONObject parseHeader = parseHeader(bArr);
            if (parseHeader != null && this.result) {
                JSONObject optJSONObject = parseHeader.optJSONObject("data");
                if (optJSONObject != null) {
                    this.a = optJSONObject.optString("doc", "");
                    this.b = optJSONObject.optString("url", "");
                    this.c = optJSONObject.optString("btn", "");
                    this.d = optJSONObject.optInt("type", -1);
                }
            }
        } catch (Exception unused) {
            this.errorCode = -1;
        }
    }
}
