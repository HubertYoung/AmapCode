package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

/* renamed from: vx reason: default package */
/* compiled from: ADH5LogResponse */
public final class vx extends AbstractAOSParser {
    public final String getErrorDesc(int i) {
        return "";
    }

    public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        parseHeader(bArr);
    }
}
