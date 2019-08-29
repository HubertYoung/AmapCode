package com.alipay.mobile.aspect;

import android.util.Pair;

public abstract class ExecutionAdvice implements Advice {
    public void onCallBefore(String pointCut, Object targetPoint, Object[] args) {
    }

    public Pair<Boolean, Object> onCallAround(String pointCut, Object targetPoint, Object[] args) {
        return null;
    }

    public void onCallAfter(String pointCut, Object targetPoint, Object[] args) {
    }
}
