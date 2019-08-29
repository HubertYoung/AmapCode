package defpackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.text.TextUtils;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressFBWarnings({"UW_UNCOND_WAIT", "WA_NOT_IN_LOOP"})
/* renamed from: cjk reason: default package */
/* compiled from: IconLoader */
public final class cjk {
    b a;
    c b;

    /* renamed from: cjk$a */
    /* compiled from: IconLoader */
    static class a extends kr {
        private int a;

        /* synthetic */ a(int i, byte b) {
            this(i);
        }

        private a(int i) {
            this.a = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj == null || getClass() != obj.getClass() || this.a == ((a) obj).a) ? false : true;
        }

        public final int hashCode() {
            return this.a;
        }

        public final Bitmap a(Bitmap bitmap) {
            Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            RectF rectF = new RectF(rect);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(-1);
            canvas.drawRoundRect(rectF, (float) this.a, (float) this.a, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            bitmap.recycle();
            return createBitmap;
        }
    }

    /* renamed from: cjk$b */
    /* compiled from: IconLoader */
    public interface b {
        void onBitmapReady(Bitmap bitmap, boolean z);
    }

    /* renamed from: cjk$c */
    /* compiled from: IconLoader */
    class c implements bkf {
        boolean a;

        public final void onBitmapFailed(Drawable drawable) {
        }

        public final void onPrepareLoad(Drawable drawable) {
        }

        /* synthetic */ c(cjk cjk, byte b2) {
            this();
        }

        private c() {
        }

        public final void onBitmapLoaded(final Bitmap bitmap, LoadedFrom loadedFrom) {
            this.a = loadedFrom == LoadedFrom.MEMORY;
            if (!this.a) {
                ahm.a(new Runnable() {
                    public final void run() {
                        if (cjk.this.a != null) {
                            cjk.this.a.onBitmapReady(bitmap, false);
                        }
                    }
                });
            } else if (cjk.this.a != null) {
                cjk.this.a.onBitmapReady(bitmap, true);
            }
        }
    }

    public final boolean a(Context context, String str, int i, b bVar) {
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        this.a = bVar;
        this.b = new c(this, 0);
        final Context context2 = context;
        final String str2 = str;
        final int i2 = i;
        final AtomicBoolean atomicBoolean2 = atomicBoolean;
        AnonymousClass1 r2 = new Runnable() {
            public final void run() {
                ImageLoader.a(context2).a(str2).a(i2, i2).a((bjo) new a(i2, 0)).a((bkf) cjk.this.b);
                atomicBoolean2.set(false);
            }
        };
        aho.a(r2);
        if (Looper.myLooper() != Looper.getMainLooper()) {
            while (atomicBoolean.get()) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return this.b.a;
    }
}
