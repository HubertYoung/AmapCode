package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: eji reason: default package */
/* compiled from: TrainTicketPurchaseResponser */
public final class eji extends AbstractAOSParser {
    public String a;
    public boolean b;

    public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        JSONObject parseHeader = parseHeader(bArr);
        boolean z = true;
        if (this.errorCode == 1 && parseHeader != null) {
            JSONObject jSONObject = new JSONObject(new String(bArr, "UTF-8"));
            this.a = jSONObject.getString("action_url");
            this.a = ejx.a(this.a);
            if (jSONObject.optInt("bdflag", 1) != 1) {
                z = false;
            }
            this.b = z;
        }
    }

    public final String getErrorDesc(int i) {
        if (this.errorMessage == null || this.errorMessage.equals("")) {
            return UNKNOWN_ERROR;
        }
        return this.errorMessage;
    }
}
