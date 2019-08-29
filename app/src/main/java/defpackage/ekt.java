package defpackage;

import android.content.Context;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import com.autonavi.minimap.ajx3.util.DimensionUtils;

/* renamed from: ekt reason: default package */
/* compiled from: PoiDetailUtils */
public final class ekt {
    public static boolean a(View view, @NonNull MotionEvent motionEvent) {
        if (view == null || view.getVisibility() != 0) {
            return false;
        }
        int width = view.getWidth();
        int height = view.getHeight();
        if (width == 0 || height == 0) {
            return false;
        }
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i = iArr[0];
        int i2 = iArr[1] + 0;
        return new RectF((float) i, (float) i2, (float) (width + i), (float) (height + i2)).contains(motionEvent.getRawX(), motionEvent.getRawY());
    }

    public static int a(Context context, String str) {
        int width = ags.a(context).width();
        (str != null && str.length() >= 2 ? str.substring(0, 2) : "").hashCode();
        return ((int) Math.floor(((double) width) * 0.5d)) + DimensionUtils.standardUnitToPixel(40.0f);
    }
}
