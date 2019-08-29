package com.xiaomi.slim;

import android.os.Build;
import android.os.Build.VERSION;
import com.alipay.sdk.util.h;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.protobuf.b.C0082b;
import com.xiaomi.push.protobuf.b.e;
import com.xiaomi.push.service.ax;
import com.xiaomi.push.service.ba;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.Adler32;

public class d {
    ByteBuffer a = ByteBuffer.allocate(2048);
    private ByteBuffer b = ByteBuffer.allocate(4);
    private Adler32 c = new Adler32();
    private f d;
    private OutputStream e;
    private int f;
    private int g;
    private byte[] h;

    d(OutputStream outputStream, f fVar) {
        this.e = new BufferedOutputStream(outputStream);
        this.d = fVar;
        TimeZone timeZone = TimeZone.getDefault();
        this.f = timeZone.getRawOffset() / 3600000;
        this.g = timeZone.useDaylightTime() ? 1 : 0;
    }

    public int a(b bVar) {
        int l = bVar.l();
        if (l > 32768) {
            StringBuilder sb = new StringBuilder("Blob size=");
            sb.append(l);
            sb.append(" should be less than 32768 Drop blob chid=");
            sb.append(bVar.c());
            sb.append(" id=");
            sb.append(bVar.h());
            b.a(sb.toString());
            return 0;
        }
        this.a.clear();
        int i = l + 8 + 4;
        if (i > this.a.capacity() || this.a.capacity() > 4096) {
            this.a = ByteBuffer.allocate(i);
        }
        this.a.putShort(-15618);
        this.a.putShort(5);
        this.a.putInt(l);
        int position = this.a.position();
        this.a = bVar.a(this.a);
        if (!"CONN".equals(bVar.a())) {
            if (this.h == null) {
                this.h = this.d.a();
            }
            ax.a(this.h, this.a.array(), true, position, l);
        }
        this.c.reset();
        this.c.update(this.a.array(), 0, this.a.position());
        this.b.putInt(0, (int) this.c.getValue());
        this.e.write(this.a.array(), 0, this.a.position());
        this.e.write(this.b.array(), 0, 4);
        this.e.flush();
        int position2 = this.a.position() + 4;
        StringBuilder sb2 = new StringBuilder("[Slim] Wrote {cmd=");
        sb2.append(bVar.a());
        sb2.append(";chid=");
        sb2.append(bVar.c());
        sb2.append(";len=");
        sb2.append(position2);
        sb2.append(h.d);
        b.c(sb2.toString());
        return position2;
    }

    public void a() {
        e eVar = new e();
        eVar.a(106);
        eVar.a(Build.MODEL);
        eVar.b(VERSION.INCREMENTAL);
        eVar.c(ba.e());
        eVar.b(36);
        eVar.d(this.d.f());
        eVar.e(this.d.e());
        eVar.f(Locale.getDefault().toString());
        eVar.c(VERSION.SDK_INT);
        byte[] a2 = this.d.d().a();
        if (a2 != null) {
            eVar.a(C0082b.b(a2));
        }
        b bVar = new b();
        bVar.a(0);
        bVar.a((String) "CONN", (String) null);
        bVar.a(0, "xiaomi.com", null);
        bVar.a(eVar.c(), (String) null);
        a(bVar);
        StringBuilder sb = new StringBuilder("[slim] open conn: andver=");
        sb.append(VERSION.SDK_INT);
        sb.append(" sdk=36 hash=");
        sb.append(ba.e());
        sb.append(" tz=");
        sb.append(this.f);
        sb.append(":");
        sb.append(this.g);
        sb.append(" Model=");
        sb.append(Build.MODEL);
        sb.append(" os=");
        sb.append(VERSION.INCREMENTAL);
        b.a(sb.toString());
    }

    public void b() {
        b bVar = new b();
        bVar.a((String) "CLOSE", (String) null);
        a(bVar);
        this.e.close();
    }
}
