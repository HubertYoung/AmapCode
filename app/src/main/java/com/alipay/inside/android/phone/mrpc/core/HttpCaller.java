package com.alipay.inside.android.phone.mrpc.core;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.inside.common.info.AppInfo;
import com.alipay.android.phone.inside.common.info.DeviceInfo;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.storage.pref.EncryptPrefUtil;
import com.alipay.inside.android.phone.mrpc.core.utils.RpcSignUtil.SignData;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.http.message.BasicHeader;

public class HttpCaller extends AbstractRpcCaller {
    private static int DEFAULT_TIMEOUT = 150000;
    private static final String KEY_SERVER_TIME = "Server-Time";
    private static final String TAG = "HttpCaller";
    private Context context;
    private InnerRpcInvokeContext invokeContext;
    private Config mConfig;
    private String mReqDataDigest;
    private SignData signData;
    private String timeStamp;

    private int transferCode(int i) {
        switch (i) {
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 5;
            case 5:
                return 6;
            case 6:
                return 7;
            case 7:
                return 8;
            case 8:
                return 15;
            case 9:
                return 16;
            default:
                return i;
        }
    }

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public HttpCaller(Config config, Method method, int i, String str, byte[] bArr, String str2, Context context2, InnerRpcInvokeContext innerRpcInvokeContext) {
        // InnerRpcInvokeContext innerRpcInvokeContext2 = innerRpcInvokeContext;
        super(method, i, str, bArr, str2, innerRpcInvokeContext2.resetCookie.booleanValue());
        this.mConfig = config;
        this.context = context2;
        this.invokeContext = innerRpcInvokeContext2;
    }

    public Object call() throws RpcException {
        HttpUrlRequest httpUrlRequest = new HttpUrlRequest(this.invokeContext.gwUrl);
        httpUrlRequest.setReqData(this.mReqData);
        httpUrlRequest.setContentType(this.mContentType);
        httpUrlRequest.setResetCookie(this.invokeContext.resetCookie.booleanValue());
        httpUrlRequest.addTags("id", String.valueOf(this.mId));
        httpUrlRequest.addTags(TransportConstants.KEY_OPERATION_TYPE, this.mOperationType);
        httpUrlRequest.addTags("AppId", this.invokeContext.appKey);
        httpUrlRequest.addTags(TransportConstants.KEY_REQ_DATA_DIGEST, getReqDataDigest());
        httpUrlRequest.addTags(TransportConstants.KEY_RPC_VERSION, "2");
        StringBuilder sb = new StringBuilder();
        sb.append(DeviceInfo.b().d());
        sb.append(this.timeStamp);
        httpUrlRequest.addTags("UUID", sb.toString());
        httpUrlRequest.setRequestMethod("POST");
        addHeader(httpUrlRequest);
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb2 = new StringBuilder("threadid = ");
        sb2.append(Thread.currentThread().getId());
        sb2.append("; ");
        sb2.append(httpUrlRequest.toString());
        f.b((String) TAG, sb2.toString());
        try {
            Response sendRequest = sendRequest(httpUrlRequest);
            TraceLogger f2 = LoggerFactory.f();
            StringBuilder sb3 = new StringBuilder("threadid=");
            sb3.append(Thread.currentThread().getId());
            sb3.append(" Response success.");
            f2.b((String) TAG, sb3.toString());
            setResponseHeaders((HttpUrlResponse) sendRequest);
            resetResponseHeader(true);
            return sendRequest;
        } catch (InterruptedException e) {
            throw new RpcException(Integer.valueOf(13), "", e);
        } catch (ExecutionException e2) {
            Throwable cause = e2.getCause();
            if (cause == null || !(cause instanceof HttpException)) {
                throw new RpcException(Integer.valueOf(9), "", e2);
            }
            HttpException httpException = (HttpException) cause;
            throw new RpcException(Integer.valueOf(transferCode(httpException.getCode())), httpException.getMsg());
        } catch (CancellationException e3) {
            throw new RpcException(Integer.valueOf(13), "", e3);
        } catch (Throwable th) {
            resetResponseHeader(false);
            throw th;
        }
    }

