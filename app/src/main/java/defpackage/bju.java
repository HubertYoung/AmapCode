package defpackage;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;

/* renamed from: bju reason: default package */
/* compiled from: ImageViewAction */
final class bju extends bji<ImageView> {
    bjl n;

    bju(ImageLoader imageLoader, ImageView imageView, bjz bjz, int i, int i2, int i3, Drawable drawable, String str, Object obj, bjl bjl, boolean z, boolean z2) {
        super(imageLoader, imageView, bjz, i, i2, i3, drawable, str, obj, z, z2);
        this.n = bjl;
    }

    public final void a(Bitmap bitmap, LoadedFrom loadedFrom) {
        if (bitmap == null) {
            throw new AssertionError(String.format("Attempted to complete action with no result!\n%s", new Object[]{this}));
        }
        ImageView imageView = (ImageView) this.c.get();
        if (imageView != null) {
            Bitmap bitmap2 = bitmap;
            LoadedFrom loadedFrom2 = loadedFrom;
            bjx.a(imageView, this.a.d, bitmap2, loadedFrom2, this.d, this.a.m);
        }
    }

    public final void a() {
        ImageView imageView = (ImageView) this.c.get();
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AnimationDrawable) {
                ((AnimationDrawable) drawable).stop();
            }
            if (this.g != 0) {
                imageView.setImageResource(this.g);
                return;
            }
            if (this.h != null) {
                imageView.setImageDrawable(this.h);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b() {
        super.b();
        if (this.n != null) {
            this.n = null;
        }
    }
}
