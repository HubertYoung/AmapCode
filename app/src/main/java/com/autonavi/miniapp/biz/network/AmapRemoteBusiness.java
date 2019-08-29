package com.autonavi.miniapp.biz.network;

import com.alipay.sdk.packet.d;
import com.amap.bundle.logs.AMapLog;
import com.taobao.tao.remotebusiness.IRemoteBaseListener;
import com.taobao.tao.remotebusiness.IRemoteListener;
import com.taobao.tao.remotebusiness.RemoteBusiness;
import com.ut.mini.UTAnalytics;
import com.ut.mini.UTHitBuilders.UTControlHitBuilder;
import mtopsdk.mtop.domain.BaseOutDo;
import mtopsdk.mtop.domain.MethodEnum;
import mtopsdk.mtop.domain.MtopResponse;

public class AmapRemoteBusiness {
    private static final String TAG = "AmapRemoteBusiness";
    private InternalListener InternalListener;
    private String mApiName;
    private String mApiVersion;
    private RemoteBusiness mInternalBusiness;
    public boolean mIsDoingReq = false;
    private boolean mNeedBlock = false;
    /* access modifiers changed from: private */
    public boolean needCommonErrorAction = true;

    class InternalListener implements IRemoteBaseListener {
        private static final String TAG = "KPRB.InternalListener";
        private IRemoteBaseListener mListener;

        public InternalListener(IRemoteBaseListener iRemoteBaseListener) {
            this.mListener = iRemoteBaseListener;
        }

        public IRemoteBaseListener getListener() {
            return this.mListener;
        }

        public void onSystemError(int i, MtopResponse mtopResponse, Object obj) {
            try {
                AmapRemoteBusiness.this.mIsDoingReq = false;
                if (AmapRemoteBusiness.this.needCommonErrorAction || this.mListener == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(mtopResponse.getRetCode());
                    AMapLog.debug("infoservice.miniapp", TAG, String.format("onSystemError: %s, %s", new Object[]{sb.toString(), mtopResponse.getRetMsg()}));
                    if (this.mListener != null) {
                        this.mListener.onSystemError(i, mtopResponse, obj);
                    }
                    return;
                }
                this.mListener.onSystemError(i, mtopResponse, obj);
            } catch (Exception e) {
                AmapRemoteBusiness.this.mtopExcepCatch(e);
            }
        }

        public void onSuccess(int i, MtopResponse mtopResponse, BaseOutDo baseOutDo, Object obj) {
            try {
                AmapRemoteBusiness.this.mIsDoingReq = false;
                if (this.mListener != null) {
                    this.mListener.onSuccess(i, mtopResponse, baseOutDo, obj);
                }
            } catch (Exception e) {
                AmapRemoteBusiness.this.mtopExcepCatch(e);
                if (this.mListener != null) {
                    this.mListener.onError(i, mtopResponse, obj);
                }
            }
        }

        public void onError(int i, MtopResponse mtopResponse, Object obj) {
            try {
                AmapRemoteBusiness.this.mIsDoingReq = false;
                if (AmapRemoteBusiness.this.needCommonErrorAction || this.mListener == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(mtopResponse.getRetCode());
                    AMapLog.debug("infoservice.miniapp", TAG, String.format("onError: %s, %s", new Object[]{sb.toString(), mtopResponse.getRetMsg()}));
                    if (this.mListener != null) {
                        this.mListener.onError(i, mtopResponse, obj);
                    }
                    return;
                }
                this.mListener.onError(i, mtopResponse, obj);
            } catch (Exception e) {
                AmapRemoteBusiness.this.mtopExcepCatch(e);
            }
        }
    }

    private AmapRemoteBusiness() {
    }

    private AmapRemoteBusiness(RemoteBusiness remoteBusiness) {
        this.mInternalBusiness = remoteBusiness;
    }

    private AmapRemoteBusiness(RemoteBusiness remoteBusiness, boolean z, String str, String str2) {
        this.mInternalBusiness = remoteBusiness;
        this.mNeedBlock = z;
        this.mApiName = str;
        this.mApiVersion = str2;
    }

    public static AmapRemoteBusiness build(ffb ffb, MethodEnum methodEnum) {
        RemoteBusiness build = RemoteBusiness.build(ffb);
        if (methodEnum != null) {
            build.reqMethod(methodEnum);
        }
        return new AmapRemoteBusiness(build, false, "", "");
    }

    public static AmapRemoteBusiness build(ffb ffb) {
        return build(ffb, null);
    }

    public AmapRemoteBusiness registeListener(IRemoteBaseListener iRemoteBaseListener) {
        this.InternalListener = new InternalListener(iRemoteBaseListener);
        this.mInternalBusiness.registeListener((IRemoteListener) this.InternalListener);
        return this;
    }

    public MtopResponse syncRequest() {
        if (this.mNeedBlock) {
            AMapLog.error("infoservice.miniapp", TAG, String.format("block req, api_name=%s, api_version=%s", new Object[]{this.mApiName, this.mApiVersion}));
            UTControlHitBuilder uTControlHitBuilder = new UTControlHitBuilder("Page_NETWORK", "BLOCK");
            StringBuilder sb = new StringBuilder();
            sb.append(this.mApiName);
            uTControlHitBuilder.setProperty(d.i, sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.mApiVersion);
            uTControlHitBuilder.setProperty(d.j, sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(this.mNeedBlock);
            uTControlHitBuilder.setProperty("need_block", sb3.toString());
            UTAnalytics.getInstance().getDefaultTracker().send(uTControlHitBuilder.build());
            if (this.InternalListener != null) {
                this.InternalListener.getListener();
            }
            return null;
        }
        this.mIsDoingReq = true;
        return this.mInternalBusiness.syncRequest();
    }

    public AmapRemoteBusiness startRequest() {
        if (this.mNeedBlock) {
            AMapLog.error("infoservice.miniapp", TAG, String.format("block req, api_name=%s, api_version=%s", new Object[]{this.mApiName, this.mApiVersion}));
            UTControlHitBuilder uTControlHitBuilder = new UTControlHitBuilder("Page_NETWORK", "BLOCK");
            StringBuilder sb = new StringBuilder();
            sb.append(this.mApiName);
            uTControlHitBuilder.setProperty(d.i, sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.mApiVersion);
            uTControlHitBuilder.setProperty(d.j, sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(this.mNeedBlock);
            uTControlHitBuilder.setProperty("need_block", sb3.toString());
            UTAnalytics.getInstance().getDefaultTracker().send(uTControlHitBuilder.build());
            if (this.InternalListener != null) {
                this.InternalListener.getListener();
            }
        } else {
            this.mIsDoingReq = true;
            this.mInternalBusiness.startRequest();
        }
        return this;
    }

    public boolean isTaskCanceled() {
        return this.mInternalBusiness.isTaskCanceled();
    }

    public void cancelRequest() {
        this.mInternalBusiness.cancelRequest();
    }

    public void disableCommonAction() {
        this.needCommonErrorAction = false;
    }

    public void enableCommonAction() {
        this.needCommonErrorAction = true;
    }

    public RemoteBusiness showLoginUI(boolean z) {
        this.mInternalBusiness.showLoginUI(z);
        return this.mInternalBusiness;
    }

    /* access modifiers changed from: private */
    public void mtopExcepCatch(Exception exc) {
        exc.printStackTrace();
    }
}
