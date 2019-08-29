package defpackage;

/* renamed from: eax reason: default package */
/* compiled from: MathUtil */
public final class eax {
    public static int a(long j, long j2) {
        int i = (j > j2 ? 1 : (j == j2 ? 0 : -1));
        if (i < 0) {
            return -1;
        }
        return i == 0 ? 0 : 1;
    }
}
