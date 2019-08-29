package com.alipay.mobile.common.nbnet.biz.util;

import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.alipay.mobile.common.nbnet.biz.GlobalNBNetContext;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.net.MalformedURLException;
import java.net.URL;

public final class URLConfigUtil {
    public static String a = "mmtcup-d7935.alipay.net:443";
    public static String b = new StringBuilder(AjxHttpLoader.DOMAIN_HTTP).append(a).toString();
    public static String c = new StringBuilder(AjxHttpLoader.DOMAIN_HTTP).append(a).append("/requestUpload.api").toString();
    private static String d = "http://mugw.alipay.com:443";
    private static String e = "http://mdgw.alipay.com:443";

    public static final URL a() {
        try {
            String uploadServer = d();
            if (!TextUtils.isEmpty(uploadServer)) {
                return new URL(uploadServer + "/requestUpload.api");
            }
            return new URL(d + "/requestUpload.api");
        } catch (MalformedURLException e2) {
            NBNetLogCat.b((String) "URLConfigUtil", (Throwable) e2);
            return null;
        }
    }

    private static String d() {
        String mmupgw = NBNetConfigUtil.a(NBNetEnvUtils.a(), "mmupgw.url", "");
        if (!TextUtils.isEmpty(mmupgw)) {
            return mmupgw;
        }
        if (!NBNetEnvUtils.b()) {
            return d;
        }
        return a("content://com.alipay.setting/mugw_server", "");
    }

    public static final URL b() {
        String uploadServer = d();
        if (!TextUtils.isEmpty(uploadServer)) {
            return new URL(uploadServer);
        }
        return new URL(d);
    }

    public static final URL c() {
        String downloadServerUrl = e();
        if (!TextUtils.isEmpty(downloadServerUrl)) {
            return new URL(downloadServerUrl);
        }
        return new URL(e);
    }

    private static String e() {
        String mmdpgwUrl = NBNetConfigUtil.a(NBNetEnvUtils.a(), "mmdpgw.url", "");
        if (!TextUtils.isEmpty(mmdpgwUrl)) {
            return mmdpgwUrl;
        }
        String urlOfSetting = a("content://com.alipay.setting/mdgw_server", "");
        if (TextUtils.isEmpty(urlOfSetting)) {
            return e;
        }
        if (urlOfSetting.startsWith(AjxHttpLoader.DOMAIN_HTTP)) {
            return urlOfSetting;
        }
        if (urlOfSetting.indexOf(":") > 0) {
            return new StringBuilder(AjxHttpLoader.DOMAIN_HTTP).append(urlOfSetting).toString();
        }
        return new StringBuilder(AjxHttpLoader.DOMAIN_HTTP).append(urlOfSetting).append(":443").toString();
    }

    private static String a(String uri, String defaultVal) {
        if (!NBNetEnvUtils.b()) {
            return defaultVal;
        }
        Cursor cursor = GlobalNBNetContext.a().getApplicationContext().getContentResolver().query(Uri.parse(uri), null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String ret = cursor.getString(0);
            NBNetLogCat.a((String) "URLConfigUtil", "[readSetting] uri:" + uri + ", value:" + ret + ", defaultVal:" + defaultVal);
            cursor.close();
            if (!TextUtils.isEmpty(ret)) {
                return ret;
            }
            return defaultVal;
        } else if (cursor == null || cursor.isClosed()) {
            return defaultVal;
        } else {
            cursor.close();
            return defaultVal;
        }
    }
}
