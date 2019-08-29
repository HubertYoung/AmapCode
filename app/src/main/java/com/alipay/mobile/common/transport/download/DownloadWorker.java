package com.alipay.mobile.common.transport.download;

import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.netsdkextdependapi.appinfo.AppInfoUtil;
import com.alipay.mobile.common.netsdkextdependapi.deviceinfo.DeviceInfoUtil;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.http.AndroidHttpClient;
import com.alipay.mobile.common.transport.http.HttpContextExtend;
import com.alipay.mobile.common.transport.http.HttpException;
import com.alipay.mobile.common.transport.http.HttpManager;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.http.HttpUrlResponse;
import com.alipay.mobile.common.transport.http.HttpWorker;
import com.alipay.mobile.common.transport.http.ResponseSizeModel;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.common.transport.utils.ConnectionUtil;
import com.alipay.mobile.common.transport.utils.DataItemsUtil;
import com.alipay.mobile.common.transport.utils.DownloadUtils;
import com.alipay.mobile.common.transport.utils.FileUtils;
import com.alipay.mobile.common.transport.utils.GtsUtils;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import java.io.File;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import javax.net.ssl.SSLException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;

public class DownloadWorker extends HttpWorker {
    private static Set<String> j = Collections.synchronizedSet(new HashSet());
    private String a;
    private SimpleDateFormat b;
    private File c;
    private File d;
    private DownloadRequest e;
    private int f = 0;
    private final int g = 3;
    private int h = 3;
    private long i = System.currentTimeMillis();

    public DownloadWorker(HttpManager httpManager, HttpUrlRequest request) {
        super(httpManager, request);
        this.e = (DownloadRequest) request;
        this.a = this.e.getPath();
        this.c = new File(this.a);
        this.d = DownloadUtils.createCacheFile(this.mContext, this.e);
        this.b = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        this.b.setTimeZone(TimeZone.getTimeZone("GMT"));
        this.mTransportContext.bizType = 4;
        this.i = System.currentTimeMillis();
        if (NetworkUtils.isWiFiMobileNetwork(TransportEnvUtil.getContext())) {
            this.h = 10;
        }
    }

    /* access modifiers changed from: protected */
    public void addRequestHeaders() {
        super.copyHeaders();
        addCookie2Header();
        String alipayLocaleDes = MiscUtils.getAlipayLocaleDes();
        if (!TextUtils.isEmpty(alipayLocaleDes)) {
            getTargetHttpUriRequest().addHeader("Accept-Language", alipayLocaleDes);
        }
        AndroidHttpClient.modifyRequestToAcceptGzipResponse(getTargetHttpUriRequest());
        AndroidHttpClient.modifyRequestToKeepAlive(getTargetHttpUriRequest());
        printHeaderLog(getTargetHttpUriRequest().getAllHeaders());
    }

    public ArrayList<Header> getHeaders() {
        ArrayList headers = new ArrayList(super.getHeaders());
        if (!this.e.isRedownload()) {
            a(headers);
        }
        return headers;
    }

    private void a(ArrayList<Header> headers) {
        try {
            String uuid = "download_" + HttpContextExtend.getInstance().getDid() + "_" + GtsUtils.get64HexCurrentTimeMillis();
            headers.add(new BasicHeader(H5AppHttpRequest.HEADER_UA, "pid=" + AppInfoUtil.getProductId() + "; pv=" + AppInfoUtil.getProductVersion() + "; uuid=" + uuid));
            this.mTransportContext.rpcUUID = uuid;
            if (this.c.exists()) {
                if (TextUtils.equals(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.RSRC_WITH_CACHE), "T")) {
                    long lastModify = this.c.lastModified();
                    if (lastModify > 0) {
                        String lastModified = this.b.format(Long.valueOf(lastModify));
                        headers.add(new BasicHeader("If-Modified-Since", lastModified));
                        LogCatUtil.debug("DownloadWorker", "If-Modified-Since:" + lastModified);
                    }
                }
            } else if (this.d.exists()) {
                long length = this.d.length();
                long lastModify2 = this.d.lastModified();
                if (length > 0 && lastModify2 > 0) {
                    headers.add(new BasicHeader("Range", "bytes=" + length + "-"));
                    LogCatUtil.debug("DownloadWorker", "Range:" + length);
                    String lastModified2 = this.b.format(Long.valueOf(lastModify2));
                    headers.add(new BasicHeader("If-Range", lastModified2));
                    LogCatUtil.debug("DownloadWorker", "If-Range:" + lastModified2);
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "DownloadWorker", ex);
        }
    }

