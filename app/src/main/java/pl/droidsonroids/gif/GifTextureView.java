package pl.droidsonroids.gif;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.os.Parcelable;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.widget.ImageView.ScaleType;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.autonavi.widget.gif.R;
import java.io.IOException;
import java.lang.ref.WeakReference;
import pl.droidsonroids.gif.annotations.Beta;

@RequiresApi(14)
public class GifTextureView extends TextureView {
    private static final ScaleType[] sScaleTypeArray = {ScaleType.MATRIX, ScaleType.FIT_XY, ScaleType.FIT_START, ScaleType.FIT_CENTER, ScaleType.FIT_END, ScaleType.CENTER, ScaleType.CENTER_CROP, ScaleType.CENTER_INSIDE};
    private boolean mFreezesAnimation;
    /* access modifiers changed from: private */
    public fhm mInputSource;
    private b mRenderThread;
    private ScaleType mScaleType = ScaleType.FIT_CENTER;
    /* access modifiers changed from: private */
    public float mSpeedFactor = 1.0f;
    private final Matrix mTransform = new Matrix();

    /* renamed from: pl.droidsonroids.gif.GifTextureView$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[ScaleType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(16:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|(3:15|16|18)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(18:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|18) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0056 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                android.widget.ImageView$ScaleType[] r0 = android.widget.ImageView.ScaleType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0014 }
                android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.CENTER     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001f }
                android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.CENTER_CROP     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x002a }
                android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.CENTER_INSIDE     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0035 }
                android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.FIT_CENTER     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0040 }
                android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.FIT_END     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x004b }
                android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.FIT_START     // Catch:{ NoSuchFieldError -> 0x004b }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x004b }
            L_0x004b:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0056 }
                android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.FIT_XY     // Catch:{ NoSuchFieldError -> 0x0056 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0056 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0056 }
            L_0x0056:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0062 }
                android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.MATRIX     // Catch:{ NoSuchFieldError -> 0x0062 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0062 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0062 }
            L_0x0062:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: pl.droidsonroids.gif.GifTextureView.AnonymousClass1.<clinit>():void");
        }
    }

    @Beta
    public interface a {
    }

    static class b extends Thread implements SurfaceTextureListener {
        final fhj a = new fhj();
        long[] b;
        /* access modifiers changed from: private */
        public GifInfoHandle c = new GifInfoHandle();
        /* access modifiers changed from: private */
        public IOException d;
        private final WeakReference<GifTextureView> e;

