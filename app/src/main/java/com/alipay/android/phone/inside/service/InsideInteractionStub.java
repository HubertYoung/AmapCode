package com.alipay.android.phone.inside.service;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import com.alipay.android.phone.inside.api.IInsideInteraction.Stub;
import com.alipay.android.phone.inside.api.IRemoteServiceCallback;
import com.alipay.android.phone.inside.proxy.InsideInteractionProxy;

public class InsideInteractionStub extends Stub {
    private final InsideInteractionProxy a;
    private final Context b;
    private BinderStatus c;

    public InsideInteractionStub(Context context, BinderStatus binderStatus) {
        this(context);
        this.c = binderStatus;
    }

    public InsideInteractionStub(Context context) {
        this.c = BinderStatus.SUCCESS;
        this.b = context;
        this.a = new InsideInteractionProxy(context);
    }

    public int getBinderStatus() throws RemoteException {
        return this.c.getValue();
    }

    public Bundle interaction(Bundle bundle) throws RemoteException {
        return this.a.b(bundle);
    }

    public void registerCallback(IRemoteServiceCallback iRemoteServiceCallback) throws RemoteException {
        this.a.a(iRemoteServiceCallback);
    }

    public void unregisterCallback(IRemoteServiceCallback iRemoteServiceCallback) throws RemoteException {
        InsideInteractionProxy.a();
    }
}
