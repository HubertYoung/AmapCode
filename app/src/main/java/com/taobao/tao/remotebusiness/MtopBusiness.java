package com.taobao.tao.remotebusiness;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.taobao.tao.remotebusiness.listener.MtopListenerProxyFactory;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.common.ApiID;
import mtopsdk.mtop.domain.ApiTypeEnum;
import mtopsdk.mtop.domain.BaseOutDo;
import mtopsdk.mtop.domain.JsonTypeEnum;
import mtopsdk.mtop.domain.MethodEnum;
import mtopsdk.mtop.domain.MtopRequest;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.mtop.domain.ProtocolEnum;
import mtopsdk.mtop.intf.Mtop;

public class MtopBusiness extends ffg {
    public static final int MAX_RETRY_TIMES = 3;
    private static final String TAG = "mtopsdk.MtopBusiness";
    private static AtomicInteger seqGen = new AtomicInteger(0);
    private ApiID apiID;
    public String authParam = null;
    public Class<?> clazz;
    public boolean isCached = false;
    private boolean isCancelled = false;
    private boolean isErrorNotifyAfterCache = false;
    public few listener;
    private MtopResponse mtopResponse = null;
    private boolean needAuth = false;
    public long onBgFinishTime = 0;
    public long reqStartTime = 0;
    @Deprecated
    public Object requestContext = null;
    protected int requestType = 0;
    protected int retryTime = 0;
    public long sendStartTime = 0;
    private final String seqNo = genSeqNo();
    public boolean showAuthUI = true;
    private boolean showLoginUI = true;
    private boolean syncRequestFlag = false;

    protected MtopBusiness(@NonNull Mtop mtop, ffb ffb, String str) {
        super(mtop, ffb, str);
    }

    protected MtopBusiness(@NonNull Mtop mtop, MtopRequest mtopRequest, String str) {
        super(mtop, mtopRequest, str);
    }

    private String genSeqNo() {
        StringBuilder sb = new StringBuilder(16);
        sb.append("MB");
        sb.append(seqGen.incrementAndGet());
        sb.append(DjangoUtils.EXTENSION_SEPARATOR);
        sb.append(this.stat.E);
        return sb.toString();
    }

    public static MtopBusiness build(Mtop mtop, ffb ffb, String str) {
        return new MtopBusiness(mtop, ffb, str);
    }

    public static MtopBusiness build(Mtop mtop, ffb ffb) {
        return build(mtop, ffb, (String) null);
    }

    @Deprecated
    public static MtopBusiness build(ffb ffb, String str) {
        return build(Mtop.a((Context) null, str), ffb, str);
    }

    @Deprecated
    public static MtopBusiness build(ffb ffb) {
        return build(Mtop.a((Context) null), ffb);
    }

    public static MtopBusiness build(Mtop mtop, MtopRequest mtopRequest, String str) {
        return new MtopBusiness(mtop, mtopRequest, str);
    }

    public static MtopBusiness build(Mtop mtop, MtopRequest mtopRequest) {
        return build(mtop, mtopRequest, (String) null);
    }

    @Deprecated
    public static MtopBusiness build(MtopRequest mtopRequest, String str) {
        return build(Mtop.a((Context) null, str), mtopRequest, str);
    }

    @Deprecated
    public static MtopBusiness build(MtopRequest mtopRequest) {
        return build(Mtop.a((Context) null), mtopRequest, (String) null);
    }

    @Deprecated
    public MtopBusiness registerListener(few few) {
        this.listener = few;
        return this;
    }

    @Deprecated
    public MtopBusiness addListener(few few) {
        this.listener = few;
        return this;
    }

    public MtopBusiness registerListener(IRemoteListener iRemoteListener) {
        this.listener = iRemoteListener;
        return this;
    }

    public String getSeqNo() {
        return this.seqNo;
    }

    public void startRequest() {
        startRequest(0, null);
    }

    public void startRequest(Class<?> cls) {
        startRequest(0, cls);
    }

