package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: adq reason: default package */
/* compiled from: SchoolbusStatusResponsor */
public final class adq extends AbstractAOSParser {
    public adk a;
    private String b;

    public final String getErrorDesc(int i) {
        return null;
    }

    public adq(String str) {
        this.b = str;
    }

    public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        this.a = new adk();
        if (bArr == null) {
            this.a.a = -1;
            this.a.c = this.b;
            this.a.d = -1;
            return;
        }
        try {
            String str = new String(bArr, "UTF-8");
            JSONObject jSONObject = new JSONObject(str);
            int optInt = jSONObject.optInt("code");
            this.a.b = str;
            if (optInt == 1) {
                JSONObject optJSONObject = jSONObject.optJSONObject("data");
                this.a.c = optJSONObject.optString("routeId");
                this.a.d = optJSONObject.optInt("routeStatus");
                this.a.e = optJSONObject.optInt("lostControl");
                return;
            }
            this.a.c = this.b;
            this.a.d = -1;
        } catch (Exception unused) {
            this.a.c = this.b;
            this.a.d = -1;
        }
    }
}
