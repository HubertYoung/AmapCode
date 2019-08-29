package defpackage;

import android.content.Context;
import com.alipay.sdk.util.h;

/* renamed from: fbe reason: default package */
/* compiled from: PushClientTask */
public abstract class fbe implements Runnable {
    private fbh a;
    public Context b;
    int c = -1;

    /* access modifiers changed from: protected */
    public abstract void a(fbh fbh);

    public fbe(fbh fbh) {
        this.a = fbh;
        this.c = fbh.f;
        if (this.c < 0) {
            throw new IllegalArgumentException("PushTask need a > 0 task id.");
        }
        this.b = ezv.a().b;
    }

    public final void run() {
        if (this.b != null && !(this.a instanceof exn)) {
            Context context = this.b;
            StringBuilder sb = new StringBuilder("[执行指令]");
            sb.append(this.a);
            fat.a(context, sb.toString());
        }
        a(this.a);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{");
        sb.append(this.a == null ? "[null]" : this.a.toString());
        sb.append(h.d);
        return sb.toString();
    }
}
