package com.alipay.mobile.common.rpc.transport.http;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.mobile.common.netsdkextdependapi.appinfo.AppInfoUtil;
import com.alipay.mobile.common.netsdkextdependapi.deviceinfo.DeviceInfoUtil;
import com.alipay.mobile.common.netsdkextdependapi.userinfo.UserInfoUtil;
import com.alipay.mobile.common.rpc.Config;
import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.common.rpc.impl.RpcLifeManagerImpl;
import com.alipay.mobile.common.rpc.transport.AbstractRpcCaller;
import com.alipay.mobile.common.rpc.transport.InnerRpcInvokeContext;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.Transport;
import com.alipay.mobile.common.transport.TransportStrategy;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.http.HttpException;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.http.HttpUrlResponse;
import com.alipay.mobile.common.transport.httpdns.AlipayHttpDnsClient;
import com.alipay.mobile.common.transport.httpdns.HttpDns.HttpdnsIP;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.MonitorErrorLogHelper;
import com.alipay.mobile.common.transport.utils.MpaasPropertiesUtil;
import com.alipay.mobile.common.transport.utils.ReadSettingServerUrl;
import com.alipay.mobile.common.transport.utils.RpcSignUtil.SignData;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

public class HttpCaller extends AbstractRpcCaller {
    private static final String TAG = "HttpCaller";
    private String cdnGwUrl = "";
    private Context context;
    private Map<String, String> extParam;
    private InnerRpcInvokeContext invokeContext;
    private long mCallRequestTicks = -1;
    private long mCallRequestTime = -1;
    private long mCallResponseTicks = -1;
    private long mCallResponseTime = -1;
    private Config mConfig;
    private String mReqDataDigest;
    private String originGwUrl = "";
    private String rpcVersion;
    private String scene;
    private SignData signData;
    private String timeStamp;

    public HttpCaller(Config config, Method method, int id, String op, byte[] reqData, String contentType, Context context2, InnerRpcInvokeContext invokeContext2) {
        super(method, id, op, reqData, contentType, invokeContext2.resetCookie.booleanValue());
        this.mConfig = config;
        this.context = context2;
        this.invokeContext = invokeContext2;
    }

