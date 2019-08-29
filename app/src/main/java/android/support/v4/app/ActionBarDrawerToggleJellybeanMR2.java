package android.support.v4.app;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;

class ActionBarDrawerToggleJellybeanMR2 {
    private static final int[] a = {16843531};

    ActionBarDrawerToggleJellybeanMR2() {
    }

    public static Object a(Object obj, Activity activity, Drawable drawable, int i) {
        ActionBar actionBar = activity.getActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(drawable);
            actionBar.setHomeActionContentDescription(i);
        }
        return obj;
    }

    public static Object a(Object obj, Activity activity, int i) {
        ActionBar actionBar = activity.getActionBar();
        if (actionBar != null) {
            actionBar.setHomeActionContentDescription(i);
        }
        return obj;
    }

    /* JADX WARNING: type inference failed for: r4v1, types: [android.content.Context] */
    /* JADX WARNING: type inference failed for: r4v3, types: [android.content.Context] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.drawable.Drawable a(android.app.Activity r4) {
        /*
            android.app.ActionBar r0 = r4.getActionBar()
            if (r0 == 0) goto L_0x000a
            android.content.Context r4 = r0.getThemedContext()
        L_0x000a:
            r0 = 0
            int[] r1 = a
            r2 = 16843470(0x10102ce, float:2.369557E-38)
            r3 = 0
            android.content.res.TypedArray r4 = r4.obtainStyledAttributes(r0, r1, r2, r3)
            android.graphics.drawable.Drawable r0 = r4.getDrawable(r3)
            r4.recycle()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.ActionBarDrawerToggleJellybeanMR2.a(android.app.Activity):android.graphics.drawable.Drawable");
    }
}
