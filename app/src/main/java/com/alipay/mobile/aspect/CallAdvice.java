package com.alipay.mobile.aspect;

import android.util.Pair;

public abstract class CallAdvice implements Advice {
    public void onExecutionBefore(String pointCut, Object thisPoint, Object[] args) {
    }

    public Pair<Boolean, Object> onExecutionAround(String pointCut, Object thisPoint, Object[] args) {
        return null;
    }

    public void onExecutionAfter(String pointCut, Object thisPoint, Object[] args) {
    }
}
