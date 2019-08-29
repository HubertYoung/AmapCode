package com.alipay.mobile.common.transport.utils;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.netsdkextdependapi.deviceinfo.DeviceInfoUtil;
import com.alipay.mobile.common.transport.TransportCallback;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.download.DownloadRequest;
import com.alipay.mobile.common.transport.http.AndroidHttpClient;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.http.HttpWorker;
import com.alipay.mobile.common.transport.http.inner.HttpClientPlannerHelper;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.protocol.HttpContext;

public class DownloadUtils {
    public static final String GET_METHOD = "GET";
    public static final int HTTPS_PORT = 443;
    public static final String HTTPS_SCHEME = "https";
    public static final String POST_METHOD = "POST";
    private static String a = "tfs.alipayobjects.com";
    private static String b = "pic.alipayobjects.com";
    private static String c = "api-mayi.django.t.taobao.com";
    public static String contentTypeKey = PoiLayoutTemplate.HTML;
    private static String d = "oalipay-dl-django.alicdn.com";

    public static boolean isNeedToDowngradeToHttps(HttpUriRequest req, HttpResponse rsp) {
        try {
            if (isNeedToDowngradeToHttps(req) && a(rsp)) {
                return true;
            }
            return false;
        } catch (Throwable ex) {
            LogCatUtil.error((String) "DownloadUtils", ex);
            return false;
        }
    }

