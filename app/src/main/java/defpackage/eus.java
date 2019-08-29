package defpackage;

import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;

/* renamed from: eus reason: default package */
/* compiled from: OggSpeexWriter */
public final class eus extends euq {
    OutputStream a;
    int b;
    int c;
    int d;
    int e;
    boolean f;
    int g;
    final byte[] h;
    int i;
    final byte[] j;
    int k;
    int l;
    int m;
    long n;

    public eus() {
        this.g = 0;
        if (this.g == 0) {
            this.g = new SecureRandom().nextInt();
        }
        this.h = new byte[65565];
        this.i = 0;
        this.j = new byte[255];
        this.k = 0;
        this.l = 0;
        this.m = 0;
        this.n = 0;
    }

    public eus(int i2, int i3, int i4, int i5, boolean z) {
        this();
        this.b = i2;
        this.c = i3;
        this.d = i4;
        this.e = i5;
        this.f = z;
    }

    /* access modifiers changed from: 0000 */
    public final void a(boolean z) throws IOException {
        int i2 = z ? 4 : 0;
        long j2 = this.n;
        int i3 = this.g;
        int i4 = this.l;
        this.l = i4 + 1;
        byte[] a2 = a(i2, j2, i3, i4, this.m, this.j);
        a(a2, 22, eur.a(eur.a(0, a2, 0, a2.length), this.h, 0, this.i));
        if (this.a != null) {
            this.a.write(a2);
            this.a.write(this.h, 0, this.i);
        }
        this.i = 0;
        this.k = 0;
        this.m = 0;
    }
}
