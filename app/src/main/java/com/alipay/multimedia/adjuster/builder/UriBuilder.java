package com.alipay.multimedia.adjuster.builder;

import android.net.Uri;
import android.text.TextUtils;
import com.alipay.multimedia.adjuster.api.data.ITraceId;
import com.alipay.multimedia.adjuster.config.ConfigMgr;
import com.alipay.multimedia.adjuster.utils.AliCdnUtils;
import com.alipay.multimedia.adjuster.utils.Log;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;

public class UriBuilder {
    private static final String AFTS_CDN_HOST_DEV = "http://mmtcdp.stable.alipay.net:443";
    private static final String AFTS_MASTER_HOST_DEV = "https://mdgwdev.alipay.net";
    private static final String DEFAULT_BIZTYPE = "mm_other";
    private static final String DEFAULT_TRACEID = "afts_traceId";
    private static final String TAG = "UriBuilder";
    private static final Log logger = Log.getLogger((String) TAG);
    private static volatile ITraceId mITraceIdBuilder = null;

    private static String buildAftsFileUrlInner(String url, String host, String bizType) {
        if (isAftsUrl(url) || !AliCdnUtils.isHttp(Uri.parse(url))) {
            return url;
        }
        String biz = bizType;
        if (TextUtils.isEmpty(bizType)) {
            biz = "mm_other";
        }
        String urlString = url.substring(url.lastIndexOf("//") + 2, url.length());
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(host).append("/uri/file/").append(urlString);
        if (urlString.contains("?")) {
            strBuilder.append("&bz=").append(biz).append("&tid=").append(genTraceId());
        } else {
            strBuilder.append("?bz=").append(biz).append("&tid=").append(genTraceId());
        }
        return strBuilder.toString();
    }

    public static String buildAftsFilecCdnUrl(String url, String bizType) {
        return buildAftsFileUrlInner(url, getAftsCdnHost(), bizType);
    }

    public static String buildAftsFileMasterUrl(String url, String bizType) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        String host = getAftsMasterHost();
        String[] aftsCdnPrefixs = getAftsCdnPrefixs();
        if (aftsCdnPrefixs != null && aftsCdnPrefixs.length > 0) {
            for (String prefix : aftsCdnPrefixs) {
                if (url.contains(prefix)) {
                    String httpsUrl = url.replace("http:", "https:");
                    String prefixWithHttps = prefix;
                    if (!prefix.startsWith("http")) {
                        prefixWithHttps = new StringBuilder(AjxHttpLoader.DOMAIN_HTTPS).append(prefix).toString();
                    }
                    return httpsUrl.replace(prefixWithHttps, host);
                }
            }
        }
        return buildAftsFileUrlInner(url, host, bizType);
    }

    private static String genTraceId() {
        String traceId = null;
        try {
            if (mITraceIdBuilder != null) {
                traceId = mITraceIdBuilder.genTraceId();
            }
        } catch (Exception e) {
            traceId = DEFAULT_TRACEID;
        }
        if (TextUtils.isEmpty(traceId)) {
            return DEFAULT_TRACEID;
        }
        return traceId;
    }

    private static boolean isAftsUrl(String url) {
        String[] prefixs = getAftsCdnPrefixs();
        if (TextUtils.isEmpty(url) || prefixs == null || prefixs.length <= 0) {
            return false;
        }
        for (String prefix : prefixs) {
            if (url.contains(prefix)) {
                return true;
            }
        }
        return false;
    }

    public static String getAftsCdnHost() {
        return isOnline() ? ConfigMgr.getInc().getCdnConfigItem().aftsCdnHost : AFTS_CDN_HOST_DEV;
    }

    public static String getAftsMasterHost() {
        return isOnline() ? ConfigMgr.getInc().getCdnConfigItem().aftsMasterHost : AFTS_MASTER_HOST_DEV;
    }

    public static String[] getAftsCdnPrefixs() {
        return ConfigMgr.getInc().getCdnConfigItem().aftsCdnPrefixs;
    }

    public static boolean isOnline() {
        return true;
    }

    public static void registerITraceId(ITraceId traceIdBuilder) {
        mITraceIdBuilder = traceIdBuilder;
    }
}
