package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.impl;

import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.TokenManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.EnvSwitcher;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.DjangoConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.DjangoConf.RefreshTokenErrorCode;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.MultipartFormEntity;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ReflectUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.mobile.common.transport.multimedia.DjgHttpManager;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpMessage;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class HttpClientProxy implements HttpClient {
    private DjgHttpManager a;
    private boolean b;
    private String c;
    private String d;
    private String e;

    public HttpClientProxy() {
        this(false);
    }

    public HttpClientProxy(boolean bHttps) {
        this.b = false;
        this.b = bHttps;
        this.a = new DjgHttpManager(AppUtils.getApplicationContext());
    }

    private DjgHttpManager a() {
        if (this.a == null) {
            this.a = new DjgHttpManager(AppUtils.getApplicationContext());
        }
        return this.a;
    }

    public HttpParams getParams() {
        return null;
    }

    public ClientConnectionManager getConnectionManager() {
        return null;
    }

    public HttpResponse execute(HttpUriRequest httpUriRequest) {
        HttpResponse response;
        if (a(httpUriRequest) || c()) {
            response = b(httpUriRequest);
        } else if (i()) {
            response = executeHttpDjgManager(httpUriRequest);
        } else {
            response = a().execute(httpUriRequest);
        }
        a(httpUriRequest, response);
        return response;
    }

    private boolean a(HttpUriRequest req) {
        boolean ret = d();
        if (!ret || req == null || EnvSwitcher.getEnv(AppUtils.getApplicationContext(), 0) != 0) {
            return ret;
        }
        String host = req.getURI().getHost();
        this.c = TextUtils.isEmpty(this.c) ? EnvSwitcher.getCurrentEnv().getServerAddress().getApiServerHost() : this.c;
        this.d = TextUtils.isEmpty(this.d) ? EnvSwitcher.getCurrentEnv().getServerAddress().getDownloadServerHost() : this.d;
        this.e = TextUtils.isEmpty(this.e) ? EnvSwitcher.getCurrentEnv().getServerAddress().getUploadServerHost() : this.e;
        if (this.d.equalsIgnoreCase(host)) {
            return e();
        }
        if (this.e.equalsIgnoreCase(host)) {
            return f();
        }
        if (this.c.equalsIgnoreCase(host)) {
            return g();
        }
        return ret;
    }

    private static void a(HttpUriRequest httpUriRequest, HttpResponse response) {
        DjangoConf conf = ConfigManager.getInstance().djangoConf();
        if (conf.isUseDjangoTokenPool() && response != null && httpUriRequest != null) {
            StatusLine statusLine = response.getStatusLine();
            if (statusLine != null) {
                int httpCode = statusLine.getStatusCode();
                RefreshTokenErrorCode code = conf.refreshTokenErrorCodeMap().get(Integer.valueOf(httpCode));
                if (200 != httpCode) {
                    Logger.D("HttpClientProxy", "checkTokenExpired httpCode: " + httpCode + ", refreshTokenErrorCode: " + code, new Object[0]);
                }
                if (code != null) {
                    if (conf.isCheckHosts()) {
                        List hosts = b(httpUriRequest, response);
                        List includeHosts = conf.errorInHosts();
                        boolean includeHostFlag = false;
                        for (int i = 0; i < hosts.size() && !includeHostFlag; i++) {
                            for (int j = 0; j < includeHosts.size() && !includeHostFlag; j++) {
                                includeHostFlag = hosts.get(i).endsWith(includeHosts.get(j));
                            }
                        }
                        if (!includeHostFlag) {
                            return;
                        }
                    }
                    boolean needRefresh = TextUtils.isEmpty(code.header) || TextUtils.isEmpty(code.headerValue);
                    if (!needRefresh) {
                        Header[] headers = response.getHeaders(code.header);
                        if (headers != null && headers.length > 0) {
                            int length = headers.length;
                            int i2 = 0;
                            while (true) {
                                if (i2 >= length) {
                                    break;
                                }
                                if (code.headerValue.equals(headers[i2].getValue())) {
                                    needRefresh = true;
                                    break;
                                }
                                i2++;
                            }
                        }
                    }
                    if (needRefresh) {
                        Logger.D("HttpClientProxy", "checkTokenExpired needRefresh true", new Object[0]);
                        TokenManager.get().expiredCurrentToken();
                        TokenManager.get().updateToken();
                    }
                }
            }
        }
    }

    private static List<String> b(HttpUriRequest httpUriRequest, HttpResponse response) {
        Set hosts = new HashSet();
        if (!(httpUriRequest == null || response == null)) {
            hosts.add(httpUriRequest.getURI().getHost());
            HttpMessage[] httpMessageArr = {httpUriRequest, response};
            for (int i = 0; i < 2; i++) {
                for (Header header : httpMessageArr[i].getHeaders("Host")) {
                    hosts.add(header.getValue());
                }
            }
            Logger.D("HttpClientProxy", "parseHosts: " + hosts, new Object[0]);
        }
        return new ArrayList(hosts);
    }

    public HttpResponse execute(HttpUriRequest httpUriRequest, HttpContext httpContext) {
        throw new UnsupportedOperationException("use djgHttpManager Exception");
    }

    public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest) {
        throw new UnsupportedOperationException("use djgHttpManager Exception");
    }

    public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) {
        throw new UnsupportedOperationException("use djgHttpManager Exception");
    }

    public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler) {
        throw new UnsupportedOperationException("use djgHttpManager Exception");
    }

    public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) {
        throw new UnsupportedOperationException("use djgHttpManager Exception");
    }

    public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler) {
        throw new UnsupportedOperationException("use djgHttpManager Exception");
    }

    public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) {
        throw new UnsupportedOperationException("use djgHttpManager Exception");
    }

    public HttpResponse executeHttpDjgManager(HttpUriRequest httpUriRequest) {
        HttpResponse rsp = null;
        int retryCount = 0;
        long start = System.currentTimeMillis();
        String host = httpUriRequest.getURI().getHost();
        boolean bRetry = false;
        do {
            String uriHost = httpUriRequest.getURI().getHost();
            Logger.D("HttpClientProxy", "executeNew retryCount=" + retryCount + ";bRetry=" + bRetry + ";host=" + host + ";uriHost=" + uriHost, new Object[0]);
            retryCount++;
            try {
                httpUriRequest = a(httpUriRequest, bRetry, host);
                bRetry = false;
                rsp = a().execute(httpUriRequest);
                if (isDjgHttpMgrRspNeedRetry(httpUriRequest, rsp)) {
                    bRetry = true;
                    Logger.D("HttpClientProxy", "executeHttpDjgManager fail rsp code=" + rsp.getStatusLine().getStatusCode() + ";uri=" + httpUriRequest.getURI(), new Object[0]);
                } else {
                    retryCount = 3;
                }
            } catch (IOException e2) {
                UCLogUtil.UC_MM_C20(0, (int) (System.currentTimeMillis() - start), httpUriRequest.getURI().toString(), CommonUtils.getExceptionMsg(e2));
                Logger.D("HttpClientProxy", "executeHttpDjgManager exp " + httpUriRequest.getURI() + ";retryCount=" + retryCount, new Object[0]);
                Logger.E((String) "HttpClientProxy", (Throwable) e2, "executeHttpDjgManager exp " + uriHost, new Object[0]);
                if (!h() || d(httpUriRequest) || !e(httpUriRequest)) {
                    throw a(e2, uriHost);
                }
                bRetry = true;
            }
        } while (retryCount < 3);
        if (!(rsp == null || rsp.getStatusLine().getStatusCode() == 200)) {
            Logger.D("HttpClientProxy", "executeHttpDjgManager rsp code=" + rsp.getStatusLine().getStatusCode() + ";uri=" + httpUriRequest.getURI(), new Object[0]);
        }
        return rsp;
    }

    private HttpResponse b(HttpUriRequest httpUriRequest) {
        HttpUriRequest httpUriRequest2 = a(httpUriRequest, true, httpUriRequest.getURI().getHost());
        long start = System.currentTimeMillis();
        try {
            HttpResponse rsp = a().execute(httpUriRequest2);
            if (!(rsp == null || rsp.getStatusLine().getStatusCode() == 200)) {
                Logger.D("HttpClientProxy", "executeHttpsDjgManager rsp code=" + rsp.getStatusLine().getStatusCode() + ";uri=" + httpUriRequest2.getURI(), new Object[0]);
            }
            return rsp;
        } catch (IOException e2) {
            UCLogUtil.UC_MM_C20(0, (int) (System.currentTimeMillis() - start), httpUriRequest2.getURI().toString(), CommonUtils.getExceptionMsg(e2));
            Logger.D("HttpClientProxy", "executeHttpsDjgManager exp uri=" + httpUriRequest2.getURI(), new Object[0]);
            throw a(e2, httpUriRequest2.getURI().getHost());
        }
    }

    public boolean isDjgHttpMgrRspNeedRetry(HttpUriRequest req, HttpResponse rsp) {
        if (rsp == null || req == null || d(req) || !e(req)) {
            return false;
        }
        if (CommonUtils.isNeedRetry(rsp.getStatusLine().getStatusCode()) || (a(rsp) && c(req))) {
            return true;
        }
        return false;
    }

    private static boolean a(HttpResponse rsp) {
        String typeKey = ConfigManager.getInstance().getCommonConfigItem().net.contentTypeKey;
        if (rsp == null || rsp.getStatusLine().getStatusCode() != 200 || TextUtils.isEmpty(typeKey)) {
            return false;
        }
        Header[] headers = rsp.getHeaders("Content-Type");
        Header ct = null;
        if (headers != null) {
            int i = 0;
            while (true) {
                if (i >= headers.length) {
                    break;
                } else if (headers[i].getName().equalsIgnoreCase("Content-Type")) {
                    ct = headers[i];
                    break;
                } else {
                    i++;
                }
            }
        }
        if (ct == null || TextUtils.isEmpty(ct.getValue()) || !ct.getValue().contains(typeKey)) {
            return false;
        }
        Logger.D("HttpClientProxy", "isNeedToRetryByContentType ContentType=" + ct.getValue() + ";typeKey=" + typeKey, new Object[0]);
        return true;
    }

    private static boolean c(HttpUriRequest req) {
        return "GET".equalsIgnoreCase(req.getMethod());
    }

    private static boolean d(HttpUriRequest req) {
        return "https".equalsIgnoreCase(req.getURI().getScheme());
    }

    private static boolean b() {
        return ConfigManager.getInstance().getCommonConfigItem().net.newHttpsSwitch == 1;
    }

    private boolean c() {
        return this.b && ConfigManager.getInstance().getCommonConfigItem().net.predownHttpsSwitch == 1;
    }

    private static boolean d() {
        return ConfigManager.getInstance().getCommonConfigItem().net.allHttpsSwitch == 1;
    }

    private static boolean e() {
        return ConfigManager.getInstance().getCommonConfigItem().net.allDownHttpsSwitch == 1;
    }

    private static boolean f() {
        return ConfigManager.getInstance().getCommonConfigItem().net.allUpHttpsSwitch == 1;
    }

    private static boolean g() {
        return ConfigManager.getInstance().getCommonConfigItem().net.allApiHttpsSwitch == 1;
    }

    private static boolean h() {
        return ConfigManager.getInstance().getCommonConfigItem().net.expswitch == 1;
    }

    private static IOException a(IOException e2, String uriHost) {
        if (VERSION.SDK_INT >= 27) {
            return new IOException(e2.getMessage() + ",h:" + uriHost);
        }
        ReflectUtils.setField(e2, ReflectUtils.getField(Throwable.class, "detailMessage"), e2.getMessage() + ",h:" + uriHost);
        return e2;
    }

    private HttpUriRequest a(HttpUriRequest req, boolean bRetry, String hostName) {
        URI uri = req.getURI();
        if (bRetry && "http".equalsIgnoreCase(uri.getScheme()) && b()) {
            String host = a(hostName);
            if ((req instanceof HttpRequestBase) && !TextUtils.isEmpty(host)) {
                ((HttpRequestBase) req).setURI(CommonUtils.changeUriByParams(uri, "https", host, 443));
            }
        }
        return req;
    }

    private String a(String host) {
        this.c = TextUtils.isEmpty(this.c) ? EnvSwitcher.getCurrentEnv().getServerAddress().getApiServerHost() : this.c;
        this.d = TextUtils.isEmpty(this.d) ? EnvSwitcher.getCurrentEnv().getServerAddress().getDownloadServerHost() : this.d;
        this.e = TextUtils.isEmpty(this.e) ? EnvSwitcher.getCurrentEnv().getServerAddress().getUploadServerHost() : this.e;
        if (EnvSwitcher.getEnv(AppUtils.getApplicationContext(), 0) == 0) {
            if (this.c.equalsIgnoreCase(host)) {
                return ConfigManager.getInstance().getCommonConfigItem().net.apiHttpsHost;
            }
            if (this.d.equalsIgnoreCase(host)) {
                return ConfigManager.getInstance().getCommonConfigItem().net.dlHttpsHost;
            }
            if (this.e.equalsIgnoreCase(host)) {
                return ConfigManager.getInstance().getCommonConfigItem().net.upHttpsHost;
            }
        }
        return null;
    }

    private static boolean e(HttpUriRequest request) {
        if (request instanceof HttpEntityEnclosingRequest) {
            HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
            if (entity instanceof MultipartFormEntity) {
                return ((MultipartFormEntity) entity).isRepeatableEntity();
            }
        }
        return true;
    }

    private static boolean i() {
        return ConfigManager.getInstance().getCommonConfigItem().net.djgMgrHttps == 1;
    }
}
