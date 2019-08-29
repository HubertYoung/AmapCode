package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;

class DrawableWrapperDonut extends Drawable implements Callback, DrawableWrapper {
    static final Mode a = Mode.SRC_IN;
    Drawable b;
    private ColorStateList c;
    private Mode d = a;
    private int e;
    private Mode f;
    private boolean g;

    DrawableWrapperDonut(Drawable drawable) {
        setWrappedDrawable(drawable);
    }

    public void draw(Canvas canvas) {
        this.b.draw(canvas);
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        this.b.setBounds(rect);
    }

    public void setChangingConfigurations(int i) {
        this.b.setChangingConfigurations(i);
    }

    public int getChangingConfigurations() {
        return this.b.getChangingConfigurations();
    }

    public void setDither(boolean z) {
        this.b.setDither(z);
    }

    public void setFilterBitmap(boolean z) {
        this.b.setFilterBitmap(z);
    }

    public void setAlpha(int i) {
        this.b.setAlpha(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.b.setColorFilter(colorFilter);
    }

    public boolean isStateful() {
        return (this.c != null && this.c.isStateful()) || this.b.isStateful();
    }

    public boolean setState(int[] iArr) {
        return a(iArr) || this.b.setState(iArr);
    }

    public int[] getState() {
        return this.b.getState();
    }

    public Drawable getCurrent() {
        return this.b.getCurrent();
    }

    public boolean setVisible(boolean z, boolean z2) {
        return super.setVisible(z, z2) || this.b.setVisible(z, z2);
    }

    public int getOpacity() {
        return this.b.getOpacity();
    }

    public Region getTransparentRegion() {
        return this.b.getTransparentRegion();
    }

    public int getIntrinsicWidth() {
        return this.b.getIntrinsicWidth();
    }

    public int getIntrinsicHeight() {
        return this.b.getIntrinsicHeight();
    }

    public int getMinimumWidth() {
        return this.b.getMinimumWidth();
    }

    public int getMinimumHeight() {
        return this.b.getMinimumHeight();
    }

    public boolean getPadding(Rect rect) {
        return this.b.getPadding(rect);
    }

    public Drawable mutate() {
        Drawable drawable = this.b;
        Drawable mutate = drawable.mutate();
        if (mutate != drawable) {
            setWrappedDrawable(mutate);
        }
        return this;
    }

    public void invalidateDrawable(Drawable drawable) {
        invalidateSelf();
    }

    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        scheduleSelf(runnable, j);
    }

    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int i) {
        return this.b.setLevel(i);
    }

    public void setTint(int i) {
        setTintList(ColorStateList.valueOf(i));
    }

    public void setTintList(ColorStateList colorStateList) {
        this.c = colorStateList;
        a(getState());
    }

    public void setTintMode(Mode mode) {
        this.d = mode;
        a(getState());
    }

    private boolean a(int[] iArr) {
        if (this.c == null || this.d == null) {
            this.g = false;
            clearColorFilter();
        } else {
            int colorForState = this.c.getColorForState(iArr, this.c.getDefaultColor());
            Mode mode = this.d;
            if (!(this.g && colorForState == this.e && mode == this.f)) {
                setColorFilter(colorForState, mode);
                this.e = colorForState;
                this.f = mode;
                this.g = true;
                return true;
            }
        }
        return false;
    }

    public Drawable getWrappedDrawable() {
        return this.b;
    }

    public void setWrappedDrawable(Drawable drawable) {
        if (this.b != null) {
            this.b.setCallback(null);
        }
        this.b = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        invalidateSelf();
    }
}
