package com.autonavi.widget.photoview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class PhotoView extends ImageView implements eqs {
    private final eqt mAttacher;
    private ScaleType mPendingScaleType;

    public PhotoView(Context context) {
        this(context, null);
    }

    public PhotoView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PhotoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        super.setScaleType(ScaleType.MATRIX);
        this.mAttacher = new eqt(this);
        if (this.mPendingScaleType != null) {
            setScaleType(this.mPendingScaleType);
            this.mPendingScaleType = null;
        }
    }

    public void setPhotoViewRotation(float f) {
        this.mAttacher.a(f);
    }

    public void setRotationTo(float f) {
        this.mAttacher.a(f);
    }

    public void setRotationBy(float f) {
        eqt eqt = this.mAttacher;
        eqt.h.postRotate(f % 360.0f);
        eqt.g();
    }

    public boolean canZoom() {
        return this.mAttacher.m;
    }

    public RectF getDisplayRect() {
        return this.mAttacher.b();
    }

    public Matrix getDisplayMatrix() {
        return this.mAttacher.f();
    }

    public boolean setDisplayMatrix(Matrix matrix) {
        eqt eqt = this.mAttacher;
        if (matrix == null) {
            throw new IllegalArgumentException("Matrix cannot be null");
        }
        ImageView c = eqt.c();
        if (c == null || c.getDrawable() == null) {
            return false;
        }
        eqt.h.set(matrix);
        eqt.a(eqt.f());
        eqt.h();
        return true;
    }

    @Deprecated
    public float getMinScale() {
        return getMinimumScale();
    }

    public float getMinimumScale() {
        return this.mAttacher.c;
    }

    @Deprecated
    public float getMidScale() {
        return getMediumScale();
    }

    public float getMediumScale() {
        return this.mAttacher.d;
    }

    @Deprecated
    public float getMaxScale() {
        return getMaximumScale();
    }

    public float getMaximumScale() {
        return this.mAttacher.e;
    }

    public float getScale() {
        return this.mAttacher.d();
    }

    public ScaleType getScaleType() {
        return this.mAttacher.n;
    }

    public void setAllowParentInterceptOnEdge(boolean z) {
        this.mAttacher.f = z;
    }

    @Deprecated
    public void setMinScale(float f) {
        setMinimumScale(f);
    }

    public void setMinimumScale(float f) {
        eqt eqt = this.mAttacher;
        eqt.a(f, eqt.d, eqt.e);
        eqt.c = f;
    }

    @Deprecated
    public void setMidScale(float f) {
        setMediumScale(f);
    }

    public void setMediumScale(float f) {
        eqt eqt = this.mAttacher;
        eqt.a(eqt.c, f, eqt.e);
        eqt.d = f;
    }

    @Deprecated
    public void setMaxScale(float f) {
        setMaximumScale(f);
    }

    public void setMaximumScale(float f) {
        eqt eqt = this.mAttacher;
        eqt.a(eqt.c, eqt.d, f);
        eqt.e = f;
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (this.mAttacher != null) {
            this.mAttacher.e();
        }
    }

    public void setImageResource(int i) {
        super.setImageResource(i);
        if (this.mAttacher != null) {
            this.mAttacher.e();
        }
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        if (this.mAttacher != null) {
            this.mAttacher.e();
        }
    }

    public void setOnMatrixChangeListener(c cVar) {
        this.mAttacher.i = cVar;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        this.mAttacher.l = onLongClickListener;
    }

    public void setOnPhotoTapListener(d dVar) {
        this.mAttacher.j = dVar;
    }

    public d getOnPhotoTapListener() {
        return this.mAttacher.j;
    }

    public void setOnViewTapListener(e eVar) {
        this.mAttacher.k = eVar;
    }

    public e getOnViewTapListener() {
        return this.mAttacher.k;
    }

    public void setScale(float f) {
        this.mAttacher.a(f, false);
    }

    public void setScale(float f, boolean z) {
        this.mAttacher.a(f, z);
    }

    public void setScale(float f, float f2, float f3, boolean z) {
        this.mAttacher.a(f, f2, f3, z);
    }

    public void setScaleType(ScaleType scaleType) {
        if (this.mAttacher != null) {
            eqt eqt = this.mAttacher;
            if (eqt.a(scaleType) && scaleType != eqt.n) {
                eqt.n = scaleType;
                eqt.e();
            }
            return;
        }
        this.mPendingScaleType = scaleType;
    }

    public void setZoomable(boolean z) {
        this.mAttacher.a(z);
    }

    public Bitmap getVisibleRectangleBitmap() {
        ImageView c = this.mAttacher.c();
        if (c == null) {
            return null;
        }
        return c.getDrawingCache();
    }

    public void setZoomTransitionDuration(int i) {
        eqt eqt = this.mAttacher;
        if (i < 0) {
            i = 200;
        }
        eqt.b = i;
    }

    public eqs getIPhotoViewImplementation() {
        return this.mAttacher;
    }

    public void setOnDoubleTapListener(OnDoubleTapListener onDoubleTapListener) {
        eqt eqt = this.mAttacher;
        if (onDoubleTapListener != null) {
            eqt.g.setOnDoubleTapListener(onDoubleTapListener);
        } else {
            eqt.g.setOnDoubleTapListener(new eqr(eqt));
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        this.mAttacher.a();
        super.onDetachedFromWindow();
    }
}
