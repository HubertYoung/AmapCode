package defpackage;

import mtopsdk.mtop.domain.MtopResponse;

/* renamed from: fer reason: default package */
/* compiled from: MtopCacheEvent */
public final class fer extends feu {
    public fer(MtopResponse mtopResponse) {
        super(mtopResponse);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("MtopCacheEvent [seqNo=");
        sb.append(this.b);
        sb.append(", mtopResponse=");
        sb.append(this.a);
        sb.append("]");
        return sb.toString();
    }
}
