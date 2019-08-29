package com.alipay.mobile.aspect;

import android.util.Pair;
import com.alipay.mobile.framework.pipeline.analysis.AnalysedRunnableManager;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.quinox.utils.StringUtil;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class FrameworkPointcutExecution {
    static final String TAG = "PointCutExecution";
    private static final List<String> a = Arrays.asList(new String[]{H5Utils.SCAN_APP_ID});
    private static final List<String> b = Arrays.asList(new String[]{"com.alipay.android.phone.businesscommon.message.MessageSwitcherAdvice"});
    private static boolean c;

    public static void onExecutionBefore(String pointCut, Object thisPoint, Object[] args) {
        List advices = FrameworkPointCutManager.getInstance().getAdviceOnPointCut(pointCut);
        if (advices != null && !advices.isEmpty()) {
            boolean isPrivileged = a(args);
            for (Advice advice : advices) {
                if (advice != null) {
                    if (isPrivileged) {
                        try {
                            if (b.contains(advice.getClass().getName())) {
                            }
                        } catch (Throwable e) {
                            TraceLogger.w((String) TAG, e);
                        }
                    }
                    String runnableName = advice.getClass().getName();
                    try {
                        AnalysedRunnableManager.startRecord(runnableName);
                    } catch (Throwable t) {
                        if (!c) {
                            c = true;
                            TraceLogger.e((String) TAG, t);
                        }
                    }
                    advice.onExecutionBefore(pointCut, thisPoint, args);
                    AnalysedRunnableManager.endRecord(runnableName);
                }
            }
        }
    }

    public static Pair<Boolean, Object> onExecutionAround(String pointCut, Object thisPoint, Object[] args) {
        return onExecutionAround(pointCut, thisPoint, args, null);
    }

    public static Pair<Boolean, Object> onExecutionAround(String pointCut, Object thisPoint, Object[] args, Set<Advice> rejectAdvices) {
        Pair aroundResult = null;
        List advices = FrameworkPointCutManager.getInstance().getAdviceOnPointCut(pointCut);
        if (advices != null && !advices.isEmpty()) {
            boolean isPrivileged = a(args);
            Iterator<Advice> it = advices.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Advice advice = it.next();
                if (advice != null) {
                    if (isPrivileged) {
                        try {
                            if (b.contains(advice.getClass().getName())) {
                                continue;
                            }
                        } catch (Throwable e) {
                            TraceLogger.w((String) TAG, e);
                        }
                    }
                    String runnableName = advice.getClass().getName();
                    AnalysedRunnableManager.startRecord(runnableName);
                    aroundResult = advice.onExecutionAround(pointCut, thisPoint, args);
                    try {
                        AnalysedRunnableManager.endRecord(runnableName);
                    } catch (Throwable t) {
                        if (!c) {
                            c = true;
                            TraceLogger.e((String) TAG, t);
                        }
                    }
                    if (aroundResult != null && ((Boolean) aroundResult.first).booleanValue()) {
                        if (rejectAdvices != null) {
                            rejectAdvices.add(advice);
                        }
                        TraceLogger.i((String) TAG, "onExecutionAround(pointCut=[" + pointCut + "], thisPoint=" + thisPoint + ", args=" + StringUtil.array2String(args) + ") return true : " + StringUtil.collection2String(advices) + ", advice=" + advice);
                    }
                }
            }
        }
        return aroundResult;
    }

    public static void onExecutionAfter(String pointCut, Object thisPoint, Object[] args) {
        List advices = FrameworkPointCutManager.getInstance().getAdviceOnPointCut(pointCut);
        if (advices != null && !advices.isEmpty()) {
            boolean isPrivileged = a(args);
            for (Advice advice : advices) {
                if (advice != null) {
                    if (isPrivileged) {
                        try {
                            if (b.contains(advice.getClass().getName())) {
                            }
                        } catch (Throwable e) {
                            TraceLogger.w((String) TAG, e);
                        }
                    }
                    String runnableName = advice.getClass().getName();
                    try {
                        AnalysedRunnableManager.startRecord(runnableName);
                    } catch (Throwable t) {
                        if (!c) {
                            c = true;
                            TraceLogger.e((String) TAG, t);
                        }
                    }
                    advice.onExecutionAfter(pointCut, thisPoint, args);
                    AnalysedRunnableManager.endRecord(runnableName);
                }
            }
        }
    }

    private static boolean a(Object[] args) {
        if (args != null && args.length > 1 && (args[1] instanceof String)) {
            if (a.contains(args[1])) {
                return true;
            }
        }
        return false;
    }
}
