package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.ComputeCallBack;

public interface Computable<A, V> {
    V compute(A a, ComputeCallBack computeCallBack);
}
