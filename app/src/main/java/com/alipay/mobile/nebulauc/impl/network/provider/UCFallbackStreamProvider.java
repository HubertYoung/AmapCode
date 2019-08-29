package com.alipay.mobile.nebulauc.impl.network.provider;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.TransportCallbackAdapter;
import com.alipay.mobile.common.transport.h5.H5HttpUrlRequest;
import com.alipay.mobile.common.transport.h5.H5HttpUrlResponse;
import com.alipay.mobile.common.transport.h5.H5NetworkManager;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.nebula.data.H5CustomHttpResponse;
import com.alipay.mobile.nebula.provider.H5FallbackStreamProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulauc.impl.network.AlipaySpdyDowngrade;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.io.InputStream;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class UCFallbackStreamProvider implements H5FallbackStreamProvider {
    public static final String TAG = "H5UCFallbackStreamProvider";

    private class H5RequestAdapter extends TransportCallbackAdapter {
        private H5RequestAdapter() {
        }

        public void onCancelled(Request request) {
            super.onCancelled(request);
        }

        public void onPreExecute(Request request) {
            super.onPreExecute(request);
        }

        public void onPostExecute(Request request, Response response) {
        }

        public void onFailed(Request request, int code, String msg) {
            H5Log.d(UCFallbackStreamProvider.TAG, "asyncRequest onFailed code " + code + " msg " + msg + " url " + ((HttpUrlRequest) request).getUrl());
        }
    }

    public InputStream getFallbackInputStream(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        String url2 = H5UrlHelper.stripAnchor(url);
        H5NetworkManager h5NetworkManager = new H5NetworkManager(H5Utils.getContext());
        H5HttpUrlRequest h5HttpUrlRequest = new H5HttpUrlRequest(url2);
        h5HttpUrlRequest.setAsyncMonitorLog(true);
        if (!AlipaySpdyDowngrade.getSwitchControl()) {
            H5Log.d(TAG, "formatRequest !useSpdyFromJS return");
            h5HttpUrlRequest.setCapture(true);
        }
        H5HttpUrlResponse httpUrlResponse = (H5HttpUrlResponse) h5NetworkManager.enqueue(h5HttpUrlRequest).get();
        boolean gzip = false;
        Map responseHeaders = httpUrlResponse.getHeader().toMultimap();
        for (String key : responseHeaders.keySet()) {
            boolean contentEncoding = TransportConstants.KEY_X_CONTENT_ENCODING.equalsIgnoreCase(key);
            for (String value : responseHeaders.get(key)) {
                H5Log.d(TAG, "handleResponse headers " + key + Token.SEPARATOR + value);
                if (contentEncoding && "gzip".equalsIgnoreCase(value)) {
                    gzip = true;
                }
            }
        }
        H5Log.d(TAG, "handleResponse gzip " + gzip);
        InputStream inputStream = httpUrlResponse.getInputStream();
        if (gzip) {
            inputStream = new GZIPInputStream(inputStream);
        }
        H5Log.d(TAG, "getFallbackInputStream success " + url2 + Token.SEPARATOR + httpUrlResponse.getCode());
        return inputStream;
    }

    public H5CustomHttpResponse httpRequest(String url, String method, Map<String, String> headers, byte[] requestData, long timeout, boolean useSpdy) {
        InputStream realStream;
        H5NetworkManager h5NetworkManager = new H5NetworkManager(H5Utils.getContext());
        H5HttpUrlRequest h5HttpUrlRequest = new H5HttpUrlRequest(url);
        h5HttpUrlRequest.setRequestMethod(method);
        h5HttpUrlRequest.setAsyncMonitorLog(true);
        for (String key : headers.keySet()) {
            String value = headers.get(key);
            h5HttpUrlRequest.addHeader(key, value);
            H5Log.d(TAG, "request headers " + key + Token.SEPARATOR + value);
        }
        h5HttpUrlRequest.setReqData(requestData);
        h5HttpUrlRequest.setTimeout(timeout);
        if (useSpdy) {
            h5HttpUrlRequest.linkType = 1;
        } else {
            h5HttpUrlRequest.linkType = 2;
        }
        H5RequestAdapter h5RequestAdapter = new H5RequestAdapter();
        h5HttpUrlRequest.setTransportCallback(h5RequestAdapter);
        H5HttpUrlResponse httpUrlResponse = (H5HttpUrlResponse) h5NetworkManager.enqueue(h5HttpUrlRequest).get();
        H5CustomHttpResponse h5CustomHttpResponse = new H5CustomHttpResponse();
        h5CustomHttpResponse.setStatusCode(httpUrlResponse.getCode());
        h5CustomHttpResponse.setHeaders(httpUrlResponse.getHeader().getAllHeaders());
        boolean gzip = false;
        Map responseHeaders = httpUrlResponse.getHeader().toMultimap();
        for (String key2 : responseHeaders.keySet()) {
            boolean contentEncoding = TransportConstants.KEY_X_CONTENT_ENCODING.equalsIgnoreCase(key2);
            for (String value2 : responseHeaders.get(key2)) {
                H5Log.d(TAG, "handleResponse headers " + key2 + Token.SEPARATOR + value2);
                if (contentEncoding && "gzip".equalsIgnoreCase(value2)) {
                    gzip = true;
                }
            }
        }
        InputStream gzipStream = null;
        InputStream inputStream = httpUrlResponse.getInputStream();
        if (gzip) {
            gzipStream = new GZIPInputStream(inputStream);
        }
        if (gzipStream != null) {
            realStream = gzipStream;
        } else {
            realStream = inputStream;
        }
        h5CustomHttpResponse.setResStream(realStream);
        return h5CustomHttpResponse;
    }
}
