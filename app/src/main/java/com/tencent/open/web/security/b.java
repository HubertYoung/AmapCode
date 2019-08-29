package com.tencent.open.web.security;

import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;
import com.tencent.open.a;
import com.tencent.open.a.C0065a;
import com.tencent.open.a.f;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: ProGuard */
public class b extends a {
    public void a(String str, String str2, List<String> list, C0065a aVar) {
        StringBuilder sb = new StringBuilder("-->getResult, objectName: ");
        sb.append(str);
        sb.append(" | methodName: ");
        sb.append(str2);
        f.a("openSDK_LOG.SecureJsBridge", sb.toString());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            try {
                list.set(i, URLDecoder.decode(list.get(i), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        com.tencent.open.a.b bVar = (com.tencent.open.a.b) this.a.get(str);
        if (bVar != null) {
            f.b("openSDK_LOG.SecureJsBridge", "-->handler != null");
            bVar.call(str2, list, aVar);
            return;
        }
        f.b("openSDK_LOG.SecureJsBridge", "-->handler == null");
        if (aVar != null) {
            aVar.a();
        }
    }

    public boolean a(WebView webView, String str) {
        f.a("openSDK_LOG.SecureJsBridge", "-->canHandleUrl---url = ".concat(String.valueOf(str)));
        if (str == null || !Uri.parse(str).getScheme().equals("jsbridge")) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("/#");
        ArrayList arrayList = new ArrayList(Arrays.asList(sb.toString().split("/")));
        if (arrayList.size() < 7) {
            return false;
        }
        String str2 = (String) arrayList.get(2);
        String str3 = (String) arrayList.get(3);
        String str4 = (String) arrayList.get(4);
        String str5 = (String) arrayList.get(5);
        StringBuilder sb2 = new StringBuilder("-->canHandleUrl, objectName: ");
        sb2.append(str2);
        sb2.append(" | methodName: ");
        sb2.append(str3);
        sb2.append(" | snStr: ");
        sb2.append(str4);
        f.a("openSDK_LOG.SecureJsBridge", sb2.toString());
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4)) {
            return false;
        }
        try {
            c cVar = new c(webView, Long.parseLong(str4), str, str5);
            a(str2, str3, arrayList.subList(6, arrayList.size() - 1), cVar);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }
}
