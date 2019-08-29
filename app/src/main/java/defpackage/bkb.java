package defpackage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.net.NetworkInfo;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: bkb reason: default package */
/* compiled from: RequestHandler */
public abstract class bkb {

    /* renamed from: bkb$a */
    /* compiled from: RequestHandler */
    public static final class a {
        public final LoadedFrom a;
        final Bitmap b;
        final InputStream c;
        final int d;
        public long e;

        public a(Bitmap bitmap, LoadedFrom loadedFrom) {
            this((Bitmap) bkh.a(bitmap, (String) "bitmap == null"), null, loadedFrom, 0);
        }

        public a(InputStream inputStream, LoadedFrom loadedFrom) {
            this(null, (InputStream) bkh.a(inputStream, (String) "stream == null"), loadedFrom, 0);
        }

        a(Bitmap bitmap, InputStream inputStream, LoadedFrom loadedFrom, int i) {
            boolean z = false;
            if (!((inputStream != null ? true : z) ^ (bitmap != null))) {
                throw new AssertionError();
            }
            this.b = bitmap;
            this.c = inputStream;
            this.a = (LoadedFrom) bkh.a(loadedFrom, (String) "loadedFrom == null");
            this.d = i;
        }
    }

    /* access modifiers changed from: protected */
    public int a() {
        return 0;
    }

    public abstract a a(bjz bjz, int i) throws IOException;

    public boolean a(NetworkInfo networkInfo) {
        return false;
    }

    public abstract boolean a(bjz bjz);

    public boolean b() {
        return false;
    }

    static Options c(bjz bjz) {
        boolean c = bjz.c();
        boolean z = bjz.r != null;
        Options options = null;
        if (c || z || bjz.q) {
            options = new Options();
            options.inJustDecodeBounds = c;
            options.inInputShareable = bjz.q;
            options.inPurgeable = bjz.q;
            if (z) {
                options.inPreferredConfig = bjz.r;
            }
        }
        return options;
    }

    static boolean a(Options options) {
        return options != null && options.inJustDecodeBounds;
    }

    static void a(int i, int i2, Options options, bjz bjz) {
        a(i, i2, options.outWidth, options.outHeight, options, bjz);
    }

    private static void a(int i, int i2, int i3, int i4, Options options, bjz bjz) {
        int i5;
        if (i4 <= i2 && i3 <= i) {
            i5 = 1;
        } else if (i2 == 0) {
            i5 = (int) Math.floor((double) (((float) i3) / ((float) i)));
        } else if (i == 0) {
            i5 = (int) Math.floor((double) (((float) i4) / ((float) i2)));
        } else {
            int floor = (int) Math.floor((double) (((float) i4) / ((float) i2)));
            int floor2 = (int) Math.floor((double) (((float) i3) / ((float) i)));
            i5 = bjz.k ? Math.max(floor, floor2) : Math.min(floor, floor2);
        }
        options.inSampleSize = i5;
        options.inJustDecodeBounds = false;
    }
}
