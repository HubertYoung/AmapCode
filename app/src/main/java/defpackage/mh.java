package defpackage;

import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.network.util.NetworkReachability;
import java.io.File;
import java.util.List;
import java.util.Map;

/* renamed from: mh reason: default package */
/* compiled from: Downloader */
public final class mh {
    /* access modifiers changed from: private */
    public static final ahn a = new ahn(1);
    /* access modifiers changed from: private */
    public a b;
    /* access modifiers changed from: private */
    public mc c;
    /* access modifiers changed from: private */
    public boolean d;
    private bjg e;

    /* renamed from: mh$a */
    /* compiled from: Downloader */
    class a implements bjf {
        a a;

        public final void onProgressUpdate(long j, long j2) {
        }

        a(a aVar) {
            this.a = aVar;
        }

        public final void onStart(long j, Map<String, List<String>> map, int i) {
            if (bno.a) {
                String.format("download  CloudResItem onStart [moduleName: %s] [moduleUrl: %s] : ", new Object[]{mh.this.b.a, mh.this.b.b});
            }
        }

        public final void onFinish(bpk bpk) {
            if (!mh.this.d) {
                String.format("download  CloudResItem onFinish [moduleName: %s] [moduleUrl: %s] ", new Object[]{mh.this.b.a, mh.this.b.b});
                mh.a.execute(new Runnable() {
                    public final void run() {
                        a aVar = a.this;
                        try {
                            String str = mh.this.b.g;
                            if (!agy.a(new File(str), null, true).equalsIgnoreCase(mh.this.b.c)) {
                                String.format("download  CloudResItem onFinish md5 not match [moduleName: %s] [moduleUrl: %s]", new Object[]{mh.this.b.a, mh.this.b.b});
                                if (mh.this.c != null) {
                                    aVar.a(false);
                                }
                                return;
                            }
                            ahf.a(str, mh.this.b.f);
                            ahd.a(new File(str));
                            StringBuilder sb = new StringBuilder();
                            sb.append(mh.this.b.f);
                            sb.append(File.separator);
                            sb.append("item.cloudVersion");
                            FileUtil.writeTextFile(new File(sb.toString()), String.valueOf(mh.this.b.e));
                            mh.this.b.h = mh.this.b.e;
                            String.format("decompress  CloudResItem [moduleName: %s] [version: %s]", new Object[]{mh.this.b.a, Integer.valueOf(mh.this.b.h)});
                            if (mh.this.c != null) {
                                aVar.a(true);
                            }
                        } catch (Exception unused) {
                            if (bno.a) {
                                String.format("download  CloudResItem [moduleName: %s] [moduleUrl: %s] onFinish(compress exception): ", new Object[]{mh.this.b.a, mh.this.b.b});
                            }
                            if (mh.this.c != null) {
                                aVar.a(false);
                            }
                        }
                    }
                });
            }
        }

        /* access modifiers changed from: 0000 */
        public final void a(final boolean z) {
            aho.a(new Runnable() {
                public final void run() {
                    if (z) {
                        mh.this.c.a(mh.this.b.f);
                    } else {
                        mh.this.c.a();
                    }
                }
            });
        }

        public final void onError(int i, int i2) {
            if (!mh.this.d && mh.this.c != null) {
                if (i == 11 || i == 12) {
                    mh.this.c.a();
                    return;
                }
                mh.this.c.a();
            }
        }
    }

    mh(a aVar, mc mcVar) {
        this.b = aVar.clone();
        this.c = mcVar;
    }

    public final void a() {
        if (!NetworkReachability.b()) {
            if (this.c != null) {
                this.c.a();
            }
        } else if (this.b == null) {
            if (this.c != null) {
                this.c.a();
            }
        } else {
            this.e = new bjg(this.b.g);
            this.e.b = true;
            this.e.setUrl(this.b.b);
            bjh.a().a(this.b.g);
            bjh.a().a(this.e, (bjf) new a(this.b));
        }
    }
}