    public void startRequest(int i, Class<?> cls) {
        if (this.request == null) {
            TBSdkLog.d(TAG, this.seqNo, "MtopRequest is null!");
            return;
        }
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            String str = this.seqNo;
            StringBuilder sb = new StringBuilder("startRequest ");
            sb.append(this.request);
            TBSdkLog.b((String) TAG, str, sb.toString());
        }
        this.reqStartTime = System.currentTimeMillis();
        this.isCancelled = false;
        this.isCached = false;
        this.clazz = cls;
        this.requestType = i;
        if (this.requestContext != null) {
            reqContext(this.requestContext);
        }
        if (this.listener != null && !this.isCancelled) {
            super.addListener(MtopListenerProxyFactory.getMtopListenerProxy(this, this.listener));
        }
        mtopCommitStatData(false);
        this.sendStartTime = System.currentTimeMillis();
        this.apiID = super.asyncRequest();
    }

    public MtopResponse syncRequest() {
        String key = this.request != null ? this.request.getKey() : "";
        if (fdb.b()) {
            TBSdkLog.d(TAG, this.seqNo, "do syncRequest in UI main thread!");
        }
        this.syncRequestFlag = true;
        if (this.listener == null) {
            this.listener = new IRemoteBaseListener() {
                public void onError(int i, MtopResponse mtopResponse, Object obj) {
                }

                public void onSuccess(int i, MtopResponse mtopResponse, BaseOutDo baseOutDo, Object obj) {
                }

                public void onSystemError(int i, MtopResponse mtopResponse, Object obj) {
                }
            };
        }
        startRequest();
        synchronized (this.listener) {
            try {
                if (this.mtopResponse == null) {
                    this.listener.wait(60000);
                }
            } catch (InterruptedException unused) {
                TBSdkLog.d(TAG, this.seqNo, "syncRequest InterruptedException. apiKey=".concat(String.valueOf(key)));
            } catch (Exception unused2) {
                TBSdkLog.d(TAG, this.seqNo, "syncRequest do wait Exception. apiKey=".concat(String.valueOf(key)));
            }
        }
        if (this.mtopResponse == null) {
            if (TBSdkLog.a(LogEnable.ErrorEnable)) {
                TBSdkLog.c(TAG, this.seqNo, "syncRequest timeout. apiKey=".concat(String.valueOf(key)));
            }
            cancelRequest();
        }
        return this.mtopResponse != null ? this.mtopResponse : handleAsyncTimeoutException();
    }

    @Deprecated
    public ApiID asyncRequest() {
        startRequest();
        return this.apiID;
    }

    public int getRequestType() {
        return this.requestType;
    }

    public boolean isTaskCanceled() {
        return this.isCancelled;
    }

    public int getRetryTime() {
        return this.retryTime;
    }

    public void cancelRequest() {
        cancelRequest(null);
    }

    /* access modifiers changed from: 0000 */
    public void cancelRequest(String str) {
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            TBSdkLog.b((String) TAG, this.seqNo, getRequestLogInfo("cancelRequest.", this));
        }
        this.isCancelled = true;
        if (this.apiID != null) {
            try {
                this.apiID.cancelApiCall();
            } catch (Throwable th) {
                TBSdkLog.a(TAG, this.seqNo, getRequestLogInfo("cancelRequest failed.", this), th);
            }
        }
        RequestPool.removeFromRequestPool(this.mtopInstance, str, this);
    }

    /* access modifiers changed from: 0000 */
    public void retryRequest() {
        retryRequest(null);
    }

    /* access modifiers changed from: 0000 */
    public void retryRequest(String str) {
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            TBSdkLog.b((String) TAG, this.seqNo, getRequestLogInfo("retryRequest.", this));
        }
        if (this.retryTime >= 3) {
            this.retryTime = 0;
            doFinish(null, null);
            return;
        }
        cancelRequest(str);
        startRequest(this.requestType, this.clazz);
        this.retryTime++;
    }

    public MtopBusiness showLoginUI(boolean z) {
        this.showLoginUI = z;
        return this;
    }

    public boolean isShowLoginUI() {
        return this.showLoginUI;
    }

    public MtopBusiness setNeedAuth(String str, boolean z) {
        this.authParam = str;
        this.showAuthUI = z;
        this.needAuth = true;
        if (TBSdkLog.a(LogEnable.DebugEnable)) {
            StringBuilder sb = new StringBuilder(64);
            sb.append("[setNeedAuth] authParam=");
            sb.append(str);
            sb.append(", showAuthUI=");
            sb.append(z);
            sb.append(", needAuth=");
            sb.append(this.needAuth);
            TBSdkLog.a((String) TAG, this.seqNo, sb.toString());
        }
        return this;
    }

    public MtopBusiness setNeedAuth(@NonNull String str, String str2, boolean z) {
        this.mtopProp.apiType = ApiTypeEnum.ISV_OPEN_API;
        this.mtopProp.isInnerOpen = true;
        if (fdd.a(str)) {
            this.mtopProp.openAppKey = str;
        }
        this.authParam = str2;
        this.showAuthUI = z;
        this.needAuth = true;
        if (TBSdkLog.a(LogEnable.DebugEnable)) {
            StringBuilder sb = new StringBuilder(64);
            sb.append("[setNeedAuth] openAppKey=");
            sb.append(str);
            sb.append(", bizParam=");
            sb.append(str2);
            sb.append(", showAuthUI=");
            sb.append(z);
            sb.append(", needAuth=");
            sb.append(this.needAuth);
            sb.append(", isInnerOpen=true");
            TBSdkLog.a((String) TAG, this.seqNo, sb.toString());
        }
        return this;
    }

    public boolean isNeedAuth() {
        return this.needAuth || this.authParam != null;
    }

    public MtopBusiness addOpenApiParams(String str, String str2) {
        return (MtopBusiness) super.addOpenApiParams(str, str2);
    }

    public MtopBusiness setErrorNotifyAfterCache(boolean z) {
        this.isErrorNotifyAfterCache = z;
        return this;
    }

    @Deprecated
    public void setErrorNotifyNeedAfterCache(boolean z) {
        setErrorNotifyAfterCache(z);
    }

    public void doFinish(MtopResponse mtopResponse2, BaseOutDo baseOutDo) {
        if (this.syncRequestFlag) {
            this.mtopResponse = mtopResponse2;
            synchronized (this.listener) {
                try {
                    this.listener.notify();
                } catch (Exception e) {
                    String str = this.seqNo;
                    StringBuilder sb = new StringBuilder("[doFinish]syncRequest do notify Exception. apiKey=");
                    sb.append(mtopResponse2 != null ? mtopResponse2.getFullKey() : "");
                    TBSdkLog.b(TAG, str, sb.toString(), e);
                }
            }
        }
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            StringBuilder sb2 = new StringBuilder(32);
            sb2.append("doFinish request=");
            sb2.append(this.request);
            if (mtopResponse2 != null) {
                sb2.append(", retCode=");
                sb2.append(mtopResponse2.getRetCode());
            }
            TBSdkLog.b((String) TAG, this.seqNo, sb2.toString());
        }
        if (this.isCancelled) {
            TBSdkLog.c(TAG, this.seqNo, "request is cancelled,don't callback listener.");
        } else if (!(this.listener instanceof IRemoteListener)) {
            String str2 = this.seqNo;
            StringBuilder sb3 = new StringBuilder("listener did't implement IRemoteBaseListener.apiKey=");
            sb3.append(mtopResponse2 != null ? mtopResponse2.getFullKey() : "");
            TBSdkLog.d(TAG, str2, sb3.toString());
        } else {
            IRemoteListener iRemoteListener = (IRemoteListener) this.listener;
            if (mtopResponse2 != null && mtopResponse2.isApiSuccess()) {
                try {
                    iRemoteListener.onSuccess(this.requestType, mtopResponse2, baseOutDo, getReqContext());
                } catch (Throwable th) {
                    TBSdkLog.b(TAG, this.seqNo, "listener onSuccess callback error", th);
                }
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    TBSdkLog.b((String) TAG, this.seqNo, (String) "listener onSuccess callback.");
                }
            } else if (!this.isCached || this.isErrorNotifyAfterCache) {
                doErrorCallback(mtopResponse2, iRemoteListener);
            } else {
                TBSdkLog.b((String) TAG, this.seqNo, (String) "listener onCached callback,doNothing in doFinish()");
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void doErrorCallback(mtopsdk.mtop.domain.MtopResponse r6, com.taobao.tao.remotebusiness.IRemoteListener r7) {
        /*
            r5 = this;
            r0 = 1
            if (r6 != 0) goto L_0x0023
            mtopsdk.common.util.TBSdkLog$LogEnable r1 = mtopsdk.common.util.TBSdkLog.LogEnable.ErrorEnable
            boolean r1 = mtopsdk.common.util.TBSdkLog.a(r1)
            if (r1 == 0) goto L_0x0077
            java.lang.String r1 = "mtopsdk.MtopBusiness"
            java.lang.String r2 = r5.seqNo
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "mtopResponse is null."
            r3.<init>(r4)
            mtopsdk.mtop.domain.MtopRequest r4 = r5.request
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            mtopsdk.common.util.TBSdkLog.b(r1, r2, r3)
            goto L_0x0077
        L_0x0023:
            boolean r1 = r6.isSessionInvalid()
            if (r1 == 0) goto L_0x0049
            mtopsdk.common.util.TBSdkLog$LogEnable r1 = mtopsdk.common.util.TBSdkLog.LogEnable.InfoEnable
            boolean r1 = mtopsdk.common.util.TBSdkLog.a(r1)
            if (r1 == 0) goto L_0x0077
            java.lang.String r1 = "mtopsdk.MtopBusiness"
            java.lang.String r2 = r5.seqNo
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "session invalid error."
            r3.<init>(r4)
            mtopsdk.mtop.domain.MtopRequest r4 = r5.request
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            mtopsdk.common.util.TBSdkLog.b(r1, r2, r3)
            goto L_0x0077
        L_0x0049:
            boolean r1 = r6.isMtopServerError()
            if (r1 != 0) goto L_0x0058
            boolean r1 = r6.isMtopSdkError()
            if (r1 == 0) goto L_0x0056
            goto L_0x0058
        L_0x0056:
            r0 = 0
            goto L_0x0077
        L_0x0058:
            mtopsdk.common.util.TBSdkLog$LogEnable r1 = mtopsdk.common.util.TBSdkLog.LogEnable.InfoEnable
            boolean r1 = mtopsdk.common.util.TBSdkLog.a(r1)
            if (r1 == 0) goto L_0x0077
            java.lang.String r1 = "mtopsdk.MtopBusiness"
            java.lang.String r2 = r5.seqNo
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "mtopServerError or mtopSdkError."
            r3.<init>(r4)
            mtopsdk.mtop.domain.MtopRequest r4 = r5.request
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            mtopsdk.common.util.TBSdkLog.b(r1, r2, r3)
        L_0x0077:
            if (r0 == 0) goto L_0x008b
            boolean r1 = r7 instanceof com.taobao.tao.remotebusiness.IRemoteBaseListener     // Catch:{ Throwable -> 0x0089 }
            if (r1 == 0) goto L_0x008b
            com.taobao.tao.remotebusiness.IRemoteBaseListener r7 = (com.taobao.tao.remotebusiness.IRemoteBaseListener) r7     // Catch:{ Throwable -> 0x0089 }
            int r1 = r5.requestType     // Catch:{ Throwable -> 0x0089 }
            java.lang.Object r2 = r5.getReqContext()     // Catch:{ Throwable -> 0x0089 }
            r7.onSystemError(r1, r6, r2)     // Catch:{ Throwable -> 0x0089 }
            goto L_0x009e
        L_0x0089:
            r6 = move-exception
            goto L_0x0095
        L_0x008b:
            int r1 = r5.requestType     // Catch:{ Throwable -> 0x0089 }
            java.lang.Object r2 = r5.getReqContext()     // Catch:{ Throwable -> 0x0089 }
            r7.onError(r1, r6, r2)     // Catch:{ Throwable -> 0x0089 }
            goto L_0x009e
        L_0x0095:
            java.lang.String r7 = "mtopsdk.MtopBusiness"
            java.lang.String r1 = r5.seqNo
            java.lang.String r2 = "listener onError callback error"
            mtopsdk.common.util.TBSdkLog.b(r7, r1, r2, r6)
        L_0x009e:
            mtopsdk.common.util.TBSdkLog$LogEnable r6 = mtopsdk.common.util.TBSdkLog.LogEnable.InfoEnable
            boolean r6 = mtopsdk.common.util.TBSdkLog.a(r6)
            if (r6 == 0) goto L_0x00c3
            java.lang.String r6 = "mtopsdk.MtopBusiness"
            java.lang.String r7 = r5.seqNo
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "listener onError callback, "
            r1.<init>(r2)
            if (r0 == 0) goto L_0x00b7
            java.lang.String r0 = "sys error"
            goto L_0x00b9
        L_0x00b7:
            java.lang.String r0 = "biz error"
        L_0x00b9:
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            mtopsdk.common.util.TBSdkLog.b(r6, r7, r0)
        L_0x00c3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.tao.remotebusiness.MtopBusiness.doErrorCallback(mtopsdk.mtop.domain.MtopResponse, com.taobao.tao.remotebusiness.IRemoteListener):void");
    }

    public MtopBusiness setBizId(int i) {
        return (MtopBusiness) super.setBizId(i);
    }

    public MtopBusiness ttid(String str) {
        return (MtopBusiness) super.ttid(str);
    }

    public MtopBusiness useCache() {
        return (MtopBusiness) super.useCache();
    }

    public MtopBusiness useWua() {
        return (MtopBusiness) super.useWua();
    }

    @Deprecated
    public MtopBusiness useWua(int i) {
        return (MtopBusiness) super.useWua(i);
    }

    public MtopBusiness addHttpQueryParameter(String str, String str2) {
        return (MtopBusiness) super.addHttpQueryParameter(str, str2);
    }

    public MtopBusiness addCacheKeyParamBlackList(List<String> list) {
        return (MtopBusiness) super.addCacheKeyParamBlackList(list);
    }

    public MtopBusiness addMteeUa(String str) {
        return (MtopBusiness) super.addMteeUa(str);
    }

    public MtopBusiness enableProgressListener() {
        return (MtopBusiness) super.enableProgressListener();
    }

    public MtopBusiness forceRefreshCache() {
        return (MtopBusiness) super.forceRefreshCache();
    }

    public MtopBusiness handler(Handler handler) {
        return (MtopBusiness) super.handler(handler);
    }

    public MtopBusiness headers(Map<String, String> map) {
        return (MtopBusiness) super.headers(map);
    }

    public MtopBusiness protocol(ProtocolEnum protocolEnum) {
        return (MtopBusiness) super.protocol(protocolEnum);
    }

    public MtopBusiness reqContext(Object obj) {
        return (MtopBusiness) super.reqContext(obj);
    }

    public MtopBusiness reqMethod(MethodEnum methodEnum) {
        return (MtopBusiness) super.reqMethod(methodEnum);
    }

    public MtopBusiness retryTime(int i) {
        return (MtopBusiness) super.retryTime(i);
    }

    public MtopBusiness setCacheControlNoCache() {
        return (MtopBusiness) super.setCacheControlNoCache();
    }

    public MtopBusiness setConnectionTimeoutMilliSecond(int i) {
        return (MtopBusiness) super.setConnectionTimeoutMilliSecond(i);
    }

    public MtopBusiness setCustomDomain(String str) {
        return (MtopBusiness) super.setCustomDomain(str);
    }

    public MtopBusiness setCustomDomain(String str, String str2, String str3) {
        return (MtopBusiness) super.setCustomDomain(str, str2, str3);
    }

    public MtopBusiness setUnitStrategy(String str) {
        return (MtopBusiness) super.setUnitStrategy(str);
    }

    public MtopBusiness setJsonType(JsonTypeEnum jsonTypeEnum) {
        return (MtopBusiness) super.setJsonType(jsonTypeEnum);
    }

    public MtopBusiness setNetInfo(int i) {
        return (MtopBusiness) super.setNetInfo(i);
    }

    public MtopBusiness setPageUrl(String str) {
        return (MtopBusiness) super.setPageUrl(str);
    }

    public MtopBusiness setReqAppKey(String str, String str2) {
        return (MtopBusiness) super.setReqAppKey(str, str2);
    }

    public MtopBusiness setReqBizExt(String str) {
        return (MtopBusiness) super.setReqBizExt(str);
    }

    public MtopBusiness setReqSource(int i) {
        return (MtopBusiness) super.setReqSource(i);
    }

    public MtopBusiness setReqUserId(String str) {
        return (MtopBusiness) super.setReqUserId(str);
    }

    public MtopBusiness setSocketTimeoutMilliSecond(int i) {
        return (MtopBusiness) super.setSocketTimeoutMilliSecond(i);
    }

    public MtopBusiness setPriorityFlag(boolean z) {
        this.mtopProp.priorityFlag = z;
        return this;
    }

    public MtopBusiness setPriorityData(Map<String, String> map) {
        this.mtopProp.priorityData = map;
        return this;
    }

    public MtopBusiness setUserInfo(@Nullable String str) {
        return (MtopBusiness) super.setUserInfo(str);
    }

    private String getRequestLogInfo(String str, MtopBusiness mtopBusiness) {
        StringBuilder sb = new StringBuilder(32);
        sb.append(str);
        sb.append(" [");
        if (mtopBusiness != null) {
            sb.append("apiName=");
            sb.append(mtopBusiness.request.getApiName());
            sb.append(";version=");
            sb.append(mtopBusiness.request.getVersion());
            sb.append(";requestType=");
            sb.append(mtopBusiness.getRequestType());
        }
        sb.append("]");
        return sb.toString();
    }
}
