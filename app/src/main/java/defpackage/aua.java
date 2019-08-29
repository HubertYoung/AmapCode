package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;

/* renamed from: aua reason: default package */
/* compiled from: SyncVehiclesParser */
public final class aua extends AbstractAOSParser {
    public String a;

    public final void parser(byte[] bArr) {
        parseHeader(bArr);
        this.a = parserMessage(UNKNOWN_ERROR, this.mDataObject);
    }

    public final String getErrorDesc(int i) {
        return this.a;
    }
}
