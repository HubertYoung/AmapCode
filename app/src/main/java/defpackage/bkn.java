package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bkn reason: default package */
/* compiled from: JsAosParser */
public final class bkn extends AbstractAOSParser {
    public String a = null;
    public byte[] b = null;

    public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        if (super.parseHeader(bArr) != null) {
            this.a = new String(bArr);
            this.a = this.a.replaceAll("\"", "\\\"");
            return;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("code", "-1");
        this.a = jSONObject.toString();
        this.b = bArr;
    }

    public final String getErrorDesc(int i) {
        return this.errorMessage;
    }
}
