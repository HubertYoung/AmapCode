package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;
import org.json.JSONObject;

/* renamed from: adp reason: default package */
/* compiled from: SchoolbusCheckResponsor */
public final class adp extends AbstractAOSParser {
    public adj a = new adj();

    public final String getErrorDesc(int i) {
        return null;
    }

    public final void parser(byte[] bArr) {
        parseHeader(bArr);
        if (bArr == null) {
            this.a.a = -1;
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(new String(bArr, "UTF-8"));
            this.a.a = jSONObject.optInt("code", 0);
            this.a.b = jSONObject.optString("result");
            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
            this.a.c = jSONObject2.optInt("role");
        } catch (Exception unused) {
            this.a.a = -2;
        }
    }
}
