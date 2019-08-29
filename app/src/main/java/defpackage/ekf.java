package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;

/* renamed from: ekf reason: default package */
/* compiled from: BusNaviReviewResponser */
public final class ekf extends AbstractAOSParser {
    public final String getErrorDesc(int i) {
        return null;
    }

    public final void parser(byte[] bArr) {
        parseHeader(bArr);
    }
}
