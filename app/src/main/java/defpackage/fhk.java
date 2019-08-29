package defpackage;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;

/* renamed from: fhk reason: default package */
/* compiled from: GifRenderingExecutor */
public final class fhk extends ScheduledThreadPoolExecutor {

    /* renamed from: fhk$a */
    /* compiled from: GifRenderingExecutor */
    static final class a {
        /* access modifiers changed from: private */
        public static final fhk a = new fhk(0);
    }

    /* synthetic */ fhk(byte b) {
        this();
    }

    public static fhk a() {
        return a.a;
    }

    private fhk() {
        super(1, new DiscardPolicy());
    }
}
