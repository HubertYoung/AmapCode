package defpackage;

/* renamed from: bke reason: default package */
/* compiled from: StatsSnapshot */
public final class bke {
    public final int a;
    public final int b;
    public final long c;
    public final long d;
    public final long e;
    public final long f;
    public final long g;
    public final long h;
    public final long i;
    public final long j;
    public final int k;
    public final int l;
    public final int m;
    public final long n;

    public bke(int i2, int i3, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, int i4, int i5, int i6, long j10) {
        this.a = i2;
        this.b = i3;
        this.c = j2;
        this.d = j3;
        this.e = j4;
        this.f = j5;
        this.g = j6;
        this.h = j7;
        this.i = j8;
        this.j = j9;
        this.k = i4;
        this.l = i5;
        this.m = i6;
        this.n = j10;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("StatsSnapshot{maxSize=");
        sb.append(this.a);
        sb.append(", size=");
        sb.append(this.b);
        sb.append(", cacheHits=");
        sb.append(this.c);
        sb.append(", cacheMisses=");
        sb.append(this.d);
        sb.append(", downloadCount=");
        sb.append(this.k);
        sb.append(", totalDownloadSize=");
        sb.append(this.e);
        sb.append(", averageDownloadSize=");
        sb.append(this.h);
        sb.append(", totalOriginalBitmapSize=");
        sb.append(this.f);
        sb.append(", totalTransformedBitmapSize=");
        sb.append(this.g);
        sb.append(", averageOriginalBitmapSize=");
        sb.append(this.i);
        sb.append(", averageTransformedBitmapSize=");
        sb.append(this.j);
        sb.append(", originalBitmapCount=");
        sb.append(this.l);
        sb.append(", transformedBitmapCount=");
        sb.append(this.m);
        sb.append(", timeStamp=");
        sb.append(this.n);
        sb.append('}');
        return sb.toString();
    }
}
