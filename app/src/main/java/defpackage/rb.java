package defpackage;

import android.os.CountDownTimer;
import android.widget.TextView;
import java.lang.ref.WeakReference;

/* renamed from: rb reason: default package */
/* compiled from: DelayTimerUtil */
public final class rb {
    public String a;
    public String b;
    public a c;
    public TextView d;
    public b e;
    long f = -1;

    /* renamed from: rb$a */
    /* compiled from: DelayTimerUtil */
    static class a extends CountDownTimer {
        WeakReference<rb> a;

        public a(rb rbVar, long j) {
            super(j, 1000);
            this.a = new WeakReference<>(rbVar);
        }

        public final void onFinish() {
            if (this.a != null) {
                rb rbVar = (rb) this.a.get();
                if (rbVar != null) {
                    if (rbVar.e != null) {
                        rbVar.e.a();
                    }
                    rbVar.a();
                }
            }
        }

        public final void onTick(long j) {
            if (this.a != null) {
                rb rbVar = (rb) this.a.get();
                if (rbVar != null) {
                    rbVar.d.setText(String.format(rbVar.a, new Object[]{Long.valueOf(j / 1000)}));
                    rbVar.f = j;
                }
            }
        }
    }

    /* renamed from: rb$b */
    /* compiled from: DelayTimerUtil */
    public interface b {
        void a();
    }

    public final void a(TextView textView, String str, String str2, long j, b bVar) {
        a();
        this.f = j;
        this.d = textView;
        this.a = str;
        this.b = str2;
        this.e = bVar;
        this.c = new a(this, j);
        this.c.start();
    }

    public final void a() {
        if (this.c != null) {
            this.e = null;
            this.c.cancel();
            this.c = null;
            this.d.setText(this.b);
            this.d = null;
            this.f = -1;
        }
    }
}
