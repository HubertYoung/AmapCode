package defpackage;

import java.util.List;
import java.util.Map;

/* renamed from: th reason: default package */
/* compiled from: DownloadModel */
public final class th {
    a a;
    String b;
    String c;
    bjg d;
    bjf e = new bjf() {
        public final void onProgressUpdate(long j, long j2) {
        }

        public final void onStart(long j, Map<String, List<String>> map, int i) {
        }

        public final void onFinish(bpk bpk) {
            if (th.this.a != null) {
                th.this.a.a();
            }
            bjh.a().a(th.this.c);
        }

        public final void onError(int i, int i2) {
            bjh.a().a(th.this.c);
            th.this.a.b();
        }
    };

    /* renamed from: th$a */
    /* compiled from: DownloadModel */
    public interface a {
        void a();

        void b();
    }

    public th(String str, String str2, a aVar) {
        this.b = str;
        this.c = str2;
        this.a = aVar;
    }
}
