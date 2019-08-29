package com.alipay.mobile.common.transport.h5;

import com.alipay.mobile.common.transport.http.HttpForm;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpUriRequest;

public class H5HttpUrlRequest extends HttpUrlRequest {
    public static final byte HTTP_LINK = 2;
    public static final String OPERATION_TYPE = "h5_http_request";
    public static final byte SPDY_LINK = 1;
    private boolean a = false;
    private boolean b = true;
    public int linkType = 2;

    public H5HttpUrlRequest(String url) {
        super(url);
        init();
    }

    public H5HttpUrlRequest(String url, byte[] reqData, ArrayList<Header> headers, HashMap<String, String> tags) {
        super(url, reqData, headers, tags);
        init();
    }

    public H5HttpUrlRequest(String url, HttpForm httpForm, ArrayList<Header> headers, HashMap<String, String> tags) {
        super(url, httpForm, headers, tags);
        init();
    }

    public H5HttpUrlRequest(String url, HttpEntity httpEntity, ArrayList<Header> headers, HashMap<String, String> tags) {
        super(url, httpEntity, headers, tags);
        init();
    }

    public H5HttpUrlRequest(String url, InputStream inputStream, int length, ArrayList<Header> headers, HashMap<String, String> tags) {
        super(url, inputStream, (long) length, headers, tags);
        init();
    }

    public H5HttpUrlRequest(HttpUriRequest httpUriRequest) {
        super(httpUriRequest);
        init();
    }

    /* access modifiers changed from: protected */
    public void init() {
        addTags(TransportConstants.KEY_OPERATION_TYPE, OPERATION_TYPE);
        setCompress(false);
        setBgRpc(false);
        setUseHttpStdRetryModel(true);
        LogCatUtil.info("H5HttpUrlRequest", "url=" + getUrl());
    }

    public boolean isGoSpdy() {
        return (this.linkType & 1) == 1;
    }

    public boolean isGoHttp() {
        return (this.linkType & 2) == 2;
    }

    public boolean isAsyncMonitorLog() {
        return this.a;
    }

    public void setAsyncMonitorLog(boolean asyncMonitorLog) {
        this.a = asyncMonitorLog;
    }

    public boolean isPrintUrlToMonitorLog() {
        return this.b;
    }

    public void setPrintUrlToMonitorLog(boolean printUrlToMonitorLog) {
        this.b = printUrlToMonitorLog;
    }
}
