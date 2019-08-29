package defpackage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;

/* renamed from: ags reason: default package */
/* compiled from: ScreenUtil */
public final class ags {
    public static Rect a(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    public static Rect a(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    public static Rect b(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        try {
            Class.forName("android.view.Display").getMethod("getRealMetrics", new Class[]{DisplayMetrics.class}).invoke(defaultDisplay, new Object[]{displayMetrics});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    public static float c(Context context) {
        if (context == null || context.getResources() == null) {
            return 0.0f;
        }
        return context.getResources().getDisplayMetrics().density;
    }

    public static int d(Context context) {
        try {
            return context.getResources().getDimensionPixelSize(Integer.parseInt(Class.forName("com.android.internal.R$dimen").getField("status_bar_height").get(null).toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean b(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels >= displayMetrics.heightPixels;
    }

    public static void a(Activity activity, boolean z) {
        if (activity != null) {
            if (VERSION.SDK_INT >= 27) {
                activity.setShowWhenLocked(z);
                activity.setTurnScreenOn(z);
            } else if (z) {
                activity.getWindow().addFlags(524288);
            } else {
                activity.getWindow().clearFlags(524288);
            }
        }
    }
}
