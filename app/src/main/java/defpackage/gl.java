package defpackage;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable.Callback;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: gl reason: default package */
/* compiled from: ImageAssetManager */
public final class gl {
    @Nullable
    public et a;
    public final Map<String, Bitmap> b = new HashMap();
    private final Context c;
    private String d;
    private final Map<String, ex> e;

    public gl(Callback callback, String str, et etVar, Map<String, ex> map) {
        this.d = str;
        if (!TextUtils.isEmpty(str) && this.d.charAt(this.d.length() - 1) != '/') {
            StringBuilder sb = new StringBuilder();
            sb.append(this.d);
            sb.append('/');
            this.d = sb.toString();
        }
        if (!(callback instanceof View)) {
            this.e = new HashMap();
            this.c = null;
            return;
        }
        this.c = ((View) callback).getContext();
        this.e = map;
        this.a = etVar;
    }

    @Nullable
    public final Bitmap a(String str) {
        Bitmap bitmap = this.b.get(str);
        if (bitmap == null) {
            ex exVar = this.e.get(str);
            if (exVar == null) {
                return null;
            }
            if (this.a != null) {
                Bitmap fetchBitmap = this.a.fetchBitmap(exVar);
                if (fetchBitmap != null) {
                    this.b.put(str, fetchBitmap);
                }
                return fetchBitmap;
            }
            try {
                if (TextUtils.isEmpty(this.d)) {
                    throw new IllegalStateException("You must set an images folder before loading an image. Set it with LottieComposition#setImagesFolder or LottieDrawable#setImagesFolder");
                }
                AssetManager assets = this.c.getAssets();
                StringBuilder sb = new StringBuilder();
                sb.append(this.d);
                sb.append(exVar.b);
                InputStream open = assets.open(sb.toString());
                Options options = new Options();
                options.inScaled = true;
                options.inDensity = 160;
                bitmap = BitmapFactory.decodeStream(open, null, options);
                this.b.put(str, bitmap);
            } catch (IOException unused) {
                return null;
            }
        }
        return bitmap;
    }

    public final void a() {
        Iterator<Entry<String, Bitmap>> it = this.b.entrySet().iterator();
        while (it.hasNext()) {
            ((Bitmap) it.next().getValue()).recycle();
            it.remove();
        }
    }

    public final boolean a(Context context) {
        return (context == null && this.c == null) || (context != null && this.c.equals(context));
    }
}
