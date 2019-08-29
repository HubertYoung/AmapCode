package com.alipay.mobile.liteprocess.advice;

import android.util.Pair;
import com.alipay.mobile.aspect.Advice;
import com.alipay.mobile.liteprocess.Util;

abstract class AbsLiteProcessAdvice implements Advice {
    /* access modifiers changed from: protected */
    public abstract Pair<Boolean, Object> a(Object obj, Object[] objArr);

    /* access modifiers changed from: protected */
    public abstract boolean a(String str, Object obj);

    /* access modifiers changed from: protected */
    public abstract Pair<Boolean, Object> b(Object obj, Object[] objArr);

    AbsLiteProcessAdvice() {
    }

    public void onCallBefore(String s, Object o, Object[] objects) {
    }

    public Pair<Boolean, Object> onCallAround(String s, Object o, Object[] objects) {
        return null;
    }

    public void onCallAfter(String s, Object o, Object[] objects) {
    }

    public void onExecutionBefore(String s, Object o, Object[] objects) {
    }

    public Pair<Boolean, Object> onExecutionAround(String pointCut, Object thisPoint, Object[] args) {
        if (!a(pointCut, thisPoint)) {
            return null;
        }
        if (Util.isLiteProcess()) {
            return b(thisPoint, args);
        }
        if (Util.isMainProcess()) {
            return a(thisPoint, args);
        }
        return null;
    }

    public void onExecutionAfter(String s, Object o, Object[] objects) {
    }
}
