package com.alipay.mobile.common.transport.h5;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.monitor.DataflowModel;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.ext.ExtTransportException;
import com.alipay.mobile.common.transport.http.AndroidHttpClient;
import com.alipay.mobile.common.transport.http.HttpContextExtend;
import com.alipay.mobile.common.transport.http.HttpManager;
import com.alipay.mobile.common.transport.http.HttpUrlHeader;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.http.HttpUrlResponse;
import com.alipay.mobile.common.transport.http.HttpWorker;
import com.alipay.mobile.common.transport.monitor.DataContainer;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.common.transport.strategy.NetworkTunnelStrategy;
import com.alipay.mobile.common.transport.utils.DataItemsUtil;
import com.alipay.mobile.common.transport.utils.GtsUtils;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.MonitorErrorLogHelper;
import com.alipay.mobile.common.transport.utils.MonitorLogRecordUtil;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class H5HttpWorker extends HttpWorker {
    private Boolean a = null;
    protected boolean noRespContent = false;

    public H5HttpWorker(HttpManager httpManager, HttpUrlRequest request) {
        super(httpManager, request);
        if (request instanceof H5HttpUrlRequest) {
            this.mTransportContext.printUrlToMonitorLog = ((H5HttpUrlRequest) request).isPrintUrlToMonitorLog();
        }
    }

    /* access modifiers changed from: protected */
    public void addRequestHeaders() {
        copyHeaders();
        addCookie2Header();
        LogCatUtil.info(HttpWorker.TAG, "add header log:");
        printHeaderLog(getTargetHttpUriRequest().getAllHeaders());
    }

    /* access modifiers changed from: protected */
    public boolean isCanUseExtTransport() {
        if (!MiscUtils.isInAlipayClient(this.mContext)) {
            return false;
        }
        if (MiscUtils.isOtherProcess(this.mContext) && !TransportConfigureManager.getInstance().equalsString(TransportConfigureItem.SUB_PROC_SPDY_SWITCH, "T")) {
            LogCatUtil.warn((String) HttpWorker.TAG, (String) "Don't use spdy, because sub process spdy switch it's off.");
            return false;
        } else if (!a() || TransportConfigureManager.getInstance().equalsString(TransportConfigureItem.SMALL_SPDY_SWITCH, "T")) {
            return true;
        } else {
            LogCatUtil.warn((String) HttpWorker.TAG, (String) "Don't use spdy, because small spdy switch it's off.");
            return false;
        }
    }

    private boolean a() {
        if (this.a != null) {
            this.a.booleanValue();
        }
        String h5AppType = getOriginRequest().getTag(TransportConstants.KEY_H5_APP_TYPE);
        if (h5AppType == null || !TextUtils.equals(h5AppType, "mini_app")) {
            this.a = Boolean.FALSE;
        } else {
            LogCatUtil.info(HttpWorker.TAG, "Current request from miniApp");
            this.a = Boolean.TRUE;
        }
        return this.a.booleanValue();
    }

    /* access modifiers changed from: protected */
    public HttpResponse executeHttpClientRequest(HttpHost targetHost, HttpRequest request, HttpContext context) {
        return super.executeHttpClientRequest(targetHost, request, context);
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    public HttpResponse executeExtClientRequest() {
        if (!getH5HttpUrlRequest().isGoSpdy()) {
            return null;
        }
        if (!NetworkTunnelStrategy.getInstance().isCanUseSpdyForH5()) {
            LogCatUtil.info(HttpWorker.TAG, "isCanUseSpdyForH5==false.");
            return null;
        }
        this.mTransportContext.choseExtLinkType = 2;
        HttpUriRequest httpUriRequest = getTargetHttpUriRequest();
        httpUriRequest.addHeader("spdy-proxy-url", httpUriRequest.getURI().toString());
        String uuid = "h5_" + HttpContextExtend.getInstance().getDid() + GtsUtils.get64HexCurrentTimeMillis();
        httpUriRequest.addHeader("spdy-h5-uuid", uuid);
        this.mTransportContext.rpcUUID = uuid;
        try {
            HttpResponse response = super.executeExtClientRequest();
            if (response == null) {
                httpUriRequest.removeHeaders("spdy-proxy-url");
                httpUriRequest.removeHeaders("spdy-h5-uuid");
                return null;
            }
            response.addHeader(new BasicHeader("x-spdy-proxy", "1"));
            httpUriRequest.removeHeaders("spdy-proxy-url");
            httpUriRequest.removeHeaders("spdy-h5-uuid");
            return response;
        } catch (Throwable th) {
            httpUriRequest.removeHeaders("spdy-proxy-url");
            httpUriRequest.removeHeaders("spdy-h5-uuid");
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void processExtTransException(Exception e) {
        MonitorErrorLogHelper.warn(HttpWorker.TAG, new ExtTransportException("", MiscUtils.getRootCause(e)));
        if (!((H5HttpUrlRequest) getOriginRequest()).isGoHttp()) {
            throw e;
        }
        LogCatUtil.error(HttpWorker.TAG, "扩展传输模块连接失败,使用Https进行重试", e);
        DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.DOWN, "T");
    }

    /* access modifiers changed from: protected */
    public void processRespCookies(HttpResponse httpResponse) {
    }

    /* access modifiers changed from: protected */
    public void etagRpcv2Adapter(HttpResponse httpResponse) {
    }

    public H5HttpUrlRequest getH5HttpUrlRequest() {
        return (H5HttpUrlRequest) getOriginRequest();
    }

    public Response processResponse(HttpResponse httpResponse, HttpUrlRequest httpUrlRequest) {
        StatusLine statusLine = httpResponse.getStatusLine();
        int resCode = statusLine.getStatusCode();
        LogCatUtil.debug(HttpWorker.TAG, "Url: " + httpUrlRequest.getUrl() + " resCode:" + resCode);
        if (this.redirectHandler.isRedirectRequested(httpResponse, this.mLocalContext)) {
            try {
                LogCatUtil.info(HttpWorker.TAG, "When a redirect, release connection.");
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    entity.consumeContent();
                } else {
                    getTargetHttpUriRequest().abort();
                }
            } catch (Exception e) {
                LogCatUtil.warn((String) HttpWorker.TAG, "redirectRequested abort exception" + e.toString());
            }
        }
        return handleResponse(httpUrlRequest, httpResponse, resCode, statusLine.getReasonPhrase());
    }

    /* access modifiers changed from: protected */
    public Response handleResponse(HttpUrlRequest httpUrlRequest, HttpResponse httpResponse, int resCode, String resMsg) {
        LogCatUtil.printInfo(HttpWorker.TAG, "开始handle，handleResponse-1," + Thread.currentThread().getId());
        HttpEntity responseEntity = httpResponse.getEntity();
        NetworkInputStreamWrapper networkInputStreamWrapper = null;
        if (responseEntity != null) {
            networkInputStreamWrapper = new NetworkInputStreamWrapper(responseEntity.getContent(), this.mTransportContext, this.mHttpManager, this);
        } else {
            this.noRespContent = true;
        }
        H5HttpUrlResponse h5Response = new H5HttpUrlResponse(handleResponseHeader(httpResponse), resCode, resMsg, networkInputStreamWrapper);
        h5Response.setStatusLine(httpResponse.getStatusLine());
        h5Response.setHttpResponse(httpResponse);
        fillResponse(h5Response, httpResponse);
        return h5Response;
    }

    /* access modifiers changed from: protected */
    public HttpResponse handleResponseForRedirect(HttpRequest httpRequest, HttpParams httpParams, HttpResponse httpResponse) {
        return httpResponse;
    }

    /* access modifiers changed from: protected */
    public String monitorLog() {
        DataContainer currentDataContainer = this.mTransportContext.getCurrentDataContainer();
        if (currentDataContainer == null) {
            return "";
        }
        if (this.noRespContent) {
            return super.monitorLog();
        }
        if (TextUtils.isEmpty(currentDataContainer.getDataItem("ERROR"))) {
            return "";
        }
        return super.monitorLog();
    }

    public String doMonitorLog() {
        if (a()) {
            DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.SUB_TYPE, "mini_app");
        }
        b();
        return super.doMonitorLog();
    }

    /* access modifiers changed from: protected */
    public HttpUrlHeader handleResponseHeader(HttpResponse httpResponse) {
        Header[] allHeaders;
        HttpUrlHeader header = new HttpUrlHeader();
        for (Header h : httpResponse.getAllHeaders()) {
            header.addHead(h.getName(), h.getValue());
        }
        return header;
    }

    /* access modifiers changed from: protected */
    public void copyHeaders() {
        ArrayList headers = getHeaders();
        if (headers != null && !headers.isEmpty()) {
            Iterator<Header> it = headers.iterator();
            while (it.hasNext()) {
                getTargetHttpUriRequest().addHeader(it.next());
            }
        }
        getTargetHttpUriRequest().removeHeaders("Accept-Encoding");
        AndroidHttpClient.modifyRequestToAcceptGzipResponse(getTargetHttpUriRequest());
    }

    /* access modifiers changed from: protected */
    public void addCookie2Header() {
    }

    /* access modifiers changed from: protected */
    public String getBodyContent(HttpUrlResponse httpUrlResponse) {
        return "";
    }

    /* access modifiers changed from: protected */
    public void extNoteTraficConsume(DataflowModel dataflowModel) {
        super.extNoteTraficConsume(dataflowModel);
        HttpUrlRequest httpUrlRequest = getOriginRequest();
        if (httpUrlRequest != null && (httpUrlRequest instanceof H5HttpUrlRequest)) {
            MonitorLogRecordUtil.recordCtrlPrintURLFlagToDataflow(dataflowModel, ((H5HttpUrlRequest) httpUrlRequest).isPrintUrlToMonitorLog());
        }
    }

    private void b() {
        try {
            HttpUriRequest targetHttpUriRequest = getTargetHttpUriRequest();
            if (targetHttpUriRequest != null) {
                Header firstHeader = targetHttpUriRequest.getFirstHeader("x-ldcid-level");
                if (firstHeader != null) {
                    DataItemsUtil.putDataItem2DataContainer(this.mTransportContext.getCurrentDataContainer(), RPCDataItems.H5_LDCID_LEVEL, firstHeader.getValue());
                }
            }
        } catch (Throwable e) {
            LogCatUtil.error(HttpWorker.TAG, "putH5IdcidLevel2Log fail. ", e);
        }
    }
}
