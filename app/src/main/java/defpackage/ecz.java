package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ecz reason: default package */
/* compiled from: AosOperationCollectionResponser */
public final class ecz extends AbstractAOSParser {
    public int a = -1;

    public final String getErrorDesc(int i) {
        return null;
    }

    public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        parseHeader(bArr);
        try {
            if (this.errorCode == 1) {
                JSONObject jSONObject = new JSONObject(new String(bArr, "UTF-8")).getJSONObject("data");
                if (jSONObject != null) {
                    this.a = jSONObject.optInt("shop_coin");
                }
            }
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
    }
}
