package com.xiaomi.xmpush.thrift;

import android.content.Context;
import com.xiaomi.channel.commonutils.android.a;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.misc.g;
import com.xiaomi.push.service.bi;
import org.apache.thrift.e;
import org.apache.thrift.f;
import org.apache.thrift.protocol.a.C0091a;
import org.apache.thrift.protocol.k;
import org.apache.thrift.transport.c;

public class au {
    public static short a(Context context, af afVar) {
        int i = 0;
        int a = a.c(context, afVar.f).a() + 0 + (g.b(context) ? 4 : 0) + (g.a(context) ? 8 : 0);
        if (bi.a(context, afVar)) {
            i = 16;
        }
        return (short) (a + i);
    }

    public static short a(boolean z, boolean z2, boolean z3) {
        int i = 0;
        int i2 = (z ? 4 : 0) + 0;
        if (z2) {
            i = 2;
        }
        return (short) (i2 + i + (z3 ? 1 : 0));
    }

    public static <T extends org.apache.thrift.a<T, ?>> byte[] a(T t) {
        if (t == null) {
            return null;
        }
        try {
            org.apache.thrift.g gVar = new org.apache.thrift.g(new C0091a());
            gVar.a.reset();
            t.b(gVar.b);
            return gVar.a.toByteArray();
        } catch (f e) {
            b.a((String) "convertThriftObjectToBytes catch TException.", (Throwable) e);
            return null;
        }
    }

    public static <T extends org.apache.thrift.a<T, ?>> void a(T t, byte[] bArr) {
        if (bArr == null) {
            throw new f((String) "the message byte is empty.");
        }
        e eVar = new e(new k.a(true, true, bArr.length));
        c cVar = eVar.b;
        int length = bArr.length;
        cVar.a = bArr;
        cVar.b = 0;
        cVar.c = length + 0;
        t.a(eVar.a);
    }
}
