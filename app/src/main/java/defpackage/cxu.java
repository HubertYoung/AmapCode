package defpackage;

import com.alipay.mobile.h5container.api.H5Param;
import com.amap.bundle.network.response.AbstractAOSParser;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cxu reason: default package */
/* compiled from: SplaAppResponser */
public final class cxu extends AbstractAOSParser {
    public String a;
    public String b;
    public String c;

    public final String getErrorDesc(int i) {
        return null;
    }

    public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        JSONObject parseHeader = parseHeader(bArr);
        if (!(bArr == null || this.errorCode != 1 || parseHeader == null)) {
            try {
                this.a = parseHeader.getString("package");
                this.b = parseHeader.getString("desc");
                this.c = parseHeader.getString(H5Param.MENU_ICON);
            } catch (JSONException unused) {
                this.errorCode = -2;
                this.errorMessage = PARSE_ERROR;
            }
        }
    }
}
