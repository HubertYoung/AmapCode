package org.apache.thrift.protocol;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import org.apache.thrift.f;
import org.apache.thrift.transport.d;

public class a extends e {
    private static final j f = new j();
    protected boolean a = false;
    protected boolean b = true;
    protected int c;
    protected boolean d = false;
    private byte[] g = new byte[1];
    private byte[] h = new byte[2];
    private byte[] i = new byte[4];
    private byte[] j = new byte[8];
    private byte[] k = new byte[1];
    private byte[] l = new byte[2];
    private byte[] m = new byte[4];
    private byte[] n = new byte[8];

    /* renamed from: org.apache.thrift.protocol.a$a reason: collision with other inner class name */
    public static class C0091a implements g {
        protected boolean a;
        protected boolean b;
        protected int c;

        public C0091a() {
            this(false, true);
        }

        public C0091a(boolean z, boolean z2) {
            this(z, z2, 0);
        }

        public C0091a(boolean z, boolean z2, int i) {
            this.a = false;
            this.b = true;
            this.a = z;
            this.b = z2;
            this.c = i;
        }

        public e a(d dVar) {
            a aVar = new a(dVar, this.a, this.b);
            if (this.c != 0) {
                aVar.c(this.c);
            }
            return aVar;
        }
    }

    public a(d dVar, boolean z, boolean z2) {
        super(dVar);
        this.a = z;
        this.b = z2;
    }

    private int a(byte[] bArr, int i2) {
        d(i2);
        return this.e.a(bArr, i2);
    }

    public final void a() {
        a(0);
    }

    public final void a(byte b2) {
        this.g[0] = b2;
        this.e.b(this.g, 0, 1);
    }

    public final void a(double d2) {
        a(Double.doubleToLongBits(d2));
    }

    public final void a(int i2) {
        this.i[0] = (byte) ((i2 >> 24) & 255);
        this.i[1] = (byte) ((i2 >> 16) & 255);
        this.i[2] = (byte) ((i2 >> 8) & 255);
        this.i[3] = (byte) (i2 & 255);
        this.e.b(this.i, 0, 4);
    }

    public final void a(long j2) {
        this.j[0] = (byte) ((int) ((j2 >> 56) & 255));
        this.j[1] = (byte) ((int) ((j2 >> 48) & 255));
        this.j[2] = (byte) ((int) ((j2 >> 40) & 255));
        this.j[3] = (byte) ((int) ((j2 >> 32) & 255));
        this.j[4] = (byte) ((int) ((j2 >> 24) & 255));
        this.j[5] = (byte) ((int) ((j2 >> 16) & 255));
        this.j[6] = (byte) ((int) ((j2 >> 8) & 255));
        this.j[7] = (byte) ((int) (j2 & 255));
        this.e.b(this.j, 0, 8);
    }

