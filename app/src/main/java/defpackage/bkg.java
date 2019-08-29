package defpackage;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;

/* renamed from: bkg reason: default package */
/* compiled from: TargetAction */
final class bkg extends bji<bkf> {
    bkg(ImageLoader imageLoader, bkf bkf, bjz bjz, int i, int i2, Drawable drawable, String str, Object obj, int i3, boolean z) {
        super(imageLoader, bkf, bjz, i, i2, i3, drawable, str, obj, false, z);
    }

    /* access modifiers changed from: 0000 */
    public final void a(Bitmap bitmap, LoadedFrom loadedFrom) {
        if (bitmap == null) {
            throw new AssertionError(String.format("Attempted to complete action with no result!\n%s", new Object[]{this}));
        }
        bkf bkf = (bkf) c();
        if (bkf != null) {
            bkf.onBitmapLoaded(bitmap, loadedFrom);
            if (bitmap.isRecycled()) {
                throw new IllegalStateException("Target callback must not recycle bitmap!");
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        bkf bkf = (bkf) c();
        if (bkf != null) {
            if (this.g != 0) {
                bkf.onBitmapFailed(this.a.d.getResources().getDrawable(this.g));
                return;
            }
            bkf.onBitmapFailed(this.h);
        }
    }
}
