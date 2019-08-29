package defpackage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import java.io.IOException;

/* renamed from: bkc reason: default package */
/* compiled from: ResourceRequestHandler */
public final class bkc extends bkb {
    private final Context a;

    public bkc(Context context) {
        this.a = context;
    }

    public final boolean a(bjz bjz) {
        if (bjz.e != 0) {
            return true;
        }
        return "android.resource".equals(bjz.d.getScheme());
    }

    public final a a(bjz bjz, int i) throws IOException {
        Resources a2 = bkh.a(this.a, bjz);
        int a3 = bkh.a(a2, bjz);
        Options c = c(bjz);
        if (a(c)) {
            BitmapFactory.decodeResource(a2, a3, c);
            a(bjz.h, bjz.i, c, bjz);
        }
        return new a(BitmapFactory.decodeResource(a2, a3, c), LoadedFrom.DISK);
    }
}
