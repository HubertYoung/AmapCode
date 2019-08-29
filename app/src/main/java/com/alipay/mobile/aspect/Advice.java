package com.alipay.mobile.aspect;

import android.util.Pair;

public interface Advice {
    void onCallAfter(String str, Object obj, Object[] objArr);

    Pair<Boolean, Object> onCallAround(String str, Object obj, Object[] objArr);

    void onCallBefore(String str, Object obj, Object[] objArr);

    void onExecutionAfter(String str, Object obj, Object[] objArr);

    Pair<Boolean, Object> onExecutionAround(String str, Object obj, Object[] objArr);

    void onExecutionBefore(String str, Object obj, Object[] objArr);
}
