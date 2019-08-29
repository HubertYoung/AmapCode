package com.alipay.mobile.framework.app.monitor;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import com.alipay.mobile.common.logging.api.monitor.MTBizReportName;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.quinox.utils.MonitorLogger;
import com.alipay.mobile.quinox.utils.ReflectUtil;
import com.alipay.mobile.quinox.utils.TraceLogger;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

public final class ONRMonitor implements OnGlobalLayoutListener, OnPreDrawListener, OnScrollChangedListener {
    private static long a = 0;
    private static long b = 0;
    private static boolean c = false;
    private static boolean f = false;
    private static AtomicInteger g = new AtomicInteger(0);
    private static int h = 0;
    private Activity d;
    private boolean e = false;

    public static void setEnable(boolean enable) {
        f = enable;
        TraceLogger.i((String) RPCDataItems.SWITCH_TAG_LOG, "setEnable: " + f);
    }

    public ONRMonitor(Activity activity) {
        this.d = activity;
    }

    public final void register() {
        if (this.d != null) {
            if (!f) {
                unregister();
            } else if (!this.e) {
                ViewTreeObserver observer = null;
                try {
                    observer = this.d.getWindow().getDecorView().getViewTreeObserver();
                } catch (Throwable tr) {
                    TraceLogger.w((String) "ONRMonitor", tr);
                }
                if (observer != null) {
                    observer.addOnGlobalLayoutListener(this);
                    observer.addOnScrollChangedListener(this);
                    observer.addOnPreDrawListener(this);
                    this.e = true;
                    if (g.addAndGet(1) == 1) {
                        a = 0;
                        b = 0;
                    }
                    TraceLogger.i((String) "ONRMonitor", "ONRMonitor registered for: " + this.d.getComponentName());
                }
            }
        }
    }

    public final void unregister() {
        if (this.d != null && this.e) {
            ViewTreeObserver observer = null;
            try {
                observer = this.d.getWindow().getDecorView().getViewTreeObserver();
            } catch (Throwable tr) {
                TraceLogger.w((String) "ONRMonitor", tr);
            }
            if (observer != null) {
                observer.removeOnPreDrawListener(this);
                observer.removeOnScrollChangedListener(this);
                if (VERSION.SDK_INT < 16) {
                    observer.removeGlobalOnLayoutListener(this);
                } else {
                    observer.removeOnGlobalLayoutListener(this);
                }
                this.e = false;
                g.addAndGet(-1);
                TraceLogger.i((String) "ONRMonitor", "ONRMonitor unregistered for: " + this.d.getComponentName());
            }
        }
    }

    public final void onGlobalLayout() {
        onDisplayChangeAction();
    }

    public final void onScrollChanged() {
        onDisplayChangeAction();
    }

    public final boolean onPreDraw() {
        onDisplayChangeAction();
        return true;
    }

    public static void onDisplayChangeAction() {
        b = SystemClock.elapsedRealtime();
        h = 0;
        c = false;
    }

    public static void onUserInteraction(Activity activity) {
        TraceLogger.i((String) "ONRMonitor", "onInteraction: " + (activity == null ? "null" : activity.getComponentName()));
        boolean noResponse = false;
        if (g.get() > 0) {
            if (b > a) {
                a = SystemClock.elapsedRealtime();
                h = 1;
            } else {
                int i = h + 1;
                h = i;
                if (i >= 3 && SystemClock.elapsedRealtime() - a > 5000) {
                    noResponse = true;
                }
            }
        }
        if (f && noResponse && !c) {
            a(activity);
        }
    }

