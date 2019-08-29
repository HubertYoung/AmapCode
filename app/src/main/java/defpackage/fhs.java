package defpackage;

import java.lang.Thread.UncaughtExceptionHandler;
import pl.droidsonroids.gif.GifDrawable;

/* renamed from: fhs reason: default package */
/* compiled from: SafeRunnable */
public abstract class fhs implements Runnable {
    protected final GifDrawable c;

    /* access modifiers changed from: protected */
    public abstract void a();

    protected fhs(GifDrawable gifDrawable) {
        this.c = gifDrawable;
    }

    public final void run() {
        try {
            if (!this.c.isRecycled()) {
                a();
            }
        } catch (Throwable th) {
            UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
            if (defaultUncaughtExceptionHandler != null) {
                defaultUncaughtExceptionHandler.uncaughtException(Thread.currentThread(), th);
            }
            throw th;
        }
    }
}
