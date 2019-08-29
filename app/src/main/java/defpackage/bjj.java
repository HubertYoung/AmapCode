package defpackage;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import com.autonavi.minimap.ajx3.loader.AjxAssetLoader;
import java.io.IOException;

/* renamed from: bjj reason: default package */
/* compiled from: AssetRequestHandler */
public final class bjj extends bkb {
    private static final int a = 22;
    private final AssetManager b;

    public bjj(Context context) {
        this.b = context.getAssets();
    }

    public final boolean a(bjz bjz) {
        Uri uri = bjz.d;
        if (!"file".equals(uri.getScheme()) || uri.getPathSegments().isEmpty() || !AjxAssetLoader.ANDROID_ASSET.equals(uri.getPathSegments().get(0))) {
            return false;
        }
        return true;
    }

    public final a a(bjz bjz, int i) throws IOException {
        return new a(this.b.open(bjz.d.toString().substring(a)), LoadedFrom.DISK);
    }
}
