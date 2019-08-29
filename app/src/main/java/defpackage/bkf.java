package defpackage;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;

/* renamed from: bkf reason: default package */
/* compiled from: Target */
public interface bkf {
    void onBitmapFailed(Drawable drawable);

    void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom);

    void onPrepareLoad(Drawable drawable);
}
