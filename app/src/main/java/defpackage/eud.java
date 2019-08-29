package defpackage;

/* renamed from: eud reason: default package */
/* compiled from: TotalSizeLruDiskUsage */
public final class eud extends eub {
    private final long a;

    public eud(long j) {
        if (j <= 0) {
            throw new IllegalArgumentException("Max size must be positive number!");
        }
        this.a = j;
    }

    /* access modifiers changed from: protected */
    public final boolean a(long j) {
        return j <= this.a;
    }
}
