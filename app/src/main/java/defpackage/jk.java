package defpackage;

import android.app.Activity;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.location.sdk.fusion.LocationParams;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

/* renamed from: jk reason: default package */
/* compiled from: DownloadModel */
public final class jk {
    final String a;
    final int b;
    final boolean c;
    public boolean d;
    int e = -1;
    public bjg f;
    public volatile jn g;
    WeakReference<Activity> h;
    js i;
    public int j;
    private final String k;
    private final String l;

    /* renamed from: jk$a */
    /* compiled from: DownloadModel */
    public class a implements bjf {
        public final void onStart(long j, Map<String, List<String>> map, int i) {
        }

        public a() {
        }

        public final void onProgressUpdate(long j, long j2) {
            int i = (int) ((((double) j) / ((double) j2)) * 100.0d);
            if (jk.this.e != i) {
                jk.this.e = i;
                final long j3 = j2;
                final long j4 = j;
                AnonymousClass1 r2 = new Runnable() {
                    public final void run() {
                        if (jk.this.g != null) {
                            jk.this.g.a(jk.this.e);
                        }
                    }
                };
                aho.a(r2);
            }
        }

        public final void onFinish(bpk bpk) {
            aho.a(new Runnable() {
                public final void run() {
                    if (jk.this.i != null) {
                        jk.this.i.d();
                    }
                    boolean z = jk.this.f != null;
                    if (z) {
                        z = new File(jk.this.f.d).exists();
                    }
                    jk.this.d = z;
                    if (!jk.this.d) {
                        a.a(a.this, new RuntimeException("file download complete, file is not exists!"));
                    } else if (jk.this.g != null) {
                        jk.this.g.a();
                    }
                    jk.this.f = null;
                }
            });
        }

        public final void onError(final int i, final int i2) {
            aho.a(new Runnable() {
                public final void run() {
                    String str;
                    StringBuilder sb = new StringBuilder("file download error, url: ");
                    sb.append(jk.this.f);
                    if (sb.toString() != null) {
                        str = jk.this.f.getUrl();
                    } else {
                        StringBuilder sb2 = new StringBuilder(", errorCode: ");
                        sb2.append(i);
                        sb2.append(", statusCode: ");
                        sb2.append(i2);
                        str = sb2.toString();
                    }
                    a.a(a.this, new RuntimeException(str));
                }
            });
        }

        static /* synthetic */ void a(a aVar, Exception exc) {
            if (jk.this.i != null) {
                jk.this.i.a(exc);
            }
            exc.printStackTrace();
            jk.this.d = false;
            if (jk.this.g != null) {
                jk.this.g.b();
            }
            jk.this.f = null;
        }
    }

    public jk(String str, String str2, String str3, int i2, boolean z, Activity activity, js jsVar) {
        this.k = str;
        this.l = str2;
        this.a = str3;
        this.b = i2;
        this.c = z;
        this.i = jsVar;
        this.h = new WeakReference<>(activity);
    }

    public final void a() {
        this.d = false;
        bjg bjg = new bjg(this.l);
        bjg.setUrl(this.k);
        bjg.b = true;
        bjg.addHeader(LocationParams.PARA_COMMON_DIU, NetworkParam.getDiu());
        bjg.addHeader(LocationParams.PARA_COMMON_ADIU, NetworkParam.getAdiu());
        this.f = bjg;
        yq.a();
        yq.a(bjg, (bjf) new a());
    }

    public final boolean b() {
        return this.f != null && !this.f.isCancelled();
    }
}
