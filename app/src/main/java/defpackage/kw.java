package defpackage;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"DM_EXIT", "UW_UNCOND_WAIT", "WA_NOT_IN_LOOP"})
/* renamed from: kw reason: default package */
/* compiled from: MultidexUtil */
public class kw {
    private static volatile kw c;
    private volatile boolean a = false;
    private volatile boolean b = false;
    private a d;

    /* renamed from: kw$a */
    /* compiled from: MultidexUtil */
    public interface a {
        void a(boolean z);
    }

    private kw() {
    }

    public static kw a() {
        if (c == null) {
            synchronized (kw.class) {
                try {
                    if (c == null) {
                        c = new kw();
                    }
                }
            }
        }
        return c;
    }

    public final void a(a aVar) {
        this.d = aVar;
        if ((this.b || this.a) && this.d != null) {
            this.d.a(this.a);
        }
    }
}
