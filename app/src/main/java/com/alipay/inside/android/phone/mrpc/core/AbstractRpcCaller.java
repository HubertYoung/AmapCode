package com.alipay.inside.android.phone.mrpc.core;

import java.lang.reflect.Method;

public abstract class AbstractRpcCaller implements RpcCaller {
    protected String mContentType;
    protected int mId;
    protected Method mMethod;
    protected String mOperationType;
    protected byte[] mReqData;
    protected boolean mResetCookie;

    public AbstractRpcCaller(Method method, int i, String str, byte[] bArr, String str2, boolean z) {
        this.mMethod = method;
        this.mId = i;
        this.mOperationType = str;
        this.mReqData = bArr;
        this.mContentType = str2;
        this.mResetCookie = z;
    }
}
