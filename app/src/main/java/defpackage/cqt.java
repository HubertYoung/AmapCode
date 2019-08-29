package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;
import org.json.JSONObject;

/* renamed from: cqt reason: default package */
/* compiled from: CarDriverLicenseParser */
public final class cqt extends AbstractAOSParser {
    public JSONObject a = null;
    private String b;

    public final void parser(byte[] bArr) {
        parseHeader(bArr);
        this.b = parserMessage(UNKNOWN_ERROR, this.mDataObject);
        try {
            if (isSuccessRequest()) {
                this.a = this.mDataObject.optJSONObject("data");
                this.errorCode = 1;
                return;
            }
            this.errorCode = -1;
            this.b = ERROR_NETWORK;
        } catch (Exception e) {
            this.errorCode = -1;
            this.b = UNKNOWN_ERROR;
            kf.a((Throwable) e);
        }
    }

    public final String getErrorDesc(int i) {
        return this.b;
    }
}
