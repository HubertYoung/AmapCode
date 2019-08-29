package com.alipay.mobile.liteprocess.advice;

import android.text.TextUtils;
import android.util.Pair;
import android.view.KeyEvent;
import com.alipay.mobile.aspect.Advice;
import com.alipay.mobile.aspect.FrameworkPointCutManager;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.liteprocess.Const;
import com.alipay.mobile.liteprocess.LiteProcessClientManager;

public class ActivityKeyEventAdvice extends AbsLiteProcessAdvice {
    private static ActivityKeyEventAdvice a;

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
            synchronized (ActivityKeyEventAdvice.class) {
                if (a == null) {
                    a = new ActivityKeyEventAdvice();
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "register activityKeyEventAdvice");
                    FrameworkPointCutManager.getInstance().registerPointCutAdvice((String) PointCutConstants.BASEACTIVITY_DISPATCHKEYEVENT, (Advice) a);
                    FrameworkPointCutManager.getInstance().registerPointCutAdvice((String) PointCutConstants.BASEFRAGMENTACTIVITY_DISPATCHKEYEVENT, (Advice) a);
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
        if (args != null && args.length > 0 && (args[0] instanceof KeyEvent)) {
            KeyEvent ev = args[0];
            if (ev.getKeyCode() == 4 && ev.getAction() == 0 && ActivityBackAdvice.isTaskRoot(thisPoint)) {
                ActivityBackAdvice.moveTaskToBack(thisPoint);
                return new Pair<>(Boolean.valueOf(true), null);
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final boolean a(String pointCut, Object thisPoint) {
        if ((TextUtils.equals(pointCut, PointCutConstants.BASEACTIVITY_DISPATCHKEYEVENT) || TextUtils.equals(pointCut, PointCutConstants.BASEFRAGMENTACTIVITY_DISPATCHKEYEVENT)) && !LiteProcessClientManager.getHookBackKeyBlackList().contains(thisPoint.getClass())) {
            return true;
        }
        return false;
    }
}
