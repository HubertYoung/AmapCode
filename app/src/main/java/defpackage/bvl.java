package defpackage;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.util.LruCache;
import com.autonavi.map.search.album.utils.NativeImageLoader$2;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* renamed from: bvl reason: default package */
/* compiled from: NativeImageLoader */
public final class bvl {
    private static bvl c;
    /* access modifiers changed from: private */
    public static LinkedHashMap<String, SoftReference<Bitmap>> e;
    public ExecutorService a = Executors.newFixedThreadPool(4);
    public Handler b = new Handler();
    private LruCache<String, Bitmap> d = new LruCache<String, Bitmap>(((int) Runtime.getRuntime().maxMemory()) / 8) {
        public final /* synthetic */ void entryRemoved(boolean z, Object obj, Object obj2, Object obj3) {
            String str = (String) obj;
            Bitmap bitmap = (Bitmap) obj2;
            if (bitmap != null) {
                bvl.e.put(str, new SoftReference(bitmap));
            }
        }

        public final /* synthetic */ int sizeOf(Object obj, Object obj2) {
            Bitmap bitmap = (Bitmap) obj2;
            if (bitmap != null) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
            return 0;
        }
    };
    /* access modifiers changed from: private */
    public ExecutorService f = Executors.newFixedThreadPool(4);

    /* renamed from: bvl$a */
    /* compiled from: NativeImageLoader */
    interface a {
        void a(Bitmap bitmap);
    }

    /* renamed from: bvl$b */
    /* compiled from: NativeImageLoader */
    public interface b {
        void a();
    }

    /* renamed from: bvl$c */
    /* compiled from: NativeImageLoader */
    static class c implements Runnable {
        private String a;
        private Bitmap b;

        c(String str, Bitmap bitmap) {
            this.a = str;
            this.b = bitmap;
        }

        public final void run() {
            cby.a(this.b, bvj.c(this.a));
        }
    }

    static /* synthetic */ int a(int i, int i2, int i3) {
        return (i3 == 0 || i3 == 180) ? i : (i3 == 90 || i3 == 270) ? i2 : i;
    }

    static /* synthetic */ int b(int i, int i2, int i3) {
        return (i3 == 0 || i3 == 180) ? i2 : (i3 == 90 || i3 == 270) ? i : i2;
    }

    public static synchronized bvl a() {
        bvl bvl;
        synchronized (bvl.class) {
            try {
                if (c == null) {
                    c = new bvl();
                }
                bvl = c;
            }
        }
        return bvl;
    }

    private bvl() {
        e = new NativeImageLoader$2(this);
    }

    public final void a(String str, Bitmap bitmap) {
        if (bitmap != null) {
            synchronized (this.d) {
                this.d.put(str, bitmap);
            }
        }
    }
}
