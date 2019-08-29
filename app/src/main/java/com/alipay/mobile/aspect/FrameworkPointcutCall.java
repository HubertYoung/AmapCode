package com.alipay.mobile.aspect;

import android.util.Pair;
import com.alipay.mobile.framework.pipeline.analysis.AnalysedRunnableManager;
import com.alipay.mobile.quinox.utils.StringUtil;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class FrameworkPointcutCall {
    static final String TAG = "PointCutCall";
    private static boolean a;

    public static void onCallBefore(String pointCut, Object thisPoint, Object[] args) {
        List advices = FrameworkPointCutManager.getInstance().getAdviceOnPointCut(pointCut);
        if (advices != null && !advices.isEmpty()) {
            for (Advice advice : advices) {
                if (advice != null) {
                    String runnableName = advice.getClass().getName();
                    try {
                        AnalysedRunnableManager.startRecord(runnableName);
                    } catch (Throwable t) {
                        if (!a) {
                            a = true;
                            TraceLogger.e((String) TAG, t);
                        }
                    }
                    try {
                        advice.onCallBefore(pointCut, thisPoint, args);
                    } catch (Throwable e) {
                        TraceLogger.w((String) TAG, e);
                    }
                    try {
                        AnalysedRunnableManager.endRecord(runnableName);
                    } catch (Throwable t2) {
                        if (!a) {
                            a = true;
                            TraceLogger.e((String) TAG, t2);
                        }
                    }
                }
            }
        }
    }

    public static Pair<Boolean, Object> onCallAround(String pointCut, Object thisPoint, Object[] args) {
        return onCallAround(pointCut, thisPoint, args, null);
    }

    public static Pair<Boolean, Object> onCallAround(String pointCut, Object thisPoint, Object[] args, Set<Advice> rejectAdvices) {
        Pair aroundResult = null;
        List advices = FrameworkPointCutManager.getInstance().getAdviceOnPointCut(pointCut);
        if (advices != null && !advices.isEmpty()) {
            Iterator<Advice> it = advices.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Advice advice = it.next();
                if (advice != null) {
                    String runnableName = advice.getClass().getName();
                    try {
                        AnalysedRunnableManager.startRecord(runnableName);
                    } catch (Throwable t) {
                        if (!a) {
                            a = true;
                            TraceLogger.e((String) TAG, t);
                        }
                    }
                    try {
                        aroundResult = advice.onCallAround(pointCut, thisPoint, args);
                    } catch (Throwable e) {
                        TraceLogger.w((String) TAG, e);
                    }
                    try {
                        AnalysedRunnableManager.endRecord(runnableName);
                    } catch (Throwable t2) {
                        if (!a) {
                            a = true;
                            TraceLogger.e((String) TAG, t2);
                        }
                    }
                    if (aroundResult != null && ((Boolean) aroundResult.first).booleanValue()) {
                        if (rejectAdvices != null) {
                            rejectAdvices.add(advice);
                        }
                        TraceLogger.i((String) TAG, "onCallAround(pointCut=[" + pointCut + "], thisPoint=" + thisPoint + ", args=" + StringUtil.array2String(args) + ") return true : " + StringUtil.collection2String(advices) + ", advice=" + advice);
                    }
                }
            }
        }
        return aroundResult;
    }

    public static void onCallAfter(String pointCut, Object thisPoint, Object[] args) {
        List advices = FrameworkPointCutManager.getInstance().getAdviceOnPointCut(pointCut);
        if (advices != null && !advices.isEmpty()) {
            for (Advice advice : advices) {
                if (advice != null) {
                    String runnableName = advice.getClass().getName();
                    try {
                        AnalysedRunnableManager.startRecord(runnableName);
                    } catch (Throwable t) {
                        if (!a) {
                            a = true;
                            TraceLogger.e((String) TAG, t);
                        }
                    }
                    try {
                        advice.onCallAfter(pointCut, thisPoint, args);
                    } catch (Throwable e) {
                        TraceLogger.w((String) TAG, e);
                    }
                    try {
                        AnalysedRunnableManager.endRecord(runnableName);
                    } catch (Throwable t2) {
                        if (!a) {
                            a = true;
                            TraceLogger.e((String) TAG, t2);
                        }
                    }
                }
            }
        }
    }
}