    public Object call() {
        String gwUrl = getWrapperRequestUrl();
        if (this.invokeContext.isGetMethod) {
            gwUrl = gwUrl + "?" + new String(this.mReqData);
        }
        HttpUrlRequest request = new HttpUrlRequest(gwUrl);
        request.setReqData(this.mReqData);
        request.setContentType(this.mContentType);
        request.setResetCookie(this.invokeContext.resetCookie.booleanValue());
        request.setCompress(this.invokeContext.compress.booleanValue());
        request.setBgRpc(this.invokeContext.bgRpc.booleanValue());
        request.setUrgentFlag(this.invokeContext.isUrgent);
        request.allowRetry = this.invokeContext.allowRetry.booleanValue();
        request.setTimeout(this.invokeContext.timeout);
        request.setAllowNonNet(this.invokeContext.allowNonNet);
        request.setSwitchLoginRpc(this.invokeContext.switchUserLoginRpc);
        request.setDisableEncrypt(this.invokeContext.disableEnctypt);
        request.setEnableEncrypt(this.invokeContext.enableEncrypt);
        addTagsToRequest(request);
        if (this.invokeContext.isGetMethod) {
            request.setRequestMethod("GET");
            request.setReqData(null);
        } else {
            request.setRequestMethod("POST");
        }
        addHeader(request);
        LogCatUtil.info(TAG, "threadid = " + Thread.currentThread().getId() + "; Request info: " + getRequestBodyContent(request));
        try {
            Response response = sendRequest(request);
            LogCatUtil.info(TAG, "threadid=" + Thread.currentThread().getId() + " Response success.");
            setResponseHeaders((HttpUrlResponse) response);
            resetResponseHeader(true);
            return response;
        } catch (InterruptedException e) {
            request.cancel("InterruptedException");
            LogCatUtil.error((String) TAG, "InterruptedException:" + MiscUtils.getRootCause(e));
            throw new RpcException(Integer.valueOf(13), "InterruptedException", e);
        } catch (ExecutionException e2) {
            request.cancel("ExecutionException");
            Throwable throwable = e2.getCause();
            LogCatUtil.debug(TAG, "ExecutionException:" + throwable);
            if (throwable != null && (throwable instanceof HttpException)) {
                httpException2RpcException((HttpException) throwable);
            }
            MonitorErrorLogHelper.log(TAG, throwable);
            throw new RpcException(Integer.valueOf(9), throwable != null ? throwable.toString() : "", throwable);
        } catch (CancellationException e3) {
            request.cancel("CancellationException");
            LogCatUtil.error((String) TAG, "CancellationException:" + MiscUtils.getRootCause(e3));
            throw new RpcException(Integer.valueOf(13), "CancellationException", e3);
        } catch (TimeoutException e4) {
            request.cancel("TimeoutException");
            LogCatUtil.error((String) TAG, "TimeoutException: " + MiscUtils.getRootCause(e4));
            throw new RpcException(Integer.valueOf(13), "TimeoutException", e4);
        } catch (Throwable th) {
            resetResponseHeader(false);
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void httpException2RpcException(HttpException throwable) {
        HttpException httpException = throwable;
        RpcException rpcException = new RpcException(Integer.valueOf(transferCode(httpException.getCode())), httpException.getMsg());
        if (httpException.getCode() == 50) {
            rpcException.setAlert(0);
        }
        throw rpcException;
    }

    private void resetResponseHeader(boolean rpcSuc) {
        if (!rpcSuc && this.invokeContext != null) {
            this.invokeContext.responseHeader = Collections.EMPTY_MAP;
        }
    }

    private String getWrapperRequestUrl() {
        this.originGwUrl = getRequestUrl();
        this.cdnGwUrl = getCdnRequestUrl();
        if (!TextUtils.isEmpty(this.cdnGwUrl)) {
            return this.cdnGwUrl;
        }
        return this.originGwUrl;
    }

    private String getRequestUrl() {
        if (!MiscUtils.isDebugger(this.context)) {
            return this.invokeContext.gwUrl;
        }
        String confiGwUrl = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.MOBILEGW_URL);
        if (!TextUtils.isEmpty(confiGwUrl)) {
            LogCatUtil.info(TAG, "getRequestUrl.   config gw url: " + confiGwUrl);
            return confiGwUrl;
        }
        try {
            if (!TransportStrategy.isAlipayUrl(this.invokeContext.gwUrl)) {
                LogCatUtil.info(TAG, "getRequestUrl.   Not alipay url: " + this.invokeContext.gwUrl);
                return this.invokeContext.gwUrl;
            }
            try {
                URI uri = new URI(this.invokeContext.gwUrl);
                if (TextUtils.equals(uri.getScheme(), "https") && uri.getHost().endsWith("alipay.net")) {
                    String gwUrl = new StringBuilder(AjxHttpLoader.DOMAIN_HTTP).append(uri.getHost()).append(uri.getPath()).toString();
                    LogCatUtil.info(TAG, "getRequestUrl.   " + uri + " replace to " + gwUrl);
                    return gwUrl;
                }
            } catch (URISyntaxException e) {
                LogCatUtil.error((String) TAG, (Throwable) e);
            }
            LogCatUtil.info(TAG, "getRequestUrl.  default gw url: " + this.invokeContext.gwUrl);
            return this.invokeContext.gwUrl;
        } catch (MalformedURLException e2) {
            LogCatUtil.error((String) TAG, (Throwable) e2);
            return this.invokeContext.gwUrl;
        }
    }

    private String getRequestBodyContent(HttpUrlRequest request) {
        if (!MiscUtils.isInLogBackList(this.mOperationType) && MiscUtils.isDebugger(this.context)) {
            return request.toString();
        }
        ArrayList headers = request.getHeaders();
        StringBuilder headerBuilder = new StringBuilder(363);
        Iterator<Header> it = headers.iterator();
        while (it.hasNext()) {
            Header header = it.next();
            headerBuilder.append(header.getName()).append("=").append(header.getValue()).append(",");
        }
        headerBuilder.append(",   根据相关法律法规和政策，部分内容未予显示");
        return headerBuilder.toString();
    }

    /* JADX INFO: finally extract failed */
    private Response sendRequest(HttpUrlRequest request) {
        Response response;
        this.mCallRequestTime = System.currentTimeMillis();
        this.mCallRequestTicks = SystemClock.elapsedRealtime();
        Future future = getTransport().execute(request);
        RpcLifeManagerImpl.getInstance().addFuture(future);
        try {
            if (this.invokeContext.timeout > 0) {
                response = future.get(this.invokeContext.timeout, TimeUnit.MILLISECONDS);
            } else {
                response = future.get((long) TransportConfigureManager.getInstance().getIntValue(TransportConfigureItem.RPC_TOTAL_TIMEOUT), TimeUnit.MILLISECONDS);
            }
            RpcLifeManagerImpl.getInstance().removeFuture(future);
            this.mCallResponseTicks = SystemClock.elapsedRealtime();
            this.mCallResponseTime = this.mCallRequestTime + (this.mCallResponseTicks - this.mCallRequestTicks);
            if (response != null) {
                return response;
            }
            LogCatUtil.info(TAG, "threadid = " + Thread.currentThread().getId() + "; Response fail: [response is null]. mOperationType=[" + this.mOperationType + "] ");
            throw new RpcException(Integer.valueOf(9), (String) "response is null");
        } catch (Throwable th) {
            RpcLifeManagerImpl.getInstance().removeFuture(future);
            throw th;
        }
    }

    private void setResponseHeaders(HttpUrlResponse response) {
        this.invokeContext.responseHeader = response.getHeader().getHeaders();
        this.mConfig.giveResponseHeader(this.mOperationType, response.getHeader());
    }

    private int transferCode(int code) {
        switch (code) {
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
            case 11:
                return 18;
            case 50:
                return 24;
            default:
                return 9;
        }
    }

    private void addHeader(HttpUrlRequest request) {
        addCustReqHeaders(request);
        this.mConfig.addExtHeaders(request);
        addAppIdToHeader(request);
        if (!isRpcVersion2()) {
            request.setHeader(new BasicHeader(LocationParams.PARA_COMMON_DID, DeviceInfoUtil.getDeviceId()));
            request.setHeader(new BasicHeader("clientId", DeviceInfoUtil.getClientId()));
            request.setHeader(new BasicHeader("TRACKERID", AppInfoUtil.getTrackerID()));
            SignData lSignData = getSignData();
            if (!(lSignData == null || TextUtils.isEmpty(lSignData.sign) || lSignData.signType == -1)) {
                request.setHeader(new BasicHeader("signType", String.valueOf(lSignData.signType)));
            }
        } else {
            addExtParam2Headers(request);
            request.setHeader(new BasicHeader("Version", "2"));
            request.setHeader(new BasicHeader("Did", DeviceInfoUtil.getDeviceId()));
            request.setHeader(new BasicHeader("Operation-Type", this.mOperationType));
            request.setHeader(new BasicHeader("Ts", this.timeStamp));
            request.setHeader(new BasicHeader("Content-Type", this.mContentType));
            if (!TextUtils.isEmpty(this.scene)) {
                request.setHeader(new BasicHeader("Scene", this.scene));
            }
            SignData lSignData2 = getSignData();
            if (lSignData2 != null && !TextUtils.isEmpty(lSignData2.sign)) {
                request.setHeader(new BasicHeader("Sign", lSignData2.sign));
                if (lSignData2.signType != -1) {
                    request.setHeader(new BasicHeader("signType", String.valueOf(lSignData2.signType)));
                }
            }
        }
        setAlipaysOptionHeaders(request);
        setCdnBackEndIpToRpcHeader(request);
        setOtherCommonHeaders(request);
    }

    private void addAppIdToHeader(HttpUrlRequest request) {
        if (!TextUtils.isEmpty(this.invokeContext.appId)) {
            request.addHeader(new BasicHeader("AppId", this.invokeContext.appId));
        } else {
            request.addHeader(new BasicHeader("AppId", MpaasPropertiesUtil.getAppId(this.context, this.invokeContext.appKey)));
        }
    }

    private void setAlipaysOptionHeaders(HttpUrlRequest request) {
        try {
            if (MiscUtils.isInAlipayClient(this.context)) {
                if (TransportConfigureManager.getInstance().equalsString(TransportConfigureItem.ALIPAY_CLIENT_VERSION, "T")) {
                    String prodVersion = AppInfoUtil.getProductVersion();
                    if (!TextUtils.isEmpty(prodVersion)) {
                        request.setHeader(new BasicHeader(H5TinyAppLogUtil.TINY_APP_STANDARD_EXTRA_CLIENTVERSION, prodVersion));
                    }
                }
                if (TransportConfigureManager.getInstance().equalsString(TransportConfigureItem.ALIPAY_USER_ID, "T")) {
                    String userId = UserInfoUtil.getLastUserId();
                    if (!TextUtils.isEmpty(userId)) {
                        request.setHeader(new BasicHeader("userId", userId));
                    }
                }
            }
        } catch (Throwable e) {
            LogCatUtil.warn((String) TAG, "add ext header exception. " + e.toString());
        }
    }

    @Deprecated
    private void addExtParam2Headers(HttpUrlRequest request) {
        if (this.extParam != null && !this.extParam.isEmpty()) {
            for (Entry entity : this.extParam.entrySet()) {
                request.addHeader(new BasicHeader((String) entity.getKey(), (String) entity.getValue()));
            }
        }
    }

    private void addCustReqHeaders(HttpUrlRequest request) {
        if (this.invokeContext.requestHeaders != null && !this.invokeContext.requestHeaders.isEmpty()) {
            for (Entry reqHeaderEntry : this.invokeContext.requestHeaders.entrySet()) {
                request.addHeader(new BasicHeader((String) reqHeaderEntry.getKey(), (String) reqHeaderEntry.getValue()));
            }
        }
        Map mpaasProperties = MpaasPropertiesUtil.getMpaasProperties(this.context);
        if (mpaasProperties != null && !mpaasProperties.isEmpty()) {
            for (Entry entry : mpaasProperties.entrySet()) {
                request.addHeader(new BasicHeader((String) entry.getKey(), (String) entry.getValue()));
            }
        }
    }

    private Transport getTransport() {
        if (this.mConfig.getTransport() != null) {
            return this.mConfig.getTransport();
        }
        throw new RpcException(Integer.valueOf(1), (String) "Not find this type Transport");
    }

    public void setReqDataDigest(String reqDataDigest) {
        this.mReqDataDigest = reqDataDigest;
    }

    public String getReqDataDigest() {
        return this.mReqDataDigest;
    }

    public String getContentType() {
        return this.mContentType;
    }

    public void setContentType(String contentType) {
        this.mContentType = contentType;
    }

    public String getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(String timeStamp2) {
        this.timeStamp = timeStamp2;
    }

    public void setSignData(SignData signData2) {
        this.signData = signData2;
    }

    public SignData getSignData() {
        return this.signData;
    }

    public String getRpcVersion() {
        return this.rpcVersion;
    }

    public void setRpcVersion(String rpcVersion2) {
        this.rpcVersion = rpcVersion2;
    }

    public boolean isRpcVersion2() {
        if (TextUtils.equals(this.rpcVersion, "2")) {
            return true;
        }
        return false;
    }

    public void setScene(String scene2) {
        this.scene = scene2;
    }

    public Map<String, String> getExtParam() {
        return this.extParam;
    }

    public void setExtParam(Map<String, String> extParam2) {
        this.extParam = extParam2;
    }

    public void setExtObjectParam(Map<String, Object> extParam2) {
        Map stringObjectMap = extParam2;
        if (extParam2 != null && !stringObjectMap.isEmpty()) {
            Map extParamMap = new HashMap(stringObjectMap.size());
            for (Entry stringObjectEntry : stringObjectMap.entrySet()) {
                extParamMap.put(stringObjectEntry.getKey(), String.valueOf(stringObjectEntry.getValue()));
            }
            this.extParam = extParamMap;
        }
    }

    private String getCdnRequestUrl() {
        try {
            if (!MiscUtils.isInAlipayClient(this.context)) {
                return "";
            }
            String operationType = this.mOperationType;
            if (TextUtils.isEmpty(operationType)) {
                return "";
            }
            String cdnApis = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.CDN_APIS);
            if (TextUtils.isEmpty(cdnApis)) {
                return "";
            }
            String cdnUrl = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.CDN_URL);
            if (TextUtils.isEmpty(cdnUrl)) {
                return "";
            }
            for (String api : cdnApis.split(",")) {
                if (TextUtils.equals(operationType, api)) {
                    LogCatUtil.info(TAG, "Modify gw url to cdn url. operationType=[" + operationType + "], cdnUrl=[" + cdnUrl + "]");
                    return cdnUrl;
                }
            }
            return "";
        } catch (Exception e) {
            LogCatUtil.error((String) TAG, (Throwable) e);
        }
    }

    private void setCdnBackEndIpToRpcHeader(HttpUrlRequest request) {
        if (!TextUtils.isEmpty(this.cdnGwUrl)) {
            request.addTags(TransportConstants.KEY_FORCE_HTTP, "true");
            try {
                URL url = new URL(this.originGwUrl);
                try {
                    HttpdnsIP ipInfoByHost = AlipayHttpDnsClient.getDnsClient().getIpInfoByHost(url.getHost());
                    if (ipInfoByHost != null) {
                        String ip = ipInfoByHost.getIp();
                        if (!TextUtils.isEmpty(ip)) {
                            request.setHeader(new BasicHeader("backend", ip));
                            return;
                        }
                    }
                } catch (Exception e) {
                    LogCatUtil.warn((String) TAG, (Throwable) e);
                }
                request.setHeader(new BasicHeader("backend", url.getHost()));
            } catch (Exception e2) {
                throw new RuntimeException("originGwUrl=" + this.originGwUrl, e2);
            }
        }
    }

    private void setOtherCommonHeaders(HttpUrlRequest request) {
        boolean shadow = ReadSettingServerUrl.getInstance().isEnabledShadowSwitch(TransportEnvUtil.getContext());
        if (MiscUtils.isDebugger(TransportEnvUtil.getContext()) && shadow) {
            request.setHeader(new BasicHeader("load-test", "Y"));
        }
    }

    private void addTagsToRequest(HttpUrlRequest request) {
        request.addTags("id", String.valueOf(this.mId));
        request.addTags(TransportConstants.KEY_OPERATION_TYPE, this.mOperationType);
        request.addTags(TransportConstants.KEY_REQ_DATA_DIGEST, getReqDataDigest());
        request.addTags(TransportConstants.KEY_RPC_VERSION, this.rpcVersion);
        request.addTags("UUID", DeviceInfoUtil.getDeviceId() + this.timeStamp);
        if (this.invokeContext.loggerLevel != -1) {
            request.addTags(TransportConstants.KEY_LOGGER_LEVEL, String.valueOf(this.invokeContext.loggerLevel));
        }
    }
}
