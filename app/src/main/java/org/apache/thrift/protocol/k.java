package org.apache.thrift.protocol;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import org.apache.thrift.f;
import org.apache.thrift.protocol.a.C0091a;
import org.apache.thrift.transport.d;

public class k extends a {
    private static int f = 10000;
    private static int g = 10000;
    private static int h = 10000;
    private static int i = 10485760;
    private static int j = 104857600;

    public static class a extends C0091a {
        public a() {
            super(false, true);
        }

        public a(boolean z, boolean z2, int i) {
            super(z, z2, i);
        }

        public e a(d dVar) {
            k kVar = new k(dVar, this.a, this.b);
            if (this.c != 0) {
                kVar.c(this.c);
            }
            return kVar;
        }
    }

    public k(d dVar, boolean z, boolean z2) {
        super(dVar, z, z2);
    }

    public final d c() {
        byte g2 = g();
        byte g3 = g();
        int i2 = i();
        if (i2 <= f) {
            return new d(g2, g3, i2);
        }
        StringBuilder sb = new StringBuilder("Thrift map size ");
        sb.append(i2);
        sb.append(" out of range!");
        throw new f(3, sb.toString());
    }

    public final c d() {
        byte g2 = g();
        int i2 = i();
        if (i2 <= g) {
            return new c(g2, i2);
        }
        StringBuilder sb = new StringBuilder("Thrift list size ");
        sb.append(i2);
        sb.append(" out of range!");
        throw new f(3, sb.toString());
    }

    public final i e() {
        byte g2 = g();
        int i2 = i();
        if (i2 <= h) {
            return new i(g2, i2);
        }
        StringBuilder sb = new StringBuilder("Thrift set size ");
        sb.append(i2);
        sb.append(" out of range!");
        throw new f(3, sb.toString());
    }

    public final String l() {
        int i2 = i();
        if (i2 > i) {
            StringBuilder sb = new StringBuilder("Thrift string size ");
            sb.append(i2);
            sb.append(" out of range!");
            throw new f(3, sb.toString());
        } else if (this.e.c() < i2) {
            return b(i2);
        } else {
            try {
                String str = new String(this.e.a(), this.e.b(), i2, "UTF-8");
                this.e.a(i2);
                return str;
            } catch (UnsupportedEncodingException unused) {
                throw new f((String) "JVM DOES NOT SUPPORT UTF-8");
            }
        }
    }

    public final ByteBuffer m() {
        int i2 = i();
        if (i2 > j) {
            StringBuilder sb = new StringBuilder("Thrift binary size ");
            sb.append(i2);
            sb.append(" out of range!");
            throw new f(3, sb.toString());
        }
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
