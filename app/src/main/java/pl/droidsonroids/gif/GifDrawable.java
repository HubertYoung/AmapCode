package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.widget.MediaController.MediaPlayerControl;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GifDrawable extends Drawable implements Animatable, MediaPlayerControl {
    public final Bitmap mBuffer;
    private final Rect mDstRect;
    public final ScheduledThreadPoolExecutor mExecutor;
    public final fhn mInvalidationHandler;
    public final boolean mIsRenderingTriggeredOnDraw;
    public volatile boolean mIsRunning;
    public final ConcurrentLinkedQueue<fhi> mListeners;
    public final GifInfoHandle mNativeInfoHandle;
    public long mNextFrameRenderTime;
    protected final Paint mPaint;
    private final fhr mRenderTask;
    public ScheduledFuture<?> mRenderTaskSchedule;
    private int mScaledHeight;
    private int mScaledWidth;
    private final Rect mSrcRect;
    private ColorStateList mTint;
    private PorterDuffColorFilter mTintFilter;
    private Mode mTintMode;
    private fhu mTransform;

    public boolean canPause() {
        return true;
    }

    public int getAudioSessionId() {
        return 0;
    }

    public int getBufferPercentage() {
        return 100;
    }

    public GifDrawable(@NonNull Resources resources, @RawRes @DrawableRes int i) throws NotFoundException, IOException {
        this(resources.openRawResourceFd(i));
        float a = fhl.a(resources, i);
        this.mScaledHeight = (int) (((float) this.mNativeInfoHandle.t()) * a);
        this.mScaledWidth = (int) (((float) this.mNativeInfoHandle.s()) * a);
    }

    public GifDrawable(@NonNull AssetManager assetManager, @NonNull String str) throws IOException {
        this(assetManager.openFd(str));
    }

    public GifDrawable(@NonNull String str) throws IOException {
        this(new GifInfoHandle(str), null, null, true);
    }

    public GifDrawable(@NonNull File file) throws IOException {
        this(file.getPath());
    }

    public GifDrawable(@NonNull InputStream inputStream) throws IOException {
        this(new GifInfoHandle(inputStream), null, null, true);
    }

    public GifDrawable(@NonNull AssetFileDescriptor assetFileDescriptor) throws IOException {
        this(new GifInfoHandle(assetFileDescriptor), null, null, true);
    }

    public GifDrawable(@NonNull FileDescriptor fileDescriptor) throws IOException {
        this(new GifInfoHandle(fileDescriptor), null, null, true);
    }

    public GifDrawable(@NonNull byte[] bArr) throws IOException {
        this(new GifInfoHandle(bArr), null, null, true);
    }

    public GifDrawable(@NonNull ByteBuffer byteBuffer) throws IOException {
        this(new GifInfoHandle(byteBuffer), null, null, true);
    }

    public GifDrawable(@Nullable ContentResolver contentResolver, @NonNull Uri uri) throws IOException {
        this(GifInfoHandle.a(contentResolver, uri), null, null, true);
    }

    GifDrawable(GifInfoHandle gifInfoHandle, GifDrawable gifDrawable, ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, boolean z) {
        this.mIsRunning = true;
        this.mNextFrameRenderTime = Long.MIN_VALUE;
        this.mDstRect = new Rect();
        this.mPaint = new Paint(6);
        this.mListeners = new ConcurrentLinkedQueue<>();
        this.mRenderTask = new fhr(this);
        this.mIsRenderingTriggeredOnDraw = z;
        this.mExecutor = scheduledThreadPoolExecutor == null ? fhk.a() : scheduledThreadPoolExecutor;
        this.mNativeInfoHandle = gifInfoHandle;
        Bitmap bitmap = null;
        if (gifDrawable != null) {
            synchronized (gifDrawable.mNativeInfoHandle) {
                if (!gifDrawable.mNativeInfoHandle.o() && gifDrawable.mNativeInfoHandle.t() >= this.mNativeInfoHandle.t() && gifDrawable.mNativeInfoHandle.s() >= this.mNativeInfoHandle.s()) {
                    gifDrawable.shutdown();
                    Bitmap bitmap2 = gifDrawable.mBuffer;
                    bitmap2.eraseColor(0);
                    bitmap = bitmap2;
                }
            }
        }
        if (bitmap == null) {
            this.mBuffer = Bitmap.createBitmap(this.mNativeInfoHandle.s(), this.mNativeInfoHandle.t(), Config.ARGB_8888);
        } else {
            this.mBuffer = bitmap;
        }
        if (VERSION.SDK_INT >= 12) {
            this.mBuffer.setHasAlpha(!gifInfoHandle.v());
        }
        this.mSrcRect = new Rect(0, 0, this.mNativeInfoHandle.s(), this.mNativeInfoHandle.t());
        this.mInvalidationHandler = new fhn(this);
        this.mRenderTask.a();
        this.mScaledWidth = this.mNativeInfoHandle.s();
        this.mScaledHeight = this.mNativeInfoHandle.t();
    }

    public void recycle() {
        shutdown();
        this.mBuffer.recycle();
    }

    private void shutdown() {
        this.mIsRunning = false;
        this.mInvalidationHandler.removeMessages(-1);
        this.mNativeInfoHandle.a();
    }

    public boolean isRecycled() {
        return this.mNativeInfoHandle.o();
    }

    public int getIntrinsicHeight() {
        return this.mScaledHeight;
    }

    public int getIntrinsicWidth() {
        return this.mScaledWidth;
    }

    public void setAlpha(@IntRange(from = 0, to = 255) int i) {
        this.mPaint.setAlpha(i);
    }

    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    public int getOpacity() {
        return (!this.mNativeInfoHandle.v() || this.mPaint.getAlpha() < 255) ? -2 : -1;
    }

    public void start() {
        synchronized (this) {
            if (!this.mIsRunning) {
                this.mIsRunning = true;
                startAnimation(this.mNativeInfoHandle.b());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void startAnimation(long j) {
        if (this.mIsRenderingTriggeredOnDraw) {
            this.mNextFrameRenderTime = 0;
            this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0);
            return;
        }
        cancelPendingRenderTask();
        this.mRenderTaskSchedule = this.mExecutor.schedule(this.mRenderTask, Math.max(j, 0), TimeUnit.MILLISECONDS);
    }

    public void reset() {
        this.mExecutor.execute(new fhs(this) {
            public final void a() {
                if (GifDrawable.this.mNativeInfoHandle.c()) {
                    GifDrawable.this.start();
                }
            }
        });
    }

    public void stop() {
        synchronized (this) {
            if (this.mIsRunning) {
                this.mIsRunning = false;
                cancelPendingRenderTask();
                this.mNativeInfoHandle.d();
            }
        }
    }

    private void cancelPendingRenderTask() {
        if (this.mRenderTaskSchedule != null) {
            this.mRenderTaskSchedule.cancel(false);
        }
        this.mInvalidationHandler.removeMessages(-1);
    }

    public boolean isRunning() {
        return this.mIsRunning;
    }

    @Nullable
    public String getComment() {
        return this.mNativeInfoHandle.e();
    }

    public int getLoopCount() {
        return this.mNativeInfoHandle.f();
    }

    public void setLoopCount(@IntRange(from = 0, to = 65535) int i) {
        GifInfoHandle gifInfoHandle = this.mNativeInfoHandle;
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException("Loop count of range <0, 65535>");
        }
        synchronized (gifInfoHandle) {
            GifInfoHandle.setLoopCount(gifInfoHandle.a, (char) i);
        }
    }

    public String toString() {
        return String.format(Locale.ENGLISH, "GIF: size: %dx%d, frames: %d, error: %d", new Object[]{Integer.valueOf(this.mNativeInfoHandle.s()), Integer.valueOf(this.mNativeInfoHandle.t()), Integer.valueOf(this.mNativeInfoHandle.u()), Integer.valueOf(this.mNativeInfoHandle.h())});
    }

    public int getNumberOfFrames() {
        return this.mNativeInfoHandle.u();
    }

    @NonNull
    public GifError getError() {
        return GifError.a(this.mNativeInfoHandle.h());
    }

    @Nullable
    public static GifDrawable createFromResource(@NonNull Resources resources, @RawRes @DrawableRes int i) {
        try {
            return new GifDrawable(resources, i);
        } catch (IOException unused) {
            return null;
        }
    }

    public void setSpeed(@FloatRange(from = 0.0d, fromInclusive = false) float f) {
        this.mNativeInfoHandle.a(f);
    }

    public void pause() {
        stop();
    }

    public int getDuration() {
        return this.mNativeInfoHandle.i();
    }

    public int getCurrentPosition() {
        return this.mNativeInfoHandle.j();
    }

    public void seekTo(@IntRange(from = 0, to = 2147483647L) final int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Position is not positive");
        }
        this.mExecutor.execute(new fhs(this) {
            public final void a() {
                GifDrawable.this.mNativeInfoHandle.a(i, GifDrawable.this.mBuffer);
                this.c.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0);
            }
        });
    }

    public void seekToFrame(@IntRange(from = 0, to = 2147483647L) final int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Frame index is not positive");
        }
        this.mExecutor.execute(new fhs(this) {
            public final void a() {
                GifDrawable.this.mNativeInfoHandle.b(i, GifDrawable.this.mBuffer);
                GifDrawable.this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0);
            }
        });
    }

    public Bitmap seekToFrameAndGet(@IntRange(from = 0, to = 2147483647L) int i) {
        Bitmap currentFrame;
        if (i < 0) {
            throw new IndexOutOfBoundsException("Frame index is not positive");
        }
        synchronized (this.mNativeInfoHandle) {
            this.mNativeInfoHandle.b(i, this.mBuffer);
            currentFrame = getCurrentFrame();
        }
        this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0);
        return currentFrame;
    }

    public Bitmap seekToPositionAndGet(@IntRange(from = 0, to = 2147483647L) int i) {
        Bitmap currentFrame;
        if (i < 0) {
            throw new IllegalArgumentException("Position is not positive");
        }
        synchronized (this.mNativeInfoHandle) {
            this.mNativeInfoHandle.a(i, this.mBuffer);
            currentFrame = getCurrentFrame();
        }
        this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0);
        return currentFrame;
    }

    public boolean isPlaying() {
        return this.mIsRunning;
    }

    public boolean canSeekBackward() {
        return getNumberOfFrames() > 1;
    }

    public boolean canSeekForward() {
        return getNumberOfFrames() > 1;
    }

    public int getFrameByteCount() {
        return this.mBuffer.getRowBytes() * this.mBuffer.getHeight();
    }

    public long getAllocationByteCount() {
        long m = this.mNativeInfoHandle.m();
        if (VERSION.SDK_INT >= 19) {
            return m + ((long) this.mBuffer.getAllocationByteCount());
        }
        return m + ((long) getFrameByteCount());
    }

    public long getMetadataAllocationByteCount() {
        return this.mNativeInfoHandle.n();
    }

    public long getInputSourceByteCount() {
        return this.mNativeInfoHandle.g();
    }

    public void getPixels(@NonNull int[] iArr) {
        this.mBuffer.getPixels(iArr, 0, this.mNativeInfoHandle.s(), 0, 0, this.mNativeInfoHandle.s(), this.mNativeInfoHandle.t());
    }

    public int getPixel(int i, int i2) {
        if (i >= this.mNativeInfoHandle.s()) {
            throw new IllegalArgumentException("x must be < width");
        } else if (i2 < this.mNativeInfoHandle.t()) {
            return this.mBuffer.getPixel(i, i2);
        } else {
            throw new IllegalArgumentException("y must be < height");
        }
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        this.mDstRect.set(rect);
        if (this.mTransform != null) {
            this.mTransform.a(rect);
        }
    }

    public void draw(@NonNull Canvas canvas) {
        boolean z;
        if (this.mTintFilter == null || this.mPaint.getColorFilter() != null) {
            z = false;
        } else {
            this.mPaint.setColorFilter(this.mTintFilter);
            z = true;
        }
        if (this.mTransform == null) {
            canvas.drawBitmap(this.mBuffer, this.mSrcRect, this.mDstRect, this.mPaint);
        } else {
            this.mTransform.a(canvas, this.mPaint, this.mBuffer);
        }
        if (z) {
            this.mPaint.setColorFilter(null);
        }
        if (this.mIsRenderingTriggeredOnDraw && this.mIsRunning && this.mNextFrameRenderTime != Long.MIN_VALUE) {
            long max = Math.max(0, this.mNextFrameRenderTime - SystemClock.uptimeMillis());
            this.mNextFrameRenderTime = Long.MIN_VALUE;
            this.mExecutor.remove(this.mRenderTask);
            this.mRenderTaskSchedule = this.mExecutor.schedule(this.mRenderTask, max, TimeUnit.MILLISECONDS);
        }
    }

    @NonNull
    public final Paint getPaint() {
        return this.mPaint;
    }

    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    public void setFilterBitmap(boolean z) {
        this.mPaint.setFilterBitmap(z);
        invalidateSelf();
    }

    @Deprecated
    public void setDither(boolean z) {
        this.mPaint.setDither(z);
        invalidateSelf();
    }

    public void addAnimationListener(@NonNull fhi fhi) {
        this.mListeners.add(fhi);
    }

    public boolean removeAnimationListener(fhi fhi) {
        return this.mListeners.remove(fhi);
    }

    public ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    public Bitmap getCurrentFrame() {
        Bitmap copy = this.mBuffer.copy(this.mBuffer.getConfig(), this.mBuffer.isMutable());
        if (VERSION.SDK_INT >= 12) {
            copy.setHasAlpha(this.mBuffer.hasAlpha());
        }
        return copy;
    }

    private PorterDuffColorFilter updateTintFilter(ColorStateList colorStateList, Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    public void setTintList(ColorStateList colorStateList) {
        this.mTint = colorStateList;
        this.mTintFilter = updateTintFilter(colorStateList, this.mTintMode);
        invalidateSelf();
    }

    public void setTintMode(@NonNull Mode mode) {
        this.mTintMode = mode;
        this.mTintFilter = updateTintFilter(this.mTint, mode);
        invalidateSelf();
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        if (this.mTint == null || this.mTintMode == null) {
            return false;
        }
        this.mTintFilter = updateTintFilter(this.mTint, this.mTintMode);
        return true;
    }

    public boolean isStateful() {
        return super.isStateful() || (this.mTint != null && this.mTint.isStateful());
    }

    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (!this.mIsRenderingTriggeredOnDraw) {
            if (z) {
                if (z2) {
                    reset();
                }
                if (visible) {
                    start();
                }
            } else if (visible) {
                stop();
            }
        }
        return visible;
    }

    public int getCurrentFrameIndex() {
        return this.mNativeInfoHandle.k();
    }

    public int getCurrentLoop() {
        int l = this.mNativeInfoHandle.l();
        return (l == 0 || l < this.mNativeInfoHandle.f()) ? l : l - 1;
    }

    public boolean isAnimationCompleted() {
        return this.mNativeInfoHandle.q();
    }

    public int getFrameDuration(@IntRange(from = 0) int i) {
        return this.mNativeInfoHandle.a(i);
    }

    public void setCornerRadius(@FloatRange(from = 0.0d) float f) {
        this.mTransform = new fht(f);
    }

    @FloatRange(from = 0.0d)
    public float getCornerRadius() {
        if (this.mTransform instanceof fht) {
            return ((fht) this.mTransform).a;
        }
        return 0.0f;
    }

    public void setTransform(@Nullable fhu fhu) {
        this.mTransform = fhu;
    }

    @Nullable
    public fhu getTransform() {
        return this.mTransform;
    }
}
