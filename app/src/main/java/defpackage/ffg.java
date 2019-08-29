package defpackage;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import com.ali.auth.third.core.model.Constants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.common.ApiID;
import mtopsdk.mtop.common.MtopNetworkProp;
import mtopsdk.mtop.domain.ApiTypeEnum;
import mtopsdk.mtop.domain.JsonTypeEnum;
import mtopsdk.mtop.domain.MethodEnum;
import mtopsdk.mtop.domain.MtopRequest;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.mtop.domain.ProtocolEnum;
import mtopsdk.mtop.intf.Mtop;
import mtopsdk.mtop.util.ErrorConstant;
import mtopsdk.mtop.util.MtopStatistics;

/* renamed from: ffg reason: default package */
/* compiled from: MtopBuilder */
public class ffg {
    private static final String TAG = "mtopsdk.MtopBuilder";
    public few listener;
    protected Mtop mtopInstance;
    public final MtopNetworkProp mtopProp;
    public MtopRequest request;
    @Deprecated
    public Object requestContext;
    protected MtopStatistics stat;

    public ffg(Mtop mtop, MtopRequest mtopRequest, String str) {
        this.mtopProp = new MtopNetworkProp();
        this.listener = null;
        this.requestContext = null;
        this.stat = null;
        this.mtopInstance = mtop;
        this.request = mtopRequest;
        this.mtopProp.ttid = str;
        this.mtopProp.pageName = fgy.a((String) "PageName");
        this.mtopProp.pageUrl = fgy.a((String) "PageUrl");
        this.mtopProp.backGround = fgy.b();
        this.stat = new MtopStatistics(mtop.c.w, this.mtopProp);
    }

    @Deprecated
    public ffg(ffb ffb, String str) {
        this(Mtop.a((Context) null), ffb, str);
    }

    @Deprecated
    public ffg(MtopRequest mtopRequest, String str) {
        this(Mtop.a((Context) null), mtopRequest, str);
    }

    @Deprecated
    public ffg(Object obj, String str) {
        this(Mtop.a((Context) null), obj, str);
    }

    public ffg ttid(String str) {
        this.mtopProp.ttid = str;
        return this;
    }

    public ffg reqContext(Object obj) {
        this.mtopProp.reqContext = obj;
        return this;
    }

    public Object getReqContext() {
        return this.mtopProp.reqContext;
    }

    public Mtop getMtopInstance() {
        return this.mtopInstance;
    }

    public ffg retryTime(int i) {
        this.mtopProp.retryTimes = i;
        return this;
    }

    public ffg headers(Map<String, String> map) {
        if (map != null && !map.isEmpty()) {
            if (this.mtopProp.requestHeaders != null) {
                this.mtopProp.requestHeaders.putAll(map);
            } else {
                this.mtopProp.requestHeaders = map;
            }
        }
        return this;
    }

    public ffg setCacheControlNoCache() {
        Map<String, String> map = this.mtopProp.requestHeaders;
        if (map == null) {
            map = new HashMap<>();
        }
        map.put("cache-control", "no-cache");
        this.mtopProp.requestHeaders = map;
        return this;
    }

    public ffg protocol(ProtocolEnum protocolEnum) {
        if (protocolEnum != null) {
            this.mtopProp.protocol = protocolEnum;
        }
        return this;
    }

    public ffg setCustomDomain(String str) {
        if (str != null) {
            this.mtopProp.customDomain = str;
        }
        return this;
    }

    public ffg setCustomDomain(String str, String str2, String str3) {
        if (fdd.a(str)) {
            this.mtopProp.customOnlineDomain = str;
        }
        if (fdd.a(str2)) {
            this.mtopProp.customPreDomain = str2;
        }
        if (fdd.a(str3)) {
            this.mtopProp.customDailyDomain = str3;
        }
        return this;
    }

    public ffg setUnitStrategy(String str) {
        if (str != null) {
            char c = 65535;
            int hashCode = str.hashCode();
            if (hashCode != -366328735) {
                if (hashCode == -354420023 && str.equals("UNIT_TRADE")) {
                    c = 0;
                }
            } else if (str.equals("UNIT_GUIDE")) {
                c = 1;
            }
            switch (c) {
                case 0:
                    setCustomDomain("trade-acs.m.taobao.com", "trade-acs.wapa.taobao.com", "trade-acs.waptest.taobao.com");
                    break;
                case 1:
                    setCustomDomain("guide-acs.m.taobao.com", "guide-acs.wapa.taobao.com", "guide-acs.waptest.taobao.com");
                    break;
            }
        }
        return this;
    }

