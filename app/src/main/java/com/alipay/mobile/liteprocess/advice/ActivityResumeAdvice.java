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
import com.alipay.mobile.liteprocess.perf.PerformanceLogger;

public class ActivityResumeAdvice extends AbsLiteProcessAdvice {
    private static ActivityResumeAdvice a;

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
            synchronized (ActivityResumeAdvice.class) {
                if (a == null) {
                    a = new ActivityResumeAdvice();
                    LoggerFactory.getTraceLogger().debug("ActivityResumeAdvice", "register ActivityResumeAdvice");
                    FrameworkPointCutManager.getInstance().registerPointCutAdvice((String) PointCutConstants.BASEACTIVITY_ONRESUME, (Advice) a);
                    FrameworkPointCutManager.getInstance().registerPointCutAdvice((String) PointCutConstants.BASEFRAGMENTACTIVITY_ONRESUME, (Advice) a);
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

    /* access modifiers changed from: protected */
    public final boolean a(String pointCut, Object thisPoint) {
        if (TextUtils.equals(pointCut, PointCutConstants.BASEACTIVITY_ONRESUME) || TextUtils.equals(pointCut, PointCutConstants.BASEFRAGMENTACTIVITY_ONRESUME)) {
            return true;
        }
        return false;
    }

    public void onExecutionAfter(String s, Object o, Object[] objects) {
        String appId;
        super.onExecutionAfter(s, o, objects);
        LoggerFactory.getTraceLogger().debug("ActivityResumeAdvice", "activity resume:" + o);
        if (o instanceof Activity) {
            MicroApplication app = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp();
            if (app == null || app.getAppId() == null) {
                TraceLogger traceLogger = LoggerFactory.getTraceLogger();
                StringBuilder append = new StringBuilder("recordAppStarted activity resume app == null ? ").append(app == null).append(", app id == null ? ");
                if (app == null) {
                    appId = MiscUtil.NULL_STR;
                } else {
                    appId = app.getAppId();
                }
                traceLogger.debug("ActivityResumeAdvice", append.append(appId).toString());
                Intent intent = ((Activity) o).getIntent();
                if (intent != null) {
                    String appId2 = intent.getStringExtra("appId");
                    LoggerFactory.getTraceLogger().debug("ActivityResumeAdvice", "recordAppStarted activity resume appid from intent = " + appId2);
                    if (appId2 != null) {
                        PerformanceLogger.recordAppStarted(appId2);
                        return;
                    }
                    return;
                }
                return;
            }
            LoggerFactory.getTraceLogger().debug("ActivityResumeAdvice", "recordAppStarted activity resume appid = " + app.getAppId());
            PerformanceLogger.recordAppStarted(app.getAppId());
        }
    }
}
