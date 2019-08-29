package defpackage;

import android.os.Handler;
import android.os.Looper;
import com.amap.bundle.logs.AMapLog;

/* renamed from: avv reason: default package */
/* compiled from: ScenicCallbackDispatacher */
public class avv {
    /* access modifiers changed from: private */
    public static final String a = "avv";

    /* renamed from: avv$a */
    /* compiled from: ScenicCallbackDispatacher */
    static class a implements amw {
        int a;
        C0057a b;
        C0057a c;
        boolean d;
        amv e;
        private Handler f;
        private Runnable g;

        /* renamed from: avv$a$a reason: collision with other inner class name */
        /* compiled from: ScenicCallbackDispatacher */
        class C0057a {
            amv a;
            amw b;

            private C0057a() {
            }

            /* synthetic */ C0057a(a aVar, byte b2) {
                this();
            }
        }

        private a() {
            this.b = new C0057a(this, 0);
            this.c = new C0057a(this, 0);
            this.f = new Handler(Looper.getMainLooper());
        }

        /* synthetic */ a(byte b2) {
            this();
        }

        public final void a(int i, amv amv) {
            if (bno.a) {
                String e2 = avv.a;
                StringBuilder sb = new StringBuilder("ScenicCallbackDispatcher.onScenicActive: engineId=");
                sb.append(i);
                sb.append("; info=");
                sb.append(amv);
                AMapLog.i(e2, sb.toString(), true);
            }
            if (i == brx.d) {
                this.a = i;
                if (this.g != null) {
                    this.f.removeCallbacks(this.g);
                }
                if (!this.d) {
                    this.e = amv;
                    if (bno.a) {
                        String e3 = avv.a;
                        StringBuilder sb2 = new StringBuilder("ScenicCallbackDispatcher.onScenicActive: Cached. info=");
                        sb2.append(this.e);
                        AMapLog.i(e3, sb2.toString(), true);
                    }
                } else {
                    a(amv);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public final void a(amv amv) {
            if (bno.a) {
                if (amv != null) {
                    String e2 = avv.a;
                    StringBuilder sb = new StringBuilder("ScenicCallbackDispatcher.onScenicActiveImpl: info.mAoiId=");
                    sb.append(amv.a);
                    sb.append(", info.mHasScenicHDMap=");
                    sb.append(amv.d);
                    sb.append(", info.mHasGuideMap=");
                    sb.append(amv.c);
                    AMapLog.i(e2, sb.toString(), true);
                } else {
                    AMapLog.i(avv.a, "ScenicCallbackDispatcher.onScenicActiveImpl: info is null", true);
                }
            }
            if (b(amv)) {
                this.b.a = null;
                this.c.a = amv;
            } else {
                this.b.a = amv;
                this.c.a = null;
            }
            if (this.b.b != null || this.c.b != null) {
                this.g = new Runnable() {
                    public final void run() {
                        a.a(a.this, a.this.b);
                        a.a(a.this, a.this.c);
                    }
                };
                this.f.postDelayed(this.g, 200);
            }
        }

        private static boolean b(amv amv) {
            return amv != null && amv.d;
        }

        static /* synthetic */ void a(a aVar, C0057a aVar2) {
            if (aVar2.b != null) {
                aVar2.b.a(aVar.a, aVar2.a);
            }
        }
    }

    /* renamed from: avv$b */
    /* compiled from: ScenicCallbackDispatacher */
    static class b {
        public static a a = new a(0);
    }

    public static amw a() {
        return b.a;
    }

    public static void b() {
        b.a.a = -1;
        b.a.b.a = null;
        b.a.c.a = null;
    }

    public static void a(amw amw) {
        b.a.c.b = amw;
    }

    public static void c() {
        b.a.c.b = null;
    }

    public static void a(boolean z) {
        b.a.d = z;
    }

    public static void d() {
        a aVar = b.a;
        if (!aVar.d) {
            aVar.a(aVar.e);
            aVar.e = null;
        }
    }
}
