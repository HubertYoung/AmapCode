package com.alipay.mobile.common.transport.http;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Process;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.DataflowID;
import com.alipay.mobile.common.logging.api.monitor.DataflowModel;
import com.alipay.mobile.common.netsdkextdependapi.deviceinfo.DeviceInfoUtil;
import com.alipay.mobile.common.netsdkextdependapi.security.SecurityUtil;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.TransportCallback;
import com.alipay.mobile.common.transport.TransportStrategy;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.download.DownloadRequest;
import com.alipay.mobile.common.transport.ext.ExtTransportClient;
import com.alipay.mobile.common.transport.ext.ExtTransportException;
import com.alipay.mobile.common.transport.ext.ExtTransportOffice;
import com.alipay.mobile.common.transport.h5.H5HttpUrlRequest;
import com.alipay.mobile.common.transport.http.inner.HttpClientPlannerHelper;
import com.alipay.mobile.common.transport.http.inner.HttpProxyWrapper;
import com.alipay.mobile.common.transport.http.inner.RpcInputStreamEntity;
import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import com.alipay.mobile.common.transport.http.selfencrypt.SelfEncryptUtils;
import com.alipay.mobile.common.transport.interceptors.TransportInterceptorManager;
import com.alipay.mobile.common.transport.io.RpcBufferedInputStream;
import com.alipay.mobile.common.transport.monitor.NetworkServiceTracer;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.alipay.mobile.common.transport.monitor.networkqos.AlipayQosService;
import com.alipay.mobile.common.transport.multimedia.DjgHttpUrlRequest;
import com.alipay.mobile.common.transport.strategy.StrategyUtil;
import com.alipay.mobile.common.transport.utils.DataItemsUtil;
import com.alipay.mobile.common.transport.utils.HeaderConstant;
import com.alipay.mobile.common.transport.utils.IOUtil;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.MonitorErrorLogHelper;
import com.alipay.mobile.common.transport.utils.MonitorLogRecordUtil;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transport.utils.RequestMethodUtils;
import com.alipay.mobile.common.transport.utils.RetryService;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.common.transport.utils.TransportContextThreadLocalUtils;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.common.transport.zfeatures.LoginRefreshHelper;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import com.alipay.mobile.monitor.api.ClientMonitor;
import com.alipay.mobile.monitor.api.MonitorFactory;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.sina.weibo.sdk.utils.WbAuthConstants;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectException;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class HttpWorker implements Callable<Response> {
    public static final String EXPIRES_PATTERN = "EEE, dd-MMM-yyyy HH:mm:ss z";
    protected static byte LAST_GOOD_PROXY = -1;
    public static final String TAG = "HttpWorker";
    protected static final HttpRequestRetryHandler sHttpRequestRetryHandler = new ZHttpRequestRetryHandler();
    private HttpUriRequest a;
    private CookieStore b = new BasicCookieStore();
    private HttpEntity c;
    protected ClientRpcPack clientRpcPack;
    private HttpHost d;
    private URL e;
    private URI f;
    private int g = 0;
    private HttpContextExtend h = HttpContextExtend.getInstance();
    private boolean i = false;
    private boolean j = false;
    private String k = null;
    private String l;
    private long m = -1;
    protected Context mContext;
    protected HttpManager mHttpManager;
    protected HttpResponse mHttpResponse;
    protected HttpContext mLocalContext = new BasicHttpContext();
    protected HttpUrlRequest mOriginRequest;
    protected TransportContext mTransportContext = new TransportContext();
    private byte n = -1;
    private byte o = -1;
    private long p = System.currentTimeMillis();
    private int q = 0;
    private Response r;
    protected RedirectHandler redirectHandler;
    private Throwable s;
    private boolean t = false;
    private HttpHost u;
    private long v = 0;
    private boolean w = false;
    protected boolean writedMonitorLog = false;
    private boolean x = false;

    public HttpWorker(HttpManager httpManager, HttpUrlRequest request) {
        this.mHttpManager = httpManager;
        this.mContext = this.mHttpManager.getContext();
        this.mOriginRequest = request;
        this.mTransportContext.context = this.mContext;
        this.mTransportContext.rpcUUID = this.mOriginRequest.getTag("UUID");
        this.redirectHandler = getHttpClient().getRedirectHandler();
        String loggerLevel = request.getTag(TransportConstants.KEY_LOGGER_LEVEL);
        if (!TextUtils.isEmpty(loggerLevel)) {
            this.mTransportContext.loggerLevel = loggerLevel;
        }
        String targetSpi = request.getTag(TransportConstants.KEY_TARGET_SPI);
        if (!TextUtils.isEmpty(targetSpi)) {
            this.mTransportContext.targetSpi = targetSpi;
        }
    }

    public URI getTargetUri() {
        if (this.f != null) {
            return this.f;
        }
        String origUrl = this.mOriginRequest.getUrl();
        if (TextUtils.isEmpty(origUrl)) {
            throw new RuntimeException("url should not be null");
        }
        URI targetURI = a(new URI(origUrl));
        this.f = targetURI;
        return targetURI;
    }

    /* access modifiers changed from: protected */
    public HttpEntity getPostData() {
        if (this.c != null) {
            return this.c;
        }
        HttpEntity httpEntity = this.mOriginRequest.getHttpEntity();
        if (httpEntity != null) {
            this.c = httpEntity;
            a(this.c);
            return this.c;
        }
        InputStream inputStream = this.mOriginRequest.getInputStream();
        if (inputStream != null) {
            this.c = new RpcInputStreamEntity(inputStream, this.mOriginRequest.getDataLength());
            a(this.c);
            return this.c;
        }
        HttpForm httpForm = this.mOriginRequest.getHttpForm();
        if (httpForm != null) {
            this.c = httpForm;
            a(this.c);
            return httpForm;
        }
        byte[] bs = this.mOriginRequest.getReqData();
        if (bs != null) {
            if (isUseSelfEncrypt()) {
                this.c = SelfEncryptUtils.getEncryptedEntity(bs, this.clientRpcPack, this.mOriginRequest);
            } else if (!this.mTransportContext.reqGzip || !this.mOriginRequest.isCompress()) {
                this.c = new ByteArrayEntity(bs);
            } else {
                this.c = AndroidHttpClient.getCompressedEntity(bs, null);
            }
            Header contentTypeHeader = r();
            if (contentTypeHeader != null) {
                if (this.c instanceof AbstractHttpEntity) {
                    this.c.setContentType(contentTypeHeader);
                }
            } else if (this.c instanceof AbstractHttpEntity) {
                this.c.setContentType(this.mOriginRequest.getContentType());
            }
        }
        if (this.a != null && (this.a instanceof HttpEntityEnclosingRequest)) {
            this.c = this.a.getEntity();
        }
        return this.c;
    }

    private void a(HttpEntity postDataEntity) {
        Header contentTypeHeader = r();
        if (contentTypeHeader != null) {
            if (postDataEntity instanceof AbstractHttpEntity) {
                ((AbstractHttpEntity) postDataEntity).setContentType(contentTypeHeader);
            }
        } else if (!TextUtils.isEmpty(this.mOriginRequest.getContentType()) && (postDataEntity instanceof AbstractHttpEntity)) {
            ((AbstractHttpEntity) postDataEntity).setContentType(this.mOriginRequest.getContentType());
        }
    }

    public ArrayList<Header> getHeaders() {
        return this.mOriginRequest.getHeaders();
    }

    /* access modifiers changed from: protected */
    public HttpUriRequest getTargetHttpUriRequest() {
        if (this.a != null) {
            try {
                if (this.a.isAborted() && (this.a instanceof HttpRequestBase)) {
                    this.a = (HttpUriRequest) this.a.clone();
                }
            } catch (Throwable e2) {
                LogCatUtil.warn((String) TAG, "getTargetHttpUriRequest. clone error " + e2.toString());
            }
            return this.a;
        }
        HttpUrlRequest originRequest = getOriginRequest();
        HttpUriRequest localHttpUriRequest = originRequest.getHttpUriRequest();
        if (localHttpUriRequest != null) {
            this.a = localHttpUriRequest;
            if (localHttpUriRequest instanceof HttpEntityEnclosingRequest) {
                this.c = ((HttpEntityEnclosingRequest) localHttpUriRequest).getEntity();
            }
            return this.a;
        }
        this.a = RequestMethodUtils.createHttpUriRequestByMethod(getPostData(), originRequest.getRequestMethod(), getTargetUri());
        originRequest.setHttpUriRequest(this.a);
        return this.a;
    }

    public Response call() {
        Response processException;
        try {
            a();
            TransportContextThreadLocalUtils.setValue(this.mTransportContext);
            if (!TextUtils.isEmpty(getOperationType()) && !getOriginRequest().isBgRpc()) {
                Process.setThreadPriority(-4);
                Thread.currentThread().setPriority(10);
            }
            this.mTransportContext.startExecutionTime = System.currentTimeMillis();
            this.m = this.mTransportContext.startExecutionTime - this.p;
            if (g() != null) {
                g().onPreExecute(this.mOriginRequest);
            }
            LoginRefreshHelper.recordRpc(this, this.mContext);
            TransportStrategy.configInit(this.mContext, getOperationType(), this.mTransportContext);
            addRequestHeaders();
            this.mTransportContext.url = getTargetHttpUriRequest().getURI().toString();
            this.mLocalContext.setAttribute(TransportConstants.KEY_NET_CONTEXT, this.mTransportContext);
            this.mLocalContext.setAttribute("http.cookie-store", this.b);
            s();
            long time = System.currentTimeMillis();
            this.mHttpResponse = executeRequest();
            this.mHttpManager.addConnectTime(System.currentTimeMillis() - time);
            processRespCookies(this.mHttpResponse);
            this.r = processResponse(this.mHttpResponse, this.mOriginRequest);
            processException = this.r;
            finallyProcess();
        } catch (HttpException e2) {
            processException = processException("HttpException", e2.getCode(), e2, false);
            finallyProcess();
        } catch (URISyntaxException e3) {
            try {
                g().onFailed(this.mOriginRequest, 10, e3.toString());
            } catch (Exception e1) {
                LogCatUtil.warn((String) TAG, "getTransportCallback().onFailed1 exception : " + e1.toString());
            }
            DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), "ERROR", "URISyntaxException:" + e3.getMessage());
            throw new RuntimeException("Url parser error!", e3.getCause());
        } catch (SSLHandshakeException e4) {
            processException = processException("SSLHandshakeException", 2, e4, false);
            finallyProcess();
        } catch (SSLPeerUnverifiedException e5) {
            processException = processException("SSLPeerUnverifiedException", 2, e5, false);
            finallyProcess();
        } catch (SSLException e6) {
            processException = processException("SSLException", 2, e6, false);
            finallyProcess();
        } catch (GeneralSecurityException e7) {
            processException = processException(e7.getClass().getSimpleName(), 2, e7, false);
            finallyProcess();
        } catch (ConnectionPoolTimeoutException e8) {
            processException = processException("ConnectionPoolTimeoutException", 3, e8, false);
            finallyProcess();
        } catch (ConnectTimeoutException e9) {
            processException = processException("ConnectTimeoutException", 3, e9, false);
            finallyProcess();
        } catch (SocketTimeoutException e10) {
            AlipayQosService.getInstance().estimate(5000.0d, 5);
            processException = processException("SocketTimeoutException", 4, e10, false);
            finallyProcess();
        } catch (NoHttpResponseException e11) {
            processException = processException("NoHttpResponseException", 5, e11, false);
            finallyProcess();
        } catch (HttpHostConnectException e12) {
            processException = processException("HttpHostConnectException", 8, e12, false);
            finallyProcess();
        } catch (ClientProtocolException e13) {
            processException = processException("ClientProtocolException", 0, e13, true);
            finallyProcess();
        } catch (UnknownHostException e14) {
            processException = processException("UnknownHostException", 9, e14, false);
            finallyProcess();
        } catch (IOException e15) {
            processException = processException("IOException", 6, e15, a(e15));
            finallyProcess();
        } catch (NullPointerException e16) {
            processException = processException("NullPointerException", 0, e16, true);
            finallyProcess();
        } catch (Throwable th) {
            finallyProcess();
            throw th;
        }
        return processException;
    }

    public boolean isRpcRequest() {
        return this.mTransportContext.bizType == 1;
    }

    private void a() {
        this.mOriginRequest.setTaskState(1);
        if (this.mOriginRequest.isCanceled()) {
            throw new HttpException(Integer.valueOf(13), "Cancel request :" + this.mOriginRequest.getUrl() + ",cancelMsg:" + this.mOriginRequest.getCancelMsg());
        }
        this.mOriginRequest.setNetworkThread(Thread.currentThread());
        if (!NetworkUtils.isNetworkAvailable(this.mContext)) {
            if (TextUtils.equals(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.IGNORE_NETWORK_STATE), "T")) {
                this.x = true;
                LogCatUtil.debug(TAG, "API=" + getOperationType() + ",ignoreNetState on,go on");
            } else if (!this.mOriginRequest.isAllowNonNet()) {
                throw new HttpException(Integer.valueOf(1), (String) "The network is not available");
            } else {
                LogCatUtil.debug(TAG, "API=" + getOperationType() + ",allowNonNet is set,go on..");
            }
        }
        String traficCheckUrl = this.mOriginRequest.getUrl();
        if (!TextUtils.isEmpty(getOperationType())) {
            traficCheckUrl = traficCheckUrl + MetaRecord.LOG_SEPARATOR + getOperationType();
        }
        if (!isTraficConsumeAccept(traficCheckUrl)) {
            throw new HttpException(Integer.valueOf(11), traficCheckUrl + " trafic beyond limit");
        }
        h(this.mOriginRequest.getUrl());
    }

    /* access modifiers changed from: protected */
    public void finallyProcess() {
        try {
            LoginRefreshHelper.removeRpc(this, this.mContext);
            notifyNetworkState();
            a(this.r);
            monitorLog();
            if (this.mHttpResponse != null) {
                f();
            }
            k();
        } catch (Exception e2) {
            LogCatUtil.error((String) TAG, (Throwable) e2);
        } finally {
            TransportContextThreadLocalUtils.setValue(null);
            this.mOriginRequest.setTaskState(2);
        }
    }

    /* access modifiers changed from: protected */
    public void notifyNetworkState() {
        if (MiscUtils.isInAlipayClient(this.mContext) && !MiscUtils.isPushProcess(this.mContext)) {
            ExtTransportOffice extTransportOffice = ExtTransportOffice.getInstance();
            if (this.mHttpResponse == null || this.mHttpResponse.getStatusLine() == null) {
                if (this.s != null) {
                    extTransportOffice.networkStateNotify(false);
                }
            } else if (this.mHttpResponse.getStatusLine().getStatusCode() == 200) {
                extTransportOffice.networkStateNotify(true);
            } else if (this.redirectHandler.isRedirectRequested(this.mHttpResponse, this.mLocalContext)) {
                if (this.mHttpResponse.getStatusLine().getStatusCode() != 302) {
                    extTransportOffice.networkStateNotify(true);
                }
            } else if (this.mHttpResponse.getStatusLine().getStatusCode() > 0) {
                extTransportOffice.networkStateNotify(true);
            } else if (this.s != null) {
                extTransportOffice.networkStateNotify(false);
            }
        }
    }

    /* access modifiers changed from: protected */
    public String monitorLog() {
        return doMonitorLog();
    }

    public String doMonitorLog() {
        if (this.writedMonitorLog) {
            return "";
        }
        this.writedMonitorLog = true;
        putCommonMonitorDataItems();
        putSubCommonMonitor();
        RPCDataParser.monitorLog(this.mTransportContext);
        return this.mTransportContext.perfLog;
    }

    /* access modifiers changed from: protected */
    public void putSubCommonMonitor() {
    }

    /* access modifiers changed from: protected */
    public void putCommonMonitorDataItems() {
        try {
            String switchLogTag = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.SWITCH_TAG_LOG);
            if (!TextUtils.isEmpty(switchLogTag)) {
                DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.SWITCH_TAG_LOG, switchLogTag);
            }
            if (getOriginRequest().isBgRpc()) {
                DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.PRIO, "BG");
            } else {
                DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.PRIO, "FG");
            }
            if (this.x) {
                DataItemsUtil.putDataItem2ContainerAnyway(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.IGN_ERR, "T");
            }
            if (MiscUtils.isAtFrontDesk(this.mContext)) {
                DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.GROUND, "FG");
            } else {
                DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.GROUND, "BG");
            }
            int qosLevel = AlipayQosService.getInstance().getQosLevel();
            double qosRto = AlipayQosService.getInstance().getRto();
            DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.QOS, qosLevel + "_" + String.format("%.4f", new Object[]{Double.valueOf(qosRto)}));
            if (this.mHttpResponse != null) {
                Header via = this.mHttpResponse.getFirstHeader("via");
                if (via != null) {
                    String value = via.getValue();
                    if (!TextUtils.isEmpty(value)) {
                        DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.CDN_VIA, value.replace(",", "，"));
                    }
                }
                Header eagleIdHeader = this.mHttpResponse.getFirstHeader(RPCDataItems.CDN_EAGLEID);
                if (eagleIdHeader != null) {
                    String val = eagleIdHeader.getValue();
                    if (!TextUtils.isEmpty(val)) {
                        DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.CDN_EAGLEID, val);
                    }
                }
            }
            String bizId = getOriginRequest().getTag("bizId");
            if (!TextUtils.isEmpty(bizId)) {
                DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), "bizId", bizId);
            }
            if (this.mOriginRequest != null && this.mOriginRequest.getDataLength() > 0) {
                DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), "REQ_RAW_SIZE", String.valueOf(this.mOriginRequest.getDataLength()));
            }
            String method = this.mOriginRequest.getRequestMethod();
            if (isRpcRequest() && TextUtils.equals(method, "GET")) {
                DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.REQUEST_METHOD, method);
            }
            if (isUseSelfEncrypt()) {
                DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.USE_SELF_ENCODE, "T");
            }
            MonitorLogRecordUtil.recordMultMainProcessItem(this.mTransportContext, this.mContext);
            c();
            b();
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, ex);
        }
    }

    private void b() {
        try {
            if (this.r != null) {
                if (TextUtils.equals(this.mTransportContext.getCurrentDataContainer().getDataItem("RETRY"), "T")) {
                    ((HttpUrlResponse) this.r).getHeader().addHead(HeaderConstant.HEADER_KEY_PARAM_RETRY, "T");
                } else {
                    ((HttpUrlResponse) this.r).getHeader().addHead(HeaderConstant.HEADER_KEY_PARAM_RETRY, "F");
                }
                String reqSize = this.mTransportContext.getCurrentDataContainer().getDataItem("REQ_SIZE");
                String respSize = this.mTransportContext.getCurrentDataContainer().getDataItem("RES_SIZE");
                long reqSizeLong = 0;
                long respSizeLong = 0;
                if (!TextUtils.isEmpty(reqSize)) {
                    reqSizeLong = Long.parseLong(reqSize);
                }
                if (!TextUtils.isEmpty(respSize)) {
                    respSizeLong = Long.parseLong(respSize);
                }
                ((HttpUrlResponse) this.r).getHeader().addHead(HeaderConstant.HEADER_KEY_PARAM_REQ_SIZE, String.valueOf(reqSizeLong));
                ((HttpUrlResponse) this.r).getHeader().addHead(HeaderConstant.HEADER_KEY_PARAM_RES_SIZE, String.valueOf(respSizeLong));
                ((HttpUrlResponse) this.r).getHeader().addHead(HeaderConstant.HEADER_KEY_PARAM_TRAFFIC, String.valueOf(reqSizeLong + respSizeLong));
                ((HttpUrlResponse) this.r).getHeader().addHead(HeaderConstant.HEADER_KEY_CLIENT_TRACE_ID, this.mTransportContext.rpcUUID);
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, ex);
        }
    }

    private void c() {
        DataflowModel dataflowModel;
        if (!isShouldReportTraffic()) {
            LogCatUtil.debug(TAG, "need't reportTrafficConsume");
        } else if (this.mTransportContext.bizType == 1 || this.mTransportContext.bizType == 4) {
            DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), "RES_SIZE", String.valueOf(this.v));
            long reqDataLength = -1;
            if (this.a != null && (this.a instanceof HttpEntityEnclosingRequestBase)) {
                HttpEntity postEntity = this.a.getEntity();
                reqDataLength = postEntity != null ? postEntity.getContentLength() : -1;
            }
            long respDataLength = -1;
            if (this.mTransportContext.bizType == 1) {
                try {
                    String resSize = DataItemsUtil.getDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), "RES_SIZE");
                    if (!TextUtils.isEmpty(resSize)) {
                        respDataLength = Long.parseLong(resSize);
                    } else {
                        respDataLength = this.v;
                    }
                } catch (Throwable th) {
                    respDataLength = this.v;
                }
            } else if (this.mTransportContext.bizType == 4) {
                respDataLength = this.v;
                DataItemsUtil.putDataItem2ContainerAnyway(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.DOWN_TRAFFIC, String.valueOf(this.v));
            }
            LogCatUtil.debug(TAG, "reportTrafficConsume,reqData=[" + reqDataLength + "] respData(air traffic)=[" + respDataLength + "]");
            if (reqDataLength > 0 || respDataLength > 0) {
                String noteUrl = this.mOriginRequest.getUrl();
                DataflowID type = DataflowID.HTTPCLIENT_DOWNLOAD;
                String diagnose = null;
                if (noteUrl != null) {
                    try {
                        if (this.mTransportContext.isRpcBizType()) {
                            noteUrl = noteUrl + MetaRecord.LOG_SEPARATOR + getOperationType();
                            type = DataflowID.HTTPCLIENT_RPC;
                            diagnose = getOperationType();
                        }
                    } catch (Throwable t2) {
                        LoggerFactory.getTraceLogger().warn((String) "noteTraficConsume", "HttpWorker.before: " + t2);
                    }
                }
                if (TextUtils.isEmpty(diagnose)) {
                    TransportCallback callback = this.mOriginRequest.getCallback();
                    if (callback != null) {
                        diagnose = callback.getClass().getName();
                    }
                }
                try {
                    dataflowModel = DataflowModel.obtain(type, noteUrl, reqDataLength, respDataLength, diagnose);
                    dataflowModel.isUpload = false;
                    dataflowModel.bizId = getOriginRequest().getTag("bizId");
                } catch (Throwable e2) {
                    LoggerFactory.getTraceLogger().warn((String) "noteTraficConsume", "HttpWorker.outer: " + e2);
                    return;
                }
                try {
                    dataflowModel.reqHeaders = getOriginRequest().getHttpUriRequest().getAllHeaders();
                } catch (Throwable t3) {
                    LoggerFactory.getTraceLogger().error((String) "noteTraficConsume", "HttpWorker.reqHeaders: " + t3);
                }
                try {
                    if (getHttpResponse() != null) {
                        dataflowModel.respHeaders = getHttpResponse().getAllHeaders();
                    }
                } catch (Throwable t4) {
                    LoggerFactory.getTraceLogger().error((String) "noteTraficConsume", "HttpWorker.respHeaders: " + t4);
                }
                try {
                    extNoteTraficConsume(dataflowModel);
                } catch (Throwable e3) {
                    LogCatUtil.error(TAG, "extNoteTraficConsume error", e3);
                }
                MonitorFactory.getMonitorContext().noteTraficConsume(dataflowModel);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void extNoteTraficConsume(DataflowModel dataflowModel) {
    }

    /* access modifiers changed from: protected */
    public Response processException(String exceptionName, int code, Throwable e2, boolean canForceRetry) {
        try {
            LogCatUtil.error(TAG, "processException,code=[" + code + "] canRetry=[" + canForceRetry + "] e=[" + e2.toString() + "]", e2);
            TransportStrategy.incrementRpcErrorCount();
            String msg = e2.toString();
            if (e2 instanceof HttpException) {
                HttpException he = (HttpException) e2;
                code = he.getCode();
                msg = he.getMsg();
            }
            if (this.mOriginRequest.isCanceled()) {
                canForceRetry = false;
                DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.CANCEL, "T");
                if (code != 13) {
                    code = 13;
                    msg = this.mOriginRequest.getCancelMsg();
                }
            }
            Map extMap = e();
            if (!(e2 instanceof HttpException)) {
                monitorErrorLog(e2);
                DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), "ERROR", exceptionName + ":" + (TextUtils.isEmpty(e2.getMessage()) ? e2.toString() : e2.getMessage()));
                a(exceptionName, code, e2, extMap);
            } else if (((HttpException) e2).getCode() != 1) {
                DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), "ERROR", exceptionName + ":" + (TextUtils.isEmpty(e2.getMessage()) ? e2.toString() : e2.getMessage()));
                monitorErrorLog(e2);
                a(exceptionName, code, e2, extMap);
            }
            if (!this.mOriginRequest.isCanceled() && (a(e2) || (canForceRetry && d()))) {
                return a(exceptionName);
            }
            abort();
            if (g() != null) {
                g().onFailed(this.mOriginRequest, code, msg);
            }
            try {
                String perfLog = monitorLog();
                if (!TextUtils.isEmpty(perfLog)) {
                    msg = msg + Token.SEPARATOR + perfLog;
                }
            } catch (Throwable th) {
            }
            HttpException httpException = new HttpException(Integer.valueOf(code), msg, e2);
            this.s = httpException;
            throw httpException;
        } finally {
            whenExceptionFlushUploadLog();
        }
    }

    private boolean d() {
        if (!NetworkUtils.isNetworkAvailable(this.mContext) || !ZHttpRequestRetryHandler.isCanRetryForStandardHttpRequest(getOriginRequest()) || !t()) {
            return false;
        }
        int retryCount = ZHttpRequestRetryHandler.getRetryCount(this.mLocalContext);
        if (this.g + retryCount >= 3) {
            LogCatUtil.debug(TAG, "canRetryCurrTask retryCount: " + retryCount + ",mRetryTimes：" + this.g + ",should't retry");
            return false;
        } else if (!this.mOriginRequest.isCanceled()) {
            return true;
        } else {
            return false;
        }
    }

    private Response a(String exceptionName) {
        DataItemsUtil.removeFromDataContainer(this.mTransportContext.getCurrentDataContainer(), "ERROR");
        DataItemsUtil.removeFromDataContainer(this.mTransportContext.getCurrentDataContainer(), "NETTUNNEL");
        this.t = true;
        LogCatUtil.info(TAG, exceptionName + " retry,retryCount:" + ZHttpRequestRetryHandler.getRetryCount(this.mLocalContext) + ",mRetryTimes:" + this.g);
        this.g++;
        resetRequestHeaders();
        return call();
    }

    /* access modifiers changed from: protected */
    public void resetRequestHeaders() {
        try {
            for (Header header : getTargetHttpUriRequest().getAllHeaders()) {
                getTargetHttpUriRequest().removeHeaders(header.getName());
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "resetRequestHeaders ex:" + ex.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void whenExceptionFlushUploadLog() {
        try {
            LoggerFactory.getLogContext().flush(LogCategory.CATEGORY_NETWORK, true);
            LoggerFactory.getLogContext().upload(LogCategory.CATEGORY_NETWORK);
        } catch (Throwable th) {
        }
    }

    private Map<String, String> e() {
        try {
            Map extMap = new HashMap();
            if (!TextUtils.isEmpty(getOperationType())) {
                extMap.put("Operation-Type", getOperationType());
            }
            HttpUrlRequest httpUrlRequest = getOriginRequest();
            if (!(httpUrlRequest instanceof DjgHttpUrlRequest)) {
                return extMap;
            }
            extMap.put("DJG_UP_BIZ", String.valueOf(((DjgHttpUrlRequest) httpUrlRequest).getInnerBizType()));
            return extMap;
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "getExtMap ex:" + ex.toString());
            return Collections.EMPTY_MAP;
        }
    }

    private boolean a(Throwable e2) {
        if (!NetworkUtils.isNetworkAvailable(this.mContext)) {
            LogCatUtil.info(TAG, "canRetryCurrTaskForProxyNetwork. can't retry for proxy， because network is invalid。");
            return false;
        } else if (this.n == -1 || this.mOriginRequest.isCanceled()) {
            return false;
        } else {
            if (!(e2 instanceof IOException) && !(e2 instanceof GeneralSecurityException)) {
                return false;
            }
            if ((!ZHttpRequestRetryHandler.isCanRetryForStandardHttpRequest(getOriginRequest()) || !t()) && (e2 instanceof SocketTimeoutException)) {
                return false;
            }
            this.o = this.n;
            this.n = -1;
            LAST_GOOD_PROXY = -1;
            LogCatUtil.debug(TAG, "Switch proxy model and retry, retryProxy is " + this.o);
            return true;
        }
    }

    private void a(String exceptionName, int code, Throwable e2, Map<String, String> extMap) {
        try {
            if (!isRpcRequest() || !MiscUtils.isMdapApi(getOperationType())) {
                NetworkServiceTracer.getInstance().recordError(this.mTransportContext.bizType, code, exceptionName + ":" + e2.getMessage(), extMap);
            }
        } catch (Exception e3) {
            LogCatUtil.debug(TAG, "reportError2Monitor exception");
        }
    }

    private void f() {
        if (!isRpcRequest() || !MiscUtils.isMdapApi(getOperationType())) {
            NetworkServiceTracer.getInstance().clearErrorByType(this.mTransportContext.bizType);
        }
    }

    /* access modifiers changed from: protected */
    public void monitorErrorLog(Throwable e2) {
        if (!isRpcRequest() || !MiscUtils.isMdapApi(getOperationType())) {
            MonitorErrorLogHelper.log(TAG, new HttpException(getOperationType(), e2));
        } else {
            LoggerFactory.getTraceLogger().error((String) TAG, e2);
        }
    }

    /* access modifiers changed from: protected */
    public void abort() {
        try {
            if (getTargetHttpUriRequest() != null) {
                getTargetHttpUriRequest().abort();
                LogCatUtil.warn((String) TAG, "abort request: " + (TextUtils.isEmpty(getOperationType()) ? getTargetHttpUriRequest().getURI().toString() : getOperationType()));
            }
        } catch (Exception e2) {
            LogCatUtil.warn(TAG, "abort exception:", e2);
        }
    }

    /* access modifiers changed from: protected */
    public void noteTraficConsume(Response rpcResponse, HttpResponse httpResponse, long size) {
        long reqDataLength = -1;
        if (this.a != null && (this.a instanceof HttpPost)) {
            HttpEntity postEntity = this.a.getEntity();
            reqDataLength = postEntity != null ? postEntity.getContentLength() : -1;
        }
        long respDataLength = -1;
        if (!(rpcResponse == null || rpcResponse.getResData() == null)) {
            respDataLength = (long) rpcResponse.getResData().length;
        }
        if (respDataLength == -1 && (rpcResponse instanceof HttpUrlResponse)) {
            try {
                respDataLength = Long.parseLong(((HttpUrlResponse) rpcResponse).getHeader().getHead("Content-Length"));
            } catch (Exception e2) {
                LoggerFactory.getTraceLogger().error((String) TAG, (String) "parse Content-Length error");
            }
        }
        if (respDataLength == -1 && httpResponse != null) {
            Header[] headers = httpResponse.getHeaders("Content-Length");
            if (headers != null && headers.length > 0) {
                try {
                    respDataLength = Long.parseLong(headers[0].getValue());
                } catch (Exception e3) {
                    LoggerFactory.getTraceLogger().error((String) TAG, (String) "parse Content-Length error");
                }
            }
            if (-1 == respDataLength) {
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    respDataLength = entity.getContentLength();
                }
            }
        }
        if (respDataLength == -1 && size > 0) {
            respDataLength = size;
        }
        String noteUrl = this.mOriginRequest.getUrl();
        DataflowID type = DataflowID.HTTPCLIENT_DOWNLOAD;
        if (noteUrl != null && this.mTransportContext.isRpcBizType()) {
            noteUrl = noteUrl + MetaRecord.LOG_SEPARATOR + getOperationType();
            type = DataflowID.HTTPCLIENT_RPC;
        }
        ClientMonitor.getInstance().noteTraficConsume(null, noteUrl, reqDataLength, respDataLength, type, null);
    }

    /* access modifiers changed from: protected */
    public boolean isTraficConsumeAccept(String traficCheckUrl) {
        return ClientMonitor.getInstance().isTraficConsumeAccept(traficCheckUrl);
    }

    private TransportCallback g() {
        return this.mOriginRequest.getCallback();
    }

    /* access modifiers changed from: protected */
    public HttpResponse executeRequest() {
        setTimeout();
        HttpResponse httpResponse = null;
        try {
            setShouldReportTraffic(true);
            this.mLocalContext.setAttribute(TransportConstants.KEY_ORIGIN_REQUEST, this.mOriginRequest);
            HttpResponse httpResponse2 = executeExtClientRequest();
            if (httpResponse2 != null) {
                if (httpResponse2 != null) {
                    getOriginRequest().setHttpResponse(httpResponse2);
                    DataItemsUtil.putDataItem2ContainerAnyway(this.mTransportContext.getCurrentDataContainer(), "HRC", String.valueOf(httpResponse2.getStatusLine().getStatusCode()));
                }
                return httpResponse2;
            }
        } catch (Exception e2) {
            processExtTransException(e2);
        } catch (Throwable th) {
            if (httpResponse != null) {
                getOriginRequest().setHttpResponse(httpResponse);
                DataItemsUtil.putDataItem2ContainerAnyway(this.mTransportContext.getCurrentDataContainer(), "HRC", String.valueOf(httpResponse.getStatusLine().getStatusCode()));
            }
            throw th;
        }
        HttpResponse httpResponse3 = executeHttpClientRequest();
        if (httpResponse3 != null) {
            getOriginRequest().setHttpResponse(httpResponse3);
            DataItemsUtil.putDataItem2ContainerAnyway(this.mTransportContext.getCurrentDataContainer(), "HRC", String.valueOf(httpResponse3.getStatusLine().getStatusCode()));
        }
        return httpResponse3;
    }

    /* access modifiers changed from: protected */
    public HttpResponse executeExtClientRequest() {
        ExtTransportClient extTransportClient;
        HttpResponse httpResponse = null;
        if (isCanUseExtTransport()) {
            if (l()) {
                LogCatUtil.debug(TAG, "opeType:" + getOperationType() + ",go H2");
            } else {
                h();
                ExtTransportOffice extTransportOffice = ExtTransportOffice.getInstance();
                if (extTransportOffice.isEnableExtTransport(this.mContext)) {
                    extTransportClient = extTransportOffice.getExtTransportClient(this.mContext, this.mTransportContext);
                } else {
                    extTransportClient = null;
                }
                if (extTransportClient != null) {
                    LogCatUtil.debug(TAG, "By " + this.mTransportContext.currentReqInfo.protocol + " to request. operationType=" + getOperationType() + " url=" + getTargetHttpUriRequest().getURI().toString() + " allowRetry=" + this.mOriginRequest.allowRetry);
                    putStalledTime();
                    this.mTransportContext.getCurrentDataContainer().timeItemDot("ALL_TIME");
                    if (extTransportClient.getModuleCategory() != 0) {
                        getTargetHttpUriRequest().removeHeaders("Accept-Encoding");
                        getTargetHttpUriRequest().removeHeaders(H5AppHttpRequest.HEADER_CONNECTION);
                    }
                    httpResponse = extTransportClient.execute(getTargetHttpHost(), getTargetHttpUriRequest(), this.mLocalContext);
                    if (extTransportClient.getModuleCategory() == 0) {
                        etagRpcv2Adapter(httpResponse);
                    }
                    TransportStrategy.resetRpcErrorCount();
                }
            }
        }
        return httpResponse;
    }

    private void h() {
        try {
            if (this.mTransportContext.bizType == 1) {
                j();
                if (i()) {
                    this.mTransportContext.choseExtLinkType = 2;
                    LogCatUtil.debug(TAG, "inSpdyRpcList,API:" + getOperationType());
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "specialRpcGoSpdy ex:" + ex.toString());
        }
    }

    private boolean i() {
        String goSpdyApis = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.GO_SPDY_APIS);
        if (TextUtils.isEmpty(goSpdyApis)) {
            return false;
        }
        String operationType = getOperationType();
        for (String equals : goSpdyApis.split(",")) {
            if (TextUtils.equals(equals, operationType)) {
                return true;
            }
        }
        return false;
    }

    private void j() {
        try {
            if (this.mOriginRequest.isBgRpc() && isRpcRequest()) {
                if (MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.BGRPC_GO_SPDY_GRAY))) {
                    this.mTransportContext.choseExtLinkType = 2;
                    LogCatUtil.debug(TAG, "bgrpc go spdy,API:" + getOperationType());
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, ex);
        }
    }

    /* access modifiers changed from: protected */
    public HttpResponse executeHttpClientRequest() {
        LogCatUtil.debug(TAG, "By Http/Https to request. operationType=" + getOperationType() + "  method=" + getTargetHttpUriRequest().getMethod() + " url=" + getTargetHttpUriRequest().getURI().toString() + " allowRetry=" + this.mOriginRequest.allowRetry);
        if (!TextUtils.isEmpty(getOperationType())) {
            this.mTransportContext.dcList.clear();
        }
        k();
        getHttpClient().setHttpRequestRetryHandler(sHttpRequestRetryHandler);
        TransportStrategy.fillCurrentReqInfo(true, "https", this.mTransportContext);
        if (this.t) {
            DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), "RETRY", "T");
        }
        HttpParams httpParams = getTargetHttpUriRequest().getParams();
        httpParams.setParameter("http.protocol.element-charset", "utf-8");
        this.mTransportContext.getCurrentDataContainer().timeItemDot("ALL_TIME");
        HttpEntity postData = getPostData();
        if (postData != null) {
            this.mLocalContext.setAttribute(TransportConstants.KEY_IS_REPEATABLE, Boolean.valueOf(postData.isRepeatable()));
            this.mTransportContext.getCurrentDataContainer().putDataItem("REQ_SIZE", postData.getContentLength());
        }
        putStalledTime();
        HttpProxyWrapper proxyWrapper = determineProxyPlanner(httpParams);
        HttpUriRequest httpUriRequest = getTargetHttpUriRequest();
        if (!(httpUriRequest instanceof HttpEntityEnclosingRequest)) {
            this.mLocalContext.setAttribute(TransportConstants.KEY_IS_REPEATABLE, Boolean.valueOf(true));
        } else {
            HttpEntity entity = ((HttpEntityEnclosingRequest) httpUriRequest).getEntity();
            if (entity instanceof ZNetworkHttpEntityWrapper) {
                ((ZNetworkHttpEntityWrapper) entity).setHttpWorker(this);
            }
        }
        HttpHost targetHost = getTargetHttpHost();
        httpParams.setParameter("http.route.forced-route", HttpClientPlannerHelper.determineRoute(getHttpClient(), targetHost, httpUriRequest, this.mLocalContext));
        HttpResponse httpResponse = executeHttpClientRequest(targetHost, httpUriRequest, this.mLocalContext);
        if (proxyWrapper.lastGoodProxy != -1) {
            LAST_GOOD_PROXY = proxyWrapper.lastGoodProxy;
        }
        etagRpcv2Adapter(httpResponse);
        TransportStrategy.resetRpcErrorCount();
        return httpResponse;
    }

    private void k() {
        try {
            getHttpClient().getConnectionManager().closeExpiredConnections();
            getHttpClient().getConnectionManager().closeIdleConnections(30, TimeUnit.SECONDS);
            LogCatUtil.info(TAG, "close expired connections.");
        } catch (Throwable th) {
        }
    }

    /* access modifiers changed from: protected */
    public void setTimeout() {
        HttpParams httpParams = getTargetHttpUriRequest().getParams();
        long custTimeout = getOriginRequest().getTimeout();
        long networkTimeout = (long) TransportStrategy.getReadTimeout(this.mContext);
        long timeout = Math.max(custTimeout, networkTimeout);
        HttpConnectionParams.setSoTimeout(httpParams, (int) timeout);
        int connTimeout = TransportStrategy.getConnTimeout(this.mContext);
        HttpConnectionParams.setConnectionTimeout(httpParams, connTimeout);
        LogCatUtil.info(TAG, "setTimeout. custReadTimeout=" + custTimeout + ", networkReadTimeout=" + networkTimeout + ", endReadtimeout=" + timeout + ", connTimeout=" + connTimeout);
    }

    /* access modifiers changed from: protected */
    public HttpHost getTargetHttpHost() {
        if (this.u != null) {
            return this.u;
        }
        URI uri = getTargetHttpUriRequest().getURI();
        this.u = new HttpHost(uri.toURL().getHost(), uri.getPort(), uri.getScheme());
        return this.u;
    }

    /* access modifiers changed from: protected */
    public HttpResponse executeHttpClientRequest(HttpHost targetHost, HttpRequest request, HttpContext context) {
        HttpResponse httpResponse;
        if (a((HttpUriRequest) request)) {
            try {
                httpResponse = AndroidH2UrlConnection.getInstance(this.mContext).execute(targetHost, request, context);
            } catch (RequestSwitchDirectionException e2) {
            }
            return handleResponseForRedirect(request, request.getParams(), httpResponse);
        }
        httpResponse = getHttpClient().execute(targetHost, request, context);
        return handleResponseForRedirect(request, request.getParams(), httpResponse);
    }

    private boolean a(HttpUriRequest httpUriRequest) {
        try {
            if (isRpcRequest()) {
                return l();
            }
            return b(httpUriRequest);
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "isGoUrlConnection ex:" + ex.toString());
            return false;
        }
    }

    private boolean l() {
        try {
            if (!MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.RPC_GO_H2_SWITCH))) {
                LogCatUtil.debug(TAG, "rpcGoH2Switch is off");
                return false;
            } else if (VERSION.SDK_INT < 23 || !isRpcRequest() || !getTargetHttpUriRequest().getURI().getScheme().equalsIgnoreCase("https") || !c(getOperationType())) {
                return false;
            } else {
                LogCatUtil.printInfo(TAG, "API:" + getOperationType() + ",go H2");
                return true;
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "isGoUrlConnectionForRPC ex:" + ex.toString());
            return false;
        }
    }

    private boolean b(HttpUriRequest httpUriRequest) {
        try {
            if (!MiscUtils.grayscaleUtdidForABTest(TransportConfigureItem.GO_URLCONNECTION_SWITCH)) {
                LogCatUtil.debug(TAG, "go urlConnectSwitch is off");
                return false;
            } else if (VERSION.SDK_INT < 23 || !httpUriRequest.getURI().getScheme().equalsIgnoreCase("https") || isRpcRequest() || !b(httpUriRequest.getURI().getHost())) {
                return false;
            } else {
                LogCatUtil.debug(TAG, "host:" + httpUriRequest.getURI().getHost() + ",go H2");
                return true;
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "isGoUrlConnectionForRSRC ex:" + ex.toString());
            return false;
        }
    }

    private static boolean b(String host) {
        String urlHostList = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.URLCONNECTION_HOST_LIST);
        if (TextUtils.isEmpty(urlHostList)) {
            return false;
        }
        for (String shortHost : urlHostList.split(",")) {
            if (host.contains(shortHost)) {
                return true;
            }
        }
        return false;
    }

    private static boolean c(String operationType) {
        String goH2RpcList = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.RPC_H2_LIST);
        if (TextUtils.isEmpty(goH2RpcList) || TextUtils.isEmpty(operationType)) {
            return false;
        }
        for (String equals : goH2RpcList.split(",")) {
            if (TextUtils.equals(equals, operationType)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public HttpResponse handleResponseForRedirect(HttpRequest httpRequest, HttpParams httpParams, HttpResponse httpResponse) {
        if ((!TextUtils.isEmpty(getOperationType()) && !TextUtils.equals(getOperationType(), DownloadRequest.OPERATION_TYPE) && !TextUtils.equals(getOperationType(), H5HttpUrlRequest.OPERATION_TYPE)) || !this.redirectHandler.isRedirectRequested(httpResponse, this.mLocalContext)) {
            return httpResponse;
        }
        processRespCookies(httpResponse);
        if (this.q >= 5) {
            throw new RedirectException("Maximum redirects (5) exceeded");
        }
        this.q++;
        try {
            Thread.sleep(200);
        } catch (InterruptedException e2) {
            LogCatUtil.warn((String) TAG, "Wait cookie sync. " + e2.toString());
        }
        URI locationURI = this.redirectHandler.getLocationURI(httpResponse, this.mLocalContext);
        h(locationURI.toString());
        URI locationURI2 = a(locationURI);
        HttpHost newTargetHost = new HttpHost(locationURI2.toURL().getHost(), locationURI2.getPort(), locationURI2.getScheme());
        HttpGet redirectRequest = new HttpGet(locationURI2);
        redirectRequest.setHeaders(httpRequest.getAllHeaders());
        redirectRequest.setParams(httpParams);
        redirectRequest.removeHeaders("Cookie");
        redirectRequest.removeHeaders("Cookie2");
        redirectRequest.addHeader("Cookie", CookieAccessHelper.getCookie(locationURI2.toString(), this.mContext));
        try {
            DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.REDIRECT_URL, locationURI2.toString());
            LogCatUtil.info(TAG, "Redirect request new headers : ");
            printHeaderLog(redirectRequest.getAllHeaders());
        } catch (Exception e3) {
            LogCatUtil.warn((String) TAG, (Throwable) e3);
        }
        redirectRequest.getParams().setParameter("http.route.forced-route", HttpClientPlannerHelper.determineRoute(getHttpClient(), newTargetHost, redirectRequest, this.mLocalContext));
        LogCatUtil.debug(TAG, "By Http/Https to redirect request. operationType=" + getOperationType() + "  method=" + redirectRequest.getMethod() + " url=" + redirectRequest.getURI().toString());
        return handleResponseForRedirect(redirectRequest, redirectRequest.getParams(), getHttpClient().execute(newTargetHost, (HttpRequest) redirectRequest, this.mLocalContext));
    }

    private URI a(URI uri) {
        if (!MiscUtils.isTFSHost(uri.getHost())) {
            return uri;
        }
        String cdnLocationUrlStr = MiscUtils.replaceTFS2CDN(uri);
        URI tmpUri = new URI(cdnLocationUrlStr);
        this.mTransportContext.url = cdnLocationUrlStr;
        LogCatUtil.info(TAG, "Origin url: " + uri.toString() + " convert to " + cdnLocationUrlStr);
        return tmpUri;
    }

    /* access modifiers changed from: protected */
    public HttpProxyWrapper determineProxyPlanner(HttpParams httpParams) {
        HttpProxyWrapper httpHostWrapper = new HttpProxyWrapper();
        httpHostWrapper.proxy = NetworkUtils.getProxyOfEnhanced(this.mContext);
        if (httpHostWrapper.proxy == null) {
            httpParams.setParameter("http.route.default-proxy", ConnRouteParams.NO_HOST);
            httpHostWrapper.proxy = null;
        } else if (getOriginRequest().isCapture()) {
            LogCatUtil.info(TAG, "determineProxyPlanner. request capture: " + getOriginRequest().isCapture());
            a(httpHostWrapper.proxy);
            httpParams.setParameter("http.route.default-proxy", httpHostWrapper.proxy);
        } else {
            int networkType = NetworkUtils.getNetworkType(this.mContext);
            if (LAST_GOOD_PROXY != -1) {
                if (LAST_GOOD_PROXY == 1) {
                    setProxyModel(httpParams, httpHostWrapper.proxy);
                } else {
                    a(httpParams);
                    httpHostWrapper.proxy = null;
                }
            } else if ((!a(networkType) || this.o != -1) && this.o != 1) {
                a(httpParams);
                httpHostWrapper.lastGoodProxy = 0;
                httpHostWrapper.proxy = null;
            } else {
                setProxyModel(httpParams, httpHostWrapper.proxy);
                httpHostWrapper.lastGoodProxy = 1;
            }
        }
        return httpHostWrapper;
    }

    private boolean a(int networkType) {
        LogCatUtil.info(TAG, "isFirstUseProxy request capture: " + getOriginRequest().isCapture());
        if (MiscUtils.isDebugger(this.mContext)) {
            if (TransportConfigureManager.getInstance().equalsString(TransportConfigureItem.DEBUG_HW_FIRST_USE_PROXY, "T")) {
                return true;
            }
            LogCatUtil.info(TAG, "isFirstUseProxy. DEBUG_HW_FIRST_USE_PROXY is false.");
            return false;
        } else if (networkType != 1 && !getOriginRequest().isCapture()) {
            return false;
        } else {
            LogCatUtil.info(TAG, "firstUseProxy");
            return true;
        }
    }

    private void a(HttpParams httpParams) {
        httpParams.setParameter("http.route.default-proxy", ConnRouteParams.NO_HOST);
        if (this.o == -1) {
            this.n = 1;
        }
    }

    /* access modifiers changed from: protected */
    public void setProxyModel(HttpParams httpParams, HttpHost proxy) {
        a(proxy);
        httpParams.setParameter("http.route.default-proxy", proxy);
        if (this.o == -1) {
            this.n = 0;
        }
    }

    /* access modifiers changed from: protected */
    public void etagRpcv2Adapter(HttpResponse httpResponse) {
        Header[] allHeaders;
        try {
            String rpcVersion = getOriginRequest().getTag(TransportConstants.KEY_RPC_VERSION);
            if (!TextUtils.isEmpty(rpcVersion) && TextUtils.equals(rpcVersion, "2")) {
                Header[] headers = httpResponse.getHeaders("Result-Status");
                if (httpResponse.getStatusLine().getStatusCode() == 200 && TextUtils.equals(headers[0].getValue(), WbAuthConstants.AUTH_FAILED_INSTALL_APP_COUNTERFEIT_CODE)) {
                    httpResponse.setStatusLine(HttpVersion.HTTP_1_1, 304, "Not Modified");
                }
            }
        } catch (Exception e2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("print headers:  ");
            for (Header header : httpResponse.getAllHeaders()) {
                stringBuilder.append(header.getName()).append("=").append(header.getValue()).append(",");
            }
            LogCatUtil.error(TAG, "Illegal response. " + stringBuilder.toString(), e2);
        }
    }

    private void a(HttpHost proxy) {
        if (proxy != null) {
            DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.PROXY, "T");
            DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), "TARGET_HOST", proxy.getHostName() + ":" + proxy.getPort());
            return;
        }
        DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.PROXY, "F");
    }

    /* access modifiers changed from: protected */
    public void putStalledTime() {
        if (this.m != -1) {
            DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.STALLED_TIME, String.valueOf(this.m));
            DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), "RPC_ALL_TIME", String.valueOf(this.mTransportContext.startExecutionTime));
        }
    }

    public String getOperationType() {
        if (!TextUtils.isEmpty(this.l)) {
            return this.l;
        }
        this.l = getOriginRequest().getTag(TransportConstants.KEY_OPERATION_TYPE);
        if (!TextUtils.isEmpty(this.l)) {
            return this.l;
        }
        return "";
    }

    /* access modifiers changed from: protected */
    public AndroidHttpClient getHttpClient() {
        return this.mHttpManager.getHttpClient();
    }

    /* access modifiers changed from: protected */
    public void addRequestHeaders() {
        addEtag2RequestHeader();
        copyHeaders();
        addCookie2Header();
        String alipayLocaleDes = MiscUtils.getAlipayLocaleDes();
        if (!TextUtils.isEmpty(alipayLocaleDes)) {
            getTargetHttpUriRequest().addHeader("Accept-Language", alipayLocaleDes);
        }
        AndroidHttpClient.modifyRequestToAcceptGzipResponse(getTargetHttpUriRequest());
        AndroidHttpClient.modifyRequestToKeepAlive(getTargetHttpUriRequest());
        if (RetryService.getInstance().isSupportResend(getOperationType(), getOriginRequest().allowRetry)) {
            getTargetHttpUriRequest().addHeader("Retryable", "1");
        }
        if (isUseSelfEncrypt()) {
            getTargetHttpUriRequest().addHeader(TransportConstants.KEY_X_MGS_ENCRYPTION, "1");
            getTargetHttpUriRequest().addHeader(TransportConstants.KEY_X_CONTENT_ENCODING, "mgss");
        }
        printHeaderLog(getTargetHttpUriRequest().getAllHeaders());
    }

    /* access modifiers changed from: protected */
    public void printHeaderLog(Header[] allHeaders) {
        if (allHeaders != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Header header : allHeaders) {
                stringBuilder.append(header.getName() + ":" + header.getValue()).append(", ");
            }
            LogCatUtil.info(TAG, "printHeaderLog.  : " + stringBuilder.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void copyHeaders() {
        copyHeaders(getTargetHttpUriRequest(), getHeaders());
    }

    public void copyHeaders(HttpUriRequest httpUriRequest, ArrayList<Header> headers) {
        if (headers != null && !headers.isEmpty()) {
            Iterator<Header> it = headers.iterator();
            while (it.hasNext()) {
                httpUriRequest.addHeader(it.next());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void addCookie2Header() {
        getTargetHttpUriRequest().addHeader("Cookie", GwCookieCacheHelper.getCookieWrapper(this.mContext, getTargetUri().toString(), this.mTransportContext));
    }

    /* access modifiers changed from: protected */
    public boolean isCanUseExtTransport() {
        String forceHttp = this.mOriginRequest.getTag(TransportConstants.KEY_FORCE_HTTP);
        if ((!TextUtils.isEmpty(forceHttp) && TextUtils.equals(forceHttp, "true")) || !MiscUtils.isInAlipayClient(this.mContext)) {
            return false;
        }
        if (MiscUtils.isPushProcess(this.mContext)) {
            LogCatUtil.printInfo(TAG, "===> Rpc push process does not use spdy <===");
            return false;
        }
        URL targetURL = n();
        if (!this.mOriginRequest.isContainerHeader("Version") || !TransportStrategy.isAlipayHost(targetURL.getHost())) {
            return false;
        }
        if (!StrategyUtil.isSwitchRpc(getOperationType()) || TransportConfigureManager.getInstance().equalsString(TransportConfigureItem.AFTER_LOGIN_GO_EXT, "T")) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public HttpHost getOriginHttpHost() {
        if (this.d != null) {
            return this.d;
        }
        URL originTargetUrl = n();
        this.d = new HttpHost(originTargetUrl.getHost(), m(), originTargetUrl.getProtocol());
        return this.d;
    }

    private int m() {
        URL targetUrl = n();
        if (targetUrl.getPort() == -1) {
            return targetUrl.getDefaultPort();
        }
        return targetUrl.getPort();
    }

    private URL n() {
        if (this.e != null) {
            return this.e;
        }
        this.e = new URL(this.mOriginRequest.getUrl());
        return this.e;
    }

    /* access modifiers changed from: protected */
    public HashMap<String, String> getContentType(String string) {
        HashMap result = new HashMap();
        String[] split = string.split(";");
        int length = split.length;
        for (int i2 = 0; i2 < length; i2++) {
            String param = split[i2];
            String[] pairs = param.indexOf(61) == -1 ? new String[]{"Content-Type", param} : param.split("=");
            result.put(pairs[0], pairs[1]);
        }
        return result;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0153 A[SYNTHETIC, Splitter:B:36:0x0153] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alipay.mobile.common.transport.Response handleResponse(com.alipay.mobile.common.transport.http.HttpUrlRequest r28, org.apache.http.HttpResponse r29, int r30, java.lang.String r31) {
        /*
            r27 = this;
            java.lang.String r4 = "HttpWorker"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "开始handle，handleResponse-1,"
            r5.<init>(r6)
            java.lang.Thread r6 = java.lang.Thread.currentThread()
            long r6 = r6.getId()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.printInfo(r4, r5)
            org.apache.http.HttpEntity r23 = r29.getEntity()
            r21 = 0
            if (r23 == 0) goto L_0x017b
            org.apache.http.StatusLine r4 = r29.getStatusLine()
            int r4 = r4.getStatusCode()
            r5 = 200(0xc8, float:2.8E-43)
            if (r4 != r5) goto L_0x017b
            java.lang.String r4 = "HttpWorker"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "200，开始处理，handleResponse-2,threadid = "
            r5.<init>(r6)
            java.lang.Thread r6 = java.lang.Thread.currentThread()
            long r6 = r6.getId()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r4, r5)
            r19 = 0
            java.io.ByteArrayOutputStream r20 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x019f }
            r20.<init>()     // Catch:{ all -> 0x019f }
            long r25 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0145 }
            r4 = 0
            r0 = r27
            r1 = r23
            r2 = r20
            com.alipay.mobile.common.transport.http.ResponseSizeModel r24 = r0.writeData(r1, r4, r2)     // Catch:{ all -> 0x0145 }
            byte[] r15 = r20.toByteArray()     // Catch:{ all -> 0x0145 }
            boolean r4 = r27.isUseSelfEncrypt()     // Catch:{ all -> 0x0145 }
            if (r4 == 0) goto L_0x007f
            r0 = r24
            long r4 = r0.compressedSize     // Catch:{ all -> 0x0145 }
            r6 = 0
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 <= 0) goto L_0x007f
            r0 = r27
            com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack r4 = r0.clientRpcPack     // Catch:{ all -> 0x0145 }
            byte[] r15 = com.alipay.mobile.common.transport.http.selfencrypt.SelfEncryptUtils.getDecryptedContent(r15, r4)     // Catch:{ all -> 0x0145 }
        L_0x007f:
            java.lang.String r4 = "ETag"
            r0 = r29
            org.apache.http.Header[] r18 = r0.getHeaders(r4)     // Catch:{ all -> 0x0145 }
            r0 = r18
            int r4 = r0.length     // Catch:{ all -> 0x0145 }
            if (r4 <= 0) goto L_0x0161
            byte[] r14 = a(r15)     // Catch:{ all -> 0x0145 }
            if (r14 != 0) goto L_0x00e3
            r4 = 0
            r0 = r27
            r0.j = r4     // Catch:{ all -> 0x0145 }
            java.lang.String r4 = "HttpWorker"
            java.lang.String r5 = "[handleResponse]  Etag fail"
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r4, r5)     // Catch:{ all -> 0x0145 }
        L_0x009e:
            r0 = r27
            com.alipay.mobile.common.transport.http.HttpManager r4 = r0.mHttpManager     // Catch:{ all -> 0x0145 }
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0145 }
            long r5 = r5 - r25
            r4.addSocketTime(r5)     // Catch:{ all -> 0x0145 }
            com.alipay.mobile.common.transport.http.HttpUrlResponse r22 = new com.alipay.mobile.common.transport.http.HttpUrlResponse     // Catch:{ all -> 0x0145 }
            r0 = r27
            r1 = r29
            com.alipay.mobile.common.transport.http.HttpUrlHeader r4 = r0.handleResponseHeader(r1)     // Catch:{ all -> 0x0145 }
            r0 = r22
            r1 = r30
            r2 = r31
            r0.<init>(r4, r1, r2, r15)     // Catch:{ all -> 0x0145 }
            r0 = r27
            r1 = r22
            r2 = r29
            r0.fillResponse(r1, r2)     // Catch:{ all -> 0x01a1 }
            r0 = r27
            java.lang.String r4 = r0.k
            r0 = r27
            r0.e(r4)
            r20.close()     // Catch:{ Exception -> 0x0168 }
        L_0x00d3:
            r27.consumeContent()
            java.lang.String r4 = "HttpWorker"
            java.lang.String r5 = "finally,handleResponse"
            com.alipay.mobile.common.transport.utils.LogCatUtil.printInfo(r4, r5)
            r21 = r22
        L_0x00df:
            r27.consumeContent()
            return r21
        L_0x00e3:
            com.alipay.mobile.common.transport.http.HttpUrlRequest r4 = r27.getOriginRequest()     // Catch:{ all -> 0x0145 }
            boolean r4 = r4.isUseEtag()     // Catch:{ all -> 0x0145 }
            if (r4 == 0) goto L_0x009e
            r4 = 0
            r4 = r18[r4]     // Catch:{ all -> 0x0145 }
            java.lang.String r17 = r4.getValue()     // Catch:{ all -> 0x0145 }
            com.alipay.mobile.common.transport.http.CachedResponseWrapper r8 = new com.alipay.mobile.common.transport.http.CachedResponseWrapper     // Catch:{ all -> 0x0145 }
            r8.<init>()     // Catch:{ all -> 0x0145 }
            r0 = r17
            r8.setEtag(r0)     // Catch:{ all -> 0x0145 }
            r8.setValue(r14)     // Catch:{ all -> 0x0145 }
            org.apache.http.HttpEntity r4 = r29.getEntity()     // Catch:{ all -> 0x0145 }
            org.apache.http.Header r4 = r4.getContentType()     // Catch:{ all -> 0x0145 }
            r8.setTypeHeader(r4)     // Catch:{ all -> 0x0145 }
            r0 = r27
            com.alipay.mobile.common.transport.http.HttpContextExtend r4 = r0.h     // Catch:{ all -> 0x0145 }
            r5 = 0
            java.lang.String r6 = "ETag"
            r0 = r27
            java.lang.String r7 = r0.k     // Catch:{ all -> 0x0145 }
            java.util.Date r9 = new java.util.Date     // Catch:{ all -> 0x0145 }
            r9.<init>()     // Catch:{ all -> 0x0145 }
            long r9 = r9.getTime()     // Catch:{ all -> 0x0145 }
            r11 = 604800000(0x240c8400, double:2.988109026E-315)
            java.lang.String r13 = "Serializable"
            r4.putSerializable(r5, r6, r7, r8, r9, r11, r13)     // Catch:{ all -> 0x0145 }
            r4 = 1
            r0 = r27
            r0.j = r4     // Catch:{ all -> 0x0145 }
            java.lang.String r4 = "HttpWorker"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0145 }
            java.lang.String r6 = "[handleResponse]  Etag success ， etag = "
            r5.<init>(r6)     // Catch:{ all -> 0x0145 }
            r0 = r17
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ all -> 0x0145 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0145 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r4, r5)     // Catch:{ all -> 0x0145 }
            goto L_0x009e
        L_0x0145:
            r4 = move-exception
            r19 = r20
        L_0x0148:
            r0 = r27
            java.lang.String r5 = r0.k
            r0 = r27
            r0.e(r5)
            if (r19 == 0) goto L_0x0156
            r19.close()     // Catch:{ Exception -> 0x0172 }
        L_0x0156:
            r27.consumeContent()
            java.lang.String r5 = "HttpWorker"
            java.lang.String r6 = "finally,handleResponse"
            com.alipay.mobile.common.transport.utils.LogCatUtil.printInfo(r5, r6)
            throw r4
        L_0x0161:
            r4 = 0
            r0 = r27
            r0.j = r4     // Catch:{ all -> 0x0145 }
            goto L_0x009e
        L_0x0168:
            r16 = move-exception
            java.lang.String r4 = "HttpWorker"
            r0 = r16
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r4, r0)
            goto L_0x00d3
        L_0x0172:
            r16 = move-exception
            java.lang.String r5 = "HttpWorker"
            r0 = r16
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r5, r0)
            goto L_0x0156
        L_0x017b:
            org.apache.http.StatusLine r4 = r29.getStatusLine()
            int r4 = r4.getStatusCode()
            r5 = 304(0x130, float:4.26E-43)
            if (r4 != r5) goto L_0x0196
            r4 = 0
            r0 = r27
            r1 = r29
            r2 = r30
            r3 = r31
            com.alipay.mobile.common.transport.http.HttpUrlResponse r21 = r0.a(r1, r2, r3, r4)
            goto L_0x00df
        L_0x0196:
            r0 = r27
            r1 = r29
            r0.b(r1)
            goto L_0x00df
        L_0x019f:
            r4 = move-exception
            goto L_0x0148
        L_0x01a1:
            r4 = move-exception
            r19 = r20
            r21 = r22
            goto L_0x0148
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.http.HttpWorker.handleResponse(com.alipay.mobile.common.transport.http.HttpUrlRequest, org.apache.http.HttpResponse, int, java.lang.String):com.alipay.mobile.common.transport.Response");
    }

    private HttpUrlResponse a(HttpResponse httpResponse, int resCode, String resMsg, HttpUrlResponse response) {
        CachedResponseWrapper crw = (CachedResponseWrapper) d(this.k);
        if (crw != null) {
            response = new HttpUrlResponse(handleResponseHeader(httpResponse), resCode, resMsg, crw.getValue());
            long period = getPeriod(httpResponse);
            Header typeHeader = crw.getTypeHeader();
            String charset = null;
            String contentType = null;
            if (typeHeader != null) {
                HashMap contentTypes = getContentType(typeHeader.getValue());
                charset = contentTypes.get("charset");
                contentType = contentTypes.get("Content-Type");
            }
            response.setContentType(contentType);
            response.setCharset(charset);
            response.setCreateTime(System.currentTimeMillis());
            response.setPeriod(period);
            LogCatUtil.info(TAG, "[processCode304] Response cache data.");
        } else {
            LogCatUtil.info(TAG, "[processCode304] Response no cache data.");
        }
        return response;
    }

    /* access modifiers changed from: protected */
    public HttpUrlHeader handleResponseHeader(HttpResponse httpResponse) {
        Header[] allHeaders;
        HttpUrlHeader header = new HttpUrlHeader();
        for (Header h2 : httpResponse.getAllHeaders()) {
            header.addHead(h2.getName(), h2.getValue());
        }
        return header;
    }

    /* access modifiers changed from: protected */
    public void fillResponse(HttpUrlResponse response, HttpResponse httpResponse) {
        try {
            long period = getPeriod(httpResponse);
            String charset = null;
            String contentType = null;
            if (httpResponse.getEntity() != null) {
                Header typeHeader = httpResponse.getEntity().getContentType();
                if (typeHeader != null) {
                    HashMap contentTypes = getContentType(typeHeader.getValue());
                    charset = contentTypes.get(HeaderConstant.HEADER_KEY_CHARSET);
                    contentType = contentTypes.get("Content-Type");
                }
            }
            response.setContentType(contentType);
            response.setCharset(charset);
            response.setCreateTime(System.currentTimeMillis());
            response.setPeriod(period);
        } catch (Throwable e2) {
            LogCatUtil.warn((String) TAG, e2);
        }
    }

    /* access modifiers changed from: protected */
    public long getPeriod(HttpResponse httpResponse) {
        long expires = 0;
        Header cache = httpResponse.getFirstHeader("Cache-Control");
        if (cache != null) {
            String[] strs = cache.getValue().split("=");
            if (strs.length >= 2) {
                try {
                    return parserMaxage(strs);
                } catch (NumberFormatException e2) {
                    LogCatUtil.warn((String) TAG, (Throwable) e2);
                }
            }
        }
        Header expire = httpResponse.getFirstHeader("Expires");
        if (expire != null) {
            try {
                expires = AndroidHttpClient.parseDate(expire.getValue()) - System.currentTimeMillis();
            } catch (Throwable e3) {
                LogCatUtil.warn((String) TAG, "parse expire exception. expire value: " + expire.getValue() + ",  exception: " + e3.toString());
                expires = 0;
            }
        }
        return expires;
    }

    /* access modifiers changed from: protected */
    public long parserMaxage(String[] strs) {
        for (int i2 = 0; i2 < strs.length; i2++) {
            if ("max-age".equalsIgnoreCase(strs[i2]) && strs[i2 + 1] != null) {
                try {
                    return Long.parseLong(strs[i2 + 1]);
                } catch (Exception e2) {
                    LogCatUtil.warn((String) TAG, "parserMaxage exception : " + e2.toString());
                }
            }
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public boolean willHandleOtherCode(int resCode, String resMsg) {
        if (resCode == 304) {
            return true;
        }
        return false;
    }

    public HttpUrlRequest getOriginRequest() {
        return this.mOriginRequest;
    }

    /* access modifiers changed from: protected */
    public ResponseSizeModel writeData(HttpEntity httpEntity, long hasReaded, OutputStream outstream) {
        if (outstream == null) {
            consumeContent();
            throw new IllegalArgumentException("Output stream may not be null");
        }
        InputStream content = httpEntity.getContent();
        RpcBufferedInputStream bufferedInputStream = new RpcBufferedInputStream(content);
        InputStream instream = AndroidHttpClient.getUngzippedContent(bufferedInputStream, httpEntity.getContentEncoding());
        boolean isEstimateRtt = o();
        long length = httpEntity.getContentLength();
        long startTime = System.currentTimeMillis();
        long everyStartReadTime = startTime;
        long read = hasReaded;
        long totalLength = hasReaded + length;
        try {
            byte[] tmp = new byte[2048];
            while (true) {
                int len = instream.read(tmp);
                if (len == -1 || this.mOriginRequest.isCanceled()) {
                    outstream.flush();
                } else {
                    a(isEstimateRtt, everyStartReadTime);
                    outstream.write(tmp, 0, len);
                    read += (long) len;
                    if (g() != null && length > 0) {
                        g().onProgressUpdate(this.mOriginRequest, ((double) read) / ((double) totalLength));
                    }
                    if (isEstimateRtt) {
                        everyStartReadTime = System.currentTimeMillis();
                    }
                }
            }
            outstream.flush();
            if (this.mOriginRequest.isCanceled()) {
                throw new HttpException(Integer.valueOf(13), "Cancel request :" + this.mOriginRequest.getUrl() + ",cancelMsg:" + this.mOriginRequest.getCancelMsg());
            }
            ResponseSizeModel responseSizeModel = new ResponseSizeModel((long) bufferedInputStream.getReaded(), read - hasReaded);
            try {
                outstream.flush();
                LogCatUtil.debug(TAG, "operationType=" + getOperationType() + " ,url=" + getTargetHttpUriRequest().getURI().toString() + " ,readed data length: " + read + ",header content-length: " + length);
                a(bufferedInputStream);
            } catch (Throwable th) {
            }
            if (this.mTransportContext.currentReqInfo != null) {
                if (TextUtils.isEmpty(this.mTransportContext.getCurrentDataContainer().getDataItem("ALL_TIME"))) {
                    this.mTransportContext.getCurrentDataContainer().timeItemRelease("ALL_TIME");
                }
                if (TextUtils.isEmpty(this.mTransportContext.getCurrentDataContainer().getDataItem(RPCDataItems.READ_TIME))) {
                    this.mTransportContext.getCurrentDataContainer().putDataItem(RPCDataItems.READ_TIME, Integer.toString((int) (System.currentTimeMillis() - startTime)));
                }
            }
            IOUtil.closeStream(content);
            IOUtil.closeStream(instream);
            consumeContent();
            return responseSizeModel;
        } catch (Throwable th2) {
            try {
                outstream.flush();
                LogCatUtil.debug(TAG, "operationType=" + getOperationType() + " ,url=" + getTargetHttpUriRequest().getURI().toString() + " ,readed data length: " + read + ",header content-length: " + length);
                a(bufferedInputStream);
            } catch (Throwable th3) {
            }
            if (this.mTransportContext.currentReqInfo != null) {
                if (TextUtils.isEmpty(this.mTransportContext.getCurrentDataContainer().getDataItem("ALL_TIME"))) {
                    this.mTransportContext.getCurrentDataContainer().timeItemRelease("ALL_TIME");
                }
                if (TextUtils.isEmpty(this.mTransportContext.getCurrentDataContainer().getDataItem(RPCDataItems.READ_TIME))) {
                    this.mTransportContext.getCurrentDataContainer().putDataItem(RPCDataItems.READ_TIME, Integer.toString((int) (System.currentTimeMillis() - startTime)));
                }
            }
            IOUtil.closeStream(content);
            IOUtil.closeStream(instream);
            consumeContent();
            throw th2;
        }
    }

    private static void a(boolean isEstimateRtt, long everyStartReadTime) {
        if (isEstimateRtt) {
            AlipayQosService.getInstance().estimateByStartTime(everyStartReadTime, 5);
        }
    }

    private boolean o() {
        if (this.mTransportContext.isRequestByMRPC()) {
            return false;
        }
        return true;
    }

    private void a(RpcBufferedInputStream bufferedInputStream) {
        try {
            this.mHttpManager.addDataSize((long) bufferedInputStream.getReaded());
            this.v += (long) bufferedInputStream.getReaded();
        } catch (Throwable ex) {
            LogCatUtil.error(TAG, "calcTrafficConsume exception", ex);
        }
    }

    /* access modifiers changed from: protected */
    public void addEtag2RequestHeader() {
        if (!getOriginRequest().isUseEtag()) {
            LogCatUtil.printInfo(TAG, "addEtag2RequestHeader. don't use etag. go return.");
            return;
        }
        HttpUriRequest httpUriRequest = getTargetHttpUriRequest();
        this.k = String.valueOf(this.mOriginRequest.hashCode());
        Object cachedValue = d(this.k);
        if (cachedValue == null || (cachedValue instanceof Exception)) {
            this.i = false;
            return;
        }
        httpUriRequest.addHeader(new BasicHeader("If-None-Match", ((CachedResponseWrapper) cachedValue).getEtag()));
        this.i = true;
    }

    private Object d(String key) {
        try {
            Serializable obj = this.h.getSerializable(null, key);
            if (!(obj instanceof CachedResponseWrapper)) {
                return obj;
            }
            CachedResponseWrapper cache = (CachedResponseWrapper) obj;
            byte[] _data = b(cache.getValue());
            if (_data == null) {
                e(key);
                return null;
            }
            cache.setValue(_data);
            return obj;
        } catch (Exception e2) {
            LogCatUtil.warn((String) TAG, (Throwable) e2);
            return null;
        }
    }

    private void e(String etagCacheKey) {
        try {
            if (this.i && !this.j) {
                this.h.remove(etagCacheKey);
                LogCatUtil.printInfo(TAG, "removeEtagFromCache，完成," + Thread.currentThread().getId());
            }
        } catch (Throwable e2) {
            LogCatUtil.error(TAG, "removeEtagFromCache", e2);
        }
    }

    public Response processResponse(HttpResponse httpResponse, HttpUrlRequest httpUrlRequest) {
        int resCode = httpResponse.getStatusLine().getStatusCode();
        String resMsg = httpResponse.getStatusLine().getReasonPhrase();
        LogCatUtil.debug(TAG, "Url: " + httpUrlRequest.getUrl() + " resCode:" + resCode);
        if (resCode == 206 || resCode == 200 || willHandleOtherCode(resCode, resMsg)) {
            a(httpResponse);
            return handleResponse(httpUrlRequest, httpResponse, resCode, resMsg);
        }
        b(httpResponse);
        throw new HttpException(Integer.valueOf(httpResponse.getStatusLine().getStatusCode()), httpResponse.getStatusLine().getReasonPhrase());
    }

    private void a(HttpResponse httpResponse) {
        if (httpResponse == null) {
            try {
                LogCatUtil.debug(TAG, "processRetry,httpResponse is null");
            } catch (Throwable ex) {
                LogCatUtil.error(TAG, "processRetry ", ex);
            }
        } else if (httpResponse.getStatusLine().getStatusCode() == 200) {
            Header header = httpResponse.getFirstHeader("Retryable");
            if (header != null) {
                String retryValue = header.getValue();
                LogCatUtil.debug(TAG, "response header [retryable] = " + retryValue);
                String api = getOperationType();
                if (TextUtils.isEmpty(api)) {
                    LogCatUtil.debug(TAG, "operationType is null,not rpc");
                } else {
                    a(retryValue, api);
                }
            }
        }
    }

    private static void a(String retryValue, String api) {
        if (TextUtils.equals(retryValue, "1")) {
            f(api);
        } else if (TextUtils.equals(retryValue, "0")) {
            g(api);
        }
    }

    private static void f(String operationType) {
        try {
            RetryService.getInstance().addOperationTypeToRetryList(operationType);
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, ex);
        }
    }

    private static void g(String operationType) {
        try {
            RetryService.getInstance().removeOpetationTypeFromRetryList(operationType);
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, ex);
        }
    }

    public int hashCode() {
        HttpUrlRequest request = getOriginRequest();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(request.getUrl());
        String reqDataDigest = request.getTag(TransportConstants.KEY_REQ_DATA_DIGEST);
        if (!TextUtils.isEmpty(reqDataDigest)) {
            stringBuilder.append(reqDataDigest);
        }
        if (!TextUtils.isEmpty(request.getContentType())) {
            stringBuilder.append(request.getContentType());
        }
        if (!TextUtils.isEmpty(getOperationType())) {
            stringBuilder.append(getOperationType());
        }
        return hashcode(stringBuilder.toString().hashCode());
    }

    /* access modifiers changed from: 0000 */
    public int hashcode(int h2) {
        int h3 = h2 ^ ((h2 >>> 20) ^ (h2 >>> 12));
        return ((h3 >>> 7) ^ h3) ^ (h3 >>> 4);
    }

    /* access modifiers changed from: protected */
    public void processExtTransException(Exception e2) {
        MonitorErrorLogHelper.warn(TAG, new ExtTransportException("", MiscUtils.getRootCause(e2)));
        if (this.mOriginRequest.isCanceled()) {
            throw new HttpException(Integer.valueOf(13), "Cancel request :" + this.mOriginRequest.getUrl() + ",cancelMsg:" + this.mOriginRequest.getCancelMsg());
        } else if (MiscUtils.grayscale(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.NO_DOWN_HTTPS))) {
            LogCatUtil.printInfo(TAG, "连接失败,没有开启使用Https进行重试: " + e2.getMessage());
            throw e2;
        } else if (!TransportStrategy.isDowngradeToHttps()) {
            LogCatUtil.printInfo(TAG, "连接失败,重试超过3次: " + e2.getMessage());
            throw e2;
        } else if (!RetryService.getInstance().isSupportResend(getOperationType(), this.mOriginRequest.allowRetry)) {
            LogCatUtil.printInfo(TAG, "连接失败,不允许使用Https进行重试: " + e2.getMessage());
            throw e2;
        } else {
            LogCatUtil.error(TAG, "扩展传输模块连接失败,使用Https进行重试", e2);
            p();
        }
    }

    private void p() {
        this.t = true;
        DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.DOWN, "T");
        TransportStrategy.incrementRpcErrorCount();
    }

    /* access modifiers changed from: protected */
    public void processRespCookies(HttpResponse httpResponse) {
        if (!MiscUtils.isInAlipayClient(this.mContext) || !MiscUtils.isPushProcess(this.mContext)) {
            LoginRefreshHelper.checkIn(this, this.mContext);
            List<Cookie> cookies = this.b.getCookies();
            a(cookies);
            if (cookies.isEmpty()) {
                LogCatUtil.debug(TAG, "processRespCookies. cookies is empty");
                return;
            }
            Map localCacheCookies = new LinkedHashMap();
            for (Cookie cookie : cookies) {
                if (isRpcRequest() && !TextUtils.isEmpty(cookie.getName()) && !TextUtils.isEmpty(cookie.getValue())) {
                    localCacheCookies.put(cookie.getName().trim(), cookie.getValue().trim());
                }
                StringBuilder cookieStrBuilder = new StringBuilder();
                cookieStrBuilder.append(cookie.getName()).append("=").append(cookie.getValue());
                if (!TextUtils.isEmpty(cookie.getDomain())) {
                    cookieStrBuilder.append("; domain=").append(cookie.getDomain());
                }
                if (!TextUtils.isEmpty(cookie.getPath())) {
                    cookieStrBuilder.append("; path=").append(cookie.getPath());
                }
                if (cookie.getExpiryDate() != null) {
                    try {
                        cookieStrBuilder.append("; expires=").append(DateUtils.formatDate(cookie.getExpiryDate(), "EEE, dd-MMM-yyyy HH:mm:ss z"));
                    } catch (Throwable e2) {
                        LogCatUtil.warn((String) TAG, "expires format exception. " + e2.toString());
                    }
                }
                if (cookie.isSecure()) {
                    cookieStrBuilder.append("; Secure");
                }
                String cookieString = cookieStrBuilder.toString();
                String targetUriStr = getTargetUri().toString();
                String domain = cookie.getDomain();
                if (TextUtils.isEmpty(domain)) {
                    domain = targetUriStr;
                } else if (!domain.startsWith(".")) {
                    domain = "." + domain;
                }
                LogCatUtil.debug(TAG, "set cookie. domain=" + domain + "  cookie=" + cookieString + " .url=" + targetUriStr);
                CookieAccessHelper.applyCookie(domain, cookieString, this.mContext);
            }
            if (!localCacheCookies.isEmpty()) {
                GwCookieCacheHelper.setCookies(MiscUtils.getTopDomain(getTargetUri().toString()), localCacheCookies);
            }
            localCacheCookies.clear();
            CookieAccessHelper.asyncFlushCookie();
            return;
        }
        LogCatUtil.info(TAG, "processRespCookies. Another process is not operating a cookie.");
    }

    private void a(List<Cookie> cookies) {
        if (this.mOriginRequest.isResetCookie()) {
            b(cookies);
        } else {
            MiscUtils.isDebugger(this.mContext);
        }
    }

    private void b(List<Cookie> cookies) {
        if (TransportConfigureManager.getInstance().equalsString(TransportConfigureItem.FORCE_RESET_COOKIE, "T")) {
            if (!MiscUtils.isAlipayGW(getOriginRequest().getUrl())) {
                q();
                return;
            }
            for (Cookie name : cookies) {
                String name2 = name.getName();
                if (!TextUtils.isEmpty(name2) && name2.equalsIgnoreCase("ALIPAYJSESSIONID")) {
                    q();
                    return;
                }
            }
        }
    }

    private void q() {
        if (!TextUtils.isEmpty(getOperationType())) {
            LogCatUtil.info(TAG, "reset cookie.  API=" + getOperationType());
            GwCookieCacheHelper.removeAllCookie();
            CookieAccessHelper.removeAllCookie();
            long current = System.currentTimeMillis();
            while (CookieAccessHelper.getCookieManager().hasCookies() && System.currentTimeMillis() - current < 1000) {
                Thread.yield();
            }
            if (!CookieAccessHelper.getCookieManager().hasCookies()) {
                DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), "R_COOKIE", "T");
                LogCatUtil.info(TAG, "reset cookie success!");
                return;
            }
            DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), "R_COOKIE", "F");
            LogCatUtil.info(TAG, "reset cookie fail!");
        }
    }

    private Header r() {
        try {
            return getTargetHttpUriRequest().getFirstHeader("Content-Type");
        } catch (Exception e2) {
            LogCatUtil.error(TAG, "getTargetContentType Exception", e2);
            return null;
        }
    }

    private void s() {
        this.mTransportContext.deviceTrafficStateInfo = AlipayQosService.getInstance().startTrafficMonitor();
    }

    public void consumeContent() {
        if (this.mHttpResponse == null || this.mHttpResponse.getEntity() == null) {
            LogCatUtil.printInfo(TAG, "Consume content don't execution!");
            return;
        }
        try {
            this.mHttpResponse.getEntity().consumeContent();
            LogCatUtil.printInfo(TAG, "Consume content finish! threadid= " + Thread.currentThread().getId());
        } catch (Throwable e2) {
            LogCatUtil.warn((String) TAG, "consumeContent exception. " + e2.toString());
        }
    }

    private void a(Response pHttpUrlResponse) {
        if (pHttpUrlResponse != null && (pHttpUrlResponse instanceof HttpUrlResponse)) {
            HttpUrlResponse httpUrlResponse = (HttpUrlResponse) pHttpUrlResponse;
            HttpUrlHeader header = httpUrlResponse.getHeader();
            if (!MiscUtils.isDebugger(this.mContext)) {
                Object[] objArr = new Object[4];
                objArr[0] = Long.valueOf(Thread.currentThread().getId());
                objArr[1] = this.l;
                objArr[2] = header != null ? header.toString() : "is null";
                objArr[3] = "";
                LogCatUtil.info(TAG, String.format("threadid = %s; Response success. mOperationType=[%s] . response header=[%s].  response body = %s  ", objArr));
                return;
            }
            Object[] objArr2 = new Object[4];
            objArr2[0] = Long.valueOf(Thread.currentThread().getId());
            objArr2[1] = this.l;
            objArr2[2] = header != null ? header.toString() : "is null";
            objArr2[3] = getBodyContent(httpUrlResponse);
            LogCatUtil.info(TAG, String.format("threadid = %s; Response success. mOperationType=[%s] . response header=[%s].  response body = %s  ", objArr2));
        }
    }

    /* access modifiers changed from: protected */
    public String getBodyContent(HttpUrlResponse httpUrlResponse) {
        if (MiscUtils.isInLogBackList(this.l)) {
            return "根据相关法律法规和政策，本结果未予显示";
        }
        if (httpUrlResponse == null || httpUrlResponse.getResData() == null) {
            return "";
        }
        return new String(httpUrlResponse.getResData());
    }

    private static byte[] a(byte[] content) {
        if (content == null) {
            return null;
        }
        try {
            if (content.length == 0) {
                return null;
            }
            return SecurityUtil.encrypt(content);
        } catch (Throwable ex) {
            LogCatUtil.error(TAG, "taoBaoEncrypt exception", ex);
            return null;
        }
    }

    private static byte[] b(byte[] content) {
        if (content == null) {
            return null;
        }
        try {
            if (content.length == 0) {
                return null;
            }
            return SecurityUtil.decrypt(content);
        } catch (Throwable ex) {
            LogCatUtil.error(TAG, "taoBaoDecrypt exception", ex);
            return null;
        }
    }

    private boolean a(IOException e2) {
        try {
            if (!NetworkUtils.isNetworkAvailable(TransportEnvUtil.getContext()) || ZHttpRequestRetryHandler.isShutdown(e2) || !t() || !ZHttpRequestRetryHandler.isCanRetryForStandardHttpRequest(getOriginRequest()) || this.mOriginRequest.isCanceled() || ZHttpRequestRetryHandler.getRetryCount(this.mLocalContext) >= 3) {
                return false;
            }
            LogCatUtil.info(TAG, "canRetryForIoException return true.");
            return true;
        } catch (Throwable e1) {
            LogCatUtil.warn((String) TAG, "canRetryForIoException fail. " + e1.toString());
            return false;
        }
    }

    private boolean t() {
        Boolean retryForRpc = ZHttpRequestRetryHandler.isRetryForRpc(getOriginRequest(), this.mTransportContext);
        if (retryForRpc == null || retryForRpc != Boolean.FALSE) {
            return true;
        }
        return false;
    }

    public boolean isShouldReportTraffic() {
        return this.w;
    }

    public void setShouldReportTraffic(boolean shouldReportTraffic) {
        this.w = shouldReportTraffic;
    }

    public HttpResponse getHttpResponse() {
        return this.mHttpResponse;
    }

    private static void h(String url) {
        TransportInterceptorManager.getInstance().onPreTransportInterceptor(url, null);
    }

    /* access modifiers changed from: protected */
    public boolean isUseSelfEncrypt() {
        boolean result;
        try {
            if (!SelfEncryptUtils.isRpcEncryptSwitchOn()) {
                LogCatUtil.debug(TAG, "rpcSelfEncryptSwitch is off");
                return false;
            }
            boolean isGetMethod = false;
            if (TextUtils.equals(this.mOriginRequest.getRequestMethod(), "GET")) {
                isGetMethod = true;
            }
            if (MiscUtils.isDebugger(this.mContext)) {
                LogCatUtil.debug(TAG, "isRpc: " + isRpcRequest() + " ,isGetMethod: " + isGetMethod + " ,isNeedSelfEncrypt: " + SelfEncryptUtils.isNeedSelfEncrypt() + " ,isInGwWhiteList: " + SelfEncryptUtils.isInGwWhiteList(this.mOriginRequest.getUrl()) + " ,isDefaultGlobalCrypt: " + SelfEncryptUtils.isDefaultGlobalCrypt());
            }
            if (!isRpcRequest() || isGetMethod || !SelfEncryptUtils.isNeedSelfEncrypt() || !SelfEncryptUtils.isInGwWhiteList(this.mOriginRequest.getUrl())) {
                return false;
            }
            if (SelfEncryptUtils.isDefaultGlobalCrypt()) {
                if (!this.mOriginRequest.isDisableEncrypt()) {
                    result = true;
                } else {
                    result = false;
                }
                LogCatUtil.debug(TAG, "isDisableEncrypt: " + this.mOriginRequest.isDisableEncrypt() + " ,result: " + result);
                return result;
            }
            boolean result2 = this.mOriginRequest.isEnableEncrypt();
            LogCatUtil.debug(TAG, "isEnableEncrypt: " + this.mOriginRequest.isEnableEncrypt() + " ,result: " + result2);
            return result2;
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "isUseSelfEncrypt ex:" + ex.toString());
            return false;
        }
    }

    private void b(HttpResponse httpResponse) {
        try {
            Header[] header = httpResponse.getAllHeaders();
            Object[] objArr = new Object[4];
            objArr[0] = Long.valueOf(Thread.currentThread().getId());
            objArr[1] = this.l;
            objArr[2] = header != null ? Arrays.toString(header) : "is null";
            objArr[3] = "";
            LogCatUtil.info(TAG, String.format("threadid = %s; HttpResponse success. mOperationType=[%s] . response header=[%s]. ", objArr));
        } catch (Throwable e2) {
            LogCatUtil.warn((String) TAG, "[logHttpResponse] log fail. " + e2.toString());
        }
    }
}
