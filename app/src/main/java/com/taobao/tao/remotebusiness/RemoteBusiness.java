package com.taobao.tao.remotebusiness;

import android.content.Context;
import mtopsdk.mtop.domain.MtopRequest;
import mtopsdk.mtop.intf.Mtop;

@Deprecated
public class RemoteBusiness extends MtopBusiness {
    protected RemoteBusiness(Mtop mtop, ffb ffb, String str) {
        super(mtop, ffb, str);
    }

    protected RemoteBusiness(Mtop mtop, MtopRequest mtopRequest, String str) {
        super(mtop, mtopRequest, str);
    }

    @Deprecated
    public static void init(Context context, String str) {
        Mtop.a(context, str);
    }

    public static RemoteBusiness build(ffb ffb, String str) {
        return new RemoteBusiness(Mtop.a((Context) null, str), ffb, str);
    }

    public static RemoteBusiness build(ffb ffb) {
        return build(ffb, (String) null);
    }

    public static RemoteBusiness build(MtopRequest mtopRequest, String str) {
        return new RemoteBusiness(Mtop.a((Context) null, str), mtopRequest, str);
    }

    public static RemoteBusiness build(MtopRequest mtopRequest) {
        return build(mtopRequest, (String) null);
    }

    @Deprecated
    public static RemoteBusiness build(Context context, ffb ffb, String str) {
        init(context, str);
        return build(ffb, str);
    }

    @Deprecated
    public static RemoteBusiness build(Context context, MtopRequest mtopRequest, String str) {
        init(context, str);
        return build(mtopRequest, str);
    }

    @Deprecated
    public RemoteBusiness setErrorNotifyAfterCache(boolean z) {
        return (RemoteBusiness) super.setErrorNotifyAfterCache(z);
    }

    @Deprecated
    public RemoteBusiness registeListener(few few) {
        return (RemoteBusiness) super.registerListener(few);
    }

    @Deprecated
    public RemoteBusiness registeListener(IRemoteListener iRemoteListener) {
        return (RemoteBusiness) super.registerListener(iRemoteListener);
    }

    public RemoteBusiness retryTime(int i) {
        return (RemoteBusiness) super.retryTime(i);
    }

    @Deprecated
    public RemoteBusiness showLoginUI(boolean z) {
        return (RemoteBusiness) super.showLoginUI(z);
    }

    @Deprecated
    public RemoteBusiness setBizId(int i) {
        return (RemoteBusiness) super.setBizId(i);
    }

    @Deprecated
    public RemoteBusiness addListener(few few) {
        return (RemoteBusiness) super.addListener(few);
    }

    @Deprecated
    public void setErrorNotifyNeedAfterCache(boolean z) {
        super.setErrorNotifyAfterCache(z);
    }

    @Deprecated
    public RemoteBusiness reqContext(Object obj) {
        return (RemoteBusiness) super.reqContext(obj);
    }
}