    private static void a(Activity activity) {
        Bitmap bitmap;
        c = true;
        long start = SystemClock.elapsedRealtime();
        Map params = new HashMap();
        String topActivity = null;
        try {
            Activity runningActivity = a();
            if (runningActivity == null) {
                runningActivity = activity;
            }
            if (runningActivity != null) {
                topActivity = runningActivity.getComponentName().getClassName();
                params.put("currentPage", topActivity);
                if (topActivity != null && topActivity.startsWith("com.alipay.mobile.nebulacore.ui.H5Activity")) {
                    String currentUrl = b(runningActivity);
                    if (!TextUtils.isEmpty(currentUrl)) {
                        params.put("currentUrl", currentUrl);
                    }
                }
            }
        } catch (Throwable tr) {
            TraceLogger.w((String) "ONRMonitor", tr);
        }
        String errorCode = "3";
        try {
            long startBmp = SystemClock.elapsedRealtime();
            View view = activity.getWindow().getDecorView().getRootView();
            if (!view.isDrawingCacheEnabled()) {
                view.setDrawingCacheEnabled(true);
                view.buildDrawingCache();
                Bitmap drawingCache = view.getDrawingCache();
                bitmap = drawingCache.copy(drawingCache.getConfig(), false);
                view.setDrawingCacheEnabled(false);
            } else {
                view.destroyDrawingCache();
                view.buildDrawingCache();
                Bitmap drawingCache2 = view.getDrawingCache();
                bitmap = drawingCache2.copy(drawingCache2.getConfig(), false);
                view.destroyDrawingCache();
            }
            int height = bitmap.getHeight();
            int width = bitmap.getWidth();
            HashMap colors = new HashMap();
            if (height >= 480 && width >= 320) {
                for (int i = 0; i < width / 4; i++) {
                    a(colors, bitmap.getPixel(i * 4, height / 4));
                    a(colors, bitmap.getPixel(i * 4, (height * 3) / 8));
                    a(colors, bitmap.getPixel(i * 4, height / 2));
                    a(colors, bitmap.getPixel(i * 4, (height * 5) / 8));
                    a(colors, bitmap.getPixel(i * 4, (height * 3) / 4));
                    a(colors, bitmap.getPixel(i * 4, (height / 4) + (((i * 4) * height) / (width * 2))));
                    a(colors, bitmap.getPixel(i * 4, height - ((height / 4) + (((i * 4) * height) / (width * 2)))));
                }
            }
            int totalPixels = 0;
            for (Entry pixel : colors.entrySet()) {
                totalPixels += ((Integer) pixel.getValue()).intValue();
            }
            Iterator it = colors.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Entry pixel2 = (Entry) it.next();
                if (((double) ((Integer) pixel2.getValue()).intValue()) / ((double) totalPixels) > 0.95d) {
                    int r = Color.red(((Integer) pixel2.getKey()).intValue());
                    int g2 = Color.green(((Integer) pixel2.getKey()).intValue());
                    int b2 = Color.blue(((Integer) pixel2.getKey()).intValue());
                    if (r >= 225 && g2 >= 225 && b2 >= 225) {
                        errorCode = "1";
                    } else if (r > 50 || g2 > 50 || b2 > 50) {
                        errorCode = "2";
                    } else {
                        errorCode = "0";
                    }
                }
            }
            Log.i("ONRMonitor", "bitmap: " + (bitmap.getRowBytes() * bitmap.getHeight()) + " colors:" + colors.size() + " errorCode:" + errorCode + " costs: " + (SystemClock.elapsedRealtime() - startBmp));
            bitmap.recycle();
        } catch (Throwable tr2) {
            TraceLogger.w((String) "ONRMonitor", tr2);
        }
        MonitorLogger.mtBizReport(MTBizReportName.MTBIZ_APM, "APM_ONR", errorCode, params);
        TraceLogger.i((String) "ONRMonitor", "report cost:" + (SystemClock.elapsedRealtime() - start) + " topActivity:" + topActivity + " viewHierarchy:" + null);
    }

    private static void a(Map<Integer, Integer> map, int key) {
        Integer oldCount = map.get(Integer.valueOf(key));
        int newCount = 1;
        if (oldCount != null) {
            newCount = oldCount.intValue() + 1;
        }
        map.put(Integer.valueOf(key), Integer.valueOf(newCount));
    }

    private static String b(Activity activity) {
        if (activity == null) {
            return null;
        }
        try {
            if (!activity.getComponentName().getClassName().startsWith("com.alipay.mobile.nebulacore.ui.H5Activity")) {
                return null;
            }
            Field[] declaredFields = activity.getClass().getDeclaredFields();
            int length = declaredFields.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Field field = declaredFields[i];
                if (field.getType().getName().endsWith("H5SessionImpl")) {
                    field.setAccessible(true);
                    String currentUrl = (String) ReflectUtil.invokeMethod(ReflectUtil.invokeMethod(field.get(activity), (String) "getTopPage"), (String) "getUrl");
                    if (!TextUtils.isEmpty(currentUrl)) {
                        return currentUrl;
                    }
                } else {
                    i++;
                }
            }
            return null;
        } catch (Throwable tr) {
            TraceLogger.w((String) "ONRMonitor", tr);
        }
    }

    private static Activity a() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);
            for (Object iToken : activities.keySet()) {
                Object activityRecord = activities.get(iToken);
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField(WidgetType.ACTIVITY);
                    activityField.setAccessible(true);
                    return (Activity) activityField.get(activityRecord);
                }
            }
        } catch (Throwable tr) {
            TraceLogger.w((String) "ONRMonitor", tr);
        }
        return null;
    }
}
