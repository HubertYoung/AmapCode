package defpackage;

import android.os.SystemClock;
import java.util.concurrent.TimeUnit;
import pl.droidsonroids.gif.GifDrawable;

/* renamed from: fhr reason: default package */
/* compiled from: RenderTask */
public final class fhr extends fhs {
    public fhr(GifDrawable gifDrawable) {
        super(gifDrawable);
    }

    public final void a() {
        long a = this.c.mNativeInfoHandle.a(this.c.mBuffer);
        if (a >= 0) {
            this.c.mNextFrameRenderTime = SystemClock.uptimeMillis() + a;
            if (this.c.isVisible() && this.c.mIsRunning && !this.c.mIsRenderingTriggeredOnDraw) {
                this.c.mExecutor.remove(this);
                this.c.mRenderTaskSchedule = this.c.mExecutor.schedule(this, a, TimeUnit.MILLISECONDS);
            }
            if (!this.c.mListeners.isEmpty() && this.c.getCurrentFrameIndex() == this.c.mNativeInfoHandle.u() - 1) {
                this.c.mInvalidationHandler.sendEmptyMessageAtTime(this.c.getCurrentLoop(), this.c.mNextFrameRenderTime);
            }
        } else {
            this.c.mNextFrameRenderTime = Long.MIN_VALUE;
            this.c.mIsRunning = false;
        }
        if (this.c.isVisible() && !this.c.mInvalidationHandler.hasMessages(-1)) {
            this.c.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0);
        }
    }
}
