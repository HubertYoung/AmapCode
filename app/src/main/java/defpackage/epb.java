package defpackage;

import java.util.List;

/* renamed from: epb reason: default package */
/* compiled from: LMNumStatistics */
public final class epb {
    public long a;
    public int b;

    public epb(long j, int i) {
        this.a = j;
        this.b = i;
    }

    public static int[] a(List<epb> list, long j) {
        int[] iArr = {0, 0, 0};
        if (list != null) {
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            for (epb next : list) {
                if (next.a >= j) {
                    i++;
                    i2 += next.b;
                    if (next.b > i3) {
                        i3 = next.b;
                    }
                }
            }
            if (i > 0) {
                iArr[0] = i2;
                iArr[1] = i2 / i;
                iArr[2] = i3;
            }
        }
        return iArr;
    }
}
