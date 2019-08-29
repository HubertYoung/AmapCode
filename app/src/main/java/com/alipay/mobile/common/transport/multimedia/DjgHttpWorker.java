package com.alipay.mobile.common.transport.multimedia;

import android.text.TextUtils;
import com.alipay.mobile.common.netsdkextdependapi.appinfo.AppInfoUtil;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.h5.H5HttpWorker;
import com.alipay.mobile.common.transport.h5.NetworkInputStreamWrapper;
import com.alipay.mobile.common.transport.http.AndroidHttpClient;
import com.alipay.mobile.common.transport.http.HttpContextExtend;
import com.alipay.mobile.common.transport.http.HttpManager;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.http.HttpUrlResponse;
import com.alipay.mobile.common.transport.http.HttpWorker;
import com.alipay.mobile.common.transport.http.ZInputStreamEntityWrapper;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.common.transport.utils.DataItemsUtil;
import com.alipay.mobile.common.transport.utils.DownloadUtils;
import com.alipay.mobile.common.transport.utils.GtsUtils;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;

public class DjgHttpWorker extends H5HttpWorker {
    public DjgHttpWorker(HttpManager httpManager, HttpUrlRequest request) {
        super(httpManager, request);
    }

    /* access modifiers changed from: protected */
    public HttpResponse executeExtClientRequest() {
        return null;
    }

    /* access modifiers changed from: protected */
    public Response handleResponse(HttpUrlRequest httpUrlRequest, HttpResponse httpResponse, int resCode, String resMsg) {
        LogCatUtil.printInfo(HttpWorker.TAG, "开始handle，handleResponse-1," + Thread.currentThread().getId());
        HttpEntity responseEntity = httpResponse.getEntity();
        InputStream inputStream = null;
        if (responseEntity != null) {
            NetworkInputStreamWrapper networkInputStreamWrapper = new NetworkInputStreamWrapper(responseEntity.getContent(), this.mTransportContext, this.mHttpManager, this);
            if (responseEntity.getContentEncoding() != null) {
                httpResponse.removeHeaders(responseEntity.getContentEncoding().getName());
            }
            inputStream = AndroidHttpClient.getUngzippedContent(networkInputStreamWrapper, responseEntity.getContentEncoding());
            httpResponse.setEntity(new ZInputStreamEntityWrapper(inputStream, responseEntity));
        } else {
            this.noRespContent = true;
        }
        DjgHttpUrlResponse djgHttpUrlResponse = new DjgHttpUrlResponse(handleResponseHeader(httpResponse), resCode, resMsg, inputStream);
        djgHttpUrlResponse.setHttpResponse(httpResponse);
        djgHttpUrlResponse.setStatusLine(httpResponse.getStatusLine());
        fillResponse(djgHttpUrlResponse, httpResponse);
        return djgHttpUrlResponse;
    }

    /* access modifiers changed from: protected */
    public HttpResponse executeHttpClientRequest(HttpHost targetHost, HttpRequest request, HttpContext context) {
        setTimeout();
        if (!DownloadUtils.isNeedToDowngradeToHttps((HttpUriRequest) request)) {
            return super.executeHttpClientRequest(targetHost, request, context);
        }
        return a((HttpUriRequest) (HttpUriRequest) request, getHttpClient().execute(targetHost, request, context));
    }

