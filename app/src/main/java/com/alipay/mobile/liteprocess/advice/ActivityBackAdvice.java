package com.alipay.mobile.liteprocess.advice;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.alipay.mobile.aspect.Advice;
import com.alipay.mobile.aspect.FrameworkPointCutManager;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.liteprocess.Const;
import com.alipay.mobile.liteprocess.LiteProcessActivity;
import com.alipay.mobile.liteprocess.LiteProcessClientManager;
import com.alipay.mobile.liteprocess.Util;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.List;

public class ActivityBackAdvice extends AbsLiteProcessAdvice {
    private static ActivityBackAdvice a;
    private static long b = 0;

    public /* bridge */ /* synthetic */ void onCallAfter(String str, Object obj, Object[] objArr) {
        super.onCallAfter(str, obj, objArr);
    }

    public /* bridge */ /* synthetic */ Pair onCallAround(String str, Object obj, Object[] objArr) {
        return super.onCallAround(str, obj, objArr);
    }

    public /* bridge */ /* synthetic */ void onCallBefore(String str, Object obj, Object[] objArr) {
        super.onCallBefore(str, obj, objArr);
    }

    public /* bridge */ /* synthetic */ void onExecutionAfter(String str, Object obj, Object[] objArr) {
        super.onExecutionAfter(str, obj, objArr);
    }

    public /* bridge */ /* synthetic */ Pair onExecutionAround(String str, Object obj, Object[] objArr) {
        return super.onExecutionAround(str, obj, objArr);
    }

    public /* bridge */ /* synthetic */ void onExecutionBefore(String str, Object obj, Object[] objArr) {
        super.onExecutionBefore(str, obj, objArr);
    }

    public static void register() {
        if (a == null) {
            synchronized (ActivityBackAdvice.class) {
                if (a == null) {
                    a = new ActivityBackAdvice();
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "register activityBackAdvice");
                    FrameworkPointCutManager.getInstance().registerPointCutAdvice((String) PointCutConstants.BASEACTIVITY_ONBACKPRESSED, (Advice) a);
                    FrameworkPointCutManager.getInstance().registerPointCutAdvice((String) PointCutConstants.BASEFRAGMENTACTIVITY_ONBACKPRESSED, (Advice) a);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final Pair<Boolean, Object> a(Object thisPoint, Object[] args) {
        return null;
    }

    /* access modifiers changed from: protected */
    public final Pair<Boolean, Object> b(Object thisPoint, Object[] args) {
        if (!isTaskRoot(thisPoint)) {
            return null;
        }
        moveTaskToBack(thisPoint);
        return new Pair<>(Boolean.valueOf(true), null);
    }

    /* access modifiers changed from: protected */
    public final boolean a(String pointCut, Object thisPoint) {
        if ((TextUtils.equals(pointCut, PointCutConstants.BASEACTIVITY_ONBACKPRESSED) || TextUtils.equals(pointCut, PointCutConstants.BASEFRAGMENTACTIVITY_ONBACKPRESSED)) && !LiteProcessClientManager.getHookBackKeyBlackList().contains(thisPoint.getClass())) {
            return true;
        }
        return false;
    }

    public static boolean isTaskRoot(Object thisPoint) {
        if (!(thisPoint instanceof Activity) || !((Activity) thisPoint).isTaskRoot()) {
            return false;
        }
        return true;
    }

    public static void moveTaskToBack(final Object thisPoint) {
        long now = System.currentTimeMillis();
        if (now - b < 1000) {
            LoggerFactory.getTraceLogger().warn((String) Const.TAG, (String) "moveTaskToBack too close.");
            return;
        }
        b = now;
        LoggerFactory.getTraceLogger().debug(Const.TAG, "moveTaskToBack begin fromBaseActivity = " + LiteProcessActivity.fromBaseActivity);
        ActivityManager activityManager = (ActivityManager) Util.getContext().getSystemService(WidgetType.ACTIVITY);
        List recentTasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
        if (TextUtils.isEmpty(LiteProcessActivity.fromBaseActivity) || Const.SchemeStartActivity.equals(LiteProcessActivity.fromBaseActivity)) {
            a(thisPoint);
            LoggerFactory.getTraceLogger().debug(Const.TAG, "startLauncher fromBaseActivity = " + LiteProcessActivity.fromBaseActivity);
        } else {
            boolean ret = a(thisPoint, activityManager, recentTasks);
            LoggerFactory.getTraceLogger().info(Const.TAG, "moveFromTaskToFront " + ret);
            if (!ret) {
                a(thisPoint);
                return;
            } else if (thisPoint instanceof Activity) {
                new Handler(Util.getContext().getMainLooper()).post(new Runnable() {
                    public final void run() {
                        try {
                            ((Activity) thisPoint).moveTaskToBack(true);
                            LoggerFactory.getTraceLogger().debug(Const.TAG, "do moveTaskToBack");
                        } catch (Throwable t) {
                            LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t));
                        }
                    }
                });
            }
        }
        LiteProcessActivity.fromBaseActivity = null;
        LoggerFactory.getTraceLogger().debug(Const.TAG, "moveTaskToBack end fromBaseActivity = " + LiteProcessActivity.fromBaseActivity);
    }

    private static void a(Object thisPoint) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(268435456);
        if (thisPoint instanceof Activity) {
            ((Activity) thisPoint).startActivity(intent);
        } else {
            Util.getMicroAppContext().getApplicationContext().startActivity(intent);
        }
    }

    private static boolean a(Object thisPoint, ActivityManager activityManager, List<RunningTaskInfo> recentTasks) {
        for (RunningTaskInfo recentTask : recentTasks) {
            if (LiteProcessActivity.fromBaseActivity.equals(recentTask.baseActivity.getClassName())) {
                Util.moveTaskToFront(activityManager, (Activity) thisPoint, recentTask, false, true, (Bundle) null, true);
                LoggerFactory.getTraceLogger().debug(Const.TAG, "moveFromTaskToFront force fromBaseActivity = " + LiteProcessActivity.fromBaseActivity);
                return true;
            }
        }
        return false;
    }
}
