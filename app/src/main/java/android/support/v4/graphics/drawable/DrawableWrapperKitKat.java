package android.support.v4.graphics.drawable;

import android.graphics.drawable.Drawable;

class DrawableWrapperKitKat extends DrawableWrapperHoneycomb {
    DrawableWrapperKitKat(Drawable drawable) {
        super(drawable);
    }

    public void setAutoMirrored(boolean z) {
        this.b.setAutoMirrored(z);
    }

    public boolean isAutoMirrored() {
        return this.b.isAutoMirrored();
    }
}
