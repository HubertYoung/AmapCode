package com.alipay.mobile.monitor.track.interceptor;

import android.app.Activity;
import android.os.Build.VERSION;
import android.view.View;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class WindowManagerHook {
    private static WindowManagerHook a = null;
    public static Comparator comparator = new WindowComparator();
    private Class<?> b;
    private Class<?> c;
    private Field d;
    private Field e;
    private Object f;
    private int g = 0;

    public class WindowComparator implements Comparator<View> {
        public int compare(View paramView1, View paramView2) {
            return (paramView2.getWidth() * paramView2.getHeight()) - (paramView1.getWidth() * paramView1.getHeight());
        }
    }

    public enum WindowType {
        Activity,
        DecorView,
        Dialog,
        PopupWindows
    }

    private WindowManagerHook() {
        perpare();
    }

    public static String getMainWindowDesc() {
        return "MainWindow";
    }

    public static WindowManagerHook getManager() {
        try {
            if (a == null) {
                a = new WindowManagerHook();
            }
        } catch (Throwable e2) {
            LoggerFactory.getTraceLogger().error((String) "WindowManagerHook", e2);
        }
        return a;
    }

    /* access modifiers changed from: 0000 */
    public Field getViewsField(Class<?> paramClass) {
        try {
            Field field = paramClass.getDeclaredField("mViews");
            field.setAccessible(true);
            return field;
        } catch (Throwable e2) {
            LoggerFactory.getTraceLogger().error((String) "WindowManagerHook", e2);
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public View[] getViewsFromWindowManger() {
        if (!(this.f == null || this.e == null)) {
            try {
                if (VERSION.SDK_INT < 19) {
                    return (View[]) this.d.get(this.f);
                }
                ArrayList object = (ArrayList) this.d.get(this.f);
                return (View[]) object.toArray(new View[object.size()]);
            } catch (Throwable localException) {
                LoggerFactory.getTraceLogger().error((String) "WindowManagerHook", localException);
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public Object getWindowManager() {
        boolean z = false;
        try {
            return this.e.get(null);
        } catch (IllegalAccessException localIllegalAccessException) {
            LoggerFactory.getTraceLogger().error((String) "WindowManagerHook", (Throwable) localIllegalAccessException);
            return z;
        }
    }

    /* access modifiers changed from: 0000 */
    public Field getWindowManagerField(Class<?> paramClass) {
        String str;
        if (VERSION.SDK_INT >= 17) {
            str = "sDefaultWindowManager";
        } else if (VERSION.SDK_INT >= 13) {
            str = "sWindowManager";
        } else {
            str = "mWindowManager";
        }
        try {
            Field field = paramClass.getDeclaredField(str);
            field.setAccessible(true);
            return field;
        } catch (Throwable e2) {
            LoggerFactory.getTraceLogger().error((String) "WindowManagerHook", e2);
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public Class<?> getWindowManagerImplClass() {
        if (VERSION.SDK_INT >= 17) {
            try {
                return Class.forName("android.view.WindowManagerGlobal");
            } catch (Throwable e2) {
                LoggerFactory.getTraceLogger().error((String) "WindowManagerHook", e2);
            }
        } else {
            try {
                return Class.forName("android.view.WindowManagerImpl");
            } catch (Throwable e3) {
                LoggerFactory.getTraceLogger().error((String) "WindowManagerHook", e3);
            }
        }
        return null;
    }

    public WindowType getWindowType(View paramView) {
        if (paramView == null) {
            LoggerFactory.getTraceLogger().warn((String) "WindowManagerHook", (String) "getWindowType paramView is null");
            return null;
        }
        Class view = paramView.getClass();
        if (view == this.b) {
            return WindowType.DecorView;
        }
        if (view == this.c) {
            return WindowType.PopupWindows;
        }
        if (paramView.getContext() instanceof Activity) {
            return WindowType.Activity;
        }
        return WindowType.Dialog;
    }

    public View[] getWindowViewWithSort() {
        View[] arrayOfView2 = getWindowViews();
        View[] arrayOfView1 = arrayOfView2;
        if (arrayOfView2.length <= 1) {
            return arrayOfView1;
        }
        View[] arrayOfView12 = (View[]) Arrays.copyOf(arrayOfView2, arrayOfView2.length);
        Arrays.sort(arrayOfView12, comparator);
        return arrayOfView12;
    }

    public boolean isAddDialog() {
        if (this.g != 2) {
            LoggerFactory.getTraceLogger().warn((String) "WindowManagerHook", (String) "isWindowsViewChange not avaiable");
            return false;
        }
        View[] views = getWindowViews();
        if (views == null) {
            LoggerFactory.getTraceLogger().warn((String) "WindowManagerHook", (String) "isWindowsViewChange not avaiable");
            return false;
        }
        WindowType type = getWindowType(views[views.length - 1]);
        if (type == null || type == WindowType.Activity) {
            return false;
        }
        return true;
    }

    public View[] getWindowViews() {
        if (this.g == 2) {
            if (this.f == null) {
                this.f = getWindowManager();
            }
            return getViewsFromWindowManger();
        }
        LoggerFactory.getTraceLogger().warn((String) "WindowManagerHook", (String) "getWindowViews failure - NOT READY !");
        return null;
    }

    /* access modifiers changed from: 0000 */
    public boolean initTypeClass() {
        try {
            this.b = Class.forName("com.android.internal.policy.PhoneWindow$DecorView");
            LoggerFactory.getTraceLogger().warn((String) "WindowManagerHook", (String) "type = PhoneWindow$DecorView");
            return true;
        } catch (Throwable th) {
            try {
                this.c = Class.forName("android.widget.PopupWindow$PopupViewContainer");
                LoggerFactory.getTraceLogger().warn((String) "WindowManagerHook", (String) "type = PopupWindow$PopupViewContainer");
                return true;
            } catch (Throwable th2) {
                try {
                    this.b = Class.forName("com.android.internal.policy.impl.PhoneWindow$DecorView");
                    LoggerFactory.getTraceLogger().warn((String) "WindowManagerHook", (String) "type = impl.PhoneWindow$DecorView");
                    return true;
                } catch (Throwable e4) {
                    LoggerFactory.getTraceLogger().error((String) "WindowManagerHook", e4);
                    return false;
                }
            }
        }
    }

    public boolean isSuccess() {
        return this.g == 2;
    }

    public void perpare() {
        Class localClass = getWindowManagerImplClass();
        if (localClass == null) {
            LoggerFactory.getTraceLogger().warn((String) "WindowManagerHook", (String) "load class failure");
            this.g = 1;
            return;
        }
        this.e = getWindowManagerField(localClass);
        if (localClass == null) {
            LoggerFactory.getTraceLogger().warn((String) "WindowManagerHook", (String) "load class failure");
            this.g = 1;
            return;
        }
        this.d = getViewsField(localClass);
        if (this.d == null) {
            LoggerFactory.getTraceLogger().warn((String) "WindowManagerHook", (String) "mViews field found failure!");
            this.g = 1;
        } else if (!initTypeClass()) {
            LoggerFactory.getTraceLogger().warn((String) "WindowManagerHook", (String) "found type class failure!");
            this.g = 1;
        } else {
            this.f = getWindowManager();
            this.g = 2;
        }
    }
}
