package com.sina.weibo.sdk.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class ConnectionFactory {
    public static HttpURLConnection createConnect(String str, Context context) {
        HttpURLConnection httpURLConnection;
        if (TextUtils.isEmpty(str) || (!str.startsWith(AjxHttpLoader.DOMAIN_HTTP) && !str.startsWith(AjxHttpLoader.DOMAIN_HTTPS))) {
            throw new RuntimeException("非法url请求");
        }
        try {
            URL url = new URL(str);
            Pair<String, Integer> apn = NetStateManager.getAPN();
            Proxy proxy = apn != null ? new Proxy(Type.HTTP, new InetSocketAddress((String) apn.first, ((Integer) apn.second).intValue())) : null;
            httpURLConnection = str.startsWith(AjxHttpLoader.DOMAIN_HTTP) ? proxy == null ? (HttpURLConnection) url.openConnection() : (HttpURLConnection) url.openConnection(proxy) : proxy == null ? (HttpsURLConnection) url.openConnection() : (HttpsURLConnection) url.openConnection(proxy);
        } catch (IOException | MalformedURLException unused) {
            httpURLConnection = null;
        }
        httpURLConnection.setUseCaches(false);
        try {
            httpURLConnection.setRequestMethod("POST");
        } catch (ProtocolException unused2) {
        }
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty(H5AppHttpRequest.HEADER_CONNECTION, "Keep-Alive");
        httpURLConnection.setRequestProperty("Charset", "UTF-8");
        httpURLConnection.setReadTimeout(20000);
        httpURLConnection.setConnectTimeout(25000);
        return httpURLConnection;
    }
}
