package defpackage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.view.DisplayCutout;
import com.amap.bundle.utils.device.DisplayType;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleOS;
import java.util.List;

/* renamed from: tt reason: default package */
/* compiled from: ScreenUtils */
public final class tt {

    /* renamed from: tt$1 reason: invalid class name */
    /* compiled from: ScreenUtils */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[DisplayType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                com.amap.bundle.utils.device.DisplayType[] r0 = com.amap.bundle.utils.device.DisplayType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.amap.bundle.utils.device.DisplayType r1 = com.amap.bundle.utils.device.DisplayType.CUTOUT     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.amap.bundle.utils.device.DisplayType r1 = com.amap.bundle.utils.device.DisplayType.NORMAL     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.tt.AnonymousClass1.<clinit>():void");
        }
    }

    public static int a(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int b(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static void a(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activity.getWindow().addFlags(1024);
        }
    }

    public static void b(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activity.getWindow().clearFlags(1024);
        }
    }

    public static int c(Activity activity) {
        if (activity == null || VERSION.SDK_INT < 28) {
            return 4;
        }
        try {
            DisplayCutout displayCutout = activity.getWindow().getDecorView().getRootWindowInsets().getDisplayCutout();
            List<Rect> boundingRects = displayCutout.getBoundingRects();
            int i = 0;
            if ((displayCutout == null || boundingRects == null || boundingRects.size() <= 0) ? false : true) {
                Rect rect = boundingRects.get(0);
                int a = a((Context) activity) / 3;
                for (int i2 = 1; i2 <= 3; i2++) {
                    i += a;
                    if (rect.centerX() < i) {
                        return i2;
                    }
                }
            }
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder("getCutoutPosition:--->");
            sb.append(th.getMessage());
            cjy.b(sb.toString());
        }
        if (ts.a()) {
            return 2;
        }
        return 4;
    }

    public static boolean c(Context context) {
        try {
            return Secure.getInt(context.getContentResolver(), AjxModuleOS.DISPLAY_NOTCH_STATUS, 0) == 1;
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder("isHuaweiHideNotch:--->");
            sb.append(th.getMessage());
            cjy.b(sb.toString());
            return false;
        }
    }
}
