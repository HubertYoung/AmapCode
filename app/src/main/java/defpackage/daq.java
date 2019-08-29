package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

/* renamed from: daq reason: default package */
/* compiled from: DisplayLogResponse */
public final class daq extends AbstractAOSParser {
    public final String getErrorDesc(int i) {
        return "";
    }

    public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        parseHeader(bArr);
    }
}
