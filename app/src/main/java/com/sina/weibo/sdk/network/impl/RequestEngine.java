package com.sina.weibo.sdk.network.impl;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.alipay.mobile.common.transport.http.multipart.FilePart;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.sina.weibo.sdk.net.NetStateManager;
import com.sina.weibo.sdk.network.IRequestParam;
import com.sina.weibo.sdk.network.IRequestParam.RequestType;
import com.sina.weibo.sdk.network.base.RequestBodyHelper;
import com.sina.weibo.sdk.network.base.UriUtils;
import com.sina.weibo.sdk.network.base.WbResponse;
import com.sina.weibo.sdk.network.base.WbResponseBody;
import com.sina.weibo.sdk.network.exception.RequestException;
import com.sina.weibo.sdk.utils.LogUtil;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class RequestEngine {
    private static void fillRequestBody(HttpURLConnection httpURLConnection, RequestParam requestParam) {
    }

    public static WbResponse request(IRequestParam iRequestParam) throws RequestException {
        HttpURLConnection httpURLConnection;
        String url = iRequestParam.getUrl();
        if (TextUtils.isEmpty(url) || (!url.startsWith("http") && !url.startsWith("https"))) {
            throw new RequestException("非法的请求地址");
        }
        String buildCompleteUri = UriUtils.buildCompleteUri(url, iRequestParam.getGetBundle());
        Pair<String, Integer> apn = NetStateManager.getAPN();
        Proxy proxy = null;
        if (apn != null) {
            proxy = new Proxy(Type.HTTP, new InetSocketAddress((String) apn.first, ((Integer) apn.second).intValue()));
        }
        try {
            URL url2 = new URL(buildCompleteUri);
            if (buildCompleteUri.startsWith("https")) {
                if (proxy == null) {
                    httpURLConnection = (HttpsURLConnection) url2.openConnection();
                } else {
                    httpURLConnection = (HttpsURLConnection) url2.openConnection(proxy);
                }
            } else if (proxy == null) {
                httpURLConnection = (HttpURLConnection) url2.openConnection();
            } else {
                httpURLConnection = (HttpURLConnection) url2.openConnection(proxy);
            }
            setRequestHeader(httpURLConnection, iRequestParam.getHeader());
            Bundle bundle = new Bundle();
            String concat = "------------".concat(String.valueOf(RequestBodyHelper.getBoundry()));
            if (iRequestParam.getMethod() == RequestType.POST) {
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty(H5AppHttpRequest.HEADER_CONNECTION, "Keep-Alive");
                httpURLConnection.setRequestProperty("Charset", "UTF-8");
                httpURLConnection.setUseCaches(false);
                if (iRequestParam.getPostBundle().getByteArray(RequestParam.KEY_PARAM_BODY_BYTE_ARRAY) != null) {
                    bundle.putString("Content-Type", FilePart.DEFAULT_CONTENT_TYPE);
                } else if (RequestBodyHelper.isMultipartRequest(iRequestParam)) {
                    bundle.putString("Content-Type", "multipart/form-data;boundary=".concat(String.valueOf(concat)));
                } else {
                    bundle.putString("Content-Type", "application/x-www-form-urlencoded");
                }
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
            } else if (iRequestParam.getMethod() == RequestType.GET) {
                httpURLConnection.setRequestMethod("GET");
            } else if (iRequestParam.getMethod() == RequestType.PATCH) {
                httpURLConnection.setRequestMethod("PATCH");
            }
            httpURLConnection.setReadTimeout(iRequestParam.getResponseTimeout());
            httpURLConnection.setConnectTimeout(iRequestParam.getRequestTimeout());
            setRequestHeader(httpURLConnection, bundle);
            httpURLConnection.connect();
            if (iRequestParam.getMethod() != RequestType.GET) {
                RequestBodyHelper.fillRequestBody(iRequestParam, httpURLConnection, concat);
            }
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                return new WbResponse(new WbResponseBody(httpURLConnection.getInputStream(), (long) httpURLConnection.getContentLength()));
            }
            if (responseCode != 302) {
                if (responseCode != 301) {
                    WbResponseBody wbResponseBody = new WbResponseBody(httpURLConnection.getErrorStream(), (long) httpURLConnection.getContentLength());
                    StringBuilder sb = new StringBuilder("服务器异常");
                    sb.append(wbResponseBody.string());
                    throw new RequestException(sb.toString());
                }
            }
            iRequestParam.setUrl(httpURLConnection.getHeaderField("Location"));
            return request(iRequestParam);
        } catch (MalformedURLException e) {
            LogUtil.v("weibosdk", e.toString());
            StringBuilder sb2 = new StringBuilder("请求异常");
            sb2.append(e.toString());
            throw new RequestException(sb2.toString());
        } catch (IOException e2) {
            LogUtil.v("weibosdk", e2.toString());
            StringBuilder sb3 = new StringBuilder("请求异常");
            sb3.append(e2.toString());
            throw new RequestException(sb3.toString());
        }
    }

    private static void setRequestHeader(HttpURLConnection httpURLConnection, Bundle bundle) {
        if (bundle != null) {
            for (String str : bundle.keySet()) {
                httpURLConnection.addRequestProperty(str, String.valueOf(bundle.get(str)));
            }
        }
    }
}
