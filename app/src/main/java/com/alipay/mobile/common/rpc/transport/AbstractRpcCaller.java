package com.alipay.mobile.common.rpc.transport;

import java.lang.reflect.Method;

public abstract class AbstractRpcCaller implements RpcCaller {
    protected String mContentType;
    protected int mId;
    protected Method mMethod;
    protected String mOperationType;
    protected byte[] mReqData;
    protected boolean mResetCookie;

    public AbstractRpcCaller(Method method, int id, String op, byte[] reqData, String contentType, boolean resetCookie) {
        this.mMethod = method;
        this.mId = id;
        this.mOperationType = op;
        this.mReqData = reqData;
        this.mContentType = contentType;
        this.mResetCookie = resetCookie;
    }
}