    private Response sendRequest(HttpUrlRequest httpUrlRequest) throws InterruptedException, ExecutionException, TimeoutException {
        Response response;
        Future<Response> execute = getTransport().execute(httpUrlRequest);
        if (this.invokeContext.timeout > 0) {
            response = execute.get(this.invokeContext.timeout, TimeUnit.MILLISECONDS);
        } else {
            response = execute.get((long) DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        }
        if (response != null) {
            return response;
        }
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("threadid = ");
        sb.append(Thread.currentThread().getId());
        sb.append("; Response fail: [response is null]. mOperationType=[");
        sb.append(this.mOperationType);
        sb.append("] ");
        f.b((String) TAG, sb.toString());
        throw new RpcException(Integer.valueOf(9), (String) "response is null");
    }

    private void resetResponseHeader(boolean z) {
        if (!z && this.invokeContext != null) {
            this.invokeContext.responseHeader = Collections.EMPTY_MAP;
        }
    }

    private void setResponseHeaders(HttpUrlResponse httpUrlResponse) {
        this.invokeContext.responseHeader = httpUrlResponse.getHeader().getHeaders();
        saveServerTime(this.invokeContext.responseHeader);
    }

    private void saveServerTime(Map<String, String> map) {
        if (map == null) {
            LoggerFactory.f().b((String) "inside", (String) "headers is null");
        } else if (map.containsKey("Server-Time")) {
            String str = map.get("Server-Time");
            try {
                long currentTimeMillis = System.currentTimeMillis();
                long parseLong = (Long.parseLong(str) - currentTimeMillis) / 1000;
                EncryptPrefUtil.a("alipay_inside_keys", "server_timespan", String.valueOf(parseLong));
                BehaviorLogger d = LoggerFactory.d();
                BehaviorType behaviorType = BehaviorType.EVENT;
                StringBuilder sb = new StringBuilder("serverTime:");
                sb.append(str);
                sb.append(", clientTime:");
                sb.append(currentTimeMillis);
                sb.append(", serverTimespan:");
                sb.append(parseLong);
                d.a("rpc", behaviorType, "RpcUpdateServerTime", sb.toString());
            } catch (Throwable th) {
                LoggerFactory.f().c((String) "inside", th);
            }
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb2 = new StringBuilder("Server-Time: ");
            sb2.append(str);
            sb2.append(", Client-Time:");
            sb2.append(System.currentTimeMillis());
            f.b((String) "inside", sb2.toString());
        } else {
            LoggerFactory.e().a("rpc", "RpcServerTimeEmpty");
            LoggerFactory.f().b((String) "inside", (String) "No Key Server-Time");
        }
    }

    private void addHeader(HttpUrlRequest httpUrlRequest) {
        addCustReqHeaders(httpUrlRequest);
        httpUrlRequest.addHeader(new BasicHeader("Version", "2"));
        httpUrlRequest.addHeader(new BasicHeader("Did", DeviceInfo.b().d()));
        httpUrlRequest.addHeader(new BasicHeader("Operation-Type", this.mOperationType));
        httpUrlRequest.addHeader(new BasicHeader("Ts", this.timeStamp));
        httpUrlRequest.addHeader(new BasicHeader("Content-Type", this.mContentType));
        httpUrlRequest.addHeader(new BasicHeader("Accept-Language", "zh-Hans"));
        SignData signData2 = getSignData();
        if (signData2 != null && !TextUtils.isEmpty(signData2.sign)) {
            httpUrlRequest.addHeader(new BasicHeader("Sign", signData2.sign));
            if (signData2.signType != -1) {
                httpUrlRequest.addHeader(new BasicHeader("signType", String.valueOf(signData2.signType)));
            }
        }
        addAppidToHeader(httpUrlRequest);
    }

    private void addCustReqHeaders(HttpUrlRequest httpUrlRequest) {
        if (this.invokeContext.requestHeaders != null && !this.invokeContext.requestHeaders.isEmpty()) {
            for (Entry next : this.invokeContext.requestHeaders.entrySet()) {
                httpUrlRequest.addHeader(new BasicHeader((String) next.getKey(), (String) next.getValue()));
            }
        }
    }

    private void addAppidToHeader(HttpUrlRequest httpUrlRequest) {
        if (!TextUtils.isEmpty(this.invokeContext.appKey)) {
            httpUrlRequest.addHeader(new BasicHeader("AppId", this.invokeContext.appKey));
        } else {
            httpUrlRequest.addHeader(new BasicHeader("AppId", AppInfo.b().e()));
        }
    }

    private Transport getTransport() throws RpcException {
        return this.mConfig.getTransport();
    }

    public String getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(String str) {
        this.timeStamp = str;
    }

    public String getContentType() {
        return this.mContentType;
    }

    public void setContentType(String str) {
        this.mContentType = str;
    }

    public void setReqDataDigest(String str) {
        this.mReqDataDigest = str;
    }

    public String getReqDataDigest() {
        return this.mReqDataDigest;
    }

    public void setSignData(SignData signData2) {
        this.signData = signData2;
    }

    public SignData getSignData() {
        return this.signData;
    }
}
