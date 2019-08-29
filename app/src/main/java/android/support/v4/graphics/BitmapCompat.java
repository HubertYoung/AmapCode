package android.support.v4.graphics;

import android.graphics.Bitmap;
import android.os.Build.VERSION;

public class BitmapCompat {
    static final BitmapImpl IMPL;

    static class BaseBitmapImpl implements BitmapImpl {
        public void a(Bitmap bitmap, boolean z) {
        }

        public boolean a(Bitmap bitmap) {
            return false;
        }

        BaseBitmapImpl() {
        }

        public int b(Bitmap bitmap) {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }
    }

    interface BitmapImpl {
        void a(Bitmap bitmap, boolean z);

        boolean a(Bitmap bitmap);

        int b(Bitmap bitmap);
    }

    static class HcMr1BitmapCompatImpl extends BaseBitmapImpl {
        HcMr1BitmapCompatImpl() {
        }

        public int b(Bitmap bitmap) {
            return BitmapCompatHoneycombMr1.a(bitmap);
        }
    }

    static class JbMr2BitmapCompatImpl extends HcMr1BitmapCompatImpl {
        JbMr2BitmapCompatImpl() {
        }

        public final boolean a(Bitmap bitmap) {
            return BitmapCompatJellybeanMR2.a(bitmap);
        }

        public final void a(Bitmap bitmap, boolean z) {
            BitmapCompatJellybeanMR2.a(bitmap, z);
        }
    }

    static class KitKatBitmapCompatImpl extends JbMr2BitmapCompatImpl {
        KitKatBitmapCompatImpl() {
        }

        public final int b(Bitmap bitmap) {
            return BitmapCompatKitKat.a(bitmap);
        }
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 19) {
            IMPL = new KitKatBitmapCompatImpl();
        } else if (i >= 18) {
            IMPL = new JbMr2BitmapCompatImpl();
        } else if (i >= 12) {
            IMPL = new HcMr1BitmapCompatImpl();
        } else {
            IMPL = new BaseBitmapImpl();
        }
    }

    public static boolean hasMipMap(Bitmap bitmap) {
        return IMPL.a(bitmap);
    }

    public static void setHasMipMap(Bitmap bitmap, boolean z) {
        IMPL.a(bitmap, z);
    }

    public static int getAllocationByteCount(Bitmap bitmap) {
        return IMPL.b(bitmap);
    }
}
