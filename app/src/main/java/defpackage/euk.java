package defpackage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.autonavi.map.core.MapCustomizeManager;

/* renamed from: euk reason: default package */
/* compiled from: ImmersiveStatusBarUtil */
public final class euk {
    private static Boolean a = Boolean.TRUE;
    private static final int b = Color.argb(51, 0, 0, 0);

    public static boolean a() {
        if (a == null) {
            return false;
        }
        return a.booleanValue();
    }

    public static void a(Activity activity) {
        if (a == null) {
            a = Boolean.valueOf(activity.findViewById(16908290).getHeight() >= activity.getResources().getDisplayMetrics().heightPixels);
            activity.findViewById(16908290).requestLayout();
            if (!a.booleanValue()) {
                a((Context) activity);
            }
        }
    }

    public static void a(Dialog dialog) {
        if (a == null) {
            View decorView = dialog.getWindow().getDecorView();
            boolean z = true;
            if (decorView instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) decorView;
                if (viewGroup.getChildCount() > 0) {
                    decorView = viewGroup.getChildAt(viewGroup.getChildCount() - 1);
                    if (decorView == null) {
                        return;
                    }
                } else {
                    return;
                }
            }
            if (decorView.getHeight() < dialog.getContext().getResources().getDisplayMetrics().heightPixels) {
                z = false;
            }
            Boolean valueOf = Boolean.valueOf(z);
            a = valueOf;
            if (valueOf.booleanValue()) {
                decorView.requestLayout();
            }
        }
    }

    public static void b(Activity activity) {
        if (activity != null) {
            a(activity.getWindow(), 0);
        }
    }

    public static void a(PopupWindow popupWindow) {
        if (popupWindow != null && popupWindow.getContentView() != null) {
            View contentView = popupWindow.getContentView();
            if (VERSION.SDK_INT >= 21) {
                contentView.setSystemUiVisibility(5894);
                contentView.setFitsSystemWindows(true);
            }
        }
    }

    public static void a(Dialog dialog, int i) {
        a(dialog.getWindow(), i);
    }

    private static void a(Window window, int i) {
        if (VERSION.SDK_INT >= 21) {
            if (VERSION.SDK_INT < 23) {
                i = b;
            }
            window.addFlags(Integer.MIN_VALUE);
            window.clearFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
            window.setStatusBarColor(i);
            a(window);
            View decorView = window.getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility() | 1024 | 256 | 2048;
            if (VERSION.SDK_INT >= 23) {
                systemUiVisibility |= 8192;
            }
            decorView.setSystemUiVisibility(systemUiVisibility);
            return;
        }
        a = Boolean.FALSE;
    }

    public static void a(Activity activity, int i) {
        if (activity != null) {
            Window window = activity.getWindow();
            if (VERSION.SDK_INT >= 21) {
                if (VERSION.SDK_INT < 23) {
                    i = b;
                }
                window.addFlags(Integer.MIN_VALUE);
                window.clearFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
                window.setStatusBarColor(i);
                View decorView = window.getDecorView();
                a(window);
                int systemUiVisibility = decorView.getSystemUiVisibility() | 1024 | 256 | 2048;
                if (VERSION.SDK_INT >= 23) {
                    systemUiVisibility &= -8193;
                }
                decorView.setSystemUiVisibility(systemUiVisibility);
            }
        }
    }

    public static int a(Context context) {
        int i = 0;
        if (b(context)) {
            return 0;
        }
        int identifier = context.getResources().getIdentifier("status_bar_height", ResUtils.DIMEN, "android");
        if (identifier > 0) {
            i = context.getResources().getDimensionPixelSize(identifier);
        }
        return i;
    }

    private static boolean b(Context context) {
        return context instanceof Activity ? (((Activity) context).getWindow().getAttributes().flags & 1024) == 1024 : !(context instanceof ContextThemeWrapper);
    }

    private static void a(Window window) {
        if (VERSION.SDK_INT >= 28) {
            LayoutParams attributes = window.getAttributes();
            attributes.layoutInDisplayCutoutMode = 1;
            window.setAttributes(attributes);
        }
    }
}
