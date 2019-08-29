package com.xiaomi.slim;

import android.text.TextUtils;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.xiaomi.push.protobuf.b.a;
import com.xiaomi.push.service.ax;
import com.xiaomi.smack.util.d;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class b {
    private static String b;
    private static long c;
    private static final byte[] f = new byte[0];
    String a;
    private a d;
    private short e;
    private byte[] g;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(d.a(5));
        sb.append("-");
        b = sb.toString();
    }

    public b() {
        this.e = 2;
        this.g = f;
        this.a = null;
        this.d = new a();
    }

    b(a aVar, short s, byte[] bArr) {
        this.e = 2;
        this.g = f;
        this.a = null;
        this.d = aVar;
        this.e = s;
        this.g = bArr;
    }

    @Deprecated
    public static b a(com.xiaomi.smack.packet.d dVar, String str) {
        int i;
        b bVar = new b();
        try {
            i = Integer.parseInt(dVar.l());
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder("Blob parse chid err ");
            sb.append(e2.getMessage());
            com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
            i = 1;
        }
        bVar.a(i);
        bVar.a(dVar.k());
        bVar.c(dVar.n());
        bVar.b(dVar.o());
        bVar.a((String) "XMLMSG", (String) null);
        try {
            bVar.a(dVar.c().getBytes("utf8"), str);
            if (TextUtils.isEmpty(str)) {
                bVar.a(3);
                return bVar;
            }
            bVar.a(2);
            bVar.a((String) "SECMSG", (String) null);
            return bVar;
        } catch (UnsupportedEncodingException e3) {
            StringBuilder sb2 = new StringBuilder("Blob setPayload err： ");
            sb2.append(e3.getMessage());
            com.xiaomi.channel.commonutils.logger.b.a(sb2.toString());
            return bVar;
        }
    }

    static b b(ByteBuffer byteBuffer) {
        try {
            ByteBuffer slice = byteBuffer.slice();
            short s = slice.getShort(0);
            short s2 = slice.getShort(2);
            int i = slice.getInt(4);
            a aVar = new a();
            aVar.b(slice.array(), slice.arrayOffset() + 8, s2);
            byte[] bArr = new byte[i];
            slice.position(s2 + 8);
            slice.get(bArr, 0, i);
            return new b(aVar, s, bArr);
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder("read Blob err :");
            sb.append(e2.getMessage());
            com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
            throw new IOException("Malformed Input");
        }
    }

    public static synchronized String g() {
        String sb;
        synchronized (b.class) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(b);
            long j = c;
            c = 1 + j;
            sb2.append(Long.toString(j));
            sb = sb2.toString();
        }
        return sb;
    }

    public String a() {
        return this.d.l();
    }

    /* access modifiers changed from: 0000 */
    public ByteBuffer a(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            byteBuffer = ByteBuffer.allocate(l());
        }
        byteBuffer.putShort(this.e);
        byteBuffer.putShort((short) this.d.a());
        byteBuffer.putInt(this.g.length);
        int position = byteBuffer.position();
        this.d.a(byteBuffer.array(), byteBuffer.arrayOffset() + position, this.d.a());
        byteBuffer.position(position + this.d.a());
        byteBuffer.put(this.g);
        return byteBuffer;
    }

    public void a(int i) {
        this.d.a(i);
    }

    public void a(long j, String str, String str2) {
        if (j != 0) {
            this.d.a(j);
        }
        if (!TextUtils.isEmpty(str)) {
            this.d.a(str);
        }
        if (!TextUtils.isEmpty(str2)) {
            this.d.b(str2);
        }
    }

    public void a(String str) {
        this.d.e(str);
    }

    public void a(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("command should not be empty");
        }
        this.d.c(str);
        this.d.p();
        if (!TextUtils.isEmpty(str2)) {
            this.d.d(str2);
        }
    }

    public void a(short s) {
        this.e = s;
    }

    public void a(byte[] bArr, String str) {
        if (!TextUtils.isEmpty(str)) {
            this.d.c(1);
            bArr = ax.a(ax.a(str, h()), bArr);
        } else {
            this.d.c(0);
        }
        this.g = bArr;
    }

    public String b() {
        return this.d.n();
    }

    public void b(String str) {
        this.a = str;
    }

    public int c() {
        return this.d.d();
    }

    public void c(String str) {
        if (!TextUtils.isEmpty(str)) {
            int indexOf = str.indexOf(AUScreenAdaptTool.PREFIX_ID);
            try {
                long parseLong = Long.parseLong(str.substring(0, indexOf));
                int indexOf2 = str.indexOf("/", indexOf);
                String substring = str.substring(indexOf + 1, indexOf2);
                String substring2 = str.substring(indexOf2 + 1);
                this.d.a(parseLong);
                this.d.a(substring);
                this.d.b(substring2);
            } catch (Exception e2) {
                StringBuilder sb = new StringBuilder("Blob parse user err ");
                sb.append(e2.getMessage());
                com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
            }
        }
    }

    public boolean d() {
        return this.d.x();
    }

    public byte[] d(String str) {
        if (this.d.u() == 1) {
            return ax.a(ax.a(str, h()), this.g);
        }
        if (this.d.u() == 0) {
            return this.g;
        }
        StringBuilder sb = new StringBuilder("unknow cipher = ");
        sb.append(this.d.u());
        com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
        return this.g;
    }

    public int e() {
        return this.d.w();
    }

    public String f() {
        return this.d.y();
    }

    public String h() {
        String q = this.d.q();
        if ("ID_NOT_AVAILABLE".equals(q)) {
            return null;
        }
        if (!this.d.r()) {
            q = g();
            this.d.e(q);
        }
        return q;
    }

    public String i() {
        return this.a;
    }

    public String j() {
        if (!this.d.g()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Long.toString(this.d.f()));
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        sb.append(this.d.h());
        sb.append("/");
        sb.append(this.d.j());
        return sb.toString();
    }

    public byte[] k() {
        return this.g;
    }

    public int l() {
        return this.d.b() + 8 + this.g.length;
    }

    public short m() {
        return this.e;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Blob [chid=");
        sb.append(c());
        sb.append("; Id=");
        sb.append(h());
        sb.append("; cmd=");
        sb.append(a());
        sb.append("; type=");
        sb.append(m());
        sb.append("; from=");
        sb.append(j());
        sb.append(" ]");
        return sb.toString();
    }
}
