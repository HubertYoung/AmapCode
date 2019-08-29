package defpackage;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/* renamed from: ahx reason: default package */
/* compiled from: ViewUtil */
public final class ahx {
    public static void a(View view) {
        if (view != null) {
            if (view.getBackground() != null) {
                view.getBackground().setCallback(null);
            }
            if (view instanceof ViewGroup) {
                int i = 0;
                while (true) {
                    ViewGroup viewGroup = (ViewGroup) view;
                    if (i < viewGroup.getChildCount()) {
                        a(viewGroup.getChildAt(i));
                        i++;
                    } else {
                        viewGroup.removeAllViews();
                        return;
                    }
                }
            }
        }
    }

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
}
