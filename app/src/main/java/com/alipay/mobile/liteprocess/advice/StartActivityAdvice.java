package com.alipay.mobile.liteprocess.advice;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Pair;
import com.alipay.mobile.aspect.Advice;
import com.alipay.mobile.aspect.FrameworkPointCutManager;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.liteprocess.Const;
import com.alipay.mobile.liteprocess.LiteProcessServerManager;

public class StartActivityAdvice extends AbsLiteProcessAdvice {
    private static StartActivityAdvice a;

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
            synchronized (StartActivityAdvice.class) {
                if (a == null) {
                    a = new StartActivityAdvice();
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "register startActivityAdvice");
                    FrameworkPointCutManager.getInstance().registerPointCutAdvice((String) PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTACTIVITY2, (Advice) a);
                    FrameworkPointCutManager.getInstance().registerPointCutAdvice((String) PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTEXTACTIVITY, (Advice) a);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final Pair<Boolean, Object> a(Object thisPoint, Object[] args) {
        if (!StartAppAdvice.a) {
            return null;
        }
        MicroApplication microApplication = null;
        Intent intent = null;
        if (args != null && args.length >= 2) {
            if (args[0] instanceof MicroApplication) {
                microApplication = args[0];
            }
            if (args[1] instanceof Intent) {
                intent = args[1];
            }
        }
        if (intent == null) {
            return null;
        }
        LoggerFactory.getTraceLogger().debug(Const.TAG, "StartActivityAdvice onExecutionAround intent = " + intent.toUri(1));
        if (LiteProcessServerManager.g().startActivityFromLiteProcessIfNeeded(microApplication, intent)) {
            return new Pair(Boolean.valueOf(true), null);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final Pair<Boolean, Object> b(Object thisPoint, Object[] args) {
        return null;
    }

    /* access modifiers changed from: protected */
    public final boolean a(String pointCut, Object thisPoint) {
        if (TextUtils.equals(pointCut, PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTACTIVITY2) || TextUtils.equals(pointCut, PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTEXTACTIVITY)) {
            return true;
        }
        return false;
    }
}
