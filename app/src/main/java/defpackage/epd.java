package defpackage;

/* renamed from: epd reason: default package */
/* compiled from: LMSwitch */
public final class epd {
    public long a;
    public int b;

    public epd(long j, int i) {
        this.a = j;
        this.b = i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("LMSwitch{timestamp=");
        sb.append(epl.a(this.a));
        sb.append(", type=");
        sb.append(Integer.toBinaryString(this.b));
        sb.append('}');
        return sb.toString();
    }
}