    public ffg addListener(few few) {
        this.listener = few;
        return this;
    }

    public ffg setNetInfo(int i) {
        this.mtopProp.netParam = i;
        return this;
    }

    public ffg addMteeUa(String str) {
        addHttpQueryParameter(Constants.UA, str);
        return this;
    }

    public ffg addHttpQueryParameter(String str, String str2) {
        if (fdd.b(str) || fdd.b(str2)) {
            if (TBSdkLog.a(LogEnable.DebugEnable)) {
                StringBuilder sb = new StringBuilder("[addHttpQueryParameter]add HttpQueryParameter error,key=");
                sb.append(str);
                sb.append(",value=");
                sb.append(str2);
                TBSdkLog.a((String) TAG, sb.toString());
            }
            return this;
        }
        if (this.mtopProp.queryParameterMap == null) {
            this.mtopProp.queryParameterMap = new HashMap();
        }
        this.mtopProp.queryParameterMap.put(str, str2);
        return this;
    }

    public ffg handler(Handler handler) {
        this.mtopProp.handler = handler;
        return this;
    }

    public ffg useCache() {
        this.mtopProp.useCache = true;
        return this;
    }

    public ffg forceRefreshCache() {
        this.mtopProp.forceRefreshCache = true;
        return this;
    }

    public ffg useWua() {
        return useWua(4);
    }

    @Deprecated
    public ffg useWua(int i) {
        this.mtopProp.wuaFlag = i;
        return this;
    }

    public ffg reqMethod(MethodEnum methodEnum) {
        if (methodEnum != null) {
            this.mtopProp.method = methodEnum;
        }
        return this;
    }

    public ffg addCacheKeyParamBlackList(List<String> list) {
        if (list != null) {
            this.mtopProp.cacheKeyBlackList = list;
        }
        return this;
    }

    public ffg setJsonType(JsonTypeEnum jsonTypeEnum) {
        if (jsonTypeEnum != null) {
            addHttpQueryParameter("type", jsonTypeEnum.getJsonType());
        }
        return this;
    }

    public ffg addOpenApiParams(String str, String str2) {
        this.mtopProp.apiType = ApiTypeEnum.ISV_OPEN_API;
        this.mtopProp.openAppKey = str;
        this.mtopProp.accessToken = str2;
        return this;
    }

    public ffg setConnectionTimeoutMilliSecond(int i) {
        if (i > 0) {
            this.mtopProp.connTimeout = i;
        }
        return this;
    }

    public ffg setSocketTimeoutMilliSecond(int i) {
        if (i > 0) {
            this.mtopProp.socketTimeout = i;
        }
        return this;
    }

    public ffg setBizId(int i) {
        this.mtopProp.bizId = i;
        return this;
    }

    public ffg setReqBizExt(String str) {
        this.mtopProp.reqBizExt = str;
        return this;
    }

    public ffg setReqUserId(String str) {
        this.mtopProp.reqUserId = str;
        return this;
    }

    public ffg setReqAppKey(String str, String str2) {
        this.mtopProp.reqAppKey = str;
        this.mtopProp.authCode = str2;
        return this;
    }

    public ffg setPageUrl(String str) {
        if (str != null) {
            this.mtopProp.pageUrl = fdb.c(str);
            this.stat.I = this.mtopProp.pageUrl;
        }
        return this;
    }

    public ffg setReqSource(int i) {
        this.mtopProp.reqSource = i;
        return this;
    }

    public ffg enableProgressListener() {
        this.mtopProp.enableProgressListener = true;
        return this;
    }

    public MtopResponse syncRequest() {
        fey createListenerProxy = createListenerProxy(this.listener);
        asyncRequest(createListenerProxy);
        synchronized (createListenerProxy) {
            try {
                if (createListenerProxy.b == null) {
                    createListenerProxy.wait(60000);
                }
            } catch (Exception e) {
                TBSdkLog.b((String) TAG, (String) "[syncRequest] callback wait error", (Throwable) e);
            }
        }
        MtopResponse mtopResponse = createListenerProxy.b;
        if (createListenerProxy.c != null) {
            this.mtopProp.reqContext = createListenerProxy.c;
        }
        if (mtopResponse != null) {
            return mtopResponse;
        }
        return handleAsyncTimeoutException();
    }

