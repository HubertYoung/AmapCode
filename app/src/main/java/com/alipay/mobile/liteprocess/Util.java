package com.alipay.mobile.liteprocess;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.AppTask;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.quinox.utils.LiteProcessInfo;
import com.alipay.mobile.quinox.utils.ProcessInfo;
import com.alipay.mobile.quinox.utils.ReflectUtil;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.map.core.MapCustomizeManager;
import java.util.Iterator;
import java.util.Set;

public class Util {
    private static Context a;
    private static ProcessInfo b;

    public static SharedPreferences getSp() {
        return getContext().getSharedPreferences("LITE_PROCESS_" + getLpid(), 0);
    }

    public static synchronized void setContext(Context context) {
        synchronized (Util.class) {
            if (a == null && context != null) {
                a = context.getApplicationContext();
            }
        }
    }

    public static synchronized Context getContext() {
        Context applicationContext;
        synchronized (Util.class) {
            if (a != null) {
                applicationContext = a;
            } else {
                applicationContext = LoggerFactory.getLogContext().getApplicationContext();
            }
        }
        return applicationContext;
    }

    public static MicroApplicationContext getMicroAppContext() {
        return LauncherApplicationAgent.getInstance().getMicroApplicationContext();
    }

    public static int getLpid() {
        return LiteProcessInfo.g(getContext()).getCurrentLiteProcessId();
    }

