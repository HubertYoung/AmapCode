package defpackage;

import com.danikula.videocache.ProxyCacheException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

/* renamed from: etl reason: default package */
/* compiled from: HttpProxyCache */
final class etl extends ets {
    final eto a;
    final ety b;
    eti c;

    public etl(eto eto, ety ety) {
        super(eto, ety);
        this.b = ety;
        this.a = eto;
    }

    /* access modifiers changed from: 0000 */
    public final void a(OutputStream outputStream, long j) throws ProxyCacheException, IOException {
        byte[] bArr = new byte[8192];
        while (true) {
            int a2 = a(bArr, j);
            if (a2 != -1) {
                outputStream.write(bArr, 0, a2);
                j += (long) a2;
            } else {
                outputStream.flush();
                return;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b(OutputStream outputStream, long j) throws ProxyCacheException, IOException {
        eto eto = new eto(this.a);
        try {
            eto.a((long) ((int) j));
            byte[] bArr = new byte[8192];
            while (true) {
                int a2 = eto.a(bArr);
                if (a2 != -1) {
                    outputStream.write(bArr, 0, a2);
                } else {
                    outputStream.flush();
                    return;
                }
            }
        } finally {
            eto.b();
        }
    }

    static String a(String str, Object... objArr) {
        return String.format(Locale.US, str, objArr);
    }

    /* access modifiers changed from: protected */
    public final void a(int i) {
        if (this.c != null) {
            this.c.a(this.b.a, i);
        }
    }
}