    public Response processResponse(HttpResponse httpResponse, HttpUrlRequest httpUrlRequest) {
        try {
            int resCode = httpResponse.getStatusLine().getStatusCode();
            String resMsg = httpResponse.getStatusLine().getReasonPhrase();
            long contentLength = -1;
            if (httpResponse.getEntity() != null) {
                contentLength = httpResponse.getEntity().getContentLength();
            }
            LogCatUtil.debug("DownloadWorker", "Url: " + httpUrlRequest.getUrl() + " resCode:" + resCode + ",contentLength:" + contentLength);
            return handleResponse(httpUrlRequest, httpResponse, resCode, resMsg);
        } catch (Exception ex) {
            LogCatUtil.error((String) "DownloadWorker", "processResponse,exception:" + ex.toString());
            if (httpResponse != null) {
                printHeaderLog(httpResponse.getAllHeaders());
            }
            a(ex);
            if (httpUrlRequest.isCanceled()) {
                LogCatUtil.debug("DownloadWorker", "request is canceled,can't retry");
                throw ex;
            }
            LogCatUtil.debug("DownloadWorker", "DOWNLOADERR_RETRY switch is on,retryCount=" + this.f);
            this.f++;
            if (!httpUrlRequest.getHttpUriRequest().isAborted()) {
                abort();
            }
            DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), "RETRY", "T");
            DataItemsUtil.putDataItem2ContainerAnyway(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.RETRYCOUNT, String.valueOf(this.f));
            HttpUriRequest newRequest = DownloadUtils.constructHttpUriRequestWithURI(httpUrlRequest.getHttpUriRequest().getURI(), httpUrlRequest.getHttpUriRequest(), httpUrlRequest, getHttpClient());
            a(newRequest);
            httpUrlRequest.setHttpUriRequest(newRequest);
            printHeaderLog(newRequest.getAllHeaders());
            return processResponse(executeHttpClientRequest(((HttpRoute) newRequest.getParams().getParameter("http.route.forced-route")).getTargetHost(), httpUrlRequest.getHttpUriRequest(), this.mLocalContext), httpUrlRequest);
        }
    }

    private void a(HttpUriRequest newRequest) {
        newRequest.removeHeaders("Range");
        newRequest.removeHeaders("If-Range");
        if (TextUtils.equals(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.RSRC_RETRY_WITH_RANGE), "T") && this.d.exists()) {
            long length = this.d.length();
            long lastModify = this.d.lastModified();
            if (length > 0 && lastModify > 0) {
                newRequest.setHeader(new BasicHeader("Range", "bytes=" + length + "-"));
                String lastModified = this.b.format(Long.valueOf(lastModify));
                newRequest.setHeader(new BasicHeader("If-Range", lastModified));
                LogCatUtil.debug("DownloadWorker", "Range:" + length + ",If-Range:" + lastModified);
            }
        }
    }

    private void a(Exception ex) {
        if (!a()) {
            LogCatUtil.debug("DownloadWorker", "checkIfCanRetry,downerrRetry switch is off");
            throw ex;
        } else if ((ex instanceof HttpException) && ((HttpException) ex).getCode() == 429) {
            throw ex;
        } else if (!NetworkUtils.isNetworkAvailable(this.mContext)) {
            LogCatUtil.debug("DownloadWorker", "network isn't available,don't retry");
            throw ex;
        } else if (!canRetryException(ex)) {
            LogCatUtil.debug("DownloadWorker", "canRetryException return false");
            throw ex;
        } else {
            if ((ex instanceof DownloadIOException) || (ex instanceof DownloadFileIOException)) {
                int errcode = ((HttpException) ex).getCode();
                if (errcode == 14 || errcode == 15 || errcode == 17 || errcode == 18 || errcode == 19) {
                    LogCatUtil.debug("DownloadWorker", "errorcode=" + errcode + ",don't retry");
                    throw ex;
                } else if (!NetworkUtils.isWiFiMobileNetwork(this.mContext) && (errcode == 16 || errcode == 20)) {
                    LogCatUtil.debug("DownloadWorker", "errorcode=" + errcode + ",don't retry");
                    throw ex;
                }
            }
            if (TextUtils.equals(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.DOWNLOAD_EXT_TIMEOUT), "T")) {
                long stalledTime = System.currentTimeMillis() - this.i;
                if (this.f >= this.h || (this.f > 3 && stalledTime > 60000)) {
                    LogCatUtil.debug("DownloadWorker", "already retry many times,throw ex,retryCount:" + this.f + ",taskStalled:" + stalledTime);
                    throw ex;
                }
            } else if (this.f > 3) {
                LogCatUtil.debug("DownloadWorker", "already retry many times,throw ex,retryCount:" + this.f);
                throw ex;
            }
        }
    }

    private static boolean a() {
        if (!MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.DOWNLOADERR_RETRY))) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02dc A[Catch:{ all -> 0x01ef }] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x01b7 A[Catch:{ all -> 0x01ef }] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x01d0 A[Catch:{ all -> 0x01ef }] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x01f2 A[SYNTHETIC, Splitter:B:74:0x01f2] */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x02c9  */
    @android.annotation.TargetApi(9)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alipay.mobile.common.transport.Response handleResponse(com.alipay.mobile.common.transport.http.HttpUrlRequest r27, org.apache.http.HttpResponse r28, int r29, java.lang.String r30) {
        /*
            r26 = this;
            java.lang.String r4 = r27.getUrl()
            r0 = r26
            com.alipay.mobile.common.transport.download.DownloadRequest r3 = r0.e
            boolean r3 = r3.isRedownload()
            if (r3 == 0) goto L_0x0035
            java.lang.String r3 = "DownloadWorker"
            java.lang.String r9 = "Redownload is true"
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r3, r9)
            r26.b()
            r3 = 200(0xc8, float:2.8E-43)
            r0 = r29
            if (r0 == r3) goto L_0x0035
            java.io.IOException r3 = new java.io.IOException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "download failed! code must be equal to 200  code="
            r9.<init>(r10)
            r0 = r29
            java.lang.StringBuilder r9 = r9.append(r0)
            java.lang.String r9 = r9.toString()
            r3.<init>(r9)
            throw r3
        L_0x0035:
            r3 = 304(0x130, float:4.26E-43)
            r0 = r29
            if (r0 != r3) goto L_0x0064
            org.apache.http.HttpEntity r3 = r28.getEntity()
            if (r3 == 0) goto L_0x0060
            r26.consumeContent()
        L_0x0044:
            java.lang.String r3 = "DownloadWorker"
            java.lang.String r9 = "HttpStatus is 304, redirect return."
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r3, r9)
            com.alipay.mobile.common.transport.http.HttpUrlResponse r24 = new com.alipay.mobile.common.transport.http.HttpUrlResponse
            r0 = r26
            r1 = r28
            com.alipay.mobile.common.transport.http.HttpUrlHeader r3 = r0.handleResponseHeader(r1)
            r9 = 0
            r0 = r24
            r1 = r29
            r2 = r30
            r0.<init>(r3, r1, r2, r9)
        L_0x005f:
            return r24
        L_0x0060:
            r26.abort()
            goto L_0x0044
        L_0x0064:
            org.apache.http.HttpEntity r25 = r28.getEntity()
            if (r25 == 0) goto L_0x0300
            java.lang.String r3 = "DownloadWorker"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "Current cache file len: "
            r9.<init>(r10)
            r0 = r26
            java.io.File r10 = r0.d
            long r10 = r10.length()
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r9 = r9.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r3, r9)
            r3 = 416(0x1a0, float:5.83E-43)
            r0 = r29
            if (r0 != r3) goto L_0x0097
            r26.b()
            org.apache.http.client.ClientProtocolException r3 = new org.apache.http.client.ClientProtocolException
            java.lang.String r9 = "httpStatus: 416 Requested Range Not Satisfiable (HTTP/1.1 - RFC 2616) "
            r3.<init>(r9)
            throw r3
        L_0x0097:
            r3 = 429(0x1ad, float:6.01E-43)
            r0 = r29
            if (r0 != r3) goto L_0x00ae
            r26.b()
            com.alipay.mobile.common.transport.http.HttpException r3 = new com.alipay.mobile.common.transport.http.HttpException
            r9 = 429(0x1ad, float:6.01E-43)
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)
            java.lang.String r10 = "The user has sent too many requests in a given amount of time."
            r3.<init>(r9, r10)
            throw r3
        L_0x00ae:
            r3 = 200(0xc8, float:2.8E-43)
            r0 = r29
            if (r0 == r3) goto L_0x00d4
            r3 = 206(0xce, float:2.89E-43)
            r0 = r29
            if (r0 == r3) goto L_0x00d4
            r26.b()
            org.apache.http.client.ClientProtocolException r3 = new org.apache.http.client.ClientProtocolException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "download failed! code="
            r9.<init>(r10)
            r0 = r29
            java.lang.StringBuilder r9 = r9.append(r0)
            java.lang.String r9 = r9.toString()
            r3.<init>(r9)
            throw r3
        L_0x00d4:
            r3 = 200(0xc8, float:2.8E-43)
            r0 = r29
            if (r0 != r3) goto L_0x00dd
            r26.b()
        L_0x00dd:
            r20 = 0
            r3 = 200(0xc8, float:2.8E-43)
            r0 = r29
            if (r0 != r3) goto L_0x0180
            r5 = 0
        L_0x00e7:
            long r7 = r25.getContentLength()     // Catch:{ Throwable -> 0x01ac }
            r3 = r26
            r3.a(r4, r5, r7)     // Catch:{ Throwable -> 0x01ac }
            java.io.FileOutputStream r21 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x018d }
            r0 = r26
            java.io.File r9 = r0.d     // Catch:{ Exception -> 0x018d }
            r3 = 200(0xc8, float:2.8E-43)
            r0 = r29
            if (r0 != r3) goto L_0x018a
            r3 = 0
        L_0x00fd:
            r0 = r21
            r0.<init>(r9, r3)     // Catch:{ Exception -> 0x018d }
            r0 = r26
            r1 = r25
            r2 = r21
            com.alipay.mobile.common.transport.http.ResponseSizeModel r16 = r0.writeData(r1, r5, r2)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r0 = r26
            r0.a(r5)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            org.apache.http.Header r15 = r25.getContentEncoding()     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r9 = r26
            r10 = r4
            r11 = r7
            r13 = r5
            r9.a(r10, r11, r13, r15, r16)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r9 = 0
            int r3 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r3 <= 0) goto L_0x0134
            r0 = r26
            com.alipay.mobile.common.transport.context.TransportContext r3 = r0.mTransportContext     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            com.alipay.mobile.common.transport.monitor.DataContainer r3 = r3.getCurrentDataContainer()     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            java.lang.String r9 = "RES_SIZE"
            java.lang.String r10 = java.lang.String.valueOf(r7)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            com.alipay.mobile.common.transport.utils.DataItemsUtil.putDataItem2DataContainer(r3, r9, r10)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
        L_0x0134:
            com.alipay.mobile.common.transport.http.HttpUrlResponse r24 = new com.alipay.mobile.common.transport.http.HttpUrlResponse     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r0 = r26
            r1 = r28
            com.alipay.mobile.common.transport.http.HttpUrlHeader r3 = r0.handleResponseHeader(r1)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r9 = 0
            r0 = r24
            r1 = r29
            r2 = r30
            r0.<init>(r3, r1, r2, r9)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r0 = r26
            r1 = r24
            r2 = r28
            r0.fillResponse(r1, r2)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r0 = r26
            java.io.File r3 = r0.d     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r0 = r28
            a(r0, r3)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r0 = r26
            r0.a(r4, r7)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r23 = 0
            r22 = 0
        L_0x0163:
            r3 = 3
            r0 = r22
            if (r0 >= r3) goto L_0x01f6
            if (r23 != 0) goto L_0x01f6
            r0 = r26
            java.io.File r3 = r0.d     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r0 = r26
            java.io.File r9 = r0.c     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            boolean r23 = com.alipay.mobile.common.transport.utils.FileUtils.streamCopyFile(r3, r9)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            if (r23 != 0) goto L_0x017d
            r0 = r26
            r0.a(r4, r7)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
        L_0x017d:
            int r22 = r22 + 1
            goto L_0x0163
        L_0x0180:
            r0 = r26
            java.io.File r3 = r0.d     // Catch:{ Throwable -> 0x01ac }
            long r5 = r3.length()     // Catch:{ Throwable -> 0x01ac }
            goto L_0x00e7
        L_0x018a:
            r3 = 1
            goto L_0x00fd
        L_0x018d:
            r18 = move-exception
            java.lang.String r3 = "DownloadWorker"
            r0 = r18
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r3, r0)     // Catch:{ Throwable -> 0x01ac }
            com.alipay.mobile.common.transport.download.DownloadIOException r17 = new com.alipay.mobile.common.transport.download.DownloadIOException     // Catch:{ Throwable -> 0x01ac }
            r3 = 20
            r0 = r26
            java.io.File r9 = r0.d     // Catch:{ Throwable -> 0x01ac }
            java.lang.String r9 = r9.getAbsolutePath()     // Catch:{ Throwable -> 0x01ac }
            java.lang.String r10 = "cache file read fail"
            r0 = r17
            r0.<init>(r3, r4, r9, r10)     // Catch:{ Throwable -> 0x01ac }
            r17.initCause(r18)     // Catch:{ Throwable -> 0x01ac }
            throw r17     // Catch:{ Throwable -> 0x01ac }
        L_0x01ac:
            r19 = move-exception
        L_0x01ad:
            r0 = r26
            com.alipay.mobile.common.transport.download.DownloadRequest r3 = r0.e     // Catch:{ all -> 0x01ef }
            boolean r3 = r3.isRedownload()     // Catch:{ all -> 0x01ef }
            if (r3 == 0) goto L_0x02c9
            r26.b()     // Catch:{ all -> 0x01ef }
        L_0x01ba:
            java.lang.String r3 = "DownloadWorker"
            r0 = r19
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r3, r0)     // Catch:{ all -> 0x01ef }
            r0 = r26
            java.io.File r3 = r0.d     // Catch:{ all -> 0x01ef }
            r0 = r28
            a(r0, r3)     // Catch:{ all -> 0x01ef }
            r0 = r19
            boolean r3 = r0 instanceof com.alipay.mobile.common.transport.download.DownloadIOException     // Catch:{ all -> 0x01ef }
            if (r3 == 0) goto L_0x02dc
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x01ef }
            java.lang.String r9 = "DownloadWorker"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x01ef }
            java.lang.String r11 = "SDKDownloadIOException "
            r10.<init>(r11)     // Catch:{ all -> 0x01ef }
            java.lang.String r11 = r19.getMessage()     // Catch:{ all -> 0x01ef }
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x01ef }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x01ef }
            r3.error(r9, r10)     // Catch:{ all -> 0x01ef }
            com.alipay.mobile.common.transport.download.DownloadIOException r19 = (com.alipay.mobile.common.transport.download.DownloadIOException) r19     // Catch:{ all -> 0x01ef }
            throw r19     // Catch:{ all -> 0x01ef }
        L_0x01ef:
            r3 = move-exception
        L_0x01f0:
            if (r20 == 0) goto L_0x01f5
            r20.close()     // Catch:{ Exception -> 0x030d }
        L_0x01f5:
            throw r3
        L_0x01f6:
            if (r23 == 0) goto L_0x0202
            r0 = r26
            java.io.File r3 = r0.c     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            boolean r3 = r3.exists()     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            if (r3 != 0) goto L_0x02b1
        L_0x0202:
            r0 = r26
            java.io.File r3 = r0.d     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            boolean r3 = r3.exists()     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            if (r3 == 0) goto L_0x0261
            java.util.Set<java.lang.String> r3 = j     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r3.add(r4)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            com.alipay.mobile.common.transport.download.DownloadFileIOException r3 = new com.alipay.mobile.common.transport.download.DownloadFileIOException     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r9 = 16
            r0 = r26
            java.io.File r10 = r0.c     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            java.lang.String r10 = r10.getAbsolutePath()     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            java.lang.String r12 = "add blackset,cacheFile exist ["
            r11.<init>(r12)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r0 = r26
            java.io.File r12 = r0.d     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            boolean r12 = r12.exists()     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            java.lang.String r12 = "]，target file exist ["
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r0 = r26
            java.io.File r12 = r0.c     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            boolean r12 = r12.exists()     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            java.lang.String r12 = "]，copy success ["
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r0 = r23
            java.lang.StringBuilder r11 = r11.append(r0)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            java.lang.String r12 = "]"
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            java.lang.String r11 = r11.toString()     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r3.<init>(r9, r4, r10, r11)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            throw r3     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
        L_0x025c:
            r19 = move-exception
            r20 = r21
            goto L_0x01ad
        L_0x0261:
            com.alipay.mobile.common.transport.download.DownloadFileIOException r3 = new com.alipay.mobile.common.transport.download.DownloadFileIOException     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r9 = 20
            r0 = r26
            java.io.File r10 = r0.c     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            java.lang.String r10 = r10.getAbsolutePath()     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            java.lang.String r12 = "cacheFile exist ["
            r11.<init>(r12)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r0 = r26
            java.io.File r12 = r0.d     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            boolean r12 = r12.exists()     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            java.lang.String r12 = "]，target file exist ["
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r0 = r26
            java.io.File r12 = r0.c     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            boolean r12 = r12.exists()     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            java.lang.String r12 = "]，copy success ["
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r0 = r23
            java.lang.StringBuilder r11 = r11.append(r0)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            java.lang.String r12 = "],cache was cleaned"
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            java.lang.String r11 = r11.toString()     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r3.<init>(r9, r4, r10, r11)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            throw r3     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
        L_0x02ac:
            r3 = move-exception
            r20 = r21
            goto L_0x01f0
        L_0x02b1:
            r0 = r26
            java.io.File r3 = r0.d     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r3.delete()     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r0 = r26
            java.io.File r3 = r0.c     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r0 = r28
            a(r0, r3)     // Catch:{ Throwable -> 0x025c, all -> 0x02ac }
            r21.close()     // Catch:{ Exception -> 0x02c6 }
            goto L_0x005f
        L_0x02c6:
            r3 = move-exception
            goto L_0x005f
        L_0x02c9:
            r0 = r26
            java.io.File r3 = r0.c     // Catch:{ all -> 0x01ef }
            boolean r3 = r3.exists()     // Catch:{ all -> 0x01ef }
            if (r3 == 0) goto L_0x01ba
            r0 = r26
            java.io.File r3 = r0.c     // Catch:{ all -> 0x01ef }
            r3.delete()     // Catch:{ all -> 0x01ef }
            goto L_0x01ba
        L_0x02dc:
            r0 = r19
            boolean r3 = r0 instanceof java.io.IOException     // Catch:{ all -> 0x01ef }
            if (r3 == 0) goto L_0x02e5
            java.io.IOException r19 = (java.io.IOException) r19     // Catch:{ all -> 0x01ef }
            throw r19     // Catch:{ all -> 0x01ef }
        L_0x02e5:
            java.io.IOException r3 = new java.io.IOException     // Catch:{ all -> 0x01ef }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x01ef }
            java.lang.String r10 = "download failed! "
            r9.<init>(r10)     // Catch:{ all -> 0x01ef }
            java.lang.String r10 = r19.toString()     // Catch:{ all -> 0x01ef }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x01ef }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x01ef }
            r0 = r19
            r3.<init>(r9, r0)     // Catch:{ all -> 0x01ef }
            throw r3     // Catch:{ all -> 0x01ef }
        L_0x0300:
            if (r25 == 0) goto L_0x0309
            r26.consumeContent()
        L_0x0305:
            r24 = 0
            goto L_0x005f
        L_0x0309:
            r26.abort()
            goto L_0x0305
        L_0x030d:
            r9 = move-exception
            goto L_0x01f5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.download.DownloadWorker.handleResponse(com.alipay.mobile.common.transport.http.HttpUrlRequest, org.apache.http.HttpResponse, int, java.lang.String):com.alipay.mobile.common.transport.Response");
    }

    private void a(long hasReaded) {
        long time = System.currentTimeMillis();
        this.mHttpManager.addSocketTime(System.currentTimeMillis() - time);
        long cacheLength = this.d.length();
        LogCatUtil.info("DownloadWorker", "Writed cache file length = " + cacheLength);
        b(this.mOriginRequest.getUrl(), cacheLength - hasReaded, System.currentTimeMillis() - time);
        this.mHttpManager.addDataSize(this.d.length() - hasReaded);
    }

    private void a(String requrl, long contentLength, long historyLength, Header header, ResponseSizeModel responseSizeModel) {
        boolean isUseGzip = false;
        if (header != null && !TextUtils.isEmpty(header.getValue()) && header.getValue().contains("gzip")) {
            isUseGzip = true;
            DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.DWN_GZIP, "T");
        }
        String errMsg = "contentLength[" + contentLength + "] isUseGzip[" + isUseGzip + "] compressedSize[" + responseSizeModel.compressedSize + "] rawSize[" + responseSizeModel.rawSize + "] cacheFile.length[" + this.d.length() + "]";
        LogCatUtil.debug("DownloadWorker", errMsg);
        if (contentLength > 0) {
            if (this.d.length() <= 0) {
                throw new DownloadIOException(20, requrl, this.d.getAbsolutePath(), errMsg + ",cache was cleaned");
            }
            String gzipCheck = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.DOWNLOAD_GZIP_CHECK);
            if (!TextUtils.equals(gzipCheck, "T") || !isUseGzip) {
                if (TextUtils.equals(gzipCheck, "T") || !isUseGzip) {
                    long currentReadedLen = this.d.length() - historyLength;
                    if (currentReadedLen != contentLength) {
                        String errMsg2 = errMsg + ",currentReadedLen:" + currentReadedLen + "，not equal contentLength:" + contentLength;
                        if (currentReadedLen < contentLength) {
                            throw new DownloadIOException(22, requrl, this.d.getAbsolutePath(), errMsg2);
                        }
                        return;
                    }
                    return;
                }
                LogCatUtil.debug("DownloadWorker", "gzip check is off");
            } else if (responseSizeModel.compressedSize != contentLength) {
                throw new DownloadIOException(22, requrl, this.d.getAbsolutePath(), errMsg + ",compressedSize not equal contentLength");
            }
        }
    }

    private void a(String requrl, long hasReaded, long contentLength) {
        if (!FileUtils.checkDataAvailableSpace(this.d, contentLength - hasReaded)) {
            throw new DownloadFileIOException(14, requrl, this.d.getAbsolutePath(), "cache space less than " + (contentLength - hasReaded));
        } else if (!FileUtils.checkFileDirWRPermissions(this.d)) {
            throw new DownloadFileIOException(17, requrl, this.d.getAbsolutePath(), "cache dir create fail");
        } else {
            a(requrl, contentLength);
            if (TextUtils.equals(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.DOWN_CHECK_SD_PERMISSION), "T") && !this.c.getParentFile().canWrite()) {
                throw new DownloadIOException(21, requrl, this.c.getAbsolutePath(), "sdcard write fail");
            } else if (j.contains(requrl)) {
                throw new DownloadIOException(19, requrl, this.c.getAbsolutePath(), "downloadFileBlackSet contains this url");
            }
        }
    }

    private void a(String requrl, long contentLength) {
        if (!FileUtils.checkDataOrSdcardAvailableSpace(this.c, contentLength)) {
            throw new DownloadFileIOException(15, requrl, this.c.getAbsolutePath(), "target space less than " + contentLength);
        } else if (!FileUtils.checkFileDirWRPermissions(this.c)) {
            throw new DownloadFileIOException(18, requrl, this.c.getAbsolutePath(), "targe dir create fail");
        }
    }

    private void b() {
        LogCatUtil.debug("DownloadWorker", "deleteAllFile");
        try {
            if (this.c.exists()) {
                LogCatUtil.debug("DownloadWorker", "deletePathFile=" + this.c.delete());
            }
            if (this.d.exists()) {
                LogCatUtil.debug("DownloadWorker", "deleteCacheFile=" + this.d.delete());
            }
        } catch (Exception e2) {
            LogCatUtil.warn((String) "DownloadWorker", (Throwable) e2);
        }
    }

    private static void a(HttpResponse httpResponse, File file) {
        try {
            Header header = httpResponse.getFirstHeader("Last-Modified");
            if (file.exists() && header != null && !file.setLastModified(AndroidHttpClient.parseDate(header.getValue()))) {
                LogCatUtil.error((String) "DownloadWorker", (String) "setLastModified error");
            }
        } catch (Exception e2) {
            LogCatUtil.warn((String) "DownloadWorker", "proc get Last-Modifie exception : " + e2.toString());
        }
    }

    /* access modifiers changed from: protected */
    public boolean willHandleOtherCode(int resCode, String resMsg) {
        if (resCode == 206 || resCode == 416 || resCode == 304) {
            return true;
        }
        return false;
    }

    private void b(String url, long size, long socketTime) {
        int netType = ConnectionUtil.getConnType(this.mContext);
        LoggerFactory.getTraceLogger().info("monitor", "url: " + url + " socketSpend: " + socketTime + " size: " + size + " netDetail: " + netType + MergeUtil.SEPARATOR_KV + ((TelephonyManager) this.mContext.getSystemService("phone")).getNetworkType());
    }

    /* access modifiers changed from: protected */
    public HttpResponse executeHttpClientRequest(HttpHost targetHost, HttpRequest request, HttpContext context) {
        if (!DownloadUtils.isNeedToDowngradeToHttps((HttpUriRequest) request)) {
            return super.executeHttpClientRequest(targetHost, request, context);
        }
        return a((HttpUriRequest) (HttpUriRequest) request, getHttpClient().execute(targetHost, request, context));
    }

    private HttpResponse a(HttpUriRequest request, HttpResponse httpResponse) {
        if (DownloadUtils.isNeedToDowngradeToHttps(request, httpResponse)) {
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                entity.consumeContent();
            }
            LogCatUtil.debug("DownloadWorker", "processDowngrade,net hijack,try https");
            DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.IMGDOWNGRADE, "T");
            HttpUrlRequest urlRequest = getOriginRequest();
            AndroidHttpClient httpClient = getHttpClient();
            if (!request.isAborted()) {
                abort();
            }
            return DownloadUtils.executeDowngradeRequest(request, urlRequest, httpClient, this.mLocalContext);
        }
        LogCatUtil.debug("DownloadWorker", "handleResponseForDowngrade,needn't downgrade to https");
        return httpResponse;
    }

    /* access modifiers changed from: protected */
    public String getBodyContent(HttpUrlResponse httpUrlResponse) {
        return "";
    }

    public boolean canRetryException(Throwable exception) {
        try {
            Throwable rootCause = MiscUtils.getRootCause(exception);
            if (rootCause != null && isRetryException(rootCause)) {
                return true;
            }
            if (rootCause == null && isRetryException(exception)) {
                return true;
            }
            return false;
        } catch (Throwable ex) {
            LogCatUtil.error((String) "DownloadWorker", ex);
        }
    }

    public boolean isRetryException(Throwable exception) {
        boolean result = (exception instanceof SocketException) || (exception instanceof SSLException) || (exception instanceof SocketTimeoutException) || (exception instanceof ConnectionPoolTimeoutException) || (exception instanceof UnknownHostException) || (exception instanceof NoHttpResponseException) || (exception instanceof ClientProtocolException) || (exception instanceof DownloadIOException);
        LogCatUtil.debug("DownloadWorker", "isRetryException,exception=" + exception.toString() + ",canRetry=" + result);
        return result;
    }
}