    private HttpResponse a(HttpUriRequest request, HttpResponse httpResponse) {
        if (!DownloadUtils.isNeedToDowngradeToHttps(request, httpResponse)) {
            return httpResponse;
        }
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            entity.consumeContent();
        }
        LogCatUtil.debug(HttpWorker.TAG, "processDegrade,net hijack, try https");
        DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.IMGDOWNGRADE, "T");
        HttpUrlRequest urlRequest = getOriginRequest();
        AndroidHttpClient httpClient = getHttpClient();
        if (!request.isAborted()) {
            abort();
        }
        return DownloadUtils.executeDowngradeRequest(request, urlRequest, httpClient, this.mLocalContext);
    }

    /* access modifiers changed from: protected */
    public void copyHeaders() {
    }

    /* access modifiers changed from: protected */
    public void addCookie2Header() {
    }

    /* access modifiers changed from: protected */
    public void addRequestHeaders() {
        AndroidHttpClient.modifyRequestToAcceptGzipResponse(getTargetHttpUriRequest());
        AndroidHttpClient.modifyRequestToKeepAlive(getTargetHttpUriRequest());
        a();
        LogCatUtil.info(HttpWorker.TAG, "add header log:");
        printHeaderLog(getTargetHttpUriRequest().getAllHeaders());
    }

    /* access modifiers changed from: protected */
    public void resetRequestHeaders() {
        try {
            getTargetHttpUriRequest().removeHeaders("Accept-Encoding");
            getTargetHttpUriRequest().removeHeaders(H5AppHttpRequest.HEADER_CONNECTION);
            getTargetHttpUriRequest().removeHeaders(H5AppHttpRequest.HEADER_UA);
        } catch (Throwable ex) {
            LogCatUtil.error((String) HttpWorker.TAG, "resetRequestHeaders ex:" + ex.toString());
        }
    }

    private void a() {
        try {
            String uuid = "djg_" + HttpContextExtend.getInstance().getDid() + "_" + GtsUtils.get64HexCurrentTimeMillis();
            getTargetHttpUriRequest().addHeader(new BasicHeader(H5AppHttpRequest.HEADER_UA, "pid=" + AppInfoUtil.getProductId() + "; pv=" + AppInfoUtil.getProductVersion() + "; uuid=" + uuid));
            this.mTransportContext.rpcUUID = uuid;
        } catch (Throwable ex) {
            LogCatUtil.error((String) HttpWorker.TAG, ex);
        }
    }

    /* access modifiers changed from: protected */
    public String getBodyContent(HttpUrlResponse httpUrlResponse) {
        return "";
    }

    /* access modifiers changed from: protected */
    public void putSubCommonMonitor() {
        try {
            long reqContentLength = getTargetHttpUriRequest().getParams().getLongParameter("Content-Length", -1);
            if (reqContentLength > 0) {
                DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), "REQ_SIZE", String.valueOf(reqContentLength));
            }
            HttpUrlRequest httpUrlRequest = getOriginRequest();
            if (httpUrlRequest instanceof DjgHttpUrlRequest) {
                DjgHttpUrlRequest djgHttpUrlRequest = (DjgHttpUrlRequest) httpUrlRequest;
                if (djgHttpUrlRequest.getInnerBizType() != null) {
                    DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.DJG_INNER_BIZ, String.valueOf(djgHttpUrlRequest.getInnerBizType()));
                }
                String upMediaType = djgHttpUrlRequest.getUpMediaType();
                if (!TextUtils.isEmpty(upMediaType)) {
                    DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.UP_MEDIA_TYPE, upMediaType);
                }
            }
        } catch (Throwable e) {
            LogCatUtil.error(HttpWorker.TAG, "DjgHttpWorker#putSubCommonMonitor.ex:" + e.toString(), e);
        }
    }

    /* access modifiers changed from: protected */
    public Response processException(String exceptionName, int code, Throwable e, boolean canForceRetry) {
        try {
            if (!a(e, canForceRetry) || getOriginRequest().isCanceled()) {
                LogCatUtil.debug(HttpWorker.TAG, "DjgHttpWorker#processException,  can't downgrade");
                return super.processException(exceptionName, code, e, canForceRetry);
            }
            LogCatUtil.error(HttpWorker.TAG, "processException,code=[" + code + "] canRetry=[" + canForceRetry + "] e=[" + e.toString() + "]", e);
            DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.IMGDOWNGRADE, "T");
            LogCatUtil.debug(HttpWorker.TAG, "DjgHttpWorker#processException, downgrade by https");
            HttpUrlRequest urlRequest = getOriginRequest();
            AndroidHttpClient httpClient = getHttpClient();
            if (!urlRequest.getHttpUriRequest().isAborted()) {
                abort();
            }
            this.mHttpResponse = DownloadUtils.executeDowngradeRequest(urlRequest.getHttpUriRequest(), urlRequest, httpClient, this.mLocalContext);
            return super.processResponse(this.mHttpResponse, urlRequest);
        } catch (Throwable ex) {
            return super.processException("downgrade exception", 0, ex, false);
        }
    }

    private boolean a(Throwable e, boolean canRetry) {
        try {
            if (!NetworkUtils.isNetworkAvailable(TransportEnvUtil.getContext())) {
                LogCatUtil.debug(HttpWorker.TAG, "Network unavailable, not downgrade");
                return false;
            } else if (!DownloadUtils.isNeedToDowngradeToHttps(getOriginRequest().getHttpUriRequest())) {
                LogCatUtil.debug(HttpWorker.TAG, "Not need to downgrade to https");
                return false;
            } else if (!(e instanceof IOException) && !canRetry) {
                return false;
            } else {
                LogCatUtil.debug(HttpWorker.TAG, "ifCanDowngrade, return true");
                return true;
            }
        } catch (Throwable throwable) {
            LogCatUtil.error((String) HttpWorker.TAG, throwable);
            return false;
        }
    }
}