    public final void a(String str) {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            a(bytes.length);
            this.e.b(bytes, 0, bytes.length);
        } catch (UnsupportedEncodingException unused) {
            throw new f((String) "JVM DOES NOT SUPPORT UTF-8");
        }
    }

    public final void a(ByteBuffer byteBuffer) {
        int limit = (byteBuffer.limit() - byteBuffer.position()) - byteBuffer.arrayOffset();
        a(limit);
        this.e.b(byteBuffer.array(), byteBuffer.position() + byteBuffer.arrayOffset(), limit);
    }

    public final void a(b bVar) {
        a(bVar.b);
        a(bVar.c);
    }

    public final void a(c cVar) {
        a(cVar.a);
        a(cVar.b);
    }

    public final void a(d dVar) {
        a(dVar.a);
        a(dVar.b);
        a(dVar.c);
    }

    public final void a(i iVar) {
        a(iVar.a);
        a(iVar.b);
    }

    public final void a(short s) {
        this.h[0] = (byte) ((s >> 8) & 255);
        this.h[1] = (byte) (s & 255);
        this.e.b(this.h, 0, 2);
    }

    public final void a(boolean z) {
        a(z ? (byte) 1 : 0);
    }

    public final String b(int i2) {
        try {
            d(i2);
            byte[] bArr = new byte[i2];
            this.e.a(bArr, i2);
            return new String(bArr, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            throw new f((String) "JVM DOES NOT SUPPORT UTF-8");
        }
    }

    public final b b() {
        byte g2 = g();
        return new b("", g2, g2 == 0 ? 0 : h());
    }

    public d c() {
        return new d(g(), g(), i());
    }

    public final void c(int i2) {
        this.c = i2;
        this.d = true;
    }

    public c d() {
        return new c(g(), i());
    }

    /* access modifiers changed from: protected */
    public final void d(int i2) {
        if (i2 < 0) {
            throw new f("Negative length: ".concat(String.valueOf(i2)));
        } else if (this.d) {
            this.c -= i2;
            if (this.c < 0) {
                throw new f("Message length exceeded: ".concat(String.valueOf(i2)));
            }
        }
    }

    public i e() {
        return new i(g(), i());
    }

    public final boolean f() {
        return g() == 1;
    }

    public final byte g() {
        if (this.e.c() > 0) {
            byte b2 = this.e.a()[this.e.b()];
            this.e.a(1);
            return b2;
        }
        a(this.k, 1);
        return this.k[0];
    }

    public final short h() {
        int i2;
        byte[] bArr = this.l;
        if (this.e.c() >= 2) {
            bArr = this.e.a();
            i2 = this.e.b();
            this.e.a(2);
        } else {
            a(this.l, 2);
            i2 = 0;
        }
        return (short) ((bArr[i2 + 1] & 255) | ((bArr[i2] & 255) << 8));
    }

    public final int i() {
        int i2;
        byte[] bArr = this.m;
        if (this.e.c() >= 4) {
            bArr = this.e.a();
            i2 = this.e.b();
            this.e.a(4);
        } else {
            a(this.m, 4);
            i2 = 0;
        }
        return (bArr[i2 + 3] & 255) | ((bArr[i2] & 255) << 24) | ((bArr[i2 + 1] & 255) << 16) | ((bArr[i2 + 2] & 255) << 8);
    }

    public final long j() {
        int i2;
        byte[] bArr = this.n;
        if (this.e.c() >= 8) {
            bArr = this.e.a();
            i2 = this.e.b();
            this.e.a(8);
        } else {
            a(this.n, 8);
            i2 = 0;
        }
        return ((long) (bArr[i2 + 7] & 255)) | (((long) (bArr[i2] & 255)) << 56) | (((long) (bArr[i2 + 1] & 255)) << 48) | (((long) (bArr[i2 + 2] & 255)) << 40) | (((long) (bArr[i2 + 3] & 255)) << 32) | (((long) (bArr[i2 + 4] & 255)) << 24) | (((long) (bArr[i2 + 5] & 255)) << 16) | (((long) (bArr[i2 + 6] & 255)) << 8);
    }

    public final double k() {
        return Double.longBitsToDouble(j());
    }

    public String l() {
        int i2 = i();
        if (this.e.c() < i2) {
            return b(i2);
        }
        try {
            String str = new String(this.e.a(), this.e.b(), i2, "UTF-8");
            this.e.a(i2);
            return str;
        } catch (UnsupportedEncodingException unused) {
            throw new f((String) "JVM DOES NOT SUPPORT UTF-8");
        }
    }

    public ByteBuffer m() {
        int i2 = i();
        d(i2);
        if (this.e.c() >= i2) {
            ByteBuffer wrap = ByteBuffer.wrap(this.e.a(), this.e.b(), i2);
            this.e.a(i2);
            return wrap;
        }
        byte[] bArr = new byte[i2];
        this.e.a(bArr, i2);
        return ByteBuffer.wrap(bArr);
    }
}
