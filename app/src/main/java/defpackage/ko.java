package defpackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import com.autonavi.common.imageloader.MemoryPolicy;
import java.io.File;
import java.lang.ref.WeakReference;

/* renamed from: ko reason: default package */
/* compiled from: AMapImageLoader */
public final class ko {
    public static void a(ImageView imageView, String str, bjo bjo, int i, bkf bkf) {
        bka bka;
        ImageView imageView2 = imageView;
        String str2 = str;
        bjo bjo2 = bjo;
        int i2 = i;
        bkf bkf2 = bkf;
        if (TextUtils.isEmpty(str)) {
            if (i2 > 0 && imageView2 != null) {
                imageView2.setImageResource(i2);
            }
            return;
        }
        if (str2.startsWith("/")) {
            File file = new File(str2);
            if (file.exists()) {
                bka = ImageLoader.a((Context) AMapAppGlobal.getApplication()).a(file);
            } else {
                return;
            }
        } else {
            bka = ImageLoader.a((Context) AMapAppGlobal.getApplication()).a(str2);
        }
        bka bka2 = bka;
        if (bjo2 != null) {
            bka2.a(bjo2);
        }
        if (i2 > 0) {
            bka2.a(i2);
        }
        if (imageView2 != null || bkf2 == null) {
            WeakReference<bkf> weakReference = null;
            if (imageView2 == null || bkf2 == null) {
                if (imageView2 != null) {
                    bka2.a(imageView2, (bjl) null);
                }
                return;
            }
            long nanoTime = System.nanoTime();
            bkh.a();
            if (imageView2 == null) {
                throw new IllegalArgumentException("Target must not be null.");
            } else if (!bka2.c()) {
                bka2.a.a((Object) imageView2);
                if (bka2.d) {
                    bjx.a(imageView2, bka2.b());
                }
            } else {
                if (bka2.c) {
                    if (bka2.d()) {
                        throw new IllegalStateException("Fit cannot be used with resize.");
                    }
                    int width = imageView.getWidth();
                    int height = imageView.getHeight();
                    if (width == 0 || height == 0) {
                        if (bka2.d) {
                            bjx.a(imageView2, bka2.b());
                        }
                        bka2.a.a(imageView2, new bjn(bka2, imageView2, null));
                        return;
                    }
                    bka2.a(width, height);
                }
                bjz a = bka2.a(nanoTime);
                String a2 = bkh.a(a);
                if (MemoryPolicy.shouldReadFromMemoryCache(bka2.f)) {
                    Bitmap b = bka2.a.b(a2);
                    if (b != null) {
                        bka2.a.a((Object) imageView2);
                        bjx.a(imageView2, bka2.a.d, b, LoadedFrom.MEMORY, bka2.b, bka2.a.m);
                        bkf2.onBitmapLoaded(b, LoadedFrom.MEMORY);
                        if (bka2.a.n) {
                            String b2 = a.b();
                            StringBuilder sb = new StringBuilder("from ");
                            sb.append(LoadedFrom.MEMORY);
                            bkh.a("Main", "completed", b2, sb.toString());
                        }
                        return;
                    }
                }
                if (bka2.d) {
                    bjx.a(imageView2, bka2.b());
                }
                bjt bjt = new bjt(bka2.a, imageView2, a, bka2.f, bka2.g, bka2.e, bka2.h, a2, bka2.i, bka2.b, bka2.j);
                if (bkf2 != null) {
                    weakReference = new WeakReference<>(bkf2);
                }
                bjt.o = weakReference;
                bka2.a.a((bji) bjt);
            }
        } else {
            bka2.a(bkf2);
        }
    }

    public static void a(ImageView imageView, String str, bjo bjo, int i) {
        a(imageView, str, bjo, i, null);
    }

    public static void a(ImageView imageView, String str) {
        a(imageView, str, null, 0, null);
    }

    public static void a(ImageView imageView, String str, int i) {
        a(imageView, str, null, i, null);
    }
}