    static boolean a(Set<String> baseClassNames) {
        try {
            return c(baseClassNames);
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t));
            return false;
        }
    }

    private static boolean c(Set<String> baseClassNames) {
        if (baseClassNames == null || baseClassNames.size() == 0) {
            return false;
        }
        if (VERSION.SDK_INT >= 21) {
            for (AppTask appTask : ((ActivityManager) LauncherApplicationAgent.getInstance().getApplicationContext().getSystemService(WidgetType.ACTIVITY)).getAppTasks()) {
                RecentTaskInfo recentTaskInfo = null;
                try {
                    recentTaskInfo = appTask.getTaskInfo();
                } catch (Throwable t) {
                    LoggerFactory.getTraceLogger().error((String) Const.TAG, "removeFromRecentTasksList " + t);
                }
                if (recentTaskInfo != null && recentTaskInfo.baseIntent != null && recentTaskInfo.baseIntent.getComponent() != null && baseClassNames.contains(recentTaskInfo.baseIntent.getComponent().getClassName())) {
                    LoggerFactory.getTraceLogger().warn((String) Const.TAG, (String) "removeFromRecentTasksList");
                    appTask.finishAndRemoveTask();
                    return true;
                }
            }
        }
        return false;
    }

    static synchronized ProcessInfo a() {
        ProcessInfo processInfo;
        synchronized (Util.class) {
            if (b != null) {
                processInfo = b;
            } else {
                processInfo = new ProcessInfo(getContext(), getCurrentProcessName());
                b = processInfo;
            }
        }
        return processInfo;
    }

    public static String getCurrentProcessName() {
        return LiteProcessInfo.g(getContext()).getCurrentProcessName();
    }

    public static boolean isMainProcess() {
        return LoggerFactory.getProcessInfo().isMainProcess();
    }

    public static boolean isLiteProcess() {
        return LiteProcessInfo.g(getContext()).isCurrentProcessALiteProcess();
    }

    @Nullable
    static ComponentName b() {
        try {
            return ((ActivityManager) getContext().getSystemService(WidgetType.ACTIVITY)).getRunningTasks(1).get(0).baseActivity;
        } catch (Throwable th) {
            return null;
        }
    }

    @Nullable
    static ComponentName b(Set<String> classNames) {
        if (classNames == null || classNames.size() == 0) {
            return null;
        }
        try {
            for (RunningTaskInfo runningTaskInfoItem : ((ActivityManager) getContext().getSystemService(WidgetType.ACTIVITY)).getRunningTasks(Integer.MAX_VALUE)) {
                if (classNames.contains(runningTaskInfoItem.baseActivity.getClassName())) {
                    return runningTaskInfoItem.baseActivity;
                }
            }
            return null;
        } catch (Throwable th) {
            return null;
        }
    }

    public static ComponentName getTopTaskTopActivity() {
        try {
            return ((ActivityManager) getContext().getSystemService(WidgetType.ACTIVITY)).getRunningTasks(1).get(0).topActivity;
        } catch (Throwable th) {
            return null;
        }
    }

    static void a(long delay) {
        final Activity activity = getMicroAppContext().getTopActivity() != null ? (Activity) getMicroAppContext().getTopActivity().get() : null;
        if (activity != null) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                public final void run() {
                    try {
                        activity.moveTaskToBack(true);
                        LoggerFactory.getTraceLogger().debug(Const.TAG, "moveCurrentProcessToBack");
                    } catch (Throwable t) {
                        LoggerFactory.getTraceLogger().error((String) Const.TAG, t);
                    }
                }
            }, delay);
        }
    }

    public static void moveTaskToFront(ActivityManager activityManager, Activity activity, RunningTaskInfo runningTaskInfo, boolean enter, boolean fromForeground, Bundle params, boolean forMoveToBack) {
        if (runningTaskInfo != null) {
            final ActivityManager activityManager2 = activityManager;
            final Activity activity2 = activity;
            final RunningTaskInfo runningTaskInfo2 = runningTaskInfo;
            final boolean z = enter;
            final boolean z2 = fromForeground;
            final Bundle bundle = params;
            final boolean z3 = forMoveToBack;
            new Handler(getContext().getMainLooper()).post(new Runnable() {
                public final void run() {
                    try {
                        Util.b(activityManager2, activity2, runningTaskInfo2, z, z2, bundle, z3);
                    } catch (Throwable t) {
                        LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t));
                    }
                }
            });
        }
    }

    public static void moveTaskToFront(ActivityManager activityManager, Activity activity, int runningTaskInfoId, boolean enter, boolean fromForeground, Bundle params, boolean forMoveToBack) {
        RunningTaskInfo runningTaskInfo = null;
        Iterator<RunningTaskInfo> it = activityManager.getRunningTasks(Integer.MAX_VALUE).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            RunningTaskInfo runningTaskInfoItem = it.next();
            if (runningTaskInfoItem.id == runningTaskInfoId) {
                runningTaskInfo = runningTaskInfoItem;
                break;
            }
        }
        if (runningTaskInfo != null) {
            final RunningTaskInfo finalRunningTaskInfo = runningTaskInfo;
            final ActivityManager activityManager2 = activityManager;
            final Activity activity2 = activity;
            final boolean z = enter;
            final boolean z2 = fromForeground;
            final Bundle bundle = params;
            final boolean z3 = forMoveToBack;
            new Handler(getContext().getMainLooper()).post(new Runnable() {
                public final void run() {
                    try {
                        Util.b(activityManager2, activity2, finalRunningTaskInfo, z, z2, bundle, z3);
                    } catch (Throwable t) {
                        LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t));
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public static void b(ActivityManager activityManager, Activity activity, RunningTaskInfo runningTaskInfo, boolean enter, boolean fromForeground, Bundle params, boolean forMoveToBack) {
        int animIn;
        int animOut;
        LoggerFactory.getTraceLogger().debug(Const.TAG, "moveTaskToFront from activity " + activity + " to runningTaskInfo.baseActivity " + runningTaskInfo.baseActivity + " enter " + enter + " fromForeground " + fromForeground + " params " + params);
        if (activity != null && runningTaskInfo.baseActivity != null) {
            Intent resultIntent = new Intent();
            resultIntent.setComponent(runningTaskInfo.baseActivity);
            resultIntent.addFlags(805306368);
            if (params != null && "true".equalsIgnoreCase(params.getString("CLEAR_TOP_APP_WHEN_RESTART"))) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "DO CLEAR_TOP_APP_WHEN_RESTART case 1");
                resultIntent.addFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
            }
            if (params != null) {
                resultIntent.putExtras(params);
            }
            if (Config.A && forMoveToBack && !runningTaskInfo.baseActivity.getClassName().equals(Const.a)) {
                resultIntent.putExtra("IS_LITE_MOVE_TASK", true);
                LoggerFactory.getTraceLogger().debug(Const.TAG, "DO ADD_IS_LITE_MOVE_TASK");
            }
            if (Const.SchemeStartActivity.equals(activity.getClass().getName())) {
                fromForeground = false;
            }
            if (fromForeground) {
                if (enter) {
                    animIn = c((String) "push_up_in_short");
                    animOut = c((String) "lite_fading_out");
                } else {
                    animIn = c((String) "lite_fading_in");
                    animOut = c((String) "push_down_out_short");
                }
                if (VERSION.SDK_INT >= 16) {
                    ActivityCompat.startActivity(activity, resultIntent, ActivityOptionsCompat.makeCustomAnimation(activity, animIn, animOut).toBundle());
                } else {
                    activity.startActivity(resultIntent);
                }
                try {
                    activity.overridePendingTransition(animIn, animOut);
                } catch (Throwable t) {
                    LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t));
                }
            } else {
                getContext().startActivity(resultIntent);
            }
        } else if (activityManager != null) {
            if (params != null && "true".equalsIgnoreCase(params.getString("CLEAR_TOP_APP_WHEN_RESTART")) && isMainProcess()) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "DO CLEAR_TOP_APP_WHEN_RESTART case 2");
                getMicroAppContext().getTopApplication().destroy(new Bundle());
            }
            if (VERSION.SDK_INT >= 16) {
                activityManager.moveTaskToFront(runningTaskInfo.id, 2, params);
            } else {
                activityManager.moveTaskToFront(runningTaskInfo.id, 2);
            }
        }
    }

    private static int c(String anim) {
        return getContext().getResources().getIdentifier(anim, ResUtils.ANIM, getContext().getPackageName());
    }

    static String a(Bundle bundle) {
        if (bundle == null) {
            return "";
        }
        JSONObject jsonObject = new JSONObject();
        for (String key : bundle.keySet()) {
            try {
                Object value = bundle.get(key);
                if (value != null) {
                    jsonObject.put(key, value);
                }
            } catch (Exception e) {
            }
        }
        return jsonObject.toJSONString();
    }

    static Bundle a(String json) {
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(json)) {
            JSONObject jsonObject = JSONObject.parseObject(json);
            for (String key : jsonObject.keySet()) {
                Object value = jsonObject.get(key);
                if (value instanceof Integer) {
                    bundle.putInt(key, ((Integer) value).intValue());
                } else if (value instanceof Double) {
                    bundle.putDouble(key, ((Double) value).doubleValue());
                } else if (value instanceof Boolean) {
                    bundle.putBoolean(key, ((Boolean) value).booleanValue());
                } else if (value instanceof String) {
                    bundle.putString(key, (String) value);
                }
            }
        }
        return bundle;
    }

    static final synchronized void b(String uid) {
        synchronized (Util.class) {
            try {
                if (!TextUtils.isEmpty(LoggerFactory.getLogContext().getUserId()) || TextUtils.isEmpty(uid)) {
                    LoggerFactory.getTraceLogger().info(Const.TAG, "no need set uid: " + LoggerFactory.getLogContext().getUserId());
                } else {
                    LoggerFactory.getLogContext().setUserId(uid);
                    LoggerFactory.getTraceLogger().info(Const.TAG, "set uid: " + uid);
                }
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error((String) Const.TAG, t);
            }
        }
        return;
    }

    static final String c() {
        String uid = "";
        try {
            Object authService = getMicroAppContext().findServiceByInterface("com.alipay.mobile.framework.service.ext.security.AuthService");
            if (authService != null) {
                Object userInfo = ReflectUtil.getMethod(Class.forName("com.alipay.mobile.framework.service.ext.security.AuthService"), "getUserInfo").invoke(authService, new Object[0]);
                if (userInfo != null) {
                    uid = (String) ReflectUtil.getMethod(Class.forName("com.alipay.mobile.framework.service.ext.security.bean.UserInfo"), "getUserId").invoke(userInfo, new Object[0]);
                } else {
                    LoggerFactory.getTraceLogger().warn((String) Const.TAG, (String) "userInfo is null!");
                }
                LoggerFactory.getTraceLogger().info(Const.TAG, "get uid: " + uid);
                return uid;
            }
            LoggerFactory.getTraceLogger().warn((String) Const.TAG, (String) "authService is null!");
            LoggerFactory.getTraceLogger().info(Const.TAG, "get uid: " + uid);
            return uid;
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG, t);
        }
    }

    public static final boolean needSupportLiteProcess() {
        String packageName = getContext().getPackageName();
        if (TextUtils.isEmpty(packageName) || !packageName.contains("com.eg.android.AlipayGphone")) {
            LoggerFactory.getTraceLogger().info(Const.TAG, "NotSupportLiteProcess");
            return false;
        }
        LoggerFactory.getTraceLogger().info(Const.TAG, "SupportLiteProcess");
        return true;
    }

    public static final void log(String tag, String content) {
        try {
            Log.class.getDeclaredMethod("e", new Class[]{String.class, String.class}).invoke(null, new Object[]{tag, content});
        } catch (Throwable th) {
        }
    }
}
