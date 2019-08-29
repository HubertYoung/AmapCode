package defpackage;

import android.view.Choreographer;
import android.view.Choreographer.FrameCallback;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import java.util.concurrent.TimeUnit;

/* renamed from: emh reason: default package */
/* compiled from: FPSSamplerAction */
public final class emh extends emf implements FrameCallback {
    private emq a;
    private Choreographer b = Choreographer.getInstance();
    private long c;
    private int d;
    private int e;

    public emh(emq emq) {
        this.a = emq;
        this.b.postFrameCallback(this);
    }

    public final void a() {
        if (this.a != null) {
            if (this.e < 30) {
                this.a.a(LogItem.MM_C13_K4_FPS, String.valueOf(this.e), false);
                return;
            }
            this.a.a(LogItem.MM_C13_K4_FPS, String.valueOf(this.e), true);
        }
    }

    public final void doFrame(long j) {
        long millis = TimeUnit.NANOSECONDS.toMillis(j);
        if (this.c > 0) {
            long j2 = millis - this.c;
            this.d++;
            if (j2 >= 1000) {
                this.e = (int) (((long) (this.d * 1000)) / j2);
                this.c = millis;
                this.d = 0;
            }
        } else {
            this.c = millis;
        }
        this.b.postFrameCallback(this);
    }
}
