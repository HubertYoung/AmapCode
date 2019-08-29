package com.xiaomi.measite.smack;

import com.xiaomi.channel.commonutils.android.j;
import com.xiaomi.slim.b;
import com.xiaomi.smack.d;
import com.xiaomi.smack.f;
import java.text.SimpleDateFormat;
import java.util.Date;

public class a implements com.xiaomi.smack.debugger.a {
    public static boolean a;
    /* access modifiers changed from: private */
    public SimpleDateFormat b = new SimpleDateFormat("hh:mm:ss aaa");
    /* access modifiers changed from: private */
    public com.xiaomi.smack.a c = null;
    private C0076a d = null;
    private C0076a e = null;
    private d f = null;
    private final String g = "[Slim] ";

    /* renamed from: com.xiaomi.measite.smack.a$a reason: collision with other inner class name */
    class C0076a implements f, com.xiaomi.smack.filter.a {
        String a;

        C0076a(boolean z) {
            this.a = z ? " RCV " : " Sent ";
        }

        public void a(b bVar) {
            StringBuilder sb;
            String str;
            if (a.a) {
                sb = new StringBuilder("[Slim] ");
                sb.append(a.this.b.format(new Date()));
                sb.append(this.a);
                str = bVar.toString();
            } else {
                sb = new StringBuilder("[Slim] ");
                sb.append(a.this.b.format(new Date()));
                sb.append(this.a);
                sb.append(" Blob [");
                sb.append(bVar.a());
                sb.append(",");
                sb.append(bVar.c());
                sb.append(",");
                sb.append(bVar.h());
                str = "]";
            }
            sb.append(str);
            com.xiaomi.channel.commonutils.logger.b.c(sb.toString());
        }

        public boolean a(com.xiaomi.smack.packet.d dVar) {
            return true;
        }

        public void b(com.xiaomi.smack.packet.d dVar) {
            StringBuilder sb;
            String str;
            if (a.a) {
                sb = new StringBuilder("[Slim] ");
                sb.append(a.this.b.format(new Date()));
                sb.append(this.a);
                sb.append(" PKT ");
                str = dVar.c();
            } else {
                sb = new StringBuilder("[Slim] ");
                sb.append(a.this.b.format(new Date()));
                sb.append(this.a);
                sb.append(" PKT [");
                sb.append(dVar.l());
                sb.append(",");
                sb.append(dVar.k());
                str = "]";
            }
            sb.append(str);
            com.xiaomi.channel.commonutils.logger.b.c(sb.toString());
        }
    }

    static {
        boolean z = true;
        if (j.c() != 1) {
            z = false;
        }
        a = z;
    }

    public a(com.xiaomi.smack.a aVar) {
        this.c = aVar;
        a();
    }

    private void a() {
        this.d = new C0076a(true);
        this.e = new C0076a(false);
        this.c.a((f) this.d, (com.xiaomi.smack.filter.a) this.d);
        this.c.b((f) this.e, (com.xiaomi.smack.filter.a) this.e);
        this.f = new b(this);
    }
}
