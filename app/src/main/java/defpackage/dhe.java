package defpackage;

import java.util.Timer;
import java.util.TimerTask;

/* renamed from: dhe reason: default package */
/* compiled from: TimerOutManager */
public final class dhe {
    public a a;
    public Timer b;
    public b c;

    /* renamed from: dhe$a */
    /* compiled from: TimerOutManager */
    public interface a {
        void onResult();
    }

    /* renamed from: dhe$b */
    /* compiled from: TimerOutManager */
    public class b extends TimerTask {
        private b() {
        }

        public /* synthetic */ b(dhe dhe, byte b) {
            this();
        }

        public final void run() {
            if (dhe.this.a != null) {
                dhe.this.a.onResult();
            }
        }
    }

    public final void a() {
        if (this.b != null) {
            this.b.cancel();
            this.b = null;
        }
        if (this.c != null) {
            this.c.cancel();
            this.c = null;
        }
        dhb.a("TimerOutManager", "cancel timer..");
    }
}
