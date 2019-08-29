package com.autonavi.minimap.ajx3.loader.picasso;

import android.graphics.drawable.Drawable;
import com.autonavi.minimap.ajx3.image.ImageCache.Image;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso.LoadedFrom;

final class TargetAction extends Action<Target> {
    TargetAction(Picasso picasso, Target target, Request request, int i, int i2, Drawable drawable, String str, Object obj, int i3, boolean z, boolean z2) {
        super(picasso, target, request, i, i2, i3, drawable, str, obj, false, z, z2);
    }

    /* access modifiers changed from: 0000 */
    public final void complete(Image image, LoadedFrom loadedFrom) {
        if (image == null) {
            throw new AssertionError("Attempted to complete action with no result and no gif!\n".concat(String.valueOf(this)));
        }
        Target target = (Target) getTarget();
        if (target != null) {
            if (image.gif != null) {
                target.onGifDrawableLoaded(image.gif, loadedFrom);
            }
            if (image.bitmap != null) {
                target.onBitmapLoaded(image.bitmap, loadedFrom);
                if (image.bitmap.isRecycled()) {
                    throw new IllegalStateException("Target callback must not recycle bitmap!");
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void error() {
        Target target = (Target) getTarget();
        if (target != null) {
            if (this.errorResId != 0) {
                target.onBitmapFailed(this.picasso.context.getResources().getDrawable(this.errorResId));
                return;
            }
            target.onBitmapFailed(this.errorDrawable);
        }
    }
}
