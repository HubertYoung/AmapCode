package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.squareup.picasso.Picasso.LoadedFrom;

class ImageViewAction extends Action<ImageView> {
    Callback callback;

    ImageViewAction(Picasso picasso, ImageView imageView, Request request, int i, int i2, int i3, Drawable drawable, String str, Object obj, Callback callback2, boolean z) {
        super(picasso, imageView, request, i, i2, i3, drawable, str, obj, z);
        this.callback = callback2;
    }

    public void complete(Bitmap bitmap, LoadedFrom loadedFrom) {
        if (bitmap == null) {
            throw new AssertionError(String.format("Attempted to complete action with no result!\n%s", new Object[]{this}));
        }
        ImageView imageView = (ImageView) this.target.get();
        if (imageView != null) {
            Bitmap bitmap2 = bitmap;
            LoadedFrom loadedFrom2 = loadedFrom;
            PicassoDrawable.setBitmap(imageView, this.picasso.context, bitmap2, loadedFrom2, this.noFade, this.picasso.indicatorsEnabled);
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
