package defpackage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;

/* renamed from: ebg reason: default package */
/* compiled from: RouteNaviUtil */
public final class ebg {
    private static int a = 0;
    private static int b = ((int) ((AMapAppGlobal.getTopActivity().getResources().getDisplayMetrics().density * 17.0f) / 2.0f));

    static {
        Bitmap decodeResource = BitmapFactory.decodeResource(AMapAppGlobal.getTopActivity().getResources(), R.drawable.compass_circle);
        if (decodeResource != null) {
            a = (decodeResource.getWidth() * 65) / 168;
        }
        if (decodeResource != null) {
            decodeResource.recycle();
        }
    }

    public static int a() {
        return a - b;
    }
}
