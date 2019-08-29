package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;

/* renamed from: ari reason: default package */
/* compiled from: RedesignOnLineResponse */
public final class ari extends AbstractAOSParser {
    public final String getErrorDesc(int i) {
        return "";
    }

    public final void parser(byte[] bArr) {
        parseHeader(bArr);
    }
}
