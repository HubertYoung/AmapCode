package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.io.IOException;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public class HttpClientUtils {
    public static final String HTTP_CONTENT_TYPE = "Content-Type";

    public static void consumeQuietly(HttpEntity entity) {
        try {
            consume(entity);
        } catch (IOException e) {
        }
    }

    public static void consume(HttpEntity entity) {
        if (entity != null && entity.isStreaming()) {
            IOUtils.closeQuietly(entity.getContent());
        }
    }

    public static String urlAppendParams(String url, List<NameValuePair> params) {
        return url + "?" + URLEncodedUtils.format(params, "UTF-8");
    }

    private static boolean a() {
        return ConfigManager.getInstance().getCommonConfigItem().net.checkContentSwitch == 1;
    }

    public static boolean checkRspContentSizeAndType(HttpResponse rsp) {
        if (rsp == null || !a()) {
            return true;
        }
        long size = rsp.getEntity().getContentLength();
        if (size > 0 || !a(rsp, size)) {
            return true;
        }
        return false;
    }

    private static boolean a(HttpResponse rsp, long size) {
        String typeKey = ConfigManager.getInstance().getCommonConfigItem().net.contentTypeKey;
        Header[] hds = rsp.getHeaders("Content-Type");
        Header ct = null;
        if (hds == null || hds.length <= 0) {
            return false;
        }
        int i = 0;
        while (true) {
            if (i >= hds.length) {
                break;
            } else if (hds[i].getName().equalsIgnoreCase("Content-Type")) {
                ct = hds[i];
                break;
            } else {
                i++;
            }
        }
        if (ct == null) {
            return false;
        }
        String value = ct.getValue();
        Logger.D("HttpClientUtils", "checkRspContentSizeAndType value=" + value + ";size=" + size, new Object[0]);
        if (TextUtils.isEmpty(value) || !value.contains(typeKey)) {
            return false;
        }
        return true;
    }
}
