package com.alipay.mobile.securitycommon.aliauth;

import android.net.Uri;
import android.text.TextUtils;
import com.alipay.mobile.account.adapter.ConfigAdapter;
import com.alipay.mobile.account.adapter.LogAdapter;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Key;
import com.amap.bundle.blutils.app.ConfigerHelper;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.json.JSONArray;

class UrlParser {
    private String[] a = {Key.REDIRECT_URL, "tpl_redirect_url", "TPL_redirect_url", ConfigerHelper.REDIRECT};
    private String[] b = {".taobao.com", ".tmall.com", ".etao.com", ".hitao.com", ".tmall.hk", ".taobao.net", ".1688.com", ".weibo.cn", ".weibo.com", ".mashangfangxin.com"};
    private List<String> c;
    private String d = "[\"^https?:\\\\/\\\\/(?:\\\\w+\\\\.)*?(django\\\\.t\\\\.taobao\\\\.com)\", \"^https?:\\\\/\\\\/(reg\\\\.taobao\\\\.com)\"]";

    UrlParser() {
        updateWhiteList(this.b);
    }

    public void updateWhiteList(String[] strArr) {
        this.c = Arrays.asList(strArr);
    }

    public void updateWhiteList(List<String> list) {
        this.c = list;
    }

    public List<String> getWhiteList() {
        return this.c;
    }

    public String getHost(String str) {
        try {
            return new URI(str).getHost();
        } catch (Exception e) {
            LogAdapter.a((String) "UrlParser", (Throwable) e);
            return "";
        }
    }

    public String getDomain(String str) {
        try {
            String host = new URI(str).getHost();
            return host.substring(host.indexOf(46) + 1);
        } catch (Exception e) {
            LogAdapter.a((String) "UrlParser", (Throwable) e);
            return "";
        }
    }

    public boolean matchBlackList(String str) {
        try {
            ConfigAdapter.a();
            String a2 = ConfigAdapter.a("alu_autologinblacklist");
            if (TextUtils.isEmpty(a2)) {
                LogAdapter.a((String) "UrlParser", (String) "blackListStr config is empty,use default");
                a2 = this.d;
            }
            if (!TextUtils.isEmpty(a2)) {
                JSONArray jSONArray = new JSONArray(a2);
                for (int i = 0; i < jSONArray.length(); i++) {
                    if (Pattern.compile(jSONArray.getString(i)).matcher(str).find()) {
                        StringBuilder sb = new StringBuilder("url:");
                        sb.append(str);
                        sb.append(" match blacklist:");
                        sb.append(jSONArray.getString(i));
                        LogAdapter.a((String) "UrlParser", sb.toString());
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            LogAdapter.a((String) "UrlParser", (Throwable) e);
        }
        return false;
    }

    public boolean matchWhiteList(String str) {
        try {
            String host = new URL(str).getHost();
            for (String next : this.c) {
                if (host.endsWith(next)) {
                    StringBuilder sb = new StringBuilder("host:");
                    sb.append(host);
                    sb.append(" match whitelist:");
                    sb.append(next);
                    LogAdapter.a((String) "UrlParser", sb.toString());
                    return true;
                }
            }
        } catch (Exception e) {
            LogAdapter.a((String) "UrlParser", (Throwable) e);
        }
        return false;
    }

    public String parseRedirectUrl(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                Uri parse = Uri.parse(str);
                for (String queryParameter : this.a) {
                    String queryParameter2 = parse.getQueryParameter(queryParameter);
                    if (!TextUtils.isEmpty(queryParameter2)) {
                        String decode = URLDecoder.decode(queryParameter2, "UTF-8");
                        LogAdapter.a((String) "UrlParser", "从服务端返回结果解析出的redirectUrl：".concat(String.valueOf(decode)));
                        return decode;
                    }
                }
            }
        } catch (Exception e) {
            LogAdapter.a((String) "UrlParser", (Throwable) e);
        }
        return str;
    }
}
