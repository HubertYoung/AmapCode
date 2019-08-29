package com.alipay.mobile.nebulauc.impl.network;

import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.util.H5IOUtils;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ResourceCORSUtil;
import com.alipay.mobile.nebula.util.H5Utils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class FallbackRequestHelper {
    private static final String TAG = "FallbackRequestHelper";

    public static InputStream setFallbackCache(InputStream inputStream, boolean isGzip, H5Page h5Page, String fallbackOriginUrl) {
        H5ContentProvider h5ContentProvider;
        if (isGzip) {
            H5Log.d(TAG, "fallback request, convert to GZIPInputStream, fallbackUrl : " + fallbackOriginUrl);
            try {
                inputStream = new GZIPInputStream(inputStream);
            } catch (Throwable e) {
                H5Log.e(TAG, "new GZIPInputStream exception, fallbackUrl : " + fallbackOriginUrl, e);
                H5LogUtil.logNebulaTech(H5LogData.seedId("H5_FallbackException").param1().add("fromAsyncFallback", "true").param4().add("走fallback请求失败," + fallbackOriginUrl + " failed to init stream " + e, null));
            }
        }
        H5Log.d(TAG, "setFallbackCache, fallbackUrl : " + fallbackOriginUrl);
        if (!(inputStream == null || h5Page == null)) {
            H5Session session = h5Page.getSession();
            if (session != null) {
                h5ContentProvider = session.getWebProvider();
            } else {
                h5ContentProvider = null;
            }
            if (h5ContentProvider != null) {
                try {
                    byte[] bytes = H5IOUtils.inputToByte(inputStream);
                    if (bytes != null) {
                        h5ContentProvider.setFallbackCache(fallbackOriginUrl, bytes);
                        H5Log.d(TAG, "set fallback cache success");
                    }
                    H5IOUtils.closeQuietly(inputStream);
                    return new ByteArrayInputStream(bytes);
                } catch (Throwable e2) {
                    H5Log.e(TAG, "set fallback cache exception, fallbackUrl : " + fallbackOriginUrl, e2);
                }
            }
        }
        return null;
    }

    public static Map<String, List<String>> getFallbackHeaders(String pageUrl, String requestUrl, String contentLength, List<String> contentType) {
        Map fallbackHeaders = new HashMap();
        List contentLengthList = new ArrayList();
        contentLengthList.add(contentLength);
        fallbackHeaders.put("Content-Length", contentLengthList);
        fallbackHeaders.put("Content-Type", contentType);
        List cache = new ArrayList();
        cache.add("no-cache");
        fallbackHeaders.put("Cache-Control", cache);
        if (!TextUtils.isEmpty(pageUrl)) {
            String corsUrl = H5ResourceCORSUtil.getCORSUrl(pageUrl);
            if (!TextUtils.isEmpty(corsUrl) && (H5ResourceCORSUtil.needAddHeader(pageUrl) || ((H5Utils.enableCheckCrossOrigin() && H5Utils.containNebulaAddcors(requestUrl)) || H5Utils.addChooseImageCrossOrigin(requestUrl)))) {
                List acaoHeaders = new ArrayList();
                acaoHeaders.add(corsUrl);
                fallbackHeaders.put("Access-Control-Allow-Origin", acaoHeaders);
                H5Log.d(TAG, "add fallback header: Access-Control-Allow-Origin corsUrl : " + corsUrl);
            }
        }
        return fallbackHeaders;
    }

    public static boolean isFallbackRequest(String url, H5Page h5Page) {
        H5ContentProvider h5ContentProvider;
        String fallbackUrl = null;
        if (h5Page == null) {
            return false;
        }
        H5Session session = h5Page.getSession();
        if (session != null) {
            h5ContentProvider = session.getWebProvider();
        } else {
            h5ContentProvider = null;
        }
        if (h5ContentProvider != null) {
            fallbackUrl = h5ContentProvider.getFallbackUrl(url);
        }
        H5Log.d(TAG, "handleFallbackRequest originUrl : " + url + ", fallbackUrl : " + fallbackUrl);
        if (!TextUtils.isEmpty(fallbackUrl)) {
            return true;
        }
        return false;
    }
}
