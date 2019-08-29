package com.alipay.mobile.common.nbnet.biz.token;

import android.text.TextUtils;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.util.NBNetEnvUtils;
import com.alipay.mobile.common.nbnet.biz.util.ProtocolUtils;
import com.alipay.mobile.common.nbnet.biz.util.URLConfigUtil;
import com.alipay.mobile.common.transport.http.HeaderMap;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import java.net.URL;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class NBNetTokenRequest {
    private URL a = URLConfigUtil.a();
    private String b = ("POST " + this.a.getPath() + ' ' + "HTTP/1.1");
    private String c;

    public final byte[] a() {
        return ProtocolUtils.a(d(), this.b);
    }

    private Map<String, String> d() {
        Map header = new HeaderMap();
        header.put("host", ProtocolUtils.a(this.a));
        header.put("content-type", "application/json;charset=utf-8");
        header.put(H5AppHttpRequest.HEADER_CONNECTION, "Keep-Alive");
        header.put(H5AppHttpRequest.HEADER_UA, "android-nbnet");
        header.put("x-arup-version", "1.0");
        header.put("x-arup-appkey", NBNetEnvUtils.c());
        header.put("x-arup-appversion", NBNetEnvUtils.f());
        header.put("x-arup-device-id", NBNetEnvUtils.e());
        String userIdStr = NBNetEnvUtils.d();
        if (!TextUtils.isEmpty(userIdStr)) {
            header.put("x-arup-userinfo", userIdStr);
        }
        String timestamp = String.valueOf(System.currentTimeMillis());
        header.put("x-arup-timestamp", timestamp);
        String body = b();
        StringBuilder signBuilder = new StringBuilder();
        signBuilder.append(NBNetEnvUtils.c());
        signBuilder.append(timestamp);
        header.put("x-arup-sign", TokenSignFactory.a().a(signBuilder.toString()));
        header.put("Content-Length", String.valueOf(body.getBytes("UTF-8").length));
        NBNetLogCat.a((String) "NBNetTokenRequest", header.toString());
        return header;
    }

    public final String b() {
        if (!TextUtils.isEmpty(this.c)) {
            return this.c;
        }
        try {
            JSONObject fileInfo = new JSONObject();
            fileInfo.put("biztype", NBNetEnvUtils.h());
            JSONArray fileInfoList = new JSONArray();
            fileInfoList.put(fileInfo);
            this.c = fileInfoList.toString();
            NBNetLogCat.a((String) "NBNetTokenRequest", "getBody. content=" + this.c);
        } catch (Throwable e) {
            NBNetLogCat.b("NBNetTokenRequest", "getBody", e);
        }
        return this.c;
    }

    public final URL c() {
        return this.a;
    }
}
