package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ejg reason: default package */
/* compiled from: TrainTicketPurchasingResponser */
public final class ejg extends AbstractAOSParser {
    public String a;

    public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        JSONObject parseHeader = parseHeader(bArr);
        if (this.errorCode == 1 && parseHeader != null) {
            this.a = new JSONObject(new String(bArr, "UTF-8")).getString("action_url");
        }
    }

    public final String getErrorDesc(int i) {
        if (this.errorMessage == null || this.errorMessage.equals("")) {
            return UNKNOWN_ERROR;
        }
        return this.errorMessage;
    }
}