    public static boolean isNeedToDowngradeToHttps(HttpUriRequest req) {
        try {
            if (!MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.DOWNLOAD_DOWNGRADE))) {
                LogCatUtil.debug("DownloadUtils", "isNeedToDowngradeToHttps. degradeSwitch is off");
                return false;
            } else if (c(req)) {
                return false;
            } else {
                if (!isContainDowngradeHost(req) && !a(req.getURI().getHost())) {
                    return false;
                }
                if (a(req) || b(req)) {
                    LogCatUtil.info("DownloadUtils", "isNeedToDowngradeToHttps. may need downgrade.");
                    return true;
                }
                LogCatUtil.info("DownloadUtils", "isNeedToDowngradeToHttps.return false");
                return false;
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "isNeedToDowngradeToHttps", ex);
            return false;
        }
    }

    private static boolean a(HttpUriRequest req) {
        if (req == null) {
            return false;
        }
        return "GET".equalsIgnoreCase(req.getMethod());
    }

    private static boolean b(HttpUriRequest req) {
        if (req == null) {
            return false;
        }
        try {
            if (!"POST".equalsIgnoreCase(req.getMethod())) {
                return false;
            }
            HttpEntity entity = ((HttpEntityEnclosingRequestBase) req).getEntity();
            if (entity == null || (entity != null && entity.isRepeatable())) {
                return true;
            }
            return false;
        } catch (Throwable ex) {
            LogCatUtil.error((String) "DownloadUtils", "isRepeatablePost ex:" + ex.toString());
            return false;
        }
    }

    private static boolean c(HttpUriRequest req) {
        boolean res = "https".equalsIgnoreCase(req.getURI().getScheme());
        if (!res) {
            LogCatUtil.info("DownloadUtils", "requestIsHttps. no https, may need downgrade.");
        }
        return res;
    }

    private static boolean a(String host) {
        String downgradeHosts = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.DOWNGRADE_HOSTS);
        if (!TextUtils.isEmpty(downgradeHosts)) {
            for (String equals : downgradeHosts.split(",")) {
                if (TextUtils.equals(equals, host)) {
                    LogCatUtil.info("DownloadUtils", "isInDowngradeHosts. " + host + " in downgradeHosts , may need downgrade.");
                    return true;
                }
            }
        }
        LogCatUtil.info("DownloadUtils", "isInDowngradeHosts. " + host + " not in downgradeHosts,can't downgrade.");
        return false;
    }

    protected static boolean isContainDowngradeHost(HttpUriRequest req) {
        try {
            boolean result = !TextUtils.isEmpty((String) req.getParams().getParameter(TransportConstants.KEY_DOWNGRADE_HTTPS_HOST));
            LogCatUtil.debug("DownloadUtils", "isContainDowngradeHost return:" + result);
            return result;
        } catch (Throwable e) {
            LogCatUtil.warn("DownloadUtils", " isContainDowngradeHost error ", e);
            return false;
        }
    }

    private static boolean a(HttpResponse rsp) {
        if (rsp == null) {
            return false;
        }
        int code = rsp.getStatusLine().getStatusCode();
        if (code == 200 || code == 206 || code == 304 || code == 429) {
            return false;
        }
        LogCatUtil.info("DownloadUtils", "isNeedToRetryByResponseCode. response code=" + code + ",may need downgrade");
        return true;
    }

    public static boolean validateResponseCode(int code) {
        if (code == 200 || code == 206 || code == 304) {
            return true;
        }
        return false;
    }

    public static URI changeUriByParams(URI dest, String scheme, String host, int port) {
        URI uri = dest;
        try {
            if (TextUtils.isEmpty(scheme)) {
                return uri;
            }
            int localPort = -1;
            if ((TextUtils.equals(scheme, "https") && port != 443) || (TextUtils.equals(scheme, "http") && port != 80)) {
                localPort = port;
            }
            return new URI(scheme, dest.getUserInfo(), host, localPort, dest.getPath(), dest.getQuery(), dest.getFragment());
        } catch (Exception e) {
            return dest;
        }
    }

    public static HttpUriRequest constructNewHttpUriRequest(HttpUriRequest oriUriRequest, HttpUrlRequest httpUrlRequest, AndroidHttpClient httpClient) {
        return constructHttpUriRequestWithURI(d(oriUriRequest), oriUriRequest, httpUrlRequest, httpClient);
    }

    public static HttpUriRequest constructHttpUriRequestWithURI(URI uri, HttpUriRequest oriUriRequest, HttpUrlRequest httpUrlRequest, AndroidHttpClient httpClient) {
        HttpUriRequest newUriRequest = a(oriUriRequest, uri);
        newUriRequest.setParams(oriUriRequest.getParams());
        newUriRequest.setHeaders(oriUriRequest.getAllHeaders());
        if (httpClient != null) {
            try {
                newUriRequest.getParams().setParameter("http.route.forced-route", HttpClientPlannerHelper.determineRoute(httpClient, new HttpHost(uri.toURL().getHost(), uri.getPort(), uri.getScheme()), newUriRequest, null));
            } catch (Throwable ex) {
                LogCatUtil.error((String) "DownloadUtils", "setParameter ex:" + ex.toString());
            }
        } else {
            newUriRequest.getParams().removeParameter("http.route.forced-route");
        }
        if (httpUrlRequest != null) {
            httpUrlRequest.setHttpUriRequest(newUriRequest);
            httpUrlRequest.setUrl(uri.toURL().toString());
        }
        return newUriRequest;
    }

    private static URI d(HttpUriRequest oriUriRequest) {
        URI uri = oriUriRequest.getURI();
        String downHost = e(oriUriRequest);
        if (!TextUtils.isEmpty(downHost)) {
            URI uri2 = changeUriByParams(uri, "https", downHost, 443);
            LogCatUtil.debug("DownloadUtils", "oriURI:" + oriUriRequest.getURI().toString() + ",newURI:" + uri2.toString());
            return uri2;
        }
        throw new IOException("downgrade exception,no downHost find with " + uri.toURL().getHost());
    }

    private static HttpUriRequest a(HttpUriRequest oriUriRequest, URI uri) {
        String requestMethod = oriUriRequest.getMethod();
        if (TextUtils.equals(requestMethod, "GET")) {
            return new HttpGet(uri);
        }
        if (TextUtils.equals(requestMethod, "POST")) {
            HttpPost httpPost = new HttpPost(uri);
            HttpEntity entity = ((HttpEntityEnclosingRequestBase) oriUriRequest).getEntity();
            if (entity == null || !entity.isRepeatable()) {
                return httpPost;
            }
            ((HttpEntityEnclosingRequestBase) httpPost).setEntity(entity);
            return httpPost;
        }
        throw new IOException("requestMethod:" + requestMethod + " not support");
    }

    public static HttpResponse executeDowngradeRequest(HttpUriRequest oriUriRequest, HttpUrlRequest httpUrlRequest, AndroidHttpClient httpClient, HttpContext httpContext) {
        HttpUriRequest newHttpUriRequest = constructNewHttpUriRequest(oriUriRequest, httpUrlRequest, httpClient);
        LogCatUtil.debug(HttpWorker.TAG, "By Http/Https to request, method= " + newHttpUriRequest.getMethod() + " ,url=" + newHttpUriRequest.getURI().toString());
        a(newHttpUriRequest.getAllHeaders());
        return httpClient.execute(((HttpRoute) newHttpUriRequest.getParams().getParameter("http.route.forced-route")).getTargetHost(), (HttpRequest) newHttpUriRequest, httpContext);
    }

    public static final File createCacheFile(Context context, DownloadRequest httpUrlRequest) {
        return new File(context.getCacheDir(), Integer.toHexString(httpUrlRequest.getUrl().hashCode()) + Integer.toHexString(httpUrlRequest.getPath().hashCode()));
    }

    public static final File createCacheFile(Context context, String url, String path, ArrayList<Header> headers, TransportCallback callback) {
        DownloadRequest request = new DownloadRequest(url, path, null, headers);
        request.setTransportCallback(callback);
        return createCacheFile(context, request);
    }

    private static void a(Header[] allHeaders) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Header header : allHeaders) {
            stringBuilder.append(header.getName() + ":" + header.getValue()).append(", ");
        }
        LogCatUtil.info(HttpWorker.TAG, "Added request headers : " + stringBuilder.toString());
    }

    private static String e(HttpUriRequest uriRequest) {
        String host = uriRequest.getURI().toURL().getHost();
        TransportConfigureManager mng = TransportConfigureManager.getInstance();
        if (TextUtils.equals(host, a)) {
            return mng.getStringValue((ConfigureItem) TransportConfigureItem.DOWN_TFS_HOST);
        }
        if (TextUtils.equals(host, b)) {
            return mng.getStringValue((ConfigureItem) TransportConfigureItem.DOWN_PIC_HOST);
        }
        if (TextUtils.equals(host, c)) {
            return mng.getStringValue((ConfigureItem) TransportConfigureItem.DOWN_APIDJG_HOST);
        }
        if (TextUtils.equals(host, d)) {
            return mng.getStringValue((ConfigureItem) TransportConfigureItem.DOWN_DLDJG_HOST);
        }
        try {
            String downgradeHost = (String) uriRequest.getParams().getParameter(TransportConstants.KEY_DOWNGRADE_HTTPS_HOST);
            if (!TextUtils.isEmpty(downgradeHost)) {
                LogCatUtil.debug("DownloadUtils", "downgradeHost:" + downgradeHost);
                return downgradeHost;
            }
        } catch (Throwable e) {
            LogCatUtil.warn("DownloadUtils", "get downgradeHost error", e);
        }
        return "";
    }
}
