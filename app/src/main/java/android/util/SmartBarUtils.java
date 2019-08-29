package android.util;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.os.Build;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.lang.reflect.InvocationTargetException;

@SuppressLint({"NewApi"})
public class SmartBarUtils {
    public SmartBarUtils() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static void setActionBarTabsShowAtBottom(ActionBar actionbar, boolean showAtBottom) {
        try {
            try {
                Class.forName("android.app.ActionBar").getMethod("setTabsShowAtBottom", new Class[]{Boolean.TYPE}).invoke(actionbar, new Object[]{Boolean.valueOf(showAtBottom)});
            } catch (IllegalArgumentException e) {
                LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e);
            } catch (IllegalAccessException e2) {
                LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e2);
            } catch (InvocationTargetException e3) {
                LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e3);
            }
        } catch (SecurityException e4) {
            LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e4);
        } catch (NoSuchMethodException e5) {
            LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e5);
        } catch (ClassNotFoundException e6) {
            LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e6);
        }
    }

    public static void setSplitBackgroundDrawable(ActionBar actionbar, Drawable drawable) {
        try {
            try {
                Class.forName("android.app.ActionBar").getMethod("setSplitBackgroundDrawable", new Class[]{Drawable.class}).invoke(actionbar, new Object[]{drawable});
            } catch (IllegalArgumentException e) {
                LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e);
            } catch (IllegalAccessException e2) {
                LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e2);
            } catch (InvocationTargetException e3) {
                LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e3);
            }
        } catch (NoSuchMethodException e1) {
            LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e1);
        } catch (ClassNotFoundException e12) {
            LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e12);
        }
    }

    public static void setActionBarViewCollapsable(ActionBar actionbar, boolean collapsable) {
        try {
            try {
                Class.forName("android.app.ActionBar").getMethod("setActionBarViewCollapsable", new Class[]{Boolean.TYPE}).invoke(actionbar, new Object[]{Boolean.valueOf(collapsable)});
            } catch (IllegalArgumentException e) {
                LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e);
            } catch (IllegalAccessException e2) {
                LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e2);
            } catch (InvocationTargetException e3) {
                LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e3);
            }
        } catch (SecurityException e4) {
            LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e4);
        } catch (NoSuchMethodException e5) {
            LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e5);
        } catch (ClassNotFoundException e6) {
            LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e6);
        }
    }

    public static void setActionModeHeaderHidden(ActionBar actionbar, boolean hidden) {
        try {
            try {
                Class.forName("android.app.ActionBar").getMethod("setActionModeHeaderHidden", new Class[]{Boolean.TYPE}).invoke(actionbar, new Object[]{Boolean.valueOf(hidden)});
            } catch (IllegalArgumentException e) {
                LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e);
            } catch (IllegalAccessException e2) {
                LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e2);
            } catch (InvocationTargetException e3) {
                LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e3);
            }
        } catch (SecurityException e4) {
            LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e4);
        } catch (NoSuchMethodException e5) {
            LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e5);
        } catch (ClassNotFoundException e6) {
            LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e6);
        }
    }

    public static boolean hasSmartBar() {
        try {
            return ((Boolean) Class.forName("android.os.Build").getMethod("hasSmartBar", new Class[0]).invoke(null, new Object[0])).booleanValue();
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().print((String) "SmartBarUtils", (Throwable) e);
            if (Build.DEVICE.equals("mx2")) {
                return true;
            }
            if (Build.DEVICE.equals("mx") || Build.DEVICE.equals("m9")) {
                return false;
            }
            return false;
        }
    }
}
