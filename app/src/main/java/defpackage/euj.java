package defpackage;

import android.app.Activity;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Handler;
import android.provider.Settings.Global;
import android.provider.Settings.System;
import android.view.WindowManager;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.amap.app.AMapAppGlobal;
import java.util.ArrayList;
import java.util.List;

/* renamed from: euj reason: default package */
/* compiled from: BottomNavigationBarUtil */
public final class euj {
    /* access modifiers changed from: private */
    public static List<a> a = new ArrayList();
    private static Boolean b = null;
    /* access modifiers changed from: private */
    public static Boolean c = null;
    /* access modifiers changed from: private */
    public static String d = "navigationbar_is_min";
    private static String e = "config_showNavigationBar";
    private static String f = "bool";
    private static String g = "android";
    private static String h = "qemu.hw.mainkeys";
    private static String i = "get";
    private static String j = "android.os.SystemProperties";

    /* renamed from: euj$a */
    /* compiled from: BottomNavigationBarUtil */
    public interface a {
        void a(boolean z);
    }

    /* renamed from: euj$b */
    /* compiled from: BottomNavigationBarUtil */
    static class b extends ContentObserver {
        public b(Handler handler) {
            super(handler);
        }

        public final void onChange(boolean z) {
            int i;
            if (VERSION.SDK_INT < 21) {
                i = System.getInt(AMapAppGlobal.getApplication().getContentResolver(), euj.d, 0);
            } else {
                i = Global.getInt(AMapAppGlobal.getApplication().getContentResolver(), euj.d, 0);
            }
            if (i == 1) {
                euj.c = Boolean.FALSE;
            } else {
                euj.c = Boolean.TRUE;
            }
            for (int size = euj.a.size() - 1; size >= 0; size--) {
                a aVar = (a) euj.a.get(size);
                if (aVar != null) {
                    aVar.a(euj.c.booleanValue());
                }
            }
        }
    }

    public static boolean a(Activity activity) {
        if (b != null) {
            return b.booleanValue();
        }
        Resources resources = activity.getResources();
        int identifier = resources.getIdentifier(e, f, g);
        if (identifier > 0) {
            b = Boolean.valueOf(resources.getBoolean(identifier));
        }
        try {
            Class<?> cls = Class.forName(j);
            String str = (String) cls.getMethod(i, new Class[]{String.class}).invoke(cls, new Object[]{h});
            if ("1".equals(str)) {
                b = Boolean.FALSE;
            } else if ("0".equals(str)) {
                b = Boolean.TRUE;
            }
        } catch (Exception unused) {
        }
        if (b.booleanValue() && eul.a()) {
            if (VERSION.SDK_INT < 21) {
                AMapAppGlobal.getApplication().getContentResolver().registerContentObserver(System.getUriFor(d), true, new b(new Handler()));
            } else {
                AMapAppGlobal.getApplication().getContentResolver().registerContentObserver(Global.getUriFor(d), true, new b(new Handler()));
            }
        }
        return b.booleanValue();
    }

    public static void a(a aVar) {
        if (!a.contains(aVar)) {
            a.add(aVar);
        }
    }

    public static void b(a aVar) {
        if (a.contains(aVar)) {
            a.remove(aVar);
        }
    }

    private static boolean c(Activity activity) {
        if (activity == null) {
            return false;
        }
        Rect rect = new Rect();
        try {
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int height = rect.height();
            int identifier = AMapAppGlobal.getApplication().getResources().getIdentifier("status_bar_height", ResUtils.DIMEN, "android");
            int dimensionPixelSize = identifier > 0 ? AMapAppGlobal.getApplication().getResources().getDimensionPixelSize(identifier) : 0;
            WindowManager windowManager = (WindowManager) AMapAppGlobal.getApplication().getSystemService(TemplateTinyApp.WINDOW_KEY);
            Point point = new Point();
            if (VERSION.SDK_INT >= 17) {
                windowManager.getDefaultDisplay().getRealSize(point);
            } else {
                windowManager.getDefaultDisplay().getSize(point);
            }
            if (height == point.y - dimensionPixelSize) {
                return false;
            }
            return true;
        } catch (ClassCastException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static int b(Activity activity) {
        boolean z;
        if (eul.a()) {
            if (c == null) {
                boolean z2 = true;
                if (Global.getInt(activity.getContentResolver(), d, 0) == 1) {
                    z2 = false;
                }
                c = Boolean.valueOf(z2);
            }
            z = c.booleanValue();
        } else {
            z = c(activity);
        }
        if (!z) {
            return 0;
        }
        Resources resources = activity.getResources();
        return resources.getDimensionPixelSize(resources.getIdentifier("navigation_bar_height", ResUtils.DIMEN, "android"));
    }
}
