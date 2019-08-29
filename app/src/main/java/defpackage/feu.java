package defpackage;

import mtopsdk.mtop.domain.MtopResponse;

/* renamed from: feu reason: default package */
/* compiled from: MtopFinishEvent */
public class feu extends fet {
    public MtopResponse a;
    public String b;

    public feu(MtopResponse mtopResponse) {
        this.a = mtopResponse;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("MtopFinishEvent [seqNo=");
        sb.append(this.b);
        sb.append(", mtopResponse");
        sb.append(this.a);
        sb.append("]");
        return sb.toString();
    }
}
