package com.alipay.mobile.liteprocess.advice;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Pair;
import com.alipay.mobile.aspect.Advice;
import com.alipay.mobile.aspect.FrameworkPointCutManager;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.beehive.util.MiscUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.liteprocess.Const;
import com.alipay.mobile.liteprocess.LiteProcessActivity;
import com.alipay.mobile.liteprocess.LiteProcessClientManager;
import com.alipay.mobile.liteprocess.Util;
import com.alipay.mobile.liteprocess.perf.PerformanceLogger;

public class ActivityCreateAdvice extends AbsLiteProcessAdvice {
    private static ActivityCreateAdvice a;

    public /* bridge */ /* synthetic */ void onCallAfter(String str, Object obj, Object[] objArr) {
        super.onCallAfter(str, obj, objArr);
    }

    public /* bridge */ /* synthetic */ Pair onCallAround(String str, Object obj, Object[] objArr) {
        return super.onCallAround(str, obj, objArr);
    }

    public /* bridge */ /* synthetic */ void onCallBefore(String str, Object obj, Object[] objArr) {
        super.onCallBefore(str, obj, objArr);
    }

    public /* bridge */ /* synthetic */ Pair onExecutionAround(String str, Object obj, Object[] objArr) {
        return super.onExecutionAround(str, obj, objArr);
    }

    public /* bridge */ /* synthetic */ void onExecutionBefore(String str, Object obj, Object[] objArr) {
        super.onExecutionBefore(str, obj, objArr);
    }

    public static void register() {
        if (a == null) {
            synchronized (ActivityCreateAdvice.class) {
                if (a == null) {
                    a = new ActivityCreateAdvice();
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "register activityCreateAdvice");
                    FrameworkPointCutManager.getInstance().registerPointCutAdvice((String) PointCutConstants.BASEACTIVITY_ONCREATE, (Advice) a);
                    FrameworkPointCutManager.getInstance().registerPointCutAdvice((String) PointCutConstants.BASEFRAGMENTACTIVITY_ONCREATE, (Advice) a);
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
        return null;
    }

    private static void a(Activity activity) {
        LoggerFactory.getTraceLogger().warn((String) Const.TAG, "ActivityCreateAdvice illegal state " + activity.getClass().getName());
        int lpid = Util.getLpid();
        if (lpid != 0) {
            Intent intent = new Intent();
            intent.setAction(Const.RESTORE_APP);
            intent.setClass(activity, LiteProcessActivity.ACTIVITY_CLASSES[lpid - 1]);
            intent.setFlags(268435456);
            activity.startActivity(intent);
            activity.finish();
        }
    }

    /* access modifiers changed from: protected */
    public final boolean a(String pointCut, Object thisPoint) {
        if (thisPoint instanceof LiteProcessActivity) {
            return false;
        }
        if (TextUtils.equals(pointCut, PointCutConstants.BASEACTIVITY_ONCREATE) || TextUtils.equals(pointCut, PointCutConstants.BASEFRAGMENTACTIVITY_ONCREATE)) {
            return true;
        }
        return false;
    }

    public void onExecutionAfter(String pointCut, Object thisPoint, Object[] args) {
        String appId;
        super.onExecutionAfter(pointCut, thisPoint, args);
        if (thisPoint instanceof Activity) {
            if (Util.isLiteProcess() && !LiteProcessClientManager.hasStartApp) {
                a((Activity) thisPoint);
            }
            MicroApplication app = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp();
            if (app == null || app.getAppId() == null) {
                TraceLogger traceLogger = LoggerFactory.getTraceLogger();
                StringBuilder append = new StringBuilder("recordAppStarted activity create app == null ? ").append(app == null).append(", app id == null ? ");
                if (app == null) {
                    appId = MiscUtil.NULL_STR;
                } else {
                    appId = app.getAppId();
                }
                traceLogger.debug(Const.TAG, append.append(appId).toString());
                Intent intent = ((Activity) thisPoint).getIntent();
                if (intent != null) {
                    String appId2 = intent.getStringExtra("appId");
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "recordAppStarted activity create appid from intent = " + appId2);
                    if (appId2 == null) {
                        LoggerFactory.getTraceLogger().warn((String) Const.TAG, (String) "recordAppStarted, but app id is null!!");
                    } else {
                        PerformanceLogger.recordAppStarted(appId2);
                    }
                }
            } else {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "recordAppStarted activity create appid = " + app.getAppId());
                PerformanceLogger.recordAppStarted(app.getAppId());
            }
        }
    }
}
