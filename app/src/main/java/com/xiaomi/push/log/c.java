package com.xiaomi.push.log;

import java.io.File;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

class c extends C0080b {
    File a;
    final /* synthetic */ int b;
    final /* synthetic */ Date c;
    final /* synthetic */ Date d;
    final /* synthetic */ String e;
    final /* synthetic */ String f;
    final /* synthetic */ boolean g;
    final /* synthetic */ b h;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    c(b bVar, int i, Date date, Date date2, String str, String str2, boolean z) {
        // this.h = bVar;
        // this.b = i;
        // this.c = date;
        // this.d = date2;
        // this.e = str;
        // this.f = str2;
        // this.g = z;
        super();
    }

    public void b() {
        if (com.xiaomi.channel.commonutils.file.c.d()) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(this.h.b.getExternalFilesDir(null));
                sb.append("/.logcache");
                File file = new File(sb.toString());
                file.mkdirs();
                if (file.isDirectory()) {
                    a aVar = new a();
                    aVar.a(this.b);
                    this.a = aVar.a(this.h.b, this.c, this.d, file);
                }
            } catch (NullPointerException unused) {
            }
        }
    }

    public void c() {
        if (this.a != null && this.a.exists()) {
            ConcurrentLinkedQueue b2 = this.h.a;
            c cVar = new c(this.e, this.f, this.a, this.g);
            b2.add(cVar);
        }
        this.h.a(0);
    }
}