        public final void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        }

        public final void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

        b(GifTextureView gifTextureView) {
            super("GifRenderThread");
            this.e = new WeakReference<>(gifTextureView);
        }

        public final void run() {
            try {
                GifTextureView gifTextureView = (GifTextureView) this.e.get();
                if (gifTextureView != null) {
                    this.c = gifTextureView.mInputSource.a();
                    GifInfoHandle gifInfoHandle = this.c;
                    GifInfoHandle.setOptions(gifInfoHandle.a, 1, gifTextureView.isOpaque());
                    final GifTextureView gifTextureView2 = (GifTextureView) this.e.get();
                    if (gifTextureView2 == null) {
                        this.c.a();
                        return;
                    }
                    gifTextureView2.setSuperSurfaceTextureListener(this);
                    boolean isAvailable = gifTextureView2.isAvailable();
                    this.a.a(isAvailable);
                    if (isAvailable) {
                        gifTextureView2.post(new Runnable() {
                            public final void run() {
                                gifTextureView2.updateTextureViewSize(b.this.c);
                            }
                        });
                    }
                    this.c.a(gifTextureView2.mSpeedFactor);
                    while (!isInterrupted()) {
                        try {
                            this.a.c();
                            SurfaceTexture surfaceTexture = gifTextureView2.getSurfaceTexture();
                            if (surfaceTexture != null) {
                                Surface surface = new Surface(surfaceTexture);
                                try {
                                    GifInfoHandle gifInfoHandle2 = this.c;
                                    GifInfoHandle.bindSurface(gifInfoHandle2.a, surface, this.b);
                                } finally {
                                    surface.release();
                                    surfaceTexture.release();
                                }
                            }
                        } catch (InterruptedException unused) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    this.c.a();
                    this.c = new GifInfoHandle();
                }
            } catch (IOException e2) {
                this.d = e2;
            }
        }

        public final void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
            GifTextureView gifTextureView = (GifTextureView) this.e.get();
            if (gifTextureView != null) {
                gifTextureView.updateTextureViewSize(this.c);
            }
            this.a.a();
        }

        public final boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            this.a.b();
            this.c.p();
            return false;
        }

        /* access modifiers changed from: 0000 */
        public final void a(@NonNull GifTextureView gifTextureView, @Nullable a aVar) {
            this.a.b();
            gifTextureView.setSuperSurfaceTextureListener(aVar != null ? new fhp(aVar) : null);
            this.c.p();
            interrupt();
        }
    }

    public SurfaceTextureListener getSurfaceTextureListener() {
        return null;
    }

    public GifTextureView(Context context) {
        super(context);
        init(null, 0, 0);
    }

    public GifTextureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet, 0, 0);
    }

    public GifTextureView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet, i, 0);
    }

    @RequiresApi(21)
    public GifTextureView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init(attributeSet, i, i2);
    }

    private void init(AttributeSet attributeSet, int i, int i2) {
        if (attributeSet != null) {
            int attributeIntValue = attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "scaleType", -1);
            if (attributeIntValue >= 0 && attributeIntValue < sScaleTypeArray.length) {
                this.mScaleType = sScaleTypeArray[attributeIntValue];
            }
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.GifTextureView, i, i2);
            this.mInputSource = findSource(obtainStyledAttributes);
            super.setOpaque(obtainStyledAttributes.getBoolean(R.styleable.GifTextureView_isOpaque, false));
            obtainStyledAttributes.recycle();
            this.mFreezesAnimation = fhl.a((View) this, attributeSet, i, i2);
        } else {
            super.setOpaque(false);
        }
        if (!isInEditMode()) {
            this.mRenderThread = new b(this);
            if (this.mInputSource != null) {
                this.mRenderThread.start();
            }
        }
    }

    public void setSurfaceTextureListener(SurfaceTextureListener surfaceTextureListener) {
        throw new UnsupportedOperationException("Changing SurfaceTextureListener is not supported");
    }

    public void setSurfaceTexture(SurfaceTexture surfaceTexture) {
        throw new UnsupportedOperationException("Changing SurfaceTexture is not supported");
    }

    private static fhm findSource(TypedArray typedArray) {
        TypedValue typedValue = new TypedValue();
        if (!typedArray.getValue(R.styleable.GifTextureView_gifSource, typedValue)) {
            return null;
        }
        if (typedValue.resourceId != 0) {
            String resourceTypeName = typedArray.getResources().getResourceTypeName(typedValue.resourceId);
            if (fhl.a.contains(resourceTypeName)) {
                return new defpackage.fhm.b(typedArray.getResources(), typedValue.resourceId);
            }
            if (!ResUtils.STRING.equals(resourceTypeName)) {
                StringBuilder sb = new StringBuilder("Expected string, drawable, mipmap or raw resource type. '");
                sb.append(resourceTypeName);
                sb.append("' is not supported");
                throw new IllegalArgumentException(sb.toString());
            }
        }
        return new defpackage.fhm.a(typedArray.getResources().getAssets(), typedValue.string.toString());
    }

    /* access modifiers changed from: private */
    public void setSuperSurfaceTextureListener(SurfaceTextureListener surfaceTextureListener) {
        super.setSurfaceTextureListener(surfaceTextureListener);
    }

    public void setOpaque(boolean z) {
        if (z != isOpaque()) {
            super.setOpaque(z);
            setInputSource(this.mInputSource);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        this.mRenderThread.a(this, null);
        super.onDetachedFromWindow();
        SurfaceTexture surfaceTexture = getSurfaceTexture();
        if (surfaceTexture != null) {
            surfaceTexture.release();
        }
    }

    public synchronized void setInputSource(@Nullable fhm fhm) {
        setInputSource(fhm, null);
    }

    @Beta
    public synchronized void setInputSource(@Nullable fhm fhm, @Nullable a aVar) {
        this.mRenderThread.a(this, aVar);
        this.mInputSource = fhm;
        this.mRenderThread = new b(this);
        if (fhm != null) {
            this.mRenderThread.start();
        }
    }

    public void setSpeed(@FloatRange(from = 0.0d, fromInclusive = false) float f) {
        this.mSpeedFactor = f;
        this.mRenderThread.c.a(f);
    }

    @Nullable
    public IOException getIOException() {
        if (this.mRenderThread.d != null) {
            return this.mRenderThread.d;
        }
        return GifIOException.a(this.mRenderThread.c.h());
    }

    public void setScaleType(@NonNull ScaleType scaleType) {
        this.mScaleType = scaleType;
        updateTextureViewSize(this.mRenderThread.c);
    }

    public ScaleType getScaleType() {
        return this.mScaleType;
    }

    /* access modifiers changed from: private */
    public void updateTextureViewSize(GifInfoHandle gifInfoHandle) {
        Matrix matrix = new Matrix();
        float width = (float) getWidth();
        float height = (float) getHeight();
        float s = ((float) gifInfoHandle.s()) / width;
        float t = ((float) gifInfoHandle.t()) / height;
        RectF rectF = new RectF(0.0f, 0.0f, (float) gifInfoHandle.s(), (float) gifInfoHandle.t());
        RectF rectF2 = new RectF(0.0f, 0.0f, width, height);
        float f = 1.0f;
        switch (AnonymousClass1.a[this.mScaleType.ordinal()]) {
            case 1:
                matrix.setScale(s, t, width / 2.0f, height / 2.0f);
                break;
            case 2:
                float min = 1.0f / Math.min(s, t);
                matrix.setScale(s * min, min * t, width / 2.0f, height / 2.0f);
                break;
            case 3:
                if (((float) gifInfoHandle.s()) > width || ((float) gifInfoHandle.t()) > height) {
                    f = Math.min(1.0f / s, 1.0f / t);
                }
                matrix.setScale(s * f, f * t, width / 2.0f, height / 2.0f);
                break;
            case 4:
                matrix.setRectToRect(rectF, rectF2, ScaleToFit.CENTER);
                matrix.preScale(s, t);
                break;
            case 5:
                matrix.setRectToRect(rectF, rectF2, ScaleToFit.END);
                matrix.preScale(s, t);
                break;
            case 6:
                matrix.setRectToRect(rectF, rectF2, ScaleToFit.START);
                matrix.preScale(s, t);
                break;
            case 7:
                return;
            case 8:
                matrix.set(this.mTransform);
                matrix.preScale(s, t);
                break;
        }
        super.setTransform(matrix);
    }

    public void setImageMatrix(Matrix matrix) {
        setTransform(matrix);
    }

    public void setTransform(Matrix matrix) {
        this.mTransform.set(matrix);
        updateTextureViewSize(this.mRenderThread.c);
    }

    public Matrix getTransform(Matrix matrix) {
        if (matrix == null) {
            matrix = new Matrix();
        }
        matrix.set(this.mTransform);
        return matrix;
    }

    public Parcelable onSaveInstanceState() {
        this.mRenderThread.b = this.mRenderThread.c.r();
        return new GifViewSavedState(super.onSaveInstanceState(), this.mFreezesAnimation ? this.mRenderThread.b : null);
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof GifViewSavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        GifViewSavedState gifViewSavedState = (GifViewSavedState) parcelable;
        super.onRestoreInstanceState(gifViewSavedState.getSuperState());
        this.mRenderThread.b = gifViewSavedState.a[0];
    }

    public void setFreezesAnimation(boolean z) {
        this.mFreezesAnimation = z;
    }
}
