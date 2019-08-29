package com.autonavi.minimap.ajx3.loader.picasso;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.autonavi.minimap.ajx3.image.ImageCache.Image;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso.LoadedFrom;

class ImageViewAction extends Action<ImageView> {
    Callback callback;

    ImageViewAction(Picasso picasso, ImageView imageView, Request request, int i, int i2, int i3, Drawable drawable, String str, Object obj, Callback callback2, boolean z, boolean z2, boolean z3) {
        super(picasso, imageView, request, i, i2, i3, drawable, str, obj, z, z2, z3);
        this.callback = callback2;
    }

    public void complete(Image image, LoadedFrom loadedFrom) {
        if (image == null || image.bitmap == null) {
            throw new AssertionError("Attempted to complete action with no result or no bitmap!\n".concat(String.valueOf(this)));
        }
        ImageView imageView = (ImageView) this.target.get();
        if (imageView != null) {
            LoadedFrom loadedFrom2 = loadedFrom;
            PicassoDrawable.setBitmap(imageView, this.picasso.context, image.bitmap, loadedFrom2, this.noFade, this.picasso.indicatorsEnabled);
            if (this.callback != null) {
                this.callback.onSuccess();
            }
        }
    }

    public void error() {
        ImageView imageView = (ImageView) this.target.get();
        if (imageView != null) {
            if (this.errorResId != 0) {
                imageView.setImageResource(this.errorResId);
            } else if (this.errorDrawable != null) {
                imageView.setImageDrawable(this.errorDrawable);
            }
            if (this.callback != null) {
                this.callback.onError();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void cancel() {
        super.cancel();
        if (this.callback != null) {
            this.callback = null;
        }
    }
}
