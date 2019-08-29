package defpackage;

import android.app.Application;
import android.os.Build.VERSION;
import android.view.DisplayCutout;
import android.view.Window;
import com.amap.bundle.utils.device.DisplayType;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;

/* renamed from: ts reason: default package */
/* compiled from: FringeUIHelper */
public final class ts {
    public static boolean a() {
        return agp.a(AMapAppGlobal.getApplication()) == DisplayType.CUTOUT;
    }

    public static int b() {
        Application application = AMapAppGlobal.getApplication();
        return (int) (((double) (AnonymousClass1.a[agp.a(application).ordinal()] != 1 ? application.getResources().getDimensionPixelSize(R.dimen.status_bar_height) : application.getResources().getDimensionPixelSize(R.dimen.status_bar_height))) * 1.5d);
    }

    public static boolean a(Window window) {
        if (VERSION.SDK_INT >= 28 && window != null) {
            try {
                DisplayCutout displayCutout = window.getDecorView().getRootWindowInsets().getDisplayCutout();
                if (displayCutout == null || displayCutout.getBoundingRects() == null || displayCutout.getBoundingRects().size() <= 0) {
                    return false;
                }
                return true;
            } catch (Exception unused) {
            }
        }
        return false;
    }
}
