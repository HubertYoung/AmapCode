package defpackage;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import java.lang.ref.WeakReference;

/* renamed from: bjt reason: default package */
/* compiled from: ImageTargetAction */
public final class bjt extends bji<ImageView> {
    final WeakReference<bjl> n = null;
    public WeakReference<bkf> o;

    public bjt(ImageLoader imageLoader, ImageView imageView, bjz bjz, int i, int i2, int i3, Drawable drawable, String str, Object obj, boolean z, boolean z2) {
        super(imageLoader, imageView, bjz, i, i2, i3, drawable, str, obj, z, z2);
    }

    /* access modifiers changed from: 0000 */
    public final void a(Bitmap bitmap, LoadedFrom loadedFrom) {
        if (bitmap == null) {
            throw new AssertionError(String.format("Attempted to complete action with no result!\n%s", new Object[]{this}));
        }
        ImageView imageView = (ImageView) this.c.get();
        if (imageView != null) {
            Bitmap bitmap2 = bitmap;
            LoadedFrom loadedFrom2 = loadedFrom;
            bjx.a(imageView, this.a.d, bitmap2, loadedFrom2, this.d, this.a.m);
            bkf bkf = null;
            if (this.o != null) {
                bkf = (bkf) this.o.get();
            }
            if (bkf != null) {
                bkf.onBitmapLoaded(bitmap, loadedFrom);
            }
            if (bitmap.isRecycled()) {
                throw new IllegalStateException("Target callback must not recycle bitmap!");
            }
            if (this.n != null) {
                this.n.get();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        ImageView imageView = (ImageView) this.c.get();
        bkf bkf = this.o != null ? (bkf) this.o.get() : null;
        if (bkf != null) {
            if (this.g != 0) {
                bkf.onBitmapFailed(this.a.d.getResources().getDrawable(this.g));
            } else {
                bkf.onBitmapFailed(this.h);
            }
        }
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AnimationDrawable) {
                ((AnimationDrawable) drawable).stop();
            }
            if (this.g != 0) {
                imageView.setImageResource(this.g);
            } else if (this.h != null) {
                imageView.setImageDrawable(this.h);
            }
        }
        if (this.n != null) {
            this.n.get();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b() {
        super.b();
    }
}