    /* access modifiers changed from: protected */
    public MtopResponse handleAsyncTimeoutException() {
        MtopResponse mtopResponse = new MtopResponse(this.request.getApiName(), this.request.getVersion(), "ANDROID_SYS_MTOP_APICALL_ASYNC_TIMEOUT", "服务竟然出错了");
        mtopResponse.mappingCodeSuffix = ErrorConstant.a(mtopResponse.getRetCode());
        mtopResponse.mappingCode = ErrorConstant.a(mtopResponse.getResponseCode(), mtopResponse.mappingCodeSuffix);
        this.stat.o = mtopResponse.getRetCode();
        this.stat.q = mtopResponse.getMappingCode();
        this.stat.p = 2;
        mtopResponse.setMtopStat(this.stat);
        this.stat.b();
        return mtopResponse;
    }

    private ApiID asyncRequest(few few) {
        this.stat.s = MtopStatistics.a();
        final fdf createMtopContext = createMtopContext(few);
        createMtopContext.f = new ApiID(null, createMtopContext);
        try {
            if (!fdb.b()) {
                if (this.mtopInstance.e) {
                    fdy fdy = this.mtopInstance.c.K;
                    if (fdy != null) {
                        fdy.a(null, createMtopContext);
                    }
                    fed.a(fdy, createMtopContext);
                    return createMtopContext.f;
                }
            }
            ffy.a().submit(new Runnable() {
                public final void run() {
                    createMtopContext.g.t = MtopStatistics.a();
                    ffg.this.mtopInstance.b();
                    fdy fdy = ffg.this.mtopInstance.c.K;
                    if (fdy != null) {
                        fdy.a(null, createMtopContext);
                    }
                    fed.a(fdy, createMtopContext);
                }
            });
            return createMtopContext.f;
        } catch (Throwable unused) {
            return createMtopContext.f;
        }
    }

    public ApiID asyncRequest() {
        return asyncRequest(this.listener);
    }

    public fdf createMtopContext(few few) {
        fdf fdf = new fdf();
        fdf.a = this.mtopInstance;
        fdf.g = this.stat;
        fdf.h = this.stat.E;
        fdf.b = this.request;
        fdf.d = this.mtopProp;
        fdf.e = few;
        fdf.o = this;
        if (this.request != null) {
            this.stat.D = this.request.getKey();
        }
        if (fdd.b(fdf.d.ttid)) {
            fdf.d.ttid = fgy.a(this.mtopInstance.b, "ttid");
        }
        if (this.requestContext != null) {
            reqContext(this.requestContext);
        }
        return fdf;
    }

    private fey createListenerProxy(few few) {
        if (few == null) {
            return new fey(new feq());
        }
        if (few instanceof a) {
            return new fez(few);
        }
        return new fey(few);
    }

    /* access modifiers changed from: protected */
    public void mtopCommitStatData(boolean z) {
        this.stat.a = z;
    }

    public ffg setUserInfo(@Nullable String str) {
        MtopNetworkProp mtopNetworkProp = this.mtopProp;
        if (fdd.b(str)) {
            str = "DEFAULT";
        }
        mtopNetworkProp.userInfo = str;
        return this;
    }

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public ffg(Mtop mtop, ffb ffb, String str) {
        MtopRequest mtopRequest;
        // if (ffb == null) {
            // mtopRequest = null;
        // } else {
            // MtopRequest mtopRequest2 = new MtopRequest();
            // if (ffb != null) {
                // ffz.a(mtopRequest2, ffb);
            // }
            // mtopRequest = mtopRequest2;
        // }
        this(mtop, mtopRequest, str);
    }

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    @Deprecated
    public ffg(Mtop mtop, Object obj, String str) {
        MtopRequest mtopRequest;
        // if (obj == null) {
            // mtopRequest = null;
        // } else {
            // MtopRequest mtopRequest2 = new MtopRequest();
            // if (obj != null) {
                // ffz.a(mtopRequest2, obj);
            // }
            // mtopRequest = mtopRequest2;
        // }
        this(mtop, mtopRequest, str);
    }
}
